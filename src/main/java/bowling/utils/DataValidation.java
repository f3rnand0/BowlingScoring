package bowling.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class DataValidation {

    public static int getInteger(String text) {
        if (NumberUtils.isParsable(text)) {
            return Integer.valueOf(text);
        }
        else {
            if (text.equals("F"))
                return 0;
            // When it's an empty string (strike)
            else
                return -1;
        }
    }

    public static int isCorrectResult(String text) {
        if (NumberUtils.isParsable(text)) {
            int value = Integer.valueOf(text);
            if (Integer.valueOf(text) >= 0 || Integer.valueOf(text) <= 10)
                return value;
            else
                return -1;
        }
        else {
            if (text.equals("F"))
                return 0;
            else
                return -1;
        }
    }
}
