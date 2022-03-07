package java.security;

import javax.security.auth.callback.*;

public static class CallbackHandlerProtection implements ProtectionParameter
{
    private final CallbackHandler handler;
    
    public CallbackHandlerProtection(final CallbackHandler handler) {
        if (handler == null) {
            throw new NullPointerException("handler must not be null");
        }
        this.handler = handler;
    }
    
    public CallbackHandler getCallbackHandler() {
        return this.handler;
    }
}
