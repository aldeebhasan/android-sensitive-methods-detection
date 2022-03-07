package org.xml.sax;

public class HandlerBase implements EntityResolver, DTDHandler, DocumentHandler, ErrorHandler
{
    @Override
    public InputSource resolveEntity(final String s, final String s2) throws SAXException {
        return null;
    }
    
    @Override
    public void notationDecl(final String s, final String s2, final String s3) {
    }
    
    @Override
    public void unparsedEntityDecl(final String s, final String s2, final String s3, final String s4) {
    }
    
    @Override
    public void setDocumentLocator(final Locator locator) {
    }
    
    @Override
    public void startDocument() throws SAXException {
    }
    
    @Override
    public void endDocument() throws SAXException {
    }
    
    @Override
    public void startElement(final String s, final AttributeList list) throws SAXException {
    }
    
    @Override
    public void endElement(final String s) throws SAXException {
    }
    
    @Override
    public void characters(final char[] array, final int n, final int n2) throws SAXException {
    }
    
    @Override
    public void ignorableWhitespace(final char[] array, final int n, final int n2) throws SAXException {
    }
    
    @Override
    public void processingInstruction(final String s, final String s2) throws SAXException {
    }
    
    @Override
    public void warning(final SAXParseException ex) throws SAXException {
    }
    
    @Override
    public void error(final SAXParseException ex) throws SAXException {
    }
    
    @Override
    public void fatalError(final SAXParseException ex) throws SAXException {
        throw ex;
    }
}
