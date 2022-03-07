package android.service.notification;

import android.os.*;

public static class RankingMap implements Parcelable
{
    public static final Creator<RankingMap> CREATOR;
    
    RankingMap() {
        throw new RuntimeException("Stub!");
    }
    
    public String[] getOrderedKeys() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getRanking(final String key, final Ranking outRanking) {
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
