package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.dto.post.response.PostSummaryResponseDto;
import com.kakaotechbootcamp.community.dto.post.response.PostsResponseDto;
import com.kakaotechbootcamp.community.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostsResponseDto getPosts(LocalDateTime lastPostCreatedAt, Long lastPostId) {
        List<PostSummaryResponseDto> posts = postRepository.findPostsByCursor(lastPostCreatedAt, lastPostId, PageRequest.of(0, 10));

        PostSummaryResponseDto last;
        LocalDateTime setLastPostCreatedAt;
        Long setLastPostId;
        if(!posts.isEmpty()) {
            last = posts.get(posts.size() - 1);
            setLastPostCreatedAt = last.getCreatedAt();
            setLastPostId = last.getPostId();

            return PostsResponseDto.of(posts,setLastPostCreatedAt, setLastPostId);
        }
        else {
            return PostsResponseDto.of(null, null, null);
        }
    }
}
