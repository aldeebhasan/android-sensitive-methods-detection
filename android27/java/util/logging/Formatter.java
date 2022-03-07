package java.util.logging;

import java.text.*;
import java.util.*;

public abstract class Formatter
{
    public abstract String format(final LogRecord p0);
    
    public String getHead(final Handler handler) {
        return "";
    }
    
    public String getTail(final Handler handler) {
        return "";
    }
    
    public synchronized String formatMessage(final LogRecord logRecord) {
        String s = logRecord.getMessage();
        final ResourceBundle resourceBundle = logRecord.getResourceBundle();
        if (resourceBundle != null) {
            try {
                s = resourceBundle.getString(logRecord.getMessage());
            }
            catch (MissingResourceException ex) {
                s = logRecord.getMessage();
            }
        }
        try {
            final Object[] parameters = logRecord.getParameters();
            if (parameters == null || parameters.length == 0) {
                return s;
            }
            if (s.indexOf("{0") >= 0 || s.indexOf("{1") >= 0 || s.indexOf("{2") >= 0 || s.indexOf("{3") >= 0) {
                return MessageFormat.format(s, parameters);
            }
            return s;
        }
        catch (Exception ex2) {
            return s;
        }
    }
}
