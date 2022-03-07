package java.security;

import java.security.cert.*;
import java.util.*;
import java.io.*;

public final class Permissions extends PermissionCollection implements Serializable
{
    private transient Map<Class<?>, PermissionCollection> permsMap;
    private transient boolean hasUnresolved;
    PermissionCollection allPermission;
    private static final long serialVersionUID = 4858622370623524688L;
    private static final ObjectStreamField[] serialPersistentFields;
    
    public Permissions() {
        this.hasUnresolved = false;
        this.permsMap = new HashMap<Class<?>, PermissionCollection>(11);
        this.allPermission = null;
    }
    
    @Override
    public void add(final Permission permission) {
        if (this.isReadOnly()) {
            throw new SecurityException("attempt to add a Permission to a readonly Permissions object");
        }
        final PermissionCollection permissionCollection;
        synchronized (this) {
            permissionCollection = this.getPermissionCollection(permission, true);
            permissionCollection.add(permission);
        }
        if (permission instanceof AllPermission) {
            this.allPermission = permissionCollection;
        }
        if (permission instanceof UnresolvedPermission) {
            this.hasUnresolved = true;
        }
    }
    
    @Override
    public boolean implies(final Permission permission) {
        if (this.allPermission != null) {
            return true;
        }
        synchronized (this) {
            final PermissionCollection permissionCollection = this.getPermissionCollection(permission, false);
            return permissionCollection != null && permissionCollection.implies(permission);
        }
    }
    
    @Override
    public Enumeration<Permission> elements() {
        synchronized (this) {
            return new PermissionsEnumerator(this.permsMap.values().iterator());
        }
    }
    
    private PermissionCollection getPermissionCollection(final Permission permission, final boolean b) {
        final Class<? extends Permission> class1 = permission.getClass();
        PermissionCollection permissionCollection = this.permsMap.get(class1);
        if (!this.hasUnresolved && !b) {
            return permissionCollection;
        }
        if (permissionCollection == null) {
            permissionCollection = (this.hasUnresolved ? this.getUnresolvedPermissions(permission) : null);
            if (permissionCollection == null && b) {
                permissionCollection = permission.newPermissionCollection();
                if (permissionCollection == null) {
                    permissionCollection = new PermissionsHash();
                }
            }
            if (permissionCollection != null) {
                this.permsMap.put(class1, permissionCollection);
            }
        }
        return permissionCollection;
    }
    
    private PermissionCollection getUnresolvedPermissions(final Permission permission) {
        final UnresolvedPermissionCollection collection = this.permsMap.get(UnresolvedPermission.class);
        if (collection == null) {
            return null;
        }
        final List<UnresolvedPermission> unresolvedPermissions = collection.getUnresolvedPermissions(permission);
        if (unresolvedPermissions == null) {
            return null;
        }
        Certificate[] array = null;
        final Object[] signers = permission.getClass().getSigners();
        int n = 0;
        if (signers != null) {
            for (int i = 0; i < signers.length; ++i) {
                if (signers[i] instanceof Certificate) {
                    ++n;
                }
            }
            array = new Certificate[n];
            int n2 = 0;
            for (int j = 0; j < signers.length; ++j) {
                if (signers[j] instanceof Certificate) {
                    array[n2++] = (Certificate)signers[j];
                }
            }
        }
        PermissionCollection permissionCollection = null;
        synchronized (unresolvedPermissions) {
            for (int size = unresolvedPermissions.size(), k = 0; k < size; ++k) {
                final Permission resolve = unresolvedPermissions.get(k).resolve(permission, array);
                if (resolve != null) {
                    if (permissionCollection == null) {
                        permissionCollection = permission.newPermissionCollection();
                        if (permissionCollection == null) {
                            permissionCollection = new PermissionsHash();
                        }
                    }
                    permissionCollection.add(resolve);
                }
            }
        }
        return permissionCollection;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final Hashtable hashtable = new Hashtable(this.permsMap.size() * 2);
        synchronized (this) {
            hashtable.putAll(this.permsMap);
        }
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("allPermission", this.allPermission);
        putFields.put("perms", hashtable);
        objectOutputStream.writeFields();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        this.allPermission = (PermissionCollection)fields.get("allPermission", null);
        final Hashtable hashtable = (Hashtable)fields.get("perms", null);
        (this.permsMap = new HashMap<Class<?>, PermissionCollection>(hashtable.size() * 2)).putAll(hashtable);
        final UnresolvedPermissionCollection collection = this.permsMap.get(UnresolvedPermission.class);
        this.hasUnresolved = (collection != null && collection.elements().hasMoreElements());
    }
    
    static {
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("perms", Hashtable.class), new ObjectStreamField("allPermission", PermissionCollection.class) };
    }
}
