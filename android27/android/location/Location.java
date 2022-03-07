package android.location;

import android.util.*;
import android.os.*;

public class Location implements Parcelable
{
    public static final Creator<Location> CREATOR;
    public static final int FORMAT_DEGREES = 0;
    public static final int FORMAT_MINUTES = 1;
    public static final int FORMAT_SECONDS = 2;
    
    public Location(final String provider) {
        throw new RuntimeException("Stub!");
    }
    
    public Location(final Location l) {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final Location l) {
        throw new RuntimeException("Stub!");
    }
    
    public void reset() {
        throw new RuntimeException("Stub!");
    }
    
    public static String convert(final double coordinate, final int outputType) {
        throw new RuntimeException("Stub!");
    }
    
    public static double convert(final String coordinate) {
        throw new RuntimeException("Stub!");
    }
    
    public static void distanceBetween(final double startLatitude, final double startLongitude, final double endLatitude, final double endLongitude, final float[] results) {
        throw new RuntimeException("Stub!");
    }
    
    public float distanceTo(final Location dest) {
        throw new RuntimeException("Stub!");
    }
    
    public float bearingTo(final Location dest) {
        throw new RuntimeException("Stub!");
    }
    
    public String getProvider() {
        throw new RuntimeException("Stub!");
    }
    
    public void setProvider(final String provider) {
        throw new RuntimeException("Stub!");
    }
    
    public long getTime() {
        throw new RuntimeException("Stub!");
    }
    
    public void setTime(final long time) {
        throw new RuntimeException("Stub!");
    }
    
    public long getElapsedRealtimeNanos() {
        throw new RuntimeException("Stub!");
    }
    
    public void setElapsedRealtimeNanos(final long time) {
        throw new RuntimeException("Stub!");
    }
    
    public double getLatitude() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLatitude(final double latitude) {
        throw new RuntimeException("Stub!");
    }
    
    public double getLongitude() {
        throw new RuntimeException("Stub!");
    }
    
    public void setLongitude(final double longitude) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasAltitude() {
        throw new RuntimeException("Stub!");
    }
    
    public double getAltitude() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAltitude(final double altitude) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void removeAltitude() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasSpeed() {
        throw new RuntimeException("Stub!");
    }
    
    public float getSpeed() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSpeed(final float speed) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void removeSpeed() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasBearing() {
        throw new RuntimeException("Stub!");
    }
    
    public float getBearing() {
        throw new RuntimeException("Stub!");
    }
    
    public void setBearing(final float bearing) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void removeBearing() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public float getAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAccuracy(final float horizontalAccuracy) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void removeAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasVerticalAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public float getVerticalAccuracyMeters() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVerticalAccuracyMeters(final float verticalAccuracyMeters) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasSpeedAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public float getSpeedAccuracyMetersPerSecond() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSpeedAccuracyMetersPerSecond(final float speedAccuracyMeterPerSecond) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasBearingAccuracy() {
        throw new RuntimeException("Stub!");
    }
    
    public float getBearingAccuracyDegrees() {
        throw new RuntimeException("Stub!");
    }
    
    public void setBearingAccuracyDegrees(final float bearingAccuracyDegrees) {
        throw new RuntimeException("Stub!");
    }
    
    public Bundle getExtras() {
        throw new RuntimeException("Stub!");
    }
    
    public void setExtras(final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isFromMockProvider() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
}
