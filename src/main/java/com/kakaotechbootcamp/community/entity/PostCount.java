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

    @Column(name = "like_count")
    private Long likeCount = 0L;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    @Column(name = "comment_count")
    private Long commentCount = 0L;

    public PostCount(Post post) {
        this.post = post;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
