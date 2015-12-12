package com.ves.restapi;

import com.ves.AppDomain;
import com.ves.VESException;
import com.ves.models.Session;
import com.ves.helpers.JsonSerialization;
import com.ves.models.ResizeResource;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Map;
import javax.ws.rs.Consumes;
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
        
        return Response.status(Response.Status.OK).entity( JsonSerialization.sessionsToJson(sessions) ).build();
    }
    
    @POST
    public Response createSession() {
         Session session = AppDomain.getSessionProvider().Create();
         
         return Response.status(Response.Status.CREATED).entity( JsonSerialization.sessionToJson(session) ).build();
     }
     
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSession(@PathParam("id") String id) {        
        Session session = AppDomain.getSessionProvider().Get(id);
        
        return Response.status(Response.Status.OK).entity( JsonSerialization.sessionToJson(session) ).build();
    }
     
    @DELETE
    @Path("/{id}")
    public Response deleteSession(@PathParam("id") String id) {
        if (AppDomain.getSessionProvider().Get(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("The session does not exist").build();
        }
        
        AppDomain.getSessionProvider().Delete(id);
        
        return Response.status(Response.Status.OK).build();
    }
    
    @POST
    @Path("/{id}/resize")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setSessionResize(@PathParam("id") String id,InputStream inputStream ) throws VESException {
        Session session = AppDomain.getSessionProvider().Get(id);
                
        Map<String,String> bodyParams;
        
        try
        {
            bodyParams = JsonSerialization.Parse( inputStream );
        }
        catch (Exception e) {
            throw new VESException(Response.Status.BAD_REQUEST.getStatusCode(), "Invalid format request");
        }
        
        // verify parameters sintax
        if (!bodyParams.containsKey("widthPercentage") || !bodyParams.containsKey("heightPercentage")) {
            throw new VESException(Response.Status.BAD_REQUEST.getStatusCode(), "Invalid format request");
        }
        
        int widthValue = Integer.parseInt(bodyParams.get("widthPercentage"));
        int heightValue = Integer.parseInt(bodyParams.get("heightPercentage"));
        
        session.addResource( new ResizeResource( widthValue, heightValue ) );
                
        return Response.status(Response.Status.CREATED).build();
    }
    
/*
var httpRequest = new XMLHttpRequest();
httpRequest.open('GET', 'http://localhost:8080/VES-webservice/ws/sessions');
httpRequest.setRequestHeader("Accept", "application/json");
httpRequest.send();
console.log( httpRequest.responseText );
*/

}
