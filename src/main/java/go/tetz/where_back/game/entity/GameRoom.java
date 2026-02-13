package go.tetz.where_back.game.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "game_rooms")
public class GameRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long hostUserId;

    @Column(nullable = false)
    private Integer maxPlayers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "gameRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<GameParticipant> participants = new ArrayList<>();

    @Builder
    public GameRoom(String name, Long hostUserId, Integer maxPlayers) {
        this.name = name;
        this.hostUserId = hostUserId;
        this.maxPlayers = maxPlayers != null ? maxPlayers : 8;
        this.status = GameStatus.WAITING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void startGame() {
        if (this.status != GameStatus.WAITING) {
            throw new IllegalStateException("대기 중인 방만 게임을 시작할 수 있습니다.");
        }
        this.status = GameStatus.PLAYING;
    }

    public void endGame() {
        this.status = GameStatus.ENDED;
    }

    public boolean isFull() {
        return participants.size() >= maxPlayers;
    }

    public boolean canJoin() {
        return status == GameStatus.WAITING && !isFull();
    }

    public void addParticipant(GameParticipant participant) {
        this.participants.add(participant);
    }

    public void removeParticipant(GameParticipant participant) {
        this.participants.remove(participant);
    }

    public enum GameStatus {
        WAITING,
        PLAYING,
        ENDED
    }
}
