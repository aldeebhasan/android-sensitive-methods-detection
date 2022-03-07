package java.text;

public interface CharacterIterator extends Cloneable
{
    public static final char DONE = '\uffff';
    
    char first();
    
    char last();
    
    char current();
    
    char next();
    
    char previous();
    
    char setIndex(final int p0);
    
    int getBeginIndex();
    
    int getEndIndex();
    
    int getIndex();
    
    Object clone();
}
