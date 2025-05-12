package com.bookjob.auth.domain.repository;

import com.bookjob.auth.domain.entity.TemporaryToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface TemporaryTokenRepository extends JpaRepository<TemporaryToken, String> {
    boolean existsByTokenAndEmail(@Param("resetToken") String resetToken, @Param("email") String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM TemporaryToken t WHERE t.expiresAt < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);
}
