package java.security.interfaces;

import java.security.*;
import java.math.*;

public interface DSAPrivateKey extends DSAKey, PrivateKey
{
    public static final long serialVersionUID = 7776497482533790279L;
    
    BigInteger getX();
}
