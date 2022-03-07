package java.nio.file;

import java.util.*;

public interface WatchKey
{
    boolean isValid();
    
    List<WatchEvent<?>> pollEvents();
    
    boolean reset();
    
    void cancel();
    
    Watchable watchable();
}
