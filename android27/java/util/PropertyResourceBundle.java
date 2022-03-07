package java.util;

import java.io.*;
import sun.util.*;

public class PropertyResourceBundle extends ResourceBundle
{
    private Map<String, Object> lookup;
    
    public PropertyResourceBundle(final InputStream inputStream) throws IOException {
        final Properties properties = new Properties();
        properties.load(inputStream);
        this.lookup = new HashMap<String, Object>((Map<? extends String, ?>)properties);
    }
    
    public PropertyResourceBundle(final Reader reader) throws IOException {
        final Properties properties = new Properties();
        properties.load(reader);
        this.lookup = new HashMap<String, Object>((Map<? extends String, ?>)properties);
    }
    
    public Object handleGetObject(final String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        return this.lookup.get(s);
    }
    
    @Override
    public Enumeration<String> getKeys() {
        final ResourceBundle parent = this.parent;
        return new ResourceBundleEnumeration(this.lookup.keySet(), (parent != null) ? parent.getKeys() : null);
    }
    
    @Override
    protected Set<String> handleKeySet() {
        return this.lookup.keySet();
    }
}
