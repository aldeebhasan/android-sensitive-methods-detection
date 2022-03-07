package java.nio.file;

import java.io.*;

public class FileSystemException extends IOException
{
    static final long serialVersionUID = -3055425747967319812L;
    private final String file;
    private final String other;
    
    public FileSystemException(final String file) {
        super((String)null);
        this.file = file;
        this.other = null;
    }
    
    public FileSystemException(final String file, final String other, final String s) {
        super(s);
        this.file = file;
        this.other = other;
    }
    
    public String getFile() {
        return this.file;
    }
    
    public String getOtherFile() {
        return this.other;
    }
    
    public String getReason() {
        return super.getMessage();
    }
    
    @Override
    public String getMessage() {
        if (this.file == null && this.other == null) {
            return this.getReason();
        }
        final StringBuilder sb = new StringBuilder();
        if (this.file != null) {
            sb.append(this.file);
        }
        if (this.other != null) {
            sb.append(" -> ");
            sb.append(this.other);
        }
        if (this.getReason() != null) {
            sb.append(": ");
            sb.append(this.getReason());
        }
        return sb.toString();
    }
}
