package java.io;

import java.lang.reflect.*;
import sun.reflect.misc.*;
import sun.misc.*;
import java.lang.ref.*;
import sun.security.action.*;
import java.util.concurrent.*;
import java.util.*;
import sun.util.logging.*;
import java.security.*;

public class ObjectInputStream extends InputStream implements ObjectInput, ObjectStreamConstants
{
    private static final int NULL_HANDLE = -1;
    private static final Object unsharedMarker;
    private static final HashMap<String, Class<?>> primClasses;
    private final BlockDataInputStream bin;
    private final ValidationList vlist;
    private long depth;
    private long totalObjectRefs;
    private boolean closed;
    private final HandleTable handles;
    private int passHandle;
    private boolean defaultDataEnd;
    private byte[] primVals;
    private final boolean enableOverride;
    private boolean enableResolve;
    private SerialCallbackContext curContext;
    private ObjectInputFilter serialFilter;
    private volatile ObjectStreamClassValidator validator;
    
    public ObjectInputStream(final InputStream inputStream) throws IOException {
        this.passHandle = -1;
        this.defaultDataEnd = false;
        this.verifySubclass();
        this.bin = new BlockDataInputStream(inputStream);
        this.handles = new HandleTable(10);
        this.vlist = new ValidationList();
        this.serialFilter = ObjectInputFilter.Config.getSerialFilter();
        this.enableOverride = false;
        this.readStreamHeader();
        this.bin.setBlockDataMode(true);
    }
    
    protected ObjectInputStream() throws IOException, SecurityException {
        this.passHandle = -1;
        this.defaultDataEnd = false;
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(ObjectInputStream.SUBCLASS_IMPLEMENTATION_PERMISSION);
        }
        this.bin = null;
        this.handles = null;
        this.vlist = null;
        this.serialFilter = ObjectInputFilter.Config.getSerialFilter();
        this.enableOverride = true;
    }
    
    @Override
    public final Object readObject() throws IOException, ClassNotFoundException {
        return this.readObject(Object.class);
    }
    
    private String readString() throws IOException {
        try {
            return (String)this.readObject(String.class);
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    private final Object readObject(final Class<?> clazz) throws IOException, ClassNotFoundException {
        if (this.enableOverride) {
            return this.readObjectOverride();
        }
        if (clazz != Object.class && clazz != String.class) {
            throw new AssertionError((Object)"internal error");
        }
        final int passHandle = this.passHandle;
        try {
            final Object object0 = this.readObject0(clazz, false);
            this.handles.markDependency(passHandle, this.passHandle);
            final ClassNotFoundException lookupException = this.handles.lookupException(this.passHandle);
            if (lookupException != null) {
                throw lookupException;
            }
            if (this.depth == 0L) {
                this.vlist.doCallbacks();
            }
            return object0;
        }
        finally {
            this.passHandle = passHandle;
            if (this.closed && this.depth == 0L) {
                this.clear();
            }
        }
    }
    
    protected Object readObjectOverride() throws IOException, ClassNotFoundException {
        return null;
    }
    
    public Object readUnshared() throws IOException, ClassNotFoundException {
        final int passHandle = this.passHandle;
        try {
            final Object object0 = this.readObject0(Object.class, true);
            this.handles.markDependency(passHandle, this.passHandle);
            final ClassNotFoundException lookupException = this.handles.lookupException(this.passHandle);
            if (lookupException != null) {
                throw lookupException;
            }
            if (this.depth == 0L) {
                this.vlist.doCallbacks();
            }
            return object0;
        }
        finally {
            this.passHandle = passHandle;
            if (this.closed && this.depth == 0L) {
                this.clear();
            }
        }
    }
    
    public void defaultReadObject() throws IOException, ClassNotFoundException {
        final SerialCallbackContext curContext = this.curContext;
        if (curContext == null) {
            throw new NotActiveException("not in call to readObject");
        }
        final Object obj = curContext.getObj();
        final ObjectStreamClass desc = curContext.getDesc();
        this.bin.setBlockDataMode(false);
        this.defaultReadFields(obj, desc);
        this.bin.setBlockDataMode(true);
        if (!desc.hasWriteObjectData()) {
            this.defaultDataEnd = true;
        }
        final ClassNotFoundException lookupException = this.handles.lookupException(this.passHandle);
        if (lookupException != null) {
            throw lookupException;
        }
    }
    
    public GetField readFields() throws IOException, ClassNotFoundException {
        final SerialCallbackContext curContext = this.curContext;
        if (curContext == null) {
            throw new NotActiveException("not in call to readObject");
        }
        curContext.getObj();
        final ObjectStreamClass desc = curContext.getDesc();
        this.bin.setBlockDataMode(false);
        final GetFieldImpl getFieldImpl = new GetFieldImpl(desc);
        getFieldImpl.readFields();
        this.bin.setBlockDataMode(true);
        if (!desc.hasWriteObjectData()) {
            this.defaultDataEnd = true;
        }
        return getFieldImpl;
    }
    
    public void registerValidation(final ObjectInputValidation objectInputValidation, final int n) throws NotActiveException, InvalidObjectException {
        if (this.depth == 0L) {
            throw new NotActiveException("stream inactive");
        }
        this.vlist.register(objectInputValidation, n);
    }
    
    protected Class<?> resolveClass(final ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        final String name = objectStreamClass.getName();
        try {
            return Class.forName(name, false, latestUserDefinedLoader());
        }
        catch (ClassNotFoundException ex) {
            final Class<?> clazz = ObjectInputStream.primClasses.get(name);
            if (clazz != null) {
                return clazz;
            }
            throw ex;
        }
    }
    
    protected Class<?> resolveProxyClass(final String[] array) throws IOException, ClassNotFoundException {
        final ClassLoader latestUserDefinedLoader = latestUserDefinedLoader();
        ClassLoader classLoader = null;
        int n = 0;
        final Class[] array2 = new Class[array.length];
        for (int i = 0; i < array.length; ++i) {
            final Class<?> forName = Class.forName(array[i], false, latestUserDefinedLoader);
            if ((forName.getModifiers() & 0x1) == 0x0) {
                if (n != 0) {
                    if (classLoader != forName.getClassLoader()) {
                        throw new IllegalAccessError("conflicting non-public interface class loaders");
                    }
                }
                else {
                    classLoader = forName.getClassLoader();
                    n = 1;
                }
            }
            array2[i] = forName;
        }
        try {
            return Proxy.getProxyClass((n != 0) ? classLoader : latestUserDefinedLoader, (Class<?>[])array2);
        }
        catch (IllegalArgumentException ex) {
            throw new ClassNotFoundException(null, ex);
        }
    }
    
    protected Object resolveObject(final Object o) throws IOException {
        return o;
    }
    
    protected boolean enableResolveObject(final boolean enableResolve) throws SecurityException {
        if (enableResolve == this.enableResolve) {
            return enableResolve;
        }
        if (enableResolve) {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(ObjectInputStream.SUBSTITUTION_PERMISSION);
            }
        }
        this.enableResolve = enableResolve;
        return !this.enableResolve;
    }
    
    protected void readStreamHeader() throws IOException, StreamCorruptedException {
        final short short1 = this.bin.readShort();
        final short short2 = this.bin.readShort();
        if (short1 != -21267 || short2 != 5) {
            throw new StreamCorruptedException(String.format("invalid stream header: %04X%04X", short1, short2));
        }
    }
    
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        final ObjectStreamClass objectStreamClass = new ObjectStreamClass();
        objectStreamClass.readNonProxy(this);
        return objectStreamClass;
    }
    
    @Override
    public int read() throws IOException {
        return this.bin.read();
    }
    
    @Override
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        if (array == null) {
            throw new NullPointerException();
        }
        final int n3 = n + n2;
        if (n < 0 || n2 < 0 || n3 > array.length || n3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.bin.read(array, n, n2, false);
    }
    
    @Override
    public int available() throws IOException {
        return this.bin.available();
    }
    
    @Override
    public void close() throws IOException {
        this.closed = true;
        if (this.depth == 0L) {
            this.clear();
        }
        this.bin.close();
    }
    
    @Override
    public boolean readBoolean() throws IOException {
        return this.bin.readBoolean();
    }
    
    @Override
    public byte readByte() throws IOException {
        return this.bin.readByte();
    }
    
    @Override
    public int readUnsignedByte() throws IOException {
        return this.bin.readUnsignedByte();
    }
    
    @Override
    public char readChar() throws IOException {
        return this.bin.readChar();
    }
    
    @Override
    public short readShort() throws IOException {
        return this.bin.readShort();
    }
    
    @Override
    public int readUnsignedShort() throws IOException {
        return this.bin.readUnsignedShort();
    }
    
    @Override
    public int readInt() throws IOException {
        return this.bin.readInt();
    }
    
    @Override
    public long readLong() throws IOException {
        return this.bin.readLong();
    }
    
    @Override
    public float readFloat() throws IOException {
        return this.bin.readFloat();
    }
    
    @Override
    public double readDouble() throws IOException {
        return this.bin.readDouble();
    }
    
    @Override
    public void readFully(final byte[] array) throws IOException {
        this.bin.readFully(array, 0, array.length, false);
    }
    
    @Override
    public void readFully(final byte[] array, final int n, final int n2) throws IOException {
        final int n3 = n + n2;
        if (n < 0 || n2 < 0 || n3 > array.length || n3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.bin.readFully(array, n, n2, false);
    }
    
    @Override
    public int skipBytes(final int n) throws IOException {
        return this.bin.skipBytes(n);
    }
    
    @Deprecated
    @Override
    public String readLine() throws IOException {
        return this.bin.readLine();
    }
    
    @Override
    public String readUTF() throws IOException {
        return this.bin.readUTF();
    }
    
    private final ObjectInputFilter getInternalObjectInputFilter() {
        return this.serialFilter;
    }
    
    private final void setInternalObjectInputFilter(final ObjectInputFilter serialFilter) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new SerializablePermission("serialFilter"));
        }
        if (this.serialFilter != null && this.serialFilter != ObjectInputFilter.Config.getSerialFilter()) {
            throw new IllegalStateException("filter can not be set more than once");
        }
        if (this.totalObjectRefs > 0L && !Caches.SET_FILTER_AFTER_READ) {
            throw new IllegalStateException("filter can not be set after an object has been read");
        }
        this.serialFilter = serialFilter;
    }
    
    private void filterCheck(final Class<?> clazz, final int n) throws InvalidClassException {
        if (this.serialFilter != null) {
            Throwable t = null;
            final long n2 = (this.bin == null) ? 0L : this.bin.getBytesRead();
            ObjectInputFilter.Status status;
            try {
                status = this.serialFilter.checkInput(new FilterValues(clazz, n, this.totalObjectRefs, this.depth, n2));
            }
            catch (RuntimeException ex) {
                status = ObjectInputFilter.Status.REJECTED;
                t = ex;
            }
            if (status == null || status == ObjectInputFilter.Status.REJECTED) {
                if (Logging.infoLogger != null) {
                    Logging.infoLogger.info("ObjectInputFilter {0}: {1}, array length: {2}, nRefs: {3}, depth: {4}, bytes: {5}, ex: {6}", status, clazz, n, this.totalObjectRefs, this.depth, n2, Objects.toString(t, "n/a"));
                }
                final InvalidClassException ex2 = new InvalidClassException("filter status: " + status);
                ex2.initCause(t);
                throw ex2;
            }
            if (Logging.traceLogger != null) {
                Logging.traceLogger.finer("ObjectInputFilter {0}: {1}, array length: {2}, nRefs: {3}, depth: {4}, bytes: {5}, ex: {6}", status, clazz, n, this.totalObjectRefs, this.depth, n2, Objects.toString(t, "n/a"));
            }
        }
    }
    
    private void checkArray(final Class<?> clazz, final int n) throws InvalidClassException {
        Objects.requireNonNull(clazz);
        if (!clazz.isArray()) {
            throw new IllegalArgumentException("not an array type");
        }
        if (n < 0) {
            throw new NegativeArraySizeException();
        }
        this.filterCheck(clazz, n);
    }
    
    private void verifySubclass() {
        final Class<? extends ObjectInputStream> class1 = this.getClass();
        if (class1 == ObjectInputStream.class) {
            return;
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null) {
            return;
        }
        ObjectStreamClass.processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
        final ObjectStreamClass.WeakClassKey weakClassKey = new ObjectStreamClass.WeakClassKey(class1, Caches.subclassAuditsQueue);
        Boolean value = Caches.subclassAudits.get(weakClassKey);
        if (value == null) {
            value = auditSubclass(class1);
            Caches.subclassAudits.putIfAbsent(weakClassKey, value);
        }
        if (value) {
            return;
        }
        securityManager.checkPermission(ObjectInputStream.SUBCLASS_IMPLEMENTATION_PERMISSION);
    }
    
    private static boolean auditSubclass(final Class<?> clazz) {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                Class<? super ObjectInputStream> clazz = (Class<? super ObjectInputStream>)clazz;
                while (clazz != ObjectInputStream.class) {
                    try {
                        clazz.getDeclaredMethod("readUnshared", (Class<?>[])null);
                        return Boolean.FALSE;
                    }
                    catch (NoSuchMethodException ex) {
                        try {
                            clazz.getDeclaredMethod("readFields", (Class<?>[])null);
                            return Boolean.FALSE;
                        }
                        catch (NoSuchMethodException ex2) {
                            clazz = clazz.getSuperclass();
                        }
                    }
                    break;
                }
                return Boolean.TRUE;
            }
        });
    }
    
    private void clear() {
        this.handles.clear();
        this.vlist.clear();
    }
    
    private Object readObject0(final Class<?> clazz, final boolean b) throws IOException {
        final boolean blockDataMode = this.bin.getBlockDataMode();
        if (blockDataMode) {
            final int currentBlockRemaining = this.bin.currentBlockRemaining();
            if (currentBlockRemaining > 0) {
                throw new OptionalDataException(currentBlockRemaining);
            }
            if (this.defaultDataEnd) {
                throw new OptionalDataException(true);
            }
            this.bin.setBlockDataMode(false);
        }
        byte peekByte;
        while ((peekByte = this.bin.peekByte()) == 121) {
            this.bin.readByte();
            this.handleReset();
        }
        ++this.depth;
        ++this.totalObjectRefs;
        try {
            switch (peekByte) {
                case 112: {
                    return this.readNull();
                }
                case 113: {
                    return clazz.cast(this.readHandle(b));
                }
                case 118: {
                    if (clazz == String.class) {
                        throw new ClassCastException("Cannot cast a class to java.lang.String");
                    }
                    return this.readClass(b);
                }
                case 114:
                case 125: {
                    if (clazz == String.class) {
                        throw new ClassCastException("Cannot cast a class to java.lang.String");
                    }
                    return this.readClassDesc(b);
                }
                case 116:
                case 124: {
                    return this.checkResolve(this.readString(b));
                }
                case 117: {
                    if (clazz == String.class) {
                        throw new ClassCastException("Cannot cast an array to java.lang.String");
                    }
                    return this.checkResolve(this.readArray(b));
                }
                case 126: {
                    if (clazz == String.class) {
                        throw new ClassCastException("Cannot cast an enum to java.lang.String");
                    }
                    return this.checkResolve(this.readEnum(b));
                }
                case 115: {
                    if (clazz == String.class) {
                        throw new ClassCastException("Cannot cast an object to java.lang.String");
                    }
                    return this.checkResolve(this.readOrdinaryObject(b));
                }
                case 123: {
                    if (clazz == String.class) {
                        throw new ClassCastException("Cannot cast an exception to java.lang.String");
                    }
                    throw new WriteAbortedException("writing aborted", this.readFatalException());
                }
                case 119:
                case 122: {
                    if (blockDataMode) {
                        this.bin.setBlockDataMode(true);
                        this.bin.peek();
                        throw new OptionalDataException(this.bin.currentBlockRemaining());
                    }
                    throw new StreamCorruptedException("unexpected block data");
                }
                case 120: {
                    if (blockDataMode) {
                        throw new OptionalDataException(true);
                    }
                    throw new StreamCorruptedException("unexpected end of block data");
                }
                default: {
                    throw new StreamCorruptedException(String.format("invalid type code: %02X", peekByte));
                }
            }
        }
        finally {
            --this.depth;
            this.bin.setBlockDataMode(blockDataMode);
        }
    }
    
    private Object checkResolve(final Object o) throws IOException {
        if (!this.enableResolve || this.handles.lookupException(this.passHandle) != null) {
            return o;
        }
        final Object resolveObject = this.resolveObject(o);
        if (resolveObject != o) {
            if (resolveObject != null) {
                if (resolveObject.getClass().isArray()) {
                    this.filterCheck(resolveObject.getClass(), Array.getLength(resolveObject));
                }
                else {
                    this.filterCheck(resolveObject.getClass(), -1);
                }
            }
            this.handles.setObject(this.passHandle, resolveObject);
        }
        return resolveObject;
    }
    
    String readTypeString() throws IOException {
        final int passHandle = this.passHandle;
        try {
            final byte peekByte = this.bin.peekByte();
            switch (peekByte) {
                case 112: {
                    return (String)this.readNull();
                }
                case 113: {
                    return (String)this.readHandle(false);
                }
                case 116:
                case 124: {
                    return this.readString(false);
                }
                default: {
                    throw new StreamCorruptedException(String.format("invalid type code: %02X", peekByte));
                }
            }
        }
        finally {
            this.passHandle = passHandle;
        }
    }
    
    private Object readNull() throws IOException {
        if (this.bin.readByte() != 112) {
            throw new InternalError();
        }
        this.passHandle = -1;
        return null;
    }
    
    private Object readHandle(final boolean b) throws IOException {
        if (this.bin.readByte() != 113) {
            throw new InternalError();
        }
        this.passHandle = this.bin.readInt() - 8257536;
        if (this.passHandle < 0 || this.passHandle >= this.handles.size()) {
            throw new StreamCorruptedException(String.format("invalid handle value: %08X", this.passHandle + 8257536));
        }
        if (b) {
            throw new InvalidObjectException("cannot read back reference as unshared");
        }
        final Object lookupObject = this.handles.lookupObject(this.passHandle);
        if (lookupObject == ObjectInputStream.unsharedMarker) {
            throw new InvalidObjectException("cannot read back reference to unshared object");
        }
        this.filterCheck(null, -1);
        return lookupObject;
    }
    
    private Class<?> readClass(final boolean b) throws IOException {
        if (this.bin.readByte() != 118) {
            throw new InternalError();
        }
        final ObjectStreamClass classDesc = this.readClassDesc(false);
        final Class<?> forClass = classDesc.forClass();
        this.passHandle = this.handles.assign(b ? ObjectInputStream.unsharedMarker : forClass);
        final ClassNotFoundException resolveException = classDesc.getResolveException();
        if (resolveException != null) {
            this.handles.markException(this.passHandle, resolveException);
        }
        this.handles.finish(this.passHandle);
        return forClass;
    }
    
    private ObjectStreamClass readClassDesc(final boolean b) throws IOException {
        final byte peekByte = this.bin.peekByte();
        ObjectStreamClass objectStreamClass = null;
        switch (peekByte) {
            case 112: {
                objectStreamClass = (ObjectStreamClass)this.readNull();
                break;
            }
            case 113: {
                objectStreamClass = (ObjectStreamClass)this.readHandle(b);
                objectStreamClass.checkInitialized();
                break;
            }
            case 125: {
                objectStreamClass = this.readProxyDesc(b);
                break;
            }
            case 114: {
                objectStreamClass = this.readNonProxyDesc(b);
                break;
            }
            default: {
                throw new StreamCorruptedException(String.format("invalid type code: %02X", peekByte));
            }
        }
        if (objectStreamClass != null) {
            this.validateDescriptor(objectStreamClass);
        }
        return objectStreamClass;
    }
    
    private boolean isCustomSubclass() {
        return this.getClass().getClassLoader() != ObjectInputStream.class.getClassLoader();
    }
    
    private ObjectStreamClass readProxyDesc(final boolean b) throws IOException {
        if (this.bin.readByte() != 125) {
            throw new InternalError();
        }
        final ObjectStreamClass objectStreamClass = new ObjectStreamClass();
        final int assign = this.handles.assign(b ? ObjectInputStream.unsharedMarker : objectStreamClass);
        this.passHandle = -1;
        final int int1 = this.bin.readInt();
        if (int1 > 65535) {
            throw new InvalidObjectException("interface limit exceeded: " + int1);
        }
        final String[] array = new String[int1];
        for (int i = 0; i < int1; ++i) {
            array[i] = this.bin.readUTF();
        }
        Class<?> resolveProxyClass = null;
        ClassNotFoundException ex = null;
        this.bin.setBlockDataMode(true);
        try {
            if ((resolveProxyClass = this.resolveProxyClass(array)) == null) {
                ex = new ClassNotFoundException("null class");
            }
            else {
                if (!Proxy.isProxyClass(resolveProxyClass)) {
                    throw new InvalidClassException("Not a proxy");
                }
                ReflectUtil.checkProxyPackageAccess(this.getClass().getClassLoader(), resolveProxyClass.getInterfaces());
                final Class<?>[] interfaces = resolveProxyClass.getInterfaces();
                for (int length = interfaces.length, j = 0; j < length; ++j) {
                    this.filterCheck(interfaces[j], -1);
                }
            }
        }
        catch (ClassNotFoundException ex2) {
            ex = ex2;
        }
        this.filterCheck(resolveProxyClass, -1);
        this.skipCustomData();
        try {
            ++this.totalObjectRefs;
            ++this.depth;
            objectStreamClass.initProxy(resolveProxyClass, ex, this.readClassDesc(false));
        }
        finally {
            --this.depth;
        }
        this.handles.finish(assign);
        this.passHandle = assign;
        return objectStreamClass;
    }
    
    private ObjectStreamClass readNonProxyDesc(final boolean b) throws IOException {
        if (this.bin.readByte() != 114) {
            throw new InternalError();
        }
        final ObjectStreamClass objectStreamClass = new ObjectStreamClass();
        final int assign = this.handles.assign(b ? ObjectInputStream.unsharedMarker : objectStreamClass);
        this.passHandle = -1;
        ObjectStreamClass classDescriptor;
        try {
            classDescriptor = this.readClassDescriptor();
        }
        catch (ClassNotFoundException ex) {
            throw (IOException)new InvalidClassException("failed to read class descriptor").initCause(ex);
        }
        Class<?> resolveClass = null;
        ClassNotFoundException ex2 = null;
        this.bin.setBlockDataMode(true);
        final boolean customSubclass = this.isCustomSubclass();
        try {
            if ((resolveClass = this.resolveClass(classDescriptor)) == null) {
                ex2 = new ClassNotFoundException("null class");
            }
            else if (customSubclass) {
                ReflectUtil.checkPackageAccess(resolveClass);
            }
        }
        catch (ClassNotFoundException ex3) {
            ex2 = ex3;
        }
        this.filterCheck(resolveClass, -1);
        this.skipCustomData();
        try {
            ++this.totalObjectRefs;
            ++this.depth;
            objectStreamClass.initNonProxy(classDescriptor, resolveClass, ex2, this.readClassDesc(false));
        }
        finally {
            --this.depth;
        }
        this.handles.finish(assign);
        this.passHandle = assign;
        return objectStreamClass;
    }
    
    private String readString(final boolean b) throws IOException {
        final byte byte1 = this.bin.readByte();
        String s = null;
        switch (byte1) {
            case 116: {
                s = this.bin.readUTF();
                break;
            }
            case 124: {
                s = this.bin.readLongUTF();
                break;
            }
            default: {
                throw new StreamCorruptedException(String.format("invalid type code: %02X", byte1));
            }
        }
        this.passHandle = this.handles.assign(b ? ObjectInputStream.unsharedMarker : s);
        this.handles.finish(this.passHandle);
        return s;
    }
    
    private Object readArray(final boolean b) throws IOException {
        if (this.bin.readByte() != 117) {
            throw new InternalError();
        }
        final ObjectStreamClass classDesc = this.readClassDesc(false);
        final int int1 = this.bin.readInt();
        this.filterCheck(classDesc.forClass(), int1);
        Object instance = null;
        Class componentType = null;
        final Class<?> forClass;
        if ((forClass = classDesc.forClass()) != null) {
            componentType = forClass.getComponentType();
            instance = Array.newInstance(componentType, int1);
        }
        final int assign = this.handles.assign(b ? ObjectInputStream.unsharedMarker : instance);
        final ClassNotFoundException resolveException = classDesc.getResolveException();
        if (resolveException != null) {
            this.handles.markException(assign, resolveException);
        }
        if (componentType == null) {
            for (int i = 0; i < int1; ++i) {
                this.readObject0(Object.class, false);
            }
        }
        else if (componentType.isPrimitive()) {
            if (componentType == Integer.TYPE) {
                this.bin.readInts((int[])instance, 0, int1);
            }
            else if (componentType == Byte.TYPE) {
                this.bin.readFully((byte[])instance, 0, int1, true);
            }
            else if (componentType == Long.TYPE) {
                this.bin.readLongs((long[])instance, 0, int1);
            }
            else if (componentType == Float.TYPE) {
                this.bin.readFloats((float[])instance, 0, int1);
            }
            else if (componentType == Double.TYPE) {
                this.bin.readDoubles((double[])instance, 0, int1);
            }
            else if (componentType == Short.TYPE) {
                this.bin.readShorts((short[])instance, 0, int1);
            }
            else if (componentType == Character.TYPE) {
                this.bin.readChars((char[])instance, 0, int1);
            }
            else {
                if (componentType != Boolean.TYPE) {
                    throw new InternalError();
                }
                this.bin.readBooleans((boolean[])instance, 0, int1);
            }
        }
        else {
            final Object[] array = (Object[])instance;
            for (int j = 0; j < int1; ++j) {
                array[j] = this.readObject0(Object.class, false);
                this.handles.markDependency(assign, this.passHandle);
            }
        }
        this.handles.finish(assign);
        this.passHandle = assign;
        return instance;
    }
    
    private Enum<?> readEnum(final boolean b) throws IOException {
        if (this.bin.readByte() != 126) {
            throw new InternalError();
        }
        final ObjectStreamClass classDesc = this.readClassDesc(false);
        if (!classDesc.isEnum()) {
            throw new InvalidClassException("non-enum class: " + classDesc);
        }
        final int assign = this.handles.assign(b ? ObjectInputStream.unsharedMarker : null);
        final ClassNotFoundException resolveException = classDesc.getResolveException();
        if (resolveException != null) {
            this.handles.markException(assign, resolveException);
        }
        final String string = this.readString(false);
        Object value = null;
        final Class<?> forClass = classDesc.forClass();
        if (forClass != null) {
            try {
                value = Enum.valueOf(forClass, string);
            }
            catch (IllegalArgumentException ex) {
                throw (IOException)new InvalidObjectException("enum constant " + string + " does not exist in " + forClass).initCause(ex);
            }
            if (!b) {
                this.handles.setObject(assign, value);
            }
        }
        this.handles.finish(assign);
        this.passHandle = assign;
        return (Enum<?>)value;
    }
    
    private Object readOrdinaryObject(final boolean b) throws IOException {
        if (this.bin.readByte() != 115) {
            throw new InternalError();
        }
        final ObjectStreamClass classDesc = this.readClassDesc(false);
        classDesc.checkDeserialize();
        final Class<?> forClass = classDesc.forClass();
        if (forClass == String.class || forClass == Class.class || forClass == ObjectStreamClass.class) {
            throw new InvalidClassException("invalid class descriptor");
        }
        Object o;
        try {
            o = (classDesc.isInstantiable() ? classDesc.newInstance() : null);
        }
        catch (Exception ex) {
            throw (IOException)new InvalidClassException(classDesc.forClass().getName(), "unable to create instance").initCause(ex);
        }
        this.passHandle = this.handles.assign(b ? ObjectInputStream.unsharedMarker : o);
        final ClassNotFoundException resolveException = classDesc.getResolveException();
        if (resolveException != null) {
            this.handles.markException(this.passHandle, resolveException);
        }
        if (classDesc.isExternalizable()) {
            this.readExternalData((Externalizable)o, classDesc);
        }
        else {
            this.readSerialData(o, classDesc);
        }
        this.handles.finish(this.passHandle);
        if (o != null && this.handles.lookupException(this.passHandle) == null && classDesc.hasReadResolveMethod()) {
            Object o2 = classDesc.invokeReadResolve(o);
            if (b && ((Externalizable)o2).getClass().isArray()) {
                o2 = cloneArray(o2);
            }
            if (o2 != o) {
                if (o2 != null) {
                    if (((Externalizable)o2).getClass().isArray()) {
                        this.filterCheck(((Externalizable)o2).getClass(), Array.getLength(o2));
                    }
                    else {
                        this.filterCheck(((Externalizable)o2).getClass(), -1);
                    }
                }
                this.handles.setObject(this.passHandle, o = o2);
            }
        }
        return o;
    }
    
    private void readExternalData(final Externalizable externalizable, final ObjectStreamClass objectStreamClass) throws IOException {
        final SerialCallbackContext curContext = this.curContext;
        if (curContext != null) {
            curContext.check();
        }
        this.curContext = null;
        try {
            final boolean hasBlockExternalData = objectStreamClass.hasBlockExternalData();
            if (hasBlockExternalData) {
                this.bin.setBlockDataMode(true);
            }
            if (externalizable != null) {
                try {
                    externalizable.readExternal(this);
                }
                catch (ClassNotFoundException ex) {
                    this.handles.markException(this.passHandle, ex);
                }
            }
            if (hasBlockExternalData) {
                this.skipCustomData();
            }
        }
        finally {
            if (curContext != null) {
                curContext.check();
            }
            this.curContext = curContext;
        }
    }
    
    private void readSerialData(final Object o, final ObjectStreamClass objectStreamClass) throws IOException {
        final ObjectStreamClass.ClassDataSlot[] classDataLayout = objectStreamClass.getClassDataLayout();
        for (int i = 0; i < classDataLayout.length; ++i) {
            final ObjectStreamClass desc = classDataLayout[i].desc;
            if (classDataLayout[i].hasData) {
                if (o == null || this.handles.lookupException(this.passHandle) != null) {
                    this.defaultReadFields(null, desc);
                }
                else if (desc.hasReadObjectMethod()) {
                    ThreadDeath threadDeath = null;
                    boolean b = false;
                    final SerialCallbackContext curContext = this.curContext;
                    if (curContext != null) {
                        curContext.check();
                    }
                    try {
                        this.curContext = new SerialCallbackContext(o, desc);
                        this.bin.setBlockDataMode(true);
                        desc.invokeReadObject(o, this);
                    }
                    catch (ClassNotFoundException ex) {
                        this.handles.markException(this.passHandle, ex);
                    }
                    finally {
                        do {
                            try {
                                this.curContext.setUsed();
                                if (curContext != null) {
                                    curContext.check();
                                }
                                this.curContext = curContext;
                                b = true;
                            }
                            catch (ThreadDeath threadDeath2) {
                                threadDeath = threadDeath2;
                            }
                        } while (!b);
                        if (threadDeath != null) {
                            throw threadDeath;
                        }
                    }
                    this.defaultDataEnd = false;
                }
                else {
                    this.defaultReadFields(o, desc);
                }
                if (desc.hasWriteObjectData()) {
                    this.skipCustomData();
                }
                else {
                    this.bin.setBlockDataMode(false);
                }
            }
            else if (o != null && desc.hasReadObjectNoDataMethod() && this.handles.lookupException(this.passHandle) == null) {
                desc.invokeReadObjectNoData(o);
            }
        }
    }
    
    private void skipCustomData() throws IOException {
        final int passHandle = this.passHandle;
    Label_0080:
        while (true) {
            if (this.bin.getBlockDataMode()) {
                this.bin.skipBlockData();
                this.bin.setBlockDataMode(false);
            }
            switch (this.bin.peekByte()) {
                case 119:
                case 122: {
                    this.bin.setBlockDataMode(true);
                    continue;
                }
                case 120: {
                    break Label_0080;
                }
                default: {
                    this.readObject0(Object.class, false);
                    continue;
                }
            }
        }
        this.bin.readByte();
        this.passHandle = passHandle;
    }
    
    private void defaultReadFields(final Object o, final ObjectStreamClass objectStreamClass) throws IOException {
        final Class<?> forClass = objectStreamClass.forClass();
        if (forClass != null && o != null && !forClass.isInstance(o)) {
            throw new ClassCastException();
        }
        final int primDataSize = objectStreamClass.getPrimDataSize();
        if (this.primVals == null || this.primVals.length < primDataSize) {
            this.primVals = new byte[primDataSize];
        }
        this.bin.readFully(this.primVals, 0, primDataSize, false);
        if (o != null) {
            objectStreamClass.setPrimFieldValues(o, this.primVals);
        }
        final int passHandle = this.passHandle;
        final ObjectStreamField[] fields = objectStreamClass.getFields(false);
        final Object[] array = new Object[objectStreamClass.getNumObjFields()];
        final int n = fields.length - array.length;
        for (int i = 0; i < array.length; ++i) {
            final ObjectStreamField objectStreamField = fields[n + i];
            array[i] = this.readObject0(Object.class, objectStreamField.isUnshared());
            if (objectStreamField.getField() != null) {
                this.handles.markDependency(passHandle, this.passHandle);
            }
        }
        if (o != null) {
            objectStreamClass.setObjFieldValues(o, array);
        }
        this.passHandle = passHandle;
    }
    
    private IOException readFatalException() throws IOException {
        if (this.bin.readByte() != 123) {
            throw new InternalError();
        }
        this.clear();
        return (IOException)this.readObject0(Object.class, false);
    }
    
    private void handleReset() throws StreamCorruptedException {
        if (this.depth > 0L) {
            throw new StreamCorruptedException("unexpected reset; recursion depth: " + this.depth);
        }
        this.clear();
    }
    
    private static native void bytesToFloats(final byte[] p0, final int p1, final float[] p2, final int p3, final int p4);
    
    private static native void bytesToDoubles(final byte[] p0, final int p1, final double[] p2, final int p3, final int p4);
    
    private static ClassLoader latestUserDefinedLoader() {
        return VM.latestUserDefinedLoader();
    }
    
    private static Object cloneArray(final Object o) {
        if (o instanceof Object[]) {
            return ((Object[])o).clone();
        }
        if (o instanceof boolean[]) {
            return ((boolean[])o).clone();
        }
        if (o instanceof byte[]) {
            return ((byte[])o).clone();
        }
        if (o instanceof char[]) {
            return ((char[])o).clone();
        }
        if (o instanceof double[]) {
            return ((double[])o).clone();
        }
        if (o instanceof float[]) {
            return ((float[])o).clone();
        }
        if (o instanceof int[]) {
            return ((int[])o).clone();
        }
        if (o instanceof long[]) {
            return ((long[])o).clone();
        }
        if (o instanceof short[]) {
            return ((short[])o).clone();
        }
        throw new AssertionError();
    }
    
    private void validateDescriptor(final ObjectStreamClass objectStreamClass) {
        final ObjectStreamClassValidator validator = this.validator;
        if (validator != null) {
            validator.validateDescriptor(objectStreamClass);
        }
    }
    
    private static void setValidator(final ObjectInputStream objectInputStream, final ObjectStreamClassValidator validator) {
        objectInputStream.validator = validator;
    }
    
    static {
        unsharedMarker = new Object();
        (primClasses = new HashMap<String, Class<?>>(8, 1.0f)).put("boolean", Boolean.TYPE);
        ObjectInputStream.primClasses.put("byte", Byte.TYPE);
        ObjectInputStream.primClasses.put("char", Character.TYPE);
        ObjectInputStream.primClasses.put("short", Short.TYPE);
        ObjectInputStream.primClasses.put("int", Integer.TYPE);
        ObjectInputStream.primClasses.put("long", Long.TYPE);
        ObjectInputStream.primClasses.put("float", Float.TYPE);
        ObjectInputStream.primClasses.put("double", Double.TYPE);
        ObjectInputStream.primClasses.put("void", Void.TYPE);
        SharedSecrets.setJavaOISAccess(new JavaOISAccess() {
            @Override
            public void setObjectInputFilter(final ObjectInputStream objectInputStream, final ObjectInputFilter objectInputFilter) {
                objectInputStream.setInternalObjectInputFilter(objectInputFilter);
            }
            
            @Override
            public ObjectInputFilter getObjectInputFilter(final ObjectInputStream objectInputStream) {
                return objectInputStream.getInternalObjectInputFilter();
            }
            
            @Override
            public void checkArray(final ObjectInputStream objectInputStream, final Class<?> clazz, final int n) throws InvalidClassException {
                objectInputStream.checkArray(clazz, n);
            }
        });
        SharedSecrets.setJavaObjectInputStreamAccess(ObjectInputStream::setValidator);
        SharedSecrets.setJavaObjectInputStreamReadString(ObjectInputStream::readString);
    }
    
    private class BlockDataInputStream extends InputStream implements DataInput
    {
        private static final int MAX_BLOCK_SIZE = 1024;
        private static final int MAX_HEADER_SIZE = 5;
        private static final int CHAR_BUF_SIZE = 256;
        private static final int HEADER_BLOCKED = -2;
        private final byte[] buf;
        private final byte[] hbuf;
        private final char[] cbuf;
        private boolean blkmode;
        private int pos;
        private int end;
        private int unread;
        private final PeekInputStream in;
        private final DataInputStream din;
        
        BlockDataInputStream(final InputStream inputStream) {
            this.buf = new byte[1024];
            this.hbuf = new byte[5];
            this.cbuf = new char[256];
            this.blkmode = false;
            this.pos = 0;
            this.end = -1;
            this.unread = 0;
            this.in = new PeekInputStream(inputStream);
            this.din = new DataInputStream(this);
        }
        
        boolean setBlockDataMode(final boolean blkmode) throws IOException {
            if (this.blkmode == blkmode) {
                return this.blkmode;
            }
            if (blkmode) {
                this.pos = 0;
                this.end = 0;
                this.unread = 0;
            }
            else if (this.pos < this.end) {
                throw new IllegalStateException("unread block data");
            }
            this.blkmode = blkmode;
            return !this.blkmode;
        }
        
        boolean getBlockDataMode() {
            return this.blkmode;
        }
        
        void skipBlockData() throws IOException {
            if (!this.blkmode) {
                throw new IllegalStateException("not in block data mode");
            }
            while (this.end >= 0) {
                this.refill();
            }
        }
        
        private int readBlockHeader(final boolean b) throws IOException {
            if (ObjectInputStream.this.defaultDataEnd) {
                return -1;
            }
            Label_0012: {
                break Label_0012;
                try {
                    while (true) {
                        final int n = b ? Integer.MAX_VALUE : this.in.available();
                        if (n == 0) {
                            return -2;
                        }
                        final int peek = this.in.peek();
                        switch (peek) {
                            case 119: {
                                if (n < 2) {
                                    return -2;
                                }
                                this.in.readFully(this.hbuf, 0, 2);
                                return this.hbuf[1] & 0xFF;
                            }
                            case 122: {
                                if (n < 5) {
                                    return -2;
                                }
                                this.in.readFully(this.hbuf, 0, 5);
                                final int int1 = Bits.getInt(this.hbuf, 1);
                                if (int1 < 0) {
                                    throw new StreamCorruptedException("illegal block data header length: " + int1);
                                }
                                return int1;
                            }
                            case 121: {
                                this.in.read();
                                ObjectInputStream.this.handleReset();
                                continue;
                            }
                            default: {
                                if (peek >= 0 && (peek < 112 || peek > 126)) {
                                    throw new StreamCorruptedException(String.format("invalid type code: %02X", peek));
                                }
                                return -1;
                            }
                        }
                    }
                }
                catch (EOFException ex) {
                    throw new StreamCorruptedException("unexpected EOF while reading block data header");
                }
            }
        }
        
        private void refill() throws IOException {
            try {
                do {
                    this.pos = 0;
                    if (this.unread > 0) {
                        final int read = this.in.read(this.buf, 0, Math.min(this.unread, 1024));
                        if (read < 0) {
                            throw new StreamCorruptedException("unexpected EOF in middle of data block");
                        }
                        this.end = read;
                        this.unread -= read;
                    }
                    else {
                        final int blockHeader = this.readBlockHeader(true);
                        if (blockHeader >= 0) {
                            this.end = 0;
                            this.unread = blockHeader;
                        }
                        else {
                            this.end = -1;
                            this.unread = 0;
                        }
                    }
                } while (this.pos == this.end);
            }
            catch (IOException ex) {
                this.pos = 0;
                this.end = -1;
                this.unread = 0;
                throw ex;
            }
        }
        
        int currentBlockRemaining() {
            if (this.blkmode) {
                return (this.end >= 0) ? (this.end - this.pos + this.unread) : 0;
            }
            throw new IllegalStateException();
        }
        
        int peek() throws IOException {
            if (this.blkmode) {
                if (this.pos == this.end) {
                    this.refill();
                }
                return (this.end >= 0) ? (this.buf[this.pos] & 0xFF) : -1;
            }
            return this.in.peek();
        }
        
        byte peekByte() throws IOException {
            final int peek = this.peek();
            if (peek < 0) {
                throw new EOFException();
            }
            return (byte)peek;
        }
        
        @Override
        public int read() throws IOException {
            if (this.blkmode) {
                if (this.pos == this.end) {
                    this.refill();
                }
                return (this.end >= 0) ? (this.buf[this.pos++] & 0xFF) : -1;
            }
            return this.in.read();
        }
        
        @Override
        public int read(final byte[] array, final int n, final int n2) throws IOException {
            return this.read(array, n, n2, false);
        }
        
        @Override
        public long skip(final long n) throws IOException {
            long n2 = n;
            while (n2 > 0L) {
                if (this.blkmode) {
                    if (this.pos == this.end) {
                        this.refill();
                    }
                    if (this.end < 0) {
                        break;
                    }
                    final int n3 = (int)Math.min(n2, this.end - this.pos);
                    n2 -= n3;
                    this.pos += n3;
                }
                else {
                    final int read;
                    if ((read = this.in.read(this.buf, 0, (int)Math.min(n2, 1024L))) < 0) {
                        break;
                    }
                    n2 -= read;
                }
            }
            return n - n2;
        }
        
        @Override
        public int available() throws IOException {
            if (this.blkmode) {
                if (this.pos == this.end && this.unread == 0) {
                    int blockHeader;
                    while ((blockHeader = this.readBlockHeader(false)) == 0) {}
                    switch (blockHeader) {
                        case -2: {
                            break;
                        }
                        case -1: {
                            this.pos = 0;
                            this.end = -1;
                            break;
                        }
                        default: {
                            this.pos = 0;
                            this.end = 0;
                            this.unread = blockHeader;
                            break;
                        }
                    }
                }
                final int n = (this.unread > 0) ? Math.min(this.in.available(), this.unread) : 0;
                return (this.end >= 0) ? (this.end - this.pos + n) : 0;
            }
            return this.in.available();
        }
        
        @Override
        public void close() throws IOException {
            if (this.blkmode) {
                this.pos = 0;
                this.end = -1;
                this.unread = 0;
            }
            this.in.close();
        }
        
        int read(final byte[] array, final int n, final int n2, final boolean b) throws IOException {
            if (n2 == 0) {
                return 0;
            }
            if (this.blkmode) {
                if (this.pos == this.end) {
                    this.refill();
                }
                if (this.end < 0) {
                    return -1;
                }
                final int min = Math.min(n2, this.end - this.pos);
                System.arraycopy(this.buf, this.pos, array, n, min);
                this.pos += min;
                return min;
            }
            else {
                if (b) {
                    final int read = this.in.read(this.buf, 0, Math.min(n2, 1024));
                    if (read > 0) {
                        System.arraycopy(this.buf, 0, array, n, read);
                    }
                    return read;
                }
                return this.in.read(array, n, n2);
            }
        }
        
        @Override
        public void readFully(final byte[] array) throws IOException {
            this.readFully(array, 0, array.length, false);
        }
        
        @Override
        public void readFully(final byte[] array, final int n, final int n2) throws IOException {
            this.readFully(array, n, n2, false);
        }
        
        public void readFully(final byte[] array, int n, int i, final boolean b) throws IOException {
            while (i > 0) {
                final int read = this.read(array, n, i, b);
                if (read < 0) {
                    throw new EOFException();
                }
                n += read;
                i -= read;
            }
        }
        
        @Override
        public int skipBytes(final int n) throws IOException {
            return this.din.skipBytes(n);
        }
        
        @Override
        public boolean readBoolean() throws IOException {
            final int read = this.read();
            if (read < 0) {
                throw new EOFException();
            }
            return read != 0;
        }
        
        @Override
        public byte readByte() throws IOException {
            final int read = this.read();
            if (read < 0) {
                throw new EOFException();
            }
            return (byte)read;
        }
        
        @Override
        public int readUnsignedByte() throws IOException {
            final int read = this.read();
            if (read < 0) {
                throw new EOFException();
            }
            return read;
        }
        
        @Override
        public char readChar() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 2);
            }
            else if (this.end - this.pos < 2) {
                return this.din.readChar();
            }
            final char char1 = Bits.getChar(this.buf, this.pos);
            this.pos += 2;
            return char1;
        }
        
        @Override
        public short readShort() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 2);
            }
            else if (this.end - this.pos < 2) {
                return this.din.readShort();
            }
            final short short1 = Bits.getShort(this.buf, this.pos);
            this.pos += 2;
            return short1;
        }
        
        @Override
        public int readUnsignedShort() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 2);
            }
            else if (this.end - this.pos < 2) {
                return this.din.readUnsignedShort();
            }
            final int n = Bits.getShort(this.buf, this.pos) & 0xFFFF;
            this.pos += 2;
            return n;
        }
        
        @Override
        public int readInt() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 4);
            }
            else if (this.end - this.pos < 4) {
                return this.din.readInt();
            }
            final int int1 = Bits.getInt(this.buf, this.pos);
            this.pos += 4;
            return int1;
        }
        
        @Override
        public float readFloat() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 4);
            }
            else if (this.end - this.pos < 4) {
                return this.din.readFloat();
            }
            final float float1 = Bits.getFloat(this.buf, this.pos);
            this.pos += 4;
            return float1;
        }
        
        @Override
        public long readLong() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 8);
            }
            else if (this.end - this.pos < 8) {
                return this.din.readLong();
            }
            final long long1 = Bits.getLong(this.buf, this.pos);
            this.pos += 8;
            return long1;
        }
        
        @Override
        public double readDouble() throws IOException {
            if (!this.blkmode) {
                this.pos = 0;
                this.in.readFully(this.buf, 0, 8);
            }
            else if (this.end - this.pos < 8) {
                return this.din.readDouble();
            }
            final double double1 = Bits.getDouble(this.buf, this.pos);
            this.pos += 8;
            return double1;
        }
        
        @Override
        public String readUTF() throws IOException {
            return this.readUTFBody(this.readUnsignedShort());
        }
        
        @Override
        public String readLine() throws IOException {
            return this.din.readLine();
        }
        
        void readBooleans(final boolean[] array, int i, final int n) throws IOException {
            final int n2 = i + n;
            while (i < n2) {
                int min2;
                if (!this.blkmode) {
                    final int min = Math.min(n2 - i, 1024);
                    this.in.readFully(this.buf, 0, min);
                    min2 = i + min;
                    this.pos = 0;
                }
                else {
                    if (this.end - this.pos < 1) {
                        array[i++] = this.din.readBoolean();
                        continue;
                    }
                    min2 = Math.min(n2, i + this.end - this.pos);
                }
                while (i < min2) {
                    array[i++] = Bits.getBoolean(this.buf, this.pos++);
                }
            }
        }
        
        void readChars(final char[] array, int i, final int n) throws IOException {
            final int n2 = i + n;
            while (i < n2) {
                int min2;
                if (!this.blkmode) {
                    final int min = Math.min(n2 - i, 512);
                    this.in.readFully(this.buf, 0, min << 1);
                    min2 = i + min;
                    this.pos = 0;
                }
                else {
                    if (this.end - this.pos < 2) {
                        array[i++] = this.din.readChar();
                        continue;
                    }
                    min2 = Math.min(n2, i + (this.end - this.pos >> 1));
                }
                while (i < min2) {
                    array[i++] = Bits.getChar(this.buf, this.pos);
                    this.pos += 2;
                }
            }
        }
        
        void readShorts(final short[] array, int i, final int n) throws IOException {
            final int n2 = i + n;
            while (i < n2) {
                int min2;
                if (!this.blkmode) {
                    final int min = Math.min(n2 - i, 512);
                    this.in.readFully(this.buf, 0, min << 1);
                    min2 = i + min;
                    this.pos = 0;
                }
                else {
                    if (this.end - this.pos < 2) {
                        array[i++] = this.din.readShort();
                        continue;
                    }
                    min2 = Math.min(n2, i + (this.end - this.pos >> 1));
                }
                while (i < min2) {
                    array[i++] = Bits.getShort(this.buf, this.pos);
                    this.pos += 2;
                }
            }
        }
        
        void readInts(final int[] array, int i, final int n) throws IOException {
            final int n2 = i + n;
            while (i < n2) {
                int min2;
                if (!this.blkmode) {
                    final int min = Math.min(n2 - i, 256);
                    this.in.readFully(this.buf, 0, min << 2);
                    min2 = i + min;
                    this.pos = 0;
                }
                else {
                    if (this.end - this.pos < 4) {
                        array[i++] = this.din.readInt();
                        continue;
                    }
                    min2 = Math.min(n2, i + (this.end - this.pos >> 2));
                }
                while (i < min2) {
                    array[i++] = Bits.getInt(this.buf, this.pos);
                    this.pos += 4;
                }
            }
        }
        
        void readFloats(final float[] array, int i, final int n) throws IOException {
            final int n2 = i + n;
            while (i < n2) {
                int n3;
                if (!this.blkmode) {
                    n3 = Math.min(n2 - i, 256);
                    this.in.readFully(this.buf, 0, n3 << 2);
                    this.pos = 0;
                }
                else {
                    if (this.end - this.pos < 4) {
                        array[i++] = this.din.readFloat();
                        continue;
                    }
                    n3 = Math.min(n2 - i, this.end - this.pos >> 2);
                }
                bytesToFloats(this.buf, this.pos, array, i, n3);
                i += n3;
                this.pos += n3 << 2;
            }
        }
        
        void readLongs(final long[] array, int i, final int n) throws IOException {
            final int n2 = i + n;
            while (i < n2) {
                int min2;
                if (!this.blkmode) {
                    final int min = Math.min(n2 - i, 128);
                    this.in.readFully(this.buf, 0, min << 3);
                    min2 = i + min;
                    this.pos = 0;
                }
                else {
                    if (this.end - this.pos < 8) {
                        array[i++] = this.din.readLong();
                        continue;
                    }
                    min2 = Math.min(n2, i + (this.end - this.pos >> 3));
                }
                while (i < min2) {
                    array[i++] = Bits.getLong(this.buf, this.pos);
                    this.pos += 8;
                }
            }
        }
        
        void readDoubles(final double[] array, int i, final int n) throws IOException {
            final int n2 = i + n;
            while (i < n2) {
                int n3;
                if (!this.blkmode) {
                    n3 = Math.min(n2 - i, 128);
                    this.in.readFully(this.buf, 0, n3 << 3);
                    this.pos = 0;
                }
                else {
                    if (this.end - this.pos < 8) {
                        array[i++] = this.din.readDouble();
                        continue;
                    }
                    n3 = Math.min(n2 - i, this.end - this.pos >> 3);
                }
                bytesToDoubles(this.buf, this.pos, array, i, n3);
                i += n3;
                this.pos += n3 << 3;
            }
        }
        
        String readLongUTF() throws IOException {
            return this.readUTFBody(this.readLong());
        }
        
        private String readUTFBody(long n) throws IOException {
            StringBuilder sb;
            if (n > 0L && n < 2147483647L) {
                sb = new StringBuilder(Math.min((int)n, 65535));
            }
            else {
                sb = new StringBuilder();
            }
            if (!this.blkmode) {
                final boolean b = false;
                this.pos = (b ? 1 : 0);
                this.end = (b ? 1 : 0);
            }
            while (n > 0L) {
                final int n2 = this.end - this.pos;
                if (n2 >= 3 || n2 == n) {
                    n -= this.readUTFSpan(sb, n);
                }
                else if (this.blkmode) {
                    n -= this.readUTFChar(sb, n);
                }
                else {
                    if (n2 > 0) {
                        System.arraycopy(this.buf, this.pos, this.buf, 0, n2);
                    }
                    this.pos = 0;
                    this.end = (int)Math.min(1024L, n);
                    this.in.readFully(this.buf, n2, this.end - n2);
                }
            }
            return sb.toString();
        }
        
        private long readUTFSpan(final StringBuilder sb, final long n) throws IOException {
            int n2 = 0;
            final int pos = this.pos;
            final int min = Math.min(this.end - this.pos, 256);
            final int n3 = this.pos + ((n > min) ? (min - 2) : ((int)n));
            boolean b = false;
            try {
                while (this.pos < n3) {
                    final int n4 = this.buf[this.pos++] & 0xFF;
                    switch (n4 >> 4) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7: {
                            this.cbuf[n2++] = (char)n4;
                            continue;
                        }
                        case 12:
                        case 13: {
                            final byte b2 = this.buf[this.pos++];
                            if ((b2 & 0xC0) != 0x80) {
                                throw new UTFDataFormatException();
                            }
                            this.cbuf[n2++] = (char)((n4 & 0x1F) << 6 | (b2 & 0x3F) << 0);
                            continue;
                        }
                        case 14: {
                            final byte b3 = this.buf[this.pos + 1];
                            final byte b4 = this.buf[this.pos + 0];
                            this.pos += 2;
                            if ((b4 & 0xC0) != 0x80 || (b3 & 0xC0) != 0x80) {
                                throw new UTFDataFormatException();
                            }
                            this.cbuf[n2++] = (char)((n4 & 0xF) << 12 | (b4 & 0x3F) << 6 | (b3 & 0x3F) << 0);
                            continue;
                        }
                        default: {
                            throw new UTFDataFormatException();
                        }
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                b = true;
            }
            finally {
                if (b || this.pos - pos > n) {
                    this.pos = pos + (int)n;
                    throw new UTFDataFormatException();
                }
            }
            sb.append(this.cbuf, 0, n2);
            return this.pos - pos;
        }
        
        private int readUTFChar(final StringBuilder sb, final long n) throws IOException {
            final int n2 = this.readByte() & 0xFF;
            switch (n2 >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7: {
                    sb.append((char)n2);
                    return 1;
                }
                case 12:
                case 13: {
                    if (n < 2L) {
                        throw new UTFDataFormatException();
                    }
                    final byte byte1 = this.readByte();
                    if ((byte1 & 0xC0) != 0x80) {
                        throw new UTFDataFormatException();
                    }
                    sb.append((char)((n2 & 0x1F) << 6 | (byte1 & 0x3F) << 0));
                    return 2;
                }
                case 14: {
                    if (n < 3L) {
                        if (n == 2L) {
                            this.readByte();
                        }
                        throw new UTFDataFormatException();
                    }
                    final byte byte2 = this.readByte();
                    final byte byte3 = this.readByte();
                    if ((byte2 & 0xC0) != 0x80 || (byte3 & 0xC0) != 0x80) {
                        throw new UTFDataFormatException();
                    }
                    sb.append((char)((n2 & 0xF) << 12 | (byte2 & 0x3F) << 6 | (byte3 & 0x3F) << 0));
                    return 3;
                }
                default: {
                    throw new UTFDataFormatException();
                }
            }
        }
        
        long getBytesRead() {
            return this.in.getBytesRead();
        }
    }
    
    private static class PeekInputStream extends InputStream
    {
        private final InputStream in;
        private int peekb;
        private long totalBytesRead;
        
        PeekInputStream(final InputStream in) {
            this.peekb = -1;
            this.totalBytesRead = 0L;
            this.in = in;
        }
        
        int peek() throws IOException {
            if (this.peekb >= 0) {
                return this.peekb;
            }
            this.peekb = this.in.read();
            this.totalBytesRead += ((this.peekb >= 0) ? 1 : 0);
            return this.peekb;
        }
        
        @Override
        public int read() throws IOException {
            if (this.peekb >= 0) {
                final int peekb = this.peekb;
                this.peekb = -1;
                return peekb;
            }
            final int read = this.in.read();
            this.totalBytesRead += ((read >= 0) ? 1 : 0);
            return read;
        }
        
        @Override
        public int read(final byte[] array, int n, int n2) throws IOException {
            if (n2 == 0) {
                return 0;
            }
            if (this.peekb < 0) {
                final int read = this.in.read(array, n, n2);
                this.totalBytesRead += ((read >= 0) ? read : 0L);
                return read;
            }
            array[n++] = (byte)this.peekb;
            --n2;
            this.peekb = -1;
            final int read2 = this.in.read(array, n, n2);
            this.totalBytesRead += ((read2 >= 0) ? read2 : 0L);
            return (read2 >= 0) ? (read2 + 1) : 1;
        }
        
        void readFully(final byte[] array, final int n, final int n2) throws IOException {
            int read;
            for (int i = 0; i < n2; i += read) {
                read = this.read(array, n + i, n2 - i);
                if (read < 0) {
                    throw new EOFException();
                }
            }
        }
        
        @Override
        public long skip(long n) throws IOException {
            if (n <= 0L) {
                return 0L;
            }
            int n2 = 0;
            if (this.peekb >= 0) {
                this.peekb = -1;
                ++n2;
                --n;
            }
            n = n2 + this.in.skip(n);
            this.totalBytesRead += n;
            return n;
        }
        
        @Override
        public int available() throws IOException {
            return this.in.available() + ((this.peekb >= 0) ? 1 : 0);
        }
        
        @Override
        public void close() throws IOException {
            this.in.close();
        }
        
        public long getBytesRead() {
            return this.totalBytesRead;
        }
    }
    
    private static class Caches
    {
        static final ConcurrentMap<ObjectStreamClass.WeakClassKey, Boolean> subclassAudits;
        static final ReferenceQueue<Class<?>> subclassAuditsQueue;
        static final boolean SET_FILTER_AFTER_READ;
        
        private static boolean privilegedGetProperty(final String s) {
            if (System.getSecurityManager() == null) {
                return Boolean.getBoolean(s);
            }
            return AccessController.doPrivileged((PrivilegedAction<Boolean>)new GetBooleanAction(s));
        }
        
        static {
            subclassAudits = new ConcurrentHashMap<ObjectStreamClass.WeakClassKey, Boolean>();
            subclassAuditsQueue = new ReferenceQueue<Class<?>>();
            SET_FILTER_AFTER_READ = privilegedGetProperty("jdk.serialSetFilterAfterRead");
        }
    }
    
    static class FilterValues implements ObjectInputFilter.FilterInfo
    {
        final Class<?> clazz;
        final long arrayLength;
        final long totalObjectRefs;
        final long depth;
        final long streamBytes;
        
        public FilterValues(final Class<?> clazz, final long arrayLength, final long totalObjectRefs, final long depth, final long streamBytes) {
            this.clazz = clazz;
            this.arrayLength = arrayLength;
            this.totalObjectRefs = totalObjectRefs;
            this.depth = depth;
            this.streamBytes = streamBytes;
        }
        
        @Override
        public Class<?> serialClass() {
            return this.clazz;
        }
        
        @Override
        public long arrayLength() {
            return this.arrayLength;
        }
        
        @Override
        public long references() {
            return this.totalObjectRefs;
        }
        
        @Override
        public long depth() {
            return this.depth;
        }
        
        @Override
        public long streamBytes() {
            return this.streamBytes;
        }
    }
    
    public abstract static class GetField
    {
        public abstract ObjectStreamClass getObjectStreamClass();
        
        public abstract boolean defaulted(final String p0) throws IOException;
        
        public abstract boolean get(final String p0, final boolean p1) throws IOException;
        
        public abstract byte get(final String p0, final byte p1) throws IOException;
        
        public abstract char get(final String p0, final char p1) throws IOException;
        
        public abstract short get(final String p0, final short p1) throws IOException;
        
        public abstract int get(final String p0, final int p1) throws IOException;
        
        public abstract long get(final String p0, final long p1) throws IOException;
        
        public abstract float get(final String p0, final float p1) throws IOException;
        
        public abstract double get(final String p0, final double p1) throws IOException;
        
        public abstract Object get(final String p0, final Object p1) throws IOException;
    }
    
    private class GetFieldImpl extends GetField
    {
        private final ObjectStreamClass desc;
        private final byte[] primVals;
        private final Object[] objVals;
        private final int[] objHandles;
        
        GetFieldImpl(final ObjectStreamClass desc) {
            this.desc = desc;
            this.primVals = new byte[desc.getPrimDataSize()];
            this.objVals = new Object[desc.getNumObjFields()];
            this.objHandles = new int[this.objVals.length];
        }
        
        @Override
        public ObjectStreamClass getObjectStreamClass() {
            return this.desc;
        }
        
        @Override
        public boolean defaulted(final String s) throws IOException {
            return this.getFieldOffset(s, null) < 0;
        }
        
        @Override
        public boolean get(final String s, final boolean b) throws IOException {
            final int fieldOffset = this.getFieldOffset(s, Boolean.TYPE);
            return (fieldOffset >= 0) ? Bits.getBoolean(this.primVals, fieldOffset) : b;
        }
        
        @Override
        public byte get(final String s, final byte b) throws IOException {
            final int fieldOffset = this.getFieldOffset(s, Byte.TYPE);
            return (fieldOffset >= 0) ? this.primVals[fieldOffset] : b;
        }
        
        @Override
        public char get(final String s, final char c) throws IOException {
            final int fieldOffset = this.getFieldOffset(s, Character.TYPE);
            return (fieldOffset >= 0) ? Bits.getChar(this.primVals, fieldOffset) : c;
        }
        
        @Override
        public short get(final String s, final short n) throws IOException {
            final int fieldOffset = this.getFieldOffset(s, Short.TYPE);
            return (fieldOffset >= 0) ? Bits.getShort(this.primVals, fieldOffset) : n;
        }
        
        @Override
        public int get(final String s, final int n) throws IOException {
            final int fieldOffset = this.getFieldOffset(s, Integer.TYPE);
            return (fieldOffset >= 0) ? Bits.getInt(this.primVals, fieldOffset) : n;
        }
        
        @Override
        public float get(final String s, final float n) throws IOException {
            final int fieldOffset = this.getFieldOffset(s, Float.TYPE);
            return (fieldOffset >= 0) ? Bits.getFloat(this.primVals, fieldOffset) : n;
        }
        
        @Override
        public long get(final String s, final long n) throws IOException {
            final int fieldOffset = this.getFieldOffset(s, Long.TYPE);
            return (fieldOffset >= 0) ? Bits.getLong(this.primVals, fieldOffset) : n;
        }
        
        @Override
        public double get(final String s, final double n) throws IOException {
            final int fieldOffset = this.getFieldOffset(s, Double.TYPE);
            return (fieldOffset >= 0) ? Bits.getDouble(this.primVals, fieldOffset) : n;
        }
        
        @Override
        public Object get(final String s, final Object o) throws IOException {
            final int fieldOffset = this.getFieldOffset(s, Object.class);
            if (fieldOffset >= 0) {
                final int n = this.objHandles[fieldOffset];
                ObjectInputStream.this.handles.markDependency(ObjectInputStream.this.passHandle, n);
                return (ObjectInputStream.this.handles.lookupException(n) == null) ? this.objVals[fieldOffset] : null;
            }
            return o;
        }
        
        void readFields() throws IOException {
            ObjectInputStream.this.bin.readFully(this.primVals, 0, this.primVals.length, false);
            final int access$500 = ObjectInputStream.this.passHandle;
            final ObjectStreamField[] fields = this.desc.getFields(false);
            final int n = fields.length - this.objVals.length;
            for (int i = 0; i < this.objVals.length; ++i) {
                this.objVals[i] = ObjectInputStream.this.readObject0(Object.class, fields[n + i].isUnshared());
                this.objHandles[i] = ObjectInputStream.this.passHandle;
            }
            ObjectInputStream.this.passHandle = access$500;
        }
        
        private int getFieldOffset(final String s, final Class<?> clazz) {
            final ObjectStreamField field = this.desc.getField(s, clazz);
            if (field != null) {
                return field.getOffset();
            }
            if (this.desc.getLocalDesc().getField(s, clazz) != null) {
                return -1;
            }
            throw new IllegalArgumentException("no such field " + s + " with type " + clazz);
        }
    }
    
    private static class HandleTable
    {
        private static final byte STATUS_OK = 1;
        private static final byte STATUS_UNKNOWN = 2;
        private static final byte STATUS_EXCEPTION = 3;
        byte[] status;
        Object[] entries;
        HandleList[] deps;
        int lowDep;
        int size;
        
        HandleTable(final int n) {
            this.lowDep = -1;
            this.size = 0;
            this.status = new byte[n];
            this.entries = new Object[n];
            this.deps = new HandleList[n];
        }
        
        int assign(final Object o) {
            if (this.size >= this.entries.length) {
                this.grow();
            }
            this.status[this.size] = 2;
            this.entries[this.size] = o;
            return this.size++;
        }
        
        void markDependency(final int n, final int lowDep) {
            if (n == -1 || lowDep == -1) {
                return;
            }
            Label_0170: {
                switch (this.status[n]) {
                    case 2: {
                        switch (this.status[lowDep]) {
                            case 1: {
                                break Label_0170;
                            }
                            case 3: {
                                this.markException(n, (ClassNotFoundException)this.entries[lowDep]);
                                break Label_0170;
                            }
                            case 2: {
                                if (this.deps[lowDep] == null) {
                                    this.deps[lowDep] = new HandleList();
                                }
                                this.deps[lowDep].add(n);
                                if (this.lowDep < 0 || this.lowDep > lowDep) {
                                    this.lowDep = lowDep;
                                    break Label_0170;
                                }
                                break Label_0170;
                            }
                            default: {
                                throw new InternalError();
                            }
                        }
                        break;
                    }
                    case 3: {
                        break;
                    }
                    default: {
                        throw new InternalError();
                    }
                }
            }
        }
        
        void markException(final int n, final ClassNotFoundException ex) {
            switch (this.status[n]) {
                case 2: {
                    this.status[n] = 3;
                    this.entries[n] = ex;
                    final HandleList list = this.deps[n];
                    if (list != null) {
                        for (int size = list.size(), i = 0; i < size; ++i) {
                            this.markException(list.get(i), ex);
                        }
                        this.deps[n] = null;
                        break;
                    }
                    break;
                }
                case 3: {
                    break;
                }
                default: {
                    throw new InternalError();
                }
            }
        }
        
        void finish(final int n) {
            int size;
            if (this.lowDep < 0) {
                size = n + 1;
            }
            else {
                if (this.lowDep < n) {
                    return;
                }
                size = this.size;
                this.lowDep = -1;
            }
            for (int i = n; i < size; ++i) {
                switch (this.status[i]) {
                    case 2: {
                        this.status[i] = 1;
                        this.deps[i] = null;
                        break;
                    }
                    case 1:
                    case 3: {
                        break;
                    }
                    default: {
                        throw new InternalError();
                    }
                }
            }
        }
        
        void setObject(final int n, final Object o) {
            switch (this.status[n]) {
                case 1:
                case 2: {
                    this.entries[n] = o;
                    break;
                }
                case 3: {
                    break;
                }
                default: {
                    throw new InternalError();
                }
            }
        }
        
        Object lookupObject(final int n) {
            return (n != -1 && this.status[n] != 3) ? this.entries[n] : null;
        }
        
        ClassNotFoundException lookupException(final int n) {
            return (n != -1 && this.status[n] == 3) ? ((ClassNotFoundException)this.entries[n]) : null;
        }
        
        void clear() {
            Arrays.fill(this.status, 0, this.size, (byte)0);
            Arrays.fill(this.entries, 0, this.size, null);
            Arrays.fill(this.deps, 0, this.size, null);
            this.lowDep = -1;
            this.size = 0;
        }
        
        int size() {
            return this.size;
        }
        
        private void grow() {
            final int n = (this.entries.length << 1) + 1;
            final byte[] status = new byte[n];
            final Object[] entries = new Object[n];
            final HandleList[] deps = new HandleList[n];
            System.arraycopy(this.status, 0, status, 0, this.size);
            System.arraycopy(this.entries, 0, entries, 0, this.size);
            System.arraycopy(this.deps, 0, deps, 0, this.size);
            this.status = status;
            this.entries = entries;
            this.deps = deps;
        }
        
        private static class HandleList
        {
            private int[] list;
            private int size;
            
            public HandleList() {
                this.list = new int[4];
                this.size = 0;
            }
            
            public void add(final int n) {
                if (this.size >= this.list.length) {
                    final int[] list = new int[this.list.length << 1];
                    System.arraycopy(this.list, 0, list, 0, this.list.length);
                    this.list = list;
                }
                this.list[this.size++] = n;
            }
            
            public int get(final int n) {
                if (n >= this.size) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                return this.list[n];
            }
            
            public int size() {
                return this.size;
            }
        }
    }
    
    private static class Logging
    {
        private static final PlatformLogger traceLogger;
        private static final PlatformLogger infoLogger;
        
        static {
            final PlatformLogger logger = PlatformLogger.getLogger("java.io.serialization");
            infoLogger = ((logger != null && logger.isLoggable(PlatformLogger.Level.INFO)) ? logger : null);
            traceLogger = ((logger != null && logger.isLoggable(PlatformLogger.Level.FINER)) ? logger : null);
        }
    }
    
    private static class ValidationList
    {
        private Callback list;
        
        void register(final ObjectInputValidation objectInputValidation, final int n) throws InvalidObjectException {
            if (objectInputValidation == null) {
                throw new InvalidObjectException("null callback");
            }
            Callback callback = null;
            Callback callback2;
            for (callback2 = this.list; callback2 != null && n < callback2.priority; callback2 = callback2.next) {
                callback = callback2;
            }
            final AccessControlContext context = AccessController.getContext();
            if (callback != null) {
                callback.next = new Callback(objectInputValidation, n, callback2, context);
            }
            else {
                this.list = new Callback(objectInputValidation, n, this.list, context);
            }
        }
        
        void doCallbacks() throws InvalidObjectException {
            try {
                while (this.list != null) {
                    AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction<Void>() {
                        @Override
                        public Void run() throws InvalidObjectException {
                            ValidationList.this.list.obj.validateObject();
                            return null;
                        }
                    }, this.list.acc);
                    this.list = this.list.next;
                }
            }
            catch (PrivilegedActionException ex) {
                this.list = null;
                throw (InvalidObjectException)ex.getException();
            }
        }
        
        public void clear() {
            this.list = null;
        }
        
        private static class Callback
        {
            final ObjectInputValidation obj;
            final int priority;
            Callback next;
            final AccessControlContext acc;
            
            Callback(final ObjectInputValidation obj, final int priority, final Callback next, final AccessControlContext acc) {
                this.obj = obj;
                this.priority = priority;
                this.next = next;
                this.acc = acc;
            }
        }
    }
}
