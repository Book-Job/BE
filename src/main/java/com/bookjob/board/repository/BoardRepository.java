package com.bookjob.board.repository;

import com.bookjob.board.domain.Board;
import com.bookjob.board.dto.response.BoardDetailResponse;
import com.bookjob.board.dto.response.BoardPreviewResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("""
            SELECT new com.bookjob.board.dto.response.BoardPreviewResponse(
            b.id, b.title, b.text, b.nickname, b.viewCount, b.isAuthentic, b.commentCount, b.createdAt, b.modifiedAt)
            FROM Board b WHERE b.id < :cursorId ORDER BY b.id DESC
            """)
    List<BoardPreviewResponse> findBoardsBeforeCursor(@Param("cursorId") Long cursorId, Pageable pageable);

    @Query("""
            SELECT new com.bookjob.board.dto.response.BoardDetailResponse(
            b.title, b.text, b.nickname, b.commentCount, b.viewCount, b.isAuthentic, b.createdAt, b.modifiedAt)
            FROM Board b WHERE b.id = :id
            """)
    Optional<BoardDetailResponse> findBoardById(@Param("id") Long id);
}
