package android.service.voice;

import android.app.*;
import android.content.*;
import android.os.*;
import java.util.*;
import java.io.*;

public class VoiceInteractionService extends Service
{
    public static final String SERVICE_INTERFACE = "android.service.voice.VoiceInteractionService";
    public static final String SERVICE_META_DATA = "android.voice_interaction";
    
    public VoiceInteractionService() {
        throw new RuntimeException("Stub!");
    }
    
    public void onLaunchVoiceAssistFromKeyguard() {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isActiveService(final Context context, final ComponentName service) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDisabledShowContext(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public int getDisabledShowContext() {
        throw new RuntimeException("Stub!");
    }
    
    public void showSession(final Bundle args, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void onCreate() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public void onReady() {
        throw new RuntimeException("Stub!");
    }
    
    public void onShutdown() {
        throw new RuntimeException("Stub!");
    }
    
    public final AlwaysOnHotwordDetector createAlwaysOnHotwordDetector(final String keyphrase, final Locale locale, final AlwaysOnHotwordDetector.Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void dump(final FileDescriptor fd, final PrintWriter pw, final String[] args) {
        throw new RuntimeException("Stub!");
    }
}
