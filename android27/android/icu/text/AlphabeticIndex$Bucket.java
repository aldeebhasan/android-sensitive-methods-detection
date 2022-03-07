package android.icu.text;

import java.util.*;

public static class Bucket<V> implements Iterable<Record<V>>
{
    Bucket() {
        throw new RuntimeException("Stub!");
    }
    
    public String getLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public LabelType getLabelType() {
        throw new RuntimeException("Stub!");
    }
    
    public int size() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Iterator<Record<V>> iterator() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public enum LabelType
    {
        INFLOW, 
        NORMAL, 
        OVERFLOW, 
        UNDERFLOW;
    }
}
