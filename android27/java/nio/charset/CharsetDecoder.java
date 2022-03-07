package java.nio.charset;

import java.nio.*;

public abstract class CharsetDecoder
{
    private final Charset charset;
    private final float averageCharsPerByte;
    private final float maxCharsPerByte;
    private String replacement;
    private CodingErrorAction malformedInputAction;
    private CodingErrorAction unmappableCharacterAction;
    private static final int ST_RESET = 0;
    private static final int ST_CODING = 1;
    private static final int ST_END = 2;
    private static final int ST_FLUSHED = 3;
    private int state;
    private static String[] stateNames;
    
    private CharsetDecoder(final Charset charset, final float averageCharsPerByte, final float maxCharsPerByte, final String replacement) {
        this.malformedInputAction = CodingErrorAction.REPORT;
        this.unmappableCharacterAction = CodingErrorAction.REPORT;
        this.state = 0;
        this.charset = charset;
        if (averageCharsPerByte <= 0.0f) {
            throw new IllegalArgumentException("Non-positive averageCharsPerByte");
        }
        if (maxCharsPerByte <= 0.0f) {
            throw new IllegalArgumentException("Non-positive maxCharsPerByte");
        }
        if (!Charset.atBugLevel("1.4") && averageCharsPerByte > maxCharsPerByte) {
            throw new IllegalArgumentException("averageCharsPerByte exceeds maxCharsPerByte");
        }
        this.replacement = replacement;
        this.averageCharsPerByte = averageCharsPerByte;
        this.maxCharsPerByte = maxCharsPerByte;
        this.replaceWith(replacement);
    }
    
    protected CharsetDecoder(final Charset charset, final float n, final float n2) {
        this(charset, n, n2, "\ufffd");
    }
    
    public final Charset charset() {
        return this.charset;
    }
    
    public final String replacement() {
        return this.replacement;
    }
    
    public final CharsetDecoder replaceWith(final String replacement) {
        if (replacement == null) {
            throw new IllegalArgumentException("Null replacement");
        }
        final int length = replacement.length();
        if (length == 0) {
            throw new IllegalArgumentException("Empty replacement");
        }
        if (length > this.maxCharsPerByte) {
            throw new IllegalArgumentException("Replacement too long");
        }
        this.implReplaceWith(this.replacement = replacement);
        return this;
    }
    
    protected void implReplaceWith(final String s) {
    }
    
    public CodingErrorAction malformedInputAction() {
        return this.malformedInputAction;
    }
    
    public final CharsetDecoder onMalformedInput(final CodingErrorAction malformedInputAction) {
        if (malformedInputAction == null) {
            throw new IllegalArgumentException("Null action");
        }
        this.implOnMalformedInput(this.malformedInputAction = malformedInputAction);
        return this;
    }
    
    protected void implOnMalformedInput(final CodingErrorAction codingErrorAction) {
    }
    
    public CodingErrorAction unmappableCharacterAction() {
        return this.unmappableCharacterAction;
    }
    
    public final CharsetDecoder onUnmappableCharacter(final CodingErrorAction unmappableCharacterAction) {
        if (unmappableCharacterAction == null) {
            throw new IllegalArgumentException("Null action");
        }
        this.implOnUnmappableCharacter(this.unmappableCharacterAction = unmappableCharacterAction);
        return this;
    }
    
    protected void implOnUnmappableCharacter(final CodingErrorAction codingErrorAction) {
    }
    
    public final float averageCharsPerByte() {
        return this.averageCharsPerByte;
    }
    
    public final float maxCharsPerByte() {
        return this.maxCharsPerByte;
    }
    
    public final CoderResult decode(final ByteBuffer byteBuffer, final CharBuffer charBuffer, final boolean b) {
        final int state = b ? 2 : 1;
        if (this.state != 0 && this.state != 1 && (!b || this.state != 2)) {
            this.throwIllegalStateException(this.state, state);
        }
        this.state = state;
        while (true) {
            CoderResult coderResult;
            try {
                coderResult = this.decodeLoop(byteBuffer, charBuffer);
            }
            catch (BufferUnderflowException ex) {
                throw new CoderMalfunctionError(ex);
            }
            catch (BufferOverflowException ex2) {
                throw new CoderMalfunctionError(ex2);
            }
            if (coderResult.isOverflow()) {
                return coderResult;
            }
            if (coderResult.isUnderflow()) {
                if (!b || !byteBuffer.hasRemaining()) {
                    return coderResult;
                }
                coderResult = CoderResult.malformedForLength(byteBuffer.remaining());
            }
            CodingErrorAction codingErrorAction = null;
            if (coderResult.isMalformed()) {
                codingErrorAction = this.malformedInputAction;
            }
            else if (coderResult.isUnmappable()) {
                codingErrorAction = this.unmappableCharacterAction;
            }
            else {
                assert false : coderResult.toString();
            }
            if (codingErrorAction == CodingErrorAction.REPORT) {
                return coderResult;
            }
            if (codingErrorAction == CodingErrorAction.REPLACE) {
                if (charBuffer.remaining() < this.replacement.length()) {
                    return CoderResult.OVERFLOW;
                }
                charBuffer.put(this.replacement);
            }
            if (codingErrorAction == CodingErrorAction.IGNORE || codingErrorAction == CodingErrorAction.REPLACE) {
                byteBuffer.position(byteBuffer.position() + coderResult.length());
            }
            else {
                assert false;
                continue;
            }
        }
    }
    
    public final CoderResult flush(final CharBuffer charBuffer) {
        if (this.state == 2) {
            final CoderResult implFlush = this.implFlush(charBuffer);
            if (implFlush.isUnderflow()) {
                this.state = 3;
            }
            return implFlush;
        }
        if (this.state != 3) {
            this.throwIllegalStateException(this.state, 3);
        }
        return CoderResult.UNDERFLOW;
    }
    
    protected CoderResult implFlush(final CharBuffer charBuffer) {
        return CoderResult.UNDERFLOW;
    }
    
    public final CharsetDecoder reset() {
        this.implReset();
        this.state = 0;
        return this;
    }
    
    protected void implReset() {
    }
    
    protected abstract CoderResult decodeLoop(final ByteBuffer p0, final CharBuffer p1);
    
    public final CharBuffer decode(final ByteBuffer byteBuffer) throws CharacterCodingException {
        int n = (int)(byteBuffer.remaining() * this.averageCharsPerByte());
        CharBuffer allocate = CharBuffer.allocate(n);
        if (n == 0 && byteBuffer.remaining() == 0) {
            return allocate;
        }
        this.reset();
        while (true) {
            CoderResult flush = byteBuffer.hasRemaining() ? this.decode(byteBuffer, allocate, true) : CoderResult.UNDERFLOW;
            if (flush.isUnderflow()) {
                flush = this.flush(allocate);
            }
            if (flush.isUnderflow()) {
                break;
            }
            if (flush.isOverflow()) {
                n = 2 * n + 1;
                final CharBuffer allocate2 = CharBuffer.allocate(n);
                allocate.flip();
                allocate2.put(allocate);
                allocate = allocate2;
            }
            else {
                flush.throwException();
            }
        }
        allocate.flip();
        return allocate;
    }
    
    public boolean isAutoDetecting() {
        return false;
    }
    
    public boolean isCharsetDetected() {
        throw new UnsupportedOperationException();
    }
    
    public Charset detectedCharset() {
        throw new UnsupportedOperationException();
    }
    
    private void throwIllegalStateException(final int n, final int n2) {
        throw new IllegalStateException("Current state = " + CharsetDecoder.stateNames[n] + ", new state = " + CharsetDecoder.stateNames[n2]);
    }
    
    static {
        CharsetDecoder.stateNames = new String[] { "RESET", "CODING", "CODING_END", "FLUSHED" };
    }
}
