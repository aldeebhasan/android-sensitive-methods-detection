package java.sql;

import java.util.concurrent.atomic.*;
import java.util.*;

public class SQLException extends Exception implements Iterable<Throwable>
{
    private String SQLState;
    private int vendorCode;
    private volatile SQLException next;
    private static final AtomicReferenceFieldUpdater<SQLException, SQLException> nextUpdater;
    private static final long serialVersionUID = 2135244094396331484L;
    
    public SQLException(final String s, final String sqlState, final int vendorCode) {
        super(s);
        this.SQLState = sqlState;
        this.vendorCode = vendorCode;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            DriverManager.println("SQLState(" + sqlState + ") vendor code(" + vendorCode + ")");
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }
    
    public SQLException(final String s, final String sqlState) {
        super(s);
        this.SQLState = sqlState;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
            DriverManager.println("SQLException: SQLState(" + sqlState + ")");
        }
    }
    
    public SQLException(final String s) {
        super(s);
        this.SQLState = null;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }
    
    public SQLException() {
        this.SQLState = null;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }
    
    public SQLException(final Throwable t) {
        super(t);
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }
    
    public SQLException(final String s, final Throwable t) {
        super(s, t);
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }
    
    public SQLException(final String s, final String sqlState, final Throwable t) {
        super(s, t);
        this.SQLState = sqlState;
        this.vendorCode = 0;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            this.printStackTrace(DriverManager.getLogWriter());
            DriverManager.println("SQLState(" + this.SQLState + ")");
        }
    }
    
    public SQLException(final String s, final String sqlState, final int vendorCode, final Throwable t) {
        super(s, t);
        this.SQLState = sqlState;
        this.vendorCode = vendorCode;
        if (!(this instanceof SQLWarning) && DriverManager.getLogWriter() != null) {
            DriverManager.println("SQLState(" + this.SQLState + ") vendor code(" + vendorCode + ")");
            this.printStackTrace(DriverManager.getLogWriter());
        }
    }
    
    public String getSQLState() {
        return this.SQLState;
    }
    
    public int getErrorCode() {
        return this.vendorCode;
    }
    
    public SQLException getNextException() {
        return this.next;
    }
    
    public void setNextException(final SQLException ex) {
        SQLException next = this;
        while (true) {
            final SQLException next2 = next.next;
            if (next2 != null) {
                next = next2;
            }
            else {
                if (SQLException.nextUpdater.compareAndSet(next, null, ex)) {
                    break;
                }
                next = next.next;
            }
        }
    }
    
    @Override
    public Iterator<Throwable> iterator() {
        return new Iterator<Throwable>() {
            SQLException firstException = SQLException.this;
            SQLException nextException = this.firstException.getNextException();
            Throwable cause = this.firstException.getCause();
            
            @Override
            public boolean hasNext() {
                return this.firstException != null || this.nextException != null || this.cause != null;
            }
            
            @Override
            public Throwable next() {
                Throwable t;
                if (this.firstException != null) {
                    t = this.firstException;
                    this.firstException = null;
                }
                else if (this.cause != null) {
                    t = this.cause;
                    this.cause = this.cause.getCause();
                }
                else {
                    if (this.nextException == null) {
                        throw new NoSuchElementException();
                    }
                    t = this.nextException;
                    this.cause = this.nextException.getCause();
                    this.nextException = this.nextException.getNextException();
                }
                return t;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    static {
        nextUpdater = AtomicReferenceFieldUpdater.newUpdater(SQLException.class, SQLException.class, "next");
    }
}
