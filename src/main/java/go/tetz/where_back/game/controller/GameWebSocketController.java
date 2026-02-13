package go.tetz.where_back.game.controller;

import go.tetz.where_back.game.dto.LocationUpdate;
import go.tetz.where_back.game.dto.PlayerLocationDto;
import go.tetz.where_back.game.service.GameRoomQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * WebSocket 실시간 메시지 핸들러
 * - /app/game/{roomId}/location → 위치 업데이트 수신 후 /topic/game/{roomId}/location 로 브로드캐스트
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class GameWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameRoomQueryService gameRoomQueryService;

    @MessageMapping("/game/{roomId}/location")
    public void handleLocationUpdate(
            @DestinationVariable Long roomId,
            @Payload LocationUpdate update) {
        if (update.getLatitude() == null || update.getLongitude() == null) {
            return;
        }

        PlayerLocationDto dto = gameRoomQueryService.toPlayerLocationDto(
                update.getUserId(),
                update.getLatitude(),
                update.getLongitude(),
                update.getTimestamp());

        if (dto != null) {
            String destination = "/topic/game/" + roomId + "/location";
            messagingTemplate.convertAndSend(destination, dto);
            log.debug("위치 브로드캐스트: roomId={}, userId={}", roomId, update.getUserId());
        }
    }
}
