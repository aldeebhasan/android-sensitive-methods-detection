package dalvik.system;

import java.io.*;
import java.net.*;
import java.util.*;

public class BaseDexClassLoader extends ClassLoader
{
    public BaseDexClassLoader(final String dexPath, final File optimizedDirectory, final String librarySearchPath, final ClassLoader parent) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected URL findResource(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected Enumeration<URL> findResources(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public String findLibrary(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected synchronized Package getPackage(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
