package java.text;

import java.util.*;

public class AttributedString
{
    private static final int ARRAY_SIZE_INCREMENT = 10;
    String text;
    int runArraySize;
    int runCount;
    int[] runStarts;
    Vector<AttributedCharacterIterator.Attribute>[] runAttributes;
    Vector<Object>[] runAttributeValues;
    
    AttributedString(final AttributedCharacterIterator[] array) {
        if (array == null) {
            throw new NullPointerException("Iterators must not be null");
        }
        if (array.length == 0) {
            this.text = "";
        }
        else {
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                this.appendContents(sb, array[i]);
            }
            this.text = sb.toString();
            if (this.text.length() > 0) {
                int n = 0;
                Object o = null;
                for (int j = 0; j < array.length; ++j) {
                    final AttributedCharacterIterator attributedCharacterIterator = array[j];
                    final int beginIndex = attributedCharacterIterator.getBeginIndex();
                    final int endIndex = attributedCharacterIterator.getEndIndex();
                    for (int k = beginIndex; k < endIndex; k = attributedCharacterIterator.getRunLimit()) {
                        attributedCharacterIterator.setIndex(k);
                        final Map<AttributedCharacterIterator.Attribute, Object> attributes = attributedCharacterIterator.getAttributes();
                        if (mapsDiffer((Map<Object, Object>)o, (Map<Object, Object>)attributes)) {
                            this.setAttributes(attributes, k - beginIndex + n);
                        }
                        o = attributes;
                    }
                    n += endIndex - beginIndex;
                }
            }
        }
    }
    
    public AttributedString(final String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
    }
    
    public AttributedString(final String text, final Map<? extends AttributedCharacterIterator.Attribute, ?> map) {
        if (text == null || map == null) {
            throw new NullPointerException();
        }
        this.text = text;
        if (text.length() != 0) {
            final int size = map.size();
            if (size > 0) {
                this.createRunAttributeDataVectors();
                final Vector vector = new Vector<AttributedCharacterIterator.Attribute>(size);
                final Vector vector2 = new Vector<Object>(size);
                this.runAttributes[0] = (Vector<AttributedCharacterIterator.Attribute>)vector;
                this.runAttributeValues[0] = (Vector<Object>)vector2;
                for (final Map.Entry<AttributedCharacterIterator.Attribute, V> entry : map.entrySet()) {
                    vector.addElement(entry.getKey());
                    vector2.addElement(entry.getValue());
                }
            }
            return;
        }
        if (map.isEmpty()) {
            return;
        }
        throw new IllegalArgumentException("Can't add attribute to 0-length text");
    }
    
    public AttributedString(final AttributedCharacterIterator attributedCharacterIterator) {
        this(attributedCharacterIterator, attributedCharacterIterator.getBeginIndex(), attributedCharacterIterator.getEndIndex(), null);
    }
    
    public AttributedString(final AttributedCharacterIterator attributedCharacterIterator, final int n, final int n2) {
        this(attributedCharacterIterator, n, n2, null);
    }
    
    public AttributedString(final AttributedCharacterIterator attributedCharacterIterator, final int index, final int n, final AttributedCharacterIterator.Attribute[] array) {
        if (attributedCharacterIterator == null) {
            throw new NullPointerException();
        }
        final int beginIndex = attributedCharacterIterator.getBeginIndex();
        final int endIndex = attributedCharacterIterator.getEndIndex();
        if (index < beginIndex || n > endIndex || index > n) {
            throw new IllegalArgumentException("Invalid substring range");
        }
        final StringBuffer sb = new StringBuffer();
        attributedCharacterIterator.setIndex(index);
        char c = attributedCharacterIterator.current();
        while (attributedCharacterIterator.getIndex() < n) {
            sb.append(c);
            c = attributedCharacterIterator.next();
        }
        this.text = sb.toString();
        if (index == n) {
            return;
        }
        final HashSet<Object> set = new HashSet<Object>();
        if (array == null) {
            set.addAll(attributedCharacterIterator.getAllAttributeKeys());
        }
        else {
            for (int i = 0; i < array.length; ++i) {
                set.add(array[i]);
            }
            set.retainAll(attributedCharacterIterator.getAllAttributeKeys());
        }
        if (set.isEmpty()) {
            return;
        }
        for (final AttributedCharacterIterator.Attribute attribute : set) {
            attributedCharacterIterator.setIndex(beginIndex);
            while (attributedCharacterIterator.getIndex() < n) {
                int runStart = attributedCharacterIterator.getRunStart(attribute);
                int runLimit = attributedCharacterIterator.getRunLimit(attribute);
                final Object attribute2 = attributedCharacterIterator.getAttribute(attribute);
                if (attribute2 != null) {
                    if (attribute2 instanceof Annotation) {
                        if (runStart >= index && runLimit <= n) {
                            this.addAttribute(attribute, attribute2, runStart - index, runLimit - index);
                        }
                        else if (runLimit > n) {
                            break;
                        }
                    }
                    else {
                        if (runStart >= n) {
                            break;
                        }
                        if (runLimit > index) {
                            if (runStart < index) {
                                runStart = index;
                            }
                            if (runLimit > n) {
                                runLimit = n;
                            }
                            if (runStart != runLimit) {
                                this.addAttribute(attribute, attribute2, runStart - index, runLimit - index);
                            }
                        }
                    }
                }
                attributedCharacterIterator.setIndex(runLimit);
            }
        }
    }
    
    public void addAttribute(final AttributedCharacterIterator.Attribute attribute, final Object o) {
        if (attribute == null) {
            throw new NullPointerException();
        }
        final int length = this.length();
        if (length == 0) {
            throw new IllegalArgumentException("Can't add attribute to 0-length text");
        }
        this.addAttributeImpl(attribute, o, 0, length);
    }
    
    public void addAttribute(final AttributedCharacterIterator.Attribute attribute, final Object o, final int n, final int n2) {
        if (attribute == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 > this.length() || n >= n2) {
            throw new IllegalArgumentException("Invalid substring range");
        }
        this.addAttributeImpl(attribute, o, n, n2);
    }
    
    public void addAttributes(final Map<? extends AttributedCharacterIterator.Attribute, ?> map, final int n, final int n2) {
        if (map == null) {
            throw new NullPointerException();
        }
        if (n < 0 || n2 > this.length() || n > n2) {
            throw new IllegalArgumentException("Invalid substring range");
        }
        if (n != n2) {
            if (this.runCount == 0) {
                this.createRunAttributeDataVectors();
            }
            final int ensureRunBreak = this.ensureRunBreak(n);
            final int ensureRunBreak2 = this.ensureRunBreak(n2);
            for (final Map.Entry<AttributedCharacterIterator.Attribute, V> entry : map.entrySet()) {
                this.addAttributeRunData(entry.getKey(), entry.getValue(), ensureRunBreak, ensureRunBreak2);
            }
            return;
        }
        if (map.isEmpty()) {
            return;
        }
        throw new IllegalArgumentException("Can't add attribute to 0-length text");
    }
    
    private synchronized void addAttributeImpl(final AttributedCharacterIterator.Attribute attribute, final Object o, final int n, final int n2) {
        if (this.runCount == 0) {
            this.createRunAttributeDataVectors();
        }
        this.addAttributeRunData(attribute, o, this.ensureRunBreak(n), this.ensureRunBreak(n2));
    }
    
    private final void createRunAttributeDataVectors() {
        final int[] runStarts = new int[10];
        final Vector[] runAttributes = new Vector[10];
        final Vector[] runAttributeValues = new Vector[10];
        this.runStarts = runStarts;
        this.runAttributes = (Vector<AttributedCharacterIterator.Attribute>[])runAttributes;
        this.runAttributeValues = (Vector<Object>[])runAttributeValues;
        this.runArraySize = 10;
        this.runCount = 1;
    }
    
    private final int ensureRunBreak(final int n) {
        return this.ensureRunBreak(n, true);
    }
    
    private final int ensureRunBreak(final int n, final boolean b) {
        if (n == this.length()) {
            return this.runCount;
        }
        int n2;
        for (n2 = 0; n2 < this.runCount && this.runStarts[n2] < n; ++n2) {}
        if (n2 < this.runCount && this.runStarts[n2] == n) {
            return n2;
        }
        if (this.runCount == this.runArraySize) {
            final int runArraySize = this.runArraySize + 10;
            final int[] runStarts = new int[runArraySize];
            final Vector[] runAttributes = new Vector[runArraySize];
            final Vector[] runAttributeValues = new Vector[runArraySize];
            for (int i = 0; i < this.runArraySize; ++i) {
                runStarts[i] = this.runStarts[i];
                runAttributes[i] = this.runAttributes[i];
                runAttributeValues[i] = this.runAttributeValues[i];
            }
            this.runStarts = runStarts;
            this.runAttributes = (Vector<AttributedCharacterIterator.Attribute>[])runAttributes;
            this.runAttributeValues = (Vector<Object>[])runAttributeValues;
            this.runArraySize = runArraySize;
        }
        Vector<AttributedCharacterIterator.Attribute> vector = null;
        Vector<Object> vector2 = null;
        if (b) {
            final Vector<AttributedCharacterIterator.Attribute> vector3 = this.runAttributes[n2 - 1];
            final Vector<Object> vector4 = this.runAttributeValues[n2 - 1];
            if (vector3 != null) {
                vector = new Vector<AttributedCharacterIterator.Attribute>(vector3);
            }
            if (vector4 != null) {
                vector2 = new Vector<Object>(vector4);
            }
        }
        ++this.runCount;
        for (int j = this.runCount - 1; j > n2; --j) {
            this.runStarts[j] = this.runStarts[j - 1];
            this.runAttributes[j] = this.runAttributes[j - 1];
            this.runAttributeValues[j] = this.runAttributeValues[j - 1];
        }
        this.runStarts[n2] = n;
        this.runAttributes[n2] = vector;
        this.runAttributeValues[n2] = vector2;
        return n2;
    }
    
    private void addAttributeRunData(final AttributedCharacterIterator.Attribute attribute, final Object o, final int n, final int n2) {
        for (int i = n; i < n2; ++i) {
            int index = -1;
            if (this.runAttributes[i] == null) {
                final Vector<AttributedCharacterIterator.Attribute> vector = new Vector<AttributedCharacterIterator.Attribute>();
                final Vector<Object> vector2 = new Vector<Object>();
                this.runAttributes[i] = vector;
                this.runAttributeValues[i] = vector2;
            }
            else {
                index = this.runAttributes[i].indexOf(attribute);
            }
            if (index == -1) {
                final int size = this.runAttributes[i].size();
                this.runAttributes[i].addElement(attribute);
                try {
                    this.runAttributeValues[i].addElement(o);
                }
                catch (Exception ex) {
                    this.runAttributes[i].setSize(size);
                    this.runAttributeValues[i].setSize(size);
                }
            }
            else {
                this.runAttributeValues[i].set(index, o);
            }
        }
    }
    
    public AttributedCharacterIterator getIterator() {
        return this.getIterator(null, 0, this.length());
    }
    
    public AttributedCharacterIterator getIterator(final AttributedCharacterIterator.Attribute[] array) {
        return this.getIterator(array, 0, this.length());
    }
    
    public AttributedCharacterIterator getIterator(final AttributedCharacterIterator.Attribute[] array, final int n, final int n2) {
        return new AttributedStringIterator(array, n, n2);
    }
    
    int length() {
        return this.text.length();
    }
    
    private char charAt(final int n) {
        return this.text.charAt(n);
    }
    
    private synchronized Object getAttribute(final AttributedCharacterIterator.Attribute attribute, final int n) {
        final Vector<AttributedCharacterIterator.Attribute> vector = this.runAttributes[n];
        final Vector<Object> vector2 = this.runAttributeValues[n];
        if (vector == null) {
            return null;
        }
        final int index = vector.indexOf(attribute);
        if (index != -1) {
            return vector2.elementAt(index);
        }
        return null;
    }
    
    private Object getAttributeCheckRange(final AttributedCharacterIterator.Attribute attribute, int n, final int n2, final int n3) {
        final Object attribute2 = this.getAttribute(attribute, n);
        if (attribute2 instanceof Annotation) {
            if (n2 > 0) {
                int n4;
                int n5;
                for (n4 = n, n5 = this.runStarts[n4]; n5 >= n2 && valuesMatch(attribute2, this.getAttribute(attribute, n4 - 1)); --n4, n5 = this.runStarts[n4]) {}
                if (n5 < n2) {
                    return null;
                }
            }
            final int length = this.length();
            if (n3 < length) {
                int n6;
                for (n6 = ((n < this.runCount - 1) ? this.runStarts[n + 1] : length); n6 <= n3 && valuesMatch(attribute2, this.getAttribute(attribute, n + 1)); n6 = ((++n < this.runCount - 1) ? this.runStarts[n + 1] : length)) {}
                if (n6 > n3) {
                    return null;
                }
            }
        }
        return attribute2;
    }
    
    private boolean attributeValuesMatch(final Set<? extends AttributedCharacterIterator.Attribute> set, final int n, final int n2) {
        for (final AttributedCharacterIterator.Attribute attribute : set) {
            if (!valuesMatch(this.getAttribute(attribute, n), this.getAttribute(attribute, n2))) {
                return false;
            }
        }
        return true;
    }
    
    private static final boolean valuesMatch(final Object o, final Object o2) {
        if (o == null) {
            return o2 == null;
        }
        return o.equals(o2);
    }
    
    private final void appendContents(final StringBuffer sb, final CharacterIterator characterIterator) {
        int i = characterIterator.getBeginIndex();
        while (i < characterIterator.getEndIndex()) {
            characterIterator.setIndex(i++);
            sb.append(characterIterator.current());
        }
    }
    
    private void setAttributes(final Map<AttributedCharacterIterator.Attribute, Object> map, final int n) {
        if (this.runCount == 0) {
            this.createRunAttributeDataVectors();
        }
        final int ensureRunBreak = this.ensureRunBreak(n, false);
        final int size;
        if (map != null && (size = map.size()) > 0) {
            final Vector vector = new Vector<AttributedCharacterIterator.Attribute>(size);
            final Vector vector2 = new Vector<Object>(size);
            for (final Map.Entry<AttributedCharacterIterator.Attribute, Object> entry : map.entrySet()) {
                vector.add(entry.getKey());
                vector2.add(entry.getValue());
            }
            this.runAttributes[ensureRunBreak] = (Vector<AttributedCharacterIterator.Attribute>)vector;
            this.runAttributeValues[ensureRunBreak] = (Vector<Object>)vector2;
        }
    }
    
    private static <K, V> boolean mapsDiffer(final Map<K, V> map, final Map<K, V> map2) {
        if (map == null) {
            return map2 != null && map2.size() > 0;
        }
        return !map.equals(map2);
    }
    
    private final class AttributeMap extends AbstractMap<AttributedCharacterIterator.Attribute, Object>
    {
        int runIndex;
        int beginIndex;
        int endIndex;
        
        AttributeMap(final int runIndex, final int beginIndex, final int endIndex) {
            this.runIndex = runIndex;
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
        }
        
        @Override
        public Set<Map.Entry<AttributedCharacterIterator.Attribute, Object>> entrySet() {
            final HashSet<Map.Entry<AttributedCharacterIterator.Attribute, Object>> set = new HashSet<Map.Entry<AttributedCharacterIterator.Attribute, Object>>();
            synchronized (AttributedString.this) {
                for (int size = AttributedString.this.runAttributes[this.runIndex].size(), i = 0; i < size; ++i) {
                    final AttributedCharacterIterator.Attribute attribute = AttributedString.this.runAttributes[this.runIndex].get(i);
                    Object o = AttributedString.this.runAttributeValues[this.runIndex].get(i);
                    if (o instanceof Annotation) {
                        o = AttributedString.this.getAttributeCheckRange(attribute, this.runIndex, this.beginIndex, this.endIndex);
                        if (o == null) {
                            continue;
                        }
                    }
                    set.add(new AttributeEntry(attribute, o));
                }
            }
            return set;
        }
        
        @Override
        public Object get(final Object o) {
            return AttributedString.this.getAttributeCheckRange((AttributedCharacterIterator.Attribute)o, this.runIndex, this.beginIndex, this.endIndex);
        }
    }
    
    private final class AttributedStringIterator implements AttributedCharacterIterator
    {
        private int beginIndex;
        private int endIndex;
        private Attribute[] relevantAttributes;
        private int currentIndex;
        private int currentRunIndex;
        private int currentRunStart;
        private int currentRunLimit;
        
        AttributedStringIterator(final Attribute[] array, final int n, final int endIndex) {
            if (n < 0 || n > endIndex || endIndex > AttributedString.this.length()) {
                throw new IllegalArgumentException("Invalid substring range");
            }
            this.beginIndex = n;
            this.endIndex = endIndex;
            this.currentIndex = n;
            this.updateRunInfo();
            if (array != null) {
                this.relevantAttributes = array.clone();
            }
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof AttributedStringIterator)) {
                return false;
            }
            final AttributedStringIterator attributedStringIterator = (AttributedStringIterator)o;
            return AttributedString.this == attributedStringIterator.getString() && this.currentIndex == attributedStringIterator.currentIndex && this.beginIndex == attributedStringIterator.beginIndex && this.endIndex == attributedStringIterator.endIndex;
        }
        
        @Override
        public int hashCode() {
            return AttributedString.this.text.hashCode() ^ this.currentIndex ^ this.beginIndex ^ this.endIndex;
        }
        
        @Override
        public Object clone() {
            try {
                return super.clone();
            }
            catch (CloneNotSupportedException ex) {
                throw new InternalError(ex);
            }
        }
        
        @Override
        public char first() {
            return this.internalSetIndex(this.beginIndex);
        }
        
        @Override
        public char last() {
            if (this.endIndex == this.beginIndex) {
                return this.internalSetIndex(this.endIndex);
            }
            return this.internalSetIndex(this.endIndex - 1);
        }
        
        @Override
        public char current() {
            if (this.currentIndex == this.endIndex) {
                return '\uffff';
            }
            return AttributedString.this.charAt(this.currentIndex);
        }
        
        @Override
        public char next() {
            if (this.currentIndex < this.endIndex) {
                return this.internalSetIndex(this.currentIndex + 1);
            }
            return '\uffff';
        }
        
        @Override
        public char previous() {
            if (this.currentIndex > this.beginIndex) {
                return this.internalSetIndex(this.currentIndex - 1);
            }
            return '\uffff';
        }
        
        @Override
        public char setIndex(final int n) {
            if (n < this.beginIndex || n > this.endIndex) {
                throw new IllegalArgumentException("Invalid index");
            }
            return this.internalSetIndex(n);
        }
        
        @Override
        public int getBeginIndex() {
            return this.beginIndex;
        }
        
        @Override
        public int getEndIndex() {
            return this.endIndex;
        }
        
        @Override
        public int getIndex() {
            return this.currentIndex;
        }
        
        @Override
        public int getRunStart() {
            return this.currentRunStart;
        }
        
        @Override
        public int getRunStart(final Attribute attribute) {
            if (this.currentRunStart == this.beginIndex || this.currentRunIndex == -1) {
                return this.currentRunStart;
            }
            final Object attribute2 = this.getAttribute(attribute);
            int n = this.currentRunStart;
            for (int currentRunIndex = this.currentRunIndex; n > this.beginIndex && valuesMatch(attribute2, AttributedString.this.getAttribute(attribute, currentRunIndex - 1)); --currentRunIndex, n = AttributedString.this.runStarts[currentRunIndex]) {}
            if (n < this.beginIndex) {
                n = this.beginIndex;
            }
            return n;
        }
        
        @Override
        public int getRunStart(final Set<? extends Attribute> set) {
            if (this.currentRunStart == this.beginIndex || this.currentRunIndex == -1) {
                return this.currentRunStart;
            }
            int n = this.currentRunStart;
            for (int currentRunIndex = this.currentRunIndex; n > this.beginIndex && AttributedString.this.attributeValuesMatch(set, this.currentRunIndex, currentRunIndex - 1); --currentRunIndex, n = AttributedString.this.runStarts[currentRunIndex]) {}
            if (n < this.beginIndex) {
                n = this.beginIndex;
            }
            return n;
        }
        
        @Override
        public int getRunLimit() {
            return this.currentRunLimit;
        }
        
        @Override
        public int getRunLimit(final Attribute attribute) {
            if (this.currentRunLimit == this.endIndex || this.currentRunIndex == -1) {
                return this.currentRunLimit;
            }
            final Object attribute2 = this.getAttribute(attribute);
            int n = this.currentRunLimit;
            for (int currentRunIndex = this.currentRunIndex; n < this.endIndex && valuesMatch(attribute2, AttributedString.this.getAttribute(attribute, currentRunIndex + 1)); n = ((++currentRunIndex < AttributedString.this.runCount - 1) ? AttributedString.this.runStarts[currentRunIndex + 1] : this.endIndex)) {}
            if (n > this.endIndex) {
                n = this.endIndex;
            }
            return n;
        }
        
        @Override
        public int getRunLimit(final Set<? extends Attribute> set) {
            if (this.currentRunLimit == this.endIndex || this.currentRunIndex == -1) {
                return this.currentRunLimit;
            }
            int n = this.currentRunLimit;
            for (int currentRunIndex = this.currentRunIndex; n < this.endIndex && AttributedString.this.attributeValuesMatch(set, this.currentRunIndex, currentRunIndex + 1); n = ((++currentRunIndex < AttributedString.this.runCount - 1) ? AttributedString.this.runStarts[currentRunIndex + 1] : this.endIndex)) {}
            if (n > this.endIndex) {
                n = this.endIndex;
            }
            return n;
        }
        
        @Override
        public Map<Attribute, Object> getAttributes() {
            if (AttributedString.this.runAttributes == null || this.currentRunIndex == -1 || AttributedString.this.runAttributes[this.currentRunIndex] == null) {
                return new Hashtable<Attribute, Object>();
            }
            return new AttributeMap(this.currentRunIndex, this.beginIndex, this.endIndex);
        }
        
        @Override
        public Set<Attribute> getAllAttributeKeys() {
            if (AttributedString.this.runAttributes == null) {
                return new HashSet<Attribute>();
            }
            synchronized (AttributedString.this) {
                final HashSet<Attribute> set = new HashSet<Attribute>();
                for (int i = 0; i < AttributedString.this.runCount; ++i) {
                    if (AttributedString.this.runStarts[i] < this.endIndex && (i == AttributedString.this.runCount - 1 || AttributedString.this.runStarts[i + 1] > this.beginIndex)) {
                        final Vector<Attribute> vector = AttributedString.this.runAttributes[i];
                        if (vector != null) {
                            int size = vector.size();
                            while (size-- > 0) {
                                set.add(vector.get(size));
                            }
                        }
                    }
                }
                return set;
            }
        }
        
        @Override
        public Object getAttribute(final Attribute attribute) {
            final int currentRunIndex = this.currentRunIndex;
            if (currentRunIndex < 0) {
                return null;
            }
            return AttributedString.this.getAttributeCheckRange(attribute, currentRunIndex, this.beginIndex, this.endIndex);
        }
        
        private AttributedString getString() {
            return AttributedString.this;
        }
        
        private char internalSetIndex(final int currentIndex) {
            this.currentIndex = currentIndex;
            if (currentIndex < this.currentRunStart || currentIndex >= this.currentRunLimit) {
                this.updateRunInfo();
            }
            if (this.currentIndex == this.endIndex) {
                return '\uffff';
            }
            return AttributedString.this.charAt(currentIndex);
        }
        
        private void updateRunInfo() {
            if (this.currentIndex == this.endIndex) {
                final int endIndex = this.endIndex;
                this.currentRunLimit = endIndex;
                this.currentRunStart = endIndex;
                this.currentRunIndex = -1;
            }
            else {
                synchronized (AttributedString.this) {
                    int currentRunIndex;
                    for (currentRunIndex = -1; currentRunIndex < AttributedString.this.runCount - 1 && AttributedString.this.runStarts[currentRunIndex + 1] <= this.currentIndex; ++currentRunIndex) {}
                    if ((this.currentRunIndex = currentRunIndex) >= 0) {
                        this.currentRunStart = AttributedString.this.runStarts[currentRunIndex];
                        if (this.currentRunStart < this.beginIndex) {
                            this.currentRunStart = this.beginIndex;
                        }
                    }
                    else {
                        this.currentRunStart = this.beginIndex;
                    }
                    if (currentRunIndex < AttributedString.this.runCount - 1) {
                        this.currentRunLimit = AttributedString.this.runStarts[currentRunIndex + 1];
                        if (this.currentRunLimit > this.endIndex) {
                            this.currentRunLimit = this.endIndex;
                        }
                    }
                    else {
                        this.currentRunLimit = this.endIndex;
                    }
                }
            }
        }
    }
}
