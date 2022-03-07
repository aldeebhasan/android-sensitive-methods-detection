package android.net.sip;

import java.io.*;
import android.os.*;
import java.text.*;

public class SipProfile implements Parcelable, Serializable, Cloneable
{
    public static final Creator<SipProfile> CREATOR;
    
    SipProfile() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel out, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    public String getUriString() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDisplayName() {
        throw new RuntimeException("Stub!");
    }
    
    public String getUserName() {
        throw new RuntimeException("Stub!");
    }
    
    public String getAuthUserName() {
        throw new RuntimeException("Stub!");
    }
    
    public String getPassword() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSipDomain() {
        throw new RuntimeException("Stub!");
    }
    
    public int getPort() {
        throw new RuntimeException("Stub!");
    }
    
    public String getProtocol() {
        throw new RuntimeException("Stub!");
    }
    
    public String getProxyAddress() {
        throw new RuntimeException("Stub!");
    }
    
    public String getProfileName() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getSendKeepAlive() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getAutoRegistration() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
    }
    
    public static class Builder
    {
        public Builder(final SipProfile profile) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final String uriString) throws ParseException {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final String username, final String serverDomain) throws ParseException {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAuthUserName(final String name) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setProfileName(final String name) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setPassword(final String password) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setPort(final int port) throws IllegalArgumentException {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setProtocol(final String protocol) throws IllegalArgumentException {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setOutboundProxy(final String outboundProxy) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setDisplayName(final String displayName) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setSendKeepAlive(final boolean flag) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setAutoRegistration(final boolean flag) {
            throw new RuntimeException("Stub!");
        }
        
        public SipProfile build() {
            throw new RuntimeException("Stub!");
        }
    }
}
