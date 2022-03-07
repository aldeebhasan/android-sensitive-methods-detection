package android.text;

public abstract class LoginFilter implements InputFilter
{
    LoginFilter() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence filter(final CharSequence source, final int start, final int end, final Spanned dest, final int dstart, final int dend) {
        throw new RuntimeException("Stub!");
    }
    
    public void onStart() {
        throw new RuntimeException("Stub!");
    }
    
    public void onInvalidCharacter(final char c) {
        throw new RuntimeException("Stub!");
    }
    
    public void onStop() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean isAllowed(final char p0);
    
    public static class UsernameFilterGMail extends LoginFilter
    {
        public UsernameFilterGMail() {
            throw new RuntimeException("Stub!");
        }
        
        public UsernameFilterGMail(final boolean appendInvalid) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean isAllowed(final char c) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class UsernameFilterGeneric extends LoginFilter
    {
        public UsernameFilterGeneric() {
            throw new RuntimeException("Stub!");
        }
        
        public UsernameFilterGeneric(final boolean appendInvalid) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean isAllowed(final char c) {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class PasswordFilterGMail extends LoginFilter
    {
        public PasswordFilterGMail() {
            throw new RuntimeException("Stub!");
        }
        
        public PasswordFilterGMail(final boolean appendInvalid) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean isAllowed(final char c) {
            throw new RuntimeException("Stub!");
        }
    }
}
