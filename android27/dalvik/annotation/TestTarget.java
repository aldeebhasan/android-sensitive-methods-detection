package dalvik.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE })
@Deprecated
public @interface TestTarget {
    String methodName() default "";
    
    String conceptName() default "";
    
    Class<?>[] methodArgs() default {};
}
