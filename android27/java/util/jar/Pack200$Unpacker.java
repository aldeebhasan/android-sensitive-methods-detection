package java.util.jar;

import java.util.*;
import java.io.*;
import java.beans.*;

public interface Unpacker
{
    public static final String KEEP = "keep";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String DEFLATE_HINT = "unpack.deflate.hint";
    public static final String PROGRESS = "unpack.progress";
    
    SortedMap<String, String> properties();
    
    void unpack(final InputStream p0, final JarOutputStream p1) throws IOException;
    
    void unpack(final File p0, final JarOutputStream p1) throws IOException;
    
    @Deprecated
    default void addPropertyChangeListener(final PropertyChangeListener propertyChangeListener) {
    }
    
    @Deprecated
    default void removePropertyChangeListener(final PropertyChangeListener propertyChangeListener) {
    }
}
