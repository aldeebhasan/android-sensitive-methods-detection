package java.nio.file;

import java.util.*;
import java.io.*;

public final class DirectoryIteratorException extends ConcurrentModificationException
{
    private static final long serialVersionUID = -6012699886086212874L;
    
    public DirectoryIteratorException(final IOException ex) {
        super(Objects.requireNonNull(ex));
    }
    
    @Override
    public IOException getCause() {
        return (IOException)super.getCause();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (!(super.getCause() instanceof IOException)) {
            throw new InvalidObjectException("Cause must be an IOException");
        }
    }
}
