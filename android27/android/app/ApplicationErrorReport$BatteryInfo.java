package android.app;

import android.os.*;
import android.util.*;

public static class BatteryInfo
{
    public String checkinDetails;
    public long durationMicros;
    public String usageDetails;
    public int usagePercent;
    
    public BatteryInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public BatteryInfo(final Parcel in) {
        throw new RuntimeException("Stub!");
    }
    
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
}
