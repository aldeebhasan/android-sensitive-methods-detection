package android.media.session;

import android.content.*;
import java.util.*;
import android.os.*;

public final class MediaSessionManager
{
    MediaSessionManager() {
        throw new RuntimeException("Stub!");
    }
    
    public List<MediaController> getActiveSessions(final ComponentName notificationListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void addOnActiveSessionsChangedListener(final OnActiveSessionsChangedListener sessionListener, final ComponentName notificationListener) {
        throw new RuntimeException("Stub!");
    }
    
    public void addOnActiveSessionsChangedListener(final OnActiveSessionsChangedListener sessionListener, final ComponentName notificationListener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeOnActiveSessionsChangedListener(final OnActiveSessionsChangedListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnActiveSessionsChangedListener
    {
        void onActiveSessionsChanged(final List<MediaController> p0);
    }
}
