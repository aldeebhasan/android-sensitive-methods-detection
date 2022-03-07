package java.util;

import java.nio.*;
import sun.misc.*;
import java.nio.charset.*;
import java.nio.channels.*;
import java.nio.file.*;
import java.io.*;
import java.text.*;
import java.util.regex.*;
import java.math.*;

public final class Scanner implements Iterator<String>, Closeable
{
    private CharBuffer buf;
    private static final int BUFFER_SIZE = 1024;
    private int position;
    private Matcher matcher;
    private Pattern delimPattern;
    private Pattern hasNextPattern;
    private int hasNextPosition;
    private String hasNextResult;
    private Readable source;
    private boolean sourceClosed;
    private boolean needInput;
    private boolean skipped;
    private int savedScannerPosition;
    private Object typeCache;
    private boolean matchValid;
    private boolean closed;
    private int radix;
    private int defaultRadix;
    private Locale locale;
    private LRUCache<String, Pattern> patternCache;
    private IOException lastException;
    private static Pattern WHITESPACE_PATTERN;
    private static Pattern FIND_ANY_PATTERN;
    private static Pattern NON_ASCII_DIGIT;
    private String groupSeparator;
    private String decimalSeparator;
    private String nanString;
    private String infinityString;
    private String positivePrefix;
    private String negativePrefix;
    private String positiveSuffix;
    private String negativeSuffix;
    private static volatile Pattern boolPattern;
    private static final String BOOLEAN_PATTERN = "true|false";
    private Pattern integerPattern;
    private String digits;
    private String non0Digit;
    private int SIMPLE_GROUP_INDEX;
    private static volatile Pattern separatorPattern;
    private static volatile Pattern linePattern;
    private static final String LINE_SEPARATOR_PATTERN = "\r\n|[\n\r\u2028\u2029\u0085]";
    private static final String LINE_PATTERN = ".*(\r\n|[\n\r\u2028\u2029\u0085])|.+$";
    private Pattern floatPattern;
    private Pattern decimalPattern;
    
    private static Pattern boolPattern() {
        Pattern boolPattern = Scanner.boolPattern;
        if (boolPattern == null) {
            boolPattern = (Scanner.boolPattern = Pattern.compile("true|false", 2));
        }
        return boolPattern;
    }
    
    private String buildIntegerPatternString() {
        final String string = "((?i)[" + this.digits.substring(0, this.radix) + "\\p{javaDigit}])";
        final String string2 = "((" + string + "++)|" + ("(" + this.non0Digit + string + "?" + string + "?(" + this.groupSeparator + string + string + string + ")+)") + ")";
        return "(" + ("([-+]?(" + string2 + "))") + ")|(" + (this.positivePrefix + string2 + this.positiveSuffix) + ")|(" + (this.negativePrefix + string2 + this.negativeSuffix) + ")";
    }
    
    private Pattern integerPattern() {
        if (this.integerPattern == null) {
            this.integerPattern = this.patternCache.forName(this.buildIntegerPatternString());
        }
        return this.integerPattern;
    }
    
    private static Pattern separatorPattern() {
        Pattern separatorPattern = Scanner.separatorPattern;
        if (separatorPattern == null) {
            separatorPattern = (Scanner.separatorPattern = Pattern.compile("\r\n|[\n\r\u2028\u2029\u0085]"));
        }
        return separatorPattern;
    }
    
    private static Pattern linePattern() {
        Pattern linePattern = Scanner.linePattern;
        if (linePattern == null) {
            linePattern = (Scanner.linePattern = Pattern.compile(".*(\r\n|[\n\r\u2028\u2029\u0085])|.+$"));
        }
        return linePattern;
    }
    
    private void buildFloatAndDecimalPattern() {
        final String s = "(([0-9\\p{javaDigit}]))";
        final String string = "([eE][+-]?" + s + "+)?";
        final String string2 = "((" + s + "++)|" + ("(" + this.non0Digit + s + "?" + s + "?(" + this.groupSeparator + s + s + s + ")+)") + ")";
        final String string3 = "(" + string2 + "|" + string2 + this.decimalSeparator + s + "*+|" + this.decimalSeparator + s + "++)";
        final String string4 = "(NaN|" + this.nanString + "|Infinity|" + this.infinityString + ")";
        final String string5 = "(([-+]?" + string3 + string + ")|" + ("(" + this.positivePrefix + string3 + this.positiveSuffix + string + ")") + "|" + ("(" + this.negativePrefix + string3 + this.negativeSuffix + string + ")") + ")";
        this.floatPattern = Pattern.compile(string5 + "|" + "[-+]?0[xX][0-9a-fA-F]*\\.[0-9a-fA-F]+([pP][-+]?[0-9]+)?" + "|" + ("(([-+]?" + string4 + ")|" + ("(" + this.positivePrefix + string4 + this.positiveSuffix + ")") + "|" + ("(" + this.negativePrefix + string4 + this.negativeSuffix + ")") + ")"));
        this.decimalPattern = Pattern.compile(string5);
    }
    
    private Pattern floatPattern() {
        if (this.floatPattern == null) {
            this.buildFloatAndDecimalPattern();
        }
        return this.floatPattern;
    }
    
    private Pattern decimalPattern() {
        if (this.decimalPattern == null) {
            this.buildFloatAndDecimalPattern();
        }
        return this.decimalPattern;
    }
    
    private Scanner(final Readable source, final Pattern delimPattern) {
        this.sourceClosed = false;
        this.needInput = false;
        this.skipped = false;
        this.savedScannerPosition = -1;
        this.typeCache = null;
        this.matchValid = false;
        this.closed = false;
        this.radix = 10;
        this.defaultRadix = 10;
        this.locale = null;
        this.patternCache = new LRUCache<String, Pattern>(7) {
            @Override
            protected Pattern create(final String s) {
                return Pattern.compile(s);
            }
            
            @Override
            protected boolean hasName(final Pattern pattern, final String s) {
                return pattern.pattern().equals(s);
            }
        };
        this.groupSeparator = "\\,";
        this.decimalSeparator = "\\.";
        this.nanString = "NaN";
        this.infinityString = "Infinity";
        this.positivePrefix = "";
        this.negativePrefix = "\\-";
        this.positiveSuffix = "";
        this.negativeSuffix = "";
        this.digits = "0123456789abcdefghijklmnopqrstuvwxyz";
        this.non0Digit = "[\\p{javaDigit}&&[^0]]";
        this.SIMPLE_GROUP_INDEX = 5;
        assert source != null : "source should not be null";
        assert delimPattern != null : "pattern should not be null";
        this.source = source;
        this.delimPattern = delimPattern;
        (this.buf = CharBuffer.allocate(1024)).limit(0);
        (this.matcher = this.delimPattern.matcher(this.buf)).useTransparentBounds(true);
        this.matcher.useAnchoringBounds(false);
        this.useLocale(Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public Scanner(final Readable readable) {
        this(Objects.requireNonNull(readable, "source"), Scanner.WHITESPACE_PATTERN);
    }
    
    public Scanner(final InputStream inputStream) {
        this(new InputStreamReader(inputStream), Scanner.WHITESPACE_PATTERN);
    }
    
    public Scanner(final InputStream inputStream, final String s) {
        this(makeReadable(Objects.requireNonNull(inputStream, "source"), toCharset(s)), Scanner.WHITESPACE_PATTERN);
    }
    
    private static Charset toCharset(final String s) {
        Objects.requireNonNull(s, "charsetName");
        try {
            return Charset.forName(s);
        }
        catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
            final Object o;
            throw new IllegalArgumentException((Throwable)o);
        }
    }
    
    private static Readable makeReadable(final InputStream inputStream, final Charset charset) {
        return new InputStreamReader(inputStream, charset);
    }
    
    public Scanner(final File file) throws FileNotFoundException {
        this(new FileInputStream(file).getChannel());
    }
    
    public Scanner(final File file, final String s) throws FileNotFoundException {
        this(Objects.requireNonNull(file), toDecoder(s));
    }
    
    private Scanner(final File file, final CharsetDecoder charsetDecoder) throws FileNotFoundException {
        this(makeReadable(new FileInputStream(file).getChannel(), charsetDecoder));
    }
    
    private static CharsetDecoder toDecoder(final String s) {
        Objects.requireNonNull(s, "charsetName");
        try {
            return Charset.forName(s).newDecoder();
        }
        catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
            throw new IllegalArgumentException(s);
        }
    }
    
    private static Readable makeReadable(final ReadableByteChannel readableByteChannel, final CharsetDecoder charsetDecoder) {
        return Channels.newReader(readableByteChannel, charsetDecoder, -1);
    }
    
    public Scanner(final Path path) throws IOException {
        this(Files.newInputStream(path, new OpenOption[0]));
    }
    
    public Scanner(final Path path, final String s) throws IOException {
        this(Objects.requireNonNull(path), toCharset(s));
    }
    
    private Scanner(final Path path, final Charset charset) throws IOException {
        this(makeReadable(Files.newInputStream(path, new OpenOption[0]), charset));
    }
    
    public Scanner(final String s) {
        this(new StringReader(s), Scanner.WHITESPACE_PATTERN);
    }
    
    public Scanner(final ReadableByteChannel readableByteChannel) {
        this(makeReadable(Objects.requireNonNull(readableByteChannel, "source")), Scanner.WHITESPACE_PATTERN);
    }
    
    private static Readable makeReadable(final ReadableByteChannel readableByteChannel) {
        return makeReadable(readableByteChannel, Charset.defaultCharset().newDecoder());
    }
    
    public Scanner(final ReadableByteChannel readableByteChannel, final String s) {
        this(makeReadable(Objects.requireNonNull(readableByteChannel, "source"), toDecoder(s)), Scanner.WHITESPACE_PATTERN);
    }
    
    private void saveState() {
        this.savedScannerPosition = this.position;
    }
    
    private void revertState() {
        this.position = this.savedScannerPosition;
        this.savedScannerPosition = -1;
        this.skipped = false;
    }
    
    private boolean revertState(final boolean b) {
        this.position = this.savedScannerPosition;
        this.savedScannerPosition = -1;
        this.skipped = false;
        return b;
    }
    
    private void cacheResult() {
        this.hasNextResult = this.matcher.group();
        this.hasNextPosition = this.matcher.end();
        this.hasNextPattern = this.matcher.pattern();
    }
    
    private void cacheResult(final String hasNextResult) {
        this.hasNextResult = hasNextResult;
        this.hasNextPosition = this.matcher.end();
        this.hasNextPattern = this.matcher.pattern();
    }
    
    private void clearCaches() {
        this.hasNextPattern = null;
        this.typeCache = null;
    }
    
    private String getCachedResult() {
        this.position = this.hasNextPosition;
        this.hasNextPattern = null;
        this.typeCache = null;
        return this.hasNextResult;
    }
    
    private void useTypeCache() {
        if (this.closed) {
            throw new IllegalStateException("Scanner closed");
        }
        this.position = this.hasNextPosition;
        this.hasNextPattern = null;
        this.typeCache = null;
    }
    
    private void readInput() {
        if (this.buf.limit() == this.buf.capacity()) {
            this.makeSpace();
        }
        final int position = this.buf.position();
        this.buf.position(this.buf.limit());
        this.buf.limit(this.buf.capacity());
        int read;
        try {
            read = this.source.read(this.buf);
        }
        catch (IOException lastException) {
            this.lastException = lastException;
            read = -1;
        }
        if (read == -1) {
            this.sourceClosed = true;
            this.needInput = false;
        }
        if (read > 0) {
            this.needInput = false;
        }
        this.buf.limit(this.buf.position());
        this.buf.position(position);
    }
    
    private boolean makeSpace() {
        this.clearCaches();
        final int n = (this.savedScannerPosition == -1) ? this.position : this.savedScannerPosition;
        this.buf.position(n);
        if (n > 0) {
            this.buf.compact();
            this.translateSavedIndexes(n);
            this.position -= n;
            this.buf.flip();
            return true;
        }
        final CharBuffer allocate = CharBuffer.allocate(this.buf.capacity() * 2);
        allocate.put(this.buf);
        allocate.flip();
        this.translateSavedIndexes(n);
        this.position -= n;
        this.buf = allocate;
        this.matcher.reset(this.buf);
        return true;
    }
    
    private void translateSavedIndexes(final int n) {
        if (this.savedScannerPosition != -1) {
            this.savedScannerPosition -= n;
        }
    }
    
    private void throwFor() {
        this.skipped = false;
        if (this.sourceClosed && this.position == this.buf.limit()) {
            throw new NoSuchElementException();
        }
        throw new InputMismatchException();
    }
    
    private boolean hasTokenInBuffer() {
        this.matchValid = false;
        this.matcher.usePattern(this.delimPattern);
        this.matcher.region(this.position, this.buf.limit());
        if (this.matcher.lookingAt()) {
            this.position = this.matcher.end();
        }
        return this.position != this.buf.limit();
    }
    
    private String getCompleteTokenInBuffer(Pattern pattern) {
        this.matchValid = false;
        this.matcher.usePattern(this.delimPattern);
        if (!this.skipped) {
            this.matcher.region(this.position, this.buf.limit());
            if (this.matcher.lookingAt()) {
                if (this.matcher.hitEnd() && !this.sourceClosed) {
                    this.needInput = true;
                    return null;
                }
                this.skipped = true;
                this.position = this.matcher.end();
            }
        }
        if (this.position == this.buf.limit()) {
            if (this.sourceClosed) {
                return null;
            }
            this.needInput = true;
            return null;
        }
        else {
            this.matcher.region(this.position, this.buf.limit());
            boolean b = this.matcher.find();
            if (b && this.matcher.end() == this.position) {
                b = this.matcher.find();
            }
            if (b) {
                if (this.matcher.requireEnd() && !this.sourceClosed) {
                    this.needInput = true;
                    return null;
                }
                final int start = this.matcher.start();
                if (pattern == null) {
                    pattern = Scanner.FIND_ANY_PATTERN;
                }
                this.matcher.usePattern(pattern);
                this.matcher.region(this.position, start);
                if (this.matcher.matches()) {
                    final String group = this.matcher.group();
                    this.position = this.matcher.end();
                    return group;
                }
                return null;
            }
            else {
                if (!this.sourceClosed) {
                    this.needInput = true;
                    return null;
                }
                if (pattern == null) {
                    pattern = Scanner.FIND_ANY_PATTERN;
                }
                this.matcher.usePattern(pattern);
                this.matcher.region(this.position, this.buf.limit());
                if (this.matcher.matches()) {
                    final String group2 = this.matcher.group();
                    this.position = this.matcher.end();
                    return group2;
                }
                return null;
            }
        }
    }
    
    private String findPatternInBuffer(final Pattern pattern, final int n) {
        this.matchValid = false;
        this.matcher.usePattern(pattern);
        final int limit = this.buf.limit();
        int n2 = -1;
        int n3 = limit;
        if (n > 0) {
            n2 = this.position + n;
            if (n2 < limit) {
                n3 = n2;
            }
        }
        this.matcher.region(this.position, n3);
        if (this.matcher.find()) {
            if (this.matcher.hitEnd() && !this.sourceClosed) {
                if (n3 != n2) {
                    this.needInput = true;
                    return null;
                }
                if (n3 == n2 && this.matcher.requireEnd()) {
                    this.needInput = true;
                    return null;
                }
            }
            this.position = this.matcher.end();
            return this.matcher.group();
        }
        if (this.sourceClosed) {
            return null;
        }
        if (n == 0 || n3 != n2) {
            this.needInput = true;
        }
        return null;
    }
    
    private String matchPatternInBuffer(final Pattern pattern) {
        this.matchValid = false;
        this.matcher.usePattern(pattern);
        this.matcher.region(this.position, this.buf.limit());
        if (this.matcher.lookingAt()) {
            if (this.matcher.hitEnd() && !this.sourceClosed) {
                this.needInput = true;
                return null;
            }
            this.position = this.matcher.end();
            return this.matcher.group();
        }
        else {
            if (this.sourceClosed) {
                return null;
            }
            this.needInput = true;
            return null;
        }
    }
    
    private void ensureOpen() {
        if (this.closed) {
            throw new IllegalStateException("Scanner closed");
        }
    }
    
    @Override
    public void close() {
        if (this.closed) {
            return;
        }
        if (this.source instanceof Closeable) {
            try {
                ((Closeable)this.source).close();
            }
            catch (IOException lastException) {
                this.lastException = lastException;
            }
        }
        this.sourceClosed = true;
        this.source = null;
        this.closed = true;
    }
    
    public IOException ioException() {
        return this.lastException;
    }
    
    public Pattern delimiter() {
        return this.delimPattern;
    }
    
    public Scanner useDelimiter(final Pattern delimPattern) {
        this.delimPattern = delimPattern;
        return this;
    }
    
    public Scanner useDelimiter(final String s) {
        this.delimPattern = this.patternCache.forName(s);
        return this;
    }
    
    public Locale locale() {
        return this.locale;
    }
    
    public Scanner useLocale(final Locale locale) {
        if (locale.equals(this.locale)) {
            return this;
        }
        this.locale = locale;
        final DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
        final DecimalFormatSymbols instance = DecimalFormatSymbols.getInstance(locale);
        this.groupSeparator = "\\x{" + Integer.toHexString(instance.getGroupingSeparator()) + "}";
        this.decimalSeparator = "\\x{" + Integer.toHexString(instance.getDecimalSeparator()) + "}";
        this.nanString = Pattern.quote(instance.getNaN());
        this.infinityString = Pattern.quote(instance.getInfinity());
        this.positivePrefix = decimalFormat.getPositivePrefix();
        if (!this.positivePrefix.isEmpty()) {
            this.positivePrefix = Pattern.quote(this.positivePrefix);
        }
        this.negativePrefix = decimalFormat.getNegativePrefix();
        if (!this.negativePrefix.isEmpty()) {
            this.negativePrefix = Pattern.quote(this.negativePrefix);
        }
        this.positiveSuffix = decimalFormat.getPositiveSuffix();
        if (!this.positiveSuffix.isEmpty()) {
            this.positiveSuffix = Pattern.quote(this.positiveSuffix);
        }
        this.negativeSuffix = decimalFormat.getNegativeSuffix();
        if (!this.negativeSuffix.isEmpty()) {
            this.negativeSuffix = Pattern.quote(this.negativeSuffix);
        }
        this.integerPattern = null;
        this.floatPattern = null;
        return this;
    }
    
    public int radix() {
        return this.defaultRadix;
    }
    
    public Scanner useRadix(final int defaultRadix) {
        if (defaultRadix < 2 || defaultRadix > 36) {
            throw new IllegalArgumentException("radix:" + defaultRadix);
        }
        if (this.defaultRadix == defaultRadix) {
            return this;
        }
        this.defaultRadix = defaultRadix;
        this.integerPattern = null;
        return this;
    }
    
    private void setRadix(final int radix) {
        if (this.radix != radix) {
            this.integerPattern = null;
            this.radix = radix;
        }
    }
    
    public MatchResult match() {
        if (!this.matchValid) {
            throw new IllegalStateException("No match result available");
        }
        return this.matcher.toMatchResult();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("java.util.Scanner");
        sb.append("[delimiters=" + this.delimPattern + "]");
        sb.append("[position=" + this.position + "]");
        sb.append("[match valid=" + this.matchValid + "]");
        sb.append("[need input=" + this.needInput + "]");
        sb.append("[source closed=" + this.sourceClosed + "]");
        sb.append("[skipped=" + this.skipped + "]");
        sb.append("[group separator=" + this.groupSeparator + "]");
        sb.append("[decimal separator=" + this.decimalSeparator + "]");
        sb.append("[positive prefix=" + this.positivePrefix + "]");
        sb.append("[negative prefix=" + this.negativePrefix + "]");
        sb.append("[positive suffix=" + this.positiveSuffix + "]");
        sb.append("[negative suffix=" + this.negativeSuffix + "]");
        sb.append("[NaN string=" + this.nanString + "]");
        sb.append("[infinity string=" + this.infinityString + "]");
        return sb.toString();
    }
    
    @Override
    public boolean hasNext() {
        this.ensureOpen();
        this.saveState();
        while (!this.sourceClosed) {
            if (this.hasTokenInBuffer()) {
                return this.revertState(true);
            }
            this.readInput();
        }
        return this.revertState(this.hasTokenInBuffer());
    }
    
    @Override
    public String next() {
        this.ensureOpen();
        this.clearCaches();
        String completeTokenInBuffer;
        while (true) {
            completeTokenInBuffer = this.getCompleteTokenInBuffer(null);
            if (completeTokenInBuffer != null) {
                break;
            }
            if (this.needInput) {
                this.readInput();
            }
            else {
                this.throwFor();
            }
        }
        this.matchValid = true;
        this.skipped = false;
        return completeTokenInBuffer;
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    public boolean hasNext(final String s) {
        return this.hasNext(this.patternCache.forName(s));
    }
    
    public String next(final String s) {
        return this.next(this.patternCache.forName(s));
    }
    
    public boolean hasNext(final Pattern pattern) {
        this.ensureOpen();
        if (pattern == null) {
            throw new NullPointerException();
        }
        this.hasNextPattern = null;
        this.saveState();
        while (this.getCompleteTokenInBuffer(pattern) == null) {
            if (!this.needInput) {
                return this.revertState(false);
            }
            this.readInput();
        }
        this.matchValid = true;
        this.cacheResult();
        return this.revertState(true);
    }
    
    public String next(final Pattern pattern) {
        this.ensureOpen();
        if (pattern == null) {
            throw new NullPointerException();
        }
        if (this.hasNextPattern == pattern) {
            return this.getCachedResult();
        }
        this.clearCaches();
        String completeTokenInBuffer;
        while (true) {
            completeTokenInBuffer = this.getCompleteTokenInBuffer(pattern);
            if (completeTokenInBuffer != null) {
                break;
            }
            if (this.needInput) {
                this.readInput();
            }
            else {
                this.throwFor();
            }
        }
        this.matchValid = true;
        this.skipped = false;
        return completeTokenInBuffer;
    }
    
    public boolean hasNextLine() {
        this.saveState();
        String s = this.findWithinHorizon(linePattern(), 0);
        if (s != null) {
            final String group = this.match().group(1);
            if (group != null) {
                s = s.substring(0, s.length() - group.length());
                this.cacheResult(s);
            }
            else {
                this.cacheResult();
            }
        }
        this.revertState();
        return s != null;
    }
    
    public String nextLine() {
        if (this.hasNextPattern == linePattern()) {
            return this.getCachedResult();
        }
        this.clearCaches();
        String s = this.findWithinHorizon(Scanner.linePattern, 0);
        if (s == null) {
            throw new NoSuchElementException("No line found");
        }
        final String group = this.match().group(1);
        if (group != null) {
            s = s.substring(0, s.length() - group.length());
        }
        if (s == null) {
            throw new NoSuchElementException();
        }
        return s;
    }
    
    public String findInLine(final String s) {
        return this.findInLine(this.patternCache.forName(s));
    }
    
    public String findInLine(final Pattern pattern) {
        this.ensureOpen();
        if (pattern == null) {
            throw new NullPointerException();
        }
        this.clearCaches();
        this.saveState();
        while (true) {
            while (this.findPatternInBuffer(separatorPattern(), 0) == null) {
                if (this.needInput) {
                    this.readInput();
                }
                else {
                    final int n = this.buf.limit();
                    this.revertState();
                    final int n2 = n - this.position;
                    if (n2 == 0) {
                        return null;
                    }
                    return this.findWithinHorizon(pattern, n2);
                }
            }
            final int n = this.matcher.start();
            continue;
        }
    }
    
    public String findWithinHorizon(final String s, final int n) {
        return this.findWithinHorizon(this.patternCache.forName(s), n);
    }
    
    public String findWithinHorizon(final Pattern pattern, final int n) {
        this.ensureOpen();
        if (pattern == null) {
            throw new NullPointerException();
        }
        if (n < 0) {
            throw new IllegalArgumentException("horizon < 0");
        }
        this.clearCaches();
        while (true) {
            final String patternInBuffer = this.findPatternInBuffer(pattern, n);
            if (patternInBuffer != null) {
                this.matchValid = true;
                return patternInBuffer;
            }
            if (!this.needInput) {
                return null;
            }
            this.readInput();
        }
    }
    
    public Scanner skip(final Pattern pattern) {
        this.ensureOpen();
        if (pattern == null) {
            throw new NullPointerException();
        }
        this.clearCaches();
        while (this.matchPatternInBuffer(pattern) == null) {
            if (!this.needInput) {
                throw new NoSuchElementException();
            }
            this.readInput();
        }
        this.matchValid = true;
        this.position = this.matcher.end();
        return this;
    }
    
    public Scanner skip(final String s) {
        return this.skip(this.patternCache.forName(s));
    }
    
    public boolean hasNextBoolean() {
        return this.hasNext(boolPattern());
    }
    
    public boolean nextBoolean() {
        this.clearCaches();
        return Boolean.parseBoolean(this.next(boolPattern()));
    }
    
    public boolean hasNextByte() {
        return this.hasNextByte(this.defaultRadix);
    }
    
    public boolean hasNextByte(final int radix) {
        this.setRadix(radix);
        boolean hasNext = this.hasNext(this.integerPattern());
        if (hasNext) {
            try {
                this.typeCache = Byte.parseByte((this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) ? this.processIntegerToken(this.hasNextResult) : this.hasNextResult, radix);
            }
            catch (NumberFormatException ex) {
                hasNext = false;
            }
        }
        return hasNext;
    }
    
    public byte nextByte() {
        return this.nextByte(this.defaultRadix);
    }
    
    public byte nextByte(final int radix) {
        if (this.typeCache != null && this.typeCache instanceof Byte && this.radix == radix) {
            final byte byteValue = (byte)this.typeCache;
            this.useTypeCache();
            return byteValue;
        }
        this.setRadix(radix);
        this.clearCaches();
        try {
            String s = this.next(this.integerPattern());
            if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) {
                s = this.processIntegerToken(s);
            }
            return Byte.parseByte(s, radix);
        }
        catch (NumberFormatException ex) {
            this.position = this.matcher.start();
            throw new InputMismatchException(ex.getMessage());
        }
    }
    
    public boolean hasNextShort() {
        return this.hasNextShort(this.defaultRadix);
    }
    
    public boolean hasNextShort(final int radix) {
        this.setRadix(radix);
        boolean hasNext = this.hasNext(this.integerPattern());
        if (hasNext) {
            try {
                this.typeCache = Short.parseShort((this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) ? this.processIntegerToken(this.hasNextResult) : this.hasNextResult, radix);
            }
            catch (NumberFormatException ex) {
                hasNext = false;
            }
        }
        return hasNext;
    }
    
    public short nextShort() {
        return this.nextShort(this.defaultRadix);
    }
    
    public short nextShort(final int radix) {
        if (this.typeCache != null && this.typeCache instanceof Short && this.radix == radix) {
            final short shortValue = (short)this.typeCache;
            this.useTypeCache();
            return shortValue;
        }
        this.setRadix(radix);
        this.clearCaches();
        try {
            String s = this.next(this.integerPattern());
            if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) {
                s = this.processIntegerToken(s);
            }
            return Short.parseShort(s, radix);
        }
        catch (NumberFormatException ex) {
            this.position = this.matcher.start();
            throw new InputMismatchException(ex.getMessage());
        }
    }
    
    public boolean hasNextInt() {
        return this.hasNextInt(this.defaultRadix);
    }
    
    public boolean hasNextInt(final int radix) {
        this.setRadix(radix);
        boolean hasNext = this.hasNext(this.integerPattern());
        if (hasNext) {
            try {
                this.typeCache = Integer.parseInt((this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) ? this.processIntegerToken(this.hasNextResult) : this.hasNextResult, radix);
            }
            catch (NumberFormatException ex) {
                hasNext = false;
            }
        }
        return hasNext;
    }
    
    private String processIntegerToken(final String s) {
        String s2 = s.replaceAll("" + this.groupSeparator, "");
        boolean b = false;
        final int length = this.negativePrefix.length();
        if (length > 0 && s2.startsWith(this.negativePrefix)) {
            b = true;
            s2 = s2.substring(length);
        }
        final int length2 = this.negativeSuffix.length();
        if (length2 > 0 && s2.endsWith(this.negativeSuffix)) {
            b = true;
            s2 = s2.substring(s2.length() - length2, s2.length());
        }
        if (b) {
            s2 = "-" + s2;
        }
        return s2;
    }
    
    public int nextInt() {
        return this.nextInt(this.defaultRadix);
    }
    
    public int nextInt(final int radix) {
        if (this.typeCache != null && this.typeCache instanceof Integer && this.radix == radix) {
            final int intValue = (int)this.typeCache;
            this.useTypeCache();
            return intValue;
        }
        this.setRadix(radix);
        this.clearCaches();
        try {
            String s = this.next(this.integerPattern());
            if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) {
                s = this.processIntegerToken(s);
            }
            return Integer.parseInt(s, radix);
        }
        catch (NumberFormatException ex) {
            this.position = this.matcher.start();
            throw new InputMismatchException(ex.getMessage());
        }
    }
    
    public boolean hasNextLong() {
        return this.hasNextLong(this.defaultRadix);
    }
    
    public boolean hasNextLong(final int radix) {
        this.setRadix(radix);
        boolean hasNext = this.hasNext(this.integerPattern());
        if (hasNext) {
            try {
                this.typeCache = Long.parseLong((this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) ? this.processIntegerToken(this.hasNextResult) : this.hasNextResult, radix);
            }
            catch (NumberFormatException ex) {
                hasNext = false;
            }
        }
        return hasNext;
    }
    
    public long nextLong() {
        return this.nextLong(this.defaultRadix);
    }
    
    public long nextLong(final int radix) {
        if (this.typeCache != null && this.typeCache instanceof Long && this.radix == radix) {
            final long longValue = (long)this.typeCache;
            this.useTypeCache();
            return longValue;
        }
        this.setRadix(radix);
        this.clearCaches();
        try {
            String s = this.next(this.integerPattern());
            if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) {
                s = this.processIntegerToken(s);
            }
            return Long.parseLong(s, radix);
        }
        catch (NumberFormatException ex) {
            this.position = this.matcher.start();
            throw new InputMismatchException(ex.getMessage());
        }
    }
    
    private String processFloatToken(final String s) {
        String s2 = s.replaceAll(this.groupSeparator, "");
        if (!this.decimalSeparator.equals("\\.")) {
            s2 = s2.replaceAll(this.decimalSeparator, ".");
        }
        boolean b = false;
        final int length = this.negativePrefix.length();
        if (length > 0 && s2.startsWith(this.negativePrefix)) {
            b = true;
            s2 = s2.substring(length);
        }
        final int length2 = this.negativeSuffix.length();
        if (length2 > 0 && s2.endsWith(this.negativeSuffix)) {
            b = true;
            s2 = s2.substring(s2.length() - length2, s2.length());
        }
        if (s2.equals(this.nanString)) {
            s2 = "NaN";
        }
        if (s2.equals(this.infinityString)) {
            s2 = "Infinity";
        }
        if (b) {
            s2 = "-" + s2;
        }
        if (Scanner.NON_ASCII_DIGIT.matcher(s2).find()) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s2.length(); ++i) {
                final char char1 = s2.charAt(i);
                if (Character.isDigit(char1)) {
                    final int digit = Character.digit(char1, 10);
                    if (digit != -1) {
                        sb.append(digit);
                    }
                    else {
                        sb.append(char1);
                    }
                }
                else {
                    sb.append(char1);
                }
            }
            s2 = sb.toString();
        }
        return s2;
    }
    
    public boolean hasNextFloat() {
        this.setRadix(10);
        boolean hasNext = this.hasNext(this.floatPattern());
        if (hasNext) {
            try {
                this.typeCache = Float.parseFloat(this.processFloatToken(this.hasNextResult));
            }
            catch (NumberFormatException ex) {
                hasNext = false;
            }
        }
        return hasNext;
    }
    
    public float nextFloat() {
        if (this.typeCache != null && this.typeCache instanceof Float) {
            final float floatValue = (float)this.typeCache;
            this.useTypeCache();
            return floatValue;
        }
        this.setRadix(10);
        this.clearCaches();
        try {
            return Float.parseFloat(this.processFloatToken(this.next(this.floatPattern())));
        }
        catch (NumberFormatException ex) {
            this.position = this.matcher.start();
            throw new InputMismatchException(ex.getMessage());
        }
    }
    
    public boolean hasNextDouble() {
        this.setRadix(10);
        boolean hasNext = this.hasNext(this.floatPattern());
        if (hasNext) {
            try {
                this.typeCache = Double.parseDouble(this.processFloatToken(this.hasNextResult));
            }
            catch (NumberFormatException ex) {
                hasNext = false;
            }
        }
        return hasNext;
    }
    
    public double nextDouble() {
        if (this.typeCache != null && this.typeCache instanceof Double) {
            final double doubleValue = (double)this.typeCache;
            this.useTypeCache();
            return doubleValue;
        }
        this.setRadix(10);
        this.clearCaches();
        try {
            return Double.parseDouble(this.processFloatToken(this.next(this.floatPattern())));
        }
        catch (NumberFormatException ex) {
            this.position = this.matcher.start();
            throw new InputMismatchException(ex.getMessage());
        }
    }
    
    public boolean hasNextBigInteger() {
        return this.hasNextBigInteger(this.defaultRadix);
    }
    
    public boolean hasNextBigInteger(final int radix) {
        this.setRadix(radix);
        boolean hasNext = this.hasNext(this.integerPattern());
        if (hasNext) {
            try {
                this.typeCache = new BigInteger((this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) ? this.processIntegerToken(this.hasNextResult) : this.hasNextResult, radix);
            }
            catch (NumberFormatException ex) {
                hasNext = false;
            }
        }
        return hasNext;
    }
    
    public BigInteger nextBigInteger() {
        return this.nextBigInteger(this.defaultRadix);
    }
    
    public BigInteger nextBigInteger(final int radix) {
        if (this.typeCache != null && this.typeCache instanceof BigInteger && this.radix == radix) {
            final BigInteger bigInteger = (BigInteger)this.typeCache;
            this.useTypeCache();
            return bigInteger;
        }
        this.setRadix(radix);
        this.clearCaches();
        try {
            String s = this.next(this.integerPattern());
            if (this.matcher.group(this.SIMPLE_GROUP_INDEX) == null) {
                s = this.processIntegerToken(s);
            }
            return new BigInteger(s, radix);
        }
        catch (NumberFormatException ex) {
            this.position = this.matcher.start();
            throw new InputMismatchException(ex.getMessage());
        }
    }
    
    public boolean hasNextBigDecimal() {
        this.setRadix(10);
        boolean hasNext = this.hasNext(this.decimalPattern());
        if (hasNext) {
            try {
                this.typeCache = new BigDecimal(this.processFloatToken(this.hasNextResult));
            }
            catch (NumberFormatException ex) {
                hasNext = false;
            }
        }
        return hasNext;
    }
    
    public BigDecimal nextBigDecimal() {
        if (this.typeCache != null && this.typeCache instanceof BigDecimal) {
            final BigDecimal bigDecimal = (BigDecimal)this.typeCache;
            this.useTypeCache();
            return bigDecimal;
        }
        this.setRadix(10);
        this.clearCaches();
        try {
            return new BigDecimal(this.processFloatToken(this.next(this.decimalPattern())));
        }
        catch (NumberFormatException ex) {
            this.position = this.matcher.start();
            throw new InputMismatchException(ex.getMessage());
        }
    }
    
    public Scanner reset() {
        this.delimPattern = Scanner.WHITESPACE_PATTERN;
        this.useLocale(Locale.getDefault(Locale.Category.FORMAT));
        this.useRadix(10);
        this.clearCaches();
        return this;
    }
    
    static {
        Scanner.WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
        Scanner.FIND_ANY_PATTERN = Pattern.compile("(?s).*");
        Scanner.NON_ASCII_DIGIT = Pattern.compile("[\\p{javaDigit}&&[^0-9]]");
    }
}
