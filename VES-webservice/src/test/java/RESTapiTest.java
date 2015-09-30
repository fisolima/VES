import org.junit.Test;
import static org.junit.Assert.*;
import com.fisolima.ves.*;
import com.fisolima.ves.config.NullConfigProvider;
import com.google.gson.Gson;
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
    
    @Override
    protected Application configure()
    {
        Set<Class<?>> resources = new java.util.HashSet<>();
        
        resources.add(MainResource.class);
        resources.add(com.fisolima.ves.ExceptionResolver.class);
        resources.add(ConfigResource.class);
        
        Application app = new ResourceConfig(resources);
        
        return app;
    }
    
    @Before
    public void Startup()
    {
        AppDomain.Initialize("VES Test Webservice", new NullConfigProvider());
    }
    
    @After
    public void Shutdown()
    {        
    }
    
    @Test
    public void MainTest()
    {
        final String result = target("main").request().get(String.class);
        
        assertEquals("VES Test Webservice", result);        
    }
    
    @Test
    public void Direct_Config_Declare_Post() throws VESException
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
}