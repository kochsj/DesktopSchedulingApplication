package model;

import java.time.LocalTime;
import java.util.Hashtable;

/**
 * Class representing a dictionary of time strings. Key's with time-string values get LocalTime values from the hashtable.<br><br>
 * Time strings are predetermined and populate the application's ComboBox fields for selection. Time strings range from 12:00am to 11:30pm; increasing by 30min each step.<br><br>
 * @see java.time.LocalTime
 */
public class TimeTable {
    private static final Hashtable<String, LocalTime> timeTable = new Hashtable<>();

    static {
        addKeyValuePairs();
    }

    /**
     * Private method fills the Hashtable with key-value pairs which can be accessed statically.
     */
    private static void addKeyValuePairs() {
        timeTable.put("12:00am", LocalTime.parse("00:00:00"));
        timeTable.put("12:30am", LocalTime.parse("00:30:00"));
        timeTable.put("1:00am", LocalTime.parse("01:00:00"));
        timeTable.put("1:30am", LocalTime.parse("01:30:00"));
        timeTable.put("2:00am", LocalTime.parse("02:00:00"));
        timeTable.put("2:30am", LocalTime.parse("02:30:00"));
        timeTable.put("3:00am", LocalTime.parse("03:00:00"));
        timeTable.put("3:30am", LocalTime.parse("03:30:00"));
        timeTable.put("4:00am", LocalTime.parse("04:00:00"));
        timeTable.put("4:30am", LocalTime.parse("04:30:00"));
        timeTable.put("5:00am", LocalTime.parse("05:00:00"));
        timeTable.put("5:30am", LocalTime.parse("05:30:00"));
        timeTable.put("6:00am", LocalTime.parse("06:00:00"));
        timeTable.put("6:30am", LocalTime.parse("06:30:00"));
        timeTable.put("7:00am", LocalTime.parse("07:00:00"));
        timeTable.put("7:30am", LocalTime.parse("07:30:00"));
        timeTable.put("8:00am", LocalTime.parse("08:00:00"));
        timeTable.put("8:30am", LocalTime.parse("08:30:00"));
        timeTable.put("9:00am", LocalTime.parse("09:00:00"));
        timeTable.put("9:30am", LocalTime.parse("09:30:00"));
        timeTable.put("10:00am", LocalTime.parse("10:00:00"));
        timeTable.put("10:30am", LocalTime.parse("10:30:00"));
        timeTable.put("11:00am", LocalTime.parse("11:00:00"));
        timeTable.put("11:30am", LocalTime.parse("11:30:00"));
        timeTable.put("12:00pm", LocalTime.parse("12:00:00"));
        timeTable.put("12:30pm", LocalTime.parse("12:30:00"));
        timeTable.put("1:00pm", LocalTime.parse("13:00:00"));
        timeTable.put("1:30pm", LocalTime.parse("13:30:00"));
        timeTable.put("2:00pm", LocalTime.parse("14:00:00"));
        timeTable.put("2:30pm", LocalTime.parse("14:30:00"));
        timeTable.put("3:00pm", LocalTime.parse("15:00:00"));
        timeTable.put("3:30pm", LocalTime.parse("15:30:00"));
        timeTable.put("4:00pm", LocalTime.parse("16:00:00"));
        timeTable.put("4:30pm", LocalTime.parse("16:30:00"));
        timeTable.put("5:00pm", LocalTime.parse("17:00:00"));
        timeTable.put("5:30pm", LocalTime.parse("17:30:00"));
        timeTable.put("6:00pm", LocalTime.parse("18:00:00"));
        timeTable.put("6:30pm", LocalTime.parse("18:30:00"));
        timeTable.put("7:00pm", LocalTime.parse("19:00:00"));
        timeTable.put("7:30pm", LocalTime.parse("19:30:00"));
        timeTable.put("8:00pm", LocalTime.parse("20:00:00"));
        timeTable.put("8:30pm", LocalTime.parse("20:30:00"));
        timeTable.put("9:00pm", LocalTime.parse("21:00:00"));
        timeTable.put("9:30pm", LocalTime.parse("21:30:00"));
        timeTable.put("10:00pm", LocalTime.parse("22:00:00"));
        timeTable.put("10:30pm", LocalTime.parse("22:30:00"));
        timeTable.put("11:00pm", LocalTime.parse("23:00:00"));
        timeTable.put("11:30pm", LocalTime.parse("23:30:00"));
    }

    /**
     * Gets the associated time from the Dictionary using a formatted 'time-string'. String parameter is in the format hh:mm[am|pm] ranging from 12:00am to 11:30pm and increasing 30min each step.<br><br>
     * @param key the formatted time-string key
     * @return the LocalTime value associated with the key
     */
    public static LocalTime getTime(String key) {
        return timeTable.get(key);
    }

}
