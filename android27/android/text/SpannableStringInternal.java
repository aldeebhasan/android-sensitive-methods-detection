package android.text;

abstract class SpannableStringInternal
{
    SpannableStringInternal() {
        throw new RuntimeException("Stub!");
    }
    
    public final int length() {
        throw new RuntimeException("Stub!");
    }
    
    public final char charAt(final int i) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public final String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public final void getChars(final int start, final int end, final char[] dest, final int off) {
        throw new RuntimeException("Stub!");
    }
    
    public int getSpanStart(final Object what) {
        throw new RuntimeException("Stub!");
    }
    
    public int getSpanEnd(final Object what) {
        throw new RuntimeException("Stub!");
    }
    
    public int getSpanFlags(final Object what) {
        throw new RuntimeException("Stub!");
    }
    
    public <T> T[] getSpans(final int queryStart, final int queryEnd, final Class<T> kind) {
        throw new RuntimeException("Stub!");
    }
    
    public int nextSpanTransition(final int start, final int limit, final Class kind) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
}
