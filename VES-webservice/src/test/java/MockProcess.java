
import com.ves.models.ISessionProvider;
import com.ves.models.Session;

public class MockProcess extends com.ves.process.Process {

    public MockProcess( ISessionProvider sessionProvider, Session session ) {
        super(sessionProvider,session);
    }
    
    @Override
    public void Start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Stop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
