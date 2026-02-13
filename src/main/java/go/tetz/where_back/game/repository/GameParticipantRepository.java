package go.tetz.where_back.game.repository;

import go.tetz.where_back.game.entity.GameParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameParticipantRepository extends JpaRepository<GameParticipant, Long> {

    List<GameParticipant> findByGameRoomId(Long gameRoomId);

    Optional<GameParticipant> findByGameRoomIdAndUserId(Long gameRoomId, Long userId);

    boolean existsByGameRoomIdAndUserId(Long gameRoomId, Long userId);
}
