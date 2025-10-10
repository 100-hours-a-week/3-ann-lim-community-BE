package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.entity.*;
import com.kakaotechbootcamp.community.exception.CustomException;
import com.kakaotechbootcamp.community.exception.ErrorCode;
import com.kakaotechbootcamp.community.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDataCleanupService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCountRepository postCountRepository;

    public void cleanUserData(Long userId) {

        List<Post> posts = postRepository.findAllByUserIdAndDeletedAtIsNull(userId);
        for (Post post : posts) {

            post.setPostCountNull();
            post.delete();

            List<Image> images = imageRepository.findAllByPostIdAndDeletedAtIsNull(post.getId());
            images.forEach(Image::delete);

            List<Comment> comments = commentRepository.findAllByPostIdAndDeletedAtIsNull(post.getId());
            comments.forEach(Comment::delete);

            postLikeRepository.deleteAllByPostId(post.getId());

            postCountRepository.deleteById(post.getId());
        }

        List<Comment> comments = commentRepository.findAllByUserIdAndDeletedAtIsNull(userId);
        comments.forEach(Comment::delete);

        List<Long> commentPostIds = commentRepository.findPostIdsByUserId(userId);
        for (Long postId : commentPostIds) {
            PostCount postcount = postCountRepository.findById(postId)
                    .orElseThrow(() -> new CustomException(ErrorCode.POST_COUNT_NOT_FOUND));

            postcount.decreaseCommentCount();
        }

        postLikeRepository.deleteAllByUserId(userId);

        List<Long> LikePostIds = postLikeRepository.findPostIdsByUserId(userId);
        for (Long postId: LikePostIds) {
            PostCount postCount = postCountRepository.findById(postId)
                    .orElseThrow(() -> new CustomException(ErrorCode.POST_COUNT_NOT_FOUND));

            postCount.decreaseLikeCount();
        }
    }
}
