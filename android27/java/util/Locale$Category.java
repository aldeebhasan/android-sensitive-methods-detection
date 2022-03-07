package java.util;

public enum Category
{
    DISPLAY("user.language.display", "user.script.display", "user.country.display", "user.variant.display"), 
    FORMAT("user.language.format", "user.script.format", "user.country.format", "user.variant.format");
    
    final String languageKey;
    final String scriptKey;
    final String countryKey;
    final String variantKey;
    
    private Category(final String languageKey, final String scriptKey, final String countryKey, final String variantKey) {
        this.languageKey = languageKey;
        this.scriptKey = scriptKey;
        this.countryKey = countryKey;
        this.variantKey = variantKey;
    }
}
