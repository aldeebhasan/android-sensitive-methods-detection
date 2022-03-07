package android.test.suitebuilder;

import junit.framework.*;
import java.lang.annotation.*;
import java.lang.reflect.*;

@Deprecated
public class TestMethod
{
    public TestMethod(final Method method, final Class<? extends TestCase> enclosingClass) {
        throw new RuntimeException("Stub!");
    }
    
    public TestMethod(final String methodName, final Class<? extends TestCase> enclosingClass) {
        throw new RuntimeException("Stub!");
    }
    
    public TestMethod(final TestCase testCase) {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    public String getEnclosingClassname() {
        throw new RuntimeException("Stub!");
    }
    
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        throw new RuntimeException("Stub!");
    }
    
    public Class<? extends TestCase> getEnclosingClass() {
        throw new RuntimeException("Stub!");
    }
    
    public TestCase createTest() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
