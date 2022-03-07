package java.text;

import java.util.*;
import java.io.*;

public class MessageFormat extends Format
{
    private static final long serialVersionUID = 6479157306784022952L;
    private Locale locale;
    private String pattern;
    private static final int INITIAL_FORMATS = 10;
    private Format[] formats;
    private int[] offsets;
    private int[] argumentNumbers;
    private int maxOffset;
    private static final int SEG_RAW = 0;
    private static final int SEG_INDEX = 1;
    private static final int SEG_TYPE = 2;
    private static final int SEG_MODIFIER = 3;
    private static final int TYPE_NULL = 0;
    private static final int TYPE_NUMBER = 1;
    private static final int TYPE_DATE = 2;
    private static final int TYPE_TIME = 3;
    private static final int TYPE_CHOICE = 4;
    private static final String[] TYPE_KEYWORDS;
    private static final int MODIFIER_DEFAULT = 0;
    private static final int MODIFIER_CURRENCY = 1;
    private static final int MODIFIER_PERCENT = 2;
    private static final int MODIFIER_INTEGER = 3;
    private static final String[] NUMBER_MODIFIER_KEYWORDS;
    private static final int MODIFIER_SHORT = 1;
    private static final int MODIFIER_MEDIUM = 2;
    private static final int MODIFIER_LONG = 3;
    private static final int MODIFIER_FULL = 4;
    private static final String[] DATE_TIME_MODIFIER_KEYWORDS;
    private static final int[] DATE_TIME_MODIFIERS;
    
    public MessageFormat(final String s) {
        this.pattern = "";
        this.formats = new Format[10];
        this.offsets = new int[10];
        this.argumentNumbers = new int[10];
        this.maxOffset = -1;
        this.locale = Locale.getDefault(Locale.Category.FORMAT);
        this.applyPattern(s);
    }
    
    public MessageFormat(final String s, final Locale locale) {
        this.pattern = "";
        this.formats = new Format[10];
        this.offsets = new int[10];
        this.argumentNumbers = new int[10];
        this.maxOffset = -1;
        this.locale = locale;
        this.applyPattern(s);
    }
    
    public void setLocale(final Locale locale) {
        this.locale = locale;
    }
    
    public Locale getLocale() {
        return this.locale;
    }
    
    public void applyPattern(final String s) {
        final StringBuilder[] array = new StringBuilder[4];
        array[0] = new StringBuilder();
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        this.maxOffset = -1;
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (n == 0) {
                if (char1 == '\'') {
                    if (i + 1 < s.length() && s.charAt(i + 1) == '\'') {
                        array[n].append(char1);
                        ++i;
                    }
                    else {
                        n3 = ((n3 == 0) ? 1 : 0);
                    }
                }
                else if (char1 == '{' && n3 == 0) {
                    n = 1;
                    if (array[1] == null) {
                        array[1] = new StringBuilder();
                    }
                }
                else {
                    array[n].append(char1);
                }
            }
            else if (n3 != 0) {
                array[n].append(char1);
                if (char1 == '\'') {
                    n3 = 0;
                }
            }
            else {
                switch (char1) {
                    case ',': {
                        if (n >= 3) {
                            array[n].append(char1);
                            continue;
                        }
                        if (array[++n] == null) {
                            array[n] = new StringBuilder();
                        }
                        continue;
                    }
                    case '{': {
                        ++n4;
                        array[n].append(char1);
                        continue;
                    }
                    case '}': {
                        if (n4 == 0) {
                            n = 0;
                            this.makeFormat(i, n2, array);
                            ++n2;
                            array[1] = null;
                            array[3] = (array[2] = null);
                            continue;
                        }
                        --n4;
                        array[n].append(char1);
                        continue;
                    }
                    case ' ': {
                        if (n != 2 || array[2].length() > 0) {
                            array[n].append(char1);
                        }
                        continue;
                    }
                    case '\'': {
                        n3 = 1;
                        break;
                    }
                }
                array[n].append(char1);
            }
        }
        if (n4 == 0 && n != 0) {
            this.maxOffset = -1;
            throw new IllegalArgumentException("Unmatched braces in the pattern.");
        }
        this.pattern = array[0].toString();
    }
    
    public String toPattern() {
        int n = 0;
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= this.maxOffset; ++i) {
            copyAndFixQuotes(this.pattern, n, this.offsets[i], sb);
            n = this.offsets[i];
            sb.append('{').append(this.argumentNumbers[i]);
            final Format format = this.formats[i];
            if (format != null) {
                if (format instanceof NumberFormat) {
                    if (format.equals(NumberFormat.getInstance(this.locale))) {
                        sb.append(",number");
                    }
                    else if (format.equals(NumberFormat.getCurrencyInstance(this.locale))) {
                        sb.append(",number,currency");
                    }
                    else if (format.equals(NumberFormat.getPercentInstance(this.locale))) {
                        sb.append(",number,percent");
                    }
                    else if (format.equals(NumberFormat.getIntegerInstance(this.locale))) {
                        sb.append(",number,integer");
                    }
                    else if (format instanceof DecimalFormat) {
                        sb.append(",number,").append(((DecimalFormat)format).toPattern());
                    }
                    else if (format instanceof ChoiceFormat) {
                        sb.append(",choice,").append(((ChoiceFormat)format).toPattern());
                    }
                }
                else if (format instanceof DateFormat) {
                    int j;
                    for (j = 0; j < MessageFormat.DATE_TIME_MODIFIERS.length; ++j) {
                        if (format.equals(DateFormat.getDateInstance(MessageFormat.DATE_TIME_MODIFIERS[j], this.locale))) {
                            sb.append(",date");
                            break;
                        }
                        if (format.equals(DateFormat.getTimeInstance(MessageFormat.DATE_TIME_MODIFIERS[j], this.locale))) {
                            sb.append(",time");
                            break;
                        }
                    }
                    if (j >= MessageFormat.DATE_TIME_MODIFIERS.length) {
                        if (format instanceof SimpleDateFormat) {
                            sb.append(",date,").append(((SimpleDateFormat)format).toPattern());
                        }
                    }
                    else if (j != 0) {
                        sb.append(',').append(MessageFormat.DATE_TIME_MODIFIER_KEYWORDS[j]);
                    }
                }
            }
            sb.append('}');
        }
        copyAndFixQuotes(this.pattern, n, this.pattern.length(), sb);
        return sb.toString();
    }
    
    public void setFormatsByArgumentIndex(final Format[] array) {
        for (int i = 0; i <= this.maxOffset; ++i) {
            final int n = this.argumentNumbers[i];
            if (n < array.length) {
                this.formats[i] = array[n];
            }
        }
    }
    
    public void setFormats(final Format[] array) {
        int length = array.length;
        if (length > this.maxOffset + 1) {
            length = this.maxOffset + 1;
        }
        for (int i = 0; i < length; ++i) {
            this.formats[i] = array[i];
        }
    }
    
    public void setFormatByArgumentIndex(final int n, final Format format) {
        for (int i = 0; i <= this.maxOffset; ++i) {
            if (this.argumentNumbers[i] == n) {
                this.formats[i] = format;
            }
        }
    }
    
    public void setFormat(final int n, final Format format) {
        this.formats[n] = format;
    }
    
    public Format[] getFormatsByArgumentIndex() {
        int n = -1;
        for (int i = 0; i <= this.maxOffset; ++i) {
            if (this.argumentNumbers[i] > n) {
                n = this.argumentNumbers[i];
            }
        }
        final Format[] array = new Format[n + 1];
        for (int j = 0; j <= this.maxOffset; ++j) {
            array[this.argumentNumbers[j]] = this.formats[j];
        }
        return array;
    }
    
    public Format[] getFormats() {
        final Format[] array = new Format[this.maxOffset + 1];
        System.arraycopy(this.formats, 0, array, 0, this.maxOffset + 1);
        return array;
    }
    
    public final StringBuffer format(final Object[] array, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.subformat(array, sb, fieldPosition, null);
    }
    
    public static String format(final String s, final Object... array) {
        return new MessageFormat(s).format(array);
    }
    
    @Override
    public final StringBuffer format(final Object o, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.subformat((Object[])o, sb, fieldPosition, null);
    }
    
    @Override
    public AttributedCharacterIterator formatToCharacterIterator(final Object o) {
        final StringBuffer sb = new StringBuffer();
        final ArrayList<AttributedCharacterIterator> list = new ArrayList<AttributedCharacterIterator>();
        if (o == null) {
            throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
        }
        this.subformat((Object[])o, sb, null, list);
        if (list.size() == 0) {
            return this.createAttributedCharacterIterator("");
        }
        return this.createAttributedCharacterIterator(list.toArray(new AttributedCharacterIterator[list.size()]));
    }
    
    public Object[] parse(final String s, final ParsePosition parsePosition) {
        if (s == null) {
            return new Object[0];
        }
        int n = -1;
        for (int i = 0; i <= this.maxOffset; ++i) {
            if (this.argumentNumbers[i] > n) {
                n = this.argumentNumbers[i];
            }
        }
        final Object[] array = new Object[n + 1];
        int n2 = 0;
        int n3 = parsePosition.index;
        final ParsePosition parsePosition2 = new ParsePosition(0);
        for (int j = 0; j <= this.maxOffset; ++j) {
            final int n4 = this.offsets[j] - n2;
            if (n4 != 0 && !this.pattern.regionMatches(n2, s, n3, n4)) {
                parsePosition.errorIndex = n3;
                return null;
            }
            final int errorIndex = n3 + n4;
            n2 += n4;
            if (this.formats[j] == null) {
                final int n5 = (j != this.maxOffset) ? this.offsets[j + 1] : this.pattern.length();
                int n6;
                if (n2 >= n5) {
                    n6 = s.length();
                }
                else {
                    n6 = s.indexOf(this.pattern.substring(n2, n5), errorIndex);
                }
                if (n6 < 0) {
                    parsePosition.errorIndex = errorIndex;
                    return null;
                }
                if (!s.substring(errorIndex, n6).equals("{" + this.argumentNumbers[j] + "}")) {
                    array[this.argumentNumbers[j]] = s.substring(errorIndex, n6);
                }
                n3 = n6;
            }
            else {
                parsePosition2.index = errorIndex;
                array[this.argumentNumbers[j]] = this.formats[j].parseObject(s, parsePosition2);
                if (parsePosition2.index == errorIndex) {
                    parsePosition.errorIndex = errorIndex;
                    return null;
                }
                n3 = parsePosition2.index;
            }
        }
        final int n7 = this.pattern.length() - n2;
        if (n7 == 0 || this.pattern.regionMatches(n2, s, n3, n7)) {
            parsePosition.index = n3 + n7;
            return array;
        }
        parsePosition.errorIndex = n3;
        return null;
    }
    
    public Object[] parse(final String s) throws ParseException {
        final ParsePosition parsePosition = new ParsePosition(0);
        final Object[] parse = this.parse(s, parsePosition);
        if (parsePosition.index == 0) {
            throw new ParseException("MessageFormat parse error!", parsePosition.errorIndex);
        }
        return parse;
    }
    
    @Override
    public Object parseObject(final String s, final ParsePosition parsePosition) {
        return this.parse(s, parsePosition);
    }
    
    @Override
    public Object clone() {
        final MessageFormat messageFormat = (MessageFormat)super.clone();
        messageFormat.formats = this.formats.clone();
        for (int i = 0; i < this.formats.length; ++i) {
            if (this.formats[i] != null) {
                messageFormat.formats[i] = (Format)this.formats[i].clone();
            }
        }
        messageFormat.offsets = this.offsets.clone();
        messageFormat.argumentNumbers = this.argumentNumbers.clone();
        return messageFormat;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final MessageFormat messageFormat = (MessageFormat)o;
        return this.maxOffset == messageFormat.maxOffset && this.pattern.equals(messageFormat.pattern) && ((this.locale != null && this.locale.equals(messageFormat.locale)) || (this.locale == null && messageFormat.locale == null)) && Arrays.equals(this.offsets, messageFormat.offsets) && Arrays.equals(this.argumentNumbers, messageFormat.argumentNumbers) && Arrays.equals(this.formats, messageFormat.formats);
    }
    
    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }
    
    private StringBuffer subformat(final Object[] array, final StringBuffer sb, final FieldPosition fieldPosition, final List<AttributedCharacterIterator> list) {
        int n = 0;
        int n2 = sb.length();
        for (int i = 0; i <= this.maxOffset; ++i) {
            sb.append(this.pattern.substring(n, this.offsets[i]));
            n = this.offsets[i];
            final int n3 = this.argumentNumbers[i];
            if (array == null || n3 >= array.length) {
                sb.append('{').append(n3).append('}');
            }
            else {
                Object o = array[n3];
                String s = null;
                Format format = null;
                if (o == null) {
                    s = "null";
                }
                else if (this.formats[i] != null) {
                    format = this.formats[i];
                    if (format instanceof ChoiceFormat) {
                        s = this.formats[i].format(o);
                        if (s.indexOf(123) >= 0) {
                            format = new MessageFormat(s, this.locale);
                            o = array;
                            s = null;
                        }
                    }
                }
                else if (o instanceof Number) {
                    format = NumberFormat.getInstance(this.locale);
                }
                else if (o instanceof Date) {
                    format = DateFormat.getDateTimeInstance(3, 3, this.locale);
                }
                else if (o instanceof String) {
                    s = (String)o;
                }
                else {
                    s = o.toString();
                    if (s == null) {
                        s = "null";
                    }
                }
                if (list != null) {
                    if (n2 != sb.length()) {
                        list.add(this.createAttributedCharacterIterator(sb.substring(n2)));
                        n2 = sb.length();
                    }
                    if (format != null) {
                        final AttributedCharacterIterator formatToCharacterIterator = format.formatToCharacterIterator(o);
                        this.append(sb, formatToCharacterIterator);
                        if (n2 != sb.length()) {
                            list.add(this.createAttributedCharacterIterator(formatToCharacterIterator, Field.ARGUMENT, n3));
                            n2 = sb.length();
                        }
                        s = null;
                    }
                    if (s != null && s.length() > 0) {
                        sb.append(s);
                        list.add(this.createAttributedCharacterIterator(s, Field.ARGUMENT, n3));
                        n2 = sb.length();
                    }
                }
                else {
                    if (format != null) {
                        s = format.format(o);
                    }
                    final int length = sb.length();
                    sb.append(s);
                    if (i == 0 && fieldPosition != null && Field.ARGUMENT.equals(fieldPosition.getFieldAttribute())) {
                        fieldPosition.setBeginIndex(length);
                        fieldPosition.setEndIndex(sb.length());
                    }
                    n2 = sb.length();
                }
            }
        }
        sb.append(this.pattern.substring(n, this.pattern.length()));
        if (list != null && n2 != sb.length()) {
            list.add(this.createAttributedCharacterIterator(sb.substring(n2)));
        }
        return sb;
    }
    
    private void append(final StringBuffer sb, final CharacterIterator characterIterator) {
        if (characterIterator.first() != '\uffff') {
            sb.append(characterIterator.first());
            char next;
            while ((next = characterIterator.next()) != '\uffff') {
                sb.append(next);
            }
        }
    }
    
    private void makeFormat(final int n, final int maxOffset, final StringBuilder[] array) {
        final String[] array2 = new String[array.length];
        for (int i = 0; i < array.length; ++i) {
            final StringBuilder sb = array[i];
            array2[i] = ((sb != null) ? sb.toString() : "");
        }
        int int1;
        try {
            int1 = Integer.parseInt(array2[1]);
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException("can't parse argument number: " + array2[1], ex);
        }
        if (int1 < 0) {
            throw new IllegalArgumentException("negative argument number: " + int1);
        }
        if (maxOffset >= this.formats.length) {
            final int n2 = this.formats.length * 2;
            final Format[] formats = new Format[n2];
            final int[] offsets = new int[n2];
            final int[] argumentNumbers = new int[n2];
            System.arraycopy(this.formats, 0, formats, 0, this.maxOffset + 1);
            System.arraycopy(this.offsets, 0, offsets, 0, this.maxOffset + 1);
            System.arraycopy(this.argumentNumbers, 0, argumentNumbers, 0, this.maxOffset + 1);
            this.formats = formats;
            this.offsets = offsets;
            this.argumentNumbers = argumentNumbers;
        }
        final int maxOffset2 = this.maxOffset;
        this.maxOffset = maxOffset;
        this.offsets[maxOffset] = array2[0].length();
        this.argumentNumbers[maxOffset] = int1;
        Format format = null;
        Label_0645: {
            if (array2[2].length() != 0) {
                final int keyword = findKeyword(array2[2], MessageFormat.TYPE_KEYWORDS);
                Block_12: {
                    switch (keyword) {
                        case 0: {
                            break Label_0645;
                        }
                        case 1: {
                            switch (findKeyword(array2[3], MessageFormat.NUMBER_MODIFIER_KEYWORDS)) {
                                case 0: {
                                    format = NumberFormat.getInstance(this.locale);
                                    break Label_0645;
                                }
                                case 1: {
                                    format = NumberFormat.getCurrencyInstance(this.locale);
                                    break Label_0645;
                                }
                                case 2: {
                                    format = NumberFormat.getPercentInstance(this.locale);
                                    break Label_0645;
                                }
                                case 3: {
                                    format = NumberFormat.getIntegerInstance(this.locale);
                                    break Label_0645;
                                }
                                default: {
                                    try {
                                        format = new DecimalFormat(array2[3], DecimalFormatSymbols.getInstance(this.locale));
                                        break Label_0645;
                                    }
                                    catch (IllegalArgumentException ex2) {
                                        this.maxOffset = maxOffset2;
                                        throw ex2;
                                    }
                                    break Block_12;
                                }
                            }
                            break;
                        }
                        case 2:
                        case 3: {
                            final int keyword2 = findKeyword(array2[3], MessageFormat.DATE_TIME_MODIFIER_KEYWORDS);
                            if (keyword2 < 0 || keyword2 >= MessageFormat.DATE_TIME_MODIFIER_KEYWORDS.length) {
                                try {
                                    format = new SimpleDateFormat(array2[3], this.locale);
                                    break Label_0645;
                                }
                                catch (IllegalArgumentException ex3) {
                                    this.maxOffset = maxOffset2;
                                    throw ex3;
                                }
                                break Block_12;
                            }
                            if (keyword == 2) {
                                format = DateFormat.getDateInstance(MessageFormat.DATE_TIME_MODIFIERS[keyword2], this.locale);
                                break Label_0645;
                            }
                            format = DateFormat.getTimeInstance(MessageFormat.DATE_TIME_MODIFIERS[keyword2], this.locale);
                            break Label_0645;
                        }
                        case 4: {
                            try {
                                format = new ChoiceFormat(array2[3]);
                                break Label_0645;
                            }
                            catch (Exception ex4) {
                                this.maxOffset = maxOffset2;
                                throw new IllegalArgumentException("Choice Pattern incorrect: " + array2[3], ex4);
                            }
                            break;
                        }
                    }
                }
                this.maxOffset = maxOffset2;
                throw new IllegalArgumentException("unknown format type: " + array2[2]);
            }
        }
        this.formats[maxOffset] = format;
    }
    
    private static final int findKeyword(final String s, final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (s.equals(array[i])) {
                return i;
            }
        }
        final String lowerCase = s.trim().toLowerCase(Locale.ROOT);
        if (lowerCase != s) {
            for (int j = 0; j < array.length; ++j) {
                if (lowerCase.equals(array[j])) {
                    return j;
                }
            }
        }
        return -1;
    }
    
    private static final void copyAndFixQuotes(final String s, final int n, final int n2, final StringBuilder sb) {
        int n3 = 0;
        for (int i = n; i < n2; ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '{') {
                if (n3 == 0) {
                    sb.append('\'');
                    n3 = 1;
                }
                sb.append(char1);
            }
            else if (char1 == '\'') {
                sb.append("''");
            }
            else {
                if (n3 != 0) {
                    sb.append('\'');
                    n3 = 0;
                }
                sb.append(char1);
            }
        }
        if (n3 != 0) {
            sb.append('\'');
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int n = (this.maxOffset >= -1 && this.formats.length > this.maxOffset && this.offsets.length > this.maxOffset && this.argumentNumbers.length > this.maxOffset) ? 1 : 0;
        if (n != 0) {
            int n2 = this.pattern.length() + 1;
            for (int i = this.maxOffset; i >= 0; --i) {
                if (this.offsets[i] < 0 || this.offsets[i] > n2) {
                    n = 0;
                    break;
                }
                n2 = this.offsets[i];
            }
        }
        if (n == 0) {
            throw new InvalidObjectException("Could not reconstruct MessageFormat from corrupt stream.");
        }
    }
    
    static {
        TYPE_KEYWORDS = new String[] { "", "number", "date", "time", "choice" };
        NUMBER_MODIFIER_KEYWORDS = new String[] { "", "currency", "percent", "integer" };
        DATE_TIME_MODIFIER_KEYWORDS = new String[] { "", "short", "medium", "long", "full" };
        DATE_TIME_MODIFIERS = new int[] { 2, 3, 2, 1, 0 };
    }
    
    public static class Field extends Format.Field
    {
        private static final long serialVersionUID = 7899943957617360810L;
        public static final Field ARGUMENT;
        
        protected Field(final String s) {
            super(s);
        }
        
        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != Field.class) {
                throw new InvalidObjectException("subclass didn't correctly implement readResolve");
            }
            return Field.ARGUMENT;
        }
        
        static {
            ARGUMENT = new Field("message argument field");
        }
    }
}
