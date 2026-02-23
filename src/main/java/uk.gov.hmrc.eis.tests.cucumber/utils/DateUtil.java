package uk.gov.hmrc.eis.tests.cucumber.utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtil {
    public static LocalDateTime parseDateTime(String dateTime, String pattern) throws DateTimeParseException {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(pattern)
                );
}

private DateUtil() {}
}