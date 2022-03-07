package javax.crypto.interfaces;

import java.security.*;
import java.math.*;

public interface DHPrivateKey extends DHKey, PrivateKey
{
    public static final long serialVersionUID = 2211791113380396553L;
    
    BigInteger getX();
}
