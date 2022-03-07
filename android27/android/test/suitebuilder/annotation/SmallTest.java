package android.test.suitebuilder.annotation;

import java.lang.annotation.*;

@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface SmallTest {
}
