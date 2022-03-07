package java.util.jar;

import sun.security.action.*;
import java.security.*;
import com.sun.java.util.jar.pack.*;
import java.util.*;
import java.beans.*;
import java.io.*;

public abstract class Pack200
{
    private static final String PACK_PROVIDER = "java.util.jar.Pack200.Packer";
    private static final String UNPACK_PROVIDER = "java.util.jar.Pack200.Unpacker";
    private static Class<?> packerImpl;
    private static Class<?> unpackerImpl;
    
    public static synchronized Packer newPacker() {
        return (Packer)newInstance("java.util.jar.Pack200.Packer");
    }
    
    public static Unpacker newUnpacker() {
        return (Unpacker)newInstance("java.util.jar.Pack200.Unpacker");
    }
    
    private static synchronized Object newInstance(final String s) {
        String s2 = "(unknown)";
        try {
            Class<?> forName = "java.util.jar.Pack200.Packer".equals(s) ? Pack200.packerImpl : Pack200.unpackerImpl;
            if (forName == null) {
                s2 = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction(s, ""));
                if (s2 != null && !s2.equals("")) {
                    forName = Class.forName(s2);
                }
                else if ("java.util.jar.Pack200.Packer".equals(s)) {
                    forName = PackerImpl.class;
                }
                else {
                    forName = UnpackerImpl.class;
                }
            }
            return forName.newInstance();
        }
        catch (ClassNotFoundException ex) {
            throw new Error("Class not found: " + s2 + ":\ncheck property " + s + " in your properties file.", ex);
        }
        catch (InstantiationException ex2) {
            throw new Error("Could not instantiate: " + s2 + ":\ncheck property " + s + " in your properties file.", ex2);
        }
        catch (IllegalAccessException ex3) {
            throw new Error("Cannot access class: " + s2 + ":\ncheck property " + s + " in your properties file.", ex3);
        }
    }
    
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
}
