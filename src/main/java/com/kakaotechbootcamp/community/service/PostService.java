package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.cache.ViewCountCache;
import com.kakaotechbootcamp.community.dto.post.request.CreatePostRequestDto;
import com.kakaotechbootcamp.community.dto.post.request.ImageMetaInfo;
import com.kakaotechbootcamp.community.dto.post.request.UpdatePostRequestDto;
import com.kakaotechbootcamp.community.dto.post.response.*;
import com.kakaotechbootcamp.community.dto.postlike.response.CreateLikeResponseDto;
import com.kakaotechbootcamp.community.entity.*;
import com.kakaotechbootcamp.community.exception.CustomException;
import com.kakaotechbootcamp.community.exception.ErrorCode;
import com.kakaotechbootcamp.community.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostCountRepository postCountRepository;
    private final CommentRepository commentRepository;
    private final ViewCountCache viewCountCache;

    @Transactional(readOnly = true)
    public PostsResponseDto getPosts(LocalDateTime lastPostCreatedAt, Long lastPostId) {

        List<PostSummaryResponseDto> posts = postRepository.findPostsByCursor(lastPostCreatedAt, lastPostId, PageRequest.of(0, 10));

        PostSummaryResponseDto last;
        LocalDateTime setLastPostCreatedAt;
        Long setLastPostId;
        if (!posts.isEmpty()) {
            last = posts.get(posts.size() - 1);
            setLastPostCreatedAt = last.getCreatedAt();
            setLastPostId = last.getPostId();

            return PostsResponseDto.of(posts,setLastPostCreatedAt, setLastPostId);
        }
        else {
            return PostsResponseDto.of(null, null, null);
        }
    }

    public CreatePostResponseDto addPost(CreatePostRequestDto createPostRequest) {

        //Todo: JWT 구현 후 수정
        User user = userRepository.findByIdAndDeletedAtIsNull(1L)
                .orElseThrow();

        Post post = new Post(user, createPostRequest.getTitle(), createPostRequest.getContent());
        postRepository.save(post);

        List<ImageMetaInfo> images = createPostRequest.getPostImages();
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                Image image = new Image(post, images.get(i).getImageUrl(), images.get(i).getImageName(), images.get(i).getExtension(), i+1);
                imageRepository.save(image);
            }
        }

        PostCount postCount = new PostCount(post);
        postCountRepository.save(postCount);

        return new CreatePostResponseDto(post.getId());
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long postId) {

        //Todo: JWT 구현 후 수정
        User user = userRepository.findByIdAndDeletedAtIsNull(1L)
                .orElseThrow();

        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        List<Image> images = imageRepository.findAllByPostIdAndDeletedAtIsNull(postId);

        List<ImageDisplayInfo> imageInfoList = images.stream().
                map(image -> ImageDisplayInfo.of(
                        image.getId(),
                        image.getImageUrl(),
                        image.getOrderNum()))
                .toList();

        PostCount postCount = postCountRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_COUNT_NOT_FOUND));

        boolean isLiked = postLikeRepository.existsByPostIdAndUserId(postId, user.getId());

        User author = post.getUser();

        viewCountCache.increment(postId);

        return PostResponseDto.of(
                post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt(),
                imageInfoList,
                postCount.getLikeCount(), postCount.getViewCount(), postCount.getCommentCount(),
                isLiked,
                author.getId(), author.getNickname(), author.getProfileImage());
    }

    @Transactional(readOnly = true)
    public EditPostResponseDto getPostForEdit(Long postId) {

        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        List<Image> images = imageRepository.findAllByPostIdAndDeletedAtIsNull(postId);

        List<ImageFullInfo> imageInfoList = images.stream().
                map(image -> ImageFullInfo.of(
                        image.getId(),
                        image.getImageUrl(),
                        image.getImageName(),
                        image.getExtension(),
                        image.getOrderNum()))
                .toList();

        return EditPostResponseDto.of(post.getId(), post.getTitle(), post.getContent(), imageInfoList);
    }

    public UpdatePostResponseDto updatePost(Long postId, UpdatePostRequestDto updatePostRequest) {

        if (updatePostRequest == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST_BODY);
        }

        if (updatePostRequest.isAllFieldsNull()) {
            throw new CustomException(ErrorCode.NO_UPDATE_CONTENT);
        }

        if (updatePostRequest.getTitle() != null) {
            if (updatePostRequest.isTitleEmpty()) {
                throw new CustomException(ErrorCode.INVALID_TITLE);
            }
        }

        if (updatePostRequest.getContent() != null) {
            if (updatePostRequest.isContentEmpty()) {
                throw new CustomException(ErrorCode.INVALID_CONTENT);
            }
        }

        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        post.update(updatePostRequest.getTitle(), updatePostRequest.getContent());

        List<Image> existingImages = imageRepository.findAllByPostIdAndDeletedAtIsNull(postId);

        List<ImageMetaInfo> newImages = updatePostRequest.getPostImages();

        if (newImages == null || (newImages != null && newImages.isEmpty())) {
            for (Image image : existingImages) {
                image.delete();
            }
        }
        else {
            Map<String, Image> existingMap = existingImages.stream()
                    .collect(Collectors.toMap(Image::getImageUrl, image -> image));

            for (int i = 0; i < newImages.size(); i++) {
                String url = newImages.get(i).getImageUrl();
                int newOrder = i + 1;

                if (existingMap.containsKey(url)) {
                    Image existing = existingMap.get(url);
                    if (existing.getOrderNum() != newOrder) {
                        existing.updateOrder(newOrder);
                    }
                    existingMap.remove(url);
                }
                else {
                    Image newImage = new Image(post, url, newImages.get(i).getImageName(), newImages.get(i).getExtension(), newOrder);
                    imageRepository.save(newImage);
                }
            }

            for (Image toDelete : existingMap.values()) {
                toDelete.delete();
            }
        }

        return new UpdatePostResponseDto(post.getId());
    }

    public void deletePost(Long postId) {

        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        post.setPostCountNull();
        post.delete();

        List<Image> images = imageRepository.findAllByPostIdAndDeletedAtIsNull(postId);
        images.forEach(Image::delete);

        List<Comment> comments = commentRepository.findAllByPostIdAndDeletedAtIsNull(postId);
        comments.forEach(Comment::delete);

        postLikeRepository.deleteAllByPostId(postId);

        postCountRepository.deleteById(postId);
    }

    public CreateLikeResponseDto addLike(Long postId) {

        Post post = postRepository.findByIdAndDeletedAtIsNull(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        //Todo: JWT 구현 후 수정
        User user = userRepository.findByIdAndDeletedAtIsNull(1L)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        boolean isLiked = postLikeRepository.existsByPostIdAndUserId(postId, user.getId());
        if (isLiked) {
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        }

        PostLike postLike = new PostLike(post, user);
        postLikeRepository.save(postLike);

        PostCount postCount = postCountRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_COUNT_NOT_FOUND));

        postCount.increaseLikeCount();

        return new CreateLikeResponseDto(postLike.getId());
    }

    public void deleteLike(Long postId) {

        //Todo: JWT 구현 후 수정
        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, 1L)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_LIKE_NOT_FOUND));

        postLikeRepository.delete(postLike);

        PostCount postCount = postCountRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_COUNT_NOT_FOUND));

        postCount.decreaseLikeCount();
    }
}