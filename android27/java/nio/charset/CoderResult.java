package java.nio.charset;

import java.nio.*;
import java.lang.ref.*;
import java.util.*;

public class CoderResult
{
    private static final int CR_UNDERFLOW = 0;
    private static final int CR_OVERFLOW = 1;
    private static final int CR_ERROR_MIN = 2;
    private static final int CR_MALFORMED = 2;
    private static final int CR_UNMAPPABLE = 3;
    private static final String[] names;
    private final int type;
    private final int length;
    public static final CoderResult UNDERFLOW;
    public static final CoderResult OVERFLOW;
    private static Cache malformedCache;
    private static Cache unmappableCache;
    
    private CoderResult(final int type, final int length) {
        this.type = type;
        this.length = length;
    }
    
    @Override
    public String toString() {
        final String s = CoderResult.names[this.type];
        return this.isError() ? (s + "[" + this.length + "]") : s;
    }
    
    public boolean isUnderflow() {
        return this.type == 0;
    }
    
    public boolean isOverflow() {
        return this.type == 1;
    }
    
    public boolean isError() {
        return this.type >= 2;
    }
    
    public boolean isMalformed() {
        return this.type == 2;
    }
    
    public boolean isUnmappable() {
        return this.type == 3;
    }
    
    public int length() {
        if (!this.isError()) {
            throw new UnsupportedOperationException();
        }
        return this.length;
    }
    
    public static CoderResult malformedForLength(final int n) {
        return CoderResult.malformedCache.get(n);
    }
    
    public static CoderResult unmappableForLength(final int n) {
        return CoderResult.unmappableCache.get(n);
    }
    
    public void throwException() throws CharacterCodingException {
        switch (this.type) {
            case 0: {
                throw new BufferUnderflowException();
            }
            case 1: {
                throw new BufferOverflowException();
            }
            case 2: {
                throw new MalformedInputException(this.length);
            }
            case 3: {
                throw new UnmappableCharacterException(this.length);
            }
            default: {
                assert false;
            }
        }
    }
    
    static {
        names = new String[] { "UNDERFLOW", "OVERFLOW", "MALFORMED", "UNMAPPABLE" };
        UNDERFLOW = new CoderResult(0, 0);
        OVERFLOW = new CoderResult(1, 0);
        CoderResult.malformedCache = new Cache() {
            public CoderResult create(final int n) {
                return new CoderResult(2, n, null);
            }
        };
        CoderResult.unmappableCache = new Cache() {
            public CoderResult create(final int n) {
                return new CoderResult(3, n, null);
            }
        };
    }
    
    private abstract static class Cache
    {
        private Map<Integer, WeakReference<CoderResult>> cache;
        
        private Cache() {
            this.cache = null;
        }
        
        protected abstract CoderResult create(final int p0);
        
        private synchronized CoderResult get(final int n) {
            if (n <= 0) {
                throw new IllegalArgumentException("Non-positive length");
            }
            final Integer n2 = new Integer(n);
            CoderResult create = null;
            if (this.cache == null) {
                this.cache = new HashMap<Integer, WeakReference<CoderResult>>();
            }
            else {
                final WeakReference<CoderResult> weakReference;
                if ((weakReference = this.cache.get(n2)) != null) {
                    create = weakReference.get();
                }
            }
            if (create == null) {
                create = this.create(n);
                this.cache.put(n2, new WeakReference<CoderResult>(create));
            }
            return create;
        }
    }
}
