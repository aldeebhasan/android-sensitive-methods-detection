package java.lang;

import java.util.function.*;
import java.util.stream.*;
import java.util.*;

public interface CharSequence
{
    int length();
    
    char charAt(final int p0);
    
    CharSequence subSequence(final int p0, final int p1);
    
    String toString();
    
    default IntStream chars() {
        class CharIterator implements PrimitiveIterator.OfInt
        {
            int cur;
            
            CharIterator() {
                this.cur = 0;
            }
            
            @Override
            public boolean hasNext() {
                return this.cur < CharSequence.this.length();
            }
            
            @Override
            public int nextInt() {
                if (this.hasNext()) {
                    return CharSequence.this.charAt(this.cur++);
                }
                throw new NoSuchElementException();
            }
            
            @Override
            public void forEachRemaining(final IntConsumer intConsumer) {
                while (this.cur < CharSequence.this.length()) {
                    intConsumer.accept(CharSequence.this.charAt(this.cur));
                    ++this.cur;
                }
            }
        }
        
        return StreamSupport.intStream(() -> Spliterators.spliterator(new CharIterator(), this.length(), 16), 16464, false);
    }
    
    default IntStream codePoints() {
        class CodePointIterator implements PrimitiveIterator.OfInt
        {
            int cur;
            
            CodePointIterator() {
                this.cur = 0;
            }
            
            @Override
            public void forEachRemaining(final IntConsumer intConsumer) {
                final int length = CharSequence.this.length();
                int i = this.cur;
                try {
                    while (i < length) {
                        final char char1 = CharSequence.this.charAt(i++);
                        if (!Character.isHighSurrogate(char1) || i >= length) {
                            intConsumer.accept(char1);
                        }
                        else {
                            final char char2 = CharSequence.this.charAt(i);
                            if (Character.isLowSurrogate(char2)) {
                                ++i;
                                intConsumer.accept(Character.toCodePoint(char1, char2));
                            }
                            else {
                                intConsumer.accept(char1);
                            }
                        }
                    }
                }
                finally {
                    this.cur = i;
                }
            }
            
            @Override
            public boolean hasNext() {
                return this.cur < CharSequence.this.length();
            }
            
            @Override
            public int nextInt() {
                final int length = CharSequence.this.length();
                if (this.cur >= length) {
                    throw new NoSuchElementException();
                }
                final char char1 = CharSequence.this.charAt(this.cur++);
                if (Character.isHighSurrogate(char1) && this.cur < length) {
                    final char char2 = CharSequence.this.charAt(this.cur);
                    if (Character.isLowSurrogate(char2)) {
                        ++this.cur;
                        return Character.toCodePoint(char1, char2);
                    }
                }
                return char1;
            }
        }
        
        return StreamSupport.intStream(() -> Spliterators.spliteratorUnknownSize(new CodePointIterator(), 16), 16, false);
    }
}
