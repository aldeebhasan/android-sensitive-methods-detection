package android.location;

import android.content.*;
import java.util.*;
import java.io.*;

public final class Geocoder
{
    public Geocoder(final Context context, final Locale locale) {
        throw new RuntimeException("Stub!");
    }
    
    public Geocoder(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isPresent() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Address> getFromLocation(final double latitude, final double longitude, final int maxResults) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public List<Address> getFromLocationName(final String locationName, final int maxResults) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public List<Address> getFromLocationName(final String locationName, final int maxResults, final double lowerLeftLatitude, final double lowerLeftLongitude, final double upperRightLatitude, final double upperRightLongitude) throws IOException {
        throw new RuntimeException("Stub!");
    }
}
