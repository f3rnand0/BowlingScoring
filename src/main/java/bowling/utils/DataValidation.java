package bowling.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class DataValidation {

    public static int getResultAsInteger(String text) {
        if (NumberUtils.isParsable(text)) {
            return Integer.valueOf(text);
        } else {
            if (text.equals("F"))
                return 0;
                // When it's an empty string (strike)
            else
                return -1;
        }
    }

    public static int getResultAsActualInteger(String text) {
        if (NumberUtils.isParsable(text)) {
            return Integer.valueOf(text);
        } else {
            if (text.equals("F"))
                return 0;
                // When it's an empty string (strike)
            else
                return Constants.INTEGER_FOR_CHAR_IDENTIFIER;
        }
    }

    public static String getResultAsString(String text) {
        if (NumberUtils.isParsable(text)) {
            return text;
        } else {
            if (text.equals("F"))
                return text;
            else
                return Constants.INTEGER_FOR_CHAR_IDENTIFIER.toString();
        }
    }

    public static boolean isPositiveInteger(Integer number) {
        return Integer.signum(number) >= 0 ? true : false;
    }
}
