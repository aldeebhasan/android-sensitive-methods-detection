package java.util;

import java.net.*;
import java.io.*;
import java.security.*;

public final class ServiceLoader<S> implements Iterable<S>
{
    private static final String PREFIX = "META-INF/services/";
    private final Class<S> service;
    private final ClassLoader loader;
    private final AccessControlContext acc;
    private LinkedHashMap<String, S> providers;
    private LazyIterator lookupIterator;
    
    public void reload() {
        this.providers.clear();
        this.lookupIterator = new LazyIterator((Class)this.service, this.loader);
    }
    
    private ServiceLoader(final Class<S> clazz, final ClassLoader classLoader) {
        this.providers = new LinkedHashMap<String, S>();
        this.service = Objects.requireNonNull(clazz, "Service interface cannot be null");
        this.loader = ((classLoader == null) ? ClassLoader.getSystemClassLoader() : classLoader);
        this.acc = ((System.getSecurityManager() != null) ? AccessController.getContext() : null);
        this.reload();
    }
    
    private static void fail(final Class<?> clazz, final String s, final Throwable t) throws ServiceConfigurationError {
        throw new ServiceConfigurationError(clazz.getName() + ": " + s, t);
    }
    
    private static void fail(final Class<?> clazz, final String s) throws ServiceConfigurationError {
        throw new ServiceConfigurationError(clazz.getName() + ": " + s);
    }
    
    private static void fail(final Class<?> clazz, final URL url, final int n, final String s) throws ServiceConfigurationError {
        fail(clazz, url + ":" + n + ": " + s);
    }
    
    private int parseLine(final Class<?> clazz, final URL url, final BufferedReader bufferedReader, final int n, final List<String> list) throws IOException, ServiceConfigurationError {
        String s = bufferedReader.readLine();
        if (s == null) {
            return -1;
        }
        final int index = s.indexOf(35);
        if (index >= 0) {
            s = s.substring(0, index);
        }
        final String trim = s.trim();
        final int length = trim.length();
        if (length != 0) {
            if (trim.indexOf(32) >= 0 || trim.indexOf(9) >= 0) {
                fail(clazz, url, n, "Illegal configuration-file syntax");
            }
            final int codePoint = trim.codePointAt(0);
            if (!Character.isJavaIdentifierStart(codePoint)) {
                fail(clazz, url, n, "Illegal provider-class name: " + trim);
            }
            int codePoint2;
            for (int i = Character.charCount(codePoint); i < length; i += Character.charCount(codePoint2)) {
                codePoint2 = trim.codePointAt(i);
                if (!Character.isJavaIdentifierPart(codePoint2) && codePoint2 != 46) {
                    fail(clazz, url, n, "Illegal provider-class name: " + trim);
                }
            }
            if (!this.providers.containsKey(trim) && !list.contains(trim)) {
                list.add(trim);
            }
        }
        return n + 1;
    }
    
    private Iterator<String> parse(final Class<?> clazz, final URL url) throws ServiceConfigurationError {
        InputStream openStream = null;
        BufferedReader bufferedReader = null;
        final ArrayList<String> list = new ArrayList<String>();
        try {
            openStream = url.openStream();
            bufferedReader = new BufferedReader(new InputStreamReader(openStream, "utf-8"));
            int line = 1;
            while ((line = this.parseLine(clazz, url, bufferedReader, line, list)) >= 0) {}
        }
        catch (IOException ex) {
            fail(clazz, "Error reading configuration file", ex);
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (openStream != null) {
                    openStream.close();
                }
            }
            catch (IOException ex2) {
                fail(clazz, "Error closing configuration file", ex2);
            }
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (openStream != null) {
                    openStream.close();
                }
            }
            catch (IOException ex3) {
                fail(clazz, "Error closing configuration file", ex3);
            }
        }
        return list.iterator();
    }
    
    @Override
    public Iterator<S> iterator() {
        return new Iterator<S>() {
            Iterator<Map.Entry<String, S>> knownProviders = ServiceLoader.this.providers.entrySet().iterator();
            
            @Override
            public boolean hasNext() {
                return this.knownProviders.hasNext() || ServiceLoader.this.lookupIterator.hasNext();
            }
            
            @Override
            public S next() {
                if (this.knownProviders.hasNext()) {
                    return this.knownProviders.next().getValue();
                }
                return ServiceLoader.this.lookupIterator.next();
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    public static <S> ServiceLoader<S> load(final Class<S> clazz, final ClassLoader classLoader) {
        return new ServiceLoader<S>(clazz, classLoader);
    }
    
    public static <S> ServiceLoader<S> load(final Class<S> clazz) {
        return load(clazz, Thread.currentThread().getContextClassLoader());
    }
    
    public static <S> ServiceLoader<S> loadInstalled(final Class<S> clazz) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        ClassLoader classLoader2 = null;
        while (classLoader != null) {
            classLoader2 = classLoader;
            classLoader = classLoader.getParent();
        }
        return load(clazz, classLoader2);
    }
    
    @Override
    public String toString() {
        return "java.util.ServiceLoader[" + this.service.getName() + "]";
    }
    
    private class LazyIterator implements Iterator<S>
    {
        Class<S> service;
        ClassLoader loader;
        Enumeration<URL> configs;
        Iterator<String> pending;
        String nextName;
        
        private LazyIterator(final Class<S> service, final ClassLoader loader) {
            this.configs = null;
            this.pending = null;
            this.nextName = null;
            this.service = service;
            this.loader = loader;
        }
        
        private boolean hasNextService() {
            if (this.nextName != null) {
                return true;
            }
            if (this.configs == null) {
                try {
                    final String string = "META-INF/services/" + this.service.getName();
                    if (this.loader == null) {
                        this.configs = ClassLoader.getSystemResources(string);
                    }
                    else {
                        this.configs = this.loader.getResources(string);
                    }
                }
                catch (IOException ex) {
                    fail(this.service, "Error locating configuration files", ex);
                }
            }
            while (this.pending == null || !this.pending.hasNext()) {
                if (!this.configs.hasMoreElements()) {
                    return false;
                }
                this.pending = (Iterator<String>)ServiceLoader.this.parse(this.service, this.configs.nextElement());
            }
            this.nextName = this.pending.next();
            return true;
        }
        
        private S nextService() {
            if (!this.hasNextService()) {
                throw new NoSuchElementException();
            }
            final String nextName = this.nextName;
            this.nextName = null;
            Class<?> forName = null;
            try {
                forName = Class.forName(nextName, false, this.loader);
            }
            catch (ClassNotFoundException ex) {
                fail(this.service, "Provider " + nextName + " not found");
            }
            if (!this.service.isAssignableFrom(forName)) {
                fail(this.service, "Provider " + nextName + " not a subtype");
            }
            try {
                final S cast = this.service.cast(forName.newInstance());
                ServiceLoader.this.providers.put(nextName, cast);
                return cast;
            }
            catch (Throwable t) {
                fail(this.service, "Provider " + nextName + " could not be instantiated", t);
                throw new Error();
            }
        }
        
        @Override
        public boolean hasNext() {
            if (ServiceLoader.this.acc == null) {
                return this.hasNextService();
            }
            return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
                @Override
                public Boolean run() {
                    return LazyIterator.this.hasNextService();
                }
            }, ServiceLoader.this.acc);
        }
        
        @Override
        public S next() {
            if (ServiceLoader.this.acc == null) {
                return this.nextService();
            }
            return AccessController.doPrivileged((PrivilegedAction<S>)new PrivilegedAction<S>() {
                @Override
                public S run() {
                    return (S)LazyIterator.this.nextService();
                }
            }, ServiceLoader.this.acc);
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
