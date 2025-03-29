package com.bookjob.member.domain;

import com.bookjob.common.domain.BaseEntity;
import com.bookjob.common.domain.Password;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    @Embedded
    @Column(nullable = false)
    private Password password;

    private String provider;

    private String providerId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private Boolean isBlocked;

    @Builder
    public Member (String loginId, String nickname, String email, Password password) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = MemberRole.MEMBER;
        this.isDeleted = false;
        this.isBlocked = false;
    }
}
