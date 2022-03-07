package android.content;

import java.util.*;

public interface EntityIterator extends Iterator<Entity>
{
    void reset();
    
    void close();
}
