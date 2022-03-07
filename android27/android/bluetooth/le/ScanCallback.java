package android.bluetooth.le;

import java.util.*;

public abstract class ScanCallback
{
    public static final int SCAN_FAILED_ALREADY_STARTED = 1;
    public static final int SCAN_FAILED_APPLICATION_REGISTRATION_FAILED = 2;
    public static final int SCAN_FAILED_FEATURE_UNSUPPORTED = 4;
    public static final int SCAN_FAILED_INTERNAL_ERROR = 3;
    
    public ScanCallback() {
        throw new RuntimeException("Stub!");
    }
    
    public void onScanResult(final int callbackType, final ScanResult result) {
        throw new RuntimeException("Stub!");
    }
    
    public void onBatchScanResults(final List<ScanResult> results) {
        throw new RuntimeException("Stub!");
    }
    
    public void onScanFailed(final int errorCode) {
        throw new RuntimeException("Stub!");
    }
}
