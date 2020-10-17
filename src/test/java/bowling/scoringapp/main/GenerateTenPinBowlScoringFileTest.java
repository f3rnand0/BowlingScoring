package bowling.scoringapp.main;

import bowling.scoringapp.read.input.api.IReadInput;
import bowling.scoringapp.read.input.impl.ReadTenPinBowlFile;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class GenerateTenPinBowlScoringFileTest {
    private static File file;

    @Before
    public void setUp() throws Exception {
        file = new File("src/test/resources/sample1.txt");
    }

    @Test
    public void readFileContents() {
        String fileContent = readFromInputStream(file).toString();
        System.out.println(fileContent);
    }

    private static String readFromInputStream(File file) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
}