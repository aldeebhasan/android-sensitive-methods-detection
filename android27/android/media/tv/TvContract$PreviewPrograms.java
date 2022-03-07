package android.media.tv;

import android.net.*;

public static final class PreviewPrograms implements BaseTvColumns
{
    public static final int ASPECT_RATIO_16_9 = 0;
    public static final int ASPECT_RATIO_1_1 = 3;
    public static final int ASPECT_RATIO_2_3 = 4;
    public static final int ASPECT_RATIO_3_2 = 1;
    public static final int ASPECT_RATIO_4_3 = 2;
    public static final int AVAILABILITY_AVAILABLE = 0;
    public static final int AVAILABILITY_FREE_WITH_SUBSCRIPTION = 1;
    public static final int AVAILABILITY_PAID_CONTENT = 2;
    public static final String COLUMN_AUDIO_LANGUAGE = "audio_language";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_AVAILABILITY = "availability";
    public static final String COLUMN_BROWSABLE = "browsable";
    public static final String COLUMN_CANONICAL_GENRE = "canonical_genre";
    public static final String COLUMN_CHANNEL_ID = "channel_id";
    public static final String COLUMN_CONTENT_ID = "content_id";
    public static final String COLUMN_CONTENT_RATING = "content_rating";
    public static final String COLUMN_DURATION_MILLIS = "duration_millis";
    public static final String COLUMN_EPISODE_DISPLAY_NUMBER = "episode_display_number";
    public static final String COLUMN_EPISODE_TITLE = "episode_title";
    public static final String COLUMN_INTENT_URI = "intent_uri";
    public static final String COLUMN_INTERACTION_COUNT = "interaction_count";
    public static final String COLUMN_INTERACTION_TYPE = "interaction_type";
    public static final String COLUMN_INTERNAL_PROVIDER_DATA = "internal_provider_data";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG1 = "internal_provider_flag1";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG2 = "internal_provider_flag2";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG3 = "internal_provider_flag3";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG4 = "internal_provider_flag4";
    public static final String COLUMN_INTERNAL_PROVIDER_ID = "internal_provider_id";
    public static final String COLUMN_ITEM_COUNT = "item_count";
    public static final String COLUMN_LAST_PLAYBACK_POSITION_MILLIS = "last_playback_position_millis";
    public static final String COLUMN_LIVE = "live";
    public static final String COLUMN_LOGO_URI = "logo_uri";
    public static final String COLUMN_LONG_DESCRIPTION = "long_description";
    public static final String COLUMN_OFFER_PRICE = "offer_price";
    public static final String COLUMN_POSTER_ART_ASPECT_RATIO = "poster_art_aspect_ratio";
    public static final String COLUMN_POSTER_ART_URI = "poster_art_uri";
    public static final String COLUMN_PREVIEW_VIDEO_URI = "preview_video_uri";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_REVIEW_RATING = "review_rating";
    public static final String COLUMN_REVIEW_RATING_STYLE = "review_rating_style";
    public static final String COLUMN_SEARCHABLE = "searchable";
    public static final String COLUMN_SEASON_DISPLAY_NUMBER = "season_display_number";
    public static final String COLUMN_SEASON_TITLE = "season_title";
    public static final String COLUMN_SHORT_DESCRIPTION = "short_description";
    public static final String COLUMN_STARTING_PRICE = "starting_price";
    public static final String COLUMN_THUMBNAIL_ASPECT_RATIO = "poster_thumbnail_aspect_ratio";
    public static final String COLUMN_THUMBNAIL_URI = "thumbnail_uri";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TRANSIENT = "transient";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_VERSION_NUMBER = "version_number";
    public static final String COLUMN_VIDEO_HEIGHT = "video_height";
    public static final String COLUMN_VIDEO_WIDTH = "video_width";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/preview_program";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/preview_program";
    public static final Uri CONTENT_URI;
    public static final int INTERACTION_TYPE_FANS = 3;
    public static final int INTERACTION_TYPE_FOLLOWERS = 2;
    public static final int INTERACTION_TYPE_LIKES = 4;
    public static final int INTERACTION_TYPE_LISTENS = 1;
    public static final int INTERACTION_TYPE_THUMBS = 5;
    public static final int INTERACTION_TYPE_VIEWERS = 6;
    public static final int INTERACTION_TYPE_VIEWS = 0;
    public static final int REVIEW_RATING_STYLE_PERCENTAGE = 2;
    public static final int REVIEW_RATING_STYLE_STARS = 0;
    public static final int REVIEW_RATING_STYLE_THUMBS_UP_DOWN = 1;
    public static final int TYPE_ALBUM = 8;
    public static final int TYPE_ARTIST = 9;
    public static final int TYPE_CHANNEL = 6;
    public static final int TYPE_CLIP = 4;
    public static final int TYPE_EVENT = 5;
    public static final int TYPE_MOVIE = 0;
    public static final int TYPE_PLAYLIST = 10;
    public static final int TYPE_STATION = 11;
    public static final int TYPE_TRACK = 7;
    public static final int TYPE_TV_EPISODE = 3;
    public static final int TYPE_TV_SEASON = 2;
    public static final int TYPE_TV_SERIES = 1;
    
    PreviewPrograms() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
}
