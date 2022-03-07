package java.security;

import java.util.*;
import sun.security.util.*;

public final class AccessControlContext
{
    private ProtectionDomain[] context;
    private boolean isPrivileged;
    private boolean isAuthorized;
    private AccessControlContext privilegedContext;
    private DomainCombiner combiner;
    private Permission[] permissions;
    private AccessControlContext parent;
    private boolean isWrapped;
    private boolean isLimited;
    private ProtectionDomain[] limitedContext;
    private static boolean debugInit;
    private static Debug debug;
    
    static Debug getDebug() {
        if (AccessControlContext.debugInit) {
            return AccessControlContext.debug;
        }
        if (Policy.isSet()) {
            AccessControlContext.debug = Debug.getInstance("access");
            AccessControlContext.debugInit = true;
        }
        return AccessControlContext.debug;
    }
    
    public AccessControlContext(final ProtectionDomain[] array) {
        this.isAuthorized = false;
        this.combiner = null;
        if (array.length == 0) {
            this.context = null;
        }
        else if (array.length == 1) {
            if (array[0] != null) {
                this.context = array.clone();
            }
            else {
                this.context = null;
            }
        }
        else {
            final ArrayList<ProtectionDomain> list = new ArrayList<ProtectionDomain>(array.length);
            for (int i = 0; i < array.length; ++i) {
                if (array[i] != null && !list.contains(array[i])) {
                    list.add(array[i]);
                }
            }
            if (!list.isEmpty()) {
                this.context = new ProtectionDomain[list.size()];
                this.context = list.toArray(this.context);
            }
        }
    }
    
    public AccessControlContext(final AccessControlContext accessControlContext, final DomainCombiner domainCombiner) {
        this(accessControlContext, domainCombiner, false);
    }
    
    AccessControlContext(final AccessControlContext accessControlContext, final DomainCombiner combiner, final boolean b) {
        this.isAuthorized = false;
        this.combiner = null;
        if (!b) {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(SecurityConstants.CREATE_ACC_PERMISSION);
                this.isAuthorized = true;
            }
        }
        else {
            this.isAuthorized = true;
        }
        this.context = accessControlContext.context;
        this.combiner = combiner;
    }
    
    AccessControlContext(final ProtectionDomain protectionDomain, final DomainCombiner combiner, AccessControlContext parent, final AccessControlContext privilegedContext, final Permission[] array) {
        this.isAuthorized = false;
        this.combiner = null;
        ProtectionDomain[] array2 = null;
        if (protectionDomain != null) {
            array2 = new ProtectionDomain[] { protectionDomain };
        }
        if (privilegedContext != null) {
            if (combiner != null) {
                this.context = combiner.combine(array2, privilegedContext.context);
            }
            else {
                this.context = combine(array2, privilegedContext.context);
            }
        }
        else if (combiner != null) {
            this.context = combiner.combine(array2, null);
        }
        else {
            this.context = combine(array2, null);
        }
        this.combiner = combiner;
        Permission[] permissions = null;
        if (array != null) {
            permissions = new Permission[array.length];
            for (int i = 0; i < array.length; ++i) {
                if (array[i] == null) {
                    throw new NullPointerException("permission can't be null");
                }
                if (array[i].getClass() == AllPermission.class) {
                    parent = null;
                }
                permissions[i] = array[i];
            }
        }
        if (parent != null) {
            this.limitedContext = combine(parent.context, parent.limitedContext);
            this.isLimited = true;
            this.isWrapped = true;
            this.permissions = permissions;
            this.parent = parent;
            this.privilegedContext = privilegedContext;
        }
        this.isAuthorized = true;
    }
    
    AccessControlContext(final ProtectionDomain[] context, final boolean isPrivileged) {
        this.isAuthorized = false;
        this.combiner = null;
        this.context = context;
        this.isPrivileged = isPrivileged;
        this.isAuthorized = true;
    }
    
    AccessControlContext(final ProtectionDomain[] context, final AccessControlContext privilegedContext) {
        this.isAuthorized = false;
        this.combiner = null;
        this.context = context;
        this.privilegedContext = privilegedContext;
        this.isPrivileged = true;
    }
    
    ProtectionDomain[] getContext() {
        return this.context;
    }
    
    boolean isPrivileged() {
        return this.isPrivileged;
    }
    
    DomainCombiner getAssignedCombiner() {
        AccessControlContext accessControlContext;
        if (this.isPrivileged) {
            accessControlContext = this.privilegedContext;
        }
        else {
            accessControlContext = AccessController.getInheritedAccessControlContext();
        }
        if (accessControlContext != null) {
            return accessControlContext.combiner;
        }
        return null;
    }
    
    public DomainCombiner getDomainCombiner() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(SecurityConstants.GET_COMBINER_PERMISSION);
        }
        return this.getCombiner();
    }
    
    DomainCombiner getCombiner() {
        return this.combiner;
    }
    
    boolean isAuthorized() {
        return this.isAuthorized;
    }
    
    public void checkPermission(final Permission permission) throws AccessControlException {
        boolean b = false;
        if (permission == null) {
            throw new NullPointerException("permission can't be null");
        }
        if (getDebug() != null) {
            boolean b2 = !Debug.isOn("codebase=");
            if (!b2) {
                for (int n = 0; this.context != null && n < this.context.length; ++n) {
                    if (this.context[n].getCodeSource() != null && this.context[n].getCodeSource().getLocation() != null && Debug.isOn("codebase=" + this.context[n].getCodeSource().getLocation().toString())) {
                        b2 = true;
                        break;
                    }
                }
            }
            b = (b2 & (!Debug.isOn("permission=") || Debug.isOn("permission=" + permission.getClass().getCanonicalName())));
            if (b && Debug.isOn("stack")) {
                Thread.dumpStack();
            }
            if (b && Debug.isOn("domain")) {
                if (this.context == null) {
                    AccessControlContext.debug.println("domain (context is null)");
                }
                else {
                    for (int i = 0; i < this.context.length; ++i) {
                        AccessControlContext.debug.println("domain " + i + " " + this.context[i]);
                    }
                }
            }
        }
        if (this.context == null) {
            this.checkPermission2(permission);
            return;
        }
        for (int j = 0; j < this.context.length; ++j) {
            if (this.context[j] != null && !this.context[j].implies(permission)) {
                if (b) {
                    AccessControlContext.debug.println("access denied " + permission);
                }
                if (Debug.isOn("failure") && AccessControlContext.debug != null) {
                    if (!b) {
                        AccessControlContext.debug.println("access denied " + permission);
                    }
                    Thread.dumpStack();
                    AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                        final /* synthetic */ Debug val$db = AccessControlContext.debug;
                        final /* synthetic */ ProtectionDomain val$pd = AccessControlContext.this.context[j];
                        
                        @Override
                        public Void run() {
                            this.val$db.println("domain that failed " + this.val$pd);
                            return null;
                        }
                    });
                }
                throw new AccessControlException("access denied " + permission, permission);
            }
        }
        if (b) {
            AccessControlContext.debug.println("access allowed " + permission);
        }
        this.checkPermission2(permission);
    }
    
    private void checkPermission2(final Permission permission) {
        if (!this.isLimited) {
            return;
        }
        if (this.privilegedContext != null) {
            this.privilegedContext.checkPermission2(permission);
        }
        if (this.isWrapped) {
            return;
        }
        if (this.permissions != null) {
            final Class<? extends Permission> class1 = permission.getClass();
            for (int i = 0; i < this.permissions.length; ++i) {
                final Permission permission2 = this.permissions[i];
                if (permission2.getClass().equals(class1) && permission2.implies(permission)) {
                    return;
                }
            }
        }
        if (this.parent != null) {
            if (this.permissions == null) {
                this.parent.checkPermission2(permission);
            }
            else {
                this.parent.checkPermission(permission);
            }
        }
    }
    
    AccessControlContext optimize() {
        DomainCombiner combiner = null;
        AccessControlContext parent = null;
        Permission[] permissions = null;
        AccessControlContext accessControlContext;
        if (this.isPrivileged) {
            accessControlContext = this.privilegedContext;
            if (accessControlContext != null && accessControlContext.isWrapped) {
                permissions = accessControlContext.permissions;
                parent = accessControlContext.parent;
            }
        }
        else {
            accessControlContext = AccessController.getInheritedAccessControlContext();
            if (accessControlContext != null && accessControlContext.isLimited) {
                parent = accessControlContext;
            }
        }
        final boolean b = this.context == null;
        final boolean b2 = accessControlContext == null || accessControlContext.context == null;
        final ProtectionDomain[] array = (ProtectionDomain[])(b2 ? null : accessControlContext.context);
        final boolean b3 = (accessControlContext == null || !accessControlContext.isWrapped) && parent == null;
        ProtectionDomain[] context;
        if (accessControlContext != null && accessControlContext.combiner != null) {
            if (getDebug() != null) {
                AccessControlContext.debug.println("AccessControlContext invoking the Combiner");
            }
            combiner = accessControlContext.combiner;
            context = combiner.combine(this.context, array);
        }
        else {
            if (b) {
                if (b2) {
                    this.calculateFields(accessControlContext, parent, permissions);
                    return this;
                }
                if (b3) {
                    return accessControlContext;
                }
            }
            else if (array != null && b3 && this.context.length == 1 && this.context[0] == array[0]) {
                return accessControlContext;
            }
            context = combine(this.context, array);
            if (b3 && !b2 && context == array) {
                return accessControlContext;
            }
            if (b2 && context == this.context) {
                this.calculateFields(accessControlContext, parent, permissions);
                return this;
            }
        }
        this.context = context;
        this.combiner = combiner;
        this.isPrivileged = false;
        this.calculateFields(accessControlContext, parent, permissions);
        return this;
    }
    
    private static ProtectionDomain[] combine(final ProtectionDomain[] array, final ProtectionDomain[] array2) {
        final boolean b = array == null;
        final boolean b2 = array2 == null;
        final int n = b ? 0 : array.length;
        if (b2 && n <= 2) {
            return array;
        }
        int n2 = b2 ? 0 : array2.length;
        ProtectionDomain[] array3 = new ProtectionDomain[n + n2];
        if (!b2) {
            System.arraycopy(array2, 0, array3, 0, n2);
        }
        for (final ProtectionDomain protectionDomain : array) {
            Label_0140: {
                if (protectionDomain != null) {
                    for (int j = 0; j < n2; ++j) {
                        if (protectionDomain == array3[j]) {
                            break Label_0140;
                        }
                    }
                    array3[n2++] = protectionDomain;
                }
            }
        }
        if (n2 != array3.length) {
            if (!b2 && n2 == array2.length) {
                return array2;
            }
            if (b2 && n2 == n) {
                return array;
            }
            final ProtectionDomain[] array4 = new ProtectionDomain[n2];
            System.arraycopy(array3, 0, array4, 0, n2);
            array3 = array4;
        }
        return array3;
    }
    
    private void calculateFields(final AccessControlContext accessControlContext, final AccessControlContext parent, final Permission[] permissions) {
        final ProtectionDomain[] combine = combine((ProtectionDomain[])((parent != null) ? parent.limitedContext : null), (ProtectionDomain[])((accessControlContext != null) ? accessControlContext.limitedContext : null));
        if (combine != null && (this.context == null || !containsAllPDs(combine, this.context))) {
            this.limitedContext = combine;
            this.permissions = permissions;
            this.parent = parent;
            this.isLimited = true;
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AccessControlContext)) {
            return false;
        }
        final AccessControlContext accessControlContext = (AccessControlContext)o;
        return this.equalContext(accessControlContext) && this.equalLimitedContext(accessControlContext);
    }
    
    private boolean equalContext(final AccessControlContext accessControlContext) {
        return this.equalPDs(this.context, accessControlContext.context) && (this.combiner != null || accessControlContext.combiner == null) && (this.combiner == null || this.combiner.equals(accessControlContext.combiner));
    }
    
    private boolean equalPDs(final ProtectionDomain[] array, final ProtectionDomain[] array2) {
        if (array == null) {
            return array2 == null;
        }
        return array2 != null && containsAllPDs(array, array2) && containsAllPDs(array2, array);
    }
    
    private boolean equalLimitedContext(final AccessControlContext accessControlContext) {
        if (accessControlContext == null) {
            return false;
        }
        if (!this.isLimited && !accessControlContext.isLimited) {
            return true;
        }
        if (!this.isLimited || !accessControlContext.isLimited) {
            return false;
        }
        if ((this.isWrapped && !accessControlContext.isWrapped) || (!this.isWrapped && accessControlContext.isWrapped)) {
            return false;
        }
        if (this.permissions == null && accessControlContext.permissions != null) {
            return false;
        }
        if (this.permissions != null && accessControlContext.permissions == null) {
            return false;
        }
        if (!this.containsAllLimits(accessControlContext) || !accessControlContext.containsAllLimits(this)) {
            return false;
        }
        final AccessControlContext nextPC = getNextPC(this);
        final AccessControlContext nextPC2 = getNextPC(accessControlContext);
        return (nextPC != null || nextPC2 == null || !nextPC2.isLimited) && (nextPC == null || nextPC.equalLimitedContext(nextPC2)) && (this.parent != null || accessControlContext.parent == null) && (this.parent == null || this.parent.equals(accessControlContext.parent));
    }
    
    private static AccessControlContext getNextPC(AccessControlContext privilegedContext) {
        while (privilegedContext != null && privilegedContext.privilegedContext != null) {
            privilegedContext = privilegedContext.privilegedContext;
            if (!privilegedContext.isWrapped) {
                return privilegedContext;
            }
        }
        return null;
    }
    
    private static boolean containsAllPDs(final ProtectionDomain[] array, final ProtectionDomain[] array2) {
        boolean b = false;
        for (int i = 0; i < array.length; ++i) {
            b = false;
            final ProtectionDomain protectionDomain;
            if ((protectionDomain = array[i]) == null) {
                for (int n = 0; n < array2.length && !b; b = (array2[n] == null), ++n) {}
            }
            else {
                final Class<? extends ProtectionDomain> class1 = protectionDomain.getClass();
                ProtectionDomain protectionDomain2;
                for (int n2 = 0; n2 < array2.length && !b; b = (protectionDomain2 != null && class1 == protectionDomain2.getClass() && protectionDomain.equals(protectionDomain2)), ++n2) {
                    protectionDomain2 = array2[n2];
                }
            }
            if (!b) {
                return false;
            }
        }
        return b;
    }
    
    private boolean containsAllLimits(final AccessControlContext accessControlContext) {
        boolean b = false;
        if (this.permissions == null && accessControlContext.permissions == null) {
            return true;
        }
        for (int i = 0; i < this.permissions.length; ++i) {
            final Permission permission = this.permissions[i];
            final Class<? extends Permission> class1 = permission.getClass();
            b = false;
            Permission permission2;
            for (int n = 0; n < accessControlContext.permissions.length && !b; b = (class1.equals(permission2.getClass()) && permission.equals(permission2)), ++n) {
                permission2 = accessControlContext.permissions[n];
            }
            if (!b) {
                return false;
            }
        }
        return b;
    }
    
    @Override
    public int hashCode() {
        int n = 0;
        if (this.context == null) {
            return n;
        }
        for (int i = 0; i < this.context.length; ++i) {
            if (this.context[i] != null) {
                n ^= this.context[i].hashCode();
            }
        }
        return n;
    }
    
    static {
        AccessControlContext.debugInit = false;
        AccessControlContext.debug = null;
    }
}
