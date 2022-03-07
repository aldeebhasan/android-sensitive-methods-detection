package java.nio.file.attribute;

import java.util.*;
import java.io.*;

public final class AclEntry
{
    private final AclEntryType type;
    private final UserPrincipal who;
    private final Set<AclEntryPermission> perms;
    private final Set<AclEntryFlag> flags;
    private volatile int hash;
    
    private AclEntry(final AclEntryType type, final UserPrincipal who, final Set<AclEntryPermission> perms, final Set<AclEntryFlag> flags) {
        this.type = type;
        this.who = who;
        this.perms = perms;
        this.flags = flags;
    }
    
    public static Builder newBuilder() {
        return new Builder((AclEntryType)null, (UserPrincipal)null, (Set)Collections.emptySet(), (Set)Collections.emptySet());
    }
    
    public static Builder newBuilder(final AclEntry aclEntry) {
        return new Builder(aclEntry.type, aclEntry.who, (Set)aclEntry.perms, (Set)aclEntry.flags);
    }
    
    public AclEntryType type() {
        return this.type;
    }
    
    public UserPrincipal principal() {
        return this.who;
    }
    
    public Set<AclEntryPermission> permissions() {
        return new HashSet<AclEntryPermission>(this.perms);
    }
    
    public Set<AclEntryFlag> flags() {
        return new HashSet<AclEntryFlag>(this.flags);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof AclEntry)) {
            return false;
        }
        final AclEntry aclEntry = (AclEntry)o;
        return this.type == aclEntry.type && this.who.equals(aclEntry.who) && this.perms.equals(aclEntry.perms) && this.flags.equals(aclEntry.flags);
    }
    
    private static int hash(final int n, final Object o) {
        return n * 127 + o.hashCode();
    }
    
    @Override
    public int hashCode() {
        if (this.hash != 0) {
            return this.hash;
        }
        return this.hash = hash(hash(hash(this.type.hashCode(), this.who), this.perms), this.flags);
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.who.getName());
        sb.append(':');
        final Iterator<AclEntryPermission> iterator = this.perms.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next().name());
            sb.append('/');
        }
        sb.setLength(sb.length() - 1);
        sb.append(':');
        if (!this.flags.isEmpty()) {
            final Iterator<AclEntryFlag> iterator2 = this.flags.iterator();
            while (iterator2.hasNext()) {
                sb.append(iterator2.next().name());
                sb.append('/');
            }
            sb.setLength(sb.length() - 1);
            sb.append(':');
        }
        sb.append(this.type.name());
        return sb.toString();
    }
    
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
}
