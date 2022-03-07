package android.service.carrier;

import java.util.*;
import android.os.*;

public final class MessagePdu implements Parcelable
{
    public static final Creator<MessagePdu> CREATOR;
    
    public MessagePdu(final List<byte[]> pduList) {
        throw new RuntimeException("Stub!");
    }
    
    public List<byte[]> getPdus() {
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
