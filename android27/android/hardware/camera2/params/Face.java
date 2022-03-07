package android.hardware.camera2.params;

import android.graphics.*;

public final class Face
{
    public static final int ID_UNSUPPORTED = -1;
    public static final int SCORE_MAX = 100;
    public static final int SCORE_MIN = 1;
    
    Face() {
        throw new RuntimeException("Stub!");
    }
    
    public Rect getBounds() {
        throw new RuntimeException("Stub!");
    }
    
    public int getScore() {
        throw new RuntimeException("Stub!");
    }
    
    public int getId() {
        throw new RuntimeException("Stub!");
    }
    
    public Point getLeftEyePosition() {
        throw new RuntimeException("Stub!");
    }
    
    public Point getRightEyePosition() {
        throw new RuntimeException("Stub!");
    }
    
    public Point getMouthPosition() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
