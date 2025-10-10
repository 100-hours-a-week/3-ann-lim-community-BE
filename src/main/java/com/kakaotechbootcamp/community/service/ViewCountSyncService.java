package com.kakaotechbootcamp.community.service;

import com.kakaotechbootcamp.community.cache.ViewCountCache;
import com.kakaotechbootcamp.community.repository.PostCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ViewCountSyncService {

    private final ViewCountCache viewCountCache;
    private final PostCountRepository postCountRepository;

    @Transactional
    public void sync() {

        Map<Long, Integer> viewCounts = viewCountCache.flushAll();

        if (viewCounts.isEmpty()) {
            return;
        }

        viewCounts.forEach(postCountRepository::increaseViewCount);
    }
}