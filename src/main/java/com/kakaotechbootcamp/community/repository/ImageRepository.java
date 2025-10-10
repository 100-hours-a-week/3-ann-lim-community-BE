package com.kakaotechbootcamp.community.repository;

import com.kakaotechbootcamp.community.entity.Image;
import com.kakaotechbootcamp.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByPostIdAndDeletedAtIsNull(Long postId);
}
