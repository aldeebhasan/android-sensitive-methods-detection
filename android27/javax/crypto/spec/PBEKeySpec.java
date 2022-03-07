package javax.crypto.spec;

import java.security.spec.*;
import java.util.*;

public class PBEKeySpec implements KeySpec
{
    private char[] password;
    private byte[] salt;
    private int iterationCount;
    private int keyLength;
    
    public PBEKeySpec(final char[] array) {
        this.salt = null;
        this.iterationCount = 0;
        this.keyLength = 0;
        if (array == null || array.length == 0) {
            this.password = new char[0];
        }
        else {
            this.password = array.clone();
        }
    }
    
    public PBEKeySpec(final char[] array, final byte[] array2, final int iterationCount, final int keyLength) {
        this.salt = null;
        this.iterationCount = 0;
        this.keyLength = 0;
        if (array == null || array.length == 0) {
            this.password = new char[0];
        }
        else {
            this.password = array.clone();
        }
        if (array2 == null) {
            throw new NullPointerException("the salt parameter must be non-null");
        }
        if (array2.length == 0) {
            throw new IllegalArgumentException("the salt parameter must not be empty");
        }
        this.salt = array2.clone();
        if (iterationCount <= 0) {
            throw new IllegalArgumentException("invalid iterationCount value");
        }
        if (keyLength <= 0) {
            throw new IllegalArgumentException("invalid keyLength value");
        }
        this.iterationCount = iterationCount;
        this.keyLength = keyLength;
    }
    
    public PBEKeySpec(final char[] array, final byte[] array2, final int iterationCount) {
        this.salt = null;
        this.iterationCount = 0;
        this.keyLength = 0;
        if (array == null || array.length == 0) {
            this.password = new char[0];
        }
        else {
            this.password = array.clone();
        }
        if (array2 == null) {
            throw new NullPointerException("the salt parameter must be non-null");
        }
        if (array2.length == 0) {
            throw new IllegalArgumentException("the salt parameter must not be empty");
        }
        this.salt = array2.clone();
        if (iterationCount <= 0) {
            throw new IllegalArgumentException("invalid iterationCount value");
        }
        this.iterationCount = iterationCount;
    }
    
    public final void clearPassword() {
        if (this.password != null) {
            Arrays.fill(this.password, ' ');
            this.password = null;
        }
    }
    
    public final char[] getPassword() {
        if (this.password == null) {
            throw new IllegalStateException("password has been cleared");
        }
        return this.password.clone();
    }
    
    public final byte[] getSalt() {
        if (this.salt != null) {
            return this.salt.clone();
        }
        return null;
    }
    
    public final int getIterationCount() {
        return this.iterationCount;
    }
    
    public final int getKeyLength() {
        return this.keyLength;
    }
}
