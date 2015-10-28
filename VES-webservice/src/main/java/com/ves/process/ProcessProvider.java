package com.ves.process;

import com.ves.VESException;
import com.ves.models.ISessionProvider;
import com.ves.models.Session;
import java.util.HashMap;
import java.util.Map;

public abstract class ProcessProvider {
    
    private final Map<String,com.ves.process.Process> processes;
    
    public ProcessProvider() {
        processes = new HashMap<>();
    }
    
    protected abstract Process CreateProcess( ISessionProvider sessionProvider, Session session );
            
    public final void Start( ISessionProvider sessionProvider, String sessionId) throws VESException
    {
        Session session = sessionProvider.Get(sessionId);
        
        if (session == null)
            throw new VESException(404, "Unknown session");
        
        if (session.getStatus() != Session.Status.READY)
            throw new VESException(400, "Session is not in a ready status");
        
        processes.put( session.getId(), CreateProcess(sessionProvider, session) );
    }
    
    public final void Stop( String sessionId)
    {
        Process process = processes.get(sessionId);
        
        if (process != null)
            process.Stop();
    }
}
