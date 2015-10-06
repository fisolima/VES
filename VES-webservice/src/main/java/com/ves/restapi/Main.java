package com.ves.restapi;

import com.ves.AppDomain;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author pippo
 */
@Path("main")
public class Main {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MainResource
     */
    public Main() {
    }

    /**
     * Retrieves representation of an instance of com.ves.MainResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        return Response.status(Response.Status.OK).entity(AppDomain.getID()).build();
    }

    /**
     * PUT method for updating or creating an instance of MainResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
