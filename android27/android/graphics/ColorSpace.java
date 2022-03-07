package android.graphics;

import java.util.function.*;

public abstract class ColorSpace
{
    public static final float[] ILLUMINANT_A;
    public static final float[] ILLUMINANT_B;
    public static final float[] ILLUMINANT_C;
    public static final float[] ILLUMINANT_D50;
    public static final float[] ILLUMINANT_D55;
    public static final float[] ILLUMINANT_D60;
    public static final float[] ILLUMINANT_D65;
    public static final float[] ILLUMINANT_D75;
    public static final float[] ILLUMINANT_E;
    public static final int MAX_ID = 63;
    public static final int MIN_ID = -1;
    
    ColorSpace() {
        throw new RuntimeException("Stub!");
    }
    
    public String getName() {
        throw new RuntimeException("Stub!");
    }
    
    public int getId() {
        throw new RuntimeException("Stub!");
    }
    
    public Model getModel() {
        throw new RuntimeException("Stub!");
    }
    
    public int getComponentCount() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract boolean isWideGamut();
    
    public boolean isSrgb() {
        throw new RuntimeException("Stub!");
    }
    
    public abstract float getMinValue(final int p0);
    
    public abstract float getMaxValue(final int p0);
    
    public float[] toXyz(final float r, final float g, final float b) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract float[] toXyz(final float[] p0);
    
    public float[] fromXyz(final float x, final float y, final float z) {
        throw new RuntimeException("Stub!");
    }
    
    public abstract float[] fromXyz(final float[] p0);
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public boolean equals(final Object o) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public int hashCode() {
        throw new RuntimeException("Stub!");
    }
    
    public static Connector connect(final ColorSpace source, final ColorSpace destination) {
        throw new RuntimeException("Stub!");
    }
    
    public static Connector connect(final ColorSpace source, final ColorSpace destination, final RenderIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public static Connector connect(final ColorSpace source) {
        throw new RuntimeException("Stub!");
    }
    
    public static Connector connect(final ColorSpace source, final RenderIntent intent) {
        throw new RuntimeException("Stub!");
    }
    
    public static ColorSpace adapt(final ColorSpace colorSpace, final float[] whitePoint) {
        throw new RuntimeException("Stub!");
    }
    
    public static ColorSpace adapt(final ColorSpace colorSpace, final float[] whitePoint, final Adaptation adaptation) {
        throw new RuntimeException("Stub!");
    }
    
    public static ColorSpace get(final Named name) {
        throw new RuntimeException("Stub!");
    }
    
    public static ColorSpace match(final float[] toXYZD50, final Rgb.TransferParameters function) {
        throw new RuntimeException("Stub!");
    }
    
    static {
        ILLUMINANT_A = null;
        ILLUMINANT_B = null;
        ILLUMINANT_C = null;
        ILLUMINANT_D50 = null;
        ILLUMINANT_D55 = null;
        ILLUMINANT_D60 = null;
        ILLUMINANT_D65 = null;
        ILLUMINANT_D75 = null;
        ILLUMINANT_E = null;
    }
    
    public enum Named
    {
        ACES, 
        ACESCG, 
        ADOBE_RGB, 
        BT2020, 
        BT709, 
        CIE_LAB, 
        CIE_XYZ, 
        DCI_P3, 
        DISPLAY_P3, 
        EXTENDED_SRGB, 
        LINEAR_EXTENDED_SRGB, 
        LINEAR_SRGB, 
        NTSC_1953, 
        PRO_PHOTO_RGB, 
        SMPTE_C, 
        SRGB;
    }
    
    public enum RenderIntent
    {
        ABSOLUTE, 
        PERCEPTUAL, 
        RELATIVE, 
        SATURATION;
    }
    
    public enum Adaptation
    {
        BRADFORD, 
        CIECAT02, 
        VON_KRIES;
    }
    
    public enum Model
    {
        CMYK, 
        LAB, 
        RGB, 
        XYZ;
        
        public int getComponentCount() {
            throw new RuntimeException("Stub!");
        }
    }
    
    public static class Rgb extends ColorSpace
    {
        public Rgb(final String name, final float[] toXYZ, final DoubleUnaryOperator oetf, final DoubleUnaryOperator eotf) {
            throw new RuntimeException("Stub!");
        }
        
        public Rgb(final String name, final float[] primaries, final float[] whitePoint, final DoubleUnaryOperator oetf, final DoubleUnaryOperator eotf, final float min, final float max) {
            throw new RuntimeException("Stub!");
        }
        
        public Rgb(final String name, final float[] toXYZ, final TransferParameters function) {
            throw new RuntimeException("Stub!");
        }
        
        public Rgb(final String name, final float[] primaries, final float[] whitePoint, final TransferParameters function) {
            throw new RuntimeException("Stub!");
        }
        
        public Rgb(final String name, final float[] toXYZ, final double gamma) {
            throw new RuntimeException("Stub!");
        }
        
        public Rgb(final String name, final float[] primaries, final float[] whitePoint, final double gamma) {
            throw new RuntimeException("Stub!");
        }
        
        public float[] getWhitePoint(final float[] whitePoint) {
            throw new RuntimeException("Stub!");
        }
        
        public float[] getWhitePoint() {
            throw new RuntimeException("Stub!");
        }
        
        public float[] getPrimaries(final float[] primaries) {
            throw new RuntimeException("Stub!");
        }
        
        public float[] getPrimaries() {
            throw new RuntimeException("Stub!");
        }
        
        public float[] getTransform(final float[] transform) {
            throw new RuntimeException("Stub!");
        }
        
        public float[] getTransform() {
            throw new RuntimeException("Stub!");
        }
        
        public float[] getInverseTransform(final float[] inverseTransform) {
            throw new RuntimeException("Stub!");
        }
        
        public float[] getInverseTransform() {
            throw new RuntimeException("Stub!");
        }
        
        public DoubleUnaryOperator getOetf() {
            throw new RuntimeException("Stub!");
        }
        
        public DoubleUnaryOperator getEotf() {
            throw new RuntimeException("Stub!");
        }
        
        public TransferParameters getTransferParameters() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean isSrgb() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean isWideGamut() {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public float getMinValue(final int component) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public float getMaxValue(final int component) {
            throw new RuntimeException("Stub!");
        }
        
        public float[] toLinear(final float r, final float g, final float b) {
            throw new RuntimeException("Stub!");
        }
        
        public float[] toLinear(final float[] v) {
            throw new RuntimeException("Stub!");
        }
        
        public float[] fromLinear(final float r, final float g, final float b) {
            throw new RuntimeException("Stub!");
        }
        
        public float[] fromLinear(final float[] v) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public float[] toXyz(final float[] v) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public float[] fromXyz(final float[] v) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public boolean equals(final Object o) {
            throw new RuntimeException("Stub!");
        }
        
        @Override
        public int hashCode() {
            throw new RuntimeException("Stub!");
        }
        
        public static class TransferParameters
        {
            public final double a;
            public final double b;
            public final double c;
            public final double d;
            public final double e;
            public final double f;
            public final double g;
            
            public TransferParameters(final double a, final double b, final double c, final double d, final double g) {
                throw new RuntimeException("Stub!");
            }
            
            public TransferParameters(final double a, final double b, final double c, final double d, final double e, final double f, final double g) {
                throw new RuntimeException("Stub!");
            }
            
            @Override
            public boolean equals(final Object o) {
                throw new RuntimeException("Stub!");
            }
            
            @Override
            public int hashCode() {
                throw new RuntimeException("Stub!");
            }
        }
    }
    
    public static class Connector
    {
        Connector() {
            throw new RuntimeException("Stub!");
        }
        
        public ColorSpace getSource() {
            throw new RuntimeException("Stub!");
        }
        
        public ColorSpace getDestination() {
            throw new RuntimeException("Stub!");
        }
        
        public RenderIntent getRenderIntent() {
            throw new RuntimeException("Stub!");
        }
        
        public float[] transform(final float r, final float g, final float b) {
            throw new RuntimeException("Stub!");
        }
        
        public float[] transform(final float[] v) {
            throw new RuntimeException("Stub!");
        }
    }
}
