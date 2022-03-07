package java.security.spec;

import java.util.*;

public class PSSParameterSpec implements AlgorithmParameterSpec
{
    private final String mdName;
    private final String mgfName;
    private final AlgorithmParameterSpec mgfSpec;
    private final int saltLen;
    private final int trailerField;
    public static final int TRAILER_FIELD_BC = 1;
    public static final PSSParameterSpec DEFAULT;
    
    private PSSParameterSpec() {
        throw new RuntimeException("default constructor not allowed");
    }
    
    public PSSParameterSpec(final String mdName, final String mgfName, final AlgorithmParameterSpec mgfSpec, final int saltLen, final int trailerField) {
        Objects.requireNonNull(mdName, "digest algorithm is null");
        Objects.requireNonNull(mgfName, "mask generation function algorithm is null");
        if (saltLen < 0) {
            throw new IllegalArgumentException("negative saltLen value: " + saltLen);
        }
        if (trailerField < 0) {
            throw new IllegalArgumentException("negative trailerField: " + trailerField);
        }
        this.mdName = mdName;
        this.mgfName = mgfName;
        this.mgfSpec = mgfSpec;
        this.saltLen = saltLen;
        this.trailerField = trailerField;
    }
    
    public PSSParameterSpec(final int n) {
        this("SHA-1", "MGF1", MGF1ParameterSpec.SHA1, n, 1);
    }
    
    public String getDigestAlgorithm() {
        return this.mdName;
    }
    
    public String getMGFAlgorithm() {
        return this.mgfName;
    }
    
    public AlgorithmParameterSpec getMGFParameters() {
        return this.mgfSpec;
    }
    
    public int getSaltLength() {
        return this.saltLen;
    }
    
    public int getTrailerField() {
        return this.trailerField;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MD: " + this.mdName + "\n").append("MGF: " + this.mgfSpec + "\n").append("SaltLength: " + this.saltLen + "\n").append("TrailerField: " + this.trailerField + "\n");
        return sb.toString();
    }
    
    static {
        DEFAULT = new PSSParameterSpec("SHA-1", "MGF1", MGF1ParameterSpec.SHA1, 20, 1);
    }
}
