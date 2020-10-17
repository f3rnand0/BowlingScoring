package bowling.scoringapp.main;

import bowling.scoringapp.dtos.ScoreFrame;
import bowling.scoringapp.read.input.api.IReadInput;
import bowling.scoringapp.read.input.impl.ReadTenPinBowlFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GenerateTenPinBowlScoringFile {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String fileName = in.next();
        File file = new File (fileName);
        String fileContent = readFromInputStream(file).toString();
        IReadInput input = new ReadTenPinBowlFile();
        // Read and validate input. Return a List<PlayerScoreFrames>
        // String scores = input.readInputAsString(fileContent);
        // Generate scoring and add to List<PlayerScoreFrames>
        // Generate scoring file
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
