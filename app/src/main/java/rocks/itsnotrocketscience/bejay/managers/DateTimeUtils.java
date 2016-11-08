package rocks.itsnotrocketscience.bejay.managers;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by sirfunkenstine on 04/05/16.
 *
 */
public class DateTimeUtils {

    public DateTimeUtils() {
    }

    public String getLocalDate() {
        return new LocalDate().toString();
    }

    public String getLocalTime() {
        LocalTime time = new LocalTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("hh:mm a");
        return fmt.print(time);
    }

    public static String getFormattedDateTime(String date, String time) {
        try {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm a");
            String all = String.format("%s %s", date, time);
            DateTime dt = fmt.parseDateTime(all);
            return dt.toString();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static String getFormattedLocalTime(int hour, int minute) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("hh mm a");
        DateTime dateTime = new DateTime(1, 1, 1, hour, minute);
        return dateTime.toLocalTime().toString(fmt);
    }

    public static String getDate(int year, int month, int day) {
        DateTime dateTime = new DateTime(year, month + 1, day, 0, 0);
        return dateTime.toLocalDate().toString();
    }
}
