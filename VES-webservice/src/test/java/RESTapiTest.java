import org.junit.Test;
import static org.junit.Assert.*;
import com.fisolima.ves.*;
import java.util.Set;
import javax.ws.rs.core.Application;
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
        
        Application app = new ResourceConfig(resources);
        
        return app;
    }
    
    @Before
    public void Startup()
    {
        AppDomain.Initialize("VES Test Webservice", null);
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
}