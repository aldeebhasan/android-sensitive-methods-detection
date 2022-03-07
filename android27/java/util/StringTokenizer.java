package java.util;

public class StringTokenizer implements Enumeration<Object>
{
    private int currentPosition;
    private int newPosition;
    private int maxPosition;
    private String str;
    private String delimiters;
    private boolean retDelims;
    private boolean delimsChanged;
    private int maxDelimCodePoint;
    private boolean hasSurrogates;
    private int[] delimiterCodePoints;
    
    private void setMaxDelimCodePoint() {
        if (this.delimiters == null) {
            this.maxDelimCodePoint = 0;
            return;
        }
        int maxDelimCodePoint = 0;
        int n = 0;
        int n2;
        for (int i = 0; i < this.delimiters.length(); i += Character.charCount(n2)) {
            n2 = this.delimiters.charAt(i);
            if (n2 >= 55296 && n2 <= 57343) {
                n2 = this.delimiters.codePointAt(i);
                this.hasSurrogates = true;
            }
            if (maxDelimCodePoint < n2) {
                maxDelimCodePoint = n2;
            }
            ++n;
        }
        this.maxDelimCodePoint = maxDelimCodePoint;
        if (this.hasSurrogates) {
            this.delimiterCodePoints = new int[n];
            int codePoint;
            for (int j = 0, n3 = 0; j < n; ++j, n3 += Character.charCount(codePoint)) {
                codePoint = this.delimiters.codePointAt(n3);
                this.delimiterCodePoints[j] = codePoint;
            }
        }
    }
    
    public StringTokenizer(final String str, final String delimiters, final boolean retDelims) {
        this.hasSurrogates = false;
        this.currentPosition = 0;
        this.newPosition = -1;
        this.delimsChanged = false;
        this.str = str;
        this.maxPosition = str.length();
        this.delimiters = delimiters;
        this.retDelims = retDelims;
        this.setMaxDelimCodePoint();
    }
    
    public StringTokenizer(final String s, final String s2) {
        this(s, s2, false);
    }
    
    public StringTokenizer(final String s) {
        this(s, " \t\n\r\f", false);
    }
    
    private int skipDelimiters(final int n) {
        if (this.delimiters == null) {
            throw new NullPointerException();
        }
        int n2 = n;
        while (!this.retDelims && n2 < this.maxPosition) {
            if (!this.hasSurrogates) {
                final char char1 = this.str.charAt(n2);
                if (char1 > this.maxDelimCodePoint) {
                    break;
                }
                if (this.delimiters.indexOf(char1) < 0) {
                    break;
                }
                ++n2;
            }
            else {
                final int codePoint = this.str.codePointAt(n2);
                if (codePoint > this.maxDelimCodePoint) {
                    break;
                }
                if (!this.isDelimiter(codePoint)) {
                    break;
                }
                n2 += Character.charCount(codePoint);
            }
        }
        return n2;
    }
    
    private int scanToken(final int n) {
        int i = n;
        while (i < this.maxPosition) {
            if (!this.hasSurrogates) {
                final char char1 = this.str.charAt(i);
                if (char1 <= this.maxDelimCodePoint && this.delimiters.indexOf(char1) >= 0) {
                    break;
                }
                ++i;
            }
            else {
                final int codePoint = this.str.codePointAt(i);
                if (codePoint <= this.maxDelimCodePoint && this.isDelimiter(codePoint)) {
                    break;
                }
                i += Character.charCount(codePoint);
            }
        }
        if (this.retDelims && n == i) {
            if (!this.hasSurrogates) {
                final char char2 = this.str.charAt(i);
                if (char2 <= this.maxDelimCodePoint && this.delimiters.indexOf(char2) >= 0) {
                    ++i;
                }
            }
            else {
                final int codePoint2 = this.str.codePointAt(i);
                if (codePoint2 <= this.maxDelimCodePoint && this.isDelimiter(codePoint2)) {
                    i += Character.charCount(codePoint2);
                }
            }
        }
        return i;
    }
    
    private boolean isDelimiter(final int n) {
        for (int i = 0; i < this.delimiterCodePoints.length; ++i) {
            if (this.delimiterCodePoints[i] == n) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasMoreTokens() {
        this.newPosition = this.skipDelimiters(this.currentPosition);
        return this.newPosition < this.maxPosition;
    }
    
    public String nextToken() {
        this.currentPosition = ((this.newPosition >= 0 && !this.delimsChanged) ? this.newPosition : this.skipDelimiters(this.currentPosition));
        this.delimsChanged = false;
        this.newPosition = -1;
        if (this.currentPosition >= this.maxPosition) {
            throw new NoSuchElementException();
        }
        final int currentPosition = this.currentPosition;
        this.currentPosition = this.scanToken(this.currentPosition);
        return this.str.substring(currentPosition, this.currentPosition);
    }
    
    public String nextToken(final String delimiters) {
        this.delimiters = delimiters;
        this.delimsChanged = true;
        this.setMaxDelimCodePoint();
        return this.nextToken();
    }
    
    @Override
    public boolean hasMoreElements() {
        return this.hasMoreTokens();
    }
    
    @Override
    public Object nextElement() {
        return this.nextToken();
    }
    
    public int countTokens() {
        int n = 0;
        int skipDelimiters;
        for (int i = this.currentPosition; i < this.maxPosition; i = this.scanToken(skipDelimiters), ++n) {
            skipDelimiters = this.skipDelimiters(i);
            if (skipDelimiters >= this.maxPosition) {
                break;
            }
        }
        return n;
    }
}
