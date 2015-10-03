package com.ves.config;

import com.ves.VESException;


public interface IConfigProvider {

    /**
     * Return the storage path to save file resources
     * 
     * @return Physical storage path
     * @throws com.ves.VESException
     */
    public String getStoragePath() throws VESException;
    
    /**
     * Return the connection string for the database driver
     * 
     * @return Connection string
     * @throws com.ves.VESException
     */
    public String getDatabaseConnectionString() throws VESException;
}
