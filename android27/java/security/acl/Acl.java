package java.security.acl;

import java.security.*;
import java.util.*;

public interface Acl extends Owner
{
    void setName(final Principal p0, final String p1) throws NotOwnerException;
    
    String getName();
    
    boolean addEntry(final Principal p0, final AclEntry p1) throws NotOwnerException;
    
    boolean removeEntry(final Principal p0, final AclEntry p1) throws NotOwnerException;
    
    Enumeration<Permission> getPermissions(final Principal p0);
    
    Enumeration<AclEntry> entries();
    
    boolean checkPermission(final Principal p0, final Permission p1);
    
    String toString();
}
