package go.tetz.where_back.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * WebSocket으로 브로드캐스트되는 게임 이벤트 (접속, 퇴장, 게임 시작 등)
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameEvent {

    public enum EventType {
        PLAYER_JOINED,
        PLAYER_LEFT,
        PLAYER_READY,
        GAME_STARTED,
        GAME_ENDED
    }

    private EventType type;
    private Long roomId;
    private Long userId;
    private String nickname;
    private Object payload;
}
