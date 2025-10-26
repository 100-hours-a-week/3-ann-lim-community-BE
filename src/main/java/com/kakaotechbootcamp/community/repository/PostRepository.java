package com.kakaotechbootcamp.community.repository;

import com.kakaotechbootcamp.community.dto.post.response.PostSummaryResponseDto;
import com.kakaotechbootcamp.community.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndDeletedAtIsNull(Long postId);
    List<Post> findAllByUserIdAndDeletedAtIsNull(Long userId);

    @Query("""
        SELECT new com.kakaotechbootcamp.community.dto.post.response.PostSummaryResponseDto(
            p.id, p.title, p.createdAt, p.updatedAt,
            u.id, u.nickname, u.profileImage,
            pc.likeCount, pc.commentCount, pc.viewCount
        )
        FROM Post p
        JOIN p.user u
        JOIN p.postCount pc
        WHERE p.deletedAt IS NULL
            AND (:lastPostCreatedAt IS NULL
                OR p.createdAt < :lastPostCreatedAt
                OR (p.createdAt = :lastPostCreatedAt AND p.id < :lastPostId))
        ORDER BY p.createdAt DESC, p.id DESC
    """)
    List<PostSummaryResponseDto> findPostsByCursor(
            @Param("lastPostCreatedAt") LocalDateTime lastPostCreatedAt,
            @Param("lastPostId") Long lastPostId,
            Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("""
        UPDATE Post p
        SET
            p.title = COALESCE(:title, p.title),
            p.content = COALESCE(:content, p.content)
        WHERE p.id = :postId
    """)
    int updatePost(
            @Param("postId") Long postId,
            @Param("title") String title,
            @Param("content") String content
    );

}
