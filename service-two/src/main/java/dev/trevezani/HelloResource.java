package dev.trevezani;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.Map;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloResource {

    @GET
    public Map<String, Object> hello() {
        var map = new HashMap<String, Object>();
        map.put("message", "Hello RESTEasy");

        return map;
    }

    @GET
    @Path("/locale/{locale}")
    public Map<String, Object> hello(@PathParam("locale") String locale) {
        String message = "Hello";

        switch (locale) {
            case "br", "pt" -> message = "Olá";
            case "es" -> message = "Hola";
        }

        var map = new HashMap<String, Object>();
        map.put("message", message);

        return map;
    }
}
