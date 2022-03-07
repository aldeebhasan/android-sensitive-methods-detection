package java.util.prefs;

import java.util.*;
import java.io.*;

public class NodeChangeEvent extends EventObject
{
    private Preferences child;
    private static final long serialVersionUID = 8068949086596572957L;
    
    public NodeChangeEvent(final Preferences preferences, final Preferences child) {
        super(preferences);
        this.child = child;
    }
    
    public Preferences getParent() {
        return (Preferences)this.getSource();
    }
    
    public Preferences getChild() {
        return this.child;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }
}
