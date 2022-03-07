package android.view.accessibility;

import android.graphics.*;

public static final class CaptionStyle
{
    public static final int EDGE_TYPE_DEPRESSED = 4;
    public static final int EDGE_TYPE_DROP_SHADOW = 2;
    public static final int EDGE_TYPE_NONE = 0;
    public static final int EDGE_TYPE_OUTLINE = 1;
    public static final int EDGE_TYPE_RAISED = 3;
    public static final int EDGE_TYPE_UNSPECIFIED = -1;
    public final int backgroundColor;
    public final int edgeColor;
    public final int edgeType;
    public final int foregroundColor;
    public final int windowColor;
    
    CaptionStyle() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasBackgroundColor() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasForegroundColor() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasEdgeType() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasEdgeColor() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasWindowColor() {
        throw new RuntimeException("Stub!");
    }
    
    public Typeface getTypeface() {
        throw new RuntimeException("Stub!");
    }
}
