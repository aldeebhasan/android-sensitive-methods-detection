package android.webkit;

import android.net.*;
import java.util.*;

public interface WebResourceRequest
{
    Uri getUrl();
    
    boolean isForMainFrame();
    
    boolean isRedirect();
    
    boolean hasGesture();
    
    String getMethod();
    
    Map<String, String> getRequestHeaders();
}
