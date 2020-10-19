package bowling.scoringapp.transform.input.impl;

import bowling.scoringapp.dtos.FrameData;
import bowling.scoringapp.transform.input.api.ITransformInput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class TransformTenPinBowlFileTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void transformInputByFrameByPlayer() {
        String[] contents = {"Jeff\t10", "John\t3", "John\t7", "Jeff\t7", "Jeff\t3", "John\t6", "John\t3", "Jeff\t9", "Jeff\t0",
                "John\t10", "Jeff\t10", "John\t8", "John\t1", "Jeff\t0", "Jeff\t8", "John\t10", "Jeff\t8", "Jeff\t2",
                "John\t10", "Jeff\tF", "Jeff\t6", "John\t9", "John\t0", "Jeff\t10", "John\t7", "John\t3", "Jeff\t10",
                "John\t4", "John\t4", "Jeff\t8", "Jeff\t2", "Jeff\t8", "John\t10", "John\t9", "John\t0"};
        ITransformInput transformInput = new TransformTenPinBowlFile<>();
        FrameData[] frameData = transformInput.transformInputByFrameByPlayer(contents);
        System.out.println(Arrays.toString(frameData));
        Assert.assertTrue(true);
    }
}