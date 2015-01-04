package org.alpha.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * Configuration for REST resources.
 */
public class RestConfig extends ResourceConfig {

    /**
     * Constructor. Register JAX-RS application components.
     */
    public RestConfig() {
        // REST endpoints
        register(OrganizationsResource.class);

        // JSON mapper
        register(ObjectMapperProvider.class);

        // Exception mappers
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        register(AppExceptionMapper.class);
    }

}
