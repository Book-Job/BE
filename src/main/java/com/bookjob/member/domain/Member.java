package com.bookjob.member.domain;

import com.bookjob.common.domain.Password;
import com.bookjob.common.domain.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Member extends SoftDeleteEntity {
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
    private Boolean isBlocked;

    @Builder
    public Member(String loginId, String nickname, String email, Password password) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = MemberRole.MEMBER;
        this.isBlocked = false;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
