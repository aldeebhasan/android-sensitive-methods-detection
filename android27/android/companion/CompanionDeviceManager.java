package android.companion;

import android.os.*;
import java.util.*;
import android.content.*;

public final class CompanionDeviceManager
{
    public static final String EXTRA_DEVICE = "android.companion.extra.DEVICE";
    
    CompanionDeviceManager() {
        throw new RuntimeException("Stub!");
    }
    
    public void associate(final AssociationRequest request, final Callback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getAssociations() {
        throw new RuntimeException("Stub!");
    }
    
    public void disassociate(final String deviceMacAddress) {
        throw new RuntimeException("Stub!");
    }
    
    public void requestNotificationAccess(final ComponentName component) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasNotificationAccess(final ComponentName component) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract static class Callback
    {
        public Callback() {
            throw new RuntimeException("Stub!");
        }
        
        public abstract void onDeviceFound(final IntentSender p0);
        
        public abstract void onFailure(final CharSequence p0);
    }
}
