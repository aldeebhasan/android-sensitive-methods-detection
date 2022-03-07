package java.io;

public class FileWriter extends OutputStreamWriter
{
    public FileWriter(final String s) throws IOException {
        super(new FileOutputStream(s));
    }
    
    public FileWriter(final String s, final boolean b) throws IOException {
        super(new FileOutputStream(s, b));
    }
    
    public FileWriter(final File file) throws IOException {
        super(new FileOutputStream(file));
    }
    
    public FileWriter(final File file, final boolean b) throws IOException {
        super(new FileOutputStream(file, b));
    }
    
    public FileWriter(final FileDescriptor fileDescriptor) {
        super(new FileOutputStream(fileDescriptor));
    }
}
