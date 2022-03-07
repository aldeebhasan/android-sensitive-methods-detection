package android.service.voice;

import android.app.*;
import android.content.*;
import android.os.*;
import android.content.res.*;
import java.io.*;

public abstract class VoiceInteractionSessionService extends Service
{
    public VoiceInteractionSessionService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCreate() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract VoiceInteractionSession onNewSession(final Bundle p0);
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onLowMemory() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onTrimMemory(final int level) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void dump(final FileDescriptor fd, final PrintWriter writer, final String[] args) {
        throw new RuntimeException("Stub!");
    }
}
