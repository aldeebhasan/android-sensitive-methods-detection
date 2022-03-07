package java.nio.file.spi;

import java.security.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public abstract class FileSystemProvider
{
    private static final Object lock;
    private static volatile List<FileSystemProvider> installedProviders;
    private static boolean loadingProviders;
    
    private static Void checkPermission() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("fileSystemProvider"));
        }
        return null;
    }
    
    private FileSystemProvider(final Void void1) {
    }
    
    protected FileSystemProvider() {
        this(checkPermission());
    }
    
    private static List<FileSystemProvider> loadInstalledProviders() {
        final ArrayList<FileSystemProvider> list = new ArrayList<FileSystemProvider>();
        for (final FileSystemProvider fileSystemProvider : ServiceLoader.load(FileSystemProvider.class, ClassLoader.getSystemClassLoader())) {
            final String scheme = fileSystemProvider.getScheme();
            if (!scheme.equalsIgnoreCase("file")) {
                boolean b = false;
                final Iterator<Object> iterator2 = list.iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next().getScheme().equalsIgnoreCase(scheme)) {
                        b = true;
                        break;
                    }
                }
                if (b) {
                    continue;
                }
                list.add(fileSystemProvider);
            }
        }
        return list;
    }
    
    public static List<FileSystemProvider> installedProviders() {
        if (FileSystemProvider.installedProviders == null) {
            final FileSystemProvider provider = FileSystems.getDefault().provider();
            synchronized (FileSystemProvider.lock) {
                if (FileSystemProvider.installedProviders == null) {
                    if (FileSystemProvider.loadingProviders) {
                        throw new Error("Circular loading of installed providers detected");
                    }
                    FileSystemProvider.loadingProviders = true;
                    final List<FileSystemProvider> list = AccessController.doPrivileged((PrivilegedAction<List<FileSystemProvider>>)new PrivilegedAction<List<FileSystemProvider>>() {
                        @Override
                        public List<FileSystemProvider> run() {
                            return loadInstalledProviders();
                        }
                    });
                    list.add(0, provider);
                    FileSystemProvider.installedProviders = (List<FileSystemProvider>)Collections.unmodifiableList((List<?>)list);
                }
            }
        }
        return FileSystemProvider.installedProviders;
    }
    
    public abstract String getScheme();
    
    public abstract FileSystem newFileSystem(final URI p0, final Map<String, ?> p1) throws IOException;
    
    public abstract FileSystem getFileSystem(final URI p0);
    
    public abstract Path getPath(final URI p0);
    
    public FileSystem newFileSystem(final Path path, final Map<String, ?> map) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    public InputStream newInputStream(final Path path, final OpenOption... array) throws IOException {
        if (array.length > 0) {
            for (final OpenOption openOption : array) {
                if (openOption == StandardOpenOption.APPEND || openOption == StandardOpenOption.WRITE) {
                    throw new UnsupportedOperationException("'" + openOption + "' not allowed");
                }
            }
        }
        return Channels.newInputStream(Files.newByteChannel(path, array));
    }
    
    public OutputStream newOutputStream(final Path path, final OpenOption... array) throws IOException {
        final int length = array.length;
        final HashSet set = new HashSet<StandardOpenOption>(length + 3);
        if (length == 0) {
            set.add(StandardOpenOption.CREATE);
            set.add(StandardOpenOption.TRUNCATE_EXISTING);
        }
        else {
            for (final OpenOption openOption : array) {
                if (openOption == StandardOpenOption.READ) {
                    throw new IllegalArgumentException("READ not allowed");
                }
                set.add((StandardOpenOption)openOption);
            }
        }
        set.add(StandardOpenOption.WRITE);
        return Channels.newOutputStream(this.newByteChannel(path, (Set<? extends OpenOption>)set, (FileAttribute<?>[])new FileAttribute[0]));
    }
    
    public FileChannel newFileChannel(final Path path, final Set<? extends OpenOption> set, final FileAttribute<?>... array) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    public AsynchronousFileChannel newAsynchronousFileChannel(final Path path, final Set<? extends OpenOption> set, final ExecutorService executorService, final FileAttribute<?>... array) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    public abstract SeekableByteChannel newByteChannel(final Path p0, final Set<? extends OpenOption> p1, final FileAttribute<?>... p2) throws IOException;
    
    public abstract DirectoryStream<Path> newDirectoryStream(final Path p0, final DirectoryStream.Filter<? super Path> p1) throws IOException;
    
    public abstract void createDirectory(final Path p0, final FileAttribute<?>... p1) throws IOException;
    
    public void createSymbolicLink(final Path path, final Path path2, final FileAttribute<?>... array) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    public void createLink(final Path path, final Path path2) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    public abstract void delete(final Path p0) throws IOException;
    
    public boolean deleteIfExists(final Path path) throws IOException {
        try {
            this.delete(path);
            return true;
        }
        catch (NoSuchFileException ex) {
            return false;
        }
    }
    
    public Path readSymbolicLink(final Path path) throws IOException {
        throw new UnsupportedOperationException();
    }
    
    public abstract void copy(final Path p0, final Path p1, final CopyOption... p2) throws IOException;
    
    public abstract void move(final Path p0, final Path p1, final CopyOption... p2) throws IOException;
    
    public abstract boolean isSameFile(final Path p0, final Path p1) throws IOException;
    
    public abstract boolean isHidden(final Path p0) throws IOException;
    
    public abstract FileStore getFileStore(final Path p0) throws IOException;
    
    public abstract void checkAccess(final Path p0, final AccessMode... p1) throws IOException;
    
    public abstract <V extends FileAttributeView> V getFileAttributeView(final Path p0, final Class<V> p1, final LinkOption... p2);
    
    public abstract <A extends BasicFileAttributes> A readAttributes(final Path p0, final Class<A> p1, final LinkOption... p2) throws IOException;
    
    public abstract Map<String, Object> readAttributes(final Path p0, final String p1, final LinkOption... p2) throws IOException;
    
    public abstract void setAttribute(final Path p0, final String p1, final Object p2, final LinkOption... p3) throws IOException;
    
    static {
        lock = new Object();
        FileSystemProvider.loadingProviders = false;
    }
}
