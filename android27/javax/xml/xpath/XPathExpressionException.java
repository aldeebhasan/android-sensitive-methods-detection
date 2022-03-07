package javax.xml.xpath;

public class XPathExpressionException extends XPathException
{
    private static final long serialVersionUID = -1837080260374986980L;
    
    public XPathExpressionException(final String s) {
        super(s);
    }
    
    public XPathExpressionException(final Throwable t) {
        super(t);
    }
}
