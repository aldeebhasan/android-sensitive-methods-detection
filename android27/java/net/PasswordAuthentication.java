package java.net;

public final class PasswordAuthentication
{
    private String userName;
    private char[] password;
    
    public PasswordAuthentication(final String userName, final char[] array) {
        this.userName = userName;
        this.password = array.clone();
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public char[] getPassword() {
        return this.password;
    }
}
