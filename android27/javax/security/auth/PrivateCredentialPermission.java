package javax.security.auth;

import sun.security.util.*;
import java.security.*;
import java.util.*;
import java.text.*;
import java.io.*;

public final class PrivateCredentialPermission extends Permission
{
    private static final long serialVersionUID = 5284372143517237068L;
    private static final CredOwner[] EMPTY_PRINCIPALS;
    private String credentialClass;
    private Set<Principal> principals;
    private transient CredOwner[] credOwners;
    private boolean testing;
    
    PrivateCredentialPermission(final String credentialClass, final Set<Principal> set) {
        super(credentialClass);
        this.testing = false;
        this.credentialClass = credentialClass;
        synchronized (set) {
            if (set.size() == 0) {
                this.credOwners = PrivateCredentialPermission.EMPTY_PRINCIPALS;
            }
            else {
                this.credOwners = new CredOwner[set.size()];
                int n = 0;
                for (final Principal principal : set) {
                    this.credOwners[n++] = new CredOwner(principal.getClass().getName(), principal.getName());
                }
            }
        }
    }
    
    public PrivateCredentialPermission(final String s, final String s2) {
        super(s);
        this.testing = false;
        if (!"read".equalsIgnoreCase(s2)) {
            throw new IllegalArgumentException(ResourcesMgr.getString("actions.can.only.be.read."));
        }
        this.init(s);
    }
    
    public String getCredentialClass() {
        return this.credentialClass;
    }
    
    public String[][] getPrincipals() {
        if (this.credOwners == null || this.credOwners.length == 0) {
            return new String[0][0];
        }
        final String[][] array = new String[this.credOwners.length][2];
        for (int i = 0; i < this.credOwners.length; ++i) {
            array[i][0] = this.credOwners[i].principalClass;
            array[i][1] = this.credOwners[i].principalName;
        }
        return array;
    }
    
    @Override
    public boolean implies(final Permission permission) {
        if (permission == null || !(permission instanceof PrivateCredentialPermission)) {
            return false;
        }
        final PrivateCredentialPermission privateCredentialPermission = (PrivateCredentialPermission)permission;
        return this.impliesCredentialClass(this.credentialClass, privateCredentialPermission.credentialClass) && this.impliesPrincipalSet(this.credOwners, privateCredentialPermission.credOwners);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PrivateCredentialPermission)) {
            return false;
        }
        final PrivateCredentialPermission privateCredentialPermission = (PrivateCredentialPermission)o;
        return this.implies(privateCredentialPermission) && privateCredentialPermission.implies(this);
    }
    
    @Override
    public int hashCode() {
        return this.credentialClass.hashCode();
    }
    
    @Override
    public String getActions() {
        return "read";
    }
    
    @Override
    public PermissionCollection newPermissionCollection() {
        return null;
    }
    
    private void init(final String s) {
        if (s == null || s.trim().length() == 0) {
            throw new IllegalArgumentException("invalid empty name");
        }
        final ArrayList<CredOwner> list = new ArrayList<CredOwner>();
        final StringTokenizer stringTokenizer = new StringTokenizer(s, " ", true);
        if (this.testing) {
            System.out.println("whole name = " + s);
        }
        this.credentialClass = stringTokenizer.nextToken();
        if (this.testing) {
            System.out.println("Credential Class = " + this.credentialClass);
        }
        if (!stringTokenizer.hasMoreTokens()) {
            throw new IllegalArgumentException(new MessageFormat(ResourcesMgr.getString("permission.name.name.syntax.invalid.")).format(new Object[] { s }) + ResourcesMgr.getString("Credential.Class.not.followed.by.a.Principal.Class.and.Name"));
        }
        while (stringTokenizer.hasMoreTokens()) {
            stringTokenizer.nextToken();
            final String nextToken = stringTokenizer.nextToken();
            if (this.testing) {
                System.out.println("    Principal Class = " + nextToken);
            }
            if (!stringTokenizer.hasMoreTokens()) {
                throw new IllegalArgumentException(new MessageFormat(ResourcesMgr.getString("permission.name.name.syntax.invalid.")).format(new Object[] { s }) + ResourcesMgr.getString("Principal.Class.not.followed.by.a.Principal.Name"));
            }
            stringTokenizer.nextToken();
            String s2 = stringTokenizer.nextToken();
            if (!s2.startsWith("\"")) {
                throw new IllegalArgumentException(new MessageFormat(ResourcesMgr.getString("permission.name.name.syntax.invalid.")).format(new Object[] { s }) + ResourcesMgr.getString("Principal.Name.must.be.surrounded.by.quotes"));
            }
            if (!s2.endsWith("\"")) {
                while (stringTokenizer.hasMoreTokens()) {
                    s2 += stringTokenizer.nextToken();
                    if (s2.endsWith("\"")) {
                        break;
                    }
                }
                if (!s2.endsWith("\"")) {
                    throw new IllegalArgumentException(new MessageFormat(ResourcesMgr.getString("permission.name.name.syntax.invalid.")).format(new Object[] { s }) + ResourcesMgr.getString("Principal.Name.missing.end.quote"));
                }
            }
            if (this.testing) {
                System.out.println("\tprincipalName = '" + s2 + "'");
            }
            final String substring = s2.substring(1, s2.length() - 1);
            if (nextToken.equals("*") && !substring.equals("*")) {
                throw new IllegalArgumentException(ResourcesMgr.getString("PrivateCredentialPermission.Principal.Class.can.not.be.a.wildcard.value.if.Principal.Name.is.not.a.wildcard.value"));
            }
            if (this.testing) {
                System.out.println("\tprincipalName = '" + substring + "'");
            }
            list.add(new CredOwner(nextToken, substring));
        }
        list.toArray(this.credOwners = new CredOwner[list.size()]);
    }
    
    private boolean impliesCredentialClass(final String s, final String s2) {
        if (s == null || s2 == null) {
            return false;
        }
        if (this.testing) {
            System.out.println("credential class comparison: " + s + "/" + s2);
        }
        return s.equals("*") || s.equals(s2);
    }
    
    private boolean impliesPrincipalSet(final CredOwner[] array, final CredOwner[] array2) {
        if (array == null || array2 == null) {
            return false;
        }
        if (array2.length == 0) {
            return true;
        }
        if (array.length == 0) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            boolean b = false;
            for (int j = 0; j < array2.length; ++j) {
                if (array[i].implies(array2[j])) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                return false;
            }
        }
        return true;
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.getName().indexOf(" ") == -1 && this.getName().indexOf("\"") == -1) {
            this.credentialClass = this.getName();
            this.credOwners = PrivateCredentialPermission.EMPTY_PRINCIPALS;
        }
        else {
            this.init(this.getName());
        }
    }
    
    static {
        EMPTY_PRINCIPALS = new CredOwner[0];
    }
    
    static class CredOwner implements Serializable
    {
        private static final long serialVersionUID = -5607449830436408266L;
        String principalClass;
        String principalName;
        
        CredOwner(final String principalClass, final String principalName) {
            this.principalClass = principalClass;
            this.principalName = principalName;
        }
        
        public boolean implies(final Object o) {
            if (o == null || !(o instanceof CredOwner)) {
                return false;
            }
            final CredOwner credOwner = (CredOwner)o;
            return (this.principalClass.equals("*") || this.principalClass.equals(credOwner.principalClass)) && (this.principalName.equals("*") || this.principalName.equals(credOwner.principalName));
        }
        
        @Override
        public String toString() {
            return new MessageFormat(ResourcesMgr.getString("CredOwner.Principal.Class.class.Principal.Name.name")).format(new Object[] { this.principalClass, this.principalName });
        }
    }
}
