package android.provider;

import android.content.*;

public static final class PinnedPositions
{
    public static final int DEMOTED = -1;
    public static final int UNPINNED = 0;
    
    public PinnedPositions() {
        throw new RuntimeException("Stub!");
    }
    
    public static void undemote(final ContentResolver contentResolver, final long contactId) {
        throw new RuntimeException("Stub!");
    }
    
    public static void pin(final ContentResolver contentResolver, final long contactId, final int pinnedPosition) {
        throw new RuntimeException("Stub!");
    }
}
