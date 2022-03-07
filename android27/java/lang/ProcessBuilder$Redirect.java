package java.lang;

import java.io.*;

public abstract static class Redirect
{
    public static final Redirect PIPE;
    public static final Redirect INHERIT;
    
    public abstract Type type();
    
    public File file() {
        return null;
    }
    
    boolean append() {
        throw new UnsupportedOperationException();
    }
    
    public static Redirect from(final File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new Redirect() {
            @Override
            public Type type() {
                return Type.READ;
            }
            
            @Override
            public File file() {
                return file;
            }
            
            @Override
            public String toString() {
                return "redirect to read from file \"" + file + "\"";
            }
        };
    }
    
    public static Redirect to(final File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new Redirect() {
            @Override
            public Type type() {
                return Type.WRITE;
            }
            
            @Override
            public File file() {
                return file;
            }
            
            @Override
            public String toString() {
                return "redirect to write to file \"" + file + "\"";
            }
            
            @Override
            boolean append() {
                return false;
            }
        };
    }
    
    public static Redirect appendTo(final File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        return new Redirect() {
            @Override
            public Type type() {
                return Type.APPEND;
            }
            
            @Override
            public File file() {
                return file;
            }
            
            @Override
            public String toString() {
                return "redirect to append to file \"" + file + "\"";
            }
            
            @Override
            boolean append() {
                return true;
            }
        };
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Redirect)) {
            return false;
        }
        final Redirect redirect = (Redirect)o;
        if (redirect.type() != this.type()) {
            return false;
        }
        assert this.file() != null;
        return this.file().equals(redirect.file());
    }
    
    @Override
    public int hashCode() {
        final File file = this.file();
        if (file == null) {
            return super.hashCode();
        }
        return file.hashCode();
    }
    
    static {
        PIPE = new Redirect() {
            @Override
            public Type type() {
                return Type.PIPE;
            }
            
            @Override
            public String toString() {
                return this.type().toString();
            }
        };
        INHERIT = new Redirect() {
            @Override
            public Type type() {
                return Type.INHERIT;
            }
            
            @Override
            public String toString() {
                return this.type().toString();
            }
        };
    }
    
    public enum Type
    {
        PIPE, 
        INHERIT, 
        READ, 
        WRITE, 
        APPEND;
    }
}
