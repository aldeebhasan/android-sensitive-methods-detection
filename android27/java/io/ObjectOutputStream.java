package java.io;

import java.security.*;
import sun.reflect.misc.*;
import sun.security.action.*;
import java.lang.ref.*;
import java.util.concurrent.*;
import java.util.*;

public class ObjectOutputStream extends OutputStream implements ObjectOutput, ObjectStreamConstants
{
    private final BlockDataOutputStream bout;
    private final HandleTable handles;
    private final ReplaceTable subs;
    private int protocol;
    private int depth;
    private byte[] primVals;
    private final boolean enableOverride;
    private boolean enableReplace;
    private SerialCallbackContext curContext;
    private PutFieldImpl curPut;
    private final DebugTraceInfoStack debugInfoStack;
    private static final boolean extendedDebugInfo;
    
    public ObjectOutputStream(final OutputStream outputStream) throws IOException {
        this.protocol = 2;
        this.verifySubclass();
        this.bout = new BlockDataOutputStream(outputStream);
        this.handles = new HandleTable(10, 3.0f);
        this.subs = new ReplaceTable(10, 3.0f);
        this.enableOverride = false;
        this.writeStreamHeader();
        this.bout.setBlockDataMode(true);
        if (ObjectOutputStream.extendedDebugInfo) {
            this.debugInfoStack = new DebugTraceInfoStack();
        }
        else {
            this.debugInfoStack = null;
        }
    }
    
    protected ObjectOutputStream() throws IOException, SecurityException {
        this.protocol = 2;
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(ObjectOutputStream.SUBCLASS_IMPLEMENTATION_PERMISSION);
        }
        this.bout = null;
        this.handles = null;
        this.subs = null;
        this.enableOverride = true;
        this.debugInfoStack = null;
    }
    
    public void useProtocolVersion(final int protocol) throws IOException {
        if (this.handles.size() != 0) {
            throw new IllegalStateException("stream non-empty");
        }
        switch (protocol) {
            case 1:
            case 2: {
                this.protocol = protocol;
            }
            default: {
                throw new IllegalArgumentException("unknown version: " + protocol);
            }
        }
    }
    
    @Override
    public final void writeObject(final Object o) throws IOException {
        if (this.enableOverride) {
            this.writeObjectOverride(o);
            return;
        }
        try {
            this.writeObject0(o, false);
        }
        catch (IOException ex) {
            if (this.depth == 0) {
                this.writeFatalException(ex);
            }
            throw ex;
        }
    }
    
    protected void writeObjectOverride(final Object o) throws IOException {
    }
    
    public void writeUnshared(final Object o) throws IOException {
        try {
            this.writeObject0(o, true);
        }
        catch (IOException ex) {
            if (this.depth == 0) {
                this.writeFatalException(ex);
            }
            throw ex;
        }
    }
    
    public void defaultWriteObject() throws IOException {
        final SerialCallbackContext curContext = this.curContext;
        if (curContext == null) {
            throw new NotActiveException("not in call to writeObject");
        }
        final Object obj = curContext.getObj();
        final ObjectStreamClass desc = curContext.getDesc();
        this.bout.setBlockDataMode(false);
        this.defaultWriteFields(obj, desc);
        this.bout.setBlockDataMode(true);
    }
    
    public PutField putFields() throws IOException {
        if (this.curPut == null) {
            final SerialCallbackContext curContext = this.curContext;
            if (curContext == null) {
                throw new NotActiveException("not in call to writeObject");
            }
            curContext.getObj();
            this.curPut = new PutFieldImpl(curContext.getDesc());
        }
        return this.curPut;
    }
    
    public void writeFields() throws IOException {
        if (this.curPut == null) {
            throw new NotActiveException("no current PutField object");
        }
        this.bout.setBlockDataMode(false);
        this.curPut.writeFields();
        this.bout.setBlockDataMode(true);
    }
    
    public void reset() throws IOException {
        if (this.depth != 0) {
            throw new IOException("stream active");
        }
        this.bout.setBlockDataMode(false);
        this.bout.writeByte(121);
        this.clear();
        this.bout.setBlockDataMode(true);
    }
    
    protected void annotateClass(final Class<?> clazz) throws IOException {
    }
    
    protected void annotateProxyClass(final Class<?> clazz) throws IOException {
    }
    
    protected Object replaceObject(final Object o) throws IOException {
        return o;
    }
    
    protected boolean enableReplaceObject(final boolean enableReplace) throws SecurityException {
        if (enableReplace == this.enableReplace) {
            return enableReplace;
        }
        if (enableReplace) {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(ObjectOutputStream.SUBSTITUTION_PERMISSION);
            }
        }
        this.enableReplace = enableReplace;
        return !this.enableReplace;
    }
    
    protected void writeStreamHeader() throws IOException {
        this.bout.writeShort(-21267);
        this.bout.writeShort(5);
    }
    
    protected void writeClassDescriptor(final ObjectStreamClass objectStreamClass) throws IOException {
        objectStreamClass.writeNonProxy(this);
    }
    
    @Override
    public void write(final int n) throws IOException {
        this.bout.write(n);
    }
    
    @Override
    public void write(final byte[] array) throws IOException {
        this.bout.write(array, 0, array.length, false);
    }
    
    @Override
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        if (array == null) {
            throw new NullPointerException();
        }
        final int n3 = n + n2;
        if (n < 0 || n2 < 0 || n3 > array.length || n3 < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.bout.write(array, n, n2, false);
    }
    
    @Override
    public void flush() throws IOException {
        this.bout.flush();
    }
    
    protected void drain() throws IOException {
        this.bout.drain();
    }
    
    @Override
    public void close() throws IOException {
        this.flush();
        this.clear();
        this.bout.close();
    }
    
    @Override
    public void writeBoolean(final boolean b) throws IOException {
        this.bout.writeBoolean(b);
    }
    
    @Override
    public void writeByte(final int n) throws IOException {
        this.bout.writeByte(n);
    }
    
    @Override
    public void writeShort(final int n) throws IOException {
        this.bout.writeShort(n);
    }
    
    @Override
    public void writeChar(final int n) throws IOException {
        this.bout.writeChar(n);
    }
    
    @Override
    public void writeInt(final int n) throws IOException {
        this.bout.writeInt(n);
    }
    
    @Override
    public void writeLong(final long n) throws IOException {
        this.bout.writeLong(n);
    }
    
    @Override
    public void writeFloat(final float n) throws IOException {
        this.bout.writeFloat(n);
    }
    
    @Override
    public void writeDouble(final double n) throws IOException {
        this.bout.writeDouble(n);
    }
    
    @Override
    public void writeBytes(final String s) throws IOException {
        this.bout.writeBytes(s);
    }
    
    @Override
    public void writeChars(final String s) throws IOException {
        this.bout.writeChars(s);
    }
    
    @Override
    public void writeUTF(final String s) throws IOException {
        this.bout.writeUTF(s);
    }
    
    int getProtocolVersion() {
        return this.protocol;
    }
    
    void writeTypeString(final String s) throws IOException {
        if (s == null) {
            this.writeNull();
        }
        else {
            final int lookup;
            if ((lookup = this.handles.lookup(s)) != -1) {
                this.writeHandle(lookup);
            }
            else {
                this.writeString(s, false);
            }
        }
    }
    
    private void verifySubclass() {
        final Class<? extends ObjectOutputStream> class1 = this.getClass();
        if (class1 == ObjectOutputStream.class) {
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
        securityManager.checkPermission(ObjectOutputStream.SUBCLASS_IMPLEMENTATION_PERMISSION);
    }
    
    private static boolean auditSubclass(final Class<?> clazz) {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>)new PrivilegedAction<Boolean>() {
            @Override
            public Boolean run() {
                Class<? super ObjectOutputStream> clazz = (Class<? super ObjectOutputStream>)clazz;
                while (clazz != ObjectOutputStream.class) {
                    try {
                        clazz.getDeclaredMethod("writeUnshared", Object.class);
                        return Boolean.FALSE;
                    }
                    catch (NoSuchMethodException ex) {
                        try {
                            clazz.getDeclaredMethod("putFields", (Class<?>[])null);
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
        this.subs.clear();
        this.handles.clear();
    }
    
    private void writeObject0(Object o, final boolean b) throws IOException {
        final boolean setBlockDataMode = this.bout.setBlockDataMode(false);
        ++this.depth;
        try {
            if ((o = this.subs.lookup(o)) == null) {
                this.writeNull();
                return;
            }
            final int lookup;
            if (!b && (lookup = this.handles.lookup(o)) != -1) {
                this.writeHandle(lookup);
                return;
            }
            if (o instanceof Class) {
                this.writeClass((Class<?>)o, b);
                return;
            }
            if (o instanceof ObjectStreamClass) {
                this.writeClassDesc((ObjectStreamClass)o, b);
                return;
            }
            final Object o2 = o;
            Class<?> clazz = o.getClass();
            ObjectStreamClass objectStreamClass;
            while (true) {
                objectStreamClass = ObjectStreamClass.lookup(clazz, true);
                final Class<?> class1;
                if (!objectStreamClass.hasWriteReplaceMethod() || (o = objectStreamClass.invokeWriteReplace(o)) == null || (class1 = o.getClass()) == clazz) {
                    break;
                }
                clazz = class1;
            }
            if (this.enableReplace) {
                final Object replaceObject = this.replaceObject(o);
                if (replaceObject != o && replaceObject != null) {
                    clazz = replaceObject.getClass();
                    objectStreamClass = ObjectStreamClass.lookup(clazz, true);
                }
                o = replaceObject;
            }
            if (o != o2) {
                this.subs.assign(o2, o);
                if (o == null) {
                    this.writeNull();
                    return;
                }
                final int lookup2;
                if (!b && (lookup2 = this.handles.lookup(o)) != -1) {
                    this.writeHandle(lookup2);
                    return;
                }
                if (o instanceof Class) {
                    this.writeClass((Class<?>)o, b);
                    return;
                }
                if (o instanceof ObjectStreamClass) {
                    this.writeClassDesc((ObjectStreamClass)o, b);
                    return;
                }
            }
            if (o instanceof String) {
                this.writeString((String)o, b);
            }
            else if (clazz.isArray()) {
                this.writeArray(o, objectStreamClass, b);
            }
            else if (o instanceof Enum) {
                this.writeEnum((Enum<?>)o, objectStreamClass, b);
            }
            else if (o instanceof Serializable) {
                this.writeOrdinaryObject(o, objectStreamClass, b);
            }
            else {
                if (ObjectOutputStream.extendedDebugInfo) {
                    throw new NotSerializableException(clazz.getName() + "\n" + this.debugInfoStack.toString());
                }
                throw new NotSerializableException(clazz.getName());
            }
        }
        finally {
            --this.depth;
            this.bout.setBlockDataMode(setBlockDataMode);
        }
    }
    
    private void writeNull() throws IOException {
        this.bout.writeByte(112);
    }
    
    private void writeHandle(final int n) throws IOException {
        this.bout.writeByte(113);
        this.bout.writeInt(8257536 + n);
    }
    
    private void writeClass(final Class<?> clazz, final boolean b) throws IOException {
        this.bout.writeByte(118);
        this.writeClassDesc(ObjectStreamClass.lookup(clazz, true), false);
        this.handles.assign(b ? null : clazz);
    }
    
    private void writeClassDesc(final ObjectStreamClass objectStreamClass, final boolean b) throws IOException {
        if (objectStreamClass == null) {
            this.writeNull();
        }
        else {
            final int lookup;
            if (!b && (lookup = this.handles.lookup(objectStreamClass)) != -1) {
                this.writeHandle(lookup);
            }
            else if (objectStreamClass.isProxy()) {
                this.writeProxyDesc(objectStreamClass, b);
            }
            else {
                this.writeNonProxyDesc(objectStreamClass, b);
            }
        }
    }
    
    private boolean isCustomSubclass() {
        return this.getClass().getClassLoader() != ObjectOutputStream.class.getClassLoader();
    }
    
    private void writeProxyDesc(final ObjectStreamClass objectStreamClass, final boolean b) throws IOException {
        this.bout.writeByte(125);
        this.handles.assign(b ? null : objectStreamClass);
        final Class<?> forClass = objectStreamClass.forClass();
        final Class[] interfaces = forClass.getInterfaces();
        this.bout.writeInt(interfaces.length);
        for (int i = 0; i < interfaces.length; ++i) {
            this.bout.writeUTF(interfaces[i].getName());
        }
        this.bout.setBlockDataMode(true);
        if (forClass != null && this.isCustomSubclass()) {
            ReflectUtil.checkPackageAccess(forClass);
        }
        this.annotateProxyClass(forClass);
        this.bout.setBlockDataMode(false);
        this.bout.writeByte(120);
        this.writeClassDesc(objectStreamClass.getSuperDesc(), false);
    }
    
    private void writeNonProxyDesc(final ObjectStreamClass objectStreamClass, final boolean b) throws IOException {
        this.bout.writeByte(114);
        this.handles.assign(b ? null : objectStreamClass);
        if (this.protocol == 1) {
            objectStreamClass.writeNonProxy(this);
        }
        else {
            this.writeClassDescriptor(objectStreamClass);
        }
        final Class<?> forClass = objectStreamClass.forClass();
        this.bout.setBlockDataMode(true);
        if (forClass != null && this.isCustomSubclass()) {
            ReflectUtil.checkPackageAccess(forClass);
        }
        this.annotateClass(forClass);
        this.bout.setBlockDataMode(false);
        this.bout.writeByte(120);
        this.writeClassDesc(objectStreamClass.getSuperDesc(), false);
    }
    
    private void writeString(final String s, final boolean b) throws IOException {
        this.handles.assign(b ? null : s);
        final long utfLength = this.bout.getUTFLength(s);
        if (utfLength <= 65535L) {
            this.bout.writeByte(116);
            this.bout.writeUTF(s, utfLength);
        }
        else {
            this.bout.writeByte(124);
            this.bout.writeLongUTF(s, utfLength);
        }
    }
    
    private void writeArray(final Object o, final ObjectStreamClass objectStreamClass, final boolean b) throws IOException {
        this.bout.writeByte(117);
        this.writeClassDesc(objectStreamClass, false);
        this.handles.assign(b ? null : o);
        final Class<?> componentType = objectStreamClass.forClass().getComponentType();
        if (componentType.isPrimitive()) {
            if (componentType == Integer.TYPE) {
                final int[] array = (int[])o;
                this.bout.writeInt(array.length);
                this.bout.writeInts(array, 0, array.length);
            }
            else if (componentType == Byte.TYPE) {
                final byte[] array2 = (byte[])o;
                this.bout.writeInt(array2.length);
                this.bout.write(array2, 0, array2.length, true);
            }
            else if (componentType == Long.TYPE) {
                final long[] array3 = (long[])o;
                this.bout.writeInt(array3.length);
                this.bout.writeLongs(array3, 0, array3.length);
            }
            else if (componentType == Float.TYPE) {
                final float[] array4 = (float[])o;
                this.bout.writeInt(array4.length);
                this.bout.writeFloats(array4, 0, array4.length);
            }
            else if (componentType == Double.TYPE) {
                final double[] array5 = (double[])o;
                this.bout.writeInt(array5.length);
                this.bout.writeDoubles(array5, 0, array5.length);
            }
            else if (componentType == Short.TYPE) {
                final short[] array6 = (short[])o;
                this.bout.writeInt(array6.length);
                this.bout.writeShorts(array6, 0, array6.length);
            }
            else if (componentType == Character.TYPE) {
                final char[] array7 = (char[])o;
                this.bout.writeInt(array7.length);
                this.bout.writeChars(array7, 0, array7.length);
            }
            else {
                if (componentType != Boolean.TYPE) {
                    throw new InternalError();
                }
                final boolean[] array8 = (boolean[])o;
                this.bout.writeInt(array8.length);
                this.bout.writeBooleans(array8, 0, array8.length);
            }
        }
        else {
            final Object[] array9 = (Object[])o;
            final int length = array9.length;
            this.bout.writeInt(length);
            if (ObjectOutputStream.extendedDebugInfo) {
                this.debugInfoStack.push("array (class \"" + o.getClass().getName() + "\", size: " + length + ")");
            }
            try {
                for (int i = 0; i < length; ++i) {
                    if (ObjectOutputStream.extendedDebugInfo) {
                        this.debugInfoStack.push("element of array (index: " + i + ")");
                    }
                    try {
                        this.writeObject0(array9[i], false);
                    }
                    finally {
                        if (ObjectOutputStream.extendedDebugInfo) {
                            this.debugInfoStack.pop();
                        }
                    }
                }
            }
            finally {
                if (ObjectOutputStream.extendedDebugInfo) {
                    this.debugInfoStack.pop();
                }
            }
        }
    }
    
    private void writeEnum(final Enum<?> enum1, final ObjectStreamClass objectStreamClass, final boolean b) throws IOException {
        this.bout.writeByte(126);
        final ObjectStreamClass superDesc = objectStreamClass.getSuperDesc();
        this.writeClassDesc((superDesc.forClass() == Enum.class) ? objectStreamClass : superDesc, false);
        this.handles.assign(b ? null : enum1);
        this.writeString(enum1.name(), false);
    }
    
    private void writeOrdinaryObject(final Object o, final ObjectStreamClass objectStreamClass, final boolean b) throws IOException {
        if (ObjectOutputStream.extendedDebugInfo) {
            this.debugInfoStack.push(((this.depth == 1) ? "root " : "") + "object (class \"" + o.getClass().getName() + "\", " + o.toString() + ")");
        }
        try {
            objectStreamClass.checkSerialize();
            this.bout.writeByte(115);
            this.writeClassDesc(objectStreamClass, false);
            this.handles.assign(b ? null : o);
            if (objectStreamClass.isExternalizable() && !objectStreamClass.isProxy()) {
                this.writeExternalData((Externalizable)o);
            }
            else {
                this.writeSerialData(o, objectStreamClass);
            }
        }
        finally {
            if (ObjectOutputStream.extendedDebugInfo) {
                this.debugInfoStack.pop();
            }
        }
    }
    
    private void writeExternalData(final Externalizable externalizable) throws IOException {
        final PutFieldImpl curPut = this.curPut;
        this.curPut = null;
        if (ObjectOutputStream.extendedDebugInfo) {
            this.debugInfoStack.push("writeExternal data");
        }
        final SerialCallbackContext curContext = this.curContext;
        try {
            this.curContext = null;
            if (this.protocol == 1) {
                externalizable.writeExternal(this);
            }
            else {
                this.bout.setBlockDataMode(true);
                externalizable.writeExternal(this);
                this.bout.setBlockDataMode(false);
                this.bout.writeByte(120);
            }
        }
        finally {
            this.curContext = curContext;
            if (ObjectOutputStream.extendedDebugInfo) {
                this.debugInfoStack.pop();
            }
        }
        this.curPut = curPut;
    }
    
    private void writeSerialData(final Object o, final ObjectStreamClass objectStreamClass) throws IOException {
        final ObjectStreamClass.ClassDataSlot[] classDataLayout = objectStreamClass.getClassDataLayout();
        for (int i = 0; i < classDataLayout.length; ++i) {
            final ObjectStreamClass desc = classDataLayout[i].desc;
            if (desc.hasWriteObjectMethod()) {
                final PutFieldImpl curPut = this.curPut;
                this.curPut = null;
                final SerialCallbackContext curContext = this.curContext;
                if (ObjectOutputStream.extendedDebugInfo) {
                    this.debugInfoStack.push("custom writeObject data (class \"" + desc.getName() + "\")");
                }
                try {
                    this.curContext = new SerialCallbackContext(o, desc);
                    this.bout.setBlockDataMode(true);
                    desc.invokeWriteObject(o, this);
                    this.bout.setBlockDataMode(false);
                    this.bout.writeByte(120);
                }
                finally {
                    this.curContext.setUsed();
                    this.curContext = curContext;
                    if (ObjectOutputStream.extendedDebugInfo) {
                        this.debugInfoStack.pop();
                    }
                }
                this.curPut = curPut;
            }
            else {
                this.defaultWriteFields(o, desc);
            }
        }
    }
    
    private void defaultWriteFields(final Object o, final ObjectStreamClass objectStreamClass) throws IOException {
        final Class<?> forClass = objectStreamClass.forClass();
        if (forClass != null && o != null && !forClass.isInstance(o)) {
            throw new ClassCastException();
        }
        objectStreamClass.checkDefaultSerialize();
        final int primDataSize = objectStreamClass.getPrimDataSize();
        if (this.primVals == null || this.primVals.length < primDataSize) {
            this.primVals = new byte[primDataSize];
        }
        objectStreamClass.getPrimFieldValues(o, this.primVals);
        this.bout.write(this.primVals, 0, primDataSize, false);
        final ObjectStreamField[] fields = objectStreamClass.getFields(false);
        final Object[] array = new Object[objectStreamClass.getNumObjFields()];
        final int n = fields.length - array.length;
        objectStreamClass.getObjFieldValues(o, array);
        for (int i = 0; i < array.length; ++i) {
            if (ObjectOutputStream.extendedDebugInfo) {
                this.debugInfoStack.push("field (class \"" + objectStreamClass.getName() + "\", name: \"" + fields[n + i].getName() + "\", type: \"" + fields[n + i].getType() + "\")");
            }
            try {
                this.writeObject0(array[i], fields[n + i].isUnshared());
            }
            finally {
                if (ObjectOutputStream.extendedDebugInfo) {
                    this.debugInfoStack.pop();
                }
            }
        }
    }
    
    private void writeFatalException(final IOException ex) throws IOException {
        this.clear();
        final boolean setBlockDataMode = this.bout.setBlockDataMode(false);
        try {
            this.bout.writeByte(123);
            this.writeObject0(ex, false);
            this.clear();
        }
        finally {
            this.bout.setBlockDataMode(setBlockDataMode);
        }
    }
    
    private static native void floatsToBytes(final float[] p0, final int p1, final byte[] p2, final int p3, final int p4);
    
    private static native void doublesToBytes(final double[] p0, final int p1, final byte[] p2, final int p3, final int p4);
    
    static {
        extendedDebugInfo = AccessController.doPrivileged((PrivilegedAction<Boolean>)new GetBooleanAction("sun.io.serialization.extendedDebugInfo"));
    }
    
    private static class BlockDataOutputStream extends OutputStream implements DataOutput
    {
        private static final int MAX_BLOCK_SIZE = 1024;
        private static final int MAX_HEADER_SIZE = 5;
        private static final int CHAR_BUF_SIZE = 256;
        private final byte[] buf;
        private final byte[] hbuf;
        private final char[] cbuf;
        private boolean blkmode;
        private int pos;
        private final OutputStream out;
        private final DataOutputStream dout;
        
        BlockDataOutputStream(final OutputStream out) {
            this.buf = new byte[1024];
            this.hbuf = new byte[5];
            this.cbuf = new char[256];
            this.blkmode = false;
            this.pos = 0;
            this.out = out;
            this.dout = new DataOutputStream(this);
        }
        
        boolean setBlockDataMode(final boolean blkmode) throws IOException {
            if (this.blkmode == blkmode) {
                return this.blkmode;
            }
            this.drain();
            this.blkmode = blkmode;
            return !this.blkmode;
        }
        
        boolean getBlockDataMode() {
            return this.blkmode;
        }
        
        @Override
        public void write(final int n) throws IOException {
            if (this.pos >= 1024) {
                this.drain();
            }
            this.buf[this.pos++] = (byte)n;
        }
        
        @Override
        public void write(final byte[] array) throws IOException {
            this.write(array, 0, array.length, false);
        }
        
        @Override
        public void write(final byte[] array, final int n, final int n2) throws IOException {
            this.write(array, n, n2, false);
        }
        
        @Override
        public void flush() throws IOException {
            this.drain();
            this.out.flush();
        }
        
        @Override
        public void close() throws IOException {
            this.flush();
            this.out.close();
        }
        
        void write(final byte[] array, int n, int i, final boolean b) throws IOException {
            if (!b && !this.blkmode) {
                this.drain();
                this.out.write(array, n, i);
                return;
            }
            while (i > 0) {
                if (this.pos >= 1024) {
                    this.drain();
                }
                if (i >= 1024 && !b && this.pos == 0) {
                    this.writeBlockHeader(1024);
                    this.out.write(array, n, 1024);
                    n += 1024;
                    i -= 1024;
                }
                else {
                    final int min = Math.min(i, 1024 - this.pos);
                    System.arraycopy(array, n, this.buf, this.pos, min);
                    this.pos += min;
                    n += min;
                    i -= min;
                }
            }
        }
        
        void drain() throws IOException {
            if (this.pos == 0) {
                return;
            }
            if (this.blkmode) {
                this.writeBlockHeader(this.pos);
            }
            this.out.write(this.buf, 0, this.pos);
            this.pos = 0;
        }
        
        private void writeBlockHeader(final int n) throws IOException {
            if (n <= 255) {
                this.hbuf[0] = 119;
                this.hbuf[1] = (byte)n;
                this.out.write(this.hbuf, 0, 2);
            }
            else {
                this.hbuf[0] = 122;
                Bits.putInt(this.hbuf, 1, n);
                this.out.write(this.hbuf, 0, 5);
            }
        }
        
        @Override
        public void writeBoolean(final boolean b) throws IOException {
            if (this.pos >= 1024) {
                this.drain();
            }
            Bits.putBoolean(this.buf, this.pos++, b);
        }
        
        @Override
        public void writeByte(final int n) throws IOException {
            if (this.pos >= 1024) {
                this.drain();
            }
            this.buf[this.pos++] = (byte)n;
        }
        
        @Override
        public void writeChar(final int n) throws IOException {
            if (this.pos + 2 <= 1024) {
                Bits.putChar(this.buf, this.pos, (char)n);
                this.pos += 2;
            }
            else {
                this.dout.writeChar(n);
            }
        }
        
        @Override
        public void writeShort(final int n) throws IOException {
            if (this.pos + 2 <= 1024) {
                Bits.putShort(this.buf, this.pos, (short)n);
                this.pos += 2;
            }
            else {
                this.dout.writeShort(n);
            }
        }
        
        @Override
        public void writeInt(final int n) throws IOException {
            if (this.pos + 4 <= 1024) {
                Bits.putInt(this.buf, this.pos, n);
                this.pos += 4;
            }
            else {
                this.dout.writeInt(n);
            }
        }
        
        @Override
        public void writeFloat(final float n) throws IOException {
            if (this.pos + 4 <= 1024) {
                Bits.putFloat(this.buf, this.pos, n);
                this.pos += 4;
            }
            else {
                this.dout.writeFloat(n);
            }
        }
        
        @Override
        public void writeLong(final long n) throws IOException {
            if (this.pos + 8 <= 1024) {
                Bits.putLong(this.buf, this.pos, n);
                this.pos += 8;
            }
            else {
                this.dout.writeLong(n);
            }
        }
        
        @Override
        public void writeDouble(final double n) throws IOException {
            if (this.pos + 8 <= 1024) {
                Bits.putDouble(this.buf, this.pos, n);
                this.pos += 8;
            }
            else {
                this.dout.writeDouble(n);
            }
        }
        
        @Override
        public void writeBytes(final String s) throws IOException {
            final int length = s.length();
            int n = 0;
            int min = 0;
            int min2;
            for (int i = 0; i < length; i += min2) {
                if (n >= min) {
                    n = 0;
                    min = Math.min(length - i, 256);
                    s.getChars(i, i + min, this.cbuf, 0);
                }
                if (this.pos >= 1024) {
                    this.drain();
                }
                min2 = Math.min(min - n, 1024 - this.pos);
                while (this.pos < this.pos + min2) {
                    this.buf[this.pos++] = (byte)this.cbuf[n++];
                }
            }
        }
        
        @Override
        public void writeChars(final String s) throws IOException {
            int min;
            for (int length = s.length(), i = 0; i < length; i += min) {
                min = Math.min(length - i, 256);
                s.getChars(i, i + min, this.cbuf, 0);
                this.writeChars(this.cbuf, 0, min);
            }
        }
        
        @Override
        public void writeUTF(final String s) throws IOException {
            this.writeUTF(s, this.getUTFLength(s));
        }
        
        void writeBooleans(final boolean[] array, int i, final int n) throws IOException {
            final int n2 = i + n;
            while (i < n2) {
                if (this.pos >= 1024) {
                    this.drain();
                }
                while (i < Math.min(n2, i + (1024 - this.pos))) {
                    Bits.putBoolean(this.buf, this.pos++, array[i++]);
                }
            }
        }
        
        void writeChars(final char[] array, int i, final int n) throws IOException {
            final int n2 = 1022;
            final int n3 = i + n;
            while (i < n3) {
                if (this.pos <= n2) {
                    while (i < Math.min(n3, i + (1024 - this.pos >> 1))) {
                        Bits.putChar(this.buf, this.pos, array[i++]);
                        this.pos += 2;
                    }
                }
                else {
                    this.dout.writeChar(array[i++]);
                }
            }
        }
        
        void writeShorts(final short[] array, int i, final int n) throws IOException {
            final int n2 = 1022;
            final int n3 = i + n;
            while (i < n3) {
                if (this.pos <= n2) {
                    while (i < Math.min(n3, i + (1024 - this.pos >> 1))) {
                        Bits.putShort(this.buf, this.pos, array[i++]);
                        this.pos += 2;
                    }
                }
                else {
                    this.dout.writeShort(array[i++]);
                }
            }
        }
        
        void writeInts(final int[] array, int i, final int n) throws IOException {
            final int n2 = 1020;
            final int n3 = i + n;
            while (i < n3) {
                if (this.pos <= n2) {
                    while (i < Math.min(n3, i + (1024 - this.pos >> 2))) {
                        Bits.putInt(this.buf, this.pos, array[i++]);
                        this.pos += 4;
                    }
                }
                else {
                    this.dout.writeInt(array[i++]);
                }
            }
        }
        
        void writeFloats(final float[] array, int i, final int n) throws IOException {
            final int n2 = 1020;
            final int n3 = i + n;
            while (i < n3) {
                if (this.pos <= n2) {
                    final int min = Math.min(n3 - i, 1024 - this.pos >> 2);
                    floatsToBytes(array, i, this.buf, this.pos, min);
                    i += min;
                    this.pos += min << 2;
                }
                else {
                    this.dout.writeFloat(array[i++]);
                }
            }
        }
        
        void writeLongs(final long[] array, int i, final int n) throws IOException {
            final int n2 = 1016;
            final int n3 = i + n;
            while (i < n3) {
                if (this.pos <= n2) {
                    while (i < Math.min(n3, i + (1024 - this.pos >> 3))) {
                        Bits.putLong(this.buf, this.pos, array[i++]);
                        this.pos += 8;
                    }
                }
                else {
                    this.dout.writeLong(array[i++]);
                }
            }
        }
        
        void writeDoubles(final double[] array, int i, final int n) throws IOException {
            final int n2 = 1016;
            final int n3 = i + n;
            while (i < n3) {
                if (this.pos <= n2) {
                    final int min = Math.min(n3 - i, 1024 - this.pos >> 3);
                    doublesToBytes(array, i, this.buf, this.pos, min);
                    i += min;
                    this.pos += min << 3;
                }
                else {
                    this.dout.writeDouble(array[i++]);
                }
            }
        }
        
        long getUTFLength(final String s) {
            final int length = s.length();
            long n = 0L;
            int min;
            for (int i = 0; i < length; i += min) {
                min = Math.min(length - i, 256);
                s.getChars(i, i + min, this.cbuf, 0);
                for (int j = 0; j < min; ++j) {
                    final char c = this.cbuf[j];
                    if (c >= '\u0001' && c <= '\u007f') {
                        ++n;
                    }
                    else if (c > '\u07ff') {
                        n += 3L;
                    }
                    else {
                        n += 2L;
                    }
                }
            }
            return n;
        }
        
        void writeUTF(final String s, final long n) throws IOException {
            if (n > 65535L) {
                throw new UTFDataFormatException();
            }
            this.writeShort((int)n);
            if (n == s.length()) {
                this.writeBytes(s);
            }
            else {
                this.writeUTFBody(s);
            }
        }
        
        void writeLongUTF(final String s) throws IOException {
            this.writeLongUTF(s, this.getUTFLength(s));
        }
        
        void writeLongUTF(final String s, final long n) throws IOException {
            this.writeLong(n);
            if (n == s.length()) {
                this.writeBytes(s);
            }
            else {
                this.writeUTFBody(s);
            }
        }
        
        private void writeUTFBody(final String s) throws IOException {
            final int n = 1021;
            int min;
            for (int length = s.length(), i = 0; i < length; i += min) {
                min = Math.min(length - i, 256);
                s.getChars(i, i + min, this.cbuf, 0);
                for (int j = 0; j < min; ++j) {
                    final char c = this.cbuf[j];
                    if (this.pos <= n) {
                        if (c <= '\u007f' && c != '\0') {
                            this.buf[this.pos++] = (byte)c;
                        }
                        else if (c > '\u07ff') {
                            this.buf[this.pos + 2] = (byte)('\u0080' | (c >> 0 & '?'));
                            this.buf[this.pos + 1] = (byte)('\u0080' | (c >> 6 & '?'));
                            this.buf[this.pos + 0] = (byte)('\u00e0' | (c >> 12 & '\u000f'));
                            this.pos += 3;
                        }
                        else {
                            this.buf[this.pos + 1] = (byte)('\u0080' | (c >> 0 & '?'));
                            this.buf[this.pos + 0] = (byte)('\u00c0' | (c >> 6 & '\u001f'));
                            this.pos += 2;
                        }
                    }
                    else if (c <= '\u007f' && c != '\0') {
                        this.write(c);
                    }
                    else if (c > '\u07ff') {
                        this.write('\u00e0' | (c >> 12 & '\u000f'));
                        this.write('\u0080' | (c >> 6 & '?'));
                        this.write('\u0080' | (c >> 0 & '?'));
                    }
                    else {
                        this.write('\u00c0' | (c >> 6 & '\u001f'));
                        this.write('\u0080' | (c >> 0 & '?'));
                    }
                }
            }
        }
    }
    
    private static class Caches
    {
        static final ConcurrentMap<ObjectStreamClass.WeakClassKey, Boolean> subclassAudits;
        static final ReferenceQueue<Class<?>> subclassAuditsQueue;
        
        static {
            subclassAudits = new ConcurrentHashMap<ObjectStreamClass.WeakClassKey, Boolean>();
            subclassAuditsQueue = new ReferenceQueue<Class<?>>();
        }
    }
    
    private static class DebugTraceInfoStack
    {
        private final List<String> stack;
        
        DebugTraceInfoStack() {
            this.stack = new ArrayList<String>();
        }
        
        void clear() {
            this.stack.clear();
        }
        
        void pop() {
            this.stack.remove(this.stack.size() - 1);
        }
        
        void push(final String s) {
            this.stack.add("\t- " + s);
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            if (!this.stack.isEmpty()) {
                for (int i = this.stack.size(); i > 0; --i) {
                    sb.append(this.stack.get(i - 1) + ((i != 1) ? "\n" : ""));
                }
            }
            return sb.toString();
        }
    }
    
    private static class HandleTable
    {
        private int size;
        private int threshold;
        private final float loadFactor;
        private int[] spine;
        private int[] next;
        private Object[] objs;
        
        HandleTable(final int n, final float loadFactor) {
            this.loadFactor = loadFactor;
            this.spine = new int[n];
            this.next = new int[n];
            this.objs = new Object[n];
            this.threshold = (int)(n * loadFactor);
            this.clear();
        }
        
        int assign(final Object o) {
            if (this.size >= this.next.length) {
                this.growEntries();
            }
            if (this.size >= this.threshold) {
                this.growSpine();
            }
            this.insert(o, this.size);
            return this.size++;
        }
        
        int lookup(final Object o) {
            if (this.size == 0) {
                return -1;
            }
            for (int i = this.spine[this.hash(o) % this.spine.length]; i >= 0; i = this.next[i]) {
                if (this.objs[i] == o) {
                    return i;
                }
            }
            return -1;
        }
        
        void clear() {
            Arrays.fill(this.spine, -1);
            Arrays.fill(this.objs, 0, this.size, null);
            this.size = 0;
        }
        
        int size() {
            return this.size;
        }
        
        private void insert(final Object o, final int n) {
            final int n2 = this.hash(o) % this.spine.length;
            this.objs[n] = o;
            this.next[n] = this.spine[n2];
            this.spine[n2] = n;
        }
        
        private void growSpine() {
            this.spine = new int[(this.spine.length << 1) + 1];
            this.threshold = (int)(this.spine.length * this.loadFactor);
            Arrays.fill(this.spine, -1);
            for (int i = 0; i < this.size; ++i) {
                this.insert(this.objs[i], i);
            }
        }
        
        private void growEntries() {
            final int n = (this.next.length << 1) + 1;
            final int[] next = new int[n];
            System.arraycopy(this.next, 0, next, 0, this.size);
            this.next = next;
            final Object[] objs = new Object[n];
            System.arraycopy(this.objs, 0, objs, 0, this.size);
            this.objs = objs;
        }
        
        private int hash(final Object o) {
            return System.identityHashCode(o) & Integer.MAX_VALUE;
        }
    }
    
    public abstract static class PutField
    {
        public abstract void put(final String p0, final boolean p1);
        
        public abstract void put(final String p0, final byte p1);
        
        public abstract void put(final String p0, final char p1);
        
        public abstract void put(final String p0, final short p1);
        
        public abstract void put(final String p0, final int p1);
        
        public abstract void put(final String p0, final long p1);
        
        public abstract void put(final String p0, final float p1);
        
        public abstract void put(final String p0, final double p1);
        
        public abstract void put(final String p0, final Object p1);
        
        @Deprecated
        public abstract void write(final ObjectOutput p0) throws IOException;
    }
    
    private class PutFieldImpl extends PutField
    {
        private final ObjectStreamClass desc;
        private final byte[] primVals;
        private final Object[] objVals;
        
        PutFieldImpl(final ObjectStreamClass desc) {
            this.desc = desc;
            this.primVals = new byte[desc.getPrimDataSize()];
            this.objVals = new Object[desc.getNumObjFields()];
        }
        
        @Override
        public void put(final String s, final boolean b) {
            Bits.putBoolean(this.primVals, this.getFieldOffset(s, Boolean.TYPE), b);
        }
        
        @Override
        public void put(final String s, final byte b) {
            this.primVals[this.getFieldOffset(s, Byte.TYPE)] = b;
        }
        
        @Override
        public void put(final String s, final char c) {
            Bits.putChar(this.primVals, this.getFieldOffset(s, Character.TYPE), c);
        }
        
        @Override
        public void put(final String s, final short n) {
            Bits.putShort(this.primVals, this.getFieldOffset(s, Short.TYPE), n);
        }
        
        @Override
        public void put(final String s, final int n) {
            Bits.putInt(this.primVals, this.getFieldOffset(s, Integer.TYPE), n);
        }
        
        @Override
        public void put(final String s, final float n) {
            Bits.putFloat(this.primVals, this.getFieldOffset(s, Float.TYPE), n);
        }
        
        @Override
        public void put(final String s, final long n) {
            Bits.putLong(this.primVals, this.getFieldOffset(s, Long.TYPE), n);
        }
        
        @Override
        public void put(final String s, final double n) {
            Bits.putDouble(this.primVals, this.getFieldOffset(s, Double.TYPE), n);
        }
        
        @Override
        public void put(final String s, final Object o) {
            this.objVals[this.getFieldOffset(s, Object.class)] = o;
        }
        
        @Override
        public void write(final ObjectOutput objectOutput) throws IOException {
            if (ObjectOutputStream.this != objectOutput) {
                throw new IllegalArgumentException("wrong stream");
            }
            objectOutput.write(this.primVals, 0, this.primVals.length);
            final ObjectStreamField[] fields = this.desc.getFields(false);
            final int n = fields.length - this.objVals.length;
            for (int i = 0; i < this.objVals.length; ++i) {
                if (fields[n + i].isUnshared()) {
                    throw new IOException("cannot write unshared object");
                }
                objectOutput.writeObject(this.objVals[i]);
            }
        }
        
        void writeFields() throws IOException {
            ObjectOutputStream.this.bout.write(this.primVals, 0, this.primVals.length, false);
            final ObjectStreamField[] fields = this.desc.getFields(false);
            final int n = fields.length - this.objVals.length;
            for (int i = 0; i < this.objVals.length; ++i) {
                if (ObjectOutputStream.extendedDebugInfo) {
                    ObjectOutputStream.this.debugInfoStack.push("field (class \"" + this.desc.getName() + "\", name: \"" + fields[n + i].getName() + "\", type: \"" + fields[n + i].getType() + "\")");
                }
                try {
                    ObjectOutputStream.this.writeObject0(this.objVals[i], fields[n + i].isUnshared());
                }
                finally {
                    if (ObjectOutputStream.extendedDebugInfo) {
                        ObjectOutputStream.this.debugInfoStack.pop();
                    }
                }
            }
        }
        
        private int getFieldOffset(final String s, final Class<?> clazz) {
            final ObjectStreamField field = this.desc.getField(s, clazz);
            if (field == null) {
                throw new IllegalArgumentException("no such field " + s + " with type " + clazz);
            }
            return field.getOffset();
        }
    }
    
    private static class ReplaceTable
    {
        private final HandleTable htab;
        private Object[] reps;
        
        ReplaceTable(final int n, final float n2) {
            this.htab = new HandleTable(n, n2);
            this.reps = new Object[n];
        }
        
        void assign(final Object o, final Object o2) {
            final int i = this.htab.assign(o);
            while (i >= this.reps.length) {
                this.grow();
            }
            this.reps[i] = o2;
        }
        
        Object lookup(final Object o) {
            final int lookup = this.htab.lookup(o);
            return (lookup >= 0) ? this.reps[lookup] : o;
        }
        
        void clear() {
            Arrays.fill(this.reps, 0, this.htab.size(), null);
            this.htab.clear();
        }
        
        int size() {
            return this.htab.size();
        }
        
        private void grow() {
            final Object[] reps = new Object[(this.reps.length << 1) + 1];
            System.arraycopy(this.reps, 0, reps, 0, this.reps.length);
            this.reps = reps;
        }
    }
}
