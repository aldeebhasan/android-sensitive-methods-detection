package org.xml.sax.ext;

import org.xml.sax.helpers.*;
import org.xml.sax.*;
import java.io.*;

public class DefaultHandler2 extends DefaultHandler implements LexicalHandler, DeclHandler, EntityResolver2
{
    @Override
    public void startCDATA() throws SAXException {
    }
    
    @Override
    public void endCDATA() throws SAXException {
    }
    
    @Override
    public void startDTD(final String s, final String s2, final String s3) throws SAXException {
    }
    
    @Override
    public void endDTD() throws SAXException {
    }
    
    @Override
    public void startEntity(final String s) throws SAXException {
    }
    
    @Override
    public void endEntity(final String s) throws SAXException {
    }
    
    @Override
    public void comment(final char[] array, final int n, final int n2) throws SAXException {
    }
    
    @Override
    public void attributeDecl(final String s, final String s2, final String s3, final String s4, final String s5) throws SAXException {
    }
    
    @Override
    public void elementDecl(final String s, final String s2) throws SAXException {
    }
    
    @Override
    public void externalEntityDecl(final String s, final String s2, final String s3) throws SAXException {
    }
    
    @Override
    public void internalEntityDecl(final String s, final String s2) throws SAXException {
    }
    
    @Override
    public InputSource getExternalSubset(final String s, final String s2) throws SAXException, IOException {
        return null;
    }
    
    @Override
    public InputSource resolveEntity(final String s, final String s2, final String s3, final String s4) throws SAXException, IOException {
        return null;
    }
    
    @Override
    public InputSource resolveEntity(final String s, final String s2) throws SAXException, IOException {
        return this.resolveEntity(null, s, null, s2);
    }
}
