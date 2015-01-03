package org.alpha.rest;

import org.glassfish.jersey.server.ResourceConfig;

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
        register(AppExceptionMapper.class);
    }

}
