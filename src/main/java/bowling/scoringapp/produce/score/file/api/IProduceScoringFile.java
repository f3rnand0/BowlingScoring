package bowling.scoringapp.produce.score.file.api;

import bowling.scoringapp.dtos.FrameData;

public interface IProduceScoringFile {
    String produceScoringFile(FrameData[][] allFrames, String inputFileName);
}
