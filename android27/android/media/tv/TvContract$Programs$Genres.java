package android.media.tv;

public static final class Genres
{
    public static final String ANIMAL_WILDLIFE = "ANIMAL_WILDLIFE";
    public static final String ARTS = "ARTS";
    public static final String COMEDY = "COMEDY";
    public static final String DRAMA = "DRAMA";
    public static final String EDUCATION = "EDUCATION";
    public static final String ENTERTAINMENT = "ENTERTAINMENT";
    public static final String FAMILY_KIDS = "FAMILY_KIDS";
    public static final String GAMING = "GAMING";
    public static final String LIFE_STYLE = "LIFE_STYLE";
    public static final String MOVIES = "MOVIES";
    public static final String MUSIC = "MUSIC";
    public static final String NEWS = "NEWS";
    public static final String PREMIER = "PREMIER";
    public static final String SHOPPING = "SHOPPING";
    public static final String SPORTS = "SPORTS";
    public static final String TECH_SCIENCE = "TECH_SCIENCE";
    public static final String TRAVEL = "TRAVEL";
    
    Genres() {
        throw new RuntimeException("Stub!");
    }
    
    public static String encode(final String... genres) {
        throw new RuntimeException("Stub!");
    }
    
    public static String[] decode(final String genres) {
        throw new RuntimeException("Stub!");
    }
    
    public static boolean isCanonical(final String genre) {
        throw new RuntimeException("Stub!");
    }
}
