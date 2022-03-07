package android.content.pm;

import java.util.*;
import android.content.*;

public static class ShortcutQuery
{
    public static final int FLAG_GET_KEY_FIELDS_ONLY = 4;
    public static final int FLAG_MATCH_DYNAMIC = 1;
    public static final int FLAG_MATCH_MANIFEST = 8;
    public static final int FLAG_MATCH_PINNED = 2;
    
    public ShortcutQuery() {
        throw new RuntimeException("Stub!");
    }
    
    public ShortcutQuery setChangedSince(final long changedSince) {
        throw new RuntimeException("Stub!");
    }
    
    public ShortcutQuery setPackage(final String packageName) {
        throw new RuntimeException("Stub!");
    }
    
    public ShortcutQuery setShortcutIds(final List<String> shortcutIds) {
        throw new RuntimeException("Stub!");
    }
    
    public ShortcutQuery setActivity(final ComponentName activity) {
        throw new RuntimeException("Stub!");
    }
    
    public ShortcutQuery setQueryFlags(final int queryFlags) {
        throw new RuntimeException("Stub!");
    }
}
