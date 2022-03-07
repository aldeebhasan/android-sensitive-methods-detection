package java.awt.font;

import java.io.*;
import java.util.*;

public final class NumericShaper implements Serializable
{
    private int key;
    private int mask;
    private Range shapingRange;
    private transient Set<Range> rangeSet;
    private transient Range[] rangeArray;
    private static final int BSEARCH_THRESHOLD = 3;
    private static final long serialVersionUID = -8022764705923730308L;
    public static final int EUROPEAN = 1;
    public static final int ARABIC = 2;
    public static final int EASTERN_ARABIC = 4;
    public static final int DEVANAGARI = 8;
    public static final int BENGALI = 16;
    public static final int GURMUKHI = 32;
    public static final int GUJARATI = 64;
    public static final int ORIYA = 128;
    public static final int TAMIL = 256;
    public static final int TELUGU = 512;
    public static final int KANNADA = 1024;
    public static final int MALAYALAM = 2048;
    public static final int THAI = 4096;
    public static final int LAO = 8192;
    public static final int TIBETAN = 16384;
    public static final int MYANMAR = 32768;
    public static final int ETHIOPIC = 65536;
    public static final int KHMER = 131072;
    public static final int MONGOLIAN = 262144;
    public static final int ALL_RANGES = 524287;
    private static final int EUROPEAN_KEY = 0;
    private static final int ARABIC_KEY = 1;
    private static final int EASTERN_ARABIC_KEY = 2;
    private static final int DEVANAGARI_KEY = 3;
    private static final int BENGALI_KEY = 4;
    private static final int GURMUKHI_KEY = 5;
    private static final int GUJARATI_KEY = 6;
    private static final int ORIYA_KEY = 7;
    private static final int TAMIL_KEY = 8;
    private static final int TELUGU_KEY = 9;
    private static final int KANNADA_KEY = 10;
    private static final int MALAYALAM_KEY = 11;
    private static final int THAI_KEY = 12;
    private static final int LAO_KEY = 13;
    private static final int TIBETAN_KEY = 14;
    private static final int MYANMAR_KEY = 15;
    private static final int ETHIOPIC_KEY = 16;
    private static final int KHMER_KEY = 17;
    private static final int MONGOLIAN_KEY = 18;
    private static final int NUM_KEYS = 19;
    private static final int CONTEXTUAL_MASK = Integer.MIN_VALUE;
    private static final char[] bases;
    private static final char[] contexts;
    private static int ctCache;
    private static int ctCacheLimit;
    private transient volatile Range currentRange;
    private static int[] strongTable;
    private transient volatile int stCache;
    
    private static int getContextKey(final char c) {
        if (c < NumericShaper.contexts[NumericShaper.ctCache]) {
            while (NumericShaper.ctCache > 0 && c < NumericShaper.contexts[NumericShaper.ctCache]) {
                --NumericShaper.ctCache;
            }
        }
        else if (c >= NumericShaper.contexts[NumericShaper.ctCache + 1]) {
            while (NumericShaper.ctCache < NumericShaper.ctCacheLimit && c >= NumericShaper.contexts[NumericShaper.ctCache + 1]) {
                ++NumericShaper.ctCache;
            }
        }
        return ((NumericShaper.ctCache & 0x1) == 0x0) ? (NumericShaper.ctCache / 2) : 0;
    }
    
    private Range rangeForCodePoint(final int n) {
        if (this.currentRange.inRange(n)) {
            return this.currentRange;
        }
        final Range[] rangeArray = this.rangeArray;
        if (rangeArray.length > 3) {
            int i = 0;
            int n2 = rangeArray.length - 1;
            while (i <= n2) {
                final int n3 = (i + n2) / 2;
                final Range currentRange = rangeArray[n3];
                if (n < currentRange.start) {
                    n2 = n3 - 1;
                }
                else {
                    if (n < currentRange.end) {
                        return this.currentRange = currentRange;
                    }
                    i = n3 + 1;
                }
            }
        }
        else {
            for (int j = 0; j < rangeArray.length; ++j) {
                if (rangeArray[j].inRange(n)) {
                    return rangeArray[j];
                }
            }
        }
        return Range.EUROPEAN;
    }
    
    private boolean isStrongDirectional(final char c) {
        int stCache = this.stCache;
        if (c < NumericShaper.strongTable[stCache]) {
            stCache = search(c, NumericShaper.strongTable, 0, stCache);
        }
        else if (c >= NumericShaper.strongTable[stCache + 1]) {
            stCache = search(c, NumericShaper.strongTable, stCache + 1, NumericShaper.strongTable.length - stCache - 1);
        }
        final boolean b = (stCache & 0x1) == 0x1;
        this.stCache = stCache;
        return b;
    }
    
    private static int getKeyFromMask(final int n) {
        int n2;
        for (n2 = 0; n2 < 19 && (n & 1 << n2) == 0x0; ++n2) {}
        if (n2 == 19 || (n & ~(1 << n2)) != 0x0) {
            throw new IllegalArgumentException("invalid shaper: " + Integer.toHexString(n));
        }
        return n2;
    }
    
    public static NumericShaper getShaper(final int n) {
        return new NumericShaper(getKeyFromMask(n), n);
    }
    
    public static NumericShaper getShaper(final Range range) {
        return new NumericShaper(range, EnumSet.of(range));
    }
    
    public static NumericShaper getContextualShaper(int n) {
        n |= Integer.MIN_VALUE;
        return new NumericShaper(0, n);
    }
    
    public static NumericShaper getContextualShaper(final Set<Range> set) {
        final NumericShaper numericShaper = new NumericShaper(Range.EUROPEAN, set);
        numericShaper.mask = Integer.MIN_VALUE;
        return numericShaper;
    }
    
    public static NumericShaper getContextualShaper(int n, final int n2) {
        final int keyFromMask = getKeyFromMask(n2);
        n |= Integer.MIN_VALUE;
        return new NumericShaper(keyFromMask, n);
    }
    
    public static NumericShaper getContextualShaper(final Set<Range> set, final Range range) {
        if (range == null) {
            throw new NullPointerException();
        }
        final NumericShaper numericShaper = new NumericShaper(range, set);
        numericShaper.mask = Integer.MIN_VALUE;
        return numericShaper;
    }
    
    private NumericShaper(final int key, final int mask) {
        this.currentRange = Range.EUROPEAN;
        this.stCache = 0;
        this.key = key;
        this.mask = mask;
    }
    
    private NumericShaper(final Range shapingRange, final Set<Range> set) {
        this.currentRange = Range.EUROPEAN;
        this.stCache = 0;
        this.shapingRange = shapingRange;
        this.rangeSet = (Set<Range>)EnumSet.copyOf((Collection<Enum>)set);
        if (this.rangeSet.contains(Range.EASTERN_ARABIC) && this.rangeSet.contains(Range.ARABIC)) {
            this.rangeSet.remove(Range.ARABIC);
        }
        if (this.rangeSet.contains(Range.TAI_THAM_THAM) && this.rangeSet.contains(Range.TAI_THAM_HORA)) {
            this.rangeSet.remove(Range.TAI_THAM_HORA);
        }
        this.rangeArray = this.rangeSet.toArray(new Range[this.rangeSet.size()]);
        if (this.rangeArray.length > 3) {
            Arrays.sort(this.rangeArray, new Comparator<Range>() {
                @Override
                public int compare(final Range range, final Range range2) {
                    return (range.base > range2.base) ? 1 : ((range.base == range2.base) ? 0 : -1);
                }
            });
        }
    }
    
    public void shape(final char[] array, final int n, final int n2) {
        this.checkParams(array, n, n2);
        if (this.isContextual()) {
            if (this.rangeSet == null) {
                this.shapeContextually(array, n, n2, this.key);
            }
            else {
                this.shapeContextually(array, n, n2, this.shapingRange);
            }
        }
        else {
            this.shapeNonContextually(array, n, n2);
        }
    }
    
    public void shape(final char[] array, final int n, final int n2, final int n3) {
        this.checkParams(array, n, n2);
        if (this.isContextual()) {
            final int keyFromMask = getKeyFromMask(n3);
            if (this.rangeSet == null) {
                this.shapeContextually(array, n, n2, keyFromMask);
            }
            else {
                this.shapeContextually(array, n, n2, Range.values()[keyFromMask]);
            }
        }
        else {
            this.shapeNonContextually(array, n, n2);
        }
    }
    
    public void shape(final char[] array, final int n, final int n2, final Range range) {
        this.checkParams(array, n, n2);
        if (range == null) {
            throw new NullPointerException("context is null");
        }
        if (this.isContextual()) {
            if (this.rangeSet != null) {
                this.shapeContextually(array, n, n2, range);
            }
            else {
                final int access$500 = toRangeIndex(range);
                if (access$500 >= 0) {
                    this.shapeContextually(array, n, n2, access$500);
                }
                else {
                    this.shapeContextually(array, n, n2, this.shapingRange);
                }
            }
        }
        else {
            this.shapeNonContextually(array, n, n2);
        }
    }
    
    private void checkParams(final char[] array, final int n, final int n2) {
        if (array == null) {
            throw new NullPointerException("text is null");
        }
        if (n < 0 || n > array.length || n + n2 < 0 || n + n2 > array.length) {
            throw new IndexOutOfBoundsException("bad start or count for text of length " + array.length);
        }
    }
    
    public boolean isContextual() {
        return (this.mask & Integer.MIN_VALUE) != 0x0;
    }
    
    public int getRanges() {
        return this.mask & Integer.MAX_VALUE;
    }
    
    public Set<Range> getRangeSet() {
        if (this.rangeSet != null) {
            return EnumSet.copyOf(this.rangeSet);
        }
        return maskToRangeSet(this.mask);
    }
    
    private void shapeNonContextually(final char[] array, final int n, final int n2) {
        char c = '0';
        int access$700;
        if (this.shapingRange != null) {
            access$700 = this.shapingRange.getDigitBase();
            c += this.shapingRange.getNumericBase();
        }
        else {
            access$700 = NumericShaper.bases[this.key];
            if (this.key == 16) {
                ++c;
            }
        }
        for (int i = n; i < n + n2; ++i) {
            final char c2 = array[i];
            if (c2 >= c && c2 <= '9') {
                array[i] = (char)(c2 + access$700);
            }
        }
    }
    
    private synchronized void shapeContextually(final char[] array, final int n, final int n2, int n3) {
        if ((this.mask & 1 << n3) == 0x0) {
            n3 = 0;
        }
        int n4 = n3;
        char c = NumericShaper.bases[n3];
        char c2 = (n3 == 16) ? '1' : '0';
        synchronized (NumericShaper.class) {
            for (int i = n; i < n + n2; ++i) {
                final char c3 = array[i];
                if (c3 >= c2 && c3 <= '9') {
                    array[i] = (char)(c3 + c);
                }
                if (this.isStrongDirectional(c3)) {
                    final int contextKey = getContextKey(c3);
                    if (contextKey != n4) {
                        n4 = contextKey;
                        n3 = contextKey;
                        if ((this.mask & 0x4) != 0x0 && (n3 == 1 || n3 == 2)) {
                            n3 = 2;
                        }
                        else if ((this.mask & 0x2) != 0x0 && (n3 == 1 || n3 == 2)) {
                            n3 = 1;
                        }
                        else if ((this.mask & 1 << n3) == 0x0) {
                            n3 = 0;
                        }
                        c = NumericShaper.bases[n3];
                        c2 = ((n3 == 16) ? '1' : '0');
                    }
                }
            }
        }
    }
    
    private void shapeContextually(final char[] array, final int n, final int n2, Range range) {
        if (range == null || !this.rangeSet.contains(range)) {
            range = Range.EUROPEAN;
        }
        Range range2 = range;
        int n3 = range.getDigitBase();
        char c = (char)('0' + range.getNumericBase());
        for (int n4 = n + n2, i = n; i < n4; ++i) {
            final char c2 = array[i];
            if (c2 >= c && c2 <= '9') {
                array[i] = (char)(c2 + n3);
            }
            else if (this.isStrongDirectional(c2)) {
                range = this.rangeForCodePoint(c2);
                if (range != range2) {
                    range2 = range;
                    n3 = range.getDigitBase();
                    c = (char)('0' + range.getNumericBase());
                }
            }
        }
    }
    
    @Override
    public int hashCode() {
        int mask = this.mask;
        if (this.rangeSet != null) {
            mask = ((mask & Integer.MIN_VALUE) ^ this.rangeSet.hashCode());
        }
        return mask;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o != null) {
            try {
                final NumericShaper numericShaper = (NumericShaper)o;
                if (this.rangeSet != null) {
                    if (numericShaper.rangeSet != null) {
                        return this.isContextual() == numericShaper.isContextual() && this.rangeSet.equals(numericShaper.rangeSet) && this.shapingRange == numericShaper.shapingRange;
                    }
                    return this.isContextual() == numericShaper.isContextual() && this.rangeSet.equals(maskToRangeSet(numericShaper.mask)) && this.shapingRange == indexToRange(numericShaper.key);
                }
                else {
                    if (numericShaper.rangeSet != null) {
                        final Set access$600 = maskToRangeSet(this.mask);
                        final Range access$601 = indexToRange(this.key);
                        return this.isContextual() == numericShaper.isContextual() && access$600.equals(numericShaper.rangeSet) && access$601 == numericShaper.shapingRange;
                    }
                    return numericShaper.mask == this.mask && numericShaper.key == this.key;
                }
            }
            catch (ClassCastException ex) {}
        }
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append("[contextual:").append(this.isContextual());
        if (this.isContextual()) {
            sb.append(", context:");
            sb.append((this.shapingRange == null) ? Range.values()[this.key] : this.shapingRange);
        }
        if (this.rangeSet == null) {
            sb.append(", range(s): ");
            int n = 1;
            for (int i = 0; i < 19; ++i) {
                if ((this.mask & 1 << i) != 0x0) {
                    if (n != 0) {
                        n = 0;
                    }
                    else {
                        sb.append(", ");
                    }
                    sb.append(Range.values()[i]);
                }
            }
        }
        else {
            sb.append(", range set: ").append(this.rangeSet);
        }
        sb.append(']');
        return sb.toString();
    }
    
    private static int getHighBit(int n) {
        if (n <= 0) {
            return -32;
        }
        int n2 = 0;
        if (n >= 65536) {
            n >>= 16;
            n2 += 16;
        }
        if (n >= 256) {
            n >>= 8;
            n2 += 8;
        }
        if (n >= 16) {
            n >>= 4;
            n2 += 4;
        }
        if (n >= 4) {
            n >>= 2;
            n2 += 2;
        }
        if (n >= 2) {
            ++n2;
        }
        return n2;
    }
    
    private static int search(final int n, final int[] array, final int n2, final int n3) {
        final int n4 = 1 << getHighBit(n3);
        final int n5 = n3 - n4;
        int i = n4;
        int n6 = n2;
        if (n >= array[n6 + n5]) {
            n6 += n5;
        }
        while (i > 1) {
            i >>= 1;
            if (n >= array[n6 + i]) {
                n6 += i;
            }
        }
        return n6;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.shapingRange != null) {
            final int access$500 = toRangeIndex(this.shapingRange);
            if (access$500 >= 0) {
                this.key = access$500;
            }
        }
        if (this.rangeSet != null) {
            this.mask |= toRangeMask(this.rangeSet);
        }
        objectOutputStream.defaultWriteObject();
    }
    
    static {
        bases = new char[] { '\0', '\u0630', '\u06c0', '\u0936', '\u09b6', '\u0a36', '\u0ab6', '\u0b36', '\u0bb6', '\u0c36', '\u0cb6', '\u0d36', '\u0e20', '\u0ea0', '\u0ef0', '\u1010', '\u1338', '\u17b0', '\u17e0' };
        contexts = new char[] { '\0', '\u0300', '\u0600', '\u0780', '\u0600', '\u0780', '\u0900', '\u0980', '\u0980', '\u0a00', '\u0a00', '\u0a80', '\u0a80', '\u0b00', '\u0b00', '\u0b80', '\u0b80', '\u0c00', '\u0c00', '\u0c80', '\u0c80', '\u0d00', '\u0d00', '\u0d80', '\u0e00', '\u0e80', '\u0e80', '\u0f00', '\u0f00', '\u1000', '\u1000', '\u1080', '\u1200', '\u1380', '\u1780', '\u1800', '\u1800', '\u1900', '\uffff' };
        NumericShaper.ctCache = 0;
        NumericShaper.ctCacheLimit = NumericShaper.contexts.length - 2;
        NumericShaper.strongTable = new int[] { 0, 65, 91, 97, 123, 170, 171, 181, 182, 186, 187, 192, 215, 216, 247, 248, 697, 699, 706, 720, 722, 736, 741, 750, 751, 880, 884, 886, 894, 902, 903, 904, 1014, 1015, 1155, 1162, 1418, 1470, 1471, 1472, 1473, 1475, 1476, 1478, 1479, 1488, 1536, 1544, 1545, 1547, 1548, 1549, 1550, 1563, 1611, 1645, 1648, 1649, 1750, 1765, 1767, 1774, 1776, 1786, 1809, 1810, 1840, 1869, 1958, 1969, 2027, 2036, 2038, 2042, 2070, 2074, 2075, 2084, 2085, 2088, 2089, 2096, 2137, 2142, 2276, 2307, 2362, 2363, 2364, 2365, 2369, 2377, 2381, 2382, 2385, 2392, 2402, 2404, 2433, 2434, 2492, 2493, 2497, 2503, 2509, 2510, 2530, 2534, 2546, 2548, 2555, 2563, 2620, 2622, 2625, 2649, 2672, 2674, 2677, 2691, 2748, 2749, 2753, 2761, 2765, 2768, 2786, 2790, 2801, 2818, 2876, 2877, 2879, 2880, 2881, 2887, 2893, 2903, 2914, 2918, 2946, 2947, 3008, 3009, 3021, 3024, 3059, 3073, 3134, 3137, 3142, 3160, 3170, 3174, 3192, 3199, 3260, 3261, 3276, 3285, 3298, 3302, 3393, 3398, 3405, 3406, 3426, 3430, 3530, 3535, 3538, 3544, 3633, 3634, 3636, 3648, 3655, 3663, 3761, 3762, 3764, 3773, 3784, 3792, 3864, 3866, 3893, 3894, 3895, 3896, 3897, 3902, 3953, 3967, 3968, 3973, 3974, 3976, 3981, 4030, 4038, 4039, 4141, 4145, 4146, 4152, 4153, 4155, 4157, 4159, 4184, 4186, 4190, 4193, 4209, 4213, 4226, 4227, 4229, 4231, 4237, 4238, 4253, 4254, 4957, 4960, 5008, 5024, 5120, 5121, 5760, 5761, 5787, 5792, 5906, 5920, 5938, 5941, 5970, 5984, 6002, 6016, 6068, 6070, 6071, 6078, 6086, 6087, 6089, 6100, 6107, 6108, 6109, 6112, 6128, 6160, 6313, 6314, 6432, 6435, 6439, 6441, 6450, 6451, 6457, 6470, 6622, 6656, 6679, 6681, 6742, 6743, 6744, 6753, 6754, 6755, 6757, 6765, 6771, 6784, 6912, 6916, 6964, 6965, 6966, 6971, 6972, 6973, 6978, 6979, 7019, 7028, 7040, 7042, 7074, 7078, 7080, 7082, 7083, 7084, 7142, 7143, 7144, 7146, 7149, 7150, 7151, 7154, 7212, 7220, 7222, 7227, 7376, 7379, 7380, 7393, 7394, 7401, 7405, 7406, 7412, 7413, 7616, 7680, 8125, 8126, 8127, 8130, 8141, 8144, 8157, 8160, 8173, 8178, 8189, 8206, 8208, 8305, 8308, 8319, 8320, 8336, 8352, 8450, 8451, 8455, 8456, 8458, 8468, 8469, 8470, 8473, 8478, 8484, 8485, 8486, 8487, 8488, 8489, 8490, 8494, 8495, 8506, 8508, 8512, 8517, 8522, 8526, 8528, 8544, 8585, 9014, 9083, 9109, 9110, 9372, 9450, 9900, 9901, 10240, 10496, 11264, 11493, 11499, 11503, 11506, 11513, 11520, 11647, 11648, 11744, 12293, 12296, 12321, 12330, 12337, 12342, 12344, 12349, 12353, 12441, 12445, 12448, 12449, 12539, 12540, 12736, 12784, 12829, 12832, 12880, 12896, 12924, 12927, 12977, 12992, 13004, 13008, 13175, 13179, 13278, 13280, 13311, 13312, 19904, 19968, 42128, 42192, 42509, 42512, 42607, 42624, 42655, 42656, 42736, 42738, 42752, 42786, 42888, 42889, 43010, 43011, 43014, 43015, 43019, 43020, 43045, 43047, 43048, 43056, 43064, 43072, 43124, 43136, 43204, 43214, 43232, 43250, 43302, 43310, 43335, 43346, 43392, 43395, 43443, 43444, 43446, 43450, 43452, 43453, 43561, 43567, 43569, 43571, 43573, 43584, 43587, 43588, 43596, 43597, 43696, 43697, 43698, 43701, 43703, 43705, 43710, 43712, 43713, 43714, 43756, 43758, 43766, 43777, 44005, 44006, 44008, 44009, 44013, 44016, 64286, 64287, 64297, 64298, 64830, 64848, 65021, 65136, 65279, 65313, 65339, 65345, 65371, 65382, 65504, 65536, 65793, 65794, 65856, 66000, 66045, 66176, 67871, 67872, 68097, 68112, 68152, 68160, 68409, 68416, 69216, 69632, 69633, 69634, 69688, 69703, 69714, 69734, 69760, 69762, 69811, 69815, 69817, 69819, 69888, 69891, 69927, 69932, 69933, 69942, 70016, 70018, 70070, 70079, 71339, 71340, 71341, 71342, 71344, 71350, 71351, 71360, 94095, 94099, 119143, 119146, 119155, 119171, 119173, 119180, 119210, 119214, 119296, 119648, 120539, 120540, 120597, 120598, 120655, 120656, 120713, 120714, 120771, 120772, 120782, 126464, 126704, 127248, 127338, 127344, 127744, 128140, 128141, 128292, 128293, 131072, 917505, 983040, 1114110, 1114111 };
    }
    
    public enum Range
    {
        EUROPEAN(48, 0, 768), 
        ARABIC(1632, 1536, 1920), 
        EASTERN_ARABIC(1776, 1536, 1920), 
        DEVANAGARI(2406, 2304, 2432), 
        BENGALI(2534, 2432, 2560), 
        GURMUKHI(2662, 2560, 2688), 
        GUJARATI(2790, 2816, 2944), 
        ORIYA(2918, 2816, 2944), 
        TAMIL(3046, 2944, 3072), 
        TELUGU(3174, 3072, 3200), 
        KANNADA(3302, 3200, 3328), 
        MALAYALAM(3430, 3328, 3456), 
        THAI(3664, 3584, 3712), 
        LAO(3792, 3712, 3840), 
        TIBETAN(3872, 3840, 4096), 
        MYANMAR(4160, 4096, 4224), 
        ETHIOPIC(4969, 4608, 4992) {
            @Override
            char getNumericBase() {
                return '\u0001';
            }
        }, 
        KHMER(6112, 6016, 6144), 
        MONGOLIAN(6160, 6144, 6400), 
        NKO(1984, 1984, 2048), 
        MYANMAR_SHAN(4240, 4096, 4256), 
        LIMBU(6470, 6400, 6480), 
        NEW_TAI_LUE(6608, 6528, 6624), 
        BALINESE(6992, 6912, 7040), 
        SUNDANESE(7088, 7040, 7104), 
        LEPCHA(7232, 7168, 7248), 
        OL_CHIKI(7248, 7248, 7296), 
        VAI(42528, 42240, 42560), 
        SAURASHTRA(43216, 43136, 43232), 
        KAYAH_LI(43264, 43264, 43312), 
        CHAM(43600, 43520, 43616), 
        TAI_THAM_HORA(6784, 6688, 6832), 
        TAI_THAM_THAM(6800, 6688, 6832), 
        JAVANESE(43472, 43392, 43488), 
        MEETEI_MAYEK(44016, 43968, 44032);
        
        private final int base;
        private final int start;
        private final int end;
        
        private static int toRangeIndex(final Range range) {
            final int ordinal = range.ordinal();
            return (ordinal < 19) ? ordinal : -1;
        }
        
        private static Range indexToRange(final int n) {
            return (n < 19) ? values()[n] : null;
        }
        
        private static int toRangeMask(final Set<Range> set) {
            int n = 0;
            final Iterator<Range> iterator = set.iterator();
            while (iterator.hasNext()) {
                final int ordinal = iterator.next().ordinal();
                if (ordinal < 19) {
                    n |= 1 << ordinal;
                }
            }
            return n;
        }
        
        private static Set<Range> maskToRangeSet(final int n) {
            final EnumSet<Range> none = EnumSet.noneOf(Range.class);
            final Range[] values = values();
            for (int i = 0; i < 19; ++i) {
                if ((n & 1 << i) != 0x0) {
                    none.add(values[i]);
                }
            }
            return none;
        }
        
        private Range(final int n2, final int start, final int end) {
            this.base = n2 - ('0' + this.getNumericBase());
            this.start = start;
            this.end = end;
        }
        
        private int getDigitBase() {
            return this.base;
        }
        
        char getNumericBase() {
            return '\0';
        }
        
        private boolean inRange(final int n) {
            return this.start <= n && n < this.end;
        }
    }
}
