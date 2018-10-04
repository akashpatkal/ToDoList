package comp5216.sydney.edu.au.todolist;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/***
 * @author Akash
 *
 * Date Converter for Room Databes
 */
public class DateConverter {
    /**
     * Convert Time stamp into Date object
     * @param value
     * @return dateObject
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        if (value == null) {
            return null;
        }
        else {
            return new Date(value);
        }

    }

    /***return Data object from time stramp object
     *
     * @param date
     * @return timeStramp
     */

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        else {
            return date.getTime();
        }
    }
}