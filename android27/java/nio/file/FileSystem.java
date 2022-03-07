package java.nio.file;

import java.nio.file.spi.*;
import java.io.*;
import java.util.*;
import java.nio.file.attribute.*;

public abstract class FileSystem implements Closeable
{
    public abstract FileSystemProvider provider();
    
    @Override
    public abstract void close() throws IOException;
    
    public abstract boolean isOpen();
    
    public abstract boolean isReadOnly();
    
    public abstract String getSeparator();
    
    public abstract Iterable<Path> getRootDirectories();
    
    public abstract Iterable<FileStore> getFileStores();
    
    public abstract Set<String> supportedFileAttributeViews();
    
    public abstract Path getPath(final String p0, final String... p1);
    
    public abstract PathMatcher getPathMatcher(final String p0);
    
    public abstract UserPrincipalLookupService getUserPrincipalLookupService();
    
    public abstract WatchService newWatchService() throws IOException;
}
