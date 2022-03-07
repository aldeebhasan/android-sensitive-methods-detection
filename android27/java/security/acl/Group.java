package java.security.acl;

import java.security.*;
import java.util.*;

public interface Group extends Principal
{
    boolean addMember(final Principal p0);
    
    boolean removeMember(final Principal p0);
    
    boolean isMember(final Principal p0);
    
    Enumeration<? extends Principal> members();
}
