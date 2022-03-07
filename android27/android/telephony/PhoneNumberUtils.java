package android.telephony;

import android.content.*;
import java.util.*;
import android.text.*;
import android.text.style.*;

public class PhoneNumberUtils
{
    public static final int FORMAT_JAPAN = 2;
    public static final int FORMAT_NANP = 1;
    public static final int FORMAT_UNKNOWN = 0;
    public static final char PAUSE = ',';
    public static final int TOA_International = 145;
    public static final int TOA_Unknown = 129;
    public static final char WAIT = ';';
    public static final char WILD = 'N';
    
    public PhoneNumberUtils() {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isISODigit(final char c) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean is12Key(final char c) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean isDialable(final char c) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean isReallyDialable(final char c) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean isNonSeparator(final char c) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean isStartsPostDial(final char c) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getNumberFromIntent(final Intent intent, final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static String extractNetworkPortion(final String phoneNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public static String stripSeparators(final String phoneNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public static String extractPostDialPortion(final String phoneNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean compare(final String a, final String b) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean compare(final Context context, final String a, final String b) {
        throw new RuntimeException("Stub!");
    }
    
    public static String toCallerIDMinMatch(final String phoneNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public static String getStrippedReversed(final String phoneNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public static String stringFromStringAndTOA(final String s, final int TOA) {
        throw new RuntimeException("Stub!");
    }
    
    public static int toaFromString(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public static String calledPartyBCDToString(final byte[] bytes, final int offset, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public static String calledPartyBCDFragmentToString(final byte[] bytes, final int offset, final int length) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isWellFormedSmsAddress(final String address) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isGlobalPhoneNumber(final String phoneNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public static byte[] networkPortionToCalledPartyBCD(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public static byte[] networkPortionToCalledPartyBCDWithLength(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public static byte[] numberToCalledPartyBCD(final String number) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static String formatNumber(final String source) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static int getFormatTypeForLocale(final Locale locale) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void formatNumber(final Editable text, final int defaultFormattingType) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void formatNanpNumber(final Editable text) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void formatJapaneseNumber(final Editable text) {
        throw new RuntimeException("Stub!");
    }
    
    public static String formatNumberToE164(final String phoneNumber, final String defaultCountryIso) {
        throw new RuntimeException("Stub!");
    }
    
    public static String formatNumberToRFC3966(final String phoneNumber, final String defaultCountryIso) {
        throw new RuntimeException("Stub!");
    }
    
    public static String formatNumber(final String phoneNumber, final String defaultCountryIso) {
        throw new RuntimeException("Stub!");
    }
    
    public static String formatNumber(final String phoneNumber, final String phoneNumberE164, final String defaultCountryIso) {
        throw new RuntimeException("Stub!");
    }
    
    public static String normalizeNumber(final String phoneNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public static String replaceUnicodeDigits(final String number) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isEmergencyNumber(final String number) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isLocalEmergencyNumber(final Context context, final String number) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isVoiceMailNumber(final String number) {
        throw new RuntimeException("Stub!");
    }
    
    public static String convertKeypadLettersToDigits(final String input) {
        throw new RuntimeException("Stub!");
    }
    
    public static CharSequence createTtsSpannable(final CharSequence phoneNumber) {
        throw new RuntimeException("Stub!");
    }
    
    public static void addTtsSpan(final Spannable s, final int start, final int endExclusive) {
        throw new RuntimeException("Stub!");
    }
    
    public static TtsSpan createTtsSpan(final String phoneNumberString) {
        throw new RuntimeException("Stub!");
    }
}
