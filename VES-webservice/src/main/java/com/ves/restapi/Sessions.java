package com.ves.restapi;

import com.sun.org.glassfish.gmbal.ParameterNames;
import com.sun.xml.internal.ws.api.message.Packet;
import com.ves.AppDomain;
import com.ves.Models.Session;
import com.ves.helpers.JsonSerialization;
import java.util.Collection;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("sessions")
public class Sessions {
    
    @Context
    private UriInfo context;
    
    public Sessions() {        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessions() {        
        Collection<Session> sessions = AppDomain.getSessionProvider().GetAll();
        
        return Response.status(Response.Status.OK).entity( JsonSerialization.toJson(sessions) ).build();
    }
    
    @POST
    public Response createSession() {
         Session session = AppDomain.getSessionProvider().Create();
         
         return Response.status(Response.Status.CREATED).entity( "{location: \'" + context.getRequestUri().toString() + "/" + session.getId() + "\'}" ).build();
     }
     
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSession(@PathParam("id") String id) {        
        Session session = AppDomain.getSessionProvider().Get(id);
        
        return Response.status(Response.Status.OK).entity( JsonSerialization.toJson(session) ).build();
    }
     
    @DELETE
    @Path("/{id}")
    public Response deleteSession(@PathParam("id") String id) {
        AppDomain.getSessionProvider().Delete(id);
        
        return Response.status(Response.Status.OK).build();
    }
    
/*
var httpRequest = new XMLHttpRequest();
httpRequest.open('GET', 'http://localhost:8080/VES-webservice/ws/sessions');
httpRequest.setRequestHeader("Accept", "application/json");
httpRequest.send();
console.log( httpRequest.responseText );
*/

}
