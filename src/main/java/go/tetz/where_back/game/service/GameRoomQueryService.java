package go.tetz.where_back.game.service;

import go.tetz.where_back.game.dto.PlayerLocationDto;
import go.tetz.where_back.game.repository.GameParticipantRepository;
import go.tetz.where_back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameRoomQueryService {

    private final UserRepository userRepository;
    private final GameParticipantRepository participantRepository;

    public PlayerLocationDto toPlayerLocationDto(Long userId, Double lat, Double lng, Long timestamp) {
        return userRepository.findById(userId)
                .map(user -> PlayerLocationDto.builder()
                        .userId(user.getId())
                        .nickname(user.getNickname())
                        .profileImageUrl(user.getProfileImageUrl())
                        .latitude(lat)
                        .longitude(lng)
                        .timestamp(timestamp != null ? timestamp : System.currentTimeMillis())
                        .build())
                .orElse(null);
    }

    public boolean isParticipant(Long roomId, Long userId) {
        return participantRepository.existsByGameRoomIdAndUserId(roomId, userId);
    }
}
