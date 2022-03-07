package android.telephony;

import android.telecom.*;
import java.util.*;
import android.app.*;
import android.os.*;
import android.net.*;

public class TelephonyManager
{
    public static final String ACTION_CONFIGURE_VOICEMAIL = "android.telephony.action.CONFIGURE_VOICEMAIL";
    public static final String ACTION_PHONE_STATE_CHANGED = "android.intent.action.PHONE_STATE";
    public static final String ACTION_RESPOND_VIA_MESSAGE = "android.intent.action.RESPOND_VIA_MESSAGE";
    public static final String ACTION_SHOW_VOICEMAIL_NOTIFICATION = "android.telephony.action.SHOW_VOICEMAIL_NOTIFICATION";
    public static final int APPTYPE_CSIM = 4;
    public static final int APPTYPE_ISIM = 5;
    public static final int APPTYPE_RUIM = 3;
    public static final int APPTYPE_SIM = 1;
    public static final int APPTYPE_USIM = 2;
    public static final int AUTHTYPE_EAP_AKA = 129;
    public static final int AUTHTYPE_EAP_SIM = 128;
    public static final int CALL_STATE_IDLE = 0;
    public static final int CALL_STATE_OFFHOOK = 2;
    public static final int CALL_STATE_RINGING = 1;
    public static final int DATA_ACTIVITY_DORMANT = 4;
    public static final int DATA_ACTIVITY_IN = 1;
    public static final int DATA_ACTIVITY_INOUT = 3;
    public static final int DATA_ACTIVITY_NONE = 0;
    public static final int DATA_ACTIVITY_OUT = 2;
    public static final int DATA_CONNECTED = 2;
    public static final int DATA_CONNECTING = 1;
    public static final int DATA_DISCONNECTED = 0;
    public static final int DATA_SUSPENDED = 3;
    public static final String EXTRA_CALL_VOICEMAIL_INTENT = "android.telephony.extra.CALL_VOICEMAIL_INTENT";
    public static final String EXTRA_HIDE_PUBLIC_SETTINGS = "android.telephony.extra.HIDE_PUBLIC_SETTINGS";
    public static final String EXTRA_INCOMING_NUMBER = "incoming_number";
    public static final String EXTRA_IS_REFRESH = "android.telephony.extra.IS_REFRESH";
    public static final String EXTRA_LAUNCH_VOICEMAIL_SETTINGS_INTENT = "android.telephony.extra.LAUNCH_VOICEMAIL_SETTINGS_INTENT";
    public static final String EXTRA_NOTIFICATION_COUNT = "android.telephony.extra.NOTIFICATION_COUNT";
    public static final String EXTRA_PHONE_ACCOUNT_HANDLE = "android.telephony.extra.PHONE_ACCOUNT_HANDLE";
    public static final String EXTRA_STATE = "state";
    public static final String EXTRA_STATE_IDLE;
    public static final String EXTRA_STATE_OFFHOOK;
    public static final String EXTRA_STATE_RINGING;
    public static final String EXTRA_VOICEMAIL_NUMBER = "android.telephony.extra.VOICEMAIL_NUMBER";
    public static final String METADATA_HIDE_VOICEMAIL_SETTINGS_MENU = "android.telephony.HIDE_VOICEMAIL_SETTINGS_MENU";
    public static final int NETWORK_TYPE_1xRTT = 7;
    public static final int NETWORK_TYPE_CDMA = 4;
    public static final int NETWORK_TYPE_EDGE = 2;
    public static final int NETWORK_TYPE_EHRPD = 14;
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    public static final int NETWORK_TYPE_EVDO_A = 6;
    public static final int NETWORK_TYPE_EVDO_B = 12;
    public static final int NETWORK_TYPE_GPRS = 1;
    public static final int NETWORK_TYPE_GSM = 16;
    public static final int NETWORK_TYPE_HSDPA = 8;
    public static final int NETWORK_TYPE_HSPA = 10;
    public static final int NETWORK_TYPE_HSPAP = 15;
    public static final int NETWORK_TYPE_HSUPA = 9;
    public static final int NETWORK_TYPE_IDEN = 11;
    public static final int NETWORK_TYPE_IWLAN = 18;
    public static final int NETWORK_TYPE_LTE = 13;
    public static final int NETWORK_TYPE_TD_SCDMA = 17;
    public static final int NETWORK_TYPE_UMTS = 3;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int PHONE_TYPE_CDMA = 2;
    public static final int PHONE_TYPE_GSM = 1;
    public static final int PHONE_TYPE_NONE = 0;
    public static final int PHONE_TYPE_SIP = 3;
    public static final int SIM_STATE_ABSENT = 1;
    public static final int SIM_STATE_CARD_IO_ERROR = 8;
    public static final int SIM_STATE_CARD_RESTRICTED = 9;
    public static final int SIM_STATE_NETWORK_LOCKED = 4;
    public static final int SIM_STATE_NOT_READY = 6;
    public static final int SIM_STATE_PERM_DISABLED = 7;
    public static final int SIM_STATE_PIN_REQUIRED = 2;
    public static final int SIM_STATE_PUK_REQUIRED = 3;
    public static final int SIM_STATE_READY = 5;
    public static final int SIM_STATE_UNKNOWN = 0;
    public static final int USSD_ERROR_SERVICE_UNAVAIL = -2;
    public static final int USSD_RETURN_FAILURE = -1;
    public static final String VVM_TYPE_CVVM = "vvm_type_cvvm";
    public static final String VVM_TYPE_OMTP = "vvm_type_omtp";
    
    TelephonyManager() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPhoneCount() {
        throw new RuntimeException("Stub!");
    }
    
    public TelephonyManager createForSubscriptionId(final int subId) {
        throw new RuntimeException("Stub!");
    }
    
    public TelephonyManager createForPhoneAccountHandle(final PhoneAccountHandle phoneAccountHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public String getDeviceSoftwareVersion() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String getDeviceId() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public String getDeviceId(final int slotIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public String getImei() {
        throw new RuntimeException("Stub!");
    }
    
    public String getImei(final int slotIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public String getMeid() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMeid(final int slotIndex) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public CellLocation getCellLocation() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public List<NeighboringCellInfo> getNeighboringCellInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPhoneType() {
        throw new RuntimeException("Stub!");
    }
    
    public String getNetworkOperatorName() {
        throw new RuntimeException("Stub!");
    }
    
    public String getNetworkOperator() {
        throw new RuntimeException("Stub!");
    }
    
    public String getNetworkSpecifier() {
        throw new RuntimeException("Stub!");
    }
    
    public PersistableBundle getCarrierConfig() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isNetworkRoaming() {
        throw new RuntimeException("Stub!");
    }
    
    public String getNetworkCountryIso() {
        throw new RuntimeException("Stub!");
    }
    
    public int getNetworkType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDataNetworkType() {
        throw new RuntimeException("Stub!");
    }
    
    public int getVoiceNetworkType() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasIccCard() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSimState() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSimState(final int slotIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public String getSimOperator() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSimOperatorName() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSimCountryIso() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSimSerialNumber() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSubscriberId() {
        throw new RuntimeException("Stub!");
    }
    
    public String getGroupIdLevel1() {
        throw new RuntimeException("Stub!");
    }
    
    public String getLine1Number() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setLine1NumberForDisplay(final String alphaTag, final String number) {
        throw new RuntimeException("Stub!");
    }
    
    public String getVoiceMailNumber() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setVoiceMailNumber(final String alphaTag, final String number) {
        throw new RuntimeException("Stub!");
    }
    
    public String getVisualVoicemailPackageName() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVisualVoicemailSmsFilterSettings(final VisualVoicemailSmsFilterSettings settings) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendVisualVoicemailSms(final String number, final int port, final String text, final PendingIntent sentIntent) {
        throw new RuntimeException("Stub!");
    }
    
    public String getVoiceMailAlphaTag() {
        throw new RuntimeException("Stub!");
    }
    
    public void sendDialerSpecialCode(final String inputCode) {
        throw new RuntimeException("Stub!");
    }
    
    public int getCallState() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDataActivity() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDataState() {
        throw new RuntimeException("Stub!");
    }
    
    public void listen(final PhoneStateListener listener, final int events) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVoiceCapable() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSmsCapable() {
        throw new RuntimeException("Stub!");
    }
    
    public List<CellInfo> getAllCellInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMmsUserAgent() {
        throw new RuntimeException("Stub!");
    }
    
    public String getMmsUAProfUrl() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(final String AID) {
        throw new RuntimeException("Stub!");
    }
    
    public IccOpenLogicalChannelResponse iccOpenLogicalChannel(final String AID, final int p2) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean iccCloseLogicalChannel(final int channel) {
        throw new RuntimeException("Stub!");
    }
    
    public String iccTransmitApduLogicalChannel(final int channel, final int cla, final int instruction, final int p1, final int p2, final int p3, final String data) {
        throw new RuntimeException("Stub!");
    }
    
    public String iccTransmitApduBasicChannel(final int cla, final int instruction, final int p1, final int p2, final int p3, final String data) {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] iccExchangeSimIO(final int fileID, final int command, final int p1, final int p2, final int p3, final String filePath) {
        throw new RuntimeException("Stub!");
    }
    
    public String sendEnvelopeWithStatus(final String content) {
        throw new RuntimeException("Stub!");
    }
    
    public String getIccAuthentication(final int appType, final int authType, final String data) {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getForbiddenPlmns() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setPreferredNetworkTypeToGlobal() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasCarrierPrivileges() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean setOperatorBrandOverride(final String brand) {
        throw new RuntimeException("Stub!");
    }
    
    public void sendUssdRequest(final String ussdRequest, final UssdResponseCallback callback, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isConcurrentVoiceAndDataSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDataEnabled(final boolean enable) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isDataEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean canChangeDtmfToneLength() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isWorldPhone() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isTtyModeSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isHearingAidCompatibilitySupported() {
        throw new RuntimeException("Stub!");
    }
    
    public ServiceState getServiceState() {
        throw new RuntimeException("Stub!");
    }
    
    public Uri getVoicemailRingtoneUri(final PhoneAccountHandle accountHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVoicemailRingtoneUri(final PhoneAccountHandle phoneAccountHandle, final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVoicemailVibrationEnabled(final PhoneAccountHandle accountHandle) {
        throw new RuntimeException("Stub!");
    }
    
    public void setVoicemailVibrationEnabled(final PhoneAccountHandle phoneAccountHandle, final boolean enabled) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        EXTRA_STATE_IDLE = null;
        EXTRA_STATE_OFFHOOK = null;
        EXTRA_STATE_RINGING = null;
    }
    
    public abstract static class UssdResponseCallback
    {
        public UssdResponseCallback() {
            throw new RuntimeException("Stub!");
        }
        
        public void onReceiveUssdResponse(final TelephonyManager telephonyManager, final String request, final CharSequence response) {
            throw new RuntimeException("Stub!");
        }
        
        public void onReceiveUssdResponseFailed(final TelephonyManager telephonyManager, final String request, final int failureCode) {
            throw new RuntimeException("Stub!");
        }
    }
}
