package javax.security.auth;

import sun.security.util.*;
import java.security.*;
import sun.misc.*;
import java.util.*;
import java.lang.ref.*;

public class SubjectDomainCombiner implements DomainCombiner
{
    private Subject subject;
    private WeakKeyValueMap<ProtectionDomain, ProtectionDomain> cachedPDs;
    private Set<Principal> principalSet;
    private Principal[] principals;
    private static final Debug debug;
    private static final boolean useJavaxPolicy;
    private static final boolean allowCaching;
    private static final JavaSecurityProtectionDomainAccess pdAccess;
    
    public SubjectDomainCombiner(final Subject subject) {
        this.cachedPDs = new WeakKeyValueMap<ProtectionDomain, ProtectionDomain>();
        this.subject = subject;
        if (subject.isReadOnly()) {
            this.principalSet = subject.getPrincipals();
            this.principals = this.principalSet.toArray(new Principal[this.principalSet.size()]);
        }
    }
    
    public Subject getSubject() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new AuthPermission("getSubjectFromDomainCombiner"));
        }
        return this.subject;
    }
    
    @Override
    public ProtectionDomain[] combine(ProtectionDomain[] optimize, final ProtectionDomain[] array) {
        if (SubjectDomainCombiner.debug != null) {
            if (this.subject == null) {
                SubjectDomainCombiner.debug.println("null subject");
            }
            else {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                    final /* synthetic */ Subject val$s = SubjectDomainCombiner.this.subject;
                    
                    @Override
                    public Void run() {
                        SubjectDomainCombiner.debug.println(this.val$s.toString());
                        return null;
                    }
                });
            }
            printInputDomains(optimize, array);
        }
        if (optimize == null || optimize.length == 0) {
            return array;
        }
        optimize = optimize(optimize);
        if (SubjectDomainCombiner.debug != null) {
            SubjectDomainCombiner.debug.println("after optimize");
            printInputDomains(optimize, array);
        }
        if (optimize == null && array == null) {
            return null;
        }
        if (SubjectDomainCombiner.useJavaxPolicy) {
            return this.combineJavaxPolicy(optimize, array);
        }
        final int n = (optimize == null) ? 0 : optimize.length;
        final int n2 = (array == null) ? 0 : array.length;
        ProtectionDomain[] optimize2 = new ProtectionDomain[n + n2];
        boolean b = true;
        synchronized (this.cachedPDs) {
            if (!this.subject.isReadOnly() && !this.subject.getPrincipals().equals(this.principalSet)) {
                final Set<Principal> principals = this.subject.getPrincipals();
                synchronized (principals) {
                    this.principalSet = new HashSet<Principal>(principals);
                }
                this.principals = this.principalSet.toArray(new Principal[this.principalSet.size()]);
                this.cachedPDs.clear();
                if (SubjectDomainCombiner.debug != null) {
                    SubjectDomainCombiner.debug.println("Subject mutated - clearing cache");
                }
            }
            for (int i = 0; i < n; ++i) {
                final ProtectionDomain protectionDomain = optimize[i];
                ProtectionDomain protectionDomain2 = this.cachedPDs.getValue(protectionDomain);
                if (protectionDomain2 == null) {
                    if (SubjectDomainCombiner.pdAccess.getStaticPermissionsField(protectionDomain)) {
                        protectionDomain2 = new ProtectionDomain(protectionDomain.getCodeSource(), protectionDomain.getPermissions());
                    }
                    else {
                        protectionDomain2 = new ProtectionDomain(protectionDomain.getCodeSource(), protectionDomain.getPermissions(), protectionDomain.getClassLoader(), this.principals);
                    }
                    this.cachedPDs.putValue(protectionDomain, protectionDomain2);
                }
                else {
                    b = false;
                }
                optimize2[i] = protectionDomain2;
            }
        }
        if (SubjectDomainCombiner.debug != null) {
            SubjectDomainCombiner.debug.println("updated current: ");
            for (int j = 0; j < n; ++j) {
                SubjectDomainCombiner.debug.println("\tupdated[" + j + "] = " + printDomain(optimize2[j]));
            }
        }
        if (n2 > 0) {
            System.arraycopy(array, 0, optimize2, n, n2);
            if (!b) {
                optimize2 = optimize(optimize2);
            }
        }
        if (SubjectDomainCombiner.debug != null) {
            if (optimize2 == null || optimize2.length == 0) {
                SubjectDomainCombiner.debug.println("returning null");
            }
            else {
                SubjectDomainCombiner.debug.println("combinedDomains: ");
                for (int k = 0; k < optimize2.length; ++k) {
                    SubjectDomainCombiner.debug.println("newDomain " + k + ": " + printDomain(optimize2[k]));
                }
            }
        }
        if (optimize2 == null || optimize2.length == 0) {
            return null;
        }
        return optimize2;
    }
    
    private ProtectionDomain[] combineJavaxPolicy(final ProtectionDomain[] array, final ProtectionDomain[] array2) {
        if (!SubjectDomainCombiner.allowCaching) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                @Override
                public Void run() {
                    Policy.getPolicy().refresh();
                    return null;
                }
            });
        }
        final int n = (array == null) ? 0 : array.length;
        final int n2 = (array2 == null) ? 0 : array2.length;
        final ProtectionDomain[] array3 = new ProtectionDomain[n + n2];
        synchronized (this.cachedPDs) {
            if (!this.subject.isReadOnly() && !this.subject.getPrincipals().equals(this.principalSet)) {
                final Set<Principal> principals = this.subject.getPrincipals();
                synchronized (principals) {
                    this.principalSet = new HashSet<Principal>(principals);
                }
                this.principals = this.principalSet.toArray(new Principal[this.principalSet.size()]);
                this.cachedPDs.clear();
                if (SubjectDomainCombiner.debug != null) {
                    SubjectDomainCombiner.debug.println("Subject mutated - clearing cache");
                }
            }
            for (int i = 0; i < n; ++i) {
                final ProtectionDomain protectionDomain = array[i];
                ProtectionDomain protectionDomain2 = this.cachedPDs.getValue(protectionDomain);
                if (protectionDomain2 == null) {
                    if (SubjectDomainCombiner.pdAccess.getStaticPermissionsField(protectionDomain)) {
                        protectionDomain2 = new ProtectionDomain(protectionDomain.getCodeSource(), protectionDomain.getPermissions());
                    }
                    else {
                        final Permissions permissions = new Permissions();
                        final PermissionCollection permissions2 = protectionDomain.getPermissions();
                        if (permissions2 != null) {
                            synchronized (permissions2) {
                                final Enumeration<Permission> elements = permissions2.elements();
                                while (elements.hasMoreElements()) {
                                    permissions.add(elements.nextElement());
                                }
                            }
                        }
                        final CodeSource codeSource = protectionDomain.getCodeSource();
                        final PermissionCollection collection = AccessController.doPrivileged((PrivilegedAction<PermissionCollection>)new PrivilegedAction<PermissionCollection>() {
                            final /* synthetic */ Subject val$finalS = SubjectDomainCombiner.this.subject;
                            
                            @Override
                            public PermissionCollection run() {
                                return Policy.getPolicy().getPermissions(this.val$finalS, codeSource);
                            }
                        });
                        synchronized (collection) {
                            final Enumeration<Permission> elements2 = collection.elements();
                            while (elements2.hasMoreElements()) {
                                final Permission permission = elements2.nextElement();
                                if (!permissions.implies(permission)) {
                                    permissions.add(permission);
                                    if (SubjectDomainCombiner.debug == null) {
                                        continue;
                                    }
                                    SubjectDomainCombiner.debug.println("Adding perm " + permission + "\n");
                                }
                            }
                        }
                        protectionDomain2 = new ProtectionDomain(codeSource, permissions, protectionDomain.getClassLoader(), this.principals);
                    }
                    if (SubjectDomainCombiner.allowCaching) {
                        this.cachedPDs.putValue(protectionDomain, protectionDomain2);
                    }
                }
                array3[i] = protectionDomain2;
            }
        }
        if (SubjectDomainCombiner.debug != null) {
            SubjectDomainCombiner.debug.println("updated current: ");
            for (int j = 0; j < n; ++j) {
                SubjectDomainCombiner.debug.println("\tupdated[" + j + "] = " + array3[j]);
            }
        }
        if (n2 > 0) {
            System.arraycopy(array2, 0, array3, n, n2);
        }
        if (SubjectDomainCombiner.debug != null) {
            if (array3 == null || array3.length == 0) {
                SubjectDomainCombiner.debug.println("returning null");
            }
            else {
                SubjectDomainCombiner.debug.println("combinedDomains: ");
                for (int k = 0; k < array3.length; ++k) {
                    SubjectDomainCombiner.debug.println("newDomain " + k + ": " + array3[k].toString());
                }
            }
        }
        if (array3 == null || array3.length == 0) {
            return null;
        }
        return array3;
    }
    
    private static ProtectionDomain[] optimize(final ProtectionDomain[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        ProtectionDomain[] array2 = new ProtectionDomain[array.length];
        int n = 0;
        for (int i = 0; i < array.length; ++i) {
            final ProtectionDomain protectionDomain;
            if ((protectionDomain = array[i]) != null) {
                boolean b = false;
                for (int n2 = 0; n2 < n && !b; b = (array2[n2] == protectionDomain), ++n2) {}
                if (!b) {
                    array2[n++] = protectionDomain;
                }
            }
        }
        if (n > 0 && n < array.length) {
            final ProtectionDomain[] array3 = new ProtectionDomain[n];
            System.arraycopy(array2, 0, array3, 0, array3.length);
            array2 = array3;
        }
        return (ProtectionDomain[])((n == 0 || array2.length == 0) ? null : array2);
    }
    
    private static boolean cachePolicy() {
        final String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return Security.getProperty("cache.auth.policy");
            }
        });
        return s == null || Boolean.parseBoolean(s);
    }
    
    private static void printInputDomains(final ProtectionDomain[] array, final ProtectionDomain[] array2) {
        if (array == null || array.length == 0) {
            SubjectDomainCombiner.debug.println("currentDomains null or 0 length");
        }
        else {
            for (int n = 0; array != null && n < array.length; ++n) {
                if (array[n] == null) {
                    SubjectDomainCombiner.debug.println("currentDomain " + n + ": SystemDomain");
                }
                else {
                    SubjectDomainCombiner.debug.println("currentDomain " + n + ": " + printDomain(array[n]));
                }
            }
        }
        if (array2 == null || array2.length == 0) {
            SubjectDomainCombiner.debug.println("assignedDomains null or 0 length");
        }
        else {
            SubjectDomainCombiner.debug.println("assignedDomains = ");
            for (int n2 = 0; array2 != null && n2 < array2.length; ++n2) {
                if (array2[n2] == null) {
                    SubjectDomainCombiner.debug.println("assignedDomain " + n2 + ": SystemDomain");
                }
                else {
                    SubjectDomainCombiner.debug.println("assignedDomain " + n2 + ": " + printDomain(array2[n2]));
                }
            }
        }
    }
    
    private static String printDomain(final ProtectionDomain protectionDomain) {
        if (protectionDomain == null) {
            return "null";
        }
        return AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return protectionDomain.toString();
            }
        });
    }
    
    static {
        debug = Debug.getInstance("combiner", "\t[SubjectDomainCombiner]");
        useJavaxPolicy = Policy.isCustomPolicySet(SubjectDomainCombiner.debug);
        allowCaching = (SubjectDomainCombiner.useJavaxPolicy && cachePolicy());
        pdAccess = SharedSecrets.getJavaSecurityProtectionDomainAccess();
    }
    
    private static class WeakKeyValueMap<K, V> extends WeakHashMap<K, WeakReference<V>>
    {
        public V getValue(final K k) {
            final WeakReference<Object> weakReference = super.get(k);
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }
        
        public V putValue(final K k, final V v) {
            final WeakReference<V> weakReference = super.put(k, new WeakReference<V>(v));
            if (weakReference != null) {
                return (V)weakReference.get();
            }
            return null;
        }
    }
}
