/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.ves.AppDomain;
import com.ves.Models.IResource;
import com.ves.Models.MemorySessionProvider;
import com.ves.Models.ResizeResource;
import com.ves.Models.ResourceType;
import com.ves.Models.Session;
import com.ves.Models.SubtitlesResource;
import com.ves.Models.VideoResource;
import com.ves.VESException;
import com.ves.config.DirectConfigProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author pippo
 */
public class SessionsTest {
    
    @Rule
    public TemporaryFolder testStoragePath = new TemporaryFolder();
    
    public SessionsTest() {
    }
    
    @Before
    public void setUp() throws VESException {
        AppDomain.Initialize( "VES Test Webservice",
                            new DirectConfigProvider( testStoragePath.getRoot().toString(), "database" ),
                            new MemorySessionProvider());
    }
    
    @After
    public void tearDown() {
    }
 
    @Test
    public void Session_New_Session_Is_Empty()
    {
        Session session = AppDomain.getSessionProvider().Create();
        
        assertNotNull(session);
        assertEquals(Session.Status.WAITING,session.getStatus());
        assertEquals(0,session.getResources().size());
    }
    
    @Test
    public void Session_Add_Resize_Resource()
    {
        Session session = AppDomain.getSessionProvider().Create();
        
        session.addResource( new ResizeResource( 50, 50));
        
        IResource res = session.getResources().get(0);
        
        assertEquals(ResourceType.RESIZE, res.getType());
        assertEquals("50;50", res.getValue());
    }
    
    @Test
    public void Session_Add_Video_Resource()
    {
        Session session = AppDomain.getSessionProvider().Create();
        
        session.addResource( new VideoResource( "file" ));
        
        IResource res = session.getResources().get(0);
        
        assertEquals(ResourceType.VIDEO, res.getType());
        assertEquals("file", res.getValue());
    }
    
    @Test
    public void Session_Add_Subtitles_Resource()
    {
        Session session = AppDomain.getSessionProvider().Create();
        
        session.addResource( new SubtitlesResource( "file" ));
        
        IResource res = session.getResources().get(0);
        
        assertEquals(ResourceType.SUBTITLE, res.getType());
        assertEquals("file", res.getValue());
    }
}
