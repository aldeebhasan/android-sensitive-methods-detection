package dalvik.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Deprecated
public @interface TestTargetClass {
    Class<?> value();
}
