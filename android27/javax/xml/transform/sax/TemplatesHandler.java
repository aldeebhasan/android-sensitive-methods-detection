package javax.xml.transform.sax;

import org.xml.sax.*;
import javax.xml.transform.*;

public interface TemplatesHandler extends ContentHandler
{
    Templates getTemplates();
    
    void setSystemId(final String p0);
    
    String getSystemId();
}
