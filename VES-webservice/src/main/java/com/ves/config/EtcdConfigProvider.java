package com.ves.config;

import com.ves.VESException;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdResult;
import java.net.URI;

public class EtcdConfigProvider extends DirectConfigProvider{

    public static EtcdConfigProvider create(String etcdEndPoint, String storageKey, String databaseKey) throws VESException {
        try
        {
            EtcdClient etcdClient = new EtcdClient(URI.create(etcdEndPoint));
            
            EtcdResult res = etcdClient.get(storageKey);
            String storagePath = res.node.value;
            
            res = etcdClient.get(databaseKey);
            String databaseConnectionString = res.node.value;            
            
            return new EtcdConfigProvider(storagePath, databaseConnectionString);
        }
        catch (Exception exc) {
            throw new VESException(503, "ETCD unavailable: " + exc.getMessage());
        }
    }

    public EtcdConfigProvider(String storagePath, String databaseConnectionString) throws VESException {
        super(storagePath, databaseConnectionString);
    }

    @Override
    public String getStoragePath() throws VESException {        
        return super.getStoragePath();
    }

    @Override
    public String getDatabaseConnectionString() throws VESException {
        return super.getDatabaseConnectionString();
    }
    
}
