package org.xml.sax;

public class SAXNotRecognizedException extends SAXException
{
    static final long serialVersionUID = 5440506620509557213L;
    
    public SAXNotRecognizedException() {
    }
    
    public SAXNotRecognizedException(final String s) {
        super(s);
    }
}
