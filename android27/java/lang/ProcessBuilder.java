package java.lang;

import java.security.*;
import java.util.*;
import java.io.*;

public final class ProcessBuilder
{
    private List<String> command;
    private File directory;
    private Map<String, String> environment;
    private boolean redirectErrorStream;
    private Redirect[] redirects;
    
    public ProcessBuilder(final List<String> command) {
        if (command == null) {
            throw new NullPointerException();
        }
        this.command = command;
    }
    
    public ProcessBuilder(final String... array) {
        this.command = new ArrayList<String>(array.length);
        for (int length = array.length, i = 0; i < length; ++i) {
            this.command.add(array[i]);
        }
    }
    
    public ProcessBuilder command(final List<String> command) {
        if (command == null) {
            throw new NullPointerException();
        }
        this.command = command;
        return this;
    }
    
    public ProcessBuilder command(final String... array) {
        this.command = new ArrayList<String>(array.length);
        for (int length = array.length, i = 0; i < length; ++i) {
            this.command.add(array[i]);
        }
        return this;
    }
    
    public List<String> command() {
        return this.command;
    }
    
    public Map<String, String> environment() {
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getenv.*"));
        }
        if (this.environment == null) {
            this.environment = ProcessEnvironment.environment();
        }
        assert this.environment != null;
        return this.environment;
    }
    
    ProcessBuilder environment(final String[] array) {
        assert this.environment == null;
        if (array != null) {
            this.environment = ProcessEnvironment.emptyEnvironment(array.length);
            assert this.environment != null;
            for (String replaceFirst : array) {
                if (replaceFirst.indexOf(0) != -1) {
                    replaceFirst = replaceFirst.replaceFirst("\u0000.*", "");
                }
                final int index = replaceFirst.indexOf(61, 1);
                if (index != -1) {
                    this.environment.put(replaceFirst.substring(0, index), replaceFirst.substring(index + 1));
                }
            }
        }
        return this;
    }
    
    public File directory() {
        return this.directory;
    }
    
    public ProcessBuilder directory(final File directory) {
        this.directory = directory;
        return this;
    }
    
    private Redirect[] redirects() {
        if (this.redirects == null) {
            this.redirects = new Redirect[] { Redirect.PIPE, Redirect.PIPE, Redirect.PIPE };
        }
        return this.redirects;
    }
    
    public ProcessBuilder redirectInput(final Redirect redirect) {
        if (redirect.type() == Redirect.Type.WRITE || redirect.type() == Redirect.Type.APPEND) {
            throw new IllegalArgumentException("Redirect invalid for reading: " + redirect);
        }
        this.redirects()[0] = redirect;
        return this;
    }
    
    public ProcessBuilder redirectOutput(final Redirect redirect) {
        if (redirect.type() == Redirect.Type.READ) {
            throw new IllegalArgumentException("Redirect invalid for writing: " + redirect);
        }
        this.redirects()[1] = redirect;
        return this;
    }
    
    public ProcessBuilder redirectError(final Redirect redirect) {
        if (redirect.type() == Redirect.Type.READ) {
            throw new IllegalArgumentException("Redirect invalid for writing: " + redirect);
        }
        this.redirects()[2] = redirect;
        return this;
    }
    
    public ProcessBuilder redirectInput(final File file) {
        return this.redirectInput(Redirect.from(file));
    }
    
    public ProcessBuilder redirectOutput(final File file) {
        return this.redirectOutput(Redirect.to(file));
    }
    
    public ProcessBuilder redirectError(final File file) {
        return this.redirectError(Redirect.to(file));
    }
    
    public Redirect redirectInput() {
        return (this.redirects == null) ? Redirect.PIPE : this.redirects[0];
    }
    
    public Redirect redirectOutput() {
        return (this.redirects == null) ? Redirect.PIPE : this.redirects[1];
    }
    
    public Redirect redirectError() {
        return (this.redirects == null) ? Redirect.PIPE : this.redirects[2];
    }
    
    public ProcessBuilder inheritIO() {
        Arrays.fill(this.redirects(), Redirect.INHERIT);
        return this;
    }
    
    public boolean redirectErrorStream() {
        return this.redirectErrorStream;
    }
    
    public ProcessBuilder redirectErrorStream(final boolean redirectErrorStream) {
        this.redirectErrorStream = redirectErrorStream;
        return this;
    }
    
    public Process start() throws IOException {
        final String[] array2;
        final String[] array = array2 = this.command.toArray(new String[this.command.size()]).clone();
        for (int length = array2.length, i = 0; i < length; ++i) {
            if (array2[i] == null) {
                throw new NullPointerException();
            }
        }
        final String s = array[0];
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkExec(s);
        }
        final String s2 = (this.directory == null) ? null : this.directory.toString();
        for (int j = 1; j < array.length; ++j) {
            if (array[j].indexOf(0) >= 0) {
                throw new IOException("invalid null character in command");
            }
        }
        try {
            return ProcessImpl.start(array, this.environment, s2, this.redirects, this.redirectErrorStream);
        }
        catch (IOException | IllegalArgumentException ex5) {
            final IllegalArgumentException ex2;
            final IllegalArgumentException ex = ex2;
            String string = ": " + ex.getMessage();
            IllegalArgumentException ex3 = ex;
            if (ex instanceof IOException && securityManager != null) {
                try {
                    securityManager.checkRead(s);
                }
                catch (SecurityException ex4) {
                    string = "";
                    ex3 = (IllegalArgumentException)ex4;
                }
            }
            throw new IOException("Cannot run program \"" + s + "\"" + ((s2 == null) ? "" : (" (in directory \"" + s2 + "\")")) + string, ex3);
        }
    }
    
    static class NullInputStream extends InputStream
    {
        static final NullInputStream INSTANCE;
        
        @Override
        public int read() {
            return -1;
        }
        
        @Override
        public int available() {
            return 0;
        }
        
        static {
            INSTANCE = new NullInputStream();
        }
    }
    
    static class NullOutputStream extends OutputStream
    {
        static final NullOutputStream INSTANCE;
        
        @Override
        public void write(final int n) throws IOException {
            throw new IOException("Stream closed");
        }
        
        static {
            INSTANCE = new NullOutputStream();
        }
    }
    
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
}
