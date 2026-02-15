package go.tetz.where_back.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerLocationDto {

    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private Double latitude;
    private Double longitude;
    private Long timestamp;
}
