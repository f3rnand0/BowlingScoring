package bowling.scoringapp.transform.input.impl;

import bowling.scoringapp.dtos.FrameData;
import bowling.scoringapp.dtos.Results;
import bowling.scoringapp.transform.input.api.ITransformInput;
import bowling.utils.Constants;
import bowling.utils.DataTransformation;
import bowling.utils.DataValidation;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class TransformTenPinBowlFile implements ITransformInput {

    @Override
    public FrameData[][] transformInputByFrameByPlayer(String[] lines) {
        Map<String, List<Integer>> playersResults = DataTransformation.transformResultsIntToMap(lines);
        Map<String, Integer> playersTurns = DataTransformation.getTurnsIntByPlayer(playersResults);
        Set<String> playersNames = playersResults.keySet();
        FrameData[][] allFrames = new FrameData[playersNames.size()][Constants.NUMBER_OF_FRAMES];
        int counter = 0;
        String player;
        for (Iterator<String> it = playersNames.iterator(); it.hasNext(); counter++) {
            player = it.next();
            FrameData[] frames = transformInputByFrameByPlayer(lines, player, playersTurns.get(player));
            allFrames[counter] = frames;
        }
        return allFrames;
    }

    private FrameData[] transformInputByFrameByPlayer(String[] lines, String player, Integer playerTurns) {
        FrameData[] frames = new FrameData[Constants.NUMBER_OF_FRAMES];
        FrameData frameData;
        String playerLine, result1, result2, result3;
        int counter = 0, result1Int, result2Int;
        Character mark;
        for (int i = 0; i < lines.length; i++) {
            playerLine = StringUtils.substringBefore(lines[i], "\t");
            // Identify result as a number, F or a different text (in that case will be -100000)
            result1 = DataValidation.getResultAsString(StringUtils.substringAfter(lines[i], "\t"));
            if (playerLine.equals(player)) {
                // Add results for all frames except last one
                if (counter < 9 && result1.equals("10")) {
                    mark = 'X';
                    frameData = fillDTO(mark, playerLine, counter + 1, result1, "");
                    // Add frame to array
                    frames[counter] = frameData;
                    counter++;
                }
                // Add results for last frame or when result is different from 10
                else {
                    if (counter < 9) {
                        result2 = DataValidation.getResultAsString(StringUtils.substringAfter(lines[i + 1], "\t"));
                        result1Int = result1.equals("F") ? 0 : Integer.valueOf(result1);
                        result2Int = result2.equals("F") ? 0 : Integer.valueOf(result2);
                        // When a spare happens
                        if (result1Int + result2Int == 10)
                            mark = '/';
                            // When a normal turn happens
                        else
                            mark = '#';

                        frameData = fillDTO(mark, playerLine, counter + 1, result1, result2);
                        // Add frame to array
                        frames[counter] = frameData;
                        counter++;
                        // Add one to skip next line
                        i = i + 1;
                    }
                    else if (counter == 9) {
                        // If no strike or spare happened on last turn
                        if (playerTurns == 10) {
                            result2 = DataValidation.getResultAsString(StringUtils.substringAfter(lines[i + 1], "\t"));
                            mark = '#';
                            frameData = fillDTO(mark, playerLine, counter + 1, result1, result2);
                            // Add frame to array
                            frames[counter] = frameData;
                            counter++;
                            // Add one to skip next line
                            i = i + 1;
                        }
                        // When spare happens on last frame
                        else if (playerTurns == 11) {
                            result2 = DataValidation.getResultAsString(StringUtils.substringAfter(lines[i + 1], "\t"));
                            // Store one additional turn
                            result3 = DataValidation.getResultAsString(StringUtils.substringAfter(lines[i + 2], "\t"));
                            mark = '/';
                            frameData = fillDTO(mark, playerLine, counter + 1, result1, result2, result3);
                            // Add frame to array
                            frames[counter] = frameData;
                            counter++;
                            // Add two to skip next lines
                            i = i + 2;
                        }
                        // When strike happens on last frame
                        else if (playerTurns == 12) {
                            result2 = DataValidation.getResultAsString(StringUtils.substringAfter(lines[i + 1], "\t"));
                            // Store two additional turns
                            result3 = DataValidation.getResultAsString(StringUtils.substringAfter(lines[i + 2], "\t"));
                            mark = 'X';
                            frameData = fillDTO(mark, playerLine, counter + 1, result1, result2, result3);
                            // Add frame to array
                            frames[counter] = frameData;
                            counter++;
                            // Add three to skip next lines
                            i = i + 2;
                        }
                    }
                }
            }
        }
        return frames;
    }

    private FrameData fillDTO(Character mark, String player, Integer counter, String... values) {
        FrameData frameData = new FrameData();
//        Map<String, Results> mapResults = new HashMap<>();
        Results results = new Results(new ArrayList<>(), mark);
        List<String> pinFalls = new ArrayList<>();
        for (String value : values) {
            pinFalls.add(value);
        }
        results.setPinFalls(pinFalls);
        results.setMark(mark);
//        mapResults.put(player, results);
        frameData.setFrame(counter);
        frameData.setPlayer(player);
        frameData.setResults(results);
        return frameData;
    }

        /*public FrameData[] transformInputByFrameByPlayer(String[] lines) {
        FrameData[] frames = new FrameData[Constants.NUMBER_OF_FRAMES];
        Character mark = '#';
        int frame = 1, counter;
        Map<String, List<String>> playersResults = DataTransformation.transformResultsStringToMap(lines);
        Map<String, Integer> playersTurns = DataTransformation.getTurnsStringByPlayer(playersResults);
        for (int i = 0; i < Constants.NUMBER_OF_FRAMES; i++) {
            // Initialize a new FrameData object
            FrameData frameData = new FrameData();
            Map<String, Results> mapResults = new HashMap<>();

            // Iterate over each list of results by every player
            for (Map.Entry<String, List<String>> entry : playersResults.entrySet()) {
                counter = 0;
                String result1, result2, result3, result4;
                int result1Int, result2Int;
                String player = entry.getKey();
                Results results = new Results(new ArrayList<>(), mark);
                // Iterate over each result of a specific player
                for (int j = 0; j < entry.getValue().size(); j++) {
                    List<String> pinFalls = new ArrayList<>();
                    result1 = entry.getValue().get(j);
                    // Add results for all frames except last one
                    if (counter < 9 && result1.equals("10")) {
                        mark = 'X';
                        pinFalls = results.getPinFalls();
                        pinFalls.add(result1);
                        pinFalls.add("");
                        results.setPinFalls(pinFalls);
                        results.setMark(mark);
                        mapResults.put(player, results);
                    }
                    // Add results for last frame or when result is different from 10
                    else {
                        if (counter < 9) {
                            result2 = entry.getValue().get(j + 1);
                            result1Int = result1.equals("F") ? 0 : Integer.valueOf(result1);
                            result2Int = result2.equals("F") ? 0 : Integer.valueOf(result2);
                            // When a spare happens
                            if (result1Int + result2Int == 10)
                                mark = '/';
                                // When a normal turn happens
                            else
                                mark = '#';

                            pinFalls = results.getPinFalls();
                            pinFalls.add(result1);
                            pinFalls.add(result2);
                            results.setPinFalls(pinFalls);
                            results.setMark(mark);
                            mapResults.put(player, results);

                            //mapResults.put(player, new Results(new String[]{result1, result2}, mark));
                            // Add one to skip next result item
                            j = j + 1;
                        } else if (counter == 9) {
                            // If no strike or spare happened on last turn
                            if (playersTurns.get(player) == 10) {
                                mark = '#';
                                result2 = entry.getValue().get(j + 1);
                                pinFalls = results.getPinFalls();
                                pinFalls.add(result1);
                                pinFalls.add(result2);
                                results.setPinFalls(pinFalls);
                                results.setMark(mark);
                                mapResults.put(player, results);
                                //mapResults.put(player, new Results(new String[]{result1, result2}, mark));
                                // Add one to skip next result item
                                j = j + 1;
                            }
                            // When spare happens on last frame
                            else if (playersTurns.get(player) == 11) {
                                mark = '/';
                                result2 = entry.getValue().get(j + 1);
                                // Store one additional turn
                                result3 = entry.getValue().get(j + 2);
                                pinFalls = results.getPinFalls();
                                pinFalls.add(result1);
                                pinFalls.add(result2);
                                pinFalls.add(result3);
                                results.setPinFalls(pinFalls);
                                results.setMark(mark);
                                mapResults.put(player, results);
                                //mapResults.put(player, new Results(new String[]{result1, result2, result3}, mark));
                                // Add two to skip next results items
                                j = j + 2;
                            }
                            // When strike happens on last frame
                            else if (playersTurns.get(player) == 12) {
                                mark = 'X';
                                result2 = entry.getValue().get(j + 1);
                                // Store two additional turns
                                result3 = entry.getValue().get(j + 2);
                                result4 = entry.getValue().get(j + 3);
                                pinFalls = results.getPinFalls();
                                pinFalls.add(result1);
                                pinFalls.add(result2);
                                pinFalls.add(result3);
                                pinFalls.add(result4);
                                results.setPinFalls(pinFalls);
                                results.setMark(mark);
                                mapResults.put(player, results);
                                //mapResults.put(player, new Results(new String[]{result1, "", result2, result3}, mark));
                                // Add three to skip next results items
                                j = j + 3;
                            }
                        }
                    }
                    counter++;
                } // end for loop (results by every player)
                // Add results to the frame
                frameData.setFrame(frame);
                frameData.setResults(mapResults);
            }  // end for loop (players)
            // Add frame to array
            frames[i] = frameData;
            frame++;
        } // end for loop (frames)
        return frames;
    }*/


    /*public FrameData transformInputByFrameByPlayerFGU(String[] lines) {
        Character mark = '#';
        Map<String, List<String>> playersResults = DataTransformation.transformResultsStringToMap(lines);
        Map<String, Integer> playersTurns = DataTransformation.getTurnsStringByPlayer(playersResults);
        // Initialize a new FrameData object
        FrameData frameData = new FrameData();
        // Iterate over each list of results
        for (Map.Entry<String, List<String>> entry : playersResults.entrySet()) {
            Map<String, Results> mapResults = new HashMap<>();
            frameData.setResults(mapResults);

            String player = entry.getKey();
            int frame = 1;
            int counter = 0;
            Results results = new Results(new ArrayList<>(), frame, mark);
            // Iterate over each result of a specific player
            for (int j = 0; j < entry.getValue().size(); j++) {
                List<String> pinFalls;
                String result1, result2, result3;
                int result1Int, result2Int;
                result1 = entry.getValue().get(j);
                // Add results for all frames except last one
                if (counter < 9 && result1.equals("10")) {
                    mark = 'X';
                    pinFalls = results.getPinFalls();
                    pinFalls.add(result1);
                    pinFalls.add("");
                    results.setPinFalls(pinFalls);
                    results.setFrame(frame);
                    results.setMark(mark);
                    mapResults.put(player, results);
                }
                // Add results for last frame or when result is different from 10
                else {
                    if (counter < 9) {
                        result2 = entry.getValue().get(j + 1);
                        result1Int = result1.equals("F") ? 0 : Integer.valueOf(result1);
                        result2Int = result2.equals("F") ? 0 : Integer.valueOf(result2);
                        // When a spare happens
                        if (result1Int + result2Int == 10)
                            mark = '/';
                            // When a normal turn happens
                        else
                            mark = '#';

                        pinFalls = results.getPinFalls();
                        pinFalls.add(result1);
                        pinFalls.add(result2);
                        results.setPinFalls(pinFalls);
                        results.setFrame(frame);
                        results.setMark(mark);
                        mapResults.put(player, results);

                        //mapResults.put(player, new Results(new String[]{result1, result2}, mark));
                        // Add one to skip next result item
                        j = j + 1;
                    } else if (counter == 9) {
                        // If no strike or spare happened on last turn
                        if (playersTurns.get(player) == 10) {
                            mark = '#';
                            result2 = entry.getValue().get(j + 1);
                            pinFalls = results.getPinFalls();
                            pinFalls.add(result1);
                            pinFalls.add(result2);
                            results.setPinFalls(pinFalls);
                            results.setFrame(frame);
                            results.setMark(mark);
                            mapResults.put(player, results);
                            //mapResults.put(player, new Results(new String[]{result1, result2}, mark));
                            // Add one to skip next result item
                            j = j + 1;
                        }
                        // When spare happens on last frame
                        else if (playersTurns.get(player) == 11) {
                            mark = '/';
                            result2 = entry.getValue().get(j + 1);
                            // Store one additional turn
                            result3 = entry.getValue().get(j + 2);
                            pinFalls = results.getPinFalls();
                            pinFalls.add(result1);
                            pinFalls.add(result2);
                            pinFalls.add(result3);
                            results.setPinFalls(pinFalls);
                            results.setFrame(frame);
                            results.setMark(mark);
                            mapResults.put(player, results);
                            //mapResults.put(player, new Results(new String[]{result1, result2, result3}, mark));
                            // Add two to skip next results items
                            j = j + 2;
                        }
                        // When strike happens on last frame
                        else if (playersTurns.get(player) == 12) {
                            mark = 'X';
                            result2 = entry.getValue().get(j + 1);
                            // Store two additional turns
                            result3 = entry.getValue().get(j + 2);
                            pinFalls = results.getPinFalls();
                            pinFalls.add(result1);
                            pinFalls.add("");
                            pinFalls.add(result2);
                            pinFalls.add(result3);
                            results.setPinFalls(pinFalls);
                            results.setFrame(frame);
                            results.setMark(mark);
                            mapResults.put(player, results);
                            //mapResults.put(player, new Results(new String[]{result1, "", result2, result3}, mark));
                            // Add three to skip next results items
                            j = j + 3;
                        }
                    }
                }
                counter++;
                frame++;
            } // end for loop (results by every player)
        }  // end for loop (players)
        return frameData;
    }*/

    /*private Map<String, Integer> getTurnsByPlayer(Map<String, List<String>> playersResults) {
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
    }*/

}
