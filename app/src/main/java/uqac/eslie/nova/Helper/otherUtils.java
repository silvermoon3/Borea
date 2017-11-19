package uqac.eslie.nova.Helper;

import java.text.Normalizer;

/**
 * Created by eliea on 17/11/2017.
 */

public class otherUtils {
    public static String UTC(String currentTime) {
        String UTCTime = "";
        int minutes = Integer.parseInt(currentTime.substring(3, 5));
        int hours = Integer.parseInt(currentTime.substring(0, 2));

        if (minutes - 5 < 0) {
            hours--;
            minutes += 60 - 5;
        } else {
            minutes -= 5;
        }

        if (hours + 5 >= 24) {
            //day--;
            hours -= 24 - 5;
        } else {
            hours += 5;
        }
        String hour = "";
        if (hours < 10) {
            hour = "0" + String.valueOf(hours);
        } else {
            hour = String.valueOf(hours);
        }
        String minute = "";
        if (minutes < 10) {
            minute = "0" + String.valueOf(minutes);
        } else {
            minute = String.valueOf(minutes);
        }

        UTCTime = hour + ":" + minute;
        return UTCTime;
    }

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }
}
