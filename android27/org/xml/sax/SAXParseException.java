package org.xml.sax;

public class SAXParseException extends SAXException
{
    private String publicId;
    private String systemId;
    private int lineNumber;
    private int columnNumber;
    static final long serialVersionUID = -5651165872476709336L;
    
    public SAXParseException(final String s, final Locator locator) {
        super(s);
        if (locator != null) {
            this.init(locator.getPublicId(), locator.getSystemId(), locator.getLineNumber(), locator.getColumnNumber());
        }
        else {
            this.init(null, null, -1, -1);
        }
    }
    
    public SAXParseException(final String s, final Locator locator, final Exception ex) {
        super(s, ex);
        if (locator != null) {
            this.init(locator.getPublicId(), locator.getSystemId(), locator.getLineNumber(), locator.getColumnNumber());
        }
        else {
            this.init(null, null, -1, -1);
        }
    }
    
    public SAXParseException(final String s, final String s2, final String s3, final int n, final int n2) {
        super(s);
        this.init(s2, s3, n, n2);
    }
    
    public SAXParseException(final String s, final String s2, final String s3, final int n, final int n2, final Exception ex) {
        super(s, ex);
        this.init(s2, s3, n, n2);
    }
    
    private void init(final String publicId, final String systemId, final int lineNumber, final int columnNumber) {
        this.publicId = publicId;
        this.systemId = systemId;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }
    
    public String getPublicId() {
        return this.publicId;
    }
    
    public String getSystemId() {
        return this.systemId;
    }
    
    public int getLineNumber() {
        return this.lineNumber;
    }
    
    public int getColumnNumber() {
        return this.columnNumber;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getName());
        final String localizedMessage = this.getLocalizedMessage();
        if (this.publicId != null) {
            sb.append("publicId: ").append(this.publicId);
        }
        if (this.systemId != null) {
            sb.append("; systemId: ").append(this.systemId);
        }
        if (this.lineNumber != -1) {
            sb.append("; lineNumber: ").append(this.lineNumber);
        }
        if (this.columnNumber != -1) {
            sb.append("; columnNumber: ").append(this.columnNumber);
        }
        if (localizedMessage != null) {
            sb.append("; ").append(localizedMessage);
        }
        return sb.toString();
    }
}
