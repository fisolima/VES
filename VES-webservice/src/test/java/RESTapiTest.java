import org.junit.Test;
import static org.junit.Assert.*;
import com.fisolima.ves.*;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;


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
    
    @Test
    public void MainTest()
    {
        final String result = target("main").request().get(String.class);
        
        assertEquals("VES WebService", result);        
    }
    
//    @Test
//    public void MainTest2()
//    {
//        final String result = target("main2").request().get(String.class);
//        
//        assertEquals("this is main2", result);        
//    }
}