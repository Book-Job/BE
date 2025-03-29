package com.bookjob.board.repository;

import com.bookjob.board.domain.Board;
import com.bookjob.board.dto.BoardPreviewResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT new com.bookjob.board.dto.BoardPreviewResponse(" +
           "b.id, b.title, b.text, b.nickname, b.viewCount, b.isAuthentic, b.commentCount, b.createdAt, b.modifiedAt) " +
           "FROM Board b WHERE b.id < :cursorId ORDER BY b.id DESC")
    List<BoardPreviewResponse> findBoardsBeforeCursor(@Param("cursorId") Long cursorId, Pageable pageable);
}
