package go.tetz.where_back.game.repository;

import go.tetz.where_back.game.entity.GameRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRoomRepository extends JpaRepository<GameRoom, Long> {

    List<GameRoom> findByStatusOrderByCreatedAtDesc(GameRoom.GameStatus status);

    List<GameRoom> findByHostUserIdOrderByCreatedAtDesc(Long hostUserId);
}
