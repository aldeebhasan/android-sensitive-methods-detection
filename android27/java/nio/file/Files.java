package java.nio.file;

import java.nio.file.spi.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.stream.*;
import java.util.function.*;
import sun.nio.fs.*;
import java.security.*;
import java.util.*;

public final class Files
{
    private static final int BUFFER_SIZE = 8192;
    private static final int MAX_BUFFER_SIZE = 2147483639;
    
    private static FileSystemProvider provider(final Path path) {
        return path.getFileSystem().provider();
    }
    
    private static Runnable asUncheckedRunnable(final Closeable closeable) {
        return () -> {
            try {
                closeable.close();
            }
            catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
        };
    }
    
    public static InputStream newInputStream(final Path path, final OpenOption... array) throws IOException {
        return provider(path).newInputStream(path, array);
    }
    
    public static OutputStream newOutputStream(final Path path, final OpenOption... array) throws IOException {
        return provider(path).newOutputStream(path, array);
    }
    
    public static SeekableByteChannel newByteChannel(final Path path, final Set<? extends OpenOption> set, final FileAttribute<?>... array) throws IOException {
        return provider(path).newByteChannel(path, set, array);
    }
    
    public static SeekableByteChannel newByteChannel(final Path path, final OpenOption... array) throws IOException {
        final HashSet<Object> set = new HashSet<Object>(array.length);
        Collections.addAll(set, array);
        return newByteChannel(path, (Set<? extends OpenOption>)set, (FileAttribute<?>[])new FileAttribute[0]);
    }
    
    public static DirectoryStream<Path> newDirectoryStream(final Path path) throws IOException {
        return provider(path).newDirectoryStream(path, AcceptAllFilter.FILTER);
    }
    
    public static DirectoryStream<Path> newDirectoryStream(final Path path, final String s) throws IOException {
        if (s.equals("*")) {
            return newDirectoryStream(path);
        }
        final FileSystem fileSystem = path.getFileSystem();
        return fileSystem.provider().newDirectoryStream(path, new DirectoryStream.Filter<Path>() {
            final /* synthetic */ PathMatcher val$matcher = fileSystem.getPathMatcher("glob:" + s);
            
            @Override
            public boolean accept(final Path path) {
                return this.val$matcher.matches(path.getFileName());
            }
        });
    }
    
    public static DirectoryStream<Path> newDirectoryStream(final Path path, final DirectoryStream.Filter<? super Path> filter) throws IOException {
        return provider(path).newDirectoryStream(path, filter);
    }
    
    public static Path createFile(final Path path, final FileAttribute<?>... array) throws IOException {
        newByteChannel(path, EnumSet.of(StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE), array).close();
        return path;
    }
    
    public static Path createDirectory(final Path path, final FileAttribute<?>... array) throws IOException {
        provider(path).createDirectory(path, array);
        return path;
    }
    
    public static Path createDirectories(Path absolutePath, final FileAttribute<?>... array) throws IOException {
        try {
            createAndCheckIsDirectory(absolutePath, array);
            return absolutePath;
        }
        catch (FileAlreadyExistsException ex) {
            throw ex;
        }
        catch (IOException ex4) {
            SecurityException ex2 = null;
            try {
                absolutePath = absolutePath.toAbsolutePath();
            }
            catch (SecurityException ex3) {
                ex2 = ex3;
            }
            Path path = absolutePath.getParent();
            while (path != null) {
                try {
                    provider(path).checkAccess(path, new AccessMode[0]);
                }
                catch (NoSuchFileException ex5) {
                    path = path.getParent();
                    continue;
                }
                break;
            }
            if (path != null) {
                Path resolve = path;
                final Iterator<Path> iterator = path.relativize(absolutePath).iterator();
                while (iterator.hasNext()) {
                    resolve = resolve.resolve(iterator.next());
                    createAndCheckIsDirectory(resolve, array);
                }
                return absolutePath;
            }
            if (ex2 == null) {
                throw new FileSystemException(absolutePath.toString(), null, "Unable to determine if root directory exists");
            }
            throw ex2;
        }
    }
    
    private static void createAndCheckIsDirectory(final Path path, final FileAttribute<?>... array) throws IOException {
        try {
            createDirectory(path, array);
        }
        catch (FileAlreadyExistsException ex) {
            if (!isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                throw ex;
            }
        }
    }
    
    public static Path createTempFile(final Path path, final String s, final String s2, final FileAttribute<?>... array) throws IOException {
        return TempFileHelper.createTempFile(Objects.requireNonNull(path), s, s2, array);
    }
    
    public static Path createTempFile(final String s, final String s2, final FileAttribute<?>... array) throws IOException {
        return TempFileHelper.createTempFile(null, s, s2, array);
    }
    
    public static Path createTempDirectory(final Path path, final String s, final FileAttribute<?>... array) throws IOException {
        return TempFileHelper.createTempDirectory(Objects.requireNonNull(path), s, array);
    }
    
    public static Path createTempDirectory(final String s, final FileAttribute<?>... array) throws IOException {
        return TempFileHelper.createTempDirectory(null, s, array);
    }
    
    public static Path createSymbolicLink(final Path path, final Path path2, final FileAttribute<?>... array) throws IOException {
        provider(path).createSymbolicLink(path, path2, array);
        return path;
    }
    
    public static Path createLink(final Path path, final Path path2) throws IOException {
        provider(path).createLink(path, path2);
        return path;
    }
    
    public static void delete(final Path path) throws IOException {
        provider(path).delete(path);
    }
    
    public static boolean deleteIfExists(final Path path) throws IOException {
        return provider(path).deleteIfExists(path);
    }
    
    public static Path copy(final Path path, final Path path2, final CopyOption... array) throws IOException {
        final FileSystemProvider provider = provider(path);
        if (provider(path2) == provider) {
            provider.copy(path, path2, array);
        }
        else {
            CopyMoveHelper.copyToForeignTarget(path, path2, array);
        }
        return path2;
    }
    
    public static Path move(final Path path, final Path path2, final CopyOption... array) throws IOException {
        final FileSystemProvider provider = provider(path);
        if (provider(path2) == provider) {
            provider.move(path, path2, array);
        }
        else {
            CopyMoveHelper.moveToForeignTarget(path, path2, array);
        }
        return path2;
    }
    
    public static Path readSymbolicLink(final Path path) throws IOException {
        return provider(path).readSymbolicLink(path);
    }
    
    public static FileStore getFileStore(final Path path) throws IOException {
        return provider(path).getFileStore(path);
    }
    
    public static boolean isSameFile(final Path path, final Path path2) throws IOException {
        return provider(path).isSameFile(path, path2);
    }
    
    public static boolean isHidden(final Path path) throws IOException {
        return provider(path).isHidden(path);
    }
    
    public static String probeContentType(final Path path) throws IOException {
        final Iterator<FileTypeDetector> iterator = FileTypeDetectors.installeDetectors.iterator();
        while (iterator.hasNext()) {
            final String probeContentType = iterator.next().probeContentType(path);
            if (probeContentType != null) {
                return probeContentType;
            }
        }
        return FileTypeDetectors.defaultFileTypeDetector.probeContentType(path);
    }
    
    public static <V extends FileAttributeView> V getFileAttributeView(final Path path, final Class<V> clazz, final LinkOption... array) {
        return provider(path).getFileAttributeView(path, clazz, array);
    }
    
    public static <A extends BasicFileAttributes> A readAttributes(final Path path, final Class<A> clazz, final LinkOption... array) throws IOException {
        return provider(path).readAttributes(path, clazz, array);
    }
    
    public static Path setAttribute(final Path path, final String s, final Object o, final LinkOption... array) throws IOException {
        provider(path).setAttribute(path, s, o, array);
        return path;
    }
    
    public static Object getAttribute(final Path path, final String s, final LinkOption... array) throws IOException {
        if (s.indexOf(42) >= 0 || s.indexOf(44) >= 0) {
            throw new IllegalArgumentException(s);
        }
        final Map<String, Object> attributes = readAttributes(path, s, array);
        assert attributes.size() == 1;
        final int index = s.indexOf(58);
        String s2;
        if (index == -1) {
            s2 = s;
        }
        else {
            s2 = ((index == s.length()) ? "" : s.substring(index + 1));
        }
        return attributes.get(s2);
    }
    
    public static Map<String, Object> readAttributes(final Path path, final String s, final LinkOption... array) throws IOException {
        return provider(path).readAttributes(path, s, array);
    }
    
    public static Set<PosixFilePermission> getPosixFilePermissions(final Path path, final LinkOption... array) throws IOException {
        return readAttributes(path, PosixFileAttributes.class, array).permissions();
    }
    
    public static Path setPosixFilePermissions(final Path path, final Set<PosixFilePermission> permissions) throws IOException {
        final PosixFileAttributeView posixFileAttributeView = getFileAttributeView(path, PosixFileAttributeView.class, new LinkOption[0]);
        if (posixFileAttributeView == null) {
            throw new UnsupportedOperationException();
        }
        posixFileAttributeView.setPermissions(permissions);
        return path;
    }
    
    public static UserPrincipal getOwner(final Path path, final LinkOption... array) throws IOException {
        final FileOwnerAttributeView fileOwnerAttributeView = getFileAttributeView(path, FileOwnerAttributeView.class, array);
        if (fileOwnerAttributeView == null) {
            throw new UnsupportedOperationException();
        }
        return fileOwnerAttributeView.getOwner();
    }
    
    public static Path setOwner(final Path path, final UserPrincipal owner) throws IOException {
        final FileOwnerAttributeView fileOwnerAttributeView = getFileAttributeView(path, FileOwnerAttributeView.class, new LinkOption[0]);
        if (fileOwnerAttributeView == null) {
            throw new UnsupportedOperationException();
        }
        fileOwnerAttributeView.setOwner(owner);
        return path;
    }
    
    public static boolean isSymbolicLink(final Path path) {
        try {
            return readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS).isSymbolicLink();
        }
        catch (IOException ex) {
            return false;
        }
    }
    
    public static boolean isDirectory(final Path path, final LinkOption... array) {
        try {
            return readAttributes(path, BasicFileAttributes.class, array).isDirectory();
        }
        catch (IOException ex) {
            return false;
        }
    }
    
    public static boolean isRegularFile(final Path path, final LinkOption... array) {
        try {
            return readAttributes(path, BasicFileAttributes.class, array).isRegularFile();
        }
        catch (IOException ex) {
            return false;
        }
    }
    
    public static FileTime getLastModifiedTime(final Path path, final LinkOption... array) throws IOException {
        return readAttributes(path, BasicFileAttributes.class, array).lastModifiedTime();
    }
    
    public static Path setLastModifiedTime(final Path path, final FileTime fileTime) throws IOException {
        getFileAttributeView(path, BasicFileAttributeView.class, new LinkOption[0]).setTimes(fileTime, null, null);
        return path;
    }
    
    public static long size(final Path path) throws IOException {
        return readAttributes(path, BasicFileAttributes.class, new LinkOption[0]).size();
    }
    
    private static boolean followLinks(final LinkOption... array) {
        boolean b = true;
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final LinkOption linkOption = array[i];
            if (linkOption == LinkOption.NOFOLLOW_LINKS) {
                b = false;
                ++i;
            }
            else {
                if (linkOption == null) {
                    throw new NullPointerException();
                }
                throw new AssertionError((Object)"Should not get here");
            }
        }
        return b;
    }
    
    public static boolean exists(final Path path, final LinkOption... array) {
        try {
            if (followLinks(array)) {
                provider(path).checkAccess(path, new AccessMode[0]);
            }
            else {
                readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            }
            return true;
        }
        catch (IOException ex) {
            return false;
        }
    }
    
    public static boolean notExists(final Path path, final LinkOption... array) {
        try {
            if (followLinks(array)) {
                provider(path).checkAccess(path, new AccessMode[0]);
            }
            else {
                readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
            }
            return false;
        }
        catch (NoSuchFileException ex) {
            return true;
        }
        catch (IOException ex2) {
            return false;
        }
    }
    
    private static boolean isAccessible(final Path path, final AccessMode... array) {
        try {
            provider(path).checkAccess(path, array);
            return true;
        }
        catch (IOException ex) {
            return false;
        }
    }
    
    public static boolean isReadable(final Path path) {
        return isAccessible(path, AccessMode.READ);
    }
    
    public static boolean isWritable(final Path path) {
        return isAccessible(path, AccessMode.WRITE);
    }
    
    public static boolean isExecutable(final Path path) {
        return isAccessible(path, AccessMode.EXECUTE);
    }
    
    public static Path walkFileTree(final Path path, final Set<FileVisitOption> set, final int n, final FileVisitor<? super Path> fileVisitor) throws IOException {
        try (final FileTreeWalker fileTreeWalker = new FileTreeWalker(set, n)) {
            FileTreeWalker.Event event = fileTreeWalker.walk(path);
            do {
                FileVisitResult fileVisitResult = null;
                switch (event.type()) {
                    case ENTRY: {
                        final IOException ioeException = event.ioeException();
                        if (ioeException != null) {
                            fileVisitResult = fileVisitor.visitFileFailed(event.file(), ioeException);
                            break;
                        }
                        assert event.attributes() != null;
                        fileVisitResult = fileVisitor.visitFile(event.file(), event.attributes());
                        break;
                    }
                    case START_DIRECTORY: {
                        fileVisitResult = fileVisitor.preVisitDirectory(event.file(), event.attributes());
                        if (fileVisitResult == FileVisitResult.SKIP_SUBTREE || fileVisitResult == FileVisitResult.SKIP_SIBLINGS) {
                            fileTreeWalker.pop();
                            break;
                        }
                        break;
                    }
                    case END_DIRECTORY: {
                        fileVisitResult = fileVisitor.postVisitDirectory(event.file(), event.ioeException());
                        if (fileVisitResult == FileVisitResult.SKIP_SIBLINGS) {
                            fileVisitResult = FileVisitResult.CONTINUE;
                            break;
                        }
                        break;
                    }
                    default: {
                        throw new AssertionError((Object)"Should not get here");
                    }
                }
                if (Objects.requireNonNull(fileVisitResult) != FileVisitResult.CONTINUE) {
                    if (fileVisitResult == FileVisitResult.TERMINATE) {
                        break;
                    }
                    if (fileVisitResult == FileVisitResult.SKIP_SIBLINGS) {
                        fileTreeWalker.skipRemainingSiblings();
                    }
                }
                event = fileTreeWalker.next();
            } while (event != null);
        }
        return path;
    }
    
    public static Path walkFileTree(final Path path, final FileVisitor<? super Path> fileVisitor) throws IOException {
        return walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, fileVisitor);
    }
    
    public static BufferedReader newBufferedReader(final Path path, final Charset charset) throws IOException {
        return new BufferedReader(new InputStreamReader(newInputStream(path, new OpenOption[0]), charset.newDecoder()));
    }
    
    public static BufferedReader newBufferedReader(final Path path) throws IOException {
        return newBufferedReader(path, StandardCharsets.UTF_8);
    }
    
    public static BufferedWriter newBufferedWriter(final Path path, final Charset charset, final OpenOption... array) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(newOutputStream(path, array), charset.newEncoder()));
    }
    
    public static BufferedWriter newBufferedWriter(final Path path, final OpenOption... array) throws IOException {
        return newBufferedWriter(path, StandardCharsets.UTF_8, array);
    }
    
    private static long copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        long n = 0L;
        final byte[] array = new byte[8192];
        int read;
        while ((read = inputStream.read(array)) > 0) {
            outputStream.write(array, 0, read);
            n += read;
        }
        return n;
    }
    
    public static long copy(final InputStream inputStream, final Path path, final CopyOption... array) throws IOException {
        Objects.requireNonNull(inputStream);
        boolean b = false;
        final int length = array.length;
        int i = 0;
        while (i < length) {
            final CopyOption copyOption = array[i];
            if (copyOption == StandardCopyOption.REPLACE_EXISTING) {
                b = true;
                ++i;
            }
            else {
                if (copyOption == null) {
                    throw new NullPointerException("options contains 'null'");
                }
                throw new UnsupportedOperationException(copyOption + " not supported");
            }
        }
        SecurityException ex = null;
        if (b) {
            try {
                deleteIfExists(path);
            }
            catch (SecurityException ex2) {
                ex = ex2;
            }
        }
        try {
            final OutputStream outputStream = newOutputStream(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        }
        catch (FileAlreadyExistsException ex3) {
            if (ex != null) {
                throw ex;
            }
            throw ex3;
        }
        OutputStream outputStream;
        try (final OutputStream outputStream2 = outputStream) {
            return copy(inputStream, outputStream2);
        }
    }
    
    public static long copy(final Path path, final OutputStream outputStream) throws IOException {
        Objects.requireNonNull(outputStream);
        try (final InputStream inputStream = newInputStream(path, new OpenOption[0])) {
            return copy(inputStream, outputStream);
        }
    }
    
    private static byte[] read(final InputStream inputStream, final int n) throws IOException {
        int max = n;
        byte[] copy = new byte[max];
        int n2 = 0;
        while (true) {
            final int read;
            if ((read = inputStream.read(copy, n2, max - n2)) > 0) {
                n2 += read;
            }
            else {
                final int read2;
                if (read < 0 || (read2 = inputStream.read()) < 0) {
                    return (max == n2) ? copy : Arrays.copyOf(copy, n2);
                }
                if (max <= 2147483639 - max) {
                    max = Math.max(max << 1, 8192);
                }
                else {
                    if (max == 2147483639) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    max = 2147483639;
                }
                copy = Arrays.copyOf(copy, max);
                copy[n2++] = (byte)read2;
            }
        }
    }
    
    public static byte[] readAllBytes(final Path path) throws IOException {
        try (final SeekableByteChannel byteChannel = newByteChannel(path, new OpenOption[0]);
             final InputStream inputStream = Channels.newInputStream(byteChannel)) {
            final long size = byteChannel.size();
            if (size > 2147483639L) {
                throw new OutOfMemoryError("Required array size too large");
            }
            return read(inputStream, (int)size);
        }
    }
    
    public static List<String> readAllLines(final Path path, final Charset charset) throws IOException {
        try (final BufferedReader bufferedReader = newBufferedReader(path, charset)) {
            final ArrayList<String> list = new ArrayList<String>();
            while (true) {
                final String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                list.add(line);
            }
            return list;
        }
    }
    
    public static List<String> readAllLines(final Path path) throws IOException {
        return readAllLines(path, StandardCharsets.UTF_8);
    }
    
    public static Path write(final Path path, final byte[] array, final OpenOption... array2) throws IOException {
        Objects.requireNonNull(array);
        try (final OutputStream outputStream = newOutputStream(path, array2)) {
            int i;
            int min;
            for (int n = i = array.length; i > 0; i -= min) {
                min = Math.min(i, 8192);
                outputStream.write(array, n - i, min);
            }
        }
        return path;
    }
    
    public static Path write(final Path path, final Iterable<? extends CharSequence> iterable, final Charset charset, final OpenOption... array) throws IOException {
        Objects.requireNonNull(iterable);
        final CharsetEncoder encoder = charset.newEncoder();
        try (final OutputStream outputStream = newOutputStream(path, array);
             final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, encoder))) {
            final Iterator<? extends CharSequence> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                bufferedWriter.append((CharSequence)iterator.next());
                bufferedWriter.newLine();
            }
        }
        return path;
    }
    
    public static Path write(final Path path, final Iterable<? extends CharSequence> iterable, final OpenOption... array) throws IOException {
        return write(path, iterable, StandardCharsets.UTF_8, array);
    }
    
    public static Stream<Path> list(final Path path) throws IOException {
        final DirectoryStream<Path> directoryStream = newDirectoryStream(path);
        try {
            return (Stream<Path>)StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<?>)new Iterator<Path>() {
                final /* synthetic */ Iterator val$delegate = directoryStream.iterator();
                
                @Override
                public boolean hasNext() {
                    try {
                        return this.val$delegate.hasNext();
                    }
                    catch (DirectoryIteratorException ex) {
                        throw new UncheckedIOException(ex.getCause());
                    }
                }
                
                @Override
                public Path next() {
                    try {
                        return this.val$delegate.next();
                    }
                    catch (DirectoryIteratorException ex) {
                        throw new UncheckedIOException(ex.getCause());
                    }
                }
            }, 1), false).onClose(asUncheckedRunnable(directoryStream));
        }
        catch (Error | RuntimeException error) {
            final RuntimeException ex2;
            final RuntimeException ex = ex2;
            try {
                directoryStream.close();
            }
            catch (IOException ex3) {
                try {
                    ex.addSuppressed(ex3);
                }
                catch (Throwable t) {}
            }
            throw ex;
        }
    }
    
    public static Stream<Path> walk(final Path path, final int n, final FileVisitOption... array) throws IOException {
        final FileTreeIterator fileTreeIterator = new FileTreeIterator(path, n, array);
        try {
            return (Stream<Path>)((Stream<Object>)StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<?>)fileTreeIterator, 1), false).onClose(fileTreeIterator::close)).map(event -> event.file());
        }
        catch (Error | RuntimeException error) {
            final Object o2;
            final Object o = o2;
            fileTreeIterator.close();
            throw o;
        }
    }
    
    public static Stream<Path> walk(final Path path, final FileVisitOption... array) throws IOException {
        return walk(path, Integer.MAX_VALUE, array);
    }
    
    public static Stream<Path> find(final Path path, final int n, final BiPredicate<Path, BasicFileAttributes> biPredicate, final FileVisitOption... array) throws IOException {
        final FileTreeIterator fileTreeIterator = new FileTreeIterator(path, n, array);
        try {
            return (Stream<Path>)((Stream<Object>)StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<?>)fileTreeIterator, 1), false).onClose(fileTreeIterator::close)).filter(event2 -> biPredicate.test(event2.file(), event2.attributes())).map(event -> event.file());
        }
        catch (Error | RuntimeException error) {
            final Object o2;
            final Object o = o2;
            fileTreeIterator.close();
            throw o;
        }
    }
    
    public static Stream<String> lines(final Path path, final Charset charset) throws IOException {
        final BufferedReader bufferedReader = newBufferedReader(path, charset);
        try {
            return (Stream<String>)bufferedReader.lines().onClose(asUncheckedRunnable(bufferedReader));
        }
        catch (Error | RuntimeException error) {
            final RuntimeException ex2;
            final RuntimeException ex = ex2;
            try {
                bufferedReader.close();
            }
            catch (IOException ex3) {
                try {
                    ex.addSuppressed(ex3);
                }
                catch (Throwable t) {}
            }
            throw ex;
        }
    }
    
    public static Stream<String> lines(final Path path) throws IOException {
        return lines(path, StandardCharsets.UTF_8);
    }
    
    private static class AcceptAllFilter implements DirectoryStream.Filter<Path>
    {
        static final AcceptAllFilter FILTER;
        
        @Override
        public boolean accept(final Path path) {
            return true;
        }
        
        static {
            FILTER = new AcceptAllFilter();
        }
    }
    
    private static class FileTypeDetectors
    {
        static final FileTypeDetector defaultFileTypeDetector;
        static final List<FileTypeDetector> installeDetectors;
        
        private static FileTypeDetector createDefaultFileTypeDetector() {
            return AccessController.doPrivileged((PrivilegedAction<FileTypeDetector>)new PrivilegedAction<FileTypeDetector>() {
                @Override
                public FileTypeDetector run() {
                    return DefaultFileTypeDetector.create();
                }
            });
        }
        
        private static List<FileTypeDetector> loadInstalledDetectors() {
            return AccessController.doPrivileged((PrivilegedAction<List<FileTypeDetector>>)new PrivilegedAction<List<FileTypeDetector>>() {
                @Override
                public List<FileTypeDetector> run() {
                    final ArrayList<FileTypeDetector> list = new ArrayList<FileTypeDetector>();
                    final Iterator<FileTypeDetector> iterator = ServiceLoader.load(FileTypeDetector.class, ClassLoader.getSystemClassLoader()).iterator();
                    while (iterator.hasNext()) {
                        list.add(iterator.next());
                    }
                    return list;
                }
            });
        }
        
        static {
            defaultFileTypeDetector = createDefaultFileTypeDetector();
            installeDetectors = loadInstalledDetectors();
        }
    }
}
