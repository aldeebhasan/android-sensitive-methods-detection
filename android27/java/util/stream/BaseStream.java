package java.util.stream;

import java.util.*;

public interface BaseStream<T, S extends BaseStream<T, S>> extends AutoCloseable
{
    Iterator<T> iterator();
    
    Spliterator<T> spliterator();
    
    boolean isParallel();
    
    S sequential();
    
    S parallel();
    
    S unordered();
    
    S onClose(final Runnable p0);
    
    void close();
}
