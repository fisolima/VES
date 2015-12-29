package com.ves.process;

import com.ves.VESException;
import com.ves.models.ISessionProvider;
import com.ves.models.Session;

public class FfmpegProcess extends Process implements Runnable {

    private Thread thread;
    
    public FfmpegProcess( ISessionProvider sessionProvider, Session session ) {
        super(sessionProvider,session);
    }
    
    @Override
    public void Start() throws VESException {
        try
        {
            session.setStatus(Session.Status.PROCESSING);

            thread = new Thread(this, session.getId());
            thread.start();
        }
        catch (Exception exc) {
            throw new VESException( 503, "ffmpeg failed: " + exc.getMessage());
        }
    }

    @Override
    public void Stop() throws VESException {
        if (session.getStatus() != Session.Status.COMPLETED)
            session.setStatus(Session.Status.INTERRUPTED);
        
        Update();
    }

    @Override
    public void run() {
        
        // test
        for (int i=0; i<50; i++) {
            if (session.getStatus() == Session.Status.INTERRUPTED)
                break;
                
            synchronized(session) {                
                session.setProgress(i*2);
            }
            
            Update();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                break;
            }
        }
        // test
        
        if (session.getStatus() != Session.Status.INTERRUPTED)
            session.setStatus(Session.Status.COMPLETED);
        
        try {
            Stop();
        }
        catch (Exception exc) {}
    }
    
}
