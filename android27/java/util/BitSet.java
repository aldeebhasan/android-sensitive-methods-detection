package java.util;

import java.nio.*;
import java.io.*;
import java.util.stream.*;

public class BitSet implements Cloneable, Serializable
{
    private static final int ADDRESS_BITS_PER_WORD = 6;
    private static final int BITS_PER_WORD = 64;
    private static final int BIT_INDEX_MASK = 63;
    private static final long WORD_MASK = -1L;
    private static final ObjectStreamField[] serialPersistentFields;
    private long[] words;
    private transient int wordsInUse;
    private transient boolean sizeIsSticky;
    private static final long serialVersionUID = 7997698588986878753L;
    
    private static int wordIndex(final int n) {
        return n >> 6;
    }
    
    private void checkInvariants() {
        assert this.words[this.wordsInUse - 1] != 0L;
        assert this.wordsInUse >= 0 && this.wordsInUse <= this.words.length;
        assert this.words[this.wordsInUse] == 0L;
    }
    
    private void recalculateWordsInUse() {
        int n;
        for (n = this.wordsInUse - 1; n >= 0 && this.words[n] == 0L; --n) {}
        this.wordsInUse = n + 1;
    }
    
    public BitSet() {
        this.wordsInUse = 0;
        this.sizeIsSticky = false;
        this.initWords(64);
        this.sizeIsSticky = false;
    }
    
    public BitSet(final int n) {
        this.wordsInUse = 0;
        this.sizeIsSticky = false;
        if (n < 0) {
            throw new NegativeArraySizeException("nbits < 0: " + n);
        }
        this.initWords(n);
        this.sizeIsSticky = true;
    }
    
    private void initWords(final int n) {
        this.words = new long[wordIndex(n - 1) + 1];
    }
    
    private BitSet(final long[] words) {
        this.wordsInUse = 0;
        this.sizeIsSticky = false;
        this.words = words;
        this.wordsInUse = words.length;
        this.checkInvariants();
    }
    
    public static BitSet valueOf(final long[] array) {
        int length;
        for (length = array.length; length > 0 && array[length - 1] == 0L; --length) {}
        return new BitSet(Arrays.copyOf(array, length));
    }
    
    public static BitSet valueOf(LongBuffer slice) {
        int remaining;
        for (slice = slice.slice(), remaining = slice.remaining(); remaining > 0 && slice.get(remaining - 1) == 0L; --remaining) {}
        final long[] array = new long[remaining];
        slice.get(array);
        return new BitSet(array);
    }
    
    public static BitSet valueOf(final byte[] array) {
        return valueOf(ByteBuffer.wrap(array));
    }
    
    public static BitSet valueOf(ByteBuffer order) {
        int remaining;
        for (order = order.slice().order(ByteOrder.LITTLE_ENDIAN), remaining = order.remaining(); remaining > 0 && order.get(remaining - 1) == 0; --remaining) {}
        final long[] array = new long[(remaining + 7) / 8];
        order.limit(remaining);
        int n = 0;
        while (order.remaining() >= 8) {
            array[n++] = order.getLong();
        }
        for (int remaining2 = order.remaining(), i = 0; i < remaining2; ++i) {
            final long[] array2 = array;
            final int n2 = n;
            array2[n2] |= (order.get() & 0xFFL) << 8 * i;
        }
        return new BitSet(array);
    }
    
    public byte[] toByteArray() {
        final int wordsInUse = this.wordsInUse;
        if (wordsInUse == 0) {
            return new byte[0];
        }
        int n = 8 * (wordsInUse - 1);
        for (long n2 = this.words[wordsInUse - 1]; n2 != 0L; n2 >>>= 8) {
            ++n;
        }
        final byte[] array = new byte[n];
        final ByteBuffer order = ByteBuffer.wrap(array).order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < wordsInUse - 1; ++i) {
            order.putLong(this.words[i]);
        }
        for (long n3 = this.words[wordsInUse - 1]; n3 != 0L; n3 >>>= 8) {
            order.put((byte)(n3 & 0xFFL));
        }
        return array;
    }
    
    public long[] toLongArray() {
        return Arrays.copyOf(this.words, this.wordsInUse);
    }
    
    private void ensureCapacity(final int n) {
        if (this.words.length < n) {
            this.words = Arrays.copyOf(this.words, Math.max(2 * this.words.length, n));
            this.sizeIsSticky = false;
        }
    }
    
    private void expandTo(final int n) {
        final int wordsInUse = n + 1;
        if (this.wordsInUse < wordsInUse) {
            this.ensureCapacity(wordsInUse);
            this.wordsInUse = wordsInUse;
        }
    }
    
    private static void checkRange(final int n, final int n2) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("fromIndex < 0: " + n);
        }
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("toIndex < 0: " + n2);
        }
        if (n > n2) {
            throw new IndexOutOfBoundsException("fromIndex: " + n + " > toIndex: " + n2);
        }
    }
    
    public void flip(final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("bitIndex < 0: " + n);
        }
        final int wordIndex = wordIndex(n);
        this.expandTo(wordIndex);
        final long[] words = this.words;
        final int n2 = wordIndex;
        words[n2] ^= 1L << n;
        this.recalculateWordsInUse();
        this.checkInvariants();
    }
    
    public void flip(final int n, final int n2) {
        checkRange(n, n2);
        if (n == n2) {
            return;
        }
        final int wordIndex = wordIndex(n);
        final int wordIndex2 = wordIndex(n2 - 1);
        this.expandTo(wordIndex2);
        final long n3 = -1L << n;
        final long n4 = -1L >>> -n2;
        if (wordIndex == wordIndex2) {
            final long[] words = this.words;
            final int n5 = wordIndex;
            words[n5] ^= (n3 & n4);
        }
        else {
            final long[] words2 = this.words;
            final int n6 = wordIndex;
            words2[n6] ^= n3;
            for (int i = wordIndex + 1; i < wordIndex2; ++i) {
                final long[] words3 = this.words;
                final int n7 = i;
                words3[n7] ^= -1L;
            }
            final long[] words4 = this.words;
            final int n8 = wordIndex2;
            words4[n8] ^= n4;
        }
        this.recalculateWordsInUse();
        this.checkInvariants();
    }
    
    public void set(final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("bitIndex < 0: " + n);
        }
        final int wordIndex = wordIndex(n);
        this.expandTo(wordIndex);
        final long[] words = this.words;
        final int n2 = wordIndex;
        words[n2] |= 1L << n;
        this.checkInvariants();
    }
    
    public void set(final int n, final boolean b) {
        if (b) {
            this.set(n);
        }
        else {
            this.clear(n);
        }
    }
    
    public void set(final int n, final int n2) {
        checkRange(n, n2);
        if (n == n2) {
            return;
        }
        final int wordIndex = wordIndex(n);
        final int wordIndex2 = wordIndex(n2 - 1);
        this.expandTo(wordIndex2);
        final long n3 = -1L << n;
        final long n4 = -1L >>> -n2;
        if (wordIndex == wordIndex2) {
            final long[] words = this.words;
            final int n5 = wordIndex;
            words[n5] |= (n3 & n4);
        }
        else {
            final long[] words2 = this.words;
            final int n6 = wordIndex;
            words2[n6] |= n3;
            for (int i = wordIndex + 1; i < wordIndex2; ++i) {
                this.words[i] = -1L;
            }
            final long[] words3 = this.words;
            final int n7 = wordIndex2;
            words3[n7] |= n4;
        }
        this.checkInvariants();
    }
    
    public void set(final int n, final int n2, final boolean b) {
        if (b) {
            this.set(n, n2);
        }
        else {
            this.clear(n, n2);
        }
    }
    
    public void clear(final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("bitIndex < 0: " + n);
        }
        final int wordIndex = wordIndex(n);
        if (wordIndex >= this.wordsInUse) {
            return;
        }
        final long[] words = this.words;
        final int n2 = wordIndex;
        words[n2] &= ~(1L << n);
        this.recalculateWordsInUse();
        this.checkInvariants();
    }
    
    public void clear(final int n, int length) {
        checkRange(n, length);
        if (n == length) {
            return;
        }
        final int wordIndex = wordIndex(n);
        if (wordIndex >= this.wordsInUse) {
            return;
        }
        int wordIndex2 = wordIndex(length - 1);
        if (wordIndex2 >= this.wordsInUse) {
            length = this.length();
            wordIndex2 = this.wordsInUse - 1;
        }
        final long n2 = -1L << n;
        final long n3 = -1L >>> -length;
        if (wordIndex == wordIndex2) {
            final long[] words = this.words;
            final int n4 = wordIndex;
            words[n4] &= ~(n2 & n3);
        }
        else {
            final long[] words2 = this.words;
            final int n5 = wordIndex;
            words2[n5] &= ~n2;
            for (int i = wordIndex + 1; i < wordIndex2; ++i) {
                this.words[i] = 0L;
            }
            final long[] words3 = this.words;
            final int n6 = wordIndex2;
            words3[n6] &= ~n3;
        }
        this.recalculateWordsInUse();
        this.checkInvariants();
    }
    
    public void clear() {
        while (this.wordsInUse > 0) {
            this.words[--this.wordsInUse] = 0L;
        }
    }
    
    public boolean get(final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("bitIndex < 0: " + n);
        }
        this.checkInvariants();
        final int wordIndex = wordIndex(n);
        return wordIndex < this.wordsInUse && (this.words[wordIndex] & 1L << n) != 0x0L;
    }
    
    public BitSet get(final int n, int n2) {
        checkRange(n, n2);
        this.checkInvariants();
        final int length = this.length();
        if (length <= n || n == n2) {
            return new BitSet(0);
        }
        if (n2 > length) {
            n2 = length;
        }
        final BitSet set = new BitSet(n2 - n);
        final int wordsInUse = wordIndex(n2 - n - 1) + 1;
        int wordIndex = wordIndex(n);
        final boolean b = (n & 0x3F) == 0x0;
        for (int i = 0; i < wordsInUse - 1; ++i, ++wordIndex) {
            set.words[i] = (b ? this.words[wordIndex] : (this.words[wordIndex] >>> n | this.words[wordIndex + 1] << -n));
        }
        final long n3 = -1L >>> -n2;
        set.words[wordsInUse - 1] = (((n2 - 1 & 0x3F) < (n & 0x3F)) ? (this.words[wordIndex] >>> n | (this.words[wordIndex + 1] & n3) << -n) : ((this.words[wordIndex] & n3) >>> n));
        set.wordsInUse = wordsInUse;
        set.recalculateWordsInUse();
        set.checkInvariants();
        return set;
    }
    
    public int nextSetBit(final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("fromIndex < 0: " + n);
        }
        this.checkInvariants();
        int wordIndex = wordIndex(n);
        if (wordIndex >= this.wordsInUse) {
            return -1;
        }
        long n2;
        for (n2 = (this.words[wordIndex] & -1L << n); n2 == 0L; n2 = this.words[wordIndex]) {
            if (++wordIndex == this.wordsInUse) {
                return -1;
            }
        }
        return wordIndex * 64 + Long.numberOfTrailingZeros(n2);
    }
    
    public int nextClearBit(final int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("fromIndex < 0: " + n);
        }
        this.checkInvariants();
        int wordIndex = wordIndex(n);
        if (wordIndex >= this.wordsInUse) {
            return n;
        }
        long n2;
        for (n2 = (~this.words[wordIndex] & -1L << n); n2 == 0L; n2 = ~this.words[wordIndex]) {
            if (++wordIndex == this.wordsInUse) {
                return this.wordsInUse * 64;
            }
        }
        return wordIndex * 64 + Long.numberOfTrailingZeros(n2);
    }
    
    public int previousSetBit(final int n) {
        if (n < 0) {
            if (n == -1) {
                return -1;
            }
            throw new IndexOutOfBoundsException("fromIndex < -1: " + n);
        }
        else {
            this.checkInvariants();
            int wordIndex = wordIndex(n);
            if (wordIndex >= this.wordsInUse) {
                return this.length() - 1;
            }
            long n2;
            for (n2 = (this.words[wordIndex] & -1L >>> -(n + 1)); n2 == 0L; n2 = this.words[wordIndex]) {
                if (wordIndex-- == 0) {
                    return -1;
                }
            }
            return (wordIndex + 1) * 64 - 1 - Long.numberOfLeadingZeros(n2);
        }
    }
    
    public int previousClearBit(final int n) {
        if (n < 0) {
            if (n == -1) {
                return -1;
            }
            throw new IndexOutOfBoundsException("fromIndex < -1: " + n);
        }
        else {
            this.checkInvariants();
            int wordIndex = wordIndex(n);
            if (wordIndex >= this.wordsInUse) {
                return n;
            }
            long n2;
            for (n2 = (~this.words[wordIndex] & -1L >>> -(n + 1)); n2 == 0L; n2 = ~this.words[wordIndex]) {
                if (wordIndex-- == 0) {
                    return -1;
                }
            }
            return (wordIndex + 1) * 64 - 1 - Long.numberOfLeadingZeros(n2);
        }
    }
    
    public int length() {
        if (this.wordsInUse == 0) {
            return 0;
        }
        return 64 * (this.wordsInUse - 1) + (64 - Long.numberOfLeadingZeros(this.words[this.wordsInUse - 1]));
    }
    
    public boolean isEmpty() {
        return this.wordsInUse == 0;
    }
    
    public boolean intersects(final BitSet set) {
        for (int i = Math.min(this.wordsInUse, set.wordsInUse) - 1; i >= 0; --i) {
            if ((this.words[i] & set.words[i]) != 0x0L) {
                return true;
            }
        }
        return false;
    }
    
    public int cardinality() {
        int n = 0;
        for (int i = 0; i < this.wordsInUse; ++i) {
            n += Long.bitCount(this.words[i]);
        }
        return n;
    }
    
    public void and(final BitSet set) {
        if (this == set) {
            return;
        }
        while (this.wordsInUse > set.wordsInUse) {
            this.words[--this.wordsInUse] = 0L;
        }
        for (int i = 0; i < this.wordsInUse; ++i) {
            final long[] words = this.words;
            final int n = i;
            words[n] &= set.words[i];
        }
        this.recalculateWordsInUse();
        this.checkInvariants();
    }
    
    public void or(final BitSet set) {
        if (this == set) {
            return;
        }
        final int min = Math.min(this.wordsInUse, set.wordsInUse);
        if (this.wordsInUse < set.wordsInUse) {
            this.ensureCapacity(set.wordsInUse);
            this.wordsInUse = set.wordsInUse;
        }
        for (int i = 0; i < min; ++i) {
            final long[] words = this.words;
            final int n = i;
            words[n] |= set.words[i];
        }
        if (min < set.wordsInUse) {
            System.arraycopy(set.words, min, this.words, min, this.wordsInUse - min);
        }
        this.checkInvariants();
    }
    
    public void xor(final BitSet set) {
        final int min = Math.min(this.wordsInUse, set.wordsInUse);
        if (this.wordsInUse < set.wordsInUse) {
            this.ensureCapacity(set.wordsInUse);
            this.wordsInUse = set.wordsInUse;
        }
        for (int i = 0; i < min; ++i) {
            final long[] words = this.words;
            final int n = i;
            words[n] ^= set.words[i];
        }
        if (min < set.wordsInUse) {
            System.arraycopy(set.words, min, this.words, min, set.wordsInUse - min);
        }
        this.recalculateWordsInUse();
        this.checkInvariants();
    }
    
    public void andNot(final BitSet set) {
        for (int i = Math.min(this.wordsInUse, set.wordsInUse) - 1; i >= 0; --i) {
            final long[] words = this.words;
            final int n = i;
            words[n] &= ~set.words[i];
        }
        this.recalculateWordsInUse();
        this.checkInvariants();
    }
    
    @Override
    public int hashCode() {
        long n = 1234L;
        int wordsInUse = this.wordsInUse;
        while (--wordsInUse >= 0) {
            n ^= this.words[wordsInUse] * (wordsInUse + 1);
        }
        return (int)(n >> 32 ^ n);
    }
    
    public int size() {
        return this.words.length * 64;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BitSet)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        final BitSet set = (BitSet)o;
        this.checkInvariants();
        set.checkInvariants();
        if (this.wordsInUse != set.wordsInUse) {
            return false;
        }
        for (int i = 0; i < this.wordsInUse; ++i) {
            if (this.words[i] != set.words[i]) {
                return false;
            }
        }
        return true;
    }
    
    public Object clone() {
        if (!this.sizeIsSticky) {
            this.trimToSize();
        }
        try {
            final BitSet set = (BitSet)super.clone();
            set.words = this.words.clone();
            set.checkInvariants();
            return set;
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    private void trimToSize() {
        if (this.wordsInUse != this.words.length) {
            this.words = Arrays.copyOf(this.words, this.wordsInUse);
            this.checkInvariants();
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        this.checkInvariants();
        if (!this.sizeIsSticky) {
            this.trimToSize();
        }
        objectOutputStream.putFields().put("bits", this.words);
        objectOutputStream.writeFields();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.words = (long[])objectInputStream.readFields().get("bits", null);
        this.wordsInUse = this.words.length;
        this.recalculateWordsInUse();
        this.sizeIsSticky = (this.words.length > 0 && this.words[this.words.length - 1] == 0L);
        this.checkInvariants();
    }
    
    @Override
    public String toString() {
        this.checkInvariants();
        final StringBuilder sb = new StringBuilder(6 * ((this.wordsInUse > 128) ? this.cardinality() : (this.wordsInUse * 64)) + 2);
        sb.append('{');
        int n = this.nextSetBit(0);
        if (n != -1) {
            sb.append(n);
            while (++n >= 0) {
                if ((n = this.nextSetBit(n)) < 0) {
                    break;
                }
                do {
                    sb.append(", ").append(n);
                } while (++n != this.nextClearBit(n));
            }
        }
        sb.append('}');
        return sb.toString();
    }
    
    public IntStream stream() {
        class BitSetIterator implements PrimitiveIterator.OfInt
        {
            int next;
            
            BitSetIterator() {
                this.next = BitSet.this.nextSetBit(0);
            }
            
            @Override
            public boolean hasNext() {
                return this.next != -1;
            }
            
            @Override
            public int nextInt() {
                if (this.next != -1) {
                    final int next = this.next;
                    this.next = BitSet.this.nextSetBit(this.next + 1);
                    return next;
                }
                throw new NoSuchElementException();
            }
        }
        return StreamSupport.intStream(() -> Spliterators.spliterator(new BitSetIterator(), this.cardinality(), 21), 16469, false);
    }
    
    static {
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("bits", long[].class) };
    }
}
