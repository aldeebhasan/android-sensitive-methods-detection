package android.system;

public final class StructStat
{
    public final StructTimespec st_atim;
    public final long st_atime;
    public final long st_blksize;
    public final long st_blocks;
    public final StructTimespec st_ctim;
    public final long st_ctime;
    public final long st_dev;
    public final int st_gid;
    public final long st_ino;
    public final int st_mode;
    public final StructTimespec st_mtim;
    public final long st_mtime;
    public final long st_nlink;
    public final long st_rdev;
    public final long st_size;
    public final int st_uid;
    
    public StructStat(final long st_dev, final long st_ino, final int st_mode, final long st_nlink, final int st_uid, final int st_gid, final long st_rdev, final long st_size, final long st_atime, final long st_mtime, final long st_ctime, final long st_blksize, final long st_blocks) {
        throw new RuntimeException("Stub!");
    }
    
    public StructStat(final long st_dev, final long st_ino, final int st_mode, final long st_nlink, final int st_uid, final int st_gid, final long st_rdev, final long st_size, final StructTimespec st_atim, final StructTimespec st_mtim, final StructTimespec st_ctim, final long st_blksize, final long st_blocks) {
        throw new RuntimeException("Stub!");
    }
    
    @Override
    public String toString() {
        throw new RuntimeException("Stub!");
    }
}
