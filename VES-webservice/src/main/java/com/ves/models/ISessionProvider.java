package com.ves.models;

import com.ves.VESException;
import java.util.Collection;

/**
 * Define the interface for the session providers
 */
public interface ISessionProvider {
    
    /**
     * Create a new session in the repository
     * @return Session created
     */
    public Session Create();
    
    /**
     * Update the specified session
     * @param session Session to update
     */
    public void Update( Session session );
    
    /**
     * Return the specified session
     * @param sessionId Session Id to find
     * @return Founded session
     */
    public Session Get( String sessionId );
    
    /**
     * Retrieves the specified session's resource path.
     * It will create the folder eventually
     * @param sessionId
     * @return 
     * @throws com.ves.VESException 
     */
    public String GetResourcePath( String sessionId ) throws VESException;;
    
    /**
     * Return all the session stored in the repository
     * 
     * @return Collection of session stored
     */
    public Collection<Session> GetAll();
    
    /**
     * Delete the specified session
     * 
     * @param sessionId Session Id to delete
     */
    public void Delete( String sessionId );
}
