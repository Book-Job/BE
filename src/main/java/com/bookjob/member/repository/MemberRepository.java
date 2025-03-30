package com.bookjob.member.repository;

import com.bookjob.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String toEmail);

    Optional<Member> findByIdAndIsBlockedFalseAndDeletedAtIsNull(Long id);

    Optional<Member> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    @Query("""
    select m.loginId from Member m where m.email = :email
    """)
    String findLoginIdByEmail(String email);
}
