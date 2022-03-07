package java.util;

import java.security.*;
import java.io.*;

public final class PropertyPermission extends BasicPermission
{
    private static final int READ = 1;
    private static final int WRITE = 2;
    private static final int ALL = 3;
    private static final int NONE = 0;
    private transient int mask;
    private String actions;
    private static final long serialVersionUID = 885438825399942851L;
    
    private void init(final int mask) {
        if ((mask & 0x3) != mask) {
            throw new IllegalArgumentException("invalid actions mask");
        }
        if (mask == 0) {
            throw new IllegalArgumentException("invalid actions mask");
        }
        if (this.getName() == null) {
            throw new NullPointerException("name can't be null");
        }
        this.mask = mask;
    }
    
    public PropertyPermission(final String s, final String s2) {
        super(s, s2);
        this.init(getMask(s2));
    }
    
    @Override
    public boolean implies(final Permission permission) {
        if (!(permission instanceof PropertyPermission)) {
            return false;
        }
        final PropertyPermission propertyPermission = (PropertyPermission)permission;
        return (this.mask & propertyPermission.mask) == propertyPermission.mask && super.implies(propertyPermission);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PropertyPermission)) {
            return false;
        }
        final PropertyPermission propertyPermission = (PropertyPermission)o;
        return this.mask == propertyPermission.mask && this.getName().equals(propertyPermission.getName());
    }
    
    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
    
    private static int getMask(final String s) {
        int n = 0;
        if (s == null) {
            return n;
        }
        if (s == "read") {
            return 1;
        }
        if (s == "write") {
            return 2;
        }
        if (s == "read,write") {
            return 3;
        }
        final char[] charArray = s.toCharArray();
        int i = charArray.length - 1;
        if (i < 0) {
            return n;
        }
        while (i != -1) {
            char c;
            while (i != -1 && ((c = charArray[i]) == ' ' || c == '\r' || c == '\n' || c == '\f' || c == '\t')) {
                --i;
            }
            int n2;
            if (i >= 3 && (charArray[i - 3] == 'r' || charArray[i - 3] == 'R') && (charArray[i - 2] == 'e' || charArray[i - 2] == 'E') && (charArray[i - 1] == 'a' || charArray[i - 1] == 'A') && (charArray[i] == 'd' || charArray[i] == 'D')) {
                n2 = 4;
                n |= 0x1;
            }
            else {
                if (i < 4 || (charArray[i - 4] != 'w' && charArray[i - 4] != 'W') || (charArray[i - 3] != 'r' && charArray[i - 3] != 'R') || (charArray[i - 2] != 'i' && charArray[i - 2] != 'I') || (charArray[i - 1] != 't' && charArray[i - 1] != 'T') || (charArray[i] != 'e' && charArray[i] != 'E')) {
                    throw new IllegalArgumentException("invalid permission: " + s);
                }
                n2 = 5;
                n |= 0x2;
            }
            for (int n3 = 0; i >= n2 && n3 == 0; --i) {
                switch (charArray[i - n2]) {
                    case ',': {
                        n3 = 1;
                        break;
                    }
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ': {
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("invalid permission: " + s);
                    }
                }
            }
            i -= n2;
        }
        return n;
    }
    
    static String getActions(final int n) {
        final StringBuilder sb = new StringBuilder();
        boolean b = false;
        if ((n & 0x1) == 0x1) {
            b = true;
            sb.append("read");
        }
        if ((n & 0x2) == 0x2) {
            if (b) {
                sb.append(',');
            }
            sb.append("write");
        }
        return sb.toString();
    }
    
    @Override
    public String getActions() {
        if (this.actions == null) {
            this.actions = getActions(this.mask);
        }
        return this.actions;
    }
    
    int getMask() {
        return this.mask;
    }
    
    @Override
    public PermissionCollection newPermissionCollection() {
        return new PropertyPermissionCollection();
    }
    
    private synchronized void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.actions == null) {
            this.getActions();
        }
        objectOutputStream.defaultWriteObject();
    }
    
    private synchronized void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init(getMask(this.actions));
    }
}
