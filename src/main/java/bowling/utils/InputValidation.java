package bowling.utils;

import org.apache.commons.lang3.StringUtils;

public class InputValidation {

    public static boolean isEmptyLine(String line) {
        if (StringUtils.isBlank(line))
            return true;
        else
            return false;
    }
}
