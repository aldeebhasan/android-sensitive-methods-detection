package javax.xml.namespace;

import java.util.*;

public interface NamespaceContext
{
    String getNamespaceURI(final String p0);
    
    String getPrefix(final String p0);
    
    Iterator getPrefixes(final String p0);
}
