package java.lang;

import java.nio.charset.*;
import java.util.regex.*;
import java.util.*;
import java.io.*;

public final class String implements Serializable, Comparable<String>, CharSequence
{
    private final char[] value;
    private int hash;
    private static final long serialVersionUID = -6849794470754667710L;
    private static final ObjectStreamField[] serialPersistentFields;
    public static final Comparator<String> CASE_INSENSITIVE_ORDER;
    
    public String() {
        this.value = "".value;
    }
    
    public String(final String s) {
        this.value = s.value;
        this.hash = s.hash;
    }
    
    public String(final char[] array) {
        this.value = Arrays.copyOf(array, array.length);
    }
    
    public String(final char[] array, final int n, final int n2) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 <= 0) {
            if (n2 < 0) {
                throw new StringIndexOutOfBoundsException(n2);
            }
            if (n <= array.length) {
                this.value = "".value;
                return;
            }
        }
        if (n > array.length - n2) {
            throw new StringIndexOutOfBoundsException(n + n2);
        }
        this.value = Arrays.copyOfRange(array, n, n + n2);
    }
    
    public String(final int[] array, final int n, final int n2) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 <= 0) {
            if (n2 < 0) {
                throw new StringIndexOutOfBoundsException(n2);
            }
            if (n <= array.length) {
                this.value = "".value;
                return;
            }
        }
        if (n > array.length - n2) {
            throw new StringIndexOutOfBoundsException(n + n2);
        }
        final int n3 = n + n2;
        int n4 = n2;
        for (int i = n; i < n3; ++i) {
            final int n5 = array[i];
            if (!Character.isBmpCodePoint(n5)) {
                if (!Character.isValidCodePoint(n5)) {
                    throw new IllegalArgumentException(Integer.toString(n5));
                }
                ++n4;
            }
        }
        final char[] value = new char[n4];
        for (int j = n, n6 = 0; j < n3; ++j, ++n6) {
            final int n7 = array[j];
            if (Character.isBmpCodePoint(n7)) {
                value[n6] = (char)n7;
            }
            else {
                Character.toSurrogates(n7, value, n6++);
            }
        }
        this.value = value;
    }
    
    public String(final byte[] array, int n, final int n2, final int n3) {
        checkBounds(array, n2, n3);
        final char[] value = new char[n3];
        if (n == 0) {
            int n4 = n3;
            while (n4-- > 0) {
                value[n4] = (char)(array[n4 + n2] & 0xFF);
            }
        }
        else {
            n <<= 8;
            int n5 = n3;
            while (n5-- > 0) {
                value[n5] = (char)(n | (array[n5 + n2] & 0xFF));
            }
        }
        this.value = value;
    }
    
    public String(final byte[] array, final int n) {
        this(array, n, 0, array.length);
    }
    
    private static void checkBounds(final byte[] array, final int n, final int n2) {
        if (n2 < 0) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n > array.length - n2) {
            throw new StringIndexOutOfBoundsException(n + n2);
        }
    }
    
    public String(final byte[] array, final int n, final int n2, final String s) throws UnsupportedEncodingException {
        if (s == null) {
            throw new NullPointerException("charsetName");
        }
        checkBounds(array, n, n2);
        this.value = StringCoding.decode(s, array, n, n2);
    }
    
    public String(final byte[] array, final int n, final int n2, final Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        checkBounds(array, n, n2);
        this.value = StringCoding.decode(charset, array, n, n2);
    }
    
    public String(final byte[] array, final String s) throws UnsupportedEncodingException {
        this(array, 0, array.length, s);
    }
    
    public String(final byte[] array, final Charset charset) {
        this(array, 0, array.length, charset);
    }
    
    public String(final byte[] array, final int n, final int n2) {
        checkBounds(array, n, n2);
        this.value = StringCoding.decode(array, n, n2);
    }
    
    public String(final byte[] array) {
        this(array, 0, array.length);
    }
    
    public String(final StringBuffer sb) {
        synchronized (sb) {
            this.value = Arrays.copyOf(sb.getValue(), sb.length());
        }
    }
    
    public String(final StringBuilder sb) {
        this.value = Arrays.copyOf(sb.getValue(), sb.length());
    }
    
    String(final char[] value, final boolean b) {
        this.value = value;
    }
    
    @Override
    public int length() {
        return this.value.length;
    }
    
    public boolean isEmpty() {
        return this.value.length == 0;
    }
    
    @Override
    public char charAt(final int n) {
        if (n < 0 || n >= this.value.length) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return this.value[n];
    }
    
    public int codePointAt(final int n) {
        if (n < 0 || n >= this.value.length) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return Character.codePointAtImpl(this.value, n, this.value.length);
    }
    
    public int codePointBefore(final int n) {
        final int n2 = n - 1;
        if (n2 < 0 || n2 >= this.value.length) {
            throw new StringIndexOutOfBoundsException(n);
        }
        return Character.codePointBeforeImpl(this.value, n, 0);
    }
    
    public int codePointCount(final int n, final int n2) {
        if (n < 0 || n2 > this.value.length || n > n2) {
            throw new IndexOutOfBoundsException();
        }
        return Character.codePointCountImpl(this.value, n, n2 - n);
    }
    
    public int offsetByCodePoints(final int n, final int n2) {
        if (n < 0 || n > this.value.length) {
            throw new IndexOutOfBoundsException();
        }
        return Character.offsetByCodePointsImpl(this.value, 0, this.value.length, n, n2);
    }
    
    void getChars(final char[] array, final int n) {
        System.arraycopy(this.value, 0, array, n, this.value.length);
    }
    
    public void getChars(final int n, final int n2, final char[] array, final int n3) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 > this.value.length) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        if (n > n2) {
            throw new StringIndexOutOfBoundsException(n2 - n);
        }
        System.arraycopy(this.value, n, array, n3, n2 - n);
    }
    
    @Deprecated
    public void getBytes(final int n, final int n2, final byte[] array, final int n3) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 > this.value.length) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        if (n > n2) {
            throw new StringIndexOutOfBoundsException(n2 - n);
        }
        Objects.requireNonNull(array);
        int n4 = n3;
        int i = n;
        for (char[] value = this.value; i < n2; array[n4++] = (byte)value[i++]) {}
    }
    
    public byte[] getBytes(final String s) throws UnsupportedEncodingException {
        if (s == null) {
            throw new NullPointerException();
        }
        return StringCoding.encode(s, this.value, 0, this.value.length);
    }
    
    public byte[] getBytes(final Charset charset) {
        if (charset == null) {
            throw new NullPointerException();
        }
        return StringCoding.encode(charset, this.value, 0, this.value.length);
    }
    
    public byte[] getBytes() {
        return StringCoding.encode(this.value, 0, this.value.length);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof String) {
            final String s = (String)o;
            int length = this.value.length;
            if (length == s.value.length) {
                final char[] value = this.value;
                final char[] value2 = s.value;
                int n = 0;
                while (length-- != 0) {
                    if (value[n] != value2[n]) {
                        return false;
                    }
                    ++n;
                }
                return true;
            }
        }
        return false;
    }
    
    public boolean contentEquals(final StringBuffer sb) {
        return this.contentEquals((CharSequence)sb);
    }
    
    private boolean nonSyncContentEquals(final AbstractStringBuilder abstractStringBuilder) {
        final char[] value = this.value;
        final char[] value2 = abstractStringBuilder.getValue();
        final int length = value.length;
        if (length != abstractStringBuilder.length()) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (value[i] != value2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public boolean contentEquals(final CharSequence charSequence) {
        if (charSequence instanceof AbstractStringBuilder) {
            if (charSequence instanceof StringBuffer) {
                synchronized (charSequence) {
                    return this.nonSyncContentEquals((AbstractStringBuilder)charSequence);
                }
            }
            return this.nonSyncContentEquals((AbstractStringBuilder)charSequence);
        }
        if (charSequence instanceof String) {
            return this.equals(charSequence);
        }
        final char[] value = this.value;
        final int length = value.length;
        if (length != charSequence.length()) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (value[i] != charSequence.charAt(i)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean equalsIgnoreCase(final String s) {
        return this == s || (s != null && s.value.length == this.value.length && this.regionMatches(true, 0, s, 0, this.value.length));
    }
    
    @Override
    public int compareTo(final String s) {
        final int length = this.value.length;
        final int length2 = s.value.length;
        final int min = Math.min(length, length2);
        final char[] value = this.value;
        final char[] value2 = s.value;
        for (int i = 0; i < min; ++i) {
            final char c = value[i];
            final char c2 = value2[i];
            if (c != c2) {
                return c - c2;
            }
        }
        return length - length2;
    }
    
    public int compareToIgnoreCase(final String s) {
        return String.CASE_INSENSITIVE_ORDER.compare(this, s);
    }
    
    public boolean regionMatches(final int n, final String s, final int n2, int n3) {
        final char[] value = this.value;
        int n4 = n;
        final char[] value2 = s.value;
        int n5 = n2;
        if (n2 < 0 || n < 0 || n > this.value.length - n3 || n2 > s.value.length - n3) {
            return false;
        }
        while (n3-- > 0) {
            if (value[n4++] != value2[n5++]) {
                return false;
            }
        }
        return true;
    }
    
    public boolean regionMatches(final boolean b, final int n, final String s, final int n2, int n3) {
        final char[] value = this.value;
        int n4 = n;
        final char[] value2 = s.value;
        int n5 = n2;
        if (n2 < 0 || n < 0 || n > this.value.length - n3 || n2 > s.value.length - n3) {
            return false;
        }
        while (n3-- > 0) {
            final char c = value[n4++];
            final char c2 = value2[n5++];
            if (c == c2) {
                continue;
            }
            if (b) {
                final char upperCase = Character.toUpperCase(c);
                final char upperCase2 = Character.toUpperCase(c2);
                if (upperCase == upperCase2) {
                    continue;
                }
                if (Character.toLowerCase(upperCase) == Character.toLowerCase(upperCase2)) {
                    continue;
                }
            }
            return false;
        }
        return true;
    }
    
    public boolean startsWith(final String s, final int n) {
        final char[] value = this.value;
        int n2 = n;
        final char[] value2 = s.value;
        int n3 = 0;
        int length = s.value.length;
        if (n < 0 || n > this.value.length - length) {
            return false;
        }
        while (--length >= 0) {
            if (value[n2++] != value2[n3++]) {
                return false;
            }
        }
        return true;
    }
    
    public boolean startsWith(final String s) {
        return this.startsWith(s, 0);
    }
    
    public boolean endsWith(final String s) {
        return this.startsWith(s, this.value.length - s.value.length);
    }
    
    @Override
    public int hashCode() {
        int hash = this.hash;
        if (hash == 0 && this.value.length > 0) {
            final char[] value = this.value;
            for (int i = 0; i < this.value.length; ++i) {
                hash = 31 * hash + value[i];
            }
            this.hash = hash;
        }
        return hash;
    }
    
    public int indexOf(final int n) {
        return this.indexOf(n, 0);
    }
    
    public int indexOf(final int n, int n2) {
        final int length = this.value.length;
        if (n2 < 0) {
            n2 = 0;
        }
        else if (n2 >= length) {
            return -1;
        }
        if (n < 65536) {
            final char[] value = this.value;
            for (int i = n2; i < length; ++i) {
                if (value[i] == n) {
                    return i;
                }
            }
            return -1;
        }
        return this.indexOfSupplementary(n, n2);
    }
    
    private int indexOfSupplementary(final int n, final int n2) {
        if (Character.isValidCodePoint(n)) {
            final char[] value = this.value;
            final char highSurrogate = Character.highSurrogate(n);
            final char lowSurrogate = Character.lowSurrogate(n);
            for (int n3 = value.length - 1, i = n2; i < n3; ++i) {
                if (value[i] == highSurrogate && value[i + 1] == lowSurrogate) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int lastIndexOf(final int n) {
        return this.lastIndexOf(n, this.value.length - 1);
    }
    
    public int lastIndexOf(final int n, final int n2) {
        if (n < 65536) {
            final char[] value = this.value;
            for (int i = Math.min(n2, value.length - 1); i >= 0; --i) {
                if (value[i] == n) {
                    return i;
                }
            }
            return -1;
        }
        return this.lastIndexOfSupplementary(n, n2);
    }
    
    private int lastIndexOfSupplementary(final int n, final int n2) {
        if (Character.isValidCodePoint(n)) {
            final char[] value = this.value;
            final char highSurrogate = Character.highSurrogate(n);
            final char lowSurrogate = Character.lowSurrogate(n);
            for (int i = Math.min(n2, value.length - 2); i >= 0; --i) {
                if (value[i] == highSurrogate && value[i + 1] == lowSurrogate) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int indexOf(final String s) {
        return this.indexOf(s, 0);
    }
    
    public int indexOf(final String s, final int n) {
        return indexOf(this.value, 0, this.value.length, s.value, 0, s.value.length, n);
    }
    
    static int indexOf(final char[] array, final int n, final int n2, final String s, final int n3) {
        return indexOf(array, n, n2, s.value, 0, s.value.length, n3);
    }
    
    static int indexOf(final char[] array, final int n, final int n2, final char[] array2, final int n3, final int n4, int n5) {
        if (n5 >= n2) {
            return (n4 == 0) ? n2 : -1;
        }
        if (n5 < 0) {
            n5 = 0;
        }
        if (n4 == 0) {
            return n5;
        }
        final char c = array2[n3];
        for (int n6 = n + (n2 - n4), i = n + n5; i <= n6; ++i) {
            if (array[i] != c) {
                while (++i <= n6 && array[i] != c) {}
            }
            if (i <= n6) {
                int n7 = i + 1;
                final int n8 = n7 + n4 - 1;
                for (int n9 = n3 + 1; n7 < n8 && array[n7] == array2[n9]; ++n7, ++n9) {}
                if (n7 == n8) {
                    return i - n;
                }
            }
        }
        return -1;
    }
    
    public int lastIndexOf(final String s) {
        return this.lastIndexOf(s, this.value.length);
    }
    
    public int lastIndexOf(final String s, final int n) {
        return lastIndexOf(this.value, 0, this.value.length, s.value, 0, s.value.length, n);
    }
    
    static int lastIndexOf(final char[] array, final int n, final int n2, final String s, final int n3) {
        return lastIndexOf(array, n, n2, s.value, 0, s.value.length, n3);
    }
    
    static int lastIndexOf(final char[] array, final int n, final int n2, final char[] array2, final int n3, final int n4, int n5) {
        final int n6 = n2 - n4;
        if (n5 < 0) {
            return -1;
        }
        if (n5 > n6) {
            n5 = n6;
        }
        if (n4 == 0) {
            return n5;
        }
        final int n7 = n3 + n4 - 1;
        final char c = array2[n7];
        final int n8 = n + n4 - 1;
        int n9 = n8 + n5;
    Label_0062:
        while (true) {
            if (n9 >= n8 && array[n9] != c) {
                --n9;
            }
            else {
                if (n9 < n8) {
                    return -1;
                }
                int i = n9 - 1;
                final int n10 = i - (n4 - 1);
                int n11 = n7 - 1;
                while (i > n10) {
                    if (array[i--] != array2[n11--]) {
                        --n9;
                        continue Label_0062;
                    }
                }
                return n10 - n + 1;
            }
        }
    }
    
    public String substring(final int n) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        final int n2 = this.value.length - n;
        if (n2 < 0) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        return (n == 0) ? this : new String(this.value, n, n2);
    }
    
    public String substring(final int n, final int n2) {
        if (n < 0) {
            throw new StringIndexOutOfBoundsException(n);
        }
        if (n2 > this.value.length) {
            throw new StringIndexOutOfBoundsException(n2);
        }
        final int n3 = n2 - n;
        if (n3 < 0) {
            throw new StringIndexOutOfBoundsException(n3);
        }
        return (n == 0 && n2 == this.value.length) ? this : new String(this.value, n, n3);
    }
    
    @Override
    public CharSequence subSequence(final int n, final int n2) {
        return this.substring(n, n2);
    }
    
    public String concat(final String s) {
        final int length = s.length();
        if (length == 0) {
            return this;
        }
        final int length2 = this.value.length;
        final char[] copy = Arrays.copyOf(this.value, length2 + length);
        s.getChars(copy, length2);
        return new String(copy, true);
    }
    
    public String replace(final char c, final char c2) {
        if (c != c2) {
            final int length = this.value.length;
            int i = -1;
            final char[] value = this.value;
            while (++i < length && value[i] != c) {}
            if (i < length) {
                final char[] array = new char[length];
                for (int j = 0; j < i; ++j) {
                    array[j] = value[j];
                }
                while (i < length) {
                    final char c3 = value[i];
                    array[i] = ((c3 == c) ? c2 : c3);
                    ++i;
                }
                return new String(array, true);
            }
        }
        return this;
    }
    
    public boolean matches(final String s) {
        return Pattern.matches(s, this);
    }
    
    public boolean contains(final CharSequence charSequence) {
        return this.indexOf(charSequence.toString()) > -1;
    }
    
    public String replaceFirst(final String s, final String s2) {
        return Pattern.compile(s).matcher(this).replaceFirst(s2);
    }
    
    public String replaceAll(final String s, final String s2) {
        return Pattern.compile(s).matcher(this).replaceAll(s2);
    }
    
    public String replace(final CharSequence charSequence, final CharSequence charSequence2) {
        return Pattern.compile(charSequence.toString(), 16).matcher(this).replaceAll(Matcher.quoteReplacement(charSequence2.toString()));
    }
    
    public String[] split(final String s, final int n) {
        char c;
        if (((s.value.length != 1 || ".$|()[{^?*+\\".indexOf(c = s.charAt(0)) != -1) && (s.length() != 2 || s.charAt(0) != '\\' || ((c = s.charAt(1)) - '0' | '9' - c) >= '\0' || (c - 'a' | 'z' - c) >= '\0' || (c - 'A' | 'Z' - c) >= '\0')) || (c >= '\ud800' && c <= '\udfff')) {
            return Pattern.compile(s).split(this, n);
        }
        int length = 0;
        final boolean b = n > 0;
        final ArrayList<String> list = new ArrayList<String>();
        int index;
        while ((index = this.indexOf(c, length)) != -1) {
            if (b && list.size() >= n - 1) {
                list.add(this.substring(length, this.value.length));
                length = this.value.length;
                break;
            }
            list.add(this.substring(length, index));
            length = index + 1;
        }
        if (length == 0) {
            return new String[] { this };
        }
        if (!b || list.size() < n) {
            list.add(this.substring(length, this.value.length));
        }
        int size = list.size();
        if (n == 0) {
            while (size > 0 && list.get(size - 1).length() == 0) {
                --size;
            }
        }
        return list.subList(0, size).toArray(new String[size]);
    }
    
    public String[] split(final String s) {
        return this.split(s, 0);
    }
    
    public static String join(final CharSequence charSequence, final CharSequence... array) {
        Objects.requireNonNull(charSequence);
        Objects.requireNonNull(array);
        final StringJoiner stringJoiner = new StringJoiner(charSequence);
        for (int length = array.length, i = 0; i < length; ++i) {
            stringJoiner.add(array[i]);
        }
        return stringJoiner.toString();
    }
    
    public static String join(final CharSequence charSequence, final Iterable<? extends CharSequence> iterable) {
        Objects.requireNonNull(charSequence);
        Objects.requireNonNull(iterable);
        final StringJoiner stringJoiner = new StringJoiner(charSequence);
        final Iterator<? extends CharSequence> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            stringJoiner.add((CharSequence)iterator.next());
        }
        return stringJoiner.toString();
    }
    
    public String toLowerCase(final Locale locale) {
        if (locale == null) {
            throw new NullPointerException();
        }
        final int length = this.value.length;
        int i = 0;
        while (i < length) {
            final char c = this.value[i];
            Label_0099: {
                if (c >= '\ud800' && c <= '\udbff') {
                    final int codePoint = this.codePointAt(i);
                    if (codePoint != Character.toLowerCase(codePoint)) {
                        break Label_0099;
                    }
                    i += Character.charCount(codePoint);
                }
                else {
                    if (c != Character.toLowerCase(c)) {
                        break Label_0099;
                    }
                    ++i;
                }
                continue;
            }
            char[] array = new char[length];
            int n = 0;
            System.arraycopy(this.value, 0, array, 0, i);
            final String language = locale.getLanguage();
            final boolean b = language == "tr" || language == "az" || language == "lt";
            int charCount;
            for (int j = i; j < length; j += charCount) {
                int codePoint2 = this.value[j];
                if ((char)codePoint2 >= '\ud800' && (char)codePoint2 <= '\udbff') {
                    codePoint2 = this.codePointAt(j);
                    charCount = Character.charCount(codePoint2);
                }
                else {
                    charCount = 1;
                }
                int n2;
                if (b || codePoint2 == 931 || codePoint2 == 304) {
                    n2 = ConditionalSpecialCasing.toLowerCaseEx(this, j, locale);
                }
                else {
                    n2 = Character.toLowerCase(codePoint2);
                }
                if (n2 == -1 || n2 >= 65536) {
                    char[] array2;
                    if (n2 == -1) {
                        array2 = ConditionalSpecialCasing.toLowerCaseCharArray(this, j, locale);
                    }
                    else {
                        if (charCount == 2) {
                            n += Character.toChars(n2, array, j + n) - charCount;
                            continue;
                        }
                        array2 = Character.toChars(n2);
                    }
                    final int length2 = array2.length;
                    if (length2 > charCount) {
                        final char[] array3 = new char[array.length + length2 - charCount];
                        System.arraycopy(array, 0, array3, 0, j + n);
                        array = array3;
                    }
                    for (int k = 0; k < length2; ++k) {
                        array[j + n + k] = array2[k];
                    }
                    n += length2 - charCount;
                }
                else {
                    array[j + n] = (char)n2;
                }
            }
            return new String(array, 0, length + n);
        }
        return this;
    }
    
    public String toLowerCase() {
        return this.toLowerCase(Locale.getDefault());
    }
    
    public String toUpperCase(final Locale locale) {
        if (locale == null) {
            throw new NullPointerException();
        }
        int charCount;
        for (int length = this.value.length, i = 0; i < length; i += charCount) {
            int codePoint = this.value[i];
            if (codePoint >= 55296 && codePoint <= 56319) {
                codePoint = this.codePointAt(i);
                charCount = Character.charCount(codePoint);
            }
            else {
                charCount = 1;
            }
            final int upperCaseEx = Character.toUpperCaseEx(codePoint);
            if (upperCaseEx == -1 || codePoint != upperCaseEx) {
                int n = 0;
                char[] array = new char[length];
                System.arraycopy(this.value, 0, array, 0, i);
                final String language = locale.getLanguage();
                final boolean b = language == "tr" || language == "az" || language == "lt";
                int charCount2;
                for (int j = i; j < length; j += charCount2) {
                    int codePoint2 = this.value[j];
                    if ((char)codePoint2 >= '\ud800' && (char)codePoint2 <= '\udbff') {
                        codePoint2 = this.codePointAt(j);
                        charCount2 = Character.charCount(codePoint2);
                    }
                    else {
                        charCount2 = 1;
                    }
                    int n2;
                    if (b) {
                        n2 = ConditionalSpecialCasing.toUpperCaseEx(this, j, locale);
                    }
                    else {
                        n2 = Character.toUpperCaseEx(codePoint2);
                    }
                    if (n2 == -1 || n2 >= 65536) {
                        char[] array2;
                        if (n2 == -1) {
                            if (b) {
                                array2 = ConditionalSpecialCasing.toUpperCaseCharArray(this, j, locale);
                            }
                            else {
                                array2 = Character.toUpperCaseCharArray(codePoint2);
                            }
                        }
                        else {
                            if (charCount2 == 2) {
                                n += Character.toChars(n2, array, j + n) - charCount2;
                                continue;
                            }
                            array2 = Character.toChars(n2);
                        }
                        final int length2 = array2.length;
                        if (length2 > charCount2) {
                            final char[] array3 = new char[array.length + length2 - charCount2];
                            System.arraycopy(array, 0, array3, 0, j + n);
                            array = array3;
                        }
                        for (int k = 0; k < length2; ++k) {
                            array[j + n + k] = array2[k];
                        }
                        n += length2 - charCount2;
                    }
                    else {
                        array[j + n] = (char)n2;
                    }
                }
                return new String(array, 0, length + n);
            }
        }
        return this;
    }
    
    public String toUpperCase() {
        return this.toUpperCase(Locale.getDefault());
    }
    
    public String trim() {
        int length;
        int n;
        char[] value;
        for (length = this.value.length, n = 0, value = this.value; n < length && value[n] <= ' '; ++n) {}
        while (n < length && value[length - 1] <= ' ') {
            --length;
        }
        return (n > 0 || length < this.value.length) ? this.substring(n, length) : this;
    }
    
    @Override
    public String toString() {
        return this;
    }
    
    public char[] toCharArray() {
        final char[] array = new char[this.value.length];
        System.arraycopy(this.value, 0, array, 0, this.value.length);
        return array;
    }
    
    public static String format(final String s, final Object... array) {
        return new Formatter().format(s, array).toString();
    }
    
    public static String format(final Locale locale, final String s, final Object... array) {
        return new Formatter(locale).format(s, array).toString();
    }
    
    public static String valueOf(final Object o) {
        return (o == null) ? "null" : o.toString();
    }
    
    public static String valueOf(final char[] array) {
        return new String(array);
    }
    
    public static String valueOf(final char[] array, final int n, final int n2) {
        return new String(array, n, n2);
    }
    
    public static String copyValueOf(final char[] array, final int n, final int n2) {
        return new String(array, n, n2);
    }
    
    public static String copyValueOf(final char[] array) {
        return new String(array);
    }
    
    public static String valueOf(final boolean b) {
        return b ? "true" : "false";
    }
    
    public static String valueOf(final char c) {
        return new String(new char[] { c }, true);
    }
    
    public static String valueOf(final int n) {
        return Integer.toString(n);
    }
    
    public static String valueOf(final long n) {
        return Long.toString(n);
    }
    
    public static String valueOf(final float n) {
        return Float.toString(n);
    }
    
    public static String valueOf(final double n) {
        return Double.toString(n);
    }
    
    public native String intern();
    
    static {
        serialPersistentFields = new ObjectStreamField[0];
        CASE_INSENSITIVE_ORDER = new CaseInsensitiveComparator();
    }
    
    private static class CaseInsensitiveComparator implements Comparator<String>, Serializable
    {
        private static final long serialVersionUID = 8575799808933029326L;
        
        @Override
        public int compare(final String s, final String s2) {
            final int length = s.length();
            final int length2 = s2.length();
            for (int min = Math.min(length, length2), i = 0; i < min; ++i) {
                final char char1 = s.charAt(i);
                final char char2 = s2.charAt(i);
                if (char1 != char2) {
                    final char upperCase = Character.toUpperCase(char1);
                    final char upperCase2 = Character.toUpperCase(char2);
                    if (upperCase != upperCase2) {
                        final char lowerCase = Character.toLowerCase(upperCase);
                        final char lowerCase2 = Character.toLowerCase(upperCase2);
                        if (lowerCase != lowerCase2) {
                            return lowerCase - lowerCase2;
                        }
                    }
                }
            }
            return length - length2;
        }
        
        private Object readResolve() {
            return String.CASE_INSENSITIVE_ORDER;
        }
    }
}
