package com.bookjob.common.utils;

import java.time.Duration;
import java.time.LocalDateTime;

public class BestPostingUtil {

    /**
     * 작성 시간이 오래될수록 점수가 줄어드는 시간 감점 점수 계산
     */
    public static double timeDecayScore(LocalDateTime createdAt) {
        long hoursAgo = Duration.between(createdAt, LocalDateTime.now()).toHours();
        return Math.max(0, 20 - hoursAgo * 0.5);
    }
}
