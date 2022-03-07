package javax.crypto;

import sun.security.x509.*;
import java.io.*;
import java.security.spec.*;
import java.security.*;
import sun.security.util.*;

public class EncryptedPrivateKeyInfo
{
    private AlgorithmId algid;
    private byte[] encryptedData;
    private byte[] encoded;
    
    public EncryptedPrivateKeyInfo(final byte[] array) throws IOException {
        this.encoded = null;
        if (array == null) {
            throw new NullPointerException("the encoded parameter must be non-null");
        }
        this.encoded = array.clone();
        final DerValue derValue = new DerValue(this.encoded);
        final DerValue[] array2 = { derValue.data.getDerValue(), derValue.data.getDerValue() };
        if (derValue.data.available() != 0) {
            throw new IOException("overrun, bytes = " + derValue.data.available());
        }
        this.algid = AlgorithmId.parse(array2[0]);
        if (array2[0].data.available() != 0) {
            throw new IOException("encryptionAlgorithm field overrun");
        }
        this.encryptedData = array2[1].getOctetString();
        if (array2[1].data.available() != 0) {
            throw new IOException("encryptedData field overrun");
        }
    }
    
    public EncryptedPrivateKeyInfo(final String s, final byte[] array) throws NoSuchAlgorithmException {
        this.encoded = null;
        if (s == null) {
            throw new NullPointerException("the algName parameter must be non-null");
        }
        this.algid = AlgorithmId.get(s);
        if (array == null) {
            throw new NullPointerException("the encryptedData parameter must be non-null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("the encryptedData parameter must not be empty");
        }
        this.encryptedData = array.clone();
        this.encoded = null;
    }
    
    public EncryptedPrivateKeyInfo(final AlgorithmParameters algorithmParameters, final byte[] array) throws NoSuchAlgorithmException {
        this.encoded = null;
        if (algorithmParameters == null) {
            throw new NullPointerException("algParams must be non-null");
        }
        this.algid = AlgorithmId.get(algorithmParameters);
        if (array == null) {
            throw new NullPointerException("encryptedData must be non-null");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("the encryptedData parameter must not be empty");
        }
        this.encryptedData = array.clone();
        this.encoded = null;
    }
    
    public String getAlgName() {
        return this.algid.getName();
    }
    
    public AlgorithmParameters getAlgParameters() {
        return this.algid.getParameters();
    }
    
    public byte[] getEncryptedData() {
        return this.encryptedData.clone();
    }
    
    public PKCS8EncodedKeySpec getKeySpec(final Cipher cipher) throws InvalidKeySpecException {
        byte[] doFinal;
        try {
            doFinal = cipher.doFinal(this.encryptedData);
            checkPKCS8Encoding(doFinal);
        }
        catch (GeneralSecurityException | IOException | IllegalStateException ex) {
            final Object o;
            throw new InvalidKeySpecException("Cannot retrieve the PKCS8EncodedKeySpec", (Throwable)o);
        }
        return new PKCS8EncodedKeySpec(doFinal);
    }
    
    private PKCS8EncodedKeySpec getKeySpecImpl(final Key key, final Provider provider) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] doFinal;
        try {
            Cipher cipher;
            if (provider == null) {
                cipher = Cipher.getInstance(this.algid.getName());
            }
            else {
                cipher = Cipher.getInstance(this.algid.getName(), provider);
            }
            cipher.init(2, key, this.algid.getParameters());
            doFinal = cipher.doFinal(this.encryptedData);
            checkPKCS8Encoding(doFinal);
        }
        catch (NoSuchAlgorithmException ex) {
            throw ex;
        }
        catch (GeneralSecurityException | IOException ex2) {
            final Object o;
            throw new InvalidKeyException("Cannot retrieve the PKCS8EncodedKeySpec", (Throwable)o);
        }
        return new PKCS8EncodedKeySpec(doFinal);
    }
    
    public PKCS8EncodedKeySpec getKeySpec(final Key key) throws NoSuchAlgorithmException, InvalidKeyException {
        if (key == null) {
            throw new NullPointerException("decryptKey is null");
        }
        return this.getKeySpecImpl(key, null);
    }
    
    public PKCS8EncodedKeySpec getKeySpec(final Key key, final String s) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException {
        if (key == null) {
            throw new NullPointerException("decryptKey is null");
        }
        if (s == null) {
            throw new NullPointerException("provider is null");
        }
        final Provider provider = Security.getProvider(s);
        if (provider == null) {
            throw new NoSuchProviderException("provider " + s + " not found");
        }
        return this.getKeySpecImpl(key, provider);
    }
    
    public PKCS8EncodedKeySpec getKeySpec(final Key key, final Provider provider) throws NoSuchAlgorithmException, InvalidKeyException {
        if (key == null) {
            throw new NullPointerException("decryptKey is null");
        }
        if (provider == null) {
            throw new NullPointerException("provider is null");
        }
        return this.getKeySpecImpl(key, provider);
    }
    
    public byte[] getEncoded() throws IOException {
        if (this.encoded == null) {
            final DerOutputStream derOutputStream = new DerOutputStream();
            final DerOutputStream derOutputStream2 = new DerOutputStream();
            this.algid.encode(derOutputStream2);
            derOutputStream2.putOctetString(this.encryptedData);
            derOutputStream.write((byte)48, derOutputStream2);
            this.encoded = derOutputStream.toByteArray();
        }
        return this.encoded.clone();
    }
    
    private static void checkTag(final DerValue derValue, final byte b, final String s) throws IOException {
        if (derValue.getTag() != b) {
            throw new IOException("invalid key encoding - wrong tag for " + s);
        }
    }
    
    private static void checkPKCS8Encoding(final byte[] array) throws IOException {
        final DerValue[] sequence = new DerInputStream(array).getSequence(3);
        switch (sequence.length) {
            case 4: {
                checkTag(sequence[3], (byte)(-128), "attributes");
            }
            case 3: {
                checkTag(sequence[0], (byte)2, "version");
                final DerInputStream derInputStream = sequence[1].toDerInputStream();
                derInputStream.getOID();
                if (derInputStream.available() != 0) {
                    derInputStream.getDerValue();
                }
                checkTag(sequence[2], (byte)4, "privateKey");
            }
            default: {
                throw new IOException("invalid key encoding");
            }
        }
    }
}
