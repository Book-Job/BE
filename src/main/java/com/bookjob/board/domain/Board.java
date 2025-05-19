package com.bookjob.board.domain;

import com.bookjob.common.domain.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends SoftDeleteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private Long viewCount = 0L;

    private Boolean isAuthentic;

    private Long commentCount = 0L;

    @Builder
    public Board(Boolean isAuthentic, Long memberId, String nickname, String text, String title) {
        this.isAuthentic = isAuthentic;
        this.memberId = memberId;
        this.nickname = nickname;
        this.text = text;
        this.title = title;
    }

    public void updateText(String newText) {
        this.text = newText;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public boolean increaseCommentCount() {
        this.commentCount++;
        return true;
    }

    public boolean decreaseViewCount() {
        if (commentCount < 0) {
            return false;
        }

        this.viewCount--;

        return true;
    }
}