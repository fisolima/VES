package com.ves.models;

import com.ves.AppDomain;
import com.ves.VESException;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public String GetResourcePath(String sessionId) throws VESException {        
        String storage = AppDomain.getConfigProvider().getStoragePath();
        
        File file = new File(storage, sessionId);
        
        if (!file.exists())
            file.mkdirs();
        
        return file.getPath();
    }
    

    @Override
    public Collection<Session> GetAll() {
        return sessions.values();
    }

    @Override
    public void Delete(String sessionId) {
        sessions.remove(sessionId);
    }
    
}
