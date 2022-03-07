package java.util;

import sun.util.*;

public abstract class ListResourceBundle extends ResourceBundle
{
    private Map<String, Object> lookup;
    
    public ListResourceBundle() {
        this.lookup = null;
    }
    
    public final Object handleGetObject(final String s) {
        if (this.lookup == null) {
            this.loadLookup();
        }
        if (s == null) {
            throw new NullPointerException();
        }
        return this.lookup.get(s);
    }
    
    @Override
    public Enumeration<String> getKeys() {
        if (this.lookup == null) {
            this.loadLookup();
        }
        final ResourceBundle parent = this.parent;
        return new ResourceBundleEnumeration(this.lookup.keySet(), (parent != null) ? parent.getKeys() : null);
    }
    
    @Override
    protected Set<String> handleKeySet() {
        if (this.lookup == null) {
            this.loadLookup();
        }
        return this.lookup.keySet();
    }
    
    protected abstract Object[][] getContents();
    
    private synchronized void loadLookup() {
        if (this.lookup != null) {
            return;
        }
        final Object[][] contents = this.getContents();
        final HashMap lookup = new HashMap<String, Object>(contents.length);
        for (int i = 0; i < contents.length; ++i) {
            final String s = (String)contents[i][0];
            final Object o = contents[i][1];
            if (s == null || o == null) {
                throw new NullPointerException();
            }
            lookup.put(s, o);
        }
        this.lookup = (Map<String, Object>)lookup;
    }
}
