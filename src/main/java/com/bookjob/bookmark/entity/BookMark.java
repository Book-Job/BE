package com.bookjob.bookmark.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    private Long memberId;

    private Long relatedId;

    @Enumerated(EnumType.STRING)
    private BookMarkType bookMarkType;

    @Builder
    public BookMark (LocalDateTime createdAt, Long memberId, Long relatedId, BookMarkType bookMarkType) {
        this.createdAt = createdAt;
        this.memberId = memberId;
        this.relatedId = relatedId;
        this.bookMarkType = bookMarkType;
    }
}
