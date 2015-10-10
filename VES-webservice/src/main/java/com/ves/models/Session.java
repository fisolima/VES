package com.ves.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jdk.nashorn.internal.objects.NativeArray;

/**
 * Define the POJO object represent the video editing session
 */
public class Session {

    public enum Status
    {
        WAITING,
        READY,
        PROCESSING,
        INTERRUPTED
    }    
    
    private String id;
    private Status status;
    private List<IResource> resources;
        
    public Session( String id ) {
        this.id = id;
        status = Status.WAITING;
        resources = new ArrayList<>();
    }
    
    public Session() {
        this( UUID.randomUUID().toString());
    }
    
    /**
     * The unique sessionID
     * @return sessionId
     */
    public String getId() {
        return id;
    }
    
    /**
     * The current session status
     * @return session status
     */
    public Status getStatus() {
        return status;
    }
    
    /**
     * A copy of all the resource assigned to the session
     * @return resource list
     */
    public List<IResource> getResources() {
        ArrayList<IResource> copyResource = new ArrayList<>();
        
        for( IResource res : resources ) {
            copyResource.add( res.clone() );
        }
                
        return copyResource;
    }
    
    /**
     * Add a resource in the session
     * @param resource The instance of a resource to add
     */
    public void addResource(IResource resource) {
        resources.add(resource);
        
        Boolean videoReady = false;
        Boolean resourceReady = false;
        
        for (IResource res : resources) {
            switch (res.getType())
            {
                case VIDEO:
                    videoReady = true;
                    break;
                    
                case SUBTITLE:
                case RESIZE:
                    resourceReady = true;
                    break;
                    
                case UNDEFINED:
                    throw new UnsupportedOperationException("Unidentified resource");
            }
        }
        
        if (videoReady && resourceReady)
            status = Status.READY;
    }
}
