package com.ves.process;

import com.ves.models.ISessionProvider;
import com.ves.models.Session;

public class FfmpegProcessProvider extends ProcessProvider
{
    @Override
    protected Process CreateProcess(ISessionProvider sessionProvider, Session session) {
        return new FfmpegProcess(sessionProvider, session);
    }
}
