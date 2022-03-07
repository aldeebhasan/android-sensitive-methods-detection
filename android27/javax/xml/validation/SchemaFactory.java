package javax.xml.validation;

import org.w3c.dom.ls.*;
import javax.xml.transform.*;
import org.xml.sax.*;
import java.io.*;
import javax.xml.transform.stream.*;
import java.net.*;

public abstract class SchemaFactory
{
    private static SecuritySupport ss;
    
    public static SchemaFactory newInstance(final String s) {
        ClassLoader classLoader = SchemaFactory.ss.getContextClassLoader();
        if (classLoader == null) {
            classLoader = SchemaFactory.class.getClassLoader();
        }
        final SchemaFactory factory = new SchemaFactoryFinder(classLoader).newFactory(s);
        if (factory == null) {
            throw new IllegalArgumentException("No SchemaFactory that implements the schema language specified by: " + s + " could be loaded");
        }
        return factory;
    }
    
    public static SchemaFactory newInstance(final String s, final String s2, final ClassLoader classLoader) {
        ClassLoader contextClassLoader = classLoader;
        if (contextClassLoader == null) {
            contextClassLoader = SchemaFactory.ss.getContextClassLoader();
        }
        final SchemaFactory instance = new SchemaFactoryFinder(contextClassLoader).createInstance(s2);
        if (instance == null) {
            throw new IllegalArgumentException("Factory " + s2 + " could not be loaded to implement the schema language specified by: " + s);
        }
        if (instance.isSchemaLanguageSupported(s)) {
            return instance;
        }
        throw new IllegalArgumentException("Factory " + instance.getClass().getName() + " does not implement the schema language specified by: " + s);
    }
    
    public abstract boolean isSchemaLanguageSupported(final String p0);
    
    public boolean getFeature(final String s) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (s == null) {
            throw new NullPointerException("the name parameter is null");
        }
        throw new SAXNotRecognizedException(s);
    }
    
    public void setFeature(final String s, final boolean b) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (s == null) {
            throw new NullPointerException("the name parameter is null");
        }
        throw new SAXNotRecognizedException(s);
    }
    
    public void setProperty(final String s, final Object o) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (s == null) {
            throw new NullPointerException("the name parameter is null");
        }
        throw new SAXNotRecognizedException(s);
    }
    
    public Object getProperty(final String s) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (s == null) {
            throw new NullPointerException("the name parameter is null");
        }
        throw new SAXNotRecognizedException(s);
    }
    
    public abstract void setErrorHandler(final ErrorHandler p0);
    
    public abstract ErrorHandler getErrorHandler();
    
    public abstract void setResourceResolver(final LSResourceResolver p0);
    
    public abstract LSResourceResolver getResourceResolver();
    
    public Schema newSchema(final Source source) throws SAXException {
        return this.newSchema(new Source[] { source });
    }
    
    public Schema newSchema(final File file) throws SAXException {
        return this.newSchema(new StreamSource(file));
    }
    
    public Schema newSchema(final URL url) throws SAXException {
        return this.newSchema(new StreamSource(url.toExternalForm()));
    }
    
    public abstract Schema newSchema(final Source[] p0) throws SAXException;
    
    public abstract Schema newSchema() throws SAXException;
    
    static {
        SchemaFactory.ss = new SecuritySupport();
    }
}
