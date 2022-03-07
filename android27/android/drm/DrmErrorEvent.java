package android.drm;

import java.util.*;

public class DrmErrorEvent extends DrmEvent
{
    public static final int TYPE_ACQUIRE_DRM_INFO_FAILED = 2008;
    public static final int TYPE_NOT_SUPPORTED = 2003;
    public static final int TYPE_NO_INTERNET_CONNECTION = 2005;
    public static final int TYPE_OUT_OF_MEMORY = 2004;
    public static final int TYPE_PROCESS_DRM_INFO_FAILED = 2006;
    public static final int TYPE_REMOVE_ALL_RIGHTS_FAILED = 2007;
    public static final int TYPE_RIGHTS_NOT_INSTALLED = 2001;
    public static final int TYPE_RIGHTS_RENEWAL_NOT_ALLOWED = 2002;
    
    public DrmErrorEvent(final int uniqueId, final int type, final String message) {
        super(0, 0, null);
        throw new RuntimeException("Stub!");
    }
    
    public DrmErrorEvent(final int uniqueId, final int type, final String message, final HashMap<String, Object> attributes) {
        super(0, 0, null);
        throw new RuntimeException("Stub!");
    }
}
