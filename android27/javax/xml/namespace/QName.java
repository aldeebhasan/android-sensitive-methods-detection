package javax.xml.namespace;

import java.io.*;
import java.security.*;

public class QName implements Serializable
{
    private static final long serialVersionUID;
    private static final long defaultSerialVersionUID = -9120448754896609940L;
    private static final long compatibleSerialVersionUID = 4418622981026545151L;
    private static boolean useDefaultSerialVersionUID;
    private final String namespaceURI;
    private final String localPart;
    private final String prefix;
    
    public QName(final String s, final String s2) {
        this(s, s2, "");
    }
    
    public QName(final String namespaceURI, final String localPart, final String prefix) {
        if (namespaceURI == null) {
            this.namespaceURI = "";
        }
        else {
            this.namespaceURI = namespaceURI;
        }
        if (localPart == null) {
            throw new IllegalArgumentException("local part cannot be \"null\" when creating a QName");
        }
        this.localPart = localPart;
        if (prefix == null) {
            throw new IllegalArgumentException("prefix cannot be \"null\" when creating a QName");
        }
        this.prefix = prefix;
    }
    
    public QName(final String s) {
        this("", s, "");
    }
    
    public String getNamespaceURI() {
        return this.namespaceURI;
    }
    
    public String getLocalPart() {
        return this.localPart;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || !(o instanceof QName)) {
            return false;
        }
        final QName qName = (QName)o;
        return this.localPart.equals(qName.localPart) && this.namespaceURI.equals(qName.namespaceURI);
    }
    
    @Override
    public final int hashCode() {
        return this.namespaceURI.hashCode() ^ this.localPart.hashCode();
    }
    
    @Override
    public String toString() {
        if (this.namespaceURI.equals("")) {
            return this.localPart;
        }
        return "{" + this.namespaceURI + "}" + this.localPart;
    }
    
    public static QName valueOf(final String s) {
        if (s == null) {
            throw new IllegalArgumentException("cannot create QName from \"null\" or \"\" String");
        }
        if (s.length() == 0) {
            return new QName("", s, "");
        }
        if (s.charAt(0) != '{') {
            return new QName("", s, "");
        }
        if (s.startsWith("{}")) {
            throw new IllegalArgumentException("Namespace URI .equals(XMLConstants.NULL_NS_URI), .equals(\"\"), only the local part, \"" + s.substring(2 + "".length()) + "\", should be provided.");
        }
        final int index = s.indexOf(125);
        if (index == -1) {
            throw new IllegalArgumentException("cannot create QName from \"" + s + "\", missing closing \"}\"");
        }
        return new QName(s.substring(1, index), s.substring(index + 1), "");
    }
    
    static {
        QName.useDefaultSerialVersionUID = true;
        try {
            final String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction() {
                @Override
                public Object run() {
                    return System.getProperty("com.sun.xml.namespace.QName.useCompatibleSerialVersionUID");
                }
            });
            QName.useDefaultSerialVersionUID = (s == null || !s.equals("1.0"));
        }
        catch (Exception ex) {
            QName.useDefaultSerialVersionUID = true;
        }
        if (QName.useDefaultSerialVersionUID) {
            serialVersionUID = -9120448754896609940L;
        }
        else {
            serialVersionUID = 4418622981026545151L;
        }
    }
}
