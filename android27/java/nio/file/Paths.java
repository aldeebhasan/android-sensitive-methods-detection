package java.nio.file;

import java.net.*;
import java.nio.file.spi.*;
import java.util.*;

public final class Paths
{
    public static Path get(final String s, final String... array) {
        return FileSystems.getDefault().getPath(s, array);
    }
    
    public static Path get(final URI uri) {
        final String scheme = uri.getScheme();
        if (scheme == null) {
            throw new IllegalArgumentException("Missing scheme");
        }
        if (scheme.equalsIgnoreCase("file")) {
            return FileSystems.getDefault().provider().getPath(uri);
        }
        for (final FileSystemProvider fileSystemProvider : FileSystemProvider.installedProviders()) {
            if (fileSystemProvider.getScheme().equalsIgnoreCase(scheme)) {
                return fileSystemProvider.getPath(uri);
            }
        }
        throw new FileSystemNotFoundException("Provider \"" + scheme + "\" not installed");
    }
}
