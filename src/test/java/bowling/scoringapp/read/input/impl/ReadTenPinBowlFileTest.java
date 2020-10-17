package bowling.scoringapp.read.input.impl;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ReadTenPinBowlFileTest {
    private static String contents;

    @Before
    public void setUp() throws Exception {
        contents = "Jeff\t10\nJohn\t3\nJohn\t7\nJeff\t7\nJeff\t3\nJohn\t6\nJohn\t3\nJeff\t9\nJeff\t0\nJohn\t10";
    }

    @Test
    public void readInputAsString() {
        String[] lines = contents.split("\n");
        System.out.println(Arrays.toString(lines));
    }
}