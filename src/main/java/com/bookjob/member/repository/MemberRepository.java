package com.bookjob.member.repository;

import com.bookjob.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String toEmail);

    Optional<Member> findByIdAndIsBlockedFalseAndDeletedAtIsNull(Long id);

    Optional<Member> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);
}
