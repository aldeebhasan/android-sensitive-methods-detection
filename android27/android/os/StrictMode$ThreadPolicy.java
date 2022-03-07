package android.os;

public static final class ThreadPolicy
{
    public static final ThreadPolicy LAX;
    
    ThreadPolicy() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        LAX = null;
    }
    
    public static final class Builder
    {
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final ThreadPolicy policy) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder detectAll() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder permitAll() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder detectNetwork() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder permitNetwork() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder detectDiskReads() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder permitDiskReads() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder detectCustomSlowCalls() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder permitCustomSlowCalls() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder permitResourceMismatches() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder detectUnbufferedIo() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder permitUnbufferedIo() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder detectResourceMismatches() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder detectDiskWrites() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder permitDiskWrites() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder penaltyDialog() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder penaltyDeath() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder penaltyDeathOnNetwork() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder penaltyFlashScreen() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder penaltyLog() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder penaltyDropBox() {
            throw new RuntimeException("Stub!");
        }
        
        public ThreadPolicy build() {
            throw new RuntimeException("Stub!");
        }
    }
}
