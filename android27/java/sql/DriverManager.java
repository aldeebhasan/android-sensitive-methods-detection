package java.sql;

import java.util.concurrent.*;
import sun.reflect.*;
import java.io.*;
import java.security.*;
import java.util.*;

public class DriverManager
{
    private static final CopyOnWriteArrayList<DriverInfo> registeredDrivers;
    private static volatile int loginTimeout;
    private static volatile PrintWriter logWriter;
    private static volatile PrintStream logStream;
    private static final Object logSync;
    static final SQLPermission SET_LOG_PERMISSION;
    static final SQLPermission DEREGISTER_DRIVER_PERMISSION;
    
    public static PrintWriter getLogWriter() {
        return DriverManager.logWriter;
    }
    
    public static void setLogWriter(final PrintWriter logWriter) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(DriverManager.SET_LOG_PERMISSION);
        }
        DriverManager.logStream = null;
        DriverManager.logWriter = logWriter;
    }
    
    @CallerSensitive
    public static Connection getConnection(final String s, final Properties properties) throws SQLException {
        return getConnection(s, properties, Reflection.getCallerClass());
    }
    
    @CallerSensitive
    public static Connection getConnection(final String s, final String s2, final String s3) throws SQLException {
        final Properties properties = new Properties();
        if (s2 != null) {
            ((Hashtable<String, String>)properties).put("user", s2);
        }
        if (s3 != null) {
            ((Hashtable<String, String>)properties).put("password", s3);
        }
        return getConnection(s, properties, Reflection.getCallerClass());
    }
    
    @CallerSensitive
    public static Connection getConnection(final String s) throws SQLException {
        return getConnection(s, new Properties(), Reflection.getCallerClass());
    }
    
    @CallerSensitive
    public static Driver getDriver(final String s) throws SQLException {
        println("DriverManager.getDriver(\"" + s + "\")");
        final Class<?> callerClass = Reflection.getCallerClass();
        for (final DriverInfo driverInfo : DriverManager.registeredDrivers) {
            if (isDriverAllowed(driverInfo.driver, callerClass)) {
                try {
                    if (driverInfo.driver.acceptsURL(s)) {
                        println("getDriver returning " + driverInfo.driver.getClass().getName());
                        return driverInfo.driver;
                    }
                    continue;
                }
                catch (SQLException ex) {}
            }
            else {
                println("    skipping: " + driverInfo.driver.getClass().getName());
            }
        }
        println("getDriver: no suitable driver");
        throw new SQLException("No suitable driver", "08001");
    }
    
    public static synchronized void registerDriver(final Driver driver) throws SQLException {
        registerDriver(driver, null);
    }
    
    public static synchronized void registerDriver(final Driver driver, final DriverAction driverAction) throws SQLException {
        if (driver != null) {
            DriverManager.registeredDrivers.addIfAbsent(new DriverInfo(driver, driverAction));
            println("registerDriver: " + driver);
            return;
        }
        throw new NullPointerException();
    }
    
    @CallerSensitive
    public static synchronized void deregisterDriver(final Driver driver) throws SQLException {
        if (driver == null) {
            return;
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(DriverManager.DEREGISTER_DRIVER_PERMISSION);
        }
        println("DriverManager.deregisterDriver: " + driver);
        final DriverInfo driverInfo = new DriverInfo(driver, null);
        if (DriverManager.registeredDrivers.contains(driverInfo)) {
            if (!isDriverAllowed(driver, Reflection.getCallerClass())) {
                throw new SecurityException();
            }
            final DriverInfo driverInfo2 = DriverManager.registeredDrivers.get(DriverManager.registeredDrivers.indexOf(driverInfo));
            if (driverInfo2.action() != null) {
                driverInfo2.action().deregister();
            }
            DriverManager.registeredDrivers.remove(driverInfo);
        }
        else {
            println("    couldn't find driver to unload");
        }
    }
    
    @CallerSensitive
    public static Enumeration<Driver> getDrivers() {
        final Vector<Driver> vector = new Vector<Driver>();
        final Class<?> callerClass = Reflection.getCallerClass();
        for (final DriverInfo driverInfo : DriverManager.registeredDrivers) {
            if (isDriverAllowed(driverInfo.driver, callerClass)) {
                vector.addElement(driverInfo.driver);
            }
            else {
                println("    skipping: " + driverInfo.getClass().getName());
            }
        }
        return vector.elements();
    }
    
    public static void setLoginTimeout(final int loginTimeout) {
        DriverManager.loginTimeout = loginTimeout;
    }
    
    public static int getLoginTimeout() {
        return DriverManager.loginTimeout;
    }
    
    @Deprecated
    public static void setLogStream(final PrintStream logStream) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(DriverManager.SET_LOG_PERMISSION);
        }
        if ((DriverManager.logStream = logStream) != null) {
            DriverManager.logWriter = new PrintWriter(logStream);
        }
        else {
            DriverManager.logWriter = null;
        }
    }
    
    @Deprecated
    public static PrintStream getLogStream() {
        return DriverManager.logStream;
    }
    
    public static void println(final String s) {
        synchronized (DriverManager.logSync) {
            if (DriverManager.logWriter != null) {
                DriverManager.logWriter.println(s);
                DriverManager.logWriter.flush();
            }
        }
    }
    
    private static boolean isDriverAllowed(final Driver driver, final Class<?> clazz) {
        return isDriverAllowed(driver, (clazz != null) ? clazz.getClassLoader() : null);
    }
    
    private static boolean isDriverAllowed(final Driver driver, final ClassLoader classLoader) {
        boolean b = false;
        if (driver != null) {
            Class<?> forName = null;
            try {
                forName = Class.forName(driver.getClass().getName(), true, classLoader);
            }
            catch (Exception ex) {}
            b = (forName == driver.getClass());
        }
        return b;
    }
    
    private static void loadInitialDrivers() {
        String s;
        try {
            s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
                @Override
                public String run() {
                    return System.getProperty("jdbc.drivers");
                }
            });
        }
        catch (Exception ex2) {
            s = null;
        }
        AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                final Iterator<Driver> iterator = ServiceLoader.load(Driver.class).iterator();
                try {
                    while (iterator.hasNext()) {
                        iterator.next();
                    }
                }
                catch (Throwable t) {}
                return null;
            }
        });
        println("DriverManager.initialize: jdbc.drivers = " + s);
        if (s == null || s.equals("")) {
            return;
        }
        final String[] split = s.split(":");
        println("number of Drivers:" + split.length);
        for (final String s2 : split) {
            try {
                println("DriverManager.Initialize: loading " + s2);
                Class.forName(s2, true, ClassLoader.getSystemClassLoader());
            }
            catch (Exception ex) {
                println("DriverManager.Initialize: load failed: " + ex);
            }
        }
    }
    
    private static Connection getConnection(final String s, final Properties properties, final Class<?> clazz) throws SQLException {
        ClassLoader contextClassLoader = (clazz != null) ? clazz.getClassLoader() : null;
        synchronized (DriverManager.class) {
            if (contextClassLoader == null) {
                contextClassLoader = Thread.currentThread().getContextClassLoader();
            }
        }
        if (s == null) {
            throw new SQLException("The url cannot be null", "08001");
        }
        println("DriverManager.getConnection(\"" + s + "\")");
        Object o = null;
        for (final DriverInfo driverInfo : DriverManager.registeredDrivers) {
            if (isDriverAllowed(driverInfo.driver, contextClassLoader)) {
                try {
                    println("    trying " + driverInfo.driver.getClass().getName());
                    final Connection connect = driverInfo.driver.connect(s, properties);
                    if (connect != null) {
                        println("getConnection returning " + driverInfo.driver.getClass().getName());
                        return connect;
                    }
                    continue;
                }
                catch (SQLException ex) {
                    if (o != null) {
                        continue;
                    }
                    o = ex;
                }
            }
            else {
                println("    skipping: " + driverInfo.getClass().getName());
            }
        }
        if (o != null) {
            println("getConnection failed: " + o);
            throw o;
        }
        println("getConnection: no suitable driver found for " + s);
        throw new SQLException("No suitable driver found for " + s, "08001");
    }
    
    static {
        registeredDrivers = new CopyOnWriteArrayList<DriverInfo>();
        DriverManager.loginTimeout = 0;
        DriverManager.logWriter = null;
        DriverManager.logStream = null;
        logSync = new Object();
        loadInitialDrivers();
        println("JDBC DriverManager initialized");
        SET_LOG_PERMISSION = new SQLPermission("setLog");
        DEREGISTER_DRIVER_PERMISSION = new SQLPermission("deregisterDriver");
    }
}
