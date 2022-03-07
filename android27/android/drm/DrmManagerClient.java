package android.drm;

import android.content.*;
import android.net.*;
import java.io.*;

public class DrmManagerClient implements AutoCloseable
{
    public static final int ERROR_NONE = 0;
    public static final int ERROR_UNKNOWN = -2000;
    
    public DrmManagerClient(final Context context) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void release() {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void setOnInfoListener(final OnInfoListener infoListener) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void setOnEventListener(final OnEventListener eventListener) {
        throw new RuntimeException("Stub!");
    }
    
    public synchronized void setOnErrorListener(final OnErrorListener errorListener) {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getAvailableDrmEngines() {
        throw new RuntimeException("Stub!");
    }
    
    public ContentValues getConstraints(final String path, final int action) {
        throw new RuntimeException("Stub!");
    }
    
    public ContentValues getMetadata(final String path) {
        throw new RuntimeException("Stub!");
    }
    
    public ContentValues getConstraints(final Uri uri, final int action) {
        throw new RuntimeException("Stub!");
    }
    
    public ContentValues getMetadata(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public int saveRights(final DrmRights drmRights, final String rightsPath, final String contentPath) throws IOException {
        throw new RuntimeException("Stub!");
    }
    
    public boolean canHandle(final String path, final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean canHandle(final Uri uri, final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public int processDrmInfo(final DrmInfo drmInfo) {
        throw new RuntimeException("Stub!");
    }
    
    public DrmInfo acquireDrmInfo(final DrmInfoRequest drmInfoRequest) {
        throw new RuntimeException("Stub!");
    }
    
    public int acquireRights(final DrmInfoRequest drmInfoRequest) {
        throw new RuntimeException("Stub!");
    }
    
    public int getDrmObjectType(final String path, final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public int getDrmObjectType(final Uri uri, final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public String getOriginalMimeType(final String path) {
        throw new RuntimeException("Stub!");
    }
    
    public String getOriginalMimeType(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public int checkRightsStatus(final String path) {
        throw new RuntimeException("Stub!");
    }
    
    public int checkRightsStatus(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public int checkRightsStatus(final String path, final int action) {
        throw new RuntimeException("Stub!");
    }
    
    public int checkRightsStatus(final Uri uri, final int action) {
        throw new RuntimeException("Stub!");
    }
    
    public int removeRights(final String path) {
        throw new RuntimeException("Stub!");
    }
    
    public int removeRights(final Uri uri) {
        throw new RuntimeException("Stub!");
    }
    
    public int removeAllRights() {
        throw new RuntimeException("Stub!");
    }
    
    public int openConvertSession(final String mimeType) {
        throw new RuntimeException("Stub!");
    }
    
    public DrmConvertedStatus convertData(final int convertId, final byte[] inputData) {
        throw new RuntimeException("Stub!");
    }
    
    public DrmConvertedStatus closeConvertSession(final int convertId) {
        throw new RuntimeException("Stub!");
    }
    
    public interface OnErrorListener
    {
        void onError(final DrmManagerClient p0, final DrmErrorEvent p1);
    }
    
    public interface OnEventListener
    {
        void onEvent(final DrmManagerClient p0, final DrmEvent p1);
    }
    
    public interface OnInfoListener
    {
        void onInfo(final DrmManagerClient p0, final DrmInfoEvent p1);
    }
}
