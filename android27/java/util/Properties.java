package java.util;

import java.io.*;
import sun.util.spi.*;
import jdk.internal.util.xml.*;
import java.security.*;

public class Properties extends Hashtable<Object, Object>
{
    private static final long serialVersionUID = 4112578634029874840L;
    protected Properties defaults;
    private static final char[] hexDigit;
    
    public Properties() {
        this(null);
    }
    
    public Properties(final Properties defaults) {
        this.defaults = defaults;
    }
    
    public synchronized Object setProperty(final String s, final String s2) {
        return ((Hashtable<String, String>)this).put(s, s2);
    }
    
    public synchronized void load(final Reader reader) throws IOException {
        this.load0(new LineReader(reader));
    }
    
    public synchronized void load(final InputStream inputStream) throws IOException {
        this.load0(new LineReader(inputStream));
    }
    
    private void load0(final LineReader lineReader) throws IOException {
        final char[] array = new char[1024];
        int line;
        while ((line = lineReader.readLine()) >= 0) {
            int i = 0;
            int j = line;
            int n = 0;
            boolean b = false;
            while (i < line) {
                final char c = lineReader.lineBuf[i];
                if ((c == '=' || c == ':') && !b) {
                    j = i + 1;
                    n = 1;
                    break;
                }
                if ((c == ' ' || c == '\t' || c == '\f') && !b) {
                    j = i + 1;
                    break;
                }
                b = (c == '\\' && !b);
                ++i;
            }
            while (j < line) {
                final char c2 = lineReader.lineBuf[j];
                if (c2 != ' ' && c2 != '\t' && c2 != '\f') {
                    if (n != 0 || (c2 != '=' && c2 != ':')) {
                        break;
                    }
                    n = 1;
                }
                ++j;
            }
            ((Hashtable<String, String>)this).put(this.loadConvert(lineReader.lineBuf, 0, i, array), this.loadConvert(lineReader.lineBuf, j, line - j, array));
        }
    }
    
    private String loadConvert(final char[] array, int i, final int n, char[] array2) {
        if (array2.length < n) {
            int n2 = n * 2;
            if (n2 < 0) {
                n2 = Integer.MAX_VALUE;
            }
            array2 = new char[n2];
        }
        final char[] array3 = array2;
        int n3 = 0;
        while (i < i + n) {
            final char c = array[i++];
            if (c == '\\') {
                char c2 = array[i++];
                if (c2 == 'u') {
                    int n4 = 0;
                    for (int j = 0; j < 4; ++j) {
                        final char c3 = array[i++];
                        switch (c3) {
                            case 48:
                            case 49:
                            case 50:
                            case 51:
                            case 52:
                            case 53:
                            case 54:
                            case 55:
                            case 56:
                            case 57: {
                                n4 = (n4 << 4) + c3 - 48;
                                break;
                            }
                            case 97:
                            case 98:
                            case 99:
                            case 100:
                            case 101:
                            case 102: {
                                n4 = (n4 << 4) + 10 + c3 - 97;
                                break;
                            }
                            case 65:
                            case 66:
                            case 67:
                            case 68:
                            case 69:
                            case 70: {
                                n4 = (n4 << 4) + 10 + c3 - 65;
                                break;
                            }
                            default: {
                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                            }
                        }
                    }
                    array3[n3++] = (char)n4;
                }
                else {
                    if (c2 == 't') {
                        c2 = '\t';
                    }
                    else if (c2 == 'r') {
                        c2 = '\r';
                    }
                    else if (c2 == 'n') {
                        c2 = '\n';
                    }
                    else if (c2 == 'f') {
                        c2 = '\f';
                    }
                    array3[n3++] = c2;
                }
            }
            else {
                array3[n3++] = c;
            }
        }
        return new String(array3, 0, n3);
    }
    
    private String saveConvert(final String s, final boolean b, final boolean b2) {
        final int length = s.length();
        int n = length * 2;
        if (n < 0) {
            n = Integer.MAX_VALUE;
        }
        final StringBuffer sb = new StringBuffer(n);
        for (int i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 > '=' && char1 < '\u007f') {
                if (char1 == '\\') {
                    sb.append('\\');
                    sb.append('\\');
                }
                else {
                    sb.append(char1);
                }
            }
            else {
                switch (char1) {
                    case ' ': {
                        if (i == 0 || b) {
                            sb.append('\\');
                        }
                        sb.append(' ');
                        break;
                    }
                    case '\t': {
                        sb.append('\\');
                        sb.append('t');
                        break;
                    }
                    case '\n': {
                        sb.append('\\');
                        sb.append('n');
                        break;
                    }
                    case '\r': {
                        sb.append('\\');
                        sb.append('r');
                        break;
                    }
                    case '\f': {
                        sb.append('\\');
                        sb.append('f');
                        break;
                    }
                    case '!':
                    case '#':
                    case ':':
                    case '=': {
                        sb.append('\\');
                        sb.append(char1);
                        break;
                    }
                    default: {
                        if ((char1 < ' ' || char1 > '~') & b2) {
                            sb.append('\\');
                            sb.append('u');
                            sb.append(toHex(char1 >> 12 & '\u000f'));
                            sb.append(toHex(char1 >> 8 & '\u000f'));
                            sb.append(toHex(char1 >> 4 & '\u000f'));
                            sb.append(toHex(char1 & '\u000f'));
                            break;
                        }
                        sb.append(char1);
                        break;
                    }
                }
            }
        }
        return sb.toString();
    }
    
    private static void writeComments(final BufferedWriter bufferedWriter, final String s) throws IOException {
        bufferedWriter.write("#");
        final int length = s.length();
        int i = 0;
        int n = 0;
        final char[] array = new char[6];
        array[0] = '\\';
        array[1] = 'u';
        while (i < length) {
            final char char1 = s.charAt(i);
            if (char1 > '\u00ff' || char1 == '\n' || char1 == '\r') {
                if (n != i) {
                    bufferedWriter.write(s.substring(n, i));
                }
                if (char1 > '\u00ff') {
                    array[2] = toHex(char1 >> 12 & '\u000f');
                    array[3] = toHex(char1 >> 8 & '\u000f');
                    array[4] = toHex(char1 >> 4 & '\u000f');
                    array[5] = toHex(char1 & '\u000f');
                    bufferedWriter.write(new String(array));
                }
                else {
                    bufferedWriter.newLine();
                    if (char1 == '\r' && i != length - 1 && s.charAt(i + 1) == '\n') {
                        ++i;
                    }
                    if (i == length - 1 || (s.charAt(i + 1) != '#' && s.charAt(i + 1) != '!')) {
                        bufferedWriter.write("#");
                    }
                }
                n = i + 1;
            }
            ++i;
        }
        if (n != i) {
            bufferedWriter.write(s.substring(n, i));
        }
        bufferedWriter.newLine();
    }
    
    @Deprecated
    public void save(final OutputStream outputStream, final String s) {
        try {
            this.store(outputStream, s);
        }
        catch (IOException ex) {}
    }
    
    public void store(final Writer writer, final String s) throws IOException {
        this.store0((writer instanceof BufferedWriter) ? ((BufferedWriter)writer) : new BufferedWriter(writer), s, false);
    }
    
    public void store(final OutputStream outputStream, final String s) throws IOException {
        this.store0(new BufferedWriter(new OutputStreamWriter(outputStream, "8859_1")), s, true);
    }
    
    private void store0(final BufferedWriter bufferedWriter, final String s, final boolean b) throws IOException {
        if (s != null) {
            writeComments(bufferedWriter, s);
        }
        bufferedWriter.write("#" + new Date().toString());
        bufferedWriter.newLine();
        synchronized (this) {
            final Enumeration<String> keys = ((Hashtable<String, V>)this).keys();
            while (keys.hasMoreElements()) {
                final String s2 = keys.nextElement();
                bufferedWriter.write(this.saveConvert(s2, true, b) + "=" + this.saveConvert((String)this.get(s2), false, b));
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.flush();
    }
    
    public synchronized void loadFromXML(final InputStream inputStream) throws IOException, InvalidPropertiesFormatException {
        XmlSupport.load(this, Objects.requireNonNull(inputStream));
        inputStream.close();
    }
    
    public void storeToXML(final OutputStream outputStream, final String s) throws IOException {
        this.storeToXML(outputStream, s, "UTF-8");
    }
    
    public void storeToXML(final OutputStream outputStream, final String s, final String s2) throws IOException {
        XmlSupport.save(this, Objects.requireNonNull(outputStream), s, Objects.requireNonNull(s2));
    }
    
    public String getProperty(final String s) {
        final String value = super.get(s);
        final String s2 = (value instanceof String) ? value : null;
        return (s2 == null && this.defaults != null) ? this.defaults.getProperty(s) : s2;
    }
    
    public String getProperty(final String s, final String s2) {
        final String property = this.getProperty(s);
        return (property == null) ? s2 : property;
    }
    
    public Enumeration<?> propertyNames() {
        final Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
        this.enumerate(hashtable);
        return hashtable.keys();
    }
    
    public Set<String> stringPropertyNames() {
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        this.enumerateStringProperties(hashtable);
        return hashtable.keySet();
    }
    
    public void list(final PrintStream printStream) {
        printStream.println("-- listing properties --");
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        this.enumerate((Hashtable<String, Object>)hashtable);
        final Enumeration<String> keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            String string = hashtable.get(s);
            if (string.length() > 40) {
                string = string.substring(0, 37) + "...";
            }
            printStream.println(s + "=" + string);
        }
    }
    
    public void list(final PrintWriter printWriter) {
        printWriter.println("-- listing properties --");
        final Hashtable<String, String> hashtable = new Hashtable<String, String>();
        this.enumerate((Hashtable<String, Object>)hashtable);
        final Enumeration<String> keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            String string = hashtable.get(s);
            if (string.length() > 40) {
                string = string.substring(0, 37) + "...";
            }
            printWriter.println(s + "=" + string);
        }
    }
    
    private synchronized void enumerate(final Hashtable<String, Object> hashtable) {
        if (this.defaults != null) {
            this.defaults.enumerate(hashtable);
        }
        final Enumeration<String> keys = ((Hashtable<String, V>)this).keys();
        while (keys.hasMoreElements()) {
            final String s = keys.nextElement();
            hashtable.put(s, this.get(s));
        }
    }
    
    private synchronized void enumerateStringProperties(final Hashtable<String, String> hashtable) {
        if (this.defaults != null) {
            this.defaults.enumerateStringProperties(hashtable);
        }
        final Enumeration<String> keys = ((Hashtable<String, V>)this).keys();
        while (keys.hasMoreElements()) {
            final String nextElement = keys.nextElement();
            final Object value = ((Hashtable<K, Object>)this).get(nextElement);
            if (nextElement instanceof String && value instanceof String) {
                hashtable.put(nextElement, (String)value);
            }
        }
    }
    
    private static char toHex(final int n) {
        return Properties.hexDigit[n & 0xF];
    }
    
    static {
        hexDigit = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    }
    
    class LineReader
    {
        byte[] inByteBuf;
        char[] inCharBuf;
        char[] lineBuf;
        int inLimit;
        int inOff;
        InputStream inStream;
        Reader reader;
        
        public LineReader(final InputStream inStream) {
            this.lineBuf = new char[1024];
            this.inLimit = 0;
            this.inOff = 0;
            this.inStream = inStream;
            this.inByteBuf = new byte[8192];
        }
        
        public LineReader(final Reader reader) {
            this.lineBuf = new char[1024];
            this.inLimit = 0;
            this.inOff = 0;
            this.reader = reader;
            this.inCharBuf = new char[8192];
        }
        
        int readLine() throws IOException {
            int n = 0;
            int n2 = 1;
            int n3 = 0;
            int n4 = 1;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            while (true) {
                if (this.inOff >= this.inLimit) {
                    this.inLimit = ((this.inStream == null) ? this.reader.read(this.inCharBuf) : this.inStream.read(this.inByteBuf));
                    this.inOff = 0;
                    if (this.inLimit <= 0) {
                        if (n == 0 || n3 != 0) {
                            return -1;
                        }
                        if (n6 != 0) {
                            --n;
                        }
                        return n;
                    }
                }
                char c;
                if (this.inStream != null) {
                    c = (char)(0xFF & this.inByteBuf[this.inOff++]);
                }
                else {
                    c = this.inCharBuf[this.inOff++];
                }
                if (n7 != 0) {
                    n7 = 0;
                    if (c == '\n') {
                        continue;
                    }
                }
                if (n2 != 0) {
                    if (c == ' ' || c == '\t') {
                        continue;
                    }
                    if (c == '\f') {
                        continue;
                    }
                    if (n5 == 0) {
                        if (c == '\r') {
                            continue;
                        }
                        if (c == '\n') {
                            continue;
                        }
                    }
                    n2 = 0;
                    n5 = 0;
                }
                if (n4 != 0) {
                    n4 = 0;
                    if (c == '#' || c == '!') {
                        n3 = 1;
                        continue;
                    }
                }
                if (c != '\n' && c != '\r') {
                    this.lineBuf[n++] = c;
                    if (n == this.lineBuf.length) {
                        int n8 = this.lineBuf.length * 2;
                        if (n8 < 0) {
                            n8 = Integer.MAX_VALUE;
                        }
                        final char[] lineBuf = new char[n8];
                        System.arraycopy(this.lineBuf, 0, lineBuf, 0, this.lineBuf.length);
                        this.lineBuf = lineBuf;
                    }
                    n6 = ((c == '\\' && n6 == 0) ? 1 : 0);
                }
                else if (n3 != 0 || n == 0) {
                    n3 = 0;
                    n4 = 1;
                    n2 = 1;
                    n = 0;
                }
                else {
                    if (this.inOff >= this.inLimit) {
                        this.inLimit = ((this.inStream == null) ? this.reader.read(this.inCharBuf) : this.inStream.read(this.inByteBuf));
                        this.inOff = 0;
                        if (this.inLimit <= 0) {
                            if (n6 != 0) {
                                --n;
                            }
                            return n;
                        }
                    }
                    if (n6 == 0) {
                        return n;
                    }
                    --n;
                    n2 = 1;
                    n5 = 1;
                    n6 = 0;
                    if (c != '\r') {
                        continue;
                    }
                    n7 = 1;
                }
            }
        }
    }
    
    private static class XmlSupport
    {
        private static final XmlPropertiesProvider PROVIDER;
        
        private static XmlPropertiesProvider loadProviderFromProperty(final ClassLoader classLoader) {
            final String property = System.getProperty("sun.util.spi.XmlPropertiesProvider");
            if (property == null) {
                return null;
            }
            try {
                return (XmlPropertiesProvider)Class.forName(property, true, classLoader).newInstance();
            }
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
                final Object o;
                throw new ServiceConfigurationError(null, (Throwable)o);
            }
        }
        
        private static XmlPropertiesProvider loadProviderAsService(final ClassLoader classLoader) {
            final Iterator<XmlPropertiesProvider> iterator = ServiceLoader.load(XmlPropertiesProvider.class, classLoader).iterator();
            return iterator.hasNext() ? iterator.next() : null;
        }
        
        private static XmlPropertiesProvider loadProvider() {
            return AccessController.doPrivileged((PrivilegedAction<XmlPropertiesProvider>)new PrivilegedAction<XmlPropertiesProvider>() {
                @Override
                public XmlPropertiesProvider run() {
                    final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                    final XmlPropertiesProvider access$000 = loadProviderFromProperty(systemClassLoader);
                    if (access$000 != null) {
                        return access$000;
                    }
                    final XmlPropertiesProvider access$2 = loadProviderAsService(systemClassLoader);
                    if (access$2 != null) {
                        return access$2;
                    }
                    return new BasicXmlPropertiesProvider();
                }
            });
        }
        
        static void load(final Properties properties, final InputStream inputStream) throws IOException, InvalidPropertiesFormatException {
            XmlSupport.PROVIDER.load(properties, inputStream);
        }
        
        static void save(final Properties properties, final OutputStream outputStream, final String s, final String s2) throws IOException {
            XmlSupport.PROVIDER.store(properties, outputStream, s, s2);
        }
        
        static {
            PROVIDER = loadProvider();
        }
    }
}
