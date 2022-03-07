package android.test;

import android.content.*;
import android.database.sqlite.*;
import android.database.*;
import java.io.*;

@Deprecated
public class RenamingDelegatingContext extends ContextWrapper
{
    public RenamingDelegatingContext(final Context context, final String filePrefix) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public RenamingDelegatingContext(final Context context, final Context fileContext, final String filePrefix) {
        super(null);
        throw new RuntimeException("Stub!");
    }
    
    public static <T extends ContentProvider> T providerWithRenamedContext(final Class<T> contentProvider, final Context c, final String filePrefix) throws IllegalAccessException, InstantiationException {
        throw new RuntimeException("Stub!");
    }
    
    public static <T extends ContentProvider> T providerWithRenamedContext(final Class<T> contentProvider, final Context c, final String filePrefix, final boolean allowAccessToExistingFilesAndDbs) throws IllegalAccessException, InstantiationException {
        throw new RuntimeException("Stub!");
    }
    
    public void makeExistingFilesAndDbsAccessible() {
        throw new RuntimeException("Stub!");
    }
    
    public String getDatabasePrefix() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public SQLiteDatabase openOrCreateDatabase(final String name, final int mode, final SQLiteDatabase.CursorFactory factory) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public SQLiteDatabase openOrCreateDatabase(final String name, final int mode, final SQLiteDatabase.CursorFactory factory, final DatabaseErrorHandler errorHandler) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean deleteDatabase(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getDatabasePath(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] databaseList() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public FileInputStream openFileInput(final String name) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public FileOutputStream openFileOutput(final String name, final int mode) throws FileNotFoundException {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getFileStreamPath(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean deleteFile(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String[] fileList() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public File getCacheDir() {
        throw new RuntimeException("Stub!");
    }
}
