package com.ves;

import com.ves.models.ISessionProvider;
import com.ves.config.IConfigProvider;
import com.ves.process.ProcessProvider;

public final class AppDomain
{
    private static String appDomainID;
    private static IConfigProvider configProvider;
    private static ISessionProvider sessionProvider;
    private static ProcessProvider processProvider;

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
    public static synchronized void setSessionProvider(ISessionProvider sessionProvider) {
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
    public static synchronized void setConfigProvider(IConfigProvider configProvider) {
        AppDomain.configProvider = configProvider;
    }
    
    public static ProcessProvider getProcessProvider() {
        return processProvider;
    }

    public static synchronized void setProcessProvider(ProcessProvider processProvider) {
        AppDomain.processProvider = processProvider;
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
                                    ISessionProvider sessionProvider,
                                    ProcessProvider processProvider ) {
        AppDomain.appDomainID = appDomainID;
        setConfigProvider(configProvider);
        setSessionProvider(sessionProvider);
        setProcessProvider(processProvider);
    }
}
