package bowling.scoringapp.generate.score;

import bowling.scoringapp.dtos.FrameData;
import bowling.scoringapp.generate.score.api.IGenerateScoring;
import bowling.scoringapp.generate.score.impl.GenerateTenPinBowlScoring;
import bowling.scoringapp.transform.input.api.ITransformInput;
import bowling.scoringapp.transform.input.impl.TransformTenPinBowlFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GenerateTenPinBowlScoringTest {
    private static final int expectedScore = 54;
    private static FrameData[] allResults;

    public GenerateTenPinBowlScoringTest() {
    }

    @Before
    public void setup() {
    }

    @Test
    public void calculateScores1() {
        String[] contents = {"Jeff\t10", "John\t3", "John\t7", "Jeff\t7", "Jeff\t3", "John\t6", "John\t3", "Jeff\t9", "Jeff\t0",
                "John\t10", "Jeff\t10", "John\t8", "John\t1", "Jeff\t0", "Jeff\t8", "John\t10", "Jeff\t8", "Jeff\t2",
                "John\t10", "Jeff\tF", "Jeff\t6", "John\t9", "John\t0", "Jeff\t10", "John\t7", "John\t3", "Jeff\t10",
                "John\t4", "John\t4", "Jeff\t10", "Jeff\t8", "Jeff\t1", "John\t10", "John\t9", "John\t0"};
        ITransformInput transformInput = new TransformTenPinBowlFile();
        FrameData[][] allFrames = transformInput.transformInputByFrameByPlayer(contents);
        IGenerateScoring produceScoring = new GenerateTenPinBowlScoring();
        allFrames = produceScoring.calculateScores(allFrames);

        Assert.assertEquals(167, allFrames[0][9].getResults().getScore().intValue());
        Assert.assertEquals(151, allFrames[1][9].getResults().getScore().intValue());

//        for (int i = 0; i < allFrames.length; i++) {
//            for (int j = 0; j < allFrames[i].length; j++) {
//                System.out.println("FRAME - frame: " + allFrames[i][j].getFrame());
//                System.out.print("FRAME - results: ");
//                Results results = allFrames[i][j].getResults();
//                System.out.print(allFrames[i][j].getPlayer() + " result: " +
//                        allFrames[i][j].getResults().getPinFalls() + " score: " +
//                        allFrames[i][j].getResults().getScore() + " mark:" +
//                        allFrames[i][j].getResults().getMark());
//                System.out.println("");
//            }
//        }
    }

    @Test
    public void calculateScores2() {
        String[] contents = {"Jeff\t10", "John\t3", "John\t7", "Jeff\t7", "Jeff\t3", "John\t6", "John\t3", "Jeff\t9", "Jeff\t0",
                "John\t10", "Jeff\t10", "John\t8", "John\t1", "Jeff\t0", "Jeff\t8", "John\t10", "Jeff\t8", "Jeff\t2",
                "John\t10", "Jeff\tF", "Jeff\t6", "John\t9", "John\t0", "Jeff\t10", "John\t7", "John\t3", "Jeff\t10",
                "John\t4", "John\t6", "Jeff\t8", "Jeff\t2", "Jeff\t1", "John\t10", "John\t9", "John\t0"};
        ITransformInput transformInput = new TransformTenPinBowlFile();
        FrameData[][] allFrames = transformInput.transformInputByFrameByPlayer(contents);
        IGenerateScoring produceScoring = new GenerateTenPinBowlScoring();
        allFrames = produceScoring.calculateScores(allFrames);

        Assert.assertEquals(149, allFrames[0][9].getResults().getScore().intValue());
        Assert.assertEquals(163, allFrames[1][9].getResults().getScore().intValue());
    }

}