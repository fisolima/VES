
package com.fisolima.ves.config;

import com.fisolima.ves.VESException;

/**
 * Define a default invalid config provider * 
 */
public class NullConfigProvider implements IConfigProvider {

    @Override
    public String getStoragePath() throws VESException {
        throw new VESException(503, "Storage path not configured");
    }

    @Override
    public String getDatabaseConnectionString() throws VESException {
        throw new VESException(503, "Database not configured");
    }
    
}
