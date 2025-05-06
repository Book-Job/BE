package com.bookjob.member.repository;

import com.bookjob.common.domain.Password;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.MemberDetailResponse;
import com.bookjob.member.dto.MyPageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
    select m.email from Member m where m.loginId = :loginId
    """)
    String findEmailByLoginId(String loginId);

    @Query("""
    select new com.bookjob.member.dto.MyPageResponse(
    m.nickname, m.email)
    from Member m
    where m.id = :id
    """)
    MyPageResponse getMyPageById(Long id);

    @Query("""
    select new com.bookjob.member.dto.MemberDetailResponse(
    m.nickname, m.email, m.loginId)
    from Member m
    where m.id = :id
    """)
    MemberDetailResponse getMemberDetailById(Long id);

    @Query("SELECT m.password FROM Member m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<Password> findPasswordById(@Param("id") Long id);
}
