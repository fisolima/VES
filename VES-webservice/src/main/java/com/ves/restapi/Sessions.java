package com.ves.restapi;


import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import com.ves.AppDomain;
import com.ves.VESException;
import com.ves.models.Session;
import com.ves.helpers.JsonSerialization;
import com.ves.models.FinalVideoResource;
import com.ves.models.ResizeData;
import com.ves.models.ResizeResource;
import com.ves.models.ResourceType;
import com.ves.models.SubtitlesResource;
import com.ves.models.VideoResource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
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
import javax.ws.rs.core.Response.ResponseBuilder;
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
    public Response deleteSession(@PathParam("id") String id) throws VESException {
        if (AppDomain.getSessionProvider().Get(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("The session does not exist").build();
        }
        
        AppDomain.getSessionProvider().Delete(id);
        AppDomain.getProcessProvider().Stop(id);
        
        String path = AppDomain.getSessionProvider().GetResourcePath(id);
        
        try {
            deleteFile(new File(path));
        } catch (Exception ex) {
            throw new VESException(503, ex.getMessage());
        }
        
        return Response.status(Response.Status.OK).build();
    }
    
    @POST
    @Path("/{id}/resize")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setSessionResize(@PathParam("id") String id, ResizeData resizeData) throws VESException {
        Session session = AppDomain.getSessionProvider().Get(id);
        
        int widthValue = (int) resizeData.widthPercentage;
        int heightValue = (int) resizeData.heightPercentage;
        
        session.addResource( new ResizeResource( widthValue, heightValue ) );
                
        AppDomain.getSessionProvider().Update(session);
        
        return Response.status(Response.Status.CREATED).build();
    }
    
    private void SaveFile( InputStream stream, String path ) throws VESException {
        try
        {
            byte[] bytes = new byte[1024];
        
            OutputStream outpuStream = new FileOutputStream(new File(path));

            int read;

            while ((read = stream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }

            outpuStream.flush();
            outpuStream.close();
        }
        catch (Exception exc) {
            throw new VESException(503, "Unable to upload file");
        }
    }
    
    private static void deleteFile(File element) {
        if (element.isDirectory()) {
            for (File sub : element.listFiles()) {
                deleteFile(sub);
            }
        }
        element.delete();
    }
    
    @POST
    @Path("/{id}/video")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadVideo(
        @PathParam("id") String id,
        @FormDataParam("file") InputStream uploadedInputStream,
        @FormDataParam("file") FormDataContentDisposition fileDetail) throws VESException {

        String path = AppDomain.getSessionProvider().GetResourcePath(id);
        
        String uploadedFileLocation = (new File(path, fileDetail.getFileName())).getPath();

        SaveFile( uploadedInputStream, uploadedFileLocation );
        
        Session session = AppDomain.getSessionProvider().Get(id);
        
        session.addResource(new VideoResource(fileDetail.getFileName()));
                
        AppDomain.getSessionProvider().Update(session);

        return Response.status(200).build();
    }
    
    @POST
    @Path("/{id}/subtitle")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadSubtitle(
        @PathParam("id") String id,
        @FormDataParam("file") InputStream uploadedInputStream,
        @FormDataParam("file") FormDataContentDisposition fileDetail) throws VESException {

        String path = AppDomain.getSessionProvider().GetResourcePath(id);
        
        String uploadedFileLocation = (new File(path, fileDetail.getFileName())).getPath();

        SaveFile( uploadedInputStream, uploadedFileLocation );
        
        Session session = AppDomain.getSessionProvider().Get(id);
        
        session.addResource(new SubtitlesResource(fileDetail.getFileName()));

        AppDomain.getSessionProvider().Update(session);
        
        return Response.status(200).build();
    }
    
    @POST
    @Path("/{id}/burn")
    public Response burn(@PathParam("id") String id) throws VESException {
        
        AppDomain.getProcessProvider().Start( AppDomain.getSessionProvider(), id);
        
        return Response.status(200).build();
    }
    
    @POST
    @Path("/{id}/cancel")
    public Response cancel(@PathParam("id") String id) throws VESException {
        
        AppDomain.getProcessProvider().Stop(id);
        
        return Response.status(200).build();
    }
    
    @GET
    @Path("/{id}/result")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getProcessedVideo(@PathParam("id") String id)  throws VESException {
        Session session = AppDomain.getSessionProvider().Get(id);
        
        if (session == null)
            throw new VESException(404, "Session not found");
        
        if (session.getStatus() != Session.Status.COMPLETED)
            throw new VESException(400, "Video not available yet");
            
        String path = AppDomain.getSessionProvider().GetResourcePath(id);
        
        FinalVideoResource res = (FinalVideoResource)session.getResources(ResourceType.FINALVIDEO).get(0);
        
        File file = new File(path + "/" + res.getValue());

        ResponseBuilder response = Response.ok((Object) file);
        
        response.header("Content-Disposition", "attachment; filename=" + res.getValue());
        
        return response.build();
    }
}
