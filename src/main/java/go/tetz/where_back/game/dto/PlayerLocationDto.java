package go.tetz.where_back.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 다른 플레이어에게 브로드캐스트되는 위치 정보 (닉네임 등 포함)
 */
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
