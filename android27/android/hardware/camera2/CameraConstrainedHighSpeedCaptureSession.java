package android.hardware.camera2;

import java.util.*;

public abstract class CameraConstrainedHighSpeedCaptureSession extends CameraCaptureSession
{
    public CameraConstrainedHighSpeedCaptureSession() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract List<CaptureRequest> createHighSpeedRequestList(final CaptureRequest p0) throws CameraAccessException;
}
