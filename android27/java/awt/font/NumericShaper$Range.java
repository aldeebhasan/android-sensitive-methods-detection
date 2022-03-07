package java.awt.font;

import java.util.*;

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
