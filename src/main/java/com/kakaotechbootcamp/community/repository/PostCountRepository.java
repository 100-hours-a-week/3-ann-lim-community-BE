package com.kakaotechbootcamp.community.repository;

import com.kakaotechbootcamp.community.entity.PostCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCountRepository extends JpaRepository<PostCount, Long> {

    @Modifying
    @Query("UPDATE PostCount pc SET pc.viewCount = pc.viewCount + :count WHERE pc.post.id = :postId")
    void increaseViewCount(@Param("postId") Long postId, @Param("count") int count);
}
