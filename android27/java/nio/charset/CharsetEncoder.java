package java.nio.charset;

import java.lang.ref.*;
import java.util.*;
import java.nio.*;

public abstract class CharsetEncoder
{
    private final Charset charset;
    private final float averageBytesPerChar;
    private final float maxBytesPerChar;
    private byte[] replacement;
    private CodingErrorAction malformedInputAction;
    private CodingErrorAction unmappableCharacterAction;
    private static final int ST_RESET = 0;
    private static final int ST_CODING = 1;
    private static final int ST_END = 2;
    private static final int ST_FLUSHED = 3;
    private int state;
    private static String[] stateNames;
    private WeakReference<CharsetDecoder> cachedDecoder;
    
    protected CharsetEncoder(final Charset charset, final float averageBytesPerChar, final float maxBytesPerChar, final byte[] replacement) {
        this.malformedInputAction = CodingErrorAction.REPORT;
        this.unmappableCharacterAction = CodingErrorAction.REPORT;
        this.state = 0;
        this.cachedDecoder = null;
        this.charset = charset;
        if (averageBytesPerChar <= 0.0f) {
            throw new IllegalArgumentException("Non-positive averageBytesPerChar");
        }
        if (maxBytesPerChar <= 0.0f) {
            throw new IllegalArgumentException("Non-positive maxBytesPerChar");
        }
        if (!Charset.atBugLevel("1.4") && averageBytesPerChar > maxBytesPerChar) {
            throw new IllegalArgumentException("averageBytesPerChar exceeds maxBytesPerChar");
        }
        this.replacement = replacement;
        this.averageBytesPerChar = averageBytesPerChar;
        this.maxBytesPerChar = maxBytesPerChar;
        this.replaceWith(replacement);
    }
    
    protected CharsetEncoder(final Charset charset, final float n, final float n2) {
        this(charset, n, n2, new byte[] { 63 });
    }
    
    public final Charset charset() {
        return this.charset;
    }
    
    public final byte[] replacement() {
        return Arrays.copyOf(this.replacement, this.replacement.length);
    }
    
    public final CharsetEncoder replaceWith(final byte[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Null replacement");
        }
        final int length = array.length;
        if (length == 0) {
            throw new IllegalArgumentException("Empty replacement");
        }
        if (length > this.maxBytesPerChar) {
            throw new IllegalArgumentException("Replacement too long");
        }
        if (!this.isLegalReplacement(array)) {
            throw new IllegalArgumentException("Illegal replacement");
        }
        this.implReplaceWith(this.replacement = Arrays.copyOf(array, array.length));
        return this;
    }
    
    protected void implReplaceWith(final byte[] array) {
    }
    
    public boolean isLegalReplacement(final byte[] array) {
        final WeakReference<CharsetDecoder> cachedDecoder = this.cachedDecoder;
        CharsetDecoder decoder;
        if (cachedDecoder == null || (decoder = cachedDecoder.get()) == null) {
            decoder = this.charset().newDecoder();
            decoder.onMalformedInput(CodingErrorAction.REPORT);
            decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
            this.cachedDecoder = new WeakReference<CharsetDecoder>(decoder);
        }
        else {
            decoder.reset();
        }
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        return !decoder.decode(wrap, CharBuffer.allocate((int)(wrap.remaining() * decoder.maxCharsPerByte())), true).isError();
    }
    
    public CodingErrorAction malformedInputAction() {
        return this.malformedInputAction;
    }
    
    public final CharsetEncoder onMalformedInput(final CodingErrorAction malformedInputAction) {
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
    
    public final CharsetEncoder onUnmappableCharacter(final CodingErrorAction unmappableCharacterAction) {
        if (unmappableCharacterAction == null) {
            throw new IllegalArgumentException("Null action");
        }
        this.implOnUnmappableCharacter(this.unmappableCharacterAction = unmappableCharacterAction);
        return this;
    }
    
    protected void implOnUnmappableCharacter(final CodingErrorAction codingErrorAction) {
    }
    
    public final float averageBytesPerChar() {
        return this.averageBytesPerChar;
    }
    
    public final float maxBytesPerChar() {
        return this.maxBytesPerChar;
    }
    
    public final CoderResult encode(final CharBuffer charBuffer, final ByteBuffer byteBuffer, final boolean b) {
        final int state = b ? 2 : 1;
        if (this.state != 0 && this.state != 1 && (!b || this.state != 2)) {
            this.throwIllegalStateException(this.state, state);
        }
        this.state = state;
        while (true) {
            CoderResult coderResult;
            try {
                coderResult = this.encodeLoop(charBuffer, byteBuffer);
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
                if (!b || !charBuffer.hasRemaining()) {
                    return coderResult;
                }
                coderResult = CoderResult.malformedForLength(charBuffer.remaining());
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
                if (byteBuffer.remaining() < this.replacement.length) {
                    return CoderResult.OVERFLOW;
                }
                byteBuffer.put(this.replacement);
            }
            if (codingErrorAction == CodingErrorAction.IGNORE || codingErrorAction == CodingErrorAction.REPLACE) {
                charBuffer.position(charBuffer.position() + coderResult.length());
            }
            else {
                assert false;
                continue;
            }
        }
    }
    
    public final CoderResult flush(final ByteBuffer byteBuffer) {
        if (this.state == 2) {
            final CoderResult implFlush = this.implFlush(byteBuffer);
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
    
    protected CoderResult implFlush(final ByteBuffer byteBuffer) {
        return CoderResult.UNDERFLOW;
    }
    
    public final CharsetEncoder reset() {
        this.implReset();
        this.state = 0;
        return this;
    }
    
    protected void implReset() {
    }
    
    protected abstract CoderResult encodeLoop(final CharBuffer p0, final ByteBuffer p1);
    
    public final ByteBuffer encode(final CharBuffer charBuffer) throws CharacterCodingException {
        int n = (int)(charBuffer.remaining() * this.averageBytesPerChar());
        ByteBuffer allocate = ByteBuffer.allocate(n);
        if (n == 0 && charBuffer.remaining() == 0) {
            return allocate;
        }
        this.reset();
        while (true) {
            CoderResult flush = charBuffer.hasRemaining() ? this.encode(charBuffer, allocate, true) : CoderResult.UNDERFLOW;
            if (flush.isUnderflow()) {
                flush = this.flush(allocate);
            }
            if (flush.isUnderflow()) {
                break;
            }
            if (flush.isOverflow()) {
                n = 2 * n + 1;
                final ByteBuffer allocate2 = ByteBuffer.allocate(n);
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
    
    private boolean canEncode(final CharBuffer charBuffer) {
        if (this.state == 3) {
            this.reset();
        }
        else if (this.state != 0) {
            this.throwIllegalStateException(this.state, 1);
        }
        final CodingErrorAction malformedInputAction = this.malformedInputAction();
        final CodingErrorAction unmappableCharacterAction = this.unmappableCharacterAction();
        try {
            this.onMalformedInput(CodingErrorAction.REPORT);
            this.onUnmappableCharacter(CodingErrorAction.REPORT);
            this.encode(charBuffer);
        }
        catch (CharacterCodingException ex) {
            return false;
        }
        finally {
            this.onMalformedInput(malformedInputAction);
            this.onUnmappableCharacter(unmappableCharacterAction);
            this.reset();
        }
        return true;
    }
    
    public boolean canEncode(final char c) {
        final CharBuffer allocate = CharBuffer.allocate(1);
        allocate.put(c);
        allocate.flip();
        return this.canEncode(allocate);
    }
    
    public boolean canEncode(final CharSequence charSequence) {
        CharBuffer charBuffer;
        if (charSequence instanceof CharBuffer) {
            charBuffer = ((CharBuffer)charSequence).duplicate();
        }
        else {
            charBuffer = CharBuffer.wrap(charSequence.toString());
        }
        return this.canEncode(charBuffer);
    }
    
    private void throwIllegalStateException(final int n, final int n2) {
        throw new IllegalStateException("Current state = " + CharsetEncoder.stateNames[n] + ", new state = " + CharsetEncoder.stateNames[n2]);
    }
    
    static {
        CharsetEncoder.stateNames = new String[] { "RESET", "CODING", "CODING_END", "FLUSHED" };
    }
}
