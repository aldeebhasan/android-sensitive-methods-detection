package java.text;

import java.io.*;
import java.util.*;

public interface AttributedCharacterIterator extends CharacterIterator
{
    int getRunStart();
    
    int getRunStart(final Attribute p0);
    
    int getRunStart(final Set<? extends Attribute> p0);
    
    int getRunLimit();
    
    int getRunLimit(final Attribute p0);
    
    int getRunLimit(final Set<? extends Attribute> p0);
    
    Map<Attribute, Object> getAttributes();
    
    Object getAttribute(final Attribute p0);
    
    Set<Attribute> getAllAttributeKeys();
    
    public static class Attribute implements Serializable
    {
        private String name;
        private static final Map<String, Attribute> instanceMap;
        public static final Attribute LANGUAGE;
        public static final Attribute READING;
        public static final Attribute INPUT_METHOD_SEGMENT;
        private static final long serialVersionUID = -9142742483513960612L;
        
        protected Attribute(final String name) {
            this.name = name;
            if (this.getClass() == Attribute.class) {
                Attribute.instanceMap.put(name, this);
            }
        }
        
        @Override
        public final boolean equals(final Object o) {
            return super.equals(o);
        }
        
        @Override
        public final int hashCode() {
            return super.hashCode();
        }
        
        @Override
        public String toString() {
            return this.getClass().getName() + "(" + this.name + ")";
        }
        
        protected String getName() {
            return this.name;
        }
        
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != Attribute.class) {
                throw new InvalidObjectException("subclass didn't correctly implement readResolve");
            }
            final Attribute attribute = Attribute.instanceMap.get(this.getName());
            if (attribute != null) {
                return attribute;
            }
            throw new InvalidObjectException("unknown attribute name");
        }
        
        static {
            instanceMap = new HashMap<String, Attribute>(7);
            LANGUAGE = new Attribute("language");
            READING = new Attribute("reading");
            INPUT_METHOD_SEGMENT = new Attribute("input_method_segment");
        }
    }
}
