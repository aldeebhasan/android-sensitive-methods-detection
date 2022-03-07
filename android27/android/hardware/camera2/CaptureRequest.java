package android.hardware.camera2;

import android.location.*;
import android.util.*;
import android.graphics.*;
import android.hardware.camera2.params.*;
import java.util.*;
import android.os.*;
import android.view.*;

public final class CaptureRequest extends CameraMetadata<Key<?>> implements Parcelable
{
    public static final Key<Boolean> BLACK_LEVEL_LOCK;
    public static final Key<Integer> COLOR_CORRECTION_ABERRATION_MODE;
    public static final Key<RggbChannelVector> COLOR_CORRECTION_GAINS;
    public static final Key<Integer> COLOR_CORRECTION_MODE;
    public static final Key<ColorSpaceTransform> COLOR_CORRECTION_TRANSFORM;
    public static final Key<Integer> CONTROL_AE_ANTIBANDING_MODE;
    public static final Key<Integer> CONTROL_AE_EXPOSURE_COMPENSATION;
    public static final Key<Boolean> CONTROL_AE_LOCK;
    public static final Key<Integer> CONTROL_AE_MODE;
    public static final Key<Integer> CONTROL_AE_PRECAPTURE_TRIGGER;
    public static final Key<MeteringRectangle[]> CONTROL_AE_REGIONS;
    public static final Key<Range<Integer>> CONTROL_AE_TARGET_FPS_RANGE;
    public static final Key<Integer> CONTROL_AF_MODE;
    public static final Key<MeteringRectangle[]> CONTROL_AF_REGIONS;
    public static final Key<Integer> CONTROL_AF_TRIGGER;
    public static final Key<Boolean> CONTROL_AWB_LOCK;
    public static final Key<Integer> CONTROL_AWB_MODE;
    public static final Key<MeteringRectangle[]> CONTROL_AWB_REGIONS;
    public static final Key<Integer> CONTROL_CAPTURE_INTENT;
    public static final Key<Integer> CONTROL_EFFECT_MODE;
    public static final Key<Boolean> CONTROL_ENABLE_ZSL;
    public static final Key<Integer> CONTROL_MODE;
    public static final Key<Integer> CONTROL_POST_RAW_SENSITIVITY_BOOST;
    public static final Key<Integer> CONTROL_SCENE_MODE;
    public static final Key<Integer> CONTROL_VIDEO_STABILIZATION_MODE;
    public static final Creator<CaptureRequest> CREATOR;
    public static final Key<Integer> EDGE_MODE;
    public static final Key<Integer> FLASH_MODE;
    public static final Key<Integer> HOT_PIXEL_MODE;
    public static final Key<Location> JPEG_GPS_LOCATION;
    public static final Key<Integer> JPEG_ORIENTATION;
    public static final Key<Byte> JPEG_QUALITY;
    public static final Key<Byte> JPEG_THUMBNAIL_QUALITY;
    public static final Key<Size> JPEG_THUMBNAIL_SIZE;
    public static final Key<Float> LENS_APERTURE;
    public static final Key<Float> LENS_FILTER_DENSITY;
    public static final Key<Float> LENS_FOCAL_LENGTH;
    public static final Key<Float> LENS_FOCUS_DISTANCE;
    public static final Key<Integer> LENS_OPTICAL_STABILIZATION_MODE;
    public static final Key<Integer> NOISE_REDUCTION_MODE;
    public static final Key<Float> REPROCESS_EFFECTIVE_EXPOSURE_FACTOR;
    public static final Key<Rect> SCALER_CROP_REGION;
    public static final Key<Long> SENSOR_EXPOSURE_TIME;
    public static final Key<Long> SENSOR_FRAME_DURATION;
    public static final Key<Integer> SENSOR_SENSITIVITY;
    public static final Key<int[]> SENSOR_TEST_PATTERN_DATA;
    public static final Key<Integer> SENSOR_TEST_PATTERN_MODE;
    public static final Key<Integer> SHADING_MODE;
    public static final Key<Integer> STATISTICS_FACE_DETECT_MODE;
    public static final Key<Boolean> STATISTICS_HOT_PIXEL_MAP_MODE;
    public static final Key<Integer> STATISTICS_LENS_SHADING_MAP_MODE;
    public static final Key<TonemapCurve> TONEMAP_CURVE;
    public static final Key<Float> TONEMAP_GAMMA;
    public static final Key<Integer> TONEMAP_MODE;
    public static final Key<Integer> TONEMAP_PRESET_CURVE;
    
    CaptureRequest() {
        throw new RuntimeException("Stub!");
    }
    
    public <T> T get(final Key<T> key) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<Key<?>> getKeys() {
        throw new RuntimeException("Stub!");
    }
    
    public Object getTag() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isReprocess() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object other) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        BLACK_LEVEL_LOCK = null;
        COLOR_CORRECTION_ABERRATION_MODE = null;
        COLOR_CORRECTION_GAINS = null;
        COLOR_CORRECTION_MODE = null;
        COLOR_CORRECTION_TRANSFORM = null;
        CONTROL_AE_ANTIBANDING_MODE = null;
        CONTROL_AE_EXPOSURE_COMPENSATION = null;
        CONTROL_AE_LOCK = null;
        CONTROL_AE_MODE = null;
        CONTROL_AE_PRECAPTURE_TRIGGER = null;
        CONTROL_AE_REGIONS = null;
        CONTROL_AE_TARGET_FPS_RANGE = null;
        CONTROL_AF_MODE = null;
        CONTROL_AF_REGIONS = null;
        CONTROL_AF_TRIGGER = null;
        CONTROL_AWB_LOCK = null;
        CONTROL_AWB_MODE = null;
        CONTROL_AWB_REGIONS = null;
        CONTROL_CAPTURE_INTENT = null;
        CONTROL_EFFECT_MODE = null;
        CONTROL_ENABLE_ZSL = null;
        CONTROL_MODE = null;
        CONTROL_POST_RAW_SENSITIVITY_BOOST = null;
        CONTROL_SCENE_MODE = null;
        CONTROL_VIDEO_STABILIZATION_MODE = null;
        CREATOR = null;
        EDGE_MODE = null;
        FLASH_MODE = null;
        HOT_PIXEL_MODE = null;
        JPEG_GPS_LOCATION = null;
        JPEG_ORIENTATION = null;
        JPEG_QUALITY = null;
        JPEG_THUMBNAIL_QUALITY = null;
        JPEG_THUMBNAIL_SIZE = null;
        LENS_APERTURE = null;
        LENS_FILTER_DENSITY = null;
        LENS_FOCAL_LENGTH = null;
        LENS_FOCUS_DISTANCE = null;
        LENS_OPTICAL_STABILIZATION_MODE = null;
        NOISE_REDUCTION_MODE = null;
        REPROCESS_EFFECTIVE_EXPOSURE_FACTOR = null;
        SCALER_CROP_REGION = null;
        SENSOR_EXPOSURE_TIME = null;
        SENSOR_FRAME_DURATION = null;
        SENSOR_SENSITIVITY = null;
        SENSOR_TEST_PATTERN_DATA = null;
        SENSOR_TEST_PATTERN_MODE = null;
        SHADING_MODE = null;
        STATISTICS_FACE_DETECT_MODE = null;
        STATISTICS_HOT_PIXEL_MAP_MODE = null;
        STATISTICS_LENS_SHADING_MAP_MODE = null;
        TONEMAP_CURVE = null;
        TONEMAP_GAMMA = null;
        TONEMAP_MODE = null;
        TONEMAP_PRESET_CURVE = null;
    }
    
    public static final class Key<T>
    {
        Key() {
            throw new RuntimeException("Stub!");
        }
        
        public String getName() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final int hashCode() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public final boolean equals(final Object o) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public String toString() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static final class Builder
    {
        Builder() {
            throw new RuntimeException("Stub!");
        }
        
        public void addTarget(final Surface outputTarget) {
            throw new RuntimeException("Stub!");
        }
        
        public void removeTarget(final Surface outputTarget) {
            throw new RuntimeException("Stub!");
        }
        
        public <T> void set(final Key<T> key, final T value) {
            throw new RuntimeException("Stub!");
        }
        
        public <T> T get(final Key<T> key) {
            throw new RuntimeException("Stub!");
        }
        
        public void setTag(final Object tag) {
            throw new RuntimeException("Stub!");
        }
        
        public CaptureRequest build() {
            throw new RuntimeException("Stub!");
        }
    }
}
