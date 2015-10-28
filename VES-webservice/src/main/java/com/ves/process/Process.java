package com.ves.process;

import com.ves.models.ISessionProvider;
import com.ves.models.Session;

public abstract class Process {
    
    private final ISessionProvider sessionProvider;
    private final Session session;
    
    public Process( ISessionProvider sessionProvider, Session session )
    {    
        this.sessionProvider = sessionProvider;
        this.session = session;
    }
    
    public abstract void Start();
    public abstract void Stop();
}
