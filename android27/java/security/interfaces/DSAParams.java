package java.security.interfaces;

import java.math.*;

public interface DSAParams
{
    BigInteger getP();
    
    BigInteger getQ();
    
    BigInteger getG();
}
