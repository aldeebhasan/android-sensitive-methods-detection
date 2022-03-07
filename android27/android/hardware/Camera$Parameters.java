package android.hardware;

import java.util.*;

@Deprecated
public class Parameters
{
    public static final String ANTIBANDING_50HZ = "50hz";
    public static final String ANTIBANDING_60HZ = "60hz";
    public static final String ANTIBANDING_AUTO = "auto";
    public static final String ANTIBANDING_OFF = "off";
    public static final String EFFECT_AQUA = "aqua";
    public static final String EFFECT_BLACKBOARD = "blackboard";
    public static final String EFFECT_MONO = "mono";
    public static final String EFFECT_NEGATIVE = "negative";
    public static final String EFFECT_NONE = "none";
    public static final String EFFECT_POSTERIZE = "posterize";
    public static final String EFFECT_SEPIA = "sepia";
    public static final String EFFECT_SOLARIZE = "solarize";
    public static final String EFFECT_WHITEBOARD = "whiteboard";
    public static final String FLASH_MODE_AUTO = "auto";
    public static final String FLASH_MODE_OFF = "off";
    public static final String FLASH_MODE_ON = "on";
    public static final String FLASH_MODE_RED_EYE = "red-eye";
    public static final String FLASH_MODE_TORCH = "torch";
    public static final int FOCUS_DISTANCE_FAR_INDEX = 2;
    public static final int FOCUS_DISTANCE_NEAR_INDEX = 0;
    public static final int FOCUS_DISTANCE_OPTIMAL_INDEX = 1;
    public static final String FOCUS_MODE_AUTO = "auto";
    public static final String FOCUS_MODE_CONTINUOUS_PICTURE = "continuous-picture";
    public static final String FOCUS_MODE_CONTINUOUS_VIDEO = "continuous-video";
    public static final String FOCUS_MODE_EDOF = "edof";
    public static final String FOCUS_MODE_FIXED = "fixed";
    public static final String FOCUS_MODE_INFINITY = "infinity";
    public static final String FOCUS_MODE_MACRO = "macro";
    public static final int PREVIEW_FPS_MAX_INDEX = 1;
    public static final int PREVIEW_FPS_MIN_INDEX = 0;
    public static final String SCENE_MODE_ACTION = "action";
    public static final String SCENE_MODE_AUTO = "auto";
    public static final String SCENE_MODE_BARCODE = "barcode";
    public static final String SCENE_MODE_BEACH = "beach";
    public static final String SCENE_MODE_CANDLELIGHT = "candlelight";
    public static final String SCENE_MODE_FIREWORKS = "fireworks";
    public static final String SCENE_MODE_HDR = "hdr";
    public static final String SCENE_MODE_LANDSCAPE = "landscape";
    public static final String SCENE_MODE_NIGHT = "night";
    public static final String SCENE_MODE_NIGHT_PORTRAIT = "night-portrait";
    public static final String SCENE_MODE_PARTY = "party";
    public static final String SCENE_MODE_PORTRAIT = "portrait";
    public static final String SCENE_MODE_SNOW = "snow";
    public static final String SCENE_MODE_SPORTS = "sports";
    public static final String SCENE_MODE_STEADYPHOTO = "steadyphoto";
    public static final String SCENE_MODE_SUNSET = "sunset";
    public static final String SCENE_MODE_THEATRE = "theatre";
    public static final String WHITE_BALANCE_AUTO = "auto";
    public static final String WHITE_BALANCE_CLOUDY_DAYLIGHT = "cloudy-daylight";
    public static final String WHITE_BALANCE_DAYLIGHT = "daylight";
    public static final String WHITE_BALANCE_FLUORESCENT = "fluorescent";
    public static final String WHITE_BALANCE_INCANDESCENT = "incandescent";
    public static final String WHITE_BALANCE_SHADE = "shade";
    public static final String WHITE_BALANCE_TWILIGHT = "twilight";
    public static final String WHITE_BALANCE_WARM_FLUORESCENT = "warm-fluorescent";
    
    Parameters() {
        throw new RuntimeException("Stub!");
    }
    
    public String flatten() {
        throw new RuntimeException("Stub!");
    }
    
    public void unflatten(final String flattened) {
        throw new RuntimeException("Stub!");
    }
    
    public void remove(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final String key, final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final String key, final int value) {
        throw new RuntimeException("Stub!");
    }
    
    public String get(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public int getInt(final String key) {
        throw new RuntimeException("Stub!");
    }
    
    public void setPreviewSize(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public Size getPreviewSize() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Size> getSupportedPreviewSizes() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Size> getSupportedVideoSizes() {
        throw new RuntimeException("Stub!");
    }
    
    public Size getPreferredPreviewSizeForVideo() {
        throw new RuntimeException("Stub!");
    }
    
    public void setJpegThumbnailSize(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public Size getJpegThumbnailSize() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Size> getSupportedJpegThumbnailSizes() {
        throw new RuntimeException("Stub!");
    }
    
    public void setJpegThumbnailQuality(final int quality) {
        throw new RuntimeException("Stub!");
    }
    
    public int getJpegThumbnailQuality() {
        throw new RuntimeException("Stub!");
    }
    
    public void setJpegQuality(final int quality) {
        throw new RuntimeException("Stub!");
    }
    
    public int getJpegQuality() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void setPreviewFrameRate(final int fps) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int getPreviewFrameRate() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public List<Integer> getSupportedPreviewFrameRates() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPreviewFpsRange(final int min, final int max) {
        throw new RuntimeException("Stub!");
    }
    
    public void getPreviewFpsRange(final int[] range) {
        throw new RuntimeException("Stub!");
    }
    
    public List<int[]> getSupportedPreviewFpsRange() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPreviewFormat(final int pixel_format) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPreviewFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Integer> getSupportedPreviewFormats() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPictureSize(final int width, final int height) {
        throw new RuntimeException("Stub!");
    }
    
    public Size getPictureSize() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Size> getSupportedPictureSizes() {
        throw new RuntimeException("Stub!");
    }
    
    public void setPictureFormat(final int pixel_format) {
        throw new RuntimeException("Stub!");
    }
    
    public int getPictureFormat() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Integer> getSupportedPictureFormats() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRotation(final int rotation) {
        throw new RuntimeException("Stub!");
    }
    
    public void setGpsLatitude(final double latitude) {
        throw new RuntimeException("Stub!");
    }
    
    public void setGpsLongitude(final double longitude) {
        throw new RuntimeException("Stub!");
    }
    
    public void setGpsAltitude(final double altitude) {
        throw new RuntimeException("Stub!");
    }
    
    public void setGpsTimestamp(final long timestamp) {
        throw new RuntimeException("Stub!");
    }
    
    public void setGpsProcessingMethod(final String processing_method) {
        throw new RuntimeException("Stub!");
    }
    
    public void removeGpsData() {
        throw new RuntimeException("Stub!");
    }
    
    public String getWhiteBalance() {
        throw new RuntimeException("Stub!");
    }
    
    public void setWhiteBalance(final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getSupportedWhiteBalance() {
        throw new RuntimeException("Stub!");
    }
    
    public String getColorEffect() {
        throw new RuntimeException("Stub!");
    }
    
    public void setColorEffect(final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getSupportedColorEffects() {
        throw new RuntimeException("Stub!");
    }
    
    public String getAntibanding() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAntibanding(final String antibanding) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getSupportedAntibanding() {
        throw new RuntimeException("Stub!");
    }
    
    public String getSceneMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setSceneMode(final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getSupportedSceneModes() {
        throw new RuntimeException("Stub!");
    }
    
    public String getFlashMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFlashMode(final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getSupportedFlashModes() {
        throw new RuntimeException("Stub!");
    }
    
    public String getFocusMode() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFocusMode(final String value) {
        throw new RuntimeException("Stub!");
    }
    
    public List<String> getSupportedFocusModes() {
        throw new RuntimeException("Stub!");
    }
    
    public float getFocalLength() {
        throw new RuntimeException("Stub!");
    }
    
    public float getHorizontalViewAngle() {
        throw new RuntimeException("Stub!");
    }
    
    public float getVerticalViewAngle() {
        throw new RuntimeException("Stub!");
    }
    
    public int getExposureCompensation() {
        throw new RuntimeException("Stub!");
    }
    
    public void setExposureCompensation(final int value) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxExposureCompensation() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMinExposureCompensation() {
        throw new RuntimeException("Stub!");
    }
    
    public float getExposureCompensationStep() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAutoExposureLock(final boolean toggle) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getAutoExposureLock() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAutoExposureLockSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public void setAutoWhiteBalanceLock(final boolean toggle) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getAutoWhiteBalanceLock() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isAutoWhiteBalanceLockSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public int getZoom() {
        throw new RuntimeException("Stub!");
    }
    
    public void setZoom(final int value) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isZoomSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxZoom() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Integer> getZoomRatios() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isSmoothZoomSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public void getFocusDistances(final float[] output) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxNumFocusAreas() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Area> getFocusAreas() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFocusAreas(final List<Area> focusAreas) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxNumMeteringAreas() {
        throw new RuntimeException("Stub!");
    }
    
    public List<Area> getMeteringAreas() {
        throw new RuntimeException("Stub!");
    }
    
    public void setMeteringAreas(final List<Area> meteringAreas) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaxNumDetectedFaces() {
        throw new RuntimeException("Stub!");
    }
    
    public void setRecordingHint(final boolean hint) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVideoSnapshotSupported() {
        throw new RuntimeException("Stub!");
    }
    
    public void setVideoStabilization(final boolean toggle) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getVideoStabilization() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isVideoStabilizationSupported() {
        throw new RuntimeException("Stub!");
    }
}
