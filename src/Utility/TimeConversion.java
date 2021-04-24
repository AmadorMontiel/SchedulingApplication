package Utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Helper class to streamline timezone conversions.
 */
public class TimeConversion {

    /**
     * Coverts a time to UTC to store in DB
     * @param timeToConvert The time that will be converted.
     * @return The UTC time after conversion.
     */
    public static LocalDateTime UTCConversion (LocalDateTime timeToConvert) {
        ZoneId localTimeZone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        ZoneId UTC = ZoneId.of("UTC");

        ZonedDateTime currentConvertedTime = timeToConvert.atZone(localTimeZone);
        ZonedDateTime UTCConvertedTimeAndDate = currentConvertedTime.withZoneSameInstant(UTC);

        return LocalDateTime.from(UTCConvertedTimeAndDate);
    }

    /**
     * Converts a time to Local Time for display purposes.
     * @param timeToConvert The time to convert to local time.
     * @return The converted time in local time.
     */
    public static LocalDateTime localTimeConversion(LocalDateTime timeToConvert) {

        ZoneId UTC = ZoneId.of("UTC");
        ZoneId localTimeZone = ZoneId.systemDefault();
        ZonedDateTime currentConvertedTime = timeToConvert.atZone(UTC);
        ZonedDateTime localConvertedTimeAndDate = currentConvertedTime.withZoneSameInstant(localTimeZone);

        return localConvertedTimeAndDate.toLocalDateTime();
    }
}
