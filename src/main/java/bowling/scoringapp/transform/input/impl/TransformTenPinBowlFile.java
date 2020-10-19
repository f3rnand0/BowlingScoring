package bowling.scoringapp.transform.input.impl;

import bowling.scoringapp.dtos.FrameData;
import bowling.scoringapp.dtos.Results;
import bowling.scoringapp.transform.input.api.ITransformInput;
import bowling.utils.Constants;
import bowling.utils.DataTransformation;
import bowling.utils.DataValidation;
import bowling.utils.ErrorMessages;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransformTenPinBowlFile<E> implements ITransformInput {

    @Override
    public FrameData[] transformInputByFrameByPlayer(String[] lines) {
        FrameData[] frames = new FrameData[Constants.NUMBER_OF_FRAMES];
        String player;
        String mark = "";
        int frame = 1;
        String result1, result2;
        int result1Int, result2Int;
        Map<String, List<String>> playersResults = DataTransformation.transformResultsStringToMap(lines);
        Map<String, Integer> playersTurns = getTurnsByPlayer(playersResults);
        for (Map.Entry<String, List<String>> entry : playersResults.entrySet()) {
            int counter = 0;
            Map<String, Results> results = new HashMap<>();
            FrameData frameData = new FrameData(frame, results, mark);
            // Get player name
            player = entry.getKey();
            for (int i = 0; i < entry.getValue().size(); i++) {
                result1 = entry.getValue().get(i);
                if (counter < 9 && result1.equals("10")) {
                    frameData.setMark("X");
                    results.put(player, new Results(new String[]{result1, ""}));
                } else {
                    if (counter < 9) {
                        result2 = entry.getValue().get(i + 1);
                        result1Int = result1.equals("F") ? 0 : Integer.valueOf(result1);
                        result2Int = result2.equals("F") ? 0 : Integer.valueOf(result2);
                        // When a spare happens
                        if (result1Int + result2Int == 10)
                            frameData.setMark("/");
                            // When a normal turn happens
                        else
                            frameData.setMark("#");

                        results.put(player, new Results(new String[]{result1, result2}));
                        // Add one to skip next line on file
                        i = i + 1;
                    } else if (counter == 9) {
                        // TODO: AQUI ME QUEDE
                        // If no strike or spare happened on last turn
                        if (playersTurns.get(player) == 10) {
                            result2 = entry.getValue().get(i + 1);
                            results.put(player, new Results(new String[]{result1, result2}));
                            // Add one to skip next line on file
                            i = i + 1;
                        }
                        // When spare happens on last frame
                        else if (playersTurns.get(player) == 11) {
                            result2 = entry.getValue().get(i + 1);
                            results.put(player, new Results(new String[]{result1, result2}));
                            // Add one to skip next line on file
                            i = i + 1;
                            // Store one additional turn
                            result1 = entry.getValue().get(i + 2);
                            //playerResultsByFrame.add(result1);
                            // Add two to skip next line on file
                            i = i + 2;
                        }
                        // When strike happens on last frame
                        else if (playersTurns.get(player) == 12) {
                            //playerResultsByFrame.add(result1);
                            // Store two additional turns
                            result1 = entry.getValue().get(i + 1);
                            //playerResultsByFrame.add(result1);
                            result1 = entry.getValue().get(i + 2);
                            //playerResultsByFrame.add(result1);
                            // Add three to skip next line on file
                            i = i + 3;
                        }
                    }
                }
                counter++;
            }
            // Add frame to array
            frames[frame] = frameData;
            frame++;
        }
        return frames;
    }

    private Map<String, Integer> getTurnsByPlayer(Map<String, List<String>> playersResults) {
        int counter;
        String result;
        Map<String, Integer> playersTurns = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : playersResults.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                String player = entry.getKey();
                result = entry.getValue().get(i);
                if (result.equals(10)) {
                    counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                    counter++;
                    playersTurns.put(player, counter);
                } else {
                    if ((i + 1) < entry.getValue().size() - 1) {
                        counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                        counter++;
                        playersTurns.put(player, counter);
                        // Add one to skip next line on file
                        i = i + 1;
                    } else {
                        counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                        counter++;
                        playersTurns.put(player, counter);
                    }
                }
            }
        }
        return playersTurns;
    }
}
