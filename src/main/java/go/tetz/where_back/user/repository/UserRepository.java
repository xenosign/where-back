package go.tetz.where_back.user.repository;

import go.tetz.where_back.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByKakaoId(Long kakaoId);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByKakaoId(Long kakaoId);
}