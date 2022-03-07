package android.database.sqlite;

import android.database.*;

public static final class OpenParams
{
    OpenParams() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLookasideSlotSize() {
        throw new RuntimeException("Stub!");
    }
    
    public int getLookasideSlotCount() {
        throw new RuntimeException("Stub!");
    }
    
    public int getOpenFlags() {
        throw new RuntimeException("Stub!");
    }
    
    public CursorFactory getCursorFactory() {
        throw new RuntimeException("Stub!");
    }
    
    public DatabaseErrorHandler getErrorHandler() {
        throw new RuntimeException("Stub!");
    }
    
    public long getIdleConnectionTimeout() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Builder
    {
        public Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public Builder(final OpenParams params) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setLookasideConfig(final int slotSize, final int slotCount) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setOpenFlags(final int openFlags) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addOpenFlags(final int openFlags) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder removeOpenFlags(final int openFlags) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setCursorFactory(final CursorFactory cursorFactory) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setErrorHandler(final DatabaseErrorHandler errorHandler) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setIdleConnectionTimeout(final long idleConnectionTimeoutMs) {
            throw new RuntimeException("Stub!");
        }
        
        public OpenParams build() {
            throw new RuntimeException("Stub!");
        }
    }
}
