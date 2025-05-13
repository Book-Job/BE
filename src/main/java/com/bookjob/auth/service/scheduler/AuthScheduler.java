package com.bookjob.auth.service.scheduler;

import com.bookjob.auth.domain.repository.TemporaryTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AuthScheduler {

    private final TemporaryTokenRepository temporaryTokenRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void deleteExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        temporaryTokenRepository.deleteExpiredTokens(now);
    }
}
