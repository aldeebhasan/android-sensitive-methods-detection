package android.net.wifi.p2p;

import java.util.*;

public interface DnsSdTxtRecordListener
{
    void onDnsSdTxtRecordAvailable(final String p0, final Map<String, String> p1, final WifiP2pDevice p2);
}
