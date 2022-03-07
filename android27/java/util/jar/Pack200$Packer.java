package java.util.jar;

import java.util.*;
import java.io.*;
import java.beans.*;

public interface Packer
{
    public static final String SEGMENT_LIMIT = "pack.segment.limit";
    public static final String KEEP_FILE_ORDER = "pack.keep.file.order";
    public static final String EFFORT = "pack.effort";
    public static final String DEFLATE_HINT = "pack.deflate.hint";
    public static final String MODIFICATION_TIME = "pack.modification.time";
    public static final String PASS_FILE_PFX = "pack.pass.file.";
    public static final String UNKNOWN_ATTRIBUTE = "pack.unknown.attribute";
    public static final String CLASS_ATTRIBUTE_PFX = "pack.class.attribute.";
    public static final String FIELD_ATTRIBUTE_PFX = "pack.field.attribute.";
    public static final String METHOD_ATTRIBUTE_PFX = "pack.method.attribute.";
    public static final String CODE_ATTRIBUTE_PFX = "pack.code.attribute.";
    public static final String PROGRESS = "pack.progress";
    public static final String KEEP = "keep";
    public static final String PASS = "pass";
    public static final String STRIP = "strip";
    public static final String ERROR = "error";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String LATEST = "latest";
    
    SortedMap<String, String> properties();
    
    void pack(final JarFile p0, final OutputStream p1) throws IOException;
    
    void pack(final JarInputStream p0, final OutputStream p1) throws IOException;
    
    @Deprecated
    default void addPropertyChangeListener(final PropertyChangeListener propertyChangeListener) {
    }
    
    @Deprecated
    default void removePropertyChangeListener(final PropertyChangeListener propertyChangeListener) {
    }
}
