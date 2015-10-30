package com.ves.restapi;

import com.ves.AppDomain;
import com.ves.VESException;
import com.ves.helpers.JsonSerialization;
import com.ves.config.DirectConfigProvider;
import com.ves.config.EtcdConfigProvider;
import com.ves.config.IConfigProvider;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("cfg")
public class Configuration {

    @Context
    private UriInfo context;

    public Configuration() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfigStatus()
    {
        IConfigProvider configProvider = AppDomain.getConfigProvider();
        
        Boolean serviceAvailable = true;
        String storageStatus = "Available";
        String databaseStatus = "Available";
        
        try
        {
            String storagePath = configProvider.getStoragePath();
            
            // TODO check path permission
        }
        catch (Exception exc)
        {
            serviceAvailable = false;
            storageStatus = "Unavailable - " + exc.getMessage();
        }
        
        try
        {
            String databaseString = configProvider.getDatabaseConnectionString();
            
            // TODO check database access
        }
        catch (Exception exc)
        {
            serviceAvailable = false;
            databaseStatus = "Unavailable - " + exc.getMessage();
        }
        
        String configType = "undefined";
        
        if (configProvider instanceof DirectConfigProvider) {
            configType = "direct config";
        }
        else if (configProvider instanceof EtcdConfigProvider) {
            configType = "etcd config";
        }
            
        
        String responseJson = "{\"available\":" + serviceAvailable.toString() + "," +
                                "\"configType\":\"" + configType + "\"," +
                                "\"storageStatus\": \"" + storageStatus + "\"," +
                                "\"databaseStatus\": \"" + databaseStatus + "\"}";
        
        return Response.status( serviceAvailable ? Response.Status.OK : Response.Status.SERVICE_UNAVAILABLE ).entity(responseJson).build();
    }
    
    @PUT
    @Path("direct")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setDirectConfigParams( String jsonParams ) throws VESException
    {        
        String storageValue = "";
        String databaseValue = "";
        
        try
        {
            Map<String,String> configMap = JsonSerialization.Parse(jsonParams);

            storageValue = configMap.get("storage");
            databaseValue = configMap.get("database");
            
            if (storageValue == null ||
                    storageValue.length() == 0 ||
                    databaseValue == null ||
                    databaseValue.length() == 0) {
                throw new Exception();
            }
                
        }
        catch (Exception exc) {
            throw new VESException(400, "Invalid format request");
        }
        
        AppDomain.setConfigProvider( new DirectConfigProvider( storageValue, databaseValue ));
        
        return Response.status(Response.Status.OK).build();
    }
    
    @PUT
    @Path("etcd")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setEtcdConfigParams(String jsonParams) throws VESException
    {
        String storageKey = "";
        String databaseKey = "";
        String etcdEndPoint = "";
        
        try
        {
            Map<String,String> configMap = JsonSerialization.Parse(jsonParams);

            storageKey = configMap.get("storageKey");
            databaseKey = configMap.get("databaseKey");
            etcdEndPoint = configMap.get("etcdEndPoint");
            
            if (storageKey == null ||
                    storageKey.length() == 0 ||
                    databaseKey == null ||
                    databaseKey.length() == 0 ||
                    etcdEndPoint == null ||
                    etcdEndPoint.length() == 0)
                throw new Exception();
        }
        catch (Exception exc) {
            throw new VESException(400, "Invalid format request");
        }
        
        AppDomain.setConfigProvider( EtcdConfigProvider.create(etcdEndPoint, storageKey, databaseKey) );
        
        return Response.status(Response.Status.OK).build();
    }
}
