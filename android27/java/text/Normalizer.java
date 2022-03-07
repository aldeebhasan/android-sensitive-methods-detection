package java.text;

import sun.text.normalizer.*;

public final class Normalizer
{
    public static String normalize(final CharSequence charSequence, final Form form) {
        return NormalizerBase.normalize(charSequence.toString(), form);
    }
    
    public static boolean isNormalized(final CharSequence charSequence, final Form form) {
        return NormalizerBase.isNormalized(charSequence.toString(), form);
    }
    
    public enum Form
    {
        NFD, 
        NFC, 
        NFKD, 
        NFKC;
    }
}
