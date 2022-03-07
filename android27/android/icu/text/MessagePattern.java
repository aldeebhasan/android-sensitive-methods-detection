package android.icu.text;

import android.icu.util.*;

public final class MessagePattern implements Cloneable, Freezable<MessagePattern>
{
    public static final int ARG_NAME_NOT_NUMBER = -1;
    public static final int ARG_NAME_NOT_VALID = -2;
    public static final double NO_NUMERIC_VALUE = -1.23456789E8;
    
    public MessagePattern() {
        throw new RuntimeException("Stub!");
    }
    
    public MessagePattern(final ApostropheMode mode) {
        throw new RuntimeException("Stub!");
    }
    
    public MessagePattern(final String pattern) {
        throw new RuntimeException("Stub!");
    }
    
    public MessagePattern parse(final String pattern) {
        throw new RuntimeException("Stub!");
    }
    
    public MessagePattern parseChoiceStyle(final String pattern) {
        throw new RuntimeException("Stub!");
    }
    
    public MessagePattern parsePluralStyle(final String pattern) {
        throw new RuntimeException("Stub!");
    }
    
    public MessagePattern parseSelectStyle(final String pattern) {
        throw new RuntimeException("Stub!");
    }
    
    public void clear() {
        throw new RuntimeException("Stub!");
    }
    
    public void clearPatternAndSetApostropheMode(final ApostropheMode mode) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object other) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    public ApostropheMode getApostropheMode() {
        throw new RuntimeException("Stub!");
    }
    
    public String getPatternString() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasNamedArguments() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean hasNumberedArguments() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    public static int validateArgumentName(final String name) {
        throw new RuntimeException("Stub!");
    }
    
    public String autoQuoteApostropheDeep() {
        throw new RuntimeException("Stub!");
    }
    
    public int countParts() {
        throw new RuntimeException("Stub!");
    }
    
    public Part getPart(final int i) {
        throw new RuntimeException("Stub!");
    }
    
    public Part.Type getPartType(final int i) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPatternIndex(final int partIndex) {
        throw new RuntimeException("Stub!");
    }
    
    public String getSubstring(final Part part) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean partSubstringMatches(final Part part, final String s) {
        throw new RuntimeException("Stub!");
    }
    
    public double getNumericValue(final Part part) {
        throw new RuntimeException("Stub!");
    }
    
    public double getPluralOffset(final int pluralStart) {
        throw new RuntimeException("Stub!");
    }
    
    public int getLimitPartIndex(final int start) {
        throw new RuntimeException("Stub!");
    }
    
    public Object clone() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public MessagePattern cloneAsThawed() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public MessagePattern freeze() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean isFrozen() {
        throw new RuntimeException("Stub!");
    }
    
    public enum ApostropheMode
    {
        DOUBLE_OPTIONAL, 
        DOUBLE_REQUIRED;
    }
    
    public static final class Part
    {
        Part() {
            throw new RuntimeException("Stub!");
        }
        
        public Type getType() {
            throw new RuntimeException("Stub!");
        }
        
        public int getIndex() {
            throw new RuntimeException("Stub!");
        }
        
        public int getLength() {
            throw new RuntimeException("Stub!");
        }
        
        public int getLimit() {
            throw new RuntimeException("Stub!");
        }
        
        public int getValue() {
            throw new RuntimeException("Stub!");
        }
        
        public ArgType getArgType() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean equals(final Object other) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int hashCode() {
            throw new RuntimeException("Stub!");
        }
        
        public enum Type
        {
            ARG_DOUBLE, 
            ARG_INT, 
            ARG_LIMIT, 
            ARG_NAME, 
            ARG_NUMBER, 
            ARG_SELECTOR, 
            ARG_START, 
            ARG_STYLE, 
            ARG_TYPE, 
            INSERT_CHAR, 
            MSG_LIMIT, 
            MSG_START, 
            REPLACE_NUMBER, 
            SKIP_SYNTAX;
            
            public boolean hasNumericValue() {
                throw new RuntimeException("Stub!");
            }
        }
    }
    
    public enum ArgType
    {
        CHOICE, 
        NONE, 
        PLURAL, 
        SELECT, 
        SELECTORDINAL, 
        SIMPLE;
        
        public boolean hasPluralStyle() {
            throw new RuntimeException("Stub!");
        }
    }
}
