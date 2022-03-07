package javax.net.ssl;

import java.security.*;
import java.util.*;

public class SSLParameters
{
    private String[] cipherSuites;
    private String[] protocols;
    private boolean wantClientAuth;
    private boolean needClientAuth;
    private String identificationAlgorithm;
    private AlgorithmConstraints algorithmConstraints;
    private Map<Integer, SNIServerName> sniNames;
    private Map<Integer, SNIMatcher> sniMatchers;
    private boolean preferLocalCipherSuites;
    private String[] applicationProtocols;
    
    public SSLParameters() {
        this.sniNames = null;
        this.sniMatchers = null;
        this.applicationProtocols = new String[0];
    }
    
    public SSLParameters(final String[] cipherSuites) {
        this.sniNames = null;
        this.sniMatchers = null;
        this.applicationProtocols = new String[0];
        this.setCipherSuites(cipherSuites);
    }
    
    public SSLParameters(final String[] cipherSuites, final String[] protocols) {
        this.sniNames = null;
        this.sniMatchers = null;
        this.applicationProtocols = new String[0];
        this.setCipherSuites(cipherSuites);
        this.setProtocols(protocols);
    }
    
    private static String[] clone(final String[] array) {
        return (String[])((array == null) ? null : ((String[])array.clone()));
    }
    
    public String[] getCipherSuites() {
        return clone(this.cipherSuites);
    }
    
    public void setCipherSuites(final String[] array) {
        this.cipherSuites = clone(array);
    }
    
    public String[] getProtocols() {
        return clone(this.protocols);
    }
    
    public void setProtocols(final String[] array) {
        this.protocols = clone(array);
    }
    
    public boolean getWantClientAuth() {
        return this.wantClientAuth;
    }
    
    public void setWantClientAuth(final boolean wantClientAuth) {
        this.wantClientAuth = wantClientAuth;
        this.needClientAuth = false;
    }
    
    public boolean getNeedClientAuth() {
        return this.needClientAuth;
    }
    
    public void setNeedClientAuth(final boolean needClientAuth) {
        this.wantClientAuth = false;
        this.needClientAuth = needClientAuth;
    }
    
    public AlgorithmConstraints getAlgorithmConstraints() {
        return this.algorithmConstraints;
    }
    
    public void setAlgorithmConstraints(final AlgorithmConstraints algorithmConstraints) {
        this.algorithmConstraints = algorithmConstraints;
    }
    
    public String getEndpointIdentificationAlgorithm() {
        return this.identificationAlgorithm;
    }
    
    public void setEndpointIdentificationAlgorithm(final String identificationAlgorithm) {
        this.identificationAlgorithm = identificationAlgorithm;
    }
    
    public final void setServerNames(final List<SNIServerName> list) {
        if (list != null) {
            if (!list.isEmpty()) {
                this.sniNames = new LinkedHashMap<Integer, SNIServerName>(list.size());
                for (final SNIServerName sniServerName : list) {
                    if (this.sniNames.put(sniServerName.getType(), sniServerName) != null) {
                        throw new IllegalArgumentException("Duplicated server name of type " + sniServerName.getType());
                    }
                }
            }
            else {
                this.sniNames = Collections.emptyMap();
            }
        }
        else {
            this.sniNames = null;
        }
    }
    
    public final List<SNIServerName> getServerNames() {
        if (this.sniNames == null) {
            return null;
        }
        if (!this.sniNames.isEmpty()) {
            return Collections.unmodifiableList((List<? extends SNIServerName>)new ArrayList<SNIServerName>(this.sniNames.values()));
        }
        return Collections.emptyList();
    }
    
    public final void setSNIMatchers(final Collection<SNIMatcher> collection) {
        if (collection != null) {
            if (!collection.isEmpty()) {
                this.sniMatchers = new HashMap<Integer, SNIMatcher>(collection.size());
                for (final SNIMatcher sniMatcher : collection) {
                    if (this.sniMatchers.put(sniMatcher.getType(), sniMatcher) != null) {
                        throw new IllegalArgumentException("Duplicated server name of type " + sniMatcher.getType());
                    }
                }
            }
            else {
                this.sniMatchers = Collections.emptyMap();
            }
        }
        else {
            this.sniMatchers = null;
        }
    }
    
    public final Collection<SNIMatcher> getSNIMatchers() {
        if (this.sniMatchers == null) {
            return null;
        }
        if (!this.sniMatchers.isEmpty()) {
            return (Collection<SNIMatcher>)Collections.unmodifiableList((List<?>)new ArrayList<Object>(this.sniMatchers.values()));
        }
        return (Collection<SNIMatcher>)Collections.emptyList();
    }
    
    public final void setUseCipherSuitesOrder(final boolean preferLocalCipherSuites) {
        this.preferLocalCipherSuites = preferLocalCipherSuites;
    }
    
    public final boolean getUseCipherSuitesOrder() {
        return this.preferLocalCipherSuites;
    }
    
    public String[] getApplicationProtocols() {
        return this.applicationProtocols.clone();
    }
    
    public void setApplicationProtocols(final String[] array) {
        if (array == null) {
            throw new IllegalArgumentException("protocols was null");
        }
        final String[] array2;
        final String[] applicationProtocols = array2 = array.clone();
        for (final String s : array2) {
            if (s == null || s.equals("")) {
                throw new IllegalArgumentException("An element of protocols was null/empty");
            }
        }
        this.applicationProtocols = applicationProtocols;
    }
}
