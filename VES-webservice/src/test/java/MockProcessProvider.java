
import com.ves.models.ISessionProvider;
import com.ves.models.Session;
import com.ves.process.Process;
import com.ves.process.ProcessProvider;


public class MockProcessProvider extends ProcessProvider {

    public MockProcessProvider() {
    }

    @Override
    protected Process CreateProcess(ISessionProvider sessionProvider, Session session) {
        return new MockProcess( sessionProvider, session);
    }
    
}
