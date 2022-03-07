package java.util.jar;

import sun.misc.*;

public static class Name
{
    private String name;
    private int hashCode;
    public static final Name MANIFEST_VERSION;
    public static final Name SIGNATURE_VERSION;
    public static final Name CONTENT_TYPE;
    public static final Name CLASS_PATH;
    public static final Name MAIN_CLASS;
    public static final Name SEALED;
    public static final Name EXTENSION_LIST;
    public static final Name EXTENSION_NAME;
    @Deprecated
    public static final Name EXTENSION_INSTALLATION;
    public static final Name IMPLEMENTATION_TITLE;
    public static final Name IMPLEMENTATION_VERSION;
    public static final Name IMPLEMENTATION_VENDOR;
    @Deprecated
    public static final Name IMPLEMENTATION_VENDOR_ID;
    @Deprecated
    public static final Name IMPLEMENTATION_URL;
    public static final Name SPECIFICATION_TITLE;
    public static final Name SPECIFICATION_VERSION;
    public static final Name SPECIFICATION_VENDOR;
    
    public Name(final String s) {
        this.hashCode = -1;
        if (s == null) {
            throw new NullPointerException("name");
        }
        if (!isValid(s)) {
            throw new IllegalArgumentException(s);
        }
        this.name = s.intern();
    }
    
    private static boolean isValid(final String s) {
        final int length = s.length();
        if (length > 70 || length == 0) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (!isValid(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean isValid(final char c) {
        return isAlpha(c) || isDigit(c) || c == '_' || c == '-';
    }
    
    private static boolean isAlpha(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    
    private static boolean isDigit(final char c) {
        return c >= '0' && c <= '9';
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Name && ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER.compare(this.name, ((Name)o).name) == 0;
    }
    
    @Override
    public int hashCode() {
        if (this.hashCode == -1) {
            this.hashCode = ASCIICaseInsensitiveComparator.lowerCaseHashCode(this.name);
        }
        return this.hashCode;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    static {
        MANIFEST_VERSION = new Name("Manifest-Version");
        SIGNATURE_VERSION = new Name("Signature-Version");
        CONTENT_TYPE = new Name("Content-Type");
        CLASS_PATH = new Name("Class-Path");
        MAIN_CLASS = new Name("Main-Class");
        SEALED = new Name("Sealed");
        EXTENSION_LIST = new Name("Extension-List");
        EXTENSION_NAME = new Name("Extension-Name");
        EXTENSION_INSTALLATION = new Name("Extension-Installation");
        IMPLEMENTATION_TITLE = new Name("Implementation-Title");
        IMPLEMENTATION_VERSION = new Name("Implementation-Version");
        IMPLEMENTATION_VENDOR = new Name("Implementation-Vendor");
        IMPLEMENTATION_VENDOR_ID = new Name("Implementation-Vendor-Id");
        IMPLEMENTATION_URL = new Name("Implementation-URL");
        SPECIFICATION_TITLE = new Name("Specification-Title");
        SPECIFICATION_VERSION = new Name("Specification-Version");
        SPECIFICATION_VENDOR = new Name("Specification-Vendor");
    }
}
