package java.util;

import sun.util.locale.*;

public static final class Builder
{
    private final InternalLocaleBuilder localeBuilder;
    
    public Builder() {
        this.localeBuilder = new InternalLocaleBuilder();
    }
    
    public Builder setLocale(final Locale locale) {
        try {
            this.localeBuilder.setLocale(Locale.access$600(locale), Locale.access$700(locale));
        }
        catch (LocaleSyntaxException ex) {
            throw new IllformedLocaleException(ex.getMessage(), ex.getErrorIndex());
        }
        return this;
    }
    
    public Builder setLanguageTag(final String s) {
        final ParseStatus parseStatus = new ParseStatus();
        final LanguageTag parse = LanguageTag.parse(s, parseStatus);
        if (parseStatus.isError()) {
            throw new IllformedLocaleException(parseStatus.getErrorMessage(), parseStatus.getErrorIndex());
        }
        this.localeBuilder.setLanguageTag(parse);
        return this;
    }
    
    public Builder setLanguage(final String language) {
        try {
            this.localeBuilder.setLanguage(language);
        }
        catch (LocaleSyntaxException ex) {
            throw new IllformedLocaleException(ex.getMessage(), ex.getErrorIndex());
        }
        return this;
    }
    
    public Builder setScript(final String script) {
        try {
            this.localeBuilder.setScript(script);
        }
        catch (LocaleSyntaxException ex) {
            throw new IllformedLocaleException(ex.getMessage(), ex.getErrorIndex());
        }
        return this;
    }
    
    public Builder setRegion(final String region) {
        try {
            this.localeBuilder.setRegion(region);
        }
        catch (LocaleSyntaxException ex) {
            throw new IllformedLocaleException(ex.getMessage(), ex.getErrorIndex());
        }
        return this;
    }
    
    public Builder setVariant(final String variant) {
        try {
            this.localeBuilder.setVariant(variant);
        }
        catch (LocaleSyntaxException ex) {
            throw new IllformedLocaleException(ex.getMessage(), ex.getErrorIndex());
        }
        return this;
    }
    
    public Builder setExtension(final char c, final String s) {
        try {
            this.localeBuilder.setExtension(c, s);
        }
        catch (LocaleSyntaxException ex) {
            throw new IllformedLocaleException(ex.getMessage(), ex.getErrorIndex());
        }
        return this;
    }
    
    public Builder setUnicodeLocaleKeyword(final String s, final String s2) {
        try {
            this.localeBuilder.setUnicodeLocaleKeyword(s, s2);
        }
        catch (LocaleSyntaxException ex) {
            throw new IllformedLocaleException(ex.getMessage(), ex.getErrorIndex());
        }
        return this;
    }
    
    public Builder addUnicodeLocaleAttribute(final String s) {
        try {
            this.localeBuilder.addUnicodeLocaleAttribute(s);
        }
        catch (LocaleSyntaxException ex) {
            throw new IllformedLocaleException(ex.getMessage(), ex.getErrorIndex());
        }
        return this;
    }
    
    public Builder removeUnicodeLocaleAttribute(final String s) {
        try {
            this.localeBuilder.removeUnicodeLocaleAttribute(s);
        }
        catch (LocaleSyntaxException ex) {
            throw new IllformedLocaleException(ex.getMessage(), ex.getErrorIndex());
        }
        return this;
    }
    
    public Builder clear() {
        this.localeBuilder.clear();
        return this;
    }
    
    public Builder clearExtensions() {
        this.localeBuilder.clearExtensions();
        return this;
    }
    
    public Locale build() {
        final BaseLocale baseLocale = this.localeBuilder.getBaseLocale();
        LocaleExtensions localeExtensions = this.localeBuilder.getLocaleExtensions();
        if (localeExtensions == null && baseLocale.getVariant().length() > 0) {
            localeExtensions = Locale.access$800(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), baseLocale.getVariant());
        }
        return Locale.getInstance(baseLocale, localeExtensions);
    }
}
