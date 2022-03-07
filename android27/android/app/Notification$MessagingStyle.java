package android.app;

import java.util.*;
import android.net.*;
import android.os.*;

public static class MessagingStyle extends Style
{
    public static final int MAXIMUM_RETAINED_MESSAGES = 25;
    
    public MessagingStyle(final CharSequence userDisplayName) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getUserDisplayName() {
        throw new RuntimeException("Stub!");
    }
    
    public MessagingStyle setConversationTitle(final CharSequence conversationTitle) {
        throw new RuntimeException("Stub!");
    }
    
    public CharSequence getConversationTitle() {
        throw new RuntimeException("Stub!");
    }
    
    public MessagingStyle addMessage(final CharSequence text, final long timestamp, final CharSequence sender) {
        throw new RuntimeException("Stub!");
    }
    
    public MessagingStyle addMessage(final Message message) {
        throw new RuntimeException("Stub!");
    }
    
    public MessagingStyle addHistoricMessage(final Message message) {
        throw new RuntimeException("Stub!");
    }
    
    public List<Message> getMessages() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Message> getHistoricMessages() {
        throw new RuntimeException("Stub!");
    }
    
    public static final class Message
    {
        public Message(final CharSequence text, final long timestamp, final CharSequence sender) {
            throw new RuntimeException("Stub!");
        }
        
        public Message setData(final String dataMimeType, final Uri dataUri) {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getText() {
            throw new RuntimeException("Stub!");
        }
        
        public long getTimestamp() {
            throw new RuntimeException("Stub!");
        }
        
        public Bundle getExtras() {
            throw new RuntimeException("Stub!");
        }
        
        public CharSequence getSender() {
            throw new RuntimeException("Stub!");
        }
        
        public String getDataMimeType() {
            throw new RuntimeException("Stub!");
        }
        
        public Uri getDataUri() {
            throw new RuntimeException("Stub!");
        }
    }
}
