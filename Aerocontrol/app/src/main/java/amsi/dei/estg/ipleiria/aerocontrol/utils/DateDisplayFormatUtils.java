package amsi.dei.estg.ipleiria.aerocontrol.utils;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDisplayFormatUtils {

    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm";

    public static Date formatStringToDate(String stringToFormat, @Nullable String customFormat) {
        String formatPattern = DATE_FORMAT;
        if(customFormat != null)
            formatPattern = customFormat;

        SimpleDateFormat formatter = new SimpleDateFormat(formatPattern);
        Date formatedDate = null;
        try {
            formatedDate = formatter.parse(stringToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;

    }

    public static String formatDateToString(Date date, @Nullable String customFormat) {
        String formatPattern = DATE_FORMAT;
        if(customFormat != null)
            formatPattern = customFormat;

        SimpleDateFormat formatter = new SimpleDateFormat(formatPattern);
        return formatter.format(date);

    }

    public static Date formatStringToDateTime(String stringToFormat, @Nullable String customFormat) {
        String formatPattern = DATE_TIME_FORMAT;
        if(customFormat != null)
            formatPattern = customFormat;

        SimpleDateFormat formatter = new SimpleDateFormat(formatPattern);
        Date formatedDate = null;
        try {
            formatedDate = formatter.parse(stringToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;

    }

    public static String formatDateTimeToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        return formatter.format(date);
    }
}
