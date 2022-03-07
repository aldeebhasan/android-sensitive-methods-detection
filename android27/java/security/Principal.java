package java.security;

import javax.security.auth.*;

public interface Principal
{
    boolean equals(final Object p0);
    
    String toString();
    
    int hashCode();
    
    String getName();
    
    default boolean implies(final Subject subject) {
        return subject != null && subject.getPrincipals().contains(this);
    }
}
