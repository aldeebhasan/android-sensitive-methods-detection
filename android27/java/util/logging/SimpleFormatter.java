package java.util.logging;

import java.util.*;
import java.io.*;
import sun.util.logging.*;

public class SimpleFormatter extends Formatter
{
    private static final String format;
    private final Date dat;
    
    public SimpleFormatter() {
        this.dat = new Date();
    }
    
    @Override
    public synchronized String format(final LogRecord logRecord) {
        this.dat.setTime(logRecord.getMillis());
        String s;
        if (logRecord.getSourceClassName() != null) {
            s = logRecord.getSourceClassName();
            if (logRecord.getSourceMethodName() != null) {
                s = s + " " + logRecord.getSourceMethodName();
            }
        }
        else {
            s = logRecord.getLoggerName();
        }
        final String formatMessage = this.formatMessage(logRecord);
        String string = "";
        if (logRecord.getThrown() != null) {
            final StringWriter stringWriter = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(stringWriter);
            printWriter.println();
            logRecord.getThrown().printStackTrace(printWriter);
            printWriter.close();
            string = stringWriter.toString();
        }
        return String.format(SimpleFormatter.format, this.dat, s, logRecord.getLoggerName(), logRecord.getLevel().getLocalizedLevelName(), formatMessage, string);
    }
    
    static {
        format = LoggingSupport.getSimpleFormat();
    }
}
