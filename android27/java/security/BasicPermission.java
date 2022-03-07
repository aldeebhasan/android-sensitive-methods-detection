package java.security;

import java.io.*;

public abstract class BasicPermission extends Permission implements Serializable
{
    private static final long serialVersionUID = 6279438298436773498L;
    private transient boolean wildcard;
    private transient String path;
    private transient boolean exitVM;
    
    private void init(final String path) {
        if (path == null) {
            throw new NullPointerException("name can't be null");
        }
        final int length = path.length();
        if (length == 0) {
            throw new IllegalArgumentException("name can't be empty");
        }
        if (path.charAt(length - 1) == '*' && (length == 1 || path.charAt(length - 2) == '.')) {
            this.wildcard = true;
            if (length == 1) {
                this.path = "";
            }
            else {
                this.path = path.substring(0, length - 1);
            }
        }
        else if (path.equals("exitVM")) {
            this.wildcard = true;
            this.path = "exitVM.";
            this.exitVM = true;
        }
        else {
            this.path = path;
        }
    }
    
    public BasicPermission(final String s) {
        super(s);
        this.init(s);
    }
    
    public BasicPermission(final String s, final String s2) {
        super(s);
        this.init(s);
    }
    
    @Override
    public boolean implies(final Permission permission) {
        if (permission == null || permission.getClass() != this.getClass()) {
            return false;
        }
        final BasicPermission basicPermission = (BasicPermission)permission;
        if (!this.wildcard) {
            return !basicPermission.wildcard && this.path.equals(basicPermission.path);
        }
        if (basicPermission.wildcard) {
            return basicPermission.path.startsWith(this.path);
        }
        return basicPermission.path.length() > this.path.length() && basicPermission.path.startsWith(this.path);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || (o != null && o.getClass() == this.getClass() && this.getName().equals(((BasicPermission)o).getName()));
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
    
    @Override
    public String getActions() {
        return "";
    }
    
    @Override
    public PermissionCollection newPermissionCollection() {
        return new BasicPermissionCollection(this.getClass());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init(this.getName());
    }
    
    final String getCanonicalName() {
        return this.exitVM ? "exitVM.*" : this.getName();
    }
}
