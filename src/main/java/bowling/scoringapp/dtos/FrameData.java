package bowling.scoringapp.dtos;

import lombok.*;

import java.util.Map;

@ToString
public class FrameData extends BaseObject {
    @Getter
    @Setter
    @NonNull
    Map<String, Results> results;

    @Getter
    @Setter
    @NonNull
    String mark;

    public FrameData(int frame, Map<String, Results> results, String mark) {
        this.frame = frame;
        this.results = results;
        this.mark = mark;
    }
}
