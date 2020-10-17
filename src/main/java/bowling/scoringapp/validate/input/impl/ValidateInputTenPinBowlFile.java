package bowling.scoringapp.validate.input.impl;

import bowling.scoringapp.validate.input.api.ValidateInputFile;
import bowling.utils.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ValidateInputTenPinBowlFile extends ValidateInputFile {

    @Override
    public boolean validateInput(String[] lines){
        for (int i = 0; i < lines.length; i++) {
            validateEmptyLines(lines[0]);

        }
        return false;
    }

    public Map<String, Integer> validateTurnsCountPerPlayer(String[] lines) {
        Map<String,List<Integer>> playersResults = new HashMap<String,List<Integer>>();
        String player = "", result = "";
        int resultInt1 = 0, resultInt2 = 0;
        int counter = 0;
        playersResults = DataTransformation.transformResultsToMap(lines);

        // Call to method that counts turns
        Map<String, Integer> playersTurns = checkTurnsByPlayer(playersResults);

        // Validate an exact number of turns
        for (Map.Entry<String, Integer> entry : playersTurns.entrySet()) {
            player = entry.getKey();
            resultInt1 = playersResults.get(player).get(playersResults.get(player).size() - 3);
            resultInt2 = playersResults.get(player).get(playersResults.get(player).size() - 2);

            // When there's a strike on last frame diminish two turns
            if (resultInt1 == 10) {
                counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                counter -= 2;
                playersTurns.put(player, counter);
            }
            // When there's a spare on last frame diminish one turn
            else if (resultInt1 + resultInt2 == 10) {
                counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                counter -= 1;
                playersTurns.put(player, counter);
            }

            // Throw exception when the number of turns is not correct
            if (entry.getValue() != Constants.NUMBER_OF_FRAMES) {
                throw new RuntimeException(ErrorMessages.FILE_INVALID_THROWS_COUNT);
            }
        }
        return playersTurns;
    }

    private Map<String, Integer> checkTurnsByPlayer(Map<String,List<Integer>> playersResults) {
        Map<String,Integer> playersTurns = new HashMap<String,Integer>();
        int counter = 0;
        int result1 = 0, result2 = 0;
        for (Map.Entry<String, List<Integer>> entry : playersResults.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                String player = entry.getKey();
                result1 = entry.getValue().get(i);
                if (result1 == 10) {
                    counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                    counter += 1;
                    playersTurns.put(player, counter);
                } else {
                    if ( (i+1) < entry.getValue().size() - 1) {
                        result2 = entry.getValue().get(i+1);
                        if (result1 + result2 > 10)
                            throw new RuntimeException(ErrorMessages.FILE_INVALID_SUM_PLAYER_RESULTS + " " + player + " " + result1 + " " + result2);
                        else {
                            // Add one to skip next line on file
                            i = i+1;
                            counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                            counter += 1;
                            playersTurns.put(player, counter);
                        }
                    }
                    else {
                        counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                        counter += 1;
                        playersTurns.put(player, counter);
                    }
                }
            }
        }
        return playersTurns;
    }

    // TODO: Por medio de DataTransformation.transformResultsToMap(lines); y algo parecido a checkTurnsByPlayer validar sumas y q resultados sean = o enteros positivos
    public Map<String, Integer> validatePlayerResultsFGU(Map<String,List<Integer>> playersResults) {
        Map<String,Integer> playersTurns = new HashMap<String,Integer>();
        int counter = 0;
        int result1 = 0, result2 = 0;
        for (Map.Entry<String, List<Integer>> entry : playersResults.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                String player = entry.getKey();
                result1 = entry.getValue().get(i);
                if (result1 == 10) {
                    counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                    counter += 1;
                    playersTurns.put(player, counter);
                } else {
                    if ( (i+1) < entry.getValue().size() - 1) {
                        result2 = entry.getValue().get(i+1);
                        if (result1 + result2 > 10)
                            throw new RuntimeException(ErrorMessages.FILE_INVALID_SUM_PLAYER_RESULTS + " " + player + " " + result1 + " " + result2);
                        else {
                            // Add one to skip next line on file
                            i = i+1;
                            counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                            counter += 1;
                            playersTurns.put(player, counter);
                        }
                    }
                    else {
                        counter = playersTurns.get(player) == null ? 0 : playersTurns.get(player);
                        counter += 1;
                        playersTurns.put(player, counter);
                    }
                }
            }
        }
        return playersTurns;
    }

    @Override
    public Boolean validatePlayerResults(String[] lines) {
        String player = "";
        int counter = 0;
        for (int i = 0; i < lines.length; i++) {
            // Get player name
            player = StringUtils.substringBefore(lines[i], "\t");
            // Get first result
            String result1 = StringUtils.substringAfter(lines[i],"\t");

            String nextPlayer = null;
            String result2 = null;

            // On first frames, except last one
            if (counter + 1 < 10) {
                // Add one to skip next line on file
                i = i+1;
                nextPlayer = StringUtils.substringBefore(lines[i+1],"\t");
                result2 = StringUtils.substringAfter(lines[i+1],"\t");

                int resultInt1 = DataValidation.isCorrectResult(result1);
                int resultInt2 = DataValidation.isCorrectResult(result2);
                // Check if result is lower or equal to 10
                if (resultInt1 != -1) {
                    // When strike happens
                    if (resultInt1 == 10)
                        if (player.equals(nextPlayer))
                            throw new RuntimeException(ErrorMessages.FILE_INVALID_PLAYER_RESULT_AFTER_STRIKE);
                    else
                        if ((resultInt1 + resultInt2) < 0 && resultInt1 + resultInt2 > 10)
                            throw new RuntimeException(ErrorMessages.FILE_INVALID_SUM_PLAYER_RESULTS);
                }
                else
                    throw new RuntimeException(ErrorMessages.FILE_INVALID_INDIVIDUAL_PLAYER_RESULT);
            }
            // On last frame
            //TODO: Validate enough lines for spare, stike or normal
            else {
                String result3 = "";
                nextPlayer = StringUtils.substringBefore(lines[i+1],"\t");
                result2 = StringUtils.substringAfter(lines[i+1],"\t");

                int resultInt1 = DataValidation.isCorrectResult(result1);
                int resultInt2 = DataValidation.isCorrectResult(result2);
                int resultInt3 = 0;
                // Check if result is lower or equal to 10
                if (resultInt1 != -1) {
                    // When strike happens
                    if (resultInt1 == 10) {
                        // TODO: Validate i+2 is possible
                        if (i+2 < lines.length) {
                            if (player.equals(nextPlayer))
                                result3 = StringUtils.substringAfter(lines[i + 2], "\t");
                            else
                                throw new RuntimeException(ErrorMessages.FILE_INVALID_PLAYER_RESULT_AFTER_STRIKE);
                            resultInt3 = DataValidation.isCorrectResult(result3);
                            if (resultInt3 != -1)
                                throw new RuntimeException(ErrorMessages.FILE_INVALID_INDIVIDUAL_PLAYER_RESULT);
                        }
                        else
                            throw new RuntimeException(ErrorMessages.FILE_MISSING_PLAYER_RESULT_AFTER_STRIKE);
                    }
                    // When spare happens
                    else if ((resultInt1 + resultInt2) < 0 && (resultInt1 + resultInt2) > 10)
                            throw new RuntimeException(ErrorMessages.FILE_INVALID_SUM_PLAYER_RESULTS);
                }
                else
                    throw new RuntimeException(ErrorMessages.FILE_INVALID_INDIVIDUAL_PLAYER_RESULT);
            }
            // Count a new frame
            counter++;
        }
        return true;
    }

    @Override
    public Boolean validateScore(String input) {
        return false;
    }

    @Override
    public Boolean validateEmptyLines(String line) {
        if (InputValidation.isEmptyLine(line))
            throw new RuntimeException(ErrorMessages.FILE_EMPTY_LINE);
        return true;
    }
}
