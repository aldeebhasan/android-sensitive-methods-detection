package javax.xml.xpath;

public class XPathFunctionException extends XPathExpressionException
{
    private static final long serialVersionUID = -1837080260374986980L;
    
    public XPathFunctionException(final String s) {
        super(s);
    }
    
    public XPathFunctionException(final Throwable t) {
        super(t);
    }
}
