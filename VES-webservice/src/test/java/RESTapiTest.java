import com.ves.VESException;
import com.ves.AppDomain;
import com.ves.restapi.Configuration;
import com.ves.restapi.Main;
import org.junit.Test;
import static org.junit.Assert.*;
import com.ves.config.NullConfigProvider;
import com.justinsb.etcd.EtcdClient;
import com.justinsb.etcd.EtcdClientException;
import com.ves.models.MemorySessionProvider;
import com.ves.models.Session;
import com.ves.helpers.JsonSerialization;
import com.ves.models.IResource;
import com.ves.models.ResizeResource;
import com.ves.models.ResourceType;
import com.ves.restapi.Sessions;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;


public class RESTapiTest extends JerseyTest
{
    private final String etcdAddress = "http://127.0.0.1:2379";

    @Rule
    public TemporaryFolder testStoragePath = new TemporaryFolder();
    
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
        
        AppDomain.Initialize("VES Test Webservice", new NullConfigProvider(), new MemorySessionProvider());
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
    }
    
    @Test
    public void Sessions_Should_Return_Not_Empty_List()
    {
        for ( int i=0; i<5; i++)
            AppDomain.getSessionProvider().Create();
        
        Response res = target("sessions").request().get();
        
        Session[] sessions = JsonSerialization.ParseSessions(res.readEntity(String.class));//res.readEntity(Session[].class);
        
        assertEquals( 200, res.getStatus());
        assertNotSame( 0, sessions.length );
    }
    
    @Test
    public void Sessions_Can_Create_New_Session()
    {
        Response res = target("sessions").request().post(null);
                
        Map<String,String> responseJson = JsonSerialization.Parse(res.readEntity(String.class));
                
        assertEquals( 201, res.getStatus() );
        assertNotNull(responseJson.get("location"));
    }
    
    @Test
    public void SessionProvider_Can_Query_Existsing_Session()
    {
        Session originalSession = AppDomain.getSessionProvider().Create();
        
        Session returnedSession = AppDomain.getSessionProvider().Get(originalSession.getId());
                
        assertEquals(originalSession.getId(), returnedSession.getId());
    }
    
    @Test
    public void Sessions_Query_A_Session()
    {
        Session originalSession = AppDomain.getSessionProvider().Create();
        
        Response res = target("sessions/" + originalSession.getId()).request().get();
        
        Session session = JsonSerialization.ParseSession(res.readEntity(String.class));
        
        assertEquals(200, res.getStatus());
        assertEquals(originalSession.getId(), session.getId());
    }
    
    @Test
    public void Sessions_Delete_A_Session()
    {
        Session session = AppDomain.getSessionProvider().Create();
        
        Response res = target("sessions/" + session.getId()).request().delete();
        
        session = AppDomain.getSessionProvider().Get(session.getId());
        
        assertEquals(200, res.getStatus());
        assertNull(session);
    }
    
    @Test
    public void Sessions_Can_Put_A_Resize_Resource()
    {
        Session session = AppDomain.getSessionProvider().Create();
        
        Response res = target("sessions/" + session.getId() + "/resize")
                        .request()
                        .post( Entity.entity("{widthPercentage:\"50\",heightPercentage:\"60\"}",MediaType.APPLICATION_JSON) );
        
        Response resSession = target("sessions/" + session.getId()).request().get();
        
        String sessionJSON = resSession.readEntity(String.class);
        
        session = JsonSerialization.ParseSession(sessionJSON);
        
        List<IResource> sessionResList = session.getResources( ResourceType.RESIZE );
        
        assertEquals(201, res.getStatus());
        assertNotSame(0, sessionResList.size());
        assertEquals("50;60", ((ResizeResource)sessionResList.get(0)).getValue());
    }
}