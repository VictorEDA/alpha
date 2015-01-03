package org.alpha.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * This provider is needed for mapping JSON to Java objects when using Jersey REST with Jackson.
 */
@Provider
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

    /**
     * Generates string representation of objects.
     */
    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    /**
     * The default date format to use for JSON conversion.
     */
    private static final DateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    static {
        DEFAULT_OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DEFAULT_OBJECT_MAPPER.setDateFormat(DEFAULT_DATE_FORMAT);
    }

    /**
     * Constructor.
     */
    public ObjectMapperProvider() {
        // empty
    }

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        return DEFAULT_OBJECT_MAPPER;
    }

}
