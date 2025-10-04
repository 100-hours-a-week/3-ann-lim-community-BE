package com.kakaotechbootcamp.community.dto.post.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountInfo {

    private Long likeCount;
    private Long commentCount;
    private Long viewCount;

    public static CountInfo of(Long likeCount, Long commentCount, Long viewCount) {
        return CountInfo.builder()
                .likeCount(likeCount)
                .commentCount(commentCount)
                .viewCount(viewCount)
                .build();
    }
}
