package android.widget;

import android.content.*;
import java.util.*;
import android.view.*;
import android.content.res.*;

public class ArrayAdapter<T> extends BaseAdapter implements Filterable, ThemedSpinnerAdapter
{
    public ArrayAdapter(final Context context, final int resource) {
        throw new RuntimeException("Stub!");
    }
    
    public ArrayAdapter(final Context context, final int resource, final int textViewResourceId) {
        throw new RuntimeException("Stub!");
    }
    
    public ArrayAdapter(final Context context, final int resource, final T[] objects) {
        throw new RuntimeException("Stub!");
    }
    
    public ArrayAdapter(final Context context, final int resource, final int textViewResourceId, final T[] objects) {
        throw new RuntimeException("Stub!");
    }
    
    public ArrayAdapter(final Context context, final int resource, final List<T> objects) {
        throw new RuntimeException("Stub!");
    }
    
    public ArrayAdapter(final Context context, final int resource, final int textViewResourceId, final List<T> objects) {
        throw new RuntimeException("Stub!");
    }
    
    public void add(final T object) {
        throw new RuntimeException("Stub!");
    }
    
    public void addAll(final Collection<? extends T> collection) {
        throw new RuntimeException("Stub!");
    }
    
    public void addAll(final T... items) {
        throw new RuntimeException("Stub!");
    }
    
    public void insert(final T object, final int index) {
        throw new RuntimeException("Stub!");
    }
    
    public void remove(final T object) {
        throw new RuntimeException("Stub!");
    }
    
    public void clear() {
        throw new RuntimeException("Stub!");
    }
    
    public void sort(final Comparator<? super T> comparator) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void notifyDataSetChanged() {
        throw new RuntimeException("Stub!");
    }
    
    public void setNotifyOnChange(final boolean notifyOnChange) {
        throw new RuntimeException("Stub!");
    }
    
    public Context getContext() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int getCount() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public T getItem(final int position) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPosition(final T item) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public long getItemId(final int position) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        throw new RuntimeException("Stub!");
    }
    
    public void setDropDownViewResource(final int resource) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void setDropDownViewTheme(final Resources.Theme theme) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Resources.Theme getDropDownViewTheme() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public View getDropDownView(final int position, final View convertView, final ViewGroup parent) {
        throw new RuntimeException("Stub!");
    }
    
    public static ArrayAdapter<CharSequence> createFromResource(final Context context, final int textArrayResId, final int textViewResId) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Filter getFilter() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public CharSequence[] getAutofillOptions() {
        throw new RuntimeException("Stub!");
    }
}
