package go.tetz.where_back.game.controller;

import go.tetz.where_back.game.dto.GameRoomCreateRequest;
import go.tetz.where_back.game.dto.GameRoomResponse;
import go.tetz.where_back.game.entity.GameRoom;
import go.tetz.where_back.game.service.GameService;
import go.tetz.where_back.user.util.AuthenticationUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final AuthenticationUtil authenticationUtil;

    @PostMapping("/rooms")
    public ResponseEntity<GameRoomResponse> createRoom(@Valid @RequestBody GameRoomCreateRequest request) {
        Long userId = authenticationUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        GameRoom room = gameService.createRoom(request, userId);
        return ResponseEntity.ok(GameRoomResponse.from(room));
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<GameRoomResponse>> getWaitingRooms() {
        List<GameRoom> rooms = gameService.getWaitingRooms();
        return ResponseEntity.ok(rooms.stream()
                .map(GameRoomResponse::from)
                .collect(Collectors.toList()));
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<GameRoomResponse> getRoom(@PathVariable Long roomId) {
        GameRoom room = gameService.getRoom(roomId);
        return ResponseEntity.ok(GameRoomResponse.from(room));
    }

    @PostMapping("/rooms/{roomId}/join")
    public ResponseEntity<Map<String, String>> joinRoom(@PathVariable Long roomId) {
        Long userId = authenticationUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        gameService.joinRoom(roomId, userId);
        return ResponseEntity.ok(Map.of("message", "참가 완료"));
    }

    @PostMapping("/rooms/{roomId}/leave")
    public ResponseEntity<Map<String, String>> leaveRoom(@PathVariable Long roomId) {
        Long userId = authenticationUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        gameService.leaveRoom(roomId, userId);
        return ResponseEntity.ok(Map.of("message", "퇴장 완료"));
    }

    @PatchMapping("/rooms/{roomId}/ready")
    public ResponseEntity<Map<String, String>> setReady(
            @PathVariable Long roomId,
            @RequestParam boolean ready) {
        Long userId = authenticationUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        gameService.setReady(roomId, userId, ready);
        return ResponseEntity.ok(Map.of("ready", String.valueOf(ready)));
    }

    @PostMapping("/rooms/{roomId}/start")
    public ResponseEntity<Map<String, String>> startGame(@PathVariable Long roomId) {
        Long userId = authenticationUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        gameService.startGame(roomId, userId);
        return ResponseEntity.ok(Map.of("message", "게임 시작"));
    }
}
