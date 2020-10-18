package bowling.scoringapp.dtos;

import lombok.*;

import java.util.Map;

@RequiredArgsConstructor
@ToString
public class FrameData {
    @Getter
    @Setter
    @NonNull
    Map<String, Results> results;

    @Getter
    @Setter
    @NonNull
    Integer frame;

    @Getter
    @Setter
    @NonNull
    String mark;
}
