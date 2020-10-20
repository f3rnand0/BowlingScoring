package bowling.scoringapp.generate.score.impl;

import bowling.scoringapp.dtos.FrameData;
import bowling.scoringapp.generate.score.api.IGenerateScoring;
import bowling.utils.DataValidation;

import java.util.List;

public class GenerateTenPinBowlScoring implements IGenerateScoring {

    @Override
    public FrameData[][] calculateScores(FrameData[][] allFrames) {
        String nextResult1, nextResult2;
        int nextResultInt1;
        for (int i = 0; i < allFrames.length; i++) {
            int score = 0;
            for (int j = 0; j < allFrames[i].length; j++) {
                Character mark = allFrames[i][j].getResults().getMark();
                // When normal turn
                if (mark.equals('#')) {
                    score += calculateTotalResult(allFrames[i][j].getResults().getPinFalls());
                    allFrames[i][j].getResults().setScore(score);
                }
                // When spare happens
                else if (mark.equals('/')) {
                    // Get result from next throw on all frames except last one
                    if (j < allFrames[i].length - 1) {
                        String nextResult = allFrames[i][j + 1].getResults().getPinFalls().get(0);
                        score += 10 + DataValidation.getResultAsInteger(nextResult);
                        allFrames[i][j].getResults().setScore(score);
                    }
                    // Get result from last throw on last frame
                    else {
                        String lastResult = allFrames[i][j].getResults().getPinFalls().get(2);
                        score += 10 + DataValidation.getResultAsInteger(lastResult);
                        allFrames[i][j].getResults().setScore(score);
                    }
                } else if (mark.equals('X')) {
                    // Get results from next two throws on all frames except last two
                    if ((j + 1) < allFrames[i].length - 1) {
                        nextResult1 = allFrames[i][j + 1].getResults().getPinFalls().get(0);
                        // When strike happens on next throw
                        if (DataValidation.getResultAsInteger(nextResult1) == 10) {
                            nextResult2 = allFrames[i][j + 2].getResults().getPinFalls().get(0);
                            score += 10 + DataValidation.getResultAsInteger(nextResult1)
                                    + DataValidation.getResultAsActualInteger(nextResult2);
                            allFrames[i][j].getResults().setScore(score);
                        }
                        // When no strike happens on next throw
                        else {
                            nextResult2 = allFrames[i][j + 1].getResults().getPinFalls().get(1);
                            score += 10 + DataValidation.getResultAsInteger(nextResult1)
                                    + DataValidation.getResultAsInteger(nextResult2);
                            allFrames[i][j].getResults().setScore(score);
                        }
                    }
                    // Get results from next two throws on penultimate frame
                    else if ((j + 1) == allFrames[i].length - 1) {
                        int bonus;
                        nextResult1 = allFrames[i][j + 1].getResults().getPinFalls().get(0);
                        nextResult2 = allFrames[i][j + 1].getResults().getPinFalls().get(1);
                        nextResultInt1 = DataValidation.getResultAsInteger(nextResult1);
                        // When there was strike on last frame
                        bonus = nextResultInt1 + DataValidation.getResultAsInteger(nextResult2);
                        score += 10 + bonus;
                        allFrames[i][j].getResults().setScore(score);
                    }
                    // Get results from next two throws on last frame
                    else {
                        nextResult1 = allFrames[i][j].getResults().getPinFalls().get(1);
                        nextResult2 = allFrames[i][j].getResults().getPinFalls().get(2);
                        score += 10 + DataValidation.getResultAsInteger(nextResult1)
                                + DataValidation.getResultAsInteger(nextResult2);
                        allFrames[i][j].getResults().setScore(score);
                    }
                }
            }
        }
        return allFrames;
    }

    private int calculateTotalResult(List<String> results) {
        int sum = 0;
        for (String result : results)
            sum += DataValidation.getResultAsInteger(result);
        return sum;
    }

}
