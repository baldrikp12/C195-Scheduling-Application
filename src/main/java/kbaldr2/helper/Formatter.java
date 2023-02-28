package kbaldr2.helper;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Formatter {
    
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    // Parses a time string in the format "HH:mm" and returns a LocalTime object
    public static LocalTime parseTime(String timeString) {
        
        return LocalTime.parse(timeString, TIME_FORMATTER);
    }
    
    // Formats a LocalTime object as a time string in the format "HH:mm:ss"
    public static String formatTime(LocalTime time) {
        
        return time.format(TIME_FORMATTER);
    }
    
    // Converts a UTC LocalDateTime to a LocalTime object in the local time zone
    public static LocalDateTime UTCtoLocal(LocalDateTime inputDateTime) {
        
        ZonedDateTime zonedDateTime = inputDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime localZonedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return localZonedDateTime.toLocalDateTime();
    }
    
    // Converts local LocalDateTime to a LocalTime object in the UTC time zone
    public static LocalDateTime localToUTC(LocalDateTime inputDateTime) {
        
        ZonedDateTime localZonedDateTime = inputDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZonedDateTime = localZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZonedDateTime.toLocalDateTime();
    }
    
    // Converts an Eastern Time LocalTime to a LocalTime object in the local time zone
    public static LocalTime ESTtoLocal(LocalTime easternTime) {
        
        ZonedDateTime easternDateTime = ZonedDateTime.of(LocalDate.now(), easternTime, ZoneId.of("America/New_York"));
        ZonedDateTime localDateTime = easternDateTime.withZoneSameInstant(ZoneId.systemDefault());
        return localDateTime.toLocalTime();
    }
    
}