package Utility;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeConversion {

    public static LocalDateTime UTCConversion (LocalDateTime timeToConvert) {
        ZoneId localTimeZone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        ZoneId UTC = ZoneId.of("UTC");

        ZonedDateTime currentConvertedTime = timeToConvert.atZone(localTimeZone);
        ZonedDateTime UTCConvertedTimeAndDate = currentConvertedTime.withZoneSameInstant(UTC);

        return LocalDateTime.from(UTCConvertedTimeAndDate);
    }

    public static LocalDateTime localTimeConversion(LocalDateTime timeToConvert) {

        ZoneId UTC = ZoneId.of("UTC");
        ZoneId localTimeZone = ZoneId.systemDefault();
        ZonedDateTime currentConvertedTime = timeToConvert.atZone(UTC);
        ZonedDateTime localConvertedTimeAndDate = currentConvertedTime.withZoneSameInstant(localTimeZone);

        return localConvertedTimeAndDate.toLocalDateTime();
    }
}
