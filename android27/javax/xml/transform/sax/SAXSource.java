package javax.xml.transform.sax;

import javax.xml.transform.*;
import org.xml.sax.*;
import javax.xml.transform.stream.*;

public class SAXSource implements Source
{
    public static final String FEATURE = "http://javax.xml.transform.sax.SAXSource/feature";
    private XMLReader reader;
    private InputSource inputSource;
    
    public SAXSource() {
    }
    
    public SAXSource(final XMLReader reader, final InputSource inputSource) {
        this.reader = reader;
        this.inputSource = inputSource;
    }
    
    public SAXSource(final InputSource inputSource) {
        this.inputSource = inputSource;
    }
    
    public void setXMLReader(final XMLReader reader) {
        this.reader = reader;
    }
    
    public XMLReader getXMLReader() {
        return this.reader;
    }
    
    public void setInputSource(final InputSource inputSource) {
        this.inputSource = inputSource;
    }
    
    public InputSource getInputSource() {
        return this.inputSource;
    }
    
    @Override
    public void setSystemId(final String systemId) {
        if (null == this.inputSource) {
            this.inputSource = new InputSource(systemId);
        }
        else {
            this.inputSource.setSystemId(systemId);
        }
    }
    
    @Override
    public String getSystemId() {
        if (this.inputSource == null) {
            return null;
        }
        return this.inputSource.getSystemId();
    }
    
    public static InputSource sourceToInputSource(final Source source) {
        if (source instanceof SAXSource) {
            return ((SAXSource)source).getInputSource();
        }
        if (source instanceof StreamSource) {
            final StreamSource streamSource = (StreamSource)source;
            final InputSource inputSource = new InputSource(streamSource.getSystemId());
            inputSource.setByteStream(streamSource.getInputStream());
            inputSource.setCharacterStream(streamSource.getReader());
            inputSource.setPublicId(streamSource.getPublicId());
            return inputSource;
        }
        return null;
    }
}
