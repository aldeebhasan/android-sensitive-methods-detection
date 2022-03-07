package android.provider;

import android.content.*;
import android.telephony.*;

public static final class Intents
{
    public static final String ACTION_CHANGE_DEFAULT = "android.provider.Telephony.ACTION_CHANGE_DEFAULT";
    public static final String ACTION_DEFAULT_SMS_PACKAGE_CHANGED = "android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED";
    public static final String ACTION_EXTERNAL_PROVIDER_CHANGE = "android.provider.action.EXTERNAL_PROVIDER_CHANGE";
    public static final String DATA_SMS_RECEIVED_ACTION = "android.intent.action.DATA_SMS_RECEIVED";
    public static final String EXTRA_IS_DEFAULT_SMS_APP = "android.provider.extra.IS_DEFAULT_SMS_APP";
    public static final String EXTRA_PACKAGE_NAME = "package";
    public static final int RESULT_SMS_DUPLICATED = 5;
    public static final int RESULT_SMS_GENERIC_ERROR = 2;
    public static final int RESULT_SMS_HANDLED = 1;
    public static final int RESULT_SMS_OUT_OF_MEMORY = 3;
    public static final int RESULT_SMS_UNSUPPORTED = 4;
    public static final String SIM_FULL_ACTION = "android.provider.Telephony.SIM_FULL";
    public static final String SMS_CB_RECEIVED_ACTION = "android.provider.Telephony.SMS_CB_RECEIVED";
    public static final String SMS_DELIVER_ACTION = "android.provider.Telephony.SMS_DELIVER";
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public static final String SMS_REJECTED_ACTION = "android.provider.Telephony.SMS_REJECTED";
    public static final String SMS_SERVICE_CATEGORY_PROGRAM_DATA_RECEIVED_ACTION = "android.provider.Telephony.SMS_SERVICE_CATEGORY_PROGRAM_DATA_RECEIVED";
    public static final String WAP_PUSH_DELIVER_ACTION = "android.provider.Telephony.WAP_PUSH_DELIVER";
    public static final String WAP_PUSH_RECEIVED_ACTION = "android.provider.Telephony.WAP_PUSH_RECEIVED";
    
    Intents() {
        throw new RuntimeException("Stub!");
    }
    
    public static SmsMessage[] getMessagesFromIntent(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
}
