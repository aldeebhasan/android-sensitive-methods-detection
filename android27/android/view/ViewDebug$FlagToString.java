package android.view;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FlagToString {
    int mask();
    
    int equals();
    
    String name();
    
    boolean outputIf() default true;
}
