package java.beans;

import java.io.*;
import java.util.*;

public class PropertyChangeSupport implements Serializable
{
    private PropertyChangeListenerMap map;
    private Object source;
    private static final ObjectStreamField[] serialPersistentFields;
    static final long serialVersionUID = 6401253773779951803L;
    
    public PropertyChangeSupport(final Object source) {
        this.map = new PropertyChangeListenerMap();
        if (source == null) {
            throw new NullPointerException();
        }
        this.source = source;
    }
    
    public void addPropertyChangeListener(final PropertyChangeListener propertyChangeListener) {
        if (propertyChangeListener == null) {
            return;
        }
        if (propertyChangeListener instanceof PropertyChangeListenerProxy) {
            final PropertyChangeListenerProxy propertyChangeListenerProxy = (PropertyChangeListenerProxy)propertyChangeListener;
            this.addPropertyChangeListener(propertyChangeListenerProxy.getPropertyName(), propertyChangeListenerProxy.getListener());
        }
        else {
            this.map.add(null, propertyChangeListener);
        }
    }
    
    public void removePropertyChangeListener(final PropertyChangeListener propertyChangeListener) {
        if (propertyChangeListener == null) {
            return;
        }
        if (propertyChangeListener instanceof PropertyChangeListenerProxy) {
            final PropertyChangeListenerProxy propertyChangeListenerProxy = (PropertyChangeListenerProxy)propertyChangeListener;
            this.removePropertyChangeListener(propertyChangeListenerProxy.getPropertyName(), propertyChangeListenerProxy.getListener());
        }
        else {
            this.map.remove(null, propertyChangeListener);
        }
    }
    
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return this.map.getListeners();
    }
    
    public void addPropertyChangeListener(final String s, PropertyChangeListener extract) {
        if (extract == null || s == null) {
            return;
        }
        extract = this.map.extract(extract);
        if (extract != null) {
            this.map.add(s, extract);
        }
    }
    
    public void removePropertyChangeListener(final String s, PropertyChangeListener extract) {
        if (extract == null || s == null) {
            return;
        }
        extract = this.map.extract(extract);
        if (extract != null) {
            this.map.remove(s, extract);
        }
    }
    
    public PropertyChangeListener[] getPropertyChangeListeners(final String s) {
        return this.map.getListeners(s);
    }
    
    public void firePropertyChange(final String s, final Object o, final Object o2) {
        if (o == null || o2 == null || !o.equals(o2)) {
            this.firePropertyChange(new PropertyChangeEvent(this.source, s, o, o2));
        }
    }
    
    public void firePropertyChange(final String s, final int n, final int n2) {
        if (n != n2) {
            this.firePropertyChange(s, n, (Object)n2);
        }
    }
    
    public void firePropertyChange(final String s, final boolean b, final boolean b2) {
        if (b != b2) {
            this.firePropertyChange(s, b, (Object)b2);
        }
    }
    
    public void firePropertyChange(final PropertyChangeEvent propertyChangeEvent) {
        final Object oldValue = propertyChangeEvent.getOldValue();
        final Object newValue = propertyChangeEvent.getNewValue();
        if (oldValue == null || newValue == null || !oldValue.equals(newValue)) {
            final String propertyName = propertyChangeEvent.getPropertyName();
            final PropertyChangeListener[] array = this.map.get(null);
            final PropertyChangeListener[] array2 = (PropertyChangeListener[])((propertyName != null) ? ((PropertyChangeListener[])this.map.get(propertyName)) : null);
            fire(array, propertyChangeEvent);
            fire(array2, propertyChangeEvent);
        }
    }
    
    private static void fire(final PropertyChangeListener[] array, final PropertyChangeEvent propertyChangeEvent) {
        if (array != null) {
            for (int length = array.length, i = 0; i < length; ++i) {
                array[i].propertyChange(propertyChangeEvent);
            }
        }
    }
    
    public void fireIndexedPropertyChange(final String s, final int n, final Object o, final Object o2) {
        if (o == null || o2 == null || !o.equals(o2)) {
            this.firePropertyChange(new IndexedPropertyChangeEvent(this.source, s, o, o2, n));
        }
    }
    
    public void fireIndexedPropertyChange(final String s, final int n, final int n2, final int n3) {
        if (n2 != n3) {
            this.fireIndexedPropertyChange(s, n, n2, (Object)n3);
        }
    }
    
    public void fireIndexedPropertyChange(final String s, final int n, final boolean b, final boolean b2) {
        if (b != b2) {
            this.fireIndexedPropertyChange(s, n, b, (Object)b2);
        }
    }
    
    public boolean hasListeners(final String s) {
        return this.map.hasListeners(s);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        Hashtable<String, PropertyChangeSupport> hashtable = null;
        PropertyChangeListener[] array = null;
        synchronized (this.map) {
            for (final Map.Entry<String, PropertyChangeListener[]> entry : this.map.getEntries()) {
                final String s = entry.getKey();
                if (s == null) {
                    array = entry.getValue();
                }
                else {
                    if (hashtable == null) {
                        hashtable = new Hashtable<String, PropertyChangeSupport>();
                    }
                    final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this.source);
                    propertyChangeSupport.map.set(null, entry.getValue());
                    hashtable.put(s, propertyChangeSupport);
                }
            }
        }
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("children", hashtable);
        putFields.put("source", this.source);
        putFields.put("propertyChangeSupportSerializedDataVersion", 2);
        objectOutputStream.writeFields();
        if (array != null) {
            for (final PropertyChangeListener propertyChangeListener : array) {
                if (propertyChangeListener instanceof Serializable) {
                    objectOutputStream.writeObject(propertyChangeListener);
                }
            }
        }
        objectOutputStream.writeObject(null);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        this.map = new PropertyChangeListenerMap();
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        final Hashtable hashtable = (Hashtable)fields.get("children", null);
        this.source = fields.get("source", null);
        fields.get("propertyChangeSupportSerializedDataVersion", 2);
        Object object;
        while (null != (object = objectInputStream.readObject())) {
            this.map.add(null, (PropertyChangeListener)object);
        }
        if (hashtable != null) {
            for (final Map.Entry<K, PropertyChangeSupport> entry : hashtable.entrySet()) {
                final PropertyChangeListener[] propertyChangeListeners = entry.getValue().getPropertyChangeListeners();
                for (int length = propertyChangeListeners.length, i = 0; i < length; ++i) {
                    this.map.add((String)entry.getKey(), propertyChangeListeners[i]);
                }
            }
        }
    }
    
    static {
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("children", Hashtable.class), new ObjectStreamField("source", Object.class), new ObjectStreamField("propertyChangeSupportSerializedDataVersion", Integer.TYPE) };
    }
    
    private static final class PropertyChangeListenerMap extends ChangeListenerMap<PropertyChangeListener>
    {
        private static final PropertyChangeListener[] EMPTY;
        
        @Override
        protected PropertyChangeListener[] newArray(final int n) {
            return (0 < n) ? new PropertyChangeListener[n] : PropertyChangeListenerMap.EMPTY;
        }
        
        @Override
        protected PropertyChangeListener newProxy(final String s, final PropertyChangeListener propertyChangeListener) {
            return new PropertyChangeListenerProxy(s, propertyChangeListener);
        }
        
        @Override
        public final PropertyChangeListener extract(PropertyChangeListener propertyChangeListener) {
            while (propertyChangeListener instanceof PropertyChangeListenerProxy) {
                propertyChangeListener = ((PropertyChangeListenerProxy)propertyChangeListener).getListener();
            }
            return propertyChangeListener;
        }
        
        static {
            EMPTY = new PropertyChangeListener[0];
        }
    }
}
