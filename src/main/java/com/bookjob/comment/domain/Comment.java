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
    private String text;

    @Column(nullable = false)
    private String nickname;

    private Boolean isAuthentic;

    @Builder
    private Comment(Long memberId, String nickname, Boolean isAuthentic, String text, Long boardId) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.isAuthentic = isAuthentic;
        this.text = text;
        this.boardId = boardId;
    }
}
