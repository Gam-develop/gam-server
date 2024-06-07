package com.gam.api.domain.user.schedular;

import com.gam.api.domain.user.entity.UserDeleteAccountReason;
import com.gam.api.domain.user.repository.UserDeleteAccountReasonRepository;
import com.gam.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSchedular {

    private final UserService userService;
    private final UserDeleteAccountReasonRepository userDeleteAccountReasonRepository;

    @Scheduled(cron = "0 0 9 * * *")
//    @Scheduled(fixedDelay = 10000)
    public void userAccountDisabled() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<UserDeleteAccountReason> deactivatedUsers30daysAgo = userDeleteAccountReasonRepository.findByCreatedAtBefore(thirtyDaysAgo);
        deactivatedUsers30daysAgo.forEach((user) -> {
            userService.deleteUser(user.getId());
        });
    }
}
