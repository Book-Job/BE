package com.bookjob.comment.domain;

import com.bookjob.common.domain.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private Long memberId;

    @Setter
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String nickname;

    private String password;

    private Boolean isAuthentic;

    @Builder
    private Comment(Long memberId, String nickname, String password, Boolean isAuthentic, String content, Long boardId) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.password = password;
        this.isAuthentic = isAuthentic;
        this.content = content;
        this.boardId = boardId;
    }
}
