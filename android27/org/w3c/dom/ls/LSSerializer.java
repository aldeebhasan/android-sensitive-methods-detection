package org.w3c.dom.ls;

import org.w3c.dom.*;

public interface LSSerializer
{
    DOMConfiguration getDomConfig();
    
    String getNewLine();
    
    void setNewLine(final String p0);
    
    LSSerializerFilter getFilter();
    
    void setFilter(final LSSerializerFilter p0);
    
    boolean write(final Node p0, final LSOutput p1) throws LSException;
    
    boolean writeToURI(final Node p0, final String p1) throws LSException;
    
    String writeToString(final Node p0) throws DOMException, LSException;
}
