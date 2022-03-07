package android.content.res;

import org.xmlpull.v1.*;
import java.io.*;
import android.os.*;

public class ColorStateList implements Parcelable
{
    public static final Creator<ColorStateList> CREATOR;
    
    public ColorStateList(final int[][] states, final int[] colors) {
        throw new RuntimeException("Stub!");
    }
    
    public static ColorStateList valueOf(final int color) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static ColorStateList createFromXml(final Resources r, final XmlPullParser parser) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public static ColorStateList createFromXml(final Resources r, final XmlPullParser parser, final Resources.Theme theme) throws XmlPullParserException, IOException {
        throw new RuntimeException("Stub!");
    }
    
    public ColorStateList withAlpha(final int alpha) {
        throw new RuntimeException("Stub!");
    }
    
    public int getChangingConfigurations() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isStateful() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isOpaque() {
        throw new RuntimeException("Stub!");
    }
    
    public int getColorForState(final int[] stateSet, final int defaultColor) {
        throw new RuntimeException("Stub!");
    }
    
    public int getDefaultColor() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
