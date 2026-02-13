package go.tetz.where_back.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * WebSocket으로 실시간 전송되는 사용자 위치 정보
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationUpdate {

    private Long userId;
    private Long roomId;
    private Double latitude;
    private Double longitude;
    private Double accuracy;  // 위치 정확도 (미터)
    private Long timestamp;   // 클라이언트 타임스탬프
}
