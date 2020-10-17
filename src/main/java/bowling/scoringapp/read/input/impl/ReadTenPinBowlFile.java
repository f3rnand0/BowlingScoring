package bowling.scoringapp.read.input.impl;

import bowling.scoringapp.read.input.api.IReadInput;

public class ReadTenPinBowlFile implements IReadInput {

    @Override
    public String readInputAsString(String contents) {
        String[] lines = contents.split("");
        return "";
    }
}
