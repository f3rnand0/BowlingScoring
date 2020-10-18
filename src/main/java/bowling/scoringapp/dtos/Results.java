package bowling.scoringapp.dtos;

import lombok.*;

@RequiredArgsConstructor
@ToString
public class Results {
//    @Getter
//    @Setter
//    @NonNull
//    String playerName;

    @Getter
    @Setter
    @NonNull
    String[] pinfalls;

    @Getter
    @Setter
    Integer score;
}
