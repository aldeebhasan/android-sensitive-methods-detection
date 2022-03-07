package android.icu.text;

import android.icu.util.*;
import java.util.*;

public static class UiListItem
{
    public final ULocale minimized;
    public final ULocale modified;
    public final String nameInDisplayLocale;
    public final String nameInSelf;
    
    public UiListItem(final ULocale minimized, final ULocale modified, final String nameInDisplayLocale, final String nameInSelf) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object obj) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static Comparator<UiListItem> getComparator(final Comparator<Object> comparator, final boolean inSelf) {
        throw new RuntimeException("Stub!");
    }
}
