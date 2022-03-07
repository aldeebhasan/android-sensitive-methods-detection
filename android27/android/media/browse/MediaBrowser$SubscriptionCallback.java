package android.media.browse;

import java.util.*;
import android.os.*;

public abstract static class SubscriptionCallback
{
    public SubscriptionCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onChildrenLoaded(final String parentId, final List<MediaItem> children) {
        throw new RuntimeException("Stub!");
    }
    
    public void onChildrenLoaded(final String parentId, final List<MediaItem> children, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
    
    public void onError(final String parentId) {
        throw new RuntimeException("Stub!");
    }
    
    public void onError(final String parentId, final Bundle options) {
        throw new RuntimeException("Stub!");
    }
}
