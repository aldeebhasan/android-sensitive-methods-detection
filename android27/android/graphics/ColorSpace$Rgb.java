package android.graphics;

import java.util.function.*;

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
