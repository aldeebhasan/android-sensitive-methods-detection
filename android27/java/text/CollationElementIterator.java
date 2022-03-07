package java.text;

import sun.text.normalizer.*;
import sun.text.*;
import java.util.*;

public final class CollationElementIterator
{
    public static final int NULLORDER = -1;
    static final int UNMAPPEDCHARVALUE = 2147418112;
    private NormalizerBase text;
    private int[] buffer;
    private int expIndex;
    private StringBuffer key;
    private int swapOrder;
    private RBCollationTables ordering;
    private RuleBasedCollator owner;
    
    CollationElementIterator(final String s, final RuleBasedCollator owner) {
        this.text = null;
        this.buffer = null;
        this.expIndex = 0;
        this.key = new StringBuffer(5);
        this.swapOrder = 0;
        this.owner = owner;
        this.ordering = owner.getTables();
        if (s.length() != 0) {
            this.text = new NormalizerBase(s, CollatorUtilities.toNormalizerMode(owner.getDecomposition()));
        }
    }
    
    CollationElementIterator(final CharacterIterator characterIterator, final RuleBasedCollator owner) {
        this.text = null;
        this.buffer = null;
        this.expIndex = 0;
        this.key = new StringBuffer(5);
        this.swapOrder = 0;
        this.owner = owner;
        this.ordering = owner.getTables();
        this.text = new NormalizerBase(characterIterator, CollatorUtilities.toNormalizerMode(owner.getDecomposition()));
    }
    
    public void reset() {
        if (this.text != null) {
            this.text.reset();
            this.text.setMode(CollatorUtilities.toNormalizerMode(this.owner.getDecomposition()));
        }
        this.buffer = null;
        this.expIndex = 0;
        this.swapOrder = 0;
    }
    
    public int next() {
        if (this.text == null) {
            return -1;
        }
        final NormalizerBase.Mode mode = this.text.getMode();
        final NormalizerBase.Mode normalizerMode = CollatorUtilities.toNormalizerMode(this.owner.getDecomposition());
        if (mode != normalizerMode) {
            this.text.setMode(normalizerMode);
        }
        if (this.buffer != null) {
            if (this.expIndex < this.buffer.length) {
                return this.strengthOrder(this.buffer[this.expIndex++]);
            }
            this.buffer = null;
            this.expIndex = 0;
        }
        else if (this.swapOrder != 0) {
            if (Character.isSupplementaryCodePoint(this.swapOrder)) {
                final char[] chars = Character.toChars(this.swapOrder);
                this.swapOrder = chars[1];
                return chars[0] << 16;
            }
            final int n = this.swapOrder << 16;
            this.swapOrder = 0;
            return n;
        }
        final int next = this.text.next();
        if (next == -1) {
            return -1;
        }
        int n2 = this.ordering.getUnicodeOrder(next);
        if (n2 == -1) {
            this.swapOrder = next;
            return 2147418112;
        }
        if (n2 >= 2130706432) {
            n2 = this.nextContractChar(next);
        }
        if (n2 >= 2113929216) {
            this.buffer = this.ordering.getExpandValueList(n2);
            this.expIndex = 0;
            n2 = this.buffer[this.expIndex++];
        }
        if (this.ordering.isSEAsianSwapping()) {
            if (isThaiPreVowel(next)) {
                final int next2 = this.text.next();
                if (isThaiBaseConsonant(next2)) {
                    this.buffer = this.makeReorderedBuffer(next2, n2, this.buffer, true);
                    n2 = this.buffer[0];
                    this.expIndex = 1;
                }
                else if (next2 != -1) {
                    this.text.previous();
                }
            }
            if (isLaoPreVowel(next)) {
                final int next3 = this.text.next();
                if (isLaoBaseConsonant(next3)) {
                    this.buffer = this.makeReorderedBuffer(next3, n2, this.buffer, true);
                    n2 = this.buffer[0];
                    this.expIndex = 1;
                }
                else if (next3 != -1) {
                    this.text.previous();
                }
            }
        }
        return this.strengthOrder(n2);
    }
    
    public int previous() {
        if (this.text == null) {
            return -1;
        }
        final NormalizerBase.Mode mode = this.text.getMode();
        final NormalizerBase.Mode normalizerMode = CollatorUtilities.toNormalizerMode(this.owner.getDecomposition());
        if (mode != normalizerMode) {
            this.text.setMode(normalizerMode);
        }
        if (this.buffer != null) {
            if (this.expIndex > 0) {
                final int[] buffer = this.buffer;
                final int expIndex = this.expIndex - 1;
                this.expIndex = expIndex;
                return this.strengthOrder(buffer[expIndex]);
            }
            this.buffer = null;
            this.expIndex = 0;
        }
        else if (this.swapOrder != 0) {
            if (Character.isSupplementaryCodePoint(this.swapOrder)) {
                final char[] chars = Character.toChars(this.swapOrder);
                this.swapOrder = chars[1];
                return chars[0] << 16;
            }
            final int n = this.swapOrder << 16;
            this.swapOrder = 0;
            return n;
        }
        final int previous = this.text.previous();
        if (previous == -1) {
            return -1;
        }
        int n2 = this.ordering.getUnicodeOrder(previous);
        if (n2 == -1) {
            this.swapOrder = 2147418112;
            return previous;
        }
        if (n2 >= 2130706432) {
            n2 = this.prevContractChar(previous);
        }
        if (n2 >= 2113929216) {
            this.buffer = this.ordering.getExpandValueList(n2);
            this.expIndex = this.buffer.length;
            final int[] buffer2 = this.buffer;
            final int expIndex2 = this.expIndex - 1;
            this.expIndex = expIndex2;
            n2 = buffer2[expIndex2];
        }
        if (this.ordering.isSEAsianSwapping()) {
            if (isThaiBaseConsonant(previous)) {
                final int previous2 = this.text.previous();
                if (isThaiPreVowel(previous2)) {
                    this.buffer = this.makeReorderedBuffer(previous2, n2, this.buffer, false);
                    this.expIndex = this.buffer.length - 1;
                    n2 = this.buffer[this.expIndex];
                }
                else {
                    this.text.next();
                }
            }
            if (isLaoBaseConsonant(previous)) {
                final int previous3 = this.text.previous();
                if (isLaoPreVowel(previous3)) {
                    this.buffer = this.makeReorderedBuffer(previous3, n2, this.buffer, false);
                    this.expIndex = this.buffer.length - 1;
                    n2 = this.buffer[this.expIndex];
                }
                else {
                    this.text.next();
                }
            }
        }
        return this.strengthOrder(n2);
    }
    
    public static final int primaryOrder(int n) {
        n &= 0xFFFF0000;
        return n >>> 16;
    }
    
    public static final short secondaryOrder(int n) {
        n &= 0xFF00;
        return (short)(n >> 8);
    }
    
    public static final short tertiaryOrder(int n) {
        return (short)(n &= 0xFF);
    }
    
    final int strengthOrder(int n) {
        final int strength = this.owner.getStrength();
        if (strength == 0) {
            n &= 0xFFFF0000;
        }
        else if (strength == 1) {
            n &= 0xFFFFFF00;
        }
        return n;
    }
    
    public void setOffset(final int n) {
        if (this.text != null) {
            if (n < this.text.getBeginIndex() || n >= this.text.getEndIndex()) {
                this.text.setIndexOnly(n);
            }
            else {
                int n2 = this.text.setIndex(n);
                if (this.ordering.usedInContractSeq(n2)) {
                    while (this.ordering.usedInContractSeq(n2)) {
                        n2 = this.text.previous();
                    }
                    int indexOnly = this.text.getIndex();
                    while (this.text.getIndex() <= n) {
                        indexOnly = this.text.getIndex();
                        this.next();
                    }
                    this.text.setIndexOnly(indexOnly);
                }
            }
        }
        this.buffer = null;
        this.expIndex = 0;
        this.swapOrder = 0;
    }
    
    public int getOffset() {
        return (this.text != null) ? this.text.getIndex() : 0;
    }
    
    public int getMaxExpansion(final int n) {
        return this.ordering.getMaxExpansion(n);
    }
    
    public void setText(final String text) {
        this.buffer = null;
        this.swapOrder = 0;
        this.expIndex = 0;
        final NormalizerBase.Mode normalizerMode = CollatorUtilities.toNormalizerMode(this.owner.getDecomposition());
        if (this.text == null) {
            this.text = new NormalizerBase(text, normalizerMode);
        }
        else {
            this.text.setMode(normalizerMode);
            this.text.setText(text);
        }
    }
    
    public void setText(final CharacterIterator text) {
        this.buffer = null;
        this.swapOrder = 0;
        this.expIndex = 0;
        final NormalizerBase.Mode normalizerMode = CollatorUtilities.toNormalizerMode(this.owner.getDecomposition());
        if (this.text == null) {
            this.text = new NormalizerBase(text, normalizerMode);
        }
        else {
            this.text.setMode(normalizerMode);
            this.text.setText(text);
        }
    }
    
    private static final boolean isThaiPreVowel(final int n) {
        return n >= 3648 && n <= 3652;
    }
    
    private static final boolean isThaiBaseConsonant(final int n) {
        return n >= 3585 && n <= 3630;
    }
    
    private static final boolean isLaoPreVowel(final int n) {
        return n >= 3776 && n <= 3780;
    }
    
    private static final boolean isLaoBaseConsonant(final int n) {
        return n >= 3713 && n <= 3758;
    }
    
    private int[] makeReorderedBuffer(final int n, int n2, int[] array, final boolean b) {
        int unicodeOrder = this.ordering.getUnicodeOrder(n);
        if (unicodeOrder >= 2130706432) {
            unicodeOrder = (b ? this.nextContractChar(n) : this.prevContractChar(n));
        }
        int[] expandValueList = null;
        if (unicodeOrder >= 2113929216) {
            expandValueList = this.ordering.getExpandValueList(unicodeOrder);
        }
        if (!b) {
            final int n3 = unicodeOrder;
            unicodeOrder = n2;
            n2 = n3;
            final int[] array2 = expandValueList;
            expandValueList = array;
            array = array2;
        }
        int[] array3;
        if (expandValueList == null && array == null) {
            array3 = new int[] { unicodeOrder, n2 };
        }
        else {
            final int n4 = (expandValueList == null) ? 1 : expandValueList.length;
            final int n5 = (array == null) ? 1 : array.length;
            array3 = new int[n4 + n5];
            if (expandValueList == null) {
                array3[0] = unicodeOrder;
            }
            else {
                System.arraycopy(expandValueList, 0, array3, 0, n4);
            }
            if (array == null) {
                array3[n4] = n2;
            }
            else {
                System.arraycopy(array, 0, array3, n4, n5);
            }
        }
        return array3;
    }
    
    static final boolean isIgnorable(final int n) {
        return primaryOrder(n) == 0;
    }
    
    private int nextContractChar(final int n) {
        final Vector<EntryPair> contractValues = this.ordering.getContractValues(n);
        int n2 = contractValues.firstElement().value;
        int length = contractValues.lastElement().entryName.length();
        final NormalizerBase normalizerBase = (NormalizerBase)this.text.clone();
        normalizerBase.previous();
        this.key.setLength(0);
        for (int n3 = normalizerBase.next(); length > 0 && n3 != -1; n3 = normalizerBase.next()) {
            if (Character.isSupplementaryCodePoint(n3)) {
                this.key.append(Character.toChars(n3));
                length -= 2;
            }
            else {
                this.key.append((char)n3);
                --length;
            }
        }
        final String string = this.key.toString();
        int i = 1;
        for (int j = contractValues.size() - 1; j > 0; --j) {
            final EntryPair entryPair = contractValues.elementAt(j);
            if (entryPair.fwd) {
                if (string.startsWith(entryPair.entryName) && entryPair.entryName.length() > i) {
                    i = entryPair.entryName.length();
                    n2 = entryPair.value;
                }
            }
        }
        while (i > 1) {
            i -= Character.charCount(this.text.next());
        }
        return n2;
    }
    
    private int prevContractChar(final int n) {
        final Vector<EntryPair> contractValues = this.ordering.getContractValues(n);
        int n2 = contractValues.firstElement().value;
        int length = contractValues.lastElement().entryName.length();
        final NormalizerBase normalizerBase = (NormalizerBase)this.text.clone();
        normalizerBase.next();
        this.key.setLength(0);
        for (int n3 = normalizerBase.previous(); length > 0 && n3 != -1; n3 = normalizerBase.previous()) {
            if (Character.isSupplementaryCodePoint(n3)) {
                this.key.append(Character.toChars(n3));
                length -= 2;
            }
            else {
                this.key.append((char)n3);
                --length;
            }
        }
        final String string = this.key.toString();
        int i = 1;
        for (int j = contractValues.size() - 1; j > 0; --j) {
            final EntryPair entryPair = contractValues.elementAt(j);
            if (!entryPair.fwd) {
                if (string.startsWith(entryPair.entryName) && entryPair.entryName.length() > i) {
                    i = entryPair.entryName.length();
                    n2 = entryPair.value;
                }
            }
        }
        while (i > 1) {
            i -= Character.charCount(this.text.previous());
        }
        return n2;
    }
}
