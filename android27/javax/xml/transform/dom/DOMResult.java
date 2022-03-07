package javax.xml.transform.dom;

import javax.xml.transform.*;
import org.w3c.dom.*;

public class DOMResult implements Result
{
    public static final String FEATURE = "http://javax.xml.transform.dom.DOMResult/feature";
    private Node node;
    private Node nextSibling;
    private String systemId;
    
    public DOMResult() {
        this.node = null;
        this.nextSibling = null;
        this.systemId = null;
        this.setNode(null);
        this.setNextSibling(null);
        this.setSystemId(null);
    }
    
    public DOMResult(final Node node) {
        this.node = null;
        this.nextSibling = null;
        this.systemId = null;
        this.setNode(node);
        this.setNextSibling(null);
        this.setSystemId(null);
    }
    
    public DOMResult(final Node node, final String systemId) {
        this.node = null;
        this.nextSibling = null;
        this.systemId = null;
        this.setNode(node);
        this.setNextSibling(null);
        this.setSystemId(systemId);
    }
    
    public DOMResult(final Node node, final Node nextSibling) {
        this.node = null;
        this.nextSibling = null;
        this.systemId = null;
        if (nextSibling != null) {
            if (node == null) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }
            if ((node.compareDocumentPosition(nextSibling) & 0x10) == 0x0) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
            }
        }
        this.setNode(node);
        this.setNextSibling(nextSibling);
        this.setSystemId(null);
    }
    
    public DOMResult(final Node node, final Node nextSibling, final String systemId) {
        this.node = null;
        this.nextSibling = null;
        this.systemId = null;
        if (nextSibling != null) {
            if (node == null) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }
            if ((node.compareDocumentPosition(nextSibling) & 0x10) == 0x0) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
            }
        }
        this.setNode(node);
        this.setNextSibling(nextSibling);
        this.setSystemId(systemId);
    }
    
    public void setNode(final Node node) {
        if (this.nextSibling != null) {
            if (node == null) {
                throw new IllegalStateException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }
            if ((node.compareDocumentPosition(this.nextSibling) & 0x10) == 0x0) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
            }
        }
        this.node = node;
    }
    
    public Node getNode() {
        return this.node;
    }
    
    public void setNextSibling(final Node nextSibling) {
        if (nextSibling != null) {
            if (this.node == null) {
                throw new IllegalStateException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }
            if ((this.node.compareDocumentPosition(nextSibling) & 0x10) == 0x0) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
            }
        }
        this.nextSibling = nextSibling;
    }
    
    public Node getNextSibling() {
        return this.nextSibling;
    }
    
    @Override
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }
    
    @Override
    public String getSystemId() {
        return this.systemId;
    }
}
