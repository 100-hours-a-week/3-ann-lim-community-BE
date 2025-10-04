package com.kakaotechbootcamp.community.repository;

import com.kakaotechbootcamp.community.dto.post.response.PostSummaryResponseDto;
import com.kakaotechbootcamp.community.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
        SELECT new com.kakaotechbootcamp.community.dto.post.response.PostSummaryResponseDto(
            p.id, p.title, p.createdAt, p.updatedAt,
            u.id, u.nickname, u.profileImage,
            pc.likeCount, pc.viewCount, pc.commentCount
        )
        FROM Post p
        JOIN p.user u
        JOIN p.postCount pc
        WHERE (:lastPostCreatedAt IS NULL)
           OR (p.createdAt < :lastPostCreatedAt)
           OR (p.createdAt = :lastPostCreatedAt AND p.id < :lastPostId)
        ORDER BY p.createdAt DESC, p.id DESC
        LIMIT 10
    """)
    List<PostSummaryResponseDto> findPostsByCursor(
            @Param("lastPostCreatedAt") LocalDateTime lastPostCreatedAt,
            @Param("lastPostId") Long lastPostId,
            Pageable pageable);
}
