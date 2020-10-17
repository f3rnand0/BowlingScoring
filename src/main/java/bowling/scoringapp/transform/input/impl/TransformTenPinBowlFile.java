package bowling.scoringapp.transform.input.impl;

import bowling.scoringapp.dtos.FrameData;
import bowling.scoringapp.dtos.Results;
import bowling.scoringapp.transform.input.api.ITransformInput;
import bowling.utils.DataValidation;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TransformTenPinBowlFile implements ITransformInput {


    @Override
    public String readInputAsString(String[] lines) {
        FrameData[] frames = new FrameData[10];
        String player = "";
        String mark = "";
        int counter = 0;
        for (int i = 0; i < lines.length; i++) {
            Map<String, Results> results = new HashMap<>();
            // Get player name
            player = StringUtils.substringBefore(lines[i], "\t");
            FrameData frameData = new FrameData(results, counter, mark);
            // Get first result
            String result1 = StringUtils.substringAfter(lines[i],"\t");
            String result2;
            // When a strike happens
            if (result1.equals("10")) {
                frameData.setMark("X");
                results.put(player, new Results(new String[]{result1, ""}));
            } else {
                // Add one to skip next line on file
                i = i+1;
                result2 = StringUtils.substringAfter(lines[i+1],"\t");
                // When a spare happens
                if ((DataValidation.getInteger(result1) + DataValidation.getInteger(result2) == 10))
                    frameData.setMark("/");
                // When a normal turn happens
                else
                    frameData.setMark("#");
                results.put(player, new Results(new String[]{result1, result2}));
            }
            // Add frame to array
            frames[counter] = frameData;
            counter++;
        }

        return "";
    }
}
