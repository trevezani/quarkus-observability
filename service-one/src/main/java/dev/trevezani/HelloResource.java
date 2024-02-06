package dev.trevezani;

import dev.trevezani.dto.MessageDTO;
import dev.trevezani.proxy.ServiceTwoProxy;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloResource {
    @Inject
    @RestClient
    ServiceTwoProxy client;

    @GET
    public MessageDTO hello() {
        return client.hello();
    }

    @Path("{locale}/{name}")
    @GET
    public MessageDTO hello(@PathParam("locale") String locale, @PathParam("name") String name) {
        MessageDTO dto = client.hello(locale);

        MessageDTO result = new MessageDTO();
        result.setMessage(dto.getMessage() + ", " + name);

        return result;
    }
}
