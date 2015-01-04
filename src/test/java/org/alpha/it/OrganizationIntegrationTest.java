package org.alpha.it;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * Integration test for organization and user resources.
 */
public class OrganizationIntegrationTest {

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
        clientConfig.register(JacksonJsonProvider.class);
        client = ClientBuilder.newClient(clientConfig);
    }

    /**
     * X.
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        // Create organization
        WebTarget webTarget = client.target(ENDPOINT + "organizations");
        Map<String, String> json = new HashMap<>();
        json.put("organization_name", "OrgName");
        Response postResponse =
                webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.APPLICATION_JSON));

        assertEquals("Organization should be created.", Status.CREATED.getStatusCode(), postResponse.getStatus());

    }

}
