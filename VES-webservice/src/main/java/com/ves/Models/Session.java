package com.ves.Models;

import java.util.UUID;

/**
 * Define the POJO object represent the video editing session
 */
public class Session {
    
    private String id;
        
    public Session( String id ) {
        this.id = id;
    }
    
    public Session() {
        this( UUID.randomUUID().toString());
    }
    
    public String getId() {
        return id;
    }
}
