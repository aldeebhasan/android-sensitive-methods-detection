package org.xml.sax.ext;

import org.xml.sax.*;
import java.io.*;

public interface EntityResolver2 extends EntityResolver
{
    InputSource getExternalSubset(final String p0, final String p1) throws SAXException, IOException;
    
    InputSource resolveEntity(final String p0, final String p1, final String p2, final String p3) throws SAXException, IOException;
}
