package java.util.logging;

import java.util.*;
import java.nio.charset.*;

public class XMLFormatter extends Formatter
{
    private LogManager manager;
    
    public XMLFormatter() {
        this.manager = LogManager.getLogManager();
    }
    
    private void a2(final StringBuilder sb, final int n) {
        if (n < 10) {
            sb.append('0');
        }
        sb.append(n);
    }
    
    private void appendISO8601(final StringBuilder sb, final long timeInMillis) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(timeInMillis);
        sb.append(gregorianCalendar.get(1));
        sb.append('-');
        this.a2(sb, gregorianCalendar.get(2) + 1);
        sb.append('-');
        this.a2(sb, gregorianCalendar.get(5));
        sb.append('T');
        this.a2(sb, gregorianCalendar.get(11));
        sb.append(':');
        this.a2(sb, gregorianCalendar.get(12));
        sb.append(':');
        this.a2(sb, gregorianCalendar.get(13));
    }
    
    private void escape(final StringBuilder sb, String s) {
        if (s == null) {
            s = "<null>";
        }
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '<') {
                sb.append("&lt;");
            }
            else if (char1 == '>') {
                sb.append("&gt;");
            }
            else if (char1 == '&') {
                sb.append("&amp;");
            }
            else {
                sb.append(char1);
            }
        }
    }
    
    @Override
    public String format(final LogRecord logRecord) {
        final StringBuilder sb = new StringBuilder(500);
        sb.append("<record>\n");
        sb.append("  <date>");
        this.appendISO8601(sb, logRecord.getMillis());
        sb.append("</date>\n");
        sb.append("  <millis>");
        sb.append(logRecord.getMillis());
        sb.append("</millis>\n");
        sb.append("  <sequence>");
        sb.append(logRecord.getSequenceNumber());
        sb.append("</sequence>\n");
        final String loggerName = logRecord.getLoggerName();
        if (loggerName != null) {
            sb.append("  <logger>");
            this.escape(sb, loggerName);
            sb.append("</logger>\n");
        }
        sb.append("  <level>");
        this.escape(sb, logRecord.getLevel().toString());
        sb.append("</level>\n");
        if (logRecord.getSourceClassName() != null) {
            sb.append("  <class>");
            this.escape(sb, logRecord.getSourceClassName());
            sb.append("</class>\n");
        }
        if (logRecord.getSourceMethodName() != null) {
            sb.append("  <method>");
            this.escape(sb, logRecord.getSourceMethodName());
            sb.append("</method>\n");
        }
        sb.append("  <thread>");
        sb.append(logRecord.getThreadID());
        sb.append("</thread>\n");
        if (logRecord.getMessage() != null) {
            final String formatMessage = this.formatMessage(logRecord);
            sb.append("  <message>");
            this.escape(sb, formatMessage);
            sb.append("</message>");
            sb.append("\n");
        }
        final ResourceBundle resourceBundle = logRecord.getResourceBundle();
        try {
            if (resourceBundle != null && resourceBundle.getString(logRecord.getMessage()) != null) {
                sb.append("  <key>");
                this.escape(sb, logRecord.getMessage());
                sb.append("</key>\n");
                sb.append("  <catalog>");
                this.escape(sb, logRecord.getResourceBundleName());
                sb.append("</catalog>\n");
            }
        }
        catch (Exception ex) {}
        final Object[] parameters = logRecord.getParameters();
        if (parameters != null && parameters.length != 0 && logRecord.getMessage().indexOf("{") == -1) {
            for (int i = 0; i < parameters.length; ++i) {
                sb.append("  <param>");
                try {
                    this.escape(sb, parameters[i].toString());
                }
                catch (Exception ex2) {
                    sb.append("???");
                }
                sb.append("</param>\n");
            }
        }
        if (logRecord.getThrown() != null) {
            final Throwable thrown = logRecord.getThrown();
            sb.append("  <exception>\n");
            sb.append("    <message>");
            this.escape(sb, thrown.toString());
            sb.append("</message>\n");
            final StackTraceElement[] stackTrace = thrown.getStackTrace();
            for (int j = 0; j < stackTrace.length; ++j) {
                final StackTraceElement stackTraceElement = stackTrace[j];
                sb.append("    <frame>\n");
                sb.append("      <class>");
                this.escape(sb, stackTraceElement.getClassName());
                sb.append("</class>\n");
                sb.append("      <method>");
                this.escape(sb, stackTraceElement.getMethodName());
                sb.append("</method>\n");
                if (stackTraceElement.getLineNumber() >= 0) {
                    sb.append("      <line>");
                    sb.append(stackTraceElement.getLineNumber());
                    sb.append("</line>\n");
                }
                sb.append("    </frame>\n");
            }
            sb.append("  </exception>\n");
        }
        sb.append("</record>\n");
        return sb.toString();
    }
    
    @Override
    public String getHead(final Handler handler) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\"");
        String s;
        if (handler != null) {
            s = handler.getEncoding();
        }
        else {
            s = null;
        }
        if (s == null) {
            s = Charset.defaultCharset().name();
        }
        try {
            s = Charset.forName(s).name();
        }
        catch (Exception ex) {}
        sb.append(" encoding=\"");
        sb.append(s);
        sb.append("\"");
        sb.append(" standalone=\"no\"?>\n");
        sb.append("<!DOCTYPE log SYSTEM \"logger.dtd\">\n");
        sb.append("<log>\n");
        return sb.toString();
    }
    
    @Override
    public String getTail(final Handler handler) {
        return "</log>\n";
    }
}
