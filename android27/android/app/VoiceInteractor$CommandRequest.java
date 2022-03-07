package android.app;

import android.os.*;

public static class CommandRequest extends Request
{
    public CommandRequest(final String command, final Bundle args) {
        throw new RuntimeException("Stub!");
    }
    
    public void onCommandResult(final boolean isCompleted, final Bundle result) {
        throw new RuntimeException("Stub!");
    }
}
