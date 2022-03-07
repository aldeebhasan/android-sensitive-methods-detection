package java.util.prefs;

import java.security.*;
import java.io.*;
import java.util.*;

public abstract class AbstractPreferences extends Preferences
{
    private final String name;
    private final String absolutePath;
    final AbstractPreferences parent;
    private final AbstractPreferences root;
    protected boolean newNode;
    private Map<String, AbstractPreferences> kidCache;
    private boolean removed;
    private PreferenceChangeListener[] prefListeners;
    private NodeChangeListener[] nodeListeners;
    protected final Object lock;
    private static final String[] EMPTY_STRING_ARRAY;
    private static final AbstractPreferences[] EMPTY_ABSTRACT_PREFS_ARRAY;
    private static final List<EventObject> eventQueue;
    private static Thread eventDispatchThread;
    
    protected AbstractPreferences(final AbstractPreferences parent, final String name) {
        this.newNode = false;
        this.kidCache = new HashMap<String, AbstractPreferences>();
        this.removed = false;
        this.prefListeners = new PreferenceChangeListener[0];
        this.nodeListeners = new NodeChangeListener[0];
        this.lock = new Object();
        if (parent == null) {
            if (!name.equals("")) {
                throw new IllegalArgumentException("Root name '" + name + "' must be \"\"");
            }
            this.absolutePath = "/";
            this.root = this;
        }
        else {
            if (name.indexOf(47) != -1) {
                throw new IllegalArgumentException("Name '" + name + "' contains '/'");
            }
            if (name.equals("")) {
                throw new IllegalArgumentException("Illegal name: empty string");
            }
            this.root = parent.root;
            this.absolutePath = ((parent == this.root) ? ("/" + name) : (parent.absolutePath() + "/" + name));
        }
        this.name = name;
        this.parent = parent;
    }
    
    @Override
    public void put(final String s, final String s2) {
        if (s == null || s2 == null) {
            throw new NullPointerException();
        }
        if (s.length() > 80) {
            throw new IllegalArgumentException("Key too long: " + s);
        }
        if (s2.length() > 8192) {
            throw new IllegalArgumentException("Value too long: " + s2);
        }
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            this.putSpi(s, s2);
            this.enqueuePreferenceChangeEvent(s, s2);
        }
    }
    
    @Override
    public String get(final String s, final String s2) {
        if (s == null) {
            throw new NullPointerException("Null key");
        }
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            String spi = null;
            try {
                spi = this.getSpi(s);
            }
            catch (Exception ex) {}
            return (spi == null) ? s2 : spi;
        }
    }
    
    @Override
    public void remove(final String s) {
        Objects.requireNonNull(s, "Specified key cannot be null");
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            this.removeSpi(s);
            this.enqueuePreferenceChangeEvent(s, null);
        }
    }
    
    @Override
    public void clear() throws BackingStoreException {
        synchronized (this.lock) {
            final String[] keys = this.keys();
            for (int i = 0; i < keys.length; ++i) {
                this.remove(keys[i]);
            }
        }
    }
    
    @Override
    public void putInt(final String s, final int n) {
        this.put(s, Integer.toString(n));
    }
    
    @Override
    public int getInt(final String s, final int n) {
        int int1 = n;
        try {
            final String value = this.get(s, null);
            if (value != null) {
                int1 = Integer.parseInt(value);
            }
        }
        catch (NumberFormatException ex) {}
        return int1;
    }
    
    @Override
    public void putLong(final String s, final long n) {
        this.put(s, Long.toString(n));
    }
    
    @Override
    public long getLong(final String s, final long n) {
        long long1 = n;
        try {
            final String value = this.get(s, null);
            if (value != null) {
                long1 = Long.parseLong(value);
            }
        }
        catch (NumberFormatException ex) {}
        return long1;
    }
    
    @Override
    public void putBoolean(final String s, final boolean b) {
        this.put(s, String.valueOf(b));
    }
    
    @Override
    public boolean getBoolean(final String s, final boolean b) {
        boolean b2 = b;
        final String value = this.get(s, null);
        if (value != null) {
            if (value.equalsIgnoreCase("true")) {
                b2 = true;
            }
            else if (value.equalsIgnoreCase("false")) {
                b2 = false;
            }
        }
        return b2;
    }
    
    @Override
    public void putFloat(final String s, final float n) {
        this.put(s, Float.toString(n));
    }
    
    @Override
    public float getFloat(final String s, final float n) {
        float float1 = n;
        try {
            final String value = this.get(s, null);
            if (value != null) {
                float1 = Float.parseFloat(value);
            }
        }
        catch (NumberFormatException ex) {}
        return float1;
    }
    
    @Override
    public void putDouble(final String s, final double n) {
        this.put(s, Double.toString(n));
    }
    
    @Override
    public double getDouble(final String s, final double n) {
        double double1 = n;
        try {
            final String value = this.get(s, null);
            if (value != null) {
                double1 = Double.parseDouble(value);
            }
        }
        catch (NumberFormatException ex) {}
        return double1;
    }
    
    @Override
    public void putByteArray(final String s, final byte[] array) {
        this.put(s, Base64.byteArrayToBase64(array));
    }
    
    @Override
    public byte[] getByteArray(final String s, final byte[] array) {
        byte[] base64ToByteArray = array;
        final String value = this.get(s, null);
        try {
            if (value != null) {
                base64ToByteArray = Base64.base64ToByteArray(value);
            }
        }
        catch (RuntimeException ex) {}
        return base64ToByteArray;
    }
    
    @Override
    public String[] keys() throws BackingStoreException {
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            return this.keysSpi();
        }
    }
    
    @Override
    public String[] childrenNames() throws BackingStoreException {
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            final TreeSet<String> set = new TreeSet<String>(this.kidCache.keySet());
            final String[] childrenNamesSpi = this.childrenNamesSpi();
            for (int length = childrenNamesSpi.length, i = 0; i < length; ++i) {
                set.add(childrenNamesSpi[i]);
            }
            return set.toArray(AbstractPreferences.EMPTY_STRING_ARRAY);
        }
    }
    
    protected final AbstractPreferences[] cachedChildren() {
        return this.kidCache.values().toArray(AbstractPreferences.EMPTY_ABSTRACT_PREFS_ARRAY);
    }
    
    @Override
    public Preferences parent() {
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            return this.parent;
        }
    }
    
    @Override
    public Preferences node(final String s) {
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            if (s.equals("")) {
                return this;
            }
            if (s.equals("/")) {
                return this.root;
            }
            if (s.charAt(0) != '/') {
                return this.node(new StringTokenizer(s, "/", true));
            }
        }
        return this.root.node(new StringTokenizer(s.substring(1), "/", true));
    }
    
    private Preferences node(final StringTokenizer stringTokenizer) {
        final String nextToken = stringTokenizer.nextToken();
        if (nextToken.equals("/")) {
            throw new IllegalArgumentException("Consecutive slashes in path");
        }
        synchronized (this.lock) {
            AbstractPreferences childSpi = this.kidCache.get(nextToken);
            if (childSpi == null) {
                if (nextToken.length() > 80) {
                    throw new IllegalArgumentException("Node name " + nextToken + " too long");
                }
                childSpi = this.childSpi(nextToken);
                if (childSpi.newNode) {
                    this.enqueueNodeAddedEvent(childSpi);
                }
                this.kidCache.put(nextToken, childSpi);
            }
            if (!stringTokenizer.hasMoreTokens()) {
                return childSpi;
            }
            stringTokenizer.nextToken();
            if (!stringTokenizer.hasMoreTokens()) {
                throw new IllegalArgumentException("Path ends with slash");
            }
            return childSpi.node(stringTokenizer);
        }
    }
    
    @Override
    public boolean nodeExists(final String s) throws BackingStoreException {
        synchronized (this.lock) {
            if (s.equals("")) {
                return !this.removed;
            }
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            if (s.equals("/")) {
                return true;
            }
            if (s.charAt(0) != '/') {
                return this.nodeExists(new StringTokenizer(s, "/", true));
            }
        }
        return this.root.nodeExists(new StringTokenizer(s.substring(1), "/", true));
    }
    
    private boolean nodeExists(final StringTokenizer stringTokenizer) throws BackingStoreException {
        final String nextToken = stringTokenizer.nextToken();
        if (nextToken.equals("/")) {
            throw new IllegalArgumentException("Consecutive slashes in path");
        }
        synchronized (this.lock) {
            AbstractPreferences child = this.kidCache.get(nextToken);
            if (child == null) {
                child = this.getChild(nextToken);
            }
            if (child == null) {
                return false;
            }
            if (!stringTokenizer.hasMoreTokens()) {
                return true;
            }
            stringTokenizer.nextToken();
            if (!stringTokenizer.hasMoreTokens()) {
                throw new IllegalArgumentException("Path ends with slash");
            }
            return child.nodeExists(stringTokenizer);
        }
    }
    
    @Override
    public void removeNode() throws BackingStoreException {
        if (this == this.root) {
            throw new UnsupportedOperationException("Can't remove the root!");
        }
        synchronized (this.parent.lock) {
            this.removeNode2();
            this.parent.kidCache.remove(this.name);
        }
    }
    
    private void removeNode2() throws BackingStoreException {
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node already removed.");
            }
            final String[] childrenNamesSpi = this.childrenNamesSpi();
            for (int i = 0; i < childrenNamesSpi.length; ++i) {
                if (!this.kidCache.containsKey(childrenNamesSpi[i])) {
                    this.kidCache.put(childrenNamesSpi[i], this.childSpi(childrenNamesSpi[i]));
                }
            }
            final Iterator<AbstractPreferences> iterator = this.kidCache.values().iterator();
            while (iterator.hasNext()) {
                try {
                    iterator.next().removeNode2();
                    iterator.remove();
                }
                catch (BackingStoreException ex) {}
            }
            this.removeNodeSpi();
            this.removed = true;
            this.parent.enqueueNodeRemovedEvent(this);
        }
    }
    
    @Override
    public String name() {
        return this.name;
    }
    
    @Override
    public String absolutePath() {
        return this.absolutePath;
    }
    
    @Override
    public boolean isUserNode() {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                return AbstractPreferences.this.root == Preferences.userRoot();
            }
        });
    }
    
    @Override
    public void addPreferenceChangeListener(final PreferenceChangeListener preferenceChangeListener) {
        if (preferenceChangeListener == null) {
            throw new NullPointerException("Change listener is null.");
        }
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            final PreferenceChangeListener[] prefListeners = this.prefListeners;
            System.arraycopy(prefListeners, 0, this.prefListeners = new PreferenceChangeListener[prefListeners.length + 1], 0, prefListeners.length);
            this.prefListeners[prefListeners.length] = preferenceChangeListener;
        }
        startEventDispatchThreadIfNecessary();
    }
    
    @Override
    public void removePreferenceChangeListener(final PreferenceChangeListener preferenceChangeListener) {
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            if (this.prefListeners == null || this.prefListeners.length == 0) {
                throw new IllegalArgumentException("Listener not registered.");
            }
            PreferenceChangeListener[] prefListeners;
            int i;
            for (prefListeners = new PreferenceChangeListener[this.prefListeners.length - 1], i = 0; i < prefListeners.length && this.prefListeners[i] != preferenceChangeListener; prefListeners[i] = this.prefListeners[i++]) {}
            if (i == prefListeners.length && this.prefListeners[i] != preferenceChangeListener) {
                throw new IllegalArgumentException("Listener not registered.");
            }
            while (i < prefListeners.length) {
                prefListeners[i] = this.prefListeners[++i];
            }
            this.prefListeners = prefListeners;
        }
    }
    
    @Override
    public void addNodeChangeListener(final NodeChangeListener nodeChangeListener) {
        if (nodeChangeListener == null) {
            throw new NullPointerException("Change listener is null.");
        }
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            if (this.nodeListeners == null) {
                (this.nodeListeners = new NodeChangeListener[1])[0] = nodeChangeListener;
            }
            else {
                final NodeChangeListener[] nodeListeners = this.nodeListeners;
                System.arraycopy(nodeListeners, 0, this.nodeListeners = new NodeChangeListener[nodeListeners.length + 1], 0, nodeListeners.length);
                this.nodeListeners[nodeListeners.length] = nodeChangeListener;
            }
        }
        startEventDispatchThreadIfNecessary();
    }
    
    @Override
    public void removeNodeChangeListener(final NodeChangeListener nodeChangeListener) {
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed.");
            }
            if (this.nodeListeners == null || this.nodeListeners.length == 0) {
                throw new IllegalArgumentException("Listener not registered.");
            }
            int n;
            for (n = 0; n < this.nodeListeners.length && this.nodeListeners[n] != nodeChangeListener; ++n) {}
            if (n == this.nodeListeners.length) {
                throw new IllegalArgumentException("Listener not registered.");
            }
            final NodeChangeListener[] nodeListeners = new NodeChangeListener[this.nodeListeners.length - 1];
            if (n != 0) {
                System.arraycopy(this.nodeListeners, 0, nodeListeners, 0, n);
            }
            if (n != nodeListeners.length) {
                System.arraycopy(this.nodeListeners, n + 1, nodeListeners, n, nodeListeners.length - n);
            }
            this.nodeListeners = nodeListeners;
        }
    }
    
    protected abstract void putSpi(final String p0, final String p1);
    
    protected abstract String getSpi(final String p0);
    
    protected abstract void removeSpi(final String p0);
    
    protected abstract void removeNodeSpi() throws BackingStoreException;
    
    protected abstract String[] keysSpi() throws BackingStoreException;
    
    protected abstract String[] childrenNamesSpi() throws BackingStoreException;
    
    protected AbstractPreferences getChild(final String s) throws BackingStoreException {
        synchronized (this.lock) {
            final String[] childrenNames = this.childrenNames();
            for (int i = 0; i < childrenNames.length; ++i) {
                if (childrenNames[i].equals(s)) {
                    return this.childSpi(childrenNames[i]);
                }
            }
        }
        return null;
    }
    
    protected abstract AbstractPreferences childSpi(final String p0);
    
    @Override
    public String toString() {
        return (this.isUserNode() ? "User" : "System") + " Preference Node: " + this.absolutePath();
    }
    
    @Override
    public void sync() throws BackingStoreException {
        this.sync2();
    }
    
    private void sync2() throws BackingStoreException {
        final AbstractPreferences[] cachedChildren;
        synchronized (this.lock) {
            if (this.removed) {
                throw new IllegalStateException("Node has been removed");
            }
            this.syncSpi();
            cachedChildren = this.cachedChildren();
        }
        for (int i = 0; i < cachedChildren.length; ++i) {
            cachedChildren[i].sync2();
        }
    }
    
    protected abstract void syncSpi() throws BackingStoreException;
    
    @Override
    public void flush() throws BackingStoreException {
        this.flush2();
    }
    
    private void flush2() throws BackingStoreException {
        final AbstractPreferences[] cachedChildren;
        synchronized (this.lock) {
            this.flushSpi();
            if (this.removed) {
                return;
            }
            cachedChildren = this.cachedChildren();
        }
        for (int i = 0; i < cachedChildren.length; ++i) {
            cachedChildren[i].flush2();
        }
    }
    
    protected abstract void flushSpi() throws BackingStoreException;
    
    protected boolean isRemoved() {
        synchronized (this.lock) {
            return this.removed;
        }
    }
    
    private static synchronized void startEventDispatchThreadIfNecessary() {
        if (AbstractPreferences.eventDispatchThread == null) {
            (AbstractPreferences.eventDispatchThread = new EventDispatchThread()).setDaemon(true);
            AbstractPreferences.eventDispatchThread.start();
        }
    }
    
    PreferenceChangeListener[] prefListeners() {
        synchronized (this.lock) {
            return this.prefListeners;
        }
    }
    
    NodeChangeListener[] nodeListeners() {
        synchronized (this.lock) {
            return this.nodeListeners;
        }
    }
    
    private void enqueuePreferenceChangeEvent(final String s, final String s2) {
        if (this.prefListeners.length != 0) {
            synchronized (AbstractPreferences.eventQueue) {
                AbstractPreferences.eventQueue.add(new PreferenceChangeEvent(this, s, s2));
                AbstractPreferences.eventQueue.notify();
            }
        }
    }
    
    private void enqueueNodeAddedEvent(final Preferences preferences) {
        if (this.nodeListeners.length != 0) {
            synchronized (AbstractPreferences.eventQueue) {
                AbstractPreferences.eventQueue.add(new NodeAddedEvent(this, preferences));
                AbstractPreferences.eventQueue.notify();
            }
        }
    }
    
    private void enqueueNodeRemovedEvent(final Preferences preferences) {
        if (this.nodeListeners.length != 0) {
            synchronized (AbstractPreferences.eventQueue) {
                AbstractPreferences.eventQueue.add(new NodeRemovedEvent(this, preferences));
                AbstractPreferences.eventQueue.notify();
            }
        }
    }
    
    @Override
    public void exportNode(final OutputStream outputStream) throws IOException, BackingStoreException {
        XmlSupport.export(outputStream, this, false);
    }
    
    @Override
    public void exportSubtree(final OutputStream outputStream) throws IOException, BackingStoreException {
        XmlSupport.export(outputStream, this, true);
    }
    
    static {
        EMPTY_STRING_ARRAY = new String[0];
        EMPTY_ABSTRACT_PREFS_ARRAY = new AbstractPreferences[0];
        eventQueue = new LinkedList<EventObject>();
        AbstractPreferences.eventDispatchThread = null;
    }
    
    private static class EventDispatchThread extends Thread
    {
        @Override
        public void run() {
            while (true) {
                EventObject eventObject = null;
                synchronized (AbstractPreferences.eventQueue) {
                    try {
                        while (AbstractPreferences.eventQueue.isEmpty()) {
                            AbstractPreferences.eventQueue.wait();
                        }
                        eventObject = AbstractPreferences.eventQueue.remove(0);
                    }
                    catch (InterruptedException ex) {
                        return;
                    }
                }
                final AbstractPreferences abstractPreferences = (AbstractPreferences)eventObject.getSource();
                if (eventObject instanceof PreferenceChangeEvent) {
                    final PreferenceChangeEvent preferenceChangeEvent = (PreferenceChangeEvent)eventObject;
                    final PreferenceChangeListener[] prefListeners = abstractPreferences.prefListeners();
                    for (int i = 0; i < prefListeners.length; ++i) {
                        prefListeners[i].preferenceChange(preferenceChangeEvent);
                    }
                }
                else {
                    final NodeChangeEvent nodeChangeEvent = (NodeChangeEvent)eventObject;
                    final NodeChangeListener[] nodeListeners = abstractPreferences.nodeListeners();
                    if (nodeChangeEvent instanceof NodeAddedEvent) {
                        for (int j = 0; j < nodeListeners.length; ++j) {
                            nodeListeners[j].childAdded(nodeChangeEvent);
                        }
                    }
                    else {
                        for (int k = 0; k < nodeListeners.length; ++k) {
                            nodeListeners[k].childRemoved(nodeChangeEvent);
                        }
                    }
                }
            }
        }
    }
    
    private class NodeAddedEvent extends NodeChangeEvent
    {
        private static final long serialVersionUID = -6743557530157328528L;
        
        NodeAddedEvent(final Preferences preferences, final Preferences preferences2) {
            super(preferences, preferences2);
        }
    }
    
    private class NodeRemovedEvent extends NodeChangeEvent
    {
        private static final long serialVersionUID = 8735497392918824837L;
        
        NodeRemovedEvent(final Preferences preferences, final Preferences preferences2) {
            super(preferences, preferences2);
        }
    }
}
