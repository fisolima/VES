package com.ves.process;

import com.ves.models.ISessionProvider;
import com.ves.models.Session;

public class FfmpegProcess extends Process {

    public FfmpegProcess( ISessionProvider sessionProvider, Session session ) {
        super(sessionProvider,session);
    }
    
    @Override
    public void Start() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void Stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
