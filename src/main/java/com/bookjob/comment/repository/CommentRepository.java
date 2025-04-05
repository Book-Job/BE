package com.bookjob.comment.repository;

import com.bookjob.comment.domain.Comment;
import com.bookjob.comment.dto.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
            SELECT new com.bookjob.comment.dto.response.CommentResponse(
            c.id, c.text, c.nickname, c.isAuthentic, c.createdAt, c.modifiedAt)
            FROM Comment c
            WHERE c.boardId = :boardId AND c.id < :cursorId AND c.deletedAt IS NULL
            ORDER BY c.createdAt DESC
            LIMIT :limit
            """)
    List<CommentResponse> findCommentByBoardId(@Param("boardId") Long boardId, @Param("cursorId") Long cursorId, int limit);
}
