package android.telecom;

import android.app.*;
import android.content.*;
import android.os.*;

public abstract class CallScreeningService extends Service
{
    public static final String SERVICE_INTERFACE = "android.telecom.CallScreeningService";
    
    public CallScreeningService() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean onUnbind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract void onScreenCall(final Call.Details p0);
    
    public final void respondToCall(final Call.Details callDetails, final CallResponse response) {
        throw new RuntimeException("Stub!");
    }
    
    public static class CallResponse
    {
        CallResponse() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getDisallowCall() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getRejectCall() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getSkipCallLog() {
            throw new RuntimeException("Stub!");
        }
        
        public boolean getSkipNotification() {
            throw new RuntimeException("Stub!");
        }
        
        public static class Builder
        {
            public Builder() {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setDisallowCall(final boolean shouldDisallowCall) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setRejectCall(final boolean shouldRejectCall) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setSkipCallLog(final boolean shouldSkipCallLog) {
                throw new RuntimeException("Stub!");
            }
            
            public Builder setSkipNotification(final boolean shouldSkipNotification) {
                throw new RuntimeException("Stub!");
            }
            
            public CallResponse build() {
                throw new RuntimeException("Stub!");
            }
        }
    }
}
