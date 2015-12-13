package com.ves;

import com.ves.models.MemorySessionProvider;
import com.ves.config.NullConfigProvider;
import com.ves.models.ResizeData;
import com.ves.process.FfmpegProcessProvider;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {    
    
    public ApplicationConfig()
    {
        AppDomain.Initialize( "VES web service",
                                new NullConfigProvider(),
                                new MemorySessionProvider(),
                                new FfmpegProcessProvider());        
    }
            
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        
        resources.add(MultiPartFeature.class);
        
        resources.add(JacksonFeature.class);
        resources.add(VESJacksonProvider.class);
        resources.add(ResizeData.class);
    
        addRestResourceClasses(resources);
        
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.ves.ExceptionResolver.class);
        resources.add(com.ves.VESJacksonProvider.class);
        resources.add(com.ves.restapi.Configuration.class);
        resources.add(com.ves.restapi.Main.class);        
        resources.add(com.ves.restapi.Sessions.class);        
    }
    
}
