package com.bookjob.job.repository;

import com.bookjob.job.domain.JobPostingOrder;
import com.bookjob.job.dto.application.MyPostingsInRecruitment;
import com.bookjob.job.dto.response.JobPostingPreviewResponse;
import com.bookjob.jooq.generated.tables.JobPosting;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.bookjob.jooq.generated.Tables.JOB_POSTING;
import static com.bookjob.jooq.generated.Tables.JOB_SEEKING;
import static org.jooq.impl.DSL.*;

@Repository
@RequiredArgsConstructor
public class JobPostingQueryRepositoryImpl implements JobPostingQueryRepository {

    private final DSLContext dslContext;
    private final JobPosting jp = JobPosting.JOB_POSTING.as("jp");

    @Override
    public List<JobPostingPreviewResponse> getJobPostingsOrderedBy(JobPostingOrder order, Long cursor, int size, String keyword) {
        LocalDateTime now = LocalDateTime.now();

        JobPostingPreviewResponse cursorPosting = null;
        if (cursor != null) {
            cursorPosting = dslContext
                    .select(jp.fields())
                    .from(jp)
                    .where(jp.ID.eq(cursor))
                    .fetchOneInto(JobPostingPreviewResponse.class);

            if (cursorPosting == null) {
                return List.of();
            }
        }

        Condition openCondition = jp.CLOSING_DATE.isNull()
                .or(jp.CLOSING_DATE.greaterThan(now));

        if (cursorPosting != null) {
            boolean isCursorOpen = cursorPosting.closingDate() == null ||
                                   cursorPosting.closingDate().isAfter(now);

            if (isCursorOpen) {
                Condition cursorCondition = getKeysetPaginationCondition(order, cursorPosting);
                openCondition = openCondition.and(cursorCondition);
            } else {
                openCondition = DSL.falseCondition();
            }
        }

        List<Long> sortedIds = new ArrayList<>(dslContext
                .select(jp.ID)
                .from(jp)
                .where(openCondition)
                .and(jobPostingSearchCondition(keyword))
                .and(jp.DELETED_AT.isNull())
                .orderBy(getOpenPostingSortFields(order))
                .limit(size)
                .fetchInto(Long.class));

        if (sortedIds.size() < size) {
            Condition closedCondition = jp.CLOSING_DATE.isNotNull()
                    .and(jp.CLOSING_DATE.lessOrEqual(now));

            if (cursorPosting != null) {
                boolean isCursorClosed = cursorPosting.closingDate() != null &&
                                         cursorPosting.closingDate().isBefore(now.plusSeconds(1));

                if (isCursorClosed) {
                    closedCondition = closedCondition.and(
                            jp.CREATED_AT.lessThan(cursorPosting.createdAt())
                                    .or(jp.CREATED_AT.eq(cursorPosting.createdAt())
                                            .and(jp.ID.greaterThan(cursorPosting.id())))
                    );
                }
            }

            sortedIds.addAll(dslContext
                    .select(jp.ID)
                    .from(jp)
                    .where(closedCondition)
                    .and(jobPostingSearchCondition(keyword))
                    .and(jp.DELETED_AT.isNull())
                    .orderBy(jp.CREATED_AT.desc())
                    .limit(size - sortedIds.size())
                    .fetchInto(Long.class));
        }

        if (sortedIds.isEmpty()) {
            return List.of();
        }

        String idListStr = sortedIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return dslContext
                .select(jp.fields())
                .from(jp)
                .where(jp.ID.in(sortedIds))
                .orderBy(DSL.field("FIND_IN_SET({0}, {1})", Integer.class, jp.ID, idListStr))
                .fetchInto(JobPostingPreviewResponse.class);
    }

    @Override
    public Page<MyPostingsInRecruitment> findMyPostingsByMemberId(Long memberId, Pageable pageable) {
        int page = pageable.getPageNumber();
        int limit = pageable.getPageSize();

        int total = dslContext.fetchCount(
                DSL.selectOne()
                        .from(
                                DSL.select(JOB_POSTING.ID)
                                        .from(JOB_POSTING)
                                        .where(JOB_POSTING.DELETED_AT.isNull()
                                                .and(JOB_POSTING.MEMBER_ID.eq(memberId)))
                                        .unionAll(
                                                DSL.select(JOB_SEEKING.ID)
                                                        .from(JOB_SEEKING)
                                                        .where(JOB_SEEKING.DELETED_AT.isNull()
                                                                .and(JOB_SEEKING.MEMBER_ID.eq(memberId)))
                                        )
                        )
        );

        if (total == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        List<MyPostingsInRecruitment> list = dslContext.select(
                        DSL.field("recruitmentId", Long.class),
                        DSL.field("title", String.class),
                        DSL.field("created_at", LocalDateTime.class),
                        DSL.field("recruitmentCategory", String.class)
                )
                .from(DSL.select(
                                JOB_POSTING.ID.as("recruitmentId"),
                                JOB_POSTING.TITLE,
                                JOB_POSTING.CREATED_AT,
                                inline("JOB_POSTING").as("recruitmentCategory"))
                        .from(JOB_POSTING)
                        .where(JOB_POSTING.DELETED_AT.isNull()
                                .and(JOB_POSTING.MEMBER_ID.eq(memberId)))
                        .unionAll(
                                DSL.select(
                                                JOB_SEEKING.ID.as("recruitmentId"),
                                                JOB_SEEKING.TITLE,
                                                JOB_SEEKING.CREATED_AT,
                                                inline("JOB_SEEKING").as("recruitmentCategory"))
                                        .from(JOB_SEEKING)
                                        .where(JOB_SEEKING.DELETED_AT.isNull()
                                                .and(JOB_SEEKING.MEMBER_ID.eq(memberId)))
                        )
                )
                .orderBy(DSL.field("created_at").desc())
                .offset((long) page * limit)
                .limit(limit)
                .fetchInto(MyPostingsInRecruitment.class);

        return new PageImpl<>(list, pageable, total);
    }

    private Condition getKeysetPaginationCondition(JobPostingOrder order,
                                                   JobPostingPreviewResponse cursor) {
        return switch (order) {
            case POPULAR -> jp.VIEW_COUNT.lessThan(cursor.viewCount())
                    .or(jp.VIEW_COUNT.eq(cursor.viewCount())
                            .and(jp.CREATED_AT.lessThan(cursor.createdAt())
                                    .or(jp.CREATED_AT.eq(cursor.createdAt())
                                            .and(jp.ID.greaterThan(cursor.id())))));
            case CLOSING_SOON -> {
                if (cursor.closingDate() != null) {
                    yield jp.CLOSING_DATE.greaterThan(cursor.closingDate())
                            .or(jp.CLOSING_DATE.eq(cursor.closingDate())
                                    .and(jp.ID.greaterThan(cursor.id())))
                            .or(jp.CLOSING_DATE.isNull());
                } else {
                    yield jp.CLOSING_DATE.isNull()
                            .and(jp.CREATED_AT.lessThan(cursor.createdAt())
                                    .or(jp.CREATED_AT.eq(cursor.createdAt())
                                            .and(jp.ID.greaterThan(cursor.id()))));
                }
            }
            default -> jp.CREATED_AT.lessThan(cursor.createdAt())
                    .or(jp.CREATED_AT.eq(cursor.createdAt())
                            .and(jp.ID.greaterThan(cursor.id())));
        };
    }

    private List<SortField<?>> getOpenPostingSortFields(JobPostingOrder order) {
        LocalDateTime now = LocalDateTime.now();

        return switch (order) {
            case POPULAR -> List.of(
                    DSL.case_()
                            .when(jp.CLOSING_DATE.isNull().or(jp.CLOSING_DATE.greaterThan(now)), 1)
                            .otherwise(0)
                            .desc(),
                    jp.VIEW_COUNT.desc(),
                    jp.CREATED_AT.desc()
            );
            case CLOSING_SOON -> List.of(
                    DSL.case_()
                            .when(jp.CLOSING_DATE.isNull().or(jp.CLOSING_DATE.greaterThan(now)), 1)
                            .otherwise(0)
                            .desc(),
                    DSL.case_()
                            .when(jp.CLOSING_DATE.isNotNull(), 1)
                            .otherwise(0)
                            .desc(),
                    jp.CLOSING_DATE.asc(),
                    jp.CREATED_AT.desc()
            );
            default -> List.of(
                    DSL.case_()
                            .when(jp.CLOSING_DATE.isNull().or(jp.CLOSING_DATE.greaterThan(now)), 1)
                            .otherwise(0)
                            .desc(),
                    jp.CREATED_AT.desc()
            );
        };
    }

    private Condition jobPostingSearchCondition(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return DSL.noCondition();
        }

        return jp.TITLE.likeIgnoreCase(concat(inline("%"), val(keyword), inline("%")));
    }
}
