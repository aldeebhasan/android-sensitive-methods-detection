package java.lang.invoke;

import java.security.*;
import sun.reflect.*;
import java.util.*;
import java.util.concurrent.*;
import sun.invoke.util.*;
import sun.reflect.misc.*;
import sun.security.util.*;
import java.lang.reflect.*;
import sun.misc.*;

public class MethodHandles
{
    private static final MemberName.Factory IMPL_NAMES;
    private static final Permission ACCESS_PERMISSION;
    private static final MethodHandle[] IDENTITY_MHS;
    private static final MethodHandle[] ZERO_MHS;
    
    @CallerSensitive
    public static Lookup lookup() {
        return new Lookup(Reflection.getCallerClass());
    }
    
    public static Lookup publicLookup() {
        return Lookup.PUBLIC_LOOKUP;
    }
    
    public static <T extends Member> T reflectAs(final Class<T> clazz, final MethodHandle methodHandle) {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(MethodHandles.ACCESS_PERMISSION);
        }
        final Lookup impl_LOOKUP = Lookup.IMPL_LOOKUP;
        return impl_LOOKUP.revealDirect(methodHandle).reflectAs(clazz, impl_LOOKUP);
    }
    
    public static MethodHandle arrayElementGetter(final Class<?> clazz) throws IllegalArgumentException {
        return MethodHandleImpl.makeArrayElementAccessor(clazz, false);
    }
    
    public static MethodHandle arrayElementSetter(final Class<?> clazz) throws IllegalArgumentException {
        return MethodHandleImpl.makeArrayElementAccessor(clazz, true);
    }
    
    public static MethodHandle spreadInvoker(MethodType spreaderType, final int n) {
        if (n < 0 || n > spreaderType.parameterCount()) {
            throw MethodHandleStatics.newIllegalArgumentException("bad argument count", n);
        }
        spreaderType = spreaderType.asSpreaderType(Object[].class, spreaderType.parameterCount() - n);
        return spreaderType.invokers().spreadInvoker(n);
    }
    
    public static MethodHandle exactInvoker(final MethodType methodType) {
        return methodType.invokers().exactInvoker();
    }
    
    public static MethodHandle invoker(final MethodType methodType) {
        return methodType.invokers().genericInvoker();
    }
    
    static MethodHandle basicInvoker(final MethodType methodType) {
        return methodType.invokers().basicInvoker();
    }
    
    public static MethodHandle explicitCastArguments(final MethodHandle methodHandle, final MethodType methodType) {
        explicitCastArgumentsChecks(methodHandle, methodType);
        final MethodType type = methodHandle.type();
        if (type == methodType) {
            return methodHandle;
        }
        if (type.explicitCastEquivalentToAsType(methodType)) {
            return methodHandle.asFixedArity().asType(methodType);
        }
        return MethodHandleImpl.makePairwiseConvert(methodHandle, methodType, false);
    }
    
    private static void explicitCastArgumentsChecks(final MethodHandle methodHandle, final MethodType methodType) {
        if (methodHandle.type().parameterCount() != methodType.parameterCount()) {
            throw new WrongMethodTypeException("cannot explicitly cast " + methodHandle + " to " + methodType);
        }
    }
    
    public static MethodHandle permuteArguments(final MethodHandle methodHandle, final MethodType methodType, int... array) {
        array = array.clone();
        MethodType methodType2 = methodHandle.type();
        permuteArgumentChecks(array, methodType, methodType2);
        final BoundMethodHandle rebind = methodHandle.rebind();
        LambdaForm lambdaForm = rebind.form;
        final int parameterCount = methodType.parameterCount();
        int firstDupOrDrop;
        while ((firstDupOrDrop = findFirstDupOrDrop(array, parameterCount)) != 0) {
            if (firstDupOrDrop > 0) {
                int n2;
                int n = n2 = firstDupOrDrop;
                final int n3 = array[n];
                boolean b = false;
                int n4;
                while ((n4 = array[--n2]) != n3) {
                    if (n3 > n4) {
                        b = true;
                    }
                }
                if (!b) {
                    n = n2;
                    n2 = firstDupOrDrop;
                }
                lambdaForm = lambdaForm.editor().dupArgumentForm(1 + n, 1 + n2);
                assert array[n] == array[n2];
                methodType2 = methodType2.dropParameterTypes(n2, n2 + 1);
                final int n5 = n2 + 1;
                System.arraycopy(array, n5, array, n2, array.length - n5);
                array = Arrays.copyOf(array, array.length - 1);
            }
            else {
                int n6;
                int n7;
                for (n6 = ~firstDupOrDrop, n7 = 0; n7 < array.length && array[n7] < n6; ++n7) {}
                final Class<?> parameterType = methodType.parameterType(n6);
                lambdaForm = lambdaForm.editor().addArgumentForm(1 + n7, LambdaForm.BasicType.basicType(parameterType));
                methodType2 = methodType2.insertParameterTypes(n7, parameterType);
                final int n8 = n7 + 1;
                array = Arrays.copyOf(array, array.length + 1);
                System.arraycopy(array, n7, array, n8, array.length - n8);
                array[n7] = n6;
            }
            assert permuteArgumentChecks(array, methodType, methodType2);
        }
        assert array.length == parameterCount;
        final LambdaForm permuteArgumentsForm = lambdaForm.editor().permuteArgumentsForm(1, array);
        if (methodType == rebind.type() && permuteArgumentsForm == rebind.internalForm()) {
            return rebind;
        }
        return rebind.copyWith(methodType, permuteArgumentsForm);
    }
    
    private static int findFirstDupOrDrop(final int[] array, final int n) {
        if (n < 63) {
            long n2 = 0L;
            for (int i = 0; i < array.length; ++i) {
                final int n3 = array[i];
                if (n3 >= n) {
                    return array.length;
                }
                final long n4 = 1L << n3;
                if ((n2 & n4) != 0x0L) {
                    return i;
                }
                n2 |= n4;
            }
            if (n2 == (1L << n) - 1L) {
                assert Long.numberOfTrailingZeros(Long.lowestOneBit(~n2)) == n;
                return 0;
            }
            else {
                final int numberOfTrailingZeros = Long.numberOfTrailingZeros(Long.lowestOneBit(~n2));
                assert numberOfTrailingZeros <= n;
                if (numberOfTrailingZeros == n) {
                    return 0;
                }
                return ~numberOfTrailingZeros;
            }
        }
        else {
            final BitSet set = new BitSet(n);
            for (int j = 0; j < array.length; ++j) {
                final int n5 = array[j];
                if (n5 >= n) {
                    return array.length;
                }
                if (set.get(n5)) {
                    return j;
                }
                set.set(n5);
            }
            final int nextClearBit = set.nextClearBit(0);
            assert nextClearBit <= n;
            if (nextClearBit == n) {
                return 0;
            }
            return ~nextClearBit;
        }
    }
    
    private static boolean permuteArgumentChecks(final int[] array, final MethodType methodType, final MethodType methodType2) {
        if (methodType.returnType() != methodType2.returnType()) {
            throw MethodHandleStatics.newIllegalArgumentException("return types do not match", methodType2, methodType);
        }
        if (array.length == methodType2.parameterCount()) {
            final int parameterCount = methodType.parameterCount();
            boolean b = false;
            for (int i = 0; i < array.length; ++i) {
                final int n = array[i];
                if (n < 0 || n >= parameterCount) {
                    b = true;
                    break;
                }
                if (methodType.parameterType(n) != methodType2.parameterType(i)) {
                    throw MethodHandleStatics.newIllegalArgumentException("parameter types do not match after reorder", methodType2, methodType);
                }
            }
            if (!b) {
                return true;
            }
        }
        throw MethodHandleStatics.newIllegalArgumentException("bad reorder array: " + Arrays.toString(array));
    }
    
    public static MethodHandle constant(final Class<?> clazz, Object convert) {
        if (clazz.isPrimitive()) {
            if (clazz == Void.TYPE) {
                throw MethodHandleStatics.newIllegalArgumentException("void type");
            }
            final Wrapper forPrimitiveType = Wrapper.forPrimitiveType(clazz);
            convert = forPrimitiveType.convert(convert, clazz);
            if (forPrimitiveType.zero().equals(convert)) {
                return zero(forPrimitiveType, clazz);
            }
            return insertArguments(identity(clazz), 0, convert);
        }
        else {
            if (convert == null) {
                return zero(Wrapper.OBJECT, clazz);
            }
            return identity(clazz).bindTo(convert);
        }
    }
    
    public static MethodHandle identity(final Class<?> clazz) {
        final Wrapper wrapper = clazz.isPrimitive() ? Wrapper.forPrimitiveType(clazz) : Wrapper.OBJECT;
        final int ordinal = wrapper.ordinal();
        MethodHandle setCachedMethodHandle = MethodHandles.IDENTITY_MHS[ordinal];
        if (setCachedMethodHandle == null) {
            setCachedMethodHandle = setCachedMethodHandle(MethodHandles.IDENTITY_MHS, ordinal, makeIdentity(wrapper.primitiveType()));
        }
        if (setCachedMethodHandle.type().returnType() == clazz) {
            return setCachedMethodHandle;
        }
        assert wrapper == Wrapper.OBJECT;
        return makeIdentity(clazz);
    }
    
    private static MethodHandle makeIdentity(final Class<?> clazz) {
        return MethodHandleImpl.makeIntrinsic(MethodType.methodType(clazz, clazz), LambdaForm.identityForm(LambdaForm.BasicType.basicType(clazz)), MethodHandleImpl.Intrinsic.IDENTITY);
    }
    
    private static MethodHandle zero(final Wrapper wrapper, final Class<?> clazz) {
        final int ordinal = wrapper.ordinal();
        MethodHandle setCachedMethodHandle = MethodHandles.ZERO_MHS[ordinal];
        if (setCachedMethodHandle == null) {
            setCachedMethodHandle = setCachedMethodHandle(MethodHandles.ZERO_MHS, ordinal, makeZero(wrapper.primitiveType()));
        }
        if (setCachedMethodHandle.type().returnType() == clazz) {
            return setCachedMethodHandle;
        }
        assert wrapper == Wrapper.OBJECT;
        return makeZero(clazz);
    }
    
    private static MethodHandle makeZero(final Class<?> clazz) {
        return MethodHandleImpl.makeIntrinsic(MethodType.methodType(clazz), LambdaForm.zeroForm(LambdaForm.BasicType.basicType(clazz)), MethodHandleImpl.Intrinsic.ZERO);
    }
    
    private static synchronized MethodHandle setCachedMethodHandle(final MethodHandle[] array, final int n, final MethodHandle methodHandle) {
        final MethodHandle methodHandle2 = array[n];
        if (methodHandle2 != null) {
            return methodHandle2;
        }
        return array[n] = methodHandle;
    }
    
    public static MethodHandle insertArguments(final MethodHandle methodHandle, final int n, final Object... array) {
        final int length = array.length;
        final Class<?>[] insertArgumentsChecks = insertArgumentsChecks(methodHandle, length, n);
        if (length == 0) {
            return methodHandle;
        }
        BoundMethodHandle boundMethodHandle = methodHandle.rebind();
        for (int i = 0; i < length; ++i) {
            final Object o = array[i];
            final Class<?> clazz = insertArgumentsChecks[n + i];
            if (clazz.isPrimitive()) {
                boundMethodHandle = insertArgumentPrimitive(boundMethodHandle, n, clazz, o);
            }
            else {
                boundMethodHandle = boundMethodHandle.bindArgumentL(n, clazz.cast(o));
            }
        }
        return boundMethodHandle;
    }
    
    private static BoundMethodHandle insertArgumentPrimitive(final BoundMethodHandle boundMethodHandle, final int n, final Class<?> clazz, Object convert) {
        final Wrapper forPrimitiveType = Wrapper.forPrimitiveType(clazz);
        convert = forPrimitiveType.convert(convert, clazz);
        switch (forPrimitiveType) {
            case INT: {
                return boundMethodHandle.bindArgumentI(n, (int)convert);
            }
            case LONG: {
                return boundMethodHandle.bindArgumentJ(n, (long)convert);
            }
            case FLOAT: {
                return boundMethodHandle.bindArgumentF(n, (float)convert);
            }
            case DOUBLE: {
                return boundMethodHandle.bindArgumentD(n, (double)convert);
            }
            default: {
                return boundMethodHandle.bindArgumentI(n, ValueConversions.widenSubword(convert));
            }
        }
    }
    
    private static Class<?>[] insertArgumentsChecks(final MethodHandle methodHandle, final int n, final int n2) throws RuntimeException {
        final MethodType type = methodHandle.type();
        final int n3 = type.parameterCount() - n;
        if (n3 < 0) {
            throw MethodHandleStatics.newIllegalArgumentException("too many values to insert");
        }
        if (n2 < 0 || n2 > n3) {
            throw MethodHandleStatics.newIllegalArgumentException("no argument type to append");
        }
        return type.ptypes();
    }
    
    public static MethodHandle dropArguments(final MethodHandle methodHandle, final int n, final List<Class<?>> list) {
        final List<Class<?>> copyTypes = copyTypes(list);
        final MethodType type = methodHandle.type();
        final int dropArgumentChecks = dropArgumentChecks(type, n, copyTypes);
        final MethodType insertParameterTypes = type.insertParameterTypes(n, copyTypes);
        if (dropArgumentChecks == 0) {
            return methodHandle;
        }
        final BoundMethodHandle rebind = methodHandle.rebind();
        LambdaForm lambdaForm = rebind.form;
        int n2 = 1 + n;
        final Iterator<Class<?>> iterator = copyTypes.iterator();
        while (iterator.hasNext()) {
            lambdaForm = lambdaForm.editor().addArgumentForm(n2++, LambdaForm.BasicType.basicType(iterator.next()));
        }
        return rebind.copyWith(insertParameterTypes, lambdaForm);
    }
    
    private static List<Class<?>> copyTypes(final List<Class<?>> list) {
        final Object[] array = list.toArray();
        return Arrays.asList((Class<?>[])Arrays.copyOf(array, array.length, (Class<? extends Object[]>)Class[].class));
    }
    
    private static int dropArgumentChecks(final MethodType methodType, final int n, final List<Class<?>> list) {
        final int size = list.size();
        MethodType.checkSlotCount(size);
        final int parameterCount = methodType.parameterCount();
        final int n2 = parameterCount + size;
        if (n < 0 || n > parameterCount) {
            throw MethodHandleStatics.newIllegalArgumentException("no argument type to remove" + Arrays.asList(methodType, n, list, n2, parameterCount));
        }
        return size;
    }
    
    public static MethodHandle dropArguments(final MethodHandle methodHandle, final int n, final Class<?>... array) {
        return dropArguments(methodHandle, n, Arrays.asList(array));
    }
    
    public static MethodHandle filterArguments(final MethodHandle methodHandle, final int n, final MethodHandle... array) {
        filterArgumentsCheckArity(methodHandle, n, array);
        MethodHandle filterArgument = methodHandle;
        int n2 = n - 1;
        for (final MethodHandle methodHandle2 : array) {
            ++n2;
            if (methodHandle2 != null) {
                filterArgument = filterArgument(filterArgument, n2, methodHandle2);
            }
        }
        return filterArgument;
    }
    
    static MethodHandle filterArgument(final MethodHandle methodHandle, final int n, final MethodHandle methodHandle2) {
        filterArgumentChecks(methodHandle, n, methodHandle2);
        final MethodType type = methodHandle.type();
        final MethodType type2 = methodHandle2.type();
        final BoundMethodHandle rebind = methodHandle.rebind();
        final Class<?> parameterType = type2.parameterType(0);
        return rebind.copyWithExtendL(type.changeParameterType(n, parameterType), rebind.editor().filterArgumentForm(1 + n, LambdaForm.BasicType.basicType(parameterType)), methodHandle2);
    }
    
    private static void filterArgumentsCheckArity(final MethodHandle methodHandle, final int n, final MethodHandle[] array) {
        if (n + array.length > methodHandle.type().parameterCount()) {
            throw MethodHandleStatics.newIllegalArgumentException("too many filters");
        }
    }
    
    private static void filterArgumentChecks(final MethodHandle methodHandle, final int n, final MethodHandle methodHandle2) throws RuntimeException {
        final MethodType type = methodHandle.type();
        final MethodType type2 = methodHandle2.type();
        if (type2.parameterCount() != 1 || type2.returnType() != type.parameterType(n)) {
            throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", type, type2);
        }
    }
    
    public static MethodHandle collectArguments(final MethodHandle methodHandle, final int n, final MethodHandle methodHandle2) {
        final MethodType collectArgumentsChecks = collectArgumentsChecks(methodHandle, n, methodHandle2);
        final MethodType type = methodHandle2.type();
        final BoundMethodHandle rebind = methodHandle.rebind();
        if (type.returnType().isArray() && methodHandle2.intrinsicName() == MethodHandleImpl.Intrinsic.NEW_ARRAY) {
            final LambdaForm collectArgumentArrayForm = rebind.editor().collectArgumentArrayForm(1 + n, methodHandle2);
            if (collectArgumentArrayForm != null) {
                return rebind.copyWith(collectArgumentsChecks, collectArgumentArrayForm);
            }
        }
        return rebind.copyWithExtendL(collectArgumentsChecks, rebind.editor().collectArgumentsForm(1 + n, type.basicType()), methodHandle2);
    }
    
    private static MethodType collectArgumentsChecks(final MethodHandle methodHandle, final int n, final MethodHandle methodHandle2) throws RuntimeException {
        final MethodType type = methodHandle.type();
        final MethodType type2 = methodHandle2.type();
        final Class<?> returnType = type2.returnType();
        final List<Class<?>> parameterList = type2.parameterList();
        if (returnType == Void.TYPE) {
            return type.insertParameterTypes(n, parameterList);
        }
        if (returnType != type.parameterType(n)) {
            throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", type, type2);
        }
        return type.dropParameterTypes(n, n + 1).insertParameterTypes(n, parameterList);
    }
    
    public static MethodHandle filterReturnValue(final MethodHandle methodHandle, final MethodHandle methodHandle2) {
        final MethodType type = methodHandle.type();
        final MethodType type2 = methodHandle2.type();
        filterReturnValueChecks(type, type2);
        final BoundMethodHandle rebind = methodHandle.rebind();
        return rebind.copyWithExtendL(type.changeReturnType(type2.returnType()), rebind.editor().filterReturnForm(LambdaForm.BasicType.basicType(type2.returnType()), false), methodHandle2);
    }
    
    private static void filterReturnValueChecks(final MethodType methodType, final MethodType methodType2) throws RuntimeException {
        final Class<?> returnType = methodType.returnType();
        final int parameterCount = methodType2.parameterCount();
        if (parameterCount == 0) {
            if (returnType == Void.TYPE) {
                return;
            }
        }
        else if (returnType == methodType2.parameterType(0) && parameterCount == 1) {
            return;
        }
        throw MethodHandleStatics.newIllegalArgumentException("target and filter types do not match", methodType, methodType2);
    }
    
    public static MethodHandle foldArguments(final MethodHandle methodHandle, final MethodHandle methodHandle2) {
        final int n = 0;
        final MethodType type = methodHandle.type();
        final MethodType type2 = methodHandle2.type();
        final Class<?> foldArgumentChecks = foldArgumentChecks(n, type, type2);
        final BoundMethodHandle rebind = methodHandle.rebind();
        final boolean b = foldArgumentChecks == Void.TYPE;
        final LambdaForm foldArgumentsForm = rebind.editor().foldArgumentsForm(1 + n, b, type2.basicType());
        MethodType dropParameterTypes = type;
        if (!b) {
            dropParameterTypes = dropParameterTypes.dropParameterTypes(n, n + 1);
        }
        return rebind.copyWithExtendL(dropParameterTypes, foldArgumentsForm, methodHandle2);
    }
    
    private static Class<?> foldArgumentChecks(final int n, final MethodType methodType, final MethodType methodType2) {
        final int parameterCount = methodType2.parameterCount();
        final Class<?> returnType = methodType2.returnType();
        final int n2 = (returnType != Void.TYPE) ? 1 : 0;
        final int n3 = n + n2;
        int n4 = (methodType.parameterCount() >= n3 + parameterCount) ? 1 : 0;
        if (n4 != 0 && !methodType2.parameterList().equals(methodType.parameterList().subList(n3, n3 + parameterCount))) {
            n4 = 0;
        }
        if (n4 != 0 && n2 != 0 && methodType2.returnType() != methodType.parameterType(0)) {
            n4 = 0;
        }
        if (n4 == 0) {
            throw misMatchedTypes("target and combiner types", methodType, methodType2);
        }
        return returnType;
    }
    
    public static MethodHandle guardWithTest(MethodHandle dropArguments, final MethodHandle methodHandle, final MethodHandle methodHandle2) {
        final MethodType type = dropArguments.type();
        final MethodType type2 = methodHandle.type();
        final MethodType type3 = methodHandle2.type();
        if (!type2.equals((Object)type3)) {
            throw misMatchedTypes("target and fallback types", type2, type3);
        }
        if (type.returnType() != Boolean.TYPE) {
            throw MethodHandleStatics.newIllegalArgumentException("guard type is not a predicate " + type);
        }
        final List<Class<?>> parameterList = type2.parameterList();
        final List<Class<?>> parameterList2 = type.parameterList();
        if (!parameterList.equals(parameterList2)) {
            final int size = parameterList2.size();
            final int size2 = parameterList.size();
            if (size >= size2 || !parameterList.subList(0, size).equals(parameterList2)) {
                throw misMatchedTypes("target and test types", type2, type);
            }
            dropArguments = dropArguments(dropArguments, size, parameterList.subList(size, size2));
            dropArguments.type();
        }
        return MethodHandleImpl.makeGuardWithTest(dropArguments, methodHandle, methodHandle2);
    }
    
    static RuntimeException misMatchedTypes(final String s, final MethodType methodType, final MethodType methodType2) {
        return MethodHandleStatics.newIllegalArgumentException(s + " must match: " + methodType + " != " + methodType2);
    }
    
    public static MethodHandle catchException(final MethodHandle methodHandle, final Class<? extends Throwable> clazz, MethodHandle dropArguments) {
        final MethodType type = methodHandle.type();
        final MethodType type2 = dropArguments.type();
        if (type2.parameterCount() < 1 || !type2.parameterType(0).isAssignableFrom(clazz)) {
            throw MethodHandleStatics.newIllegalArgumentException("handler does not accept exception type " + clazz);
        }
        if (type2.returnType() != type.returnType()) {
            throw misMatchedTypes("target and handler return types", type, type2);
        }
        final List<Class<?>> parameterList = type.parameterList();
        final List<Class<?>> parameterList2 = type2.parameterList();
        final List<Class<?>> subList = parameterList2.subList(1, parameterList2.size());
        if (!parameterList.equals(subList)) {
            final int size = subList.size();
            final int size2 = parameterList.size();
            if (size >= size2 || !parameterList.subList(0, size).equals(subList)) {
                throw misMatchedTypes("target and handler types", type, type2);
            }
            dropArguments = dropArguments(dropArguments, 1 + size, parameterList.subList(size, size2));
            dropArguments.type();
        }
        return MethodHandleImpl.makeGuardWithCatch(methodHandle, clazz, dropArguments);
    }
    
    public static MethodHandle throwException(final Class<?> clazz, final Class<? extends Throwable> clazz2) {
        if (!Throwable.class.isAssignableFrom(clazz2)) {
            throw new ClassCastException(clazz2.getName());
        }
        return MethodHandleImpl.throwException(MethodType.methodType(clazz, clazz2));
    }
    
    static {
        IMPL_NAMES = MemberName.getFactory();
        MethodHandleImpl.initStatics();
        ACCESS_PERMISSION = new ReflectPermission("suppressAccessChecks");
        IDENTITY_MHS = new MethodHandle[Wrapper.values().length];
        ZERO_MHS = new MethodHandle[Wrapper.values().length];
    }
    
    public static final class Lookup
    {
        private final Class<?> lookupClass;
        private final int allowedModes;
        public static final int PUBLIC = 1;
        public static final int PRIVATE = 2;
        public static final int PROTECTED = 4;
        public static final int PACKAGE = 8;
        private static final int ALL_MODES = 15;
        private static final int TRUSTED = -1;
        static final Lookup PUBLIC_LOOKUP;
        static final Lookup IMPL_LOOKUP;
        private static final boolean ALLOW_NESTMATE_ACCESS = false;
        static ConcurrentHashMap<MemberName, DirectMethodHandle> LOOKASIDE_TABLE;
        static final /* synthetic */ boolean $assertionsDisabled;
        
        private static int fixmods(int n) {
            n &= 0x7;
            return (n != 0) ? n : 8;
        }
        
        public Class<?> lookupClass() {
            return this.lookupClass;
        }
        
        private Class<?> lookupClassOrNull() {
            return (this.allowedModes == -1) ? null : this.lookupClass;
        }
        
        public int lookupModes() {
            return this.allowedModes & 0xF;
        }
        
        Lookup(final Class<?> clazz) {
            this(clazz, 15);
            checkUnprivilegedlookupClass(clazz, 15);
        }
        
        private Lookup(final Class<?> lookupClass, final int allowedModes) {
            this.lookupClass = lookupClass;
            this.allowedModes = allowedModes;
        }
        
        public Lookup in(final Class<?> clazz) {
            clazz.getClass();
            if (this.allowedModes == -1) {
                return new Lookup(clazz, 15);
            }
            if (clazz == this.lookupClass) {
                return this;
            }
            int n = this.allowedModes & 0xB;
            if ((n & 0x8) != 0x0 && !VerifyAccess.isSamePackage(this.lookupClass, clazz)) {
                n &= 0xFFFFFFF5;
            }
            if ((n & 0x2) != 0x0 && !VerifyAccess.isSamePackageMember(this.lookupClass, clazz)) {
                n &= 0xFFFFFFFD;
            }
            if ((n & 0x1) != 0x0 && !VerifyAccess.isClassAccessible(clazz, this.lookupClass, this.allowedModes)) {
                n = 0;
            }
            checkUnprivilegedlookupClass(clazz, n);
            return new Lookup(clazz, n);
        }
        
        private static void checkUnprivilegedlookupClass(final Class<?> clazz, final int n) {
            final String name = clazz.getName();
            if (name.startsWith("java.lang.invoke.")) {
                throw MethodHandleStatics.newIllegalArgumentException("illegal lookupClass: " + clazz);
            }
            if (n == 15 && clazz.getClassLoader() == null && (name.startsWith("java.") || (name.startsWith("sun.") && !name.startsWith("sun.invoke.") && !name.equals("sun.reflect.ReflectionFactory")))) {
                throw MethodHandleStatics.newIllegalArgumentException("illegal lookupClass: " + clazz);
            }
        }
        
        @Override
        public String toString() {
            final String name = this.lookupClass.getName();
            switch (this.allowedModes) {
                case 0: {
                    return name + "/noaccess";
                }
                case 1: {
                    return name + "/public";
                }
                case 9: {
                    return name + "/package";
                }
                case 11: {
                    return name + "/private";
                }
                case 15: {
                    return name;
                }
                case -1: {
                    return "/trusted";
                }
                default: {
                    final String string = name + "/" + Integer.toHexString(this.allowedModes);
                    assert false : string;
                    return string;
                }
            }
        }
        
        public MethodHandle findStatic(final Class<?> clazz, final String s, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
            final MemberName resolveOrFail = this.resolveOrFail((byte)6, clazz, s, methodType);
            return this.getDirectMethod((byte)6, clazz, resolveOrFail, this.findBoundCallerClass(resolveOrFail));
        }
        
        public MethodHandle findVirtual(final Class<?> clazz, final String s, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
            if (clazz == MethodHandle.class) {
                final MethodHandle virtualForMH = this.findVirtualForMH(s, methodType);
                if (virtualForMH != null) {
                    return virtualForMH;
                }
            }
            final int n = clazz.isInterface() ? 9 : 5;
            final MemberName resolveOrFail = this.resolveOrFail((byte)n, clazz, s, methodType);
            return this.getDirectMethod((byte)n, clazz, resolveOrFail, this.findBoundCallerClass(resolveOrFail));
        }
        
        private MethodHandle findVirtualForMH(final String s, final MethodType methodType) {
            if ("invoke".equals(s)) {
                return MethodHandles.invoker(methodType);
            }
            if ("invokeExact".equals(s)) {
                return MethodHandles.exactInvoker(methodType);
            }
            assert !MemberName.isMethodHandleInvokeName(s);
            return null;
        }
        
        public MethodHandle findConstructor(final Class<?> clazz, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
            if (clazz.isArray()) {
                throw new NoSuchMethodException("no constructor for array class: " + clazz.getName());
            }
            return this.getDirectConstructor(clazz, this.resolveOrFail((byte)8, clazz, "<init>", methodType));
        }
        
        public MethodHandle findSpecial(final Class<?> clazz, final String s, final MethodType methodType, final Class<?> clazz2) throws NoSuchMethodException, IllegalAccessException {
            this.checkSpecialCaller(clazz2);
            final Lookup in = this.in(clazz2);
            final MemberName resolveOrFail = in.resolveOrFail((byte)7, clazz, s, methodType);
            return in.getDirectMethod((byte)7, clazz, resolveOrFail, this.findBoundCallerClass(resolveOrFail));
        }
        
        public MethodHandle findGetter(final Class<?> clazz, final String s, final Class<?> clazz2) throws NoSuchFieldException, IllegalAccessException {
            return this.getDirectField((byte)1, clazz, this.resolveOrFail((byte)1, clazz, s, clazz2));
        }
        
        public MethodHandle findSetter(final Class<?> clazz, final String s, final Class<?> clazz2) throws NoSuchFieldException, IllegalAccessException {
            return this.getDirectField((byte)3, clazz, this.resolveOrFail((byte)3, clazz, s, clazz2));
        }
        
        public MethodHandle findStaticGetter(final Class<?> clazz, final String s, final Class<?> clazz2) throws NoSuchFieldException, IllegalAccessException {
            return this.getDirectField((byte)2, clazz, this.resolveOrFail((byte)2, clazz, s, clazz2));
        }
        
        public MethodHandle findStaticSetter(final Class<?> clazz, final String s, final Class<?> clazz2) throws NoSuchFieldException, IllegalAccessException {
            return this.getDirectField((byte)4, clazz, this.resolveOrFail((byte)4, clazz, s, clazz2));
        }
        
        public MethodHandle bind(final Object o, final String s, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
            final Class<?> class1 = o.getClass();
            final MemberName resolveOrFail = this.resolveOrFail((byte)7, class1, s, methodType);
            return this.getDirectMethodNoRestrict((byte)7, class1, resolveOrFail, this.findBoundCallerClass(resolveOrFail)).bindArgumentL(0, o).setVarargs(resolveOrFail);
        }
        
        public MethodHandle unreflect(final Method method) throws IllegalAccessException {
            if (method.getDeclaringClass() == MethodHandle.class) {
                final MethodHandle unreflectForMH = this.unreflectForMH(method);
                if (unreflectForMH != null) {
                    return unreflectForMH;
                }
            }
            final MemberName memberName = new MemberName(method);
            byte referenceKind = memberName.getReferenceKind();
            if (referenceKind == 7) {
                referenceKind = 5;
            }
            assert memberName.isMethod();
            return (method.isAccessible() ? Lookup.IMPL_LOOKUP : this).getDirectMethodNoSecurityManager(referenceKind, memberName.getDeclaringClass(), memberName, this.findBoundCallerClass(memberName));
        }
        
        private MethodHandle unreflectForMH(final Method method) {
            if (MemberName.isMethodHandleInvokeName(method.getName())) {
                return MethodHandleImpl.fakeMethodHandleInvoke(new MemberName(method));
            }
            return null;
        }
        
        public MethodHandle unreflectSpecial(final Method method, final Class<?> clazz) throws IllegalAccessException {
            this.checkSpecialCaller(clazz);
            final Lookup in = this.in(clazz);
            final MemberName memberName = new MemberName(method, true);
            assert memberName.isMethod();
            return in.getDirectMethodNoSecurityManager((byte)7, memberName.getDeclaringClass(), memberName, this.findBoundCallerClass(memberName));
        }
        
        public MethodHandle unreflectConstructor(final Constructor<?> constructor) throws IllegalAccessException {
            final MemberName memberName = new MemberName(constructor);
            assert memberName.isConstructor();
            return (constructor.isAccessible() ? Lookup.IMPL_LOOKUP : this).getDirectConstructorNoSecurityManager(memberName.getDeclaringClass(), memberName);
        }
        
        public MethodHandle unreflectGetter(final Field field) throws IllegalAccessException {
            return this.unreflectField(field, false);
        }
        
        private MethodHandle unreflectField(final Field field, final boolean b) throws IllegalAccessException {
            final MemberName memberName = new MemberName(field, b);
            if (!Lookup.$assertionsDisabled) {
                if (b) {
                    if (MethodHandleNatives.refKindIsSetter(memberName.getReferenceKind())) {
                        return (field.isAccessible() ? Lookup.IMPL_LOOKUP : this).getDirectFieldNoSecurityManager(memberName.getReferenceKind(), field.getDeclaringClass(), memberName);
                    }
                }
                else if (MethodHandleNatives.refKindIsGetter(memberName.getReferenceKind())) {
                    return (field.isAccessible() ? Lookup.IMPL_LOOKUP : this).getDirectFieldNoSecurityManager(memberName.getReferenceKind(), field.getDeclaringClass(), memberName);
                }
                throw new AssertionError();
            }
            return (field.isAccessible() ? Lookup.IMPL_LOOKUP : this).getDirectFieldNoSecurityManager(memberName.getReferenceKind(), field.getDeclaringClass(), memberName);
        }
        
        public MethodHandle unreflectSetter(final Field field) throws IllegalAccessException {
            return this.unreflectField(field, true);
        }
        
        public MethodHandleInfo revealDirect(final MethodHandle methodHandle) {
            final MemberName internalMemberName = methodHandle.internalMemberName();
            if (internalMemberName == null || (!internalMemberName.isResolved() && !internalMemberName.isMethodHandleInvoke())) {
                throw MethodHandleStatics.newIllegalArgumentException("not a direct method handle");
            }
            final Class<?> declaringClass = internalMemberName.getDeclaringClass();
            byte referenceKind = internalMemberName.getReferenceKind();
            assert MethodHandleNatives.refKindIsValid(referenceKind);
            if (referenceKind == 7 && !methodHandle.isInvokeSpecial()) {
                referenceKind = 5;
            }
            if (referenceKind == 5 && declaringClass.isInterface()) {
                referenceKind = 9;
            }
            try {
                this.checkAccess(referenceKind, declaringClass, internalMemberName);
                this.checkSecurityManager(declaringClass, internalMemberName);
            }
            catch (IllegalAccessException ex) {
                throw new IllegalArgumentException(ex);
            }
            if (this.allowedModes != -1 && internalMemberName.isCallerSensitive()) {
                final Class<?> internalCallerClass = methodHandle.internalCallerClass();
                if (!this.hasPrivateAccess() || internalCallerClass != this.lookupClass()) {
                    throw new IllegalArgumentException("method handle is caller sensitive: " + internalCallerClass);
                }
            }
            return new InfoFromMemberName(this, internalMemberName, referenceKind);
        }
        
        MemberName resolveOrFail(final byte b, final Class<?> clazz, final String s, final Class<?> clazz2) throws NoSuchFieldException, IllegalAccessException {
            this.checkSymbolicClass(clazz);
            s.getClass();
            clazz2.getClass();
            return MethodHandles.IMPL_NAMES.resolveOrFail(b, new MemberName(clazz, s, clazz2, b), this.lookupClassOrNull(), NoSuchFieldException.class);
        }
        
        MemberName resolveOrFail(final byte b, final Class<?> clazz, final String s, final MethodType methodType) throws NoSuchMethodException, IllegalAccessException {
            this.checkSymbolicClass(clazz);
            s.getClass();
            methodType.getClass();
            this.checkMethodName(b, s);
            return MethodHandles.IMPL_NAMES.resolveOrFail(b, new MemberName(clazz, s, methodType, b), this.lookupClassOrNull(), NoSuchMethodException.class);
        }
        
        MemberName resolveOrFail(final byte b, final MemberName memberName) throws ReflectiveOperationException {
            this.checkSymbolicClass(memberName.getDeclaringClass());
            memberName.getName().getClass();
            memberName.getType().getClass();
            return MethodHandles.IMPL_NAMES.resolveOrFail(b, memberName, this.lookupClassOrNull(), ReflectiveOperationException.class);
        }
        
        void checkSymbolicClass(final Class<?> clazz) throws IllegalAccessException {
            clazz.getClass();
            final Class<?> lookupClassOrNull = this.lookupClassOrNull();
            if (lookupClassOrNull != null && !VerifyAccess.isClassAccessible(clazz, lookupClassOrNull, this.allowedModes)) {
                throw new MemberName(clazz).makeAccessException("symbolic reference class is not public", this);
            }
        }
        
        void checkMethodName(final byte b, final String s) throws NoSuchMethodException {
            if (s.startsWith("<") && b != 8) {
                throw new NoSuchMethodException("illegal method name: " + s);
            }
        }
        
        Class<?> findBoundCallerClass(final MemberName memberName) throws IllegalAccessException {
            Class<?> lookupClass = null;
            if (MethodHandleNatives.isCallerSensitive(memberName)) {
                if (!this.hasPrivateAccess()) {
                    throw new IllegalAccessException("Attempt to lookup caller-sensitive method using restricted lookup object");
                }
                lookupClass = this.lookupClass;
            }
            return lookupClass;
        }
        
        private boolean hasPrivateAccess() {
            return (this.allowedModes & 0x2) != 0x0;
        }
        
        void checkSecurityManager(final Class<?> clazz, final MemberName memberName) {
            final SecurityManager securityManager = System.getSecurityManager();
            if (securityManager == null) {
                return;
            }
            if (this.allowedModes == -1) {
                return;
            }
            final boolean hasPrivateAccess = this.hasPrivateAccess();
            if (!hasPrivateAccess || !VerifyAccess.classLoaderIsAncestor(this.lookupClass, clazz)) {
                ReflectUtil.checkPackageAccess(clazz);
            }
            if (memberName.isPublic()) {
                return;
            }
            if (!hasPrivateAccess) {
                securityManager.checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
            }
            final Class<?> declaringClass = memberName.getDeclaringClass();
            if (!hasPrivateAccess && declaringClass != clazz) {
                ReflectUtil.checkPackageAccess(declaringClass);
            }
        }
        
        void checkMethod(final byte b, final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
            final boolean b2 = b == 6;
            String s;
            if (memberName.isConstructor()) {
                s = "expected a method, not a constructor";
            }
            else if (!memberName.isMethod()) {
                s = "expected a method";
            }
            else {
                if (b2 == memberName.isStatic()) {
                    this.checkAccess(b, clazz, memberName);
                    return;
                }
                s = (b2 ? "expected a static method" : "expected a non-static method");
            }
            throw memberName.makeAccessException(s, this);
        }
        
        void checkField(final byte b, final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
            final boolean b2 = !MethodHandleNatives.refKindHasReceiver(b);
            if (b2 != memberName.isStatic()) {
                throw memberName.makeAccessException(b2 ? "expected a static field" : "expected a non-static field", this);
            }
            this.checkAccess(b, clazz, memberName);
        }
        
        void checkAccess(final byte b, final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
            assert memberName.referenceKindIsConsistentWith(b) && MethodHandleNatives.refKindIsValid(b) && MethodHandleNatives.refKindIsField(b) == memberName.isField();
            final int allowedModes = this.allowedModes;
            if (allowedModes == -1) {
                return;
            }
            int modifiers = memberName.getModifiers();
            if (Modifier.isProtected(modifiers) && b == 5 && memberName.getDeclaringClass() == Object.class && memberName.getName().equals("clone") && clazz.isArray()) {
                modifiers ^= 0x5;
            }
            if (Modifier.isProtected(modifiers) && b == 8) {
                modifiers ^= 0x4;
            }
            if (Modifier.isFinal(modifiers) && MethodHandleNatives.refKindIsSetter(b)) {
                throw memberName.makeAccessException("unexpected set of a final field", this);
            }
            if (Modifier.isPublic(modifiers) && Modifier.isPublic(clazz.getModifiers()) && allowedModes != 0) {
                return;
            }
            final int fixmods = fixmods(modifiers);
            if ((fixmods & allowedModes) != 0x0) {
                if (VerifyAccess.isMemberAccessible(clazz, memberName.getDeclaringClass(), modifiers, this.lookupClass(), allowedModes)) {
                    return;
                }
            }
            else if ((fixmods & 0x4) != 0x0 && (allowedModes & 0x8) != 0x0 && VerifyAccess.isSamePackage(memberName.getDeclaringClass(), this.lookupClass())) {
                return;
            }
            throw memberName.makeAccessException(this.accessFailedMessage(clazz, memberName), this);
        }
        
        String accessFailedMessage(final Class<?> clazz, final MemberName memberName) {
            final Class<?> declaringClass = memberName.getDeclaringClass();
            final int modifiers = memberName.getModifiers();
            boolean b = Modifier.isPublic(declaringClass.getModifiers()) && (declaringClass == clazz || Modifier.isPublic(clazz.getModifiers()));
            if (!b && (this.allowedModes & 0x8) != 0x0) {
                b = (VerifyAccess.isClassAccessible(declaringClass, this.lookupClass(), 15) && (declaringClass == clazz || VerifyAccess.isClassAccessible(clazz, this.lookupClass(), 15)));
            }
            if (!b) {
                return "class is not public";
            }
            if (Modifier.isPublic(modifiers)) {
                return "access to public member failed";
            }
            if (Modifier.isPrivate(modifiers)) {
                return "member is private";
            }
            if (Modifier.isProtected(modifiers)) {
                return "member is protected";
            }
            return "member is private to package";
        }
        
        private void checkSpecialCaller(final Class<?> clazz) throws IllegalAccessException {
            if (this.allowedModes == -1) {
                return;
            }
            if (!this.hasPrivateAccess() || clazz != this.lookupClass()) {
                throw new MemberName(clazz).makeAccessException("no private access for invokespecial", this);
            }
        }
        
        private boolean restrictProtectedReceiver(final MemberName memberName) {
            return memberName.isProtected() && !memberName.isStatic() && this.allowedModes != -1 && memberName.getDeclaringClass() != this.lookupClass() && !VerifyAccess.isSamePackage(memberName.getDeclaringClass(), this.lookupClass());
        }
        
        private MethodHandle restrictReceiver(final MemberName memberName, final DirectMethodHandle directMethodHandle, final Class<?> clazz) throws IllegalAccessException {
            assert !memberName.isStatic();
            if (!memberName.getDeclaringClass().isAssignableFrom(clazz)) {
                throw memberName.makeAccessException("caller class must be a subclass below the method", clazz);
            }
            final MethodType type = directMethodHandle.type();
            if (type.parameterType(0) == clazz) {
                return directMethodHandle;
            }
            final MethodType changeParameterType = type.changeParameterType(0, clazz);
            assert !directMethodHandle.isVarargsCollector();
            assert directMethodHandle.viewAsTypeChecks(changeParameterType, true);
            return directMethodHandle.copyWith(changeParameterType, directMethodHandle.form);
        }
        
        private MethodHandle getDirectMethod(final byte b, final Class<?> clazz, final MemberName memberName, final Class<?> clazz2) throws IllegalAccessException {
            return this.getDirectMethodCommon(b, clazz, memberName, true, true, clazz2);
        }
        
        private MethodHandle getDirectMethodNoRestrict(final byte b, final Class<?> clazz, final MemberName memberName, final Class<?> clazz2) throws IllegalAccessException {
            return this.getDirectMethodCommon(b, clazz, memberName, true, false, clazz2);
        }
        
        private MethodHandle getDirectMethodNoSecurityManager(final byte b, final Class<?> clazz, final MemberName memberName, final Class<?> clazz2) throws IllegalAccessException {
            return this.getDirectMethodCommon(b, clazz, memberName, false, true, clazz2);
        }
        
        private MethodHandle getDirectMethodCommon(final byte b, Class<?> clazz, MemberName varargs, final boolean b2, final boolean b3, final Class<?> clazz2) throws IllegalAccessException {
            this.checkMethod(b, clazz, varargs);
            if (b2) {
                this.checkSecurityManager(clazz, varargs);
            }
            assert !varargs.isMethodHandleInvoke();
            if (b == 7 && clazz != this.lookupClass() && !clazz.isInterface() && clazz != this.lookupClass().getSuperclass() && clazz.isAssignableFrom(this.lookupClass())) {
                assert !varargs.getName().equals("<init>");
                Class<?> clazz3 = this.lookupClass();
                MemberName resolveOrNull;
                do {
                    clazz3 = clazz3.getSuperclass();
                    resolveOrNull = MethodHandles.IMPL_NAMES.resolveOrNull(b, new MemberName(clazz3, varargs.getName(), varargs.getMethodType(), (byte)7), this.lookupClassOrNull());
                } while (resolveOrNull == null && clazz != clazz3);
                if (resolveOrNull == null) {
                    throw new InternalError(varargs.toString());
                }
                varargs = resolveOrNull;
                clazz = clazz3;
                this.checkMethod(b, clazz, varargs);
            }
            MethodHandle methodHandle;
            final DirectMethodHandle directMethodHandle = (DirectMethodHandle)(methodHandle = DirectMethodHandle.make(b, clazz, varargs));
            if (b3 && (b == 7 || (MethodHandleNatives.refKindHasReceiver(b) && this.restrictProtectedReceiver(varargs)))) {
                methodHandle = this.restrictReceiver(varargs, directMethodHandle, this.lookupClass());
            }
            return this.maybeBindCaller(varargs, methodHandle, clazz2).setVarargs(varargs);
        }
        
        private MethodHandle maybeBindCaller(final MemberName memberName, final MethodHandle methodHandle, final Class<?> clazz) throws IllegalAccessException {
            if (this.allowedModes == -1 || !MethodHandleNatives.isCallerSensitive(memberName)) {
                return methodHandle;
            }
            Class<?> lookupClass = this.lookupClass;
            if (!this.hasPrivateAccess()) {
                lookupClass = clazz;
            }
            return MethodHandleImpl.bindCaller(methodHandle, lookupClass);
        }
        
        private MethodHandle getDirectField(final byte b, final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
            return this.getDirectFieldCommon(b, clazz, memberName, true);
        }
        
        private MethodHandle getDirectFieldNoSecurityManager(final byte b, final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
            return this.getDirectFieldCommon(b, clazz, memberName, false);
        }
        
        private MethodHandle getDirectFieldCommon(final byte b, final Class<?> clazz, final MemberName memberName, final boolean b2) throws IllegalAccessException {
            this.checkField(b, clazz, memberName);
            if (b2) {
                this.checkSecurityManager(clazz, memberName);
            }
            final DirectMethodHandle make = DirectMethodHandle.make(clazz, memberName);
            if (MethodHandleNatives.refKindHasReceiver(b) && this.restrictProtectedReceiver(memberName)) {
                return this.restrictReceiver(memberName, make, this.lookupClass());
            }
            return make;
        }
        
        private MethodHandle getDirectConstructor(final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
            return this.getDirectConstructorCommon(clazz, memberName, true);
        }
        
        private MethodHandle getDirectConstructorNoSecurityManager(final Class<?> clazz, final MemberName memberName) throws IllegalAccessException {
            return this.getDirectConstructorCommon(clazz, memberName, false);
        }
        
        private MethodHandle getDirectConstructorCommon(final Class<?> clazz, final MemberName varargs, final boolean b) throws IllegalAccessException {
            assert varargs.isConstructor();
            this.checkAccess((byte)8, clazz, varargs);
            if (b) {
                this.checkSecurityManager(clazz, varargs);
            }
            assert !MethodHandleNatives.isCallerSensitive(varargs);
            return DirectMethodHandle.make(varargs).setVarargs(varargs);
        }
        
        MethodHandle linkMethodHandleConstant(final byte b, final Class<?> clazz, final String s, final Object o) throws ReflectiveOperationException {
            if (!(o instanceof Class) && !(o instanceof MethodType)) {
                throw new InternalError("unresolved MemberName");
            }
            final MemberName memberName = new MemberName(b, clazz, s, o);
            final DirectMethodHandle directMethodHandle = Lookup.LOOKASIDE_TABLE.get(memberName);
            if (directMethodHandle != null) {
                this.checkSymbolicClass(clazz);
                return directMethodHandle;
            }
            if (clazz == MethodHandle.class && b == 5) {
                final MethodHandle virtualForMH = this.findVirtualForMH(memberName.getName(), memberName.getMethodType());
                if (virtualForMH != null) {
                    return virtualForMH;
                }
            }
            final MemberName resolveOrFail = this.resolveOrFail(b, memberName);
            final MethodHandle directMethodForConstant = this.getDirectMethodForConstant(b, clazz, resolveOrFail);
            if (directMethodForConstant instanceof DirectMethodHandle && this.canBeCached(b, clazz, resolveOrFail)) {
                MemberName memberName2 = directMethodForConstant.internalMemberName();
                if (memberName2 != null) {
                    memberName2 = memberName2.asNormalOriginal();
                }
                if (memberName.equals(memberName2)) {
                    Lookup.LOOKASIDE_TABLE.put(memberName2, (DirectMethodHandle)directMethodForConstant);
                }
            }
            return directMethodForConstant;
        }
        
        private boolean canBeCached(final byte b, final Class<?> clazz, final MemberName memberName) {
            if (b == 7) {
                return false;
            }
            if (!Modifier.isPublic(clazz.getModifiers()) || !Modifier.isPublic(memberName.getDeclaringClass().getModifiers()) || !memberName.isPublic() || memberName.isCallerSensitive()) {
                return false;
            }
            final ClassLoader classLoader = clazz.getClassLoader();
            if (!VM.isSystemDomainLoader(classLoader)) {
                ClassLoader classLoader2 = ClassLoader.getSystemClassLoader();
                boolean b2 = false;
                while (classLoader2 != null) {
                    if (classLoader == classLoader2) {
                        b2 = true;
                        break;
                    }
                    classLoader2 = classLoader2.getParent();
                }
                if (!b2) {
                    return false;
                }
            }
            try {
                this.checkSecurityManager(clazz, MethodHandles.publicLookup().resolveOrFail(b, new MemberName(b, clazz, memberName.getName(), memberName.getType())));
            }
            catch (ReflectiveOperationException | SecurityException ex) {
                return false;
            }
            return true;
        }
        
        private MethodHandle getDirectMethodForConstant(final byte b, final Class<?> clazz, final MemberName memberName) throws ReflectiveOperationException {
            if (MethodHandleNatives.refKindIsField(b)) {
                return this.getDirectFieldNoSecurityManager(b, clazz, memberName);
            }
            if (MethodHandleNatives.refKindIsMethod(b)) {
                return this.getDirectMethodNoSecurityManager(b, clazz, memberName, this.lookupClass);
            }
            if (b == 8) {
                return this.getDirectConstructorNoSecurityManager(clazz, memberName);
            }
            throw MethodHandleStatics.newIllegalArgumentException("bad MethodHandle constant #" + memberName);
        }
        
        static {
            MethodHandles.IMPL_NAMES.getClass();
            PUBLIC_LOOKUP = new Lookup(Object.class, 1);
            IMPL_LOOKUP = new Lookup(Object.class, -1);
            Lookup.LOOKASIDE_TABLE = new ConcurrentHashMap<MemberName, DirectMethodHandle>();
        }
    }
}
