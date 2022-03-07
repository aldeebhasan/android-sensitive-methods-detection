package javax.xml.parsers;

import org.xml.sax.helpers.*;
import java.io.*;
import org.xml.sax.*;
import javax.xml.validation.*;

public abstract class SAXParser
{
    public void reset() {
        throw new UnsupportedOperationException("This SAXParser, \"" + this.getClass().getName() + "\", does not support the reset functionality.  Specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\" version \"" + this.getClass().getPackage().getSpecificationVersion() + "\"");
    }
    
    public void parse(final InputStream inputStream, final HandlerBase handlerBase) throws SAXException, IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        this.parse(new InputSource(inputStream), handlerBase);
    }
    
    public void parse(final InputStream inputStream, final HandlerBase handlerBase, final String systemId) throws SAXException, IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        final InputSource inputSource = new InputSource(inputStream);
        inputSource.setSystemId(systemId);
        this.parse(inputSource, handlerBase);
    }
    
    public void parse(final InputStream inputStream, final DefaultHandler defaultHandler) throws SAXException, IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        this.parse(new InputSource(inputStream), defaultHandler);
    }
    
    public void parse(final InputStream inputStream, final DefaultHandler defaultHandler, final String systemId) throws SAXException, IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        final InputSource inputSource = new InputSource(inputStream);
        inputSource.setSystemId(systemId);
        this.parse(inputSource, defaultHandler);
    }
    
    public void parse(final String s, final HandlerBase handlerBase) throws SAXException, IOException {
        if (s == null) {
            throw new IllegalArgumentException("uri cannot be null");
        }
        this.parse(new InputSource(s), handlerBase);
    }
    
    public void parse(final String s, final DefaultHandler defaultHandler) throws SAXException, IOException {
        if (s == null) {
            throw new IllegalArgumentException("uri cannot be null");
        }
        this.parse(new InputSource(s), defaultHandler);
    }
    
    public void parse(final File file, final HandlerBase handlerBase) throws SAXException, IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.parse(new InputSource(file.toURI().toASCIIString()), handlerBase);
    }
    
    public void parse(final File file, final DefaultHandler defaultHandler) throws SAXException, IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.parse(new InputSource(file.toURI().toASCIIString()), defaultHandler);
    }
    
    public void parse(final InputSource inputSource, final HandlerBase handlerBase) throws SAXException, IOException {
        if (inputSource == null) {
            throw new IllegalArgumentException("InputSource cannot be null");
        }
        final Parser parser = this.getParser();
        if (handlerBase != null) {
            parser.setDocumentHandler(handlerBase);
            parser.setEntityResolver(handlerBase);
            parser.setErrorHandler(handlerBase);
            parser.setDTDHandler(handlerBase);
        }
        parser.parse(inputSource);
    }
    
    public void parse(final InputSource inputSource, final DefaultHandler defaultHandler) throws SAXException, IOException {
        if (inputSource == null) {
            throw new IllegalArgumentException("InputSource cannot be null");
        }
        final XMLReader xmlReader = this.getXMLReader();
        if (defaultHandler != null) {
            xmlReader.setContentHandler(defaultHandler);
            xmlReader.setEntityResolver(defaultHandler);
            xmlReader.setErrorHandler(defaultHandler);
            xmlReader.setDTDHandler(defaultHandler);
        }
        xmlReader.parse(inputSource);
    }
    
    public abstract Parser getParser() throws SAXException;
    
    public abstract XMLReader getXMLReader() throws SAXException;
    
    public abstract boolean isNamespaceAware();
    
    public abstract boolean isValidating();
    
    public abstract void setProperty(final String p0, final Object p1) throws SAXNotRecognizedException, SAXNotSupportedException;
    
    public abstract Object getProperty(final String p0) throws SAXNotRecognizedException, SAXNotSupportedException;
    
    public Schema getSchema() {
        throw new UnsupportedOperationException("This parser does not support specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\" version \"" + this.getClass().getPackage().getSpecificationVersion() + "\"");
    }
    
    public boolean isXIncludeAware() {
        throw new UnsupportedOperationException("This parser does not support specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\" version \"" + this.getClass().getPackage().getSpecificationVersion() + "\"");
    }
}
