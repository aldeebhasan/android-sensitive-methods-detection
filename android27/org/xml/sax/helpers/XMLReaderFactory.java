package org.xml.sax.helpers;

import org.xml.sax.*;
import java.io.*;

public final class XMLReaderFactory
{
    private static final String property = "org.xml.sax.driver";
    private static SecuritySupport ss;
    private static String _clsFromJar;
    private static boolean _jarread;
    
    public static XMLReader createXMLReader() throws SAXException {
        String s = null;
        ClassLoader contextClassLoader = XMLReaderFactory.ss.getContextClassLoader();
        try {
            s = XMLReaderFactory.ss.getSystemProperty("org.xml.sax.driver");
        }
        catch (RuntimeException ex) {}
        if (s == null) {
            if (!XMLReaderFactory._jarread) {
                XMLReaderFactory._jarread = true;
                final String s2 = "META-INF/services/org.xml.sax.driver";
                try {
                    InputStream inputStream;
                    if (contextClassLoader != null) {
                        inputStream = XMLReaderFactory.ss.getResourceAsStream(contextClassLoader, s2);
                        if (inputStream == null) {
                            contextClassLoader = null;
                            inputStream = XMLReaderFactory.ss.getResourceAsStream(contextClassLoader, s2);
                        }
                    }
                    else {
                        inputStream = XMLReaderFactory.ss.getResourceAsStream(contextClassLoader, s2);
                    }
                    if (inputStream != null) {
                        XMLReaderFactory._clsFromJar = new BufferedReader(new InputStreamReader(inputStream, "UTF8")).readLine();
                        inputStream.close();
                    }
                }
                catch (Exception ex2) {}
            }
            s = XMLReaderFactory._clsFromJar;
        }
        if (s == null) {
            s = "com.sun.org.apache.xerces.internal.parsers.SAXParser";
        }
        if (s != null) {
            return loadClass(contextClassLoader, s);
        }
        try {
            return new ParserAdapter(ParserFactory.makeParser());
        }
        catch (Exception ex3) {
            throw new SAXException("Can't create default XMLReader; is system property org.xml.sax.driver set?");
        }
    }
    
    public static XMLReader createXMLReader(final String s) throws SAXException {
        return loadClass(XMLReaderFactory.ss.getContextClassLoader(), s);
    }
    
    private static XMLReader loadClass(final ClassLoader classLoader, final String s) throws SAXException {
        try {
            return (XMLReader)NewInstance.newInstance(classLoader, s);
        }
        catch (ClassNotFoundException ex) {
            throw new SAXException("SAX2 driver class " + s + " not found", ex);
        }
        catch (IllegalAccessException ex2) {
            throw new SAXException("SAX2 driver class " + s + " found but cannot be loaded", ex2);
        }
        catch (InstantiationException ex3) {
            throw new SAXException("SAX2 driver class " + s + " loaded but cannot be instantiated (no empty public constructor?)", ex3);
        }
        catch (ClassCastException ex4) {
            throw new SAXException("SAX2 driver class " + s + " does not implement XMLReader", ex4);
        }
    }
    
    static {
        XMLReaderFactory.ss = new SecuritySupport();
        XMLReaderFactory._clsFromJar = null;
        XMLReaderFactory._jarread = false;
    }
}
