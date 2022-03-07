package android.nfc.cardemulation;

import android.nfc.*;
import android.content.*;
import java.util.*;
import android.app.*;

public final class CardEmulation
{
    public static final String ACTION_CHANGE_DEFAULT = "android.nfc.cardemulation.action.ACTION_CHANGE_DEFAULT";
    public static final String CATEGORY_OTHER = "other";
    public static final String CATEGORY_PAYMENT = "payment";
    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_SERVICE_COMPONENT = "component";
    public static final int SELECTION_MODE_ALWAYS_ASK = 1;
    public static final int SELECTION_MODE_ASK_IF_CONFLICT = 2;
    public static final int SELECTION_MODE_PREFER_DEFAULT = 0;
    
    CardEmulation() {
        throw new RuntimeException("Stub!");
    }
    
    public static synchronized CardEmulation getInstance(final NfcAdapter adapter) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDefaultServiceForCategory(final ComponentName service, final String category) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDefaultServiceForAid(final ComponentName service, final String aid) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean categoryAllowsForegroundPreference(final String category) {
        throw new RuntimeException("Stub!");
    }
    
    public int getSelectionModeForCategory(final String category) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean registerAidsForService(final ComponentName service, final String category, final List<String> aids) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getAidsForService(final ComponentName service, final String category) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean removeAidsForService(final ComponentName service, final String category) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setPreferredService(final Activity activity, final ComponentName service) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean unsetPreferredService(final Activity activity) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean supportsAidPrefixRegistration() {
        throw new RuntimeException("Stub!");
    }
}
