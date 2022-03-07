package android.widget;

public interface Validator
{
    boolean isValid(final CharSequence p0);
    
    CharSequence fixText(final CharSequence p0);
}
