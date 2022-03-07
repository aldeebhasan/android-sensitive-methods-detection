package java.text;

public final class StringCharacterIterator implements CharacterIterator
{
    private String text;
    private int begin;
    private int end;
    private int pos;
    
    public StringCharacterIterator(final String s) {
        this(s, 0);
    }
    
    public StringCharacterIterator(final String s, final int n) {
        this(s, 0, s.length(), n);
    }
    
    public StringCharacterIterator(final String text, final int begin, final int end, final int pos) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
        if (begin < 0 || begin > end || end > text.length()) {
            throw new IllegalArgumentException("Invalid substring range");
        }
        if (pos < begin || pos > end) {
            throw new IllegalArgumentException("Invalid position");
        }
        this.begin = begin;
        this.end = end;
        this.pos = pos;
    }
    
    public void setText(final String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
        this.begin = 0;
        this.end = text.length();
        this.pos = 0;
    }
    
    @Override
    public char first() {
        this.pos = this.begin;
        return this.current();
    }
    
    @Override
    public char last() {
        if (this.end != this.begin) {
            this.pos = this.end - 1;
        }
        else {
            this.pos = this.end;
        }
        return this.current();
    }
    
    @Override
    public char setIndex(final int pos) {
        if (pos < this.begin || pos > this.end) {
            throw new IllegalArgumentException("Invalid index");
        }
        this.pos = pos;
        return this.current();
    }
    
    @Override
    public char current() {
        if (this.pos >= this.begin && this.pos < this.end) {
            return this.text.charAt(this.pos);
        }
        return '\uffff';
    }
    
    @Override
    public char next() {
        if (this.pos < this.end - 1) {
            ++this.pos;
            return this.text.charAt(this.pos);
        }
        this.pos = this.end;
        return '\uffff';
    }
    
    @Override
    public char previous() {
        if (this.pos > this.begin) {
            --this.pos;
            return this.text.charAt(this.pos);
        }
        return '\uffff';
    }
    
    @Override
    public int getBeginIndex() {
        return this.begin;
    }
    
    @Override
    public int getEndIndex() {
        return this.end;
    }
    
    @Override
    public int getIndex() {
        return this.pos;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StringCharacterIterator)) {
            return false;
        }
        final StringCharacterIterator stringCharacterIterator = (StringCharacterIterator)o;
        return this.hashCode() == stringCharacterIterator.hashCode() && this.text.equals(stringCharacterIterator.text) && this.pos == stringCharacterIterator.pos && this.begin == stringCharacterIterator.begin && this.end == stringCharacterIterator.end;
    }
    
    @Override
    public int hashCode() {
        return this.text.hashCode() ^ this.pos ^ this.begin ^ this.end;
    }
    
    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
}
