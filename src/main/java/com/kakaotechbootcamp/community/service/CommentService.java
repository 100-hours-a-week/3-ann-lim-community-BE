package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.comment.request.CreateCommentRequestDto;
import com.kakaotechbootcamp.community.dto.comment.request.UpdateCommentRequestDto;
import com.kakaotechbootcamp.community.dto.comment.response.CommentSummaryResponseDto;
import com.kakaotechbootcamp.community.dto.comment.response.CommentsResponseDto;
import com.kakaotechbootcamp.community.dto.comment.response.CreateCommentResponseDto;
import com.kakaotechbootcamp.community.dto.comment.response.UpdateCommentResponseDto;
import com.kakaotechbootcamp.community.entity.Comment;
import com.kakaotechbootcamp.community.entity.Post;
import com.kakaotechbootcamp.community.entity.User;
import com.kakaotechbootcamp.community.exception.CustomException;
import com.kakaotechbootcamp.community.exception.ErrorCode;
import com.kakaotechbootcamp.community.repository.CommentRepository;
import com.kakaotechbootcamp.community.repository.PostRepository;
import com.kakaotechbootcamp.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public CommentsResponseDto getComments(Long postId, LocalDateTime lastPostCreatedAt, Long lastPostId) {

        List<CommentSummaryResponseDto> comments = commentRepository.findCommentsByCursor(postId, lastPostCreatedAt, lastPostId, PageRequest.of(0, 10));

        CommentSummaryResponseDto last;
        LocalDateTime setLastCommentCreatedAt;
        Long setLastCommentId;
        if (!comments.isEmpty()) {
            last = comments.get(comments.size() - 1);
            setLastCommentCreatedAt = last.getCreatedAt();
            setLastCommentId = last.getCommentId();

            return CommentsResponseDto.of(comments,setLastCommentCreatedAt, setLastCommentId);
        }
        else {
            return CommentsResponseDto.of(null, null, null);
        }
    }

    public CreateCommentResponseDto addComment(Long postId, CreateCommentRequestDto createCommentRequest) {

        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        //Todo: JWT 구현 후 수정
        User user = userRepository.findByIdAndDeletedAtIsNull(1L)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Comment comment = new Comment(post, user, createCommentRequest.getContent());
        commentRepository.save(comment);

        return new CreateCommentResponseDto(comment.getId());
    }

    public UpdateCommentResponseDto updateComment(Long postId, Long commentId, UpdateCommentRequestDto updateCommentRequest) {

        if (updateCommentRequest.isContentNull()) {
            throw new CustomException(ErrorCode.NO_UPDATE_CONTENT);
        }

        Comment comment  = commentRepository.findByIdAndPostIdAndDeletedAtIsNull(commentId, postId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        comment.update(updateCommentRequest.getContent());

        return new UpdateCommentResponseDto(comment.getId());
    }

    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findByIdAndPostIdAndDeletedAtIsNull(commentId, postId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.delete();

        return;
    }
}
