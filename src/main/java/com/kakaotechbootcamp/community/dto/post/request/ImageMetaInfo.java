package com.kakaotechbootcamp.community.dto.post.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageMetaInfo {

    private String imageUrl;
    private String imageName;
    private String extension;

    public static ImageMetaInfo of(String imageUrl, String imageName, String extension) {
        return ImageMetaInfo.builder()
                .imageUrl(imageUrl)
                .imageName(imageName)
                .extension(extension)
                .build();
    }
}