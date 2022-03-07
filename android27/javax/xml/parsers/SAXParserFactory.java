package javax.xml.parsers;

import org.xml.sax.*;
import javax.xml.validation.*;

public abstract class SAXParserFactory
{
    private boolean validating;
    private boolean namespaceAware;
    
    protected SAXParserFactory() {
        this.validating = false;
        this.namespaceAware = false;
    }
    
    public static SAXParserFactory newInstance() {
        return FactoryFinder.find(SAXParserFactory.class, "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
    }
    
    public static SAXParserFactory newInstance(final String s, final ClassLoader classLoader) {
        return FactoryFinder.newInstance(SAXParserFactory.class, s, classLoader, false);
    }
    
    public abstract SAXParser newSAXParser() throws ParserConfigurationException, SAXException;
    
    public void setNamespaceAware(final boolean namespaceAware) {
        this.namespaceAware = namespaceAware;
    }
    
    public void setValidating(final boolean validating) {
        this.validating = validating;
    }
    
    public boolean isNamespaceAware() {
        return this.namespaceAware;
    }
    
    public boolean isValidating() {
        return this.validating;
    }
    
    public abstract void setFeature(final String p0, final boolean p1) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException;
    
    public abstract boolean getFeature(final String p0) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException;
    
    public Schema getSchema() {
        throw new UnsupportedOperationException("This parser does not support specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\" version \"" + this.getClass().getPackage().getSpecificationVersion() + "\"");
    }
    
    public void setSchema(final Schema schema) {
        throw new UnsupportedOperationException("This parser does not support specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\" version \"" + this.getClass().getPackage().getSpecificationVersion() + "\"");
    }
    
    public void setXIncludeAware(final boolean b) {
        if (b) {
            throw new UnsupportedOperationException(" setXIncludeAware is not supported on this JAXP implementation or earlier: " + this.getClass());
        }
    }
    
    public boolean isXIncludeAware() {
        throw new UnsupportedOperationException("This parser does not support specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\" version \"" + this.getClass().getPackage().getSpecificationVersion() + "\"");
    }
}
