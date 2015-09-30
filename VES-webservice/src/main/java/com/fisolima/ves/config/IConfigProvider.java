package com.fisolima.ves.config;

import com.fisolima.ves.VESException;


public interface IConfigProvider {

    /**
     * Return the storage path to save file resources
     * 
     * @return Physical storage path
     * @throws com.fisolima.ves.VESException
     */
    public String getStoragePath() throws VESException;
    
    /**
     * Return the connection string for the database driver
     * 
     * @return Connection string
     * @throws com.fisolima.ves.VESException
     */
    public String getDatabaseConnectionString() throws VESException;
}
