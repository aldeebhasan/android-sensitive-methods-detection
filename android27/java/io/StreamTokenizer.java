package java.io;

import java.util.*;

public class StreamTokenizer
{
    private Reader reader;
    private InputStream input;
    private char[] buf;
    private int peekc;
    private static final int NEED_CHAR = Integer.MAX_VALUE;
    private static final int SKIP_LF = 2147483646;
    private boolean pushedBack;
    private boolean forceLower;
    private int LINENO;
    private boolean eolIsSignificantP;
    private boolean slashSlashCommentsP;
    private boolean slashStarCommentsP;
    private byte[] ctype;
    private static final byte CT_WHITESPACE = 1;
    private static final byte CT_DIGIT = 2;
    private static final byte CT_ALPHA = 4;
    private static final byte CT_QUOTE = 8;
    private static final byte CT_COMMENT = 16;
    public int ttype;
    public static final int TT_EOF = -1;
    public static final int TT_EOL = 10;
    public static final int TT_NUMBER = -2;
    public static final int TT_WORD = -3;
    private static final int TT_NOTHING = -4;
    public String sval;
    public double nval;
    
    private StreamTokenizer() {
        this.reader = null;
        this.input = null;
        this.buf = new char[20];
        this.peekc = Integer.MAX_VALUE;
        this.LINENO = 1;
        this.eolIsSignificantP = false;
        this.slashSlashCommentsP = false;
        this.slashStarCommentsP = false;
        this.ctype = new byte[256];
        this.ttype = -4;
        this.wordChars(97, 122);
        this.wordChars(65, 90);
        this.wordChars(160, 255);
        this.whitespaceChars(0, 32);
        this.commentChar(47);
        this.quoteChar(34);
        this.quoteChar(39);
        this.parseNumbers();
    }
    
    public StreamTokenizer(final InputStream input) {
        this();
        if (input == null) {
            throw new NullPointerException();
        }
        this.input = input;
    }
    
    public StreamTokenizer(final Reader reader) {
        this();
        if (reader == null) {
            throw new NullPointerException();
        }
        this.reader = reader;
    }
    
    public void resetSyntax() {
        int length = this.ctype.length;
        while (--length >= 0) {
            this.ctype[length] = 0;
        }
    }
    
    public void wordChars(int i, int n) {
        if (i < 0) {
            i = 0;
        }
        if (n >= this.ctype.length) {
            n = this.ctype.length - 1;
        }
        while (i <= n) {
            final byte[] ctype = this.ctype;
            final int n2 = i++;
            ctype[n2] |= 0x4;
        }
    }
    
    public void whitespaceChars(int i, int n) {
        if (i < 0) {
            i = 0;
        }
        if (n >= this.ctype.length) {
            n = this.ctype.length - 1;
        }
        while (i <= n) {
            this.ctype[i++] = 1;
        }
    }
    
    public void ordinaryChars(int i, int n) {
        if (i < 0) {
            i = 0;
        }
        if (n >= this.ctype.length) {
            n = this.ctype.length - 1;
        }
        while (i <= n) {
            this.ctype[i++] = 0;
        }
    }
    
    public void ordinaryChar(final int n) {
        if (n >= 0 && n < this.ctype.length) {
            this.ctype[n] = 0;
        }
    }
    
    public void commentChar(final int n) {
        if (n >= 0 && n < this.ctype.length) {
            this.ctype[n] = 16;
        }
    }
    
    public void quoteChar(final int n) {
        if (n >= 0 && n < this.ctype.length) {
            this.ctype[n] = 8;
        }
    }
    
    public void parseNumbers() {
        for (int i = 48; i <= 57; ++i) {
            final byte[] ctype = this.ctype;
            final int n = i;
            ctype[n] |= 0x2;
        }
        final byte[] ctype2 = this.ctype;
        final int n2 = 46;
        ctype2[n2] |= 0x2;
        final byte[] ctype3 = this.ctype;
        final int n3 = 45;
        ctype3[n3] |= 0x2;
    }
    
    public void eolIsSignificant(final boolean eolIsSignificantP) {
        this.eolIsSignificantP = eolIsSignificantP;
    }
    
    public void slashStarComments(final boolean slashStarCommentsP) {
        this.slashStarCommentsP = slashStarCommentsP;
    }
    
    public void slashSlashComments(final boolean slashSlashCommentsP) {
        this.slashSlashCommentsP = slashSlashCommentsP;
    }
    
    public void lowerCaseMode(final boolean forceLower) {
        this.forceLower = forceLower;
    }
    
    private int read() throws IOException {
        if (this.reader != null) {
            return this.reader.read();
        }
        if (this.input != null) {
            return this.input.read();
        }
        throw new IllegalStateException();
    }
    
    public int nextToken() throws IOException {
        if (this.pushedBack) {
            this.pushedBack = false;
            return this.ttype;
        }
        final byte[] ctype = this.ctype;
        this.sval = null;
        int n = this.peekc;
        if (n < 0) {
            n = Integer.MAX_VALUE;
        }
        if (n == 2147483646) {
            n = this.read();
            if (n < 0) {
                return this.ttype = -1;
            }
            if (n == 10) {
                n = Integer.MAX_VALUE;
            }
        }
        if (n == Integer.MAX_VALUE) {
            n = this.read();
            if (n < 0) {
                return this.ttype = -1;
            }
        }
        this.ttype = n;
        this.peekc = Integer.MAX_VALUE;
        byte b;
        for (b = (byte)((n < 256) ? ctype[n] : 4); (b & 0x1) != 0x0; b = (byte)((n < 256) ? ctype[n] : 4)) {
            if (n == 13) {
                ++this.LINENO;
                if (this.eolIsSignificantP) {
                    this.peekc = 2147483646;
                    return this.ttype = 10;
                }
                n = this.read();
                if (n == 10) {
                    n = this.read();
                }
            }
            else {
                if (n == 10) {
                    ++this.LINENO;
                    if (this.eolIsSignificantP) {
                        return this.ttype = 10;
                    }
                }
                n = this.read();
            }
            if (n < 0) {
                return this.ttype = -1;
            }
        }
        if ((b & 0x2) != 0x0) {
            boolean b2 = false;
            if (n == 45) {
                n = this.read();
                if (n != 46 && (n < 48 || n > 57)) {
                    this.peekc = n;
                    return this.ttype = 45;
                }
                b2 = true;
            }
            double n2 = 0.0;
            int i = 0;
            int n3 = 0;
            while (true) {
                if (n == 46 && n3 == 0) {
                    n3 = 1;
                }
                else {
                    if (48 > n || n > 57) {
                        break;
                    }
                    n2 = n2 * 10.0 + (n - 48);
                    i += n3;
                }
                n = this.read();
            }
            this.peekc = n;
            if (i != 0) {
                double n4 = 10.0;
                --i;
                while (i > 0) {
                    n4 *= 10.0;
                    --i;
                }
                n2 /= n4;
            }
            this.nval = (b2 ? (-n2) : n2);
            return this.ttype = -2;
        }
        if ((b & 0x4) != 0x0) {
            int n5 = 0;
            do {
                if (n5 >= this.buf.length) {
                    this.buf = Arrays.copyOf(this.buf, this.buf.length * 2);
                }
                this.buf[n5++] = (char)n;
                n = this.read();
            } while ((((n < 0) ? 1 : ((n < 256) ? ctype[n] : 4)) & 0x6) != 0x0);
            this.peekc = n;
            this.sval = String.copyValueOf(this.buf, 0, n5);
            if (this.forceLower) {
                this.sval = this.sval.toLowerCase();
            }
            return this.ttype = -3;
        }
        if ((b & 0x8) != 0x0) {
            this.ttype = n;
            int n6 = 0;
            int n7 = this.read();
            while (n7 >= 0 && n7 != this.ttype && n7 != 10 && n7 != 13) {
                int n8;
                if (n7 == 92) {
                    final int read;
                    n8 = (read = this.read());
                    if (n8 >= 48 && n8 <= 55) {
                        n8 -= 48;
                        final int read2 = this.read();
                        if (48 <= read2 && read2 <= 55) {
                            n8 = (n8 << 3) + (read2 - 48);
                            final int read3 = this.read();
                            if (48 <= read3 && read3 <= 55 && read <= 51) {
                                n8 = (n8 << 3) + (read3 - 48);
                                n7 = this.read();
                            }
                            else {
                                n7 = read3;
                            }
                        }
                        else {
                            n7 = read2;
                        }
                    }
                    else {
                        switch (n8) {
                            case 97: {
                                n8 = 7;
                                break;
                            }
                            case 98: {
                                n8 = 8;
                                break;
                            }
                            case 102: {
                                n8 = 12;
                                break;
                            }
                            case 110: {
                                n8 = 10;
                                break;
                            }
                            case 114: {
                                n8 = 13;
                                break;
                            }
                            case 116: {
                                n8 = 9;
                                break;
                            }
                            case 118: {
                                n8 = 11;
                                break;
                            }
                        }
                        n7 = this.read();
                    }
                }
                else {
                    n8 = n7;
                    n7 = this.read();
                }
                if (n6 >= this.buf.length) {
                    this.buf = Arrays.copyOf(this.buf, this.buf.length * 2);
                }
                this.buf[n6++] = (char)n8;
            }
            this.peekc = ((n7 == this.ttype) ? Integer.MAX_VALUE : n7);
            this.sval = String.copyValueOf(this.buf, 0, n6);
            return this.ttype;
        }
        if (n == 47 && (this.slashSlashCommentsP || this.slashStarCommentsP)) {
            final int read4 = this.read();
            if (read4 == 42 && this.slashStarCommentsP) {
                int n10;
                for (int n9 = 0; (n10 = this.read()) != 47 || n9 != 42; n9 = n10) {
                    if (n10 == 13) {
                        ++this.LINENO;
                        n10 = this.read();
                        if (n10 == 10) {
                            n10 = this.read();
                        }
                    }
                    else if (n10 == 10) {
                        ++this.LINENO;
                        n10 = this.read();
                    }
                    if (n10 < 0) {
                        return this.ttype = -1;
                    }
                }
                return this.nextToken();
            }
            if (read4 == 47 && this.slashSlashCommentsP) {
                int read5;
                while ((read5 = this.read()) != 10 && read5 != 13 && read5 >= 0) {}
                this.peekc = read5;
                return this.nextToken();
            }
            if ((ctype[47] & 0x10) != 0x0) {
                int read6;
                while ((read6 = this.read()) != 10 && read6 != 13 && read6 >= 0) {}
                this.peekc = read6;
                return this.nextToken();
            }
            this.peekc = read4;
            return this.ttype = 47;
        }
        else {
            if ((b & 0x10) != 0x0) {
                int read7;
                while ((read7 = this.read()) != 10 && read7 != 13 && read7 >= 0) {}
                this.peekc = read7;
                return this.nextToken();
            }
            return this.ttype = n;
        }
    }
    
    public void pushBack() {
        if (this.ttype != -4) {
            this.pushedBack = true;
        }
    }
    
    public int lineno() {
        return this.LINENO;
    }
    
    @Override
    public String toString() {
        String s = null;
        switch (this.ttype) {
            case -1: {
                s = "EOF";
                break;
            }
            case 10: {
                s = "EOL";
                break;
            }
            case -3: {
                s = this.sval;
                break;
            }
            case -2: {
                s = "n=" + this.nval;
                break;
            }
            case -4: {
                s = "NOTHING";
                break;
            }
            default: {
                if (this.ttype < 256 && (this.ctype[this.ttype] & 0x8) != 0x0) {
                    s = this.sval;
                    break;
                }
                final char[] array = new char[3];
                array[0] = (array[2] = '\'');
                array[1] = (char)this.ttype;
                s = new String(array);
                break;
            }
        }
        return "Token[" + s + "], line " + this.LINENO;
    }
}
