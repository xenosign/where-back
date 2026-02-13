package go.tetz.where_back.game.service;

import go.tetz.where_back.game.dto.GameRoomCreateRequest;
import go.tetz.where_back.game.entity.GameParticipant;
import go.tetz.where_back.game.entity.GameRoom;
import go.tetz.where_back.game.repository.GameParticipantRepository;
import go.tetz.where_back.game.repository.GameRoomRepository;
import go.tetz.where_back.user.entity.UserEntity;
import go.tetz.where_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GameService {

    private final GameRoomRepository gameRoomRepository;
    private final GameParticipantRepository participantRepository;
    private final UserRepository userRepository;

    public GameRoom createRoom(GameRoomCreateRequest request, Long hostUserId) {
        UserEntity host = userRepository.findById(hostUserId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        GameRoom room = GameRoom.builder()
                .name(request.getName())
                .hostUserId(hostUserId)
                .maxPlayers(request.getMaxPlayers() != null ? request.getMaxPlayers() : 8)
                .build();

        room = gameRoomRepository.save(room);

        GameParticipant participant = GameParticipant.builder()
                .gameRoom(room)
                .user(host)
                .build();
        participantRepository.save(participant);
        room.addParticipant(participant);

        log.info("게임방 생성: roomId={}, hostId={}", room.getId(), hostUserId);
        return room;
    }

    @Transactional(readOnly = true)
    public GameRoom getRoom(Long roomId) {
        return gameRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임방입니다."));
    }

    public GameParticipant joinRoom(Long roomId, Long userId) {
        GameRoom room = getRoom(roomId);
        if (!room.canJoin()) {
            throw new IllegalStateException("참가할 수 없는 게임방입니다.");
        }

        if (participantRepository.existsByGameRoomIdAndUserId(roomId, userId)) {
            throw new IllegalStateException("이미 참가 중인 방입니다.");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        GameParticipant participant = GameParticipant.builder()
                .gameRoom(room)
                .user(user)
                .build();
        participant = participantRepository.save(participant);
        room.addParticipant(participant);

        log.info("게임방 참가: roomId={}, userId={}", roomId, userId);
        return participant;
    }

    public void leaveRoom(Long roomId, Long userId) {
        GameParticipant participant = participantRepository.findByGameRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("참가자가 아닙니다."));

        GameRoom room = participant.getGameRoom();
        participantRepository.delete(participant);
        room.removeParticipant(participant);

        log.info("게임방 퇴장: roomId={}, userId={}", roomId, userId);
    }

    public void setReady(Long roomId, Long userId, boolean ready) {
        GameParticipant participant = participantRepository.findByGameRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("참가자가 아닙니다."));
        participant.setReady(ready);
    }

    public void startGame(Long roomId, Long userId) {
        GameRoom room = getRoom(roomId);
        if (!room.getHostUserId().equals(userId)) {
            throw new IllegalStateException("방장만 게임을 시작할 수 있습니다.");
        }
        room.startGame();
        log.info("게임 시작: roomId={}", roomId);
    }

    @Transactional(readOnly = true)
    public List<GameRoom> getWaitingRooms() {
        return gameRoomRepository.findByStatusOrderByCreatedAtDesc(GameRoom.GameStatus.WAITING);
    }
}
