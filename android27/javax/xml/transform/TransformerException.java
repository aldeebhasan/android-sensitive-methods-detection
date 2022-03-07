package javax.xml.transform;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.security.*;

public class TransformerException extends Exception
{
    private static final long serialVersionUID = 975798773772956428L;
    SourceLocator locator;
    Throwable containedException;
    
    public SourceLocator getLocator() {
        return this.locator;
    }
    
    public void setLocator(final SourceLocator locator) {
        this.locator = locator;
    }
    
    public Throwable getException() {
        return this.containedException;
    }
    
    @Override
    public Throwable getCause() {
        return (this.containedException == this) ? null : this.containedException;
    }
    
    @Override
    public synchronized Throwable initCause(final Throwable containedException) {
        if (this.containedException != null) {
            throw new IllegalStateException("Can't overwrite cause");
        }
        if (containedException == this) {
            throw new IllegalArgumentException("Self-causation not permitted");
        }
        this.containedException = containedException;
        return this;
    }
    
    public TransformerException(final String s) {
        this(s, null, null);
    }
    
    public TransformerException(final Throwable t) {
        this(null, null, t);
    }
    
    public TransformerException(final String s, final Throwable t) {
        this(s, null, t);
    }
    
    public TransformerException(final String s, final SourceLocator sourceLocator) {
        this(s, sourceLocator, null);
    }
    
    public TransformerException(final String s, final SourceLocator locator, final Throwable containedException) {
        super((s == null || s.length() == 0) ? ((containedException == null) ? "" : containedException.toString()) : s);
        this.containedException = containedException;
        this.locator = locator;
    }
    
    public String getMessageAndLocation() {
        final StringBuilder sb = new StringBuilder();
        sb.append(Objects.toString(super.getMessage(), ""));
        sb.append(Objects.toString(this.getLocationAsString(), ""));
        return sb.toString();
    }
    
    public String getLocationAsString() {
        if (this.locator == null) {
            return null;
        }
        if (System.getSecurityManager() == null) {
            return this.getLocationString();
        }
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return TransformerException.this.getLocationString();
            }
        }, new AccessControlContext(new ProtectionDomain[] { this.getNonPrivDomain() }));
    }
    
    private String getLocationString() {
        if (this.locator == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        final String systemId = this.locator.getSystemId();
        final int lineNumber = this.locator.getLineNumber();
        final int columnNumber = this.locator.getColumnNumber();
        if (null != systemId) {
            sb.append("; SystemID: ");
            sb.append(systemId);
        }
        if (lineNumber != 0) {
            sb.append("; Line#: ");
            sb.append(lineNumber);
        }
        if (columnNumber != 0) {
            sb.append("; Column#: ");
            sb.append(columnNumber);
        }
        return sb.toString();
    }
    
    @Override
    public void printStackTrace() {
        this.printStackTrace(new PrintWriter(System.err, true));
    }
    
    @Override
    public void printStackTrace(final PrintStream printStream) {
        this.printStackTrace(new PrintWriter(printStream));
    }
    
    @Override
    public void printStackTrace(PrintWriter printWriter) {
        if (printWriter == null) {
            printWriter = new PrintWriter(System.err, true);
        }
        try {
            final String locationAsString = this.getLocationAsString();
            if (null != locationAsString) {
                printWriter.println(locationAsString);
            }
            super.printStackTrace(printWriter);
        }
        catch (Throwable t) {}
        Throwable exception = this.getException();
        for (int n = 0; n < 10 && null != exception; ++n) {
            printWriter.println("---------");
            try {
                if (exception instanceof TransformerException) {
                    final String locationAsString2 = ((TransformerException)exception).getLocationAsString();
                    if (null != locationAsString2) {
                        printWriter.println(locationAsString2);
                    }
                }
                exception.printStackTrace(printWriter);
            }
            catch (Throwable t2) {
                printWriter.println("Could not print stack trace...");
            }
            try {
                final Method method = ((TransformerException)exception).getClass().getMethod("getException", (Class<?>[])null);
                Label_0158: {
                    if (null == method) {
                        break Label_0158;
                    }
                    final TransformerException ex = (TransformerException)exception;
                    exception = (Throwable)method.invoke(exception, (Object[])null);
                    if (ex == exception) {
                        break;
                    }
                    try {
                        continue;
                        exception = null;
                    }
                    catch (IllegalAccessException ex2) {
                        exception = null;
                    }
                }
            }
            catch (InvocationTargetException ex3) {}
            catch (IllegalAccessException ex4) {}
            catch (NoSuchMethodException ex5) {}
        }
        printWriter.flush();
    }
    
    private ProtectionDomain getNonPrivDomain() {
        return new ProtectionDomain(new CodeSource(null, (CodeSigner[])null), new Permissions());
    }
}
