package javax.security.auth;

import sun.security.util.*;
import java.security.*;
import java.text.*;
import java.io.*;
import java.util.*;

public final class Subject implements Serializable
{
    private static final long serialVersionUID = -8308522755600156056L;
    Set<Principal> principals;
    transient Set<Object> pubCredentials;
    transient Set<Object> privCredentials;
    private volatile boolean readOnly;
    private static final int PRINCIPAL_SET = 1;
    private static final int PUB_CREDENTIAL_SET = 2;
    private static final int PRIV_CREDENTIAL_SET = 3;
    private static final ProtectionDomain[] NULL_PD_ARRAY;
    
    public Subject() {
        this.readOnly = false;
        this.principals = Collections.synchronizedSet(new SecureSet<Principal>(this, 1));
        this.pubCredentials = Collections.synchronizedSet(new SecureSet<Object>(this, 2));
        this.privCredentials = Collections.synchronizedSet(new SecureSet<Object>(this, 3));
    }
    
    public Subject(final boolean readOnly, final Set<? extends Principal> set, final Set<?> set2, final Set<?> set3) {
        this.readOnly = false;
        if (set == null || set2 == null || set3 == null) {
            throw new NullPointerException(ResourcesMgr.getString("invalid.null.input.s."));
        }
        this.principals = Collections.synchronizedSet(new SecureSet<Principal>(this, 1, set));
        this.pubCredentials = Collections.synchronizedSet(new SecureSet<Object>(this, 2, set2));
        this.privCredentials = Collections.synchronizedSet(new SecureSet<Object>(this, 3, set3));
        this.readOnly = readOnly;
    }
    
    public void setReadOnly() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AuthPermissionHolder.SET_READ_ONLY_PERMISSION);
        }
        this.readOnly = true;
    }
    
    public boolean isReadOnly() {
        return this.readOnly;
    }
    
    public static Subject getSubject(final AccessControlContext accessControlContext) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AuthPermissionHolder.GET_SUBJECT_PERMISSION);
        }
        if (accessControlContext == null) {
            throw new NullPointerException(ResourcesMgr.getString("invalid.null.AccessControlContext.provided"));
        }
        return AccessController.doPrivileged((PrivilegedAction<Subject>)new PrivilegedAction<Subject>() {
            @Override
            public Subject run() {
                final DomainCombiner domainCombiner = accessControlContext.getDomainCombiner();
                if (!(domainCombiner instanceof SubjectDomainCombiner)) {
                    return null;
                }
                return ((SubjectDomainCombiner)domainCombiner).getSubject();
            }
        });
    }
    
    public static <T> T doAs(final Subject subject, final PrivilegedAction<T> privilegedAction) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AuthPermissionHolder.DO_AS_PERMISSION);
        }
        if (privilegedAction == null) {
            throw new NullPointerException(ResourcesMgr.getString("invalid.null.action.provided"));
        }
        return AccessController.doPrivileged(privilegedAction, createContext(subject, AccessController.getContext()));
    }
    
    public static <T> T doAs(final Subject subject, final PrivilegedExceptionAction<T> privilegedExceptionAction) throws PrivilegedActionException {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AuthPermissionHolder.DO_AS_PERMISSION);
        }
        if (privilegedExceptionAction == null) {
            throw new NullPointerException(ResourcesMgr.getString("invalid.null.action.provided"));
        }
        return AccessController.doPrivileged(privilegedExceptionAction, createContext(subject, AccessController.getContext()));
    }
    
    public static <T> T doAsPrivileged(final Subject subject, final PrivilegedAction<T> privilegedAction, final AccessControlContext accessControlContext) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AuthPermissionHolder.DO_AS_PRIVILEGED_PERMISSION);
        }
        if (privilegedAction == null) {
            throw new NullPointerException(ResourcesMgr.getString("invalid.null.action.provided"));
        }
        return AccessController.doPrivileged(privilegedAction, createContext(subject, (accessControlContext == null) ? new AccessControlContext(Subject.NULL_PD_ARRAY) : accessControlContext));
    }
    
    public static <T> T doAsPrivileged(final Subject subject, final PrivilegedExceptionAction<T> privilegedExceptionAction, final AccessControlContext accessControlContext) throws PrivilegedActionException {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(AuthPermissionHolder.DO_AS_PRIVILEGED_PERMISSION);
        }
        if (privilegedExceptionAction == null) {
            throw new NullPointerException(ResourcesMgr.getString("invalid.null.action.provided"));
        }
        return AccessController.doPrivileged(privilegedExceptionAction, createContext(subject, (accessControlContext == null) ? new AccessControlContext(Subject.NULL_PD_ARRAY) : accessControlContext));
    }
    
    private static AccessControlContext createContext(final Subject subject, final AccessControlContext accessControlContext) {
        return AccessController.doPrivileged((PrivilegedAction<AccessControlContext>)new PrivilegedAction<AccessControlContext>() {
            @Override
            public AccessControlContext run() {
                if (subject == null) {
                    return new AccessControlContext(accessControlContext, null);
                }
                return new AccessControlContext(accessControlContext, new SubjectDomainCombiner(subject));
            }
        });
    }
    
    public Set<Principal> getPrincipals() {
        return this.principals;
    }
    
    public <T extends Principal> Set<T> getPrincipals(final Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException(ResourcesMgr.getString("invalid.null.Class.provided"));
        }
        return new ClassSet<T>(1, clazz);
    }
    
    public Set<Object> getPublicCredentials() {
        return this.pubCredentials;
    }
    
    public Set<Object> getPrivateCredentials() {
        return this.privCredentials;
    }
    
    public <T> Set<T> getPublicCredentials(final Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException(ResourcesMgr.getString("invalid.null.Class.provided"));
        }
        return new ClassSet<T>(2, clazz);
    }
    
    public <T> Set<T> getPrivateCredentials(final Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException(ResourcesMgr.getString("invalid.null.Class.provided"));
        }
        return new ClassSet<T>(3, clazz);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subject)) {
            return false;
        }
        final Subject subject = (Subject)o;
        final HashSet set;
        synchronized (subject.principals) {
            set = new HashSet(subject.principals);
        }
        if (!this.principals.equals(set)) {
            return false;
        }
        final HashSet set2;
        synchronized (subject.pubCredentials) {
            set2 = new HashSet(subject.pubCredentials);
        }
        if (!this.pubCredentials.equals(set2)) {
            return false;
        }
        final HashSet set3;
        synchronized (subject.privCredentials) {
            set3 = new HashSet(subject.privCredentials);
        }
        return this.privCredentials.equals(set3);
    }
    
    @Override
    public String toString() {
        return this.toString(true);
    }
    
    String toString(final boolean b) {
        final String string = ResourcesMgr.getString("Subject.");
        String s = "";
        synchronized (this.principals) {
            final Iterator<Principal> iterator = this.principals.iterator();
            while (iterator.hasNext()) {
                s = s + ResourcesMgr.getString(".Principal.") + iterator.next().toString() + ResourcesMgr.getString("NEWLINE");
            }
        }
        synchronized (this.pubCredentials) {
            final Iterator<Object> iterator2 = this.pubCredentials.iterator();
            while (iterator2.hasNext()) {
                s = s + ResourcesMgr.getString(".Public.Credential.") + iterator2.next().toString() + ResourcesMgr.getString("NEWLINE");
            }
        }
        if (b) {
            synchronized (this.privCredentials) {
                final Iterator<Object> iterator3 = this.privCredentials.iterator();
                while (iterator3.hasNext()) {
                    try {
                        s = s + ResourcesMgr.getString(".Private.Credential.") + iterator3.next().toString() + ResourcesMgr.getString("NEWLINE");
                        continue;
                    }
                    catch (SecurityException ex) {
                        s += ResourcesMgr.getString(".Private.Credential.inaccessible.");
                    }
                    break;
                }
            }
        }
        return string + s;
    }
    
    @Override
    public int hashCode() {
        int n = 0;
        synchronized (this.principals) {
            final Iterator<Principal> iterator = this.principals.iterator();
            while (iterator.hasNext()) {
                n ^= iterator.next().hashCode();
            }
        }
        synchronized (this.pubCredentials) {
            final Iterator<Object> iterator2 = this.pubCredentials.iterator();
            while (iterator2.hasNext()) {
                n ^= this.getCredHashCode(iterator2.next());
            }
        }
        return n;
    }
    
    private int getCredHashCode(final Object o) {
        try {
            return o.hashCode();
        }
        catch (IllegalStateException ex) {
            return o.getClass().toString().hashCode();
        }
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        synchronized (this.principals) {
            objectOutputStream.defaultWriteObject();
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        this.readOnly = fields.get("readOnly", false);
        final Set set = (Set)fields.get("principals", null);
        if (set == null) {
            throw new NullPointerException(ResourcesMgr.getString("invalid.null.input.s."));
        }
        try {
            this.principals = Collections.synchronizedSet(new SecureSet<Principal>(this, 1, set));
        }
        catch (NullPointerException ex) {
            this.principals = Collections.synchronizedSet(new SecureSet<Principal>(this, 1));
        }
        this.pubCredentials = Collections.synchronizedSet(new SecureSet<Object>(this, 2));
        this.privCredentials = Collections.synchronizedSet(new SecureSet<Object>(this, 3));
    }
    
    static {
        NULL_PD_ARRAY = new ProtectionDomain[0];
    }
    
    static class AuthPermissionHolder
    {
        static final AuthPermission DO_AS_PERMISSION;
        static final AuthPermission DO_AS_PRIVILEGED_PERMISSION;
        static final AuthPermission SET_READ_ONLY_PERMISSION;
        static final AuthPermission GET_SUBJECT_PERMISSION;
        static final AuthPermission MODIFY_PRINCIPALS_PERMISSION;
        static final AuthPermission MODIFY_PUBLIC_CREDENTIALS_PERMISSION;
        static final AuthPermission MODIFY_PRIVATE_CREDENTIALS_PERMISSION;
        
        static {
            DO_AS_PERMISSION = new AuthPermission("doAs");
            DO_AS_PRIVILEGED_PERMISSION = new AuthPermission("doAsPrivileged");
            SET_READ_ONLY_PERMISSION = new AuthPermission("setReadOnly");
            GET_SUBJECT_PERMISSION = new AuthPermission("getSubject");
            MODIFY_PRINCIPALS_PERMISSION = new AuthPermission("modifyPrincipals");
            MODIFY_PUBLIC_CREDENTIALS_PERMISSION = new AuthPermission("modifyPublicCredentials");
            MODIFY_PRIVATE_CREDENTIALS_PERMISSION = new AuthPermission("modifyPrivateCredentials");
        }
    }
    
    private class ClassSet<T> extends AbstractSet<T>
    {
        private int which;
        private Class<T> c;
        private Set<T> set;
        
        ClassSet(final int which, final Class<T> c) {
            this.which = which;
            this.c = c;
            this.set = new HashSet<T>();
            switch (which) {
                case 1: {
                    synchronized (Subject.this.principals) {
                        this.populateSet();
                    }
                    break;
                }
                case 2: {
                    synchronized (Subject.this.pubCredentials) {
                        this.populateSet();
                    }
                    break;
                }
                default: {
                    synchronized (Subject.this.privCredentials) {
                        this.populateSet();
                    }
                    break;
                }
            }
        }
        
        private void populateSet() {
            Object o = null;
            switch (this.which) {
                case 1: {
                    o = Subject.this.principals.iterator();
                    break;
                }
                case 2: {
                    o = Subject.this.pubCredentials.iterator();
                    break;
                }
                default: {
                    o = Subject.this.privCredentials.iterator();
                    break;
                }
            }
            while (((Iterator)o).hasNext()) {
                T t;
                if (this.which == 3) {
                    t = AccessController.doPrivileged((PrivilegedAction<T>)new PrivilegedAction<Object>() {
                        @Override
                        public Object run() {
                            return ((Iterator<Object>)o).next();
                        }
                    });
                }
                else {
                    t = ((Iterator<T>)o).next();
                }
                if (this.c.isAssignableFrom(t.getClass())) {
                    if (this.which != 3) {
                        this.set.add(t);
                    }
                    else {
                        final SecurityManager securityManager = System.getSecurityManager();
                        if (securityManager != null) {
                            securityManager.checkPermission(new PrivateCredentialPermission(t.getClass().getName(), Subject.this.getPrincipals()));
                        }
                        this.set.add(t);
                    }
                }
            }
        }
        
        @Override
        public int size() {
            return this.set.size();
        }
        
        @Override
        public Iterator<T> iterator() {
            return this.set.iterator();
        }
        
        @Override
        public boolean add(final T t) {
            if (!t.getClass().isAssignableFrom(this.c)) {
                throw new SecurityException(new MessageFormat(ResourcesMgr.getString("attempting.to.add.an.object.which.is.not.an.instance.of.class")).format(new Object[] { this.c.toString() }));
            }
            return this.set.add(t);
        }
    }
    
    private static class SecureSet<E> extends AbstractSet<E> implements Serializable
    {
        private static final long serialVersionUID = 7911754171111800359L;
        private static final ObjectStreamField[] serialPersistentFields;
        Subject subject;
        LinkedList<E> elements;
        private int which;
        
        SecureSet(final Subject subject, final int which) {
            this.subject = subject;
            this.which = which;
            this.elements = new LinkedList<E>();
        }
        
        SecureSet(final Subject subject, final int which, final Set<? extends E> set) {
            this.subject = subject;
            this.which = which;
            this.elements = new LinkedList<E>(set);
        }
        
        @Override
        public int size() {
            return this.elements.size();
        }
        
        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>() {
                ListIterator<E> i = this.val$list.listIterator(0);
                final /* synthetic */ LinkedList val$list = SecureSet.this.elements;
                
                @Override
                public boolean hasNext() {
                    return this.i.hasNext();
                }
                
                @Override
                public E next() {
                    if (SecureSet.this.which != 3) {
                        return this.i.next();
                    }
                    final SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        try {
                            securityManager.checkPermission(new PrivateCredentialPermission(this.val$list.get(this.i.nextIndex()).getClass().getName(), SecureSet.this.subject.getPrincipals()));
                        }
                        catch (SecurityException ex) {
                            this.i.next();
                            throw ex;
                        }
                    }
                    return this.i.next();
                }
                
                @Override
                public void remove() {
                    if (SecureSet.this.subject.isReadOnly()) {
                        throw new IllegalStateException(ResourcesMgr.getString("Subject.is.read.only"));
                    }
                    final SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        switch (SecureSet.this.which) {
                            case 1: {
                                securityManager.checkPermission(AuthPermissionHolder.MODIFY_PRINCIPALS_PERMISSION);
                                break;
                            }
                            case 2: {
                                securityManager.checkPermission(AuthPermissionHolder.MODIFY_PUBLIC_CREDENTIALS_PERMISSION);
                                break;
                            }
                            default: {
                                securityManager.checkPermission(AuthPermissionHolder.MODIFY_PRIVATE_CREDENTIALS_PERMISSION);
                                break;
                            }
                        }
                    }
                    this.i.remove();
                }
            };
        }
        
        @Override
        public boolean add(final E e) {
            if (this.subject.isReadOnly()) {
                throw new IllegalStateException(ResourcesMgr.getString("Subject.is.read.only"));
            }
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                switch (this.which) {
                    case 1: {
                        securityManager.checkPermission(AuthPermissionHolder.MODIFY_PRINCIPALS_PERMISSION);
                        break;
                    }
                    case 2: {
                        securityManager.checkPermission(AuthPermissionHolder.MODIFY_PUBLIC_CREDENTIALS_PERMISSION);
                        break;
                    }
                    default: {
                        securityManager.checkPermission(AuthPermissionHolder.MODIFY_PRIVATE_CREDENTIALS_PERMISSION);
                        break;
                    }
                }
            }
            switch (this.which) {
                case 1: {
                    if (!(e instanceof Principal)) {
                        throw new SecurityException(ResourcesMgr.getString("attempting.to.add.an.object.which.is.not.an.instance.of.java.security.Principal.to.a.Subject.s.Principal.Set"));
                    }
                    break;
                }
            }
            return !this.elements.contains(e) && this.elements.add(e);
        }
        
        @Override
        public boolean remove(final Object o) {
            final Iterator<E> iterator = this.iterator();
            while (iterator.hasNext()) {
                Object o2;
                if (this.which != 3) {
                    o2 = iterator.next();
                }
                else {
                    o2 = AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<E>() {
                        @Override
                        public E run() {
                            return iterator.next();
                        }
                    });
                }
                if (o2 == null) {
                    if (o == null) {
                        iterator.remove();
                        return true;
                    }
                    continue;
                }
                else {
                    if (o2.equals(o)) {
                        iterator.remove();
                        return true;
                    }
                    continue;
                }
            }
            return false;
        }
        
        @Override
        public boolean contains(final Object o) {
            final Iterator<E> iterator = this.iterator();
            while (iterator.hasNext()) {
                Object o2;
                if (this.which != 3) {
                    o2 = iterator.next();
                }
                else {
                    final SecurityManager securityManager = System.getSecurityManager();
                    if (securityManager != null) {
                        securityManager.checkPermission(new PrivateCredentialPermission(o.getClass().getName(), this.subject.getPrincipals()));
                    }
                    o2 = AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<E>() {
                        @Override
                        public E run() {
                            return iterator.next();
                        }
                    });
                }
                if (o2 == null) {
                    if (o == null) {
                        return true;
                    }
                    continue;
                }
                else {
                    if (o2.equals(o)) {
                        return true;
                    }
                    continue;
                }
            }
            return false;
        }
        
        @Override
        public boolean removeAll(final Collection<?> collection) {
            Objects.requireNonNull(collection);
            boolean b = false;
            final Iterator<E> iterator = this.iterator();
            while (iterator.hasNext()) {
                Object o;
                if (this.which != 3) {
                    o = iterator.next();
                }
                else {
                    o = AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<E>() {
                        @Override
                        public E run() {
                            return iterator.next();
                        }
                    });
                }
                for (final Object next : collection) {
                    if (o == null) {
                        if (next == null) {
                            iterator.remove();
                            b = true;
                            break;
                        }
                        continue;
                    }
                    else {
                        if (o.equals(next)) {
                            iterator.remove();
                            b = true;
                            break;
                        }
                        continue;
                    }
                }
            }
            return b;
        }
        
        @Override
        public boolean retainAll(final Collection<?> collection) {
            Objects.requireNonNull(collection);
            boolean b = false;
            final Iterator<E> iterator = this.iterator();
            while (iterator.hasNext()) {
                boolean b2 = false;
                Object o;
                if (this.which != 3) {
                    o = iterator.next();
                }
                else {
                    o = AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<E>() {
                        @Override
                        public E run() {
                            return iterator.next();
                        }
                    });
                }
                for (final Object next : collection) {
                    if (o == null) {
                        if (next == null) {
                            b2 = true;
                            break;
                        }
                        continue;
                    }
                    else {
                        if (o.equals(next)) {
                            b2 = true;
                            break;
                        }
                        continue;
                    }
                }
                if (!b2) {
                    iterator.remove();
                    b = true;
                }
            }
            return b;
        }
        
        @Override
        public void clear() {
            final Iterator<E> iterator = this.iterator();
            while (iterator.hasNext()) {
                if (this.which != 3) {
                    iterator.next();
                }
                else {
                    AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<E>() {
                        @Override
                        public E run() {
                            return iterator.next();
                        }
                    });
                }
                iterator.remove();
            }
        }
        
        private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
            if (this.which == 3) {
                final Iterator<E> iterator = this.iterator();
                while (iterator.hasNext()) {
                    iterator.next();
                }
            }
            final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
            putFields.put("this$0", this.subject);
            putFields.put("elements", this.elements);
            putFields.put("which", this.which);
            objectOutputStream.writeFields();
        }
        
        private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            final ObjectInputStream.GetField fields = objectInputStream.readFields();
            this.subject = (Subject)fields.get("this$0", null);
            this.which = fields.get("which", 0);
            final LinkedList elements = (LinkedList)fields.get("elements", null);
            if (elements.getClass() != LinkedList.class) {
                this.elements = new LinkedList<E>(elements);
            }
            else {
                this.elements = (LinkedList<E>)elements;
            }
        }
        
        static {
            serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("this$0", Subject.class), new ObjectStreamField("elements", LinkedList.class), new ObjectStreamField("which", Integer.TYPE) };
        }
    }
}
