package java.util;

import java.io.*;

public class InvalidPropertiesFormatException extends IOException
{
    private static final long serialVersionUID = 7763056076009360219L;
    
    public InvalidPropertiesFormatException(final Throwable t) {
        super((t == null) ? null : t.toString());
        this.initCause(t);
    }
    
    public InvalidPropertiesFormatException(final String s) {
        super(s);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws NotSerializableException {
        throw new NotSerializableException("Not serializable.");
    }
}
