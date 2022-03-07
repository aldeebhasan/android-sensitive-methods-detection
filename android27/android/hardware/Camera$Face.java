package android.hardware;

import android.graphics.*;

@Deprecated
public static class Face
{
    public int id;
    public Point leftEye;
    public Point mouth;
    public Rect rect;
    public Point rightEye;
    public int score;
    
    public Face() {
        throw new RuntimeException("Stub!");
    }
}
