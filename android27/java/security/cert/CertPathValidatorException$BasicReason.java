package java.security.cert;

public enum BasicReason implements Reason
{
    UNSPECIFIED, 
    EXPIRED, 
    NOT_YET_VALID, 
    REVOKED, 
    UNDETERMINED_REVOCATION_STATUS, 
    INVALID_SIGNATURE, 
    ALGORITHM_CONSTRAINED;
}
