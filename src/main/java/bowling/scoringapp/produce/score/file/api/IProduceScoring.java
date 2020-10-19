package bowling.scoringapp.produce.score.file.api;

import bowling.scoringapp.dtos.FrameData;

public interface IProduceScoring {
    FrameData[][] calculateScores(FrameData[][] allResults);
}
