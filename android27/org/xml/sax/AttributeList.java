package org.xml.sax;

public interface AttributeList
{
    int getLength();
    
    String getName(final int p0);
    
    String getType(final int p0);
    
    String getValue(final int p0);
    
    String getType(final String p0);
    
    String getValue(final String p0);
}
