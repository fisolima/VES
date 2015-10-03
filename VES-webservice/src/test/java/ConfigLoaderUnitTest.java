
import com.ves.VESException;
import com.ves.config.EtcdConfigProvider;
import com.ves.config.NullConfigProvider;
import com.justinsb.etcd.EtcdClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.net.URI;


public class ConfigLoaderUnitTest {    
    
    private String etcdAddress = "http://127.0.0.1:2379";
    
    public ConfigLoaderUnitTest() {
    }
    
    @Before
    public void setUp() throws Exception {
        EtcdClient etcdClient = new EtcdClient(URI.create(etcdAddress));
        
        etcdClient.set("VESStorage", "storage");
        etcdClient.set("VESDatabase", "database");
    }
    
    @After
    public void tearDown()  throws Exception {
        EtcdClient etcdClient = new EtcdClient(URI.create(etcdAddress));
        
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
    public void EtcdConfigProvider_Exists() throws VESException
    {
        EtcdConfigProvider cfg = new EtcdConfigProvider("storage", "storage");
                
        assertNotNull(cfg);
    }
    
    @Test
    public void EtcdConfigProvider_Should_Read_Keys() throws VESException
    {
        EtcdConfigProvider cfg = EtcdConfigProvider.create( etcdAddress, "VESStorage", "VESDatabase");
        
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
        EtcdConfigProvider cfg = EtcdConfigProvider.create( etcdAddress, "__NOSTORAGE__", "VESDatabase");
        
        assertFalse(true);
    }
    
    @Test(expected=VESException.class)
    public void EtcdConfigProvider_Should_Fail_On_Missing_Database_Key() throws VESException
    {
        EtcdConfigProvider cfg = EtcdConfigProvider.create( etcdAddress, "VESStorage", "__NODB__");
        
        assertFalse(true);
    }
}
