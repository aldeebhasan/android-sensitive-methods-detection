package java.security;

import java.util.regex.*;
import java.io.*;
import java.util.*;
import java.math.*;
import sun.security.util.*;

public final class PKCS12Attribute implements KeyStore.Entry.Attribute
{
    private static final Pattern COLON_SEPARATED_HEX_PAIRS;
    private String name;
    private String value;
    private byte[] encoded;
    private int hashValue;
    
    public PKCS12Attribute(final String name, final String value) {
        this.hashValue = -1;
        if (name == null || value == null) {
            throw new NullPointerException();
        }
        ObjectIdentifier objectIdentifier;
        try {
            objectIdentifier = new ObjectIdentifier(name);
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Incorrect format: name", ex);
        }
        this.name = name;
        final int length = value.length();
        String[] split;
        if (value.charAt(0) == '[' && value.charAt(length - 1) == ']') {
            split = value.substring(1, length - 1).split(", ");
        }
        else {
            split = new String[] { value };
        }
        this.value = value;
        try {
            this.encoded = this.encode(objectIdentifier, split);
        }
        catch (IOException ex2) {
            throw new IllegalArgumentException("Incorrect format: value", ex2);
        }
    }
    
    public PKCS12Attribute(final byte[] array) {
        this.hashValue = -1;
        if (array == null) {
            throw new NullPointerException();
        }
        this.encoded = array.clone();
        try {
            this.parse(array);
        }
        catch (IOException ex) {
            throw new IllegalArgumentException("Incorrect format: encoded", ex);
        }
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public byte[] getEncoded() {
        return this.encoded.clone();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof PKCS12Attribute && Arrays.equals(this.encoded, ((PKCS12Attribute)o).getEncoded()));
    }
    
    @Override
    public int hashCode() {
        if (this.hashValue == -1) {
            Arrays.hashCode(this.encoded);
        }
        return this.hashValue;
    }
    
    @Override
    public String toString() {
        return this.name + "=" + this.value;
    }
    
    private byte[] encode(final ObjectIdentifier objectIdentifier, final String[] array) throws IOException {
        final DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putOID(objectIdentifier);
        final DerOutputStream derOutputStream2 = new DerOutputStream();
        for (final String s : array) {
            if (PKCS12Attribute.COLON_SEPARATED_HEX_PAIRS.matcher(s).matches()) {
                byte[] array2 = new BigInteger(s.replace(":", ""), 16).toByteArray();
                if (array2[0] == 0) {
                    array2 = Arrays.copyOfRange(array2, 1, array2.length);
                }
                derOutputStream2.putOctetString(array2);
            }
            else {
                derOutputStream2.putUTF8String(s);
            }
        }
        derOutputStream.write((byte)49, derOutputStream2);
        final DerOutputStream derOutputStream3 = new DerOutputStream();
        derOutputStream3.write((byte)48, derOutputStream);
        return derOutputStream3.toByteArray();
    }
    
    private void parse(final byte[] array) throws IOException {
        final DerValue[] sequence = new DerInputStream(array).getSequence(2);
        if (sequence.length != 2) {
            throw new IOException("Invalid length for PKCS12Attribute");
        }
        final ObjectIdentifier oid = sequence[0].getOID();
        final DerValue[] set = new DerInputStream(sequence[1].toByteArray()).getSet(1);
        final String[] array2 = new String[set.length];
        for (int i = 0; i < set.length; ++i) {
            if (set[i].tag == 4) {
                array2[i] = Debug.toString(set[i].getOctetString());
            }
            else {
                final String asString;
                if ((asString = set[i].getAsString()) != null) {
                    array2[i] = asString;
                }
                else if (set[i].tag == 6) {
                    array2[i] = set[i].getOID().toString();
                }
                else if (set[i].tag == 24) {
                    array2[i] = set[i].getGeneralizedTime().toString();
                }
                else if (set[i].tag == 23) {
                    array2[i] = set[i].getUTCTime().toString();
                }
                else if (set[i].tag == 2) {
                    array2[i] = set[i].getBigInteger().toString();
                }
                else if (set[i].tag == 1) {
                    array2[i] = String.valueOf(set[i].getBoolean());
                }
                else {
                    array2[i] = Debug.toString(set[i].getDataBytes());
                }
            }
        }
        this.name = oid.toString();
        this.value = ((array2.length == 1) ? array2[0] : Arrays.toString(array2));
    }
    
    static {
        COLON_SEPARATED_HEX_PAIRS = Pattern.compile("^[0-9a-fA-F]{2}(:[0-9a-fA-F]{2})+$");
    }
}
