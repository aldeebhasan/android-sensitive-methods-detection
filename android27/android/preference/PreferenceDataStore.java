package android.preference;

import java.util.*;

public interface PreferenceDataStore
{
    default void putString(final String key, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    default void putStringSet(final String key, final Set<String> values) {
        throw new RuntimeException("Stub!");
    }
    
    default void putInt(final String key, final int value) {
        throw new RuntimeException("Stub!");
    }
    
    default void putLong(final String key, final long value) {
        throw new RuntimeException("Stub!");
    }
    
    default void putFloat(final String key, final float value) {
        throw new RuntimeException("Stub!");
    }
    
    default void putBoolean(final String key, final boolean value) {
        throw new RuntimeException("Stub!");
    }
    
    default String getString(final String key, final String defValue) {
        throw new RuntimeException("Stub!");
    }
    
    default Set<String> getStringSet(final String key, final Set<String> defValues) {
        throw new RuntimeException("Stub!");
    }
    
    default int getInt(final String key, final int defValue) {
        throw new RuntimeException("Stub!");
    }
    
    default long getLong(final String key, final long defValue) {
        throw new RuntimeException("Stub!");
    }
    
    default float getFloat(final String key, final float defValue) {
        throw new RuntimeException("Stub!");
    }
    
    default boolean getBoolean(final String key, final boolean defValue) {
        throw new RuntimeException("Stub!");
    }
}
