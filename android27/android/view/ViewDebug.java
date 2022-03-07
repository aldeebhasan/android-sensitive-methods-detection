package android.view;

import java.lang.annotation.*;

public class ViewDebug
{
    @Deprecated
    public static final boolean TRACE_HIERARCHY = false;
    @Deprecated
    public static final boolean TRACE_RECYCLER = false;
    
    public ViewDebug() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void trace(final View view, final RecyclerTraceType type, final int... parameters) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void startRecyclerTracing(final String prefix, final View view) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void stopRecyclerTracing() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void trace(final View view, final HierarchyTraceType type) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void startHierarchyTracing(final String prefix, final View view) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public static void stopHierarchyTracing() {
        throw new RuntimeException("Stub!");
    }
    
    public static void dumpCapturedView(final String tag, final Object view) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public enum HierarchyTraceType
    {
        BUILD_CACHE, 
        DRAW, 
        INVALIDATE, 
        INVALIDATE_CHILD, 
        INVALIDATE_CHILD_IN_PARENT, 
        ON_LAYOUT, 
        ON_MEASURE, 
        REQUEST_LAYOUT;
    }
    
    @Deprecated
    public enum RecyclerTraceType
    {
        BIND_VIEW, 
        MOVE_FROM_ACTIVE_TO_SCRAP_HEAP, 
        MOVE_TO_SCRAP_HEAP, 
        NEW_VIEW, 
        RECYCLE_FROM_ACTIVE_HEAP, 
        RECYCLE_FROM_SCRAP_HEAP;
    }
    
    @Target({ ElementType.FIELD, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CapturedViewProperty {
        boolean retrieveReturn() default false;
    }
    
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FlagToString {
        int mask();
        
        int equals();
        
        String name();
        
        boolean outputIf() default true;
    }
    
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IntToString {
        int from();
        
        String to();
    }
    
    @Target({ ElementType.FIELD, ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ExportedProperty {
        boolean resolveId() default false;
        
        IntToString[] mapping() default {};
        
        IntToString[] indexMapping() default {};
        
        FlagToString[] flagMapping() default {};
        
        boolean deepExport() default false;
        
        String prefix() default "";
        
        String category() default "";
        
        boolean formatToHexString() default false;
        
        boolean hasAdjacentMapping() default false;
    }
}
