package android.test;

import java.lang.annotation.*;

@Deprecated
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FlakyTest {
    int tolerance() default 1;
}
