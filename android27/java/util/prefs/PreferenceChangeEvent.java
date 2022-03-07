package java.util.prefs;

import java.util.*;
import java.io.*;

public class PreferenceChangeEvent extends EventObject
{
    private String key;
    private String newValue;
    private static final long serialVersionUID = 793724513368024975L;
    
    public PreferenceChangeEvent(final Preferences preferences, final String key, final String newValue) {
        super(preferences);
        this.key = key;
        this.newValue = newValue;
    }
    
    public Preferences getNode() {
        return (Preferences)this.getSource();
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getNewValue() {
        return this.newValue;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }
}
