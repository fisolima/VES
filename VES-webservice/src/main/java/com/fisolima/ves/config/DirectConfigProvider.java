package com.fisolima.ves.config;

import com.fisolima.ves.VESException;

public class DirectConfigProvider implements IConfigProvider {

    private final String storagePath;
    private final String databaseConnectionString;

    public DirectConfigProvider( String storagePath, String databaseConnectionString) throws VESException {
        this.storagePath = storagePath;
        this.databaseConnectionString = databaseConnectionString;
        
        // for data integrity check        
        ValidateParams();
    }
    
    private void ValidateParams() throws VESException {
        if (storagePath == null || storagePath.length() == 0)
            throw new VESException((503), "Storage path not configured");
        
        if (databaseConnectionString == null || databaseConnectionString.length() == 0)
            throw new VESException((503), "database not configured");
    }
    
    @Override
    public String getStoragePath() throws VESException {
        ValidateParams();
        
        return storagePath;
    }

    @Override
    public String getDatabaseConnectionString() throws VESException {
        ValidateParams();
        
        return databaseConnectionString;
    }
    
}
