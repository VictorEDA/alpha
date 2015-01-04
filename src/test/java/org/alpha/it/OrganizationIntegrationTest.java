package org.alpha.it;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.alpha.BaseTest;
import org.alpha.entities.Organization;
import org.alpha.entities.User;
import org.alpha.rest.ObjectMapperProvider;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Integration test for organization and user resources.
 */
public class OrganizationIntegrationTest extends BaseTest {

    /**
     * The API endpoint.
     */
    private static final String ENDPOINT = "http://localhost:8080/";

    /**
     * The REST client.
     */
    private static Client client;

    /**
     * Set up test environment for class.
     */
    @BeforeClass
    public static void setUpClass() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(ObjectMapperProvider.class);
        client = ClientBuilder.newClient(clientConfig);
    }

    /**
     * X.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Basic test for create and get API methods.
     */
    @Test
    public void test_create_get() {
        // Create organization
        Organization organization = createOrganization("OrgName");

        // Get organization
        WebTarget webTarget = client.target(ENDPOINT + "organizations/" + organization.getId());
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals("Response should be OK.", Status.OK.getStatusCode(), response.getStatus());
        Organization copy = response.readEntity(Organization.class);
        assertTrue("Organization should match the one created.",
                EqualsBuilder.reflectionEquals(organization, copy));
    }

    /**
     * Create organization.
     * @param orgName The organization name.
     * @return the organization
     */
    private Organization createOrganization(final String orgName) {
        WebTarget webTarget = client.target(ENDPOINT + "organizations");
        Map<String, String> json = new HashMap<>();
        json.put("organization_name", orgName);
        Response response =
                webTarget.request(MediaType.APPLICATION_JSON).post(
                        Entity.entity(json, MediaType.APPLICATION_JSON));

        assertEquals("Organization should be created.", Status.CREATED.getStatusCode(), response.getStatus());
        Organization organization = response.readEntity(Organization.class);
        checkOrganizationCreate(orgName, organization);
        return organization;
    }

    /**
     * Check to make sure organization was created correctly.
     * @param orgName The organization name.
     * @param organization The organization to check.
     */
    private void checkOrganizationCreate(final String orgName, Organization organization) {
        assertEquals("Organization name should be correct.", orgName, organization.getName());
        assertThat("Access token must be valid.", organization.getAccessToken(), not(isEmptyOrNullString()));
        assertThat("Organization id must be valid.", organization.getId(), greaterThan(0L));
        assertEquals("Created and updated dates must be the same.", organization.getCreatedAt(),
                organization.getUpdatedAt());
        Date date = new Date();
        assertThat("Creation date must be correct.", organization.getCreatedAt().getTime(),
                lessThanOrEqualTo(date.getTime()));
        assertEquals("No users should exist.", 0, organization.getUsers().size());
    }

    /**
     * Basic test for create and get API methods with users.
     */
    @Test
    public void test_create_get_users() {
        // Create organization
        final String orgName = "Org With Users";
        Organization organization = createOrganization(orgName);

        // Create a couple users.
        Set<User> users = new HashSet<>();
        users.add(createUser("abc", organization));
        users.add(createUser("def", organization));

        // Get organization
        WebTarget webTarget = client.target(ENDPOINT + "organizations/" + organization.getId());
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals("Response should be OK.", Status.OK.getStatusCode(), response.getStatus());
        Organization copy = response.readEntity(Organization.class);
        assertEquals("Organization copy name should be correct.", orgName, copy.getName());
        assertEquals("Organization should have 2 users.", 2, copy.getUsers().size());
        // Compare the users
        int usersCompared = 0;
        for (User copyUser : copy.getUsers()) {
            for (User user : users) {
                if (user.getId() == copyUser.getId()) {
                    assertTrue("User should match the one created.", EqualsBuilder.reflectionEquals(user, copyUser));
                    usersCompared++;
                    break;
                }
            }
        }
        assertEquals("We should have compared 2 users.", 2, usersCompared);
    }

    /**
     * Create a user.
     * @param customerUserId The customer user id.
     * @param organization The organization for the user.
     * @return the created user
     */
    private User createUser(String customerUserId, Organization organization) {
        WebTarget webTarget = client.target(ENDPOINT + "organizations/" + organization.getId() + "/users");
        Map<String, String> json = new HashMap<>();
        json.put("customer_user_id", customerUserId);
        Response response =
                webTarget.request(MediaType.APPLICATION_JSON).post(
                        Entity.entity(json, MediaType.APPLICATION_JSON));
        assertEquals("User should be created.", Status.CREATED.getStatusCode(), response.getStatus());
        User user = response.readEntity(User.class);
        checkUserCreate(customerUserId, user);
        return user;
    }

    /**
     * Check that user was created correctly.
     * @param customerUserId The customer user id.
     * @param user The user to check.
     */
    private void checkUserCreate(String customerUserId, User user) {
        assertEquals("Customer user id should be correct.", customerUserId, user.getCustomerUserId());
        assertThat("Access token must be valid.", user.getAccessToken(), not(isEmptyOrNullString()));
        assertThat("Organization id must be valid.", user.getId(), greaterThan(0L));
        assertEquals("Created and updated dates must be the same.", user.getCreatedAt(), user.getUpdatedAt());
        Date date = new Date();
        assertThat("Creation date must be correct.", user.getCreatedAt().getTime(),
                lessThanOrEqualTo(date.getTime()));
    }

    /**
     * Failure test for bad create request.
     */
    @Test
    public void test_create_bad_request() {
        WebTarget webTarget = client.target(ENDPOINT + "organizations");
        Map<String, String> json = new HashMap<>();
        // Set empty organization name.
        json.put("organization_name", "");
        Response response =
                webTarget.request(MediaType.APPLICATION_JSON).post(
                        Entity.entity(json, MediaType.APPLICATION_JSON));
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    /**
     * Failure test when organization already exists.
     */
    @Test
    public void test_create_exists() {
        final String orgName = "Double Org";
        createOrganization(orgName);

        // Send the request in again.
        WebTarget webTarget = client.target(ENDPOINT + "organizations");
        Map<String, String> json = new HashMap<>();
        json.put("organization_name", orgName);
        Response response =
                webTarget.request(MediaType.APPLICATION_JSON).post(
                        Entity.entity(json, MediaType.APPLICATION_JSON));
        assertEquals(Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    /**
     * Failure test when organization cannot be found or bad request is given.
     */
    @Test
    public void test_get_not_found() {
        WebTarget webTarget = client.target(ENDPOINT + "organizations/bad");
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        webTarget = client.target(ENDPOINT + "organizations/-1");
        response = webTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

        webTarget = client.target(ENDPOINT + "organizations/1234566");
        response = webTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    /**
     * Failure test for bad create user request.
     */
    @Test
    public void test_create_user_bad_request() {
        Organization organization = createOrganization("test_create_user_bad_request");
        WebTarget webTarget = client.target(ENDPOINT + "organizations/" + organization.getId() + "/users");
        Map<String, String> json = new HashMap<>();
        // Null customer user id
        json.put("customer_user_id", null);
        Response response =
                webTarget.request(MediaType.APPLICATION_JSON).post(
                        Entity.entity(json, MediaType.APPLICATION_JSON));
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    /**
     * Failure test for create user when organization does not exist.
     */
    @Test
    public void test_create_user_bad_org() {
        WebTarget webTarget = client.target(ENDPOINT + "organizations/123456/users");
        Map<String, String> json = new HashMap<>();
        json.put("customer_user_id", "test_create_user_bad_org");
        Response response =
                webTarget.request(MediaType.APPLICATION_JSON).post(
                        Entity.entity(json, MediaType.APPLICATION_JSON));
        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    /**
     * Failure test for create user when user already exists.
     */
    @Test
    public void test_create_user_exists() {
        // Create organization
        final String orgName = "test_create_user_exists";
        Organization organization = createOrganization(orgName);

        // Create a couple users.
        String customerUserId = "test_create_user_exists";
        createUser(customerUserId, organization);

        // Try to create another user with the same id.
        WebTarget webTarget = client.target(ENDPOINT + "organizations/" + organization.getId() + "/users");
        Map<String, String> json = new HashMap<>();
        json.put("customer_user_id", customerUserId);
        Response response =
                webTarget.request(MediaType.APPLICATION_JSON).post(
                        Entity.entity(json, MediaType.APPLICATION_JSON));
        assertEquals(Status.CONFLICT.getStatusCode(), response.getStatus());
    }

}
