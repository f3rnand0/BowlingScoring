package bowling.scoringapp.produce.score.file.impl;

import bowling.scoringapp.dtos.ScoreFrame;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class GenerateTenPinBowlScoringTest {
    private static ScoreFrame[] allResults;
    private static final int expectedScore = 54;

    public GenerateTenPinBowlScoringTest() {
    }

    @Before
    public void setup() {
        String[] players = {"A", "B"};
        Map<String,String[]> playersResults1 = new HashMap<>();
        Map<String,String[]> playersResults2 = new HashMap<>();
        Map<String,String[]> playersResults3 = new HashMap<>();
        Map<String,String[]> playersResults4 = new HashMap<>();
        Map<String,String[]> playersResults5 = new HashMap<>();
        Map<String,String[]> playersResults6 = new HashMap<>();
        Map<String,String[]> playersResults7 = new HashMap<>();
        Map<String,String[]> playersResults8 = new HashMap<>();
        Map<String,String[]> playersResults9 = new HashMap<>();
        Map<String,String[]> playersResults10 = new HashMap<>();
        playersResults1.put("A", new String[]{"2", "8"});
        playersResults1.put("B", new String[]{"3", "7"});
        ScoreFrame s1 = new ScoreFrame(playersResults1, 1);
        playersResults2.put("A", new String[]{"10", ""});
        playersResults2.put("B", new String[]{"6", "3"});
        ScoreFrame s2 = new ScoreFrame(playersResults2, 2);
        playersResults3.put("A", new String[]{"10", "", "2", "F"});
        playersResults3.put("B", new String[]{"", "10"});
        ScoreFrame s3 = new ScoreFrame(playersResults3, 3);
        allResults = new ScoreFrame[]{s1, s2, s3};
    }

    @Test
    public void calculateScores() {
        List<Integer> scores = new ArrayList<>(allResults.length);
        List<String> players = new ArrayList<>();

        // Get players
        for (String player: allResults[0].getPlayersResults().keySet()) {
            players.add(player);
        }

        // Calculate score for player A
        String playerA = players.get(0);
        int score = 0;
        for (int i = 0; i < allResults.length; i++) {
            // Get result of frame
            String[] frameResults = allResults[i].getPlayersResults().get(playerA);
            int frameResult1 = getInteger(frameResults[0]);
            int frameResult2 = getInteger(frameResults[1]);
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
            String[] frameResults = allResults[frame+1].getPlayersResults().get(player);
            bonus = getInteger(frameResults[0]);
        }
        // On last frame
        else {
            String lastBonus = allResults[frame].getPlayersResults().get(player)[2];
            bonus = getInteger(lastBonus);
        }
        return bonus;
    }

    private int nextStrikeBonus(int frame, String player) {
        int bonus = 0;
        // On first frames, except last two
        if ((frame + 1) < (allResults.length - 1)) {
            String[] frameResults = allResults[frame+1].getPlayersResults().get(player);
            bonus = getInteger(frameResults[0]);
            frameResults = allResults[frame+2].getPlayersResults().get(player);
            bonus += getInteger(frameResults[0]);
        }
        // On penultimate frame
        else if ((frame + 1) == (allResults.length - 1)) {
            String[] frameResults = allResults[frame + 1].getPlayersResults().get(player);
            int firstValue = getInteger(frameResults[0]);
            int secondValue = getInteger(frameResults[1]);
            // When there was strike on last frame
            if (firstValue == 10)
                bonus = getInteger(frameResults[0]) + getInteger(frameResults[2]);
            // When there was spare or some pinfalls on last frame
            else
                bonus = firstValue + secondValue;
        }
        // On last frame
        else {
            String result1 = allResults[frame].getPlayersResults().get(player)[2];
            String result2 = allResults[frame].getPlayersResults().get(player)[3];
            bonus = getInteger(result1) + getInteger(result2);
        }
        return bonus;
    }

    private int getInteger(String text) {
        if (NumberUtils.isParsable(text)) {
            return Integer.valueOf(text);
        }
        else {
            if (text.equals("F"))
                return 0;
            // When is empty string
            else
                return -1;
        }
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