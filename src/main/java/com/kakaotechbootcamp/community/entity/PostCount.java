package com.kakaotechbootcamp.community.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCount {

    @Id
    @Column(name = "post_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "post_id", unique = true)
    private Post post;

    @Column(name = "like_count", insertable = false, updatable = false)
    private Long likeCount;

    @Column(name = "view_count", insertable = false, updatable = false)
    private Long viewCount;

    @Column(name = "comment_count", insertable = false, updatable = false)
    private Long commentCount;

    public PostCount(Post post) {
        this.post = post;
    }
}
