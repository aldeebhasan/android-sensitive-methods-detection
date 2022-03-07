package java.security;

import java.io.*;

public class GuardedObject implements Serializable
{
    private static final long serialVersionUID = -5240450096227834308L;
    private Object object;
    private Guard guard;
    
    public GuardedObject(final Object object, final Guard guard) {
        this.guard = guard;
        this.object = object;
    }
    
    public Object getObject() throws SecurityException {
        if (this.guard != null) {
            this.guard.checkGuard(this.object);
        }
        return this.object;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.guard != null) {
            this.guard.checkGuard(this.object);
        }
        objectOutputStream.defaultWriteObject();
    }
}
