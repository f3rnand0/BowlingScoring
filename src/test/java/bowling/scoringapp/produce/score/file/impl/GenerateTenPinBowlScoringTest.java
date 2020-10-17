package bowling.scoringapp.produce.score.file.impl;

import bowling.scoringapp.dtos.FrameData;
import bowling.scoringapp.dtos.Results;
import bowling.utils.DataValidation;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GenerateTenPinBowlScoringTest {
    private static FrameData[] allResults;
    private static final int expectedScore = 54;

    public GenerateTenPinBowlScoringTest() {
    }

    @Before
    public void setup() {
        String[] players = {"A", "B"};
        Map<String,Results> playersResults1 = new HashMap<>();
        Map<String,Results> playersResults2 = new HashMap<>();
        Map<String,Results> playersResults3 = new HashMap<>();
        Map<String,Results> playersResults4 = new HashMap<>();
        Map<String,Results> playersResults5 = new HashMap<>();
        Map<String,Results> playersResults6 = new HashMap<>();
        Map<String,Results> playersResults7 = new HashMap<>();
        Map<String,Results> playersResults8 = new HashMap<>();
        Map<String,Results> playersResults9 = new HashMap<>();
        Map<String,Results> playersResults10 = new HashMap<>();
        playersResults1.put("A", new Results(new String[]{"2", "8"}));
        playersResults1.put("B", new Results(new String[]{"3", "7"}));
        FrameData s1 = new FrameData(playersResults1, 1, "");
        playersResults2.put("A", new Results(new String[]{"10", ""}));
        playersResults2.put("B", new Results(new String[]{"6", "3"}));
        FrameData s2 = new FrameData(playersResults2, 2, "");
        playersResults3.put("A", new Results(new String[]{"10", "", "2", "F"}));
        playersResults3.put("B", new Results(new String[]{"", "10"}));
        FrameData s3 = new FrameData(playersResults3, 3, "");
        allResults = new FrameData[]{s1, s2, s3};
    }

    @Test
    public void calculateScores() {
        List<Integer> scores = new ArrayList<>(allResults.length);
        List<String> players = new ArrayList<>();

        // Get players
        for (String player: allResults[0].getResults().keySet()) {
            players.add(player);
        }

        // Calculate score for player A
        String playerA = players.get(0);
        int score = 0;
        for (int i = 0; i < allResults.length; i++) {
            // Get result of frame
            String[] frameResults = allResults[i].getResults().get(playerA).getPinfalls();
            int frameResult1 = DataValidation.getInteger(frameResults[0]);
            int frameResult2 = DataValidation.getInteger(frameResults[1]);
            System.out.println("frameResult1: " +frameResult1);
            System.out.println("frameResult2: " +frameResult2);

            // Normal and spare calculation
            if (frameResult1 > -1 && frameResult2 > -1) {
                // Spare
                if (frameResult1 + frameResult2 == 10)
                    score += 10 + nextSpareBonus(i,playerA);
                else
                    score += frameResult1 + frameResult2;
            }
            // Strike calculation
            else
                score += 10 + nextStrikeBonus(i,playerA);
            // Add to score list
            scores.add(score);
        }
        System.out.println("Scores: " + scores);

        assertEquals(expectedScore, scores.get(allResults.length-1).intValue());
//        return scores;
    }

    private int nextSpareBonus(int frame, String player) {
        int bonus = 0;
        // On all frames except last one
        if (frame < allResults.length - 1) {
            String[] frameResults = allResults[frame+1].getResults().get(player).getPinfalls();
            bonus = DataValidation.getInteger(frameResults[0]);
        }
        // On last frame
        else {
            String lastBonus = allResults[frame].getResults().get(player).getPinfalls()[2];
            bonus = DataValidation.getInteger(lastBonus);
        }
        return bonus;
    }

    private int nextStrikeBonus(int frame, String player) {
        int bonus = 0;
        // On first frames, except last two
        if ((frame + 1) < (allResults.length - 1)) {
            String[] frameResults = allResults[frame+1].getResults().get(player).getPinfalls();
            bonus = DataValidation.getInteger(frameResults[0]);
            frameResults = allResults[frame+2].getResults().get(player).getPinfalls();
            bonus += DataValidation.getInteger(frameResults[0]);
        }
        // On penultimate frame
        else if ((frame + 1) == (allResults.length - 1)) {
            String[] frameResults = allResults[frame + 1].getResults().get(player).getPinfalls();
            int firstValue = DataValidation.getInteger(frameResults[0]);
            int secondValue = DataValidation.getInteger(frameResults[1]);
            // When there was strike on last frame
            if (firstValue == 10)
                bonus = DataValidation.getInteger(frameResults[0]) + DataValidation.getInteger(frameResults[2]);
            // When there was spare or some pinfalls on last frame
            else
                bonus = firstValue + secondValue;
        }
        // On last frame
        else {
            String result1 = allResults[frame].getResults().get(player).getPinfalls()[2];
            String result2 = allResults[frame].getResults().get(player).getPinfalls()[3];
            bonus = DataValidation.getInteger(result1) + DataValidation.getInteger(result2);
        }
        return bonus;
    }



    private int getPositiveInteger(String text) {
        if (NumberUtils.isParsable(text)) {
            int value = Integer.valueOf(text);
            if (Integer.signum(value) > 0)
                return value;
        }
        return 0;
    }

}