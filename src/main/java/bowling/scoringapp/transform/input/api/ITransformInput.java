package bowling.scoringapp.transform.input.api;

import bowling.scoringapp.dtos.BaseObject;
import bowling.scoringapp.dtos.FrameData;

public interface ITransformInput {
    FrameData[][] transformInputByFrameByPlayer(String[] lines);
}
