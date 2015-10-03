package com.ves.restapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("sessions")
public class Sessions {
    
    @Context
    private UriInfo context;
    
    public Sessions() {        
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSessions() {
        return "[]";
    }
}
