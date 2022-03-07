package java.io;

public class FileReader extends InputStreamReader
{
    public FileReader(final String s) throws FileNotFoundException {
        super(new FileInputStream(s));
    }
    
    public FileReader(final File file) throws FileNotFoundException {
        super(new FileInputStream(file));
    }
    
    public FileReader(final FileDescriptor fileDescriptor) {
        super(new FileInputStream(fileDescriptor));
    }
}
