package java.beans;

import java.util.*;

public class PropertyChangeEvent extends EventObject
{
    private static final long serialVersionUID = 7042693688939648123L;
    private String propertyName;
    private Object newValue;
    private Object oldValue;
    private Object propagationId;
    
    public PropertyChangeEvent(final Object o, final String propertyName, final Object oldValue, final Object newValue) {
        super(o);
        this.propertyName = propertyName;
        this.newValue = newValue;
        this.oldValue = oldValue;
    }
    
    public String getPropertyName() {
        return this.propertyName;
    }
    
    public Object getNewValue() {
        return this.newValue;
    }
    
    public Object getOldValue() {
        return this.oldValue;
    }
    
    public void setPropagationId(final Object propagationId) {
        this.propagationId = propagationId;
    }
    
    public Object getPropagationId() {
        return this.propagationId;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getName());
        sb.append("[propertyName=").append(this.getPropertyName());
        this.appendTo(sb);
        sb.append("; oldValue=").append(this.getOldValue());
        sb.append("; newValue=").append(this.getNewValue());
        sb.append("; propagationId=").append(this.getPropagationId());
        sb.append("; source=").append(this.getSource());
        return sb.append("]").toString();
    }
    
    void appendTo(final StringBuilder sb) {
    }
}
