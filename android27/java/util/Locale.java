package java.util;

import sun.security.action.*;
import java.security.*;
import java.util.spi.*;
import sun.util.locale.provider.*;
import java.text.*;
import java.io.*;
import sun.util.locale.*;

public final class Locale implements Cloneable, Serializable
{
    private static final Cache LOCALECACHE;
    public static final Locale ENGLISH;
    public static final Locale FRENCH;
    public static final Locale GERMAN;
    public static final Locale ITALIAN;
    public static final Locale JAPANESE;
    public static final Locale KOREAN;
    public static final Locale CHINESE;
    public static final Locale SIMPLIFIED_CHINESE;
    public static final Locale TRADITIONAL_CHINESE;
    public static final Locale FRANCE;
    public static final Locale GERMANY;
    public static final Locale ITALY;
    public static final Locale JAPAN;
    public static final Locale KOREA;
    public static final Locale CHINA;
    public static final Locale PRC;
    public static final Locale TAIWAN;
    public static final Locale UK;
    public static final Locale US;
    public static final Locale CANADA;
    public static final Locale CANADA_FRENCH;
    public static final Locale ROOT;
    public static final char PRIVATE_USE_EXTENSION = 'x';
    public static final char UNICODE_LOCALE_EXTENSION = 'u';
    static final long serialVersionUID = 9149081749638150636L;
    private static final int DISPLAY_LANGUAGE = 0;
    private static final int DISPLAY_COUNTRY = 1;
    private static final int DISPLAY_VARIANT = 2;
    private static final int DISPLAY_SCRIPT = 3;
    private transient BaseLocale baseLocale;
    private transient LocaleExtensions localeExtensions;
    private transient volatile int hashCodeValue;
    private static volatile Locale defaultLocale;
    private static volatile Locale defaultDisplayLocale;
    private static volatile Locale defaultFormatLocale;
    private transient volatile String languageTag;
    private static final ObjectStreamField[] serialPersistentFields;
    private static volatile String[] isoLanguages;
    private static volatile String[] isoCountries;
    
    private Locale(final BaseLocale baseLocale, final LocaleExtensions localeExtensions) {
        this.hashCodeValue = 0;
        this.baseLocale = baseLocale;
        this.localeExtensions = localeExtensions;
    }
    
    public Locale(final String s, final String s2, final String s3) {
        this.hashCodeValue = 0;
        if (s == null || s2 == null || s3 == null) {
            throw new NullPointerException();
        }
        this.baseLocale = BaseLocale.getInstance(convertOldISOCodes(s), "", s2, s3);
        this.localeExtensions = getCompatibilityExtensions(s, "", s2, s3);
    }
    
    public Locale(final String s, final String s2) {
        this(s, s2, "");
    }
    
    public Locale(final String s) {
        this(s, "", "");
    }
    
    private static Locale createConstant(final String s, final String s2) {
        return getInstance(BaseLocale.createInstance(s, s2), null);
    }
    
    static Locale getInstance(final String s, final String s2, final String s3) {
        return getInstance(s, "", s2, s3, null);
    }
    
    static Locale getInstance(final String s, final String s2, final String s3, final String s4, LocaleExtensions compatibilityExtensions) {
        if (s == null || s2 == null || s3 == null || s4 == null) {
            throw new NullPointerException();
        }
        if (compatibilityExtensions == null) {
            compatibilityExtensions = getCompatibilityExtensions(s, s2, s3, s4);
        }
        return getInstance(BaseLocale.getInstance(s, s2, s3, s4), compatibilityExtensions);
    }
    
    static Locale getInstance(final BaseLocale baseLocale, final LocaleExtensions localeExtensions) {
        return Locale.LOCALECACHE.get(new LocaleKey(baseLocale, localeExtensions));
    }
    
    public static Locale getDefault() {
        return Locale.defaultLocale;
    }
    
    public static Locale getDefault(final Category category) {
        switch (category) {
            case DISPLAY: {
                if (Locale.defaultDisplayLocale == null) {
                    synchronized (Locale.class) {
                        if (Locale.defaultDisplayLocale == null) {
                            Locale.defaultDisplayLocale = initDefault(category);
                        }
                    }
                }
                return Locale.defaultDisplayLocale;
            }
            case FORMAT: {
                if (Locale.defaultFormatLocale == null) {
                    synchronized (Locale.class) {
                        if (Locale.defaultFormatLocale == null) {
                            Locale.defaultFormatLocale = initDefault(category);
                        }
                    }
                }
                return Locale.defaultFormatLocale;
            }
            default: {
                assert false : "Unknown Category";
                return getDefault();
            }
        }
    }
    
    private static Locale initDefault() {
        final String s = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("user.language", "en"));
        final String s2 = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("user.region"));
        String substring;
        String substring2;
        String s3;
        if (s2 != null) {
            final int index = s2.indexOf(95);
            if (index >= 0) {
                substring = s2.substring(0, index);
                substring2 = s2.substring(index + 1);
            }
            else {
                substring = s2;
                substring2 = "";
            }
            s3 = "";
        }
        else {
            s3 = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("user.script", ""));
            substring = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("user.country", ""));
            substring2 = AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction("user.variant", ""));
        }
        return getInstance(s, s3, substring, substring2, null);
    }
    
    private static Locale initDefault(final Category category) {
        return getInstance(AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction(category.languageKey, Locale.defaultLocale.getLanguage())), AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction(category.scriptKey, Locale.defaultLocale.getScript())), AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction(category.countryKey, Locale.defaultLocale.getCountry())), AccessController.doPrivileged((PrivilegedAction<String>)new GetPropertyAction(category.variantKey, Locale.defaultLocale.getVariant())), null);
    }
    
    public static synchronized void setDefault(final Locale defaultLocale) {
        setDefault(Category.DISPLAY, defaultLocale);
        setDefault(Category.FORMAT, defaultLocale);
        Locale.defaultLocale = defaultLocale;
    }
    
    public static synchronized void setDefault(final Category category, final Locale locale) {
        if (category == null) {
            throw new NullPointerException("Category cannot be NULL");
        }
        if (locale == null) {
            throw new NullPointerException("Can't set default locale to NULL");
        }
        final SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new PropertyPermission("user.language", "write"));
        }
        switch (category) {
            case DISPLAY: {
                Locale.defaultDisplayLocale = locale;
                break;
            }
            case FORMAT: {
                Locale.defaultFormatLocale = locale;
                break;
            }
            default: {
                assert false : "Unknown Category";
                break;
            }
        }
    }
    
    public static Locale[] getAvailableLocales() {
        return LocaleServiceProviderPool.getAllAvailableLocales();
    }
    
    public static String[] getISOCountries() {
        if (Locale.isoCountries == null) {
            Locale.isoCountries = getISO2Table("ADANDAEAREAFAFGAGATGAIAIAALALBAMARMANANTAOAGOAQATAARARGASASMATAUTAUAUSAWABWAXALAAZAZEBABIHBBBRBBDBGDBEBELBFBFABGBGRBHBHRBIBDIBJBENBLBLMBMBMUBNBRNBOBOLBQBESBRBRABSBHSBTBTNBVBVTBWBWABYBLRBZBLZCACANCCCCKCDCODCFCAFCGCOGCHCHECICIVCKCOKCLCHLCMCMRCNCHNCOCOLCRCRICUCUBCVCPVCWCUWCXCXRCYCYPCZCZEDEDEUDJDJIDKDNKDMDMADODOMDZDZAECECUEEESTEGEGYEHESHERERIESESPETETHFIFINFJFJIFKFLKFMFSMFOFROFRFRAGAGABGBGBRGDGRDGEGEOGFGUFGGGGYGHGHAGIGIBGLGRLGMGMBGNGINGPGLPGQGNQGRGRCGSSGSGTGTMGUGUMGWGNBGYGUYHKHKGHMHMDHNHNDHRHRVHTHTIHUHUNIDIDNIEIRLILISRIMIMNININDIOIOTIQIRQIRIRNISISLITITAJEJEYJMJAMJOJORJPJPNKEKENKGKGZKHKHMKIKIRKMCOMKNKNAKPPRKKRKORKWKWTKYCYMKZKAZLALAOLBLBNLCLCALILIELKLKALRLBRLSLSOLTLTULULUXLVLVALYLBYMAMARMCMCOMDMDAMEMNEMFMAFMGMDGMHMHLMKMKDMLMLIMMMMRMNMNGMOMACMPMNPMQMTQMRMRTMSMSRMTMLTMUMUSMVMDVMWMWIMXMEXMYMYSMZMOZNANAMNCNCLNENERNFNFKNGNGANINICNLNLDNONORNPNPLNRNRUNUNIUNZNZLOMOMNPAPANPEPERPFPYFPGPNGPHPHLPKPAKPLPOLPMSPMPNPCNPRPRIPSPSEPTPRTPWPLWPYPRYQAQATREREUROROURSSRBRURUSRWRWASASAUSBSLBSCSYCSDSDNSESWESGSGPSHSHNSISVNSJSJMSKSVKSLSLESMSMRSNSENSOSOMSRSURSSSSDSTSTPSVSLVSXSXMSYSYRSZSWZTCTCATDTCDTFATFTGTGOTHTHATJTJKTKTKLTLTLSTMTKMTNTUNTOTONTRTURTTTTOTVTUVTWTWNTZTZAUAUKRUGUGAUMUMIUSUSAUYURYUZUZBVAVATVCVCTVEVENVGVGBVIVIRVNVNMVUVUTWFWLFWSWSMYEYEMYTMYTZAZAFZMZMBZWZWE");
        }
        final String[] array = new String[Locale.isoCountries.length];
        System.arraycopy(Locale.isoCountries, 0, array, 0, Locale.isoCountries.length);
        return array;
    }
    
    public static String[] getISOLanguages() {
        if (Locale.isoLanguages == null) {
            Locale.isoLanguages = getISO2Table("aaaarababkaeaveafafrakakaamamhanargararaasasmavavaayaymazazebabakbebelbgbulbhbihbibisbmbambnbenbobodbrbrebsboscacatcechechchacocoscrcrecscescuchucvchvcycymdadandedeudvdivdzdzoeeeweelellenengeoepoesspaetesteueusfafasfffulfifinfjfijfofaofrfrafyfrygaglegdglaglglggngrngugujgvglvhahauhehebhihinhohmohrhrvhthathuhunhyhyehzheriainaidindieileigiboiiiiiikipkinindioidoisislititaiuikuiwhebjajpnjiyidjvjavkakatkgkonkikikkjkuakkkazklkalkmkhmknkankokorkrkaukskaskukurkvkomkwcorkykirlalatlbltzlgluglilimlnlinlolaoltlitlulublvlavmgmlgmhmahmimrimkmkdmlmalmnmonmomolmrmarmsmsamtmltmymyananaunbnobndndenenepngndonlnldnnnnononornrnblnvnavnynyaocociojojiomormororiososspapanpipliplpolpspusptporququermrohrnrunroronrurusrwkinsasanscsrdsdsndsesmesgsagsisinskslkslslvsmsmosnsnasosomsqsqisrsrpsssswstsotsusunsvsweswswatatamteteltgtgkththatitirtktuktltgltntsntotontrturtstsotttattwtwitytahuguigukukrururduzuzbvevenvivievovolwawlnwowolxhxhoyiyidyoyorzazhazhzhozuzul");
        }
        final String[] array = new String[Locale.isoLanguages.length];
        System.arraycopy(Locale.isoLanguages, 0, array, 0, Locale.isoLanguages.length);
        return array;
    }
    
    private static String[] getISO2Table(final String s) {
        final int n = s.length() / 5;
        final String[] array = new String[n];
        for (int i = 0, n2 = 0; i < n; ++i, n2 += 5) {
            array[i] = s.substring(n2, n2 + 2);
        }
        return array;
    }
    
    public String getLanguage() {
        return this.baseLocale.getLanguage();
    }
    
    public String getScript() {
        return this.baseLocale.getScript();
    }
    
    public String getCountry() {
        return this.baseLocale.getRegion();
    }
    
    public String getVariant() {
        return this.baseLocale.getVariant();
    }
    
    public boolean hasExtensions() {
        return this.localeExtensions != null;
    }
    
    public Locale stripExtensions() {
        return this.hasExtensions() ? getInstance(this.baseLocale, null) : this;
    }
    
    public String getExtension(final char c) {
        if (!LocaleExtensions.isValidKey(c)) {
            throw new IllegalArgumentException("Ill-formed extension key: " + c);
        }
        return this.hasExtensions() ? this.localeExtensions.getExtensionValue(c) : null;
    }
    
    public Set<Character> getExtensionKeys() {
        if (!this.hasExtensions()) {
            return Collections.emptySet();
        }
        return this.localeExtensions.getKeys();
    }
    
    public Set<String> getUnicodeLocaleAttributes() {
        if (!this.hasExtensions()) {
            return Collections.emptySet();
        }
        return this.localeExtensions.getUnicodeLocaleAttributes();
    }
    
    public String getUnicodeLocaleType(final String s) {
        if (!isUnicodeExtensionKey(s)) {
            throw new IllegalArgumentException("Ill-formed Unicode locale key: " + s);
        }
        return this.hasExtensions() ? this.localeExtensions.getUnicodeLocaleType(s) : null;
    }
    
    public Set<String> getUnicodeLocaleKeys() {
        if (this.localeExtensions == null) {
            return Collections.emptySet();
        }
        return this.localeExtensions.getUnicodeLocaleKeys();
    }
    
    BaseLocale getBaseLocale() {
        return this.baseLocale;
    }
    
    LocaleExtensions getLocaleExtensions() {
        return this.localeExtensions;
    }
    
    @Override
    public final String toString() {
        final boolean b = this.baseLocale.getLanguage().length() != 0;
        final boolean b2 = this.baseLocale.getScript().length() != 0;
        final boolean b3 = this.baseLocale.getRegion().length() != 0;
        final boolean b4 = this.baseLocale.getVariant().length() != 0;
        final boolean b5 = this.localeExtensions != null && this.localeExtensions.getID().length() != 0;
        final StringBuilder sb = new StringBuilder(this.baseLocale.getLanguage());
        if (b3 || (b && (b4 || b2 || b5))) {
            sb.append('_').append(this.baseLocale.getRegion());
        }
        if (b4 && (b || b3)) {
            sb.append('_').append(this.baseLocale.getVariant());
        }
        if (b2 && (b || b3)) {
            sb.append("_#").append(this.baseLocale.getScript());
        }
        if (b5 && (b || b3)) {
            sb.append('_');
            if (!b2) {
                sb.append('#');
            }
            sb.append(this.localeExtensions.getID());
        }
        return sb.toString();
    }
    
    public String toLanguageTag() {
        if (this.languageTag != null) {
            return this.languageTag;
        }
        final LanguageTag locale = LanguageTag.parseLocale(this.baseLocale, this.localeExtensions);
        final StringBuilder sb = new StringBuilder();
        final String language = locale.getLanguage();
        if (language.length() > 0) {
            sb.append(LanguageTag.canonicalizeLanguage(language));
        }
        final String script = locale.getScript();
        if (script.length() > 0) {
            sb.append("-");
            sb.append(LanguageTag.canonicalizeScript(script));
        }
        final String region = locale.getRegion();
        if (region.length() > 0) {
            sb.append("-");
            sb.append(LanguageTag.canonicalizeRegion(region));
        }
        for (final String s : locale.getVariants()) {
            sb.append("-");
            sb.append(s);
        }
        for (final String s2 : locale.getExtensions()) {
            sb.append("-");
            sb.append(LanguageTag.canonicalizeExtension(s2));
        }
        final String privateuse = locale.getPrivateuse();
        if (privateuse.length() > 0) {
            if (sb.length() > 0) {
                sb.append("-");
            }
            sb.append("x").append("-");
            sb.append(privateuse);
        }
        final String string = sb.toString();
        synchronized (this) {
            if (this.languageTag == null) {
                this.languageTag = string;
            }
        }
        return this.languageTag;
    }
    
    public static Locale forLanguageTag(final String s) {
        final LanguageTag parse = LanguageTag.parse(s, null);
        final InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
        internalLocaleBuilder.setLanguageTag(parse);
        final BaseLocale baseLocale = internalLocaleBuilder.getBaseLocale();
        LocaleExtensions localeExtensions = internalLocaleBuilder.getLocaleExtensions();
        if (localeExtensions == null && baseLocale.getVariant().length() > 0) {
            localeExtensions = getCompatibilityExtensions(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), baseLocale.getVariant());
        }
        return getInstance(baseLocale, localeExtensions);
    }
    
    public String getISO3Language() throws MissingResourceException {
        final String language = this.baseLocale.getLanguage();
        if (language.length() == 3) {
            return language;
        }
        final String iso3Code = getISO3Code(language, "aaaarababkaeaveafafrakakaamamhanargararaasasmavavaayaymazazebabakbebelbgbulbhbihbibisbmbambnbenbobodbrbrebsboscacatcechechchacocoscrcrecscescuchucvchvcycymdadandedeudvdivdzdzoeeeweelellenengeoepoesspaetesteueusfafasfffulfifinfjfijfofaofrfrafyfrygaglegdglaglglggngrngugujgvglvhahauhehebhihinhohmohrhrvhthathuhunhyhyehzheriainaidindieileigiboiiiiiikipkinindioidoisislititaiuikuiwhebjajpnjiyidjvjavkakatkgkonkikikkjkuakkkazklkalkmkhmknkankokorkrkaukskaskukurkvkomkwcorkykirlalatlbltzlgluglilimlnlinlolaoltlitlulublvlavmgmlgmhmahmimrimkmkdmlmalmnmonmomolmrmarmsmsamtmltmymyananaunbnobndndenenepngndonlnldnnnnononornrnblnvnavnynyaocociojojiomormororiososspapanpipliplpolpspusptporququermrohrnrunroronrurusrwkinsasanscsrdsdsndsesmesgsagsisinskslkslslvsmsmosnsnasosomsqsqisrsrpsssswstsotsusunsvsweswswatatamteteltgtgkththatitirtktuktltgltntsntotontrturtstsotttattwtwitytahuguigukukrururduzuzbvevenvivievovolwawlnwowolxhxhoyiyidyoyorzazhazhzhozuzul");
        if (iso3Code == null) {
            throw new MissingResourceException("Couldn't find 3-letter language code for " + language, "FormatData_" + this.toString(), "ShortLanguage");
        }
        return iso3Code;
    }
    
    public String getISO3Country() throws MissingResourceException {
        final String iso3Code = getISO3Code(this.baseLocale.getRegion(), "ADANDAEAREAFAFGAGATGAIAIAALALBAMARMANANTAOAGOAQATAARARGASASMATAUTAUAUSAWABWAXALAAZAZEBABIHBBBRBBDBGDBEBELBFBFABGBGRBHBHRBIBDIBJBENBLBLMBMBMUBNBRNBOBOLBQBESBRBRABSBHSBTBTNBVBVTBWBWABYBLRBZBLZCACANCCCCKCDCODCFCAFCGCOGCHCHECICIVCKCOKCLCHLCMCMRCNCHNCOCOLCRCRICUCUBCVCPVCWCUWCXCXRCYCYPCZCZEDEDEUDJDJIDKDNKDMDMADODOMDZDZAECECUEEESTEGEGYEHESHERERIESESPETETHFIFINFJFJIFKFLKFMFSMFOFROFRFRAGAGABGBGBRGDGRDGEGEOGFGUFGGGGYGHGHAGIGIBGLGRLGMGMBGNGINGPGLPGQGNQGRGRCGSSGSGTGTMGUGUMGWGNBGYGUYHKHKGHMHMDHNHNDHRHRVHTHTIHUHUNIDIDNIEIRLILISRIMIMNININDIOIOTIQIRQIRIRNISISLITITAJEJEYJMJAMJOJORJPJPNKEKENKGKGZKHKHMKIKIRKMCOMKNKNAKPPRKKRKORKWKWTKYCYMKZKAZLALAOLBLBNLCLCALILIELKLKALRLBRLSLSOLTLTULULUXLVLVALYLBYMAMARMCMCOMDMDAMEMNEMFMAFMGMDGMHMHLMKMKDMLMLIMMMMRMNMNGMOMACMPMNPMQMTQMRMRTMSMSRMTMLTMUMUSMVMDVMWMWIMXMEXMYMYSMZMOZNANAMNCNCLNENERNFNFKNGNGANINICNLNLDNONORNPNPLNRNRUNUNIUNZNZLOMOMNPAPANPEPERPFPYFPGPNGPHPHLPKPAKPLPOLPMSPMPNPCNPRPRIPSPSEPTPRTPWPLWPYPRYQAQATREREUROROURSSRBRURUSRWRWASASAUSBSLBSCSYCSDSDNSESWESGSGPSHSHNSISVNSJSJMSKSVKSLSLESMSMRSNSENSOSOMSRSURSSSSDSTSTPSVSLVSXSXMSYSYRSZSWZTCTCATDTCDTFATFTGTGOTHTHATJTJKTKTKLTLTLSTMTKMTNTUNTOTONTRTURTTTTOTVTUVTWTWNTZTZAUAUKRUGUGAUMUMIUSUSAUYURYUZUZBVAVATVCVCTVEVENVGVGBVIVIRVNVNMVUVUTWFWLFWSWSMYEYEMYTMYTZAZAFZMZMBZWZWE");
        if (iso3Code == null) {
            throw new MissingResourceException("Couldn't find 3-letter country code for " + this.baseLocale.getRegion(), "FormatData_" + this.toString(), "ShortCountry");
        }
        return iso3Code;
    }
    
    private static String getISO3Code(final String s, final String s2) {
        final int length = s.length();
        if (length == 0) {
            return "";
        }
        int i;
        final int n = i = s2.length();
        if (length == 2) {
            final char char1 = s.charAt(0);
            final char char2 = s.charAt(1);
            for (i = 0; i < n; i += 5) {
                if (s2.charAt(i) == char1 && s2.charAt(i + 1) == char2) {
                    break;
                }
            }
        }
        return (i < n) ? s2.substring(i + 2, i + 5) : null;
    }
    
    public final String getDisplayLanguage() {
        return this.getDisplayLanguage(getDefault(Category.DISPLAY));
    }
    
    public String getDisplayLanguage(final Locale locale) {
        return this.getDisplayString(this.baseLocale.getLanguage(), locale, 0);
    }
    
    public String getDisplayScript() {
        return this.getDisplayScript(getDefault(Category.DISPLAY));
    }
    
    public String getDisplayScript(final Locale locale) {
        return this.getDisplayString(this.baseLocale.getScript(), locale, 3);
    }
    
    public final String getDisplayCountry() {
        return this.getDisplayCountry(getDefault(Category.DISPLAY));
    }
    
    public String getDisplayCountry(final Locale locale) {
        return this.getDisplayString(this.baseLocale.getRegion(), locale, 1);
    }
    
    private String getDisplayString(final String s, final Locale locale, final int n) {
        if (s.length() == 0) {
            return "";
        }
        if (locale == null) {
            throw new NullPointerException();
        }
        final String s2 = LocaleServiceProviderPool.getPool(LocaleNameProvider.class).getLocalizedObject((LocaleServiceProviderPool.LocalizedObjectGetter<LocaleServiceProvider, String>)LocaleNameGetter.INSTANCE, locale, (n == 2) ? ("%%" + s) : s, n, s);
        if (s2 != null) {
            return s2;
        }
        return s;
    }
    
    public final String getDisplayVariant() {
        return this.getDisplayVariant(getDefault(Category.DISPLAY));
    }
    
    public String getDisplayVariant(final Locale locale) {
        if (this.baseLocale.getVariant().length() == 0) {
            return "";
        }
        final LocaleResources localeResources = LocaleProviderAdapter.forJRE().getLocaleResources(locale);
        return formatList(this.getDisplayVariantArray(locale), localeResources.getLocaleName("ListPattern"), localeResources.getLocaleName("ListCompositionPattern"));
    }
    
    public final String getDisplayName() {
        return this.getDisplayName(getDefault(Category.DISPLAY));
    }
    
    public String getDisplayName(final Locale locale) {
        final LocaleResources localeResources = LocaleProviderAdapter.forJRE().getLocaleResources(locale);
        final String displayLanguage = this.getDisplayLanguage(locale);
        final String displayScript = this.getDisplayScript(locale);
        final String displayCountry = this.getDisplayCountry(locale);
        final String[] displayVariantArray = this.getDisplayVariantArray(locale);
        final String localeName = localeResources.getLocaleName("DisplayNamePattern");
        final String localeName2 = localeResources.getLocaleName("ListPattern");
        final String localeName3 = localeResources.getLocaleName("ListCompositionPattern");
        if (displayLanguage.length() == 0 && displayScript.length() == 0 && displayCountry.length() == 0) {
            if (displayVariantArray.length == 0) {
                return "";
            }
            return formatList(displayVariantArray, localeName2, localeName3);
        }
        else {
            final ArrayList<String> list = new ArrayList<String>(4);
            if (displayLanguage.length() != 0) {
                list.add(displayLanguage);
            }
            if (displayScript.length() != 0) {
                list.add(displayScript);
            }
            if (displayCountry.length() != 0) {
                list.add(displayCountry);
            }
            if (displayVariantArray.length != 0) {
                list.addAll(Arrays.asList(displayVariantArray));
            }
            final String s = list.get(0);
            final int size = list.size();
            final String[] array = (size > 1) ? list.subList(1, size).toArray(new String[size - 1]) : new String[0];
            final Object[] array2 = { new Integer((array.length != 0) ? 2 : 1), s, (array.length != 0) ? formatList(array, localeName2, localeName3) : null };
            if (localeName != null) {
                return new MessageFormat(localeName).format(array2);
            }
            final StringBuilder sb = new StringBuilder();
            sb.append((String)array2[1]);
            if (array2.length > 2) {
                sb.append(" (");
                sb.append((String)array2[2]);
                sb.append(')');
            }
            return sb.toString();
        }
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }
    
    @Override
    public int hashCode() {
        int hashCodeValue = this.hashCodeValue;
        if (hashCodeValue == 0) {
            hashCodeValue = this.baseLocale.hashCode();
            if (this.localeExtensions != null) {
                hashCodeValue ^= this.localeExtensions.hashCode();
            }
            this.hashCodeValue = hashCodeValue;
        }
        return hashCodeValue;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Locale)) {
            return false;
        }
        if (!this.baseLocale.equals(((Locale)o).baseLocale)) {
            return false;
        }
        if (this.localeExtensions == null) {
            return ((Locale)o).localeExtensions == null;
        }
        return this.localeExtensions.equals(((Locale)o).localeExtensions);
    }
    
    private String[] getDisplayVariantArray(final Locale locale) {
        final StringTokenizer stringTokenizer = new StringTokenizer(this.baseLocale.getVariant(), "_");
        final String[] array = new String[stringTokenizer.countTokens()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.getDisplayString(stringTokenizer.nextToken(), locale, 2);
        }
        return array;
    }
    
    private static String formatList(String[] composeList, final String s, final String s2) {
        if (s == null || s2 == null) {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < composeList.length; ++i) {
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(composeList[i]);
            }
            return sb.toString();
        }
        if (composeList.length > 3) {
            composeList = composeList(new MessageFormat(s2), composeList);
        }
        final Object[] array = new Object[composeList.length + 1];
        System.arraycopy(composeList, 0, array, 1, composeList.length);
        array[0] = new Integer(composeList.length);
        return new MessageFormat(s).format(array);
    }
    
    private static String[] composeList(final MessageFormat messageFormat, final String[] array) {
        if (array.length <= 3) {
            return array;
        }
        final String format = messageFormat.format(new String[] { array[0], array[1] });
        final String[] array2 = new String[array.length - 1];
        System.arraycopy(array, 2, array2, 1, array2.length - 1);
        array2[0] = format;
        return composeList(messageFormat, array2);
    }
    
    private static boolean isUnicodeExtensionKey(final String s) {
        return s.length() == 2 && LocaleUtils.isAlphaNumericString(s);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final ObjectOutputStream.PutField putFields = objectOutputStream.putFields();
        putFields.put("language", this.baseLocale.getLanguage());
        putFields.put("script", this.baseLocale.getScript());
        putFields.put("country", this.baseLocale.getRegion());
        putFields.put("variant", this.baseLocale.getVariant());
        putFields.put("extensions", (this.localeExtensions == null) ? "" : this.localeExtensions.getID());
        putFields.put("hashcode", -1);
        objectOutputStream.writeFields();
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        final ObjectInputStream.GetField fields = objectInputStream.readFields();
        final String s = (String)fields.get("language", "");
        final String s2 = (String)fields.get("script", "");
        final String s3 = (String)fields.get("country", "");
        final String s4 = (String)fields.get("variant", "");
        final String extensions = (String)fields.get("extensions", "");
        this.baseLocale = BaseLocale.getInstance(convertOldISOCodes(s), s2, s3, s4);
        if (extensions.length() > 0) {
            try {
                final InternalLocaleBuilder internalLocaleBuilder = new InternalLocaleBuilder();
                internalLocaleBuilder.setExtensions(extensions);
                this.localeExtensions = internalLocaleBuilder.getLocaleExtensions();
                return;
            }
            catch (LocaleSyntaxException ex) {
                throw new IllformedLocaleException(ex.getMessage());
            }
        }
        this.localeExtensions = null;
    }
    
    private Object readResolve() throws ObjectStreamException {
        return getInstance(this.baseLocale.getLanguage(), this.baseLocale.getScript(), this.baseLocale.getRegion(), this.baseLocale.getVariant(), this.localeExtensions);
    }
    
    private static String convertOldISOCodes(String intern) {
        intern = LocaleUtils.toLowerString(intern).intern();
        if (intern == "he") {
            return "iw";
        }
        if (intern == "yi") {
            return "ji";
        }
        if (intern == "id") {
            return "in";
        }
        return intern;
    }
    
    private static LocaleExtensions getCompatibilityExtensions(final String s, final String s2, final String s3, final String s4) {
        LocaleExtensions localeExtensions = null;
        if (LocaleUtils.caseIgnoreMatch(s, "ja") && s2.length() == 0 && LocaleUtils.caseIgnoreMatch(s3, "jp") && "JP".equals(s4)) {
            localeExtensions = LocaleExtensions.CALENDAR_JAPANESE;
        }
        else if (LocaleUtils.caseIgnoreMatch(s, "th") && s2.length() == 0 && LocaleUtils.caseIgnoreMatch(s3, "th") && "TH".equals(s4)) {
            localeExtensions = LocaleExtensions.NUMBER_THAI;
        }
        return localeExtensions;
    }
    
    public static List<Locale> filter(final List<LanguageRange> list, final Collection<Locale> collection, final FilteringMode filteringMode) {
        return LocaleMatcher.filter(list, collection, filteringMode);
    }
    
    public static List<Locale> filter(final List<LanguageRange> list, final Collection<Locale> collection) {
        return filter(list, collection, FilteringMode.AUTOSELECT_FILTERING);
    }
    
    public static List<String> filterTags(final List<LanguageRange> list, final Collection<String> collection, final FilteringMode filteringMode) {
        return LocaleMatcher.filterTags(list, collection, filteringMode);
    }
    
    public static List<String> filterTags(final List<LanguageRange> list, final Collection<String> collection) {
        return filterTags(list, collection, FilteringMode.AUTOSELECT_FILTERING);
    }
    
    public static Locale lookup(final List<LanguageRange> list, final Collection<Locale> collection) {
        return LocaleMatcher.lookup(list, collection);
    }
    
    public static String lookupTag(final List<LanguageRange> list, final Collection<String> collection) {
        return LocaleMatcher.lookupTag(list, collection);
    }
    
    static {
        LOCALECACHE = new Cache();
        ENGLISH = createConstant("en", "");
        FRENCH = createConstant("fr", "");
        GERMAN = createConstant("de", "");
        ITALIAN = createConstant("it", "");
        JAPANESE = createConstant("ja", "");
        KOREAN = createConstant("ko", "");
        CHINESE = createConstant("zh", "");
        SIMPLIFIED_CHINESE = createConstant("zh", "CN");
        TRADITIONAL_CHINESE = createConstant("zh", "TW");
        FRANCE = createConstant("fr", "FR");
        GERMANY = createConstant("de", "DE");
        ITALY = createConstant("it", "IT");
        JAPAN = createConstant("ja", "JP");
        KOREA = createConstant("ko", "KR");
        CHINA = Locale.SIMPLIFIED_CHINESE;
        PRC = Locale.SIMPLIFIED_CHINESE;
        TAIWAN = Locale.TRADITIONAL_CHINESE;
        UK = createConstant("en", "GB");
        US = createConstant("en", "US");
        CANADA = createConstant("en", "CA");
        CANADA_FRENCH = createConstant("fr", "CA");
        ROOT = createConstant("", "");
        Locale.defaultLocale = initDefault();
        Locale.defaultDisplayLocale = null;
        Locale.defaultFormatLocale = null;
        serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("language", String.class), new ObjectStreamField("country", String.class), new ObjectStreamField("variant", String.class), new ObjectStreamField("hashcode", Integer.TYPE), new ObjectStreamField("script", String.class), new ObjectStreamField("extensions", String.class) };
        Locale.isoLanguages = null;
        Locale.isoCountries = null;
    }
    
    public static final class Builder
    {
        private final InternalLocaleBuilder localeBuilder;
        
        public Builder() {
            this.localeBuilder = new InternalLocaleBuilder();
        }
        
        public Builder setLocale(final Locale locale) {
            try {
                this.localeBuilder.setLocale(locale.baseLocale, locale.localeExtensions);
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
                localeExtensions = getCompatibilityExtensions(baseLocale.getLanguage(), baseLocale.getScript(), baseLocale.getRegion(), baseLocale.getVariant());
            }
            return Locale.getInstance(baseLocale, localeExtensions);
        }
    }
    
    private static class Cache extends LocaleObjectCache<LocaleKey, Locale>
    {
        @Override
        protected Locale createObject(final LocaleKey localeKey) {
            return new Locale(localeKey.base, localeKey.exts, null);
        }
    }
    
    private static final class LocaleKey
    {
        private final BaseLocale base;
        private final LocaleExtensions exts;
        private final int hash;
        
        private LocaleKey(final BaseLocale base, final LocaleExtensions exts) {
            this.base = base;
            this.exts = exts;
            int hashCode = this.base.hashCode();
            if (this.exts != null) {
                hashCode ^= this.exts.hashCode();
            }
            this.hash = hashCode;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof LocaleKey)) {
                return false;
            }
            final LocaleKey localeKey = (LocaleKey)o;
            if (this.hash != localeKey.hash || !this.base.equals(localeKey.base)) {
                return false;
            }
            if (this.exts == null) {
                return localeKey.exts == null;
            }
            return this.exts.equals(localeKey.exts);
        }
        
        @Override
        public int hashCode() {
            return this.hash;
        }
    }
    
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
    
    public enum FilteringMode
    {
        AUTOSELECT_FILTERING, 
        EXTENDED_FILTERING, 
        IGNORE_EXTENDED_RANGES, 
        MAP_EXTENDED_RANGES, 
        REJECT_EXTENDED_RANGES;
    }
    
    public static final class LanguageRange
    {
        public static final double MAX_WEIGHT = 1.0;
        public static final double MIN_WEIGHT = 0.0;
        private final String range;
        private final double weight;
        private volatile int hash;
        
        public LanguageRange(final String s) {
            this(s, 1.0);
        }
        
        public LanguageRange(String lowerCase, final double weight) {
            this.hash = 0;
            if (lowerCase == null) {
                throw new NullPointerException();
            }
            if (weight < 0.0 || weight > 1.0) {
                throw new IllegalArgumentException("weight=" + weight);
            }
            lowerCase = lowerCase.toLowerCase();
            boolean b = false;
            final String[] split = lowerCase.split("-");
            if (isSubtagIllFormed(split[0], true) || lowerCase.endsWith("-")) {
                b = true;
            }
            else {
                for (int i = 1; i < split.length; ++i) {
                    if (isSubtagIllFormed(split[i], false)) {
                        b = true;
                        break;
                    }
                }
            }
            if (b) {
                throw new IllegalArgumentException("range=" + lowerCase);
            }
            this.range = lowerCase;
            this.weight = weight;
        }
        
        private static boolean isSubtagIllFormed(final String s, final boolean b) {
            if (s.equals("") || s.length() > 8) {
                return true;
            }
            if (s.equals("*")) {
                return false;
            }
            final char[] charArray = s.toCharArray();
            if (b) {
                for (final char c : charArray) {
                    if (c < 'a' || c > 'z') {
                        return true;
                    }
                }
            }
            else {
                for (final char c2 : charArray) {
                    if (c2 < '0' || (c2 > '9' && c2 < 'a') || c2 > 'z') {
                        return true;
                    }
                }
            }
            return false;
        }
        
        public String getRange() {
            return this.range;
        }
        
        public double getWeight() {
            return this.weight;
        }
        
        public static List<LanguageRange> parse(final String s) {
            return LocaleMatcher.parse(s);
        }
        
        public static List<LanguageRange> parse(final String s, final Map<String, List<String>> map) {
            return mapEquivalents(parse(s), map);
        }
        
        public static List<LanguageRange> mapEquivalents(final List<LanguageRange> list, final Map<String, List<String>> map) {
            return LocaleMatcher.mapEquivalents(list, map);
        }
        
        @Override
        public int hashCode() {
            if (this.hash == 0) {
                final int n = 37 * 17 + this.range.hashCode();
                final long doubleToLongBits = Double.doubleToLongBits(this.weight);
                this.hash = 37 * n + (int)(doubleToLongBits ^ doubleToLongBits >>> 32);
            }
            return this.hash;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof LanguageRange)) {
                return false;
            }
            final LanguageRange languageRange = (LanguageRange)o;
            return this.hash == languageRange.hash && this.range.equals(languageRange.range) && this.weight == languageRange.weight;
        }
    }
    
    private static class LocaleNameGetter implements LocaleServiceProviderPool.LocalizedObjectGetter<LocaleNameProvider, String>
    {
        private static final LocaleNameGetter INSTANCE;
        
        @Override
        public String getObject(final LocaleNameProvider localeNameProvider, final Locale locale, final String s, final Object... array) {
            assert array.length == 2;
            final int intValue = (int)array[0];
            final String s2 = (String)array[1];
            switch (intValue) {
                case 0: {
                    return localeNameProvider.getDisplayLanguage(s2, locale);
                }
                case 1: {
                    return localeNameProvider.getDisplayCountry(s2, locale);
                }
                case 2: {
                    return localeNameProvider.getDisplayVariant(s2, locale);
                }
                case 3: {
                    return localeNameProvider.getDisplayScript(s2, locale);
                }
                default: {
                    assert false;
                    return null;
                }
            }
        }
        
        static {
            INSTANCE = new LocaleNameGetter();
        }
    }
}
