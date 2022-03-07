package java.lang.reflect;

import java.lang.annotation.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import sun.reflect.annotation.*;

public interface AnnotatedElement
{
    default boolean isAnnotationPresent(final Class<? extends Annotation> clazz) {
        return this.getAnnotation(clazz) != null;
    }
    
     <T extends Annotation> T getAnnotation(final Class<T> p0);
    
    Annotation[] getAnnotations();
    
    default <T extends Annotation> T[] getAnnotationsByType(final Class<T> clazz) {
        Annotation[] array = this.getDeclaredAnnotationsByType((Class<Annotation>)clazz);
        if (array.length == 0 && this instanceof Class && AnnotationType.getInstance(clazz).isInherited()) {
            final Class superclass = ((Class)this).getSuperclass();
            if (superclass != null) {
                array = superclass.getAnnotationsByType(clazz);
            }
        }
        return (T[])array;
    }
    
    default <T extends Annotation> T getDeclaredAnnotation(final Class<T> clazz) {
        Objects.requireNonNull(clazz);
        for (final Annotation annotation : this.getDeclaredAnnotations()) {
            if (clazz.equals(annotation.annotationType())) {
                return clazz.cast(annotation);
            }
        }
        return null;
    }
    
    default <T extends Annotation> T[] getDeclaredAnnotationsByType(final Class<T> clazz) {
        Objects.requireNonNull(clazz);
        return AnnotationSupport.getDirectlyAndIndirectlyPresent(Arrays.stream(this.getDeclaredAnnotations()).collect((Collector<? super Annotation, ?, Map<Class<? extends Annotation>, Annotation>>)Collectors.toMap((Function<? super Annotation, ?>)Annotation::annotationType, (Function<? super Annotation, ?>)Function.identity(), (annotation, p1) -> annotation, (Supplier<R>)LinkedHashMap::new)), clazz);
    }
    
    Annotation[] getDeclaredAnnotations();
}
