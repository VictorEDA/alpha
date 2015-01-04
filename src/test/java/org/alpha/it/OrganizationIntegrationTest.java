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
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.alpha.BaseTest;
import org.alpha.entities.Organization;
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
        final String orgName = "OrgName";
        WebTarget webTarget = client.target(ENDPOINT + "organizations");
        Map<String, String> json = new HashMap<>();
        json.put("organization_name", orgName);
        Response response =
                webTarget.request(MediaType.APPLICATION_JSON).post(
                        Entity.entity(json, MediaType.APPLICATION_JSON));

        assertEquals("Organization should be created.", Status.CREATED.getStatusCode(), response.getStatus());
        Organization organization = response.readEntity(Organization.class);
        assertEquals("Organization name should be correct.", orgName, organization.getName());
        assertThat("Access token must be valid.", organization.getAccessToken(), not(isEmptyOrNullString()));
        assertThat("Organization id must be valid.", organization.getId(), greaterThan(0L));
        assertEquals("Created and updated dates must be the same.", organization.getCreatedAt(),
                organization.getUpdatedAt());
        Date date = new Date();
        assertThat("Creation date must be correct.", organization.getCreatedAt().getTime(),
                lessThanOrEqualTo(date.getTime()));

        // Get organization
        webTarget = client.target(ENDPOINT + "organizations/" + organization.getId());
        response = webTarget.request(MediaType.APPLICATION_JSON).get();
        assertEquals("Response should be OK.", Status.OK.getStatusCode(), response.getStatus());
        Organization copy = response.readEntity(Organization.class);
        LOGGER.info("Original:" + organization.toString());
        LOGGER.info("Copy    :" + copy.toString());
        assertTrue("Organization should match the one created.",
                EqualsBuilder.reflectionEquals(organization, copy));
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
        WebTarget webTarget = client.target(ENDPOINT + "organizations");
        Map<String, String> json = new HashMap<>();
        json.put("organization_name", orgName);
        Response response =
                webTarget.request(MediaType.APPLICATION_JSON).post(
                        Entity.entity(json, MediaType.APPLICATION_JSON));
        assertEquals("Organization should be created.", Status.CREATED.getStatusCode(), response.getStatus());
        // Send the request in again.
        response =
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
}
