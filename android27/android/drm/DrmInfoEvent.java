package android.drm;

import java.util.*;

public class DrmInfoEvent extends DrmEvent
{
    public static final int TYPE_ACCOUNT_ALREADY_REGISTERED = 5;
    public static final int TYPE_ALREADY_REGISTERED_BY_ANOTHER_ACCOUNT = 1;
    public static final int TYPE_REMOVE_RIGHTS = 2;
    public static final int TYPE_RIGHTS_INSTALLED = 3;
    public static final int TYPE_RIGHTS_REMOVED = 6;
    public static final int TYPE_WAIT_FOR_RIGHTS = 4;
    
    public DrmInfoEvent(final int uniqueId, final int type, final String message) {
        super(0, 0, null);
        throw new RuntimeException("Stub!");
    }
    
    public DrmInfoEvent(final int uniqueId, final int type, final String message, final HashMap<String, Object> attributes) {
        super(0, 0, null);
        throw new RuntimeException("Stub!");
    }
}
