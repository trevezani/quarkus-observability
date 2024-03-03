package dev.trevezani.filter;

import io.opentelemetry.api.trace.Span;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class TraceIdResponseFilter implements ContainerResponseFilter {
    private static final String TRACE_ID = "x-correlation-id";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add(TRACE_ID, Span.current().getSpanContext().getTraceId());
    }
}
