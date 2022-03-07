package org.xml.sax.helpers;

import org.xml.sax.*;

public class AttributesImpl implements Attributes
{
    int length;
    String[] data;
    
    public AttributesImpl() {
        this.length = 0;
        this.data = null;
    }
    
    public AttributesImpl(final Attributes attributes) {
        this.setAttributes(attributes);
    }
    
    @Override
    public int getLength() {
        return this.length;
    }
    
    @Override
    public String getURI(final int n) {
        if (n >= 0 && n < this.length) {
            return this.data[n * 5];
        }
        return null;
    }
    
    @Override
    public String getLocalName(final int n) {
        if (n >= 0 && n < this.length) {
            return this.data[n * 5 + 1];
        }
        return null;
    }
    
    @Override
    public String getQName(final int n) {
        if (n >= 0 && n < this.length) {
            return this.data[n * 5 + 2];
        }
        return null;
    }
    
    @Override
    public String getType(final int n) {
        if (n >= 0 && n < this.length) {
            return this.data[n * 5 + 3];
        }
        return null;
    }
    
    @Override
    public String getValue(final int n) {
        if (n >= 0 && n < this.length) {
            return this.data[n * 5 + 4];
        }
        return null;
    }
    
    @Override
    public int getIndex(final String s, final String s2) {
        for (int n = this.length * 5, i = 0; i < n; i += 5) {
            if (this.data[i].equals(s) && this.data[i + 1].equals(s2)) {
                return i / 5;
            }
        }
        return -1;
    }
    
    @Override
    public int getIndex(final String s) {
        for (int n = this.length * 5, i = 0; i < n; i += 5) {
            if (this.data[i + 2].equals(s)) {
                return i / 5;
            }
        }
        return -1;
    }
    
    @Override
    public String getType(final String s, final String s2) {
        for (int n = this.length * 5, i = 0; i < n; i += 5) {
            if (this.data[i].equals(s) && this.data[i + 1].equals(s2)) {
                return this.data[i + 3];
            }
        }
        return null;
    }
    
    @Override
    public String getType(final String s) {
        for (int n = this.length * 5, i = 0; i < n; i += 5) {
            if (this.data[i + 2].equals(s)) {
                return this.data[i + 3];
            }
        }
        return null;
    }
    
    @Override
    public String getValue(final String s, final String s2) {
        for (int n = this.length * 5, i = 0; i < n; i += 5) {
            if (this.data[i].equals(s) && this.data[i + 1].equals(s2)) {
                return this.data[i + 4];
            }
        }
        return null;
    }
    
    @Override
    public String getValue(final String s) {
        for (int n = this.length * 5, i = 0; i < n; i += 5) {
            if (this.data[i + 2].equals(s)) {
                return this.data[i + 4];
            }
        }
        return null;
    }
    
    public void clear() {
        if (this.data != null) {
            for (int i = 0; i < this.length * 5; ++i) {
                this.data[i] = null;
            }
        }
        this.length = 0;
    }
    
    public void setAttributes(final Attributes attributes) {
        this.clear();
        this.length = attributes.getLength();
        if (this.length > 0) {
            this.data = new String[this.length * 5];
            for (int i = 0; i < this.length; ++i) {
                this.data[i * 5] = attributes.getURI(i);
                this.data[i * 5 + 1] = attributes.getLocalName(i);
                this.data[i * 5 + 2] = attributes.getQName(i);
                this.data[i * 5 + 3] = attributes.getType(i);
                this.data[i * 5 + 4] = attributes.getValue(i);
            }
        }
    }
    
    public void addAttribute(final String s, final String s2, final String s3, final String s4, final String s5) {
        this.ensureCapacity(this.length + 1);
        this.data[this.length * 5] = s;
        this.data[this.length * 5 + 1] = s2;
        this.data[this.length * 5 + 2] = s3;
        this.data[this.length * 5 + 3] = s4;
        this.data[this.length * 5 + 4] = s5;
        ++this.length;
    }
    
    public void setAttribute(final int n, final String s, final String s2, final String s3, final String s4, final String s5) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5] = s;
            this.data[n * 5 + 1] = s2;
            this.data[n * 5 + 2] = s3;
            this.data[n * 5 + 3] = s4;
            this.data[n * 5 + 4] = s5;
        }
        else {
            this.badIndex(n);
        }
    }
    
    public void removeAttribute(int n) {
        if (n >= 0 && n < this.length) {
            if (n < this.length - 1) {
                System.arraycopy(this.data, (n + 1) * 5, this.data, n * 5, (this.length - n - 1) * 5);
            }
            n = (this.length - 1) * 5;
            this.data[n++] = null;
            this.data[n++] = null;
            this.data[n++] = null;
            this.data[n++] = null;
            this.data[n] = null;
            --this.length;
        }
        else {
            this.badIndex(n);
        }
    }
    
    public void setURI(final int n, final String s) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5] = s;
        }
        else {
            this.badIndex(n);
        }
    }
    
    public void setLocalName(final int n, final String s) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5 + 1] = s;
        }
        else {
            this.badIndex(n);
        }
    }
    
    public void setQName(final int n, final String s) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5 + 2] = s;
        }
        else {
            this.badIndex(n);
        }
    }
    
    public void setType(final int n, final String s) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5 + 3] = s;
        }
        else {
            this.badIndex(n);
        }
    }
    
    public void setValue(final int n, final String s) {
        if (n >= 0 && n < this.length) {
            this.data[n * 5 + 4] = s;
        }
        else {
            this.badIndex(n);
        }
    }
    
    private void ensureCapacity(final int n) {
        if (n <= 0) {
            return;
        }
        int i;
        if (this.data == null || this.data.length == 0) {
            i = 25;
        }
        else {
            if (this.data.length >= n * 5) {
                return;
            }
            i = this.data.length;
        }
        while (i < n * 5) {
            i *= 2;
        }
        final String[] data = new String[i];
        if (this.length > 0) {
            System.arraycopy(this.data, 0, data, 0, this.length * 5);
        }
        this.data = data;
    }
    
    private void badIndex(final int n) throws ArrayIndexOutOfBoundsException {
        throw new ArrayIndexOutOfBoundsException("Attempt to modify attribute at illegal index: " + n);
    }
}
