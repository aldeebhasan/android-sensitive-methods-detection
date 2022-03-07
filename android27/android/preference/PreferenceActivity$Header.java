package android.preference;

import android.content.*;
import android.content.res.*;
import android.os.*;

public static final class Header implements Parcelable
{
    public static final Creator<Header> CREATOR;
    public CharSequence breadCrumbShortTitle;
    public int breadCrumbShortTitleRes;
    public CharSequence breadCrumbTitle;
    public int breadCrumbTitleRes;
    public Bundle extras;
    public String fragment;
    public Bundle fragmentArguments;
    public int iconRes;
    public long id;
    public Intent intent;
    public CharSequence summary;
    public int summaryRes;
    public CharSequence title;
    public int titleRes;
    
    public Header() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getTitle(final Resources res) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getSummary(final Resources res) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getBreadCrumbTitle(final Resources res) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getBreadCrumbShortTitle(final Resources res) {
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
    
    public void readFromParcel(final Parcel in) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
