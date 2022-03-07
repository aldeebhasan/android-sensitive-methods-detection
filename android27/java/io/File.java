package java.io;

import sun.misc.*;
import java.net.*;
import java.util.*;
import java.nio.file.*;
import sun.security.action.*;
import java.security.*;

public class File implements Serializable, Comparable<File>
{
    private static final FileSystem fs;
    private final String path;
    private transient PathStatus status;
    private final transient int prefixLength;
    public static final char separatorChar;
    public static final String separator;
    public static final char pathSeparatorChar;
    public static final String pathSeparator;
    private static final long PATH_OFFSET;
    private static final long PREFIX_LENGTH_OFFSET;
    private static final Unsafe UNSAFE;
    private static final long serialVersionUID = 301077366599181567L;
    private transient volatile Path filePath;
    
    final boolean isInvalid() {
        if (this.status == null) {
            this.status = ((this.path.indexOf(0) < 0) ? PathStatus.CHECKED : PathStatus.INVALID);
        }
        return this.status == PathStatus.INVALID;
    }
    
    int getPrefixLength() {
        return this.prefixLength;
    }
    
    private File(final String path, final int prefixLength) {
        this.status = null;
        this.path = path;
        this.prefixLength = prefixLength;
    }
    
    private File(final String s, final File file) {
        this.status = null;
        assert file.path != null;
        assert !file.path.equals("");
        this.path = File.fs.resolve(file.path, s);
        this.prefixLength = file.prefixLength;
    }
    
    public File(final String s) {
        this.status = null;
        if (s == null) {
            throw new NullPointerException();
        }
        this.path = File.fs.normalize(s);
        this.prefixLength = File.fs.prefixLength(this.path);
    }
    
    public File(final String s, final String s2) {
        this.status = null;
        if (s2 == null) {
            throw new NullPointerException();
        }
        if (s != null) {
            if (s.equals("")) {
                this.path = File.fs.resolve(File.fs.getDefaultParent(), File.fs.normalize(s2));
            }
            else {
                this.path = File.fs.resolve(File.fs.normalize(s), File.fs.normalize(s2));
            }
        }
        else {
            this.path = File.fs.normalize(s2);
        }
        this.prefixLength = File.fs.prefixLength(this.path);
    }
    
    public File(final File file, final String s) {
        this.status = null;
        if (s == null) {
            throw new NullPointerException();
        }
        if (file != null) {
            if (file.path.equals("")) {
                this.path = File.fs.resolve(File.fs.getDefaultParent(), File.fs.normalize(s));
            }
            else {
                this.path = File.fs.resolve(file.path, File.fs.normalize(s));
            }
        }
        else {
            this.path = File.fs.normalize(s);
        }
        this.prefixLength = File.fs.prefixLength(this.path);
    }
    
    public File(final URI uri) {
        this.status = null;
        if (!uri.isAbsolute()) {
            throw new IllegalArgumentException("URI is not absolute");
        }
        if (uri.isOpaque()) {
            throw new IllegalArgumentException("URI is not hierarchical");
        }
        final String scheme = uri.getScheme();
        if (scheme == null || !scheme.equalsIgnoreCase("file")) {
            throw new IllegalArgumentException("URI scheme is not \"file\"");
        }
        if (uri.getAuthority() != null) {
            throw new IllegalArgumentException("URI has an authority component");
        }
        if (uri.getFragment() != null) {
            throw new IllegalArgumentException("URI has a fragment component");
        }
        if (uri.getQuery() != null) {
            throw new IllegalArgumentException("URI has a query component");
        }
        final String path = uri.getPath();
        if (path.equals("")) {
            throw new IllegalArgumentException("URI path component is empty");
        }
        String s = File.fs.fromURIPath(path);
        if (File.separatorChar != '/') {
            s = s.replace('/', File.separatorChar);
        }
        this.path = File.fs.normalize(s);
        this.prefixLength = File.fs.prefixLength(this.path);
    }
    
    public String getName() {
        final int lastIndex = this.path.lastIndexOf(File.separatorChar);
        if (lastIndex < this.prefixLength) {
            return this.path.substring(this.prefixLength);
        }
        return this.path.substring(lastIndex + 1);
    }
    
    public String getParent() {
        final int lastIndex = this.path.lastIndexOf(File.separatorChar);
        if (lastIndex >= this.prefixLength) {
            return this.path.substring(0, lastIndex);
        }
        if (this.prefixLength > 0 && this.path.length() > this.prefixLength) {
            return this.path.substring(0, this.prefixLength);
        }
        return null;
    }
    
    public File getParentFile() {
        final String parent = this.getParent();
        if (parent == null) {
            return null;
        }
        return new File(parent, this.prefixLength);
    }
    
    public String getPath() {
        return this.path;
    }
    
    public boolean isAbsolute() {
        return File.fs.isAbsolute(this);
    }
    
    public String getAbsolutePath() {
        return File.fs.resolve(this);
    }
    
    public File getAbsoluteFile() {
        final String absolutePath = this.getAbsolutePath();
        return new File(absolutePath, File.fs.prefixLength(absolutePath));
    }
    
    public String getCanonicalPath() throws IOException {
        if (this.isInvalid()) {
            throw new IOException("Invalid file path");
        }
        return File.fs.canonicalize(File.fs.resolve(this));
    }
    
    public File getCanonicalFile() throws IOException {
        final String canonicalPath = this.getCanonicalPath();
        return new File(canonicalPath, File.fs.prefixLength(canonicalPath));
    }
    
    private static String slashify(final String s, final boolean b) {
        String s2 = s;
        if (File.separatorChar != '/') {
            s2 = s2.replace(File.separatorChar, '/');
        }
        if (!s2.startsWith("/")) {
            s2 = "/" + s2;
        }
        if (!s2.endsWith("/") && b) {
            s2 += "/";
        }
        return s2;
    }
    
    @Deprecated
    public URL toURL() throws MalformedURLException {
        if (this.isInvalid()) {
            throw new MalformedURLException("Invalid file path");
        }
        return new URL("file", "", slashify(this.getAbsolutePath(), this.isDirectory()));
    }
    
    public URI toURI() {
        try {
            final File absoluteFile = this.getAbsoluteFile();
            String s = slashify(absoluteFile.getPath(), absoluteFile.isDirectory());
            if (s.startsWith("//")) {
                s = "//" + s;
            }
            return new URI("file", null, s, null);
        }
        catch (URISyntaxException ex) {
            throw new Error(ex);
        }
    }
    
    public boolean canRead() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        return !this.isInvalid() && File.fs.checkAccess(this, 4);
    }
    
    public boolean canWrite() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        return !this.isInvalid() && File.fs.checkAccess(this, 2);
    }
    
    public boolean exists() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        return !this.isInvalid() && (File.fs.getBooleanAttributes(this) & 0x1) != 0x0;
    }
    
    public boolean isDirectory() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        return !this.isInvalid() && (File.fs.getBooleanAttributes(this) & 0x4) != 0x0;
    }
    
    public boolean isFile() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        return !this.isInvalid() && (File.fs.getBooleanAttributes(this) & 0x2) != 0x0;
    }
    
    public boolean isHidden() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        return !this.isInvalid() && (File.fs.getBooleanAttributes(this) & 0x8) != 0x0;
    }
    
    public long lastModified() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return 0L;
        }
        return File.fs.getLastModifiedTime(this);
    }
    
    public long length() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return 0L;
        }
        return File.fs.getLength(this);
    }
    
    public boolean createNewFile() throws IOException {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        if (this.isInvalid()) {
            throw new IOException("Invalid file path");
        }
        return File.fs.createFileExclusively(this.path);
    }
    
    public boolean delete() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkDelete(this.path);
        }
        return !this.isInvalid() && File.fs.delete(this);
    }
    
    public void deleteOnExit() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkDelete(this.path);
        }
        if (this.isInvalid()) {
            return;
        }
        DeleteOnExitHook.add(this.path);
    }
    
    public String[] list() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return null;
        }
        return File.fs.list(this);
    }
    
    public String[] list(final FilenameFilter filenameFilter) {
        final String[] list = this.list();
        if (list == null || filenameFilter == null) {
            return list;
        }
        final ArrayList<String> list2 = new ArrayList<String>();
        for (int i = 0; i < list.length; ++i) {
            if (filenameFilter.accept(this, list[i])) {
                list2.add(list[i]);
            }
        }
        return list2.toArray(new String[list2.size()]);
    }
    
    public File[] listFiles() {
        final String[] list = this.list();
        if (list == null) {
            return null;
        }
        final int length = list.length;
        final File[] array = new File[length];
        for (int i = 0; i < length; ++i) {
            array[i] = new File(list[i], this);
        }
        return array;
    }
    
    public File[] listFiles(final FilenameFilter filenameFilter) {
        final String[] list = this.list();
        if (list == null) {
            return null;
        }
        final ArrayList<File> list2 = new ArrayList<File>();
        for (final String s : list) {
            if (filenameFilter == null || filenameFilter.accept(this, s)) {
                list2.add(new File(s, this));
            }
        }
        return list2.toArray(new File[list2.size()]);
    }
    
    public File[] listFiles(final FileFilter fileFilter) {
        final String[] list = this.list();
        if (list == null) {
            return null;
        }
        final ArrayList<File> list2 = new ArrayList<File>();
        final String[] array = list;
        for (int length = array.length, i = 0; i < length; ++i) {
            final File file = new File(array[i], this);
            if (fileFilter == null || fileFilter.accept(file)) {
                list2.add(file);
            }
        }
        return list2.toArray(new File[list2.size()]);
    }
    
    public boolean mkdir() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        return !this.isInvalid() && File.fs.createDirectory(this);
    }
    
    public boolean mkdirs() {
        if (this.exists()) {
            return false;
        }
        if (this.mkdir()) {
            return true;
        }
        File canonicalFile;
        try {
            canonicalFile = this.getCanonicalFile();
        }
        catch (IOException ex) {
            return false;
        }
        final File parentFile = canonicalFile.getParentFile();
        return parentFile != null && (parentFile.mkdirs() || parentFile.exists()) && canonicalFile.mkdir();
    }
    
    public boolean renameTo(final File file) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
            securityManager.checkWrite(file.path);
        }
        if (file == null) {
            throw new NullPointerException();
        }
        return !this.isInvalid() && !file.isInvalid() && File.fs.rename(this, file);
    }
    
    public boolean setLastModified(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("Negative time");
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        return !this.isInvalid() && File.fs.setLastModifiedTime(this, n);
    }
    
    public boolean setReadOnly() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        return !this.isInvalid() && File.fs.setReadOnly(this);
    }
    
    public boolean setWritable(final boolean b, final boolean b2) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        return !this.isInvalid() && File.fs.setPermission(this, 2, b, b2);
    }
    
    public boolean setWritable(final boolean b) {
        return this.setWritable(b, true);
    }
    
    public boolean setReadable(final boolean b, final boolean b2) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        return !this.isInvalid() && File.fs.setPermission(this, 4, b, b2);
    }
    
    public boolean setReadable(final boolean b) {
        return this.setReadable(b, true);
    }
    
    public boolean setExecutable(final boolean b, final boolean b2) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkWrite(this.path);
        }
        return !this.isInvalid() && File.fs.setPermission(this, 1, b, b2);
    }
    
    public boolean setExecutable(final boolean b) {
        return this.setExecutable(b, true);
    }
    
    public boolean canExecute() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkExec(this.path);
        }
        return !this.isInvalid() && File.fs.checkAccess(this, 1);
    }
    
    public static File[] listRoots() {
        return File.fs.listRoots();
    }
    
    public long getTotalSpace() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getFileSystemAttributes"));
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return 0L;
        }
        return File.fs.getSpace(this, 0);
    }
    
    public long getFreeSpace() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getFileSystemAttributes"));
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return 0L;
        }
        return File.fs.getSpace(this, 1);
    }
    
    public long getUsableSpace() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getFileSystemAttributes"));
            securityManager.checkRead(this.path);
        }
        if (this.isInvalid()) {
            return 0L;
        }
        return File.fs.getSpace(this, 2);
    }
    
    public static File createTempFile(final String s, String s2, final File file) throws IOException {
        if (s.length() < 3) {
            throw new IllegalArgumentException("Prefix string too short");
        }
        if (s2 == null) {
            s2 = ".tmp";
        }
        final File file2 = (file != null) ? file : TempDirectory.location();
        final SecurityManager securityManager = System.getSecurityManager();
        File generateFile;
        do {
            generateFile = TempDirectory.generateFile(s, s2, file2);
            if (securityManager != null) {
                try {
                    securityManager.checkWrite(generateFile.getPath());
                }
                catch (SecurityException ex) {
                    if (file == null) {
                        throw new SecurityException("Unable to create temporary file");
                    }
                    throw ex;
                }
            }
        } while ((File.fs.getBooleanAttributes(generateFile) & 0x1) != 0x0);
        if (!File.fs.createFileExclusively(generateFile.getPath())) {
            throw new IOException("Unable to create temporary file");
        }
        return generateFile;
    }
    
    public static File createTempFile(final String s, final String s2) throws IOException {
        return createTempFile(s, s2, null);
    }
    
    @Override
    public int compareTo(final File file) {
        return File.fs.compare(this, file);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o instanceof File && this.compareTo((File)o) == 0;
    }
    
    @Override
    public int hashCode() {
        return File.fs.hashCode(this);
    }
    
    @Override
    public String toString() {
        return this.getPath();
    }
    
    private synchronized void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeChar(File.separatorChar);
    }
    
    private synchronized void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String replace = (String)objectInputStream.readFields().get("path", null);
        final char char1 = objectInputStream.readChar();
        if (char1 != File.separatorChar) {
            replace = replace.replace(char1, File.separatorChar);
        }
        final String normalize = File.fs.normalize(replace);
        File.UNSAFE.putObject(this, File.PATH_OFFSET, normalize);
        File.UNSAFE.putIntVolatile(this, File.PREFIX_LENGTH_OFFSET, File.fs.prefixLength(normalize));
    }
    
    public Path toPath() {
        Path filePath = this.filePath;
        if (filePath == null) {
            synchronized (this) {
                filePath = this.filePath;
                if (filePath == null) {
                    filePath = FileSystems.getDefault().getPath(this.path, new String[0]);
                    this.filePath = filePath;
                }
            }
        }
        return filePath;
    }
    
    static {
        fs = DefaultFileSystem.getFileSystem();
        separatorChar = File.fs.getSeparator();
        separator = "" + File.separatorChar;
        pathSeparatorChar = File.fs.getPathSeparator();
        pathSeparator = "" + File.pathSeparatorChar;
        try {
            final Unsafe unsafe = Unsafe.getUnsafe();
            PATH_OFFSET = unsafe.objectFieldOffset(File.class.getDeclaredField("path"));
            PREFIX_LENGTH_OFFSET = unsafe.objectFieldOffset(File.class.getDeclaredField("prefixLength"));
            UNSAFE = unsafe;
        }
        catch (ReflectiveOperationException ex) {
            throw new Error(ex);
        }
    }
    
    private enum PathStatus
    {
        INVALID, 
        CHECKED;
    }
    
    private static class TempDirectory
    {
        private static final File tmpdir;
        private static final SecureRandom random;
        
        static File location() {
            return TempDirectory.tmpdir;
        }
        
        static File generateFile(String name, final String s, final File file) throws IOException {
            final long nextLong = TempDirectory.random.nextLong();
            long abs;
            if (nextLong == Long.MIN_VALUE) {
                abs = 0L;
            }
            else {
                abs = Math.abs(nextLong);
            }
            name = new File(name).getName();
            final String string = name + Long.toString(abs) + s;
            final File file2 = new File(file, string);
            if (string.equals(file2.getName()) && !file2.isInvalid()) {
                return file2;
            }
            if (System.getSecurityManager() != null) {
                throw new IOException("Unable to create temporary file");
            }
            throw new IOException("Unable to create temporary file, " + file2);
        }
        
        static {
            tmpdir = new File(AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("java.io.tmpdir")));
            random = new SecureRandom();
        }
    }
}
