package java.text;

public class RuleBasedCollator extends Collator
{
    static final int CHARINDEX = 1879048192;
    static final int EXPANDCHARINDEX = 2113929216;
    static final int CONTRACTCHARINDEX = 2130706432;
    static final int UNMAPPED = -1;
    private static final int COLLATIONKEYOFFSET = 1;
    private RBCollationTables tables;
    private StringBuffer primResult;
    private StringBuffer secResult;
    private StringBuffer terResult;
    private CollationElementIterator sourceCursor;
    private CollationElementIterator targetCursor;
    
    public RuleBasedCollator(final String s) throws ParseException {
        this(s, 1);
    }
    
    RuleBasedCollator(final String s, final int decomposition) throws ParseException {
        this.tables = null;
        this.primResult = null;
        this.secResult = null;
        this.terResult = null;
        this.sourceCursor = null;
        this.targetCursor = null;
        this.setStrength(2);
        this.setDecomposition(decomposition);
        this.tables = new RBCollationTables(s, decomposition);
    }
    
    private RuleBasedCollator(final RuleBasedCollator ruleBasedCollator) {
        this.tables = null;
        this.primResult = null;
        this.secResult = null;
        this.terResult = null;
        this.sourceCursor = null;
        this.targetCursor = null;
        this.setStrength(ruleBasedCollator.getStrength());
        this.setDecomposition(ruleBasedCollator.getDecomposition());
        this.tables = ruleBasedCollator.tables;
    }
    
    public String getRules() {
        return this.tables.getRules();
    }
    
    public CollationElementIterator getCollationElementIterator(final String s) {
        return new CollationElementIterator(s, this);
    }
    
    public CollationElementIterator getCollationElementIterator(final CharacterIterator characterIterator) {
        return new CollationElementIterator(characterIterator, this);
    }
    
    @Override
    public synchronized int compare(final String text, final String text2) {
        if (text == null || text2 == null) {
            throw new NullPointerException();
        }
        int n = 0;
        if (this.sourceCursor == null) {
            this.sourceCursor = this.getCollationElementIterator(text);
        }
        else {
            this.sourceCursor.setText(text);
        }
        if (this.targetCursor == null) {
            this.targetCursor = this.getCollationElementIterator(text2);
        }
        else {
            this.targetCursor.setText(text2);
        }
        int n2 = 0;
        int n3 = 0;
        int n4;
        final boolean b = (n4 = ((this.getStrength() >= 1) ? 1 : 0)) != 0;
        int n5 = (this.getStrength() >= 2) ? 1 : 0;
        int n6 = 1;
        int n7 = 1;
        while (true) {
            if (n6 != 0) {
                n2 = this.sourceCursor.next();
            }
            else {
                n6 = 1;
            }
            if (n7 != 0) {
                n3 = this.targetCursor.next();
            }
            else {
                n7 = 1;
            }
            if (n2 != -1 && n3 != -1) {
                final int primaryOrder = CollationElementIterator.primaryOrder(n2);
                final int primaryOrder2 = CollationElementIterator.primaryOrder(n3);
                if (n2 == n3) {
                    if (!this.tables.isFrenchSec() || primaryOrder == 0 || n4 != 0) {
                        continue;
                    }
                    n4 = (b ? 1 : 0);
                    n5 = 0;
                }
                else if (primaryOrder != primaryOrder2) {
                    if (n2 == 0) {
                        n7 = 0;
                    }
                    else if (n3 == 0) {
                        n6 = 0;
                    }
                    else if (primaryOrder == 0) {
                        if (n4 != 0) {
                            n = 1;
                            n4 = 0;
                        }
                        n7 = 0;
                    }
                    else if (primaryOrder2 == 0) {
                        if (n4 != 0) {
                            n = -1;
                            n4 = 0;
                        }
                        n6 = 0;
                    }
                    else {
                        if (primaryOrder < primaryOrder2) {
                            return -1;
                        }
                        return 1;
                    }
                }
                else {
                    if (n4 == 0) {
                        continue;
                    }
                    final short secondaryOrder = CollationElementIterator.secondaryOrder(n2);
                    final short secondaryOrder2 = CollationElementIterator.secondaryOrder(n3);
                    if (secondaryOrder != secondaryOrder2) {
                        n = ((secondaryOrder < secondaryOrder2) ? -1 : 1);
                        n4 = 0;
                    }
                    else {
                        if (n5 == 0) {
                            continue;
                        }
                        final short tertiaryOrder = CollationElementIterator.tertiaryOrder(n2);
                        final short tertiaryOrder2 = CollationElementIterator.tertiaryOrder(n3);
                        if (tertiaryOrder == tertiaryOrder2) {
                            continue;
                        }
                        n = ((tertiaryOrder < tertiaryOrder2) ? -1 : 1);
                        n5 = 0;
                    }
                }
            }
            else {
                Label_0495: {
                    if (n2 != -1) {
                        while (CollationElementIterator.primaryOrder(n2) == 0) {
                            if (CollationElementIterator.secondaryOrder(n2) != 0 && n4 != 0) {
                                n = 1;
                                n4 = 0;
                            }
                            if ((n2 = this.sourceCursor.next()) == -1) {
                                break Label_0495;
                            }
                        }
                        return 1;
                    }
                    if (n3 != -1) {
                        while (CollationElementIterator.primaryOrder(n3) == 0) {
                            if (CollationElementIterator.secondaryOrder(n3) != 0 && n4 != 0) {
                                n = -1;
                                n4 = 0;
                            }
                            if ((n3 = this.targetCursor.next()) == -1) {
                                break Label_0495;
                            }
                        }
                        return -1;
                    }
                }
                if (n == 0 && this.getStrength() == 3) {
                    final int decomposition = this.getDecomposition();
                    Normalizer.Form form;
                    if (decomposition == 1) {
                        form = Normalizer.Form.NFD;
                    }
                    else {
                        if (decomposition != 2) {
                            return text.compareTo(text2);
                        }
                        form = Normalizer.Form.NFKD;
                    }
                    return Normalizer.normalize(text, form).compareTo(Normalizer.normalize(text2, form));
                }
                return n;
            }
        }
    }
    
    @Override
    public synchronized CollationKey getCollationKey(final String text) {
        if (text == null) {
            return null;
        }
        if (this.primResult == null) {
            this.primResult = new StringBuffer();
            this.secResult = new StringBuffer();
            this.terResult = new StringBuffer();
        }
        else {
            this.primResult.setLength(0);
            this.secResult.setLength(0);
            this.terResult.setLength(0);
        }
        final boolean b = this.getStrength() >= 1;
        final boolean b2 = this.getStrength() >= 2;
        int length = 0;
        if (this.sourceCursor == null) {
            this.sourceCursor = this.getCollationElementIterator(text);
        }
        else {
            this.sourceCursor.setText(text);
        }
        int next;
        while ((next = this.sourceCursor.next()) != -1) {
            final short secondaryOrder = CollationElementIterator.secondaryOrder(next);
            final short tertiaryOrder = CollationElementIterator.tertiaryOrder(next);
            if (!CollationElementIterator.isIgnorable(next)) {
                this.primResult.append((char)(CollationElementIterator.primaryOrder(next) + 1));
                if (b) {
                    if (this.tables.isFrenchSec() && length < this.secResult.length()) {
                        RBCollationTables.reverse(this.secResult, length, this.secResult.length());
                    }
                    this.secResult.append((char)(secondaryOrder + 1));
                    length = this.secResult.length();
                }
                if (!b2) {
                    continue;
                }
                this.terResult.append((char)(tertiaryOrder + 1));
            }
            else {
                if (b && secondaryOrder != 0) {
                    this.secResult.append((char)(secondaryOrder + this.tables.getMaxSecOrder() + 1));
                }
                if (!b2 || tertiaryOrder == 0) {
                    continue;
                }
                this.terResult.append((char)(tertiaryOrder + this.tables.getMaxTerOrder() + 1));
            }
        }
        if (this.tables.isFrenchSec()) {
            if (length < this.secResult.length()) {
                RBCollationTables.reverse(this.secResult, length, this.secResult.length());
            }
            RBCollationTables.reverse(this.secResult, 0, this.secResult.length());
        }
        this.primResult.append('\0');
        this.secResult.append('\0');
        this.secResult.append(this.terResult.toString());
        this.primResult.append(this.secResult.toString());
        if (this.getStrength() == 3) {
            this.primResult.append('\0');
            final int decomposition = this.getDecomposition();
            if (decomposition == 1) {
                this.primResult.append(Normalizer.normalize(text, Normalizer.Form.NFD));
            }
            else if (decomposition == 2) {
                this.primResult.append(Normalizer.normalize(text, Normalizer.Form.NFKD));
            }
            else {
                this.primResult.append(text);
            }
        }
        return new RuleBasedCollationKey(text, this.primResult.toString());
    }
    
    @Override
    public Object clone() {
        if (this.getClass() == RuleBasedCollator.class) {
            return new RuleBasedCollator(this);
        }
        final RuleBasedCollator ruleBasedCollator = (RuleBasedCollator)super.clone();
        ruleBasedCollator.primResult = null;
        ruleBasedCollator.secResult = null;
        ruleBasedCollator.terResult = null;
        ruleBasedCollator.sourceCursor = null;
        ruleBasedCollator.targetCursor = null;
        return ruleBasedCollator;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && super.equals(o) && this.getRules().equals(((RuleBasedCollator)o).getRules());
    }
    
    @Override
    public int hashCode() {
        return this.getRules().hashCode();
    }
    
    RBCollationTables getTables() {
        return this.tables;
    }
}
