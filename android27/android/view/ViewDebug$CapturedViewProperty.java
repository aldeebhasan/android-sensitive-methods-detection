package android.view;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CapturedViewProperty {
    boolean retrieveReturn() default false;
}
