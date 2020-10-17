package bowling.scoringapp.dtos;

import lombok.*;

@RequiredArgsConstructor
@ToString
public class PlayerResult {
    @Getter
    @Setter
    @NonNull
    String playerName;

    @Getter
    @Setter
    @NonNull
    String[] pinfalls;

//    public PlayerResult(@NonNull String playerName, @NonNull String[] pinfalls) {
//        this.playerName = playerName;
//        this.pinfalls = pinfalls;
//    }
}
