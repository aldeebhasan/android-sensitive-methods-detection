package java.text;

import java.util.*;
import java.io.*;

public class ChoiceFormat extends NumberFormat
{
    private static final long serialVersionUID = 1795184449645032964L;
    private double[] choiceLimits;
    private String[] choiceFormats;
    static final long SIGN = Long.MIN_VALUE;
    static final long EXPONENT = 9218868437227405312L;
    static final long POSITIVEINFINITY = 9218868437227405312L;
    
    public void applyPattern(final String s) {
        final StringBuffer[] array = new StringBuffer[2];
        for (int i = 0; i < array.length; ++i) {
            array[i] = new StringBuffer();
        }
        double[] array2 = new double[30];
        String[] array3 = new String[30];
        int n = 0;
        int n2 = 0;
        double n3 = 0.0;
        double n4 = Double.NaN;
        boolean b = false;
        for (int j = 0; j < s.length(); ++j) {
            final char char1 = s.charAt(j);
            if (char1 == '\'') {
                if (j + 1 < s.length() && s.charAt(j + 1) == char1) {
                    array[n2].append(char1);
                    ++j;
                }
                else {
                    b = !b;
                }
            }
            else if (b) {
                array[n2].append(char1);
            }
            else if (char1 == '<' || char1 == '#' || char1 == '\u2264') {
                if (array[0].length() == 0) {
                    throw new IllegalArgumentException();
                }
                try {
                    final String string = array[0].toString();
                    if (string.equals("\u221e")) {
                        n3 = Double.POSITIVE_INFINITY;
                    }
                    else if (string.equals("-\u221e")) {
                        n3 = Double.NEGATIVE_INFINITY;
                    }
                    else {
                        n3 = Double.valueOf(array[0].toString());
                    }
                }
                catch (Exception ex) {
                    throw new IllegalArgumentException();
                }
                if (char1 == '<' && n3 != Double.POSITIVE_INFINITY && n3 != Double.NEGATIVE_INFINITY) {
                    n3 = nextDouble(n3);
                }
                if (n3 <= n4) {
                    throw new IllegalArgumentException();
                }
                array[0].setLength(0);
                n2 = 1;
            }
            else if (char1 == '|') {
                if (n == array2.length) {
                    array2 = doubleArraySize(array2);
                    array3 = this.doubleArraySize(array3);
                }
                array2[n] = n3;
                array3[n] = array[1].toString();
                ++n;
                n4 = n3;
                array[1].setLength(0);
                n2 = 0;
            }
            else {
                array[n2].append(char1);
            }
        }
        if (n2 == 1) {
            if (n == array2.length) {
                array2 = doubleArraySize(array2);
                array3 = this.doubleArraySize(array3);
            }
            array2[n] = n3;
            array3[n] = array[1].toString();
            ++n;
        }
        System.arraycopy(array2, 0, this.choiceLimits = new double[n], 0, n);
        System.arraycopy(array3, 0, this.choiceFormats = new String[n], 0, n);
    }
    
    public String toPattern() {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.choiceLimits.length; ++i) {
            if (i != 0) {
                sb.append('|');
            }
            final double previousDouble = previousDouble(this.choiceLimits[i]);
            if (Math.abs(Math.IEEEremainder(this.choiceLimits[i], 1.0)) < Math.abs(Math.IEEEremainder(previousDouble, 1.0))) {
                sb.append("" + this.choiceLimits[i]);
                sb.append('#');
            }
            else {
                if (this.choiceLimits[i] == Double.POSITIVE_INFINITY) {
                    sb.append("\u221e");
                }
                else if (this.choiceLimits[i] == Double.NEGATIVE_INFINITY) {
                    sb.append("-\u221e");
                }
                else {
                    sb.append("" + previousDouble);
                }
                sb.append('<');
            }
            final String s = this.choiceFormats[i];
            final boolean b = s.indexOf(60) >= 0 || s.indexOf(35) >= 0 || s.indexOf(8804) >= 0 || s.indexOf(124) >= 0;
            if (b) {
                sb.append('\'');
            }
            if (s.indexOf(39) < 0) {
                sb.append(s);
            }
            else {
                for (int j = 0; j < s.length(); ++j) {
                    final char char1 = s.charAt(j);
                    sb.append(char1);
                    if (char1 == '\'') {
                        sb.append(char1);
                    }
                }
            }
            if (b) {
                sb.append('\'');
            }
        }
        return sb.toString();
    }
    
    public ChoiceFormat(final String s) {
        this.applyPattern(s);
    }
    
    public ChoiceFormat(final double[] array, final String[] array2) {
        this.setChoices(array, array2);
    }
    
    public void setChoices(final double[] array, final String[] array2) {
        if (array.length != array2.length) {
            throw new IllegalArgumentException("Array and limit arrays must be of the same length.");
        }
        this.choiceLimits = Arrays.copyOf(array, array.length);
        this.choiceFormats = Arrays.copyOf(array2, array2.length);
    }
    
    public double[] getLimits() {
        return Arrays.copyOf(this.choiceLimits, this.choiceLimits.length);
    }
    
    public Object[] getFormats() {
        return Arrays.copyOf(this.choiceFormats, this.choiceFormats.length);
    }
    
    @Override
    public StringBuffer format(final long n, final StringBuffer sb, final FieldPosition fieldPosition) {
        return this.format((double)n, sb, fieldPosition);
    }
    
    @Override
    public StringBuffer format(final double n, final StringBuffer sb, final FieldPosition fieldPosition) {
        int n2;
        for (n2 = 0; n2 < this.choiceLimits.length && n >= this.choiceLimits[n2]; ++n2) {}
        if (--n2 < 0) {
            n2 = 0;
        }
        return sb.append(this.choiceFormats[n2]);
    }
    
    @Override
    public Number parse(final String s, final ParsePosition parsePosition) {
        int n2;
        final int n = n2 = parsePosition.index;
        double n3 = Double.NaN;
        for (int i = 0; i < this.choiceFormats.length; ++i) {
            final String s2 = this.choiceFormats[i];
            if (s.regionMatches(n, s2, 0, s2.length())) {
                parsePosition.index = n + s2.length();
                final double n4 = this.choiceLimits[i];
                if (parsePosition.index > n2) {
                    n2 = parsePosition.index;
                    n3 = n4;
                    if (n2 == s.length()) {
                        break;
                    }
                }
            }
        }
        parsePosition.index = n2;
        if (parsePosition.index == n) {
            parsePosition.errorIndex = n2;
        }
        return new Double(n3);
    }
    
    public static final double nextDouble(final double n) {
        return nextDouble(n, true);
    }
    
    public static final double previousDouble(final double n) {
        return nextDouble(n, false);
    }
    
    @Override
    public Object clone() {
        final ChoiceFormat choiceFormat = (ChoiceFormat)super.clone();
        choiceFormat.choiceLimits = this.choiceLimits.clone();
        choiceFormat.choiceFormats = this.choiceFormats.clone();
        return choiceFormat;
    }
    
    @Override
    public int hashCode() {
        int length = this.choiceLimits.length;
        if (this.choiceFormats.length > 0) {
            length ^= this.choiceFormats[this.choiceFormats.length - 1].hashCode();
        }
        return length;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        final ChoiceFormat choiceFormat = (ChoiceFormat)o;
        return Arrays.equals(this.choiceLimits, choiceFormat.choiceLimits) && Arrays.equals(this.choiceFormats, choiceFormat.choiceFormats);
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.choiceLimits.length != this.choiceFormats.length) {
            throw new InvalidObjectException("limits and format arrays of different length.");
        }
    }
    
    public static double nextDouble(final double n, final boolean b) {
        if (Double.isNaN(n)) {
            return n;
        }
        if (n != 0.0) {
            final long doubleToLongBits = Double.doubleToLongBits(n);
            long n2 = doubleToLongBits & Long.MAX_VALUE;
            if (doubleToLongBits > 0L == b) {
                if (n2 != 9218868437227405312L) {
                    ++n2;
                }
            }
            else {
                --n2;
            }
            return Double.longBitsToDouble(n2 | (doubleToLongBits & Long.MIN_VALUE));
        }
        final double longBitsToDouble = Double.longBitsToDouble(1L);
        if (b) {
            return longBitsToDouble;
        }
        return -longBitsToDouble;
    }
    
    private static double[] doubleArraySize(final double[] array) {
        final int length = array.length;
        final double[] array2 = new double[length * 2];
        System.arraycopy(array, 0, array2, 0, length);
        return array2;
    }
    
    private String[] doubleArraySize(final String[] array) {
        final int length = array.length;
        final String[] array2 = new String[length * 2];
        System.arraycopy(array, 0, array2, 0, length);
        return array2;
    }
}
