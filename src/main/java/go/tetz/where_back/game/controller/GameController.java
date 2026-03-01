package go.tetz.where_back.game.controller;

import go.tetz.where_back.game.dto.GameRoomCreateRequest;
import go.tetz.where_back.game.dto.GameRoomResponse;
import go.tetz.where_back.game.entity.GameRoom;
import go.tetz.where_back.game.service.GameService;
import go.tetz.where_back.user.util.AuthenticationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "게임", description = "게임방 및 참가 API")
@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final AuthenticationUtil authenticationUtil;

    @Operation(summary = "방 생성")
    @ApiResponse(responseCode = "401", description = "인증 필요")
    @PostMapping("/rooms")
    public ResponseEntity<GameRoomResponse> createRoom(@Valid @RequestBody GameRoomCreateRequest request) {
        Long userId = authenticationUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        GameRoom room = gameService.createRoom(request, userId);
        return ResponseEntity.ok(GameRoomResponse.from(room));
    }

    @Operation(summary = "대기 방 목록")
    @GetMapping("/rooms")
    public ResponseEntity<List<GameRoomResponse>> getWaitingRooms() {
        List<GameRoom> rooms = gameService.getWaitingRooms();
        return ResponseEntity.ok(rooms.stream()
                .map(GameRoomResponse::from)
                .collect(Collectors.toList()));
    }

    @Operation(summary = "방 상세 조회")
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<GameRoomResponse> getRoom(
            @Parameter(description = "방 ID") @PathVariable Long roomId) {
        GameRoom room = gameService.getRoom(roomId);
        return ResponseEntity.ok(GameRoomResponse.from(room));
    }

    @Operation(summary = "방 참가")
    @ApiResponse(responseCode = "401", description = "인증 필요")
    @PostMapping("/rooms/{roomId}/join")
    public ResponseEntity<Map<String, String>> joinRoom(
            @Parameter(description = "방 ID") @PathVariable Long roomId) {
        Long userId = authenticationUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        gameService.joinRoom(roomId, userId);
        return ResponseEntity.ok(Map.of("message", "참가 완료"));
    }

    @Operation(summary = "방 퇴장")
    @ApiResponse(responseCode = "401", description = "인증 필요")
    @PostMapping("/rooms/{roomId}/leave")
    public ResponseEntity<Map<String, String>> leaveRoom(
            @Parameter(description = "방 ID") @PathVariable Long roomId) {
        Long userId = authenticationUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        gameService.leaveRoom(roomId, userId);
        return ResponseEntity.ok(Map.of("message", "퇴장 완료"));
    }

    @Operation(summary = "준비 상태 변경")
    @ApiResponse(responseCode = "401", description = "인증 필요")
    @PatchMapping("/rooms/{roomId}/ready")
    public ResponseEntity<Map<String, String>> setReady(
            @Parameter(description = "방 ID") @PathVariable Long roomId,
            @Parameter(description = "준비 여부") @RequestParam boolean ready) {
        Long userId = authenticationUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        gameService.setReady(roomId, userId, ready);
        return ResponseEntity.ok(Map.of("ready", String.valueOf(ready)));
    }

    @Operation(summary = "게임 시작", description = "방장만 호출 가능")
    @ApiResponse(responseCode = "401", description = "인증 필요")
    @PostMapping("/rooms/{roomId}/start")
    public ResponseEntity<Map<String, String>> startGame(
            @Parameter(description = "방 ID") @PathVariable Long roomId) {
        Long userId = authenticationUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        gameService.startGame(roomId, userId);
        return ResponseEntity.ok(Map.of("message", "게임 시작"));
    }
}
