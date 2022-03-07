package org.xml.sax.helpers;

import java.io.*;
import org.xml.sax.*;
import java.util.*;

public class ParserAdapter implements XMLReader, DocumentHandler
{
    private static SecuritySupport ss;
    private static final String FEATURES = "http://xml.org/sax/features/";
    private static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
    private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
    private static final String XMLNS_URIs = "http://xml.org/sax/features/xmlns-uris";
    private NamespaceSupport nsSupport;
    private AttributeListAdapter attAdapter;
    private boolean parsing;
    private String[] nameParts;
    private Parser parser;
    private AttributesImpl atts;
    private boolean namespaces;
    private boolean prefixes;
    private boolean uris;
    Locator locator;
    EntityResolver entityResolver;
    DTDHandler dtdHandler;
    ContentHandler contentHandler;
    ErrorHandler errorHandler;
    
    public ParserAdapter() throws SAXException {
        this.parsing = false;
        this.nameParts = new String[3];
        this.parser = null;
        this.atts = null;
        this.namespaces = true;
        this.prefixes = false;
        this.uris = false;
        this.entityResolver = null;
        this.dtdHandler = null;
        this.contentHandler = null;
        this.errorHandler = null;
        final String systemProperty = ParserAdapter.ss.getSystemProperty("org.xml.sax.parser");
        try {
            this.setup(ParserFactory.makeParser());
        }
        catch (ClassNotFoundException ex) {
            throw new SAXException("Cannot find SAX1 driver class " + systemProperty, ex);
        }
        catch (IllegalAccessException ex2) {
            throw new SAXException("SAX1 driver class " + systemProperty + " found but cannot be loaded", ex2);
        }
        catch (InstantiationException ex3) {
            throw new SAXException("SAX1 driver class " + systemProperty + " loaded but cannot be instantiated", ex3);
        }
        catch (ClassCastException ex4) {
            throw new SAXException("SAX1 driver class " + systemProperty + " does not implement org.xml.sax.Parser");
        }
        catch (NullPointerException ex5) {
            throw new SAXException("System property org.xml.sax.parser not specified");
        }
    }
    
    public ParserAdapter(final Parser parser) {
        this.parsing = false;
        this.nameParts = new String[3];
        this.parser = null;
        this.atts = null;
        this.namespaces = true;
        this.prefixes = false;
        this.uris = false;
        this.entityResolver = null;
        this.dtdHandler = null;
        this.contentHandler = null;
        this.errorHandler = null;
        this.setup(parser);
    }
    
    private void setup(final Parser parser) {
        if (parser == null) {
            throw new NullPointerException("Parser argument must not be null");
        }
        this.parser = parser;
        this.atts = new AttributesImpl();
        this.nsSupport = new NamespaceSupport();
        this.attAdapter = new AttributeListAdapter();
    }
    
    @Override
    public void setFeature(final String s, final boolean uris) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (s.equals("http://xml.org/sax/features/namespaces")) {
            this.checkNotParsing("feature", s);
            this.namespaces = uris;
            if (!this.namespaces && !this.prefixes) {
                this.prefixes = true;
            }
        }
        else if (s.equals("http://xml.org/sax/features/namespace-prefixes")) {
            this.checkNotParsing("feature", s);
            this.prefixes = uris;
            if (!this.prefixes && !this.namespaces) {
                this.namespaces = true;
            }
        }
        else {
            if (!s.equals("http://xml.org/sax/features/xmlns-uris")) {
                throw new SAXNotRecognizedException("Feature: " + s);
            }
            this.checkNotParsing("feature", s);
            this.uris = uris;
        }
    }
    
    @Override
    public boolean getFeature(final String s) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (s.equals("http://xml.org/sax/features/namespaces")) {
            return this.namespaces;
        }
        if (s.equals("http://xml.org/sax/features/namespace-prefixes")) {
            return this.prefixes;
        }
        if (s.equals("http://xml.org/sax/features/xmlns-uris")) {
            return this.uris;
        }
        throw new SAXNotRecognizedException("Feature: " + s);
    }
    
    @Override
    public void setProperty(final String s, final Object o) throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new SAXNotRecognizedException("Property: " + s);
    }
    
    @Override
    public Object getProperty(final String s) throws SAXNotRecognizedException, SAXNotSupportedException {
        throw new SAXNotRecognizedException("Property: " + s);
    }
    
    @Override
    public void setEntityResolver(final EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }
    
    @Override
    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }
    
    @Override
    public void setDTDHandler(final DTDHandler dtdHandler) {
        this.dtdHandler = dtdHandler;
    }
    
    @Override
    public DTDHandler getDTDHandler() {
        return this.dtdHandler;
    }
    
    @Override
    public void setContentHandler(final ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }
    
    @Override
    public ContentHandler getContentHandler() {
        return this.contentHandler;
    }
    
    @Override
    public void setErrorHandler(final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
    
    @Override
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }
    
    @Override
    public void parse(final String s) throws IOException, SAXException {
        this.parse(new InputSource(s));
    }
    
    @Override
    public void parse(final InputSource inputSource) throws IOException, SAXException {
        if (this.parsing) {
            throw new SAXException("Parser is already in use");
        }
        this.setupParser();
        this.parsing = true;
        try {
            this.parser.parse(inputSource);
        }
        finally {
            this.parsing = false;
        }
        this.parsing = false;
    }
    
    @Override
    public void setDocumentLocator(final Locator locator) {
        this.locator = locator;
        if (this.contentHandler != null) {
            this.contentHandler.setDocumentLocator(locator);
        }
    }
    
    @Override
    public void startDocument() throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.startDocument();
        }
    }
    
    @Override
    public void endDocument() throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.endDocument();
        }
    }
    
    @Override
    public void startElement(final String s, final AttributeList attributeList) throws SAXException {
        Vector<SAXParseException> vector = null;
        if (!this.namespaces) {
            if (this.contentHandler != null) {
                this.attAdapter.setAttributeList(attributeList);
                this.contentHandler.startElement("", "", s.intern(), this.attAdapter);
            }
            return;
        }
        this.nsSupport.pushContext();
        final int length = attributeList.getLength();
        for (int i = 0; i < length; ++i) {
            final String name = attributeList.getName(i);
            if (name.startsWith("xmlns")) {
                final int index = name.indexOf(58);
                String substring;
                if (index == -1 && name.length() == 5) {
                    substring = "";
                }
                else {
                    if (index != 5) {
                        continue;
                    }
                    substring = name.substring(index + 1);
                }
                final String value = attributeList.getValue(i);
                if (!this.nsSupport.declarePrefix(substring, value)) {
                    this.reportError("Illegal Namespace prefix: " + substring);
                }
                else if (this.contentHandler != null) {
                    this.contentHandler.startPrefixMapping(substring, value);
                }
            }
        }
        this.atts.clear();
        for (int j = 0; j < length; ++j) {
            final String name2 = attributeList.getName(j);
            final String type = attributeList.getType(j);
            final String value2 = attributeList.getValue(j);
            if (name2.startsWith("xmlns")) {
                final int index2 = name2.indexOf(58);
                String substring2;
                if (index2 == -1 && name2.length() == 5) {
                    substring2 = "";
                }
                else if (index2 != 5) {
                    substring2 = null;
                }
                else {
                    substring2 = name2.substring(6);
                }
                if (substring2 != null) {
                    if (!this.prefixes) {
                        continue;
                    }
                    if (this.uris) {
                        final AttributesImpl atts = this.atts;
                        final NamespaceSupport nsSupport = this.nsSupport;
                        atts.addAttribute("http://www.w3.org/XML/1998/namespace", substring2, name2.intern(), type, value2);
                        continue;
                    }
                    this.atts.addAttribute("", "", name2.intern(), type, value2);
                    continue;
                }
            }
            try {
                final String[] processName = this.processName(name2, true, true);
                this.atts.addAttribute(processName[0], processName[1], processName[2], type, value2);
            }
            catch (SAXException ex) {
                if (vector == null) {
                    vector = new Vector<SAXParseException>();
                }
                vector.addElement((SAXParseException)ex);
                this.atts.addAttribute("", name2, name2, type, value2);
            }
        }
        if (vector != null && this.errorHandler != null) {
            for (int k = 0; k < vector.size(); ++k) {
                this.errorHandler.error(vector.elementAt(k));
            }
        }
        if (this.contentHandler != null) {
            final String[] processName2 = this.processName(s, false, false);
            this.contentHandler.startElement(processName2[0], processName2[1], processName2[2], this.atts);
        }
    }
    
    @Override
    public void endElement(final String s) throws SAXException {
        if (!this.namespaces) {
            if (this.contentHandler != null) {
                this.contentHandler.endElement("", "", s.intern());
            }
            return;
        }
        final String[] processName = this.processName(s, false, false);
        if (this.contentHandler != null) {
            this.contentHandler.endElement(processName[0], processName[1], processName[2]);
            final Enumeration declaredPrefixes = this.nsSupport.getDeclaredPrefixes();
            while (declaredPrefixes.hasMoreElements()) {
                this.contentHandler.endPrefixMapping(declaredPrefixes.nextElement());
            }
        }
        this.nsSupport.popContext();
    }
    
    @Override
    public void characters(final char[] array, final int n, final int n2) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.characters(array, n, n2);
        }
    }
    
    @Override
    public void ignorableWhitespace(final char[] array, final int n, final int n2) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.ignorableWhitespace(array, n, n2);
        }
    }
    
    @Override
    public void processingInstruction(final String s, final String s2) throws SAXException {
        if (this.contentHandler != null) {
            this.contentHandler.processingInstruction(s, s2);
        }
    }
    
    private void setupParser() {
        if (!this.prefixes && !this.namespaces) {
            throw new IllegalStateException();
        }
        this.nsSupport.reset();
        if (this.uris) {
            this.nsSupport.setNamespaceDeclUris(true);
        }
        if (this.entityResolver != null) {
            this.parser.setEntityResolver(this.entityResolver);
        }
        if (this.dtdHandler != null) {
            this.parser.setDTDHandler(this.dtdHandler);
        }
        if (this.errorHandler != null) {
            this.parser.setErrorHandler(this.errorHandler);
        }
        this.parser.setDocumentHandler(this);
        this.locator = null;
    }
    
    private String[] processName(final String s, final boolean b, final boolean b2) throws SAXException {
        String[] processName = this.nsSupport.processName(s, this.nameParts, b);
        if (processName == null) {
            if (b2) {
                throw this.makeException("Undeclared prefix: " + s);
            }
            this.reportError("Undeclared prefix: " + s);
            processName = new String[3];
            processName[0] = (processName[1] = "");
            processName[2] = s.intern();
        }
        return processName;
    }
    
    void reportError(final String s) throws SAXException {
        if (this.errorHandler != null) {
            this.errorHandler.error(this.makeException(s));
        }
    }
    
    private SAXParseException makeException(final String s) {
        if (this.locator != null) {
            return new SAXParseException(s, this.locator);
        }
        return new SAXParseException(s, null, null, -1, -1);
    }
    
    private void checkNotParsing(final String s, final String s2) throws SAXNotSupportedException {
        if (this.parsing) {
            throw new SAXNotSupportedException("Cannot change " + s + ' ' + s2 + " while parsing");
        }
    }
    
    static {
        ParserAdapter.ss = new SecuritySupport();
    }
    
    final class AttributeListAdapter implements Attributes
    {
        private AttributeList qAtts;
        
        void setAttributeList(final AttributeList qAtts) {
            this.qAtts = qAtts;
        }
        
        @Override
        public int getLength() {
            return this.qAtts.getLength();
        }
        
        @Override
        public String getURI(final int n) {
            return "";
        }
        
        @Override
        public String getLocalName(final int n) {
            return "";
        }
        
        @Override
        public String getQName(final int n) {
            return this.qAtts.getName(n).intern();
        }
        
        @Override
        public String getType(final int n) {
            return this.qAtts.getType(n).intern();
        }
        
        @Override
        public String getValue(final int n) {
            return this.qAtts.getValue(n);
        }
        
        @Override
        public int getIndex(final String s, final String s2) {
            return -1;
        }
        
        @Override
        public int getIndex(final String s) {
            for (int length = ParserAdapter.this.atts.getLength(), i = 0; i < length; ++i) {
                if (this.qAtts.getName(i).equals(s)) {
                    return i;
                }
            }
            return -1;
        }
        
        @Override
        public String getType(final String s, final String s2) {
            return null;
        }
        
        @Override
        public String getType(final String s) {
            return this.qAtts.getType(s).intern();
        }
        
        @Override
        public String getValue(final String s, final String s2) {
            return null;
        }
        
        @Override
        public String getValue(final String s) {
            return this.qAtts.getValue(s);
        }
    }
}
