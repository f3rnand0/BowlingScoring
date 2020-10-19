package bowling.scoringapp.dtos;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class BaseObject {
    @Getter
    @Setter
    @NonNull
    public Integer frame;
}
