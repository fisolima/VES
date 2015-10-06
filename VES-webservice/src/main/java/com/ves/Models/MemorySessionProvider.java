package com.ves.Models;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Define an in-memory session provider
 */
public class MemorySessionProvider implements ISessionProvider {

    private final Map<String,Session> sessions;
    
    public MemorySessionProvider () {
        sessions = new HashMap<>();
    }
    
    @Override
    public Session Create() {
        Session session = new Session();
        
        sessions.put(session.getId(), session);
        
        return session;
    }

    @Override
    public void Update(Session session) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Session Get(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public Collection<Session> GetAll() {
        return sessions.values();
    }

    @Override
    public void Delete(String sessionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
