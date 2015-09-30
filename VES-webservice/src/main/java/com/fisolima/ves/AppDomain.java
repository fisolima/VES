package com.fisolima.ves;

import com.fisolima.ves.config.IConfigProvider;

public final class AppDomain
{
    private static String appDomainID;
    private static IConfigProvider configProvider;
    
    /**
     * Return the interface of the config providers
     * 
     * @return 
     */
    public static IConfigProvider getConfigProvider() {
        return configProvider;
    }
    
    /**
     * Return the application domain identifier
     * 
     * @return 
     */
    public static String getID() {
        return appDomainID;
    }
    
    public static void Initialize( String _appDomainID, IConfigProvider _configProvider ) {
        appDomainID = _appDomainID;
        configProvider = _configProvider;
    }
}
