package com.kakaotechbootcamp.community.repository;

import com.kakaotechbootcamp.community.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshTokenAndRevokedFalse(String refreshToken);
    void deleteAllByUserId(Long userId);
    void deleteByExpiresAtBefore(LocalDateTime now);
}