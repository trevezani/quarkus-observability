package dev.trevezani.exception;

import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.http.conn.HttpHostConnectException;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {
    @Inject
    Logger log;

    @Override
    public Response toResponse(Exception exception) {
        log.error("Exception: " + exception.toString(), exception);

        Error error = new Error();
        error.timestamp = LocalDateTime.now();

        if (exception.getCause() != null) {
            switch (exception.getCause()) {
                case HttpHostConnectException e -> {
                    error.status = Response.Status.SERVICE_UNAVAILABLE.getStatusCode();
                    error.error = Response.Status.SERVICE_UNAVAILABLE.getReasonPhrase();
                    error.message = "Service '" + e.getHost().toString() + "' unavailable";
                }
                default -> {
                    error.status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
                    error.error = Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
                    error.message = exception.getCause().getMessage();
                }
            }
        } else {
            switch (exception) {
                case WebApplicationException e -> {
                    error.status = e.getResponse().getStatus();
                    error.error = Response.Status.fromStatusCode(error.status).getReasonPhrase();
                    error.message = exception.getMessage();
                }
                default -> {
                    error.status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
                    error.error = Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
                    error.message = exception.getMessage();
                }
            }
        }

        return Response.status(Response.Status.fromStatusCode(error.status))
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    static class Error {
        public LocalDateTime timestamp;
        public Integer status;
        public String error;
        public String message;
    }
}
