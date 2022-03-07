package java.text;

public class FieldPosition
{
    int field;
    int endIndex;
    int beginIndex;
    private Format.Field attribute;
    
    public FieldPosition(final int field) {
        this.field = 0;
        this.endIndex = 0;
        this.beginIndex = 0;
        this.field = field;
    }
    
    public FieldPosition(final Format.Field field) {
        this(field, -1);
    }
    
    public FieldPosition(final Format.Field attribute, final int field) {
        this.field = 0;
        this.endIndex = 0;
        this.beginIndex = 0;
        this.attribute = attribute;
        this.field = field;
    }
    
    public Format.Field getFieldAttribute() {
        return this.attribute;
    }
    
    public int getField() {
        return this.field;
    }
    
    public int getBeginIndex() {
        return this.beginIndex;
    }
    
    public int getEndIndex() {
        return this.endIndex;
    }
    
    public void setBeginIndex(final int beginIndex) {
        this.beginIndex = beginIndex;
    }
    
    public void setEndIndex(final int endIndex) {
        this.endIndex = endIndex;
    }
    
    Format.FieldDelegate getFieldDelegate() {
        return new Delegate();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof FieldPosition)) {
            return false;
        }
        final FieldPosition fieldPosition = (FieldPosition)o;
        if (this.attribute == null) {
            if (fieldPosition.attribute != null) {
                return false;
            }
        }
        else if (!this.attribute.equals(fieldPosition.attribute)) {
            return false;
        }
        return this.beginIndex == fieldPosition.beginIndex && this.endIndex == fieldPosition.endIndex && this.field == fieldPosition.field;
    }
    
    @Override
    public int hashCode() {
        return this.field << 24 | this.beginIndex << 16 | this.endIndex;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[field=" + this.field + ",attribute=" + this.attribute + ",beginIndex=" + this.beginIndex + ",endIndex=" + this.endIndex + ']';
    }
    
    private boolean matchesField(final Format.Field field) {
        return this.attribute != null && this.attribute.equals(field);
    }
    
    private boolean matchesField(final Format.Field field, final int n) {
        if (this.attribute != null) {
            return this.attribute.equals(field);
        }
        return n == this.field;
    }
    
    private class Delegate implements Format.FieldDelegate
    {
        private boolean encounteredField;
        
        @Override
        public void formatted(final Format.Field field, final Object o, final int beginIndex, final int endIndex, final StringBuffer sb) {
            if (!this.encounteredField && FieldPosition.this.matchesField(field)) {
                FieldPosition.this.setBeginIndex(beginIndex);
                FieldPosition.this.setEndIndex(endIndex);
                this.encounteredField = (beginIndex != endIndex);
            }
        }
        
        @Override
        public void formatted(final int n, final Format.Field field, final Object o, final int beginIndex, final int endIndex, final StringBuffer sb) {
            if (!this.encounteredField && FieldPosition.this.matchesField(field, n)) {
                FieldPosition.this.setBeginIndex(beginIndex);
                FieldPosition.this.setEndIndex(endIndex);
                this.encounteredField = (beginIndex != endIndex);
            }
        }
    }
}
