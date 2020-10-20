package bowling.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTransformation {


    public static Map<String, List<Integer>> transformResultsToMap(String[] lines) {
        Map<String, List<Integer>> playersResults = new HashMap<>();
        String player, result;
        int resultInt;
        for (int i = 0; i < lines.length; i++) {
            List<Integer> results;
            player = StringUtils.substringBefore(lines[i], "\t");
            result = StringUtils.substringAfter(lines[i], "\t");
            resultInt = DataValidation.getResultAsActualInteger(result);

            // Add every result to each player
            if (playersResults.containsKey(player))
                results = playersResults.get(player);
            else
                results = new ArrayList<>();

            results.add(resultInt);
            playersResults.put(player, results);
        }
        return playersResults;
    }

    public static Map<String, Integer> getTurnsByPlayer(Map<String, List<Integer>> playersResults) {
        Map<String, Integer> playersTurns = new HashMap<>();
        int counter;
        int result1;
        for (Map.Entry<String, List<Integer>> entry : playersResults.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                String player = entry.getKey();
                result1 = entry.getValue().get(i);
                if (result1 == 10) {
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
