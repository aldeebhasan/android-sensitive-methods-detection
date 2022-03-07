package javax.xml.transform.sax;

import org.xml.sax.ext.*;
import org.xml.sax.*;
import javax.xml.transform.*;

public interface TransformerHandler extends ContentHandler, LexicalHandler, DTDHandler
{
    void setResult(final Result p0) throws IllegalArgumentException;
    
    void setSystemId(final String p0);
    
    String getSystemId();
    
    Transformer getTransformer();
}
