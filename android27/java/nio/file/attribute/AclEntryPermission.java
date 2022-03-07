package java.nio.file.attribute;

public enum AclEntryPermission
{
    READ_DATA, 
    WRITE_DATA, 
    APPEND_DATA, 
    READ_NAMED_ATTRS, 
    WRITE_NAMED_ATTRS, 
    EXECUTE, 
    DELETE_CHILD, 
    READ_ATTRIBUTES, 
    WRITE_ATTRIBUTES, 
    DELETE, 
    READ_ACL, 
    WRITE_ACL, 
    WRITE_OWNER, 
    SYNCHRONIZE;
    
    public static final AclEntryPermission LIST_DIRECTORY;
    public static final AclEntryPermission ADD_FILE;
    public static final AclEntryPermission ADD_SUBDIRECTORY;
    
    static {
        LIST_DIRECTORY = AclEntryPermission.READ_DATA;
        ADD_FILE = AclEntryPermission.WRITE_DATA;
        ADD_SUBDIRECTORY = AclEntryPermission.APPEND_DATA;
    }
}
