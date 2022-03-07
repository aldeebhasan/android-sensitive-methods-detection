package java.text;

import sun.text.bidi.*;

public final class Bidi
{
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int DIRECTION_RIGHT_TO_LEFT = 1;
    public static final int DIRECTION_DEFAULT_LEFT_TO_RIGHT = -2;
    public static final int DIRECTION_DEFAULT_RIGHT_TO_LEFT = -1;
    private BidiBase bidiBase;
    
    public Bidi(final String s, final int n) {
        if (s == null) {
            throw new IllegalArgumentException("paragraph is null");
        }
        this.bidiBase = new BidiBase(s.toCharArray(), 0, null, 0, s.length(), n);
    }
    
    public Bidi(final AttributedCharacterIterator para) {
        if (para == null) {
            throw new IllegalArgumentException("paragraph is null");
        }
        (this.bidiBase = new BidiBase(0, 0)).setPara(para);
    }
    
    public Bidi(final char[] array, final int n, final byte[] array2, final int n2, final int n3, final int n4) {
        if (array == null) {
            throw new IllegalArgumentException("text is null");
        }
        if (n3 < 0) {
            throw new IllegalArgumentException("bad length: " + n3);
        }
        if (n < 0 || n3 > array.length - n) {
            throw new IllegalArgumentException("bad range: " + n + " length: " + n3 + " for text of length: " + array.length);
        }
        if (array2 != null && (n2 < 0 || n3 > array2.length - n2)) {
            throw new IllegalArgumentException("bad range: " + n2 + " length: " + n3 + " for embeddings of length: " + array.length);
        }
        this.bidiBase = new BidiBase(array, n, array2, n2, n3, n4);
    }
    
    public Bidi createLineBidi(final int n, final int n2) {
        final Bidi bidi = new Bidi(new AttributedString("").getIterator());
        return this.bidiBase.setLine(this, this.bidiBase, bidi, bidi.bidiBase, n, n2);
    }
    
    public boolean isMixed() {
        return this.bidiBase.isMixed();
    }
    
    public boolean isLeftToRight() {
        return this.bidiBase.isLeftToRight();
    }
    
    public boolean isRightToLeft() {
        return this.bidiBase.isRightToLeft();
    }
    
    public int getLength() {
        return this.bidiBase.getLength();
    }
    
    public boolean baseIsLeftToRight() {
        return this.bidiBase.baseIsLeftToRight();
    }
    
    public int getBaseLevel() {
        return this.bidiBase.getParaLevel();
    }
    
    public int getLevelAt(final int n) {
        return this.bidiBase.getLevelAt(n);
    }
    
    public int getRunCount() {
        return this.bidiBase.countRuns();
    }
    
    public int getRunLevel(final int n) {
        return this.bidiBase.getRunLevel(n);
    }
    
    public int getRunStart(final int n) {
        return this.bidiBase.getRunStart(n);
    }
    
    public int getRunLimit(final int n) {
        return this.bidiBase.getRunLimit(n);
    }
    
    public static boolean requiresBidi(final char[] array, final int n, final int n2) {
        return BidiBase.requiresBidi(array, n, n2);
    }
    
    public static void reorderVisually(final byte[] array, final int n, final Object[] array2, final int n2, final int n3) {
        BidiBase.reorderVisually(array, n, array2, n2, n3);
    }
    
    @Override
    public String toString() {
        return this.bidiBase.toString();
    }
}
