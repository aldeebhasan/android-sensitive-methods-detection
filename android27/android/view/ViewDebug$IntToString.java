package android.view;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IntToString {
    int from();
    
    String to();
}
