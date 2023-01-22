package amsi.dei.estg.ipleiria.aerocontrol.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDisplayFormatUtils {

    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm";

    public static Date formatStringToDate(String stringToFormat) {
        Date formatedDate = null;
        try {
            formatedDate = new SimpleDateFormat(DATE_FORMAT).parse(stringToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;

    }
}
