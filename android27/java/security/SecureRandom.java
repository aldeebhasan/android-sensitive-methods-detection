package java.security;

import sun.security.util.*;
import sun.security.provider.*;
import java.util.*;
import sun.security.jca.*;
import java.util.regex.*;

public class SecureRandom extends Random
{
    private static final Debug pdebug;
    private static final boolean skipDebug;
    private Provider provider;
    private SecureRandomSpi secureRandomSpi;
    private String algorithm;
    private static volatile SecureRandom seedGenerator;
    static final long serialVersionUID = 4940670005562187L;
    private byte[] state;
    private MessageDigest digest;
    private byte[] randomBytes;
    private int randomBytesUsed;
    private long counter;
    
    public SecureRandom() {
        super(0L);
        this.provider = null;
        this.secureRandomSpi = null;
        this.digest = null;
        this.getDefaultPRNG(false, null);
    }
    
    public SecureRandom(final byte[] array) {
        super(0L);
        this.provider = null;
        this.secureRandomSpi = null;
        this.digest = null;
        this.getDefaultPRNG(true, array);
    }
    
    private void getDefaultPRNG(final boolean b, final byte[] array) {
        Provider.Service service = null;
        String algorithm = null;
        for (final Provider provider : Providers.getProviderList().providers()) {
            if (provider.getName().equals("SUN")) {
                algorithm = SunEntries.DEF_SECURE_RANDOM_ALGO;
                service = provider.getService("SecureRandom", algorithm);
                break;
            }
            service = provider.getDefaultSecureRandomService();
            if (service != null) {
                algorithm = service.getAlgorithm();
                break;
            }
        }
        if (service == null) {
            algorithm = "SHA1PRNG";
            this.secureRandomSpi = new sun.security.provider.SecureRandom();
            this.provider = Providers.getSunProvider();
        }
        else {
            try {
                this.secureRandomSpi = (SecureRandomSpi)service.newInstance(null);
                this.provider = service.getProvider();
            }
            catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (b) {
            this.secureRandomSpi.engineSetSeed(array);
        }
        if (this.getClass() == SecureRandom.class) {
            this.algorithm = algorithm;
        }
    }
    
    protected SecureRandom(final SecureRandomSpi secureRandomSpi, final Provider provider) {
        this(secureRandomSpi, provider, null);
    }
    
    private SecureRandom(final SecureRandomSpi secureRandomSpi, final Provider provider, final String algorithm) {
        super(0L);
        this.provider = null;
        this.secureRandomSpi = null;
        this.digest = null;
        this.secureRandomSpi = secureRandomSpi;
        this.provider = provider;
        this.algorithm = algorithm;
        if (!SecureRandom.skipDebug && SecureRandom.pdebug != null) {
            SecureRandom.pdebug.println("SecureRandom." + algorithm + " algorithm from: " + this.provider.getName());
        }
    }
    
    public static SecureRandom getInstance(final String s) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("SecureRandom", SecureRandomSpi.class, s);
        return new SecureRandom((SecureRandomSpi)instance.impl, instance.provider, s);
    }
    
    public static SecureRandom getInstance(final String s, final String s2) throws NoSuchAlgorithmException, NoSuchProviderException {
        final GetInstance.Instance instance = GetInstance.getInstance("SecureRandom", SecureRandomSpi.class, s, s2);
        return new SecureRandom((SecureRandomSpi)instance.impl, instance.provider, s);
    }
    
    public static SecureRandom getInstance(final String s, final Provider provider) throws NoSuchAlgorithmException {
        final GetInstance.Instance instance = GetInstance.getInstance("SecureRandom", SecureRandomSpi.class, s, provider);
        return new SecureRandom((SecureRandomSpi)instance.impl, instance.provider, s);
    }
    
    public final Provider getProvider() {
        return this.provider;
    }
    
    public String getAlgorithm() {
        return (this.algorithm != null) ? this.algorithm : "unknown";
    }
    
    public synchronized void setSeed(final byte[] array) {
        this.secureRandomSpi.engineSetSeed(array);
    }
    
    @Override
    public void setSeed(final long n) {
        if (n != 0L) {
            this.secureRandomSpi.engineSetSeed(longToByteArray(n));
        }
    }
    
    @Override
    public void nextBytes(final byte[] array) {
        this.secureRandomSpi.engineNextBytes(array);
    }
    
    @Override
    protected final int next(final int n) {
        final int n2 = (n + 7) / 8;
        final byte[] array = new byte[n2];
        int n3 = 0;
        this.nextBytes(array);
        for (int i = 0; i < n2; ++i) {
            n3 = (n3 << 8) + (array[i] & 0xFF);
        }
        return n3 >>> n2 * 8 - n;
    }
    
    public static byte[] getSeed(final int n) {
        if (SecureRandom.seedGenerator == null) {
            SecureRandom.seedGenerator = new SecureRandom();
        }
        return SecureRandom.seedGenerator.generateSeed(n);
    }
    
    public byte[] generateSeed(final int n) {
        if (n < 0) {
            throw new NegativeArraySizeException("numBytes cannot be negative");
        }
        return this.secureRandomSpi.engineGenerateSeed(n);
    }
    
    private static byte[] longToByteArray(long n) {
        final byte[] array = new byte[8];
        for (int i = 0; i < 8; ++i) {
            array[i] = (byte)n;
            n >>= 8;
        }
        return array;
    }
    
    public static SecureRandom getInstanceStrong() throws NoSuchAlgorithmException {
        final String s = AccessController.doPrivileged((PrivilegedAction<String>)new PrivilegedAction<String>() {
            @Override
            public String run() {
                return Security.getProperty("securerandom.strongAlgorithms");
            }
        });
        if (s == null || s.length() == 0) {
            throw new NoSuchAlgorithmException("Null/empty securerandom.strongAlgorithms Security Property");
        }
        String group = s;
        while (group != null) {
            final Matcher matcher;
            if ((matcher = StrongPatternHolder.pattern.matcher(group)).matches()) {
                final String group2 = matcher.group(1);
                final String group3 = matcher.group(3);
                try {
                    if (group3 == null) {
                        return getInstance(group2);
                    }
                    return getInstance(group2, group3);
                }
                catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
                    group = matcher.group(5);
                    continue;
                }
            }
            group = null;
        }
        throw new NoSuchAlgorithmException("No strong SecureRandom impls available: " + s);
    }
    
    static {
        pdebug = Debug.getInstance("provider", "Provider");
        skipDebug = (Debug.isOn("engine=") && !Debug.isOn("securerandom"));
        SecureRandom.seedGenerator = null;
    }
    
    private static final class StrongPatternHolder
    {
        private static Pattern pattern;
        
        static {
            StrongPatternHolder.pattern = Pattern.compile("\\s*([\\S&&[^:,]]*)(\\:([\\S&&[^,]]*))?\\s*(\\,(.*))?");
        }
    }
}
