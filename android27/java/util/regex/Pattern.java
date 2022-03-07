package java.util.regex;

import java.text.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;
import java.io.*;

public final class Pattern implements Serializable
{
    public static final int UNIX_LINES = 1;
    public static final int CASE_INSENSITIVE = 2;
    public static final int COMMENTS = 4;
    public static final int MULTILINE = 8;
    public static final int LITERAL = 16;
    public static final int DOTALL = 32;
    public static final int UNICODE_CASE = 64;
    public static final int CANON_EQ = 128;
    public static final int UNICODE_CHARACTER_CLASS = 256;
    private static final long serialVersionUID = 5073258162644648461L;
    private String pattern;
    private int flags;
    private transient volatile boolean compiled;
    private transient String normalizedPattern;
    transient Node root;
    transient Node matchRoot;
    transient int[] buffer;
    transient volatile Map<String, Integer> namedGroups;
    transient GroupHead[] groupNodes;
    private transient int[] temp;
    transient int capturingGroupCount;
    transient int localCount;
    private transient int cursor;
    private transient int patternLength;
    private transient boolean hasSupplementary;
    static final int MAX_REPS = Integer.MAX_VALUE;
    static final int GREEDY = 0;
    static final int LAZY = 1;
    static final int POSSESSIVE = 2;
    static final int INDEPENDENT = 3;
    static Node lookbehindEnd;
    static Node accept;
    static Node lastAccept;
    
    public static Pattern compile(final String s) {
        return new Pattern(s, 0);
    }
    
    public static Pattern compile(final String s, final int n) {
        return new Pattern(s, n);
    }
    
    public String pattern() {
        return this.pattern;
    }
    
    @Override
    public String toString() {
        return this.pattern;
    }
    
    public Matcher matcher(final CharSequence charSequence) {
        if (!this.compiled) {
            synchronized (this) {
                if (!this.compiled) {
                    this.compile();
                }
            }
        }
        return new Matcher(this, charSequence);
    }
    
    public int flags() {
        return this.flags;
    }
    
    public static boolean matches(final String s, final CharSequence charSequence) {
        return compile(s).matcher(charSequence).matches();
    }
    
    public String[] split(final CharSequence charSequence, final int n) {
        int n2 = 0;
        final boolean b = n > 0;
        final ArrayList<String> list = new ArrayList<String>();
        final Matcher matcher = this.matcher(charSequence);
        while (matcher.find()) {
            if (!b || list.size() < n - 1) {
                if (n2 == 0 && n2 == matcher.start() && matcher.start() == matcher.end()) {
                    continue;
                }
                list.add(charSequence.subSequence(n2, matcher.start()).toString());
                n2 = matcher.end();
            }
            else {
                if (list.size() != n - 1) {
                    continue;
                }
                list.add(charSequence.subSequence(n2, charSequence.length()).toString());
                n2 = matcher.end();
            }
        }
        if (n2 == 0) {
            return new String[] { charSequence.toString() };
        }
        if (!b || list.size() < n) {
            list.add(charSequence.subSequence(n2, charSequence.length()).toString());
        }
        int size = list.size();
        if (n == 0) {
            while (size > 0 && list.get(size - 1).equals("")) {
                --size;
            }
        }
        return list.subList(0, size).toArray(new String[size]);
    }
    
    public String[] split(final CharSequence charSequence) {
        return this.split(charSequence, 0);
    }
    
    public static String quote(final String s) {
        if (s.indexOf("\\E") == -1) {
            return "\\Q" + s + "\\E";
        }
        final StringBuilder sb = new StringBuilder(s.length() * 2);
        sb.append("\\Q");
        int n = 0;
        int index;
        while ((index = s.indexOf("\\E", n)) != -1) {
            sb.append(s.substring(n, index));
            n = index + 2;
            sb.append("\\E\\\\E\\Q");
        }
        sb.append(s.substring(n, s.length()));
        sb.append("\\E");
        return sb.toString();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.capturingGroupCount = 1;
        this.localCount = 0;
        this.compiled = false;
        if (this.pattern.length() == 0) {
            this.root = new Start(Pattern.lastAccept);
            this.matchRoot = Pattern.lastAccept;
            this.compiled = true;
        }
    }
    
    private Pattern(final String pattern, final int flags) {
        this.compiled = false;
        this.pattern = pattern;
        this.flags = flags;
        if ((this.flags & 0x100) != 0x0) {
            this.flags |= 0x40;
        }
        this.capturingGroupCount = 1;
        this.localCount = 0;
        if (this.pattern.length() > 0) {
            try {
                this.compile();
                return;
            }
            catch (StackOverflowError stackOverflowError) {
                throw this.error("Stack overflow during pattern compilation");
            }
        }
        this.root = new Start(Pattern.lastAccept);
        this.matchRoot = Pattern.lastAccept;
    }
    
    private void normalize() {
        int n = -1;
        this.normalizedPattern = Normalizer.normalize(this.pattern, Normalizer.Form.NFD);
        this.patternLength = this.normalizedPattern.length();
        final StringBuilder sb = new StringBuilder(this.patternLength);
        int n2;
        for (int i = 0; i < this.patternLength; i += Character.charCount(n2)) {
            n2 = this.normalizedPattern.codePointAt(i);
            if (Character.getType(n2) == 6 && n != -1) {
                final StringBuilder sb2 = new StringBuilder();
                sb2.appendCodePoint(n);
                sb2.appendCodePoint(n2);
                while (Character.getType(n2) == 6) {
                    i += Character.charCount(n2);
                    if (i >= this.patternLength) {
                        break;
                    }
                    n2 = this.normalizedPattern.codePointAt(i);
                    sb2.appendCodePoint(n2);
                }
                final String produceEquivalentAlternation = this.produceEquivalentAlternation(sb2.toString());
                sb.setLength(sb.length() - Character.charCount(n));
                sb.append("(?:").append(produceEquivalentAlternation).append(")");
            }
            else if (n2 == 91 && n != 92) {
                i = this.normalizeCharClass(sb, i);
            }
            else {
                sb.appendCodePoint(n2);
            }
            n = n2;
        }
        this.normalizedPattern = sb.toString();
    }
    
    private int normalizeCharClass(final StringBuilder sb, int n) {
        final StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = null;
        int n2 = -1;
        if (++n == this.normalizedPattern.length()) {
            throw this.error("Unclosed character class");
        }
        sb2.append("[");
        while (true) {
            int n3 = this.normalizedPattern.codePointAt(n);
            if (n3 == 93 && n2 != 92) {
                sb2.append((char)n3);
                String s;
                if (sb3 != null) {
                    s = "(?:" + sb2.toString() + sb3.toString() + ")";
                }
                else {
                    s = sb2.toString();
                }
                sb.append(s);
                return n;
            }
            if (Character.getType(n3) == 6) {
                final StringBuilder sb4 = new StringBuilder();
                sb4.appendCodePoint(n2);
                while (Character.getType(n3) == 6) {
                    sb4.appendCodePoint(n3);
                    n += Character.charCount(n3);
                    if (n >= this.normalizedPattern.length()) {
                        break;
                    }
                    n3 = this.normalizedPattern.codePointAt(n);
                }
                final String produceEquivalentAlternation = this.produceEquivalentAlternation(sb4.toString());
                sb2.setLength(sb2.length() - Character.charCount(n2));
                if (sb3 == null) {
                    sb3 = new StringBuilder();
                }
                sb3.append('|');
                sb3.append(produceEquivalentAlternation);
            }
            else {
                sb2.appendCodePoint(n3);
                ++n;
            }
            if (n == this.normalizedPattern.length()) {
                throw this.error("Unclosed character class");
            }
            n2 = n3;
        }
    }
    
    private String produceEquivalentAlternation(final String s) {
        final int countChars = countChars(s, 0, 1);
        if (s.length() == countChars) {
            return s;
        }
        final String substring = s.substring(0, countChars);
        final String[] producePermutations = this.producePermutations(s.substring(countChars));
        final StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < producePermutations.length; ++i) {
            final String string = substring + producePermutations[i];
            if (i > 0) {
                sb.append("|" + string);
            }
            final String composeOneStep = this.composeOneStep(string);
            if (composeOneStep != null) {
                sb.append("|" + this.produceEquivalentAlternation(composeOneStep));
            }
        }
        return sb.toString();
    }
    
    private String[] producePermutations(final String s) {
        if (s.length() == countChars(s, 0, 1)) {
            return new String[] { s };
        }
        if (s.length() != countChars(s, 0, 2)) {
            int n = 1;
            final int countCodePoints = countCodePoints(s);
            for (int i = 1; i < countCodePoints; ++i) {
                n *= i + 1;
            }
            final String[] array = new String[n];
            final int[] array2 = new int[countCodePoints];
            int j = 0;
            int n2 = 0;
            while (j < countCodePoints) {
                final int codePoint = Character.codePointAt(s, n2);
                array2[j] = this.getClass(codePoint);
                n2 += Character.charCount(codePoint);
                ++j;
            }
            int n3 = 0;
            int k = 0;
            int n4 = 0;
        Label_0215:
            while (k < countCodePoints) {
                final int countChars = countChars(s, n4, 1);
                while (true) {
                    for (int l = k - 1; l >= 0; --l) {
                        if (array2[l] == array2[k]) {
                            ++k;
                            n4 += countChars;
                            continue Label_0215;
                        }
                    }
                    final String[] producePermutations = this.producePermutations(new StringBuilder(s).delete(n4, n4 + countChars).toString());
                    final String substring = s.substring(n4, n4 + countChars);
                    for (int n5 = 0; n5 < producePermutations.length; ++n5) {
                        array[n3++] = substring + producePermutations[n5];
                    }
                    continue;
                }
            }
            final String[] array3 = new String[n3];
            for (int n6 = 0; n6 < n3; ++n6) {
                array3[n6] = array[n6];
            }
            return array3;
        }
        final int codePoint2 = Character.codePointAt(s, 0);
        final int codePoint3 = Character.codePointAt(s, Character.charCount(codePoint2));
        if (this.getClass(codePoint3) == this.getClass(codePoint2)) {
            return new String[] { s };
        }
        final String[] array4 = { s, null };
        final StringBuilder sb = new StringBuilder(2);
        sb.appendCodePoint(codePoint3);
        sb.appendCodePoint(codePoint2);
        array4[1] = sb.toString();
        return array4;
    }
    
    private int getClass(final int n) {
        return sun.text.Normalizer.getCombiningClass(n);
    }
    
    private String composeOneStep(final String s) {
        final int countChars = countChars(s, 0, 2);
        final String substring = s.substring(0, countChars);
        final String normalize = Normalizer.normalize(substring, Normalizer.Form.NFC);
        if (normalize.equals(substring)) {
            return null;
        }
        return normalize + s.substring(countChars);
    }
    
    private void RemoveQEQuoting() {
        final int patternLength = this.patternLength;
        int i = 0;
        while (i < patternLength - 1) {
            if (this.temp[i] != 92) {
                ++i;
            }
            else {
                if (this.temp[i + 1] == 81) {
                    break;
                }
                i += 2;
            }
        }
        if (i >= patternLength - 1) {
            return;
        }
        int patternLength2 = i;
        i += 2;
        final int[] array = new int[patternLength2 + 3 * (patternLength - i) + 2];
        System.arraycopy(this.temp, 0, array, 0, patternLength2);
        int n = 1;
        int n2 = 1;
        while (i < patternLength) {
            final int n3 = this.temp[i++];
            if (!ASCII.isAscii(n3) || ASCII.isAlpha(n3)) {
                array[patternLength2++] = n3;
            }
            else if (ASCII.isDigit(n3)) {
                if (n2 != 0) {
                    array[patternLength2++] = 92;
                    array[patternLength2++] = 120;
                    array[patternLength2++] = 51;
                }
                array[patternLength2++] = n3;
            }
            else if (n3 != 92) {
                if (n != 0) {
                    array[patternLength2++] = 92;
                }
                array[patternLength2++] = n3;
            }
            else if (n != 0) {
                if (this.temp[i] == 69) {
                    ++i;
                    n = 0;
                }
                else {
                    array[patternLength2++] = 92;
                    array[patternLength2++] = 92;
                }
            }
            else {
                if (this.temp[i] == 81) {
                    ++i;
                    n = 1;
                    n2 = 1;
                    continue;
                }
                array[patternLength2++] = n3;
                if (i != patternLength) {
                    array[patternLength2++] = this.temp[i++];
                }
            }
            n2 = 0;
        }
        this.patternLength = patternLength2;
        this.temp = Arrays.copyOf(array, patternLength2 + 2);
    }
    
    private void compile() {
        if (this.has(128) && !this.has(16)) {
            this.normalize();
        }
        else {
            this.normalizedPattern = this.pattern;
        }
        this.patternLength = this.normalizedPattern.length();
        this.temp = new int[this.patternLength + 2];
        this.hasSupplementary = false;
        int patternLength = 0;
        int codePoint;
        for (int i = 0; i < this.patternLength; i += Character.charCount(codePoint)) {
            codePoint = this.normalizedPattern.codePointAt(i);
            if (isSupplementary(codePoint)) {
                this.hasSupplementary = true;
            }
            this.temp[patternLength++] = codePoint;
        }
        this.patternLength = patternLength;
        if (!this.has(16)) {
            this.RemoveQEQuoting();
        }
        this.buffer = new int[32];
        this.groupNodes = new GroupHead[10];
        this.namedGroups = null;
        if (this.has(16)) {
            this.matchRoot = this.newSlice(this.temp, this.patternLength, this.hasSupplementary);
            this.matchRoot.next = Pattern.lastAccept;
        }
        else {
            this.matchRoot = this.expr(Pattern.lastAccept);
            if (this.patternLength != this.cursor) {
                if (this.peek() == 41) {
                    throw this.error("Unmatched closing ')'");
                }
                throw this.error("Unexpected internal error");
            }
        }
        if (this.matchRoot instanceof Slice) {
            this.root = BnM.optimize(this.matchRoot);
            if (this.root == this.matchRoot) {
                this.root = (this.hasSupplementary ? new StartS(this.matchRoot) : new Start(this.matchRoot));
            }
        }
        else if (this.matchRoot instanceof Begin || this.matchRoot instanceof First) {
            this.root = this.matchRoot;
        }
        else {
            this.root = (this.hasSupplementary ? new StartS(this.matchRoot) : new Start(this.matchRoot));
        }
        this.temp = null;
        this.buffer = null;
        this.groupNodes = null;
        this.patternLength = 0;
        this.compiled = true;
    }
    
    Map<String, Integer> namedGroups() {
        if (this.namedGroups == null) {
            this.namedGroups = new HashMap<String, Integer>(2);
        }
        return this.namedGroups;
    }
    
    private static void printObjectTree(Node next) {
        while (next != null) {
            if (next instanceof Prolog) {
                System.out.println(next);
                printObjectTree(((Prolog)next).loop);
                System.out.println("**** end contents prolog loop");
            }
            else if (next instanceof Loop) {
                System.out.println(next);
                printObjectTree(((Loop)next).body);
                System.out.println("**** end contents Loop body");
            }
            else if (next instanceof Curly) {
                System.out.println(next);
                printObjectTree(((Curly)next).atom);
                System.out.println("**** end contents Curly body");
            }
            else if (next instanceof GroupCurly) {
                System.out.println(next);
                printObjectTree(((GroupCurly)next).atom);
                System.out.println("**** end contents GroupCurly body");
            }
            else {
                if (next instanceof GroupTail) {
                    System.out.println(next);
                    System.out.println("Tail next is " + next.next);
                    return;
                }
                System.out.println(next);
            }
            next = next.next;
            if (next != null) {
                System.out.println("->next:");
            }
            if (next == Pattern.accept) {
                System.out.println("Accept Node");
                next = null;
            }
        }
    }
    
    private boolean has(final int n) {
        return (this.flags & n) != 0x0;
    }
    
    private void accept(final int n, final String s) {
        int pastWhitespace = this.temp[this.cursor++];
        if (this.has(4)) {
            pastWhitespace = this.parsePastWhitespace(pastWhitespace);
        }
        if (n != pastWhitespace) {
            throw this.error(s);
        }
    }
    
    private void mark(final int n) {
        this.temp[this.patternLength] = n;
    }
    
    private int peek() {
        int peekPastWhitespace = this.temp[this.cursor];
        if (this.has(4)) {
            peekPastWhitespace = this.peekPastWhitespace(peekPastWhitespace);
        }
        return peekPastWhitespace;
    }
    
    private int read() {
        int pastWhitespace = this.temp[this.cursor++];
        if (this.has(4)) {
            pastWhitespace = this.parsePastWhitespace(pastWhitespace);
        }
        return pastWhitespace;
    }
    
    private int readEscaped() {
        return this.temp[this.cursor++];
    }
    
    private int next() {
        int peekPastWhitespace = this.temp[++this.cursor];
        if (this.has(4)) {
            peekPastWhitespace = this.peekPastWhitespace(peekPastWhitespace);
        }
        return peekPastWhitespace;
    }
    
    private int nextEscaped() {
        return this.temp[++this.cursor];
    }
    
    private int peekPastWhitespace(int peekPastLine) {
        while (ASCII.isSpace(peekPastLine) || peekPastLine == 35) {
            while (ASCII.isSpace(peekPastLine)) {
                peekPastLine = this.temp[++this.cursor];
            }
            if (peekPastLine == 35) {
                peekPastLine = this.peekPastLine();
            }
        }
        return peekPastLine;
    }
    
    private int parsePastWhitespace(int pastLine) {
        while (ASCII.isSpace(pastLine) || pastLine == 35) {
            while (ASCII.isSpace(pastLine)) {
                pastLine = this.temp[this.cursor++];
            }
            if (pastLine == 35) {
                pastLine = this.parsePastLine();
            }
        }
        return pastLine;
    }
    
    private int parsePastLine() {
        int n;
        for (n = this.temp[this.cursor++]; n != 0 && !this.isLineSeparator(n); n = this.temp[this.cursor++]) {}
        if (n == 0 && this.cursor > this.patternLength) {
            this.cursor = this.patternLength;
            n = this.temp[this.cursor++];
        }
        return n;
    }
    
    private int peekPastLine() {
        int n;
        for (n = this.temp[++this.cursor]; n != 0 && !this.isLineSeparator(n); n = this.temp[++this.cursor]) {}
        if (n == 0 && this.cursor > this.patternLength) {
            this.cursor = this.patternLength;
            n = this.temp[this.cursor];
        }
        return n;
    }
    
    private boolean isLineSeparator(final int n) {
        if (this.has(1)) {
            return n == 10;
        }
        return n == 10 || n == 13 || (n | 0x1) == 0x2029 || n == 133;
    }
    
    private int skip() {
        final int cursor = this.cursor;
        final int n = this.temp[cursor + 1];
        this.cursor = cursor + 2;
        return n;
    }
    
    private void unread() {
        --this.cursor;
    }
    
    private PatternSyntaxException error(final String s) {
        return new PatternSyntaxException(s, this.normalizedPattern, this.cursor - 1);
    }
    
    private boolean findSupplementary(final int n, final int n2) {
        for (int i = n; i < n2; ++i) {
            if (isSupplementary(this.temp[i])) {
                return true;
            }
        }
        return false;
    }
    
    private static final boolean isSupplementary(final int n) {
        return n >= 65536 || Character.isSurrogate((char)n);
    }
    
    private Node expr(final Node next) {
        Node node = null;
        Node node2 = null;
        Branch branch = null;
        Node node3 = null;
        while (true) {
            Node sequence = this.sequence(next);
            final Node root = this.root;
            if (node == null) {
                node = sequence;
                node2 = root;
            }
            else {
                if (node3 == null) {
                    node3 = new BranchConn();
                    node3.next = next;
                }
                if (sequence == next) {
                    sequence = null;
                }
                else {
                    root.next = node3;
                }
                if (node == branch) {
                    branch.add(sequence);
                }
                else {
                    if (node == next) {
                        node = null;
                    }
                    else {
                        node2.next = node3;
                    }
                    branch = (Branch)(node = new Branch(node, sequence, node3));
                }
            }
            if (this.peek() != 124) {
                break;
            }
            this.next();
        }
        return node;
    }
    
    private Node sequence(final Node next) {
        Node node = null;
        Node root = null;
    Label_0535:
        while (true) {
            final int peek = this.peek();
            Node node2 = null;
            Label_0503: {
                switch (peek) {
                    case 40: {
                        final Node group0 = this.group0();
                        if (group0 == null) {
                            continue;
                        }
                        if (node == null) {
                            node = group0;
                        }
                        else {
                            root.next = group0;
                        }
                        root = this.root;
                        continue;
                    }
                    case 91: {
                        node2 = this.clazz(true);
                        break Label_0503;
                    }
                    case 92: {
                        final int nextEscaped = this.nextEscaped();
                        if (nextEscaped == 112 || nextEscaped == 80) {
                            boolean b = true;
                            final boolean b2 = nextEscaped == 80;
                            if (this.next() != 123) {
                                this.unread();
                            }
                            else {
                                b = false;
                            }
                            node2 = this.family(b, b2);
                            break Label_0503;
                        }
                        this.unread();
                        node2 = this.atom();
                        break Label_0503;
                    }
                    case 94: {
                        this.next();
                        if (!this.has(8)) {
                            node2 = new Begin();
                            break Label_0503;
                        }
                        if (this.has(1)) {
                            node2 = new UnixCaret();
                            break Label_0503;
                        }
                        node2 = new Caret();
                        break Label_0503;
                    }
                    case 36: {
                        this.next();
                        if (this.has(1)) {
                            node2 = new UnixDollar(this.has(8));
                            break Label_0503;
                        }
                        node2 = new Dollar(this.has(8));
                        break Label_0503;
                    }
                    case 46: {
                        this.next();
                        if (this.has(32)) {
                            node2 = new All();
                            break Label_0503;
                        }
                        if (this.has(1)) {
                            node2 = new UnixDot();
                            break Label_0503;
                        }
                        node2 = new Dot();
                        break Label_0503;
                    }
                    case 41:
                    case 124: {
                        break Label_0535;
                    }
                    case 93:
                    case 125: {
                        node2 = this.atom();
                        break Label_0503;
                    }
                    case 42:
                    case 43:
                    case 63: {
                        this.next();
                        throw this.error("Dangling meta character '" + (char)peek + "'");
                    }
                    case 0: {
                        if (this.cursor >= this.patternLength) {
                            break Label_0535;
                        }
                        break;
                    }
                }
                node2 = this.atom();
            }
            final Node closure = this.closure(node2);
            if (node == null) {
                root = (node = closure);
            }
            else {
                root.next = closure;
                root = closure;
            }
        }
        if (node == null) {
            return next;
        }
        root.next = next;
        this.root = root;
        return node;
    }
    
    private Node atom() {
        int n = 0;
        int n2 = -1;
        boolean b = false;
        int n3 = this.peek();
    Label_0352:
        while (true) {
            switch (n3) {
                case 42:
                case 43:
                case 63:
                case 123: {
                    if (n > 1) {
                        this.cursor = n2;
                        --n;
                        break Label_0352;
                    }
                    break Label_0352;
                }
                case 36:
                case 40:
                case 41:
                case 46:
                case 91:
                case 94:
                case 124: {
                    break Label_0352;
                }
                case 92: {
                    final int nextEscaped = this.nextEscaped();
                    if (nextEscaped == 112 || nextEscaped == 80) {
                        if (n > 0) {
                            this.unread();
                            break Label_0352;
                        }
                        final boolean b2 = nextEscaped == 80;
                        boolean b3 = true;
                        if (this.next() != 123) {
                            this.unread();
                        }
                        else {
                            b3 = false;
                        }
                        return this.family(b3, b2);
                    }
                    else {
                        this.unread();
                        n2 = this.cursor;
                        final int escape = this.escape(false, n == 0, false);
                        if (escape >= 0) {
                            this.append(escape, n);
                            ++n;
                            if (isSupplementary(escape)) {
                                b = true;
                            }
                            n3 = this.peek();
                            continue;
                        }
                        if (n == 0) {
                            return this.root;
                        }
                        this.cursor = n2;
                        break Label_0352;
                    }
                    break;
                }
                case 0: {
                    if (this.cursor >= this.patternLength) {
                        break Label_0352;
                    }
                    break;
                }
            }
            n2 = this.cursor;
            this.append(n3, n);
            ++n;
            if (isSupplementary(n3)) {
                b = true;
            }
            n3 = this.next();
        }
        if (n == 1) {
            return this.newSingle(this.buffer[0]);
        }
        return this.newSlice(this.buffer, n, b);
    }
    
    private void append(final int n, final int n2) {
        if (n2 >= this.buffer.length) {
            final int[] buffer = new int[n2 + n2];
            System.arraycopy(this.buffer, 0, buffer, 0, n2);
            this.buffer = buffer;
        }
        this.buffer[n2] = n;
    }
    
    private Node ref(int n) {
        int i = 0;
        while (i == 0) {
            final int peek = this.peek();
            switch (peek) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57: {
                    final int n2 = n * 10 + (peek - 48);
                    if (this.capturingGroupCount - 1 < n2) {
                        i = 1;
                        continue;
                    }
                    n = n2;
                    this.read();
                    continue;
                }
                default: {
                    i = 1;
                    continue;
                }
            }
        }
        if (this.has(2)) {
            return new CIBackRef(n, this.has(64));
        }
        return new BackRef(n);
    }
    
    private int escape(final boolean b, final boolean b2, final boolean b3) {
        final int skip = this.skip();
        switch (skip) {
            case 48: {
                return this.o();
            }
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57: {
                if (b) {
                    break;
                }
                if (b2) {
                    this.root = this.ref(skip - 48);
                }
                return -1;
            }
            case 65: {
                if (b) {
                    break;
                }
                if (b2) {
                    this.root = new Begin();
                }
                return -1;
            }
            case 66: {
                if (b) {
                    break;
                }
                if (b2) {
                    this.root = new Bound(Bound.NONE, this.has(256));
                }
                return -1;
            }
            case 67: {
                break;
            }
            case 68: {
                if (b2) {
                    this.root = (this.has(256) ? new Utype(UnicodeProp.DIGIT).complement() : new Ctype(1024).complement());
                }
                return -1;
            }
            case 69:
            case 70: {
                break;
            }
            case 71: {
                if (b) {
                    break;
                }
                if (b2) {
                    this.root = new LastMatch();
                }
                return -1;
            }
            case 72: {
                if (b2) {
                    this.root = new HorizWS().complement();
                }
                return -1;
            }
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81: {
                break;
            }
            case 82: {
                if (b) {
                    break;
                }
                if (b2) {
                    this.root = new LineEnding();
                }
                return -1;
            }
            case 83: {
                if (b2) {
                    this.root = (this.has(256) ? new Utype(UnicodeProp.WHITE_SPACE).complement() : new Ctype(2048).complement());
                }
                return -1;
            }
            case 84:
            case 85: {
                break;
            }
            case 86: {
                if (b2) {
                    this.root = new VertWS().complement();
                }
                return -1;
            }
            case 87: {
                if (b2) {
                    this.root = (this.has(256) ? new Utype(UnicodeProp.WORD).complement() : new Ctype(67328).complement());
                }
                return -1;
            }
            case 88:
            case 89: {
                break;
            }
            case 90: {
                if (b) {
                    break;
                }
                if (b2) {
                    if (this.has(1)) {
                        this.root = new UnixDollar(false);
                    }
                    else {
                        this.root = new Dollar(false);
                    }
                }
                return -1;
            }
            case 97: {
                return 7;
            }
            case 98: {
                if (b) {
                    break;
                }
                if (b2) {
                    this.root = new Bound(Bound.BOTH, this.has(256));
                }
                return -1;
            }
            case 99: {
                return this.c();
            }
            case 100: {
                if (b2) {
                    this.root = (this.has(256) ? new Utype(UnicodeProp.DIGIT) : new Ctype(1024));
                }
                return -1;
            }
            case 101: {
                return 27;
            }
            case 102: {
                return 12;
            }
            case 103: {
                break;
            }
            case 104: {
                if (b2) {
                    this.root = new HorizWS();
                }
                return -1;
            }
            case 105:
            case 106: {
                break;
            }
            case 107: {
                if (b) {
                    break;
                }
                if (this.read() != 60) {
                    throw this.error("\\k is not followed by '<' for named capturing group");
                }
                final String groupname = this.groupname(this.read());
                if (!this.namedGroups().containsKey(groupname)) {
                    throw this.error("(named capturing group <" + groupname + "> does not exit");
                }
                if (b2) {
                    if (this.has(2)) {
                        this.root = new CIBackRef(this.namedGroups().get(groupname), this.has(64));
                    }
                    else {
                        this.root = new BackRef(this.namedGroups().get(groupname));
                    }
                }
                return -1;
            }
            case 108:
            case 109: {
                break;
            }
            case 110: {
                return 10;
            }
            case 111:
            case 112:
            case 113: {
                break;
            }
            case 114: {
                return 13;
            }
            case 115: {
                if (b2) {
                    this.root = (this.has(256) ? new Utype(UnicodeProp.WHITE_SPACE) : new Ctype(2048));
                }
                return -1;
            }
            case 116: {
                return 9;
            }
            case 117: {
                return this.u();
            }
            case 118: {
                if (b3) {
                    return 11;
                }
                if (b2) {
                    this.root = new VertWS();
                }
                return -1;
            }
            case 119: {
                if (b2) {
                    this.root = (this.has(256) ? new Utype(UnicodeProp.WORD) : new Ctype(67328));
                }
                return -1;
            }
            case 120: {
                return this.x();
            }
            case 121: {
                break;
            }
            case 122: {
                if (b) {
                    break;
                }
                if (b2) {
                    this.root = new End();
                }
                return -1;
            }
            default: {
                return skip;
            }
        }
        throw this.error("Illegal/unsupported escape sequence");
    }
    
    private CharProperty clazz(final boolean b) {
        CharProperty charProperty = null;
        CharProperty charProperty2 = null;
        final BitClass bitClass = new BitClass();
        boolean b2 = true;
        int n = 1;
        int n2 = this.next();
        while (true) {
            switch (n2) {
                case 94: {
                    if (n == 0) {
                        break;
                    }
                    if (this.temp[this.cursor - 1] != 91) {
                        break;
                    }
                    n2 = this.next();
                    b2 = !b2;
                    continue;
                }
                case 91: {
                    n = 0;
                    charProperty2 = this.clazz(true);
                    if (charProperty == null) {
                        charProperty = charProperty2;
                    }
                    else {
                        charProperty = union(charProperty, charProperty2);
                    }
                    n2 = this.peek();
                    continue;
                }
                case 38: {
                    n = 0;
                    if (this.next() == 38) {
                        n2 = this.next();
                        CharProperty charProperty3 = null;
                        while (n2 != 93 && n2 != 38) {
                            if (n2 == 91) {
                                if (charProperty3 == null) {
                                    charProperty3 = this.clazz(true);
                                }
                                else {
                                    charProperty3 = union(charProperty3, this.clazz(true));
                                }
                            }
                            else {
                                this.unread();
                                charProperty3 = this.clazz(false);
                            }
                            n2 = this.peek();
                        }
                        if (charProperty3 != null) {
                            charProperty2 = charProperty3;
                        }
                        if (charProperty == null) {
                            if (charProperty3 == null) {
                                throw this.error("Bad class syntax");
                            }
                            charProperty = charProperty3;
                        }
                        else {
                            charProperty = intersection(charProperty, charProperty2);
                        }
                        continue;
                    }
                    this.unread();
                    break;
                }
                case 0: {
                    n = 0;
                    if (this.cursor >= this.patternLength) {
                        throw this.error("Unclosed character class");
                    }
                    break;
                }
                case 93: {
                    n = 0;
                    if (charProperty != null) {
                        if (b) {
                            this.next();
                        }
                        return charProperty;
                    }
                    break;
                }
                default: {
                    n = 0;
                    break;
                }
            }
            charProperty2 = this.range(bitClass);
            if (b2) {
                if (charProperty == null) {
                    charProperty = charProperty2;
                }
                else if (charProperty != charProperty2) {
                    charProperty = union(charProperty, charProperty2);
                }
            }
            else if (charProperty == null) {
                charProperty = charProperty2.complement();
            }
            else if (charProperty != charProperty2) {
                charProperty = setDifference(charProperty, charProperty2);
            }
            n2 = this.peek();
        }
    }
    
    private CharProperty bitsOrSingle(final BitClass bitClass, final int n) {
        if (n < 256 && (!this.has(2) || !this.has(64) || (n != 255 && n != 181 && n != 73 && n != 105 && n != 83 && n != 115 && n != 75 && n != 107 && n != 197 && n != 229))) {
            return bitClass.add(n, this.flags());
        }
        return this.newSingle(n);
    }
    
    private CharProperty range(final BitClass bitClass) {
        int n = this.peek();
        if (n == 92) {
            final int nextEscaped = this.nextEscaped();
            if (nextEscaped == 112 || nextEscaped == 80) {
                final boolean b = nextEscaped == 80;
                boolean b2 = true;
                if (this.next() != 123) {
                    this.unread();
                }
                else {
                    b2 = false;
                }
                return this.family(b2, b);
            }
            final boolean b3 = this.temp[this.cursor + 1] == 45;
            this.unread();
            n = this.escape(true, true, b3);
            if (n == -1) {
                return (CharProperty)this.root;
            }
        }
        else {
            this.next();
        }
        if (n >= 0) {
            if (this.peek() == 45) {
                final int n2 = this.temp[this.cursor + 1];
                if (n2 == 91) {
                    return this.bitsOrSingle(bitClass, n);
                }
                if (n2 != 93) {
                    this.next();
                    int n3 = this.peek();
                    if (n3 == 92) {
                        n3 = this.escape(true, false, true);
                    }
                    else {
                        this.next();
                    }
                    if (n3 < n) {
                        throw this.error("Illegal character range");
                    }
                    if (this.has(2)) {
                        return this.caseInsensitiveRangeFor(n, n3);
                    }
                    return rangeFor(n, n3);
                }
            }
            return this.bitsOrSingle(bitClass, n);
        }
        throw this.error("Unexpected character '" + (char)n + "'");
    }
    
    private CharProperty family(final boolean b, final boolean b2) {
        this.next();
        CharProperty charProperty = null;
        String value;
        if (b) {
            final int n = this.temp[this.cursor];
            if (!Character.isSupplementaryCodePoint(n)) {
                value = String.valueOf((char)n);
            }
            else {
                value = new String(this.temp, this.cursor, 1);
            }
            this.read();
        }
        else {
            final int cursor = this.cursor;
            this.mark(125);
            while (this.read() != 125) {}
            this.mark(0);
            final int cursor2 = this.cursor;
            if (cursor2 > this.patternLength) {
                throw this.error("Unclosed character family");
            }
            if (cursor + 1 >= cursor2) {
                throw this.error("Empty character family");
            }
            value = new String(this.temp, cursor, cursor2 - cursor - 1);
        }
        final int index = value.indexOf(61);
        if (index != -1) {
            final String substring = value.substring(index + 1);
            final String lowerCase = value.substring(0, index).toLowerCase(Locale.ENGLISH);
            if ("sc".equals(lowerCase) || "script".equals(lowerCase)) {
                charProperty = this.unicodeScriptPropertyFor(substring);
            }
            else if ("blk".equals(lowerCase) || "block".equals(lowerCase)) {
                charProperty = this.unicodeBlockPropertyFor(substring);
            }
            else {
                if (!"gc".equals(lowerCase) && !"general_category".equals(lowerCase)) {
                    throw this.error("Unknown Unicode property {name=<" + lowerCase + ">, value=<" + substring + ">}");
                }
                charProperty = this.charPropertyNodeFor(substring);
            }
        }
        else if (value.startsWith("In")) {
            charProperty = this.unicodeBlockPropertyFor(value.substring(2));
        }
        else if (value.startsWith("Is")) {
            final String substring2 = value.substring(2);
            final UnicodeProp forName = UnicodeProp.forName(substring2);
            if (forName != null) {
                charProperty = new Utype(forName);
            }
            if (charProperty == null) {
                charProperty = CharPropertyNames.charPropertyFor(substring2);
            }
            if (charProperty == null) {
                charProperty = this.unicodeScriptPropertyFor(substring2);
            }
        }
        else {
            if (this.has(256)) {
                final UnicodeProp forPOSIXName = UnicodeProp.forPOSIXName(value);
                if (forPOSIXName != null) {
                    charProperty = new Utype(forPOSIXName);
                }
            }
            if (charProperty == null) {
                charProperty = this.charPropertyNodeFor(value);
            }
        }
        if (b2) {
            if (charProperty instanceof Category || charProperty instanceof Block) {
                this.hasSupplementary = true;
            }
            charProperty = charProperty.complement();
        }
        return charProperty;
    }
    
    private CharProperty unicodeScriptPropertyFor(final String s) {
        Character.UnicodeScript forName;
        try {
            forName = Character.UnicodeScript.forName(s);
        }
        catch (IllegalArgumentException ex) {
            throw this.error("Unknown character script name {" + s + "}");
        }
        return new Script(forName);
    }
    
    private CharProperty unicodeBlockPropertyFor(final String s) {
        Character.UnicodeBlock forName;
        try {
            forName = Character.UnicodeBlock.forName(s);
        }
        catch (IllegalArgumentException ex) {
            throw this.error("Unknown character block name {" + s + "}");
        }
        return new Block(forName);
    }
    
    private CharProperty charPropertyNodeFor(final String s) {
        final CharProperty charProperty = CharPropertyNames.charPropertyFor(s);
        if (charProperty == null) {
            throw this.error("Unknown character property name {" + s + "}");
        }
        return charProperty;
    }
    
    private String groupname(int read) {
        final StringBuilder sb = new StringBuilder();
        sb.append(Character.toChars(read));
        while (ASCII.isLower(read = this.read()) || ASCII.isUpper(read) || ASCII.isDigit(read)) {
            sb.append(Character.toChars(read));
        }
        if (sb.length() == 0) {
            throw this.error("named capturing group has 0 length name");
        }
        if (read != 62) {
            throw this.error("named capturing group is missing trailing '>'");
        }
        return sb.toString();
    }
    
    private Node group0() {
        boolean b = false;
        final int flags = this.flags;
        this.root = null;
        Node body = null;
        Node root = null;
        if (this.next() == 63) {
            final int skip = this.skip();
            switch (skip) {
                case 58: {
                    body = this.createGroup(true);
                    root = this.root;
                    body.next = this.expr(root);
                    break;
                }
                case 33:
                case 61: {
                    final Node group = this.createGroup(true);
                    group.next = this.expr(this.root);
                    if (skip == 61) {
                        root = (body = new Pos(group));
                        break;
                    }
                    root = (body = new Neg(group));
                    break;
                }
                case 62: {
                    final Node group2 = this.createGroup(true);
                    group2.next = this.expr(this.root);
                    root = (body = new Ques(group2, 3));
                    break;
                }
                case 60: {
                    final int read = this.read();
                    if (ASCII.isLower(read) || ASCII.isUpper(read)) {
                        final String groupname = this.groupname(read);
                        if (this.namedGroups().containsKey(groupname)) {
                            throw this.error("Named capturing group <" + groupname + "> is already defined");
                        }
                        b = true;
                        body = this.createGroup(false);
                        root = this.root;
                        this.namedGroups().put(groupname, this.capturingGroupCount - 1);
                        body.next = this.expr(root);
                        break;
                    }
                    else {
                        final int cursor = this.cursor;
                        final Node group3 = this.createGroup(true);
                        final Node root2 = this.root;
                        group3.next = this.expr(root2);
                        root2.next = Pattern.lookbehindEnd;
                        final TreeInfo treeInfo = new TreeInfo();
                        group3.study(treeInfo);
                        if (!treeInfo.maxValid) {
                            throw this.error("Look-behind group does not have an obvious maximum length");
                        }
                        final boolean supplementary = this.findSupplementary(cursor, this.patternLength);
                        if (read == 61) {
                            root = (body = (supplementary ? new BehindS(group3, treeInfo.maxLength, treeInfo.minLength) : new Behind(group3, treeInfo.maxLength, treeInfo.minLength)));
                            break;
                        }
                        if (read == 33) {
                            root = (body = (supplementary ? new NotBehindS(group3, treeInfo.maxLength, treeInfo.minLength) : new NotBehind(group3, treeInfo.maxLength, treeInfo.minLength)));
                            break;
                        }
                        throw this.error("Unknown look-behind group");
                    }
                    break;
                }
                case 36:
                case 64: {
                    throw this.error("Unknown group type");
                }
                default: {
                    this.unread();
                    this.addFlag();
                    final int read2 = this.read();
                    if (read2 == 41) {
                        return null;
                    }
                    if (read2 != 58) {
                        throw this.error("Unknown inline modifier");
                    }
                    body = this.createGroup(true);
                    root = this.root;
                    body.next = this.expr(root);
                    break;
                }
            }
        }
        else {
            b = true;
            body = this.createGroup(false);
            root = this.root;
            body.next = this.expr(root);
        }
        this.accept(41, "Unclosed group");
        this.flags = flags;
        final Node closure = this.closure(body);
        if (closure == body) {
            this.root = root;
            return closure;
        }
        if (body == root) {
            return this.root = closure;
        }
        if (closure instanceof Ques) {
            final Ques ques = (Ques)closure;
            if (ques.type == 2) {
                return this.root = closure;
            }
            root.next = new BranchConn();
            final Node next = root.next;
            Branch branch;
            if (ques.type == 0) {
                branch = new Branch(body, null, next);
            }
            else {
                branch = new Branch(null, body, next);
            }
            this.root = next;
            return branch;
        }
        else {
            if (!(closure instanceof Curly)) {
                throw this.error("Internal logic error");
            }
            final Curly curly = (Curly)closure;
            if (curly.type == 2) {
                return this.root = closure;
            }
            if (body.study(new TreeInfo())) {
                final GroupTail groupTail = (GroupTail)root;
                return this.root = new GroupCurly(body.next, curly.cmin, curly.cmax, curly.type, ((GroupTail)root).localIndex, ((GroupTail)root).groupIndex, b);
            }
            final int localIndex = ((GroupHead)body).localIndex;
            Loop loop;
            if (curly.type == 0) {
                loop = new Loop(this.localCount, localIndex);
            }
            else {
                loop = new LazyLoop(this.localCount, localIndex);
            }
            final Prolog prolog = new Prolog(loop);
            ++this.localCount;
            loop.cmin = curly.cmin;
            loop.cmax = curly.cmax;
            loop.body = body;
            root.next = loop;
            this.root = loop;
            return prolog;
        }
    }
    
    private Node createGroup(final boolean b) {
        final int n = this.localCount++;
        int n2 = 0;
        if (!b) {
            n2 = this.capturingGroupCount++;
        }
        final GroupHead groupHead = new GroupHead(n);
        this.root = new GroupTail(n, n2);
        if (!b && n2 < 10) {
            this.groupNodes[n2] = groupHead;
        }
        return groupHead;
    }
    
    private void addFlag() {
        int n = this.peek();
    Label_0208:
        while (true) {
            switch (n) {
                case 105: {
                    this.flags |= 0x2;
                    break;
                }
                case 109: {
                    this.flags |= 0x8;
                    break;
                }
                case 115: {
                    this.flags |= 0x20;
                    break;
                }
                case 100: {
                    this.flags |= 0x1;
                    break;
                }
                case 117: {
                    this.flags |= 0x40;
                    break;
                }
                case 99: {
                    this.flags |= 0x80;
                    break;
                }
                case 120: {
                    this.flags |= 0x4;
                    break;
                }
                case 85: {
                    this.flags |= 0x140;
                    break;
                }
                case 45: {
                    this.next();
                    this.subFlag();
                }
                default: {
                    break Label_0208;
                }
            }
            n = this.next();
        }
    }
    
    private void subFlag() {
        int n = this.peek();
    Label_0191:
        while (true) {
            switch (n) {
                case 105: {
                    this.flags &= 0xFFFFFFFD;
                    break;
                }
                case 109: {
                    this.flags &= 0xFFFFFFF7;
                    break;
                }
                case 115: {
                    this.flags &= 0xFFFFFFDF;
                    break;
                }
                case 100: {
                    this.flags &= 0xFFFFFFFE;
                    break;
                }
                case 117: {
                    this.flags &= 0xFFFFFFBF;
                    break;
                }
                case 99: {
                    this.flags &= 0xFFFFFF7F;
                    break;
                }
                case 120: {
                    this.flags &= 0xFFFFFFFB;
                    break;
                }
                case 85: {
                    this.flags &= 0xFFFFFEBF;
                }
                default: {
                    break Label_0191;
                }
            }
            n = this.next();
        }
    }
    
    private Node closure(final Node node) {
        switch (this.peek()) {
            case 63: {
                final int next = this.next();
                if (next == 63) {
                    this.next();
                    return new Ques(node, 1);
                }
                if (next == 43) {
                    this.next();
                    return new Ques(node, 2);
                }
                return new Ques(node, 0);
            }
            case 42: {
                final int next2 = this.next();
                if (next2 == 63) {
                    this.next();
                    return new Curly(node, 0, Integer.MAX_VALUE, 1);
                }
                if (next2 == 43) {
                    this.next();
                    return new Curly(node, 0, Integer.MAX_VALUE, 2);
                }
                return new Curly(node, 0, Integer.MAX_VALUE, 0);
            }
            case 43: {
                final int next3 = this.next();
                if (next3 == 63) {
                    this.next();
                    return new Curly(node, 1, Integer.MAX_VALUE, 1);
                }
                if (next3 == 43) {
                    this.next();
                    return new Curly(node, 1, Integer.MAX_VALUE, 2);
                }
                return new Curly(node, 1, Integer.MAX_VALUE, 0);
            }
            case 123: {
                int n = this.temp[this.cursor + 1];
                if (!ASCII.isDigit(n)) {
                    throw this.error("Illegal repetition");
                }
                this.skip();
                int n2 = 0;
                do {
                    n2 = n2 * 10 + (n - 48);
                } while (ASCII.isDigit(n = this.read()));
                int n3 = n2;
                if (n == 44) {
                    n = this.read();
                    n3 = Integer.MAX_VALUE;
                    if (n != 125) {
                        n3 = 0;
                        while (ASCII.isDigit(n)) {
                            n3 = n3 * 10 + (n - 48);
                            n = this.read();
                        }
                    }
                }
                if (n != 125) {
                    throw this.error("Unclosed counted closure");
                }
                if ((n2 | n3 | n3 - n2) < 0) {
                    throw this.error("Illegal repetition range");
                }
                final int peek = this.peek();
                Curly curly;
                if (peek == 63) {
                    this.next();
                    curly = new Curly(node, n2, n3, 1);
                }
                else if (peek == 43) {
                    this.next();
                    curly = new Curly(node, n2, n3, 2);
                }
                else {
                    curly = new Curly(node, n2, n3, 0);
                }
                return curly;
            }
            default: {
                return node;
            }
        }
    }
    
    private int c() {
        if (this.cursor < this.patternLength) {
            return this.read() ^ 0x40;
        }
        throw this.error("Illegal control escape sequence");
    }
    
    private int o() {
        final int read = this.read();
        if ((read - 48 | 55 - read) < 0) {
            throw this.error("Illegal octal escape sequence");
        }
        final int read2 = this.read();
        if ((read2 - 48 | 55 - read2) < 0) {
            this.unread();
            return read - 48;
        }
        final int read3 = this.read();
        if ((read3 - 48 | 55 - read3) >= 0 && (read - 48 | 51 - read) >= 0) {
            return (read - 48) * 64 + (read2 - 48) * 8 + (read3 - 48);
        }
        this.unread();
        return (read - 48) * 8 + (read2 - 48);
    }
    
    private int x() {
        final int read = this.read();
        if (ASCII.isHexDigit(read)) {
            final int read2 = this.read();
            if (ASCII.isHexDigit(read2)) {
                return ASCII.toDigit(read) * 16 + ASCII.toDigit(read2);
            }
        }
        else if (read == 123 && ASCII.isHexDigit(this.peek())) {
            int n = 0;
            int read3;
            while (ASCII.isHexDigit(read3 = this.read())) {
                n = (n << 4) + ASCII.toDigit(read3);
                if (n > 1114111) {
                    throw this.error("Hexadecimal codepoint is too big");
                }
            }
            if (read3 != 125) {
                throw this.error("Unclosed hexadecimal escape sequence");
            }
            return n;
        }
        throw this.error("Illegal hexadecimal escape sequence");
    }
    
    private int cursor() {
        return this.cursor;
    }
    
    private void setcursor(final int cursor) {
        this.cursor = cursor;
    }
    
    private int uxxxx() {
        int n = 0;
        for (int i = 0; i < 4; ++i) {
            final int read = this.read();
            if (!ASCII.isHexDigit(read)) {
                throw this.error("Illegal Unicode escape sequence");
            }
            n = n * 16 + ASCII.toDigit(read);
        }
        return n;
    }
    
    private int u() {
        final int uxxxx = this.uxxxx();
        if (Character.isHighSurrogate((char)uxxxx)) {
            final int cursor = this.cursor();
            if (this.read() == 92 && this.read() == 117) {
                final int uxxxx2 = this.uxxxx();
                if (Character.isLowSurrogate((char)uxxxx2)) {
                    return Character.toCodePoint((char)uxxxx, (char)uxxxx2);
                }
            }
            this.setcursor(cursor);
        }
        return uxxxx;
    }
    
    private static final int countChars(final CharSequence charSequence, final int n, final int n2) {
        if (n2 == 1 && !Character.isHighSurrogate(charSequence.charAt(n))) {
            assert n >= 0 && n < charSequence.length();
            return 1;
        }
        else {
            final int length = charSequence.length();
            int n3 = n;
            if (n2 >= 0) {
                assert n >= 0 && n < length;
                for (int n4 = 0; n3 < length && n4 < n2; ++n4) {
                    if (Character.isHighSurrogate(charSequence.charAt(n3++)) && n3 < length && Character.isLowSurrogate(charSequence.charAt(n3))) {
                        ++n3;
                    }
                }
                return n3 - n;
            }
            else {
                assert n >= 0 && n <= length;
                if (n == 0) {
                    return 0;
                }
                for (int n5 = -n2, n6 = 0; n3 > 0 && n6 < n5; ++n6) {
                    if (Character.isLowSurrogate(charSequence.charAt(--n3)) && n3 > 0 && Character.isHighSurrogate(charSequence.charAt(n3 - 1))) {
                        --n3;
                    }
                }
                return n - n3;
            }
        }
    }
    
    private static final int countCodePoints(final CharSequence charSequence) {
        final int length = charSequence.length();
        int n = 0;
        for (int i = 0; i < length; ++i) {
            ++n;
            if (Character.isHighSurrogate(charSequence.charAt(i++)) && i < length && Character.isLowSurrogate(charSequence.charAt(i))) {}
        }
        return n;
    }
    
    private CharProperty newSingle(final int n) {
        if (this.has(2)) {
            if (this.has(64)) {
                final int upperCase = Character.toUpperCase(n);
                final int lowerCase = Character.toLowerCase(upperCase);
                if (upperCase != lowerCase) {
                    return new SingleU(lowerCase);
                }
            }
            else if (ASCII.isAscii(n)) {
                final int lower = ASCII.toLower(n);
                final int upper = ASCII.toUpper(n);
                if (lower != upper) {
                    return new SingleI(lower, upper);
                }
            }
        }
        if (isSupplementary(n)) {
            return new SingleS(n);
        }
        return new Single(n);
    }
    
    private Node newSlice(final int[] array, final int n, final boolean b) {
        final int[] array2 = new int[n];
        if (!this.has(2)) {
            for (int i = 0; i < n; ++i) {
                array2[i] = array[i];
            }
            return b ? new SliceS(array2) : new Slice(array2);
        }
        if (this.has(64)) {
            for (int j = 0; j < n; ++j) {
                array2[j] = Character.toLowerCase(Character.toUpperCase(array[j]));
            }
            return b ? new SliceUS(array2) : new SliceU(array2);
        }
        for (int k = 0; k < n; ++k) {
            array2[k] = ASCII.toLower(array[k]);
        }
        return b ? new SliceIS(array2) : new SliceI(array2);
    }
    
    private static boolean inRange(final int n, final int n2, final int n3) {
        return n <= n2 && n2 <= n3;
    }
    
    private static CharProperty rangeFor(final int n, final int n2) {
        return new CharProperty() {
            @Override
            boolean isSatisfiedBy(final int n) {
                return inRange(n, n, n2);
            }
        };
    }
    
    private CharProperty caseInsensitiveRangeFor(final int n, final int n2) {
        if (this.has(64)) {
            return new CharProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    if (inRange(n, n, n2)) {
                        return true;
                    }
                    final int upperCase = Character.toUpperCase(n);
                    return inRange(n, upperCase, n2) || inRange(n, Character.toLowerCase(upperCase), n2);
                }
            };
        }
        return new CharProperty() {
            @Override
            boolean isSatisfiedBy(final int n) {
                return inRange(n, n, n2) || (ASCII.isAscii(n) && (inRange(n, ASCII.toUpper(n), n2) || inRange(n, ASCII.toLower(n), n2)));
            }
        };
    }
    
    private static CharProperty union(final CharProperty charProperty, final CharProperty charProperty2) {
        return new CharProperty() {
            @Override
            boolean isSatisfiedBy(final int n) {
                return charProperty.isSatisfiedBy(n) || charProperty2.isSatisfiedBy(n);
            }
        };
    }
    
    private static CharProperty intersection(final CharProperty charProperty, final CharProperty charProperty2) {
        return new CharProperty() {
            @Override
            boolean isSatisfiedBy(final int n) {
                return charProperty.isSatisfiedBy(n) && charProperty2.isSatisfiedBy(n);
            }
        };
    }
    
    private static CharProperty setDifference(final CharProperty charProperty, final CharProperty charProperty2) {
        return new CharProperty() {
            @Override
            boolean isSatisfiedBy(final int n) {
                return !charProperty2.isSatisfiedBy(n) && charProperty.isSatisfiedBy(n);
            }
        };
    }
    
    private static boolean hasBaseCharacter(final Matcher matcher, final int n, final CharSequence charSequence) {
        for (int n2 = matcher.transparentBounds ? 0 : matcher.from, i = n; i >= n2; --i) {
            final int codePoint = Character.codePointAt(charSequence, i);
            if (Character.isLetterOrDigit(codePoint)) {
                return true;
            }
            if (Character.getType(codePoint) != 6) {
                return false;
            }
        }
        return false;
    }
    
    public Predicate<String> asPredicate() {
        return s -> this.matcher(s).find();
    }
    
    public Stream<String> splitAsStream(final CharSequence charSequence) {
        class MatcherIterator implements Iterator<String>
        {
            private final Matcher matcher;
            private int current;
            private String nextElement;
            private int emptyElementCount;
            
            MatcherIterator() {
                this.matcher = Pattern.this.matcher(charSequence);
            }
            
            @Override
            public String next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                if (this.emptyElementCount == 0) {
                    final String nextElement = this.nextElement;
                    this.nextElement = null;
                    return nextElement;
                }
                --this.emptyElementCount;
                return "";
            }
            
            @Override
            public boolean hasNext() {
                if (this.nextElement != null || this.emptyElementCount > 0) {
                    return true;
                }
                if (this.current == charSequence.length()) {
                    return false;
                }
                while (this.matcher.find()) {
                    this.nextElement = charSequence.subSequence(this.current, this.matcher.start()).toString();
                    this.current = this.matcher.end();
                    if (!this.nextElement.isEmpty()) {
                        return true;
                    }
                    if (this.current <= 0) {
                        continue;
                    }
                    ++this.emptyElementCount;
                }
                this.nextElement = charSequence.subSequence(this.current, charSequence.length()).toString();
                this.current = charSequence.length();
                if (!this.nextElement.isEmpty()) {
                    return true;
                }
                this.emptyElementCount = 0;
                this.nextElement = null;
                return false;
            }
        }
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<? extends String>)new MatcherIterator(), 272), false);
    }
    
    static {
        Pattern.lookbehindEnd = new Node() {
            @Override
            boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
                return n == matcher.lookbehindTo;
            }
        };
        Pattern.accept = new Node();
        Pattern.lastAccept = new LastNode();
    }
    
    static final class All extends CharProperty
    {
        @Override
        boolean isSatisfiedBy(final int n) {
            return true;
        }
    }
    
    private abstract static class CharProperty extends Node
    {
        abstract boolean isSatisfiedBy(final int p0);
        
        CharProperty complement() {
            return new CharProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return !CharProperty.this.isSatisfiedBy(n);
                }
            };
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            if (n < matcher.to) {
                final int codePoint = Character.codePointAt(charSequence, n);
                return this.isSatisfiedBy(codePoint) && this.next.match(matcher, n + Character.charCount(codePoint), charSequence);
            }
            matcher.hitEnd = true;
            return false;
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            ++treeInfo.minLength;
            ++treeInfo.maxLength;
            return this.next.study(treeInfo);
        }
    }
    
    static class Node
    {
        Node next;
        
        Node() {
            this.next = Pattern.accept;
        }
        
        boolean match(final Matcher matcher, final int last, final CharSequence charSequence) {
            matcher.last = last;
            matcher.groups[0] = matcher.first;
            matcher.groups[1] = matcher.last;
            return true;
        }
        
        boolean study(final TreeInfo treeInfo) {
            if (this.next != null) {
                return this.next.study(treeInfo);
            }
            return treeInfo.deterministic;
        }
    }
    
    static final class TreeInfo
    {
        int minLength;
        int maxLength;
        boolean maxValid;
        boolean deterministic;
        
        TreeInfo() {
            this.reset();
        }
        
        void reset() {
            this.minLength = 0;
            this.maxLength = 0;
            this.maxValid = true;
            this.deterministic = true;
        }
    }
    
    static class BackRef extends Node
    {
        int groupIndex;
        
        BackRef(final int n) {
            this.groupIndex = n + n;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int n2 = matcher.groups[this.groupIndex];
            final int n3 = matcher.groups[this.groupIndex + 1] - n2;
            if (n2 < 0) {
                return false;
            }
            if (n + n3 > matcher.to) {
                matcher.hitEnd = true;
                return false;
            }
            for (int i = 0; i < n3; ++i) {
                if (charSequence.charAt(n + i) != charSequence.charAt(n2 + i)) {
                    return false;
                }
            }
            return this.next.match(matcher, n + n3, charSequence);
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            treeInfo.maxValid = false;
            return this.next.study(treeInfo);
        }
    }
    
    static final class Begin extends Node
    {
        @Override
        boolean match(final Matcher matcher, final int first, final CharSequence charSequence) {
            if (first == (matcher.anchoringBounds ? matcher.from : 0) && this.next.match(matcher, first, charSequence)) {
                matcher.first = first;
                matcher.groups[0] = first;
                matcher.groups[1] = matcher.last;
                return true;
            }
            return false;
        }
    }
    
    static class Behind extends Node
    {
        Node cond;
        int rmax;
        int rmin;
        
        Behind(final Node cond, final int rmax, final int rmin) {
            this.cond = cond;
            this.rmax = rmax;
            this.rmin = rmin;
        }
        
        @Override
        boolean match(final Matcher matcher, final int lookbehindTo, final CharSequence charSequence) {
            final int from = matcher.from;
            boolean match = false;
            final int max = Math.max(lookbehindTo - this.rmax, matcher.transparentBounds ? 0 : matcher.from);
            final int lookbehindTo2 = matcher.lookbehindTo;
            matcher.lookbehindTo = lookbehindTo;
            if (matcher.transparentBounds) {
                matcher.from = 0;
            }
            for (int n = lookbehindTo - this.rmin; !match && n >= max; match = this.cond.match(matcher, n, charSequence), --n) {}
            matcher.from = from;
            matcher.lookbehindTo = lookbehindTo2;
            return match && this.next.match(matcher, lookbehindTo, charSequence);
        }
    }
    
    static final class BehindS extends Behind
    {
        BehindS(final Node node, final int n, final int n2) {
            super(node, n, n2);
        }
        
        @Override
        boolean match(final Matcher matcher, final int lookbehindTo, final CharSequence charSequence) {
            final int access$300 = countChars(charSequence, lookbehindTo, -this.rmax);
            final int access$301 = countChars(charSequence, lookbehindTo, -this.rmin);
            final int from = matcher.from;
            final int n = matcher.transparentBounds ? 0 : matcher.from;
            boolean match = false;
            final int max = Math.max(lookbehindTo - access$300, n);
            final int lookbehindTo2 = matcher.lookbehindTo;
            matcher.lookbehindTo = lookbehindTo;
            if (matcher.transparentBounds) {
                matcher.from = 0;
            }
            for (int n2 = lookbehindTo - access$301; !match && n2 >= max; match = this.cond.match(matcher, n2, charSequence), n2 -= ((n2 > max) ? countChars(charSequence, n2, -1) : 1)) {}
            matcher.from = from;
            matcher.lookbehindTo = lookbehindTo2;
            return match && this.next.match(matcher, lookbehindTo, charSequence);
        }
    }
    
    private static final class BitClass extends BmpCharProperty
    {
        final boolean[] bits;
        
        BitClass() {
            this.bits = new boolean[256];
        }
        
        private BitClass(final boolean[] bits) {
            this.bits = bits;
        }
        
        BitClass add(final int n, final int n2) {
            assert n >= 0 && n <= 255;
            if ((n2 & 0x2) != 0x0) {
                if (ASCII.isAscii(n)) {
                    this.bits[ASCII.toUpper(n)] = true;
                    this.bits[ASCII.toLower(n)] = true;
                }
                else if ((n2 & 0x40) != 0x0) {
                    this.bits[Character.toLowerCase(n)] = true;
                    this.bits[Character.toUpperCase(n)] = true;
                }
            }
            this.bits[n] = true;
            return this;
        }
        
        @Override
        boolean isSatisfiedBy(final int n) {
            return n < 256 && this.bits[n];
        }
    }
    
    private abstract static class BmpCharProperty extends CharProperty
    {
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            if (n < matcher.to) {
                return this.isSatisfiedBy(charSequence.charAt(n)) && this.next.match(matcher, n + 1, charSequence);
            }
            matcher.hitEnd = true;
            return false;
        }
    }
    
    static final class Block extends CharProperty
    {
        final Character.UnicodeBlock block;
        
        Block(final Character.UnicodeBlock block) {
            this.block = block;
        }
        
        @Override
        boolean isSatisfiedBy(final int n) {
            return this.block == Character.UnicodeBlock.of(n);
        }
    }
    
    static class BnM extends Node
    {
        int[] buffer;
        int[] lastOcc;
        int[] optoSft;
        
        static Node optimize(final Node node) {
            if (!(node instanceof Slice)) {
                return node;
            }
            final int[] buffer = ((Slice)node).buffer;
            final int length = buffer.length;
            if (length < 4) {
                return node;
            }
            final int[] array = new int[128];
            final int[] array2 = new int[length];
            for (int i = 0; i < length; ++i) {
                array[buffer[i] & 0x7F] = i + 1;
            }
            int j = length;
        Label_0066:
            while (j > 0) {
                while (true) {
                    int k;
                    for (k = length - 1; k >= j; --k) {
                        if (buffer[k] != buffer[k - j]) {
                            --j;
                            continue Label_0066;
                        }
                        array2[k - 1] = j;
                    }
                    while (k > 0) {
                        array2[--k] = j;
                    }
                    continue;
                }
            }
            array2[length - 1] = 1;
            if (node instanceof SliceS) {
                return new BnMS(buffer, array, array2, node.next);
            }
            return new BnM(buffer, array, array2, node.next);
        }
        
        BnM(final int[] buffer, final int[] lastOcc, final int[] optoSft, final Node next) {
            this.buffer = buffer;
            this.lastOcc = lastOcc;
            this.optoSft = optoSft;
            this.next = next;
        }
        
        @Override
        boolean match(final Matcher matcher, int i, final CharSequence charSequence) {
            final int[] buffer = this.buffer;
            final int length = buffer.length;
            final int n = matcher.to - length;
        Label_0020:
            while (i <= n) {
                for (int j = length - 1; j >= 0; --j) {
                    final char char1 = charSequence.charAt(i + j);
                    if (char1 != buffer[j]) {
                        i += Math.max(j + 1 - this.lastOcc[char1 & '\u007f'], this.optoSft[j]);
                        continue Label_0020;
                    }
                }
                matcher.first = i;
                if (this.next.match(matcher, i + length, charSequence)) {
                    matcher.first = i;
                    matcher.groups[0] = matcher.first;
                    matcher.groups[1] = matcher.last;
                    return true;
                }
                ++i;
            }
            matcher.hitEnd = true;
            return false;
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            treeInfo.minLength += this.buffer.length;
            treeInfo.maxValid = false;
            return this.next.study(treeInfo);
        }
    }
    
    static final class BnMS extends BnM
    {
        int lengthInChars;
        
        BnMS(final int[] array, final int[] array2, final int[] array3, final Node node) {
            super(array, array2, array3, node);
            for (int i = 0; i < this.buffer.length; ++i) {
                this.lengthInChars += Character.charCount(this.buffer[i]);
            }
        }
        
        @Override
        boolean match(final Matcher matcher, int i, final CharSequence charSequence) {
            final int[] buffer = this.buffer;
            final int length = buffer.length;
            final int n = matcher.to - this.lengthInChars;
        Label_0022:
            while (i <= n) {
                int codePointBefore;
                for (int j = countChars(charSequence, i, length), n2 = length - 1; j > 0; j -= Character.charCount(codePointBefore), --n2) {
                    codePointBefore = Character.codePointBefore(charSequence, i + j);
                    if (codePointBefore != buffer[n2]) {
                        i += countChars(charSequence, i, Math.max(n2 + 1 - this.lastOcc[codePointBefore & 0x7F], this.optoSft[n2]));
                        continue Label_0022;
                    }
                }
                matcher.first = i;
                if (this.next.match(matcher, i + this.lengthInChars, charSequence)) {
                    matcher.first = i;
                    matcher.groups[0] = matcher.first;
                    matcher.groups[1] = matcher.last;
                    return true;
                }
                i += countChars(charSequence, i, 1);
            }
            matcher.hitEnd = true;
            return false;
        }
    }
    
    static final class Slice extends SliceNode
    {
        Slice(final int[] array) {
            super(array);
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int[] buffer = this.buffer;
            final int length = buffer.length;
            for (int i = 0; i < length; ++i) {
                if (n + i >= matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                if (buffer[i] != charSequence.charAt(n + i)) {
                    return false;
                }
            }
            return this.next.match(matcher, n + length, charSequence);
        }
    }
    
    static class SliceNode extends Node
    {
        int[] buffer;
        
        SliceNode(final int[] buffer) {
            this.buffer = buffer;
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            treeInfo.minLength += this.buffer.length;
            treeInfo.maxLength += this.buffer.length;
            return this.next.study(treeInfo);
        }
    }
    
    static final class SliceS extends SliceNode
    {
        SliceS(final int[] array) {
            super(array);
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int[] buffer = this.buffer;
            int n2 = n;
            for (int i = 0; i < buffer.length; ++i) {
                if (n2 >= matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                final int codePoint = Character.codePointAt(charSequence, n2);
                if (buffer[i] != codePoint) {
                    return false;
                }
                n2 += Character.charCount(codePoint);
                if (n2 > matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
            }
            return this.next.match(matcher, n2, charSequence);
        }
    }
    
    static final class Bound extends Node
    {
        static int LEFT;
        static int RIGHT;
        static int BOTH;
        static int NONE;
        int type;
        boolean useUWORD;
        
        Bound(final int type, final boolean useUWORD) {
            this.type = type;
            this.useUWORD = useUWORD;
        }
        
        boolean isWord(final int n) {
            return this.useUWORD ? UnicodeProp.WORD.is(n) : (n == 95 || Character.isLetterOrDigit(n));
        }
        
        int check(final Matcher matcher, final int n, final CharSequence charSequence) {
            boolean b = false;
            int from = matcher.from;
            int n2 = matcher.to;
            if (matcher.transparentBounds) {
                from = 0;
                n2 = matcher.getTextLength();
            }
            if (n > from) {
                final int codePointBefore = Character.codePointBefore(charSequence, n);
                b = (this.isWord(codePointBefore) || (Character.getType(codePointBefore) == 6 && hasBaseCharacter(matcher, n - 1, charSequence)));
            }
            boolean b2 = false;
            if (n < n2) {
                final int codePoint = Character.codePointAt(charSequence, n);
                b2 = (this.isWord(codePoint) || (Character.getType(codePoint) == 6 && hasBaseCharacter(matcher, n, charSequence)));
            }
            else {
                matcher.hitEnd = true;
                matcher.requireEnd = true;
            }
            return (b ^ b2) ? (b2 ? Bound.LEFT : Bound.RIGHT) : Bound.NONE;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            return (this.check(matcher, n, charSequence) & this.type) > 0 && this.next.match(matcher, n, charSequence);
        }
        
        static {
            Bound.LEFT = 1;
            Bound.RIGHT = 2;
            Bound.BOTH = 3;
            Bound.NONE = 4;
        }
    }
    
    static final class Branch extends Node
    {
        Node[] atoms;
        int size;
        Node conn;
        
        Branch(final Node node, final Node node2, final Node conn) {
            this.atoms = new Node[2];
            this.size = 2;
            this.conn = conn;
            this.atoms[0] = node;
            this.atoms[1] = node2;
        }
        
        void add(final Node node) {
            if (this.size >= this.atoms.length) {
                final Node[] atoms = new Node[this.atoms.length * 2];
                System.arraycopy(this.atoms, 0, atoms, 0, this.atoms.length);
                this.atoms = atoms;
            }
            this.atoms[this.size++] = node;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            for (int i = 0; i < this.size; ++i) {
                if (this.atoms[i] == null) {
                    if (this.conn.next.match(matcher, n, charSequence)) {
                        return true;
                    }
                }
                else if (this.atoms[i].match(matcher, n, charSequence)) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            final int minLength = treeInfo.minLength;
            final int maxLength = treeInfo.maxLength;
            boolean maxValid = treeInfo.maxValid;
            int min = Integer.MAX_VALUE;
            int max = -1;
            for (int i = 0; i < this.size; ++i) {
                treeInfo.reset();
                if (this.atoms[i] != null) {
                    this.atoms[i].study(treeInfo);
                }
                min = Math.min(min, treeInfo.minLength);
                max = Math.max(max, treeInfo.maxLength);
                maxValid &= treeInfo.maxValid;
            }
            final int n = minLength + min;
            final int n2 = maxLength + max;
            treeInfo.reset();
            this.conn.next.study(treeInfo);
            treeInfo.minLength += n;
            treeInfo.maxLength += n2;
            treeInfo.maxValid &= maxValid;
            return treeInfo.deterministic = false;
        }
    }
    
    static final class BranchConn extends Node
    {
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            return this.next.match(matcher, n, charSequence);
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            return treeInfo.deterministic;
        }
    }
    
    static class CIBackRef extends Node
    {
        int groupIndex;
        boolean doUnicodeCase;
        
        CIBackRef(final int n, final boolean doUnicodeCase) {
            this.groupIndex = n + n;
            this.doUnicodeCase = doUnicodeCase;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            int n2 = matcher.groups[this.groupIndex];
            final int n3 = matcher.groups[this.groupIndex + 1] - n2;
            if (n2 < 0) {
                return false;
            }
            if (n + n3 > matcher.to) {
                matcher.hitEnd = true;
                return false;
            }
            int n4 = n;
            for (int i = 0; i < n3; ++i) {
                final int codePoint = Character.codePointAt(charSequence, n4);
                final int codePoint2 = Character.codePointAt(charSequence, n2);
                if (codePoint != codePoint2) {
                    if (this.doUnicodeCase) {
                        final int upperCase = Character.toUpperCase(codePoint);
                        final int upperCase2 = Character.toUpperCase(codePoint2);
                        if (upperCase != upperCase2 && Character.toLowerCase(upperCase) != Character.toLowerCase(upperCase2)) {
                            return false;
                        }
                    }
                    else if (ASCII.toLower(codePoint) != ASCII.toLower(codePoint2)) {
                        return false;
                    }
                }
                n4 += Character.charCount(codePoint);
                n2 += Character.charCount(codePoint2);
            }
            return this.next.match(matcher, n + n3, charSequence);
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            treeInfo.maxValid = false;
            return this.next.study(treeInfo);
        }
    }
    
    static final class Caret extends Node
    {
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            int from = matcher.from;
            int n2 = matcher.to;
            if (!matcher.anchoringBounds) {
                from = 0;
                n2 = matcher.getTextLength();
            }
            if (n == n2) {
                matcher.hitEnd = true;
                return false;
            }
            if (n > from) {
                final char char1 = charSequence.charAt(n - 1);
                if (char1 != '\n' && char1 != '\r' && (char1 | '\u0001') != '\u2029' && char1 != '\u0085') {
                    return false;
                }
                if (char1 == '\r' && charSequence.charAt(n) == '\n') {
                    return false;
                }
            }
            return this.next.match(matcher, n, charSequence);
        }
    }
    
    static final class Category extends CharProperty
    {
        final int typeMask;
        
        Category(final int typeMask) {
            this.typeMask = typeMask;
        }
        
        @Override
        boolean isSatisfiedBy(final int n) {
            return (this.typeMask & 1 << Character.getType(n)) != 0x0;
        }
    }
    
    private static class CharPropertyNames
    {
        private static final HashMap<String, CharPropertyFactory> map;
        
        static CharProperty charPropertyFor(final String s) {
            final CharPropertyFactory charPropertyFactory = CharPropertyNames.map.get(s);
            return (charPropertyFactory == null) ? null : charPropertyFactory.make();
        }
        
        private static void defCategory(final String s, final int n) {
            CharPropertyNames.map.put(s, new CharPropertyFactory() {
                @Override
                CharProperty make() {
                    return new Category(n);
                }
            });
        }
        
        private static void defRange(final String s, final int n, final int n2) {
            CharPropertyNames.map.put(s, new CharPropertyFactory() {
                @Override
                CharProperty make() {
                    return rangeFor(n, n2);
                }
            });
        }
        
        private static void defCtype(final String s, final int n) {
            CharPropertyNames.map.put(s, new CharPropertyFactory() {
                @Override
                CharProperty make() {
                    return new Ctype(n);
                }
            });
        }
        
        private static void defClone(final String s, final CloneableProperty cloneableProperty) {
            CharPropertyNames.map.put(s, new CharPropertyFactory() {
                @Override
                CharProperty make() {
                    return cloneableProperty.clone();
                }
            });
        }
        
        static {
            map = new HashMap<String, CharPropertyFactory>();
            defCategory("Cn", 1);
            defCategory("Lu", 2);
            defCategory("Ll", 4);
            defCategory("Lt", 8);
            defCategory("Lm", 16);
            defCategory("Lo", 32);
            defCategory("Mn", 64);
            defCategory("Me", 128);
            defCategory("Mc", 256);
            defCategory("Nd", 512);
            defCategory("Nl", 1024);
            defCategory("No", 2048);
            defCategory("Zs", 4096);
            defCategory("Zl", 8192);
            defCategory("Zp", 16384);
            defCategory("Cc", 32768);
            defCategory("Cf", 65536);
            defCategory("Co", 262144);
            defCategory("Cs", 524288);
            defCategory("Pd", 1048576);
            defCategory("Ps", 2097152);
            defCategory("Pe", 4194304);
            defCategory("Pc", 8388608);
            defCategory("Po", 16777216);
            defCategory("Sm", 33554432);
            defCategory("Sc", 67108864);
            defCategory("Sk", 134217728);
            defCategory("So", 268435456);
            defCategory("Pi", 536870912);
            defCategory("Pf", 1073741824);
            defCategory("L", 62);
            defCategory("M", 448);
            defCategory("N", 3584);
            defCategory("Z", 28672);
            defCategory("C", 884736);
            defCategory("P", 1643118592);
            defCategory("S", 503316480);
            defCategory("LC", 14);
            defCategory("LD", 574);
            defRange("L1", 0, 255);
            CharPropertyNames.map.put("all", new CharPropertyFactory() {
                @Override
                CharProperty make() {
                    return new All();
                }
            });
            defRange("ASCII", 0, 127);
            defCtype("Alnum", 1792);
            defCtype("Alpha", 768);
            defCtype("Blank", 16384);
            defCtype("Cntrl", 8192);
            defRange("Digit", 48, 57);
            defCtype("Graph", 5888);
            defRange("Lower", 97, 122);
            defRange("Print", 32, 126);
            defCtype("Punct", 4096);
            defCtype("Space", 2048);
            defRange("Upper", 65, 90);
            defCtype("XDigit", 32768);
            defClone("javaLowerCase", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isLowerCase(n);
                }
            });
            defClone("javaUpperCase", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isUpperCase(n);
                }
            });
            defClone("javaAlphabetic", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isAlphabetic(n);
                }
            });
            defClone("javaIdeographic", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isIdeographic(n);
                }
            });
            defClone("javaTitleCase", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isTitleCase(n);
                }
            });
            defClone("javaDigit", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isDigit(n);
                }
            });
            defClone("javaDefined", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isDefined(n);
                }
            });
            defClone("javaLetter", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isLetter(n);
                }
            });
            defClone("javaLetterOrDigit", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isLetterOrDigit(n);
                }
            });
            defClone("javaJavaIdentifierStart", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isJavaIdentifierStart(n);
                }
            });
            defClone("javaJavaIdentifierPart", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isJavaIdentifierPart(n);
                }
            });
            defClone("javaUnicodeIdentifierStart", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isUnicodeIdentifierStart(n);
                }
            });
            defClone("javaUnicodeIdentifierPart", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isUnicodeIdentifierPart(n);
                }
            });
            defClone("javaIdentifierIgnorable", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isIdentifierIgnorable(n);
                }
            });
            defClone("javaSpaceChar", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isSpaceChar(n);
                }
            });
            defClone("javaWhitespace", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isWhitespace(n);
                }
            });
            defClone("javaISOControl", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isISOControl(n);
                }
            });
            defClone("javaMirrored", new CloneableProperty() {
                @Override
                boolean isSatisfiedBy(final int n) {
                    return Character.isMirrored(n);
                }
            });
        }
        
        private abstract static class CharPropertyFactory
        {
            abstract CharProperty make();
        }
        
        private abstract static class CloneableProperty extends CharProperty implements Cloneable
        {
            public CloneableProperty clone() {
                try {
                    return (CloneableProperty)super.clone();
                }
                catch (CloneNotSupportedException ex) {
                    throw new AssertionError((Object)ex);
                }
            }
        }
    }
    
    static final class Ctype extends BmpCharProperty
    {
        final int ctype;
        
        Ctype(final int ctype) {
            this.ctype = ctype;
        }
        
        @Override
        boolean isSatisfiedBy(final int n) {
            return n < 128 && ASCII.isType(n, this.ctype);
        }
    }
    
    static final class Conditional extends Node
    {
        Node cond;
        Node yes;
        Node not;
        
        Conditional(final Node cond, final Node yes, final Node not) {
            this.cond = cond;
            this.yes = yes;
            this.not = not;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            if (this.cond.match(matcher, n, charSequence)) {
                return this.yes.match(matcher, n, charSequence);
            }
            return this.not.match(matcher, n, charSequence);
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            final int minLength = treeInfo.minLength;
            final int maxLength = treeInfo.maxLength;
            final boolean maxValid = treeInfo.maxValid;
            treeInfo.reset();
            this.yes.study(treeInfo);
            final int minLength2 = treeInfo.minLength;
            final int maxLength2 = treeInfo.maxLength;
            final boolean maxValid2 = treeInfo.maxValid;
            treeInfo.reset();
            this.not.study(treeInfo);
            treeInfo.minLength = minLength + Math.min(minLength2, treeInfo.minLength);
            treeInfo.maxLength = maxLength + Math.max(maxLength2, treeInfo.maxLength);
            treeInfo.maxValid &= (maxValid & maxValid2);
            treeInfo.deterministic = false;
            return this.next.study(treeInfo);
        }
    }
    
    static final class Curly extends Node
    {
        Node atom;
        int type;
        int cmin;
        int cmax;
        
        Curly(final Node atom, final int cmin, final int cmax, final int type) {
            this.atom = atom;
            this.type = type;
            this.cmin = cmin;
            this.cmax = cmax;
        }
        
        @Override
        boolean match(final Matcher matcher, int last, final CharSequence charSequence) {
            int i;
            for (i = 0; i < this.cmin; ++i) {
                if (!this.atom.match(matcher, last, charSequence)) {
                    return false;
                }
                last = matcher.last;
            }
            if (this.type == 0) {
                return this.match0(matcher, last, i, charSequence);
            }
            if (this.type == 1) {
                return this.match1(matcher, last, i, charSequence);
            }
            return this.match2(matcher, last, i, charSequence);
        }
        
        boolean match0(final Matcher matcher, int last, int i, final CharSequence charSequence) {
            if (i >= this.cmax) {
                return this.next.match(matcher, last, charSequence);
            }
            final int n = i;
            if (this.atom.match(matcher, last, charSequence)) {
                final int n2 = matcher.last - last;
                if (n2 != 0) {
                    last = matcher.last;
                    ++i;
                    while (i < this.cmax) {
                        if (!this.atom.match(matcher, last, charSequence)) {
                            break;
                        }
                        if (last + n2 != matcher.last) {
                            if (this.match0(matcher, matcher.last, i + 1, charSequence)) {
                                return true;
                            }
                            break;
                        }
                        else {
                            last += n2;
                            ++i;
                        }
                    }
                    while (i >= n) {
                        if (this.next.match(matcher, last, charSequence)) {
                            return true;
                        }
                        last -= n2;
                        --i;
                    }
                    return false;
                }
            }
            return this.next.match(matcher, last, charSequence);
        }
        
        boolean match1(final Matcher matcher, int last, int n, final CharSequence charSequence) {
            while (!this.next.match(matcher, last, charSequence)) {
                if (n >= this.cmax) {
                    return false;
                }
                if (!this.atom.match(matcher, last, charSequence)) {
                    return false;
                }
                if (last == matcher.last) {
                    return false;
                }
                last = matcher.last;
                ++n;
            }
            return true;
        }
        
        boolean match2(final Matcher matcher, int last, int i, final CharSequence charSequence) {
            while (i < this.cmax) {
                if (!this.atom.match(matcher, last, charSequence)) {
                    break;
                }
                if (last == matcher.last) {
                    break;
                }
                last = matcher.last;
                ++i;
            }
            return this.next.match(matcher, last, charSequence);
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            final int minLength = treeInfo.minLength;
            final int maxLength = treeInfo.maxLength;
            final boolean maxValid = treeInfo.maxValid;
            final boolean deterministic = treeInfo.deterministic;
            treeInfo.reset();
            this.atom.study(treeInfo);
            int minLength2 = treeInfo.minLength * this.cmin + minLength;
            if (minLength2 < minLength) {
                minLength2 = 268435455;
            }
            treeInfo.minLength = minLength2;
            if (maxValid & treeInfo.maxValid) {
                if ((treeInfo.maxLength = treeInfo.maxLength * this.cmax + maxLength) < maxLength) {
                    treeInfo.maxValid = false;
                }
            }
            else {
                treeInfo.maxValid = false;
            }
            if (treeInfo.deterministic && this.cmin == this.cmax) {
                treeInfo.deterministic = deterministic;
            }
            else {
                treeInfo.deterministic = false;
            }
            return this.next.study(treeInfo);
        }
    }
    
    static final class Dollar extends Node
    {
        boolean multiline;
        
        Dollar(final boolean multiline) {
            this.multiline = multiline;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int n2 = matcher.anchoringBounds ? matcher.to : matcher.getTextLength();
            if (!this.multiline) {
                if (n < n2 - 2) {
                    return false;
                }
                if (n == n2 - 2) {
                    if (charSequence.charAt(n) != '\r') {
                        return false;
                    }
                    if (charSequence.charAt(n + 1) != '\n') {
                        return false;
                    }
                }
            }
            if (n < n2) {
                final char char1 = charSequence.charAt(n);
                if (char1 == '\n') {
                    if (n > 0 && charSequence.charAt(n - 1) == '\r') {
                        return false;
                    }
                    if (this.multiline) {
                        return this.next.match(matcher, n, charSequence);
                    }
                }
                else {
                    if (char1 != '\r' && char1 != '\u0085' && (char1 | '\u0001') != '\u2029') {
                        return false;
                    }
                    if (this.multiline) {
                        return this.next.match(matcher, n, charSequence);
                    }
                }
            }
            matcher.hitEnd = true;
            matcher.requireEnd = true;
            return this.next.match(matcher, n, charSequence);
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            this.next.study(treeInfo);
            return treeInfo.deterministic;
        }
    }
    
    static final class Dot extends CharProperty
    {
        @Override
        boolean isSatisfiedBy(final int n) {
            return n != 10 && n != 13 && (n | 0x1) != 0x2029 && n != 133;
        }
    }
    
    static final class End extends Node
    {
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            if (n == (matcher.anchoringBounds ? matcher.to : matcher.getTextLength())) {
                matcher.hitEnd = true;
                return this.next.match(matcher, n, charSequence);
            }
            return false;
        }
    }
    
    static final class First extends Node
    {
        Node atom;
        
        First(final Node node) {
            this.atom = BnM.optimize(node);
        }
        
        @Override
        boolean match(final Matcher matcher, int i, final CharSequence charSequence) {
            if (this.atom instanceof BnM) {
                return this.atom.match(matcher, i, charSequence) && this.next.match(matcher, matcher.last, charSequence);
            }
            while (i <= matcher.to) {
                if (this.atom.match(matcher, i, charSequence)) {
                    return this.next.match(matcher, matcher.last, charSequence);
                }
                i += countChars(charSequence, i, 1);
                ++matcher.first;
            }
            matcher.hitEnd = true;
            return false;
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            this.atom.study(treeInfo);
            treeInfo.maxValid = false;
            treeInfo.deterministic = false;
            return this.next.study(treeInfo);
        }
    }
    
    static final class GroupCurly extends Node
    {
        Node atom;
        int type;
        int cmin;
        int cmax;
        int localIndex;
        int groupIndex;
        boolean capture;
        
        GroupCurly(final Node atom, final int cmin, final int cmax, final int type, final int localIndex, final int groupIndex, final boolean capture) {
            this.atom = atom;
            this.type = type;
            this.cmin = cmin;
            this.cmax = cmax;
            this.localIndex = localIndex;
            this.groupIndex = groupIndex;
            this.capture = capture;
        }
        
        @Override
        boolean match(final Matcher matcher, int last, final CharSequence charSequence) {
            final int[] groups = matcher.groups;
            final int[] locals = matcher.locals;
            final int n = locals[this.localIndex];
            int n2 = 0;
            int n3 = 0;
            if (this.capture) {
                n2 = groups[this.groupIndex];
                n3 = groups[this.groupIndex + 1];
            }
            locals[this.localIndex] = -1;
            boolean b = true;
            for (int i = 0; i < this.cmin; ++i) {
                if (!this.atom.match(matcher, last, charSequence)) {
                    b = false;
                    break;
                }
                if (this.capture) {
                    groups[this.groupIndex] = last;
                    groups[this.groupIndex + 1] = matcher.last;
                }
                last = matcher.last;
            }
            if (b) {
                if (this.type == 0) {
                    b = this.match0(matcher, last, this.cmin, charSequence);
                }
                else if (this.type == 1) {
                    b = this.match1(matcher, last, this.cmin, charSequence);
                }
                else {
                    b = this.match2(matcher, last, this.cmin, charSequence);
                }
            }
            if (!b) {
                locals[this.localIndex] = n;
                if (this.capture) {
                    groups[this.groupIndex] = n2;
                    groups[this.groupIndex + 1] = n3;
                }
            }
            return b;
        }
        
        boolean match0(final Matcher matcher, int n, int i, final CharSequence charSequence) {
            final int n2 = i;
            final int[] groups = matcher.groups;
            int n3 = 0;
            int n4 = 0;
            if (this.capture) {
                n3 = groups[this.groupIndex];
                n4 = groups[this.groupIndex + 1];
            }
            Label_0297: {
                if (i < this.cmax) {
                    if (this.atom.match(matcher, n, charSequence)) {
                        final int n5 = matcher.last - n;
                        if (n5 <= 0) {
                            if (this.capture) {
                                groups[this.groupIndex] = n;
                                groups[this.groupIndex + 1] = n + n5;
                            }
                            n += n5;
                        }
                        else {
                            while (true) {
                                do {
                                    if (this.capture) {
                                        groups[this.groupIndex] = n;
                                        groups[this.groupIndex + 1] = n + n5;
                                    }
                                    n += n5;
                                    if (++i < this.cmax) {
                                        if (this.atom.match(matcher, n, charSequence)) {
                                            continue;
                                        }
                                    }
                                    while (i > n2) {
                                        if (this.next.match(matcher, n, charSequence)) {
                                            if (this.capture) {
                                                groups[this.groupIndex + 1] = n;
                                                groups[this.groupIndex] = n - n5;
                                            }
                                            return true;
                                        }
                                        n -= n5;
                                        if (this.capture) {
                                            groups[this.groupIndex + 1] = n;
                                            groups[this.groupIndex] = n - n5;
                                        }
                                        --i;
                                    }
                                    break Label_0297;
                                } while (n + n5 == matcher.last);
                                if (this.match0(matcher, n, i, charSequence)) {
                                    return true;
                                }
                                continue;
                            }
                        }
                    }
                }
            }
            if (this.capture) {
                groups[this.groupIndex] = n3;
                groups[this.groupIndex + 1] = n4;
            }
            return this.next.match(matcher, n, charSequence);
        }
        
        boolean match1(final Matcher matcher, int last, int n, final CharSequence charSequence) {
            while (!this.next.match(matcher, last, charSequence)) {
                if (n >= this.cmax) {
                    return false;
                }
                if (!this.atom.match(matcher, last, charSequence)) {
                    return false;
                }
                if (last == matcher.last) {
                    return false;
                }
                if (this.capture) {
                    matcher.groups[this.groupIndex] = last;
                    matcher.groups[this.groupIndex + 1] = matcher.last;
                }
                last = matcher.last;
                ++n;
            }
            return true;
        }
        
        boolean match2(final Matcher matcher, int last, int i, final CharSequence charSequence) {
            while (i < this.cmax) {
                if (!this.atom.match(matcher, last, charSequence)) {
                    break;
                }
                if (this.capture) {
                    matcher.groups[this.groupIndex] = last;
                    matcher.groups[this.groupIndex + 1] = matcher.last;
                }
                if (last == matcher.last) {
                    break;
                }
                last = matcher.last;
                ++i;
            }
            return this.next.match(matcher, last, charSequence);
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            final int minLength = treeInfo.minLength;
            final int maxLength = treeInfo.maxLength;
            final boolean maxValid = treeInfo.maxValid;
            final boolean deterministic = treeInfo.deterministic;
            treeInfo.reset();
            this.atom.study(treeInfo);
            int minLength2 = treeInfo.minLength * this.cmin + minLength;
            if (minLength2 < minLength) {
                minLength2 = 268435455;
            }
            treeInfo.minLength = minLength2;
            if (maxValid & treeInfo.maxValid) {
                if ((treeInfo.maxLength = treeInfo.maxLength * this.cmax + maxLength) < maxLength) {
                    treeInfo.maxValid = false;
                }
            }
            else {
                treeInfo.maxValid = false;
            }
            if (treeInfo.deterministic && this.cmin == this.cmax) {
                treeInfo.deterministic = deterministic;
            }
            else {
                treeInfo.deterministic = false;
            }
            return this.next.study(treeInfo);
        }
    }
    
    static final class GroupHead extends Node
    {
        int localIndex;
        
        GroupHead(final int localIndex) {
            this.localIndex = localIndex;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int n2 = matcher.locals[this.localIndex];
            matcher.locals[this.localIndex] = n;
            final boolean match = this.next.match(matcher, n, charSequence);
            matcher.locals[this.localIndex] = n2;
            return match;
        }
        
        boolean matchRef(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int n2 = matcher.locals[this.localIndex];
            matcher.locals[this.localIndex] = ~n;
            final boolean match = this.next.match(matcher, n, charSequence);
            matcher.locals[this.localIndex] = n2;
            return match;
        }
    }
    
    static final class GroupRef extends Node
    {
        GroupHead head;
        
        GroupRef(final GroupHead head) {
            this.head = head;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            return this.head.matchRef(matcher, n, charSequence) && this.next.match(matcher, matcher.last, charSequence);
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            treeInfo.maxValid = false;
            treeInfo.deterministic = false;
            return this.next.study(treeInfo);
        }
    }
    
    static final class GroupTail extends Node
    {
        int localIndex;
        int groupIndex;
        
        GroupTail(final int localIndex, final int n) {
            this.localIndex = localIndex;
            this.groupIndex = n + n;
        }
        
        @Override
        boolean match(final Matcher matcher, final int last, final CharSequence charSequence) {
            final int n = matcher.locals[this.localIndex];
            if (n < 0) {
                matcher.last = last;
                return true;
            }
            final int n2 = matcher.groups[this.groupIndex];
            final int n3 = matcher.groups[this.groupIndex + 1];
            matcher.groups[this.groupIndex] = n;
            matcher.groups[this.groupIndex + 1] = last;
            if (this.next.match(matcher, last, charSequence)) {
                return true;
            }
            matcher.groups[this.groupIndex] = n2;
            matcher.groups[this.groupIndex + 1] = n3;
            return false;
        }
    }
    
    static final class HorizWS extends BmpCharProperty
    {
        @Override
        boolean isSatisfiedBy(final int n) {
            return n == 9 || n == 32 || n == 160 || n == 5760 || n == 6158 || (n >= 8192 && n <= 8202) || n == 8239 || n == 8287 || n == 12288;
        }
    }
    
    static final class LastMatch extends Node
    {
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            return n == matcher.oldLast && this.next.match(matcher, n, charSequence);
        }
    }
    
    static class LastNode extends Node
    {
        @Override
        boolean match(final Matcher matcher, final int last, final CharSequence charSequence) {
            if (matcher.acceptMode == 1 && last != matcher.to) {
                return false;
            }
            matcher.last = last;
            matcher.groups[0] = matcher.first;
            matcher.groups[1] = matcher.last;
            return true;
        }
    }
    
    static final class LazyLoop extends Loop
    {
        LazyLoop(final int n, final int n2) {
            super(n, n2);
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            if (n <= matcher.locals[this.beginIndex]) {
                return this.next.match(matcher, n, charSequence);
            }
            final int n2 = matcher.locals[this.countIndex];
            if (n2 < this.cmin) {
                matcher.locals[this.countIndex] = n2 + 1;
                final boolean match = this.body.match(matcher, n, charSequence);
                if (!match) {
                    matcher.locals[this.countIndex] = n2;
                }
                return match;
            }
            if (this.next.match(matcher, n, charSequence)) {
                return true;
            }
            if (n2 < this.cmax) {
                matcher.locals[this.countIndex] = n2 + 1;
                final boolean match2 = this.body.match(matcher, n, charSequence);
                if (!match2) {
                    matcher.locals[this.countIndex] = n2;
                }
                return match2;
            }
            return false;
        }
        
        @Override
        boolean matchInit(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int n2 = matcher.locals[this.countIndex];
            boolean b = false;
            if (0 < this.cmin) {
                matcher.locals[this.countIndex] = 1;
                b = this.body.match(matcher, n, charSequence);
            }
            else if (this.next.match(matcher, n, charSequence)) {
                b = true;
            }
            else if (0 < this.cmax) {
                matcher.locals[this.countIndex] = 1;
                b = this.body.match(matcher, n, charSequence);
            }
            matcher.locals[this.countIndex] = n2;
            return b;
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            treeInfo.maxValid = false;
            return treeInfo.deterministic = false;
        }
    }
    
    static class Loop extends Node
    {
        Node body;
        int countIndex;
        int beginIndex;
        int cmin;
        int cmax;
        
        Loop(final int countIndex, final int beginIndex) {
            this.countIndex = countIndex;
            this.beginIndex = beginIndex;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            if (n > matcher.locals[this.beginIndex]) {
                final int n2 = matcher.locals[this.countIndex];
                if (n2 < this.cmin) {
                    matcher.locals[this.countIndex] = n2 + 1;
                    final boolean match = this.body.match(matcher, n, charSequence);
                    if (!match) {
                        matcher.locals[this.countIndex] = n2;
                    }
                    return match;
                }
                if (n2 < this.cmax) {
                    matcher.locals[this.countIndex] = n2 + 1;
                    if (this.body.match(matcher, n, charSequence)) {
                        return true;
                    }
                    matcher.locals[this.countIndex] = n2;
                }
            }
            return this.next.match(matcher, n, charSequence);
        }
        
        boolean matchInit(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int n2 = matcher.locals[this.countIndex];
            boolean b;
            if (0 < this.cmin) {
                matcher.locals[this.countIndex] = 1;
                b = this.body.match(matcher, n, charSequence);
            }
            else if (0 < this.cmax) {
                matcher.locals[this.countIndex] = 1;
                b = this.body.match(matcher, n, charSequence);
                if (!b) {
                    b = this.next.match(matcher, n, charSequence);
                }
            }
            else {
                b = this.next.match(matcher, n, charSequence);
            }
            matcher.locals[this.countIndex] = n2;
            return b;
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            treeInfo.maxValid = false;
            return treeInfo.deterministic = false;
        }
    }
    
    static final class LineEnding extends Node
    {
        @Override
        boolean match(final Matcher matcher, int n, final CharSequence charSequence) {
            if (n < matcher.to) {
                final char char1 = charSequence.charAt(n);
                if (char1 == '\n' || char1 == '\u000b' || char1 == '\f' || char1 == '\u0085' || char1 == '\u2028' || char1 == '\u2029') {
                    return this.next.match(matcher, n + 1, charSequence);
                }
                if (char1 == '\r') {
                    if (++n < matcher.to && charSequence.charAt(n) == '\n') {
                        ++n;
                    }
                    return this.next.match(matcher, n, charSequence);
                }
            }
            else {
                matcher.hitEnd = true;
            }
            return false;
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            ++treeInfo.minLength;
            treeInfo.maxLength += 2;
            return this.next.study(treeInfo);
        }
    }
    
    static final class Neg extends Node
    {
        Node cond;
        
        Neg(final Node cond) {
            this.cond = cond;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int to = matcher.to;
            boolean b = false;
            if (matcher.transparentBounds) {
                matcher.to = matcher.getTextLength();
            }
            try {
                if (n < matcher.to) {
                    b = !this.cond.match(matcher, n, charSequence);
                }
                else {
                    matcher.requireEnd = true;
                    b = !this.cond.match(matcher, n, charSequence);
                }
            }
            finally {
                matcher.to = to;
            }
            return b && this.next.match(matcher, n, charSequence);
        }
    }
    
    static class NotBehind extends Node
    {
        Node cond;
        int rmax;
        int rmin;
        
        NotBehind(final Node cond, final int rmax, final int rmin) {
            this.cond = cond;
            this.rmax = rmax;
            this.rmin = rmin;
        }
        
        @Override
        boolean match(final Matcher matcher, final int lookbehindTo, final CharSequence charSequence) {
            final int lookbehindTo2 = matcher.lookbehindTo;
            final int from = matcher.from;
            boolean match = false;
            final int max = Math.max(lookbehindTo - this.rmax, matcher.transparentBounds ? 0 : matcher.from);
            matcher.lookbehindTo = lookbehindTo;
            if (matcher.transparentBounds) {
                matcher.from = 0;
            }
            for (int n = lookbehindTo - this.rmin; !match && n >= max; match = this.cond.match(matcher, n, charSequence), --n) {}
            matcher.from = from;
            matcher.lookbehindTo = lookbehindTo2;
            return !match && this.next.match(matcher, lookbehindTo, charSequence);
        }
    }
    
    static final class NotBehindS extends NotBehind
    {
        NotBehindS(final Node node, final int n, final int n2) {
            super(node, n, n2);
        }
        
        @Override
        boolean match(final Matcher matcher, final int lookbehindTo, final CharSequence charSequence) {
            final int access$300 = countChars(charSequence, lookbehindTo, -this.rmax);
            final int access$301 = countChars(charSequence, lookbehindTo, -this.rmin);
            final int from = matcher.from;
            final int lookbehindTo2 = matcher.lookbehindTo;
            boolean match = false;
            final int max = Math.max(lookbehindTo - access$300, matcher.transparentBounds ? 0 : matcher.from);
            matcher.lookbehindTo = lookbehindTo;
            if (matcher.transparentBounds) {
                matcher.from = 0;
            }
            for (int n = lookbehindTo - access$301; !match && n >= max; match = this.cond.match(matcher, n, charSequence), n -= ((n > max) ? countChars(charSequence, n, -1) : 1)) {}
            matcher.from = from;
            matcher.lookbehindTo = lookbehindTo2;
            return !match && this.next.match(matcher, lookbehindTo, charSequence);
        }
    }
    
    static final class Pos extends Node
    {
        Node cond;
        
        Pos(final Node cond) {
            this.cond = cond;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int to = matcher.to;
            boolean match = false;
            if (matcher.transparentBounds) {
                matcher.to = matcher.getTextLength();
            }
            try {
                match = this.cond.match(matcher, n, charSequence);
            }
            finally {
                matcher.to = to;
            }
            return match && this.next.match(matcher, n, charSequence);
        }
    }
    
    static final class Prolog extends Node
    {
        Loop loop;
        
        Prolog(final Loop loop) {
            this.loop = loop;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            return this.loop.matchInit(matcher, n, charSequence);
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            return this.loop.study(treeInfo);
        }
    }
    
    static final class Ques extends Node
    {
        Node atom;
        int type;
        
        Ques(final Node atom, final int type) {
            this.atom = atom;
            this.type = type;
        }
        
        @Override
        boolean match(final Matcher matcher, int last, final CharSequence charSequence) {
            switch (this.type) {
                case 0: {
                    return (this.atom.match(matcher, last, charSequence) && this.next.match(matcher, matcher.last, charSequence)) || this.next.match(matcher, last, charSequence);
                }
                case 1: {
                    return this.next.match(matcher, last, charSequence) || (this.atom.match(matcher, last, charSequence) && this.next.match(matcher, matcher.last, charSequence));
                }
                case 2: {
                    if (this.atom.match(matcher, last, charSequence)) {
                        last = matcher.last;
                    }
                    return this.next.match(matcher, last, charSequence);
                }
                default: {
                    return this.atom.match(matcher, last, charSequence) && this.next.match(matcher, matcher.last, charSequence);
                }
            }
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            if (this.type != 3) {
                final int minLength = treeInfo.minLength;
                this.atom.study(treeInfo);
                treeInfo.minLength = minLength;
                treeInfo.deterministic = false;
                return this.next.study(treeInfo);
            }
            this.atom.study(treeInfo);
            return this.next.study(treeInfo);
        }
    }
    
    static final class Script extends CharProperty
    {
        final Character.UnicodeScript script;
        
        Script(final Character.UnicodeScript script) {
            this.script = script;
        }
        
        @Override
        boolean isSatisfiedBy(final int n) {
            return this.script == Character.UnicodeScript.of(n);
        }
    }
    
    static final class Single extends BmpCharProperty
    {
        final int c;
        
        Single(final int c) {
            this.c = c;
        }
        
        @Override
        boolean isSatisfiedBy(final int n) {
            return n == this.c;
        }
    }
    
    static final class SingleI extends BmpCharProperty
    {
        final int lower;
        final int upper;
        
        SingleI(final int lower, final int upper) {
            this.lower = lower;
            this.upper = upper;
        }
        
        @Override
        boolean isSatisfiedBy(final int n) {
            return n == this.lower || n == this.upper;
        }
    }
    
    static final class SingleS extends CharProperty
    {
        final int c;
        
        SingleS(final int c) {
            this.c = c;
        }
        
        @Override
        boolean isSatisfiedBy(final int n) {
            return n == this.c;
        }
    }
    
    static final class SingleU extends CharProperty
    {
        final int lower;
        
        SingleU(final int lower) {
            this.lower = lower;
        }
        
        @Override
        boolean isSatisfiedBy(final int n) {
            return this.lower == n || this.lower == Character.toLowerCase(Character.toUpperCase(n));
        }
    }
    
    static class SliceI extends SliceNode
    {
        SliceI(final int[] array) {
            super(array);
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int[] buffer = this.buffer;
            final int length = buffer.length;
            for (int i = 0; i < length; ++i) {
                if (n + i >= matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                final char char1 = charSequence.charAt(n + i);
                if (buffer[i] != char1 && buffer[i] != ASCII.toLower(char1)) {
                    return false;
                }
            }
            return this.next.match(matcher, n + length, charSequence);
        }
    }
    
    static class SliceIS extends SliceNode
    {
        SliceIS(final int[] array) {
            super(array);
        }
        
        int toLower(final int n) {
            return ASCII.toLower(n);
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int[] buffer = this.buffer;
            int n2 = n;
            for (int i = 0; i < buffer.length; ++i) {
                if (n2 >= matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                final int codePoint = Character.codePointAt(charSequence, n2);
                if (buffer[i] != codePoint && buffer[i] != this.toLower(codePoint)) {
                    return false;
                }
                n2 += Character.charCount(codePoint);
                if (n2 > matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
            }
            return this.next.match(matcher, n2, charSequence);
        }
    }
    
    static final class SliceU extends SliceNode
    {
        SliceU(final int[] array) {
            super(array);
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int[] buffer = this.buffer;
            final int length = buffer.length;
            for (int i = 0; i < length; ++i) {
                if (n + i >= matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                final char char1 = charSequence.charAt(n + i);
                if (buffer[i] != char1 && buffer[i] != Character.toLowerCase(Character.toUpperCase((int)char1))) {
                    return false;
                }
            }
            return this.next.match(matcher, n + length, charSequence);
        }
    }
    
    static final class SliceUS extends SliceIS
    {
        SliceUS(final int[] array) {
            super(array);
        }
        
        @Override
        int toLower(final int n) {
            return Character.toLowerCase(Character.toUpperCase(n));
        }
    }
    
    static class Start extends Node
    {
        int minLength;
        
        Start(final Node next) {
            this.next = next;
            final TreeInfo treeInfo = new TreeInfo();
            this.next.study(treeInfo);
            this.minLength = treeInfo.minLength;
        }
        
        @Override
        boolean match(final Matcher matcher, int i, final CharSequence charSequence) {
            if (i > matcher.to - this.minLength) {
                matcher.hitEnd = true;
                return false;
            }
            while (i <= matcher.to - this.minLength) {
                if (this.next.match(matcher, i, charSequence)) {
                    matcher.first = i;
                    matcher.groups[0] = matcher.first;
                    matcher.groups[1] = matcher.last;
                    return true;
                }
                ++i;
            }
            matcher.hitEnd = true;
            return false;
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            this.next.study(treeInfo);
            treeInfo.maxValid = false;
            return treeInfo.deterministic = false;
        }
    }
    
    static final class StartS extends Start
    {
        StartS(final Node node) {
            super(node);
        }
        
        @Override
        boolean match(final Matcher matcher, int i, final CharSequence charSequence) {
            if (i > matcher.to - this.minLength) {
                matcher.hitEnd = true;
                return false;
            }
            final int n = matcher.to - this.minLength;
            while (i <= n) {
                if (this.next.match(matcher, i, charSequence)) {
                    matcher.first = i;
                    matcher.groups[0] = matcher.first;
                    matcher.groups[1] = matcher.last;
                    return true;
                }
                if (i == n) {
                    break;
                }
                if (!Character.isHighSurrogate(charSequence.charAt(i++)) || i >= charSequence.length() || !Character.isLowSurrogate(charSequence.charAt(i))) {
                    continue;
                }
                ++i;
            }
            matcher.hitEnd = true;
            return false;
        }
    }
    
    static final class UnixCaret extends Node
    {
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            int from = matcher.from;
            int n2 = matcher.to;
            if (!matcher.anchoringBounds) {
                from = 0;
                n2 = matcher.getTextLength();
            }
            if (n == n2) {
                matcher.hitEnd = true;
                return false;
            }
            return (n <= from || charSequence.charAt(n - 1) == '\n') && this.next.match(matcher, n, charSequence);
        }
    }
    
    static final class UnixDollar extends Node
    {
        boolean multiline;
        
        UnixDollar(final boolean multiline) {
            this.multiline = multiline;
        }
        
        @Override
        boolean match(final Matcher matcher, final int n, final CharSequence charSequence) {
            final int n2 = matcher.anchoringBounds ? matcher.to : matcher.getTextLength();
            if (n < n2) {
                if (charSequence.charAt(n) != '\n') {
                    return false;
                }
                if (!this.multiline && n != n2 - 1) {
                    return false;
                }
                if (this.multiline) {
                    return this.next.match(matcher, n, charSequence);
                }
            }
            matcher.hitEnd = true;
            matcher.requireEnd = true;
            return this.next.match(matcher, n, charSequence);
        }
        
        @Override
        boolean study(final TreeInfo treeInfo) {
            this.next.study(treeInfo);
            return treeInfo.deterministic;
        }
    }
    
    static final class UnixDot extends CharProperty
    {
        @Override
        boolean isSatisfiedBy(final int n) {
            return n != 10;
        }
    }
    
    static final class Utype extends CharProperty
    {
        final UnicodeProp uprop;
        
        Utype(final UnicodeProp uprop) {
            this.uprop = uprop;
        }
        
        @Override
        boolean isSatisfiedBy(final int n) {
            return this.uprop.is(n);
        }
    }
    
    static final class VertWS extends BmpCharProperty
    {
        @Override
        boolean isSatisfiedBy(final int n) {
            return (n >= 10 && n <= 13) || n == 133 || n == 8232 || n == 8233;
        }
    }
}
