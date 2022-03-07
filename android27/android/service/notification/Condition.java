package android.service.notification;

import android.net.*;
import android.os.*;
import android.content.*;

public final class Condition implements Parcelable
{
    public static final Creator<Condition> CREATOR;
    public static final int FLAG_RELEVANT_ALWAYS = 2;
    public static final int FLAG_RELEVANT_NOW = 1;
    public static final String SCHEME = "condition";
    public static final int STATE_ERROR = 3;
    public static final int STATE_FALSE = 0;
    public static final int STATE_TRUE = 1;
    public static final int STATE_UNKNOWN = 2;
    public final int flags;
    public final int icon;
    public final Uri id;
    public final String line1;
    public final String line2;
    public final int state;
    public final String summary;
    
    public Condition(final Uri id, final String summary, final int state) {
        throw new RuntimeException("Stub!");
    }
    
    public Condition(final Uri id, final String summary, final String line1, final String line2, final int icon, final int state, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public Condition(final Parcel source) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static String stateToString(final int state) {
        throw new RuntimeException("Stub!");
    }
    
    public static String relevanceToString(final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    public Condition copy() {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri.Builder newId(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isValidId(final Uri id, final String pkg) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
