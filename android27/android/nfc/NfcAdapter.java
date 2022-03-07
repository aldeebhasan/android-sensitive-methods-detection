package android.nfc;

import android.net.*;
import android.app.*;
import android.content.*;
import android.os.*;

public final class NfcAdapter
{
    public static final String ACTION_ADAPTER_STATE_CHANGED = "android.nfc.action.ADAPTER_STATE_CHANGED";
    public static final String ACTION_NDEF_DISCOVERED = "android.nfc.action.NDEF_DISCOVERED";
    public static final String ACTION_TAG_DISCOVERED = "android.nfc.action.TAG_DISCOVERED";
    public static final String ACTION_TECH_DISCOVERED = "android.nfc.action.TECH_DISCOVERED";
    public static final String EXTRA_ADAPTER_STATE = "android.nfc.extra.ADAPTER_STATE";
    public static final String EXTRA_ID = "android.nfc.extra.ID";
    public static final String EXTRA_NDEF_MESSAGES = "android.nfc.extra.NDEF_MESSAGES";
    public static final String EXTRA_READER_PRESENCE_CHECK_DELAY = "presence";
    public static final String EXTRA_TAG = "android.nfc.extra.TAG";
    public static final int FLAG_READER_NFC_A = 1;
    public static final int FLAG_READER_NFC_B = 2;
    public static final int FLAG_READER_NFC_BARCODE = 16;
    public static final int FLAG_READER_NFC_F = 4;
    public static final int FLAG_READER_NFC_V = 8;
    public static final int FLAG_READER_NO_PLATFORM_SOUNDS = 256;
    public static final int FLAG_READER_SKIP_NDEF_CHECK = 128;
    public static final int STATE_OFF = 1;
    public static final int STATE_ON = 3;
    public static final int STATE_TURNING_OFF = 4;
    public static final int STATE_TURNING_ON = 2;
    
    NfcAdapter() {
        throw new RuntimeException("Stub!");
    }
    
    public static NfcAdapter getDefaultAdapter(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public void setBeamPushUris(final Uri[] uris, final Activity activity) {
        throw new RuntimeException("Stub!");
    }
    
    public void setBeamPushUrisCallback(final CreateBeamUrisCallback callback, final Activity activity) {
        throw new RuntimeException("Stub!");
    }
    
    public void setNdefPushMessage(final NdefMessage message, final Activity activity, final Activity... activities) {
        throw new RuntimeException("Stub!");
    }
    
    public void setNdefPushMessageCallback(final CreateNdefMessageCallback callback, final Activity activity, final Activity... activities) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnNdefPushCompleteCallback(final OnNdefPushCompleteCallback callback, final Activity activity, final Activity... activities) {
        throw new RuntimeException("Stub!");
    }
    
    public void enableForegroundDispatch(final Activity activity, final PendingIntent intent, final IntentFilter[] filters, final String[][] techLists) {
        throw new RuntimeException("Stub!");
    }
    
    public void disableForegroundDispatch(final Activity activity) {
        throw new RuntimeException("Stub!");
    }
    
    public void enableReaderMode(final Activity activity, final ReaderCallback callback, final int flags, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public void disableReaderMode(final Activity activity) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean invokeBeam(final Activity activity) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void enableForegroundNdefPush(final Activity activity, final NdefMessage message) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void disableForegroundNdefPush(final Activity activity) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isNdefPushEnabled() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean ignore(final Tag tag, final int debounceMs, final OnTagRemovedListener tagRemovedListener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnTagRemovedListener
    {
        void onTagRemoved();
    }
    
    public interface CreateBeamUrisCallback
    {
        Uri[] createBeamUris(final NfcEvent p0);
    }
    
    public interface CreateNdefMessageCallback
    {
        NdefMessage createNdefMessage(final NfcEvent p0);
    }
    
    public interface OnNdefPushCompleteCallback
    {
        void onNdefPushComplete(final NfcEvent p0);
    }
    
    public interface ReaderCallback
    {
        void onTagDiscovered(final Tag p0);
    }
}
