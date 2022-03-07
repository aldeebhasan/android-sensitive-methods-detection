package java.lang;

import java.io.*;
import java.util.*;

public final class Character implements Serializable, Comparable<Character>
{
    public static final int MIN_RADIX = 2;
    public static final int MAX_RADIX = 36;
    public static final char MIN_VALUE = '\0';
    public static final char MAX_VALUE = '\uffff';
    public static final Class<Character> TYPE;
    public static final byte UNASSIGNED = 0;
    public static final byte UPPERCASE_LETTER = 1;
    public static final byte LOWERCASE_LETTER = 2;
    public static final byte TITLECASE_LETTER = 3;
    public static final byte MODIFIER_LETTER = 4;
    public static final byte OTHER_LETTER = 5;
    public static final byte NON_SPACING_MARK = 6;
    public static final byte ENCLOSING_MARK = 7;
    public static final byte COMBINING_SPACING_MARK = 8;
    public static final byte DECIMAL_DIGIT_NUMBER = 9;
    public static final byte LETTER_NUMBER = 10;
    public static final byte OTHER_NUMBER = 11;
    public static final byte SPACE_SEPARATOR = 12;
    public static final byte LINE_SEPARATOR = 13;
    public static final byte PARAGRAPH_SEPARATOR = 14;
    public static final byte CONTROL = 15;
    public static final byte FORMAT = 16;
    public static final byte PRIVATE_USE = 18;
    public static final byte SURROGATE = 19;
    public static final byte DASH_PUNCTUATION = 20;
    public static final byte START_PUNCTUATION = 21;
    public static final byte END_PUNCTUATION = 22;
    public static final byte CONNECTOR_PUNCTUATION = 23;
    public static final byte OTHER_PUNCTUATION = 24;
    public static final byte MATH_SYMBOL = 25;
    public static final byte CURRENCY_SYMBOL = 26;
    public static final byte MODIFIER_SYMBOL = 27;
    public static final byte OTHER_SYMBOL = 28;
    public static final byte INITIAL_QUOTE_PUNCTUATION = 29;
    public static final byte FINAL_QUOTE_PUNCTUATION = 30;
    static final int ERROR = -1;
    public static final byte DIRECTIONALITY_UNDEFINED = -1;
    public static final byte DIRECTIONALITY_LEFT_TO_RIGHT = 0;
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT = 1;
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC = 2;
    public static final byte DIRECTIONALITY_EUROPEAN_NUMBER = 3;
    public static final byte DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR = 4;
    public static final byte DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR = 5;
    public static final byte DIRECTIONALITY_ARABIC_NUMBER = 6;
    public static final byte DIRECTIONALITY_COMMON_NUMBER_SEPARATOR = 7;
    public static final byte DIRECTIONALITY_NONSPACING_MARK = 8;
    public static final byte DIRECTIONALITY_BOUNDARY_NEUTRAL = 9;
    public static final byte DIRECTIONALITY_PARAGRAPH_SEPARATOR = 10;
    public static final byte DIRECTIONALITY_SEGMENT_SEPARATOR = 11;
    public static final byte DIRECTIONALITY_WHITESPACE = 12;
    public static final byte DIRECTIONALITY_OTHER_NEUTRALS = 13;
    public static final byte DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING = 14;
    public static final byte DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE = 15;
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING = 16;
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE = 17;
    public static final byte DIRECTIONALITY_POP_DIRECTIONAL_FORMAT = 18;
    public static final char MIN_HIGH_SURROGATE = '\ud800';
    public static final char MAX_HIGH_SURROGATE = '\udbff';
    public static final char MIN_LOW_SURROGATE = '\udc00';
    public static final char MAX_LOW_SURROGATE = '\udfff';
    public static final char MIN_SURROGATE = '\ud800';
    public static final char MAX_SURROGATE = '\udfff';
    public static final int MIN_SUPPLEMENTARY_CODE_POINT = 65536;
    public static final int MIN_CODE_POINT = 0;
    public static final int MAX_CODE_POINT = 1114111;
    private final char value;
    private static final long serialVersionUID = 3786198910865385080L;
    public static final int SIZE = 16;
    public static final int BYTES = 2;
    
    public Character(final char value) {
        this.value = value;
    }
    
    public static Character valueOf(final char c) {
        if (c <= '\u007f') {
            return CharacterCache.cache[c];
        }
        return new Character(c);
    }
    
    public char charValue() {
        return this.value;
    }
    
    @Override
    public int hashCode() {
        return hashCode(this.value);
    }
    
    public static int hashCode(final char c) {
        return c;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Character && this.value == (char)o;
    }
    
    @Override
    public String toString() {
        return String.valueOf(new char[] { this.value });
    }
    
    public static String toString(final char c) {
        return String.valueOf(c);
    }
    
    public static boolean isValidCodePoint(final int n) {
        return n >>> 16 < 17;
    }
    
    public static boolean isBmpCodePoint(final int n) {
        return n >>> 16 == 0;
    }
    
    public static boolean isSupplementaryCodePoint(final int n) {
        return n >= 65536 && n < 1114112;
    }
    
    public static boolean isHighSurrogate(final char c) {
        return c >= '\ud800' && c < '\udc00';
    }
    
    public static boolean isLowSurrogate(final char c) {
        return c >= '\udc00' && c < '\ue000';
    }
    
    public static boolean isSurrogate(final char c) {
        return c >= '\ud800' && c < '\ue000';
    }
    
    public static boolean isSurrogatePair(final char c, final char c2) {
        return isHighSurrogate(c) && isLowSurrogate(c2);
    }
    
    public static int charCount(final int n) {
        return (n >= 65536) ? 2 : 1;
    }
    
    public static int toCodePoint(final char c, final char c2) {
        return (c << 10) + c2 - 56613888;
    }
    
    public static int codePointAt(final CharSequence charSequence, int n) {
        final char char1 = charSequence.charAt(n);
        if (isHighSurrogate(char1) && ++n < charSequence.length()) {
            final char char2 = charSequence.charAt(n);
            if (isLowSurrogate(char2)) {
                return toCodePoint(char1, char2);
            }
        }
        return char1;
    }
    
    public static int codePointAt(final char[] array, final int n) {
        return codePointAtImpl(array, n, array.length);
    }
    
    public static int codePointAt(final char[] array, final int n, final int n2) {
        if (n >= n2 || n2 < 0 || n2 > array.length) {
            throw new IndexOutOfBoundsException();
        }
        return codePointAtImpl(array, n, n2);
    }
    
    static int codePointAtImpl(final char[] array, int n, final int n2) {
        final char c = array[n];
        if (isHighSurrogate(c) && ++n < n2) {
            final char c2 = array[n];
            if (isLowSurrogate(c2)) {
                return toCodePoint(c, c2);
            }
        }
        return c;
    }
    
    public static int codePointBefore(final CharSequence charSequence, int n) {
        final char char1 = charSequence.charAt(--n);
        if (isLowSurrogate(char1) && n > 0) {
            final char char2 = charSequence.charAt(--n);
            if (isHighSurrogate(char2)) {
                return toCodePoint(char2, char1);
            }
        }
        return char1;
    }
    
    public static int codePointBefore(final char[] array, final int n) {
        return codePointBeforeImpl(array, n, 0);
    }
    
    public static int codePointBefore(final char[] array, final int n, final int n2) {
        if (n <= n2 || n2 < 0 || n2 >= array.length) {
            throw new IndexOutOfBoundsException();
        }
        return codePointBeforeImpl(array, n, n2);
    }
    
    static int codePointBeforeImpl(final char[] array, int n, final int n2) {
        final char c = array[--n];
        if (isLowSurrogate(c) && n > n2) {
            final char c2 = array[--n];
            if (isHighSurrogate(c2)) {
                return toCodePoint(c2, c);
            }
        }
        return c;
    }
    
    public static char highSurrogate(final int n) {
        return (char)((n >>> 10) + 55232);
    }
    
    public static char lowSurrogate(final int n) {
        return (char)((n & 0x3FF) + 56320);
    }
    
    public static int toChars(final int n, final char[] array, final int n2) {
        if (isBmpCodePoint(n)) {
            array[n2] = (char)n;
            return 1;
        }
        if (isValidCodePoint(n)) {
            toSurrogates(n, array, n2);
            return 2;
        }
        throw new IllegalArgumentException();
    }
    
    public static char[] toChars(final int n) {
        if (isBmpCodePoint(n)) {
            return new char[] { (char)n };
        }
        if (isValidCodePoint(n)) {
            final char[] array = new char[2];
            toSurrogates(n, array, 0);
            return array;
        }
        throw new IllegalArgumentException();
    }
    
    static void toSurrogates(final int n, final char[] array, final int n2) {
        array[n2 + 1] = lowSurrogate(n);
        array[n2] = highSurrogate(n);
    }
    
    public static int codePointCount(final CharSequence charSequence, final int n, final int n2) {
        final int length = charSequence.length();
        if (n < 0 || n2 > length || n > n2) {
            throw new IndexOutOfBoundsException();
        }
        int n3 = n2 - n;
        for (int i = n; i < n2; ++i) {
            if (isHighSurrogate(charSequence.charAt(i++)) && i < n2 && isLowSurrogate(charSequence.charAt(i))) {
                --n3;
            }
        }
        return n3;
    }
    
    public static int codePointCount(final char[] array, final int n, final int n2) {
        if (n2 > array.length - n || n < 0 || n2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        return codePointCountImpl(array, n, n2);
    }
    
    static int codePointCountImpl(final char[] array, final int n, final int n2) {
        final int n3 = n + n2;
        int n4 = n2;
        for (int i = n; i < n3; ++i) {
            if (isHighSurrogate(array[i++]) && i < n3 && isLowSurrogate(array[i])) {
                --n4;
            }
        }
        return n4;
    }
    
    public static int offsetByCodePoints(final CharSequence charSequence, final int n, final int n2) {
        final int length = charSequence.length();
        if (n < 0 || n > length) {
            throw new IndexOutOfBoundsException();
        }
        int n3 = n;
        if (n2 >= 0) {
            int n4;
            for (n4 = 0; n3 < length && n4 < n2; ++n4) {
                if (isHighSurrogate(charSequence.charAt(n3++)) && n3 < length && isLowSurrogate(charSequence.charAt(n3))) {
                    ++n3;
                }
            }
            if (n4 < n2) {
                throw new IndexOutOfBoundsException();
            }
        }
        else {
            int n5;
            for (n5 = n2; n3 > 0 && n5 < 0; ++n5) {
                if (isLowSurrogate(charSequence.charAt(--n3)) && n3 > 0 && isHighSurrogate(charSequence.charAt(n3 - 1))) {
                    --n3;
                }
            }
            if (n5 < 0) {
                throw new IndexOutOfBoundsException();
            }
        }
        return n3;
    }
    
    public static int offsetByCodePoints(final char[] array, final int n, final int n2, final int n3, final int n4) {
        if (n2 > array.length - n || n < 0 || n2 < 0 || n3 < n || n3 > n + n2) {
            throw new IndexOutOfBoundsException();
        }
        return offsetByCodePointsImpl(array, n, n2, n3, n4);
    }
    
    static int offsetByCodePointsImpl(final char[] array, final int n, final int n2, final int n3, final int n4) {
        int n5 = n3;
        if (n4 >= 0) {
            int n6;
            int n7;
            for (n6 = n + n2, n7 = 0; n5 < n6 && n7 < n4; ++n7) {
                if (isHighSurrogate(array[n5++]) && n5 < n6 && isLowSurrogate(array[n5])) {
                    ++n5;
                }
            }
            if (n7 < n4) {
                throw new IndexOutOfBoundsException();
            }
        }
        else {
            int n8;
            for (n8 = n4; n5 > n && n8 < 0; ++n8) {
                if (isLowSurrogate(array[--n5]) && n5 > n && isHighSurrogate(array[n5 - 1])) {
                    --n5;
                }
            }
            if (n8 < 0) {
                throw new IndexOutOfBoundsException();
            }
        }
        return n5;
    }
    
    public static boolean isLowerCase(final char c) {
        return isLowerCase((int)c);
    }
    
    public static boolean isLowerCase(final int n) {
        return getType(n) == 2 || CharacterData.of(n).isOtherLowercase(n);
    }
    
    public static boolean isUpperCase(final char c) {
        return isUpperCase((int)c);
    }
    
    public static boolean isUpperCase(final int n) {
        return getType(n) == 1 || CharacterData.of(n).isOtherUppercase(n);
    }
    
    public static boolean isTitleCase(final char c) {
        return isTitleCase((int)c);
    }
    
    public static boolean isTitleCase(final int n) {
        return getType(n) == 3;
    }
    
    public static boolean isDigit(final char c) {
        return isDigit((int)c);
    }
    
    public static boolean isDigit(final int n) {
        return getType(n) == 9;
    }
    
    public static boolean isDefined(final char c) {
        return isDefined((int)c);
    }
    
    public static boolean isDefined(final int n) {
        return getType(n) != 0;
    }
    
    public static boolean isLetter(final char c) {
        return isLetter((int)c);
    }
    
    public static boolean isLetter(final int n) {
        return (62 >> getType(n) & 0x1) != 0x0;
    }
    
    public static boolean isLetterOrDigit(final char c) {
        return isLetterOrDigit((int)c);
    }
    
    public static boolean isLetterOrDigit(final int n) {
        return (574 >> getType(n) & 0x1) != 0x0;
    }
    
    @Deprecated
    public static boolean isJavaLetter(final char c) {
        return isJavaIdentifierStart(c);
    }
    
    @Deprecated
    public static boolean isJavaLetterOrDigit(final char c) {
        return isJavaIdentifierPart(c);
    }
    
    public static boolean isAlphabetic(final int n) {
        return (1086 >> getType(n) & 0x1) != 0x0 || CharacterData.of(n).isOtherAlphabetic(n);
    }
    
    public static boolean isIdeographic(final int n) {
        return CharacterData.of(n).isIdeographic(n);
    }
    
    public static boolean isJavaIdentifierStart(final char c) {
        return isJavaIdentifierStart((int)c);
    }
    
    public static boolean isJavaIdentifierStart(final int n) {
        return CharacterData.of(n).isJavaIdentifierStart(n);
    }
    
    public static boolean isJavaIdentifierPart(final char c) {
        return isJavaIdentifierPart((int)c);
    }
    
    public static boolean isJavaIdentifierPart(final int n) {
        return CharacterData.of(n).isJavaIdentifierPart(n);
    }
    
    public static boolean isUnicodeIdentifierStart(final char c) {
        return isUnicodeIdentifierStart((int)c);
    }
    
    public static boolean isUnicodeIdentifierStart(final int n) {
        return CharacterData.of(n).isUnicodeIdentifierStart(n);
    }
    
    public static boolean isUnicodeIdentifierPart(final char c) {
        return isUnicodeIdentifierPart((int)c);
    }
    
    public static boolean isUnicodeIdentifierPart(final int n) {
        return CharacterData.of(n).isUnicodeIdentifierPart(n);
    }
    
    public static boolean isIdentifierIgnorable(final char c) {
        return isIdentifierIgnorable((int)c);
    }
    
    public static boolean isIdentifierIgnorable(final int n) {
        return CharacterData.of(n).isIdentifierIgnorable(n);
    }
    
    public static char toLowerCase(final char c) {
        return (char)toLowerCase((int)c);
    }
    
    public static int toLowerCase(final int n) {
        return CharacterData.of(n).toLowerCase(n);
    }
    
    public static char toUpperCase(final char c) {
        return (char)toUpperCase((int)c);
    }
    
    public static int toUpperCase(final int n) {
        return CharacterData.of(n).toUpperCase(n);
    }
    
    public static char toTitleCase(final char c) {
        return (char)toTitleCase((int)c);
    }
    
    public static int toTitleCase(final int n) {
        return CharacterData.of(n).toTitleCase(n);
    }
    
    public static int digit(final char c, final int n) {
        return digit((int)c, n);
    }
    
    public static int digit(final int n, final int n2) {
        return CharacterData.of(n).digit(n, n2);
    }
    
    public static int getNumericValue(final char c) {
        return getNumericValue((int)c);
    }
    
    public static int getNumericValue(final int n) {
        return CharacterData.of(n).getNumericValue(n);
    }
    
    @Deprecated
    public static boolean isSpace(final char c) {
        return c <= ' ' && (4294981120L >> c & 0x1L) != 0x0L;
    }
    
    public static boolean isSpaceChar(final char c) {
        return isSpaceChar((int)c);
    }
    
    public static boolean isSpaceChar(final int n) {
        return (28672 >> getType(n) & 0x1) != 0x0;
    }
    
    public static boolean isWhitespace(final char c) {
        return isWhitespace((int)c);
    }
    
    public static boolean isWhitespace(final int n) {
        return CharacterData.of(n).isWhitespace(n);
    }
    
    public static boolean isISOControl(final char c) {
        return isISOControl((int)c);
    }
    
    public static boolean isISOControl(final int n) {
        return n <= 159 && (n >= 127 || n >>> 5 == 0);
    }
    
    public static int getType(final char c) {
        return getType((int)c);
    }
    
    public static int getType(final int n) {
        return CharacterData.of(n).getType(n);
    }
    
    public static char forDigit(final int n, final int n2) {
        if (n >= n2 || n < 0) {
            return '\0';
        }
        if (n2 < 2 || n2 > 36) {
            return '\0';
        }
        if (n < 10) {
            return (char)(48 + n);
        }
        return (char)(87 + n);
    }
    
    public static byte getDirectionality(final char c) {
        return getDirectionality((int)c);
    }
    
    public static byte getDirectionality(final int n) {
        return CharacterData.of(n).getDirectionality(n);
    }
    
    public static boolean isMirrored(final char c) {
        return isMirrored((int)c);
    }
    
    public static boolean isMirrored(final int n) {
        return CharacterData.of(n).isMirrored(n);
    }
    
    @Override
    public int compareTo(final Character c) {
        return compare(this.value, c.value);
    }
    
    public static int compare(final char c, final char c2) {
        return c - c2;
    }
    
    static int toUpperCaseEx(final int n) {
        assert isValidCodePoint(n);
        return CharacterData.of(n).toUpperCaseEx(n);
    }
    
    static char[] toUpperCaseCharArray(final int n) {
        assert isBmpCodePoint(n);
        return CharacterData.of(n).toUpperCaseCharArray(n);
    }
    
    public static char reverseBytes(final char c) {
        return (char)((c & '\uff00') >> 8 | c << 8);
    }
    
    public static String getName(final int n) {
        if (!isValidCodePoint(n)) {
            throw new IllegalArgumentException();
        }
        final String value = CharacterName.get(n);
        if (value != null) {
            return value;
        }
        if (getType(n) == 0) {
            return null;
        }
        final UnicodeBlock of = UnicodeBlock.of(n);
        if (of != null) {
            return of.toString().replace('_', ' ') + " " + Integer.toHexString(n).toUpperCase(Locale.ENGLISH);
        }
        return Integer.toHexString(n).toUpperCase(Locale.ENGLISH);
    }
    
    static {
        TYPE = Class.getPrimitiveClass("char");
    }
    
    private static class CharacterCache
    {
        static final Character[] cache;
        
        static {
            cache = new Character[128];
            for (int i = 0; i < CharacterCache.cache.length; ++i) {
                CharacterCache.cache[i] = new Character((char)i);
            }
        }
    }
    
    public static class Subset
    {
        private String name;
        
        protected Subset(final String name) {
            if (name == null) {
                throw new NullPointerException("name");
            }
            this.name = name;
        }
        
        @Override
        public final boolean equals(final Object o) {
            return this == o;
        }
        
        @Override
        public final int hashCode() {
            return super.hashCode();
        }
        
        @Override
        public final String toString() {
            return this.name;
        }
    }
    
    public static final class UnicodeBlock extends Subset
    {
        private static Map<String, UnicodeBlock> map;
        public static final UnicodeBlock BASIC_LATIN;
        public static final UnicodeBlock LATIN_1_SUPPLEMENT;
        public static final UnicodeBlock LATIN_EXTENDED_A;
        public static final UnicodeBlock LATIN_EXTENDED_B;
        public static final UnicodeBlock IPA_EXTENSIONS;
        public static final UnicodeBlock SPACING_MODIFIER_LETTERS;
        public static final UnicodeBlock COMBINING_DIACRITICAL_MARKS;
        public static final UnicodeBlock GREEK;
        public static final UnicodeBlock CYRILLIC;
        public static final UnicodeBlock ARMENIAN;
        public static final UnicodeBlock HEBREW;
        public static final UnicodeBlock ARABIC;
        public static final UnicodeBlock DEVANAGARI;
        public static final UnicodeBlock BENGALI;
        public static final UnicodeBlock GURMUKHI;
        public static final UnicodeBlock GUJARATI;
        public static final UnicodeBlock ORIYA;
        public static final UnicodeBlock TAMIL;
        public static final UnicodeBlock TELUGU;
        public static final UnicodeBlock KANNADA;
        public static final UnicodeBlock MALAYALAM;
        public static final UnicodeBlock THAI;
        public static final UnicodeBlock LAO;
        public static final UnicodeBlock TIBETAN;
        public static final UnicodeBlock GEORGIAN;
        public static final UnicodeBlock HANGUL_JAMO;
        public static final UnicodeBlock LATIN_EXTENDED_ADDITIONAL;
        public static final UnicodeBlock GREEK_EXTENDED;
        public static final UnicodeBlock GENERAL_PUNCTUATION;
        public static final UnicodeBlock SUPERSCRIPTS_AND_SUBSCRIPTS;
        public static final UnicodeBlock CURRENCY_SYMBOLS;
        public static final UnicodeBlock COMBINING_MARKS_FOR_SYMBOLS;
        public static final UnicodeBlock LETTERLIKE_SYMBOLS;
        public static final UnicodeBlock NUMBER_FORMS;
        public static final UnicodeBlock ARROWS;
        public static final UnicodeBlock MATHEMATICAL_OPERATORS;
        public static final UnicodeBlock MISCELLANEOUS_TECHNICAL;
        public static final UnicodeBlock CONTROL_PICTURES;
        public static final UnicodeBlock OPTICAL_CHARACTER_RECOGNITION;
        public static final UnicodeBlock ENCLOSED_ALPHANUMERICS;
        public static final UnicodeBlock BOX_DRAWING;
        public static final UnicodeBlock BLOCK_ELEMENTS;
        public static final UnicodeBlock GEOMETRIC_SHAPES;
        public static final UnicodeBlock MISCELLANEOUS_SYMBOLS;
        public static final UnicodeBlock DINGBATS;
        public static final UnicodeBlock CJK_SYMBOLS_AND_PUNCTUATION;
        public static final UnicodeBlock HIRAGANA;
        public static final UnicodeBlock KATAKANA;
        public static final UnicodeBlock BOPOMOFO;
        public static final UnicodeBlock HANGUL_COMPATIBILITY_JAMO;
        public static final UnicodeBlock KANBUN;
        public static final UnicodeBlock ENCLOSED_CJK_LETTERS_AND_MONTHS;
        public static final UnicodeBlock CJK_COMPATIBILITY;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS;
        public static final UnicodeBlock HANGUL_SYLLABLES;
        public static final UnicodeBlock PRIVATE_USE_AREA;
        public static final UnicodeBlock CJK_COMPATIBILITY_IDEOGRAPHS;
        public static final UnicodeBlock ALPHABETIC_PRESENTATION_FORMS;
        public static final UnicodeBlock ARABIC_PRESENTATION_FORMS_A;
        public static final UnicodeBlock COMBINING_HALF_MARKS;
        public static final UnicodeBlock CJK_COMPATIBILITY_FORMS;
        public static final UnicodeBlock SMALL_FORM_VARIANTS;
        public static final UnicodeBlock ARABIC_PRESENTATION_FORMS_B;
        public static final UnicodeBlock HALFWIDTH_AND_FULLWIDTH_FORMS;
        public static final UnicodeBlock SPECIALS;
        @Deprecated
        public static final UnicodeBlock SURROGATES_AREA;
        public static final UnicodeBlock SYRIAC;
        public static final UnicodeBlock THAANA;
        public static final UnicodeBlock SINHALA;
        public static final UnicodeBlock MYANMAR;
        public static final UnicodeBlock ETHIOPIC;
        public static final UnicodeBlock CHEROKEE;
        public static final UnicodeBlock UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS;
        public static final UnicodeBlock OGHAM;
        public static final UnicodeBlock RUNIC;
        public static final UnicodeBlock KHMER;
        public static final UnicodeBlock MONGOLIAN;
        public static final UnicodeBlock BRAILLE_PATTERNS;
        public static final UnicodeBlock CJK_RADICALS_SUPPLEMENT;
        public static final UnicodeBlock KANGXI_RADICALS;
        public static final UnicodeBlock IDEOGRAPHIC_DESCRIPTION_CHARACTERS;
        public static final UnicodeBlock BOPOMOFO_EXTENDED;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A;
        public static final UnicodeBlock YI_SYLLABLES;
        public static final UnicodeBlock YI_RADICALS;
        public static final UnicodeBlock CYRILLIC_SUPPLEMENTARY;
        public static final UnicodeBlock TAGALOG;
        public static final UnicodeBlock HANUNOO;
        public static final UnicodeBlock BUHID;
        public static final UnicodeBlock TAGBANWA;
        public static final UnicodeBlock LIMBU;
        public static final UnicodeBlock TAI_LE;
        public static final UnicodeBlock KHMER_SYMBOLS;
        public static final UnicodeBlock PHONETIC_EXTENSIONS;
        public static final UnicodeBlock MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A;
        public static final UnicodeBlock SUPPLEMENTAL_ARROWS_A;
        public static final UnicodeBlock SUPPLEMENTAL_ARROWS_B;
        public static final UnicodeBlock MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B;
        public static final UnicodeBlock SUPPLEMENTAL_MATHEMATICAL_OPERATORS;
        public static final UnicodeBlock MISCELLANEOUS_SYMBOLS_AND_ARROWS;
        public static final UnicodeBlock KATAKANA_PHONETIC_EXTENSIONS;
        public static final UnicodeBlock YIJING_HEXAGRAM_SYMBOLS;
        public static final UnicodeBlock VARIATION_SELECTORS;
        public static final UnicodeBlock LINEAR_B_SYLLABARY;
        public static final UnicodeBlock LINEAR_B_IDEOGRAMS;
        public static final UnicodeBlock AEGEAN_NUMBERS;
        public static final UnicodeBlock OLD_ITALIC;
        public static final UnicodeBlock GOTHIC;
        public static final UnicodeBlock UGARITIC;
        public static final UnicodeBlock DESERET;
        public static final UnicodeBlock SHAVIAN;
        public static final UnicodeBlock OSMANYA;
        public static final UnicodeBlock CYPRIOT_SYLLABARY;
        public static final UnicodeBlock BYZANTINE_MUSICAL_SYMBOLS;
        public static final UnicodeBlock MUSICAL_SYMBOLS;
        public static final UnicodeBlock TAI_XUAN_JING_SYMBOLS;
        public static final UnicodeBlock MATHEMATICAL_ALPHANUMERIC_SYMBOLS;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B;
        public static final UnicodeBlock CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT;
        public static final UnicodeBlock TAGS;
        public static final UnicodeBlock VARIATION_SELECTORS_SUPPLEMENT;
        public static final UnicodeBlock SUPPLEMENTARY_PRIVATE_USE_AREA_A;
        public static final UnicodeBlock SUPPLEMENTARY_PRIVATE_USE_AREA_B;
        public static final UnicodeBlock HIGH_SURROGATES;
        public static final UnicodeBlock HIGH_PRIVATE_USE_SURROGATES;
        public static final UnicodeBlock LOW_SURROGATES;
        public static final UnicodeBlock ARABIC_SUPPLEMENT;
        public static final UnicodeBlock NKO;
        public static final UnicodeBlock SAMARITAN;
        public static final UnicodeBlock MANDAIC;
        public static final UnicodeBlock ETHIOPIC_SUPPLEMENT;
        public static final UnicodeBlock UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED;
        public static final UnicodeBlock NEW_TAI_LUE;
        public static final UnicodeBlock BUGINESE;
        public static final UnicodeBlock TAI_THAM;
        public static final UnicodeBlock BALINESE;
        public static final UnicodeBlock SUNDANESE;
        public static final UnicodeBlock BATAK;
        public static final UnicodeBlock LEPCHA;
        public static final UnicodeBlock OL_CHIKI;
        public static final UnicodeBlock VEDIC_EXTENSIONS;
        public static final UnicodeBlock PHONETIC_EXTENSIONS_SUPPLEMENT;
        public static final UnicodeBlock COMBINING_DIACRITICAL_MARKS_SUPPLEMENT;
        public static final UnicodeBlock GLAGOLITIC;
        public static final UnicodeBlock LATIN_EXTENDED_C;
        public static final UnicodeBlock COPTIC;
        public static final UnicodeBlock GEORGIAN_SUPPLEMENT;
        public static final UnicodeBlock TIFINAGH;
        public static final UnicodeBlock ETHIOPIC_EXTENDED;
        public static final UnicodeBlock CYRILLIC_EXTENDED_A;
        public static final UnicodeBlock SUPPLEMENTAL_PUNCTUATION;
        public static final UnicodeBlock CJK_STROKES;
        public static final UnicodeBlock LISU;
        public static final UnicodeBlock VAI;
        public static final UnicodeBlock CYRILLIC_EXTENDED_B;
        public static final UnicodeBlock BAMUM;
        public static final UnicodeBlock MODIFIER_TONE_LETTERS;
        public static final UnicodeBlock LATIN_EXTENDED_D;
        public static final UnicodeBlock SYLOTI_NAGRI;
        public static final UnicodeBlock COMMON_INDIC_NUMBER_FORMS;
        public static final UnicodeBlock PHAGS_PA;
        public static final UnicodeBlock SAURASHTRA;
        public static final UnicodeBlock DEVANAGARI_EXTENDED;
        public static final UnicodeBlock KAYAH_LI;
        public static final UnicodeBlock REJANG;
        public static final UnicodeBlock HANGUL_JAMO_EXTENDED_A;
        public static final UnicodeBlock JAVANESE;
        public static final UnicodeBlock CHAM;
        public static final UnicodeBlock MYANMAR_EXTENDED_A;
        public static final UnicodeBlock TAI_VIET;
        public static final UnicodeBlock ETHIOPIC_EXTENDED_A;
        public static final UnicodeBlock MEETEI_MAYEK;
        public static final UnicodeBlock HANGUL_JAMO_EXTENDED_B;
        public static final UnicodeBlock VERTICAL_FORMS;
        public static final UnicodeBlock ANCIENT_GREEK_NUMBERS;
        public static final UnicodeBlock ANCIENT_SYMBOLS;
        public static final UnicodeBlock PHAISTOS_DISC;
        public static final UnicodeBlock LYCIAN;
        public static final UnicodeBlock CARIAN;
        public static final UnicodeBlock OLD_PERSIAN;
        public static final UnicodeBlock IMPERIAL_ARAMAIC;
        public static final UnicodeBlock PHOENICIAN;
        public static final UnicodeBlock LYDIAN;
        public static final UnicodeBlock KHAROSHTHI;
        public static final UnicodeBlock OLD_SOUTH_ARABIAN;
        public static final UnicodeBlock AVESTAN;
        public static final UnicodeBlock INSCRIPTIONAL_PARTHIAN;
        public static final UnicodeBlock INSCRIPTIONAL_PAHLAVI;
        public static final UnicodeBlock OLD_TURKIC;
        public static final UnicodeBlock RUMI_NUMERAL_SYMBOLS;
        public static final UnicodeBlock BRAHMI;
        public static final UnicodeBlock KAITHI;
        public static final UnicodeBlock CUNEIFORM;
        public static final UnicodeBlock CUNEIFORM_NUMBERS_AND_PUNCTUATION;
        public static final UnicodeBlock EGYPTIAN_HIEROGLYPHS;
        public static final UnicodeBlock BAMUM_SUPPLEMENT;
        public static final UnicodeBlock KANA_SUPPLEMENT;
        public static final UnicodeBlock ANCIENT_GREEK_MUSICAL_NOTATION;
        public static final UnicodeBlock COUNTING_ROD_NUMERALS;
        public static final UnicodeBlock MAHJONG_TILES;
        public static final UnicodeBlock DOMINO_TILES;
        public static final UnicodeBlock PLAYING_CARDS;
        public static final UnicodeBlock ENCLOSED_ALPHANUMERIC_SUPPLEMENT;
        public static final UnicodeBlock ENCLOSED_IDEOGRAPHIC_SUPPLEMENT;
        public static final UnicodeBlock MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS;
        public static final UnicodeBlock EMOTICONS;
        public static final UnicodeBlock TRANSPORT_AND_MAP_SYMBOLS;
        public static final UnicodeBlock ALCHEMICAL_SYMBOLS;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C;
        public static final UnicodeBlock CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D;
        public static final UnicodeBlock ARABIC_EXTENDED_A;
        public static final UnicodeBlock SUNDANESE_SUPPLEMENT;
        public static final UnicodeBlock MEETEI_MAYEK_EXTENSIONS;
        public static final UnicodeBlock MEROITIC_HIEROGLYPHS;
        public static final UnicodeBlock MEROITIC_CURSIVE;
        public static final UnicodeBlock SORA_SOMPENG;
        public static final UnicodeBlock CHAKMA;
        public static final UnicodeBlock SHARADA;
        public static final UnicodeBlock TAKRI;
        public static final UnicodeBlock MIAO;
        public static final UnicodeBlock ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS;
        private static final int[] blockStarts;
        private static final UnicodeBlock[] blocks;
        
        private UnicodeBlock(final String s) {
            super(s);
            UnicodeBlock.map.put(s, this);
        }
        
        private UnicodeBlock(final String s, final String s2) {
            this(s);
            UnicodeBlock.map.put(s2, this);
        }
        
        private UnicodeBlock(final String s, final String... array) {
            this(s);
            for (int length = array.length, i = 0; i < length; ++i) {
                UnicodeBlock.map.put(array[i], this);
            }
        }
        
        public static UnicodeBlock of(final char c) {
            return of((int)c);
        }
        
        public static UnicodeBlock of(final int n) {
            if (!Character.isValidCodePoint(n)) {
                throw new IllegalArgumentException();
            }
            int n2 = 0;
            int length = UnicodeBlock.blockStarts.length;
            int n3 = length / 2;
            while (length - n2 > 1) {
                if (n >= UnicodeBlock.blockStarts[n3]) {
                    n2 = n3;
                }
                else {
                    length = n3;
                }
                n3 = (length + n2) / 2;
            }
            return UnicodeBlock.blocks[n3];
        }
        
        public static final UnicodeBlock forName(final String s) {
            final UnicodeBlock unicodeBlock = UnicodeBlock.map.get(s.toUpperCase(Locale.US));
            if (unicodeBlock == null) {
                throw new IllegalArgumentException();
            }
            return unicodeBlock;
        }
        
        static {
            UnicodeBlock.map = new HashMap<String, UnicodeBlock>(256);
            BASIC_LATIN = new UnicodeBlock("BASIC_LATIN", new String[] { "BASIC LATIN", "BASICLATIN" });
            LATIN_1_SUPPLEMENT = new UnicodeBlock("LATIN_1_SUPPLEMENT", new String[] { "LATIN-1 SUPPLEMENT", "LATIN-1SUPPLEMENT" });
            LATIN_EXTENDED_A = new UnicodeBlock("LATIN_EXTENDED_A", new String[] { "LATIN EXTENDED-A", "LATINEXTENDED-A" });
            LATIN_EXTENDED_B = new UnicodeBlock("LATIN_EXTENDED_B", new String[] { "LATIN EXTENDED-B", "LATINEXTENDED-B" });
            IPA_EXTENSIONS = new UnicodeBlock("IPA_EXTENSIONS", new String[] { "IPA EXTENSIONS", "IPAEXTENSIONS" });
            SPACING_MODIFIER_LETTERS = new UnicodeBlock("SPACING_MODIFIER_LETTERS", new String[] { "SPACING MODIFIER LETTERS", "SPACINGMODIFIERLETTERS" });
            COMBINING_DIACRITICAL_MARKS = new UnicodeBlock("COMBINING_DIACRITICAL_MARKS", new String[] { "COMBINING DIACRITICAL MARKS", "COMBININGDIACRITICALMARKS" });
            GREEK = new UnicodeBlock("GREEK", new String[] { "GREEK AND COPTIC", "GREEKANDCOPTIC" });
            CYRILLIC = new UnicodeBlock("CYRILLIC");
            ARMENIAN = new UnicodeBlock("ARMENIAN");
            HEBREW = new UnicodeBlock("HEBREW");
            ARABIC = new UnicodeBlock("ARABIC");
            DEVANAGARI = new UnicodeBlock("DEVANAGARI");
            BENGALI = new UnicodeBlock("BENGALI");
            GURMUKHI = new UnicodeBlock("GURMUKHI");
            GUJARATI = new UnicodeBlock("GUJARATI");
            ORIYA = new UnicodeBlock("ORIYA");
            TAMIL = new UnicodeBlock("TAMIL");
            TELUGU = new UnicodeBlock("TELUGU");
            KANNADA = new UnicodeBlock("KANNADA");
            MALAYALAM = new UnicodeBlock("MALAYALAM");
            THAI = new UnicodeBlock("THAI");
            LAO = new UnicodeBlock("LAO");
            TIBETAN = new UnicodeBlock("TIBETAN");
            GEORGIAN = new UnicodeBlock("GEORGIAN");
            HANGUL_JAMO = new UnicodeBlock("HANGUL_JAMO", new String[] { "HANGUL JAMO", "HANGULJAMO" });
            LATIN_EXTENDED_ADDITIONAL = new UnicodeBlock("LATIN_EXTENDED_ADDITIONAL", new String[] { "LATIN EXTENDED ADDITIONAL", "LATINEXTENDEDADDITIONAL" });
            GREEK_EXTENDED = new UnicodeBlock("GREEK_EXTENDED", new String[] { "GREEK EXTENDED", "GREEKEXTENDED" });
            GENERAL_PUNCTUATION = new UnicodeBlock("GENERAL_PUNCTUATION", new String[] { "GENERAL PUNCTUATION", "GENERALPUNCTUATION" });
            SUPERSCRIPTS_AND_SUBSCRIPTS = new UnicodeBlock("SUPERSCRIPTS_AND_SUBSCRIPTS", new String[] { "SUPERSCRIPTS AND SUBSCRIPTS", "SUPERSCRIPTSANDSUBSCRIPTS" });
            CURRENCY_SYMBOLS = new UnicodeBlock("CURRENCY_SYMBOLS", new String[] { "CURRENCY SYMBOLS", "CURRENCYSYMBOLS" });
            COMBINING_MARKS_FOR_SYMBOLS = new UnicodeBlock("COMBINING_MARKS_FOR_SYMBOLS", new String[] { "COMBINING DIACRITICAL MARKS FOR SYMBOLS", "COMBININGDIACRITICALMARKSFORSYMBOLS", "COMBINING MARKS FOR SYMBOLS", "COMBININGMARKSFORSYMBOLS" });
            LETTERLIKE_SYMBOLS = new UnicodeBlock("LETTERLIKE_SYMBOLS", new String[] { "LETTERLIKE SYMBOLS", "LETTERLIKESYMBOLS" });
            NUMBER_FORMS = new UnicodeBlock("NUMBER_FORMS", new String[] { "NUMBER FORMS", "NUMBERFORMS" });
            ARROWS = new UnicodeBlock("ARROWS");
            MATHEMATICAL_OPERATORS = new UnicodeBlock("MATHEMATICAL_OPERATORS", new String[] { "MATHEMATICAL OPERATORS", "MATHEMATICALOPERATORS" });
            MISCELLANEOUS_TECHNICAL = new UnicodeBlock("MISCELLANEOUS_TECHNICAL", new String[] { "MISCELLANEOUS TECHNICAL", "MISCELLANEOUSTECHNICAL" });
            CONTROL_PICTURES = new UnicodeBlock("CONTROL_PICTURES", new String[] { "CONTROL PICTURES", "CONTROLPICTURES" });
            OPTICAL_CHARACTER_RECOGNITION = new UnicodeBlock("OPTICAL_CHARACTER_RECOGNITION", new String[] { "OPTICAL CHARACTER RECOGNITION", "OPTICALCHARACTERRECOGNITION" });
            ENCLOSED_ALPHANUMERICS = new UnicodeBlock("ENCLOSED_ALPHANUMERICS", new String[] { "ENCLOSED ALPHANUMERICS", "ENCLOSEDALPHANUMERICS" });
            BOX_DRAWING = new UnicodeBlock("BOX_DRAWING", new String[] { "BOX DRAWING", "BOXDRAWING" });
            BLOCK_ELEMENTS = new UnicodeBlock("BLOCK_ELEMENTS", new String[] { "BLOCK ELEMENTS", "BLOCKELEMENTS" });
            GEOMETRIC_SHAPES = new UnicodeBlock("GEOMETRIC_SHAPES", new String[] { "GEOMETRIC SHAPES", "GEOMETRICSHAPES" });
            MISCELLANEOUS_SYMBOLS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS", new String[] { "MISCELLANEOUS SYMBOLS", "MISCELLANEOUSSYMBOLS" });
            DINGBATS = new UnicodeBlock("DINGBATS");
            CJK_SYMBOLS_AND_PUNCTUATION = new UnicodeBlock("CJK_SYMBOLS_AND_PUNCTUATION", new String[] { "CJK SYMBOLS AND PUNCTUATION", "CJKSYMBOLSANDPUNCTUATION" });
            HIRAGANA = new UnicodeBlock("HIRAGANA");
            KATAKANA = new UnicodeBlock("KATAKANA");
            BOPOMOFO = new UnicodeBlock("BOPOMOFO");
            HANGUL_COMPATIBILITY_JAMO = new UnicodeBlock("HANGUL_COMPATIBILITY_JAMO", new String[] { "HANGUL COMPATIBILITY JAMO", "HANGULCOMPATIBILITYJAMO" });
            KANBUN = new UnicodeBlock("KANBUN");
            ENCLOSED_CJK_LETTERS_AND_MONTHS = new UnicodeBlock("ENCLOSED_CJK_LETTERS_AND_MONTHS", new String[] { "ENCLOSED CJK LETTERS AND MONTHS", "ENCLOSEDCJKLETTERSANDMONTHS" });
            CJK_COMPATIBILITY = new UnicodeBlock("CJK_COMPATIBILITY", new String[] { "CJK COMPATIBILITY", "CJKCOMPATIBILITY" });
            CJK_UNIFIED_IDEOGRAPHS = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS", new String[] { "CJK UNIFIED IDEOGRAPHS", "CJKUNIFIEDIDEOGRAPHS" });
            HANGUL_SYLLABLES = new UnicodeBlock("HANGUL_SYLLABLES", new String[] { "HANGUL SYLLABLES", "HANGULSYLLABLES" });
            PRIVATE_USE_AREA = new UnicodeBlock("PRIVATE_USE_AREA", new String[] { "PRIVATE USE AREA", "PRIVATEUSEAREA" });
            CJK_COMPATIBILITY_IDEOGRAPHS = new UnicodeBlock("CJK_COMPATIBILITY_IDEOGRAPHS", new String[] { "CJK COMPATIBILITY IDEOGRAPHS", "CJKCOMPATIBILITYIDEOGRAPHS" });
            ALPHABETIC_PRESENTATION_FORMS = new UnicodeBlock("ALPHABETIC_PRESENTATION_FORMS", new String[] { "ALPHABETIC PRESENTATION FORMS", "ALPHABETICPRESENTATIONFORMS" });
            ARABIC_PRESENTATION_FORMS_A = new UnicodeBlock("ARABIC_PRESENTATION_FORMS_A", new String[] { "ARABIC PRESENTATION FORMS-A", "ARABICPRESENTATIONFORMS-A" });
            COMBINING_HALF_MARKS = new UnicodeBlock("COMBINING_HALF_MARKS", new String[] { "COMBINING HALF MARKS", "COMBININGHALFMARKS" });
            CJK_COMPATIBILITY_FORMS = new UnicodeBlock("CJK_COMPATIBILITY_FORMS", new String[] { "CJK COMPATIBILITY FORMS", "CJKCOMPATIBILITYFORMS" });
            SMALL_FORM_VARIANTS = new UnicodeBlock("SMALL_FORM_VARIANTS", new String[] { "SMALL FORM VARIANTS", "SMALLFORMVARIANTS" });
            ARABIC_PRESENTATION_FORMS_B = new UnicodeBlock("ARABIC_PRESENTATION_FORMS_B", new String[] { "ARABIC PRESENTATION FORMS-B", "ARABICPRESENTATIONFORMS-B" });
            HALFWIDTH_AND_FULLWIDTH_FORMS = new UnicodeBlock("HALFWIDTH_AND_FULLWIDTH_FORMS", new String[] { "HALFWIDTH AND FULLWIDTH FORMS", "HALFWIDTHANDFULLWIDTHFORMS" });
            SPECIALS = new UnicodeBlock("SPECIALS");
            SURROGATES_AREA = new UnicodeBlock("SURROGATES_AREA");
            SYRIAC = new UnicodeBlock("SYRIAC");
            THAANA = new UnicodeBlock("THAANA");
            SINHALA = new UnicodeBlock("SINHALA");
            MYANMAR = new UnicodeBlock("MYANMAR");
            ETHIOPIC = new UnicodeBlock("ETHIOPIC");
            CHEROKEE = new UnicodeBlock("CHEROKEE");
            UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS = new UnicodeBlock("UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS", new String[] { "UNIFIED CANADIAN ABORIGINAL SYLLABICS", "UNIFIEDCANADIANABORIGINALSYLLABICS" });
            OGHAM = new UnicodeBlock("OGHAM");
            RUNIC = new UnicodeBlock("RUNIC");
            KHMER = new UnicodeBlock("KHMER");
            MONGOLIAN = new UnicodeBlock("MONGOLIAN");
            BRAILLE_PATTERNS = new UnicodeBlock("BRAILLE_PATTERNS", new String[] { "BRAILLE PATTERNS", "BRAILLEPATTERNS" });
            CJK_RADICALS_SUPPLEMENT = new UnicodeBlock("CJK_RADICALS_SUPPLEMENT", new String[] { "CJK RADICALS SUPPLEMENT", "CJKRADICALSSUPPLEMENT" });
            KANGXI_RADICALS = new UnicodeBlock("KANGXI_RADICALS", new String[] { "KANGXI RADICALS", "KANGXIRADICALS" });
            IDEOGRAPHIC_DESCRIPTION_CHARACTERS = new UnicodeBlock("IDEOGRAPHIC_DESCRIPTION_CHARACTERS", new String[] { "IDEOGRAPHIC DESCRIPTION CHARACTERS", "IDEOGRAPHICDESCRIPTIONCHARACTERS" });
            BOPOMOFO_EXTENDED = new UnicodeBlock("BOPOMOFO_EXTENDED", new String[] { "BOPOMOFO EXTENDED", "BOPOMOFOEXTENDED" });
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A", new String[] { "CJK UNIFIED IDEOGRAPHS EXTENSION A", "CJKUNIFIEDIDEOGRAPHSEXTENSIONA" });
            YI_SYLLABLES = new UnicodeBlock("YI_SYLLABLES", new String[] { "YI SYLLABLES", "YISYLLABLES" });
            YI_RADICALS = new UnicodeBlock("YI_RADICALS", new String[] { "YI RADICALS", "YIRADICALS" });
            CYRILLIC_SUPPLEMENTARY = new UnicodeBlock("CYRILLIC_SUPPLEMENTARY", new String[] { "CYRILLIC SUPPLEMENTARY", "CYRILLICSUPPLEMENTARY", "CYRILLIC SUPPLEMENT", "CYRILLICSUPPLEMENT" });
            TAGALOG = new UnicodeBlock("TAGALOG");
            HANUNOO = new UnicodeBlock("HANUNOO");
            BUHID = new UnicodeBlock("BUHID");
            TAGBANWA = new UnicodeBlock("TAGBANWA");
            LIMBU = new UnicodeBlock("LIMBU");
            TAI_LE = new UnicodeBlock("TAI_LE", new String[] { "TAI LE", "TAILE" });
            KHMER_SYMBOLS = new UnicodeBlock("KHMER_SYMBOLS", new String[] { "KHMER SYMBOLS", "KHMERSYMBOLS" });
            PHONETIC_EXTENSIONS = new UnicodeBlock("PHONETIC_EXTENSIONS", new String[] { "PHONETIC EXTENSIONS", "PHONETICEXTENSIONS" });
            MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A = new UnicodeBlock("MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A", new String[] { "MISCELLANEOUS MATHEMATICAL SYMBOLS-A", "MISCELLANEOUSMATHEMATICALSYMBOLS-A" });
            SUPPLEMENTAL_ARROWS_A = new UnicodeBlock("SUPPLEMENTAL_ARROWS_A", new String[] { "SUPPLEMENTAL ARROWS-A", "SUPPLEMENTALARROWS-A" });
            SUPPLEMENTAL_ARROWS_B = new UnicodeBlock("SUPPLEMENTAL_ARROWS_B", new String[] { "SUPPLEMENTAL ARROWS-B", "SUPPLEMENTALARROWS-B" });
            MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B = new UnicodeBlock("MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B", new String[] { "MISCELLANEOUS MATHEMATICAL SYMBOLS-B", "MISCELLANEOUSMATHEMATICALSYMBOLS-B" });
            SUPPLEMENTAL_MATHEMATICAL_OPERATORS = new UnicodeBlock("SUPPLEMENTAL_MATHEMATICAL_OPERATORS", new String[] { "SUPPLEMENTAL MATHEMATICAL OPERATORS", "SUPPLEMENTALMATHEMATICALOPERATORS" });
            MISCELLANEOUS_SYMBOLS_AND_ARROWS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS_AND_ARROWS", new String[] { "MISCELLANEOUS SYMBOLS AND ARROWS", "MISCELLANEOUSSYMBOLSANDARROWS" });
            KATAKANA_PHONETIC_EXTENSIONS = new UnicodeBlock("KATAKANA_PHONETIC_EXTENSIONS", new String[] { "KATAKANA PHONETIC EXTENSIONS", "KATAKANAPHONETICEXTENSIONS" });
            YIJING_HEXAGRAM_SYMBOLS = new UnicodeBlock("YIJING_HEXAGRAM_SYMBOLS", new String[] { "YIJING HEXAGRAM SYMBOLS", "YIJINGHEXAGRAMSYMBOLS" });
            VARIATION_SELECTORS = new UnicodeBlock("VARIATION_SELECTORS", new String[] { "VARIATION SELECTORS", "VARIATIONSELECTORS" });
            LINEAR_B_SYLLABARY = new UnicodeBlock("LINEAR_B_SYLLABARY", new String[] { "LINEAR B SYLLABARY", "LINEARBSYLLABARY" });
            LINEAR_B_IDEOGRAMS = new UnicodeBlock("LINEAR_B_IDEOGRAMS", new String[] { "LINEAR B IDEOGRAMS", "LINEARBIDEOGRAMS" });
            AEGEAN_NUMBERS = new UnicodeBlock("AEGEAN_NUMBERS", new String[] { "AEGEAN NUMBERS", "AEGEANNUMBERS" });
            OLD_ITALIC = new UnicodeBlock("OLD_ITALIC", new String[] { "OLD ITALIC", "OLDITALIC" });
            GOTHIC = new UnicodeBlock("GOTHIC");
            UGARITIC = new UnicodeBlock("UGARITIC");
            DESERET = new UnicodeBlock("DESERET");
            SHAVIAN = new UnicodeBlock("SHAVIAN");
            OSMANYA = new UnicodeBlock("OSMANYA");
            CYPRIOT_SYLLABARY = new UnicodeBlock("CYPRIOT_SYLLABARY", new String[] { "CYPRIOT SYLLABARY", "CYPRIOTSYLLABARY" });
            BYZANTINE_MUSICAL_SYMBOLS = new UnicodeBlock("BYZANTINE_MUSICAL_SYMBOLS", new String[] { "BYZANTINE MUSICAL SYMBOLS", "BYZANTINEMUSICALSYMBOLS" });
            MUSICAL_SYMBOLS = new UnicodeBlock("MUSICAL_SYMBOLS", new String[] { "MUSICAL SYMBOLS", "MUSICALSYMBOLS" });
            TAI_XUAN_JING_SYMBOLS = new UnicodeBlock("TAI_XUAN_JING_SYMBOLS", new String[] { "TAI XUAN JING SYMBOLS", "TAIXUANJINGSYMBOLS" });
            MATHEMATICAL_ALPHANUMERIC_SYMBOLS = new UnicodeBlock("MATHEMATICAL_ALPHANUMERIC_SYMBOLS", new String[] { "MATHEMATICAL ALPHANUMERIC SYMBOLS", "MATHEMATICALALPHANUMERICSYMBOLS" });
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B", new String[] { "CJK UNIFIED IDEOGRAPHS EXTENSION B", "CJKUNIFIEDIDEOGRAPHSEXTENSIONB" });
            CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT = new UnicodeBlock("CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT", new String[] { "CJK COMPATIBILITY IDEOGRAPHS SUPPLEMENT", "CJKCOMPATIBILITYIDEOGRAPHSSUPPLEMENT" });
            TAGS = new UnicodeBlock("TAGS");
            VARIATION_SELECTORS_SUPPLEMENT = new UnicodeBlock("VARIATION_SELECTORS_SUPPLEMENT", new String[] { "VARIATION SELECTORS SUPPLEMENT", "VARIATIONSELECTORSSUPPLEMENT" });
            SUPPLEMENTARY_PRIVATE_USE_AREA_A = new UnicodeBlock("SUPPLEMENTARY_PRIVATE_USE_AREA_A", new String[] { "SUPPLEMENTARY PRIVATE USE AREA-A", "SUPPLEMENTARYPRIVATEUSEAREA-A" });
            SUPPLEMENTARY_PRIVATE_USE_AREA_B = new UnicodeBlock("SUPPLEMENTARY_PRIVATE_USE_AREA_B", new String[] { "SUPPLEMENTARY PRIVATE USE AREA-B", "SUPPLEMENTARYPRIVATEUSEAREA-B" });
            HIGH_SURROGATES = new UnicodeBlock("HIGH_SURROGATES", new String[] { "HIGH SURROGATES", "HIGHSURROGATES" });
            HIGH_PRIVATE_USE_SURROGATES = new UnicodeBlock("HIGH_PRIVATE_USE_SURROGATES", new String[] { "HIGH PRIVATE USE SURROGATES", "HIGHPRIVATEUSESURROGATES" });
            LOW_SURROGATES = new UnicodeBlock("LOW_SURROGATES", new String[] { "LOW SURROGATES", "LOWSURROGATES" });
            ARABIC_SUPPLEMENT = new UnicodeBlock("ARABIC_SUPPLEMENT", new String[] { "ARABIC SUPPLEMENT", "ARABICSUPPLEMENT" });
            NKO = new UnicodeBlock("NKO");
            SAMARITAN = new UnicodeBlock("SAMARITAN");
            MANDAIC = new UnicodeBlock("MANDAIC");
            ETHIOPIC_SUPPLEMENT = new UnicodeBlock("ETHIOPIC_SUPPLEMENT", new String[] { "ETHIOPIC SUPPLEMENT", "ETHIOPICSUPPLEMENT" });
            UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED = new UnicodeBlock("UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED", new String[] { "UNIFIED CANADIAN ABORIGINAL SYLLABICS EXTENDED", "UNIFIEDCANADIANABORIGINALSYLLABICSEXTENDED" });
            NEW_TAI_LUE = new UnicodeBlock("NEW_TAI_LUE", new String[] { "NEW TAI LUE", "NEWTAILUE" });
            BUGINESE = new UnicodeBlock("BUGINESE");
            TAI_THAM = new UnicodeBlock("TAI_THAM", new String[] { "TAI THAM", "TAITHAM" });
            BALINESE = new UnicodeBlock("BALINESE");
            SUNDANESE = new UnicodeBlock("SUNDANESE");
            BATAK = new UnicodeBlock("BATAK");
            LEPCHA = new UnicodeBlock("LEPCHA");
            OL_CHIKI = new UnicodeBlock("OL_CHIKI", new String[] { "OL CHIKI", "OLCHIKI" });
            VEDIC_EXTENSIONS = new UnicodeBlock("VEDIC_EXTENSIONS", new String[] { "VEDIC EXTENSIONS", "VEDICEXTENSIONS" });
            PHONETIC_EXTENSIONS_SUPPLEMENT = new UnicodeBlock("PHONETIC_EXTENSIONS_SUPPLEMENT", new String[] { "PHONETIC EXTENSIONS SUPPLEMENT", "PHONETICEXTENSIONSSUPPLEMENT" });
            COMBINING_DIACRITICAL_MARKS_SUPPLEMENT = new UnicodeBlock("COMBINING_DIACRITICAL_MARKS_SUPPLEMENT", new String[] { "COMBINING DIACRITICAL MARKS SUPPLEMENT", "COMBININGDIACRITICALMARKSSUPPLEMENT" });
            GLAGOLITIC = new UnicodeBlock("GLAGOLITIC");
            LATIN_EXTENDED_C = new UnicodeBlock("LATIN_EXTENDED_C", new String[] { "LATIN EXTENDED-C", "LATINEXTENDED-C" });
            COPTIC = new UnicodeBlock("COPTIC");
            GEORGIAN_SUPPLEMENT = new UnicodeBlock("GEORGIAN_SUPPLEMENT", new String[] { "GEORGIAN SUPPLEMENT", "GEORGIANSUPPLEMENT" });
            TIFINAGH = new UnicodeBlock("TIFINAGH");
            ETHIOPIC_EXTENDED = new UnicodeBlock("ETHIOPIC_EXTENDED", new String[] { "ETHIOPIC EXTENDED", "ETHIOPICEXTENDED" });
            CYRILLIC_EXTENDED_A = new UnicodeBlock("CYRILLIC_EXTENDED_A", new String[] { "CYRILLIC EXTENDED-A", "CYRILLICEXTENDED-A" });
            SUPPLEMENTAL_PUNCTUATION = new UnicodeBlock("SUPPLEMENTAL_PUNCTUATION", new String[] { "SUPPLEMENTAL PUNCTUATION", "SUPPLEMENTALPUNCTUATION" });
            CJK_STROKES = new UnicodeBlock("CJK_STROKES", new String[] { "CJK STROKES", "CJKSTROKES" });
            LISU = new UnicodeBlock("LISU");
            VAI = new UnicodeBlock("VAI");
            CYRILLIC_EXTENDED_B = new UnicodeBlock("CYRILLIC_EXTENDED_B", new String[] { "CYRILLIC EXTENDED-B", "CYRILLICEXTENDED-B" });
            BAMUM = new UnicodeBlock("BAMUM");
            MODIFIER_TONE_LETTERS = new UnicodeBlock("MODIFIER_TONE_LETTERS", new String[] { "MODIFIER TONE LETTERS", "MODIFIERTONELETTERS" });
            LATIN_EXTENDED_D = new UnicodeBlock("LATIN_EXTENDED_D", new String[] { "LATIN EXTENDED-D", "LATINEXTENDED-D" });
            SYLOTI_NAGRI = new UnicodeBlock("SYLOTI_NAGRI", new String[] { "SYLOTI NAGRI", "SYLOTINAGRI" });
            COMMON_INDIC_NUMBER_FORMS = new UnicodeBlock("COMMON_INDIC_NUMBER_FORMS", new String[] { "COMMON INDIC NUMBER FORMS", "COMMONINDICNUMBERFORMS" });
            PHAGS_PA = new UnicodeBlock("PHAGS_PA", "PHAGS-PA");
            SAURASHTRA = new UnicodeBlock("SAURASHTRA");
            DEVANAGARI_EXTENDED = new UnicodeBlock("DEVANAGARI_EXTENDED", new String[] { "DEVANAGARI EXTENDED", "DEVANAGARIEXTENDED" });
            KAYAH_LI = new UnicodeBlock("KAYAH_LI", new String[] { "KAYAH LI", "KAYAHLI" });
            REJANG = new UnicodeBlock("REJANG");
            HANGUL_JAMO_EXTENDED_A = new UnicodeBlock("HANGUL_JAMO_EXTENDED_A", new String[] { "HANGUL JAMO EXTENDED-A", "HANGULJAMOEXTENDED-A" });
            JAVANESE = new UnicodeBlock("JAVANESE");
            CHAM = new UnicodeBlock("CHAM");
            MYANMAR_EXTENDED_A = new UnicodeBlock("MYANMAR_EXTENDED_A", new String[] { "MYANMAR EXTENDED-A", "MYANMAREXTENDED-A" });
            TAI_VIET = new UnicodeBlock("TAI_VIET", new String[] { "TAI VIET", "TAIVIET" });
            ETHIOPIC_EXTENDED_A = new UnicodeBlock("ETHIOPIC_EXTENDED_A", new String[] { "ETHIOPIC EXTENDED-A", "ETHIOPICEXTENDED-A" });
            MEETEI_MAYEK = new UnicodeBlock("MEETEI_MAYEK", new String[] { "MEETEI MAYEK", "MEETEIMAYEK" });
            HANGUL_JAMO_EXTENDED_B = new UnicodeBlock("HANGUL_JAMO_EXTENDED_B", new String[] { "HANGUL JAMO EXTENDED-B", "HANGULJAMOEXTENDED-B" });
            VERTICAL_FORMS = new UnicodeBlock("VERTICAL_FORMS", new String[] { "VERTICAL FORMS", "VERTICALFORMS" });
            ANCIENT_GREEK_NUMBERS = new UnicodeBlock("ANCIENT_GREEK_NUMBERS", new String[] { "ANCIENT GREEK NUMBERS", "ANCIENTGREEKNUMBERS" });
            ANCIENT_SYMBOLS = new UnicodeBlock("ANCIENT_SYMBOLS", new String[] { "ANCIENT SYMBOLS", "ANCIENTSYMBOLS" });
            PHAISTOS_DISC = new UnicodeBlock("PHAISTOS_DISC", new String[] { "PHAISTOS DISC", "PHAISTOSDISC" });
            LYCIAN = new UnicodeBlock("LYCIAN");
            CARIAN = new UnicodeBlock("CARIAN");
            OLD_PERSIAN = new UnicodeBlock("OLD_PERSIAN", new String[] { "OLD PERSIAN", "OLDPERSIAN" });
            IMPERIAL_ARAMAIC = new UnicodeBlock("IMPERIAL_ARAMAIC", new String[] { "IMPERIAL ARAMAIC", "IMPERIALARAMAIC" });
            PHOENICIAN = new UnicodeBlock("PHOENICIAN");
            LYDIAN = new UnicodeBlock("LYDIAN");
            KHAROSHTHI = new UnicodeBlock("KHAROSHTHI");
            OLD_SOUTH_ARABIAN = new UnicodeBlock("OLD_SOUTH_ARABIAN", new String[] { "OLD SOUTH ARABIAN", "OLDSOUTHARABIAN" });
            AVESTAN = new UnicodeBlock("AVESTAN");
            INSCRIPTIONAL_PARTHIAN = new UnicodeBlock("INSCRIPTIONAL_PARTHIAN", new String[] { "INSCRIPTIONAL PARTHIAN", "INSCRIPTIONALPARTHIAN" });
            INSCRIPTIONAL_PAHLAVI = new UnicodeBlock("INSCRIPTIONAL_PAHLAVI", new String[] { "INSCRIPTIONAL PAHLAVI", "INSCRIPTIONALPAHLAVI" });
            OLD_TURKIC = new UnicodeBlock("OLD_TURKIC", new String[] { "OLD TURKIC", "OLDTURKIC" });
            RUMI_NUMERAL_SYMBOLS = new UnicodeBlock("RUMI_NUMERAL_SYMBOLS", new String[] { "RUMI NUMERAL SYMBOLS", "RUMINUMERALSYMBOLS" });
            BRAHMI = new UnicodeBlock("BRAHMI");
            KAITHI = new UnicodeBlock("KAITHI");
            CUNEIFORM = new UnicodeBlock("CUNEIFORM");
            CUNEIFORM_NUMBERS_AND_PUNCTUATION = new UnicodeBlock("CUNEIFORM_NUMBERS_AND_PUNCTUATION", new String[] { "CUNEIFORM NUMBERS AND PUNCTUATION", "CUNEIFORMNUMBERSANDPUNCTUATION" });
            EGYPTIAN_HIEROGLYPHS = new UnicodeBlock("EGYPTIAN_HIEROGLYPHS", new String[] { "EGYPTIAN HIEROGLYPHS", "EGYPTIANHIEROGLYPHS" });
            BAMUM_SUPPLEMENT = new UnicodeBlock("BAMUM_SUPPLEMENT", new String[] { "BAMUM SUPPLEMENT", "BAMUMSUPPLEMENT" });
            KANA_SUPPLEMENT = new UnicodeBlock("KANA_SUPPLEMENT", new String[] { "KANA SUPPLEMENT", "KANASUPPLEMENT" });
            ANCIENT_GREEK_MUSICAL_NOTATION = new UnicodeBlock("ANCIENT_GREEK_MUSICAL_NOTATION", new String[] { "ANCIENT GREEK MUSICAL NOTATION", "ANCIENTGREEKMUSICALNOTATION" });
            COUNTING_ROD_NUMERALS = new UnicodeBlock("COUNTING_ROD_NUMERALS", new String[] { "COUNTING ROD NUMERALS", "COUNTINGRODNUMERALS" });
            MAHJONG_TILES = new UnicodeBlock("MAHJONG_TILES", new String[] { "MAHJONG TILES", "MAHJONGTILES" });
            DOMINO_TILES = new UnicodeBlock("DOMINO_TILES", new String[] { "DOMINO TILES", "DOMINOTILES" });
            PLAYING_CARDS = new UnicodeBlock("PLAYING_CARDS", new String[] { "PLAYING CARDS", "PLAYINGCARDS" });
            ENCLOSED_ALPHANUMERIC_SUPPLEMENT = new UnicodeBlock("ENCLOSED_ALPHANUMERIC_SUPPLEMENT", new String[] { "ENCLOSED ALPHANUMERIC SUPPLEMENT", "ENCLOSEDALPHANUMERICSUPPLEMENT" });
            ENCLOSED_IDEOGRAPHIC_SUPPLEMENT = new UnicodeBlock("ENCLOSED_IDEOGRAPHIC_SUPPLEMENT", new String[] { "ENCLOSED IDEOGRAPHIC SUPPLEMENT", "ENCLOSEDIDEOGRAPHICSUPPLEMENT" });
            MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS = new UnicodeBlock("MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS", new String[] { "MISCELLANEOUS SYMBOLS AND PICTOGRAPHS", "MISCELLANEOUSSYMBOLSANDPICTOGRAPHS" });
            EMOTICONS = new UnicodeBlock("EMOTICONS");
            TRANSPORT_AND_MAP_SYMBOLS = new UnicodeBlock("TRANSPORT_AND_MAP_SYMBOLS", new String[] { "TRANSPORT AND MAP SYMBOLS", "TRANSPORTANDMAPSYMBOLS" });
            ALCHEMICAL_SYMBOLS = new UnicodeBlock("ALCHEMICAL_SYMBOLS", new String[] { "ALCHEMICAL SYMBOLS", "ALCHEMICALSYMBOLS" });
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C", new String[] { "CJK UNIFIED IDEOGRAPHS EXTENSION C", "CJKUNIFIEDIDEOGRAPHSEXTENSIONC" });
            CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D = new UnicodeBlock("CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D", new String[] { "CJK UNIFIED IDEOGRAPHS EXTENSION D", "CJKUNIFIEDIDEOGRAPHSEXTENSIOND" });
            ARABIC_EXTENDED_A = new UnicodeBlock("ARABIC_EXTENDED_A", new String[] { "ARABIC EXTENDED-A", "ARABICEXTENDED-A" });
            SUNDANESE_SUPPLEMENT = new UnicodeBlock("SUNDANESE_SUPPLEMENT", new String[] { "SUNDANESE SUPPLEMENT", "SUNDANESESUPPLEMENT" });
            MEETEI_MAYEK_EXTENSIONS = new UnicodeBlock("MEETEI_MAYEK_EXTENSIONS", new String[] { "MEETEI MAYEK EXTENSIONS", "MEETEIMAYEKEXTENSIONS" });
            MEROITIC_HIEROGLYPHS = new UnicodeBlock("MEROITIC_HIEROGLYPHS", new String[] { "MEROITIC HIEROGLYPHS", "MEROITICHIEROGLYPHS" });
            MEROITIC_CURSIVE = new UnicodeBlock("MEROITIC_CURSIVE", new String[] { "MEROITIC CURSIVE", "MEROITICCURSIVE" });
            SORA_SOMPENG = new UnicodeBlock("SORA_SOMPENG", new String[] { "SORA SOMPENG", "SORASOMPENG" });
            CHAKMA = new UnicodeBlock("CHAKMA");
            SHARADA = new UnicodeBlock("SHARADA");
            TAKRI = new UnicodeBlock("TAKRI");
            MIAO = new UnicodeBlock("MIAO");
            ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS = new UnicodeBlock("ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS", new String[] { "ARABIC MATHEMATICAL ALPHABETIC SYMBOLS", "ARABICMATHEMATICALALPHABETICSYMBOLS" });
            blockStarts = new int[] { 0, 128, 256, 384, 592, 688, 768, 880, 1024, 1280, 1328, 1424, 1536, 1792, 1872, 1920, 1984, 2048, 2112, 2144, 2208, 2304, 2432, 2560, 2688, 2816, 2944, 3072, 3200, 3328, 3456, 3584, 3712, 3840, 4096, 4256, 4352, 4608, 4992, 5024, 5120, 5760, 5792, 5888, 5920, 5952, 5984, 6016, 6144, 6320, 6400, 6480, 6528, 6624, 6656, 6688, 6832, 6912, 7040, 7104, 7168, 7248, 7296, 7360, 7376, 7424, 7552, 7616, 7680, 7936, 8192, 8304, 8352, 8400, 8448, 8528, 8592, 8704, 8960, 9216, 9280, 9312, 9472, 9600, 9632, 9728, 9984, 10176, 10224, 10240, 10496, 10624, 10752, 11008, 11264, 11360, 11392, 11520, 11568, 11648, 11744, 11776, 11904, 12032, 12256, 12272, 12288, 12352, 12448, 12544, 12592, 12688, 12704, 12736, 12784, 12800, 13056, 13312, 19904, 19968, 40960, 42128, 42192, 42240, 42560, 42656, 42752, 42784, 43008, 43056, 43072, 43136, 43232, 43264, 43312, 43360, 43392, 43488, 43520, 43616, 43648, 43744, 43776, 43824, 43968, 44032, 55216, 55296, 56192, 56320, 57344, 63744, 64256, 64336, 65024, 65040, 65056, 65072, 65104, 65136, 65280, 65520, 65536, 65664, 65792, 65856, 65936, 66000, 66048, 66176, 66208, 66272, 66304, 66352, 66384, 66432, 66464, 66528, 66560, 66640, 66688, 66736, 67584, 67648, 67680, 67840, 67872, 67904, 67968, 68000, 68096, 68192, 68224, 68352, 68416, 68448, 68480, 68608, 68688, 69216, 69248, 69632, 69760, 69840, 69888, 69968, 70016, 70112, 71296, 71376, 73728, 74752, 74880, 77824, 78896, 92160, 92736, 93952, 94112, 110592, 110848, 118784, 119040, 119296, 119376, 119552, 119648, 119680, 119808, 120832, 126464, 126720, 126976, 127024, 127136, 127232, 127488, 127744, 128512, 128592, 128640, 128768, 128896, 131072, 173792, 173824, 177984, 178208, 194560, 195104, 917504, 917632, 917760, 918000, 983040, 1048576 };
            blocks = new UnicodeBlock[] { UnicodeBlock.BASIC_LATIN, UnicodeBlock.LATIN_1_SUPPLEMENT, UnicodeBlock.LATIN_EXTENDED_A, UnicodeBlock.LATIN_EXTENDED_B, UnicodeBlock.IPA_EXTENSIONS, UnicodeBlock.SPACING_MODIFIER_LETTERS, UnicodeBlock.COMBINING_DIACRITICAL_MARKS, UnicodeBlock.GREEK, UnicodeBlock.CYRILLIC, UnicodeBlock.CYRILLIC_SUPPLEMENTARY, UnicodeBlock.ARMENIAN, UnicodeBlock.HEBREW, UnicodeBlock.ARABIC, UnicodeBlock.SYRIAC, UnicodeBlock.ARABIC_SUPPLEMENT, UnicodeBlock.THAANA, UnicodeBlock.NKO, UnicodeBlock.SAMARITAN, UnicodeBlock.MANDAIC, null, UnicodeBlock.ARABIC_EXTENDED_A, UnicodeBlock.DEVANAGARI, UnicodeBlock.BENGALI, UnicodeBlock.GURMUKHI, UnicodeBlock.GUJARATI, UnicodeBlock.ORIYA, UnicodeBlock.TAMIL, UnicodeBlock.TELUGU, UnicodeBlock.KANNADA, UnicodeBlock.MALAYALAM, UnicodeBlock.SINHALA, UnicodeBlock.THAI, UnicodeBlock.LAO, UnicodeBlock.TIBETAN, UnicodeBlock.MYANMAR, UnicodeBlock.GEORGIAN, UnicodeBlock.HANGUL_JAMO, UnicodeBlock.ETHIOPIC, UnicodeBlock.ETHIOPIC_SUPPLEMENT, UnicodeBlock.CHEROKEE, UnicodeBlock.UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS, UnicodeBlock.OGHAM, UnicodeBlock.RUNIC, UnicodeBlock.TAGALOG, UnicodeBlock.HANUNOO, UnicodeBlock.BUHID, UnicodeBlock.TAGBANWA, UnicodeBlock.KHMER, UnicodeBlock.MONGOLIAN, UnicodeBlock.UNIFIED_CANADIAN_ABORIGINAL_SYLLABICS_EXTENDED, UnicodeBlock.LIMBU, UnicodeBlock.TAI_LE, UnicodeBlock.NEW_TAI_LUE, UnicodeBlock.KHMER_SYMBOLS, UnicodeBlock.BUGINESE, UnicodeBlock.TAI_THAM, null, UnicodeBlock.BALINESE, UnicodeBlock.SUNDANESE, UnicodeBlock.BATAK, UnicodeBlock.LEPCHA, UnicodeBlock.OL_CHIKI, null, UnicodeBlock.SUNDANESE_SUPPLEMENT, UnicodeBlock.VEDIC_EXTENSIONS, UnicodeBlock.PHONETIC_EXTENSIONS, UnicodeBlock.PHONETIC_EXTENSIONS_SUPPLEMENT, UnicodeBlock.COMBINING_DIACRITICAL_MARKS_SUPPLEMENT, UnicodeBlock.LATIN_EXTENDED_ADDITIONAL, UnicodeBlock.GREEK_EXTENDED, UnicodeBlock.GENERAL_PUNCTUATION, UnicodeBlock.SUPERSCRIPTS_AND_SUBSCRIPTS, UnicodeBlock.CURRENCY_SYMBOLS, UnicodeBlock.COMBINING_MARKS_FOR_SYMBOLS, UnicodeBlock.LETTERLIKE_SYMBOLS, UnicodeBlock.NUMBER_FORMS, UnicodeBlock.ARROWS, UnicodeBlock.MATHEMATICAL_OPERATORS, UnicodeBlock.MISCELLANEOUS_TECHNICAL, UnicodeBlock.CONTROL_PICTURES, UnicodeBlock.OPTICAL_CHARACTER_RECOGNITION, UnicodeBlock.ENCLOSED_ALPHANUMERICS, UnicodeBlock.BOX_DRAWING, UnicodeBlock.BLOCK_ELEMENTS, UnicodeBlock.GEOMETRIC_SHAPES, UnicodeBlock.MISCELLANEOUS_SYMBOLS, UnicodeBlock.DINGBATS, UnicodeBlock.MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A, UnicodeBlock.SUPPLEMENTAL_ARROWS_A, UnicodeBlock.BRAILLE_PATTERNS, UnicodeBlock.SUPPLEMENTAL_ARROWS_B, UnicodeBlock.MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B, UnicodeBlock.SUPPLEMENTAL_MATHEMATICAL_OPERATORS, UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_ARROWS, UnicodeBlock.GLAGOLITIC, UnicodeBlock.LATIN_EXTENDED_C, UnicodeBlock.COPTIC, UnicodeBlock.GEORGIAN_SUPPLEMENT, UnicodeBlock.TIFINAGH, UnicodeBlock.ETHIOPIC_EXTENDED, UnicodeBlock.CYRILLIC_EXTENDED_A, UnicodeBlock.SUPPLEMENTAL_PUNCTUATION, UnicodeBlock.CJK_RADICALS_SUPPLEMENT, UnicodeBlock.KANGXI_RADICALS, null, UnicodeBlock.IDEOGRAPHIC_DESCRIPTION_CHARACTERS, UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION, UnicodeBlock.HIRAGANA, UnicodeBlock.KATAKANA, UnicodeBlock.BOPOMOFO, UnicodeBlock.HANGUL_COMPATIBILITY_JAMO, UnicodeBlock.KANBUN, UnicodeBlock.BOPOMOFO_EXTENDED, UnicodeBlock.CJK_STROKES, UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS, UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS, UnicodeBlock.CJK_COMPATIBILITY, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A, UnicodeBlock.YIJING_HEXAGRAM_SYMBOLS, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS, UnicodeBlock.YI_SYLLABLES, UnicodeBlock.YI_RADICALS, UnicodeBlock.LISU, UnicodeBlock.VAI, UnicodeBlock.CYRILLIC_EXTENDED_B, UnicodeBlock.BAMUM, UnicodeBlock.MODIFIER_TONE_LETTERS, UnicodeBlock.LATIN_EXTENDED_D, UnicodeBlock.SYLOTI_NAGRI, UnicodeBlock.COMMON_INDIC_NUMBER_FORMS, UnicodeBlock.PHAGS_PA, UnicodeBlock.SAURASHTRA, UnicodeBlock.DEVANAGARI_EXTENDED, UnicodeBlock.KAYAH_LI, UnicodeBlock.REJANG, UnicodeBlock.HANGUL_JAMO_EXTENDED_A, UnicodeBlock.JAVANESE, null, UnicodeBlock.CHAM, UnicodeBlock.MYANMAR_EXTENDED_A, UnicodeBlock.TAI_VIET, UnicodeBlock.MEETEI_MAYEK_EXTENSIONS, UnicodeBlock.ETHIOPIC_EXTENDED_A, null, UnicodeBlock.MEETEI_MAYEK, UnicodeBlock.HANGUL_SYLLABLES, UnicodeBlock.HANGUL_JAMO_EXTENDED_B, UnicodeBlock.HIGH_SURROGATES, UnicodeBlock.HIGH_PRIVATE_USE_SURROGATES, UnicodeBlock.LOW_SURROGATES, UnicodeBlock.PRIVATE_USE_AREA, UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS, UnicodeBlock.ALPHABETIC_PRESENTATION_FORMS, UnicodeBlock.ARABIC_PRESENTATION_FORMS_A, UnicodeBlock.VARIATION_SELECTORS, UnicodeBlock.VERTICAL_FORMS, UnicodeBlock.COMBINING_HALF_MARKS, UnicodeBlock.CJK_COMPATIBILITY_FORMS, UnicodeBlock.SMALL_FORM_VARIANTS, UnicodeBlock.ARABIC_PRESENTATION_FORMS_B, UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS, UnicodeBlock.SPECIALS, UnicodeBlock.LINEAR_B_SYLLABARY, UnicodeBlock.LINEAR_B_IDEOGRAMS, UnicodeBlock.AEGEAN_NUMBERS, UnicodeBlock.ANCIENT_GREEK_NUMBERS, UnicodeBlock.ANCIENT_SYMBOLS, UnicodeBlock.PHAISTOS_DISC, null, UnicodeBlock.LYCIAN, UnicodeBlock.CARIAN, null, UnicodeBlock.OLD_ITALIC, UnicodeBlock.GOTHIC, null, UnicodeBlock.UGARITIC, UnicodeBlock.OLD_PERSIAN, null, UnicodeBlock.DESERET, UnicodeBlock.SHAVIAN, UnicodeBlock.OSMANYA, null, UnicodeBlock.CYPRIOT_SYLLABARY, UnicodeBlock.IMPERIAL_ARAMAIC, null, UnicodeBlock.PHOENICIAN, UnicodeBlock.LYDIAN, null, UnicodeBlock.MEROITIC_HIEROGLYPHS, UnicodeBlock.MEROITIC_CURSIVE, UnicodeBlock.KHAROSHTHI, UnicodeBlock.OLD_SOUTH_ARABIAN, null, UnicodeBlock.AVESTAN, UnicodeBlock.INSCRIPTIONAL_PARTHIAN, UnicodeBlock.INSCRIPTIONAL_PAHLAVI, null, UnicodeBlock.OLD_TURKIC, null, UnicodeBlock.RUMI_NUMERAL_SYMBOLS, null, UnicodeBlock.BRAHMI, UnicodeBlock.KAITHI, UnicodeBlock.SORA_SOMPENG, UnicodeBlock.CHAKMA, null, UnicodeBlock.SHARADA, null, UnicodeBlock.TAKRI, null, UnicodeBlock.CUNEIFORM, UnicodeBlock.CUNEIFORM_NUMBERS_AND_PUNCTUATION, null, UnicodeBlock.EGYPTIAN_HIEROGLYPHS, null, UnicodeBlock.BAMUM_SUPPLEMENT, null, UnicodeBlock.MIAO, null, UnicodeBlock.KANA_SUPPLEMENT, null, UnicodeBlock.BYZANTINE_MUSICAL_SYMBOLS, UnicodeBlock.MUSICAL_SYMBOLS, UnicodeBlock.ANCIENT_GREEK_MUSICAL_NOTATION, null, UnicodeBlock.TAI_XUAN_JING_SYMBOLS, UnicodeBlock.COUNTING_ROD_NUMERALS, null, UnicodeBlock.MATHEMATICAL_ALPHANUMERIC_SYMBOLS, null, UnicodeBlock.ARABIC_MATHEMATICAL_ALPHABETIC_SYMBOLS, null, UnicodeBlock.MAHJONG_TILES, UnicodeBlock.DOMINO_TILES, UnicodeBlock.PLAYING_CARDS, UnicodeBlock.ENCLOSED_ALPHANUMERIC_SUPPLEMENT, UnicodeBlock.ENCLOSED_IDEOGRAPHIC_SUPPLEMENT, UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS, UnicodeBlock.EMOTICONS, null, UnicodeBlock.TRANSPORT_AND_MAP_SYMBOLS, UnicodeBlock.ALCHEMICAL_SYMBOLS, null, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B, null, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C, UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D, null, UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT, null, UnicodeBlock.TAGS, null, UnicodeBlock.VARIATION_SELECTORS_SUPPLEMENT, null, UnicodeBlock.SUPPLEMENTARY_PRIVATE_USE_AREA_A, UnicodeBlock.SUPPLEMENTARY_PRIVATE_USE_AREA_B };
        }
    }
    
    public enum UnicodeScript
    {
        COMMON, 
        LATIN, 
        GREEK, 
        CYRILLIC, 
        ARMENIAN, 
        HEBREW, 
        ARABIC, 
        SYRIAC, 
        THAANA, 
        DEVANAGARI, 
        BENGALI, 
        GURMUKHI, 
        GUJARATI, 
        ORIYA, 
        TAMIL, 
        TELUGU, 
        KANNADA, 
        MALAYALAM, 
        SINHALA, 
        THAI, 
        LAO, 
        TIBETAN, 
        MYANMAR, 
        GEORGIAN, 
        HANGUL, 
        ETHIOPIC, 
        CHEROKEE, 
        CANADIAN_ABORIGINAL, 
        OGHAM, 
        RUNIC, 
        KHMER, 
        MONGOLIAN, 
        HIRAGANA, 
        KATAKANA, 
        BOPOMOFO, 
        HAN, 
        YI, 
        OLD_ITALIC, 
        GOTHIC, 
        DESERET, 
        INHERITED, 
        TAGALOG, 
        HANUNOO, 
        BUHID, 
        TAGBANWA, 
        LIMBU, 
        TAI_LE, 
        LINEAR_B, 
        UGARITIC, 
        SHAVIAN, 
        OSMANYA, 
        CYPRIOT, 
        BRAILLE, 
        BUGINESE, 
        COPTIC, 
        NEW_TAI_LUE, 
        GLAGOLITIC, 
        TIFINAGH, 
        SYLOTI_NAGRI, 
        OLD_PERSIAN, 
        KHAROSHTHI, 
        BALINESE, 
        CUNEIFORM, 
        PHOENICIAN, 
        PHAGS_PA, 
        NKO, 
        SUNDANESE, 
        BATAK, 
        LEPCHA, 
        OL_CHIKI, 
        VAI, 
        SAURASHTRA, 
        KAYAH_LI, 
        REJANG, 
        LYCIAN, 
        CARIAN, 
        LYDIAN, 
        CHAM, 
        TAI_THAM, 
        TAI_VIET, 
        AVESTAN, 
        EGYPTIAN_HIEROGLYPHS, 
        SAMARITAN, 
        MANDAIC, 
        LISU, 
        BAMUM, 
        JAVANESE, 
        MEETEI_MAYEK, 
        IMPERIAL_ARAMAIC, 
        OLD_SOUTH_ARABIAN, 
        INSCRIPTIONAL_PARTHIAN, 
        INSCRIPTIONAL_PAHLAVI, 
        OLD_TURKIC, 
        BRAHMI, 
        KAITHI, 
        MEROITIC_HIEROGLYPHS, 
        MEROITIC_CURSIVE, 
        SORA_SOMPENG, 
        CHAKMA, 
        SHARADA, 
        TAKRI, 
        MIAO, 
        UNKNOWN;
        
        private static final int[] scriptStarts;
        private static final UnicodeScript[] scripts;
        private static HashMap<String, UnicodeScript> aliases;
        
        public static UnicodeScript of(final int n) {
            if (!Character.isValidCodePoint(n)) {
                throw new IllegalArgumentException();
            }
            if (Character.getType(n) == 0) {
                return UnicodeScript.UNKNOWN;
            }
            int binarySearch = Arrays.binarySearch(UnicodeScript.scriptStarts, n);
            if (binarySearch < 0) {
                binarySearch = -binarySearch - 2;
            }
            return UnicodeScript.scripts[binarySearch];
        }
        
        public static final UnicodeScript forName(String upperCase) {
            upperCase = upperCase.toUpperCase(Locale.ENGLISH);
            final UnicodeScript unicodeScript = UnicodeScript.aliases.get(upperCase);
            if (unicodeScript != null) {
                return unicodeScript;
            }
            return valueOf(upperCase);
        }
        
        static {
            scriptStarts = new int[] { 0, 65, 91, 97, 123, 170, 171, 186, 187, 192, 215, 216, 247, 248, 697, 736, 741, 746, 748, 768, 880, 884, 885, 894, 900, 901, 902, 903, 904, 994, 1008, 1024, 1157, 1159, 1329, 1417, 1418, 1425, 1536, 1548, 1549, 1563, 1566, 1567, 1568, 1600, 1601, 1611, 1622, 1632, 1642, 1648, 1649, 1757, 1758, 1792, 1872, 1920, 1984, 2048, 2112, 2208, 2304, 2385, 2387, 2404, 2406, 2433, 2561, 2689, 2817, 2946, 3073, 3202, 3330, 3458, 3585, 3647, 3648, 3713, 3840, 4053, 4057, 4096, 4256, 4347, 4348, 4352, 4608, 5024, 5120, 5760, 5792, 5867, 5870, 5888, 5920, 5941, 5952, 5984, 6016, 6144, 6146, 6148, 6149, 6150, 6320, 6400, 6480, 6528, 6624, 6656, 6688, 6912, 7040, 7104, 7168, 7248, 7360, 7376, 7379, 7380, 7393, 7394, 7401, 7405, 7406, 7412, 7413, 7424, 7462, 7467, 7468, 7517, 7522, 7526, 7531, 7544, 7545, 7615, 7616, 7680, 7936, 8192, 8204, 8206, 8305, 8308, 8319, 8320, 8336, 8352, 8400, 8448, 8486, 8487, 8490, 8492, 8498, 8499, 8526, 8527, 8544, 8585, 10240, 10496, 11264, 11360, 11392, 11520, 11568, 11648, 11744, 11776, 11904, 12272, 12293, 12294, 12295, 12296, 12321, 12330, 12334, 12336, 12344, 12348, 12353, 12441, 12443, 12445, 12448, 12449, 12539, 12541, 12549, 12593, 12688, 12704, 12736, 12784, 12800, 12832, 12896, 12927, 13008, 13055, 13056, 13144, 13312, 19904, 19968, 40960, 42192, 42240, 42560, 42656, 42752, 42786, 42888, 42891, 43008, 43056, 43072, 43136, 43232, 43264, 43312, 43360, 43392, 43520, 43616, 43648, 43744, 43777, 43968, 44032, 55292, 63744, 64256, 64275, 64285, 64336, 64830, 64848, 65021, 65024, 65040, 65056, 65072, 65136, 65279, 65313, 65339, 65345, 65371, 65382, 65392, 65393, 65438, 65440, 65504, 65536, 65792, 65856, 65936, 66045, 66176, 66208, 66304, 66352, 66432, 66464, 66560, 66640, 66688, 67584, 67648, 67840, 67872, 67968, 68000, 68096, 68192, 68352, 68416, 68448, 68608, 69216, 69632, 69760, 69840, 69888, 70016, 71296, 73728, 77824, 92160, 93952, 110592, 110593, 118784, 119143, 119146, 119163, 119171, 119173, 119180, 119210, 119214, 119296, 119552, 126464, 126976, 127488, 127489, 131072, 917505, 917760, 918000 };
            scripts = new UnicodeScript[] { UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.BOPOMOFO, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.GREEK, UnicodeScript.COMMON, UnicodeScript.GREEK, UnicodeScript.COMMON, UnicodeScript.GREEK, UnicodeScript.COMMON, UnicodeScript.GREEK, UnicodeScript.COMMON, UnicodeScript.GREEK, UnicodeScript.COPTIC, UnicodeScript.GREEK, UnicodeScript.CYRILLIC, UnicodeScript.INHERITED, UnicodeScript.CYRILLIC, UnicodeScript.ARMENIAN, UnicodeScript.COMMON, UnicodeScript.ARMENIAN, UnicodeScript.HEBREW, UnicodeScript.ARABIC, UnicodeScript.COMMON, UnicodeScript.ARABIC, UnicodeScript.COMMON, UnicodeScript.ARABIC, UnicodeScript.COMMON, UnicodeScript.ARABIC, UnicodeScript.COMMON, UnicodeScript.ARABIC, UnicodeScript.INHERITED, UnicodeScript.ARABIC, UnicodeScript.COMMON, UnicodeScript.ARABIC, UnicodeScript.INHERITED, UnicodeScript.ARABIC, UnicodeScript.COMMON, UnicodeScript.ARABIC, UnicodeScript.SYRIAC, UnicodeScript.ARABIC, UnicodeScript.THAANA, UnicodeScript.NKO, UnicodeScript.SAMARITAN, UnicodeScript.MANDAIC, UnicodeScript.ARABIC, UnicodeScript.DEVANAGARI, UnicodeScript.INHERITED, UnicodeScript.DEVANAGARI, UnicodeScript.COMMON, UnicodeScript.DEVANAGARI, UnicodeScript.BENGALI, UnicodeScript.GURMUKHI, UnicodeScript.GUJARATI, UnicodeScript.ORIYA, UnicodeScript.TAMIL, UnicodeScript.TELUGU, UnicodeScript.KANNADA, UnicodeScript.MALAYALAM, UnicodeScript.SINHALA, UnicodeScript.THAI, UnicodeScript.COMMON, UnicodeScript.THAI, UnicodeScript.LAO, UnicodeScript.TIBETAN, UnicodeScript.COMMON, UnicodeScript.TIBETAN, UnicodeScript.MYANMAR, UnicodeScript.GEORGIAN, UnicodeScript.COMMON, UnicodeScript.GEORGIAN, UnicodeScript.HANGUL, UnicodeScript.ETHIOPIC, UnicodeScript.CHEROKEE, UnicodeScript.CANADIAN_ABORIGINAL, UnicodeScript.OGHAM, UnicodeScript.RUNIC, UnicodeScript.COMMON, UnicodeScript.RUNIC, UnicodeScript.TAGALOG, UnicodeScript.HANUNOO, UnicodeScript.COMMON, UnicodeScript.BUHID, UnicodeScript.TAGBANWA, UnicodeScript.KHMER, UnicodeScript.MONGOLIAN, UnicodeScript.COMMON, UnicodeScript.MONGOLIAN, UnicodeScript.COMMON, UnicodeScript.MONGOLIAN, UnicodeScript.CANADIAN_ABORIGINAL, UnicodeScript.LIMBU, UnicodeScript.TAI_LE, UnicodeScript.NEW_TAI_LUE, UnicodeScript.KHMER, UnicodeScript.BUGINESE, UnicodeScript.TAI_THAM, UnicodeScript.BALINESE, UnicodeScript.SUNDANESE, UnicodeScript.BATAK, UnicodeScript.LEPCHA, UnicodeScript.OL_CHIKI, UnicodeScript.SUNDANESE, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.GREEK, UnicodeScript.CYRILLIC, UnicodeScript.LATIN, UnicodeScript.GREEK, UnicodeScript.LATIN, UnicodeScript.GREEK, UnicodeScript.LATIN, UnicodeScript.CYRILLIC, UnicodeScript.LATIN, UnicodeScript.GREEK, UnicodeScript.INHERITED, UnicodeScript.LATIN, UnicodeScript.GREEK, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.GREEK, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.BRAILLE, UnicodeScript.COMMON, UnicodeScript.GLAGOLITIC, UnicodeScript.LATIN, UnicodeScript.COPTIC, UnicodeScript.GEORGIAN, UnicodeScript.TIFINAGH, UnicodeScript.ETHIOPIC, UnicodeScript.CYRILLIC, UnicodeScript.COMMON, UnicodeScript.HAN, UnicodeScript.COMMON, UnicodeScript.HAN, UnicodeScript.COMMON, UnicodeScript.HAN, UnicodeScript.COMMON, UnicodeScript.HAN, UnicodeScript.INHERITED, UnicodeScript.HANGUL, UnicodeScript.COMMON, UnicodeScript.HAN, UnicodeScript.COMMON, UnicodeScript.HIRAGANA, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.HIRAGANA, UnicodeScript.COMMON, UnicodeScript.KATAKANA, UnicodeScript.COMMON, UnicodeScript.KATAKANA, UnicodeScript.BOPOMOFO, UnicodeScript.HANGUL, UnicodeScript.COMMON, UnicodeScript.BOPOMOFO, UnicodeScript.COMMON, UnicodeScript.KATAKANA, UnicodeScript.HANGUL, UnicodeScript.COMMON, UnicodeScript.HANGUL, UnicodeScript.COMMON, UnicodeScript.KATAKANA, UnicodeScript.COMMON, UnicodeScript.KATAKANA, UnicodeScript.COMMON, UnicodeScript.HAN, UnicodeScript.COMMON, UnicodeScript.HAN, UnicodeScript.YI, UnicodeScript.LISU, UnicodeScript.VAI, UnicodeScript.CYRILLIC, UnicodeScript.BAMUM, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.SYLOTI_NAGRI, UnicodeScript.COMMON, UnicodeScript.PHAGS_PA, UnicodeScript.SAURASHTRA, UnicodeScript.DEVANAGARI, UnicodeScript.KAYAH_LI, UnicodeScript.REJANG, UnicodeScript.HANGUL, UnicodeScript.JAVANESE, UnicodeScript.CHAM, UnicodeScript.MYANMAR, UnicodeScript.TAI_VIET, UnicodeScript.MEETEI_MAYEK, UnicodeScript.ETHIOPIC, UnicodeScript.MEETEI_MAYEK, UnicodeScript.HANGUL, UnicodeScript.UNKNOWN, UnicodeScript.HAN, UnicodeScript.LATIN, UnicodeScript.ARMENIAN, UnicodeScript.HEBREW, UnicodeScript.ARABIC, UnicodeScript.COMMON, UnicodeScript.ARABIC, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.ARABIC, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.LATIN, UnicodeScript.COMMON, UnicodeScript.KATAKANA, UnicodeScript.COMMON, UnicodeScript.KATAKANA, UnicodeScript.COMMON, UnicodeScript.HANGUL, UnicodeScript.COMMON, UnicodeScript.LINEAR_B, UnicodeScript.COMMON, UnicodeScript.GREEK, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.LYCIAN, UnicodeScript.CARIAN, UnicodeScript.OLD_ITALIC, UnicodeScript.GOTHIC, UnicodeScript.UGARITIC, UnicodeScript.OLD_PERSIAN, UnicodeScript.DESERET, UnicodeScript.SHAVIAN, UnicodeScript.OSMANYA, UnicodeScript.CYPRIOT, UnicodeScript.IMPERIAL_ARAMAIC, UnicodeScript.PHOENICIAN, UnicodeScript.LYDIAN, UnicodeScript.MEROITIC_HIEROGLYPHS, UnicodeScript.MEROITIC_CURSIVE, UnicodeScript.KHAROSHTHI, UnicodeScript.OLD_SOUTH_ARABIAN, UnicodeScript.AVESTAN, UnicodeScript.INSCRIPTIONAL_PARTHIAN, UnicodeScript.INSCRIPTIONAL_PAHLAVI, UnicodeScript.OLD_TURKIC, UnicodeScript.ARABIC, UnicodeScript.BRAHMI, UnicodeScript.KAITHI, UnicodeScript.SORA_SOMPENG, UnicodeScript.CHAKMA, UnicodeScript.SHARADA, UnicodeScript.TAKRI, UnicodeScript.CUNEIFORM, UnicodeScript.EGYPTIAN_HIEROGLYPHS, UnicodeScript.BAMUM, UnicodeScript.MIAO, UnicodeScript.KATAKANA, UnicodeScript.HIRAGANA, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.COMMON, UnicodeScript.GREEK, UnicodeScript.COMMON, UnicodeScript.ARABIC, UnicodeScript.COMMON, UnicodeScript.HIRAGANA, UnicodeScript.COMMON, UnicodeScript.HAN, UnicodeScript.COMMON, UnicodeScript.INHERITED, UnicodeScript.UNKNOWN };
            (UnicodeScript.aliases = new HashMap<String, UnicodeScript>(128)).put("ARAB", UnicodeScript.ARABIC);
            UnicodeScript.aliases.put("ARMI", UnicodeScript.IMPERIAL_ARAMAIC);
            UnicodeScript.aliases.put("ARMN", UnicodeScript.ARMENIAN);
            UnicodeScript.aliases.put("AVST", UnicodeScript.AVESTAN);
            UnicodeScript.aliases.put("BALI", UnicodeScript.BALINESE);
            UnicodeScript.aliases.put("BAMU", UnicodeScript.BAMUM);
            UnicodeScript.aliases.put("BATK", UnicodeScript.BATAK);
            UnicodeScript.aliases.put("BENG", UnicodeScript.BENGALI);
            UnicodeScript.aliases.put("BOPO", UnicodeScript.BOPOMOFO);
            UnicodeScript.aliases.put("BRAI", UnicodeScript.BRAILLE);
            UnicodeScript.aliases.put("BRAH", UnicodeScript.BRAHMI);
            UnicodeScript.aliases.put("BUGI", UnicodeScript.BUGINESE);
            UnicodeScript.aliases.put("BUHD", UnicodeScript.BUHID);
            UnicodeScript.aliases.put("CAKM", UnicodeScript.CHAKMA);
            UnicodeScript.aliases.put("CANS", UnicodeScript.CANADIAN_ABORIGINAL);
            UnicodeScript.aliases.put("CARI", UnicodeScript.CARIAN);
            UnicodeScript.aliases.put("CHAM", UnicodeScript.CHAM);
            UnicodeScript.aliases.put("CHER", UnicodeScript.CHEROKEE);
            UnicodeScript.aliases.put("COPT", UnicodeScript.COPTIC);
            UnicodeScript.aliases.put("CPRT", UnicodeScript.CYPRIOT);
            UnicodeScript.aliases.put("CYRL", UnicodeScript.CYRILLIC);
            UnicodeScript.aliases.put("DEVA", UnicodeScript.DEVANAGARI);
            UnicodeScript.aliases.put("DSRT", UnicodeScript.DESERET);
            UnicodeScript.aliases.put("EGYP", UnicodeScript.EGYPTIAN_HIEROGLYPHS);
            UnicodeScript.aliases.put("ETHI", UnicodeScript.ETHIOPIC);
            UnicodeScript.aliases.put("GEOR", UnicodeScript.GEORGIAN);
            UnicodeScript.aliases.put("GLAG", UnicodeScript.GLAGOLITIC);
            UnicodeScript.aliases.put("GOTH", UnicodeScript.GOTHIC);
            UnicodeScript.aliases.put("GREK", UnicodeScript.GREEK);
            UnicodeScript.aliases.put("GUJR", UnicodeScript.GUJARATI);
            UnicodeScript.aliases.put("GURU", UnicodeScript.GURMUKHI);
            UnicodeScript.aliases.put("HANG", UnicodeScript.HANGUL);
            UnicodeScript.aliases.put("HANI", UnicodeScript.HAN);
            UnicodeScript.aliases.put("HANO", UnicodeScript.HANUNOO);
            UnicodeScript.aliases.put("HEBR", UnicodeScript.HEBREW);
            UnicodeScript.aliases.put("HIRA", UnicodeScript.HIRAGANA);
            UnicodeScript.aliases.put("ITAL", UnicodeScript.OLD_ITALIC);
            UnicodeScript.aliases.put("JAVA", UnicodeScript.JAVANESE);
            UnicodeScript.aliases.put("KALI", UnicodeScript.KAYAH_LI);
            UnicodeScript.aliases.put("KANA", UnicodeScript.KATAKANA);
            UnicodeScript.aliases.put("KHAR", UnicodeScript.KHAROSHTHI);
            UnicodeScript.aliases.put("KHMR", UnicodeScript.KHMER);
            UnicodeScript.aliases.put("KNDA", UnicodeScript.KANNADA);
            UnicodeScript.aliases.put("KTHI", UnicodeScript.KAITHI);
            UnicodeScript.aliases.put("LANA", UnicodeScript.TAI_THAM);
            UnicodeScript.aliases.put("LAOO", UnicodeScript.LAO);
            UnicodeScript.aliases.put("LATN", UnicodeScript.LATIN);
            UnicodeScript.aliases.put("LEPC", UnicodeScript.LEPCHA);
            UnicodeScript.aliases.put("LIMB", UnicodeScript.LIMBU);
            UnicodeScript.aliases.put("LINB", UnicodeScript.LINEAR_B);
            UnicodeScript.aliases.put("LISU", UnicodeScript.LISU);
            UnicodeScript.aliases.put("LYCI", UnicodeScript.LYCIAN);
            UnicodeScript.aliases.put("LYDI", UnicodeScript.LYDIAN);
            UnicodeScript.aliases.put("MAND", UnicodeScript.MANDAIC);
            UnicodeScript.aliases.put("MERC", UnicodeScript.MEROITIC_CURSIVE);
            UnicodeScript.aliases.put("MERO", UnicodeScript.MEROITIC_HIEROGLYPHS);
            UnicodeScript.aliases.put("MLYM", UnicodeScript.MALAYALAM);
            UnicodeScript.aliases.put("MONG", UnicodeScript.MONGOLIAN);
            UnicodeScript.aliases.put("MTEI", UnicodeScript.MEETEI_MAYEK);
            UnicodeScript.aliases.put("MYMR", UnicodeScript.MYANMAR);
            UnicodeScript.aliases.put("NKOO", UnicodeScript.NKO);
            UnicodeScript.aliases.put("OGAM", UnicodeScript.OGHAM);
            UnicodeScript.aliases.put("OLCK", UnicodeScript.OL_CHIKI);
            UnicodeScript.aliases.put("ORKH", UnicodeScript.OLD_TURKIC);
            UnicodeScript.aliases.put("ORYA", UnicodeScript.ORIYA);
            UnicodeScript.aliases.put("OSMA", UnicodeScript.OSMANYA);
            UnicodeScript.aliases.put("PHAG", UnicodeScript.PHAGS_PA);
            UnicodeScript.aliases.put("PLRD", UnicodeScript.MIAO);
            UnicodeScript.aliases.put("PHLI", UnicodeScript.INSCRIPTIONAL_PAHLAVI);
            UnicodeScript.aliases.put("PHNX", UnicodeScript.PHOENICIAN);
            UnicodeScript.aliases.put("PRTI", UnicodeScript.INSCRIPTIONAL_PARTHIAN);
            UnicodeScript.aliases.put("RJNG", UnicodeScript.REJANG);
            UnicodeScript.aliases.put("RUNR", UnicodeScript.RUNIC);
            UnicodeScript.aliases.put("SAMR", UnicodeScript.SAMARITAN);
            UnicodeScript.aliases.put("SARB", UnicodeScript.OLD_SOUTH_ARABIAN);
            UnicodeScript.aliases.put("SAUR", UnicodeScript.SAURASHTRA);
            UnicodeScript.aliases.put("SHAW", UnicodeScript.SHAVIAN);
            UnicodeScript.aliases.put("SHRD", UnicodeScript.SHARADA);
            UnicodeScript.aliases.put("SINH", UnicodeScript.SINHALA);
            UnicodeScript.aliases.put("SORA", UnicodeScript.SORA_SOMPENG);
            UnicodeScript.aliases.put("SUND", UnicodeScript.SUNDANESE);
            UnicodeScript.aliases.put("SYLO", UnicodeScript.SYLOTI_NAGRI);
            UnicodeScript.aliases.put("SYRC", UnicodeScript.SYRIAC);
            UnicodeScript.aliases.put("TAGB", UnicodeScript.TAGBANWA);
            UnicodeScript.aliases.put("TALE", UnicodeScript.TAI_LE);
            UnicodeScript.aliases.put("TAKR", UnicodeScript.TAKRI);
            UnicodeScript.aliases.put("TALU", UnicodeScript.NEW_TAI_LUE);
            UnicodeScript.aliases.put("TAML", UnicodeScript.TAMIL);
            UnicodeScript.aliases.put("TAVT", UnicodeScript.TAI_VIET);
            UnicodeScript.aliases.put("TELU", UnicodeScript.TELUGU);
            UnicodeScript.aliases.put("TFNG", UnicodeScript.TIFINAGH);
            UnicodeScript.aliases.put("TGLG", UnicodeScript.TAGALOG);
            UnicodeScript.aliases.put("THAA", UnicodeScript.THAANA);
            UnicodeScript.aliases.put("THAI", UnicodeScript.THAI);
            UnicodeScript.aliases.put("TIBT", UnicodeScript.TIBETAN);
            UnicodeScript.aliases.put("UGAR", UnicodeScript.UGARITIC);
            UnicodeScript.aliases.put("VAII", UnicodeScript.VAI);
            UnicodeScript.aliases.put("XPEO", UnicodeScript.OLD_PERSIAN);
            UnicodeScript.aliases.put("XSUX", UnicodeScript.CUNEIFORM);
            UnicodeScript.aliases.put("YIII", UnicodeScript.YI);
            UnicodeScript.aliases.put("ZINH", UnicodeScript.INHERITED);
            UnicodeScript.aliases.put("ZYYY", UnicodeScript.COMMON);
            UnicodeScript.aliases.put("ZZZZ", UnicodeScript.UNKNOWN);
        }
    }
}
