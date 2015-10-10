package com.ves;

import com.ves.models.ISessionProvider;
import com.ves.config.IConfigProvider;

public final class AppDomain
{
    private static String appDomainID;
    private static IConfigProvider configProvider;
    private static ISessionProvider sessionProvider;

    /**
     * Return the interface to manage sessions
     * 
     * @return 
     */
    public static ISessionProvider getSessionProvider() {
        return sessionProvider;
    }

    /**
     * Set the provider to manage session
     * 
     * @param sessionProvider 
     */
    public static void setSessionProvider(ISessionProvider sessionProvider) {
        AppDomain.sessionProvider = sessionProvider;
    }
    
    /**
     * Return the interface of the config providers
     * 
     * @return 
     */
    public static IConfigProvider getConfigProvider() {
        return configProvider;
    }
    
    /**
     * Set the domain config provider
     * @param configProvider 
     */
    public static void setConfigProvider(IConfigProvider configProvider) {
        AppDomain.configProvider = configProvider;
    }
    
    /**
     * Return the application domain identifier
     * 
     * @return 
     */
    public static String getID() {
        return appDomainID;
    }
    
    public static void Initialize( String appDomainID,
                                    IConfigProvider configProvider,
                                    ISessionProvider sessionProvider ) {
        AppDomain.appDomainID = appDomainID;
        setConfigProvider(configProvider);
        setSessionProvider(sessionProvider);
    }

    
}
