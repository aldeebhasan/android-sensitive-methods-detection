package android.renderscript;

public final class ScriptIntrinsicBLAS extends ScriptIntrinsic
{
    public static final int CONJ_TRANSPOSE = 113;
    public static final int LEFT = 141;
    public static final int LOWER = 122;
    public static final int NON_UNIT = 131;
    public static final int NO_TRANSPOSE = 111;
    public static final int RIGHT = 142;
    public static final int TRANSPOSE = 112;
    public static final int UNIT = 132;
    public static final int UPPER = 121;
    
    ScriptIntrinsicBLAS() {
        throw new RuntimeException("Stub!");
    }
    
    public static ScriptIntrinsicBLAS create(final RenderScript rs) {
        throw new RuntimeException("Stub!");
    }
    
    public void SGEMV(final int TransA, final float alpha, final Allocation A, final Allocation X, final int incX, final float beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void DGEMV(final int TransA, final double alpha, final Allocation A, final Allocation X, final int incX, final double beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void CGEMV(final int TransA, final Float2 alpha, final Allocation A, final Allocation X, final int incX, final Float2 beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZGEMV(final int TransA, final Double2 alpha, final Allocation A, final Allocation X, final int incX, final Double2 beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void SGBMV(final int TransA, final int KL, final int KU, final float alpha, final Allocation A, final Allocation X, final int incX, final float beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void DGBMV(final int TransA, final int KL, final int KU, final double alpha, final Allocation A, final Allocation X, final int incX, final double beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void CGBMV(final int TransA, final int KL, final int KU, final Float2 alpha, final Allocation A, final Allocation X, final int incX, final Float2 beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZGBMV(final int TransA, final int KL, final int KU, final Double2 alpha, final Allocation A, final Allocation X, final int incX, final Double2 beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void STRMV(final int Uplo, final int TransA, final int Diag, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void DTRMV(final int Uplo, final int TransA, final int Diag, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void CTRMV(final int Uplo, final int TransA, final int Diag, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZTRMV(final int Uplo, final int TransA, final int Diag, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void STBMV(final int Uplo, final int TransA, final int Diag, final int K, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void DTBMV(final int Uplo, final int TransA, final int Diag, final int K, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void CTBMV(final int Uplo, final int TransA, final int Diag, final int K, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZTBMV(final int Uplo, final int TransA, final int Diag, final int K, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void STPMV(final int Uplo, final int TransA, final int Diag, final Allocation Ap, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void DTPMV(final int Uplo, final int TransA, final int Diag, final Allocation Ap, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void CTPMV(final int Uplo, final int TransA, final int Diag, final Allocation Ap, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZTPMV(final int Uplo, final int TransA, final int Diag, final Allocation Ap, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void STRSV(final int Uplo, final int TransA, final int Diag, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void DTRSV(final int Uplo, final int TransA, final int Diag, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void CTRSV(final int Uplo, final int TransA, final int Diag, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZTRSV(final int Uplo, final int TransA, final int Diag, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void STBSV(final int Uplo, final int TransA, final int Diag, final int K, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void DTBSV(final int Uplo, final int TransA, final int Diag, final int K, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void CTBSV(final int Uplo, final int TransA, final int Diag, final int K, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZTBSV(final int Uplo, final int TransA, final int Diag, final int K, final Allocation A, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void STPSV(final int Uplo, final int TransA, final int Diag, final Allocation Ap, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void DTPSV(final int Uplo, final int TransA, final int Diag, final Allocation Ap, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void CTPSV(final int Uplo, final int TransA, final int Diag, final Allocation Ap, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZTPSV(final int Uplo, final int TransA, final int Diag, final Allocation Ap, final Allocation X, final int incX) {
        throw new RuntimeException("Stub!");
    }
    
    public void SSYMV(final int Uplo, final float alpha, final Allocation A, final Allocation X, final int incX, final float beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void SSBMV(final int Uplo, final int K, final float alpha, final Allocation A, final Allocation X, final int incX, final float beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void SSPMV(final int Uplo, final float alpha, final Allocation Ap, final Allocation X, final int incX, final float beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void SGER(final float alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void SSYR(final int Uplo, final float alpha, final Allocation X, final int incX, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void SSPR(final int Uplo, final float alpha, final Allocation X, final int incX, final Allocation Ap) {
        throw new RuntimeException("Stub!");
    }
    
    public void SSYR2(final int Uplo, final float alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void SSPR2(final int Uplo, final float alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation Ap) {
        throw new RuntimeException("Stub!");
    }
    
    public void DSYMV(final int Uplo, final double alpha, final Allocation A, final Allocation X, final int incX, final double beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void DSBMV(final int Uplo, final int K, final double alpha, final Allocation A, final Allocation X, final int incX, final double beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void DSPMV(final int Uplo, final double alpha, final Allocation Ap, final Allocation X, final int incX, final double beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void DGER(final double alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void DSYR(final int Uplo, final double alpha, final Allocation X, final int incX, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void DSPR(final int Uplo, final double alpha, final Allocation X, final int incX, final Allocation Ap) {
        throw new RuntimeException("Stub!");
    }
    
    public void DSYR2(final int Uplo, final double alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void DSPR2(final int Uplo, final double alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation Ap) {
        throw new RuntimeException("Stub!");
    }
    
    public void CHEMV(final int Uplo, final Float2 alpha, final Allocation A, final Allocation X, final int incX, final Float2 beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void CHBMV(final int Uplo, final int K, final Float2 alpha, final Allocation A, final Allocation X, final int incX, final Float2 beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void CHPMV(final int Uplo, final Float2 alpha, final Allocation Ap, final Allocation X, final int incX, final Float2 beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void CGERU(final Float2 alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void CGERC(final Float2 alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void CHER(final int Uplo, final float alpha, final Allocation X, final int incX, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void CHPR(final int Uplo, final float alpha, final Allocation X, final int incX, final Allocation Ap) {
        throw new RuntimeException("Stub!");
    }
    
    public void CHER2(final int Uplo, final Float2 alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void CHPR2(final int Uplo, final Float2 alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation Ap) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZHEMV(final int Uplo, final Double2 alpha, final Allocation A, final Allocation X, final int incX, final Double2 beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZHBMV(final int Uplo, final int K, final Double2 alpha, final Allocation A, final Allocation X, final int incX, final Double2 beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZHPMV(final int Uplo, final Double2 alpha, final Allocation Ap, final Allocation X, final int incX, final Double2 beta, final Allocation Y, final int incY) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZGERU(final Double2 alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZGERC(final Double2 alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZHER(final int Uplo, final double alpha, final Allocation X, final int incX, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZHPR(final int Uplo, final double alpha, final Allocation X, final int incX, final Allocation Ap) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZHER2(final int Uplo, final Double2 alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation A) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZHPR2(final int Uplo, final Double2 alpha, final Allocation X, final int incX, final Allocation Y, final int incY, final Allocation Ap) {
        throw new RuntimeException("Stub!");
    }
    
    public void SGEMM(final int TransA, final int TransB, final float alpha, final Allocation A, final Allocation B, final float beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void DGEMM(final int TransA, final int TransB, final double alpha, final Allocation A, final Allocation B, final double beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void CGEMM(final int TransA, final int TransB, final Float2 alpha, final Allocation A, final Allocation B, final Float2 beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZGEMM(final int TransA, final int TransB, final Double2 alpha, final Allocation A, final Allocation B, final Double2 beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void SSYMM(final int Side, final int Uplo, final float alpha, final Allocation A, final Allocation B, final float beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void DSYMM(final int Side, final int Uplo, final double alpha, final Allocation A, final Allocation B, final double beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void CSYMM(final int Side, final int Uplo, final Float2 alpha, final Allocation A, final Allocation B, final Float2 beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZSYMM(final int Side, final int Uplo, final Double2 alpha, final Allocation A, final Allocation B, final Double2 beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void SSYRK(final int Uplo, final int Trans, final float alpha, final Allocation A, final float beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void DSYRK(final int Uplo, final int Trans, final double alpha, final Allocation A, final double beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void CSYRK(final int Uplo, final int Trans, final Float2 alpha, final Allocation A, final Float2 beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZSYRK(final int Uplo, final int Trans, final Double2 alpha, final Allocation A, final Double2 beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void SSYR2K(final int Uplo, final int Trans, final float alpha, final Allocation A, final Allocation B, final float beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void DSYR2K(final int Uplo, final int Trans, final double alpha, final Allocation A, final Allocation B, final double beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void CSYR2K(final int Uplo, final int Trans, final Float2 alpha, final Allocation A, final Allocation B, final Float2 beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZSYR2K(final int Uplo, final int Trans, final Double2 alpha, final Allocation A, final Allocation B, final Double2 beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void STRMM(final int Side, final int Uplo, final int TransA, final int Diag, final float alpha, final Allocation A, final Allocation B) {
        throw new RuntimeException("Stub!");
    }
    
    public void DTRMM(final int Side, final int Uplo, final int TransA, final int Diag, final double alpha, final Allocation A, final Allocation B) {
        throw new RuntimeException("Stub!");
    }
    
    public void CTRMM(final int Side, final int Uplo, final int TransA, final int Diag, final Float2 alpha, final Allocation A, final Allocation B) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZTRMM(final int Side, final int Uplo, final int TransA, final int Diag, final Double2 alpha, final Allocation A, final Allocation B) {
        throw new RuntimeException("Stub!");
    }
    
    public void STRSM(final int Side, final int Uplo, final int TransA, final int Diag, final float alpha, final Allocation A, final Allocation B) {
        throw new RuntimeException("Stub!");
    }
    
    public void DTRSM(final int Side, final int Uplo, final int TransA, final int Diag, final double alpha, final Allocation A, final Allocation B) {
        throw new RuntimeException("Stub!");
    }
    
    public void CTRSM(final int Side, final int Uplo, final int TransA, final int Diag, final Float2 alpha, final Allocation A, final Allocation B) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZTRSM(final int Side, final int Uplo, final int TransA, final int Diag, final Double2 alpha, final Allocation A, final Allocation B) {
        throw new RuntimeException("Stub!");
    }
    
    public void CHEMM(final int Side, final int Uplo, final Float2 alpha, final Allocation A, final Allocation B, final Float2 beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZHEMM(final int Side, final int Uplo, final Double2 alpha, final Allocation A, final Allocation B, final Double2 beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void CHERK(final int Uplo, final int Trans, final float alpha, final Allocation A, final float beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZHERK(final int Uplo, final int Trans, final double alpha, final Allocation A, final double beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void CHER2K(final int Uplo, final int Trans, final Float2 alpha, final Allocation A, final Allocation B, final float beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void ZHER2K(final int Uplo, final int Trans, final Double2 alpha, final Allocation A, final Allocation B, final double beta, final Allocation C) {
        throw new RuntimeException("Stub!");
    }
    
    public void BNNM(final Allocation A, final int a_offset, final Allocation B, final int b_offset, final Allocation C, final int c_offset, final int c_mult) {
        throw new RuntimeException("Stub!");
    }
}
