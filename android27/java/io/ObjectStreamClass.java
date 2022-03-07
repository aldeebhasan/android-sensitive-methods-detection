package java.io;

import sun.reflect.misc.*;
import sun.reflect.*;
import java.lang.ref.*;
import java.util.*;
import java.security.*;
import java.util.concurrent.*;
import sun.misc.*;
import java.lang.reflect.*;

public class ObjectStreamClass implements Serializable
{
    public static final ObjectStreamField[] NO_FIELDS;
    private static final long serialVersionUID = -6120832682080437368L;
    private static final ObjectStreamField[] serialPersistentFields;
    private static boolean disableSerialConstructorChecks;
    private static final ReflectionFactory reflFactory;
    private Class<?> cl;
    private String name;
    private volatile Long suid;
    private boolean isProxy;
    private boolean isEnum;
    private boolean serializable;
    private boolean externalizable;
    private boolean hasWriteObjectData;
    private boolean hasBlockExternalData;
    private ClassNotFoundException resolveEx;
    private ExceptionInfo deserializeEx;
    private ExceptionInfo serializeEx;
    private ExceptionInfo defaultSerializeEx;
    private ObjectStreamField[] fields;
    private int primDataSize;
    private int numObjFields;
    private FieldReflector fieldRefl;
    private volatile ClassDataSlot[] dataLayout;
    private Constructor<?> cons;
    private ProtectionDomain[] domains;
    private Method writeObjectMethod;
    private Method readObjectMethod;
    private Method readObjectNoDataMethod;
    private Method writeReplaceMethod;
    private Method readResolveMethod;
    private ObjectStreamClass localDesc;
    private ObjectStreamClass superDesc;
    private boolean initialized;
    
    private static native void initNative();
    
    public static ObjectStreamClass lookup(final Class<?> clazz) {
        return lookup(clazz, false);
    }
    
    public static ObjectStreamClass lookupAny(final Class<?> clazz) {
        return lookup(clazz, true);
    }
    
    public String getName() {
        return this.name;
    }
    
    public long getSerialVersionUID() {
        if (this.suid == null) {
            this.suid = AccessController.doPrivileged((PrivilegedAction<Long>)new PrivilegedAction<Long>() {
                @Override
                public Long run() {
                    return computeDefaultSUID(ObjectStreamClass.this.cl);
                }
            });
        }
        return this.suid;
    }
    
    @CallerSensitive
    public Class<?> forClass() {
        if (this.cl == null) {
            return null;
        }
        this.requireInitialized();
        if (System.getSecurityManager() != null && ReflectUtil.needsPackageAccessCheck(Reflection.getCallerClass().getClassLoader(), this.cl.getClassLoader())) {
            ReflectUtil.checkPackageAccess(this.cl);
        }
        return this.cl;
    }
    
    public ObjectStreamField[] getFields() {
        return this.getFields(true);
    }
    
    public ObjectStreamField getField(final String s) {
        return this.getField(s, null);
    }
    
    @Override
    public String toString() {
        return this.name + ": static final long serialVersionUID = " + this.getSerialVersionUID() + "L;";
    }
    
    static ObjectStreamClass lookup(final Class<?> clazz, final boolean b) {
        if (!b && !Serializable.class.isAssignableFrom(clazz)) {
            return null;
        }
        processQueue(Caches.localDescsQueue, Caches.localDescs);
        final WeakClassKey weakClassKey = new WeakClassKey(clazz, Caches.localDescsQueue);
        Reference<Object> reference = Caches.localDescs.get(weakClassKey);
        Object o = null;
        if (reference != null) {
            o = reference.get();
        }
        EntryFuture entryFuture = null;
        if (o == null) {
            final EntryFuture entryFuture2 = new EntryFuture();
            final SoftReference softReference = new SoftReference<Object>(entryFuture2);
            do {
                if (reference != null) {
                    Caches.localDescs.remove(weakClassKey, reference);
                }
                reference = Caches.localDescs.putIfAbsent(weakClassKey, softReference);
                if (reference != null) {
                    o = reference.get();
                }
            } while (reference != null && o == null);
            if (o == null) {
                entryFuture = entryFuture2;
            }
        }
        if (o instanceof ObjectStreamClass) {
            return (ObjectStreamClass)o;
        }
        if (o instanceof EntryFuture) {
            entryFuture = (EntryFuture)o;
            if (entryFuture.getOwner() == Thread.currentThread()) {
                o = null;
            }
            else {
                o = entryFuture.get();
            }
        }
        if (o == null) {
            try {
                o = new ObjectStreamClass(clazz);
            }
            catch (Throwable t) {
                o = t;
            }
            if (entryFuture.set(o)) {
                Caches.localDescs.put(weakClassKey, new SoftReference<EntryFuture>((EntryFuture)o));
            }
            else {
                o = entryFuture.get();
            }
        }
        if (o instanceof ObjectStreamClass) {
            return (ObjectStreamClass)o;
        }
        if (o instanceof RuntimeException) {
            throw (RuntimeException)o;
        }
        if (o instanceof Error) {
            throw (Error)o;
        }
        throw new InternalError("unexpected entry: " + o);
    }
    
    private ObjectStreamClass(final Class<?> cl) {
        this.hasBlockExternalData = true;
        this.cl = cl;
        this.name = cl.getName();
        this.isProxy = Proxy.isProxyClass(cl);
        this.isEnum = Enum.class.isAssignableFrom(cl);
        this.serializable = Serializable.class.isAssignableFrom(cl);
        this.externalizable = Externalizable.class.isAssignableFrom(cl);
        final Class<?> superclass = cl.getSuperclass();
        this.superDesc = ((superclass != null) ? lookup(superclass, false) : null);
        this.localDesc = this;
        if (this.serializable) {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                @Override
                public Void run() {
                    if (ObjectStreamClass.this.isEnum) {
                        ObjectStreamClass.this.suid = 0L;
                        ObjectStreamClass.this.fields = ObjectStreamClass.NO_FIELDS;
                        return null;
                    }
                    if (cl.isArray()) {
                        ObjectStreamClass.this.fields = ObjectStreamClass.NO_FIELDS;
                        return null;
                    }
                    ObjectStreamClass.this.suid = getDeclaredSUID(cl);
                    try {
                        ObjectStreamClass.this.fields = getSerialFields(cl);
                        ObjectStreamClass.this.computeFieldOffsets();
                    }
                    catch (InvalidClassException ex) {
                        ObjectStreamClass.this.serializeEx = (ObjectStreamClass.this.deserializeEx = new ExceptionInfo(ex.classname, ex.getMessage()));
                        ObjectStreamClass.this.fields = ObjectStreamClass.NO_FIELDS;
                    }
                    if (ObjectStreamClass.this.externalizable) {
                        ObjectStreamClass.this.cons = getExternalizableConstructor(cl);
                    }
                    else {
                        ObjectStreamClass.this.cons = getSerializableConstructor(cl);
                        ObjectStreamClass.this.writeObjectMethod = getPrivateMethod(cl, "writeObject", new Class[] { ObjectOutputStream.class }, Void.TYPE);
                        ObjectStreamClass.this.readObjectMethod = getPrivateMethod(cl, "readObject", new Class[] { ObjectInputStream.class }, Void.TYPE);
                        ObjectStreamClass.this.readObjectNoDataMethod = getPrivateMethod(cl, "readObjectNoData", null, Void.TYPE);
                        ObjectStreamClass.this.hasWriteObjectData = (ObjectStreamClass.this.writeObjectMethod != null);
                    }
                    ObjectStreamClass.this.domains = ObjectStreamClass.this.getProtectionDomains(ObjectStreamClass.this.cons, cl);
                    ObjectStreamClass.this.writeReplaceMethod = getInheritableMethod(cl, "writeReplace", null, Object.class);
                    ObjectStreamClass.this.readResolveMethod = getInheritableMethod(cl, "readResolve", null, Object.class);
                    return null;
                }
            });
        }
        else {
            this.suid = 0L;
            this.fields = ObjectStreamClass.NO_FIELDS;
        }
        try {
            this.fieldRefl = getReflector(this.fields, this);
        }
        catch (InvalidClassException ex) {
            throw new InternalError(ex);
        }
        if (this.deserializeEx == null) {
            if (this.isEnum) {
                this.deserializeEx = new ExceptionInfo(this.name, "enum type");
            }
            else if (this.cons == null) {
                this.deserializeEx = new ExceptionInfo(this.name, "no valid constructor");
            }
        }
        for (int i = 0; i < this.fields.length; ++i) {
            if (this.fields[i].getField() == null) {
                this.defaultSerializeEx = new ExceptionInfo(this.name, "unmatched serializable field(s) declared");
            }
        }
        this.initialized = true;
    }
    
    ObjectStreamClass() {
        this.hasBlockExternalData = true;
    }
    
    private ProtectionDomain noPermissionsDomain() {
        final Permissions permissions = new Permissions();
        permissions.setReadOnly();
        return new ProtectionDomain(null, permissions);
    }
    
    private ProtectionDomain[] getProtectionDomains(final Constructor<?> constructor, final Class<?> clazz) {
        ProtectionDomain[] array = null;
        if (constructor != null && clazz.getClassLoader() != null && System.getSecurityManager() != null) {
            Class<?> superclass = clazz;
            final Class<?> declaringClass = constructor.getDeclaringClass();
            Set<ProtectionDomain> set = null;
            while (superclass != declaringClass) {
                final ProtectionDomain protectionDomain = superclass.getProtectionDomain();
                if (protectionDomain != null) {
                    if (set == null) {
                        set = new HashSet<ProtectionDomain>();
                    }
                    set.add(protectionDomain);
                }
                superclass = superclass.getSuperclass();
                if (superclass == null) {
                    if (set == null) {
                        set = new HashSet<ProtectionDomain>();
                    }
                    else {
                        set.clear();
                    }
                    set.add(this.noPermissionsDomain());
                    break;
                }
            }
            if (set != null) {
                array = set.toArray(new ProtectionDomain[0]);
            }
        }
        return array;
    }
    
    void initProxy(final Class<?> cl, final ClassNotFoundException resolveEx, final ObjectStreamClass superDesc) throws InvalidClassException {
        ObjectStreamClass lookup = null;
        if (cl != null) {
            lookup = lookup(cl, true);
            if (!lookup.isProxy) {
                throw new InvalidClassException("cannot bind proxy descriptor to a non-proxy class");
            }
        }
        this.cl = cl;
        this.resolveEx = resolveEx;
        this.superDesc = superDesc;
        this.isProxy = true;
        this.serializable = true;
        this.suid = 0L;
        this.fields = ObjectStreamClass.NO_FIELDS;
        if (lookup != null) {
            this.localDesc = lookup;
            this.name = this.localDesc.name;
            this.externalizable = this.localDesc.externalizable;
            this.writeReplaceMethod = this.localDesc.writeReplaceMethod;
            this.readResolveMethod = this.localDesc.readResolveMethod;
            this.deserializeEx = this.localDesc.deserializeEx;
            this.domains = this.localDesc.domains;
            this.cons = this.localDesc.cons;
        }
        this.fieldRefl = getReflector(this.fields, this.localDesc);
        this.initialized = true;
    }
    
    void initNonProxy(final ObjectStreamClass objectStreamClass, final Class<?> cl, final ClassNotFoundException resolveEx, final ObjectStreamClass superDesc) throws InvalidClassException {
        final long longValue = Long.valueOf(objectStreamClass.getSerialVersionUID());
        ObjectStreamClass lookup = null;
        if (cl != null) {
            lookup = lookup(cl, true);
            if (lookup.isProxy) {
                throw new InvalidClassException("cannot bind non-proxy descriptor to a proxy class");
            }
            if (objectStreamClass.isEnum != lookup.isEnum) {
                throw new InvalidClassException(objectStreamClass.isEnum ? "cannot bind enum descriptor to a non-enum class" : "cannot bind non-enum descriptor to an enum class");
            }
            if (objectStreamClass.serializable == lookup.serializable && !cl.isArray() && longValue != lookup.getSerialVersionUID()) {
                throw new InvalidClassException(lookup.name, "local class incompatible: stream classdesc serialVersionUID = " + longValue + ", local class serialVersionUID = " + lookup.getSerialVersionUID());
            }
            if (!classNamesEqual(objectStreamClass.name, lookup.name)) {
                throw new InvalidClassException(lookup.name, "local class name incompatible with stream class name \"" + objectStreamClass.name + "\"");
            }
            if (!objectStreamClass.isEnum) {
                if (objectStreamClass.serializable == lookup.serializable && objectStreamClass.externalizable != lookup.externalizable) {
                    throw new InvalidClassException(lookup.name, "Serializable incompatible with Externalizable");
                }
                if (objectStreamClass.serializable != lookup.serializable || objectStreamClass.externalizable != lookup.externalizable || (!objectStreamClass.serializable && !objectStreamClass.externalizable)) {
                    this.deserializeEx = new ExceptionInfo(lookup.name, "class invalid for deserialization");
                }
            }
        }
        this.cl = cl;
        this.resolveEx = resolveEx;
        this.superDesc = superDesc;
        this.name = objectStreamClass.name;
        this.suid = longValue;
        this.isProxy = false;
        this.isEnum = objectStreamClass.isEnum;
        this.serializable = objectStreamClass.serializable;
        this.externalizable = objectStreamClass.externalizable;
        this.hasBlockExternalData = objectStreamClass.hasBlockExternalData;
        this.hasWriteObjectData = objectStreamClass.hasWriteObjectData;
        this.fields = objectStreamClass.fields;
        this.primDataSize = objectStreamClass.primDataSize;
        this.numObjFields = objectStreamClass.numObjFields;
        if (lookup != null) {
            this.localDesc = lookup;
            this.writeObjectMethod = this.localDesc.writeObjectMethod;
            this.readObjectMethod = this.localDesc.readObjectMethod;
            this.readObjectNoDataMethod = this.localDesc.readObjectNoDataMethod;
            this.writeReplaceMethod = this.localDesc.writeReplaceMethod;
            this.readResolveMethod = this.localDesc.readResolveMethod;
            if (this.deserializeEx == null) {
                this.deserializeEx = this.localDesc.deserializeEx;
            }
            this.domains = this.localDesc.domains;
            this.cons = this.localDesc.cons;
        }
        this.fieldRefl = getReflector(this.fields, this.localDesc);
        this.fields = this.fieldRefl.getFields();
        this.initialized = true;
    }
    
    void readNonProxy(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.name = objectInputStream.readUTF();
        this.suid = objectInputStream.readLong();
        this.isProxy = false;
        final byte byte1 = objectInputStream.readByte();
        this.hasWriteObjectData = ((byte1 & 0x1) != 0x0);
        this.hasBlockExternalData = ((byte1 & 0x8) != 0x0);
        this.externalizable = ((byte1 & 0x4) != 0x0);
        final boolean b = (byte1 & 0x2) != 0x0;
        if (this.externalizable && b) {
            throw new InvalidClassException(this.name, "serializable and externalizable flags conflict");
        }
        this.serializable = (this.externalizable || b);
        this.isEnum = ((byte1 & 0x10) != 0x0);
        if (this.isEnum && this.suid != 0L) {
            throw new InvalidClassException(this.name, "enum descriptor has non-zero serialVersionUID: " + this.suid);
        }
        final short short1 = objectInputStream.readShort();
        if (this.isEnum && short1 != 0) {
            throw new InvalidClassException(this.name, "enum descriptor has non-zero field count: " + short1);
        }
        this.fields = ((short1 > 0) ? new ObjectStreamField[short1] : ObjectStreamClass.NO_FIELDS);
        for (short n = 0; n < short1; ++n) {
            final char c = (char)objectInputStream.readByte();
            final String utf = objectInputStream.readUTF();
            final String s = (c == 'L' || c == '[') ? objectInputStream.readTypeString() : new String(new char[] { c });
            try {
                this.fields[n] = new ObjectStreamField(utf, s, false);
            }
            catch (RuntimeException ex) {
                throw (IOException)new InvalidClassException(this.name, "invalid descriptor for field " + utf).initCause(ex);
            }
        }
        this.computeFieldOffsets();
    }
    
    void writeNonProxy(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(this.name);
        objectOutputStream.writeLong(this.getSerialVersionUID());
        int n = 0;
        if (this.externalizable) {
            n = (byte)(n | 0x4);
            if (objectOutputStream.getProtocolVersion() != 1) {
                n = (byte)(n | 0x8);
            }
        }
        else if (this.serializable) {
            n = (byte)(n | 0x2);
        }
        if (this.hasWriteObjectData) {
            n = (byte)(n | 0x1);
        }
        if (this.isEnum) {
            n = (byte)(n | 0x10);
        }
        objectOutputStream.writeByte(n);
        objectOutputStream.writeShort(this.fields.length);
        for (int i = 0; i < this.fields.length; ++i) {
            final ObjectStreamField objectStreamField = this.fields[i];
            objectOutputStream.writeByte(objectStreamField.getTypeCode());
            objectOutputStream.writeUTF(objectStreamField.getName());
            if (!objectStreamField.isPrimitive()) {
                objectOutputStream.writeTypeString(objectStreamField.getTypeString());
            }
        }
    }
    
    ClassNotFoundException getResolveException() {
        return this.resolveEx;
    }
    
    private final void requireInitialized() {
        if (!this.initialized) {
            throw new InternalError("Unexpected call when not initialized");
        }
    }
    
    final void checkInitialized() throws InvalidClassException {
        if (!this.initialized) {
            throw new InvalidClassException("Class descriptor should be initialized");
        }
    }
    
    void checkDeserialize() throws InvalidClassException {
        this.requireInitialized();
        if (this.deserializeEx != null) {
            throw this.deserializeEx.newInvalidClassException();
        }
    }
    
    void checkSerialize() throws InvalidClassException {
        this.requireInitialized();
        if (this.serializeEx != null) {
            throw this.serializeEx.newInvalidClassException();
        }
    }
    
    void checkDefaultSerialize() throws InvalidClassException {
        this.requireInitialized();
        if (this.defaultSerializeEx != null) {
            throw this.defaultSerializeEx.newInvalidClassException();
        }
    }
    
    ObjectStreamClass getSuperDesc() {
        this.requireInitialized();
        return this.superDesc;
    }
    
    ObjectStreamClass getLocalDesc() {
        this.requireInitialized();
        return this.localDesc;
    }
    
    ObjectStreamField[] getFields(final boolean b) {
        return b ? this.fields.clone() : this.fields;
    }
    
    ObjectStreamField getField(final String s, final Class<?> clazz) {
        for (int i = 0; i < this.fields.length; ++i) {
            final ObjectStreamField objectStreamField = this.fields[i];
            if (objectStreamField.getName().equals(s)) {
                if (clazz == null || (clazz == Object.class && !objectStreamField.isPrimitive())) {
                    return objectStreamField;
                }
                final Class<?> type = objectStreamField.getType();
                if (type != null && clazz.isAssignableFrom(type)) {
                    return objectStreamField;
                }
            }
        }
        return null;
    }
    
    boolean isProxy() {
        this.requireInitialized();
        return this.isProxy;
    }
    
    boolean isEnum() {
        this.requireInitialized();
        return this.isEnum;
    }
    
    boolean isExternalizable() {
        this.requireInitialized();
        return this.externalizable;
    }
    
    boolean isSerializable() {
        this.requireInitialized();
        return this.serializable;
    }
    
    boolean hasBlockExternalData() {
        this.requireInitialized();
        return this.hasBlockExternalData;
    }
    
    boolean hasWriteObjectData() {
        this.requireInitialized();
        return this.hasWriteObjectData;
    }
    
    boolean isInstantiable() {
        this.requireInitialized();
        return this.cons != null;
    }
    
    boolean hasWriteObjectMethod() {
        this.requireInitialized();
        return this.writeObjectMethod != null;
    }
    
    boolean hasReadObjectMethod() {
        this.requireInitialized();
        return this.readObjectMethod != null;
    }
    
    boolean hasReadObjectNoDataMethod() {
        this.requireInitialized();
        return this.readObjectNoDataMethod != null;
    }
    
    boolean hasWriteReplaceMethod() {
        this.requireInitialized();
        return this.writeReplaceMethod != null;
    }
    
    boolean hasReadResolveMethod() {
        this.requireInitialized();
        return this.readResolveMethod != null;
    }
    
    Object newInstance() throws InstantiationException, InvocationTargetException, UnsupportedOperationException {
        this.requireInitialized();
        if (this.cons != null) {
            try {
                if (this.domains == null || this.domains.length == 0) {
                    return this.cons.newInstance(new Object[0]);
                }
                final JavaSecurityAccess javaSecurityAccess = SharedSecrets.getJavaSecurityAccess();
                final Throwable t;
                final PrivilegedAction<?> privilegedAction = () -> {
                    try {
                        return this.cons.newInstance(new Object[0]);
                    }
                    catch (InstantiationException | InvocationTargetException | IllegalAccessException ex4) {
                        throw new UndeclaredThrowableException(t);
                    }
                };
                try {
                    return javaSecurityAccess.doIntersectionPrivilege(privilegedAction, AccessController.getContext(), new AccessControlContext(this.domains));
                }
                catch (UndeclaredThrowableException ex) {
                    final Throwable cause = ex.getCause();
                    if (cause instanceof InstantiationException) {
                        throw (InstantiationException)cause;
                    }
                    if (cause instanceof InvocationTargetException) {
                        throw (InvocationTargetException)cause;
                    }
                    if (cause instanceof IllegalAccessException) {
                        throw (IllegalAccessException)cause;
                    }
                    throw ex;
                }
            }
            catch (IllegalAccessException ex2) {
                throw new InternalError(ex2);
            }
            catch (InstantiationError instantiationError) {
                final InstantiationException ex3 = new InstantiationException();
                ex3.initCause(instantiationError);
                throw ex3;
            }
        }
        throw new UnsupportedOperationException();
    }
    
    void invokeWriteObject(final Object o, final ObjectOutputStream objectOutputStream) throws IOException, UnsupportedOperationException {
        this.requireInitialized();
        if (this.writeObjectMethod != null) {
            try {
                this.writeObjectMethod.invoke(o, objectOutputStream);
                return;
            }
            catch (InvocationTargetException ex) {
                final Throwable targetException = ex.getTargetException();
                if (targetException instanceof IOException) {
                    throw (IOException)targetException;
                }
                throwMiscException(targetException);
                return;
            }
            catch (IllegalAccessException ex2) {
                throw new InternalError(ex2);
            }
            throw new UnsupportedOperationException();
        }
        throw new UnsupportedOperationException();
    }
    
    void invokeReadObject(final Object o, final ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException, UnsupportedOperationException {
        this.requireInitialized();
        if (this.readObjectMethod != null) {
            try {
                this.readObjectMethod.invoke(o, objectInputStream);
                return;
            }
            catch (InvocationTargetException ex) {
                final Throwable targetException = ex.getTargetException();
                if (targetException instanceof ClassNotFoundException) {
                    throw (ClassNotFoundException)targetException;
                }
                if (targetException instanceof IOException) {
                    throw (IOException)targetException;
                }
                throwMiscException(targetException);
                return;
            }
            catch (IllegalAccessException ex2) {
                throw new InternalError(ex2);
            }
            throw new UnsupportedOperationException();
        }
        throw new UnsupportedOperationException();
    }
    
    void invokeReadObjectNoData(final Object o) throws IOException, UnsupportedOperationException {
        this.requireInitialized();
        if (this.readObjectNoDataMethod != null) {
            try {
                this.readObjectNoDataMethod.invoke(o, (Object[])null);
                return;
            }
            catch (InvocationTargetException ex) {
                final Throwable targetException = ex.getTargetException();
                if (targetException instanceof ObjectStreamException) {
                    throw (ObjectStreamException)targetException;
                }
                throwMiscException(targetException);
                return;
            }
            catch (IllegalAccessException ex2) {
                throw new InternalError(ex2);
            }
            throw new UnsupportedOperationException();
        }
        throw new UnsupportedOperationException();
    }
    
    Object invokeWriteReplace(final Object o) throws IOException, UnsupportedOperationException {
        this.requireInitialized();
        if (this.writeReplaceMethod != null) {
            try {
                return this.writeReplaceMethod.invoke(o, (Object[])null);
            }
            catch (InvocationTargetException ex) {
                final Throwable targetException = ex.getTargetException();
                if (targetException instanceof ObjectStreamException) {
                    throw (ObjectStreamException)targetException;
                }
                throwMiscException(targetException);
                throw new InternalError(targetException);
            }
            catch (IllegalAccessException ex2) {
                throw new InternalError(ex2);
            }
        }
        throw new UnsupportedOperationException();
    }
    
    Object invokeReadResolve(final Object o) throws IOException, UnsupportedOperationException {
        this.requireInitialized();
        if (this.readResolveMethod != null) {
            try {
                return this.readResolveMethod.invoke(o, (Object[])null);
            }
            catch (InvocationTargetException ex) {
                final Throwable targetException = ex.getTargetException();
                if (targetException instanceof ObjectStreamException) {
                    throw (ObjectStreamException)targetException;
                }
                throwMiscException(targetException);
                throw new InternalError(targetException);
            }
            catch (IllegalAccessException ex2) {
                throw new InternalError(ex2);
            }
        }
        throw new UnsupportedOperationException();
    }
    
    ClassDataSlot[] getClassDataLayout() throws InvalidClassException {
        if (this.dataLayout == null) {
            this.dataLayout = this.getClassDataLayout0();
        }
        return this.dataLayout;
    }
    
    private ClassDataSlot[] getClassDataLayout0() throws InvalidClassException {
        final ArrayList<Object> list = new ArrayList<Object>();
        Class<?> clazz = this.cl;
        Class<?> clazz2;
        for (clazz2 = this.cl; clazz2 != null && Serializable.class.isAssignableFrom(clazz2); clazz2 = clazz2.getSuperclass()) {}
        final HashSet<String> set = new HashSet<String>(3);
        for (ObjectStreamClass superDesc = this; superDesc != null; superDesc = superDesc.superDesc) {
            if (set.contains(superDesc.name)) {
                throw new InvalidClassException("Circular reference.");
            }
            set.add(superDesc.name);
            final String s = (superDesc.cl != null) ? superDesc.cl.getName() : superDesc.name;
            Class<?> clazz3 = null;
            for (Class<?> superclass = clazz; superclass != clazz2; superclass = superclass.getSuperclass()) {
                if (s.equals(superclass.getName())) {
                    clazz3 = superclass;
                    break;
                }
            }
            if (clazz3 != null) {
                for (Class<?> superclass2 = clazz; superclass2 != clazz3; superclass2 = superclass2.getSuperclass()) {
                    list.add(new ClassDataSlot(lookup(superclass2, true), false));
                }
                clazz = clazz3.getSuperclass();
            }
            list.add(new ClassDataSlot(superDesc.getVariantFor(clazz3), true));
        }
        for (Class<?> superclass3 = clazz; superclass3 != clazz2; superclass3 = superclass3.getSuperclass()) {
            list.add(new ClassDataSlot(lookup(superclass3, true), false));
        }
        Collections.reverse(list);
        return list.toArray(new ClassDataSlot[list.size()]);
    }
    
    int getPrimDataSize() {
        return this.primDataSize;
    }
    
    int getNumObjFields() {
        return this.numObjFields;
    }
    
    void getPrimFieldValues(final Object o, final byte[] array) {
        this.fieldRefl.getPrimFieldValues(o, array);
    }
    
    void setPrimFieldValues(final Object o, final byte[] array) {
        this.fieldRefl.setPrimFieldValues(o, array);
    }
    
    void getObjFieldValues(final Object o, final Object[] array) {
        this.fieldRefl.getObjFieldValues(o, array);
    }
    
    void setObjFieldValues(final Object o, final Object[] array) {
        this.fieldRefl.setObjFieldValues(o, array);
    }
    
    private void computeFieldOffsets() throws InvalidClassException {
        this.primDataSize = 0;
        this.numObjFields = 0;
        int n = -1;
        for (int i = 0; i < this.fields.length; ++i) {
            final ObjectStreamField objectStreamField = this.fields[i];
            switch (objectStreamField.getTypeCode()) {
                case 'B':
                case 'Z': {
                    objectStreamField.setOffset(this.primDataSize++);
                    break;
                }
                case 'C':
                case 'S': {
                    objectStreamField.setOffset(this.primDataSize);
                    this.primDataSize += 2;
                    break;
                }
                case 'F':
                case 'I': {
                    objectStreamField.setOffset(this.primDataSize);
                    this.primDataSize += 4;
                    break;
                }
                case 'D':
                case 'J': {
                    objectStreamField.setOffset(this.primDataSize);
                    this.primDataSize += 8;
                    break;
                }
                case 'L':
                case '[': {
                    objectStreamField.setOffset(this.numObjFields++);
                    if (n == -1) {
                        n = i;
                        break;
                    }
                    break;
                }
                default: {
                    throw new InternalError();
                }
            }
        }
        if (n != -1 && n + this.numObjFields != this.fields.length) {
            throw new InvalidClassException(this.name, "illegal field order");
        }
    }
    
    private ObjectStreamClass getVariantFor(final Class<?> clazz) throws InvalidClassException {
        if (this.cl == clazz) {
            return this;
        }
        final ObjectStreamClass objectStreamClass = new ObjectStreamClass();
        if (this.isProxy) {
            objectStreamClass.initProxy(clazz, null, this.superDesc);
        }
        else {
            objectStreamClass.initNonProxy(this, clazz, null, this.superDesc);
        }
        return objectStreamClass;
    }
    
    private static Constructor<?> getExternalizableConstructor(final Class<?> clazz) {
        try {
            final Constructor<?> declaredConstructor = clazz.getDeclaredConstructor((Class<?>[])null);
            declaredConstructor.setAccessible(true);
            return ((declaredConstructor.getModifiers() & 0x1) != 0x0) ? declaredConstructor : null;
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
    }
    
    private static boolean superHasAccessibleConstructor(final Class<?> clazz) {
        final Class<?> superclass = clazz.getSuperclass();
        assert Serializable.class.isAssignableFrom(clazz);
        assert superclass != null;
        if (packageEquals(clazz, superclass)) {
            final Constructor[] declaredConstructors = superclass.getDeclaredConstructors();
            for (int length = declaredConstructors.length, i = 0; i < length; ++i) {
                if ((declaredConstructors[i].getModifiers() & 0x2) == 0x0) {
                    return true;
                }
            }
            return false;
        }
        if ((superclass.getModifiers() & 0x5) == 0x0) {
            return false;
        }
        final Constructor<?>[] declaredConstructors2 = superclass.getDeclaredConstructors();
        for (int length2 = declaredConstructors2.length, j = 0; j < length2; ++j) {
            if ((declaredConstructors2[j].getModifiers() & 0x5) != 0x0) {
                return true;
            }
        }
        return false;
    }
    
    private static Constructor<?> getSerializableConstructor(final Class<?> clazz) {
        Class<?> superclass = clazz;
        while (Serializable.class.isAssignableFrom(superclass)) {
            final Class<?> clazz2 = superclass;
            if ((superclass = superclass.getSuperclass()) == null || (!ObjectStreamClass.disableSerialConstructorChecks && !superHasAccessibleConstructor(clazz2))) {
                return null;
            }
        }
        try {
            final Constructor<?> declaredConstructor = superclass.getDeclaredConstructor((Class<?>[])null);
            final int modifiers = declaredConstructor.getModifiers();
            if ((modifiers & 0x2) != 0x0 || ((modifiers & 0x5) == 0x0 && !packageEquals(clazz, superclass))) {
                return null;
            }
            final Constructor<?> constructorForSerialization = ObjectStreamClass.reflFactory.newConstructorForSerialization(clazz, declaredConstructor);
            constructorForSerialization.setAccessible(true);
            return constructorForSerialization;
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
    }
    
    private static Method getInheritableMethod(final Class<?> clazz, final String s, final Class<?>[] array, final Class<?> clazz2) {
        Method declaredMethod = null;
        Class<?> superclass = clazz;
        while (superclass != null) {
            try {
                declaredMethod = superclass.getDeclaredMethod(s, array);
            }
            catch (NoSuchMethodException ex) {
                superclass = superclass.getSuperclass();
                continue;
            }
            break;
        }
        if (declaredMethod == null || declaredMethod.getReturnType() != clazz2) {
            return null;
        }
        declaredMethod.setAccessible(true);
        final int modifiers = declaredMethod.getModifiers();
        if ((modifiers & 0x408) != 0x0) {
            return null;
        }
        if ((modifiers & 0x5) != 0x0) {
            return declaredMethod;
        }
        if ((modifiers & 0x2) != 0x0) {
            return (clazz == superclass) ? declaredMethod : null;
        }
        return packageEquals(clazz, superclass) ? declaredMethod : null;
    }
    
    private static Method getPrivateMethod(final Class<?> clazz, final String s, final Class<?>[] array, final Class<?> clazz2) {
        try {
            final Method declaredMethod = clazz.getDeclaredMethod(s, (Class[])array);
            declaredMethod.setAccessible(true);
            final int modifiers = declaredMethod.getModifiers();
            return (declaredMethod.getReturnType() == clazz2 && (modifiers & 0x8) == 0x0 && (modifiers & 0x2) != 0x0) ? declaredMethod : null;
        }
        catch (NoSuchMethodException ex) {
            return null;
        }
    }
    
    private static boolean packageEquals(final Class<?> clazz, final Class<?> clazz2) {
        return clazz.getClassLoader() == clazz2.getClassLoader() && getPackageName(clazz).equals(getPackageName(clazz2));
    }
    
    private static String getPackageName(final Class<?> clazz) {
        String s = clazz.getName();
        final int lastIndex = s.lastIndexOf(91);
        if (lastIndex >= 0) {
            s = s.substring(lastIndex + 2);
        }
        final int lastIndex2 = s.lastIndexOf(46);
        return (lastIndex2 >= 0) ? s.substring(0, lastIndex2) : "";
    }
    
    private static boolean classNamesEqual(String substring, String substring2) {
        substring = substring.substring(substring.lastIndexOf(46) + 1);
        substring2 = substring2.substring(substring2.lastIndexOf(46) + 1);
        return substring.equals(substring2);
    }
    
    private static String getClassSignature(Class<?> componentType) {
        final StringBuilder sb = new StringBuilder();
        while (componentType.isArray()) {
            sb.append('[');
            componentType = componentType.getComponentType();
        }
        if (componentType.isPrimitive()) {
            if (componentType == Integer.TYPE) {
                sb.append('I');
            }
            else if (componentType == Byte.TYPE) {
                sb.append('B');
            }
            else if (componentType == Long.TYPE) {
                sb.append('J');
            }
            else if (componentType == Float.TYPE) {
                sb.append('F');
            }
            else if (componentType == Double.TYPE) {
                sb.append('D');
            }
            else if (componentType == Short.TYPE) {
                sb.append('S');
            }
            else if (componentType == Character.TYPE) {
                sb.append('C');
            }
            else if (componentType == Boolean.TYPE) {
                sb.append('Z');
            }
            else {
                if (componentType != Void.TYPE) {
                    throw new InternalError();
                }
                sb.append('V');
            }
        }
        else {
            sb.append('L' + componentType.getName().replace('.', '/') + ';');
        }
        return sb.toString();
    }
    
    private static String getMethodSignature(final Class<?>[] array, final Class<?> clazz) {
        final StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (int i = 0; i < array.length; ++i) {
            sb.append(getClassSignature(array[i]));
        }
        sb.append(')');
        sb.append(getClassSignature(clazz));
        return sb.toString();
    }
    
    private static void throwMiscException(final Throwable t) throws IOException {
        if (t instanceof RuntimeException) {
            throw (RuntimeException)t;
        }
        if (t instanceof Error) {
            throw (Error)t;
        }
        final IOException ex = new IOException("unexpected exception type");
        ex.initCause(t);
        throw ex;
    }
    
    private static ObjectStreamField[] getSerialFields(final Class<?> clazz) throws InvalidClassException {
        ObjectStreamField[] array;
        if (Serializable.class.isAssignableFrom(clazz) && !Externalizable.class.isAssignableFrom(clazz) && !Proxy.isProxyClass(clazz) && !clazz.isInterface()) {
            if ((array = getDeclaredSerialFields(clazz)) == null) {
                array = getDefaultSerialFields(clazz);
            }
            Arrays.sort(array);
        }
        else {
            array = ObjectStreamClass.NO_FIELDS;
        }
        return array;
    }
    
    private static ObjectStreamField[] getDeclaredSerialFields(final Class<?> clazz) throws InvalidClassException {
        ObjectStreamField[] array = null;
        try {
            final Field declaredField = clazz.getDeclaredField("serialPersistentFields");
            final int n = 26;
            if ((declaredField.getModifiers() & n) == n) {
                declaredField.setAccessible(true);
                array = (ObjectStreamField[])declaredField.get(null);
            }
        }
        catch (Exception ex) {}
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ObjectStreamClass.NO_FIELDS;
        }
        final ObjectStreamField[] array2 = new ObjectStreamField[array.length];
        final HashSet set = new HashSet<String>(array.length);
        for (int i = 0; i < array.length; ++i) {
            final ObjectStreamField objectStreamField = array[i];
            final String name = objectStreamField.getName();
            if (set.contains(name)) {
                throw new InvalidClassException("multiple serializable fields named " + name);
            }
            set.add(name);
            try {
                final Field declaredField2 = clazz.getDeclaredField(name);
                if (declaredField2.getType() == objectStreamField.getType() && (declaredField2.getModifiers() & 0x8) == 0x0) {
                    array2[i] = new ObjectStreamField(declaredField2, objectStreamField.isUnshared(), true);
                }
            }
            catch (NoSuchFieldException ex2) {}
            if (array2[i] == null) {
                array2[i] = new ObjectStreamField(name, objectStreamField.getType(), objectStreamField.isUnshared());
            }
        }
        return array2;
    }
    
    private static ObjectStreamField[] getDefaultSerialFields(final Class<?> clazz) {
        final Field[] declaredFields = clazz.getDeclaredFields();
        final ArrayList<ObjectStreamField> list = new ArrayList<ObjectStreamField>();
        final int n = 136;
        for (int i = 0; i < declaredFields.length; ++i) {
            if ((declaredFields[i].getModifiers() & n) == 0x0) {
                list.add(new ObjectStreamField(declaredFields[i], false, true));
            }
        }
        final int size = list.size();
        return (size == 0) ? ObjectStreamClass.NO_FIELDS : list.toArray(new ObjectStreamField[size]);
    }
    
    private static Long getDeclaredSUID(final Class<?> clazz) {
        try {
            final Field declaredField = clazz.getDeclaredField("serialVersionUID");
            final int n = 24;
            if ((declaredField.getModifiers() & n) == n) {
                declaredField.setAccessible(true);
                return declaredField.getLong(null);
            }
        }
        catch (Exception ex) {}
        return null;
    }
    
    private static long computeDefaultSUID(final Class<?> clazz) {
        if (!Serializable.class.isAssignableFrom(clazz) || Proxy.isProxyClass(clazz)) {
            return 0L;
        }
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeUTF(clazz.getName());
            int n = clazz.getModifiers() & 0x611;
            final Method[] declaredMethods = clazz.getDeclaredMethods();
            if ((n & 0x200) != 0x0) {
                n = ((declaredMethods.length > 0) ? (n | 0x400) : (n & 0xFFFFFBFF));
            }
            dataOutputStream.writeInt(n);
            if (!clazz.isArray()) {
                final Class<?>[] interfaces = clazz.getInterfaces();
                final String[] array = new String[interfaces.length];
                for (int i = 0; i < interfaces.length; ++i) {
                    array[i] = interfaces[i].getName();
                }
                Arrays.sort(array);
                for (int j = 0; j < array.length; ++j) {
                    dataOutputStream.writeUTF(array[j]);
                }
            }
            final Field[] declaredFields = clazz.getDeclaredFields();
            final MemberSignature[] array2 = new MemberSignature[declaredFields.length];
            for (int k = 0; k < declaredFields.length; ++k) {
                array2[k] = new MemberSignature(declaredFields[k]);
            }
            Arrays.sort(array2, new Comparator<MemberSignature>() {
                @Override
                public int compare(final MemberSignature memberSignature, final MemberSignature memberSignature2) {
                    return memberSignature.name.compareTo(memberSignature2.name);
                }
            });
            for (int l = 0; l < array2.length; ++l) {
                final MemberSignature memberSignature = array2[l];
                final int n2 = memberSignature.member.getModifiers() & 0xDF;
                if ((n2 & 0x2) == 0x0 || (n2 & 0x88) == 0x0) {
                    dataOutputStream.writeUTF(memberSignature.name);
                    dataOutputStream.writeInt(n2);
                    dataOutputStream.writeUTF(memberSignature.signature);
                }
            }
            if (hasStaticInitializer(clazz)) {
                dataOutputStream.writeUTF("<clinit>");
                dataOutputStream.writeInt(8);
                dataOutputStream.writeUTF("()V");
            }
            final Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
            final MemberSignature[] array3 = new MemberSignature[declaredConstructors.length];
            for (int n3 = 0; n3 < declaredConstructors.length; ++n3) {
                array3[n3] = new MemberSignature(declaredConstructors[n3]);
            }
            Arrays.sort(array3, new Comparator<MemberSignature>() {
                @Override
                public int compare(final MemberSignature memberSignature, final MemberSignature memberSignature2) {
                    return memberSignature.signature.compareTo(memberSignature2.signature);
                }
            });
            for (int n4 = 0; n4 < array3.length; ++n4) {
                final MemberSignature memberSignature2 = array3[n4];
                final int n5 = memberSignature2.member.getModifiers() & 0xD3F;
                if ((n5 & 0x2) == 0x0) {
                    dataOutputStream.writeUTF("<init>");
                    dataOutputStream.writeInt(n5);
                    dataOutputStream.writeUTF(memberSignature2.signature.replace('/', '.'));
                }
            }
            final MemberSignature[] array4 = new MemberSignature[declaredMethods.length];
            for (int n6 = 0; n6 < declaredMethods.length; ++n6) {
                array4[n6] = new MemberSignature(declaredMethods[n6]);
            }
            Arrays.sort(array4, new Comparator<MemberSignature>() {
                @Override
                public int compare(final MemberSignature memberSignature, final MemberSignature memberSignature2) {
                    int n = memberSignature.name.compareTo(memberSignature2.name);
                    if (n == 0) {
                        n = memberSignature.signature.compareTo(memberSignature2.signature);
                    }
                    return n;
                }
            });
            for (int n7 = 0; n7 < array4.length; ++n7) {
                final MemberSignature memberSignature3 = array4[n7];
                final int n8 = memberSignature3.member.getModifiers() & 0xD3F;
                if ((n8 & 0x2) == 0x0) {
                    dataOutputStream.writeUTF(memberSignature3.name);
                    dataOutputStream.writeInt(n8);
                    dataOutputStream.writeUTF(memberSignature3.signature.replace('/', '.'));
                }
            }
            dataOutputStream.flush();
            final byte[] digest = MessageDigest.getInstance("SHA").digest(byteArrayOutputStream.toByteArray());
            long n9 = 0L;
            for (int n10 = Math.min(digest.length, 8) - 1; n10 >= 0; --n10) {
                n9 = (n9 << 8 | (digest[n10] & 0xFF));
            }
            return n9;
        }
        catch (IOException ex) {
            throw new InternalError(ex);
        }
        catch (NoSuchAlgorithmException ex2) {
            throw new SecurityException(ex2.getMessage());
        }
    }
    
    private static native boolean hasStaticInitializer(final Class<?> p0);
    
    private static FieldReflector getReflector(final ObjectStreamField[] array, final ObjectStreamClass objectStreamClass) throws InvalidClassException {
        final Class<?> clazz = (objectStreamClass != null && array.length > 0) ? objectStreamClass.cl : null;
        processQueue(Caches.reflectorsQueue, Caches.reflectors);
        final FieldReflectorKey fieldReflectorKey = new FieldReflectorKey(clazz, array, Caches.reflectorsQueue);
        Reference<Object> reference = Caches.reflectors.get(fieldReflectorKey);
        Object o = null;
        if (reference != null) {
            o = reference.get();
        }
        Object o2 = null;
        if (o == null) {
            final EntryFuture entryFuture = new EntryFuture();
            final SoftReference softReference = new SoftReference<Object>(entryFuture);
            do {
                if (reference != null) {
                    Caches.reflectors.remove(fieldReflectorKey, reference);
                }
                reference = Caches.reflectors.putIfAbsent(fieldReflectorKey, softReference);
                if (reference != null) {
                    o = reference.get();
                }
            } while (reference != null && o == null);
            if (o == null) {
                o2 = entryFuture;
            }
        }
        if (o instanceof FieldReflector) {
            return (FieldReflector)o;
        }
        if (o instanceof EntryFuture) {
            o = ((EntryFuture)o).get();
        }
        else if (o == null) {
            try {
                o = new FieldReflector(matchFields(array, objectStreamClass));
            }
            catch (Throwable t) {
                o = t;
            }
            ((EntryFuture)o2).set(o);
            Caches.reflectors.put(fieldReflectorKey, new SoftReference<EntryFuture>((EntryFuture)o));
        }
        if (o instanceof FieldReflector) {
            return (FieldReflector)o;
        }
        if (o instanceof InvalidClassException) {
            throw (InvalidClassException)o;
        }
        if (o instanceof RuntimeException) {
            throw (RuntimeException)o;
        }
        if (o instanceof Error) {
            throw (Error)o;
        }
        throw new InternalError("unexpected entry: " + o);
    }
    
    private static ObjectStreamField[] matchFields(final ObjectStreamField[] array, final ObjectStreamClass objectStreamClass) throws InvalidClassException {
        final ObjectStreamField[] array2 = (objectStreamClass != null) ? objectStreamClass.fields : ObjectStreamClass.NO_FIELDS;
        final ObjectStreamField[] array3 = new ObjectStreamField[array.length];
        for (int i = 0; i < array.length; ++i) {
            final ObjectStreamField objectStreamField = array[i];
            ObjectStreamField objectStreamField2 = null;
            for (int j = 0; j < array2.length; ++j) {
                final ObjectStreamField objectStreamField3 = array2[j];
                if (objectStreamField.getName().equals(objectStreamField3.getName())) {
                    if ((objectStreamField.isPrimitive() || objectStreamField3.isPrimitive()) && objectStreamField.getTypeCode() != objectStreamField3.getTypeCode()) {
                        throw new InvalidClassException(objectStreamClass.name, "incompatible types for field " + objectStreamField.getName());
                    }
                    if (objectStreamField3.getField() != null) {
                        objectStreamField2 = new ObjectStreamField(objectStreamField3.getField(), objectStreamField3.isUnshared(), false);
                    }
                    else {
                        objectStreamField2 = new ObjectStreamField(objectStreamField3.getName(), objectStreamField3.getSignature(), objectStreamField3.isUnshared());
                    }
                }
            }
            if (objectStreamField2 == null) {
                objectStreamField2 = new ObjectStreamField(objectStreamField.getName(), objectStreamField.getSignature(), false);
            }
            objectStreamField2.setOffset(objectStreamField.getOffset());
            array3[i] = objectStreamField2;
        }
        return array3;
    }
    
    static void processQueue(final ReferenceQueue<Class<?>> referenceQueue, final ConcurrentMap<? extends WeakReference<Class<?>>, ?> concurrentMap) {
        Reference<? extends Class<?>> poll;
        while ((poll = referenceQueue.poll()) != null) {
            concurrentMap.remove(poll);
        }
    }
    
    static {
        NO_FIELDS = new ObjectStreamField[0];
        serialPersistentFields = ObjectStreamClass.NO_FIELDS;
        ObjectStreamClass.disableSerialConstructorChecks = AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                return "true".equals(System.getProperty("jdk.disableSerialConstructorChecks")) ? Boolean.TRUE : Boolean.FALSE;
            }
        });
        reflFactory = AccessController.doPrivileged((PrivilegedAction<ReflectionFactory>)new ReflectionFactory.GetReflectionFactoryAction());
        initNative();
    }
    
    private static class Caches
    {
        static final ConcurrentMap<WeakClassKey, Reference<?>> localDescs;
        static final ConcurrentMap<FieldReflectorKey, Reference<?>> reflectors;
        private static final ReferenceQueue<Class<?>> localDescsQueue;
        private static final ReferenceQueue<Class<?>> reflectorsQueue;
        
        static {
            localDescs = new ConcurrentHashMap<WeakClassKey, Reference<?>>();
            reflectors = new ConcurrentHashMap<FieldReflectorKey, Reference<?>>();
            localDescsQueue = new ReferenceQueue<Class<?>>();
            reflectorsQueue = new ReferenceQueue<Class<?>>();
        }
    }
    
    static class WeakClassKey extends WeakReference<Class<?>>
    {
        private final int hash;
        
        WeakClassKey(final Class<?> clazz, final ReferenceQueue<Class<?>> referenceQueue) {
            super(clazz, referenceQueue);
            this.hash = System.identityHashCode(clazz);
        }
        
        @Override
        public int hashCode() {
            return this.hash;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof WeakClassKey) {
                final Object value = ((Reference<Object>)this).get();
                return value != null && value == ((Reference<Object>)o).get();
            }
            return false;
        }
    }
    
    private static class FieldReflectorKey extends WeakReference<Class<?>>
    {
        private final String[] sigs;
        private final int hash;
        private final boolean nullClass;
        
        FieldReflectorKey(final Class<?> clazz, final ObjectStreamField[] array, final ReferenceQueue<Class<?>> referenceQueue) {
            super(clazz, referenceQueue);
            this.nullClass = (clazz == null);
            this.sigs = new String[2 * array.length];
            int i = 0;
            int n = 0;
            while (i < array.length) {
                final ObjectStreamField objectStreamField = array[i];
                this.sigs[n++] = objectStreamField.getName();
                this.sigs[n++] = objectStreamField.getSignature();
                ++i;
            }
            this.hash = System.identityHashCode(clazz) + Arrays.hashCode(this.sigs);
        }
        
        @Override
        public int hashCode() {
            return this.hash;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof FieldReflectorKey) {
                final FieldReflectorKey fieldReflectorKey = (FieldReflectorKey)o;
                if (this.nullClass) {
                    if (!fieldReflectorKey.nullClass) {
                        return false;
                    }
                }
                else {
                    final Class clazz;
                    if ((clazz = ((Reference<Class>)this).get()) == null || clazz != ((Reference<Class>)fieldReflectorKey).get()) {
                        return false;
                    }
                }
                if (Arrays.equals(this.sigs, fieldReflectorKey.sigs)) {
                    return true;
                }
                return false;
            }
            return false;
        }
    }
    
    static class ClassDataSlot
    {
        final ObjectStreamClass desc;
        final boolean hasData;
        
        ClassDataSlot(final ObjectStreamClass desc, final boolean hasData) {
            this.desc = desc;
            this.hasData = hasData;
        }
    }
    
    private static class EntryFuture
    {
        private static final Object unset;
        private final Thread owner;
        private Object entry;
        
        private EntryFuture() {
            this.owner = Thread.currentThread();
            this.entry = EntryFuture.unset;
        }
        
        synchronized boolean set(final Object entry) {
            if (this.entry != EntryFuture.unset) {
                return false;
            }
            this.entry = entry;
            this.notifyAll();
            return true;
        }
        
        synchronized Object get() {
            boolean b = false;
            while (this.entry == EntryFuture.unset) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {
                    b = true;
                }
            }
            if (b) {
                AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction<Void>() {
                    @Override
                    public Void run() {
                        Thread.currentThread().interrupt();
                        return null;
                    }
                });
            }
            return this.entry;
        }
        
        Thread getOwner() {
            return this.owner;
        }
        
        static {
            unset = new Object();
        }
    }
    
    private static class ExceptionInfo
    {
        private final String className;
        private final String message;
        
        ExceptionInfo(final String className, final String message) {
            this.className = className;
            this.message = message;
        }
        
        InvalidClassException newInvalidClassException() {
            return new InvalidClassException(this.className, this.message);
        }
    }
    
    private static class FieldReflector
    {
        private static final Unsafe unsafe;
        private final ObjectStreamField[] fields;
        private final int numPrimFields;
        private final long[] readKeys;
        private final long[] writeKeys;
        private final int[] offsets;
        private final char[] typeCodes;
        private final Class<?>[] types;
        
        FieldReflector(final ObjectStreamField[] fields) {
            this.fields = fields;
            final int length = fields.length;
            this.readKeys = new long[length];
            this.writeKeys = new long[length];
            this.offsets = new int[length];
            this.typeCodes = new char[length];
            final ArrayList<Class<?>> list = new ArrayList<Class<?>>();
            final HashSet<Long> set = new HashSet<Long>();
            for (int i = 0; i < length; ++i) {
                final ObjectStreamField objectStreamField = fields[i];
                final Field field = objectStreamField.getField();
                final long n = (field != null) ? FieldReflector.unsafe.objectFieldOffset(field) : -1L;
                this.readKeys[i] = n;
                this.writeKeys[i] = (set.add(n) ? n : -1L);
                this.offsets[i] = objectStreamField.getOffset();
                this.typeCodes[i] = objectStreamField.getTypeCode();
                if (!objectStreamField.isPrimitive()) {
                    list.add((field != null) ? field.getType() : null);
                }
            }
            this.types = list.toArray(new Class[list.size()]);
            this.numPrimFields = length - this.types.length;
        }
        
        ObjectStreamField[] getFields() {
            return this.fields;
        }
        
        void getPrimFieldValues(final Object o, final byte[] array) {
            if (o == null) {
                throw new NullPointerException();
            }
            for (int i = 0; i < this.numPrimFields; ++i) {
                final long n = this.readKeys[i];
                final int n2 = this.offsets[i];
                switch (this.typeCodes[i]) {
                    case 'Z': {
                        Bits.putBoolean(array, n2, FieldReflector.unsafe.getBoolean(o, n));
                        break;
                    }
                    case 'B': {
                        array[n2] = FieldReflector.unsafe.getByte(o, n);
                        break;
                    }
                    case 'C': {
                        Bits.putChar(array, n2, FieldReflector.unsafe.getChar(o, n));
                        break;
                    }
                    case 'S': {
                        Bits.putShort(array, n2, FieldReflector.unsafe.getShort(o, n));
                        break;
                    }
                    case 'I': {
                        Bits.putInt(array, n2, FieldReflector.unsafe.getInt(o, n));
                        break;
                    }
                    case 'F': {
                        Bits.putFloat(array, n2, FieldReflector.unsafe.getFloat(o, n));
                        break;
                    }
                    case 'J': {
                        Bits.putLong(array, n2, FieldReflector.unsafe.getLong(o, n));
                        break;
                    }
                    case 'D': {
                        Bits.putDouble(array, n2, FieldReflector.unsafe.getDouble(o, n));
                        break;
                    }
                    default: {
                        throw new InternalError();
                    }
                }
            }
        }
        
        void setPrimFieldValues(final Object o, final byte[] array) {
            if (o == null) {
                throw new NullPointerException();
            }
            for (int i = 0; i < this.numPrimFields; ++i) {
                final long n = this.writeKeys[i];
                if (n != -1L) {
                    final int n2 = this.offsets[i];
                    switch (this.typeCodes[i]) {
                        case 'Z': {
                            FieldReflector.unsafe.putBoolean(o, n, Bits.getBoolean(array, n2));
                            break;
                        }
                        case 'B': {
                            FieldReflector.unsafe.putByte(o, n, array[n2]);
                            break;
                        }
                        case 'C': {
                            FieldReflector.unsafe.putChar(o, n, Bits.getChar(array, n2));
                            break;
                        }
                        case 'S': {
                            FieldReflector.unsafe.putShort(o, n, Bits.getShort(array, n2));
                            break;
                        }
                        case 'I': {
                            FieldReflector.unsafe.putInt(o, n, Bits.getInt(array, n2));
                            break;
                        }
                        case 'F': {
                            FieldReflector.unsafe.putFloat(o, n, Bits.getFloat(array, n2));
                            break;
                        }
                        case 'J': {
                            FieldReflector.unsafe.putLong(o, n, Bits.getLong(array, n2));
                            break;
                        }
                        case 'D': {
                            FieldReflector.unsafe.putDouble(o, n, Bits.getDouble(array, n2));
                            break;
                        }
                        default: {
                            throw new InternalError();
                        }
                    }
                }
            }
        }
        
        void getObjFieldValues(final Object o, final Object[] array) {
            if (o == null) {
                throw new NullPointerException();
            }
            int i = this.numPrimFields;
            while (i < this.fields.length) {
                switch (this.typeCodes[i]) {
                    case 'L':
                    case '[': {
                        array[this.offsets[i]] = FieldReflector.unsafe.getObject(o, this.readKeys[i]);
                        ++i;
                        continue;
                    }
                    default: {
                        throw new InternalError();
                    }
                }
            }
        }
        
        void setObjFieldValues(final Object o, final Object[] array) {
            if (o == null) {
                throw new NullPointerException();
            }
            for (int i = this.numPrimFields; i < this.fields.length; ++i) {
                final long n = this.writeKeys[i];
                if (n != -1L) {
                    switch (this.typeCodes[i]) {
                        case 'L':
                        case '[': {
                            final Object o2 = array[this.offsets[i]];
                            if (o2 != null && !this.types[i - this.numPrimFields].isInstance(o2)) {
                                final Field field = this.fields[i].getField();
                                throw new ClassCastException("cannot assign instance of " + o2.getClass().getName() + " to field " + field.getDeclaringClass().getName() + "." + field.getName() + " of type " + field.getType().getName() + " in instance of " + o.getClass().getName());
                            }
                            FieldReflector.unsafe.putObject(o, n, o2);
                            break;
                        }
                        default: {
                            throw new InternalError();
                        }
                    }
                }
            }
        }
        
        static {
            unsafe = Unsafe.getUnsafe();
        }
    }
    
    private static class MemberSignature
    {
        public final Member member;
        public final String name;
        public final String signature;
        
        public MemberSignature(final Field member) {
            this.member = member;
            this.name = member.getName();
            this.signature = getClassSignature(member.getType());
        }
        
        public MemberSignature(final Constructor<?> member) {
            this.member = member;
            this.name = member.getName();
            this.signature = getMethodSignature(member.getParameterTypes(), Void.TYPE);
        }
        
        public MemberSignature(final Method member) {
            this.member = member;
            this.name = member.getName();
            this.signature = getMethodSignature(member.getParameterTypes(), member.getReturnType());
        }
    }
}
