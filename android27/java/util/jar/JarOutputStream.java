package java.util.jar;

import java.util.zip.*;
import java.io.*;

public class JarOutputStream extends ZipOutputStream
{
    private static final int JAR_MAGIC = 51966;
    private boolean firstEntry;
    
    public JarOutputStream(final OutputStream outputStream, final Manifest manifest) throws IOException {
        super(outputStream);
        this.firstEntry = true;
        if (manifest == null) {
            throw new NullPointerException("man");
        }
        this.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));
        manifest.write(new BufferedOutputStream(this));
        this.closeEntry();
    }
    
    public JarOutputStream(final OutputStream outputStream) throws IOException {
        super(outputStream);
        this.firstEntry = true;
    }
    
    @Override
    public void putNextEntry(final ZipEntry zipEntry) throws IOException {
        if (this.firstEntry) {
            final byte[] extra = zipEntry.getExtra();
            if (extra == null || !hasMagic(extra)) {
                byte[] extra2;
                if (extra == null) {
                    extra2 = new byte[4];
                }
                else {
                    final byte[] array = new byte[extra.length + 4];
                    System.arraycopy(extra, 0, array, 4, extra.length);
                    extra2 = array;
                }
                set16(extra2, 0, 51966);
                set16(extra2, 2, 0);
                zipEntry.setExtra(extra2);
            }
            this.firstEntry = false;
        }
        super.putNextEntry(zipEntry);
    }
    
    private static boolean hasMagic(final byte[] array) {
        try {
            for (int i = 0; i < array.length; i += get16(array, i + 2) + 4) {
                if (get16(array, i) == 51966) {
                    return true;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {}
        return false;
    }
    
    private static int get16(final byte[] array, final int n) {
        return Byte.toUnsignedInt(array[n]) | Byte.toUnsignedInt(array[n + 1]) << 8;
    }
    
    private static void set16(final byte[] array, final int n, final int n2) {
        array[n + 0] = (byte)n2;
        array[n + 1] = (byte)(n2 >> 8);
    }
}
