package bowling.scoringapp.main;

import bowling.scoringapp.dtos.FrameData;
import bowling.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.util.Arrays;

@Category(IntegrationTest.class)
public class GenerateScoringFileIntegrationTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void produceScoringFile() {
        File file = new File("src/test/resources/sample1.txt");
        String fileContent = new GenerateTenPinBowlScoringFileTest().readFromInputStream(file);
        String[] contents = fileContent.split(Constants.FILE_DELIMITER);

        GenerateTenPinBowlScoringFile generateScoringFile = new GenerateTenPinBowlScoringFile();
        generateScoringFile.validateInputFile(contents);
        FrameData[][] allFrames = generateScoringFile.produceScoring(contents);
        Assert.assertEquals(Arrays.asList("10", ""), allFrames[0][8].getResults().getPinFalls());
        Assert.assertEquals(Arrays.asList("4", "4"), allFrames[1][8].getResults().getPinFalls());
        Assert.assertEquals(167, allFrames[0][9].getResults().getScore().intValue());
        Assert.assertEquals(151, allFrames[1][9].getResults().getScore().intValue());
    }
}
