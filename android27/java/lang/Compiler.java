package java.lang;

import java.security.*;

public final class Compiler
{
    private static native void initialize();
    
    private static native void registerNatives();
    
    public static native boolean compileClass(final Class<?> p0);
    
    public static native boolean compileClasses(final String p0);
    
    public static native Object command(final Object p0);
    
    public static native void enable();
    
    public static native void disable();
    
    static {
        registerNatives();
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                boolean b = false;
                final String property = System.getProperty("java.compiler");
                if (property != null && !property.equals("NONE") && !property.equals("")) {
                    try {
                        System.loadLibrary(property);
                        initialize();
                        b = true;
                    }
                    catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                        System.err.println("Warning: JIT compiler \"" + property + "\" not found. Will use interpreter.");
                    }
                }
                final String property2 = System.getProperty("java.vm.info");
                if (b) {
                    System.setProperty("java.vm.info", property2 + ", " + property);
                }
                else {
                    System.setProperty("java.vm.info", property2 + ", nojit");
                }
                return null;
            }
        });
    }
}
