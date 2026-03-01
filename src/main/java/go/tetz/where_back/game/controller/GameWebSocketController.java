package go.tetz.where_back.game.controller;

import go.tetz.where_back.game.dto.LocationUpdate;
import go.tetz.where_back.game.dto.PlayerLocationDto;
import go.tetz.where_back.game.service.GameRoomQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Tag(name = "게임 WebSocket", description = "실시간 위치 브로드캐스트 (STOMP /app/game/{roomId}/location)")
@Controller
@RequiredArgsConstructor
@Slf4j
public class GameWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final GameRoomQueryService gameRoomQueryService;

    @MessageMapping("/game/{roomId}/location")
    public void handleLocationUpdate(
            @DestinationVariable Long roomId,
            @Payload LocationUpdate update,
            Principal principal) {
        if (principal == null) {
            log.warn("인증되지 않은 위치 업데이트 차단: roomId={}", roomId);
            return;
        }

        Long userId = Long.valueOf(principal.getName());
        if (update.getLatitude() == null || update.getLongitude() == null) {
            return;
        }

        if (!gameRoomQueryService.isParticipant(roomId, userId)) {
            log.warn("방 참가자가 아닌 사용자의 위치 업데이트 차단: roomId={}, userId={}", roomId, userId);
            return;
        }

        PlayerLocationDto dto = gameRoomQueryService.toPlayerLocationDto(
                userId,
                update.getLatitude(),
                update.getLongitude(),
                update.getTimestamp());

        if (dto != null) {
            String destination = "/topic/game/" + roomId + "/location";
            messagingTemplate.convertAndSend(destination, dto);
            log.debug("위치 브로드캐스트: roomId={}, userId={}", roomId, userId);
        }
    }
}
