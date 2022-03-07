package java.util;

import java.util.concurrent.*;
import java.lang.reflect.*;
import java.util.stream.*;
import java.io.*;
import java.util.function.*;
import sun.security.action.*;
import java.security.*;

public class Arrays
{
    private static final int MIN_ARRAY_SORT_GRAN = 8192;
    private static final int INSERTIONSORT_THRESHOLD = 7;
    
    private static void rangeCheck(final int n, final int n2, final int n3) {
        if (n2 > n3) {
            throw new IllegalArgumentException("fromIndex(" + n2 + ") > toIndex(" + n3 + ")");
        }
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        if (n3 > n) {
            throw new ArrayIndexOutOfBoundsException(n3);
        }
    }
    
    public static void sort(final int[] array) {
        DualPivotQuicksort.sort(array, 0, array.length - 1, null, 0, 0);
    }
    
    public static void sort(final int[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
    }
    
    public static void sort(final long[] array) {
        DualPivotQuicksort.sort(array, 0, array.length - 1, null, 0, 0);
    }
    
    public static void sort(final long[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
    }
    
    public static void sort(final short[] array) {
        DualPivotQuicksort.sort(array, 0, array.length - 1, null, 0, 0);
    }
    
    public static void sort(final short[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
    }
    
    public static void sort(final char[] array) {
        DualPivotQuicksort.sort(array, 0, array.length - 1, null, 0, 0);
    }
    
    public static void sort(final char[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
    }
    
    public static void sort(final byte[] array) {
        DualPivotQuicksort.sort(array, 0, array.length - 1);
    }
    
    public static void sort(final byte[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        DualPivotQuicksort.sort(array, n, n2 - 1);
    }
    
    public static void sort(final float[] array) {
        DualPivotQuicksort.sort(array, 0, array.length - 1, null, 0, 0);
    }
    
    public static void sort(final float[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
    }
    
    public static void sort(final double[] array) {
        DualPivotQuicksort.sort(array, 0, array.length - 1, null, 0, 0);
    }
    
    public static void sort(final double[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
    }
    
    public static void parallelSort(final byte[] array) {
        final int length = array.length;
        final int commonPoolParallelism;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, 0, length - 1);
        }
        else {
            final int n;
            new ArraysParallelSortHelpers.FJByte.Sorter(null, array, new byte[length], 0, length, 0, ((n = length / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n).invoke();
        }
    }
    
    public static void parallelSort(final byte[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        final int n3 = n2 - n;
        final int commonPoolParallelism;
        if (n3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, n, n2 - 1);
        }
        else {
            final int n4;
            new ArraysParallelSortHelpers.FJByte.Sorter(null, array, new byte[n3], n, n3, 0, ((n4 = n3 / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n4).invoke();
        }
    }
    
    public static void parallelSort(final char[] array) {
        final int length = array.length;
        final int commonPoolParallelism;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, 0, length - 1, null, 0, 0);
        }
        else {
            final int n;
            new ArraysParallelSortHelpers.FJChar.Sorter(null, array, new char[length], 0, length, 0, ((n = length / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n).invoke();
        }
    }
    
    public static void parallelSort(final char[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        final int n3 = n2 - n;
        final int commonPoolParallelism;
        if (n3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
        }
        else {
            final int n4;
            new ArraysParallelSortHelpers.FJChar.Sorter(null, array, new char[n3], n, n3, 0, ((n4 = n3 / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n4).invoke();
        }
    }
    
    public static void parallelSort(final short[] array) {
        final int length = array.length;
        final int commonPoolParallelism;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, 0, length - 1, null, 0, 0);
        }
        else {
            final int n;
            new ArraysParallelSortHelpers.FJShort.Sorter(null, array, new short[length], 0, length, 0, ((n = length / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n).invoke();
        }
    }
    
    public static void parallelSort(final short[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        final int n3 = n2 - n;
        final int commonPoolParallelism;
        if (n3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
        }
        else {
            final int n4;
            new ArraysParallelSortHelpers.FJShort.Sorter(null, array, new short[n3], n, n3, 0, ((n4 = n3 / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n4).invoke();
        }
    }
    
    public static void parallelSort(final int[] array) {
        final int length = array.length;
        final int commonPoolParallelism;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, 0, length - 1, null, 0, 0);
        }
        else {
            final int n;
            new ArraysParallelSortHelpers.FJInt.Sorter(null, array, new int[length], 0, length, 0, ((n = length / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n).invoke();
        }
    }
    
    public static void parallelSort(final int[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        final int n3 = n2 - n;
        final int commonPoolParallelism;
        if (n3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
        }
        else {
            final int n4;
            new ArraysParallelSortHelpers.FJInt.Sorter(null, array, new int[n3], n, n3, 0, ((n4 = n3 / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n4).invoke();
        }
    }
    
    public static void parallelSort(final long[] array) {
        final int length = array.length;
        final int commonPoolParallelism;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, 0, length - 1, null, 0, 0);
        }
        else {
            final int n;
            new ArraysParallelSortHelpers.FJLong.Sorter(null, array, new long[length], 0, length, 0, ((n = length / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n).invoke();
        }
    }
    
    public static void parallelSort(final long[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        final int n3 = n2 - n;
        final int commonPoolParallelism;
        if (n3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
        }
        else {
            final int n4;
            new ArraysParallelSortHelpers.FJLong.Sorter(null, array, new long[n3], n, n3, 0, ((n4 = n3 / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n4).invoke();
        }
    }
    
    public static void parallelSort(final float[] array) {
        final int length = array.length;
        final int commonPoolParallelism;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, 0, length - 1, null, 0, 0);
        }
        else {
            final int n;
            new ArraysParallelSortHelpers.FJFloat.Sorter(null, array, new float[length], 0, length, 0, ((n = length / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n).invoke();
        }
    }
    
    public static void parallelSort(final float[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        final int n3 = n2 - n;
        final int commonPoolParallelism;
        if (n3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
        }
        else {
            final int n4;
            new ArraysParallelSortHelpers.FJFloat.Sorter(null, array, new float[n3], n, n3, 0, ((n4 = n3 / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n4).invoke();
        }
    }
    
    public static void parallelSort(final double[] array) {
        final int length = array.length;
        final int commonPoolParallelism;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, 0, length - 1, null, 0, 0);
        }
        else {
            final int n;
            new ArraysParallelSortHelpers.FJDouble.Sorter(null, array, new double[length], 0, length, 0, ((n = length / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n).invoke();
        }
    }
    
    public static void parallelSort(final double[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        final int n3 = n2 - n;
        final int commonPoolParallelism;
        if (n3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            DualPivotQuicksort.sort(array, n, n2 - 1, null, 0, 0);
        }
        else {
            final int n4;
            new ArraysParallelSortHelpers.FJDouble.Sorter(null, array, new double[n3], n, n3, 0, ((n4 = n3 / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n4).invoke();
        }
    }
    
    public static <T extends Comparable<? super T>> void parallelSort(final T[] array) {
        final int length = array.length;
        final int commonPoolParallelism;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            TimSort.sort(array, 0, length, NaturalOrder.INSTANCE, null, 0, 0);
        }
        else {
            final int n;
            new ArraysParallelSortHelpers.FJObject.Sorter(null, array, (Object[])Array.newInstance(array.getClass().getComponentType(), length), 0, length, 0, ((n = length / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n, NaturalOrder.INSTANCE).invoke();
        }
    }
    
    public static <T extends Comparable<? super T>> void parallelSort(final T[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        final int n3 = n2 - n;
        final int commonPoolParallelism;
        if (n3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            TimSort.sort(array, n, n2, NaturalOrder.INSTANCE, null, 0, 0);
        }
        else {
            final int n4;
            new ArraysParallelSortHelpers.FJObject.Sorter(null, array, (Object[])Array.newInstance(array.getClass().getComponentType(), n3), n, n3, 0, ((n4 = n3 / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n4, NaturalOrder.INSTANCE).invoke();
        }
    }
    
    public static <T> void parallelSort(final T[] array, Comparator<? super T> instance) {
        if (instance == null) {
            instance = NaturalOrder.INSTANCE;
        }
        final int length = array.length;
        final int commonPoolParallelism;
        if (length <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            TimSort.sort(array, 0, length, instance, null, 0, 0);
        }
        else {
            final int n;
            new ArraysParallelSortHelpers.FJObject.Sorter(null, array, (Object[])Array.newInstance(array.getClass().getComponentType(), length), 0, length, 0, ((n = length / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n, instance).invoke();
        }
    }
    
    public static <T> void parallelSort(final T[] array, final int n, final int n2, Comparator<? super T> instance) {
        rangeCheck(array.length, n, n2);
        if (instance == null) {
            instance = NaturalOrder.INSTANCE;
        }
        final int n3 = n2 - n;
        final int commonPoolParallelism;
        if (n3 <= 8192 || (commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism()) == 1) {
            TimSort.sort(array, n, n2, instance, null, 0, 0);
        }
        else {
            final int n4;
            new ArraysParallelSortHelpers.FJObject.Sorter(null, array, (Object[])Array.newInstance(array.getClass().getComponentType(), n3), n, n3, 0, ((n4 = n3 / (commonPoolParallelism << 2)) <= 8192) ? 8192 : n4, instance).invoke();
        }
    }
    
    public static void sort(final Object[] array) {
        if (LegacyMergeSort.userRequested) {
            legacyMergeSort(array);
        }
        else {
            ComparableTimSort.sort(array, 0, array.length, null, 0, 0);
        }
    }
    
    private static void legacyMergeSort(final Object[] array) {
        mergeSort(array.clone(), array, 0, array.length, 0);
    }
    
    public static void sort(final Object[] array, final int n, final int n2) {
        rangeCheck(array.length, n, n2);
        if (LegacyMergeSort.userRequested) {
            legacyMergeSort(array, n, n2);
        }
        else {
            ComparableTimSort.sort(array, n, n2, null, 0, 0);
        }
    }
    
    private static void legacyMergeSort(final Object[] array, final int n, final int n2) {
        mergeSort(copyOfRange(array, n, n2), array, n, n2, -n);
    }
    
    private static void mergeSort(final Object[] array, final Object[] array2, int n, int n2, final int n3) {
        final int n4 = n2 - n;
        if (n4 < 7) {
            for (int i = n; i < n2; ++i) {
                for (int n5 = i; n5 > n && ((Comparable)array2[n5 - 1]).compareTo(array2[n5]) > 0; --n5) {
                    swap(array2, n5, n5 - 1);
                }
            }
            return;
        }
        final int n6 = n;
        final int n7 = n2;
        n += n3;
        n2 += n3;
        final int n8 = n + n2 >>> 1;
        mergeSort(array2, array, n, n8, -n3);
        mergeSort(array2, array, n8, n2, -n3);
        if (((Comparable)array[n8 - 1]).compareTo(array[n8]) <= 0) {
            System.arraycopy(array, n, array2, n6, n4);
            return;
        }
        int j = n6;
        int n9 = n;
        int n10 = n8;
        while (j < n7) {
            if (n10 >= n2 || (n9 < n8 && ((Comparable)array[n9]).compareTo(array[n10]) <= 0)) {
                array2[j] = array[n9++];
            }
            else {
                array2[j] = array[n10++];
            }
            ++j;
        }
    }
    
    private static void swap(final Object[] array, final int n, final int n2) {
        final Object o = array[n];
        array[n] = array[n2];
        array[n2] = o;
    }
    
    public static <T> void sort(final T[] array, final Comparator<? super T> comparator) {
        if (comparator == null) {
            sort(array);
        }
        else if (LegacyMergeSort.userRequested) {
            legacyMergeSort((Object[])array, (Comparator<? super Object>)comparator);
        }
        else {
            TimSort.sort(array, 0, array.length, comparator, null, 0, 0);
        }
    }
    
    private static <T> void legacyMergeSort(final T[] array, final Comparator<? super T> comparator) {
        final T[] array2 = array.clone();
        if (comparator == null) {
            mergeSort(array2, array, 0, array.length, 0);
        }
        else {
            mergeSort(array2, array, 0, array.length, 0, comparator);
        }
    }
    
    public static <T> void sort(final T[] array, final int n, final int n2, final Comparator<? super T> comparator) {
        if (comparator == null) {
            sort(array, n, n2);
        }
        else {
            rangeCheck(array.length, n, n2);
            if (LegacyMergeSort.userRequested) {
                legacyMergeSort((Object[])array, n, n2, (Comparator<? super Object>)comparator);
            }
            else {
                TimSort.sort(array, n, n2, comparator, null, 0, 0);
            }
        }
    }
    
    private static <T> void legacyMergeSort(final T[] array, final int n, final int n2, final Comparator<? super T> comparator) {
        final T[] copyOfRange = copyOfRange(array, n, n2);
        if (comparator == null) {
            mergeSort(copyOfRange, array, n, n2, -n);
        }
        else {
            mergeSort(copyOfRange, array, n, n2, -n, comparator);
        }
    }
    
    private static void mergeSort(final Object[] array, final Object[] array2, int n, int n2, final int n3, final Comparator comparator) {
        final int n4 = n2 - n;
        if (n4 < 7) {
            for (int i = n; i < n2; ++i) {
                for (int n5 = i; n5 > n && comparator.compare(array2[n5 - 1], array2[n5]) > 0; --n5) {
                    swap(array2, n5, n5 - 1);
                }
            }
            return;
        }
        final int n6 = n;
        final int n7 = n2;
        n += n3;
        n2 += n3;
        final int n8 = n + n2 >>> 1;
        mergeSort(array2, array, n, n8, -n3, comparator);
        mergeSort(array2, array, n8, n2, -n3, comparator);
        if (comparator.compare(array[n8 - 1], array[n8]) <= 0) {
            System.arraycopy(array, n, array2, n6, n4);
            return;
        }
        int j = n6;
        int n9 = n;
        int n10 = n8;
        while (j < n7) {
            if (n10 >= n2 || (n9 < n8 && comparator.compare(array[n9], array[n10]) <= 0)) {
                array2[j] = array[n9++];
            }
            else {
                array2[j] = array[n10++];
            }
            ++j;
        }
    }
    
    public static <T> void parallelPrefix(final T[] array, final BinaryOperator<T> binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        if (array.length > 0) {
            new ArrayPrefixHelpers.CumulateTask(null, (BinaryOperator<T>)binaryOperator, (T[])array, 0, array.length).invoke();
        }
    }
    
    public static <T> void parallelPrefix(final T[] array, final int n, final int n2, final BinaryOperator<T> binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        rangeCheck(array.length, n, n2);
        if (n < n2) {
            new ArrayPrefixHelpers.CumulateTask(null, (BinaryOperator<T>)binaryOperator, (T[])array, n, n2).invoke();
        }
    }
    
    public static void parallelPrefix(final long[] array, final LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        if (array.length > 0) {
            new ArrayPrefixHelpers.LongCumulateTask(null, longBinaryOperator, array, 0, array.length).invoke();
        }
    }
    
    public static void parallelPrefix(final long[] array, final int n, final int n2, final LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        rangeCheck(array.length, n, n2);
        if (n < n2) {
            new ArrayPrefixHelpers.LongCumulateTask(null, longBinaryOperator, array, n, n2).invoke();
        }
    }
    
    public static void parallelPrefix(final double[] array, final DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        if (array.length > 0) {
            new ArrayPrefixHelpers.DoubleCumulateTask(null, doubleBinaryOperator, array, 0, array.length).invoke();
        }
    }
    
    public static void parallelPrefix(final double[] array, final int n, final int n2, final DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        rangeCheck(array.length, n, n2);
        if (n < n2) {
            new ArrayPrefixHelpers.DoubleCumulateTask(null, doubleBinaryOperator, array, n, n2).invoke();
        }
    }
    
    public static void parallelPrefix(final int[] array, final IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        if (array.length > 0) {
            new ArrayPrefixHelpers.IntCumulateTask(null, intBinaryOperator, array, 0, array.length).invoke();
        }
    }
    
    public static void parallelPrefix(final int[] array, final int n, final int n2, final IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        rangeCheck(array.length, n, n2);
        if (n < n2) {
            new ArrayPrefixHelpers.IntCumulateTask(null, intBinaryOperator, array, n, n2).invoke();
        }
    }
    
    public static int binarySearch(final long[] array, final long n) {
        return binarySearch0(array, 0, array.length, n);
    }
    
    public static int binarySearch(final long[] array, final int n, final int n2, final long n3) {
        rangeCheck(array.length, n, n2);
        return binarySearch0(array, n, n2, n3);
    }
    
    private static int binarySearch0(final long[] array, final int n, final int n2, final long n3) {
        int i = n;
        int n4 = n2 - 1;
        while (i <= n4) {
            final int n5 = i + n4 >>> 1;
            final long n6 = array[n5];
            if (n6 < n3) {
                i = n5 + 1;
            }
            else {
                if (n6 <= n3) {
                    return n5;
                }
                n4 = n5 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final int[] array, final int n) {
        return binarySearch0(array, 0, array.length, n);
    }
    
    public static int binarySearch(final int[] array, final int n, final int n2, final int n3) {
        rangeCheck(array.length, n, n2);
        return binarySearch0(array, n, n2, n3);
    }
    
    private static int binarySearch0(final int[] array, final int n, final int n2, final int n3) {
        int i = n;
        int n4 = n2 - 1;
        while (i <= n4) {
            final int n5 = i + n4 >>> 1;
            final int n6 = array[n5];
            if (n6 < n3) {
                i = n5 + 1;
            }
            else {
                if (n6 <= n3) {
                    return n5;
                }
                n4 = n5 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final short[] array, final short n) {
        return binarySearch0(array, 0, array.length, n);
    }
    
    public static int binarySearch(final short[] array, final int n, final int n2, final short n3) {
        rangeCheck(array.length, n, n2);
        return binarySearch0(array, n, n2, n3);
    }
    
    private static int binarySearch0(final short[] array, final int n, final int n2, final short n3) {
        int i = n;
        int n4 = n2 - 1;
        while (i <= n4) {
            final int n5 = i + n4 >>> 1;
            final short n6 = array[n5];
            if (n6 < n3) {
                i = n5 + 1;
            }
            else {
                if (n6 <= n3) {
                    return n5;
                }
                n4 = n5 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final char[] array, final char c) {
        return binarySearch0(array, 0, array.length, c);
    }
    
    public static int binarySearch(final char[] array, final int n, final int n2, final char c) {
        rangeCheck(array.length, n, n2);
        return binarySearch0(array, n, n2, c);
    }
    
    private static int binarySearch0(final char[] array, final int n, final int n2, final char c) {
        int i = n;
        int n3 = n2 - 1;
        while (i <= n3) {
            final int n4 = i + n3 >>> 1;
            final char c2 = array[n4];
            if (c2 < c) {
                i = n4 + 1;
            }
            else {
                if (c2 <= c) {
                    return n4;
                }
                n3 = n4 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final byte[] array, final byte b) {
        return binarySearch0(array, 0, array.length, b);
    }
    
    public static int binarySearch(final byte[] array, final int n, final int n2, final byte b) {
        rangeCheck(array.length, n, n2);
        return binarySearch0(array, n, n2, b);
    }
    
    private static int binarySearch0(final byte[] array, final int n, final int n2, final byte b) {
        int i = n;
        int n3 = n2 - 1;
        while (i <= n3) {
            final int n4 = i + n3 >>> 1;
            final byte b2 = array[n4];
            if (b2 < b) {
                i = n4 + 1;
            }
            else {
                if (b2 <= b) {
                    return n4;
                }
                n3 = n4 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final double[] array, final double n) {
        return binarySearch0(array, 0, array.length, n);
    }
    
    public static int binarySearch(final double[] array, final int n, final int n2, final double n3) {
        rangeCheck(array.length, n, n2);
        return binarySearch0(array, n, n2, n3);
    }
    
    private static int binarySearch0(final double[] array, final int n, final int n2, final double n3) {
        int i = n;
        int n4 = n2 - 1;
        while (i <= n4) {
            final int n5 = i + n4 >>> 1;
            final double n6 = array[n5];
            if (n6 < n3) {
                i = n5 + 1;
            }
            else if (n6 > n3) {
                n4 = n5 - 1;
            }
            else {
                final long doubleToLongBits = Double.doubleToLongBits(n6);
                final long doubleToLongBits2 = Double.doubleToLongBits(n3);
                if (doubleToLongBits == doubleToLongBits2) {
                    return n5;
                }
                if (doubleToLongBits < doubleToLongBits2) {
                    i = n5 + 1;
                }
                else {
                    n4 = n5 - 1;
                }
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final float[] array, final float n) {
        return binarySearch0(array, 0, array.length, n);
    }
    
    public static int binarySearch(final float[] array, final int n, final int n2, final float n3) {
        rangeCheck(array.length, n, n2);
        return binarySearch0(array, n, n2, n3);
    }
    
    private static int binarySearch0(final float[] array, final int n, final int n2, final float n3) {
        int i = n;
        int n4 = n2 - 1;
        while (i <= n4) {
            final int n5 = i + n4 >>> 1;
            final float n6 = array[n5];
            if (n6 < n3) {
                i = n5 + 1;
            }
            else if (n6 > n3) {
                n4 = n5 - 1;
            }
            else {
                final int floatToIntBits = Float.floatToIntBits(n6);
                final int floatToIntBits2 = Float.floatToIntBits(n3);
                if (floatToIntBits == floatToIntBits2) {
                    return n5;
                }
                if (floatToIntBits < floatToIntBits2) {
                    i = n5 + 1;
                }
                else {
                    n4 = n5 - 1;
                }
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final Object[] array, final Object o) {
        return binarySearch0(array, 0, array.length, o);
    }
    
    public static int binarySearch(final Object[] array, final int n, final int n2, final Object o) {
        rangeCheck(array.length, n, n2);
        return binarySearch0(array, n, n2, o);
    }
    
    private static int binarySearch0(final Object[] array, final int n, final int n2, final Object o) {
        int i = n;
        int n3 = n2 - 1;
        while (i <= n3) {
            final int n4 = i + n3 >>> 1;
            final int compareTo = ((Comparable)array[n4]).compareTo(o);
            if (compareTo < 0) {
                i = n4 + 1;
            }
            else {
                if (compareTo <= 0) {
                    return n4;
                }
                n3 = n4 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static <T> int binarySearch(final T[] array, final T t, final Comparator<? super T> comparator) {
        return binarySearch0(array, 0, array.length, t, comparator);
    }
    
    public static <T> int binarySearch(final T[] array, final int n, final int n2, final T t, final Comparator<? super T> comparator) {
        rangeCheck(array.length, n, n2);
        return binarySearch0(array, n, n2, (Object)t, (Comparator<? super Object>)comparator);
    }
    
    private static <T> int binarySearch0(final T[] array, final int n, final int n2, final T t, final Comparator<? super T> comparator) {
        if (comparator == null) {
            return binarySearch0(array, n, n2, t);
        }
        int i = n;
        int n3 = n2 - 1;
        while (i <= n3) {
            final int n4 = i + n3 >>> 1;
            final int compare = comparator.compare(array[n4], t);
            if (compare < 0) {
                i = n4 + 1;
            }
            else {
                if (compare <= 0) {
                    return n4;
                }
                n3 = n4 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static boolean equals(final long[] array, final long[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        if (array2.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final int[] array, final int[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        if (array2.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final short[] array, final short[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        if (array2.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final char[] array, final char[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        if (array2.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final byte[] array, final byte[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        if (array2.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final boolean[] array, final boolean[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        if (array2.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (array[i] != array2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final double[] array, final double[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        if (array2.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (Double.doubleToLongBits(array[i]) != Double.doubleToLongBits(array2[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final float[] array, final float[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        if (array2.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (Float.floatToIntBits(array[i]) != Float.floatToIntBits(array2[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final Object[] array, final Object[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        if (array2.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            final Object o = array[i];
            final Object o2 = array2[i];
            if (o == null) {
                if (o2 != null) {
                    return false;
                }
            }
            else if (!o.equals(o2)) {
                return false;
            }
        }
        return true;
    }
    
    public static void fill(final long[] array, final long n) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = n;
        }
    }
    
    public static void fill(final long[] array, final int n, final int n2, final long n3) {
        rangeCheck(array.length, n, n2);
        for (int i = n; i < n2; ++i) {
            array[i] = n3;
        }
    }
    
    public static void fill(final int[] array, final int n) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = n;
        }
    }
    
    public static void fill(final int[] array, final int n, final int n2, final int n3) {
        rangeCheck(array.length, n, n2);
        for (int i = n; i < n2; ++i) {
            array[i] = n3;
        }
    }
    
    public static void fill(final short[] array, final short n) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = n;
        }
    }
    
    public static void fill(final short[] array, final int n, final int n2, final short n3) {
        rangeCheck(array.length, n, n2);
        for (int i = n; i < n2; ++i) {
            array[i] = n3;
        }
    }
    
    public static void fill(final char[] array, final char c) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = c;
        }
    }
    
    public static void fill(final char[] array, final int n, final int n2, final char c) {
        rangeCheck(array.length, n, n2);
        for (int i = n; i < n2; ++i) {
            array[i] = c;
        }
    }
    
    public static void fill(final byte[] array, final byte b) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = b;
        }
    }
    
    public static void fill(final byte[] array, final int n, final int n2, final byte b) {
        rangeCheck(array.length, n, n2);
        for (int i = n; i < n2; ++i) {
            array[i] = b;
        }
    }
    
    public static void fill(final boolean[] array, final boolean b) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = b;
        }
    }
    
    public static void fill(final boolean[] array, final int n, final int n2, final boolean b) {
        rangeCheck(array.length, n, n2);
        for (int i = n; i < n2; ++i) {
            array[i] = b;
        }
    }
    
    public static void fill(final double[] array, final double n) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = n;
        }
    }
    
    public static void fill(final double[] array, final int n, final int n2, final double n3) {
        rangeCheck(array.length, n, n2);
        for (int i = n; i < n2; ++i) {
            array[i] = n3;
        }
    }
    
    public static void fill(final float[] array, final float n) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = n;
        }
    }
    
    public static void fill(final float[] array, final int n, final int n2, final float n3) {
        rangeCheck(array.length, n, n2);
        for (int i = n; i < n2; ++i) {
            array[i] = n3;
        }
    }
    
    public static void fill(final Object[] array, final Object o) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = o;
        }
    }
    
    public static void fill(final Object[] array, final int n, final int n2, final Object o) {
        rangeCheck(array.length, n, n2);
        for (int i = n; i < n2; ++i) {
            array[i] = o;
        }
    }
    
    public static <T> T[] copyOf(final T[] array, final int n) {
        return copyOf(array, n, (Class<? extends T[]>)array.getClass());
    }
    
    public static <T, U> T[] copyOf(final U[] array, final int n, final Class<? extends T[]> clazz) {
        final Object[] array2 = (Object[])((clazz == Object[].class) ? new Object[n] : Array.newInstance(clazz.getComponentType(), n));
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return (T[])array2;
    }
    
    public static byte[] copyOf(final byte[] array, final int n) {
        final byte[] array2 = new byte[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static short[] copyOf(final short[] array, final int n) {
        final short[] array2 = new short[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static int[] copyOf(final int[] array, final int n) {
        final int[] array2 = new int[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static long[] copyOf(final long[] array, final int n) {
        final long[] array2 = new long[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static char[] copyOf(final char[] array, final int n) {
        final char[] array2 = new char[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static float[] copyOf(final float[] array, final int n) {
        final float[] array2 = new float[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static double[] copyOf(final double[] array, final int n) {
        final double[] array2 = new double[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static boolean[] copyOf(final boolean[] array, final int n) {
        final boolean[] array2 = new boolean[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static <T> T[] copyOfRange(final T[] array, final int n, final int n2) {
        return copyOfRange(array, n, n2, (Class<? extends T[]>)array.getClass());
    }
    
    public static <T, U> T[] copyOfRange(final U[] array, final int n, final int n2, final Class<? extends T[]> clazz) {
        final int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException(n + " > " + n2);
        }
        final Object[] array2 = (Object[])((clazz == Object[].class) ? new Object[n3] : Array.newInstance(clazz.getComponentType(), n3));
        System.arraycopy(array, n, array2, 0, Math.min(array.length - n, n3));
        return (T[])array2;
    }
    
    public static byte[] copyOfRange(final byte[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException(n + " > " + n2);
        }
        final byte[] array2 = new byte[n3];
        System.arraycopy(array, n, array2, 0, Math.min(array.length - n, n3));
        return array2;
    }
    
    public static short[] copyOfRange(final short[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException(n + " > " + n2);
        }
        final short[] array2 = new short[n3];
        System.arraycopy(array, n, array2, 0, Math.min(array.length - n, n3));
        return array2;
    }
    
    public static int[] copyOfRange(final int[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException(n + " > " + n2);
        }
        final int[] array2 = new int[n3];
        System.arraycopy(array, n, array2, 0, Math.min(array.length - n, n3));
        return array2;
    }
    
    public static long[] copyOfRange(final long[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException(n + " > " + n2);
        }
        final long[] array2 = new long[n3];
        System.arraycopy(array, n, array2, 0, Math.min(array.length - n, n3));
        return array2;
    }
    
    public static char[] copyOfRange(final char[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException(n + " > " + n2);
        }
        final char[] array2 = new char[n3];
        System.arraycopy(array, n, array2, 0, Math.min(array.length - n, n3));
        return array2;
    }
    
    public static float[] copyOfRange(final float[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException(n + " > " + n2);
        }
        final float[] array2 = new float[n3];
        System.arraycopy(array, n, array2, 0, Math.min(array.length - n, n3));
        return array2;
    }
    
    public static double[] copyOfRange(final double[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException(n + " > " + n2);
        }
        final double[] array2 = new double[n3];
        System.arraycopy(array, n, array2, 0, Math.min(array.length - n, n3));
        return array2;
    }
    
    public static boolean[] copyOfRange(final boolean[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException(n + " > " + n2);
        }
        final boolean[] array2 = new boolean[n3];
        System.arraycopy(array, n, array2, 0, Math.min(array.length - n, n3));
        return array2;
    }
    
    @SafeVarargs
    public static <T> List<T> asList(final T... array) {
        return new ArrayList<T>(array);
    }
    
    public static int hashCode(final long[] array) {
        if (array == null) {
            return 0;
        }
        int n = 1;
        for (final long n2 : array) {
            n = 31 * n + (int)(n2 ^ n2 >>> 32);
        }
        return n;
    }
    
    public static int hashCode(final int[] array) {
        if (array == null) {
            return 0;
        }
        int n = 1;
        for (int length = array.length, i = 0; i < length; ++i) {
            n = 31 * n + array[i];
        }
        return n;
    }
    
    public static int hashCode(final short[] array) {
        if (array == null) {
            return 0;
        }
        int n = 1;
        for (int length = array.length, i = 0; i < length; ++i) {
            n = 31 * n + array[i];
        }
        return n;
    }
    
    public static int hashCode(final char[] array) {
        if (array == null) {
            return 0;
        }
        int n = 1;
        for (int length = array.length, i = 0; i < length; ++i) {
            n = 31 * n + array[i];
        }
        return n;
    }
    
    public static int hashCode(final byte[] array) {
        if (array == null) {
            return 0;
        }
        int n = 1;
        for (int length = array.length, i = 0; i < length; ++i) {
            n = 31 * n + array[i];
        }
        return n;
    }
    
    public static int hashCode(final boolean[] array) {
        if (array == null) {
            return 0;
        }
        int n = 1;
        for (int length = array.length, i = 0; i < length; ++i) {
            n = 31 * n + (array[i] ? 1231 : 1237);
        }
        return n;
    }
    
    public static int hashCode(final float[] array) {
        if (array == null) {
            return 0;
        }
        int n = 1;
        for (int length = array.length, i = 0; i < length; ++i) {
            n = 31 * n + Float.floatToIntBits(array[i]);
        }
        return n;
    }
    
    public static int hashCode(final double[] array) {
        if (array == null) {
            return 0;
        }
        int n = 1;
        for (int length = array.length, i = 0; i < length; ++i) {
            final long doubleToLongBits = Double.doubleToLongBits(array[i]);
            n = 31 * n + (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
        }
        return n;
    }
    
    public static int hashCode(final Object[] array) {
        if (array == null) {
            return 0;
        }
        int n = 1;
        for (final Object o : array) {
            n = 31 * n + ((o == null) ? 0 : o.hashCode());
        }
        return n;
    }
    
    public static int deepHashCode(final Object[] array) {
        if (array == null) {
            return 0;
        }
        int n = 1;
        for (final Object o : array) {
            int n2 = 0;
            if (o instanceof Object[]) {
                n2 = deepHashCode((Object[])o);
            }
            else if (o instanceof byte[]) {
                n2 = hashCode((byte[])o);
            }
            else if (o instanceof short[]) {
                n2 = hashCode((short[])o);
            }
            else if (o instanceof int[]) {
                n2 = hashCode((int[])o);
            }
            else if (o instanceof long[]) {
                n2 = hashCode((long[])o);
            }
            else if (o instanceof char[]) {
                n2 = hashCode((char[])o);
            }
            else if (o instanceof float[]) {
                n2 = hashCode((float[])o);
            }
            else if (o instanceof double[]) {
                n2 = hashCode((double[])o);
            }
            else if (o instanceof boolean[]) {
                n2 = hashCode((boolean[])o);
            }
            else if (o != null) {
                n2 = o.hashCode();
            }
            n = 31 * n + n2;
        }
        return n;
    }
    
    public static boolean deepEquals(final Object[] array, final Object[] array2) {
        if (array == array2) {
            return true;
        }
        if (array == null || array2 == null) {
            return false;
        }
        final int length = array.length;
        if (array2.length != length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            final Object o = array[i];
            final Object o2 = array2[i];
            if (o != o2) {
                if (o == null) {
                    return false;
                }
                if (!deepEquals0(o, o2)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    static boolean deepEquals0(final Object o, final Object o2) {
        assert o != null;
        boolean b;
        if (o instanceof Object[] && o2 instanceof Object[]) {
            b = deepEquals((Object[])o, (Object[])o2);
        }
        else if (o instanceof byte[] && o2 instanceof byte[]) {
            b = equals((byte[])o, (byte[])o2);
        }
        else if (o instanceof short[] && o2 instanceof short[]) {
            b = equals((short[])o, (short[])o2);
        }
        else if (o instanceof int[] && o2 instanceof int[]) {
            b = equals((int[])o, (int[])o2);
        }
        else if (o instanceof long[] && o2 instanceof long[]) {
            b = equals((long[])o, (long[])o2);
        }
        else if (o instanceof char[] && o2 instanceof char[]) {
            b = equals((char[])o, (char[])o2);
        }
        else if (o instanceof float[] && o2 instanceof float[]) {
            b = equals((float[])o, (float[])o2);
        }
        else if (o instanceof double[] && o2 instanceof double[]) {
            b = equals((double[])o, (double[])o2);
        }
        else if (o instanceof boolean[] && o2 instanceof boolean[]) {
            b = equals((boolean[])o, (boolean[])o2);
        }
        else {
            b = o.equals(o2);
        }
        return b;
    }
    
    public static String toString(final long[] array) {
        if (array == null) {
            return "null";
        }
        final int n = array.length - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int n2 = 0;
        while (true) {
            sb.append(array[n2]);
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static String toString(final int[] array) {
        if (array == null) {
            return "null";
        }
        final int n = array.length - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int n2 = 0;
        while (true) {
            sb.append(array[n2]);
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static String toString(final short[] array) {
        if (array == null) {
            return "null";
        }
        final int n = array.length - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int n2 = 0;
        while (true) {
            sb.append(array[n2]);
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static String toString(final char[] array) {
        if (array == null) {
            return "null";
        }
        final int n = array.length - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int n2 = 0;
        while (true) {
            sb.append(array[n2]);
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static String toString(final byte[] array) {
        if (array == null) {
            return "null";
        }
        final int n = array.length - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int n2 = 0;
        while (true) {
            sb.append(array[n2]);
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static String toString(final boolean[] array) {
        if (array == null) {
            return "null";
        }
        final int n = array.length - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int n2 = 0;
        while (true) {
            sb.append(array[n2]);
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static String toString(final float[] array) {
        if (array == null) {
            return "null";
        }
        final int n = array.length - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int n2 = 0;
        while (true) {
            sb.append(array[n2]);
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static String toString(final double[] array) {
        if (array == null) {
            return "null";
        }
        final int n = array.length - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int n2 = 0;
        while (true) {
            sb.append(array[n2]);
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static String toString(final Object[] array) {
        if (array == null) {
            return "null";
        }
        final int n = array.length - 1;
        if (n == -1) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        int n2 = 0;
        while (true) {
            sb.append(String.valueOf(array[n2]));
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static String deepToString(final Object[] array) {
        if (array == null) {
            return "null";
        }
        int n = 20 * array.length;
        if (array.length != 0 && n <= 0) {
            n = Integer.MAX_VALUE;
        }
        final StringBuilder sb = new StringBuilder(n);
        deepToString(array, sb, new HashSet<Object[]>());
        return sb.toString();
    }
    
    private static void deepToString(final Object[] array, final StringBuilder sb, final Set<Object[]> set) {
        if (array == null) {
            sb.append("null");
            return;
        }
        final int n = array.length - 1;
        if (n == -1) {
            sb.append("[]");
            return;
        }
        set.add(array);
        sb.append('[');
        int n2 = 0;
        while (true) {
            final Object o = array[n2];
            if (o == null) {
                sb.append("null");
            }
            else {
                final Class<? extends byte[]> class1 = ((byte[])o).getClass();
                if (class1.isArray()) {
                    if (class1 == byte[].class) {
                        sb.append(toString((byte[])o));
                    }
                    else if (class1 == short[].class) {
                        sb.append(toString((short[])o));
                    }
                    else if (class1 == int[].class) {
                        sb.append(toString((int[])o));
                    }
                    else if (class1 == long[].class) {
                        sb.append(toString((long[])o));
                    }
                    else if (class1 == char[].class) {
                        sb.append(toString((char[])o));
                    }
                    else if (class1 == float[].class) {
                        sb.append(toString((float[])o));
                    }
                    else if (class1 == double[].class) {
                        sb.append(toString((double[])o));
                    }
                    else if (class1 == boolean[].class) {
                        sb.append(toString((boolean[])o));
                    }
                    else if (set.contains(o)) {
                        sb.append("[...]");
                    }
                    else {
                        deepToString((Object[])o, sb, set);
                    }
                }
                else {
                    sb.append(o.toString());
                }
            }
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        sb.append(']');
        set.remove(array);
    }
    
    public static <T> void setAll(final T[] array, final IntFunction<? extends T> intFunction) {
        Objects.requireNonNull(intFunction);
        for (int i = 0; i < array.length; ++i) {
            array[i] = (T)intFunction.apply(i);
        }
    }
    
    public static <T> void parallelSetAll(final T[] array, final IntFunction<? extends T> intFunction) {
        Objects.requireNonNull(intFunction);
        IntStream.range(0, array.length).parallel().forEach(n -> array[n] = (T)intFunction.apply(n));
    }
    
    public static void setAll(final int[] array, final IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        for (int i = 0; i < array.length; ++i) {
            array[i] = intUnaryOperator.applyAsInt(i);
        }
    }
    
    public static void parallelSetAll(final int[] array, final IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        IntStream.range(0, array.length).parallel().forEach(n -> array[n] = intUnaryOperator.applyAsInt(n));
    }
    
    public static void setAll(final long[] array, final IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        for (int i = 0; i < array.length; ++i) {
            array[i] = intToLongFunction.applyAsLong(i);
        }
    }
    
    public static void parallelSetAll(final long[] array, final IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        IntStream.range(0, array.length).parallel().forEach(n -> array[n] = intToLongFunction.applyAsLong(n));
    }
    
    public static void setAll(final double[] array, final IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        for (int i = 0; i < array.length; ++i) {
            array[i] = intToDoubleFunction.applyAsDouble(i);
        }
    }
    
    public static void parallelSetAll(final double[] array, final IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        IntStream.range(0, array.length).parallel().forEach(n -> array[n] = intToDoubleFunction.applyAsDouble(n));
    }
    
    public static <T> Spliterator<T> spliterator(final T[] array) {
        return Spliterators.spliterator(array, 1040);
    }
    
    public static <T> Spliterator<T> spliterator(final T[] array, final int n, final int n2) {
        return Spliterators.spliterator(array, n, n2, 1040);
    }
    
    public static Spliterator.OfInt spliterator(final int[] array) {
        return Spliterators.spliterator(array, 1040);
    }
    
    public static Spliterator.OfInt spliterator(final int[] array, final int n, final int n2) {
        return Spliterators.spliterator(array, n, n2, 1040);
    }
    
    public static Spliterator.OfLong spliterator(final long[] array) {
        return Spliterators.spliterator(array, 1040);
    }
    
    public static Spliterator.OfLong spliterator(final long[] array, final int n, final int n2) {
        return Spliterators.spliterator(array, n, n2, 1040);
    }
    
    public static Spliterator.OfDouble spliterator(final double[] array) {
        return Spliterators.spliterator(array, 1040);
    }
    
    public static Spliterator.OfDouble spliterator(final double[] array, final int n, final int n2) {
        return Spliterators.spliterator(array, n, n2, 1040);
    }
    
    public static <T> Stream<T> stream(final T[] array) {
        return stream(array, 0, array.length);
    }
    
    public static <T> Stream<T> stream(final T[] array, final int n, final int n2) {
        return StreamSupport.stream((Spliterator<T>)spliterator((T[])array, n, n2), false);
    }
    
    public static IntStream stream(final int[] array) {
        return stream(array, 0, array.length);
    }
    
    public static IntStream stream(final int[] array, final int n, final int n2) {
        return StreamSupport.intStream(spliterator(array, n, n2), false);
    }
    
    public static LongStream stream(final long[] array) {
        return stream(array, 0, array.length);
    }
    
    public static LongStream stream(final long[] array, final int n, final int n2) {
        return StreamSupport.longStream(spliterator(array, n, n2), false);
    }
    
    public static DoubleStream stream(final double[] array) {
        return stream(array, 0, array.length);
    }
    
    public static DoubleStream stream(final double[] array, final int n, final int n2) {
        return StreamSupport.doubleStream(spliterator(array, n, n2), false);
    }
    
    private static class ArrayList<E> extends AbstractList<E> implements RandomAccess, Serializable
    {
        private static final long serialVersionUID = -2764017481108945198L;
        private final E[] a;
        
        ArrayList(final E[] array) {
            this.a = Objects.requireNonNull(array);
        }
        
        @Override
        public int size() {
            return this.a.length;
        }
        
        @Override
        public Object[] toArray() {
            return this.a.clone();
        }
        
        @Override
        public <T> T[] toArray(final T[] array) {
            final int size = this.size();
            if (array.length < size) {
                return (T[])Arrays.copyOf(this.a, size, array.getClass());
            }
            System.arraycopy(this.a, 0, array, 0, size);
            if (array.length > size) {
                array[size] = null;
            }
            return array;
        }
        
        @Override
        public E get(final int n) {
            return (E)this.a[n];
        }
        
        @Override
        public E set(final int n, final E e) {
            final Object o = this.a[n];
            this.a[n] = e;
            return (E)o;
        }
        
        @Override
        public int indexOf(final Object o) {
            final Object[] a = this.a;
            if (o == null) {
                for (int i = 0; i < a.length; ++i) {
                    if (a[i] == null) {
                        return i;
                    }
                }
            }
            else {
                for (int j = 0; j < a.length; ++j) {
                    if (o.equals(a[j])) {
                        return j;
                    }
                }
            }
            return -1;
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.indexOf(o) != -1;
        }
        
        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.spliterator(this.a, 16);
        }
        
        @Override
        public void forEach(final Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            final Object[] a = this.a;
            for (int length = a.length, i = 0; i < length; ++i) {
                consumer.accept((Object)a[i]);
            }
        }
        
        @Override
        public void replaceAll(final UnaryOperator<E> unaryOperator) {
            Objects.requireNonNull(unaryOperator);
            final Object[] a = this.a;
            for (int i = 0; i < a.length; ++i) {
                a[i] = unaryOperator.apply((E)a[i]);
            }
        }
        
        @Override
        public void sort(final Comparator<? super E> comparator) {
            Arrays.sort(this.a, (Comparator<? super Object>)comparator);
        }
    }
    
    static final class LegacyMergeSort
    {
        private static final boolean userRequested;
        
        static {
            userRequested = AccessController.doPrivileged((PrivilegedAction<Boolean>)new GetBooleanAction("java.util.Arrays.useLegacyMergeSort"));
        }
    }
    
    static final class NaturalOrder implements Comparator<Object>
    {
        static final NaturalOrder INSTANCE;
        
        @Override
        public int compare(final Object o, final Object o2) {
            return ((Comparable)o).compareTo(o2);
        }
        
        static {
            INSTANCE = new NaturalOrder();
        }
    }
}
