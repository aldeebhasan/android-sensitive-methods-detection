package org.xml.sax.helpers;

import java.util.*;

public class NamespaceSupport
{
    public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
    public static final String NSDECL = "http://www.w3.org/xmlns/2000/";
    private static final Enumeration EMPTY_ENUMERATION;
    private Context[] contexts;
    private Context currentContext;
    private int contextPos;
    private boolean namespaceDeclUris;
    
    public NamespaceSupport() {
        this.reset();
    }
    
    public void reset() {
        this.contexts = new Context[32];
        this.namespaceDeclUris = false;
        this.contextPos = 0;
        this.contexts[this.contextPos] = (this.currentContext = new Context());
        this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
    }
    
    public void pushContext() {
        final int length = this.contexts.length;
        ++this.contextPos;
        if (this.contextPos >= length) {
            final Context[] contexts = new Context[length * 2];
            System.arraycopy(this.contexts, 0, contexts, 0, length);
            this.contexts = contexts;
        }
        this.currentContext = this.contexts[this.contextPos];
        if (this.currentContext == null) {
            this.contexts[this.contextPos] = (this.currentContext = new Context());
        }
        if (this.contextPos > 0) {
            this.currentContext.setParent(this.contexts[this.contextPos - 1]);
        }
    }
    
    public void popContext() {
        this.contexts[this.contextPos].clear();
        --this.contextPos;
        if (this.contextPos < 0) {
            throw new EmptyStackException();
        }
        this.currentContext = this.contexts[this.contextPos];
    }
    
    public boolean declarePrefix(final String s, final String s2) {
        if (s.equals("xml") || s.equals("xmlns")) {
            return false;
        }
        this.currentContext.declarePrefix(s, s2);
        return true;
    }
    
    public String[] processName(final String s, final String[] array, final boolean b) {
        final String[] processName = this.currentContext.processName(s, b);
        if (processName == null) {
            return null;
        }
        array[0] = processName[0];
        array[1] = processName[1];
        array[2] = processName[2];
        return array;
    }
    
    public String getURI(final String s) {
        return this.currentContext.getURI(s);
    }
    
    public Enumeration getPrefixes() {
        return this.currentContext.getPrefixes();
    }
    
    public String getPrefix(final String s) {
        return this.currentContext.getPrefix(s);
    }
    
    public Enumeration getPrefixes(final String s) {
        final ArrayList<Object> list = new ArrayList<Object>();
        final Enumeration prefixes = this.getPrefixes();
        while (prefixes.hasMoreElements()) {
            final String s2 = prefixes.nextElement();
            if (s.equals(this.getURI(s2))) {
                list.add(s2);
            }
        }
        return Collections.enumeration(list);
    }
    
    public Enumeration getDeclaredPrefixes() {
        return this.currentContext.getDeclaredPrefixes();
    }
    
    public void setNamespaceDeclUris(final boolean namespaceDeclUris) {
        if (this.contextPos != 0) {
            throw new IllegalStateException();
        }
        if (namespaceDeclUris == this.namespaceDeclUris) {
            return;
        }
        this.namespaceDeclUris = namespaceDeclUris;
        if (namespaceDeclUris) {
            this.currentContext.declarePrefix("xmlns", "http://www.w3.org/xmlns/2000/");
        }
        else {
            this.contexts[this.contextPos] = (this.currentContext = new Context());
            this.currentContext.declarePrefix("xml", "http://www.w3.org/XML/1998/namespace");
        }
    }
    
    public boolean isNamespaceDeclUris() {
        return this.namespaceDeclUris;
    }
    
    static {
        EMPTY_ENUMERATION = Collections.enumeration(new ArrayList<Object>());
    }
    
    final class Context
    {
        Map<String, String> prefixTable;
        Map<String, String> uriTable;
        Map<String, String[]> elementNameTable;
        Map<String, String[]> attributeNameTable;
        String defaultNS;
        private List<String> declarations;
        private boolean declSeen;
        private Context parent;
        
        Context() {
            this.defaultNS = null;
            this.declarations = null;
            this.declSeen = false;
            this.parent = null;
            this.copyTables();
        }
        
        void setParent(final Context parent) {
            this.parent = parent;
            this.declarations = null;
            this.prefixTable = parent.prefixTable;
            this.uriTable = parent.uriTable;
            this.elementNameTable = parent.elementNameTable;
            this.attributeNameTable = parent.attributeNameTable;
            this.defaultNS = parent.defaultNS;
            this.declSeen = false;
        }
        
        void clear() {
            this.parent = null;
            this.prefixTable = null;
            this.uriTable = null;
            this.elementNameTable = null;
            this.attributeNameTable = null;
            this.defaultNS = null;
        }
        
        void declarePrefix(String intern, String intern2) {
            if (!this.declSeen) {
                this.copyTables();
            }
            if (this.declarations == null) {
                this.declarations = new ArrayList<String>();
            }
            intern = intern.intern();
            intern2 = intern2.intern();
            if ("".equals(intern)) {
                if ("".equals(intern2)) {
                    this.defaultNS = null;
                }
                else {
                    this.defaultNS = intern2;
                }
            }
            else {
                this.prefixTable.put(intern, intern2);
                this.uriTable.put(intern2, intern);
            }
            this.declarations.add(intern);
        }
        
        String[] processName(final String s, final boolean b) {
            Map<String, String[]> map;
            if (b) {
                map = this.attributeNameTable;
            }
            else {
                map = this.elementNameTable;
            }
            final String[] array = map.get(s);
            if (array != null) {
                return array;
            }
            final String[] array2 = { null, null, s.intern() };
            final int index = s.indexOf(58);
            if (index == -1) {
                if (b) {
                    if (s == "xmlns" && NamespaceSupport.this.namespaceDeclUris) {
                        array2[0] = "http://www.w3.org/xmlns/2000/";
                    }
                    else {
                        array2[0] = "";
                    }
                }
                else if (this.defaultNS == null) {
                    array2[0] = "";
                }
                else {
                    array2[0] = this.defaultNS;
                }
                array2[1] = array2[2];
            }
            else {
                final String substring = s.substring(0, index);
                final String substring2 = s.substring(index + 1);
                String defaultNS;
                if ("".equals(substring)) {
                    defaultNS = this.defaultNS;
                }
                else {
                    defaultNS = this.prefixTable.get(substring);
                }
                if (defaultNS == null || (!b && "xmlns".equals(substring))) {
                    return null;
                }
                array2[0] = defaultNS;
                array2[1] = substring2.intern();
            }
            map.put(array2[2], array2);
            return array2;
        }
        
        String getURI(final String s) {
            if ("".equals(s)) {
                return this.defaultNS;
            }
            if (this.prefixTable == null) {
                return null;
            }
            return this.prefixTable.get(s);
        }
        
        String getPrefix(final String s) {
            if (this.uriTable == null) {
                return null;
            }
            return this.uriTable.get(s);
        }
        
        Enumeration getDeclaredPrefixes() {
            if (this.declarations == null) {
                return NamespaceSupport.EMPTY_ENUMERATION;
            }
            return Collections.enumeration(this.declarations);
        }
        
        Enumeration getPrefixes() {
            if (this.prefixTable == null) {
                return NamespaceSupport.EMPTY_ENUMERATION;
            }
            return Collections.enumeration(this.prefixTable.keySet());
        }
        
        private void copyTables() {
            if (this.prefixTable != null) {
                this.prefixTable = new HashMap<String, String>(this.prefixTable);
            }
            else {
                this.prefixTable = new HashMap<String, String>();
            }
            if (this.uriTable != null) {
                this.uriTable = new HashMap<String, String>(this.uriTable);
            }
            else {
                this.uriTable = new HashMap<String, String>();
            }
            this.elementNameTable = new HashMap<String, String[]>();
            this.attributeNameTable = new HashMap<String, String[]>();
            this.declSeen = true;
        }
    }
}
