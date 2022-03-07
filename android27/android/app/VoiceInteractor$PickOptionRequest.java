package android.app;

import android.os.*;

public static class PickOptionRequest extends Request
{
    public PickOptionRequest(final Prompt prompt, final Option[] options, final Bundle extras) {
        throw new RuntimeException("Stub!");
    }
    
    public void onPickOptionResult(final boolean finished, final Option[] selections, final Bundle result) {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Option implements Parcelable
    {
        public static final Creator<Option> CREATOR;
        
        public Option(final CharSequence label, final int index) {
            throw new RuntimeException("Stub!");
        }
        
        public Option addSynonym(final CharSequence synonym) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getLabel() {
            throw new RuntimeException("Stub!");
        }
        
        public int getIndex() {
            throw new RuntimeException("Stub!");
        }
        
        public int countSynonyms() {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getSynonymAt(final int index) {
            throw new RuntimeException("Stub!");
        }
        
        public void setExtras(final Bundle extras) {
            throw new RuntimeException("Stub!");
        }
        
        public Bundle getExtras() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int describeContents() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public void writeToParcel(final Parcel dest, final int flags) {
            throw new RuntimeException("Stub!");
        }
        
        static {
            CREATOR = null;
        }
    }
}
