package javax.xml.transform.sax;

import javax.xml.transform.*;
import org.xml.sax.*;

public abstract class SAXTransformerFactory extends TransformerFactory
{
    public static final String FEATURE = "http://javax.xml.transform.sax.SAXTransformerFactory/feature";
    public static final String FEATURE_XMLFILTER = "http://javax.xml.transform.sax.SAXTransformerFactory/feature/xmlfilter";
    
    public abstract TransformerHandler newTransformerHandler(final Source p0) throws TransformerConfigurationException;
    
    public abstract TransformerHandler newTransformerHandler(final Templates p0) throws TransformerConfigurationException;
    
    public abstract TransformerHandler newTransformerHandler() throws TransformerConfigurationException;
    
    public abstract TemplatesHandler newTemplatesHandler() throws TransformerConfigurationException;
    
    public abstract XMLFilter newXMLFilter(final Source p0) throws TransformerConfigurationException;
    
    public abstract XMLFilter newXMLFilter(final Templates p0) throws TransformerConfigurationException;
}
