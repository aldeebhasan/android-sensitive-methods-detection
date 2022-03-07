package java.util;

import java.io.*;
import java.lang.invoke.*;

public interface Entry<K, V>
{
    K getKey();
    
    V getValue();
    
    V setValue(final V p0);
    
    boolean equals(final Object p0);
    
    int hashCode();
    
    default <K extends Comparable<? super K>, V> Comparator<Entry<K, V>> comparingByKey() {
        return (Comparator<Entry<K, V>>)((entry, entry2) -> entry.getKey().compareTo(entry2.getKey()));
    }
    
    default <K, V extends Comparable<? super V>> Comparator<Entry<K, V>> comparingByValue() {
        return (Comparator<Entry<K, V>>)((entry, entry2) -> entry.getValue().compareTo(entry2.getValue()));
    }
    
    default <K, V> Comparator<Entry<K, V>> comparingByKey(final Comparator<? super K> comparator) {
        Objects.requireNonNull(comparator);
        return (Comparator<Entry<K, V>>)((entry, entry2) -> comparator.compare(entry.getKey(), entry2.getKey()));
    }
    
    default <K, V> Comparator<Entry<K, V>> comparingByValue(final Comparator<? super V> comparator) {
        Objects.requireNonNull(comparator);
        return (Comparator<Entry<K, V>>)((entry, entry2) -> comparator.compare(entry.getValue(), entry2.getValue()));
    }
}
