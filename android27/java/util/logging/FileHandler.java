package java.util.logging;

import java.io.*;
import java.nio.file.*;
import java.nio.channels.*;
import java.security.*;
import java.util.*;

public class FileHandler extends StreamHandler
{
    private MeteredStream meter;
    private boolean append;
    private int limit;
    private int count;
    private String pattern;
    private String lockFileName;
    private FileChannel lockFileChannel;
    private File[] files;
    private static final int DEFAULT_MAX_LOCKS = 100;
    private static int maxLocks;
    private static final Set<String> locks;
    
    private void open(final File file, final boolean b) throws IOException {
        int n = 0;
        if (b) {
            n = (int)file.length();
        }
        this.setOutputStream(this.meter = new MeteredStream(new BufferedOutputStream(new FileOutputStream(file.toString(), b)), n));
    }
    
    private void configure() {
        final LogManager logManager = LogManager.getLogManager();
        final String name = this.getClass().getName();
        this.pattern = logManager.getStringProperty(name + ".pattern", "%h/java%u.log");
        this.limit = logManager.getIntProperty(name + ".limit", 0);
        if (this.limit < 0) {
            this.limit = 0;
        }
        this.count = logManager.getIntProperty(name + ".count", 1);
        if (this.count <= 0) {
            this.count = 1;
        }
        this.append = logManager.getBooleanProperty(name + ".append", false);
        this.setLevel(logManager.getLevelProperty(name + ".level", Level.ALL));
        this.setFilter(logManager.getFilterProperty(name + ".filter", null));
        this.setFormatter(logManager.getFormatterProperty(name + ".formatter", new XMLFormatter()));
        try {
            this.setEncoding(logManager.getStringProperty(name + ".encoding", null));
        }
        catch (Exception ex) {
            try {
                this.setEncoding(null);
            }
            catch (Exception ex2) {}
        }
    }
    
    public FileHandler() throws IOException, SecurityException {
        this.checkPermission();
        this.configure();
        this.openFiles();
    }
    
    public FileHandler(final String pattern) throws IOException, SecurityException {
        if (pattern.length() < 1) {
            throw new IllegalArgumentException();
        }
        this.checkPermission();
        this.configure();
        this.pattern = pattern;
        this.limit = 0;
        this.count = 1;
        this.openFiles();
    }
    
    public FileHandler(final String pattern, final boolean append) throws IOException, SecurityException {
        if (pattern.length() < 1) {
            throw new IllegalArgumentException();
        }
        this.checkPermission();
        this.configure();
        this.pattern = pattern;
        this.limit = 0;
        this.count = 1;
        this.append = append;
        this.openFiles();
    }
    
    public FileHandler(final String pattern, final int limit, final int count) throws IOException, SecurityException {
        if (limit < 0 || count < 1 || pattern.length() < 1) {
            throw new IllegalArgumentException();
        }
        this.checkPermission();
        this.configure();
        this.pattern = pattern;
        this.limit = limit;
        this.count = count;
        this.openFiles();
    }
    
    public FileHandler(final String pattern, final int limit, final int count, final boolean append) throws IOException, SecurityException {
        if (limit < 0 || count < 1 || pattern.length() < 1) {
            throw new IllegalArgumentException();
        }
        this.checkPermission();
        this.configure();
        this.pattern = pattern;
        this.limit = limit;
        this.count = count;
        this.append = append;
        this.openFiles();
    }
    
    private boolean isParentWritable(final Path path) {
        Path path2 = path.getParent();
        if (path2 == null) {
            path2 = path.toAbsolutePath().getParent();
        }
        return path2 != null && Files.isWritable(path2);
    }
    
    private void openFiles() throws IOException {
        LogManager.getLogManager().checkPermission();
        if (this.count < 1) {
            throw new IllegalArgumentException("file count = " + this.count);
        }
        if (this.limit < 0) {
            this.limit = 0;
        }
        final InitializationErrorManager errorManager = new InitializationErrorManager();
        this.setErrorManager(errorManager);
        int n = -1;
        while (++n <= FileHandler.maxLocks) {
            this.lockFileName = this.generate(this.pattern, 0, n).toString() + ".lck";
            Label_0423: {
                synchronized (FileHandler.locks) {
                    if (FileHandler.locks.contains(this.lockFileName)) {
                        continue;
                    }
                    final Path value = Paths.get(this.lockFileName, new String[0]);
                    FileChannel lockFileChannel = null;
                    int n2 = -1;
                    boolean b = false;
                    while (lockFileChannel == null && n2++ < 1) {
                        try {
                            lockFileChannel = FileChannel.open(value, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
                            b = true;
                        }
                        catch (FileAlreadyExistsException ex) {
                            if (Files.isRegularFile(value, LinkOption.NOFOLLOW_LINKS) && this.isParentWritable(value)) {
                                try {
                                    lockFileChannel = FileChannel.open(value, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
                                    continue;
                                }
                                catch (NoSuchFileException ex2) {
                                    continue;
                                }
                                catch (IOException ex3) {}
                                break;
                                continue;
                            }
                            break;
                        }
                    }
                    if (lockFileChannel == null) {
                        continue;
                    }
                    this.lockFileChannel = lockFileChannel;
                    boolean b2;
                    try {
                        b2 = (this.lockFileChannel.tryLock() != null);
                    }
                    catch (IOException ex4) {
                        b2 = b;
                    }
                    catch (OverlappingFileLockException ex5) {
                        b2 = false;
                    }
                    if (b2) {
                        FileHandler.locks.add(this.lockFileName);
                        break Label_0423;
                    }
                    this.lockFileChannel.close();
                }
                continue;
            }
            this.files = new File[this.count];
            for (int i = 0; i < this.count; ++i) {
                this.files[i] = this.generate(this.pattern, i, n);
            }
            if (this.append) {
                this.open(this.files[0], true);
            }
            else {
                this.rotate();
            }
            final Exception lastException = errorManager.lastException;
            if (lastException == null) {
                this.setErrorManager(new ErrorManager());
                return;
            }
            if (lastException instanceof IOException) {
                throw (IOException)lastException;
            }
            if (lastException instanceof SecurityException) {
                throw (SecurityException)lastException;
            }
            throw new IOException("Exception: " + lastException);
        }
        throw new IOException("Couldn't get lock for " + this.pattern + ", maxLocks: " + FileHandler.maxLocks);
    }
    
    private File generate(final String s, final int n, final int n2) throws IOException {
        File file = null;
        String s2 = "";
        int i = 0;
        boolean b = false;
        boolean b2 = false;
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            ++i;
            char lowerCase = '\0';
            if (i < s.length()) {
                lowerCase = Character.toLowerCase(s.charAt(i));
            }
            if (char1 == '/') {
                if (file == null) {
                    file = new File(s2);
                }
                else {
                    file = new File(file, s2);
                }
                s2 = "";
            }
            else {
                if (char1 == '%') {
                    if (lowerCase == 't') {
                        String s3 = System.getProperty("java.io.tmpdir");
                        if (s3 == null) {
                            s3 = System.getProperty("user.home");
                        }
                        file = new File(s3);
                        ++i;
                        s2 = "";
                        continue;
                    }
                    if (lowerCase == 'h') {
                        file = new File(System.getProperty("user.home"));
                        if (isSetUID()) {
                            throw new IOException("can't use %h in set UID program");
                        }
                        ++i;
                        s2 = "";
                        continue;
                    }
                    else {
                        if (lowerCase == 'g') {
                            s2 += n;
                            b = true;
                            ++i;
                            continue;
                        }
                        if (lowerCase == 'u') {
                            s2 += n2;
                            b2 = true;
                            ++i;
                            continue;
                        }
                        if (lowerCase == '%') {
                            s2 += "%";
                            ++i;
                            continue;
                        }
                    }
                }
                s2 += char1;
            }
        }
        if (this.count > 1 && !b) {
            s2 = s2 + "." + n;
        }
        if (n2 > 0 && !b2) {
            s2 = s2 + "." + n2;
        }
        if (s2.length() > 0) {
            if (file == null) {
                file = new File(s2);
            }
            else {
                file = new File(file, s2);
            }
        }
        return file;
    }
    
    private synchronized void rotate() {
        final Level level = this.getLevel();
        this.setLevel(Level.OFF);
        super.close();
        for (int i = this.count - 2; i >= 0; --i) {
            final File file = this.files[i];
            final File file2 = this.files[i + 1];
            if (file.exists()) {
                if (file2.exists()) {
                    file2.delete();
                }
                file.renameTo(file2);
            }
        }
        try {
            this.open(this.files[0], false);
        }
        catch (IOException ex) {
            this.reportError(null, ex, 4);
        }
        this.setLevel(level);
    }
    
    @Override
    public synchronized void publish(final LogRecord logRecord) {
        if (!this.isLoggable(logRecord)) {
            return;
        }
        super.publish(logRecord);
        this.flush();
        if (this.limit > 0 && this.meter.written >= this.limit) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    FileHandler.this.rotate();
                    return null;
                }
            });
        }
    }
    
    @Override
    public synchronized void close() throws SecurityException {
        super.close();
        if (this.lockFileName == null) {
            return;
        }
        try {
            this.lockFileChannel.close();
        }
        catch (Exception ex) {}
        synchronized (FileHandler.locks) {
            FileHandler.locks.remove(this.lockFileName);
        }
        new File(this.lockFileName).delete();
        this.lockFileName = null;
        this.lockFileChannel = null;
    }
    
    private static native boolean isSetUID();
    
    static {
        locks = new HashSet<String>();
        FileHandler.maxLocks = AccessController.doPrivileged(() -> Integer.getInteger("jdk.internal.FileHandlerLogging.maxLocks", 100));
        if (FileHandler.maxLocks <= 0) {
            FileHandler.maxLocks = 100;
        }
    }
    
    private static class InitializationErrorManager extends ErrorManager
    {
        Exception lastException;
        
        @Override
        public void error(final String s, final Exception lastException, final int n) {
            this.lastException = lastException;
        }
    }
    
    private class MeteredStream extends OutputStream
    {
        final OutputStream out;
        int written;
        
        MeteredStream(final OutputStream out, final int written) {
            this.out = out;
            this.written = written;
        }
        
        @Override
        public void write(final int n) throws IOException {
            this.out.write(n);
            ++this.written;
        }
        
        @Override
        public void write(final byte[] array) throws IOException {
            this.out.write(array);
            this.written += array.length;
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) throws IOException {
            this.out.write(array, n, n2);
            this.written += n2;
        }
        
        @Override
        public void flush() throws IOException {
            this.out.flush();
        }
        
        @Override
        public void close() throws IOException {
            this.out.close();
        }
    }
}
