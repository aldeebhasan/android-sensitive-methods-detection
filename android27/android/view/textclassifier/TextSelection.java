package android.view.textclassifier;

public final class TextSelection
{
    TextSelection() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSelectionStartIndex() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSelectionEndIndex() {
        throw new RuntimeException("Stub!");
    }
    
    public int getEntityCount() {
        throw new RuntimeException("Stub!");
    }
    
    public String getEntity(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public float getConfidenceScore(final String entity) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Builder
    {
        public Builder(final int startIndex, final int endIndex) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setEntityType(final String type, final float confidenceScore) {
            throw new RuntimeException("Stub!");
        }
        
        public TextSelection build() {
            throw new RuntimeException("Stub!");
        }
    }
}
