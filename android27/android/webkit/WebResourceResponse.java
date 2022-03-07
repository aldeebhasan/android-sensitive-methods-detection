package android.webkit;

import java.io.*;
import java.util.*;

public class WebResourceResponse
{
    public WebResourceResponse(final String mimeType, final String encoding, final InputStream data) {
        throw new RuntimeException("Stub!");
    }
    
    public WebResourceResponse(final String mimeType, final String encoding, final int statusCode, final String reasonPhrase, final Map<String, String> responseHeaders, final InputStream data) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMimeType(final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public String getMimeType() {
        throw new RuntimeException("Stub!");
    }
    
    public void setEncoding(final String encoding) {
        throw new RuntimeException("Stub!");
    }
    
    public String getEncoding() {
        throw new RuntimeException("Stub!");
    }
    
    public void setStatusCodeAndReasonPhrase(final int statusCode, final String reasonPhrase) {
        throw new RuntimeException("Stub!");
    }
    
    public int getStatusCode() {
        throw new RuntimeException("Stub!");
    }
    
    public String getReasonPhrase() {
        throw new RuntimeException("Stub!");
    }
    
    public void setResponseHeaders(final Map<String, String> headers) {
        throw new RuntimeException("Stub!");
    }
    
    public Map<String, String> getResponseHeaders() {
        throw new RuntimeException("Stub!");
    }
    
    public void setData(final InputStream data) {
        throw new RuntimeException("Stub!");
    }
    
    public InputStream getData() {
        throw new RuntimeException("Stub!");
    }
}
