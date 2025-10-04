package com.kakaotechbootcamp.community.repository;

import com.kakaotechbootcamp.community.entity.PostCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCountRepository extends JpaRepository<PostCount, Long> {
}
