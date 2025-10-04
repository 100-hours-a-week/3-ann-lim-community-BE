package com.kakaotechbootcamp.community.dto.post.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageInfo {

    private Long imageId;
    private String imageUrl;
    private int orderNum;

    public static ImageInfo of(Long imageId, String imageUrl, int orderNum) {
        return ImageInfo.builder()
                .imageId(imageId)
                .imageUrl(imageUrl)
                .orderNum(orderNum)
                .build();
    }
}
