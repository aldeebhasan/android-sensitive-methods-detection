package android.location;

import android.os.*;

public interface LocationListener
{
    void onLocationChanged(final Location p0);
    
    void onStatusChanged(final String p0, final int p1, final Bundle p2);
    
    void onProviderEnabled(final String p0);
    
    void onProviderDisabled(final String p0);
}
