package com.ves.models;

import com.ves.VESException;
import java.util.Collection;

public class NullSessionProvider implements ISessionProvider {

    @Override
    public Session Create() {
        throw new UnsupportedOperationException("Service not configured yet.");
    }

    @Override
    public void Update(Session session) {
        throw new UnsupportedOperationException("Service not configured yet.");
    }

    @Override
    public Session Get(String sessionId) {
        throw new UnsupportedOperationException("Service not configured yet.");
    }

    @Override
    public String GetResourcePath(String sessionId) throws VESException {
        throw new UnsupportedOperationException("Service not configured yet.");
    }

    @Override
    public Collection<Session> GetAll() {
        throw new UnsupportedOperationException("Service not configured yet.");
    }

    @Override
    public void Delete(String sessionId) {
        throw new UnsupportedOperationException("Service not configured yet.");
    }
    
}
