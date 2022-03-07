package javax.crypto.interfaces;

import java.security.*;
import java.math.*;

public interface DHPublicKey extends DHKey, PublicKey
{
    public static final long serialVersionUID = -6628103563352519193L;
    
    BigInteger getY();
}
