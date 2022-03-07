package org.xml.sax;

public interface DocumentHandler
{
    void setDocumentLocator(final Locator p0);
    
    void startDocument() throws SAXException;
    
    void endDocument() throws SAXException;
    
    void startElement(final String p0, final AttributeList p1) throws SAXException;
    
    void endElement(final String p0) throws SAXException;
    
    void characters(final char[] p0, final int p1, final int p2) throws SAXException;
    
    void ignorableWhitespace(final char[] p0, final int p1, final int p2) throws SAXException;
    
    void processingInstruction(final String p0, final String p1) throws SAXException;
}
