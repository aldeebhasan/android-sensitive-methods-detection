package javax.xml.validation;

import javax.xml.transform.*;
import java.io.*;
import org.w3c.dom.ls.*;
import org.xml.sax.*;

public abstract class Validator
{
    public abstract void reset();
    
    public void validate(final Source source) throws SAXException, IOException {
        this.validate(source, null);
    }
    
    public abstract void validate(final Source p0, final Result p1) throws SAXException, IOException;
    
    public abstract void setErrorHandler(final ErrorHandler p0);
    
    public abstract ErrorHandler getErrorHandler();
    
    public abstract void setResourceResolver(final LSResourceResolver p0);
    
    public abstract LSResourceResolver getResourceResolver();
    
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
}
