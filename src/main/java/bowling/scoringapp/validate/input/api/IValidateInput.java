package bowling.scoringapp.validate.input.api;

public interface IValidateInput {

    Boolean validateThrowsCount(String input);

    Boolean validateScore(String input);
}
