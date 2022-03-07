package android.content;

import java.util.*;

public interface Editor
{
    Editor putString(final String p0, final String p1);
    
    Editor putStringSet(final String p0, final Set<String> p1);
    
    Editor putInt(final String p0, final int p1);
    
    Editor putLong(final String p0, final long p1);
    
    Editor putFloat(final String p0, final float p1);
    
    Editor putBoolean(final String p0, final boolean p1);
    
    Editor remove(final String p0);
    
    Editor clear();
    
    boolean commit();
    
    void apply();
}
