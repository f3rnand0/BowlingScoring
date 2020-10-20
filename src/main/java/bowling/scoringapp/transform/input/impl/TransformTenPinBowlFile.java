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
        Map<String, List<Integer>> playersResults = DataTransformation.transformResultsToMap(lines);
        Map<String, Integer> playersTurns = DataTransformation.getTurnsByPlayer(playersResults);
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
                    } else if (counter == 9) {
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
        Results results = new Results(new ArrayList<>(), mark);
        List<String> pinFalls = new ArrayList<>();
        for (String value : values) {
            pinFalls.add(value);
        }
        results.setPinFalls(pinFalls);
        results.setMark(mark);
        frameData.setFrame(counter);
        frameData.setPlayer(player);
        frameData.setResults(results);
        return frameData;
    }

}
