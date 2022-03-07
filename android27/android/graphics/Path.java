package android.graphics;

public class Path
{
    public Path() {
        throw new RuntimeException("Stub!");
    }
    
    public Path(final Path src) {
        throw new RuntimeException("Stub!");
    }
    
    public void reset() {
        throw new RuntimeException("Stub!");
    }
    
    public void rewind() {
        throw new RuntimeException("Stub!");
    }
    
    public void set(final Path src) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean op(final Path path, final Op op) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean op(final Path path1, final Path path2, final Op op) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isConvex() {
        throw new RuntimeException("Stub!");
    }
    
    public FillType getFillType() {
        throw new RuntimeException("Stub!");
    }
    
    public void setFillType(final FillType ft) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isInverseFillType() {
        throw new RuntimeException("Stub!");
    }
    
    public void toggleInverseFillType() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isEmpty() {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isRect(final RectF rect) {
        throw new RuntimeException("Stub!");
    }
    
    public void computeBounds(final RectF bounds, final boolean exact) {
        throw new RuntimeException("Stub!");
    }
    
    public void incReserve(final int extraPtCount) {
        throw new RuntimeException("Stub!");
    }
    
    public void moveTo(final float x, final float y) {
        throw new RuntimeException("Stub!");
    }
    
    public void rMoveTo(final float dx, final float dy) {
        throw new RuntimeException("Stub!");
    }
    
    public void lineTo(final float x, final float y) {
        throw new RuntimeException("Stub!");
    }
    
    public void rLineTo(final float dx, final float dy) {
        throw new RuntimeException("Stub!");
    }
    
    public void quadTo(final float x1, final float y1, final float x2, final float y2) {
        throw new RuntimeException("Stub!");
    }
    
    public void rQuadTo(final float dx1, final float dy1, final float dx2, final float dy2) {
        throw new RuntimeException("Stub!");
    }
    
    public void cubicTo(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        throw new RuntimeException("Stub!");
    }
    
    public void rCubicTo(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3) {
        throw new RuntimeException("Stub!");
    }
    
    public void arcTo(final RectF oval, final float startAngle, final float sweepAngle, final boolean forceMoveTo) {
        throw new RuntimeException("Stub!");
    }
    
    public void arcTo(final RectF oval, final float startAngle, final float sweepAngle) {
        throw new RuntimeException("Stub!");
    }
    
    public void arcTo(final float left, final float top, final float right, final float bottom, final float startAngle, final float sweepAngle, final boolean forceMoveTo) {
        throw new RuntimeException("Stub!");
    }
    
    public void close() {
        throw new RuntimeException("Stub!");
    }
    
    public void addRect(final RectF rect, final Direction dir) {
        throw new RuntimeException("Stub!");
    }
    
    public void addRect(final float left, final float top, final float right, final float bottom, final Direction dir) {
        throw new RuntimeException("Stub!");
    }
    
    public void addOval(final RectF oval, final Direction dir) {
        throw new RuntimeException("Stub!");
    }
    
    public void addOval(final float left, final float top, final float right, final float bottom, final Direction dir) {
        throw new RuntimeException("Stub!");
    }
    
    public void addCircle(final float x, final float y, final float radius, final Direction dir) {
        throw new RuntimeException("Stub!");
    }
    
    public void addArc(final RectF oval, final float startAngle, final float sweepAngle) {
        throw new RuntimeException("Stub!");
    }
    
    public void addArc(final float left, final float top, final float right, final float bottom, final float startAngle, final float sweepAngle) {
        throw new RuntimeException("Stub!");
    }
    
    public void addRoundRect(final RectF rect, final float rx, final float ry, final Direction dir) {
        throw new RuntimeException("Stub!");
    }
    
    public void addRoundRect(final float left, final float top, final float right, final float bottom, final float rx, final float ry, final Direction dir) {
        throw new RuntimeException("Stub!");
    }
    
    public void addRoundRect(final RectF rect, final float[] radii, final Direction dir) {
        throw new RuntimeException("Stub!");
    }
    
    public void addRoundRect(final float left, final float top, final float right, final float bottom, final float[] radii, final Direction dir) {
        throw new RuntimeException("Stub!");
    }
    
    public void addPath(final Path src, final float dx, final float dy) {
        throw new RuntimeException("Stub!");
    }
    
    public void addPath(final Path src) {
        throw new RuntimeException("Stub!");
    }
    
    public void addPath(final Path src, final Matrix matrix) {
        throw new RuntimeException("Stub!");
    }
    
    public void offset(final float dx, final float dy, final Path dst) {
        throw new RuntimeException("Stub!");
    }
    
    public void offset(final float dx, final float dy) {
        throw new RuntimeException("Stub!");
    }
    
    public void setLastPoint(final float dx, final float dy) {
        throw new RuntimeException("Stub!");
    }
    
    public void transform(final Matrix matrix, final Path dst) {
        throw new RuntimeException("Stub!");
    }
    
    public void transform(final Matrix matrix) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    protected void finalize() throws Throwable {
        throw new RuntimeException("Stub!");
    }
    
    public float[] approximate(final float acceptableError) {
        throw new RuntimeException("Stub!");
    }
    
    public enum Op
    {
        DIFFERENCE, 
        INTERSECT, 
        REVERSE_DIFFERENCE, 
        UNION, 
        XOR;
    }
    
    public enum FillType
    {
        EVEN_ODD, 
        INVERSE_EVEN_ODD, 
        INVERSE_WINDING, 
        WINDING;
    }
    
    public enum Direction
    {
        CCW, 
        CW;
    }
}
