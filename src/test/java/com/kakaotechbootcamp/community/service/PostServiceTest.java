package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.post.request.CreatePostRequestDto;
import com.kakaotechbootcamp.community.dto.post.request.ImageMetaInfo;
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
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private PostCountRepository postCountRepository;

    @Mock
    private HttpServletRequest request;

    @Test
    void getPosts_whenPostsExist() {

        // given
        LocalDateTime createdAt = LocalDateTime.now();

        PostSummaryResponseDto post1 = new PostSummaryResponseDto(1L, "title1", createdAt, createdAt, 1L, "user1", null, 3L, 0L, 4L);
        PostSummaryResponseDto post2 = new PostSummaryResponseDto(2L, "title2", createdAt, createdAt, 2L, "user2", null, 2L, 0L, 4L);

        List<PostSummaryResponseDto> mockPosts = List.of(post1, post2);

        Mockito.when(postRepository.findPostsByCursor(any(), any(), any())).thenReturn(mockPosts);

        // when
        PostsResponseDto response = postService.getPosts(null, null);

        // then
        assertThat(response.getPosts().size()).isEqualTo(2);
        assertThat(response.getLastPostId()).isEqualTo(post2.getPostId());
        assertThat(response.getLastPostCreatedAt()).isEqualTo(post2.getCreatedAt());
    }

//    @Test
//    void getPosts_whenNoPost() {
//
//        // given
//        LocalDateTime createdAt = LocalDateTime.now();
//
//        PostSummaryResponseDto post1 = new PostSummaryResponseDto(1L, "title1", createdAt, createdAt, 1L, "user1", null, 3L, 0L, 4L);
//        PostSummaryResponseDto post2 = new PostSummaryResponseDto(2L, "title2", createdAt, createdAt, 2L, "user2", null, 2L, 0L, 4L);
//
//        List<PostSummaryResponseDto> mockPosts = List.of(post1, post2);
//
//        Mockito.when(postRepository.findPostsByCursor(any(), any(), any())).thenReturn(mockPosts);
//
//        // when
//        PostsResponseDto response = postService.getPosts(post2.getCreatedAt(), post2.getPostId());
//
//        // then
//        assertThat(response.getPosts()).isNull();
//        assertThat(response.getLastPostId()).isNull();
//        assertThat(response.getLastPostCreatedAt()).isNull();
//    }
//
//    @Test
//    void addPost_success() {
//
//        // given
//        Long userId = 1L;
//        Mockito.when(request.getAttribute("userId")).thenReturn(userId);
//
//        User user = new User("test@gmail.com", "Test1234@", "test", null);
//        Mockito.when(userRepository.findByIdAndDeletedAtIsNull(userId))
//                .thenReturn(Optional.of(user));
//
//        Post post = new Post(user, "title", "content");
//        ReflectionTestUtils.setField(post, "id", 1L);
//        Mockito.when(postRepository.save(any())).thenReturn(post);
//
//        List<ImageMetaInfo> images = new ArrayList<>();
//        images.add(ImageMetaInfo.of("url1", "img1", "jpg"));
//        images.add(ImageMetaInfo.of("url2", "img2", "png"));
//
//        CreatePostRequestDto dto = new CreatePostRequestDto();
//        ReflectionTestUtils.setField(dto, "title", "title");
//        ReflectionTestUtils.setField(dto, "content", "content");
//        ReflectionTestUtils.setField(dto, "postImages", images);
//
//        // when
//        CreatePostResponseDto response = postService.addPost(request, dto);
//
//        // then
//        assertThat(response.getPostId()).isEqualTo(1L);
//
//        Mockito.verify(imageRepository, Mockito.times(2)).save(any(Image.class));
//        Mockito.verify(postCountRepository, Mockito.times(1)).save(any(PostCount.class));
//    }
}