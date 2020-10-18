package bowling.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTransformation {

    public static Map<String, List<Integer>> playersResults = new HashMap<String,List<Integer>>();

    public static Map<String, List<Integer>> transformResultsToMap(String[] lines) {
        Map<String,List<Integer>> playersResults = new HashMap<String,List<Integer>>();
        String player = "", result = "";
        int resultInt1 = 0;
        for (int i = 0; i < lines.length; i++) {
            player = StringUtils.substringBefore(lines[i], "\t");
            result = StringUtils.substringAfter(lines[i], "\t");
            resultInt1 = DataValidation.getActualInteger(result);

            // Add every result to each player
            if (playersResults.containsKey(player)) {
                List<Integer> results = playersResults.get(player);
                results.add(resultInt1);
                playersResults.put(player,results);
            }
            else {
                List<Integer> results = new ArrayList<>();
                results.add(resultInt1);
                playersResults.put(player, results);
            }
        }
        return playersResults;
    }
}
