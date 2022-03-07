package java.util;

import java.nio.charset.*;
import java.util.regex.*;
import sun.misc.*;
import java.math.*;
import java.time.temporal.*;
import java.time.*;
import java.text.*;
import java.io.*;

public final class Formatter implements Closeable, Flushable
{
    private Appendable a;
    private final Locale l;
    private IOException lastException;
    private final char zero;
    private static double scaleUp;
    private static final int MAX_FD_CHARS = 30;
    private static final String formatSpecifier = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
    private static Pattern fsPattern;
    
    private static Charset toCharset(final String s) throws UnsupportedEncodingException {
        Objects.requireNonNull(s, "charsetName");
        try {
            return Charset.forName(s);
        }
        catch (IllegalCharsetNameException | UnsupportedCharsetException ex) {
            throw new UnsupportedEncodingException(s);
        }
    }
    
    private static final Appendable nonNullAppendable(final Appendable appendable) {
        if (appendable == null) {
            return new StringBuilder();
        }
        return appendable;
    }
    
    private Formatter(final Locale l, final Appendable a) {
        this.a = a;
        this.l = l;
        this.zero = getZero(l);
    }
    
    private Formatter(final Charset charset, final Locale locale, final File file) throws FileNotFoundException {
        this(locale, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset)));
    }
    
    public Formatter() {
        this(Locale.getDefault(Locale.Category.FORMAT), new StringBuilder());
    }
    
    public Formatter(final Appendable appendable) {
        this(Locale.getDefault(Locale.Category.FORMAT), nonNullAppendable(appendable));
    }
    
    public Formatter(final Locale locale) {
        this(locale, new StringBuilder());
    }
    
    public Formatter(final Appendable appendable, final Locale locale) {
        this(locale, nonNullAppendable(appendable));
    }
    
    public Formatter(final String s) throws FileNotFoundException {
        this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(new FileOutputStream(s))));
    }
    
    public Formatter(final String s, final String s2) throws FileNotFoundException, UnsupportedEncodingException {
        this(s, s2, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public Formatter(final String s, final String s2, final Locale locale) throws FileNotFoundException, UnsupportedEncodingException {
        this(toCharset(s2), locale, new File(s));
    }
    
    public Formatter(final File file) throws FileNotFoundException {
        this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file))));
    }
    
    public Formatter(final File file, final String s) throws FileNotFoundException, UnsupportedEncodingException {
        this(file, s, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public Formatter(final File file, final String s, final Locale locale) throws FileNotFoundException, UnsupportedEncodingException {
        this(toCharset(s), locale, file);
    }
    
    public Formatter(final PrintStream printStream) {
        this(Locale.getDefault(Locale.Category.FORMAT), Objects.requireNonNull(printStream));
    }
    
    public Formatter(final OutputStream outputStream) {
        this(Locale.getDefault(Locale.Category.FORMAT), new BufferedWriter(new OutputStreamWriter(outputStream)));
    }
    
    public Formatter(final OutputStream outputStream, final String s) throws UnsupportedEncodingException {
        this(outputStream, s, Locale.getDefault(Locale.Category.FORMAT));
    }
    
    public Formatter(final OutputStream outputStream, final String s, final Locale locale) throws UnsupportedEncodingException {
        this(locale, new BufferedWriter(new OutputStreamWriter(outputStream, s)));
    }
    
    private static char getZero(final Locale locale) {
        if (locale != null && !locale.equals(Locale.US)) {
            return DecimalFormatSymbols.getInstance(locale).getZeroDigit();
        }
        return '0';
    }
    
    public Locale locale() {
        this.ensureOpen();
        return this.l;
    }
    
    public Appendable out() {
        this.ensureOpen();
        return this.a;
    }
    
    @Override
    public String toString() {
        this.ensureOpen();
        return this.a.toString();
    }
    
    @Override
    public void flush() {
        this.ensureOpen();
        if (this.a instanceof Flushable) {
            try {
                ((Flushable)this.a).flush();
            }
            catch (IOException lastException) {
                this.lastException = lastException;
            }
        }
    }
    
    @Override
    public void close() {
        if (this.a == null) {
            return;
        }
        try {
            if (this.a instanceof Closeable) {
                ((Closeable)this.a).close();
            }
        }
        catch (IOException lastException) {
            this.lastException = lastException;
        }
        finally {
            this.a = null;
        }
    }
    
    private void ensureOpen() {
        if (this.a == null) {
            throw new FormatterClosedException();
        }
    }
    
    public IOException ioException() {
        return this.lastException;
    }
    
    public Formatter format(final String s, final Object... array) {
        return this.format(this.l, s, array);
    }
    
    public Formatter format(final Locale locale, final String s, final Object... array) {
        this.ensureOpen();
        int n = -1;
        int n2 = -1;
        final FormatString[] parse = this.parse(s);
        for (int i = 0; i < parse.length; ++i) {
            final FormatString formatString = parse[i];
            final int index = formatString.index();
            try {
                switch (index) {
                    case -2: {
                        formatString.print(null, locale);
                        break;
                    }
                    case -1: {
                        if (n < 0 || (array != null && n > array.length - 1)) {
                            throw new MissingFormatArgumentException(formatString.toString());
                        }
                        formatString.print((array == null) ? null : array[n], locale);
                        break;
                    }
                    case 0: {
                        n = ++n2;
                        if (array != null && n2 > array.length - 1) {
                            throw new MissingFormatArgumentException(formatString.toString());
                        }
                        formatString.print((array == null) ? null : array[n2], locale);
                        break;
                    }
                    default: {
                        n = index - 1;
                        if (array != null && n > array.length - 1) {
                            throw new MissingFormatArgumentException(formatString.toString());
                        }
                        formatString.print((array == null) ? null : array[n], locale);
                        break;
                    }
                }
            }
            catch (IOException lastException) {
                this.lastException = lastException;
            }
        }
        return this;
    }
    
    private FormatString[] parse(final String s) {
        final ArrayList<FixedString> list = new ArrayList<FixedString>();
        final Matcher matcher = Formatter.fsPattern.matcher(s);
        for (int i = 0, length = s.length(); i < length; i = matcher.end()) {
            if (!matcher.find(i)) {
                checkText(s, i, length);
                list.add(new FixedString(s.substring(i)));
                break;
            }
            if (matcher.start() != i) {
                checkText(s, i, matcher.start());
                list.add(new FixedString(s.substring(i, matcher.start())));
            }
            list.add((FixedString)new FormatSpecifier(matcher));
        }
        return list.toArray(new FormatString[list.size()]);
    }
    
    private static void checkText(final String s, final int n, final int n2) {
        for (int i = n; i < n2; ++i) {
            if (s.charAt(i) == '%') {
                throw new UnknownFormatConversionException(String.valueOf((i == n2 - 1) ? '%' : s.charAt(i + 1)));
            }
        }
    }
    
    static {
        Formatter.fsPattern = Pattern.compile("%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])");
    }
    
    public enum BigDecimalLayoutForm
    {
        SCIENTIFIC, 
        DECIMAL_FLOAT;
    }
    
    private static class Conversion
    {
        static final char DECIMAL_INTEGER = 'd';
        static final char OCTAL_INTEGER = 'o';
        static final char HEXADECIMAL_INTEGER = 'x';
        static final char HEXADECIMAL_INTEGER_UPPER = 'X';
        static final char SCIENTIFIC = 'e';
        static final char SCIENTIFIC_UPPER = 'E';
        static final char GENERAL = 'g';
        static final char GENERAL_UPPER = 'G';
        static final char DECIMAL_FLOAT = 'f';
        static final char HEXADECIMAL_FLOAT = 'a';
        static final char HEXADECIMAL_FLOAT_UPPER = 'A';
        static final char CHARACTER = 'c';
        static final char CHARACTER_UPPER = 'C';
        static final char DATE_TIME = 't';
        static final char DATE_TIME_UPPER = 'T';
        static final char BOOLEAN = 'b';
        static final char BOOLEAN_UPPER = 'B';
        static final char STRING = 's';
        static final char STRING_UPPER = 'S';
        static final char HASHCODE = 'h';
        static final char HASHCODE_UPPER = 'H';
        static final char LINE_SEPARATOR = 'n';
        static final char PERCENT_SIGN = '%';
        
        static boolean isValid(final char c) {
            return isGeneral(c) || isInteger(c) || isFloat(c) || isText(c) || c == 't' || isCharacter(c);
        }
        
        static boolean isGeneral(final char c) {
            switch (c) {
                case 'B':
                case 'H':
                case 'S':
                case 'b':
                case 'h':
                case 's': {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        
        static boolean isCharacter(final char c) {
            switch (c) {
                case 'C':
                case 'c': {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        
        static boolean isInteger(final char c) {
            switch (c) {
                case 'X':
                case 'd':
                case 'o':
                case 'x': {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        
        static boolean isFloat(final char c) {
            switch (c) {
                case 'A':
                case 'E':
                case 'G':
                case 'a':
                case 'e':
                case 'f':
                case 'g': {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        
        static boolean isText(final char c) {
            switch (c) {
                case '%':
                case 'n': {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
    }
    
    private static class DateTime
    {
        static final char HOUR_OF_DAY_0 = 'H';
        static final char HOUR_0 = 'I';
        static final char HOUR_OF_DAY = 'k';
        static final char HOUR = 'l';
        static final char MINUTE = 'M';
        static final char NANOSECOND = 'N';
        static final char MILLISECOND = 'L';
        static final char MILLISECOND_SINCE_EPOCH = 'Q';
        static final char AM_PM = 'p';
        static final char SECONDS_SINCE_EPOCH = 's';
        static final char SECOND = 'S';
        static final char TIME = 'T';
        static final char ZONE_NUMERIC = 'z';
        static final char ZONE = 'Z';
        static final char NAME_OF_DAY_ABBREV = 'a';
        static final char NAME_OF_DAY = 'A';
        static final char NAME_OF_MONTH_ABBREV = 'b';
        static final char NAME_OF_MONTH = 'B';
        static final char CENTURY = 'C';
        static final char DAY_OF_MONTH_0 = 'd';
        static final char DAY_OF_MONTH = 'e';
        static final char NAME_OF_MONTH_ABBREV_X = 'h';
        static final char DAY_OF_YEAR = 'j';
        static final char MONTH = 'm';
        static final char YEAR_2 = 'y';
        static final char YEAR_4 = 'Y';
        static final char TIME_12_HOUR = 'r';
        static final char TIME_24_HOUR = 'R';
        static final char DATE_TIME = 'c';
        static final char DATE = 'D';
        static final char ISO_STANDARD_DATE = 'F';
        
        static boolean isValid(final char c) {
            switch (c) {
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'F':
                case 'H':
                case 'I':
                case 'L':
                case 'M':
                case 'N':
                case 'Q':
                case 'R':
                case 'S':
                case 'T':
                case 'Y':
                case 'Z':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'h':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'p':
                case 'r':
                case 's':
                case 'y':
                case 'z': {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
    }
    
    private class FixedString implements FormatString
    {
        private String s;
        
        FixedString(final String s) {
            this.s = s;
        }
        
        @Override
        public int index() {
            return -2;
        }
        
        @Override
        public void print(final Object o, final Locale locale) throws IOException {
            Formatter.this.a.append(this.s);
        }
        
        @Override
        public String toString() {
            return this.s;
        }
    }
    
    private interface FormatString
    {
        int index();
        
        void print(final Object p0, final Locale p1) throws IOException;
        
        String toString();
    }
    
    private static class Flags
    {
        private int flags;
        static final Flags NONE;
        static final Flags LEFT_JUSTIFY;
        static final Flags UPPERCASE;
        static final Flags ALTERNATE;
        static final Flags PLUS;
        static final Flags LEADING_SPACE;
        static final Flags ZERO_PAD;
        static final Flags GROUP;
        static final Flags PARENTHESES;
        static final Flags PREVIOUS;
        
        private Flags(final int flags) {
            this.flags = flags;
        }
        
        public int valueOf() {
            return this.flags;
        }
        
        public boolean contains(final Flags flags) {
            return (this.flags & flags.valueOf()) == flags.valueOf();
        }
        
        public Flags dup() {
            return new Flags(this.flags);
        }
        
        private Flags add(final Flags flags) {
            this.flags |= flags.valueOf();
            return this;
        }
        
        public Flags remove(final Flags flags) {
            this.flags &= ~flags.valueOf();
            return this;
        }
        
        public static Flags parse(final String s) {
            final char[] charArray = s.toCharArray();
            final Flags flags = new Flags(0);
            for (int i = 0; i < charArray.length; ++i) {
                final Flags parse = parse(charArray[i]);
                if (flags.contains(parse)) {
                    throw new DuplicateFormatFlagsException(parse.toString());
                }
                flags.add(parse);
            }
            return flags;
        }
        
        private static Flags parse(final char c) {
            switch (c) {
                case '-': {
                    return Flags.LEFT_JUSTIFY;
                }
                case '#': {
                    return Flags.ALTERNATE;
                }
                case '+': {
                    return Flags.PLUS;
                }
                case ' ': {
                    return Flags.LEADING_SPACE;
                }
                case '0': {
                    return Flags.ZERO_PAD;
                }
                case ',': {
                    return Flags.GROUP;
                }
                case '(': {
                    return Flags.PARENTHESES;
                }
                case '<': {
                    return Flags.PREVIOUS;
                }
                default: {
                    throw new UnknownFormatFlagsException(String.valueOf(c));
                }
            }
        }
        
        public static String toString(final Flags flags) {
            return flags.toString();
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            if (this.contains(Flags.LEFT_JUSTIFY)) {
                sb.append('-');
            }
            if (this.contains(Flags.UPPERCASE)) {
                sb.append('^');
            }
            if (this.contains(Flags.ALTERNATE)) {
                sb.append('#');
            }
            if (this.contains(Flags.PLUS)) {
                sb.append('+');
            }
            if (this.contains(Flags.LEADING_SPACE)) {
                sb.append(' ');
            }
            if (this.contains(Flags.ZERO_PAD)) {
                sb.append('0');
            }
            if (this.contains(Flags.GROUP)) {
                sb.append(',');
            }
            if (this.contains(Flags.PARENTHESES)) {
                sb.append('(');
            }
            if (this.contains(Flags.PREVIOUS)) {
                sb.append('<');
            }
            return sb.toString();
        }
        
        static {
            NONE = new Flags(0);
            LEFT_JUSTIFY = new Flags(1);
            UPPERCASE = new Flags(2);
            ALTERNATE = new Flags(4);
            PLUS = new Flags(8);
            LEADING_SPACE = new Flags(16);
            ZERO_PAD = new Flags(32);
            GROUP = new Flags(64);
            PARENTHESES = new Flags(128);
            PREVIOUS = new Flags(256);
        }
    }
    
    private class FormatSpecifier implements FormatString
    {
        private int index;
        private Flags f;
        private int width;
        private int precision;
        private boolean dt;
        private char c;
        static final /* synthetic */ boolean $assertionsDisabled;
        
        private int index(final String s) {
            if (s != null) {
                try {
                    this.index = Integer.parseInt(s.substring(0, s.length() - 1));
                }
                catch (NumberFormatException ex) {
                    assert false;
                }
            }
            else {
                this.index = 0;
            }
            return this.index;
        }
        
        @Override
        public int index() {
            return this.index;
        }
        
        private Flags flags(final String s) {
            this.f = Flags.parse(s);
            if (this.f.contains(Flags.PREVIOUS)) {
                this.index = -1;
            }
            return this.f;
        }
        
        Flags flags() {
            return this.f;
        }
        
        private int width(final String s) {
            this.width = -1;
            if (s != null) {
                try {
                    this.width = Integer.parseInt(s);
                    if (this.width < 0) {
                        throw new IllegalFormatWidthException(this.width);
                    }
                }
                catch (NumberFormatException ex) {
                    assert false;
                }
            }
            return this.width;
        }
        
        int width() {
            return this.width;
        }
        
        private int precision(final String s) {
            this.precision = -1;
            if (s != null) {
                try {
                    this.precision = Integer.parseInt(s.substring(1));
                    if (this.precision < 0) {
                        throw new IllegalFormatPrecisionException(this.precision);
                    }
                }
                catch (NumberFormatException ex) {
                    assert false;
                }
            }
            return this.precision;
        }
        
        int precision() {
            return this.precision;
        }
        
        private char conversion(final String s) {
            this.c = s.charAt(0);
            if (!this.dt) {
                if (!Conversion.isValid(this.c)) {
                    throw new UnknownFormatConversionException(String.valueOf(this.c));
                }
                if (Character.isUpperCase(this.c)) {
                    this.f.add(Flags.UPPERCASE);
                }
                this.c = Character.toLowerCase(this.c);
                if (Conversion.isText(this.c)) {
                    this.index = -2;
                }
            }
            return this.c;
        }
        
        private char conversion() {
            return this.c;
        }
        
        FormatSpecifier(final Matcher matcher) {
            this.index = -1;
            this.f = Flags.NONE;
            this.dt = false;
            int n = 1;
            this.index(matcher.group(n++));
            this.flags(matcher.group(n++));
            this.width(matcher.group(n++));
            this.precision(matcher.group(n++));
            final String group = matcher.group(n++);
            if (group != null) {
                this.dt = true;
                if (group.equals("T")) {
                    this.f.add(Flags.UPPERCASE);
                }
            }
            this.conversion(matcher.group(n));
            if (this.dt) {
                this.checkDateTime();
            }
            else if (Conversion.isGeneral(this.c)) {
                this.checkGeneral();
            }
            else if (Conversion.isCharacter(this.c)) {
                this.checkCharacter();
            }
            else if (Conversion.isInteger(this.c)) {
                this.checkInteger();
            }
            else if (Conversion.isFloat(this.c)) {
                this.checkFloat();
            }
            else {
                if (!Conversion.isText(this.c)) {
                    throw new UnknownFormatConversionException(String.valueOf(this.c));
                }
                this.checkText();
            }
        }
        
        @Override
        public void print(final Object o, final Locale locale) throws IOException {
            if (this.dt) {
                this.printDateTime(o, locale);
                return;
            }
            switch (this.c) {
                case 'd':
                case 'o':
                case 'x': {
                    this.printInteger(o, locale);
                    break;
                }
                case 'a':
                case 'e':
                case 'f':
                case 'g': {
                    this.printFloat(o, locale);
                    break;
                }
                case 'C':
                case 'c': {
                    this.printCharacter(o);
                    break;
                }
                case 'b': {
                    this.printBoolean(o);
                    break;
                }
                case 's': {
                    this.printString(o, locale);
                    break;
                }
                case 'h': {
                    this.printHashCode(o);
                    break;
                }
                case 'n': {
                    Formatter.this.a.append(System.lineSeparator());
                    break;
                }
                case '%': {
                    Formatter.this.a.append('%');
                    break;
                }
                default: {
                    assert false;
                    break;
                }
            }
        }
        
        private void printInteger(final Object o, final Locale locale) throws IOException {
            if (o == null) {
                this.print("null");
            }
            else if (o instanceof Byte) {
                this.print((byte)o, locale);
            }
            else if (o instanceof Short) {
                this.print((short)o, locale);
            }
            else if (o instanceof Integer) {
                this.print((int)o, locale);
            }
            else if (o instanceof Long) {
                this.print((long)o, locale);
            }
            else if (o instanceof BigInteger) {
                this.print((BigInteger)o, locale);
            }
            else {
                this.failConversion(this.c, o);
            }
        }
        
        private void printFloat(final Object o, final Locale locale) throws IOException {
            if (o == null) {
                this.print("null");
            }
            else if (o instanceof Float) {
                this.print((float)o, locale);
            }
            else if (o instanceof Double) {
                this.print((double)o, locale);
            }
            else if (o instanceof BigDecimal) {
                this.print((BigDecimal)o, locale);
            }
            else {
                this.failConversion(this.c, o);
            }
        }
        
        private void printDateTime(final Object o, final Locale locale) throws IOException {
            if (o == null) {
                this.print("null");
                return;
            }
            Calendar calendar = null;
            if (o instanceof Long) {
                calendar = Calendar.getInstance((locale == null) ? Locale.US : locale);
                calendar.setTimeInMillis((long)o);
            }
            else if (o instanceof Date) {
                calendar = Calendar.getInstance((locale == null) ? Locale.US : locale);
                calendar.setTime((Date)o);
            }
            else if (o instanceof Calendar) {
                calendar = (Calendar)((Calendar)o).clone();
                calendar.setLenient(true);
            }
            else {
                if (o instanceof TemporalAccessor) {
                    this.print((TemporalAccessor)o, this.c, locale);
                    return;
                }
                this.failConversion(this.c, o);
            }
            this.print(calendar, this.c, locale);
        }
        
        private void printCharacter(final Object o) throws IOException {
            if (o == null) {
                this.print("null");
                return;
            }
            String string = null;
            if (o instanceof Character) {
                string = ((Character)o).toString();
            }
            else if (o instanceof Byte) {
                final byte byteValue = (byte)o;
                if (!Character.isValidCodePoint(byteValue)) {
                    throw new IllegalFormatCodePointException(byteValue);
                }
                string = new String(Character.toChars(byteValue));
            }
            else if (o instanceof Short) {
                final short shortValue = (short)o;
                if (!Character.isValidCodePoint(shortValue)) {
                    throw new IllegalFormatCodePointException(shortValue);
                }
                string = new String(Character.toChars(shortValue));
            }
            else if (o instanceof Integer) {
                final int intValue = (int)o;
                if (!Character.isValidCodePoint(intValue)) {
                    throw new IllegalFormatCodePointException(intValue);
                }
                string = new String(Character.toChars(intValue));
            }
            else {
                this.failConversion(this.c, o);
            }
            this.print(string);
        }
        
        private void printString(final Object o, final Locale locale) throws IOException {
            if (o instanceof Formattable) {
                Formatter this$0 = Formatter.this;
                if (this$0.locale() != locale) {
                    this$0 = new Formatter(this$0.out(), locale);
                }
                ((Formattable)o).formatTo(this$0, this.f.valueOf(), this.width, this.precision);
            }
            else {
                if (this.f.contains(Flags.ALTERNATE)) {
                    this.failMismatch(Flags.ALTERNATE, 's');
                }
                if (o == null) {
                    this.print("null");
                }
                else {
                    this.print(o.toString());
                }
            }
        }
        
        private void printBoolean(final Object o) throws IOException {
            String string;
            if (o != null) {
                string = ((o instanceof Boolean) ? ((Boolean)o).toString() : Boolean.toString(true));
            }
            else {
                string = Boolean.toString(false);
            }
            this.print(string);
        }
        
        private void printHashCode(final Object o) throws IOException {
            this.print((o == null) ? "null" : Integer.toHexString(o.hashCode()));
        }
        
        private void print(String s) throws IOException {
            if (this.precision != -1 && this.precision < s.length()) {
                s = s.substring(0, this.precision);
            }
            if (this.f.contains(Flags.UPPERCASE)) {
                s = s.toUpperCase();
            }
            Formatter.this.a.append(this.justify(s));
        }
        
        private String justify(final String s) {
            if (this.width == -1) {
                return s;
            }
            final StringBuilder sb = new StringBuilder();
            final boolean contains = this.f.contains(Flags.LEFT_JUSTIFY);
            final int n = this.width - s.length();
            if (!contains) {
                for (int i = 0; i < n; ++i) {
                    sb.append(' ');
                }
            }
            sb.append(s);
            if (contains) {
                for (int j = 0; j < n; ++j) {
                    sb.append(' ');
                }
            }
            return sb.toString();
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("%");
            sb.append(this.f.dup().remove(Flags.UPPERCASE).toString());
            if (this.index > 0) {
                sb.append(this.index).append('$');
            }
            if (this.width != -1) {
                sb.append(this.width);
            }
            if (this.precision != -1) {
                sb.append('.').append(this.precision);
            }
            if (this.dt) {
                sb.append(this.f.contains(Flags.UPPERCASE) ? 'T' : 't');
            }
            sb.append(this.f.contains(Flags.UPPERCASE) ? Character.toUpperCase(this.c) : this.c);
            return sb.toString();
        }
        
        private void checkGeneral() {
            if ((this.c == 'b' || this.c == 'h') && this.f.contains(Flags.ALTERNATE)) {
                this.failMismatch(Flags.ALTERNATE, this.c);
            }
            if (this.width == -1 && this.f.contains(Flags.LEFT_JUSTIFY)) {
                throw new MissingFormatWidthException(this.toString());
            }
            this.checkBadFlags(Flags.PLUS, Flags.LEADING_SPACE, Flags.ZERO_PAD, Flags.GROUP, Flags.PARENTHESES);
        }
        
        private void checkDateTime() {
            if (this.precision != -1) {
                throw new IllegalFormatPrecisionException(this.precision);
            }
            if (!DateTime.isValid(this.c)) {
                throw new UnknownFormatConversionException("t" + this.c);
            }
            this.checkBadFlags(Flags.ALTERNATE, Flags.PLUS, Flags.LEADING_SPACE, Flags.ZERO_PAD, Flags.GROUP, Flags.PARENTHESES);
            if (this.width == -1 && this.f.contains(Flags.LEFT_JUSTIFY)) {
                throw new MissingFormatWidthException(this.toString());
            }
        }
        
        private void checkCharacter() {
            if (this.precision != -1) {
                throw new IllegalFormatPrecisionException(this.precision);
            }
            this.checkBadFlags(Flags.ALTERNATE, Flags.PLUS, Flags.LEADING_SPACE, Flags.ZERO_PAD, Flags.GROUP, Flags.PARENTHESES);
            if (this.width == -1 && this.f.contains(Flags.LEFT_JUSTIFY)) {
                throw new MissingFormatWidthException(this.toString());
            }
        }
        
        private void checkInteger() {
            this.checkNumeric();
            if (this.precision != -1) {
                throw new IllegalFormatPrecisionException(this.precision);
            }
            if (this.c == 'd') {
                this.checkBadFlags(Flags.ALTERNATE);
            }
            else if (this.c == 'o') {
                this.checkBadFlags(Flags.GROUP);
            }
            else {
                this.checkBadFlags(Flags.GROUP);
            }
        }
        
        private void checkBadFlags(final Flags... array) {
            for (int i = 0; i < array.length; ++i) {
                if (this.f.contains(array[i])) {
                    this.failMismatch(array[i], this.c);
                }
            }
        }
        
        private void checkFloat() {
            this.checkNumeric();
            if (this.c != 'f') {
                if (this.c == 'a') {
                    this.checkBadFlags(Flags.PARENTHESES, Flags.GROUP);
                }
                else if (this.c == 'e') {
                    this.checkBadFlags(Flags.GROUP);
                }
                else if (this.c == 'g') {
                    this.checkBadFlags(Flags.ALTERNATE);
                }
            }
        }
        
        private void checkNumeric() {
            if (this.width != -1 && this.width < 0) {
                throw new IllegalFormatWidthException(this.width);
            }
            if (this.precision != -1 && this.precision < 0) {
                throw new IllegalFormatPrecisionException(this.precision);
            }
            if (this.width == -1 && (this.f.contains(Flags.LEFT_JUSTIFY) || this.f.contains(Flags.ZERO_PAD))) {
                throw new MissingFormatWidthException(this.toString());
            }
            if ((this.f.contains(Flags.PLUS) && this.f.contains(Flags.LEADING_SPACE)) || (this.f.contains(Flags.LEFT_JUSTIFY) && this.f.contains(Flags.ZERO_PAD))) {
                throw new IllegalFormatFlagsException(this.f.toString());
            }
        }
        
        private void checkText() {
            if (this.precision != -1) {
                throw new IllegalFormatPrecisionException(this.precision);
            }
            switch (this.c) {
                case '%': {
                    if (this.f.valueOf() != Flags.LEFT_JUSTIFY.valueOf() && this.f.valueOf() != Flags.NONE.valueOf()) {
                        throw new IllegalFormatFlagsException(this.f.toString());
                    }
                    if (this.width == -1 && this.f.contains(Flags.LEFT_JUSTIFY)) {
                        throw new MissingFormatWidthException(this.toString());
                    }
                    break;
                }
                case 'n': {
                    if (this.width != -1) {
                        throw new IllegalFormatWidthException(this.width);
                    }
                    if (this.f.valueOf() != Flags.NONE.valueOf()) {
                        throw new IllegalFormatFlagsException(this.f.toString());
                    }
                    break;
                }
                default: {
                    assert false;
                    break;
                }
            }
        }
        
        private void print(final byte b, final Locale locale) throws IOException {
            long n = b;
            if (b < 0 && (this.c == 'o' || this.c == 'x')) {
                n += 256L;
                assert n >= 0L : n;
            }
            this.print(n, locale);
        }
        
        private void print(final short n, final Locale locale) throws IOException {
            long n2 = n;
            if (n < 0 && (this.c == 'o' || this.c == 'x')) {
                n2 += 65536L;
                assert n2 >= 0L : n2;
            }
            this.print(n2, locale);
        }
        
        private void print(final int n, final Locale locale) throws IOException {
            long n2 = n;
            if (n < 0 && (this.c == 'o' || this.c == 'x')) {
                n2 += 4294967296L;
                assert n2 >= 0L : n2;
            }
            this.print(n2, locale);
        }
        
        private void print(final long n, final Locale locale) throws IOException {
            final StringBuilder sb = new StringBuilder();
            if (this.c == 'd') {
                final boolean b = n < 0L;
                char[] array;
                if (n < 0L) {
                    array = Long.toString(n, 10).substring(1).toCharArray();
                }
                else {
                    array = Long.toString(n, 10).toCharArray();
                }
                this.leadingSign(sb, b);
                this.localizedMagnitude(sb, array, this.f, this.adjustWidth(this.width, this.f, b), locale);
                this.trailingSign(sb, b);
            }
            else if (this.c == 'o') {
                this.checkBadFlags(Flags.PARENTHESES, Flags.LEADING_SPACE, Flags.PLUS);
                final String octalString = Long.toOctalString(n);
                final int n2 = this.f.contains(Flags.ALTERNATE) ? (octalString.length() + 1) : octalString.length();
                if (this.f.contains(Flags.ALTERNATE)) {
                    sb.append('0');
                }
                if (this.f.contains(Flags.ZERO_PAD)) {
                    for (int i = 0; i < this.width - n2; ++i) {
                        sb.append('0');
                    }
                }
                sb.append(octalString);
            }
            else if (this.c == 'x') {
                this.checkBadFlags(Flags.PARENTHESES, Flags.LEADING_SPACE, Flags.PLUS);
                String s = Long.toHexString(n);
                final int n3 = this.f.contains(Flags.ALTERNATE) ? (s.length() + 2) : s.length();
                if (this.f.contains(Flags.ALTERNATE)) {
                    sb.append(this.f.contains(Flags.UPPERCASE) ? "0X" : "0x");
                }
                if (this.f.contains(Flags.ZERO_PAD)) {
                    for (int j = 0; j < this.width - n3; ++j) {
                        sb.append('0');
                    }
                }
                if (this.f.contains(Flags.UPPERCASE)) {
                    s = s.toUpperCase();
                }
                sb.append(s);
            }
            Formatter.this.a.append(this.justify(sb.toString()));
        }
        
        private StringBuilder leadingSign(final StringBuilder sb, final boolean b) {
            if (!b) {
                if (this.f.contains(Flags.PLUS)) {
                    sb.append('+');
                }
                else if (this.f.contains(Flags.LEADING_SPACE)) {
                    sb.append(' ');
                }
            }
            else if (this.f.contains(Flags.PARENTHESES)) {
                sb.append('(');
            }
            else {
                sb.append('-');
            }
            return sb;
        }
        
        private StringBuilder trailingSign(final StringBuilder sb, final boolean b) {
            if (b && this.f.contains(Flags.PARENTHESES)) {
                sb.append(')');
            }
            return sb;
        }
        
        private void print(final BigInteger bigInteger, final Locale locale) throws IOException {
            final StringBuilder sb = new StringBuilder();
            final boolean b = bigInteger.signum() == -1;
            final BigInteger abs = bigInteger.abs();
            this.leadingSign(sb, b);
            if (this.c == 'd') {
                this.localizedMagnitude(sb, abs.toString().toCharArray(), this.f, this.adjustWidth(this.width, this.f, b), locale);
            }
            else if (this.c == 'o') {
                final String string = abs.toString(8);
                int n = string.length() + sb.length();
                if (b && this.f.contains(Flags.PARENTHESES)) {
                    ++n;
                }
                if (this.f.contains(Flags.ALTERNATE)) {
                    ++n;
                    sb.append('0');
                }
                if (this.f.contains(Flags.ZERO_PAD)) {
                    for (int i = 0; i < this.width - n; ++i) {
                        sb.append('0');
                    }
                }
                sb.append(string);
            }
            else if (this.c == 'x') {
                String s = abs.toString(16);
                int n2 = s.length() + sb.length();
                if (b && this.f.contains(Flags.PARENTHESES)) {
                    ++n2;
                }
                if (this.f.contains(Flags.ALTERNATE)) {
                    n2 += 2;
                    sb.append(this.f.contains(Flags.UPPERCASE) ? "0X" : "0x");
                }
                if (this.f.contains(Flags.ZERO_PAD)) {
                    for (int j = 0; j < this.width - n2; ++j) {
                        sb.append('0');
                    }
                }
                if (this.f.contains(Flags.UPPERCASE)) {
                    s = s.toUpperCase();
                }
                sb.append(s);
            }
            this.trailingSign(sb, bigInteger.signum() == -1);
            Formatter.this.a.append(this.justify(sb.toString()));
        }
        
        private void print(final float n, final Locale locale) throws IOException {
            this.print((double)n, locale);
        }
        
        private void print(final double n, final Locale locale) throws IOException {
            final StringBuilder sb = new StringBuilder();
            final boolean b = Double.compare(n, 0.0) == -1;
            if (!Double.isNaN(n)) {
                final double abs = Math.abs(n);
                this.leadingSign(sb, b);
                if (!Double.isInfinite(abs)) {
                    this.print(sb, abs, locale, this.f, this.c, this.precision, b);
                }
                else {
                    sb.append(this.f.contains(Flags.UPPERCASE) ? "INFINITY" : "Infinity");
                }
                this.trailingSign(sb, b);
            }
            else {
                sb.append(this.f.contains(Flags.UPPERCASE) ? "NAN" : "NaN");
            }
            Formatter.this.a.append(this.justify(sb.toString()));
        }
        
        private void print(final StringBuilder sb, final double n, final Locale locale, final Flags flags, final char c, final int n2, final boolean b) throws IOException {
            if (c == 'e') {
                final int n3 = (n2 == -1) ? 6 : n2;
                final FormattedFloatingDecimal value = FormattedFloatingDecimal.valueOf(n, n3, FormattedFloatingDecimal.Form.SCIENTIFIC);
                char[] array = this.addZeros(value.getMantissa(), n3);
                if (flags.contains(Flags.ALTERNATE) && n3 == 0) {
                    array = this.addDot(array);
                }
                char[] exponent;
                if (n == 0.0) {
                    final char[] array2 = exponent = new char[3];
                    array2[0] = '+';
                    array2[2] = (array2[1] = '0');
                }
                else {
                    exponent = value.getExponent();
                }
                final char[] array3 = exponent;
                int n4 = this.width;
                if (this.width != -1) {
                    n4 = this.adjustWidth(this.width - array3.length - 1, flags, b);
                }
                this.localizedMagnitude(sb, array, flags, n4, locale);
                sb.append(flags.contains(Flags.UPPERCASE) ? 'E' : 'e');
                final Flags remove = flags.dup().remove(Flags.GROUP);
                final char c2 = array3[0];
                assert c2 == '-';
                sb.append(c2);
                final char[] array4 = new char[array3.length - 1];
                System.arraycopy(array3, 1, array4, 0, array3.length - 1);
                sb.append((CharSequence)this.localizedMagnitude(null, array4, remove, -1, locale));
            }
            else if (c == 'f') {
                final int n5 = (n2 == -1) ? 6 : n2;
                char[] array5 = this.addZeros(FormattedFloatingDecimal.valueOf(n, n5, FormattedFloatingDecimal.Form.DECIMAL_FLOAT).getMantissa(), n5);
                if (flags.contains(Flags.ALTERNATE) && n5 == 0) {
                    array5 = this.addDot(array5);
                }
                int n6 = this.width;
                if (this.width != -1) {
                    n6 = this.adjustWidth(this.width, flags, b);
                }
                this.localizedMagnitude(sb, array5, flags, n6, locale);
            }
            else if (c == 'g') {
                int n7;
                if ((n7 = n2) == -1) {
                    n7 = 6;
                }
                else if (n2 == 0) {
                    n7 = 1;
                }
                Object exponent2;
                char[] mantissa;
                int exponentRounded;
                if (n == 0.0) {
                    exponent2 = null;
                    mantissa = new char[] { '0' };
                    exponentRounded = 0;
                }
                else {
                    final FormattedFloatingDecimal value2 = FormattedFloatingDecimal.valueOf(n, n7, FormattedFloatingDecimal.Form.GENERAL);
                    exponent2 = value2.getExponent();
                    mantissa = value2.getMantissa();
                    exponentRounded = value2.getExponentRounded();
                }
                if (exponent2 != null) {
                    --n7;
                }
                else {
                    n7 -= exponentRounded + 1;
                }
                char[] array6 = this.addZeros(mantissa, n7);
                if (flags.contains(Flags.ALTERNATE) && n7 == 0) {
                    array6 = this.addDot(array6);
                }
                int n8 = this.width;
                if (this.width != -1) {
                    if (exponent2 != null) {
                        n8 = this.adjustWidth(this.width - exponent2.length - 1, flags, b);
                    }
                    else {
                        n8 = this.adjustWidth(this.width, flags, b);
                    }
                }
                this.localizedMagnitude(sb, array6, flags, n8, locale);
                if (exponent2 != null) {
                    sb.append(flags.contains(Flags.UPPERCASE) ? 'E' : 'e');
                    final Flags remove2 = flags.dup().remove(Flags.GROUP);
                    final char c3 = exponent2[0];
                    assert c3 == '-';
                    sb.append(c3);
                    final char[] array7 = new char[exponent2.length - 1];
                    System.arraycopy(exponent2, 1, array7, 0, exponent2.length - 1);
                    sb.append((CharSequence)this.localizedMagnitude(null, array7, remove2, -1, locale));
                }
            }
            else if (c == 'a') {
                int n9;
                if ((n9 = n2) == -1) {
                    n9 = 0;
                }
                else if (n2 == 0) {
                    n9 = 1;
                }
                final String hexDouble = this.hexDouble(n, n9);
                final boolean contains = flags.contains(Flags.UPPERCASE);
                sb.append(contains ? "0X" : "0x");
                if (flags.contains(Flags.ZERO_PAD)) {
                    for (int i = 0; i < this.width - hexDouble.length() - 2; ++i) {
                        sb.append('0');
                    }
                }
                final int index = hexDouble.indexOf(112);
                char[] array8 = hexDouble.substring(0, index).toCharArray();
                if (contains) {
                    array8 = new String(array8).toUpperCase(Locale.US).toCharArray();
                }
                sb.append((n9 != 0) ? this.addZeros(array8, n9) : array8);
                sb.append((char)(contains ? 80 : 112));
                sb.append(hexDouble.substring(index + 1));
            }
        }
        
        private char[] addZeros(final char[] array, final int n) {
            int n2;
            for (n2 = 0; n2 < array.length && array[n2] != '.'; ++n2) {}
            int n3 = 0;
            if (n2 == array.length) {
                n3 = 1;
            }
            final int n4 = array.length - n2 - ((n3 != 0) ? 0 : 1);
            assert n4 <= n;
            if (n4 == n) {
                return array;
            }
            final char[] array2 = new char[array.length + n - n4 + n3];
            System.arraycopy(array, 0, array2, 0, array.length);
            int length = array.length;
            if (n3 != 0) {
                array2[array.length] = '.';
                ++length;
            }
            for (int i = length; i < array2.length; ++i) {
                array2[i] = '0';
            }
            return array2;
        }
        
        private String hexDouble(double n, final int n2) {
            if (!Double.isFinite(n) || n == 0.0 || n2 == 0 || n2 >= 13) {
                return Double.toHexString(n).substring(2);
            }
            assert n2 >= 1 && n2 <= 12;
            final boolean b = Math.getExponent(n) == -1023;
            if (b) {
                Formatter.scaleUp = Math.scalb(1.0, 54);
                n *= Formatter.scaleUp;
                final int exponent = Math.getExponent(n);
                assert exponent >= -1022 && exponent <= 1023 : exponent;
            }
            final int n3 = 53 - (1 + n2 * 4);
            assert n3 >= 1 && n3 < 53;
            final long doubleToLongBits = Double.doubleToLongBits(n);
            long n4 = (doubleToLongBits & Long.MAX_VALUE) >> n3;
            final long n5 = doubleToLongBits & ~(-1L << n3);
            final boolean b2 = (n4 & 0x1L) == 0x0L;
            final boolean b3 = (1L << n3 - 1 & n5) != 0x0L;
            final boolean b4 = n3 > 1 && (~(1L << n3 - 1) & n5) != 0x0L;
            if ((b2 && b3 && b4) || (!b2 && b3)) {
                ++n4;
            }
            final double longBitsToDouble = Double.longBitsToDouble((doubleToLongBits & Long.MIN_VALUE) | n4 << n3);
            if (Double.isInfinite(longBitsToDouble)) {
                return "1.0p1024";
            }
            final String substring = Double.toHexString(longBitsToDouble).substring(2);
            if (!b) {
                return substring;
            }
            final int index = substring.indexOf(112);
            if (index != -1) {
                return substring.substring(0, index) + "p" + Integer.toString(Integer.parseInt(substring.substring(index + 1)) - 54);
            }
            assert false;
            return null;
        }
        
        private void print(final BigDecimal bigDecimal, final Locale locale) throws IOException {
            if (this.c == 'a') {
                this.failConversion(this.c, bigDecimal);
            }
            final StringBuilder sb = new StringBuilder();
            final boolean b = bigDecimal.signum() == -1;
            final BigDecimal abs = bigDecimal.abs();
            this.leadingSign(sb, b);
            this.print(sb, abs, locale, this.f, this.c, this.precision, b);
            this.trailingSign(sb, b);
            Formatter.this.a.append(this.justify(sb.toString()));
        }
        
        private void print(final StringBuilder sb, BigDecimal setScale, final Locale locale, final Flags flags, final char c, final int n, final boolean b) throws IOException {
            if (c == 'e') {
                final int n2 = (n == -1) ? 6 : n;
                final int scale = setScale.scale();
                final int precision = setScale.precision();
                int n3 = 0;
                int n4;
                if (n2 > precision - 1) {
                    n4 = precision;
                    n3 = n2 - (precision - 1);
                }
                else {
                    n4 = n2 + 1;
                }
                final BigDecimal bigDecimal = new BigDecimal(setScale.unscaledValue(), scale, new MathContext(n4));
                final BigDecimalLayout bigDecimalLayout = new BigDecimalLayout(bigDecimal.unscaledValue(), bigDecimal.scale(), BigDecimalLayoutForm.SCIENTIFIC);
                char[] array = bigDecimalLayout.mantissa();
                if ((precision == 1 || !bigDecimalLayout.hasDot()) && (n3 > 0 || flags.contains(Flags.ALTERNATE))) {
                    array = this.addDot(array);
                }
                final char[] trailingZeros = this.trailingZeros(array, n3);
                final char[] exponent = bigDecimalLayout.exponent();
                int n5 = this.width;
                if (this.width != -1) {
                    n5 = this.adjustWidth(this.width - exponent.length - 1, flags, b);
                }
                this.localizedMagnitude(sb, trailingZeros, flags, n5, locale);
                sb.append(flags.contains(Flags.UPPERCASE) ? 'E' : 'e');
                final Flags remove = flags.dup().remove(Flags.GROUP);
                final char c2 = exponent[0];
                assert c2 == '-';
                sb.append(exponent[0]);
                final char[] array2 = new char[exponent.length - 1];
                System.arraycopy(exponent, 1, array2, 0, exponent.length - 1);
                sb.append((CharSequence)this.localizedMagnitude(null, array2, remove, -1, locale));
            }
            else if (c == 'f') {
                final int n6 = (n == -1) ? 6 : n;
                final int scale2 = setScale.scale();
                if (scale2 > n6) {
                    final int precision2 = setScale.precision();
                    if (precision2 <= scale2) {
                        setScale = setScale.setScale(n6, RoundingMode.HALF_UP);
                    }
                    else {
                        setScale = new BigDecimal(setScale.unscaledValue(), scale2, new MathContext(precision2 - (scale2 - n6)));
                    }
                }
                final BigDecimalLayout bigDecimalLayout2 = new BigDecimalLayout(setScale.unscaledValue(), setScale.scale(), BigDecimalLayoutForm.DECIMAL_FLOAT);
                char[] array3 = bigDecimalLayout2.mantissa();
                final int n7 = (bigDecimalLayout2.scale() < n6) ? (n6 - bigDecimalLayout2.scale()) : 0;
                if (bigDecimalLayout2.scale() == 0 && (flags.contains(Flags.ALTERNATE) || n7 > 0)) {
                    array3 = this.addDot(bigDecimalLayout2.mantissa());
                }
                this.localizedMagnitude(sb, this.trailingZeros(array3, n7), flags, this.adjustWidth(this.width, flags, b), locale);
            }
            else if (c == 'g') {
                int n8;
                if ((n8 = n) == -1) {
                    n8 = 6;
                }
                else if (n == 0) {
                    n8 = 1;
                }
                final BigDecimal value = BigDecimal.valueOf(1L, 4);
                final BigDecimal value2 = BigDecimal.valueOf(1L, -n8);
                if (setScale.equals(BigDecimal.ZERO) || (setScale.compareTo(value) != -1 && setScale.compareTo(value2) == -1)) {
                    this.print(sb, setScale, locale, flags, 'f', n8 - (-setScale.scale() + (setScale.unscaledValue().toString().length() - 1)) - 1, b);
                }
                else {
                    this.print(sb, setScale, locale, flags, 'e', n8 - 1, b);
                }
            }
            else if (c == 'a' && !FormatSpecifier.$assertionsDisabled) {
                throw new AssertionError();
            }
        }
        
        private int adjustWidth(final int n, final Flags flags, final boolean b) {
            int n2 = n;
            if (n2 != -1 && b && flags.contains(Flags.PARENTHESES)) {
                --n2;
            }
            return n2;
        }
        
        private char[] addDot(final char[] array) {
            final char[] array2 = new char[array.length + 1];
            System.arraycopy(array, 0, array2, 0, array.length);
            array2[array2.length - 1] = '.';
            return array2;
        }
        
        private char[] trailingZeros(final char[] array, final int n) {
            char[] array2 = array;
            if (n > 0) {
                array2 = new char[array.length + n];
                System.arraycopy(array, 0, array2, 0, array.length);
                for (int i = array.length; i < array2.length; ++i) {
                    array2[i] = '0';
                }
            }
            return array2;
        }
        
        private void print(final Calendar calendar, final char c, final Locale locale) throws IOException {
            final StringBuilder sb = new StringBuilder();
            this.print(sb, calendar, c, locale);
            String s = this.justify(sb.toString());
            if (this.f.contains(Flags.UPPERCASE)) {
                s = s.toUpperCase();
            }
            Formatter.this.a.append(s);
        }
        
        private Appendable print(StringBuilder sb, final Calendar calendar, final char c, final Locale locale) throws IOException {
            if (sb == null) {
                sb = new StringBuilder();
            }
            switch (c) {
                case 'H':
                case 'I':
                case 'k':
                case 'l': {
                    int value = calendar.get(11);
                    if (c == 'I' || c == 'l') {
                        value = ((value == 0 || value == 12) ? 12 : (value % 12));
                    }
                    sb.append((CharSequence)this.localizedMagnitude(null, value, (c == 'H' || c == 'I') ? Flags.ZERO_PAD : Flags.NONE, 2, locale));
                    break;
                }
                case 'M': {
                    sb.append((CharSequence)this.localizedMagnitude(null, calendar.get(12), Flags.ZERO_PAD, 2, locale));
                    break;
                }
                case 'N': {
                    sb.append((CharSequence)this.localizedMagnitude(null, calendar.get(14) * 1000000, Flags.ZERO_PAD, 9, locale));
                    break;
                }
                case 'L': {
                    sb.append((CharSequence)this.localizedMagnitude(null, calendar.get(14), Flags.ZERO_PAD, 3, locale));
                    break;
                }
                case 'Q': {
                    sb.append((CharSequence)this.localizedMagnitude(null, calendar.getTimeInMillis(), Flags.NONE, this.width, locale));
                    break;
                }
                case 'p': {
                    String[] amPmStrings = { "AM", "PM" };
                    if (locale != null && locale != Locale.US) {
                        amPmStrings = DateFormatSymbols.getInstance(locale).getAmPmStrings();
                    }
                    sb.append(amPmStrings[calendar.get(9)].toLowerCase((locale != null) ? locale : Locale.US));
                    break;
                }
                case 's': {
                    sb.append((CharSequence)this.localizedMagnitude(null, calendar.getTimeInMillis() / 1000L, Flags.NONE, this.width, locale));
                    break;
                }
                case 'S': {
                    sb.append((CharSequence)this.localizedMagnitude(null, calendar.get(13), Flags.ZERO_PAD, 2, locale));
                    break;
                }
                case 'z': {
                    int n = calendar.get(15) + calendar.get(16);
                    final boolean b = n < 0;
                    sb.append((char)(b ? 45 : 43));
                    if (b) {
                        n = -n;
                    }
                    final int n2 = n / 60000;
                    sb.append((CharSequence)this.localizedMagnitude(null, n2 / 60 * 100 + n2 % 60, Flags.ZERO_PAD, 4, locale));
                    break;
                }
                case 'Z': {
                    sb.append(calendar.getTimeZone().getDisplayName(calendar.get(16) != 0, 0, (locale == null) ? Locale.US : locale));
                    break;
                }
                case 'A':
                case 'a': {
                    final int value2 = calendar.get(7);
                    final DateFormatSymbols instance = DateFormatSymbols.getInstance((locale == null) ? Locale.US : locale);
                    if (c == 'A') {
                        sb.append(instance.getWeekdays()[value2]);
                        break;
                    }
                    sb.append(instance.getShortWeekdays()[value2]);
                    break;
                }
                case 'B':
                case 'b':
                case 'h': {
                    final int value3 = calendar.get(2);
                    final DateFormatSymbols instance2 = DateFormatSymbols.getInstance((locale == null) ? Locale.US : locale);
                    if (c == 'B') {
                        sb.append(instance2.getMonths()[value3]);
                        break;
                    }
                    sb.append(instance2.getShortMonths()[value3]);
                    break;
                }
                case 'C':
                case 'Y':
                case 'y': {
                    int value4 = calendar.get(1);
                    int n3 = 2;
                    switch (c) {
                        case 'C': {
                            value4 /= 100;
                            break;
                        }
                        case 'y': {
                            value4 %= 100;
                            break;
                        }
                        case 'Y': {
                            n3 = 4;
                            break;
                        }
                    }
                    sb.append((CharSequence)this.localizedMagnitude(null, value4, Flags.ZERO_PAD, n3, locale));
                    break;
                }
                case 'd':
                case 'e': {
                    sb.append((CharSequence)this.localizedMagnitude(null, calendar.get(5), (c == 'd') ? Flags.ZERO_PAD : Flags.NONE, 2, locale));
                    break;
                }
                case 'j': {
                    sb.append((CharSequence)this.localizedMagnitude(null, calendar.get(6), Flags.ZERO_PAD, 3, locale));
                    break;
                }
                case 'm': {
                    sb.append((CharSequence)this.localizedMagnitude(null, calendar.get(2) + 1, Flags.ZERO_PAD, 2, locale));
                    break;
                }
                case 'R':
                case 'T': {
                    final char c2 = ':';
                    this.print(sb, calendar, 'H', locale).append(c2);
                    this.print(sb, calendar, 'M', locale);
                    if (c == 'T') {
                        sb.append(c2);
                        this.print(sb, calendar, 'S', locale);
                        break;
                    }
                    break;
                }
                case 'r': {
                    final char c3 = ':';
                    this.print(sb, calendar, 'I', locale).append(c3);
                    this.print(sb, calendar, 'M', locale).append(c3);
                    this.print(sb, calendar, 'S', locale).append(' ');
                    final StringBuilder sb2 = new StringBuilder();
                    this.print(sb2, calendar, 'p', locale);
                    sb.append(sb2.toString().toUpperCase((locale != null) ? locale : Locale.US));
                    break;
                }
                case 'c': {
                    final char c4 = ' ';
                    this.print(sb, calendar, 'a', locale).append(c4);
                    this.print(sb, calendar, 'b', locale).append(c4);
                    this.print(sb, calendar, 'd', locale).append(c4);
                    this.print(sb, calendar, 'T', locale).append(c4);
                    this.print(sb, calendar, 'Z', locale).append(c4);
                    this.print(sb, calendar, 'Y', locale);
                    break;
                }
                case 'D': {
                    final char c5 = '/';
                    this.print(sb, calendar, 'm', locale).append(c5);
                    this.print(sb, calendar, 'd', locale).append(c5);
                    this.print(sb, calendar, 'y', locale);
                    break;
                }
                case 'F': {
                    final char c6 = '-';
                    this.print(sb, calendar, 'Y', locale).append(c6);
                    this.print(sb, calendar, 'm', locale).append(c6);
                    this.print(sb, calendar, 'd', locale);
                    break;
                }
                default: {
                    assert false;
                    break;
                }
            }
            return sb;
        }
        
        private void print(final TemporalAccessor temporalAccessor, final char c, final Locale locale) throws IOException {
            final StringBuilder sb = new StringBuilder();
            this.print(sb, temporalAccessor, c, locale);
            String s = this.justify(sb.toString());
            if (this.f.contains(Flags.UPPERCASE)) {
                s = s.toUpperCase();
            }
            Formatter.this.a.append(s);
        }
        
        private Appendable print(StringBuilder sb, final TemporalAccessor temporalAccessor, final char c, final Locale locale) throws IOException {
            if (sb == null) {
                sb = new StringBuilder();
            }
            try {
                switch (c) {
                    case 'H': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.HOUR_OF_DAY), Flags.ZERO_PAD, 2, locale));
                        break;
                    }
                    case 'k': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.HOUR_OF_DAY), Flags.NONE, 2, locale));
                        break;
                    }
                    case 'I': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.CLOCK_HOUR_OF_AMPM), Flags.ZERO_PAD, 2, locale));
                        break;
                    }
                    case 'l': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.CLOCK_HOUR_OF_AMPM), Flags.NONE, 2, locale));
                        break;
                    }
                    case 'M': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.MINUTE_OF_HOUR), Flags.ZERO_PAD, 2, locale));
                        break;
                    }
                    case 'N': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.MILLI_OF_SECOND) * 1000000, Flags.ZERO_PAD, 9, locale));
                        break;
                    }
                    case 'L': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.MILLI_OF_SECOND), Flags.ZERO_PAD, 3, locale));
                        break;
                    }
                    case 'Q': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.getLong(ChronoField.INSTANT_SECONDS) * 1000L + temporalAccessor.getLong(ChronoField.MILLI_OF_SECOND), Flags.NONE, this.width, locale));
                        break;
                    }
                    case 'p': {
                        String[] amPmStrings = { "AM", "PM" };
                        if (locale != null && locale != Locale.US) {
                            amPmStrings = DateFormatSymbols.getInstance(locale).getAmPmStrings();
                        }
                        sb.append(amPmStrings[temporalAccessor.get(ChronoField.AMPM_OF_DAY)].toLowerCase((locale != null) ? locale : Locale.US));
                        break;
                    }
                    case 's': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.getLong(ChronoField.INSTANT_SECONDS), Flags.NONE, this.width, locale));
                        break;
                    }
                    case 'S': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.SECOND_OF_MINUTE), Flags.ZERO_PAD, 2, locale));
                        break;
                    }
                    case 'z': {
                        int value = temporalAccessor.get(ChronoField.OFFSET_SECONDS);
                        final boolean b = value < 0;
                        sb.append((char)(b ? 45 : 43));
                        if (b) {
                            value = -value;
                        }
                        final int n = value / 60;
                        sb.append((CharSequence)this.localizedMagnitude(null, n / 60 * 100 + n % 60, Flags.ZERO_PAD, 4, locale));
                        break;
                    }
                    case 'Z': {
                        final ZoneId zoneId = temporalAccessor.query(TemporalQueries.zone());
                        if (zoneId == null) {
                            throw new IllegalFormatConversionException(c, temporalAccessor.getClass());
                        }
                        if (!(zoneId instanceof ZoneOffset) && temporalAccessor.isSupported(ChronoField.INSTANT_SECONDS)) {
                            sb.append(TimeZone.getTimeZone(zoneId.getId()).getDisplayName(zoneId.getRules().isDaylightSavings(Instant.from(temporalAccessor)), 0, (locale == null) ? Locale.US : locale));
                            break;
                        }
                        sb.append(zoneId.getId());
                        break;
                    }
                    case 'A':
                    case 'a': {
                        final int n2 = temporalAccessor.get(ChronoField.DAY_OF_WEEK) % 7 + 1;
                        final DateFormatSymbols instance = DateFormatSymbols.getInstance((locale == null) ? Locale.US : locale);
                        if (c == 'A') {
                            sb.append(instance.getWeekdays()[n2]);
                            break;
                        }
                        sb.append(instance.getShortWeekdays()[n2]);
                        break;
                    }
                    case 'B':
                    case 'b':
                    case 'h': {
                        final int n3 = temporalAccessor.get(ChronoField.MONTH_OF_YEAR) - 1;
                        final DateFormatSymbols instance2 = DateFormatSymbols.getInstance((locale == null) ? Locale.US : locale);
                        if (c == 'B') {
                            sb.append(instance2.getMonths()[n3]);
                            break;
                        }
                        sb.append(instance2.getShortMonths()[n3]);
                        break;
                    }
                    case 'C':
                    case 'Y':
                    case 'y': {
                        int value2 = temporalAccessor.get(ChronoField.YEAR_OF_ERA);
                        int n4 = 2;
                        switch (c) {
                            case 'C': {
                                value2 /= 100;
                                break;
                            }
                            case 'y': {
                                value2 %= 100;
                                break;
                            }
                            case 'Y': {
                                n4 = 4;
                                break;
                            }
                        }
                        sb.append((CharSequence)this.localizedMagnitude(null, value2, Flags.ZERO_PAD, n4, locale));
                        break;
                    }
                    case 'd':
                    case 'e': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.DAY_OF_MONTH), (c == 'd') ? Flags.ZERO_PAD : Flags.NONE, 2, locale));
                        break;
                    }
                    case 'j': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.DAY_OF_YEAR), Flags.ZERO_PAD, 3, locale));
                        break;
                    }
                    case 'm': {
                        sb.append((CharSequence)this.localizedMagnitude(null, temporalAccessor.get(ChronoField.MONTH_OF_YEAR), Flags.ZERO_PAD, 2, locale));
                        break;
                    }
                    case 'R':
                    case 'T': {
                        final char c2 = ':';
                        this.print(sb, temporalAccessor, 'H', locale).append(c2);
                        this.print(sb, temporalAccessor, 'M', locale);
                        if (c == 'T') {
                            sb.append(c2);
                            this.print(sb, temporalAccessor, 'S', locale);
                            break;
                        }
                        break;
                    }
                    case 'r': {
                        final char c3 = ':';
                        this.print(sb, temporalAccessor, 'I', locale).append(c3);
                        this.print(sb, temporalAccessor, 'M', locale).append(c3);
                        this.print(sb, temporalAccessor, 'S', locale).append(' ');
                        final StringBuilder sb2 = new StringBuilder();
                        this.print(sb2, temporalAccessor, 'p', locale);
                        sb.append(sb2.toString().toUpperCase((locale != null) ? locale : Locale.US));
                        break;
                    }
                    case 'c': {
                        final char c4 = ' ';
                        this.print(sb, temporalAccessor, 'a', locale).append(c4);
                        this.print(sb, temporalAccessor, 'b', locale).append(c4);
                        this.print(sb, temporalAccessor, 'd', locale).append(c4);
                        this.print(sb, temporalAccessor, 'T', locale).append(c4);
                        this.print(sb, temporalAccessor, 'Z', locale).append(c4);
                        this.print(sb, temporalAccessor, 'Y', locale);
                        break;
                    }
                    case 'D': {
                        final char c5 = '/';
                        this.print(sb, temporalAccessor, 'm', locale).append(c5);
                        this.print(sb, temporalAccessor, 'd', locale).append(c5);
                        this.print(sb, temporalAccessor, 'y', locale);
                        break;
                    }
                    case 'F': {
                        final char c6 = '-';
                        this.print(sb, temporalAccessor, 'Y', locale).append(c6);
                        this.print(sb, temporalAccessor, 'm', locale).append(c6);
                        this.print(sb, temporalAccessor, 'd', locale);
                        break;
                    }
                    default: {
                        assert false;
                        break;
                    }
                }
            }
            catch (DateTimeException ex) {
                throw new IllegalFormatConversionException(c, temporalAccessor.getClass());
            }
            return sb;
        }
        
        private void failMismatch(final Flags flags, final char c) {
            throw new FormatFlagsConversionMismatchException(flags.toString(), c);
        }
        
        private void failConversion(final char c, final Object o) {
            throw new IllegalFormatConversionException(c, o.getClass());
        }
        
        private char getZero(final Locale locale) {
            if (locale != null && !locale.equals(Formatter.this.locale())) {
                return DecimalFormatSymbols.getInstance(locale).getZeroDigit();
            }
            return Formatter.this.zero;
        }
        
        private StringBuilder localizedMagnitude(final StringBuilder sb, final long n, final Flags flags, final int n2, final Locale locale) {
            return this.localizedMagnitude(sb, Long.toString(n, 10).toCharArray(), flags, n2, locale);
        }
        
        private StringBuilder localizedMagnitude(StringBuilder sb, final char[] array, final Flags flags, final int n, final Locale locale) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            final int length = sb.length();
            final char zero = this.getZero(locale);
            char groupingSeparator = '\0';
            int groupingSize = -1;
            char decimalSeparator = '\0';
            int length2;
            final int n2 = length2 = array.length;
            for (int i = 0; i < n2; ++i) {
                if (array[i] == '.') {
                    length2 = i;
                    break;
                }
            }
            if (length2 < n2) {
                if (locale == null || locale.equals(Locale.US)) {
                    decimalSeparator = '.';
                }
                else {
                    decimalSeparator = DecimalFormatSymbols.getInstance(locale).getDecimalSeparator();
                }
            }
            if (flags.contains(Flags.GROUP)) {
                if (locale == null || locale.equals(Locale.US)) {
                    groupingSeparator = ',';
                    groupingSize = 3;
                }
                else {
                    groupingSeparator = DecimalFormatSymbols.getInstance(locale).getGroupingSeparator();
                    groupingSize = ((DecimalFormat)NumberFormat.getIntegerInstance(locale)).getGroupingSize();
                }
            }
            for (int j = 0; j < n2; ++j) {
                if (j == length2) {
                    sb.append(decimalSeparator);
                    groupingSeparator = '\0';
                }
                else {
                    sb.append((char)(array[j] - '0' + zero));
                    if (groupingSeparator != '\0' && j != length2 - 1 && (length2 - j) % groupingSize == 1) {
                        sb.append(groupingSeparator);
                    }
                }
            }
            final int length3 = sb.length();
            if (n != -1 && flags.contains(Flags.ZERO_PAD)) {
                for (int k = 0; k < n - length3; ++k) {
                    sb.insert(length, zero);
                }
            }
            return sb;
        }
        
        private class BigDecimalLayout
        {
            private StringBuilder mant;
            private StringBuilder exp;
            private boolean dot;
            private int scale;
            
            public BigDecimalLayout(final BigInteger bigInteger, final int n, final BigDecimalLayoutForm bigDecimalLayoutForm) {
                this.dot = false;
                this.layout(bigInteger, n, bigDecimalLayoutForm);
            }
            
            public boolean hasDot() {
                return this.dot;
            }
            
            public int scale() {
                return this.scale;
            }
            
            public char[] layoutChars() {
                final StringBuilder sb = new StringBuilder(this.mant);
                if (this.exp != null) {
                    sb.append('E');
                    sb.append((CharSequence)this.exp);
                }
                return this.toCharArray(sb);
            }
            
            public char[] mantissa() {
                return this.toCharArray(this.mant);
            }
            
            public char[] exponent() {
                return this.toCharArray(this.exp);
            }
            
            private char[] toCharArray(final StringBuilder sb) {
                if (sb == null) {
                    return null;
                }
                final char[] array = new char[sb.length()];
                sb.getChars(0, array.length, array, 0);
                return array;
            }
            
            private void layout(final BigInteger bigInteger, final int scale, final BigDecimalLayoutForm bigDecimalLayoutForm) {
                final char[] charArray = bigInteger.toString().toCharArray();
                this.scale = scale;
                this.mant = new StringBuilder(charArray.length + 14);
                if (scale == 0) {
                    final int length = charArray.length;
                    if (length > 1) {
                        this.mant.append(charArray[0]);
                        if (bigDecimalLayoutForm == BigDecimalLayoutForm.SCIENTIFIC) {
                            this.mant.append('.');
                            this.dot = true;
                            this.mant.append(charArray, 1, length - 1);
                            this.exp = new StringBuilder("+");
                            if (length < 10) {
                                this.exp.append("0").append(length - 1);
                            }
                            else {
                                this.exp.append(length - 1);
                            }
                        }
                        else {
                            this.mant.append(charArray, 1, length - 1);
                        }
                    }
                    else {
                        this.mant.append(charArray);
                        if (bigDecimalLayoutForm == BigDecimalLayoutForm.SCIENTIFIC) {
                            this.exp = new StringBuilder("+00");
                        }
                    }
                    return;
                }
                final long n = -scale + (charArray.length - 1);
                if (bigDecimalLayoutForm == BigDecimalLayoutForm.DECIMAL_FLOAT) {
                    int i = scale - charArray.length;
                    if (i >= 0) {
                        this.mant.append("0.");
                        this.dot = true;
                        while (i > 0) {
                            this.mant.append('0');
                            --i;
                        }
                        this.mant.append(charArray);
                    }
                    else if (-i < charArray.length) {
                        this.mant.append(charArray, 0, -i);
                        this.mant.append('.');
                        this.dot = true;
                        this.mant.append(charArray, -i, scale);
                    }
                    else {
                        this.mant.append(charArray, 0, charArray.length);
                        for (int j = 0; j < -scale; ++j) {
                            this.mant.append('0');
                        }
                        this.scale = 0;
                    }
                }
                else {
                    this.mant.append(charArray[0]);
                    if (charArray.length > 1) {
                        this.mant.append('.');
                        this.dot = true;
                        this.mant.append(charArray, 1, charArray.length - 1);
                    }
                    this.exp = new StringBuilder();
                    if (n != 0L) {
                        final long abs = Math.abs(n);
                        this.exp.append((char)((n < 0L) ? 45 : 43));
                        if (abs < 10L) {
                            this.exp.append('0');
                        }
                        this.exp.append(abs);
                    }
                    else {
                        this.exp.append("+00");
                    }
                }
            }
        }
    }
}
