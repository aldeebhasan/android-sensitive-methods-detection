package java.security;

import java.util.*;

public interface Entry
{
    default Set<Attribute> getAttributes() {
        return Collections.emptySet();
    }
    
    public interface Attribute
    {
        String getName();
        
        String getValue();
    }
}
