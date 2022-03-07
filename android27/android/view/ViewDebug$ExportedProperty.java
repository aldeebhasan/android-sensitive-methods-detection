package android.view;

import java.lang.annotation.*;

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
