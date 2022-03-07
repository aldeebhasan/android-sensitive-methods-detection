package android.net;

import java.io.*;
import java.util.*;
import android.os.*;

public abstract class Uri implements Parcelable, Comparable<Uri>
{
    public static final Creator<Uri> CREATOR;
    public static final Uri EMPTY;
    
    Uri() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean isHierarchical();
    
    public boolean isOpaque() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean isRelative();
    
    public boolean isAbsolute() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract String getScheme();
    
    public abstract String getSchemeSpecificPart();
    
    public abstract String getEncodedSchemeSpecificPart();
    
    public abstract String getAuthority();
    
    public abstract String getEncodedAuthority();
    
    public abstract String getUserInfo();
    
    public abstract String getEncodedUserInfo();
    
    public abstract String getHost();
    
    public abstract int getPort();
    
    public abstract String getPath();
    
    public abstract String getEncodedPath();
    
    public abstract String getQuery();
    
    public abstract String getEncodedQuery();
    
    public abstract String getFragment();
    
    public abstract String getEncodedFragment();
    
    public abstract List<String> getPathSegments();
    
    public abstract String getLastPathSegment();
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int compareTo(final Uri other) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public abstract String toString();
    
    public abstract Builder buildUpon();
    
    public static Uri parse(final String uriString) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri fromFile(final File file) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri fromParts(final String scheme, final String ssp, final String fragment) {
        throw new RuntimeException("Stub!");
    }
    
    public Set<String> getQueryParameterNames() {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getQueryParameters(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public String getQueryParameter(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getBooleanQueryParameter(final String key, final boolean defaultValue) {
        throw new RuntimeException("Stub!");
    }
    
    public Uri normalizeScheme() {
        throw new RuntimeException("Stub!");
    }
    
    public static void writeToParcel(final Parcel out, final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public static String encode(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public static String encode(final String s, final String allow) {
        throw new RuntimeException("Stub!");
    }
    
    public static String decode(final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public static Uri withAppendedPath(final Uri baseUri, final String pathSegment) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CREATOR = null;
        EMPTY = null;
    }
    
    public static final class Builder
    {
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder scheme(final String scheme) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder opaquePart(final String opaquePart) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder encodedOpaquePart(final String opaquePart) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder authority(final String authority) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder encodedAuthority(final String authority) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder path(final String path) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder encodedPath(final String path) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder appendPath(final String newSegment) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder appendEncodedPath(final String newSegment) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder query(final String query) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder encodedQuery(final String query) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder fragment(final String fragment) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder encodedFragment(final String fragment) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder appendQueryParameter(final String key, final String value) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder clearQuery() {
            throw new RuntimeException("Stub!");
        }
        
        public Uri build() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }
}
