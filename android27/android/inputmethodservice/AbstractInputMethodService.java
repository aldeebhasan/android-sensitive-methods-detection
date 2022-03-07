package android.inputmethodservice;

import android.app.*;
import java.io.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.inputmethod.*;

public abstract class AbstractInputMethodService extends Service implements KeyEvent.Callback
{
    public AbstractInputMethodService() {
        throw new RuntimeException("Stub!");
    }
    
    public KeyEvent.DispatcherState getKeyDispatcherState() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract AbstractInputMethodImpl onCreateInputMethodInterface();
    
    public abstract AbstractInputMethodSessionImpl onCreateInputMethodSessionInterface();
    
    @Override
    protected void dump(final FileDescriptor fd, final PrintWriter fout, final String[] args) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onTrackballEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean onGenericMotionEvent(final MotionEvent event) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract class AbstractInputMethodImpl implements InputMethod
    {
        public AbstractInputMethodImpl() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void createSession(final SessionCallback callback) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void setSessionEnabled(final InputMethodSession session, final boolean enabled) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void revokeSession(final InputMethodSession session) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public abstract class AbstractInputMethodSessionImpl implements InputMethodSession
    {
        public AbstractInputMethodSessionImpl() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isEnabled() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean isRevoked() {
            throw new RuntimeException("Stub!");
        }
        
        public void setEnabled(final boolean enabled) {
            throw new RuntimeException("Stub!");
        }
        
        public void revokeSelf() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void dispatchKeyEvent(final int seq, final KeyEvent event, final EventCallback callback) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void dispatchTrackballEvent(final int seq, final MotionEvent event, final EventCallback callback) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void dispatchGenericMotionEvent(final int seq, final MotionEvent event, final EventCallback callback) {
            throw new RuntimeException("Stub!");
        }
    }
}
