package java.net;

import sun.security.util.*;
import java.util.*;
import sun.net.util.*;
import sun.net.www.*;
import java.io.*;
import sun.net.*;
import java.security.*;
import sun.security.action.*;

public final class SocketPermission extends Permission implements Serializable
{
    private static final long serialVersionUID = -7204263841984476862L;
    private static final int CONNECT = 1;
    private static final int LISTEN = 2;
    private static final int ACCEPT = 4;
    private static final int RESOLVE = 8;
    private static final int NONE = 0;
    private static final int ALL = 15;
    private static final int PORT_MIN = 0;
    private static final int PORT_MAX = 65535;
    private static final int PRIV_PORT_MAX = 1023;
    private static final int DEF_EPH_LOW = 49152;
    private transient int mask;
    private String actions;
    private transient String hostname;
    private transient String cname;
    private transient InetAddress[] addresses;
    private transient boolean wildcard;
    private transient boolean init_with_ip;
    private transient boolean invalid;
    private transient int[] portrange;
    private transient boolean defaultDeny;
    private transient boolean untrusted;
    private transient boolean trusted;
    private static boolean trustNameService;
    private static Debug debug;
    private static boolean debugInit;
    private transient String cdomain;
    private transient String hdomain;
    
    private static synchronized Debug getDebug() {
        if (!SocketPermission.debugInit) {
            SocketPermission.debug = Debug.getInstance("access");
            SocketPermission.debugInit = true;
        }
        return SocketPermission.debug;
    }
    
    public SocketPermission(final String s, final String s2) {
        super(getHost(s));
        this.defaultDeny = false;
        this.init(this.getName(), getMask(s2));
    }
    
    SocketPermission(final String s, final int n) {
        super(getHost(s));
        this.defaultDeny = false;
        this.init(this.getName(), n);
    }
    
    private void setDeny() {
        this.defaultDeny = true;
    }
    
    private static String getHost(String s) {
        if (s.equals("")) {
            return "localhost";
        }
        final int index;
        if (s.charAt(0) != '[' && (index = s.indexOf(58)) != s.lastIndexOf(58)) {
            final int countTokens = new StringTokenizer(s, ":").countTokens();
            if (countTokens == 9) {
                final int lastIndex = s.lastIndexOf(58);
                s = "[" + s.substring(0, lastIndex) + "]" + s.substring(lastIndex);
            }
            else {
                if (countTokens != 8 || s.indexOf("::") != -1) {
                    throw new IllegalArgumentException("Ambiguous hostport part");
                }
                s = "[" + s + "]";
            }
        }
        return s;
    }
    
    private int[] parsePort(final String s) throws Exception {
        if (s == null || s.equals("") || s.equals("*")) {
            return new int[] { 0, 65535 };
        }
        final int index = s.indexOf(45);
        if (index == -1) {
            final int int1 = Integer.parseInt(s);
            return new int[] { int1, int1 };
        }
        final String substring = s.substring(0, index);
        final String substring2 = s.substring(index + 1);
        int int2;
        if (substring.equals("")) {
            int2 = 0;
        }
        else {
            int2 = Integer.parseInt(substring);
        }
        int int3;
        if (substring2.equals("")) {
            int3 = 65535;
        }
        else {
            int3 = Integer.parseInt(substring2);
        }
        if (int2 < 0 || int3 < 0 || int3 < int2) {
            throw new IllegalArgumentException("invalid port range");
        }
        return new int[] { int2, int3 };
    }
    
    private boolean includesEphemerals() {
        return this.portrange[0] == 0;
    }
    
    private void init(String hostname, final int n) {
        if ((n & 0xF) != n) {
            throw new IllegalArgumentException("invalid actions mask");
        }
        this.mask = (n | 0x8);
        final int n2 = 0;
        final String s = hostname;
        int n4;
        if (hostname.charAt(0) == '[') {
            final int n3 = 1;
            final int index = hostname.indexOf(93);
            if (index == -1) {
                throw new IllegalArgumentException("invalid host/port: " + hostname);
            }
            hostname = hostname.substring(n3, index);
            n4 = s.indexOf(58, index + 1);
        }
        else {
            final int n5 = 0;
            n4 = hostname.indexOf(58, n2);
            final int n6;
            if ((n6 = n4) != -1) {
                hostname = hostname.substring(n5, n6);
            }
        }
        if (n4 != -1) {
            final String substring = s.substring(n4 + 1);
            try {
                this.portrange = this.parsePort(substring);
            }
            catch (Exception ex) {
                throw new IllegalArgumentException("invalid port range: " + substring);
            }
        }
        else {
            this.portrange = new int[] { 0, 65535 };
        }
        this.hostname = hostname;
        if (hostname.lastIndexOf(42) > 0) {
            throw new IllegalArgumentException("invalid host wildcard specification");
        }
        if (hostname.startsWith("*")) {
            this.wildcard = true;
            if (hostname.equals("*")) {
                this.cname = "";
            }
            else {
                if (!hostname.startsWith("*.")) {
                    throw new IllegalArgumentException("invalid host wildcard specification");
                }
                this.cname = hostname.substring(1).toLowerCase();
            }
            return;
        }
        if (hostname.length() > 0) {
            final char char1 = hostname.charAt(0);
            if (char1 == ':' || Character.digit(char1, 16) != -1) {
                byte[] array = IPAddressUtil.textToNumericFormatV4(hostname);
                if (array == null) {
                    array = IPAddressUtil.textToNumericFormatV6(hostname);
                }
                if (array != null) {
                    try {
                        this.addresses = new InetAddress[] { InetAddress.getByAddress(array) };
                        this.init_with_ip = true;
                    }
                    catch (UnknownHostException ex2) {
                        this.invalid = true;
                    }
                }
            }
        }
    }
    
    private static int getMask(final String s) {
        if (s == null) {
            throw new NullPointerException("action can't be null");
        }
        if (s.equals("")) {
            throw new IllegalArgumentException("action can't be empty");
        }
        int n = 0;
        if (s == "resolve") {
            return 8;
        }
        if (s == "connect") {
            return 1;
        }
        if (s == "listen") {
            return 2;
        }
        if (s == "accept") {
            return 4;
        }
        if (s == "connect,accept") {
            return 5;
        }
        final char[] charArray = s.toCharArray();
        int i = charArray.length - 1;
        if (i < 0) {
            return n;
        }
        while (i != -1) {
            char c;
            while (i != -1 && ((c = charArray[i]) == ' ' || c == '\r' || c == '\n' || c == '\f' || c == '\t')) {
                --i;
            }
            int n2;
            if (i >= 6 && (charArray[i - 6] == 'c' || charArray[i - 6] == 'C') && (charArray[i - 5] == 'o' || charArray[i - 5] == 'O') && (charArray[i - 4] == 'n' || charArray[i - 4] == 'N') && (charArray[i - 3] == 'n' || charArray[i - 3] == 'N') && (charArray[i - 2] == 'e' || charArray[i - 2] == 'E') && (charArray[i - 1] == 'c' || charArray[i - 1] == 'C') && (charArray[i] == 't' || charArray[i] == 'T')) {
                n2 = 7;
                n |= 0x1;
            }
            else if (i >= 6 && (charArray[i - 6] == 'r' || charArray[i - 6] == 'R') && (charArray[i - 5] == 'e' || charArray[i - 5] == 'E') && (charArray[i - 4] == 's' || charArray[i - 4] == 'S') && (charArray[i - 3] == 'o' || charArray[i - 3] == 'O') && (charArray[i - 2] == 'l' || charArray[i - 2] == 'L') && (charArray[i - 1] == 'v' || charArray[i - 1] == 'V') && (charArray[i] == 'e' || charArray[i] == 'E')) {
                n2 = 7;
                n |= 0x8;
            }
            else if (i >= 5 && (charArray[i - 5] == 'l' || charArray[i - 5] == 'L') && (charArray[i - 4] == 'i' || charArray[i - 4] == 'I') && (charArray[i - 3] == 's' || charArray[i - 3] == 'S') && (charArray[i - 2] == 't' || charArray[i - 2] == 'T') && (charArray[i - 1] == 'e' || charArray[i - 1] == 'E') && (charArray[i] == 'n' || charArray[i] == 'N')) {
                n2 = 6;
                n |= 0x2;
            }
            else {
                if (i < 5 || (charArray[i - 5] != 'a' && charArray[i - 5] != 'A') || (charArray[i - 4] != 'c' && charArray[i - 4] != 'C') || (charArray[i - 3] != 'c' && charArray[i - 3] != 'C') || (charArray[i - 2] != 'e' && charArray[i - 2] != 'E') || (charArray[i - 1] != 'p' && charArray[i - 1] != 'P') || (charArray[i] != 't' && charArray[i] != 'T')) {
                    throw new IllegalArgumentException("invalid permission: " + s);
                }
                n2 = 6;
                n |= 0x4;
            }
            for (int n3 = 0; i >= n2 && n3 == 0; --i) {
                switch (charArray[i - n2]) {
                    case ',': {
                        n3 = 1;
                        break;
                    }
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ': {
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("invalid permission: " + s);
                    }
                }
            }
            i -= n2;
        }
        return n;
    }
    
    private boolean isUntrusted() throws UnknownHostException {
        if (this.trusted) {
            return false;
        }
        if (this.invalid || this.untrusted) {
            return true;
        }
        try {
            if (!SocketPermission.trustNameService && (this.defaultDeny || URLConnection.isProxiedHost(this.hostname))) {
                if (this.cname == null) {
                    this.getCanonName();
                }
                if (!this.match(this.cname, this.hostname) && !this.authorized(this.hostname, this.addresses[0].getAddress())) {
                    this.untrusted = true;
                    final Debug debug = getDebug();
                    if (debug != null && Debug.isOn("failure")) {
                        debug.println("socket access restriction: proxied host (" + this.addresses[0] + ") does not match " + this.cname + " from reverse lookup");
                    }
                    return true;
                }
                this.trusted = true;
            }
        }
        catch (UnknownHostException ex) {
            this.invalid = true;
            throw ex;
        }
        return false;
    }
    
    void getCanonName() throws UnknownHostException {
        if (this.cname != null || this.invalid || this.untrusted) {
            return;
        }
        try {
            if (this.addresses == null) {
                this.getIP();
            }
            if (this.init_with_ip) {
                this.cname = this.addresses[0].getHostName(false).toLowerCase();
            }
            else {
                this.cname = InetAddress.getByName(this.addresses[0].getHostAddress()).getHostName(false).toLowerCase();
            }
        }
        catch (UnknownHostException ex) {
            this.invalid = true;
            throw ex;
        }
    }
    
    private boolean match(final String s, final String s2) {
        final String lowerCase = s.toLowerCase();
        final String lowerCase2 = s2.toLowerCase();
        if (lowerCase.startsWith(lowerCase2) && (lowerCase.length() == lowerCase2.length() || lowerCase.charAt(lowerCase2.length()) == '.')) {
            return true;
        }
        if (this.cdomain == null) {
            this.cdomain = RegisteredDomain.getRegisteredDomain(lowerCase);
        }
        if (this.hdomain == null) {
            this.hdomain = RegisteredDomain.getRegisteredDomain(lowerCase2);
        }
        return this.cdomain.length() != 0 && this.hdomain.length() != 0 && this.cdomain.equals(this.hdomain);
    }
    
    private boolean authorized(final String s, final byte[] array) {
        if (array.length == 4) {
            return this.authorizedIPv4(s, array);
        }
        return array.length == 16 && this.authorizedIPv6(s, array);
    }
    
    private boolean authorizedIPv4(final String s, final byte[] array) {
        String s2 = "";
        try {
            s2 = "auth." + (array[3] & 0xFF) + "." + (array[2] & 0xFF) + "." + (array[1] & 0xFF) + "." + (array[0] & 0xFF) + ".in-addr.arpa";
            s2 = this.hostname + '.' + s2;
            final InetAddress inetAddress = InetAddress.getAllByName0(s2, false)[0];
            if (inetAddress.equals(InetAddress.getByAddress(array))) {
                return true;
            }
            final Debug debug = getDebug();
            if (debug != null && Debug.isOn("failure")) {
                debug.println("socket access restriction: IP address of " + inetAddress + " != " + InetAddress.getByAddress(array));
            }
        }
        catch (UnknownHostException ex) {
            final Debug debug2 = getDebug();
            if (debug2 != null && Debug.isOn("failure")) {
                debug2.println("socket access restriction: forward lookup failed for " + s2);
            }
        }
        return false;
    }
    
    private boolean authorizedIPv6(final String s, final byte[] array) {
        String s2 = "";
        try {
            final StringBuffer sb = new StringBuffer(39);
            for (int i = 15; i >= 0; --i) {
                sb.append(Integer.toHexString(array[i] & 0xF));
                sb.append('.');
                sb.append(Integer.toHexString(array[i] >> 4 & 0xF));
                sb.append('.');
            }
            s2 = "auth." + sb.toString() + "IP6.ARPA";
            s2 = this.hostname + '.' + s2;
            final InetAddress inetAddress = InetAddress.getAllByName0(s2, false)[0];
            if (inetAddress.equals(InetAddress.getByAddress(array))) {
                return true;
            }
            final Debug debug = getDebug();
            if (debug != null && Debug.isOn("failure")) {
                debug.println("socket access restriction: IP address of " + inetAddress + " != " + InetAddress.getByAddress(array));
            }
        }
        catch (UnknownHostException ex) {
            final Debug debug2 = getDebug();
            if (debug2 != null && Debug.isOn("failure")) {
                debug2.println("socket access restriction: forward lookup failed for " + s2);
            }
        }
        return false;
    }
    
    void getIP() throws UnknownHostException {
        if (this.addresses != null || this.wildcard || this.invalid) {
            return;
        }
        try {
            String s;
            if (this.getName().charAt(0) == '[') {
                s = this.getName().substring(1, this.getName().indexOf(93));
            }
            else {
                final int index = this.getName().indexOf(":");
                if (index == -1) {
                    s = this.getName();
                }
                else {
                    s = this.getName().substring(0, index);
                }
            }
            this.addresses = new InetAddress[] { InetAddress.getAllByName0(s, false)[0] };
        }
        catch (UnknownHostException ex) {
            this.invalid = true;
            throw ex;
        }
        catch (IndexOutOfBoundsException ex2) {
            this.invalid = true;
            throw new UnknownHostException(this.getName());
        }
    }
    
    @Override
    public boolean implies(final Permission permission) {
        if (!(permission instanceof SocketPermission)) {
            return false;
        }
        if (permission == this) {
            return true;
        }
        final SocketPermission socketPermission = (SocketPermission)permission;
        return (this.mask & socketPermission.mask) == socketPermission.mask && this.impliesIgnoreMask(socketPermission);
    }
    
    boolean impliesIgnoreMask(final SocketPermission socketPermission) {
        if ((socketPermission.mask & 0x8) != socketPermission.mask && (socketPermission.portrange[0] < this.portrange[0] || socketPermission.portrange[1] > this.portrange[1])) {
            if (!this.includesEphemerals() && !socketPermission.includesEphemerals()) {
                return false;
            }
            if (!inRange(this.portrange[0], this.portrange[1], socketPermission.portrange[0], socketPermission.portrange[1])) {
                return false;
            }
        }
        if (this.wildcard && "".equals(this.cname)) {
            return true;
        }
        if (this.invalid || socketPermission.invalid) {
            return this.compareHostnames(socketPermission);
        }
        try {
            if (this.init_with_ip) {
                if (socketPermission.wildcard) {
                    return false;
                }
                if (socketPermission.init_with_ip) {
                    return this.addresses[0].equals(socketPermission.addresses[0]);
                }
                if (socketPermission.addresses == null) {
                    socketPermission.getIP();
                }
                for (int i = 0; i < socketPermission.addresses.length; ++i) {
                    if (this.addresses[0].equals(socketPermission.addresses[i])) {
                        return true;
                    }
                }
                return false;
            }
            else if (this.wildcard || socketPermission.wildcard) {
                if (this.wildcard && socketPermission.wildcard) {
                    return socketPermission.cname.endsWith(this.cname);
                }
                if (socketPermission.wildcard) {
                    return false;
                }
                if (socketPermission.cname == null) {
                    socketPermission.getCanonName();
                }
                return socketPermission.cname.endsWith(this.cname);
            }
            else {
                if (this.addresses == null) {
                    this.getIP();
                }
                if (socketPermission.addresses == null) {
                    socketPermission.getIP();
                }
                if (!socketPermission.init_with_ip || !this.isUntrusted()) {
                    for (int j = 0; j < this.addresses.length; ++j) {
                        for (int k = 0; k < socketPermission.addresses.length; ++k) {
                            if (this.addresses[j].equals(socketPermission.addresses[k])) {
                                return true;
                            }
                        }
                    }
                    if (this.cname == null) {
                        this.getCanonName();
                    }
                    if (socketPermission.cname == null) {
                        socketPermission.getCanonName();
                    }
                    return this.cname.equalsIgnoreCase(socketPermission.cname);
                }
            }
        }
        catch (UnknownHostException ex) {
            return this.compareHostnames(socketPermission);
        }
        return false;
    }
    
    private boolean compareHostnames(final SocketPermission socketPermission) {
        final String hostname = this.hostname;
        final String hostname2 = socketPermission.hostname;
        if (hostname == null) {
            return false;
        }
        if (this.wildcard) {
            final int length = this.cname.length();
            return hostname2.regionMatches(true, hostname2.length() - length, this.cname, 0, length);
        }
        return hostname.equalsIgnoreCase(hostname2);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SocketPermission)) {
            return false;
        }
        final SocketPermission socketPermission = (SocketPermission)o;
        if (this.mask != socketPermission.mask) {
            return false;
        }
        if ((socketPermission.mask & 0x8) != socketPermission.mask && (this.portrange[0] != socketPermission.portrange[0] || this.portrange[1] != socketPermission.portrange[1])) {
            return false;
        }
        if (this.getName().equalsIgnoreCase(socketPermission.getName())) {
            return true;
        }
        try {
            this.getCanonName();
            socketPermission.getCanonName();
        }
        catch (UnknownHostException ex) {
            return false;
        }
        return !this.invalid && !socketPermission.invalid && this.cname != null && this.cname.equalsIgnoreCase(socketPermission.cname);
    }
    
    @Override
    public int hashCode() {
        if (this.init_with_ip || this.wildcard) {
            return this.getName().hashCode();
        }
        try {
            this.getCanonName();
        }
        catch (UnknownHostException ex) {}
        if (this.invalid || this.cname == null) {
            return this.getName().hashCode();
        }
        return this.cname.hashCode();
    }
    
    int getMask() {
        return this.mask;
    }
    
    private static String getActions(final int n) {
        final StringBuilder sb = new StringBuilder();
        int n2 = 0;
        if ((n & 0x1) == 0x1) {
            n2 = 1;
            sb.append("connect");
        }
        if ((n & 0x2) == 0x2) {
            if (n2 != 0) {
                sb.append(',');
            }
            else {
                n2 = 1;
            }
            sb.append("listen");
        }
        if ((n & 0x4) == 0x4) {
            if (n2 != 0) {
                sb.append(',');
            }
            else {
                n2 = 1;
            }
            sb.append("accept");
        }
        if ((n & 0x8) == 0x8) {
            if (n2 != 0) {
                sb.append(',');
            }
            sb.append("resolve");
        }
        return sb.toString();
    }
    
    @Override
    public String getActions() {
        if (this.actions == null) {
            this.actions = getActions(this.mask);
        }
        return this.actions;
    }
    
    @Override
    public PermissionCollection newPermissionCollection() {
        return new SocketPermissionCollection();
    }
    
    private synchronized void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        if (this.actions == null) {
            this.getActions();
        }
        objectOutputStream.defaultWriteObject();
    }
    
    private synchronized void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.init(this.getName(), getMask(this.actions));
    }
    
    private static int initEphemeralPorts(final String s, final int n) {
        return AccessController.doPrivileged((PrivilegedAction<Integer>)new PrivilegedAction<Integer>() {
            @Override
            public Integer run() {
                final int intValue = Integer.getInteger("jdk.net.ephemeralPortRange." + s, -1);
                if (intValue != -1) {
                    return intValue;
                }
                return s.equals("low") ? PortConfig.getLower() : PortConfig.getUpper();
            }
        });
    }
    
    private static boolean inRange(final int n, final int n2, int n3, final int n4) {
        final int low = EphemeralRange.low;
        final int high = EphemeralRange.high;
        if (n3 == 0) {
            if (!inRange(n, n2, low, high)) {
                return false;
            }
            if (n4 == 0) {
                return true;
            }
            n3 = 1;
        }
        if (n == 0 && n2 == 0) {
            return n3 >= low && n4 <= high;
        }
        if (n != 0) {
            return n3 >= n && n4 <= n2;
        }
        if (n2 >= low - 1) {
            return n4 <= high;
        }
        return (n3 <= n2 && n4 <= n2) || (n3 >= low && n4 <= high);
    }
    
    static {
        SocketPermission.debug = null;
        SocketPermission.debugInit = false;
        SocketPermission.trustNameService = AccessController.doPrivileged((PrivilegedAction<Boolean>)new GetBooleanAction("sun.net.trustNameService"));
    }
    
    private static class EphemeralRange
    {
        static final int low;
        static final int high;
        
        static {
            low = initEphemeralPorts("low", 49152);
            high = initEphemeralPorts("high", 65535);
        }
    }
}
