package java.text;

public class ParsePosition
{
    int index;
    int errorIndex;
    
    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
    
    public ParsePosition(final int index) {
        this.index = 0;
        this.errorIndex = -1;
        this.index = index;
    }
    
    public void setErrorIndex(final int errorIndex) {
        this.errorIndex = errorIndex;
    }
    
    public int getErrorIndex() {
        return this.errorIndex;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof ParsePosition)) {
            return false;
        }
        final ParsePosition parsePosition = (ParsePosition)o;
        return this.index == parsePosition.index && this.errorIndex == parsePosition.errorIndex;
    }
    
    @Override
    public int hashCode() {
        return this.errorIndex << 16 | this.index;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "[index=" + this.index + ",errorIndex=" + this.errorIndex + ']';
    }
}
