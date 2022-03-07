package android.service.carrier;

import android.app.*;
import java.util.*;
import android.net.*;
import android.content.*;
import android.os.*;

public abstract class CarrierMessagingService extends Service
{
    public static final int DOWNLOAD_STATUS_ERROR = 2;
    public static final int DOWNLOAD_STATUS_OK = 0;
    public static final int DOWNLOAD_STATUS_RETRY_ON_CARRIER_NETWORK = 1;
    public static final int RECEIVE_OPTIONS_DEFAULT = 0;
    public static final int RECEIVE_OPTIONS_DROP = 1;
    public static final int RECEIVE_OPTIONS_SKIP_NOTIFY_WHEN_CREDENTIAL_PROTECTED_STORAGE_UNAVAILABLE = 2;
    public static final int SEND_FLAG_REQUEST_DELIVERY_STATUS = 1;
    public static final int SEND_STATUS_ERROR = 2;
    public static final int SEND_STATUS_OK = 0;
    public static final int SEND_STATUS_RETRY_ON_CARRIER_NETWORK = 1;
    public static final String SERVICE_INTERFACE = "android.service.carrier.CarrierMessagingService";
    
    public CarrierMessagingService() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onFilterSms(final MessagePdu pdu, final String format, final int destPort, final int subId, final ResultCallback<Boolean> callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void onReceiveTextSms(final MessagePdu pdu, final String format, final int destPort, final int subId, final ResultCallback<Integer> callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onSendTextSms(final String text, final int subId, final String destAddress, final ResultCallback<SendSmsResult> callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSendTextSms(final String text, final int subId, final String destAddress, final int sendSmsFlag, final ResultCallback<SendSmsResult> callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onSendDataSms(final byte[] data, final int subId, final String destAddress, final int destPort, final ResultCallback<SendSmsResult> callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSendDataSms(final byte[] data, final int subId, final String destAddress, final int destPort, final int sendSmsFlag, final ResultCallback<SendSmsResult> callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void onSendMultipartTextSms(final List<String> parts, final int subId, final String destAddress, final ResultCallback<SendMultipartSmsResult> callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSendMultipartTextSms(final List<String> parts, final int subId, final String destAddress, final int sendSmsFlag, final ResultCallback<SendMultipartSmsResult> callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void onSendMms(final Uri pduUri, final int subId, final Uri location, final ResultCallback<SendMmsResult> callback) {
        throw new RuntimeException("Stub!");
    }
    
    public void onDownloadMms(final Uri contentUri, final int subId, final Uri location, final ResultCallback<Integer> callback) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public IBinder onBind(final Intent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public static final class SendMmsResult
    {
        public SendMmsResult(final int sendStatus, final byte[] sendConfPdu) {
            throw new RuntimeException("Stub!");
        }
        
        public int getSendStatus() {
            throw new RuntimeException("Stub!");
        }
        
        public byte[] getSendConfPdu() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class SendSmsResult
    {
        public SendSmsResult(final int sendStatus, final int messageRef) {
            throw new RuntimeException("Stub!");
        }
        
        public int getMessageRef() {
            throw new RuntimeException("Stub!");
        }
        
        public int getSendStatus() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class SendMultipartSmsResult
    {
        public SendMultipartSmsResult(final int sendStatus, final int[] messageRefs) {
            throw new RuntimeException("Stub!");
        }
        
        public int[] getMessageRefs() {
            throw new RuntimeException("Stub!");
        }
        
        public int getSendStatus() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface ResultCallback<T>
    {
        void onReceiveResult(final T p0) throws RemoteException;
    }
}
