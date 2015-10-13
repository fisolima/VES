package com.ves.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ves.models.Session;
import com.ves.models.json.SessionJsonSerializer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper to serialize and deserialize json objects
 * @author pippo
 */
public class JsonSerialization {
    
    /**
     * Parse the string json object into a string map
     * @param json Object to parse
     * @return string mapped object
     */
    public static Map<String,String> Parse( String json )
    {
        Gson gson = new Gson();

        Map<String, String> configMap = new HashMap<>();
        
        return gson.fromJson(json, configMap.getClass());
    }
    
    /**
     * Parse the inputStream as a utf8 text stream of a json object into a string map
     * @param inputStream
     * @return 
     */
    public static Map<String,String> Parse( InputStream inputStream ) throws IOException
    {
        final StringBuilder stringBuilder = new StringBuilder();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader( inputStream ));
               
        String line;
        
        while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
    
        return JsonSerialization.Parse(stringBuilder.toString());
    }
    
    /**
     * Parse the string json object into the specified objec
     * @param <T> type object to deserialize into
     * @param json Object to parse
     * @param clazz type object to deserialize into
     * @return An instance of the deserialize object
     */
    public static <T> T ParseObject(String json, Class<T> clazz)
    {
        Gson gson = new Gson();
        
        return gson.fromJson(json,clazz);
    }
    
    /**
     * Convert the given object in a json string
     * @param obj object to convert
     * @return the json string serialazed
     */
    public static String toJson( Object obj ) 
    {
        Gson gson = new Gson();
        
        return gson.toJson(obj);
    }
    
    public static String sessionToJson( Session session ) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Session.class, new SessionJsonSerializer()).create();
        
        return gson.toJson(session);
    }
    
    public static String sessionsToJson( Collection<Session> sessions ) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Session.class, new SessionJsonSerializer()).create();
        
        return gson.toJson(sessions);
    }
    
    public static Session ParseSession( String json ) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Session.class, new SessionJsonSerializer()).create();
        
        return gson.fromJson(json, Session.class);
    }
    
    public static Session[] ParseSessions( String json ) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Session.class, new SessionJsonSerializer()).create();
        
        return gson.fromJson(json, Session[].class);
    }
}
