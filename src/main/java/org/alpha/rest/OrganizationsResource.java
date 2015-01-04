package org.alpha.rest;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.alpha.AppConfigurationException;
import org.alpha.Helper;
import org.alpha.entities.Organization;
import org.alpha.entities.User;
import org.alpha.services.AppServiceException;
import org.alpha.services.EntityExistsException;
import org.alpha.services.EntityNotFoundException;
import org.alpha.services.OrganizationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;

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
     * The root secret for retrieving root access token.
     */
    @Inject
    private String rootSecret;

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

    public static class CreateOrganizationJson {
        @JsonProperty("organization_name")
        @NotNull
        @NotEmpty
        public String organizationName;
    }

    /**
     * Create organization.
     * @param json The JSON object specifying organization properties.
     * @return 201 and created organization<br>
     *         400 if bad request<br>
     *         401 if unauthorized<br>
     *         409 if organization already exists<br>
     *         500 if server error
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createOrganization(@Valid CreateOrganizationJson json) throws AppServiceException {
        final String methodName = "createOrganization";

        Helper.logEntrance(LOGGER, methodName, "json", json);
        Response response = null;

        Organization organization = new Organization();
        organization.setName(json.organizationName);
        organization.setAccessToken(RandomStringUtils.randomAlphanumeric(32));
        organization.setUsers(new ArrayList<User>());

        try {
            organizationService.create(organization);
            response = Response.status(Status.CREATED).entity(organization).build();
        } catch (EntityExistsException e) {
            response = Response.status(Status.CONFLICT).entity(new ErrorJson("error", e.getMessage())).build();
        }

        return Helper.logExit(LOGGER, methodName, response);
    }

    /**
     * Get organization.
     * @param organizationId The organization id.
     * @return 200 and organization<br>
     *         401 if unauthorized<br>
     *         404 if not found<br>
     *         500 if server error
     */
    @Path("/{organization_id}")
    @GET
    @Produces("application/json")
    public Response getOrganization(@PathParam("organization_id") long organizationId)
            throws AppServiceException {
        final String methodName = "getOrganization";

        Helper.logEntrance(LOGGER, methodName, "organizationId", organizationId);

        Response response = null;
        if (organizationId <= 0) {
            response = Response.status(Status.NOT_FOUND).build();
        } else {
            try {
                Organization organization = organizationService.read(organizationId);
                response = Response.ok().entity(organization).build();
            } catch (EntityNotFoundException e) {
                response = Response.status(Status.NOT_FOUND).build();
            }
        }
        return Helper.logExit(LOGGER, methodName, response);
    }

    // TODO: Delete organization.

    // TODO: Provision user

    @Path("/test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayPlainTextHello() {
        return "{\"status\":\"good\"}";
    }

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

    /**
     * Retrieves the 'rootSecret' variable.
     * @return the 'rootSecret' variable value
     */
    public String getRootSecret() {
        return rootSecret;
    }

    /**
     * Sets the 'rootSecret' variable.
     * @param rootSecret the new 'rootSecret' variable value to set
     */
    public void setRootSecret(String rootSecret) {
        this.rootSecret = rootSecret;
    }

}
