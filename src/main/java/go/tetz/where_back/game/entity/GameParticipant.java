package go.tetz.where_back.game.entity;

import go.tetz.where_back.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "game_participants", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"game_room_id", "user_id"})
})
public class GameParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_room_id", nullable = false)
    private GameRoom gameRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    private boolean isReady;

    @Builder
    public GameParticipant(GameRoom gameRoom, UserEntity user) {
        this.gameRoom = gameRoom;
        this.user = user;
        this.joinedAt = LocalDateTime.now();
        this.isReady = false;
    }

    public void setReady(boolean ready) {
        this.isReady = ready;
    }
}
