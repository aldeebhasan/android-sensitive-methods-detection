package java.lang.invoke;

import java.util.*;
import sun.invoke.util.*;
import java.io.*;
import java.util.concurrent.*;
import java.lang.ref.*;

public final class MethodType implements Serializable
{
    private static final long serialVersionUID = 292L;
    private final Class<?> rtype;
    private final Class<?>[] ptypes;
    @Stable
    private MethodTypeForm form;
    @Stable
    private Object wrapAlt;
    @Stable
    private Invokers invokers;
    @Stable
    private String methodDescriptor;
    static final int MAX_JVM_ARITY = 255;
    static final int MAX_MH_ARITY = 254;
    static final int MAX_MH_INVOKER_ARITY = 253;
    static final ConcurrentWeakInternSet<MethodType> internTable;
    static final Class<?>[] NO_PTYPES;
    private static final MethodType[] objectOnlyTypes;
    private static final ObjectStreamField[] serialPersistentFields;
    private static final long rtypeOffset;
    private static final long ptypesOffset;
    
    private MethodType(final Class<?> rtype, final Class<?>[] array, final boolean b) {
        checkRtype(rtype);
        checkPtypes(array);
        this.rtype = rtype;
        this.ptypes = (b ? array : Arrays.copyOf(array, array.length));
    }
    
    private MethodType(final Class<?>[] ptypes, final Class<?> rtype) {
        this.rtype = rtype;
        this.ptypes = ptypes;
    }
    
    MethodTypeForm form() {
        return this.form;
    }
    
    Class<?> rtype() {
        return this.rtype;
    }
    
    Class<?>[] ptypes() {
        return this.ptypes;
    }
    
    void setForm(final MethodTypeForm form) {
        this.form = form;
    }
    
    private static void checkRtype(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
    }
    
    private static void checkPtype(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        if (clazz == Void.TYPE) {
            throw MethodHandleStatics.newIllegalArgumentException("parameter type cannot be void");
        }
    }
    
    private static int checkPtypes(final Class<?>[] array) {
        int n = 0;
        for (final Class<?> clazz : array) {
            checkPtype(clazz);
            if (clazz == Double.TYPE || clazz == Long.TYPE) {
                ++n;
            }
        }
        checkSlotCount(array.length + n);
        return n;
    }
    
    static void checkSlotCount(final int n) {
        if ((n & 0xFF) != n) {
            throw MethodHandleStatics.newIllegalArgumentException("bad parameter count " + n);
        }
    }
    
    private static IndexOutOfBoundsException newIndexOutOfBoundsException(Object string) {
        if (string instanceof Integer) {
            string = "bad index: " + string;
        }
        return new IndexOutOfBoundsException(string.toString());
    }
    
    public static MethodType methodType(final Class<?> clazz, final Class<?>[] array) {
        return makeImpl(clazz, array, false);
    }
    
    public static MethodType methodType(final Class<?> clazz, final List<Class<?>> list) {
        return makeImpl(clazz, listToArray(list), false);
    }
    
    private static Class<?>[] listToArray(final List<Class<?>> list) {
        checkSlotCount(list.size());
        return list.toArray(MethodType.NO_PTYPES);
    }
    
    public static MethodType methodType(final Class<?> clazz, final Class<?> clazz2, final Class<?>... array) {
        final Class[] array2 = new Class[1 + array.length];
        array2[0] = clazz2;
        System.arraycopy(array, 0, array2, 1, array.length);
        return makeImpl(clazz, array2, true);
    }
    
    public static MethodType methodType(final Class<?> clazz) {
        return makeImpl(clazz, MethodType.NO_PTYPES, true);
    }
    
    public static MethodType methodType(final Class<?> clazz, final Class<?> clazz2) {
        return makeImpl(clazz, new Class[] { clazz2 }, true);
    }
    
    public static MethodType methodType(final Class<?> clazz, final MethodType methodType) {
        return makeImpl(clazz, methodType.ptypes, true);
    }
    
    static MethodType makeImpl(final Class<?> clazz, Class<?>[] no_PTYPES, boolean b) {
        final MethodType methodType = MethodType.internTable.get(new MethodType(no_PTYPES, clazz));
        if (methodType != null) {
            return methodType;
        }
        if (no_PTYPES.length == 0) {
            no_PTYPES = MethodType.NO_PTYPES;
            b = true;
        }
        final MethodType methodType2 = new MethodType(clazz, no_PTYPES, b);
        methodType2.form = MethodTypeForm.findForm(methodType2);
        return MethodType.internTable.add(methodType2);
    }
    
    public static MethodType genericMethodType(final int n, final boolean b) {
        checkSlotCount(n);
        final int n2 = b ? 1 : 0;
        final int n3 = n * 2 + n2;
        if (n3 < MethodType.objectOnlyTypes.length) {
            final MethodType methodType = MethodType.objectOnlyTypes[n3];
            if (methodType != null) {
                return methodType;
            }
        }
        final Class[] array = new Class[n + n2];
        Arrays.fill(array, Object.class);
        if (n2 != 0) {
            array[n] = Object[].class;
        }
        final MethodType impl = makeImpl(Object.class, array, true);
        if (n3 < MethodType.objectOnlyTypes.length) {
            MethodType.objectOnlyTypes[n3] = impl;
        }
        return impl;
    }
    
    public static MethodType genericMethodType(final int n) {
        return genericMethodType(n, false);
    }
    
    public MethodType changeParameterType(final int n, final Class<?> clazz) {
        if (this.parameterType(n) == clazz) {
            return this;
        }
        checkPtype(clazz);
        final Class[] array = this.ptypes.clone();
        array[n] = clazz;
        return makeImpl(this.rtype, array, true);
    }
    
    public MethodType insertParameterTypes(final int n, final Class<?>... array) {
        final int length = this.ptypes.length;
        if (n < 0 || n > length) {
            throw newIndexOutOfBoundsException(n);
        }
        checkSlotCount(this.parameterSlotCount() + array.length + checkPtypes(array));
        final int length2 = array.length;
        if (length2 == 0) {
            return this;
        }
        final Class<?>[] array2 = Arrays.copyOfRange(this.ptypes, 0, length + length2);
        System.arraycopy(array2, n, array2, n + length2, length - n);
        System.arraycopy(array, 0, array2, n, length2);
        return makeImpl(this.rtype, array2, true);
    }
    
    public MethodType appendParameterTypes(final Class<?>... array) {
        return this.insertParameterTypes(this.parameterCount(), array);
    }
    
    public MethodType insertParameterTypes(final int n, final List<Class<?>> list) {
        return this.insertParameterTypes(n, listToArray(list));
    }
    
    public MethodType appendParameterTypes(final List<Class<?>> list) {
        return this.insertParameterTypes(this.parameterCount(), list);
    }
    
    MethodType replaceParameterTypes(final int n, final int n2, final Class<?>... array) {
        if (n == n2) {
            return this.insertParameterTypes(n, array);
        }
        final int length = this.ptypes.length;
        if (0 > n || n > n2 || n2 > length) {
            throw newIndexOutOfBoundsException("start=" + n + " end=" + n2);
        }
        if (array.length == 0) {
            return this.dropParameterTypes(n, n2);
        }
        return this.dropParameterTypes(n, n2).insertParameterTypes(n, array);
    }
    
    MethodType asSpreaderType(final Class<?> clazz, final int n) {
        assert this.parameterCount() >= n;
        final int n2 = this.ptypes.length - n;
        if (n == 0) {
            return this;
        }
        if (clazz == Object[].class) {
            if (this.isGeneric()) {
                return this;
            }
            if (n2 == 0) {
                MethodType methodType = genericMethodType(n);
                if (this.rtype != Object.class) {
                    methodType = methodType.changeReturnType(this.rtype);
                }
                return methodType;
            }
        }
        final Class componentType = clazz.getComponentType();
        assert componentType != null;
        for (int i = n2; i < this.ptypes.length; ++i) {
            if (this.ptypes[i] != componentType) {
                final Class[] array = this.ptypes.clone();
                Arrays.fill(array, i, this.ptypes.length, componentType);
                return methodType(this.rtype, array);
            }
        }
        return this;
    }
    
    Class<?> leadingReferenceParameter() {
        final Class<?> clazz;
        if (this.ptypes.length == 0 || (clazz = this.ptypes[0]).isPrimitive()) {
            throw MethodHandleStatics.newIllegalArgumentException("no leading reference parameter");
        }
        return clazz;
    }
    
    MethodType asCollectorType(final Class<?> clazz, final int n) {
        assert this.parameterCount() >= 1;
        assert this.lastParameterType().isAssignableFrom(clazz);
        MethodType methodType;
        if (clazz == Object[].class) {
            methodType = genericMethodType(n);
            if (this.rtype != Object.class) {
                methodType = methodType.changeReturnType(this.rtype);
            }
        }
        else {
            final Class<?> componentType = clazz.getComponentType();
            assert componentType != null;
            methodType = methodType(this.rtype, Collections.nCopies(n, componentType));
        }
        if (this.ptypes.length == 1) {
            return methodType;
        }
        return methodType.insertParameterTypes(0, this.parameterList().subList(0, this.ptypes.length - 1));
    }
    
    public MethodType dropParameterTypes(final int n, final int n2) {
        final int length = this.ptypes.length;
        if (0 > n || n > n2 || n2 > length) {
            throw newIndexOutOfBoundsException("start=" + n + " end=" + n2);
        }
        if (n == n2) {
            return this;
        }
        Class<?>[] no_PTYPES;
        if (n == 0) {
            if (n2 == length) {
                no_PTYPES = MethodType.NO_PTYPES;
            }
            else {
                no_PTYPES = Arrays.copyOfRange(this.ptypes, n2, length);
            }
        }
        else if (n2 == length) {
            no_PTYPES = Arrays.copyOfRange(this.ptypes, 0, n);
        }
        else {
            final int n3 = length - n2;
            no_PTYPES = Arrays.copyOfRange(this.ptypes, 0, n + n3);
            System.arraycopy(this.ptypes, n2, no_PTYPES, n, n3);
        }
        return makeImpl(this.rtype, no_PTYPES, true);
    }
    
    public MethodType changeReturnType(final Class<?> clazz) {
        if (this.returnType() == clazz) {
            return this;
        }
        return makeImpl(clazz, this.ptypes, true);
    }
    
    public boolean hasPrimitives() {
        return this.form.hasPrimitives();
    }
    
    public boolean hasWrappers() {
        return this.unwrap() != this;
    }
    
    public MethodType erase() {
        return this.form.erasedType();
    }
    
    MethodType basicType() {
        return this.form.basicType();
    }
    
    MethodType invokerType() {
        return this.insertParameterTypes(0, MethodHandle.class);
    }
    
    public MethodType generic() {
        return genericMethodType(this.parameterCount());
    }
    
    boolean isGeneric() {
        return this == this.erase() && !this.hasPrimitives();
    }
    
    public MethodType wrap() {
        return this.hasPrimitives() ? wrapWithPrims(this) : this;
    }
    
    public MethodType unwrap() {
        return unwrapWithNoPrims(this.hasPrimitives() ? wrapWithPrims(this) : this);
    }
    
    private static MethodType wrapWithPrims(final MethodType methodType) {
        assert methodType.hasPrimitives();
        MethodType canonicalize = (MethodType)methodType.wrapAlt;
        if (canonicalize == null) {
            canonicalize = MethodTypeForm.canonicalize(methodType, 2, 2);
            assert canonicalize != null;
            methodType.wrapAlt = canonicalize;
        }
        return canonicalize;
    }
    
    private static MethodType unwrapWithNoPrims(final MethodType methodType) {
        assert !methodType.hasPrimitives();
        MethodType canonicalize = (MethodType)methodType.wrapAlt;
        if (canonicalize == null) {
            canonicalize = MethodTypeForm.canonicalize(methodType, 3, 3);
            if (canonicalize == null) {
                canonicalize = methodType;
            }
            methodType.wrapAlt = canonicalize;
        }
        return canonicalize;
    }
    
    public Class<?> parameterType(final int n) {
        return this.ptypes[n];
    }
    
    public int parameterCount() {
        return this.ptypes.length;
    }
    
    public Class<?> returnType() {
        return this.rtype;
    }
    
    public List<Class<?>> parameterList() {
        return Collections.unmodifiableList((List<? extends Class<?>>)Arrays.asList((T[])this.ptypes.clone()));
    }
    
    Class<?> lastParameterType() {
        final int length = this.ptypes.length;
        return (length == 0) ? Void.TYPE : this.ptypes[length - 1];
    }
    
    public Class<?>[] parameterArray() {
        return (Class<?>[])this.ptypes.clone();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof MethodType && this.equals((MethodType)o));
    }
    
    private boolean equals(final MethodType methodType) {
        return this.rtype == methodType.rtype && Arrays.equals(this.ptypes, methodType.ptypes);
    }
    
    @Override
    public int hashCode() {
        int n = 31 + this.rtype.hashCode();
        final Class<?>[] ptypes = this.ptypes;
        for (int length = ptypes.length, i = 0; i < length; ++i) {
            n = 31 * n + ptypes[i].hashCode();
        }
        return n;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < this.ptypes.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(this.ptypes[i].getSimpleName());
        }
        sb.append(")");
        sb.append(this.rtype.getSimpleName());
        return sb.toString();
    }
    
    boolean isViewableAs(final MethodType methodType, final boolean b) {
        return VerifyType.isNullConversion(this.returnType(), methodType.returnType(), b) && this.parametersAreViewableAs(methodType, b);
    }
    
    boolean parametersAreViewableAs(final MethodType methodType, final boolean b) {
        if (this.form == methodType.form && this.form.erasedType == this) {
            return true;
        }
        if (this.ptypes == methodType.ptypes) {
            return true;
        }
        final int parameterCount = this.parameterCount();
        if (parameterCount != methodType.parameterCount()) {
            return false;
        }
        for (int i = 0; i < parameterCount; ++i) {
            if (!VerifyType.isNullConversion(methodType.parameterType(i), this.parameterType(i), b)) {
                return false;
            }
        }
        return true;
    }
    
    boolean isConvertibleTo(final MethodType methodType) {
        final MethodTypeForm form = this.form();
        final MethodTypeForm form2 = methodType.form();
        if (form == form2) {
            return true;
        }
        if (!canConvert(this.returnType(), methodType.returnType())) {
            return false;
        }
        final Class<?>[] ptypes = methodType.ptypes;
        final Class<?>[] ptypes2 = this.ptypes;
        if (ptypes == ptypes2) {
            return true;
        }
        final int length;
        if ((length = ptypes.length) != ptypes2.length) {
            return false;
        }
        if (length <= 1) {
            return length != 1 || canConvert(ptypes[0], ptypes2[0]);
        }
        if ((form.primitiveParameterCount() != 0 || form.erasedType != this) && (form2.primitiveParameterCount() != 0 || form2.erasedType != methodType)) {
            return this.canConvertParameters(ptypes, ptypes2);
        }
        assert this.canConvertParameters(ptypes, ptypes2);
        return true;
    }
    
    boolean explicitCastEquivalentToAsType(final MethodType methodType) {
        if (this == methodType) {
            return true;
        }
        if (!explicitCastEquivalentToAsType(this.rtype, methodType.rtype)) {
            return false;
        }
        final Class<?>[] ptypes = methodType.ptypes;
        final Class<?>[] ptypes2 = this.ptypes;
        if (ptypes2 == ptypes) {
            return true;
        }
        assert ptypes2.length == ptypes.length;
        for (int i = 0; i < ptypes2.length; ++i) {
            if (!explicitCastEquivalentToAsType(ptypes[i], ptypes2[i])) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean explicitCastEquivalentToAsType(final Class<?> clazz, final Class<?> clazz2) {
        if (clazz == clazz2 || clazz2 == Object.class || clazz2 == Void.TYPE) {
            return true;
        }
        if (clazz.isPrimitive()) {
            return canConvert(clazz, clazz2);
        }
        return !clazz2.isPrimitive() && (!clazz2.isInterface() || clazz2.isAssignableFrom(clazz));
    }
    
    private boolean canConvertParameters(final Class<?>[] array, final Class<?>[] array2) {
        for (int i = 0; i < array.length; ++i) {
            if (!canConvert(array[i], array2[i])) {
                return false;
            }
        }
        return true;
    }
    
    static boolean canConvert(final Class<?> clazz, final Class<?> clazz2) {
        if (clazz == clazz2 || clazz == Object.class || clazz2 == Object.class) {
            return true;
        }
        if (clazz.isPrimitive()) {
            if (clazz == Void.TYPE) {
                return true;
            }
            final Wrapper forPrimitiveType = Wrapper.forPrimitiveType(clazz);
            if (clazz2.isPrimitive()) {
                return Wrapper.forPrimitiveType(clazz2).isConvertibleFrom(forPrimitiveType);
            }
            return clazz2.isAssignableFrom(forPrimitiveType.wrapperType());
        }
        else {
            if (!clazz2.isPrimitive()) {
                return true;
            }
            if (clazz2 == Void.TYPE) {
                return true;
            }
            final Wrapper forPrimitiveType2 = Wrapper.forPrimitiveType(clazz2);
            return clazz.isAssignableFrom(forPrimitiveType2.wrapperType()) || (Wrapper.isWrapperType(clazz) && forPrimitiveType2.isConvertibleFrom(Wrapper.forWrapperType(clazz)));
        }
    }
    
    int parameterSlotCount() {
        return this.form.parameterSlotCount();
    }
    
    Invokers invokers() {
        final Invokers invokers = this.invokers;
        if (invokers != null) {
            return invokers;
        }
        return this.invokers = new Invokers(this);
    }
    
    int parameterSlotDepth(final int n) {
        if (n < 0 || n > this.ptypes.length) {
            this.parameterType(n);
        }
        return this.form.parameterToArgSlot(n - 1);
    }
    
    int returnSlotCount() {
        return this.form.returnSlotCount();
    }
    
    public static MethodType fromMethodDescriptorString(final String s, final ClassLoader classLoader) throws IllegalArgumentException, TypeNotPresentException {
        if (!s.startsWith("(") || s.indexOf(41) < 0 || s.indexOf(46) >= 0) {
            throw MethodHandleStatics.newIllegalArgumentException("not a method descriptor: " + s);
        }
        final List<Class<?>> method = BytecodeDescriptor.parseMethod(s, classLoader);
        final Class<?> clazz = method.remove(method.size() - 1);
        checkSlotCount(method.size());
        return makeImpl(clazz, listToArray(method), true);
    }
    
    public String toMethodDescriptorString() {
        String methodDescriptor = this.methodDescriptor;
        if (methodDescriptor == null) {
            methodDescriptor = BytecodeDescriptor.unparse(this);
            this.methodDescriptor = methodDescriptor;
        }
        return methodDescriptor;
    }
    
    static String toFieldDescriptorString(final Class<?> clazz) {
        return BytecodeDescriptor.unparse(clazz);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.returnType());
        objectOutputStream.writeObject(this.parameterArray());
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        MethodHandleStatics.UNSAFE.putObject(this, MethodType.rtypeOffset, Void.TYPE);
        MethodHandleStatics.UNSAFE.putObject(this, MethodType.ptypesOffset, MethodType.NO_PTYPES);
        objectInputStream.defaultReadObject();
        this.wrapAlt = new MethodType[] { methodType((Class<?>)objectInputStream.readObject(), (Class<?>[])objectInputStream.readObject()) };
    }
    
    private Object readResolve() {
        final MethodType methodType = ((MethodType[])this.wrapAlt)[0];
        this.wrapAlt = null;
        return methodType;
    }
    
    static {
        internTable = new ConcurrentWeakInternSet<MethodType>();
        NO_PTYPES = new Class[0];
        objectOnlyTypes = new MethodType[20];
        serialPersistentFields = new ObjectStreamField[0];
        try {
            rtypeOffset = MethodHandleStatics.UNSAFE.objectFieldOffset(MethodType.class.getDeclaredField("rtype"));
            ptypesOffset = MethodHandleStatics.UNSAFE.objectFieldOffset(MethodType.class.getDeclaredField("ptypes"));
        }
        catch (Exception ex) {
            throw new Error(ex);
        }
    }
    
    private static class ConcurrentWeakInternSet<T>
    {
        private final ConcurrentMap<WeakEntry<T>, WeakEntry<T>> map;
        private final ReferenceQueue<T> stale;
        
        public ConcurrentWeakInternSet() {
            this.map = new ConcurrentHashMap<WeakEntry<T>, WeakEntry<T>>();
            this.stale = new ReferenceQueue<T>();
        }
        
        public T get(final T t) {
            if (t == null) {
                throw new NullPointerException();
            }
            this.expungeStaleElements();
            final WeakEntry<Object> weakEntry = this.map.get(new WeakEntry(t));
            if (weakEntry != null) {
                final T value = weakEntry.get();
                if (value != null) {
                    return value;
                }
            }
            return null;
        }
        
        public T add(final T t) {
            if (t == null) {
                throw new NullPointerException();
            }
            final WeakEntry<T> weakEntry = new WeakEntry<T>(t, this.stale);
            T t2;
            do {
                this.expungeStaleElements();
                final WeakEntry<T> weakEntry2 = this.map.putIfAbsent(weakEntry, weakEntry);
                t2 = (T)((weakEntry2 == null) ? t : weakEntry2.get());
            } while (t2 == null);
            return t2;
        }
        
        private void expungeStaleElements() {
            Reference<? extends T> poll;
            while ((poll = this.stale.poll()) != null) {
                this.map.remove(poll);
            }
        }
        
        private static class WeakEntry<T> extends WeakReference<T>
        {
            public final int hashcode;
            
            public WeakEntry(final T t, final ReferenceQueue<T> referenceQueue) {
                super(t, referenceQueue);
                this.hashcode = t.hashCode();
            }
            
            public WeakEntry(final T t) {
                super(t);
                this.hashcode = t.hashCode();
            }
            
            @Override
            public boolean equals(final Object o) {
                if (o instanceof WeakEntry) {
                    final Object value = ((WeakEntry)o).get();
                    final Object value2 = this.get();
                    return (value == null || value2 == null) ? (this == o) : value2.equals(value);
                }
                return false;
            }
            
            @Override
            public int hashCode() {
                return this.hashcode;
            }
        }
    }
}
