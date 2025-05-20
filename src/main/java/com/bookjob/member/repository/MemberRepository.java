package com.bookjob.member.repository;

import com.bookjob.common.domain.Password;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.response.MemberDetailResponse;
import com.bookjob.member.dto.response.MyPageResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String toEmail);

    Optional<Member> findByIdAndIsBlockedFalseAndDeletedAtIsNull(Long id);

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
    select new com.bookjob.member.dto.response.MyPageResponse(
    m.nickname, m.email)
    from Member m
    where m.id = :id
    """)
    MyPageResponse getMyPageById(Long id);

    @Query("""
            select new com.bookjob.member.dto.response.MemberDetailResponse(
            m.nickname, m.email, m.loginId)
            from Member m
            where m.id = :id
            """)
    MemberDetailResponse getMemberDetailById(Long id);

    @Query("SELECT m.password FROM Member m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<Password> findPasswordById(@Param("id") Long id);

    Optional<Member> findByEmail(@Param("email") String email);

    @Query("""
            select m
            from Member m
            where m.loginId = :loginId
            and m.deletedAt is null
            """)
    Optional<Member> findByLoginIdNotDeleted(@Param("loginId")String loginId);

    Optional<Member> findByLoginIdAndDeletedAtIsNotNull(@Param("loginId") String loginId);

    @Modifying
    @Query("DELETE FROM Member m WHERE m.loginId = :loginId AND m.deletedAt IS NOT NULL")
    void hardDeleteByLoginId(@Param("loginId") String loginId);

}
