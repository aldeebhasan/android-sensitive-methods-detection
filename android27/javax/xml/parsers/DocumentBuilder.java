package javax.xml.parsers;

import java.io.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.validation.*;

public abstract class DocumentBuilder
{
    public void reset() {
        throw new UnsupportedOperationException("This DocumentBuilder, \"" + this.getClass().getName() + "\", does not support the reset functionality.  Specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\" version \"" + this.getClass().getPackage().getSpecificationVersion() + "\"");
    }
    
    public Document parse(final InputStream inputStream) throws SAXException, IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        return this.parse(new InputSource(inputStream));
    }
    
    public Document parse(final InputStream inputStream, final String systemId) throws SAXException, IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }
        final InputSource inputSource = new InputSource(inputStream);
        inputSource.setSystemId(systemId);
        return this.parse(inputSource);
    }
    
    public Document parse(final String s) throws SAXException, IOException {
        if (s == null) {
            throw new IllegalArgumentException("URI cannot be null");
        }
        return this.parse(new InputSource(s));
    }
    
    public Document parse(final File file) throws SAXException, IOException {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        return this.parse(new InputSource(file.toURI().toASCIIString()));
    }
    
    public abstract Document parse(final InputSource p0) throws SAXException, IOException;
    
    public abstract boolean isNamespaceAware();
    
    public abstract boolean isValidating();
    
    public abstract void setEntityResolver(final EntityResolver p0);
    
    public abstract void setErrorHandler(final ErrorHandler p0);
    
    public abstract Document newDocument();
    
    public abstract DOMImplementation getDOMImplementation();
    
    public Schema getSchema() {
        throw new UnsupportedOperationException("This parser does not support specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\" version \"" + this.getClass().getPackage().getSpecificationVersion() + "\"");
    }
    
    public boolean isXIncludeAware() {
        throw new UnsupportedOperationException("This parser does not support specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\" version \"" + this.getClass().getPackage().getSpecificationVersion() + "\"");
    }
}
