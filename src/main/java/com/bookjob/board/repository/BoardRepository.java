package com.bookjob.board.repository;

import com.bookjob.board.domain.Board;
import com.bookjob.board.dto.response.BoardDetailResponse;
import com.bookjob.member.dto.MyPostingsInBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("""
            SELECT new com.bookjob.board.dto.response.BoardDetailResponse(
            b.title,
            b.text,
            b.nickname,
            b.commentCount,
            b.viewCount,
            b.isAuthentic,
            CASE WHEN b.memberId = :memberId THEN true ELSE false END,
            b.createdAt,
            b.modifiedAt
            )
            FROM Board b WHERE b.id = :id AND b.deletedAt IS NULL
            """)
    Optional<BoardDetailResponse> findBoardById(@Param("id") Long id, @Param("memberId") Long memberId);

    @Query("""
            SELECT new com.bookjob.member.dto.MyPostingsInBoard(
            b.id, b.title, b.createdAt, b.commentCount, b.viewCount)
            FROM Board b
            WHERE b.deletedAt is null and
            b.memberId = :memberId
            """)
    Page<MyPostingsInBoard> findMyPostingsByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query("""
            SELECT b
            FROM Board b
            WHERE b.createdAt >= :since
            AND b.deletedAt is null
            """)
    Page<Board> findRecentPosts(@Param("since") LocalDateTime since, Pageable pageable);
}
