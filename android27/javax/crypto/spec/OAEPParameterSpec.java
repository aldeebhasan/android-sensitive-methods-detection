package javax.crypto.spec;

import java.security.spec.*;

public class OAEPParameterSpec implements AlgorithmParameterSpec
{
    private String mdName;
    private String mgfName;
    private AlgorithmParameterSpec mgfSpec;
    private PSource pSrc;
    public static final OAEPParameterSpec DEFAULT;
    
    private OAEPParameterSpec() {
        this.mdName = "SHA-1";
        this.mgfName = "MGF1";
        this.mgfSpec = MGF1ParameterSpec.SHA1;
        this.pSrc = PSource.PSpecified.DEFAULT;
    }
    
    public OAEPParameterSpec(final String mdName, final String mgfName, final AlgorithmParameterSpec mgfSpec, final PSource pSrc) {
        this.mdName = "SHA-1";
        this.mgfName = "MGF1";
        this.mgfSpec = MGF1ParameterSpec.SHA1;
        this.pSrc = PSource.PSpecified.DEFAULT;
        if (mdName == null) {
            throw new NullPointerException("digest algorithm is null");
        }
        if (mgfName == null) {
            throw new NullPointerException("mask generation function algorithm is null");
        }
        if (pSrc == null) {
            throw new NullPointerException("source of the encoding input is null");
        }
        this.mdName = mdName;
        this.mgfName = mgfName;
        this.mgfSpec = mgfSpec;
        this.pSrc = pSrc;
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
    
    public PSource getPSource() {
        return this.pSrc;
    }
    
    static {
        DEFAULT = new OAEPParameterSpec();
    }
}
