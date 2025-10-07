package com.kakaotechbootcamp.community.repository;

import com.kakaotechbootcamp.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndDeletedAtIsNull(Long userId);
    Optional<User> findByEmailAndDeletedAtIsNull(String email);
    boolean existsByEmailAndDeletedAtIsNull(String email);
    boolean existsByNicknameAndDeletedAtIsNull(String nickname);
}
