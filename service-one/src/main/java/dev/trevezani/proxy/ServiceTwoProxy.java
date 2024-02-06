package dev.trevezani.proxy;

import dev.trevezani.dto.MessageDTO;
import io.smallrye.faulttolerance.api.CircuitBreakerName;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.*;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/hello")
@RegisterRestClient
public interface ServiceTwoProxy {

    @GET
    @Path("/xxx")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.75, delay = 5000)
    @CircuitBreakerName("hello")
    MessageDTO hello();

    @GET
    @Path("/locale/{locale}")
    @Produces(MediaType.APPLICATION_JSON)
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(MessageFallback.class)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.75, delay = 5000)
    @CircuitBreakerName("hello-locale")
    MessageDTO hello(@PathParam("locale") String locale);

    public static class MessageFallback implements FallbackHandler<MessageDTO> {
        @Override
        public MessageDTO handle(ExecutionContext executionContext) {
            MessageDTO dto = new MessageDTO();
            dto.setMessage("Hi");

            return dto;
        }
    }

}
