package java.lang;

import java.util.*;

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
