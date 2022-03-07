package javax.xml.transform;

import java.util.*;

public interface Templates
{
    Transformer newTransformer() throws TransformerConfigurationException;
    
    Properties getOutputProperties();
}
