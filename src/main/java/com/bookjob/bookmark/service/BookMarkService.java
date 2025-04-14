package com.bookjob.bookmark.service;

import com.bookjob.bookmark.dto.request.BookMarkCreateRequest;
import com.bookjob.bookmark.dto.response.MyBookMarksListResponse;
import com.bookjob.bookmark.dto.response.MyBookMarksResponse;
import com.bookjob.bookmark.entity.BookMark;
import com.bookjob.bookmark.entity.BookMarkType;
import com.bookjob.bookmark.repository.BookMarkRepository;
import com.bookjob.common.exception.ForbiddenException;
import com.bookjob.common.exception.NotFoundException;
import com.bookjob.job.repository.JobPostingRepository;
import com.bookjob.job.repository.JobSeekingRepository;
import com.bookjob.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final JobSeekingRepository jobSeekingRepository;
    private final JobPostingRepository jobPostingRepository;

    public void createBookMark(Member member, BookMarkCreateRequest request) {
        BookMarkType type = BookMarkType.fromString(request.type());
        boolean exists = switch (type) {
            case JOB_POSTING -> jobPostingRepository.existsById(request.relatedId());
            case JOB_SEEKING -> jobSeekingRepository.existsById(request.relatedId());
        };

        if (!exists) {
            throw NotFoundException.bookMarkTargetNotFound();
        }


        BookMark bookMark = BookMark.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .relatedId(request.relatedId())
                .bookMarkType(type)
                .build();
        bookMarkRepository.save(bookMark);
    }

    public void deleteBookMark(Member member, Long id) {
        BookMark bookMark = bookMarkRepository.findById(id).orElseThrow(NotFoundException::bookMarkNotFound);

        if (!bookMark.getMemberId().equals(member.getId())) {
           throw ForbiddenException.forbidden();
        }

        bookMarkRepository.delete(bookMark);
    }

    public MyBookMarksListResponse getMyBookmarks(Member member) {
        List<BookMark> bookMarks = bookMarkRepository.findByMemberId(member.getId());

        List<MyBookMarksResponse> responses = bookMarks.stream()
                .map(bookMark -> {
                    Long relatedId = bookMark.getRelatedId();
                    BookMarkType type = bookMark.getBookMarkType();

                    return switch (type) {
                        case JOB_POSTING -> jobPostingRepository.findById(relatedId)
                                .map(posting -> new MyBookMarksResponse(
                                        bookMark.getId(),
                                        posting.getId(),
                                        posting.getTitle(),
                                        posting.getViewCount(),
                                        posting.getJobCategory().name(), // or recruitmentCategory
                                        posting.getEmploymentType().name(),
                                        posting.getCreatedAt()
                                ))
                                .orElse(null);

                        case JOB_SEEKING -> jobSeekingRepository.findById(relatedId)
                                .map(seeking -> new MyBookMarksResponse(
                                        bookMark.getId(),
                                        seeking.getId(),
                                        seeking.getTitle(),
                                        seeking.getViewCount(),
                                        seeking.getJobCategory().name(),
                                        seeking.getEmploymentType().name(),
                                        seeking.getCreatedAt()
                                ))
                                .orElse(null);
                    };
                })
                .filter(Objects::nonNull)
                .toList();
        return new MyBookMarksListResponse(responses);
    }
}
