package com.kakaotechbootcamp.community.repository;

import com.kakaotechbootcamp.community.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
    void deleteAllByPostId(Long postId);
    void deleteAllByUserId(Long userId);

    @Query("SELECT DISTINCT l.post.id FROM PostLike l WHERE l.user.id = :userId")
    List<Long> findPostIdsByUserId(@Param("userId") Long userId);
}
