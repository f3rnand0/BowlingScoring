package bowling.scoringapp.validate.input.impl;

import bowling.scoringapp.validate.input.api.ValidateInputFile;
import bowling.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidateInputTenPinBowlFile extends ValidateInputFile {

    @Override
    public Boolean validateEmptyLines(String[] lines) {
        for (int i = 0; i < lines.length; i++) {
            if (InputValidation.isEmptyLine(lines[i]))
                throw new RuntimeException(ErrorMessages.FILE_EMPTY_LINE);
        }
        return true;
    }

    @Override
    public Boolean validateTurnsCountPerPlayer(String[] lines) {
        String player;
        int resultInt1, resultInt2;
        int counter;
        boolean isPositiveInt1, isPositiveInt2;

        // Get results by player
        playersResults = DataTransformation.transformResultsToMap(lines);

        // Count turns by player
        playersTurns = DataTransformation.getTurnsByPlayer(playersResults);

        // Validate an exact number of turns
        for (Map.Entry<String, Integer> entry : playersTurns.entrySet()) {
            player = entry.getKey();
            resultInt1 = playersResults.get(player).get(playersResults.get(player).size() - 3);
            resultInt2 = playersResults.get(player).get(playersResults.get(player).size() - 2);
            isPositiveInt1 = Integer.signum(resultInt1) >= 0 ? true : false;
            isPositiveInt2 = Integer.signum(resultInt2) >= 0 ? true : false;

            // When there's a strike on last frame diminish two turns
            if (resultInt1 == 10) {
                counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                counter -= 2;
                playersTurns.put(player, counter);
            }
            // When there's a spare on last frame diminish one turn
            else if (isPositiveInt1 && isPositiveInt2 && (resultInt1 + resultInt2 == 10)) {
                counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                counter -= 1;
                playersTurns.put(player, counter);
            }

            // Throw exception when the number of turns is not correct
            if (entry.getValue() != Constants.NUMBER_OF_FRAMES) {
                throw new RuntimeException(ErrorMessages.FILE_INVALID_THROWS_COUNT);
            }
        }
        return true;
    }

    public Boolean validatePlayerResults(String[] lines) {
        // Get results by player
        playersResults = DataTransformation.transformResultsToMap(lines);
        // Get number of turns by player
        playersTurns = DataTransformation.getTurnsByPlayer(playersResults);
        // Get results by frame and by player
        playersResultsByFrame = getResultsByFrameByPlayer();

        // Validate results by frame are >=0 or <=10
        for (Map.Entry<String, List<Integer>> entry : playersResultsByFrame.entrySet()) {
            for (Integer sum : entry.getValue()) {
                if (sum < 0 || sum > 10)
                    throw new RuntimeException(ErrorMessages.FILE_INVALID_SUM_PLAYER_RESULTS);
            }
        }
        return true;
    }

    private Map<String, List<Integer>> getResultsByFrameByPlayer() {
        int result1, result2;
        for (Map.Entry<String, List<Integer>> entry : playersResults.entrySet()) {
            int counter = 0;
            String player = entry.getKey();
            List<Integer> playerResultsByFrame = new ArrayList<>();
            for (int i = 0; i < entry.getValue().size(); i++) {
                result1 = entry.getValue().get(i);
                if (!DataValidation.isPositiveInteger(result1)) {
                    throw new RuntimeException(ErrorMessages.FILE_INVALID_INDIVIDUAL_PLAYER_RESULT);
                }
                if (counter < 9 && result1 == 10) {
                    playerResultsByFrame.add(result1);
                    counter++;
                } else {
                    if (counter < 9) {
                        result2 = entry.getValue().get(i + 1);
                        if (!DataValidation.isPositiveInteger(result2)) {
                            throw new RuntimeException(ErrorMessages.FILE_INVALID_INDIVIDUAL_PLAYER_RESULT);
                        }
                        playerResultsByFrame.add(result1 + result2);
                        counter++;
                        // Add one to skip next line on file
                        i = i + 1;
                    } else if (counter == 9) {
                        // If no strike or spare happened on last turn
                        if (playersTurns.get(player) == 10) {
                            result2 = entry.getValue().get(i + 1);
                            if (!DataValidation.isPositiveInteger(result2)) {
                                throw new RuntimeException(ErrorMessages.FILE_INVALID_INDIVIDUAL_PLAYER_RESULT);
                            }
                            playerResultsByFrame.add(result1 + result2);
                            counter++;
                            // Add one to skip next line on file
                            i = i + 1;
                        }
                        // When spare happens on last frame
                        else if (playersTurns.get(player) == 11) {
                            result2 = entry.getValue().get(i + 1);
                            if (!DataValidation.isPositiveInteger(result2)) {
                                throw new RuntimeException(ErrorMessages.FILE_INVALID_INDIVIDUAL_PLAYER_RESULT);
                            }
                            playerResultsByFrame.add(result1 + result2);
                            counter++;
                            // Store one additional turn
                            result1 = entry.getValue().get(i + 2);
                            if (!DataValidation.isPositiveInteger(result1)) {
                                throw new RuntimeException(ErrorMessages.FILE_INVALID_INDIVIDUAL_PLAYER_RESULT);
                            }
                            playerResultsByFrame.add(result1);
                            counter++;
                            // Add two to skip next line on file
                            i = i + 2;
                        }
                        // When strike happens on last frame
                        else if (playersTurns.get(player) == 12) {
                            playerResultsByFrame.add(result1);
                            counter++;
                            // Store two additional turns
                            result1 = entry.getValue().get(i + 1);
                            if (!DataValidation.isPositiveInteger(result1)) {
                                throw new RuntimeException(ErrorMessages.FILE_INVALID_INDIVIDUAL_PLAYER_RESULT);
                            }
                            playerResultsByFrame.add(result1);
                            counter++;
                            result1 = entry.getValue().get(i + 2);
                            if (!DataValidation.isPositiveInteger(result1)) {
                                throw new RuntimeException(ErrorMessages.FILE_INVALID_INDIVIDUAL_PLAYER_RESULT);
                            }
                            playerResultsByFrame.add(result1);
                            counter++;
                            // Add three to skip next line on file
                            i = i + 3;
                        }
                    }
                }
            }
            playersResultsByFrame.put(player, playerResultsByFrame);
        }
        return playersResultsByFrame;
    }
}
