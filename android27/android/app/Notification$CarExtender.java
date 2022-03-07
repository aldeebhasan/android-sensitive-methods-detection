package android.app;

import android.graphics.*;

public static final class CarExtender implements Extender
{
    public CarExtender() {
        throw new RuntimeException("Stub!");
    }
    
    public CarExtender(final Notification notif) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public Notification.Builder extend(final Notification.Builder builder) {
        throw new RuntimeException("Stub!");
    }
    
    public CarExtender setColor(final int color) {
        throw new RuntimeException("Stub!");
    }
    
    public int getColor() {
        throw new RuntimeException("Stub!");
    }
    
    public CarExtender setLargeIcon(final Bitmap largeIcon) {
        throw new RuntimeException("Stub!");
    }
    
    public Bitmap getLargeIcon() {
        throw new RuntimeException("Stub!");
    }
    
    public CarExtender setUnreadConversation(final UnreadConversation unreadConversation) {
        throw new RuntimeException("Stub!");
    }
    
    public UnreadConversation getUnreadConversation() {
        throw new RuntimeException("Stub!");
    }
    
    public static class UnreadConversation
    {
        UnreadConversation() {
            throw new RuntimeException("Stub!");
        }
        
        public String[] getMessages() {
            throw new RuntimeException("Stub!");
        }
        
        public RemoteInput getRemoteInput() {
            throw new RuntimeException("Stub!");
        }
        
        public PendingIntent getReplyPendingIntent() {
            throw new RuntimeException("Stub!");
        }
        
        public PendingIntent getReadPendingIntent() {
            throw new RuntimeException("Stub!");
        }
        
        public String[] getParticipants() {
            throw new RuntimeException("Stub!");
        }
        
        public String getParticipant() {
            throw new RuntimeException("Stub!");
        }
        
        public long getLatestTimestamp() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class Builder
    {
        public Builder(final String name) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder addMessage(final String message) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setReplyAction(final PendingIntent pendingIntent, final RemoteInput remoteInput) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setReadPendingIntent(final PendingIntent pendingIntent) {
            throw new RuntimeException("Stub!");
        }
        
        public Builder setLatestTimestamp(final long timestamp) {
            throw new RuntimeException("Stub!");
        }
        
        public UnreadConversation build() {
            throw new RuntimeException("Stub!");
        }
    }
}
