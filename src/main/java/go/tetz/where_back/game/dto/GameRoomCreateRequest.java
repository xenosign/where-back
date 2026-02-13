package go.tetz.where_back.game.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameRoomCreateRequest {

    @NotBlank(message = "방 이름을 입력해주세요.")
    @Size(max = 50)
    private String name;

    @Min(2)
    @Max(16)
    private Integer maxPlayers = 8;
}
