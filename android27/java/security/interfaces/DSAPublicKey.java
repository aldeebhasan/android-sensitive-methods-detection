package java.security.interfaces;

import java.security.*;
import java.math.*;

public interface DSAPublicKey extends DSAKey, PublicKey
{
    public static final long serialVersionUID = 1234526332779022332L;
    
    BigInteger getY();
}
