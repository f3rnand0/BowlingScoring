package bowling.scoringapp.transform.input.api;

import bowling.scoringapp.dtos.BaseObject;

public interface ITransformInput {
    <E extends BaseObject> E[] transformInputByFrameByPlayer(String[] lines);
}
