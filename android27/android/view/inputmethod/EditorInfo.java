package android.view.inputmethod;

import android.text.*;
import android.util.*;
import android.os.*;

public class EditorInfo implements InputType, Parcelable
{
    public static final Creator<EditorInfo> CREATOR;
    public static final int IME_ACTION_DONE = 6;
    public static final int IME_ACTION_GO = 2;
    public static final int IME_ACTION_NEXT = 5;
    public static final int IME_ACTION_NONE = 1;
    public static final int IME_ACTION_PREVIOUS = 7;
    public static final int IME_ACTION_SEARCH = 3;
    public static final int IME_ACTION_SEND = 4;
    public static final int IME_ACTION_UNSPECIFIED = 0;
    public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
    public static final int IME_FLAG_NAVIGATE_NEXT = 134217728;
    public static final int IME_FLAG_NAVIGATE_PREVIOUS = 67108864;
    public static final int IME_FLAG_NO_ACCESSORY_ACTION = 536870912;
    public static final int IME_FLAG_NO_ENTER_ACTION = 1073741824;
    public static final int IME_FLAG_NO_EXTRACT_UI = 268435456;
    public static final int IME_FLAG_NO_FULLSCREEN = 33554432;
    public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
    public static final int IME_MASK_ACTION = 255;
    public static final int IME_NULL = 0;
    public int actionId;
    public CharSequence actionLabel;
    public String[] contentMimeTypes;
    public Bundle extras;
    public int fieldId;
    public String fieldName;
    public LocaleList hintLocales;
    public CharSequence hintText;
    public int imeOptions;
    public int initialCapsMode;
    public int initialSelEnd;
    public int initialSelStart;
    public int inputType;
    public CharSequence label;
    public String packageName;
    public String privateImeOptions;
    
    public EditorInfo() {
        this.contentMimeTypes = null;
        throw new RuntimeException("Stub!");
    }
    
    public final void makeCompatible(final int targetSdkVersion) {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
