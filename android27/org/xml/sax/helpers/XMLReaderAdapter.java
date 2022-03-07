package org.xml.sax.helpers;

import java.util.*;
import java.io.*;
import org.xml.sax.*;

public class XMLReaderAdapter implements Parser, ContentHandler
{
    XMLReader xmlReader;
    DocumentHandler documentHandler;
    AttributesAdapter qAtts;
    
    public XMLReaderAdapter() throws SAXException {
        this.setup(XMLReaderFactory.createXMLReader());
    }
    
    public XMLReaderAdapter(final XMLReader xmlReader) {
        this.setup(xmlReader);
    }
    
    private void setup(final XMLReader xmlReader) {
        if (xmlReader == null) {
            throw new NullPointerException("XMLReader must not be null");
        }
        this.xmlReader = xmlReader;
        this.qAtts = new AttributesAdapter();
    }
    
    @Override
    public void setLocale(final Locale locale) throws SAXException {
        throw new SAXNotSupportedException("setLocale not supported");
    }
    
    @Override
    public void setEntityResolver(final EntityResolver entityResolver) {
        this.xmlReader.setEntityResolver(entityResolver);
    }
    
    @Override
    public void setDTDHandler(final DTDHandler dtdHandler) {
        this.xmlReader.setDTDHandler(dtdHandler);
    }
    
    @Override
    public void setDocumentHandler(final DocumentHandler documentHandler) {
        this.documentHandler = documentHandler;
    }
    
    @Override
    public void setErrorHandler(final ErrorHandler errorHandler) {
        this.xmlReader.setErrorHandler(errorHandler);
    }
    
    @Override
    public void parse(final String s) throws IOException, SAXException {
        this.parse(new InputSource(s));
    }
    
    @Override
    public void parse(final InputSource inputSource) throws IOException, SAXException {
        this.setupXMLReader();
        this.xmlReader.parse(inputSource);
    }
    
    private void setupXMLReader() throws SAXException {
        this.xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
        try {
            this.xmlReader.setFeature("http://xml.org/sax/features/namespaces", false);
        }
        catch (SAXException ex) {}
        this.xmlReader.setContentHandler(this);
    }
    
    @Override
    public void setDocumentLocator(final Locator documentLocator) {
        if (this.documentHandler != null) {
            this.documentHandler.setDocumentLocator(documentLocator);
        }
    }
    
    @Override
    public void startDocument() throws SAXException {
        if (this.documentHandler != null) {
            this.documentHandler.startDocument();
        }
    }
    
    @Override
    public void endDocument() throws SAXException {
        if (this.documentHandler != null) {
            this.documentHandler.endDocument();
        }
    }
    
    @Override
    public void startPrefixMapping(final String s, final String s2) {
    }
    
    @Override
    public void endPrefixMapping(final String s) {
    }
    
    @Override
    public void startElement(final String s, final String s2, final String s3, final Attributes attributes) throws SAXException {
        if (this.documentHandler != null) {
            this.qAtts.setAttributes(attributes);
            this.documentHandler.startElement(s3, this.qAtts);
        }
    }
    
    @Override
    public void endElement(final String s, final String s2, final String s3) throws SAXException {
        if (this.documentHandler != null) {
            this.documentHandler.endElement(s3);
        }
    }
    
    @Override
    public void characters(final char[] array, final int n, final int n2) throws SAXException {
        if (this.documentHandler != null) {
            this.documentHandler.characters(array, n, n2);
        }
    }
    
    @Override
    public void ignorableWhitespace(final char[] array, final int n, final int n2) throws SAXException {
        if (this.documentHandler != null) {
            this.documentHandler.ignorableWhitespace(array, n, n2);
        }
    }
    
    @Override
    public void processingInstruction(final String s, final String s2) throws SAXException {
        if (this.documentHandler != null) {
            this.documentHandler.processingInstruction(s, s2);
        }
    }
    
    @Override
    public void skippedEntity(final String s) throws SAXException {
    }
    
    final class AttributesAdapter implements AttributeList
    {
        private Attributes attributes;
        
        void setAttributes(final Attributes attributes) {
            this.attributes = attributes;
        }
        
        @Override
        public int getLength() {
            return this.attributes.getLength();
        }
        
        @Override
        public String getName(final int n) {
            return this.attributes.getQName(n);
        }
        
        @Override
        public String getType(final int n) {
            return this.attributes.getType(n);
        }
        
        @Override
        public String getValue(final int n) {
            return this.attributes.getValue(n);
        }
        
        @Override
        public String getType(final String s) {
            return this.attributes.getType(s);
        }
        
        @Override
        public String getValue(final String s) {
            return this.attributes.getValue(s);
        }
    }
}
