package org.xml.sax.ext;

import org.xml.sax.*;

public interface Locator2 extends Locator
{
    String getXMLVersion();
    
    String getEncoding();
}
