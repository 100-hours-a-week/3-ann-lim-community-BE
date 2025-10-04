package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.post.request.CreatePostRequestDto;
import com.kakaotechbootcamp.community.dto.post.response.CreatePostResponseDto;
import com.kakaotechbootcamp.community.dto.post.response.PostSummaryResponseDto;
import com.kakaotechbootcamp.community.dto.post.response.PostsResponseDto;
import com.kakaotechbootcamp.community.entity.Image;
import com.kakaotechbootcamp.community.entity.Post;
import com.kakaotechbootcamp.community.entity.PostCount;
import com.kakaotechbootcamp.community.entity.User;
import com.kakaotechbootcamp.community.repository.ImageRepository;
import com.kakaotechbootcamp.community.repository.PostCountRepository;
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
public class PostService {

    //Todo: JWT 구현 후 삭제
    private final UserRepository userRepository;

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final PostCountRepository postCountRepository;

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

        //Todo: JWT 구현 후 삭제
        User user = userRepository.findById(1L)
                .orElseThrow();

        Post post = new Post(user, createPostRequest.getTitle(), createPostRequest.getContent());
        postRepository.save(post);

        List<String> images = createPostRequest.getPostImages();
        if (images != null && !images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                Image image = new Image(post, images.get(i), i+1);
                imageRepository.save(image);
            }
        }

        PostCount postCount = new PostCount(post);
        postCountRepository.save(postCount);

        return new CreatePostResponseDto(post.getId());
    }
}
