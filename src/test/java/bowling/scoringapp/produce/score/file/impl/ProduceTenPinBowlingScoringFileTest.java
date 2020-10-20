package bowling.scoringapp.produce.score.file.impl;

import bowling.scoringapp.dtos.FrameData;
import bowling.scoringapp.generate.score.api.IGenerateScoring;
import bowling.scoringapp.generate.score.impl.GenerateTenPinBowlScoring;
import bowling.scoringapp.produce.score.file.api.IProduceScoringFile;
import bowling.scoringapp.transform.input.api.ITransformInput;
import bowling.scoringapp.transform.input.impl.TransformTenPinBowlFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class ProduceTenPinBowlingScoringFileTest {

    @Before
    public void setUp() {
    }

    @Test
    public void produceScoringFile() {
        String[] contents = {"Jeff\t10", "John\t3", "John\t7", "Jeff\t7", "Jeff\t3", "John\t6", "John\t3", "Jeff\t9", "Jeff\t0",
                "John\t10", "Jeff\t10", "John\t8", "John\t1", "Jeff\t0", "Jeff\t8", "John\t10", "Jeff\t8", "Jeff\t2",
                "John\t10", "Jeff\tF", "Jeff\t6", "John\t9", "John\t0", "Jeff\t10", "John\t7", "John\t3", "Jeff\t10",
                "John\t4", "John\t4", "Jeff\t10", "Jeff\t8", "Jeff\t1", "John\t10", "John\t9", "John\t0"};
        ITransformInput transformInput = new TransformTenPinBowlFile();
        FrameData[][] allFrames = transformInput.transformInputByFrameByPlayer(contents);
        IGenerateScoring produceScoring = new GenerateTenPinBowlScoring();
        allFrames = produceScoring.calculateScores(allFrames);
        String filePath = "src/test/resources/sample1.txt";
        IProduceScoringFile produceFile = new ProduceTenPinBowlingScoringFile();
        File file = new File(produceFile.produceScoringFile(allFrames, filePath));
        Assert.assertTrue(file.exists() && !file.isDirectory());
    }
}