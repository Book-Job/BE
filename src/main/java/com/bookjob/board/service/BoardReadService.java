package com.bookjob.board.service;

import com.bookjob.board.domain.Board;
import com.bookjob.board.dto.BoardScore;
import com.bookjob.board.dto.response.BoardBestResponse;
import com.bookjob.board.dto.response.BoardDetailResponse;
import com.bookjob.board.dto.response.BoardPreviewResponse;
import com.bookjob.board.dto.response.CursorBoardResponse;
import com.bookjob.board.repository.BoardQueryRepository;
import com.bookjob.board.repository.BoardRepository;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.common.utils.BestPostingUtil;
import com.bookjob.member.domain.Member;
import com.bookjob.member.dto.MyPostingsInBoard;
import com.bookjob.member.dto.response.MyPostingsInBoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardReadService {

    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;

    public CursorBoardResponse getBoardsAfterCursorWithKeyword(String keyword, Long cursor, int pageSize) {
        Long cursorId = cursor != null ? cursor : java.lang.Long.MAX_VALUE;

        List<BoardPreviewResponse> boards = boardQueryRepository.getBoardListWithKeyword(keyword, cursorId, pageSize);

        if (boards.isEmpty()) {
            return new CursorBoardResponse(List.of(), 0L);
        }

        Long lastBoardId = boards.getLast().boardId();

        return new CursorBoardResponse(boards, lastBoardId);
    }

    public BoardDetailResponse getBoardDetails(Long boardId, Long memberId) {
        return boardRepository.findBoardById(boardId, memberId).orElseThrow(
                () -> NotFoundException.boardNotFound(boardId)
        );
    }

    public boolean notExistsBoard(Long boardId) {
        return !boardRepository.existsById(boardId);
    }

    public MyPostingsInBoardResponse getMyPostingsInBoard(Member member, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<MyPostingsInBoard> myPostingsInBoardList =
                boardRepository.findMyPostingsByMemberId(member.getId(), pageable);
        return MyPostingsInBoardResponse.of(myPostingsInBoardList);
    }

    public List<BoardBestResponse> getBoardBest() {
        LocalDateTime threeWeeksAgo = LocalDateTime.now().minusDays(21);
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boards = boardRepository.findRecentPosts(threeWeeksAgo, pageable);

        return boards.stream()
                .map(board -> new BoardScore(board, calculateScore(board)))
                .sorted(Comparator.comparing(BoardScore::score).reversed())
                .limit(10)
                .map(boardScore -> new BoardBestResponse(boardScore.board()))
                .toList();
    }

    /**
     * 최종 점수 계산 로직
     * - 조회수 * 4.0 (기본 가중치)
     * - 댓글 수 * 5.0 (댓글이 많은 글에 가중치 더 부여)
     * - 시간에 따른 감점
     * - 랜덤 요소 (예측을 어렵게 하기 위한 0.0 ~ 1.0 사이의 랜덤 점수 부여)
     */
    private double calculateScore(Board board) {
        double timeScore =  BestPostingUtil.timeDecayScore(board.getCreatedAt());
        double randomFactor = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
        long viewCount = Optional.ofNullable(board.getViewCount()).orElse(0L);
        long commentCount = Optional.ofNullable(board.getCommentCount()).orElse(0L);

        return viewCount * 4.0
                + commentCount * 5.0
                + timeScore
                + randomFactor;
    }
}
