package org.xml.sax;

import java.io.*;

public class InputSource
{
    private String publicId;
    private String systemId;
    private InputStream byteStream;
    private String encoding;
    private Reader characterStream;
    
    public InputSource() {
    }
    
    public InputSource(final String systemId) {
        this.setSystemId(systemId);
    }
    
    public InputSource(final InputStream byteStream) {
        this.setByteStream(byteStream);
    }
    
    public InputSource(final Reader characterStream) {
        this.setCharacterStream(characterStream);
    }
    
    public void setPublicId(final String publicId) {
        this.publicId = publicId;
    }
    
    public String getPublicId() {
        return this.publicId;
    }
    
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }
    
    public String getSystemId() {
        return this.systemId;
    }
    
    public void setByteStream(final InputStream byteStream) {
        this.byteStream = byteStream;
    }
    
    public InputStream getByteStream() {
        return this.byteStream;
    }
    
    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }
    
    public String getEncoding() {
        return this.encoding;
    }
    
    public void setCharacterStream(final Reader characterStream) {
        this.characterStream = characterStream;
    }
    
    public Reader getCharacterStream() {
        return this.characterStream;
    }
}
