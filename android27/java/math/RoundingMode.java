package java.math;

public enum RoundingMode
{
    UP(0), 
    DOWN(1), 
    CEILING(2), 
    FLOOR(3), 
    HALF_UP(4), 
    HALF_DOWN(5), 
    HALF_EVEN(6), 
    UNNECESSARY(7);
    
    final int oldMode;
    
    private RoundingMode(final int oldMode) {
        this.oldMode = oldMode;
    }
    
    public static RoundingMode valueOf(final int n) {
        switch (n) {
            case 0: {
                return RoundingMode.UP;
            }
            case 1: {
                return RoundingMode.DOWN;
            }
            case 2: {
                return RoundingMode.CEILING;
            }
            case 3: {
                return RoundingMode.FLOOR;
            }
            case 4: {
                return RoundingMode.HALF_UP;
            }
            case 5: {
                return RoundingMode.HALF_DOWN;
            }
            case 6: {
                return RoundingMode.HALF_EVEN;
            }
            case 7: {
                return RoundingMode.UNNECESSARY;
            }
            default: {
                throw new IllegalArgumentException("argument out of range");
            }
        }
    }
}
