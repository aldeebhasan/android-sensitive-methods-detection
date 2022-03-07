package android.content;

import java.util.*;
import android.net.*;

public final class Entity
{
    public Entity(final ContentValues values) {
        throw new RuntimeException("Stub!");
    }
    
    public ContentValues getEntityValues() {
        throw new RuntimeException("Stub!");
    }
    
    public ArrayList<NamedContentValues> getSubValues() {
        throw new RuntimeException("Stub!");
    }
    
    public void addSubValue(final Uri uri, final ContentValues values) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static class NamedContentValues
    {
        public final Uri uri;
        public final ContentValues values;
        
        public NamedContentValues(final Uri uri, final ContentValues values) {
            throw new RuntimeException("Stub!");
        }
    }
}
