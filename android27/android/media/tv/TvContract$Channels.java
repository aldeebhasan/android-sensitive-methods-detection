package android.media.tv;

import android.net.*;

public static final class Channels implements BaseTvColumns
{
    public static final String COLUMN_APP_LINK_COLOR = "app_link_color";
    public static final String COLUMN_APP_LINK_ICON_URI = "app_link_icon_uri";
    public static final String COLUMN_APP_LINK_INTENT_URI = "app_link_intent_uri";
    public static final String COLUMN_APP_LINK_POSTER_ART_URI = "app_link_poster_art_uri";
    public static final String COLUMN_APP_LINK_TEXT = "app_link_text";
    public static final String COLUMN_BROWSABLE = "browsable";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DISPLAY_NAME = "display_name";
    public static final String COLUMN_DISPLAY_NUMBER = "display_number";
    public static final String COLUMN_INPUT_ID = "input_id";
    public static final String COLUMN_INTERNAL_PROVIDER_DATA = "internal_provider_data";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG1 = "internal_provider_flag1";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG2 = "internal_provider_flag2";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG3 = "internal_provider_flag3";
    public static final String COLUMN_INTERNAL_PROVIDER_FLAG4 = "internal_provider_flag4";
    public static final String COLUMN_INTERNAL_PROVIDER_ID = "internal_provider_id";
    public static final String COLUMN_LOCKED = "locked";
    public static final String COLUMN_NETWORK_AFFILIATION = "network_affiliation";
    public static final String COLUMN_ORIGINAL_NETWORK_ID = "original_network_id";
    public static final String COLUMN_SEARCHABLE = "searchable";
    public static final String COLUMN_SERVICE_ID = "service_id";
    public static final String COLUMN_SERVICE_TYPE = "service_type";
    public static final String COLUMN_TRANSIENT = "transient";
    public static final String COLUMN_TRANSPORT_STREAM_ID = "transport_stream_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_VERSION_NUMBER = "version_number";
    public static final String COLUMN_VIDEO_FORMAT = "video_format";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/channel";
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/channel";
    public static final Uri CONTENT_URI;
    public static final String SERVICE_TYPE_AUDIO = "SERVICE_TYPE_AUDIO";
    public static final String SERVICE_TYPE_AUDIO_VIDEO = "SERVICE_TYPE_AUDIO_VIDEO";
    public static final String SERVICE_TYPE_OTHER = "SERVICE_TYPE_OTHER";
    public static final String TYPE_1SEG = "TYPE_1SEG";
    public static final String TYPE_ATSC_C = "TYPE_ATSC_C";
    public static final String TYPE_ATSC_M_H = "TYPE_ATSC_M_H";
    public static final String TYPE_ATSC_T = "TYPE_ATSC_T";
    public static final String TYPE_CMMB = "TYPE_CMMB";
    public static final String TYPE_DTMB = "TYPE_DTMB";
    public static final String TYPE_DVB_C = "TYPE_DVB_C";
    public static final String TYPE_DVB_C2 = "TYPE_DVB_C2";
    public static final String TYPE_DVB_H = "TYPE_DVB_H";
    public static final String TYPE_DVB_S = "TYPE_DVB_S";
    public static final String TYPE_DVB_S2 = "TYPE_DVB_S2";
    public static final String TYPE_DVB_SH = "TYPE_DVB_SH";
    public static final String TYPE_DVB_T = "TYPE_DVB_T";
    public static final String TYPE_DVB_T2 = "TYPE_DVB_T2";
    public static final String TYPE_ISDB_C = "TYPE_ISDB_C";
    public static final String TYPE_ISDB_S = "TYPE_ISDB_S";
    public static final String TYPE_ISDB_T = "TYPE_ISDB_T";
    public static final String TYPE_ISDB_TB = "TYPE_ISDB_TB";
    public static final String TYPE_NTSC = "TYPE_NTSC";
    public static final String TYPE_OTHER = "TYPE_OTHER";
    public static final String TYPE_PAL = "TYPE_PAL";
    public static final String TYPE_PREVIEW = "TYPE_PREVIEW";
    public static final String TYPE_SECAM = "TYPE_SECAM";
    public static final String TYPE_S_DMB = "TYPE_S_DMB";
    public static final String TYPE_T_DMB = "TYPE_T_DMB";
    public static final String VIDEO_FORMAT_1080I = "VIDEO_FORMAT_1080I";
    public static final String VIDEO_FORMAT_1080P = "VIDEO_FORMAT_1080P";
    public static final String VIDEO_FORMAT_2160P = "VIDEO_FORMAT_2160P";
    public static final String VIDEO_FORMAT_240P = "VIDEO_FORMAT_240P";
    public static final String VIDEO_FORMAT_360P = "VIDEO_FORMAT_360P";
    public static final String VIDEO_FORMAT_4320P = "VIDEO_FORMAT_4320P";
    public static final String VIDEO_FORMAT_480I = "VIDEO_FORMAT_480I";
    public static final String VIDEO_FORMAT_480P = "VIDEO_FORMAT_480P";
    public static final String VIDEO_FORMAT_576I = "VIDEO_FORMAT_576I";
    public static final String VIDEO_FORMAT_576P = "VIDEO_FORMAT_576P";
    public static final String VIDEO_FORMAT_720P = "VIDEO_FORMAT_720P";
    public static final String VIDEO_RESOLUTION_ED = "VIDEO_RESOLUTION_ED";
    public static final String VIDEO_RESOLUTION_FHD = "VIDEO_RESOLUTION_FHD";
    public static final String VIDEO_RESOLUTION_HD = "VIDEO_RESOLUTION_HD";
    public static final String VIDEO_RESOLUTION_SD = "VIDEO_RESOLUTION_SD";
    public static final String VIDEO_RESOLUTION_UHD = "VIDEO_RESOLUTION_UHD";
    
    Channels() {
        throw new RuntimeException("Stub!");
    }
    
    public static final String getVideoResolution(final String videoFormat) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        CONTENT_URI = null;
    }
    
    public static final class Logo
    {
        public static final String CONTENT_DIRECTORY = "logo";
        
        Logo() {
            throw new RuntimeException("Stub!");
        }
    }
}
