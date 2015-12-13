package com.ves.models;

import com.ves.AppDomain;
import com.ves.VESException;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Define an in-memory session provider
 */
public class MemorySessionProvider implements ISessionProvider {

    public static final String MEMORY_PROVIDER = "__MEMORY_PROVIDER__";
            
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
        sessions.put(session.getId(), session);
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
