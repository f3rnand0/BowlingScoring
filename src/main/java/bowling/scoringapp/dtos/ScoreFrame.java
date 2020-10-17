package bowling.scoringapp.dtos;

import lombok.*;

import java.util.Map;

@RequiredArgsConstructor
@ToString
public class ScoreFrame {
    @Getter
    @Setter
    @NonNull
    Map<String,String[]> playersResults;

    @Getter
    @Setter
    @NonNull
    Integer frame;

    @Getter
    @Setter
    Integer score;

//    public ScoreFrame(PlayerResult[] playersResults, Integer frame) {
//        this.playersResults = playersResults;
//        this.frame = frame;
//    }
}
