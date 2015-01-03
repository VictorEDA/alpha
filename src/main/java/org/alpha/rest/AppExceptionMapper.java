package org.alpha.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.alpha.AppException;

/**
 * Convert exceptions into a JSON response.
 */
@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException> {

    /**
     * The JSON response object.
     */
    public static class ErrorJson {
        public String status;
        public String message;
    }

    /**
     * Default constructor.
     */
    public AppExceptionMapper() {
        // empty
    }

    @Override
    public Response toResponse(AppException exception) {
        ErrorJson json = new ErrorJson();
        json.status = "error";
        json.message = exception.getMessage();
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(json).build();
    }
}
