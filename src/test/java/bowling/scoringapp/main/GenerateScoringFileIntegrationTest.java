package bowling.scoringapp.main;

import bowling.scoringapp.dtos.FrameData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Arrays;

@Category(IntegrationTest.class)
public class GenerateScoringFileIntegrationTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void produceScoringFile() {
        String[] contents = {"Jeff\t10", "John\t3", "John\t7", "Jeff\t7", "Jeff\t3", "John\t6", "John\t3", "Jeff\t9", "Jeff\t0",
                "John\t10", "Jeff\t10", "John\t8", "John\t1", "Jeff\t0", "Jeff\t8", "John\t10", "Jeff\t8", "Jeff\t2",
                "John\t10", "Jeff\tF", "Jeff\t6", "John\t9", "John\t0", "Jeff\t10", "John\t7", "John\t3", "Jeff\t10",
                "John\t4", "John\t4", "Jeff\t10", "Jeff\t8", "Jeff\t1", "John\t10", "John\t9", "John\t0"};

        GenerateTenPinBowlScoringFile generateScoringFile = new GenerateTenPinBowlScoringFile();
        generateScoringFile.validateInputFile(contents);
        FrameData[][] allFrames = generateScoringFile.produceScoring(contents);
        Assert.assertEquals(Arrays.asList("10", ""), allFrames[0][8].getResults().getPinFalls());
        Assert.assertEquals(Arrays.asList("4", "4"), allFrames[1][8].getResults().getPinFalls());
        Assert.assertEquals(167,allFrames[0][9].getResults().getScore().intValue());
        Assert.assertEquals(151,allFrames[1][9].getResults().getScore().intValue());
    }
}
