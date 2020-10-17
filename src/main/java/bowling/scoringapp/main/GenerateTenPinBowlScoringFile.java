package bowling.scoringapp.main;

import bowling.scoringapp.transform.input.api.ITransformInput;
import bowling.scoringapp.transform.input.impl.TransformTenPinBowlFile;
import bowling.scoringapp.validate.input.api.ValidateInputFile;
import bowling.scoringapp.validate.input.impl.ValidateInputTenPinBowlFile;
import bowling.utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class GenerateTenPinBowlScoringFile {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String fileName = in.next();
        File file = new File (fileName);
        String fileContent = readFromInputStream(file).toString();
        String[] lines = fileContent.split(Constants.FILE_DELIMITER);

        ValidateInputFile validate = new ValidateInputTenPinBowlFile();

        ITransformInput transform = new TransformTenPinBowlFile();
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
