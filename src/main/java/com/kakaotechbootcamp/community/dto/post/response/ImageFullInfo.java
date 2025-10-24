package com.kakaotechbootcamp.community.dto.post.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageFullInfo {

    private Long imageId;
    private String imageUrl;
    private String imageName;
    private String extension;
    private int orderNum;

    public static ImageFullInfo of(Long imageId, String imageUrl, String imageName, String extension, int orderNum) {
        return ImageFullInfo.builder()
                .imageId(imageId)
                .imageUrl(imageUrl)
                .imageName(imageName)
                .extension(extension)
                .orderNum(orderNum)
                .build();
    }
}
