package java.sql;

public class DataTruncation extends SQLWarning
{
    private int index;
    private boolean parameter;
    private boolean read;
    private int dataSize;
    private int transferSize;
    private static final long serialVersionUID = 6464298989504059473L;
    
    public DataTruncation(final int index, final boolean parameter, final boolean read, final int dataSize, final int transferSize) {
        super("Data truncation", read ? "01004" : "22001");
        this.index = index;
        this.parameter = parameter;
        this.read = read;
        this.dataSize = dataSize;
        this.transferSize = transferSize;
    }
    
    public DataTruncation(final int index, final boolean parameter, final boolean read, final int dataSize, final int transferSize, final Throwable t) {
        super("Data truncation", read ? "01004" : "22001", t);
        this.index = index;
        this.parameter = parameter;
        this.read = read;
        this.dataSize = dataSize;
        this.transferSize = transferSize;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public boolean getParameter() {
        return this.parameter;
    }
    
    public boolean getRead() {
        return this.read;
    }
    
    public int getDataSize() {
        return this.dataSize;
    }
    
    public int getTransferSize() {
        return this.transferSize;
    }
}
