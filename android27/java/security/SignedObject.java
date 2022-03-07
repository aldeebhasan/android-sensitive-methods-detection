package java.security;

import java.io.*;

public final class SignedObject implements Serializable
{
    private static final long serialVersionUID = 720502720485447167L;
    private byte[] content;
    private byte[] signature;
    private String thealgorithm;
    
    public SignedObject(final Serializable s, final PrivateKey privateKey, final Signature signature) throws IOException, InvalidKeyException, SignatureException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(s);
        objectOutputStream.flush();
        objectOutputStream.close();
        this.content = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        this.sign(privateKey, signature);
    }
    
    public Object getObject() throws IOException, ClassNotFoundException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.content);
        final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        final Object object = objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return object;
    }
    
    public byte[] getSignature() {
        return this.signature.clone();
    }
    
    public String getAlgorithm() {
        return this.thealgorithm;
    }
    
    public boolean verify(final PublicKey publicKey, final Signature signature) throws InvalidKeyException, SignatureException {
        signature.initVerify(publicKey);
        signature.update(this.content.clone());
        return signature.verify(this.signature.clone());
    }
    
    private void sign(final PrivateKey privateKey, final Signature signature) throws InvalidKeyException, SignatureException {
        signature.initSign(privateKey);
        signature.update(this.content.clone());
        this.signature = signature.sign().clone();
        this.thealgorithm = signature.getAlgorithm();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        this.content = ((byte[])fields.get("content", null)).clone();
        this.signature = ((byte[])fields.get("signature", null)).clone();
        this.thealgorithm = (String)fields.get("thealgorithm", null);
    }
}
