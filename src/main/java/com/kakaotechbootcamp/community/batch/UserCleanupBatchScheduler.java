package com.kakaotechbootcamp.community.batch;

import com.kakaotechbootcamp.community.entity.User;
import com.kakaotechbootcamp.community.repository.UserRepository;
import com.kakaotechbootcamp.community.service.UserDataCleanupService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class UserCleanupBatchScheduler {

    private final UserRepository userRepository;
    private final UserDataCleanupService cleanupService;

    @Scheduled(cron = "0 0 4 * * *")
    public void cleanupDeletedUsers() {

        List<User> deletedUsers = userRepository.findAllByDeletedAtIsNotNull();

        for (User user : deletedUsers) {
            cleanupService.cleanUserData(user.getId());
        }
    }
}
