package java.beans;

public class IndexedPropertyChangeEvent extends PropertyChangeEvent
{
    private static final long serialVersionUID = -320227448495806870L;
    private int index;
    
    public IndexedPropertyChangeEvent(final Object o, final String s, final Object o2, final Object o3, final int index) {
        super(o, s, o2, o3);
        this.index = index;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    @Override
    void appendTo(final StringBuilder sb) {
        sb.append("; index=").append(this.getIndex());
    }
}
