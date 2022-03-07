package java.text;

import java.io.*;

public abstract class Format implements Serializable, Cloneable
{
    private static final long serialVersionUID = -299282585814624189L;
    
    public final String format(final Object o) {
        return this.format(o, new StringBuffer(), new FieldPosition(0)).toString();
    }
    
    public abstract StringBuffer format(final Object p0, final StringBuffer p1, final FieldPosition p2);
    
    public AttributedCharacterIterator formatToCharacterIterator(final Object o) {
        return this.createAttributedCharacterIterator(this.format(o));
    }
    
    public abstract Object parseObject(final String p0, final ParsePosition p1);
    
    public Object parseObject(final String s) throws ParseException {
        final ParsePosition parsePosition = new ParsePosition(0);
        final Object object = this.parseObject(s, parsePosition);
        if (parsePosition.index == 0) {
            throw new ParseException("Format.parseObject(String) failed", parsePosition.errorIndex);
        }
        return object;
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    AttributedCharacterIterator createAttributedCharacterIterator(final String s) {
        return new AttributedString(s).getIterator();
    }
    
    AttributedCharacterIterator createAttributedCharacterIterator(final AttributedCharacterIterator[] array) {
        return new AttributedString(array).getIterator();
    }
    
    AttributedCharacterIterator createAttributedCharacterIterator(final String s, final AttributedCharacterIterator.Attribute attribute, final Object o) {
        final AttributedString attributedString = new AttributedString(s);
        attributedString.addAttribute(attribute, o);
        return attributedString.getIterator();
    }
    
    AttributedCharacterIterator createAttributedCharacterIterator(final AttributedCharacterIterator attributedCharacterIterator, final AttributedCharacterIterator.Attribute attribute, final Object o) {
        final AttributedString attributedString = new AttributedString(attributedCharacterIterator);
        attributedString.addAttribute(attribute, o);
        return attributedString.getIterator();
    }
    
    public static class Field extends AttributedCharacterIterator.Attribute
    {
        private static final long serialVersionUID = 276966692217360283L;
        
        protected Field(final String s) {
            super(s);
        }
    }
    
    interface FieldDelegate
    {
        void formatted(final Field p0, final Object p1, final int p2, final int p3, final StringBuffer p4);
        
        void formatted(final int p0, final Field p1, final Object p2, final int p3, final int p4, final StringBuffer p5);
    }
}
