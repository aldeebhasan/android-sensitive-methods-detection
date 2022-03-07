package java.security.interfaces;

import java.security.*;
import java.math.*;

public interface RSAPrivateKey extends PrivateKey, RSAKey
{
    public static final long serialVersionUID = 5187144804936595022L;
    
    BigInteger getPrivateExponent();
}
