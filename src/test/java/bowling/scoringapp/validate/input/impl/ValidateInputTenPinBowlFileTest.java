package bowling.scoringapp.validate.input.impl;

import bowling.scoringapp.validate.input.api.ValidateInputFile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ValidateInputTenPinBowlFileTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void validateTurnsCountPerPlayer1() {
        String[] contents = {"Jeff\t10", "John\t3", "John\t7", "Jeff\t7", "Jeff\t3", "John\t6", "John\t3", "Jeff\t9", "Jeff\t0",
                "John\t10", "Jeff\t10", "John\t8", "John\t1", "Jeff\t0", "Jeff\t8", "John\t10", "Jeff\t8", "Jeff\t2",
                "John\t10", "Jeff\tF", "Jeff\t6", "John\t9", "John\t0", "Jeff\t10", "John\t7", "John\t3", "Jeff\t10",
                "John\t4", "John\t4", "Jeff\t8", "Jeff\t2", "Jeff\t8", "John\t10", "John\t9", "John\t0"};

        ValidateInputTenPinBowlFile validate = new ValidateInputTenPinBowlFile();
        Map<String, Integer> playersTurns = validate.validateTurnsCountPerPlayer(contents);
        for (Map.Entry<String, Integer> entry : playersTurns.entrySet()) {
            System.out.println("FGU: " + entry.getKey() + " : " + entry.getValue());
        }

        Assert.assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public void validateTurnsCountPerPlayer2() {
        String[] contents = {"Jeff\t10", "John\t3", "John\t7", "Jeff\t7", "Jeff\t3", "John\t6", "John\t3", "Jeff\t9", "Jeff\t0",
                "John\t10", "Jeff\t10", "John\t8", "John\t1", "Jeff\t0", "Jeff\t8", "John\t10", "Jeff\t8", "Jeff\t2",
                "John\t10", "Jeff\tF", "Jeff\t6", "John\t9", "John\t0", "Jeff\t10", "John\t7", "John\t3", "Jeff\t10",
                "John\t4", "John\t4", "Jeff\t8", "Jeff\t2", "Jeff\t8", "Jeff\t8", "John\t10", "John\t9", "John\t0", "John\t2"};

        ValidateInputTenPinBowlFile validate = new ValidateInputTenPinBowlFile();
        Map<String, Integer> playersTurns = validate.validateTurnsCountPerPlayer(contents);
        for (Map.Entry<String, Integer> entry : playersTurns.entrySet()) {
            System.out.println("FGU: " + entry.getKey() + " : " + entry.getValue());
        }

        Assert.assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public void validateTurnsCountPerPlayer3() {
        String[] contents = {"Jeff\t10", "John\t3", "John\t7", "Jeff\t7", "Jeff\t3", "John\t6", "John\t3", "Jeff\t9", "Jeff\t0",
                "John\t10", "Jeff\t10", "John\t8", "John\t1", "Jeff\t0", "Jeff\t8", "John\t10", "Jeff\t8", "Jeff\t2",
                "John\t10", "Jeff\tF", "Jeff\t6", "John\t9", "John\t0", "Jeff\t10", "John\t7", "John\t3", "Jeff\t10",
                "John\t4", "John\t4", "Jeff\t8", "Jeff\t2", "John\t10", "John\t0"};

        ValidateInputTenPinBowlFile validate = new ValidateInputTenPinBowlFile();
        Map<String, Integer> playersTurns = validate.validateTurnsCountPerPlayer(contents);
        for (Map.Entry<String, Integer> entry : playersTurns.entrySet()) {
            System.out.println("FGU: " + entry.getKey() + " : " + entry.getValue());
        }

        Assert.assertTrue(true);
    }
}