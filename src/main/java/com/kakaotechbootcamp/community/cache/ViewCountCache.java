package com.kakaotechbootcamp.community.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ViewCountCache {

    private final ConcurrentHashMap<Long, Integer> viewCounts = new ConcurrentHashMap<>();

    public void increment(Long postId) {
        viewCounts.merge(postId, 1, Integer::sum);
    }

    public Map<Long, Integer> flushAll() {
        Map<Long, Integer> copy = Map.copyOf(viewCounts);
        viewCounts.clear();

        return copy;
    }

}
