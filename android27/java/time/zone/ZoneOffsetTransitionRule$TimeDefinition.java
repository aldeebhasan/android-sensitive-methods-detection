package java.time.zone;

import java.time.*;

public enum TimeDefinition
{
    UTC, 
    WALL, 
    STANDARD;
    
    public LocalDateTime createDateTime(final LocalDateTime localDateTime, final ZoneOffset zoneOffset, final ZoneOffset zoneOffset2) {
        switch (this) {
            case UTC: {
                return localDateTime.plusSeconds(zoneOffset2.getTotalSeconds() - ZoneOffset.UTC.getTotalSeconds());
            }
            case STANDARD: {
                return localDateTime.plusSeconds(zoneOffset2.getTotalSeconds() - zoneOffset.getTotalSeconds());
            }
            default: {
                return localDateTime;
            }
        }
    }
}
