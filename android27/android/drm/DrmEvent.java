package android.drm;

import java.util.*;

public class DrmEvent
{
    public static final String DRM_INFO_OBJECT = "drm_info_object";
    public static final String DRM_INFO_STATUS_OBJECT = "drm_info_status_object";
    public static final int TYPE_ALL_RIGHTS_REMOVED = 1001;
    public static final int TYPE_DRM_INFO_PROCESSED = 1002;
    
    protected DrmEvent(final int uniqueId, final int type, final String message, final HashMap<String, Object> attributes) {
        throw new RuntimeException("Stub!");
    }
    
    protected DrmEvent(final int uniqueId, final int type, final String message) {
        throw new RuntimeException("Stub!");
    }
    
    public int getUniqueId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getType() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMessage() {
        throw new RuntimeException("Stub!");
    }
    
    public Object getAttribute(final String key) {
        throw new RuntimeException("Stub!");
    }
}
