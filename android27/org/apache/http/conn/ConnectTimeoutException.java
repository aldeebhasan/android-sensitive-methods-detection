package org.apache.http.conn;

import java.io.*;

@Deprecated
public class ConnectTimeoutException extends InterruptedIOException
{
    public ConnectTimeoutException() {
        throw new RuntimeException("Stub!");
    }
    
    public ConnectTimeoutException(final String message) {
        throw new RuntimeException("Stub!");
    }
}
