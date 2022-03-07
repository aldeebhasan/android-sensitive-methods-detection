package javax.security.auth.callback;

import java.io.*;

public class PasswordCallback implements Callback, Serializable
{
    private static final long serialVersionUID = 2267422647454909926L;
    private String prompt;
    private boolean echoOn;
    private char[] inputPassword;
    
    public PasswordCallback(final String prompt, final boolean echoOn) {
        if (prompt == null || prompt.length() == 0) {
            throw new IllegalArgumentException();
        }
        this.prompt = prompt;
        this.echoOn = echoOn;
    }
    
    public String getPrompt() {
        return this.prompt;
    }
    
    public boolean isEchoOn() {
        return this.echoOn;
    }
    
    public void setPassword(final char[] array) {
        this.inputPassword = (char[])((array == null) ? null : ((char[])array.clone()));
    }
    
    public char[] getPassword() {
        return (char[])((this.inputPassword == null) ? null : ((char[])this.inputPassword.clone()));
    }
    
    public void clearPassword() {
        if (this.inputPassword != null) {
            for (int i = 0; i < this.inputPassword.length; ++i) {
                this.inputPassword[i] = ' ';
            }
        }
    }
}
