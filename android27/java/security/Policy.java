package java.security;

import java.util.concurrent.atomic.*;
import sun.security.util.*;
import sun.security.provider.*;
import sun.security.jca.*;
import java.util.*;

public abstract class Policy
{
    public static final PermissionCollection UNSUPPORTED_EMPTY_COLLECTION;
    private static AtomicReference<PolicyInfo> policy;
    private static final Debug debug;
    private WeakHashMap<ProtectionDomain.Key, PermissionCollection> pdMapping;
    
    static boolean isSet() {
        final PolicyInfo policyInfo = Policy.policy.get();
        return policyInfo.policy != null && policyInfo.initialized;
    }
    
    private static void checkPermission(final String s) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new SecurityPermission("createPolicy." + s));
        }
    }
    
    public static Policy getPolicy() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.GET_POLICY_PERMISSION);
        }
        return getPolicyNoCheck();
    }
    
    static Policy getPolicyNoCheck() {
        final PolicyInfo policyInfo = Policy.policy.get();
        if (!policyInfo.initialized || policyInfo.policy == null) {
            synchronized (Policy.class) {
                PolicyInfo policyInfo2 = Policy.policy.get();
                if (policyInfo2.policy == null) {
                    String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
                        @Override
                        public String run() {
                            return Security.getProperty("policy.provider");
                        }
                    });
                    if (s == null) {
                        s = "sun.security.provider.PolicyFile";
                    }
                    try {
                        policyInfo2 = new PolicyInfo((Policy)Class.forName(s).newInstance(), true);
                    }
                    catch (Exception ex) {
                        final PolicyFile policyFile = new PolicyFile();
                        Policy.policy.set(new PolicyInfo(policyFile, false));
                        final Policy policy = AccessController.doPrivileged((PrivilegedAction<Policy>)new PrivilegedAction<Policy>() {
                            @Override
                            public Policy run() {
                                try {
                                    ClassLoader classLoader = ClassLoader.getSystemClassLoader();
                                    ClassLoader classLoader2 = null;
                                    while (classLoader != null) {
                                        classLoader2 = classLoader;
                                        classLoader = classLoader.getParent();
                                    }
                                    return (classLoader2 != null) ? ((Policy)Class.forName(s, true, classLoader2).newInstance()) : null;
                                }
                                catch (Exception ex) {
                                    if (Policy.debug != null) {
                                        Policy.debug.println("policy provider " + s + " not available");
                                        ex.printStackTrace();
                                    }
                                    return null;
                                }
                            }
                        });
                        if (policy != null) {
                            policyInfo2 = new PolicyInfo(policy, true);
                        }
                        else {
                            if (Policy.debug != null) {
                                Policy.debug.println("using sun.security.provider.PolicyFile");
                            }
                            policyInfo2 = new PolicyInfo(policyFile, true);
                        }
                    }
                    Policy.policy.set(policyInfo2);
                }
                return policyInfo2.policy;
            }
        }
        return policyInfo.policy;
    }
    
    public static void setPolicy(final Policy policy) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new SecurityPermission("setPolicy"));
        }
        if (policy != null) {
            initPolicy(policy);
        }
        synchronized (Policy.class) {
            Policy.policy.set(new PolicyInfo(policy, policy != null));
        }
    }
    
    private static void initPolicy(final Policy policy) {
        final ProtectionDomain protectionDomain = AccessController.doPrivileged((PrivilegedAction<ProtectionDomain>)new PrivilegedAction<ProtectionDomain>() {
            @Override
            public ProtectionDomain run() {
                return policy.getClass().getProtectionDomain();
            }
        });
        PermissionCollection permissions = null;
        synchronized (policy) {
            if (policy.pdMapping == null) {
                policy.pdMapping = new WeakHashMap<ProtectionDomain.Key, PermissionCollection>();
            }
        }
        if (protectionDomain.getCodeSource() != null) {
            final Policy policy2 = Policy.policy.get().policy;
            if (policy2 != null) {
                permissions = policy2.getPermissions(protectionDomain);
            }
            if (permissions == null) {
                permissions = new Permissions();
                permissions.add(SecurityConstants.ALL_PERMISSION);
            }
            synchronized (policy.pdMapping) {
                policy.pdMapping.put(protectionDomain.key, permissions);
            }
        }
    }
    
    public static Policy getInstance(final String s, final Parameters parameters) throws NoSuchAlgorithmException {
        checkPermission(s);
        try {
            final GetInstance.Instance instance = GetInstance.getInstance("Policy", PolicySpi.class, s, parameters);
            return new PolicyDelegate((PolicySpi)instance.impl, instance.provider, s, parameters);
        }
        catch (NoSuchAlgorithmException ex) {
            return handleException(ex);
        }
    }
    
    public static Policy getInstance(final String s, final Parameters parameters, final String s2) throws NoSuchProviderException, NoSuchAlgorithmException {
        if (s2 == null || s2.length() == 0) {
            throw new IllegalArgumentException("missing provider");
        }
        checkPermission(s);
        try {
            final GetInstance.Instance instance = GetInstance.getInstance("Policy", PolicySpi.class, s, parameters, s2);
            return new PolicyDelegate((PolicySpi)instance.impl, instance.provider, s, parameters);
        }
        catch (NoSuchAlgorithmException ex) {
            return handleException(ex);
        }
    }
    
    public static Policy getInstance(final String s, final Parameters parameters, final Provider provider) throws NoSuchAlgorithmException {
        if (provider == null) {
            throw new IllegalArgumentException("missing provider");
        }
        checkPermission(s);
        try {
            final GetInstance.Instance instance = GetInstance.getInstance("Policy", PolicySpi.class, s, parameters, provider);
            return new PolicyDelegate((PolicySpi)instance.impl, instance.provider, s, parameters);
        }
        catch (NoSuchAlgorithmException ex) {
            return handleException(ex);
        }
    }
    
    private static Policy handleException(final NoSuchAlgorithmException ex) throws NoSuchAlgorithmException {
        final Throwable cause = ex.getCause();
        if (cause instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)cause;
        }
        throw ex;
    }
    
    public Provider getProvider() {
        return null;
    }
    
    public String getType() {
        return null;
    }
    
    public Parameters getParameters() {
        return null;
    }
    
    public PermissionCollection getPermissions(final CodeSource codeSource) {
        return Policy.UNSUPPORTED_EMPTY_COLLECTION;
    }
    
    public PermissionCollection getPermissions(final ProtectionDomain protectionDomain) {
        PermissionCollection collection = null;
        if (protectionDomain == null) {
            return new Permissions();
        }
        if (this.pdMapping == null) {
            initPolicy(this);
        }
        synchronized (this.pdMapping) {
            collection = this.pdMapping.get(protectionDomain.key);
        }
        if (collection != null) {
            final Permissions permissions = new Permissions();
            synchronized (collection) {
                final Enumeration<Permission> elements = collection.elements();
                while (elements.hasMoreElements()) {
                    permissions.add(elements.nextElement());
                }
            }
            return permissions;
        }
        PermissionCollection permissions2 = this.getPermissions(protectionDomain.getCodeSource());
        if (permissions2 == null || permissions2 == Policy.UNSUPPORTED_EMPTY_COLLECTION) {
            permissions2 = new Permissions();
        }
        this.addStaticPerms(permissions2, protectionDomain.getPermissions());
        return permissions2;
    }
    
    private void addStaticPerms(final PermissionCollection collection, final PermissionCollection collection2) {
        if (collection2 != null) {
            synchronized (collection2) {
                final Enumeration<Permission> elements = collection2.elements();
                while (elements.hasMoreElements()) {
                    collection.add(elements.nextElement());
                }
            }
        }
    }
    
    public boolean implies(final ProtectionDomain protectionDomain, final Permission permission) {
        if (this.pdMapping == null) {
            initPolicy(this);
        }
        final PermissionCollection collection;
        synchronized (this.pdMapping) {
            collection = this.pdMapping.get(protectionDomain.key);
        }
        if (collection != null) {
            return collection.implies(permission);
        }
        final PermissionCollection permissions = this.getPermissions(protectionDomain);
        if (permissions == null) {
            return false;
        }
        synchronized (this.pdMapping) {
            this.pdMapping.put(protectionDomain.key, permissions);
        }
        return permissions.implies(permission);
    }
    
    public void refresh() {
    }
    
    static {
        UNSUPPORTED_EMPTY_COLLECTION = new UnsupportedEmptyCollection();
        Policy.policy = new AtomicReference<PolicyInfo>(new PolicyInfo(null, false));
        debug = Debug.getInstance("policy");
    }
    
    public interface Parameters
    {
    }
    
    private static class PolicyDelegate extends Policy
    {
        private PolicySpi spi;
        private Provider p;
        private String type;
        private Parameters params;
        
        private PolicyDelegate(final PolicySpi spi, final Provider p4, final String type, final Parameters params) {
            this.spi = spi;
            this.p = p4;
            this.type = type;
            this.params = params;
        }
        
        @Override
        public String getType() {
            return this.type;
        }
        
        @Override
        public Parameters getParameters() {
            return this.params;
        }
        
        @Override
        public Provider getProvider() {
            return this.p;
        }
        
        @Override
        public PermissionCollection getPermissions(final CodeSource codeSource) {
            return this.spi.engineGetPermissions(codeSource);
        }
        
        @Override
        public PermissionCollection getPermissions(final ProtectionDomain protectionDomain) {
            return this.spi.engineGetPermissions(protectionDomain);
        }
        
        @Override
        public boolean implies(final ProtectionDomain protectionDomain, final Permission permission) {
            return this.spi.engineImplies(protectionDomain, permission);
        }
        
        @Override
        public void refresh() {
            this.spi.engineRefresh();
        }
    }
    
    private static class PolicyInfo
    {
        final Policy policy;
        final boolean initialized;
        
        PolicyInfo(final Policy policy, final boolean initialized) {
            this.policy = policy;
            this.initialized = initialized;
        }
    }
    
    private static class UnsupportedEmptyCollection extends PermissionCollection
    {
        private static final long serialVersionUID = -8492269157353014774L;
        private Permissions perms;
        
        public UnsupportedEmptyCollection() {
            (this.perms = new Permissions()).setReadOnly();
        }
        
        @Override
        public void add(final Permission permission) {
            this.perms.add(permission);
        }
        
        @Override
        public boolean implies(final Permission permission) {
            return this.perms.implies(permission);
        }
        
        @Override
        public Enumeration<Permission> elements() {
            return this.perms.elements();
        }
    }
}
