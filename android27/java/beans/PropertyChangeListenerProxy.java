package java.beans;

import java.util.*;

public class PropertyChangeListenerProxy extends EventListenerProxy<PropertyChangeListener> implements PropertyChangeListener
{
    private final String propertyName;
    
    public PropertyChangeListenerProxy(final String propertyName, final PropertyChangeListener propertyChangeListener) {
        super(propertyChangeListener);
        this.propertyName = propertyName;
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent propertyChangeEvent) {
        this.getListener().propertyChange(propertyChangeEvent);
    }
    
    public String getPropertyName() {
        return this.propertyName;
    }
}
