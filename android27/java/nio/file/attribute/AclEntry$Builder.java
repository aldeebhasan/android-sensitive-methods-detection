package java.nio.file.attribute;

import java.util.*;

public static final class Builder
{
    private AclEntryType type;
    private UserPrincipal who;
    private Set<AclEntryPermission> perms;
    private Set<AclEntryFlag> flags;
    
    private Builder(final AclEntryType type, final UserPrincipal who, final Set<AclEntryPermission> perms, final Set<AclEntryFlag> flags) {
        assert perms != null && flags != null;
        this.type = type;
        this.who = who;
        this.perms = perms;
        this.flags = flags;
    }
    
    public AclEntry build() {
        if (this.type == null) {
            throw new IllegalStateException("Missing type component");
        }
        if (this.who == null) {
            throw new IllegalStateException("Missing who component");
        }
        return new AclEntry(this.type, this.who, this.perms, this.flags, null);
    }
    
    public Builder setType(final AclEntryType type) {
        if (type == null) {
            throw new NullPointerException();
        }
        this.type = type;
        return this;
    }
    
    public Builder setPrincipal(final UserPrincipal who) {
        if (who == null) {
            throw new NullPointerException();
        }
        this.who = who;
        return this;
    }
    
    private static void checkSet(final Set<?> set, final Class<?> clazz) {
        for (final Object next : set) {
            if (next == null) {
                throw new NullPointerException();
            }
            clazz.cast(next);
        }
    }
    
    public Builder setPermissions(final Set<AclEntryPermission> set) {
        Object perms;
        if (set.isEmpty()) {
            perms = Collections.emptySet();
        }
        else {
            perms = EnumSet.copyOf((Collection<Enum>)set);
            checkSet((Set<?>)perms, AclEntryPermission.class);
        }
        this.perms = (Set<AclEntryPermission>)perms;
        return this;
    }
    
    public Builder setPermissions(final AclEntryPermission... array) {
        final EnumSet<AclEntryPermission> none = EnumSet.noneOf(AclEntryPermission.class);
        for (final AclEntryPermission aclEntryPermission : array) {
            if (aclEntryPermission == null) {
                throw new NullPointerException();
            }
            none.add(aclEntryPermission);
        }
        this.perms = none;
        return this;
    }
    
    public Builder setFlags(final Set<AclEntryFlag> set) {
        Object flags;
        if (set.isEmpty()) {
            flags = Collections.emptySet();
        }
        else {
            flags = EnumSet.copyOf((Collection<Enum>)set);
            checkSet((Set<?>)flags, AclEntryFlag.class);
        }
        this.flags = (Set<AclEntryFlag>)flags;
        return this;
    }
    
    public Builder setFlags(final AclEntryFlag... array) {
        final EnumSet<AclEntryFlag> none = EnumSet.noneOf(AclEntryFlag.class);
        for (final AclEntryFlag aclEntryFlag : array) {
            if (aclEntryFlag == null) {
                throw new NullPointerException();
            }
            none.add(aclEntryFlag);
        }
        this.flags = none;
        return this;
    }
}
