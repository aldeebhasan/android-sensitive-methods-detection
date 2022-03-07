package android.nfc;

import android.os.*;

public final class NdefMessage implements Parcelable
{
    public static final Creator<NdefMessage> CREATOR;
    
    public NdefMessage(final byte[] data) throws FormatException {
        throw new RuntimeException("Stub!");
    }
    
    public NdefMessage(final NdefRecord record, final NdefRecord... records) {
        throw new RuntimeException("Stub!");
    }
    
    public NdefMessage(final NdefRecord[] records) {
        throw new RuntimeException("Stub!");
    }
    
    public NdefRecord[] getRecords() {
        throw new RuntimeException("Stub!");
    }
    
    public int getByteArrayLength() {
        throw new RuntimeException("Stub!");
    }
    
    public byte[] toByteArray() {
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
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object obj) {
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
