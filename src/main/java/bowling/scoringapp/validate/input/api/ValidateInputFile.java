package bowling.scoringapp.validate.input.api;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ValidateInputFile {
    @Getter
    protected Map<String, List<Integer>> playersResults = new HashMap<>();
    @Getter
    protected Map<String, Integer> playersTurns = new HashMap<>();
    @Getter
    protected Map<String, List<Integer>> playersResultsByFrame = new HashMap<>();

    public boolean validateInput(String[] lines){
        return false;
    }

    public Boolean validateTurnsCountPerPlayer(String[] lines) {
        return false;
    }

    public Boolean validatePlayerResults(String[] lines) {
        return false;
    }

    public Boolean validateScore(String input) {
        return false;
    }

    public Boolean validateEmptyLines(String[] lines) {
        return false;
    }
}
