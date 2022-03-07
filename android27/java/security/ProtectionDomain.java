package java.security;

import sun.security.util.*;
import sun.misc.*;
import java.util.*;

public class ProtectionDomain
{
    private CodeSource codesource;
    private ClassLoader classloader;
    private Principal[] principals;
    private PermissionCollection permissions;
    private boolean hasAllPerm;
    private boolean staticPermissions;
    final Key key;
    private static final Debug debug;
    
    public ProtectionDomain(final CodeSource codesource, final PermissionCollection permissions) {
        this.hasAllPerm = false;
        this.key = new Key();
        this.codesource = codesource;
        if (permissions != null) {
            (this.permissions = permissions).setReadOnly();
            if (permissions instanceof Permissions && ((Permissions)permissions).allPermission != null) {
                this.hasAllPerm = true;
            }
        }
        this.classloader = null;
        this.principals = new Principal[0];
        this.staticPermissions = true;
    }
    
    public ProtectionDomain(final CodeSource codesource, final PermissionCollection permissions, final ClassLoader classloader, final Principal[] array) {
        this.hasAllPerm = false;
        this.key = new Key();
        this.codesource = codesource;
        if (permissions != null) {
            (this.permissions = permissions).setReadOnly();
            if (permissions instanceof Permissions && ((Permissions)permissions).allPermission != null) {
                this.hasAllPerm = true;
            }
        }
        this.classloader = classloader;
        this.principals = ((array != null) ? array.clone() : new Principal[0]);
        this.staticPermissions = false;
    }
    
    public final CodeSource getCodeSource() {
        return this.codesource;
    }
    
    public final ClassLoader getClassLoader() {
        return this.classloader;
    }
    
    public final Principal[] getPrincipals() {
        return this.principals.clone();
    }
    
    public final PermissionCollection getPermissions() {
        return this.permissions;
    }
    
    public boolean implies(final Permission permission) {
        return this.hasAllPerm || (!this.staticPermissions && Policy.getPolicyNoCheck().implies(this, permission)) || (this.permissions != null && this.permissions.implies(permission));
    }
    
    boolean impliesCreateAccessControlContext() {
        return this.implies(SecurityConstants.CREATE_ACC_PERMISSION);
    }
    
    @Override
    public String toString() {
        String string = "<no principals>";
        if (this.principals != null && this.principals.length > 0) {
            final StringBuilder sb = new StringBuilder("(principals ");
            for (int i = 0; i < this.principals.length; ++i) {
                sb.append(this.principals[i].getClass().getName() + " \"" + this.principals[i].getName() + "\"");
                if (i < this.principals.length - 1) {
                    sb.append(",\n");
                }
                else {
                    sb.append(")\n");
                }
            }
            string = sb.toString();
        }
        return "ProtectionDomain  " + this.codesource + "\n " + this.classloader + "\n " + string + "\n " + ((Policy.isSet() && seeAllp()) ? this.mergePermissions() : this.getPermissions()) + "\n";
    }
    
    private static boolean seeAllp() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            return true;
        }
        if (ProtectionDomain.debug != null) {
            if (securityManager.getClass().getClassLoader() == null && Policy.getPolicyNoCheck().getClass().getClassLoader() == null) {
                return true;
            }
        }
        else {
            try {
                securityManager.checkPermission(SecurityConstants.GET_POLICY_PERMISSION);
                return true;
            }
            catch (SecurityException ex) {}
        }
        return false;
    }
    
    private PermissionCollection mergePermissions() {
        if (this.staticPermissions) {
            return this.permissions;
        }
        final PermissionCollection collection = AccessController.doPrivileged((PrivilegedAction<PermissionCollection>)new PrivilegedAction<PermissionCollection>() {
            @Override
            public PermissionCollection run() {
                return Policy.getPolicyNoCheck().getPermissions(ProtectionDomain.this);
            }
        });
        final Permissions permissions = new Permissions();
        final int n = 32;
        int n2 = 8;
        final ArrayList list = new ArrayList<Object>(n2);
        final ArrayList list2 = new ArrayList<Object>(n);
        if (this.permissions != null) {
            synchronized (this.permissions) {
                final Enumeration<Permission> elements = this.permissions.elements();
                while (elements.hasMoreElements()) {
                    list.add(elements.nextElement());
                }
            }
        }
        if (collection != null) {
            synchronized (collection) {
                final Enumeration<Permission> elements2 = collection.elements();
                while (elements2.hasMoreElements()) {
                    list2.add(elements2.nextElement());
                    ++n2;
                }
            }
        }
        if (collection != null && this.permissions != null) {
            synchronized (this.permissions) {
                final Enumeration<Permission> elements3 = this.permissions.elements();
                while (elements3.hasMoreElements()) {
                    final Permission permission = elements3.nextElement();
                    final Class<? extends Permission> class1 = permission.getClass();
                    final String actions = permission.getActions();
                    final String name = permission.getName();
                    for (int i = 0; i < list2.size(); ++i) {
                        final Permission permission2 = list2.get(i);
                        if (class1.isInstance(permission2) && name.equals(permission2.getName()) && actions.equals(permission2.getActions())) {
                            list2.remove(i);
                            break;
                        }
                    }
                }
            }
        }
        if (collection != null) {
            for (int j = list2.size() - 1; j >= 0; --j) {
                permissions.add((Permission)list2.get(j));
            }
        }
        if (this.permissions != null) {
            for (int k = list.size() - 1; k >= 0; --k) {
                permissions.add((Permission)list.get(k));
            }
        }
        return permissions;
    }
    
    static {
        SharedSecrets.setJavaSecurityAccess(new JavaSecurityAccessImpl());
        debug = Debug.getInstance("domain");
        SharedSecrets.setJavaSecurityProtectionDomainAccess(new JavaSecurityProtectionDomainAccess() {
            @Override
            public ProtectionDomainCache getProtectionDomainCache() {
                return new ProtectionDomainCache() {
                    private final Map<Key, PermissionCollection> map = Collections.synchronizedMap(new WeakHashMap<Key, PermissionCollection>());
                    
                    @Override
                    public void put(final ProtectionDomain protectionDomain, final PermissionCollection collection) {
                        this.map.put((protectionDomain == null) ? null : protectionDomain.key, collection);
                    }
                    
                    @Override
                    public PermissionCollection get(final ProtectionDomain protectionDomain) {
                        return (protectionDomain == null) ? this.map.get(null) : this.map.get(protectionDomain.key);
                    }
                };
            }
            
            @Override
            public boolean getStaticPermissionsField(final ProtectionDomain protectionDomain) {
                return protectionDomain.staticPermissions;
            }
        });
    }
    
    private static class JavaSecurityAccessImpl implements JavaSecurityAccess
    {
        @Override
        public <T> T doIntersectionPrivilege(final PrivilegedAction<T> privilegedAction, final AccessControlContext accessControlContext, final AccessControlContext accessControlContext2) {
            if (privilegedAction == null) {
                throw new NullPointerException();
            }
            return AccessController.doPrivileged(privilegedAction, getCombinedACC(accessControlContext2, accessControlContext));
        }
        
        @Override
        public <T> T doIntersectionPrivilege(final PrivilegedAction<T> privilegedAction, final AccessControlContext accessControlContext) {
            return this.doIntersectionPrivilege(privilegedAction, AccessController.getContext(), accessControlContext);
        }
        
        private static AccessControlContext getCombinedACC(final AccessControlContext accessControlContext, final AccessControlContext accessControlContext2) {
            return new AccessControlContext(accessControlContext2.getContext(), new AccessControlContext(accessControlContext, accessControlContext2.getCombiner(), true)).optimize();
        }
    }
    
    final class Key
    {
    }
}
