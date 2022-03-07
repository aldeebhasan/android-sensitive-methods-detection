package android.speech.tts;

public class Engine
{
    public static final String ACTION_CHECK_TTS_DATA = "android.speech.tts.engine.CHECK_TTS_DATA";
    public static final String ACTION_GET_SAMPLE_TEXT = "android.speech.tts.engine.GET_SAMPLE_TEXT";
    public static final String ACTION_INSTALL_TTS_DATA = "android.speech.tts.engine.INSTALL_TTS_DATA";
    public static final String ACTION_TTS_DATA_INSTALLED = "android.speech.tts.engine.TTS_DATA_INSTALLED";
    @Deprecated
    public static final int CHECK_VOICE_DATA_BAD_DATA = -1;
    public static final int CHECK_VOICE_DATA_FAIL = 0;
    @Deprecated
    public static final int CHECK_VOICE_DATA_MISSING_DATA = -2;
    @Deprecated
    public static final int CHECK_VOICE_DATA_MISSING_VOLUME = -3;
    public static final int CHECK_VOICE_DATA_PASS = 1;
    public static final int DEFAULT_STREAM = 3;
    public static final String EXTRA_AVAILABLE_VOICES = "availableVoices";
    @Deprecated
    public static final String EXTRA_CHECK_VOICE_DATA_FOR = "checkVoiceDataFor";
    public static final String EXTRA_SAMPLE_TEXT = "sampleText";
    @Deprecated
    public static final String EXTRA_TTS_DATA_INSTALLED = "dataInstalled";
    public static final String EXTRA_UNAVAILABLE_VOICES = "unavailableVoices";
    @Deprecated
    public static final String EXTRA_VOICE_DATA_FILES = "dataFiles";
    @Deprecated
    public static final String EXTRA_VOICE_DATA_FILES_INFO = "dataFilesInfo";
    @Deprecated
    public static final String EXTRA_VOICE_DATA_ROOT_DIRECTORY = "dataRoot";
    public static final String INTENT_ACTION_TTS_SERVICE = "android.intent.action.TTS_SERVICE";
    @Deprecated
    public static final String KEY_FEATURE_EMBEDDED_SYNTHESIS = "embeddedTts";
    public static final String KEY_FEATURE_NETWORK_RETRIES_COUNT = "networkRetriesCount";
    @Deprecated
    public static final String KEY_FEATURE_NETWORK_SYNTHESIS = "networkTts";
    public static final String KEY_FEATURE_NETWORK_TIMEOUT_MS = "networkTimeoutMs";
    public static final String KEY_FEATURE_NOT_INSTALLED = "notInstalled";
    public static final String KEY_PARAM_PAN = "pan";
    public static final String KEY_PARAM_SESSION_ID = "sessionId";
    public static final String KEY_PARAM_STREAM = "streamType";
    public static final String KEY_PARAM_UTTERANCE_ID = "utteranceId";
    public static final String KEY_PARAM_VOLUME = "volume";
    public static final String SERVICE_META_DATA = "android.speech.tts";
    
    public Engine() {
        throw new RuntimeException("Stub!");
    }
}
