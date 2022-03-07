package javax.net.ssl;

import java.net.*;
import java.nio.*;
import java.nio.charset.*;
import java.util.*;
import java.util.regex.*;

public final class SNIHostName extends SNIServerName
{
    private final String hostname;
    
    public SNIHostName(String ascii) {
        super(0, (ascii = IDN.toASCII(Objects.requireNonNull(ascii, "Server name value of host_name cannot be null"), 2)).getBytes(StandardCharsets.US_ASCII));
        this.hostname = ascii;
        this.checkHostName();
    }
    
    public SNIHostName(final byte[] array) {
        super(0, array);
        try {
            this.hostname = IDN.toASCII(StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(array)).toString());
        }
        catch (RuntimeException | CharacterCodingException ex) {
            final Object o;
            throw new IllegalArgumentException("The encoded server name value is invalid", (Throwable)o);
        }
        this.checkHostName();
    }
    
    public String getAsciiName() {
        return this.hostname;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof SNIHostName && this.hostname.equalsIgnoreCase(((SNIHostName)o).hostname));
    }
    
    @Override
    public int hashCode() {
        return 31 * 17 + this.hostname.toUpperCase(Locale.ENGLISH).hashCode();
    }
    
    @Override
    public String toString() {
        return "type=host_name (0), value=" + this.hostname;
    }
    
    public static SNIMatcher createSNIMatcher(final String s) {
        if (s == null) {
            throw new NullPointerException("The regular expression cannot be null");
        }
        return new SNIHostNameMatcher(s);
    }
    
    private void checkHostName() {
        if (this.hostname.isEmpty()) {
            throw new IllegalArgumentException("Server name value of host_name cannot be empty");
        }
        if (this.hostname.endsWith(".")) {
            throw new IllegalArgumentException("Server name value of host_name cannot have the trailing dot");
        }
    }
    
    private static final class SNIHostNameMatcher extends SNIMatcher
    {
        private final Pattern pattern;
        
        SNIHostNameMatcher(final String s) {
            super(0);
            this.pattern = Pattern.compile(s, 2);
        }
        
        @Override
        public boolean matches(final SNIServerName sniServerName) {
            if (sniServerName == null) {
                throw new NullPointerException("The SNIServerName argument cannot be null");
            }
            SNIHostName sniHostName = null;
            Label_0061: {
                if (!(sniServerName instanceof SNIHostName)) {
                    if (sniServerName.getType() != 0) {
                        throw new IllegalArgumentException("The server name type is not host_name");
                    }
                    try {
                        sniHostName = new SNIHostName(sniServerName.getEncoded());
                        break Label_0061;
                    }
                    catch (NullPointerException | IllegalArgumentException ex) {
                        return false;
                    }
                }
                sniHostName = (SNIHostName)sniServerName;
            }
            final String asciiName = sniHostName.getAsciiName();
            return this.pattern.matcher(asciiName).matches() || this.pattern.matcher(IDN.toUnicode(asciiName)).matches();
        }
    }
}
