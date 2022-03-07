package android.os;

import java.io.*;

public interface OnFileDescriptorEventListener
{
    public static final int EVENT_ERROR = 4;
    public static final int EVENT_INPUT = 1;
    public static final int EVENT_OUTPUT = 2;
    
    int onFileDescriptorEvents(final FileDescriptor p0, final int p1);
}
