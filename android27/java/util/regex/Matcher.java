package java.util.regex;

import java.util.*;
import java.io.*;

public final class Matcher implements MatchResult
{
    Pattern parentPattern;
    int[] groups;
    int from;
    int to;
    int lookbehindTo;
    CharSequence text;
    static final int ENDANCHOR = 1;
    static final int NOANCHOR = 0;
    int acceptMode;
    int first;
    int last;
    int oldLast;
    int lastAppendPosition;
    int[] locals;
    boolean hitEnd;
    boolean requireEnd;
    boolean transparentBounds;
    boolean anchoringBounds;
    
    Matcher() {
        this.acceptMode = 0;
        this.first = -1;
        this.last = 0;
        this.oldLast = -1;
        this.lastAppendPosition = 0;
        this.transparentBounds = false;
        this.anchoringBounds = true;
    }
    
    Matcher(final Pattern parentPattern, final CharSequence text) {
        this.acceptMode = 0;
        this.first = -1;
        this.last = 0;
        this.oldLast = -1;
        this.lastAppendPosition = 0;
        this.transparentBounds = false;
        this.anchoringBounds = true;
        this.parentPattern = parentPattern;
        this.text = text;
        this.groups = new int[Math.max(parentPattern.capturingGroupCount, 10) * 2];
        this.locals = new int[parentPattern.localCount];
        this.reset();
    }
    
    public Pattern pattern() {
        return this.parentPattern;
    }
    
    public MatchResult toMatchResult() {
        final Matcher matcher = new Matcher(this.parentPattern, this.text.toString());
        matcher.first = this.first;
        matcher.last = this.last;
        matcher.groups = this.groups.clone();
        return matcher;
    }
    
    public Matcher usePattern(final Pattern parentPattern) {
        if (parentPattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        }
        this.parentPattern = parentPattern;
        this.groups = new int[Math.max(parentPattern.capturingGroupCount, 10) * 2];
        this.locals = new int[parentPattern.localCount];
        for (int i = 0; i < this.groups.length; ++i) {
            this.groups[i] = -1;
        }
        for (int j = 0; j < this.locals.length; ++j) {
            this.locals[j] = -1;
        }
        return this;
    }
    
    public Matcher reset() {
        this.first = -1;
        this.last = 0;
        this.oldLast = -1;
        for (int i = 0; i < this.groups.length; ++i) {
            this.groups[i] = -1;
        }
        for (int j = 0; j < this.locals.length; ++j) {
            this.locals[j] = -1;
        }
        this.lastAppendPosition = 0;
        this.from = 0;
        this.to = this.getTextLength();
        return this;
    }
    
    public Matcher reset(final CharSequence text) {
        this.text = text;
        return this.reset();
    }
    
    @Override
    public int start() {
        if (this.first < 0) {
            throw new IllegalStateException("No match available");
        }
        return this.first;
    }
    
    @Override
    public int start(final int n) {
        if (this.first < 0) {
            throw new IllegalStateException("No match available");
        }
        if (n < 0 || n > this.groupCount()) {
            throw new IndexOutOfBoundsException("No group " + n);
        }
        return this.groups[n * 2];
    }
    
    public int start(final String s) {
        return this.groups[this.getMatchedGroupIndex(s) * 2];
    }
    
    @Override
    public int end() {
        if (this.first < 0) {
            throw new IllegalStateException("No match available");
        }
        return this.last;
    }
    
    @Override
    public int end(final int n) {
        if (this.first < 0) {
            throw new IllegalStateException("No match available");
        }
        if (n < 0 || n > this.groupCount()) {
            throw new IndexOutOfBoundsException("No group " + n);
        }
        return this.groups[n * 2 + 1];
    }
    
    public int end(final String s) {
        return this.groups[this.getMatchedGroupIndex(s) * 2 + 1];
    }
    
    @Override
    public String group() {
        return this.group(0);
    }
    
    @Override
    public String group(final int n) {
        if (this.first < 0) {
            throw new IllegalStateException("No match found");
        }
        if (n < 0 || n > this.groupCount()) {
            throw new IndexOutOfBoundsException("No group " + n);
        }
        if (this.groups[n * 2] == -1 || this.groups[n * 2 + 1] == -1) {
            return null;
        }
        return this.getSubSequence(this.groups[n * 2], this.groups[n * 2 + 1]).toString();
    }
    
    public String group(final String s) {
        final int matchedGroupIndex = this.getMatchedGroupIndex(s);
        if (this.groups[matchedGroupIndex * 2] == -1 || this.groups[matchedGroupIndex * 2 + 1] == -1) {
            return null;
        }
        return this.getSubSequence(this.groups[matchedGroupIndex * 2], this.groups[matchedGroupIndex * 2 + 1]).toString();
    }
    
    @Override
    public int groupCount() {
        return this.parentPattern.capturingGroupCount - 1;
    }
    
    public boolean matches() {
        return this.match(this.from, 1);
    }
    
    public boolean find() {
        int n = this.last;
        if (n == this.first) {
            ++n;
        }
        if (n < this.from) {
            n = this.from;
        }
        if (n > this.to) {
            for (int i = 0; i < this.groups.length; ++i) {
                this.groups[i] = -1;
            }
            return false;
        }
        return this.search(n);
    }
    
    public boolean find(final int n) {
        final int textLength = this.getTextLength();
        if (n < 0 || n > textLength) {
            throw new IndexOutOfBoundsException("Illegal start index");
        }
        this.reset();
        return this.search(n);
    }
    
    public boolean lookingAt() {
        return this.match(this.from, 0);
    }
    
    public static String quoteReplacement(final String s) {
        if (s.indexOf(92) == -1 && s.indexOf(36) == -1) {
            return s;
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (char1 == '\\' || char1 == '$') {
                sb.append('\\');
            }
            sb.append(char1);
        }
        return sb.toString();
    }
    
    public Matcher appendReplacement(final StringBuffer sb, final String s) {
        if (this.first < 0) {
            throw new IllegalStateException("No match available");
        }
        int i = 0;
        final StringBuilder sb2 = new StringBuilder();
        while (i < s.length()) {
            final char char1 = s.charAt(i);
            if (char1 == '\\') {
                if (++i == s.length()) {
                    throw new IllegalArgumentException("character to be escaped is missing");
                }
                sb2.append(s.charAt(i));
                ++i;
            }
            else if (char1 == '$') {
                if (++i == s.length()) {
                    throw new IllegalArgumentException("Illegal group reference: group index is missing");
                }
                char c = s.charAt(i);
                int intValue;
                if (c == '{') {
                    ++i;
                    final StringBuilder sb3 = new StringBuilder();
                    while (i < s.length()) {
                        c = s.charAt(i);
                        if (!ASCII.isLower(c) && !ASCII.isUpper(c) && !ASCII.isDigit(c)) {
                            break;
                        }
                        sb3.append(c);
                        ++i;
                    }
                    if (sb3.length() == 0) {
                        throw new IllegalArgumentException("named capturing group has 0 length name");
                    }
                    if (c != '}') {
                        throw new IllegalArgumentException("named capturing group is missing trailing '}'");
                    }
                    final String string = sb3.toString();
                    if (ASCII.isDigit(string.charAt(0))) {
                        throw new IllegalArgumentException("capturing group name {" + string + "} starts with digit character");
                    }
                    if (!this.parentPattern.namedGroups().containsKey(string)) {
                        throw new IllegalArgumentException("No group with name {" + string + "}");
                    }
                    intValue = this.parentPattern.namedGroups().get(string);
                    ++i;
                }
                else {
                    intValue = c - '0';
                    if (intValue < 0 || intValue > 9) {
                        throw new IllegalArgumentException("Illegal group reference");
                    }
                    ++i;
                    int j = 0;
                    while (j == 0) {
                        if (i >= s.length()) {
                            break;
                        }
                        final char c2 = (char)(s.charAt(i) - '0');
                        if (c2 < '\0') {
                            break;
                        }
                        if (c2 > '\t') {
                            break;
                        }
                        final int n = intValue * 10 + c2;
                        if (this.groupCount() < n) {
                            j = 1;
                        }
                        else {
                            intValue = n;
                            ++i;
                        }
                    }
                }
                if (this.start(intValue) == -1 || this.end(intValue) == -1) {
                    continue;
                }
                sb2.append(this.text, this.start(intValue), this.end(intValue));
            }
            else {
                sb2.append(char1);
                ++i;
            }
        }
        sb.append(this.text, this.lastAppendPosition, this.first);
        sb.append((CharSequence)sb2);
        this.lastAppendPosition = this.last;
        return this;
    }
    
    public StringBuffer appendTail(final StringBuffer sb) {
        sb.append(this.text, this.lastAppendPosition, this.getTextLength());
        return sb;
    }
    
    public String replaceAll(final String s) {
        this.reset();
        if (this.find()) {
            final StringBuffer sb = new StringBuffer();
            do {
                this.appendReplacement(sb, s);
            } while (this.find());
            this.appendTail(sb);
            return sb.toString();
        }
        return this.text.toString();
    }
    
    public String replaceFirst(final String s) {
        if (s == null) {
            throw new NullPointerException("replacement");
        }
        this.reset();
        if (!this.find()) {
            return this.text.toString();
        }
        final StringBuffer sb = new StringBuffer();
        this.appendReplacement(sb, s);
        this.appendTail(sb);
        return sb.toString();
    }
    
    public Matcher region(final int from, final int to) {
        if (from < 0 || from > this.getTextLength()) {
            throw new IndexOutOfBoundsException("start");
        }
        if (to < 0 || to > this.getTextLength()) {
            throw new IndexOutOfBoundsException("end");
        }
        if (from > to) {
            throw new IndexOutOfBoundsException("start > end");
        }
        this.reset();
        this.from = from;
        this.to = to;
        return this;
    }
    
    public int regionStart() {
        return this.from;
    }
    
    public int regionEnd() {
        return this.to;
    }
    
    public boolean hasTransparentBounds() {
        return this.transparentBounds;
    }
    
    public Matcher useTransparentBounds(final boolean transparentBounds) {
        this.transparentBounds = transparentBounds;
        return this;
    }
    
    public boolean hasAnchoringBounds() {
        return this.anchoringBounds;
    }
    
    public Matcher useAnchoringBounds(final boolean anchoringBounds) {
        this.anchoringBounds = anchoringBounds;
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("java.util.regex.Matcher");
        sb.append("[pattern=" + this.pattern());
        sb.append(" region=");
        sb.append(this.regionStart() + "," + this.regionEnd());
        sb.append(" lastmatch=");
        if (this.first >= 0 && this.group() != null) {
            sb.append(this.group());
        }
        sb.append("]");
        return sb.toString();
    }
    
    public boolean hitEnd() {
        return this.hitEnd;
    }
    
    public boolean requireEnd() {
        return this.requireEnd;
    }
    
    boolean search(int first) {
        this.hitEnd = false;
        this.requireEnd = false;
        first = ((first < 0) ? 0 : first);
        this.first = first;
        this.oldLast = ((this.oldLast < 0) ? first : this.oldLast);
        for (int i = 0; i < this.groups.length; ++i) {
            this.groups[i] = -1;
        }
        this.acceptMode = 0;
        final boolean match = this.parentPattern.root.match(this, first, this.text);
        if (!match) {
            this.first = -1;
        }
        this.oldLast = this.last;
        return match;
    }
    
    boolean match(int first, final int acceptMode) {
        this.hitEnd = false;
        this.requireEnd = false;
        first = ((first < 0) ? 0 : first);
        this.first = first;
        this.oldLast = ((this.oldLast < 0) ? first : this.oldLast);
        for (int i = 0; i < this.groups.length; ++i) {
            this.groups[i] = -1;
        }
        this.acceptMode = acceptMode;
        final boolean match = this.parentPattern.matchRoot.match(this, first, this.text);
        if (!match) {
            this.first = -1;
        }
        this.oldLast = this.last;
        return match;
    }
    
    int getTextLength() {
        return this.text.length();
    }
    
    CharSequence getSubSequence(final int n, final int n2) {
        return this.text.subSequence(n, n2);
    }
    
    char charAt(final int n) {
        return this.text.charAt(n);
    }
    
    int getMatchedGroupIndex(final String s) {
        Objects.requireNonNull(s, "Group name");
        if (this.first < 0) {
            throw new IllegalStateException("No match found");
        }
        if (!this.parentPattern.namedGroups().containsKey(s)) {
            throw new IllegalArgumentException("No group with name <" + s + ">");
        }
        return this.parentPattern.namedGroups().get(s);
    }
}
