package android.service.quicksettings;

import android.graphics.drawable.*;
import android.os.*;

public final class Tile implements Parcelable
{
    public static final Creator<Tile> CREATOR;
    public static final int STATE_ACTIVE = 2;
    public static final int STATE_INACTIVE = 1;
    public static final int STATE_UNAVAILABLE = 0;
    
    Tile() {
        throw new RuntimeException("Stub!");
    }
    
    public int getState() {
        throw new RuntimeException("Stub!");
    }
    
    public void setState(final int state) {
        throw new RuntimeException("Stub!");
    }
    
    public Icon getIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public void setIcon(final Icon icon) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getLabel() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLabel(final CharSequence label) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getContentDescription() {
        throw new RuntimeException("Stub!");
    }
    
    public void setContentDescription(final CharSequence contentDescription) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    public void updateTile() {
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
