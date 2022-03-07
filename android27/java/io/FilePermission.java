package java.io;

import java.nio.file.*;
import java.security.*;

public final class FilePermission extends Permission implements Serializable
{
    private static final int EXECUTE = 1;
    private static final int WRITE = 2;
    private static final int READ = 4;
    private static final int DELETE = 8;
    private static final int READLINK = 16;
    private static final int ALL = 31;
    private static final int NONE = 0;
    private transient int mask;
    private transient boolean directory;
    private transient boolean recursive;
    private String actions;
    private transient String cpath;
    private transient boolean invalid;
    private transient boolean allFiles;
    private static final char RECURSIVE_CHAR = '-';
    private static final char WILD_CHAR = '*';
    private static final long serialVersionUID = 7930732926638008763L;
    
    private void init(final int mask) {
        if ((mask & 0x1F) != mask) {
            throw new IllegalArgumentException("invalid actions mask");
        }
        if (mask == 0) {
            throw new IllegalArgumentException("invalid actions mask");
        }
        if ((this.cpath = this.getName()) == null) {
            throw new NullPointerException("name can't be null");
        }
        this.mask = mask;
        if (this.cpath.equals("<<ALL FILES>>")) {
            this.allFiles = true;
            this.directory = true;
            this.recursive = true;
            this.cpath = "";
            return;
        }
        if (this.isPathInvalid()) {
            this.invalid = true;
            return;
        }
        this.cpath = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                try {
                    final String access$000 = FilePermission.this.cpath;
                    if (FilePermission.this.cpath.endsWith("*")) {
                        final String canonicalPath = new File(access$000.substring(0, access$000.length() - 1) + "-").getCanonicalPath();
                        return canonicalPath.substring(0, canonicalPath.length() - 1) + "*";
                    }
                    return new File(access$000).getCanonicalPath();
                }
                catch (IOException ex) {
                    return FilePermission.this.cpath;
                }
            }
        });
        int length = this.cpath.length();
        final char c = (length > 0) ? this.cpath.charAt(length - 1) : '\0';
        if (c == '-' && this.cpath.charAt(length - 2) == File.separatorChar) {
            this.directory = true;
            this.recursive = true;
            this.cpath = this.cpath.substring(0, --length);
        }
        else if (c == '*' && this.cpath.charAt(length - 2) == File.separatorChar) {
            this.directory = true;
            this.cpath = this.cpath.substring(0, --length);
        }
    }
    
    private boolean isPathInvalid() {
        if (this.cpath.indexOf(". ") != -1) {
            try {
                new File(this.cpath.endsWith("*") ? (this.cpath.substring(0, this.cpath.length() - 1) + "-") : this.cpath).toPath();
            }
            catch (InvalidPathException ex) {
                return true;
            }
        }
        return false;
    }
    
    public FilePermission(final String s, final String s2) {
        super(s);
        this.init(getMask(s2));
    }
    
    FilePermission(final String s, final int n) {
        super(s);
        this.init(n);
    }
    
    @Override
    public boolean implies(final Permission permission) {
        if (!(permission instanceof FilePermission)) {
            return false;
        }
        final FilePermission filePermission = (FilePermission)permission;
        return (this.mask & filePermission.mask) == filePermission.mask && this.impliesIgnoreMask(filePermission);
    }
    
    boolean impliesIgnoreMask(final FilePermission filePermission) {
        if (this == filePermission) {
            return true;
        }
        if (this.allFiles) {
            return true;
        }
        if (this.invalid || filePermission.invalid) {
            return false;
        }
        if (filePermission.allFiles) {
            return false;
        }
        if (!this.directory) {
            return !filePermission.directory && this.cpath.equals(filePermission.cpath);
        }
        if (this.recursive) {
            if (filePermission.directory) {
                return filePermission.cpath.length() >= this.cpath.length() && filePermission.cpath.startsWith(this.cpath);
            }
            return filePermission.cpath.length() > this.cpath.length() && filePermission.cpath.startsWith(this.cpath);
        }
        else {
            if (filePermission.directory) {
                return !filePermission.recursive && this.cpath.equals(filePermission.cpath);
            }
            final int lastIndex = filePermission.cpath.lastIndexOf(File.separatorChar);
            return lastIndex != -1 && this.cpath.length() == lastIndex + 1 && this.cpath.regionMatches(0, filePermission.cpath, 0, lastIndex + 1);
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FilePermission)) {
            return false;
        }
        final FilePermission filePermission = (FilePermission)o;
        return !this.invalid && !filePermission.invalid && this.mask == filePermission.mask && this.allFiles == filePermission.allFiles && this.cpath.equals(filePermission.cpath) && this.directory == filePermission.directory && this.recursive == filePermission.recursive;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    private static int getMask(final String s) {
        int n = 0;
        if (s == null) {
            return n;
        }
        if (s == "read") {
            return 4;
        }
        if (s == "write") {
            return 2;
        }
        if (s == "execute") {
            return 1;
        }
        if (s == "delete") {
            return 8;
        }
        if (s == "readlink") {
            return 16;
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
                n |= 0x4;
            }
            else if (i >= 4 && (charArray[i - 4] == 'w' || charArray[i - 4] == 'W') && (charArray[i - 3] == 'r' || charArray[i - 3] == 'R') && (charArray[i - 2] == 'i' || charArray[i - 2] == 'I') && (charArray[i - 1] == 't' || charArray[i - 1] == 'T') && (charArray[i] == 'e' || charArray[i] == 'E')) {
                n2 = 5;
                n |= 0x2;
            }
            else if (i >= 6 && (charArray[i - 6] == 'e' || charArray[i - 6] == 'E') && (charArray[i - 5] == 'x' || charArray[i - 5] == 'X') && (charArray[i - 4] == 'e' || charArray[i - 4] == 'E') && (charArray[i - 3] == 'c' || charArray[i - 3] == 'C') && (charArray[i - 2] == 'u' || charArray[i - 2] == 'U') && (charArray[i - 1] == 't' || charArray[i - 1] == 'T') && (charArray[i] == 'e' || charArray[i] == 'E')) {
                n2 = 7;
                n |= 0x1;
            }
            else if (i >= 5 && (charArray[i - 5] == 'd' || charArray[i - 5] == 'D') && (charArray[i - 4] == 'e' || charArray[i - 4] == 'E') && (charArray[i - 3] == 'l' || charArray[i - 3] == 'L') && (charArray[i - 2] == 'e' || charArray[i - 2] == 'E') && (charArray[i - 1] == 't' || charArray[i - 1] == 'T') && (charArray[i] == 'e' || charArray[i] == 'E')) {
                n2 = 6;
                n |= 0x8;
            }
            else {
                if (i < 7 || (charArray[i - 7] != 'r' && charArray[i - 7] != 'R') || (charArray[i - 6] != 'e' && charArray[i - 6] != 'E') || (charArray[i - 5] != 'a' && charArray[i - 5] != 'A') || (charArray[i - 4] != 'd' && charArray[i - 4] != 'D') || (charArray[i - 3] != 'l' && charArray[i - 3] != 'L') || (charArray[i - 2] != 'i' && charArray[i - 2] != 'I') || (charArray[i - 1] != 'n' && charArray[i - 1] != 'N') || (charArray[i] != 'k' && charArray[i] != 'K')) {
                    throw new IllegalArgumentException("invalid permission: " + s);
                }
                n2 = 8;
                n |= 0x10;
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
    
    int getMask() {
        return this.mask;
    }
    
    private static String getActions(final int n) {
        final StringBuilder sb = new StringBuilder();
        int n2 = 0;
        if ((n & 0x4) == 0x4) {
            n2 = 1;
            sb.append("read");
        }
        if ((n & 0x2) == 0x2) {
            if (n2 != 0) {
                sb.append(',');
            }
            else {
                n2 = 1;
            }
            sb.append("write");
        }
        if ((n & 0x1) == 0x1) {
            if (n2 != 0) {
                sb.append(',');
            }
            else {
                n2 = 1;
            }
            sb.append("execute");
        }
        if ((n & 0x8) == 0x8) {
            if (n2 != 0) {
                sb.append(',');
            }
            else {
                n2 = 1;
            }
            sb.append("delete");
        }
        if ((n & 0x10) == 0x10) {
            if (n2 != 0) {
                sb.append(',');
            }
            sb.append("readlink");
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
    
    @Override
    public PermissionCollection newPermissionCollection() {
        return new FilePermissionCollection();
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.actions == null) {
            this.getActions();
        }
        objectOutputStream.defaultWriteObject();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init(getMask(this.actions));
    }
}
