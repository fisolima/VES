package com.ves;

import com.ves.config.DirectConfigProvider;
import com.ves.config.IConfigProvider;

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
     * Set the domain config provider
     * @param _configProvider 
     */
    public static void setConfigProvider(IConfigProvider _configProvider) {
        configProvider = _configProvider;
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
