package android.telephony;

import android.content.*;
import java.util.*;

public class SubscriptionManager
{
    public static final String ACTION_DEFAULT_SMS_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SMS_SUBSCRIPTION_CHANGED";
    public static final String ACTION_DEFAULT_SUBSCRIPTION_CHANGED = "android.telephony.action.DEFAULT_SUBSCRIPTION_CHANGED";
    public static final int DATA_ROAMING_DISABLE = 0;
    public static final int DATA_ROAMING_ENABLE = 1;
    public static final String EXTRA_SUBSCRIPTION_INDEX = "android.telephony.extra.SUBSCRIPTION_INDEX";
    public static final int INVALID_SUBSCRIPTION_ID = -1;
    
    SubscriptionManager() {
        throw new RuntimeException("Stub!");
    }
    
    public static SubscriptionManager from(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public void addOnSubscriptionsChangedListener(final OnSubscriptionsChangedListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeOnSubscriptionsChangedListener(final OnSubscriptionsChangedListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public SubscriptionInfo getActiveSubscriptionInfo(final int subId) {
        throw new RuntimeException("Stub!");
    }
    
    public SubscriptionInfo getActiveSubscriptionInfoForSimSlotIndex(final int slotIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public List<SubscriptionInfo> getActiveSubscriptionInfoList() {
        throw new RuntimeException("Stub!");
    }
    
    public int getActiveSubscriptionInfoCount() {
        throw new RuntimeException("Stub!");
    }
    
    public int getActiveSubscriptionInfoCountMax() {
        throw new RuntimeException("Stub!");
    }
    
    public static int getDefaultSubscriptionId() {
        throw new RuntimeException("Stub!");
    }
    
    public static int getDefaultVoiceSubscriptionId() {
        throw new RuntimeException("Stub!");
    }
    
    public static int getDefaultSmsSubscriptionId() {
        throw new RuntimeException("Stub!");
    }
    
    public static int getDefaultDataSubscriptionId() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isNetworkRoaming(final int subId) {
        throw new RuntimeException("Stub!");
    }
    
    public static class OnSubscriptionsChangedListener
    {
        public OnSubscriptionsChangedListener() {
            throw new RuntimeException("Stub!");
        }
        
        public void onSubscriptionsChanged() {
            throw new RuntimeException("Stub!");
        }
    }
}
