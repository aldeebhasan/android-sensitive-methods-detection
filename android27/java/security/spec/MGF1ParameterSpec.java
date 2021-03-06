package java.security.spec;

public class MGF1ParameterSpec implements AlgorithmParameterSpec
{
    public static final MGF1ParameterSpec SHA1;
    public static final MGF1ParameterSpec SHA224;
    public static final MGF1ParameterSpec SHA256;
    public static final MGF1ParameterSpec SHA384;
    public static final MGF1ParameterSpec SHA512;
    public static final MGF1ParameterSpec SHA512_224;
    public static final MGF1ParameterSpec SHA512_256;
    private String mdName;
    
    public MGF1ParameterSpec(final String mdName) {
        if (mdName == null) {
            throw new NullPointerException("digest algorithm is null");
        }
        this.mdName = mdName;
    }
    
    public String getDigestAlgorithm() {
        return this.mdName;
    }
    
    static {
        SHA1 = new MGF1ParameterSpec("SHA-1");
        SHA224 = new MGF1ParameterSpec("SHA-224");
        SHA256 = new MGF1ParameterSpec("SHA-256");
        SHA384 = new MGF1ParameterSpec("SHA-384");
        SHA512 = new MGF1ParameterSpec("SHA-512");
        SHA512_224 = new MGF1ParameterSpec("SHA-512/224");
        SHA512_256 = new MGF1ParameterSpec("SHA-512/256");
    }
}
