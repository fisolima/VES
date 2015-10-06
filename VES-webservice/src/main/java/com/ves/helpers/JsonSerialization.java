package com.ves.helpers;

import com.google.gson.Gson;
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
}
