package android.util;

import org.xml.sax.*;
import org.xmlpull.v1.*;
import java.io.*;

public class Xml
{
    public static String FEATURE_RELAXED;
    
    Xml() {
        throw new RuntimeException("Stub!");
    }
    
    public static void parse(final String xml, final ContentHandler contentHandler) throws SAXException {
        throw new RuntimeException("Stub!");
    }
    
    public static void parse(final Reader in, final ContentHandler contentHandler) throws IOException, SAXException {
        throw new RuntimeException("Stub!");
    }
    
    public static void parse(final InputStream in, final Encoding encoding, final ContentHandler contentHandler) throws IOException, SAXException {
        throw new RuntimeException("Stub!");
    }
    
    public static XmlPullParser newPullParser() {
        throw new RuntimeException("Stub!");
    }
    
    public static XmlSerializer newSerializer() {
        throw new RuntimeException("Stub!");
    }
    
    public static Encoding findEncodingByName(final String encodingName) throws UnsupportedEncodingException {
        throw new RuntimeException("Stub!");
    }
    
    public static AttributeSet asAttributeSet(final XmlPullParser parser) {
        throw new RuntimeException("Stub!");
    }
    
    public enum Encoding
    {
        ISO_8859_1, 
        US_ASCII, 
        UTF_16, 
        UTF_8;
    }
}
