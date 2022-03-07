package android.media.tv;

import android.net.*;

public static final class Programs implements BaseTvColumns
{
    public static final String COLUMN_AUDIO_LANGUAGE = "audio_language";
    public static final String COLUMN_BROADCAST_GENRE = "broadcast_genre";
    public static final String COLUMN_CANONICAL_GENRE = "canonical_genre";
    public static final String COLUMN_CHANNEL_ID = "channel_id";
    public static final String COLUMN_CONTENT_RATING = "content_rating";
    public static final String COLUMN_END_TIME_UTC_MILLIS = "end_time_utc_millis";
    public static final String COLUMN_EPISODE_DISPLAY_NUMBER = "episode_display_number";
    @Deprecated
    public static final String COLUMN_EPISODE_NUMBER = "episode_number";
    public static final String COLUMN_EPISODE_TITLE = "episode_title";
    public static final String COLUMN_INTERNAL_PROVIDER_DATA = "internal_provider_data";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG1 = "internal_provider_flag1";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG2 = "internal_provider_flag2";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG3 = "internal_provider_flag3";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG4 = "internal_provider_flag4";
    public static final String COLUMN_LONG_DESCRIPTION = "long_description";
    public static final String COLUMN_POSTER_ART_URI = "poster_art_uri";
    public static final String COLUMN_RECORDING_PROHIBITED = "recording_prohibited";
    public static final String COLUMN_REVIEW_RATING = "review_rating";
    public static final String COLUMN_REVIEW_RATING_STYLE = "review_rating_style";
    public static final String COLUMN_SEARCHABLE = "searchable";
    public static final String COLUMN_SEASON_DISPLAY_NUMBER = "season_display_number";
    @Deprecated
    public static final String COLUMN_SEASON_NUMBER = "season_number";
    public static final String COLUMN_SEASON_TITLE = "season_title";
    public static final String COLUMN_SHORT_DESCRIPTION = "short_description";
    public static final String COLUMN_START_TIME_UTC_MILLIS = "start_time_utc_millis";
    public static final String COLUMN_THUMBNAIL_URI = "thumbnail_uri";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_VERSION_NUMBER = "version_number";
    public static final String COLUMN_VIDEO_HEIGHT = "video_height";
    public static final String COLUMN_VIDEO_WIDTH = "video_width";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/program";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/program";
    public static final Uri CONTENT_URI;
    public static final int REVIEW_RATING_STYLE_PERCENTAGE = 2;
    public static final int REVIEW_RATING_STYLE_STARS = 0;
    public static final int REVIEW_RATING_STYLE_THUMBS_UP_DOWN = 1;
    
    Programs() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
    
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
}
