import com.ves.VESException;
import com.ves.AppDomain;
import com.ves.restapi.Configuration;
import com.ves.restapi.Main;
import org.junit.Test;
import static org.junit.Assert.*;
import com.ves.config.NullConfigProvider;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.ves.restapi.Sessions;
import java.net.URI;
import java.util.Set;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;


public class RESTapiTest extends JerseyTest
{
    private final String etcdAddress = "http://127.0.0.1:2379";

    @Override
    protected Application configure()
    {
        Set<Class<?>> resources = new java.util.HashSet<>();
        
        resources.add(Main.class);
        resources.add(com.ves.ExceptionResolver.class);
        resources.add(Configuration.class);
        resources.add(Sessions.class);
                
        Application app = new ResourceConfig(resources);
        
        return app;
    }
    
    @Before
    public void Startup() throws EtcdClientException
    {
        EtcdClient etcdClient = new EtcdClient(URI.create(etcdAddress));
        
        etcdClient.set("VESStorageTest", "storage");
        etcdClient.set("VESDatabaseTest", "database");
        
        AppDomain.Initialize("VES Test Webservice", new NullConfigProvider());
    }
    
    @After
    public void Shutdown() throws EtcdClientException
    {
        EtcdClient etcdClient = new EtcdClient(URI.create(etcdAddress));
        
        etcdClient.delete("VESStorageTest");
        etcdClient.delete("VESDatabaseTest");
    }
    
    @Test
    public void MainTest()
    {
        final String result = target("main").request().get(String.class);
        
        assertEquals("VES Test Webservice", result);        
    }
    
    @Test
    public void Direct_Config_Declare_Put() throws VESException
    {
        String value = "{storage:\"storage\",database:\"database\"}";
          
        Response res = target("cfg/direct").request().put( Entity.entity(value, MediaType.TEXT_PLAIN) );
        
        assertEquals(200, res.getStatus());
        assertEquals("storage",AppDomain.getConfigProvider().getStoragePath());
        assertEquals("database",AppDomain.getConfigProvider().getDatabaseConnectionString());
    }
    
    @Test
    public void Direct_Config_Declare_Should_Fail_On_Invalid_Data() throws VESException
    {
        String value = "{abc:\"storage\",database:\"database\"}";
          
        Response res = target("cfg/direct").request().put( Entity.entity(value, MediaType.TEXT_PLAIN) );
        
        assertEquals(400, res.getStatus());
    }
    
    @Test
    public void Etcd_Config_Declare_Put() throws VESException
    {
        String value = "{storageKey:\"VESStorageTest\",databaseKey:\"VESDatabaseTest\",etcdEndPoint:\"" + etcdAddress + "\"}";
          
        Response res = target("cfg/etcd").request().put( Entity.entity(value, MediaType.TEXT_PLAIN) );
        
        assertEquals(200, res.getStatus());
        assertEquals("storage",AppDomain.getConfigProvider().getStoragePath());
        assertEquals("database",AppDomain.getConfigProvider().getDatabaseConnectionString());
    }
    
    @Test
    public void Etcd_Config_Declare_Should_Fail_On_Invalid_Data() throws VESException
    {
        String value = "zge4zhxrtddth";
          
        Response res = target("cfg/etcd").request().put( Entity.entity(value, MediaType.TEXT_PLAIN) );
        
        assertEquals(400, res.getStatus());
    }
    
    @Test
    public void Sessions_Should_Return_List()
    {
        Response res = target("sessions").request().get();
        
        assertEquals( 200, res.getStatus());
        assertEquals("[]", res.readEntity(String.class));
    }
}