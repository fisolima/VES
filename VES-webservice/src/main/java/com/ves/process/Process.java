package com.ves.process;

import com.ves.VESException;
import com.ves.models.ISessionProvider;
import com.ves.models.Session;

public abstract class Process {
    
    protected final ISessionProvider sessionProvider;
    protected final Session session;
    
    public Process( ISessionProvider sessionProvider, Session session )
    {    
        this.sessionProvider = sessionProvider;
        this.session = session;
    }
    
    public abstract void Start() throws VESException;
    public abstract void Stop() throws VESException;
    
    protected void Update() {
        synchronized(session) {
            sessionProvider.Update(session);
        }
    }
}
