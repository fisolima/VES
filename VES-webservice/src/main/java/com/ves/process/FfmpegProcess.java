package com.ves.process;

import com.ves.VESException;
import com.ves.models.FinalVideoResource;
import com.ves.models.IResource;
import com.ves.models.ISessionProvider;
import com.ves.models.ResourceType;
import com.ves.models.Session;
import com.ves.models.SubtitlesResource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.lang.ProcessBuilder;
import java.util.ArrayList;
import java.util.List;

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
        if (session.getStatus() != Session.Status.COMPLETED && session.getStatus() != Session.Status.FAILED)
            session.setStatus(Session.Status.INTERRUPTED);
        
        Update();
    }

    @Override
    public void run() {
        
        // test
//        for (int i=0; i<50; i++) {
//            if (session.getStatus() == Session.Status.INTERRUPTED)
//                break;
//                
//            synchronized(session) {                
//                session.setProgress(i*2);
//            }
//            
//            Update();
//            
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                break;
//            }
//        }
        // test

        StringBuilder strBuild = new StringBuilder();
        
        try
        {
            // create command list
            List<String> args = new ArrayList<>();
            
            String path = sessionProvider.GetResourcePath(session.getId()) + "/";
            String videoFile = session.getResources(ResourceType.VIDEO).get(0).getValue();
            String finalVideo = "out" + videoFile;

            // source video
            args.add("ffmpeg");
            args.add("-i");
            args.add(path + videoFile);

            // subtitle resource
            List<IResource> resources = session.getResources(ResourceType.SUBTITLE);
            
            if (!resources.isEmpty()){
                SubtitlesResource resource = (SubtitlesResource)resources.get(0);
                
                args.add("-i");
                args.add(path + resource.getValue());
                args.add("-c:s");
                args.add("mov_text");
                args.add("-c:v");
                args.add("copy");
                args.add("-c:a");
                args.add("copy");
            }
            
            // output video
            args.add("-y");
            args.add(path + finalVideo);
            
            // execute ffmpeg process
            java.lang.Process process = new ProcessBuilder(args).redirectErrorStream(true).start();
            
            BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.defaultCharset()));
            
            String line;
            
            while ((line = processOutputReader.readLine()) != null)
            {
                if (session.getStatus() == Session.Status.INTERRUPTED)
                {
                    process.destroy();
                    
                    break;
                }
                    
                strBuild.append(line).append(System.lineSeparator());
            }
            
            process.waitFor();
            
            if (session.getStatus() != Session.Status.INTERRUPTED)
            {
                switch (process.exitValue()){
                    // successo
                    case 0:
                    {
                        synchronized(session) {                
                            session.setProgress(100);
                        }

                        Update();

                        session.setStatus(Session.Status.COMPLETED);

                        session.addResource(new FinalVideoResource(finalVideo));
                    } break;
                        
                    // errore
                    default:
                        session.setStatus(Session.Status.FAILED);
                        break;
                }
            }
        }
        catch (Exception exc){
            strBuild.append("ERROR: ").append(exc.getMessage());
        }
        
        session.setProgressDescription(strBuild.toString().trim());
        
        try {
            Stop();
        }
        catch (Exception exc) {}
    }
    
}
