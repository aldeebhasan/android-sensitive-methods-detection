package javax.security.auth.callback;

import java.io.*;

public interface CallbackHandler
{
    void handle(final Callback[] p0) throws IOException, UnsupportedCallbackException;
}
