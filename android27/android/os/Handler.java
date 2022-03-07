package android.os;

import android.util.*;

public class Handler
{
    public Handler() {
        throw new RuntimeException("Stub!");
    }
    
    public Handler(final Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public Handler(final Looper looper) {
        throw new RuntimeException("Stub!");
    }
    
    public Handler(final Looper looper, final Callback callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void handleMessage(final Message msg) {
        throw new RuntimeException("Stub!");
    }
    
    public void dispatchMessage(final Message msg) {
        throw new RuntimeException("Stub!");
    }
    
    public String getMessageName(final Message message) {
        throw new RuntimeException("Stub!");
    }
    
    public final Message obtainMessage() {
        throw new RuntimeException("Stub!");
    }
    
    public final Message obtainMessage(final int what) {
        throw new RuntimeException("Stub!");
    }
    
    public final Message obtainMessage(final int what, final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    public final Message obtainMessage(final int what, final int arg1, final int arg2) {
        throw new RuntimeException("Stub!");
    }
    
    public final Message obtainMessage(final int what, final int arg1, final int arg2, final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean post(final Runnable r) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean postAtTime(final Runnable r, final long uptimeMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean postAtTime(final Runnable r, final Object token, final long uptimeMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean postDelayed(final Runnable r, final long delayMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean postAtFrontOfQueue(final Runnable r) {
        throw new RuntimeException("Stub!");
    }
    
    public final void removeCallbacks(final Runnable r) {
        throw new RuntimeException("Stub!");
    }
    
    public final void removeCallbacks(final Runnable r, final Object token) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean sendMessage(final Message msg) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean sendEmptyMessage(final int what) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean sendEmptyMessageDelayed(final int what, final long delayMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean sendEmptyMessageAtTime(final int what, final long uptimeMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean sendMessageDelayed(final Message msg, final long delayMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean sendMessageAtTime(final Message msg, final long uptimeMillis) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean sendMessageAtFrontOfQueue(final Message msg) {
        throw new RuntimeException("Stub!");
    }
    
    public final void removeMessages(final int what) {
        throw new RuntimeException("Stub!");
    }
    
    public final void removeMessages(final int what, final Object object) {
        throw new RuntimeException("Stub!");
    }
    
    public final void removeCallbacksAndMessages(final Object token) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean hasMessages(final int what) {
        throw new RuntimeException("Stub!");
    }
    
    public final boolean hasMessages(final int what, final Object object) {
        throw new RuntimeException("Stub!");
    }
    
    public final Looper getLooper() {
        throw new RuntimeException("Stub!");
    }
    
    public final void dump(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public interface Callback
    {
        boolean handleMessage(final Message p0);
    }
}
