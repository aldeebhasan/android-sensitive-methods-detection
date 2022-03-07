package org.xml.sax.helpers;

import java.io.*;
import org.xml.sax.*;

public class XMLFilterImpl implements XMLFilter, EntityResolver, DTDHandler, ContentHandler, ErrorHandler
{
    private XMLReader parent;
    private Locator locator;
    private EntityResolver entityResolver;
    private DTDHandler dtdHandler;
    private ContentHandler contentHandler;
    private ErrorHandler errorHandler;
    
    public XMLFilterImpl() {
        this.parent = null;
        this.locator = null;
        this.entityResolver = null;
        this.dtdHandler = null;
        this.contentHandler = null;
        this.errorHandler = null;
    }
    
    public XMLFilterImpl(final XMLReader parent) {
        this.parent = null;
        this.locator = null;
        this.entityResolver = null;
        this.dtdHandler = null;
        this.contentHandler = null;
        this.errorHandler = null;
        this.setParent(parent);
    }
    
    @Override
    public void setParent(final XMLReader parent) {
        this.parent = parent;
    }
    
    @Override
    public XMLReader getParent() {
        return this.parent;
    }
    
    @Override
    public void setFeature(final String s, final boolean b) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (this.parent != null) {
            this.parent.setFeature(s, b);
            return;
        }
        throw new SAXNotRecognizedException("Feature: " + s);
    }
    
    @Override
    public boolean getFeature(final String s) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (this.parent != null) {
            return this.parent.getFeature(s);
        }
        throw new SAXNotRecognizedException("Feature: " + s);
    }
    
    @Override
    public void setProperty(final String s, final Object o) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (this.parent != null) {
            this.parent.setProperty(s, o);
            return;
        }
        throw new SAXNotRecognizedException("Property: " + s);
    }
    
    @Override
    public Object getProperty(final String s) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (this.parent != null) {
            return this.parent.getProperty(s);
        }
        throw new SAXNotRecognizedException("Property: " + s);
    }
    
    @Override
    public void setEntityResolver(final EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }
    
    @Override
    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }
    
    @Override
    public void setDTDHandler(final DTDHandler dtdHandler) {
        this.dtdHandler = dtdHandler;
    }
    
    @Override
    public DTDHandler getDTDHandler() {
        return this.dtdHandler;
    }
    
    @Override
    public void setContentHandler(final ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }
    
    @Override
    public ContentHandler getContentHandler() {
        return this.contentHandler;
    }
    
    @Override
    public void setErrorHandler(final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
    
    @Override
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }
    
    @Override
    public void parse(final InputSource inputSource) throws SAXException, IOException {
        this.setupParse();
        this.parent.parse(inputSource);
    }
    
    @Override
    public void parse(final String s) throws SAXException, IOException {
        this.parse(new InputSource(s));
    }
    
    @Override
    public InputSource resolveEntity(final String s, final String s2) throws SAXException, IOException {
        if (this.entityResolver != null) {
            return this.entityResolver.resolveEntity(s, s2);
        }
        return null;
    }
    
    @Override
    public void notationDecl(final String s, final String s2, final String s3) throws SAXException {
        if (this.dtdHandler != null) {
            this.dtdHandler.notationDecl(s, s2, s3);
        }
    }
    
    @Override
    public void unparsedEntityDecl(final String s, final String s2, final String s3, final String s4) throws SAXException {
        if (this.dtdHandler != null) {
            this.dtdHandler.unparsedEntityDecl(s, s2, s3, s4);
        }
    }
    
    @Override
    public void setDocumentLocator(final Locator locator) {
        this.locator = locator;
        if (this.contentHandler != null) {
            this.contentHandler.setDocumentLocator(locator);
        }
    }
    
    @Override
    public void startDocument() throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.startDocument();
        }
    }
    
    @Override
    public void endDocument() throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.endDocument();
        }
    }
    
    @Override
    public void startPrefixMapping(final String s, final String s2) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.startPrefixMapping(s, s2);
        }
    }
    
    @Override
    public void endPrefixMapping(final String s) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.endPrefixMapping(s);
        }
    }
    
    @Override
    public void startElement(final String s, final String s2, final String s3, final Attributes attributes) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.startElement(s, s2, s3, attributes);
        }
    }
    
    @Override
    public void endElement(final String s, final String s2, final String s3) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.endElement(s, s2, s3);
        }
    }
    
    @Override
    public void characters(final char[] array, final int n, final int n2) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.characters(array, n, n2);
        }
    }
    
    @Override
    public void ignorableWhitespace(final char[] array, final int n, final int n2) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.ignorableWhitespace(array, n, n2);
        }
    }
    
    @Override
    public void processingInstruction(final String s, final String s2) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.processingInstruction(s, s2);
        }
    }
    
    @Override
    public void skippedEntity(final String s) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.skippedEntity(s);
        }
    }
    
    @Override
    public void warning(final SAXParseException ex) throws SAXException {
        if (this.errorHandler != null) {
            this.errorHandler.warning(ex);
        }
    }
    
    @Override
    public void error(final SAXParseException ex) throws SAXException {
        if (this.errorHandler != null) {
            this.errorHandler.error(ex);
        }
    }
    
    @Override
    public void fatalError(final SAXParseException ex) throws SAXException {
        if (this.errorHandler != null) {
            this.errorHandler.fatalError(ex);
        }
    }
    
    private void setupParse() {
        if (this.parent == null) {
            throw new NullPointerException("No parent for filter");
        }
        this.parent.setEntityResolver(this);
        this.parent.setDTDHandler(this);
        this.parent.setContentHandler(this);
        this.parent.setErrorHandler(this);
    }
}
