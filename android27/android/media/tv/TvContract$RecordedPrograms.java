package android.media.tv;

import android.net.*;

public static final class RecordedPrograms implements BaseTvColumns
{
    public static final String COLUMN_AUDIO_LANGUAGE = "audio_language";
    public static final String COLUMN_BROADCAST_GENRE = "broadcast_genre";
    public static final String COLUMN_CANONICAL_GENRE = "canonical_genre";
    public static final String COLUMN_CHANNEL_ID = "channel_id";
    public static final String COLUMN_CONTENT_RATING = "content_rating";
    public static final String COLUMN_END_TIME_UTC_MILLIS = "end_time_utc_millis";
    public static final String COLUMN_EPISODE_DISPLAY_NUMBER = "episode_display_number";
    public static final String COLUMN_EPISODE_TITLE = "episode_title";
    public static final String COLUMN_INPUT_ID = "input_id";
    public static final String COLUMN_INTERNAL_PROVIDER_DATA = "internal_provider_data";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG1 = "internal_provider_flag1";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG2 = "internal_provider_flag2";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG3 = "internal_provider_flag3";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG4 = "internal_provider_flag4";
    public static final String COLUMN_LONG_DESCRIPTION = "long_description";
    public static final String COLUMN_POSTER_ART_URI = "poster_art_uri";
    public static final String COLUMN_RECORDING_DATA_BYTES = "recording_data_bytes";
    public static final String COLUMN_RECORDING_DATA_URI = "recording_data_uri";
    public static final String COLUMN_RECORDING_DURATION_MILLIS = "recording_duration_millis";
    public static final String COLUMN_RECORDING_EXPIRE_TIME_UTC_MILLIS = "recording_expire_time_utc_millis";
    public static final String COLUMN_REVIEW_RATING = "review_rating";
    public static final String COLUMN_REVIEW_RATING_STYLE = "review_rating_style";
    public static final String COLUMN_SEARCHABLE = "searchable";
    public static final String COLUMN_SEASON_DISPLAY_NUMBER = "season_display_number";
    public static final String COLUMN_SEASON_TITLE = "season_title";
    public static final String COLUMN_SHORT_DESCRIPTION = "short_description";
    public static final String COLUMN_START_TIME_UTC_MILLIS = "start_time_utc_millis";
    public static final String COLUMN_THUMBNAIL_URI = "thumbnail_uri";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_VERSION_NUMBER = "version_number";
    public static final String COLUMN_VIDEO_HEIGHT = "video_height";
    public static final String COLUMN_VIDEO_WIDTH = "video_width";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/recorded_program";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/recorded_program";
    public static final Uri CONTENT_URI;
    public static final int REVIEW_RATING_STYLE_PERCENTAGE = 2;
    public static final int REVIEW_RATING_STYLE_STARS = 0;
    public static final int REVIEW_RATING_STYLE_THUMBS_UP_DOWN = 1;
    
    RecordedPrograms() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
