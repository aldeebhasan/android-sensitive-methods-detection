package java.security.interfaces;

import java.math.*;
import java.security.spec.*;

public interface RSAKey
{
    BigInteger getModulus();
    
    default AlgorithmParameterSpec getParams() {
        return null;
    }
}
