package javax.xml.transform.stream;

import javax.xml.transform.*;
import java.io.*;

public class StreamSource implements Source
{
    public static final String FEATURE = "http://javax.xml.transform.stream.StreamSource/feature";
    private String publicId;
    private String systemId;
    private InputStream inputStream;
    private Reader reader;
    
    public StreamSource() {
    }
    
    public StreamSource(final InputStream inputStream) {
        this.setInputStream(inputStream);
    }
    
    public StreamSource(final InputStream inputStream, final String systemId) {
        this.setInputStream(inputStream);
        this.setSystemId(systemId);
    }
    
    public StreamSource(final Reader reader) {
        this.setReader(reader);
    }
    
    public StreamSource(final Reader reader, final String systemId) {
        this.setReader(reader);
        this.setSystemId(systemId);
    }
    
    public StreamSource(final String systemId) {
        this.systemId = systemId;
    }
    
    public StreamSource(final File file) {
        this.setSystemId(file.toURI().toASCIIString());
    }
    
    public void setInputStream(final InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    public InputStream getInputStream() {
        return this.inputStream;
    }
    
    public void setReader(final Reader reader) {
        this.reader = reader;
    }
    
    public Reader getReader() {
        return this.reader;
    }
    
    public void setPublicId(final String publicId) {
        this.publicId = publicId;
    }
    
    public String getPublicId() {
        return this.publicId;
    }
    
    @Override
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }
    
    @Override
    public String getSystemId() {
        return this.systemId;
    }
    
    public void setSystemId(final File file) {
        this.systemId = file.toURI().toASCIIString();
    }
}
