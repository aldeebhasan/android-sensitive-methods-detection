package javax.xml.transform;

public abstract class TransformerFactory
{
    public static TransformerFactory newInstance() throws TransformerFactoryConfigurationError {
        return FactoryFinder.find(TransformerFactory.class, "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
    }
    
    public static TransformerFactory newInstance(final String s, final ClassLoader classLoader) throws TransformerFactoryConfigurationError {
        return FactoryFinder.newInstance(TransformerFactory.class, s, classLoader, false);
    }
    
    public abstract Transformer newTransformer(final Source p0) throws TransformerConfigurationException;
    
    public abstract Transformer newTransformer() throws TransformerConfigurationException;
    
    public abstract Templates newTemplates(final Source p0) throws TransformerConfigurationException;
    
    public abstract Source getAssociatedStylesheet(final Source p0, final String p1, final String p2, final String p3) throws TransformerConfigurationException;
    
    public abstract void setURIResolver(final URIResolver p0);
    
    public abstract URIResolver getURIResolver();
    
    public abstract void setFeature(final String p0, final boolean p1) throws TransformerConfigurationException;
    
    public abstract boolean getFeature(final String p0);
    
    public abstract void setAttribute(final String p0, final Object p1);
    
    public abstract Object getAttribute(final String p0);
    
    public abstract void setErrorListener(final ErrorListener p0);
    
    public abstract ErrorListener getErrorListener();
}
