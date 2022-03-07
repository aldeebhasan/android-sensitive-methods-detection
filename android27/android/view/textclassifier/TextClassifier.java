package android.view.textclassifier;

import android.os.*;

public interface TextClassifier
{
    public static final TextClassifier NO_OP = null;
    public static final String TYPE_ADDRESS = "address";
    public static final String TYPE_EMAIL = "email";
    public static final String TYPE_OTHER = "other";
    public static final String TYPE_PHONE = "phone";
    public static final String TYPE_URL = "url";
    
    TextSelection suggestSelection(final CharSequence p0, final int p1, final int p2, final LocaleList p3);
    
    TextClassification classifyText(final CharSequence p0, final int p1, final int p2, final LocaleList p3);
}
