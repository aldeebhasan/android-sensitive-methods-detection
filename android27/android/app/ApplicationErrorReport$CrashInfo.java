package android.app;

import android.os.*;
import android.util.*;

public static class CrashInfo
{
    public String exceptionClassName;
    public String exceptionMessage;
    public String stackTrace;
    public String throwClassName;
    public String throwFileName;
    public int throwLineNumber;
    public String throwMethodName;
    
    public CrashInfo() {
        throw new RuntimeException("Stub!");
    }
    
    public CrashInfo(final Throwable tr) {
        throw new RuntimeException("Stub!");
    }
    
    public CrashInfo(final Parcel in) {
        throw new RuntimeException("Stub!");
    }
    
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    public void dump(final Printer pw, final String prefix) {
        throw new RuntimeException("Stub!");
    }
}
