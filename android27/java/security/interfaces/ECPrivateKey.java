package java.security.interfaces;

import java.security.*;
import java.math.*;

public interface ECPrivateKey extends PrivateKey, ECKey
{
    public static final long serialVersionUID = -7896394956925609184L;
    
    BigInteger getS();
}
