package javax.xml.transform.stream;

import javax.xml.transform.*;
import java.io.*;

public class StreamResult implements Result
{
    public static final String FEATURE = "http://javax.xml.transform.stream.StreamResult/feature";
    private String systemId;
    private OutputStream outputStream;
    private Writer writer;
    
    public StreamResult() {
    }
    
    public StreamResult(final OutputStream outputStream) {
        this.setOutputStream(outputStream);
    }
    
    public StreamResult(final Writer writer) {
        this.setWriter(writer);
    }
    
    public StreamResult(final String systemId) {
        this.systemId = systemId;
    }
    
    public StreamResult(final File file) {
        this.setSystemId(file.toURI().toASCIIString());
    }
    
    public void setOutputStream(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    
    public OutputStream getOutputStream() {
        return this.outputStream;
    }
    
    public void setWriter(final Writer writer) {
        this.writer = writer;
    }
    
    public Writer getWriter() {
        return this.writer;
    }
    
    @Override
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }
    
    public void setSystemId(final File file) {
        this.systemId = file.toURI().toASCIIString();
    }
    
    @Override
    public String getSystemId() {
        return this.systemId;
    }
}
