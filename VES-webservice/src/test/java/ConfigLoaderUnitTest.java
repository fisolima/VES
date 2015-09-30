
import com.fisolima.ves.VESException;
import com.fisolima.ves.config.EtcdConfigProvider;
import com.fisolima.ves.config.NullConfigProvider;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.net.URI;


public class ConfigLoaderUnitTest {    
    
    public ConfigLoaderUnitTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        EtcdClient etcdClient = new EtcdClient(URI.create("http://127.0.0.1:2379"));
        
        etcdClient.set("VESStorage", "storage");
        etcdClient.set("VESDatabase", "database");
    }
    
    @After
    public void tearDown()  throws Exception {
        EtcdClient etcdClient = new EtcdClient(URI.create("http://127.0.0.1:2379"));
        
        etcdClient.delete("VESStorage");
        etcdClient.delete("VESDatabase");
    }
    
    @Test(expected=VESException.class)
    public void NullConfigProvider_Should_Fail_On_Missing_Storage() throws VESException
    {
        NullConfigProvider cfg = new NullConfigProvider();
        
        cfg.getStoragePath();
        
        assertFalse(true);
    }
    
    @Test(expected=VESException.class)
    public void NullConfigProvider_Should_Fail_On_Missing_Database() throws VESException
    {
        NullConfigProvider cfg = new NullConfigProvider();
        
        cfg.getDatabaseConnectionString();
        
        assertFalse(true);
    }
            
    @Test
    public void EtcdConfigProvider_Exists()
    {
        EtcdConfigProvider cfg = new EtcdConfigProvider("storage", "storage");
                
        assertNotNull(cfg);
    }
    
    @Test
    public void EtcdConfigProvider_Should_Read_Keys() throws VESException
    {
        EtcdConfigProvider cfg = EtcdConfigProvider.create( "http://127.0.0.1:2379", "VESStorage", "VESDatabase");
        
        assertEquals("storage", cfg.getStoragePath());
        assertEquals("database", cfg.getDatabaseConnectionString());
    }
    
    @Test(expected=VESException.class)
    public void EtcdConfigProvider_Should_Fail_On_Unreachable_Etcd() throws VESException
    {
        EtcdConfigProvider cfg = EtcdConfigProvider.create( "http://_MISSING_:2379", "VESStorage", "VESDatabase");
        
        assertFalse(true);
    }
    
    @Test(expected=VESException.class)
    public void EtcdConfigProvider_Should_Fail_On_Missing_Storage_Key() throws VESException
    {
        EtcdConfigProvider cfg = EtcdConfigProvider.create( "http://127.0.0.1:2379", "__NOSTORAGE__", "VESDatabase");
        
        assertFalse(true);
    }
    
    @Test(expected=VESException.class)
    public void EtcdConfigProvider_Should_Fail_On_Missing_Database_Key() throws VESException
    {
        EtcdConfigProvider cfg = EtcdConfigProvider.create( "http://127.0.0.1:2379", "VESStorage", "__NODB__");
        
        assertFalse(true);
    }
}
