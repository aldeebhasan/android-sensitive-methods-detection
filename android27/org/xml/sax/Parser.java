package org.xml.sax;

import java.util.*;
import java.io.*;

public interface Parser
{
    void setLocale(final Locale p0) throws SAXException;
    
    void setEntityResolver(final EntityResolver p0);
    
    void setDTDHandler(final DTDHandler p0);
    
    void setDocumentHandler(final DocumentHandler p0);
    
    void setErrorHandler(final ErrorHandler p0);
    
    void parse(final InputSource p0) throws SAXException, IOException;
    
    void parse(final String p0) throws SAXException, IOException;
}
