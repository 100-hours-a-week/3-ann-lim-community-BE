package com.kakaotechbootcamp.community.repository;

import com.kakaotechbootcamp.community.dto.comment.response.CommentSummaryResponseDto;
import com.kakaotechbootcamp.community.dto.post.response.PostSummaryResponseDto;
import com.kakaotechbootcamp.community.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdAndDeletedAtIsNull(Long postId);
    List<Comment> findAllByUserIdAndDeletedAtIsNull(Long userId);
    Optional<Comment> findByIdAndPostIdAndDeletedAtIsNull(Long commentId, Long postId);

    @Query("SELECT DISTINCT c.post.id FROM Comment c WHERE c.user.id = :userId")
    List<Long> findPostIdsByUserId(@Param("userId") Long userId);


    @Query("""
    SELECT new com.kakaotechbootcamp.community.dto.comment.response.CommentSummaryResponseDto(
        c.id, c.content, c.createdAt, c.updatedAt,
        u.id, u.nickname, u.profileImage
    )
    FROM Comment c
    JOIN c.user u
    WHERE c.post.id = :postId
        AND c.deletedAt IS NULL
        AND (:lastCommentCreatedAt IS NULL
            OR c.createdAt > :lastCommentCreatedAt
            OR (c.createdAt = :lastCommentCreatedAt AND c.id > :lastCommentId))
    ORDER BY c.createdAt ASC, c.id ASC
""")
    List<CommentSummaryResponseDto> findCommentsByCursor(
            @Param("postId") Long postId,
            @Param("lastCommentCreatedAt") LocalDateTime lastCommentCreatedAt,
            @Param("lastCommentId") Long lastCommentId,
            Pageable pageable);
}
