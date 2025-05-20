package com.bookjob.board.repository;

import com.bookjob.board.domain.Board;
import com.bookjob.member.dto.MyPostingsInBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findBoardByIdAndDeletedAtIsNull(Long id);

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

    void deleteAllByMemberId(Long memberId);
}
