package android.hardware.camera2;

import android.hardware.camera2.params.*;
import android.graphics.*;
import android.util.*;
import java.util.*;

public final class CameraCharacteristics extends CameraMetadata<Key<?>>
{
    public static final Key<int[]> COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES;
    public static final Key<int[]> CONTROL_AE_AVAILABLE_ANTIBANDING_MODES;
    public static final Key<int[]> CONTROL_AE_AVAILABLE_MODES;
    public static final Key<Range<Integer>[]> CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES;
    public static final Key<Range<Integer>> CONTROL_AE_COMPENSATION_RANGE;
    public static final Key<Rational> CONTROL_AE_COMPENSATION_STEP;
    public static final Key<Boolean> CONTROL_AE_LOCK_AVAILABLE;
    public static final Key<int[]> CONTROL_AF_AVAILABLE_MODES;
    public static final Key<int[]> CONTROL_AVAILABLE_EFFECTS;
    public static final Key<int[]> CONTROL_AVAILABLE_MODES;
    public static final Key<int[]> CONTROL_AVAILABLE_SCENE_MODES;
    public static final Key<int[]> CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES;
    public static final Key<int[]> CONTROL_AWB_AVAILABLE_MODES;
    public static final Key<Boolean> CONTROL_AWB_LOCK_AVAILABLE;
    public static final Key<Integer> CONTROL_MAX_REGIONS_AE;
    public static final Key<Integer> CONTROL_MAX_REGIONS_AF;
    public static final Key<Integer> CONTROL_MAX_REGIONS_AWB;
    public static final Key<Range<Integer>> CONTROL_POST_RAW_SENSITIVITY_BOOST_RANGE;
    public static final Key<Boolean> DEPTH_DEPTH_IS_EXCLUSIVE;
    public static final Key<int[]> EDGE_AVAILABLE_EDGE_MODES;
    public static final Key<Boolean> FLASH_INFO_AVAILABLE;
    public static final Key<int[]> HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES;
    public static final Key<Integer> INFO_SUPPORTED_HARDWARE_LEVEL;
    public static final Key<Size[]> JPEG_AVAILABLE_THUMBNAIL_SIZES;
    public static final Key<Integer> LENS_FACING;
    public static final Key<float[]> LENS_INFO_AVAILABLE_APERTURES;
    public static final Key<float[]> LENS_INFO_AVAILABLE_FILTER_DENSITIES;
    public static final Key<float[]> LENS_INFO_AVAILABLE_FOCAL_LENGTHS;
    public static final Key<int[]> LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION;
    public static final Key<Integer> LENS_INFO_FOCUS_DISTANCE_CALIBRATION;
    public static final Key<Float> LENS_INFO_HYPERFOCAL_DISTANCE;
    public static final Key<Float> LENS_INFO_MINIMUM_FOCUS_DISTANCE;
    public static final Key<float[]> LENS_INTRINSIC_CALIBRATION;
    public static final Key<float[]> LENS_POSE_ROTATION;
    public static final Key<float[]> LENS_POSE_TRANSLATION;
    public static final Key<float[]> LENS_RADIAL_DISTORTION;
    public static final Key<int[]> NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES;
    public static final Key<Integer> REPROCESS_MAX_CAPTURE_STALL;
    public static final Key<int[]> REQUEST_AVAILABLE_CAPABILITIES;
    public static final Key<Integer> REQUEST_MAX_NUM_INPUT_STREAMS;
    public static final Key<Integer> REQUEST_MAX_NUM_OUTPUT_PROC;
    public static final Key<Integer> REQUEST_MAX_NUM_OUTPUT_PROC_STALLING;
    public static final Key<Integer> REQUEST_MAX_NUM_OUTPUT_RAW;
    public static final Key<Integer> REQUEST_PARTIAL_RESULT_COUNT;
    public static final Key<Byte> REQUEST_PIPELINE_MAX_DEPTH;
    public static final Key<Float> SCALER_AVAILABLE_MAX_DIGITAL_ZOOM;
    public static final Key<Integer> SCALER_CROPPING_TYPE;
    public static final Key<StreamConfigurationMap> SCALER_STREAM_CONFIGURATION_MAP;
    public static final Key<int[]> SENSOR_AVAILABLE_TEST_PATTERN_MODES;
    public static final Key<BlackLevelPattern> SENSOR_BLACK_LEVEL_PATTERN;
    public static final Key<ColorSpaceTransform> SENSOR_CALIBRATION_TRANSFORM1;
    public static final Key<ColorSpaceTransform> SENSOR_CALIBRATION_TRANSFORM2;
    public static final Key<ColorSpaceTransform> SENSOR_COLOR_TRANSFORM1;
    public static final Key<ColorSpaceTransform> SENSOR_COLOR_TRANSFORM2;
    public static final Key<ColorSpaceTransform> SENSOR_FORWARD_MATRIX1;
    public static final Key<ColorSpaceTransform> SENSOR_FORWARD_MATRIX2;
    public static final Key<Rect> SENSOR_INFO_ACTIVE_ARRAY_SIZE;
    public static final Key<Integer> SENSOR_INFO_COLOR_FILTER_ARRANGEMENT;
    public static final Key<Range<Long>> SENSOR_INFO_EXPOSURE_TIME_RANGE;
    public static final Key<Boolean> SENSOR_INFO_LENS_SHADING_APPLIED;
    public static final Key<Long> SENSOR_INFO_MAX_FRAME_DURATION;
    public static final Key<SizeF> SENSOR_INFO_PHYSICAL_SIZE;
    public static final Key<Size> SENSOR_INFO_PIXEL_ARRAY_SIZE;
    public static final Key<Rect> SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE;
    public static final Key<Range<Integer>> SENSOR_INFO_SENSITIVITY_RANGE;
    public static final Key<Integer> SENSOR_INFO_TIMESTAMP_SOURCE;
    public static final Key<Integer> SENSOR_INFO_WHITE_LEVEL;
    public static final Key<Integer> SENSOR_MAX_ANALOG_SENSITIVITY;
    public static final Key<Rect[]> SENSOR_OPTICAL_BLACK_REGIONS;
    public static final Key<Integer> SENSOR_ORIENTATION;
    public static final Key<Integer> SENSOR_REFERENCE_ILLUMINANT1;
    public static final Key<Byte> SENSOR_REFERENCE_ILLUMINANT2;
    public static final Key<int[]> SHADING_AVAILABLE_MODES;
    public static final Key<int[]> STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES;
    public static final Key<boolean[]> STATISTICS_INFO_AVAILABLE_HOT_PIXEL_MAP_MODES;
    public static final Key<int[]> STATISTICS_INFO_AVAILABLE_LENS_SHADING_MAP_MODES;
    public static final Key<Integer> STATISTICS_INFO_MAX_FACE_COUNT;
    public static final Key<Integer> SYNC_MAX_LATENCY;
    public static final Key<int[]> TONEMAP_AVAILABLE_TONE_MAP_MODES;
    public static final Key<Integer> TONEMAP_MAX_CURVE_POINTS;
    
    CameraCharacteristics() {
        throw new RuntimeException("Stub!");
    }
    
    public <T> T get(final Key<T> key) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public List<Key<?>> getKeys() {
        throw new RuntimeException("Stub!");
    }
    
    public List<CaptureRequest.Key<?>> getAvailableCaptureRequestKeys() {
        throw new RuntimeException("Stub!");
    }
    
    public List<CaptureResult.Key<?>> getAvailableCaptureResultKeys() {
        throw new RuntimeException("Stub!");
    }
    
    static {
        COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES = null;
        CONTROL_AE_AVAILABLE_ANTIBANDING_MODES = null;
        CONTROL_AE_AVAILABLE_MODES = null;
        CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES = null;
        CONTROL_AE_COMPENSATION_RANGE = null;
        CONTROL_AE_COMPENSATION_STEP = null;
        CONTROL_AE_LOCK_AVAILABLE = null;
        CONTROL_AF_AVAILABLE_MODES = null;
        CONTROL_AVAILABLE_EFFECTS = null;
        CONTROL_AVAILABLE_MODES = null;
        CONTROL_AVAILABLE_SCENE_MODES = null;
        CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES = null;
        CONTROL_AWB_AVAILABLE_MODES = null;
        CONTROL_AWB_LOCK_AVAILABLE = null;
        CONTROL_MAX_REGIONS_AE = null;
        CONTROL_MAX_REGIONS_AF = null;
        CONTROL_MAX_REGIONS_AWB = null;
        CONTROL_POST_RAW_SENSITIVITY_BOOST_RANGE = null;
        DEPTH_DEPTH_IS_EXCLUSIVE = null;
        EDGE_AVAILABLE_EDGE_MODES = null;
        FLASH_INFO_AVAILABLE = null;
        HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES = null;
        INFO_SUPPORTED_HARDWARE_LEVEL = null;
        JPEG_AVAILABLE_THUMBNAIL_SIZES = null;
        LENS_FACING = null;
        LENS_INFO_AVAILABLE_APERTURES = null;
        LENS_INFO_AVAILABLE_FILTER_DENSITIES = null;
        LENS_INFO_AVAILABLE_FOCAL_LENGTHS = null;
        LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION = null;
        LENS_INFO_FOCUS_DISTANCE_CALIBRATION = null;
        LENS_INFO_HYPERFOCAL_DISTANCE = null;
        LENS_INFO_MINIMUM_FOCUS_DISTANCE = null;
        LENS_INTRINSIC_CALIBRATION = null;
        LENS_POSE_ROTATION = null;
        LENS_POSE_TRANSLATION = null;
        LENS_RADIAL_DISTORTION = null;
        NOISE_REDUCTION_AVAILABLE_NOISE_REDUCTION_MODES = null;
        REPROCESS_MAX_CAPTURE_STALL = null;
        REQUEST_AVAILABLE_CAPABILITIES = null;
        REQUEST_MAX_NUM_INPUT_STREAMS = null;
        REQUEST_MAX_NUM_OUTPUT_PROC = null;
        REQUEST_MAX_NUM_OUTPUT_PROC_STALLING = null;
        REQUEST_MAX_NUM_OUTPUT_RAW = null;
        REQUEST_PARTIAL_RESULT_COUNT = null;
        REQUEST_PIPELINE_MAX_DEPTH = null;
        SCALER_AVAILABLE_MAX_DIGITAL_ZOOM = null;
        SCALER_CROPPING_TYPE = null;
        SCALER_STREAM_CONFIGURATION_MAP = null;
        SENSOR_AVAILABLE_TEST_PATTERN_MODES = null;
        SENSOR_BLACK_LEVEL_PATTERN = null;
        SENSOR_CALIBRATION_TRANSFORM1 = null;
        SENSOR_CALIBRATION_TRANSFORM2 = null;
        SENSOR_COLOR_TRANSFORM1 = null;
        SENSOR_COLOR_TRANSFORM2 = null;
        SENSOR_FORWARD_MATRIX1 = null;
        SENSOR_FORWARD_MATRIX2 = null;
        SENSOR_INFO_ACTIVE_ARRAY_SIZE = null;
        SENSOR_INFO_COLOR_FILTER_ARRANGEMENT = null;
        SENSOR_INFO_EXPOSURE_TIME_RANGE = null;
        SENSOR_INFO_LENS_SHADING_APPLIED = null;
        SENSOR_INFO_MAX_FRAME_DURATION = null;
        SENSOR_INFO_PHYSICAL_SIZE = null;
        SENSOR_INFO_PIXEL_ARRAY_SIZE = null;
        SENSOR_INFO_PRE_CORRECTION_ACTIVE_ARRAY_SIZE = null;
        SENSOR_INFO_SENSITIVITY_RANGE = null;
        SENSOR_INFO_TIMESTAMP_SOURCE = null;
        SENSOR_INFO_WHITE_LEVEL = null;
        SENSOR_MAX_ANALOG_SENSITIVITY = null;
        SENSOR_OPTICAL_BLACK_REGIONS = null;
        SENSOR_ORIENTATION = null;
        SENSOR_REFERENCE_ILLUMINANT1 = null;
        SENSOR_REFERENCE_ILLUMINANT2 = null;
        SHADING_AVAILABLE_MODES = null;
        STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES = null;
        STATISTICS_INFO_AVAILABLE_HOT_PIXEL_MAP_MODES = null;
        STATISTICS_INFO_AVAILABLE_LENS_SHADING_MAP_MODES = null;
        STATISTICS_INFO_MAX_FACE_COUNT = null;
        SYNC_MAX_LATENCY = null;
        TONEMAP_AVAILABLE_TONE_MAP_MODES = null;
        TONEMAP_MAX_CURVE_POINTS = null;
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
}
