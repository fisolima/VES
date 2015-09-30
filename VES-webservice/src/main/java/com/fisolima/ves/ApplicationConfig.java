/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fisolima.ves;

import com.fisolima.ves.config.NullConfigProvider;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author pippo
 */
@javax.ws.rs.ApplicationPath("ws")
public class ApplicationConfig extends Application {    
    
    public ApplicationConfig() //@Context ServletContext servletContext) {
    {
        AppDomain.Initialize( "VES web service",
                                new NullConfigProvider());        
    }
            
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
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
        resources.add(com.fisolima.ves.ConfigResource.class);
        resources.add(com.fisolima.ves.ExceptionResolver.class);
        resources.add(com.fisolima.ves.MainResource.class);
    }
    
}
