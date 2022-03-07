package org.xmlpull.v1;

public class XmlPullParserException extends Exception
{
    protected int column;
    protected Throwable detail;
    protected int row;
    
    public XmlPullParserException(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public XmlPullParserException(final String msg, final XmlPullParser parser, final Throwable chain) {
        throw new RuntimeException("Stub!");
    }
    
    public Throwable getDetail() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLineNumber() {
        throw new RuntimeException("Stub!");
    }
    
    public int getColumnNumber() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void printStackTrace() {
        throw new RuntimeException("Stub!");
    }
}
