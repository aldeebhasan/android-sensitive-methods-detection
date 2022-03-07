package org.xml.sax;

import java.io.*;

public interface EntityResolver
{
    InputSource resolveEntity(final String p0, final String p1) throws SAXException, IOException;
}
