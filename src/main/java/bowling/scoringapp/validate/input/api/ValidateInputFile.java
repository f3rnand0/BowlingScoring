package bowling.scoringapp.validate.input.api;

public abstract class ValidateInputFile {

    public boolean validateInput(String[] lines){
        return false;
    }

//    public Boolean validateTurnsCountPerPlayer(String[] lines) {
//        return false;
//    }

    public Boolean validatePlayerResults(String[] lines) {
        return false;
    }

    public Boolean validateScore(String input) {
        return false;
    }

    public Boolean validateEmptyLines(String line) {
        return false;
    }
}
