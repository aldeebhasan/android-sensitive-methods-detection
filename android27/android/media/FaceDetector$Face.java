package android.media;

import android.graphics.*;

public class Face
{
    public static final float CONFIDENCE_THRESHOLD = 0.4f;
    public static final int EULER_X = 0;
    public static final int EULER_Y = 1;
    public static final int EULER_Z = 2;
    
    Face() {
        throw new RuntimeException("Stub!");
    }
    
    public float confidence() {
        throw new RuntimeException("Stub!");
    }
    
    public void getMidPoint(final PointF point) {
        throw new RuntimeException("Stub!");
    }
    
    public float eyesDistance() {
        throw new RuntimeException("Stub!");
    }
    
    public float pose(final int euler) {
        throw new RuntimeException("Stub!");
    }
}
