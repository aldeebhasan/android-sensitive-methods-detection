package java.security.cert;

import java.util.*;

public interface X509Extension
{
    boolean hasUnsupportedCriticalExtension();
    
    Set<String> getCriticalExtensionOIDs();
    
    Set<String> getNonCriticalExtensionOIDs();
    
    byte[] getExtensionValue(final String p0);
}
