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
    public static class ErrorJSON {
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
        ErrorJSON json = new ErrorJSON();
        json.status = "error";
        json.message = exception.getMessage();
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(json).build();
    }
}
