package junit.framework;

import java.lang.reflect.*;
import java.util.*;

public class TestSuite implements Test
{
    public TestSuite() {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuite(final Class<?> theClass) {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuite(final Class<? extends TestCase> theClass, final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuite(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuite(final Class<?>... classes) {
        throw new RuntimeException("Stub!");
    }
    
    public TestSuite(final Class<? extends TestCase>[] classes, final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public static Test createTest(final Class<?> theClass, final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public static Constructor<?> getTestConstructor(final Class<?> theClass) throws NoSuchMethodException {
        throw new RuntimeException("Stub!");
    }
    
    public static Test warning(final String message) {
        throw new RuntimeException("Stub!");
    }
    
    public void addTest(final Test test) {
        throw new RuntimeException("Stub!");
    }
    
    public void addTestSuite(final Class<? extends TestCase> testClass) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int countTestCases() {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void run(final TestResult result) {
        throw new RuntimeException("Stub!");
    }
    
    public void runTest(final Test test, final TestResult result) {
        throw new RuntimeException("Stub!");
    }
    
    public void setName(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public Test testAt(final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public int testCount() {
        throw new RuntimeException("Stub!");
    }
    
    public Enumeration<Test> tests() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
