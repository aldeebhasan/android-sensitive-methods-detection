package java.util.prefs;

import java.util.*;

public interface NodeChangeListener extends EventListener
{
    void childAdded(final NodeChangeEvent p0);
    
    void childRemoved(final NodeChangeEvent p0);
}
