package android.media;

import android.os.*;
import java.util.*;

public final class MediaDrm
{
    @Deprecated
    public static final int EVENT_KEY_EXPIRED = 3;
    public static final int EVENT_KEY_REQUIRED = 2;
    @Deprecated
    public static final int EVENT_PROVISION_REQUIRED = 1;
    public static final int EVENT_SESSION_RECLAIMED = 5;
    public static final int EVENT_VENDOR_DEFINED = 4;
    public static final int KEY_TYPE_OFFLINE = 2;
    public static final int KEY_TYPE_RELEASE = 3;
    public static final int KEY_TYPE_STREAMING = 1;
    public static final String PROPERTY_ALGORITHMS = "algorithms";
    public static final String PROPERTY_DESCRIPTION = "description";
    public static final String PROPERTY_DEVICE_UNIQUE_ID = "deviceUniqueId";
    public static final String PROPERTY_VENDOR = "vendor";
    public static final String PROPERTY_VERSION = "version";
    
    public MediaDrm(final UUID uuid) throws UnsupportedSchemeException {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean isCryptoSchemeSupported(final UUID uuid) {
        throw new RuntimeException("Stub!");
    }
    
    public static final boolean isCryptoSchemeSupported(final UUID uuid, final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnExpirationUpdateListener(final OnExpirationUpdateListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnKeyStatusChangeListener(final OnKeyStatusChangeListener listener, final Handler handler) {
        throw new RuntimeException("Stub!");
    }
    
    public void setOnEventListener(final OnEventListener listener) {
        throw new RuntimeException("Stub!");
    }
    
    public native byte[] openSession() throws NotProvisionedException, ResourceBusyException;
    
    public native void closeSession(final byte[] p0);
    
    public native KeyRequest getKeyRequest(final byte[] p0, final byte[] p1, final String p2, final int p3, final HashMap<String, String> p4) throws NotProvisionedException;
    
    public native byte[] provideKeyResponse(final byte[] p0, final byte[] p1) throws NotProvisionedException, DeniedByServerException;
    
    public native void restoreKeys(final byte[] p0, final byte[] p1);
    
    public native void removeKeys(final byte[] p0);
    
    public native HashMap<String, String> queryKeyStatus(final byte[] p0);
    
    public ProvisionRequest getProvisionRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public void provideProvisionResponse(final byte[] response) throws DeniedByServerException {
        throw new RuntimeException("Stub!");
    }
    
    public native List<byte[]> getSecureStops();
    
    public native byte[] getSecureStop(final byte[] p0);
    
    public native void releaseSecureStops(final byte[] p0);
    
    public native void releaseAllSecureStops();
    
    public native String getPropertyString(final String p0);
    
    public native byte[] getPropertyByteArray(final String p0);
    
    public native void setPropertyString(final String p0, final String p1);
    
    public native void setPropertyByteArray(final String p0, final byte[] p1);
    
    public CryptoSession getCryptoSession(final byte[] sessionId, final String cipherAlgorithm, final String macAlgorithm) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() {
        throw new RuntimeException("Stub!");
    }
    
    public final native void release();
    
    public static final class MediaDrmStateException extends IllegalStateException
    {
        MediaDrmStateException() {
            throw new RuntimeException("Stub!");
        }
        
        public String getDiagnosticInfo() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class KeyStatus
    {
        public static final int STATUS_EXPIRED = 1;
        public static final int STATUS_INTERNAL_ERROR = 4;
        public static final int STATUS_OUTPUT_NOT_ALLOWED = 2;
        public static final int STATUS_PENDING = 3;
        public static final int STATUS_USABLE = 0;
        
        KeyStatus() {
            throw new RuntimeException("Stub!");
        }
        
        public int getStatusCode() {
            throw new RuntimeException("Stub!");
        }
        
        public byte[] getKeyId() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class KeyRequest
    {
        public static final int REQUEST_TYPE_INITIAL = 0;
        public static final int REQUEST_TYPE_RELEASE = 2;
        public static final int REQUEST_TYPE_RENEWAL = 1;
        
        KeyRequest() {
            throw new RuntimeException("Stub!");
        }
        
        public byte[] getData() {
            throw new RuntimeException("Stub!");
        }
        
        public String getDefaultUrl() {
            throw new RuntimeException("Stub!");
        }
        
        public int getRequestType() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class ProvisionRequest
    {
        ProvisionRequest() {
            throw new RuntimeException("Stub!");
        }
        
        public byte[] getData() {
            throw new RuntimeException("Stub!");
        }
        
        public String getDefaultUrl() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public final class CryptoSession
    {
        CryptoSession() {
            throw new RuntimeException("Stub!");
        }
        
        public byte[] encrypt(final byte[] keyid, final byte[] input, final byte[] iv) {
            throw new RuntimeException("Stub!");
        }
        
        public byte[] decrypt(final byte[] keyid, final byte[] input, final byte[] iv) {
            throw new RuntimeException("Stub!");
        }
        
        public byte[] sign(final byte[] keyid, final byte[] message) {
            throw new RuntimeException("Stub!");
        }
        
        public boolean verify(final byte[] keyid, final byte[] message, final byte[] signature) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public interface OnEventListener
    {
        void onEvent(final MediaDrm p0, final byte[] p1, final int p2, final int p3, final byte[] p4);
    }
    
    public interface OnKeyStatusChangeListener
    {
        void onKeyStatusChange(final MediaDrm p0, final byte[] p1, final List<KeyStatus> p2, final boolean p3);
    }
    
    public interface OnExpirationUpdateListener
    {
        void onExpirationUpdate(final MediaDrm p0, final byte[] p1, final long p2);
    }
}
