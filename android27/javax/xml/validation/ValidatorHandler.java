package javax.xml.validation;

import org.w3c.dom.ls.*;
import org.xml.sax.*;

public abstract class ValidatorHandler implements ContentHandler
{
    public abstract void setContentHandler(final ContentHandler p0);
    
    public abstract ContentHandler getContentHandler();
    
    public abstract void setErrorHandler(final ErrorHandler p0);
    
    public abstract ErrorHandler getErrorHandler();
    
    public abstract void setResourceResolver(final LSResourceResolver p0);
    
    public abstract LSResourceResolver getResourceResolver();
    
    public abstract TypeInfoProvider getTypeInfoProvider();
    
    public boolean getFeature(final String s) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (s == null) {
            throw new NullPointerException();
        }
        throw new SAXNotRecognizedException(s);
    }
    
    public void setFeature(final String s, final boolean b) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (s == null) {
            throw new NullPointerException();
        }
        throw new SAXNotRecognizedException(s);
    }
    
    public void setProperty(final String s, final Object o) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (s == null) {
            throw new NullPointerException();
        }
        throw new SAXNotRecognizedException(s);
    }
    
    public Object getProperty(final String s) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (s == null) {
            throw new NullPointerException();
        }
        throw new SAXNotRecognizedException(s);
    }
}
