package com.fisolima.ves.config;

import com.fisolima.ves.VESException;

public class DirectConfigProvider implements IConfigProvider {

    private final String storagePath;
    private final String databaseConnectionString;

    public DirectConfigProvider( String storagePath, String databaseConnectionString) {
        this.storagePath = storagePath;
        this.databaseConnectionString = databaseConnectionString;
    }
        
    @Override
    public String getStoragePath() throws VESException {
        if (storagePath.length() == 0)
            throw new VESException((503), "Storage path not configured");
        
        return storagePath;
    }

    @Override
    public String getDatabaseConnectionString() throws VESException {
        if (databaseConnectionString.length() == 0)
            throw new VESException((503), "database not configured");
        
        return databaseConnectionString;
    }
    
}
