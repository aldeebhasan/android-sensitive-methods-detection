package java.security.acl;

import java.security.*;
import java.util.*;

public interface AclEntry extends Cloneable
{
    boolean setPrincipal(final Principal p0);
    
    Principal getPrincipal();
    
    void setNegativePermissions();
    
    boolean isNegative();
    
    boolean addPermission(final Permission p0);
    
    boolean removePermission(final Permission p0);
    
    boolean checkPermission(final Permission p0);
    
    Enumeration<Permission> permissions();
    
    String toString();
    
    Object clone();
}
