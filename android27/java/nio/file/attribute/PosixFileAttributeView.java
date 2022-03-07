package java.nio.file.attribute;

import java.io.*;
import java.util.*;

public interface PosixFileAttributeView extends BasicFileAttributeView, FileOwnerAttributeView
{
    String name();
    
    PosixFileAttributes readAttributes() throws IOException;
    
    void setPermissions(final Set<PosixFilePermission> p0) throws IOException;
    
    void setGroup(final GroupPrincipal p0) throws IOException;
}
