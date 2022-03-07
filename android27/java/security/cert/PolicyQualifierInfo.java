package java.security.cert;

import sun.security.util.*;
import java.io.*;
import sun.misc.*;

public class PolicyQualifierInfo
{
    private byte[] mEncoded;
    private String mId;
    private byte[] mData;
    private String pqiString;
    
    public PolicyQualifierInfo(final byte[] array) throws IOException {
        this.mEncoded = array.clone();
        final DerValue derValue = new DerValue(this.mEncoded);
        if (derValue.tag != 48) {
            throw new IOException("Invalid encoding for PolicyQualifierInfo");
        }
        this.mId = derValue.data.getDerValue().getOID().toString();
        final byte[] byteArray = derValue.data.toByteArray();
        if (byteArray == null) {
            this.mData = null;
        }
        else {
            System.arraycopy(byteArray, 0, this.mData = new byte[byteArray.length], 0, byteArray.length);
        }
    }
    
    public final String getPolicyQualifierId() {
        return this.mId;
    }
    
    public final byte[] getEncoded() {
        return this.mEncoded.clone();
    }
    
    public final byte[] getPolicyQualifier() {
        return (byte[])((this.mData == null) ? null : ((byte[])this.mData.clone()));
    }
    
    @Override
    public String toString() {
        if (this.pqiString != null) {
            return this.pqiString;
        }
        final HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
        final StringBuffer sb = new StringBuffer();
        sb.append("PolicyQualifierInfo: [\n");
        sb.append("  qualifierID: " + this.mId + "\n");
        sb.append("  qualifier: " + ((this.mData == null) ? "null" : hexDumpEncoder.encodeBuffer(this.mData)) + "\n");
        sb.append("]");
        return this.pqiString = sb.toString();
    }
}
