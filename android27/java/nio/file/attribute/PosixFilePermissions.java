package java.nio.file.attribute;

import java.util.*;

public final class PosixFilePermissions
{
    private static void writeBits(final StringBuilder sb, final boolean b, final boolean b2, final boolean b3) {
        if (b) {
            sb.append('r');
        }
        else {
            sb.append('-');
        }
        if (b2) {
            sb.append('w');
        }
        else {
            sb.append('-');
        }
        if (b3) {
            sb.append('x');
        }
        else {
            sb.append('-');
        }
    }
    
    public static String toString(final Set<PosixFilePermission> set) {
        final StringBuilder sb = new StringBuilder(9);
        writeBits(sb, set.contains(PosixFilePermission.OWNER_READ), set.contains(PosixFilePermission.OWNER_WRITE), set.contains(PosixFilePermission.OWNER_EXECUTE));
        writeBits(sb, set.contains(PosixFilePermission.GROUP_READ), set.contains(PosixFilePermission.GROUP_WRITE), set.contains(PosixFilePermission.GROUP_EXECUTE));
        writeBits(sb, set.contains(PosixFilePermission.OTHERS_READ), set.contains(PosixFilePermission.OTHERS_WRITE), set.contains(PosixFilePermission.OTHERS_EXECUTE));
        return sb.toString();
    }
    
    private static boolean isSet(final char c, final char c2) {
        if (c == c2) {
            return true;
        }
        if (c == '-') {
            return false;
        }
        throw new IllegalArgumentException("Invalid mode");
    }
    
    private static boolean isR(final char c) {
        return isSet(c, 'r');
    }
    
    private static boolean isW(final char c) {
        return isSet(c, 'w');
    }
    
    private static boolean isX(final char c) {
        return isSet(c, 'x');
    }
    
    public static Set<PosixFilePermission> fromString(final String s) {
        if (s.length() != 9) {
            throw new IllegalArgumentException("Invalid mode");
        }
        final EnumSet<PosixFilePermission> none = EnumSet.noneOf(PosixFilePermission.class);
        if (isR(s.charAt(0))) {
            none.add(PosixFilePermission.OWNER_READ);
        }
        if (isW(s.charAt(1))) {
            none.add(PosixFilePermission.OWNER_WRITE);
        }
        if (isX(s.charAt(2))) {
            none.add(PosixFilePermission.OWNER_EXECUTE);
        }
        if (isR(s.charAt(3))) {
            none.add(PosixFilePermission.GROUP_READ);
        }
        if (isW(s.charAt(4))) {
            none.add(PosixFilePermission.GROUP_WRITE);
        }
        if (isX(s.charAt(5))) {
            none.add(PosixFilePermission.GROUP_EXECUTE);
        }
        if (isR(s.charAt(6))) {
            none.add(PosixFilePermission.OTHERS_READ);
        }
        if (isW(s.charAt(7))) {
            none.add(PosixFilePermission.OTHERS_WRITE);
        }
        if (isX(s.charAt(8))) {
            none.add(PosixFilePermission.OTHERS_EXECUTE);
        }
        return none;
    }
    
    public static FileAttribute<Set<PosixFilePermission>> asFileAttribute(final Set<PosixFilePermission> set) {
        final HashSet<PosixFilePermission> set2 = new HashSet<PosixFilePermission>(set);
        final Iterator<Object> iterator = set2.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == null) {
                throw new NullPointerException();
            }
        }
        return new FileAttribute<Set<PosixFilePermission>>() {
            @Override
            public String name() {
                return "posix:permissions";
            }
            
            @Override
            public Set<PosixFilePermission> value() {
                return Collections.unmodifiableSet((Set<? extends PosixFilePermission>)set2);
            }
        };
    }
}
