package android.graphics;

public class Canvas
{
    public static final int ALL_SAVE_FLAG = 31;
    @Deprecated
    public static final int CLIP_SAVE_FLAG = 2;
    @Deprecated
    public static final int CLIP_TO_LAYER_SAVE_FLAG = 16;
    @Deprecated
    public static final int FULL_COLOR_LAYER_SAVE_FLAG = 8;
    @Deprecated
    public static final int HAS_ALPHA_LAYER_SAVE_FLAG = 4;
    @Deprecated
    public static final int MATRIX_SAVE_FLAG = 1;
    
    public Canvas() {
        throw new RuntimeException("Stub!");
    }
    
    public Canvas(final Bitmap bitmap) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isHardwareAccelerated() {
        throw new RuntimeException("Stub!");
    }
    
    public void setBitmap(final Bitmap bitmap) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean isOpaque() {
        throw new RuntimeException("Stub!");
    }
    
    public int getWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public int getDensity() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDensity(final int density) {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaximumBitmapWidth() {
        throw new RuntimeException("Stub!");
    }
    
    public int getMaximumBitmapHeight() {
        throw new RuntimeException("Stub!");
    }
    
    public int save() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int save(final int saveFlags) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int saveLayer(final RectF bounds, final Paint paint, final int saveFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public int saveLayer(final RectF bounds, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int saveLayer(final float left, final float top, final float right, final float bottom, final Paint paint, final int saveFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public int saveLayer(final float left, final float top, final float right, final float bottom, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int saveLayerAlpha(final RectF bounds, final int alpha, final int saveFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public int saveLayerAlpha(final RectF bounds, final int alpha) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public int saveLayerAlpha(final float left, final float top, final float right, final float bottom, final int alpha, final int saveFlags) {
        throw new RuntimeException("Stub!");
    }
    
    public int saveLayerAlpha(final float left, final float top, final float right, final float bottom, final int alpha) {
        throw new RuntimeException("Stub!");
    }
    
    public void restore() {
        throw new RuntimeException("Stub!");
    }
    
    public int getSaveCount() {
        throw new RuntimeException("Stub!");
    }
    
    public void restoreToCount(final int saveCount) {
        throw new RuntimeException("Stub!");
    }
    
    public void translate(final float dx, final float dy) {
        throw new RuntimeException("Stub!");
    }
    
    public void scale(final float sx, final float sy) {
        throw new RuntimeException("Stub!");
    }
    
    public final void scale(final float sx, final float sy, final float px, final float py) {
        throw new RuntimeException("Stub!");
    }
    
    public void rotate(final float degrees) {
        throw new RuntimeException("Stub!");
    }
    
    public final void rotate(final float degrees, final float px, final float py) {
        throw new RuntimeException("Stub!");
    }
    
    public void skew(final float sx, final float sy) {
        throw new RuntimeException("Stub!");
    }
    
    public void concat(final Matrix matrix) {
        throw new RuntimeException("Stub!");
    }
    
    public void setMatrix(final Matrix matrix) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void getMatrix(final Matrix ctm) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public final Matrix getMatrix() {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean clipRect(final RectF rect, final Region.Op op) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean clipRect(final Rect rect, final Region.Op op) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clipRect(final RectF rect) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clipOutRect(final RectF rect) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clipRect(final Rect rect) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clipOutRect(final Rect rect) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean clipRect(final float left, final float top, final float right, final float bottom, final Region.Op op) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clipRect(final float left, final float top, final float right, final float bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clipOutRect(final float left, final float top, final float right, final float bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clipRect(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clipOutRect(final int left, final int top, final int right, final int bottom) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public boolean clipPath(final Path path, final Region.Op op) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clipPath(final Path path) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean clipOutPath(final Path path) {
        throw new RuntimeException("Stub!");
    }
    
    public DrawFilter getDrawFilter() {
        throw new RuntimeException("Stub!");
    }
    
    public void setDrawFilter(final DrawFilter filter) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean quickReject(final RectF rect, final EdgeType type) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean quickReject(final Path path, final EdgeType type) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean quickReject(final float left, final float top, final float right, final float bottom, final EdgeType type) {
        throw new RuntimeException("Stub!");
    }
    
    public boolean getClipBounds(final Rect bounds) {
        throw new RuntimeException("Stub!");
    }
    
    public final Rect getClipBounds() {
        throw new RuntimeException("Stub!");
    }
    
    public void drawPicture(final Picture picture) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawPicture(final Picture picture, final RectF dst) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawPicture(final Picture picture, final Rect dst) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawArc(final RectF oval, final float startAngle, final float sweepAngle, final boolean useCenter, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawArc(final float left, final float top, final float right, final float bottom, final float startAngle, final float sweepAngle, final boolean useCenter, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawARGB(final int a, final int r, final int g, final int b) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawBitmap(final Bitmap bitmap, final float left, final float top, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawBitmap(final Bitmap bitmap, final Rect src, final RectF dst, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawBitmap(final Bitmap bitmap, final Rect src, final Rect dst, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void drawBitmap(final int[] colors, final int offset, final int stride, final float x, final float y, final int width, final int height, final boolean hasAlpha, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void drawBitmap(final int[] colors, final int offset, final int stride, final int x, final int y, final int width, final int height, final boolean hasAlpha, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawBitmap(final Bitmap bitmap, final Matrix matrix, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawBitmapMesh(final Bitmap bitmap, final int meshWidth, final int meshHeight, final float[] verts, final int vertOffset, final int[] colors, final int colorOffset, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawCircle(final float cx, final float cy, final float radius, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawColor(final int color) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawColor(final int color, final PorterDuff.Mode mode) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawLine(final float startX, final float startY, final float stopX, final float stopY, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawLines(final float[] pts, final int offset, final int count, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawLines(final float[] pts, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawOval(final RectF oval, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawOval(final float left, final float top, final float right, final float bottom, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawPaint(final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawPath(final Path path, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawPoint(final float x, final float y, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawPoints(final float[] pts, final int offset, final int count, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawPoints(final float[] pts, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void drawPosText(final char[] text, final int index, final int count, final float[] pos, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    @Deprecated
    public void drawPosText(final String text, final float[] pos, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawRect(final RectF rect, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawRect(final Rect r, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawRect(final float left, final float top, final float right, final float bottom, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawRGB(final int r, final int g, final int b) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawRoundRect(final RectF rect, final float rx, final float ry, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawRoundRect(final float left, final float top, final float right, final float bottom, final float rx, final float ry, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawText(final char[] text, final int index, final int count, final float x, final float y, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawText(final String text, final float x, final float y, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawText(final String text, final int start, final int end, final float x, final float y, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawText(final CharSequence text, final int start, final int end, final float x, final float y, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawTextOnPath(final char[] text, final int index, final int count, final Path path, final float hOffset, final float vOffset, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawTextOnPath(final String text, final Path path, final float hOffset, final float vOffset, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawTextRun(final char[] text, final int index, final int count, final int contextIndex, final int contextCount, final float x, final float y, final boolean isRtl, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawTextRun(final CharSequence text, final int start, final int end, final int contextStart, final int contextEnd, final float x, final float y, final boolean isRtl, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public void drawVertices(final VertexMode mode, final int vertexCount, final float[] verts, final int vertOffset, final float[] texs, final int texOffset, final int[] colors, final int colorOffset, final short[] indices, final int indexOffset, final int indexCount, final Paint paint) {
        throw new RuntimeException("Stub!");
    }
    
    public enum EdgeType
    {
        AA, 
        BW;
    }
    
    public enum VertexMode
    {
        TRIANGLES, 
        TRIANGLE_FAN, 
        TRIANGLE_STRIP;
    }
}
