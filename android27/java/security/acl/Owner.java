package java.security.acl;

import java.security.*;

public interface Owner
{
    boolean addOwner(final Principal p0, final Principal p1) throws NotOwnerException;
    
    boolean deleteOwner(final Principal p0, final Principal p1) throws NotOwnerException, LastOwnerException;
    
    boolean isOwner(final Principal p0);
}
