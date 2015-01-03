package org.alpha.rest;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.alpha.AppConfigurationException;
import org.alpha.Helper;
import org.alpha.entities.Organization;
import org.alpha.services.AppServiceException;
import org.alpha.services.OrganizationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The end point for managing organizations and users.
 */
@Path("/{version:(v1\\/)?}organizations")
public class OrganizationsResource {

    /**
     * The logger to use.
     */
    private static final Logger LOGGER = LogManager.getLogger(OrganizationsResource.class);

    /**
     * The root access token for creating and deleting organizations.
     */
    @Inject
    private String rootAccessToken;

    /**
     * The organization service.
     */
    @Inject
    private OrganizationService organizationService;

    /**
     * Default constructor.
     */
    public OrganizationsResource() {
        // empty
    }

    /**
     * Check initialization.
     * @throws AppConfigurationException if organizationService is null, or rootAccessToken is null/empty
     */
    @PostConstruct
    public void checkInit() {
        Helper.checkNullConfig(organizationService, "organizationService");
    }

    @XmlRootElement
    public static class CreateOrganizationJson {
        @JsonProperty("organization_id")
        public String organizationId;
    }

    /**
     * Create organization.
     * @param json The JSON object specifying organization properties.
     * @return organization creation status
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createOrganization(CreateOrganizationJson json) throws AppServiceException {
        LOGGER.info("derby.system.home:" + System.getProperty("derby.system.home"));
        // TODO: add logging and error checking and exception handling
        Organization organization = new Organization();
        organization.setName(json.organizationId);
        organization.setAccessToken(RandomStringUtils.randomAlphanumeric(32));
        organizationService.create(organization);
        return Response.ok().entity(organization).build();
        // return "{\"access_token\":\"" + organization.getAccessToken() + "\"}";
    }

    @Path("/test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayPlainTextHello() {
        return "{\"status\":\"good\"}";
    }

    // Get organization.

    // Delete organization.

    // Provision user

    /**
     * Retrieves the 'organizationService' variable.
     * @return the 'organizationService' variable value
     */
    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    /**
     * Sets the 'organizationService' variable.
     * @param organizationService the new 'organizationService' variable value to set
     */
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

}
