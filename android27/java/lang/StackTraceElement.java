package java.lang;

import java.io.*;
import java.util.*;

public final class StackTraceElement implements Serializable
{
    private String declaringClass;
    private String methodName;
    private String fileName;
    private int lineNumber;
    private static final long serialVersionUID = 6992337162326171013L;
    
    public StackTraceElement(final String s, final String s2, final String fileName, final int lineNumber) {
        this.declaringClass = Objects.requireNonNull(s, "Declaring class is null");
        this.methodName = Objects.requireNonNull(s2, "Method name is null");
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }
    
    public String getFileName() {
        return this.fileName;
    }
    
    public int getLineNumber() {
        return this.lineNumber;
    }
    
    public String getClassName() {
        return this.declaringClass;
    }
    
    public String getMethodName() {
        return this.methodName;
    }
    
    public boolean isNativeMethod() {
        return this.lineNumber == -2;
    }
    
    @Override
    public String toString() {
        return this.getClassName() + "." + this.methodName + (this.isNativeMethod() ? "(Native Method)" : ((this.fileName != null && this.lineNumber >= 0) ? ("(" + this.fileName + ":" + this.lineNumber + ")") : ((this.fileName != null) ? ("(" + this.fileName + ")") : "(Unknown Source)")));
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StackTraceElement)) {
            return false;
        }
        final StackTraceElement stackTraceElement = (StackTraceElement)o;
        return stackTraceElement.declaringClass.equals(this.declaringClass) && stackTraceElement.lineNumber == this.lineNumber && Objects.equals(this.methodName, stackTraceElement.methodName) && Objects.equals(this.fileName, stackTraceElement.fileName);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * this.declaringClass.hashCode() + this.methodName.hashCode()) + Objects.hashCode(this.fileName)) + this.lineNumber;
    }
}
