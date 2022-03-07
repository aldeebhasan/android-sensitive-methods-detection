package android.view;

import android.content.*;
import org.xmlpull.v1.*;
import android.util.*;

public abstract class LayoutInflater
{
    protected LayoutInflater(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    protected LayoutInflater(final LayoutInflater original, final Context newContext) {
        throw new RuntimeException("Stub!");
    }
    
    public static LayoutInflater from(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract LayoutInflater cloneInContext(final Context p0);
    
    public Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    public final Factory getFactory() {
        throw new RuntimeException("Stub!");
    }
    
    public final Factory2 getFactory2() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFactory(final Factory factory) {
        throw new RuntimeException("Stub!");
    }
    
    public void setFactory2(final Factory2 factory) {
        throw new RuntimeException("Stub!");
    }
    
    public Filter getFilter() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFilter(final Filter filter) {
        throw new RuntimeException("Stub!");
    }
    
    public View inflate(final int resource, final ViewGroup root) {
        throw new RuntimeException("Stub!");
    }
    
    public View inflate(final XmlPullParser parser, final ViewGroup root) {
        throw new RuntimeException("Stub!");
    }
    
    public View inflate(final int resource, final ViewGroup root, final boolean attachToRoot) {
        throw new RuntimeException("Stub!");
    }
    
    public View inflate(final XmlPullParser parser, final ViewGroup root, final boolean attachToRoot) {
        throw new RuntimeException("Stub!");
    }
    
    public final View createView(final String name, final String prefix, final AttributeSet attrs) throws ClassNotFoundException, InflateException {
        throw new RuntimeException("Stub!");
    }
    
    protected View onCreateView(final String name, final AttributeSet attrs) throws ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    protected View onCreateView(final View parent, final String name, final AttributeSet attrs) throws ClassNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    public interface Factory2 extends Factory
    {
        View onCreateView(final View p0, final String p1, final Context p2, final AttributeSet p3);
    }
    
    public interface Factory
    {
        View onCreateView(final String p0, final Context p1, final AttributeSet p2);
    }
    
    public interface Filter
    {
        boolean onLoadClass(final Class p0);
    }
}
