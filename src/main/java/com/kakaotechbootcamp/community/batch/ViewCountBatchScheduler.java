package com.kakaotechbootcamp.community.batch;

import com.kakaotechbootcamp.community.service.ViewCountSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ViewCountBatchScheduler {

    private final ViewCountSyncService viewCountSyncService;

    @Scheduled(fixedRate = 300000)
    public void syncViewCountsToDB() {
        viewCountSyncService.sync();
    }
}
