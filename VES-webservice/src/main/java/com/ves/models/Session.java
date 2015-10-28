package com.ves.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Define the object represent the video editing session
 */
public class Session {

    public enum Status
    {
        WAITING(1),
        READY(2),
        PROCESSING(3),
        INTERRUPTED(4),
        COMPLETED(5);
        
        private final int value;
        
        private Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }    
    
    private String id;
    private Status status;
    private int progress;    
    private String progressDescription;
    private Date progressLastModify;
    private List<IResource> resources;
        
    public Session( String id ) {
        this.id = id;
        status = Status.WAITING;
        progress = 0;
        progressDescription = "";
        progressLastModify = new Date();
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
     * Set the current session status
     * @param status 
     */
    public void setStatus( Status status ) {
        this.status = status;
    }
    
    /**
     * Return the percentage of completion
     * @return 
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Set the percentage of completion
     * @param progress 
     */
    public void setProgress(int progress) {
        progressLastModify = new Date();
        
        this.progress = progress;
    }
    
    /**
     * Return the last modified applied on the current burning session
     * @return 
     */
    public Date getProgressLastModify() {
        return progressLastModify;
    }
    
    /**
     * Set the registered last modify timestamp
     * @param date 
     */
    public void setProgressLastModify( Date date ) {
        this.progressLastModify = date;
    }

    /**
     * Get an extended status of the completion
     * @return 
     */
    public String getProgressDescription() {
        return progressDescription;
    }

    /**
     * Set an extended status of the completion
     * @param progressDescription 
     */
    public void setProgressDescription(String progressDescription) {
        this.progressDescription = progressDescription;
    }
    
    /**
     * A copy of all resources assigned to the session
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
     * A copy of all resources assigned to the session of that kind
     * @param type type of the resource
     * @return resource list
     */
    public List<IResource> getResources( ResourceType type) {
        ArrayList<IResource> copyResource = new ArrayList<>();
        
        for( IResource res : resources ) {
            if (res.getType() == type)
                copyResource.add( res.clone() );
        }
                
        return copyResource;
    }
    
    /**
     * Add a resource in the session
     * @param resource The instance of a resource to add
     */
    public void addResource(IResource resource) {
        
        // remove conflicting resource
        Iterator<IResource> resourceIterator = resources.iterator();
        
        while (resourceIterator.hasNext()) {
            IResource currentRes = resourceIterator.next();
            
            if (resource.getType() == currentRes.getType() && resource.isUnique()) {
                resourceIterator.remove();
            }                
        }
        
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
