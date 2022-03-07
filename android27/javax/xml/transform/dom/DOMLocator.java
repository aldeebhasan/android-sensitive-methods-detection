package javax.xml.transform.dom;

import javax.xml.transform.*;
import org.w3c.dom.*;

public interface DOMLocator extends SourceLocator
{
    Node getOriginatingNode();
}
