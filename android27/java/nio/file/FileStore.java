package java.nio.file;

import java.io.*;
import java.nio.file.attribute.*;

public abstract class FileStore
{
    public abstract String name();
    
    public abstract String type();
    
    public abstract boolean isReadOnly();
    
    public abstract long getTotalSpace() throws IOException;
    
    public abstract long getUsableSpace() throws IOException;
    
    public abstract long getUnallocatedSpace() throws IOException;
    
    public abstract boolean supportsFileAttributeView(final Class<? extends FileAttributeView> p0);
    
    public abstract boolean supportsFileAttributeView(final String p0);
    
    public abstract <V extends FileStoreAttributeView> V getFileStoreAttributeView(final Class<V> p0);
    
    public abstract Object getAttribute(final String p0) throws IOException;
}
