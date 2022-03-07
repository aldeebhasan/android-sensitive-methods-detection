package android.telephony;

import android.content.*;
import android.graphics.*;
import android.os.*;

public class SubscriptionInfo implements Parcelable
{
    public static final Creator<SubscriptionInfo> CREATOR;
    
    SubscriptionInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSubscriptionId() {
        throw new RuntimeException("Stub!");
    }
    
    public String getIccId() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSimSlotIndex() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getDisplayName() {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getCarrierName() {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap createIconBitmap(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public int getIconTint() {
        throw new RuntimeException("Stub!");
    }
    
    public String getNumber() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDataRoaming() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMcc() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMnc() {
        throw new RuntimeException("Stub!");
    }
    
    public String getCountryIso() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
