package go.tetz.where_back.game.dto;

import go.tetz.where_back.game.entity.GameParticipant;
import go.tetz.where_back.game.entity.GameRoom;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class GameRoomResponse {

    private Long id;
    private String name;
    private Long hostUserId;
    private Integer maxPlayers;
    private Integer currentPlayers;
    private GameRoom.GameStatus status;
    private LocalDateTime createdAt;
    private List<ParticipantInfo> participants;

    public static GameRoomResponse from(GameRoom room) {
        return GameRoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .hostUserId(room.getHostUserId())
                .maxPlayers(room.getMaxPlayers())
                .currentPlayers(room.getParticipants().size())
                .status(room.getStatus())
                .createdAt(room.getCreatedAt())
                .participants(room.getParticipants().stream()
                        .map(ParticipantInfo::from)
                        .collect(Collectors.toList()))
                .build();
    }

    @Getter
    @Builder
    public static class ParticipantInfo {
        private Long userId;
        private String nickname;
        private String profileImageUrl;
        private boolean isReady;

        public static ParticipantInfo from(GameParticipant p) {
            return ParticipantInfo.builder()
                    .userId(p.getUser().getId())
                    .nickname(p.getUser().getNickname())
                    .profileImageUrl(p.getUser().getProfileImageUrl())
                    .isReady(p.isReady())
                    .build();
        }
    }
}
