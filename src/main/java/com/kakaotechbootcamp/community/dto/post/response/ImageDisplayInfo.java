package com.kakaotechbootcamp.community.dto.post.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageDisplayInfo {

    private Long imageId;
    private String imageUrl;
    private int orderNum;

    public static ImageDisplayInfo of(Long imageId, String imageUrl, int orderNum) {
        return ImageDisplayInfo.builder()
                .imageId(imageId)
                .imageUrl(imageUrl)
                .orderNum(orderNum)
                .build();
    }
}
