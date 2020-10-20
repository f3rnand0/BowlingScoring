package bowling.scoringapp.main;

import bowling.scoringapp.dtos.FrameData;
import bowling.scoringapp.generate.score.api.IGenerateScoring;
import bowling.scoringapp.generate.score.impl.GenerateTenPinBowlScoring;
import bowling.scoringapp.produce.score.file.api.IProduceScoringFile;
import bowling.scoringapp.produce.score.file.impl.ProduceTenPinBowlingScoringFile;
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
        File file = new File(fileName);

        GenerateTenPinBowlScoringFile generateScoringFile = new GenerateTenPinBowlScoringFile();
        // Read file and store data into lines array
        String fileContent = generateScoringFile.readFromInputStream(file);
        String[] lines = fileContent.split(Constants.FILE_DELIMITER);
        // Validate contents of file
        generateScoringFile.validateInputFile(lines);
        // Transform input into objects and generate scores
        FrameData[][] allFrames = generateScoringFile.produceScoring(lines);
        // Generate scoring file
        String scoringFilePath = generateScoringFile.createScoringFile(allFrames, file.getAbsolutePath());
        // Confirm process was successful
        System.out.println("Successfuly created scoring file: " + scoringFilePath);
    }

    public String readFromInputStream(File file) {
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

    public void validateInputFile(String[] lines) {
        ValidateInputFile validate = new ValidateInputTenPinBowlFile();
        // Validate if there's empty lines in file
        validate.validateEmptyLines(lines);
        // Validate throws count by player
        validate.validateTurnsCountPerPlayer(lines);
        // Validate results by player
        validate.validatePlayerResults(lines);
    }

    public FrameData[][] produceScoring(String[] lines) {
        ITransformInput transform = new TransformTenPinBowlFile();
        FrameData[][] allFrames = transform.transformInputByFrameByPlayer(lines);
        IGenerateScoring produceScoring = new GenerateTenPinBowlScoring();
        return produceScoring.calculateScores(allFrames);
    }

    public String createScoringFile(FrameData[][] allFrames, String inputFilePath) {
        IProduceScoringFile produceScoringFile = new ProduceTenPinBowlingScoringFile();
        return produceScoringFile.produceScoringFile(allFrames, inputFilePath);
    }
}
