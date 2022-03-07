package java.nio.file.attribute;

import java.util.*;

public interface PosixFileAttributes extends BasicFileAttributes
{
    UserPrincipal owner();
    
    GroupPrincipal group();
    
    Set<PosixFilePermission> permissions();
}
