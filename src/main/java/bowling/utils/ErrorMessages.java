package bowling.utils;

public class ErrorMessages {
    public static final String FILE_EMPTY_LINE  = "ERROR!!! Scoring file has empty lines";
    public static final String FILE_INVALID_INDIVIDUAL_PLAYER_RESULT  = "ERROR!!! Individual result not valid for player";
    public static final String FILE_INVALID_PLAYER_RESULT_AFTER_STRIKE  = "ERROR!!! Next result is not aplicable for a previous strike for player";
    public static final String FILE_MISSING_PLAYER_RESULT_AFTER_STRIKE  = "ERROR!!! Missing result for a previous strike for player";
    public static final String FILE_INVALID_SUM_PLAYER_RESULTS  = "ERROR!!! The sum of the frame results are not correct for player";
    public static final String FILE_INVALID_THROWS_COUNT  = "ERROR!!! The number of throws is not ten for player";
}

