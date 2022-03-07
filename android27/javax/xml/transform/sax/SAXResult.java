package javax.xml.transform.sax;

import javax.xml.transform.*;
import org.xml.sax.*;
import org.xml.sax.ext.*;

public class SAXResult implements Result
{
    public static final String FEATURE = "http://javax.xml.transform.sax.SAXResult/feature";
    private ContentHandler handler;
    private LexicalHandler lexhandler;
    private String systemId;
    
    public SAXResult() {
    }
    
    public SAXResult(final ContentHandler handler) {
        this.setHandler(handler);
    }
    
    public void setHandler(final ContentHandler handler) {
        this.handler = handler;
    }
    
    public ContentHandler getHandler() {
        return this.handler;
    }
    
    public void setLexicalHandler(final LexicalHandler lexhandler) {
        this.lexhandler = lexhandler;
    }
    
    public LexicalHandler getLexicalHandler() {
        return this.lexhandler;
    }
    
    @Override
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }
    
    @Override
    public String getSystemId() {
        return this.systemId;
    }
}
