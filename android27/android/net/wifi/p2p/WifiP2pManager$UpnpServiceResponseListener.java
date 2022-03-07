package android.net.wifi.p2p;

import java.util.*;

public interface UpnpServiceResponseListener
{
    void onUpnpServiceAvailable(final List<String> p0, final WifiP2pDevice p1);
}
