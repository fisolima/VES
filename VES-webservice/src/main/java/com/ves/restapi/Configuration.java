package com.ves.restapi;

import com.ves.AppDomain;
import com.ves.VESException;
import com.ves.helpers.JsonSerialization;
import com.ves.config.DirectConfigProvider;
import com.ves.config.EtcdConfigProvider;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("cfg")
public class Configuration {

    @Context
    private UriInfo context;

    public Configuration() {
    }
    
    @PUT
    @Path("direct")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response setDirectConfigParams( String jsonParams ) throws VESException
    {        
        String storageValue = "";
        String databaseValue = "";
        
        try
        {
            Map<String,String> configMap = JsonSerialization.Parse(jsonParams);

            storageValue = configMap.get("storage");
            databaseValue = configMap.get("database");
            
            if (storageValue == null || databaseValue == null)
                throw new Exception();
        }
        catch (Exception exc) {
            throw new VESException(400, "Invalid format request");
        }
        
        AppDomain.setConfigProvider( new DirectConfigProvider( storageValue, databaseValue ));
        
        return Response.status(Response.Status.OK).build();
    }
    
    @PUT
    @Path("etcd")
    @Consumes(MediaType.TEXT_PLAIN)
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
            
            if (storageKey == null || databaseKey == null || etcdEndPoint == null)
                throw new Exception();
        }
        catch (Exception exc) {
            throw new VESException(400, "Invalid format request");
        }
        
        AppDomain.setConfigProvider( EtcdConfigProvider.create(etcdEndPoint, storageKey, databaseKey) );
        
        return Response.status(Response.Status.OK).build();
    }
}