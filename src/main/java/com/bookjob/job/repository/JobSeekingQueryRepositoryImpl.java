package com.bookjob.job.repository;

import com.bookjob.job.dto.response.JobSeekingPreviewResponse;
import com.bookjob.jooq.generated.enums.JobSeekingJobCategory;
import com.bookjob.jooq.generated.tables.JobSeeking;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JobSeekingQueryRepositoryImpl implements JobSeekingQueryRepository {

    private final DSLContext dslContext;

    @Override
    public List<JobSeekingPreviewResponse> getJobSeekingsOrderedBy(Long cursor, String jobCategory, int size) {
        JobSeeking js = JobSeeking.JOB_SEEKING;

        Condition condition = DSL.noCondition();

        // 1. jobCategory 필터 적용
        if (jobCategory != null) {
            try {
                JobSeekingJobCategory categoryEnum = JobSeekingJobCategory.valueOf(jobCategory);
                condition = condition.and(js.JOB_CATEGORY.eq(categoryEnum));
            } catch (IllegalArgumentException e) {
                // 유효하지 않은 enum 문자열이 들어올 경우 빈 결과 반환
                return List.of();
            }
        }

        // 2. 커서 조건 적용
        if (cursor != null) {
            Record2<Long, LocalDateTime> cursorRecord = dslContext
                    .select(js.ID, js.CREATED_AT)
                    .from(js)
                    .where(js.ID.eq(cursor))
                    .fetchOne();

            if (cursorRecord == null) {
                return List.of();
            }

            Long cursorId = cursorRecord.get(js.ID);
            LocalDateTime cursorCreatedAt = cursorRecord.get(js.CREATED_AT);

            condition = condition.and(
                    js.CREATED_AT.lt(cursorCreatedAt)
                            .or(js.CREATED_AT.eq(cursorCreatedAt).and(js.ID.gt(cursorId)))
            );
        }

        // 3. 실제 쿼리 실행
        return dslContext
                .select(
                        js.ID,
                        js.NICKNAME,
                        js.TITLE,
                        js.TEXT,
                        js.VIEW_COUNT,
                        js.EXPERIENCE,
                        js.EMPLOYMENT_TYPE,
                        js.JOB_CATEGORY,
                        js.CREATED_AT,
                        js.MODIFIED_AT
                )
                .from(js)
                .where(condition)
                .orderBy(js.CREATED_AT.desc(), js.ID.asc()) // Keyset pagination
                .limit(size)
                .fetchInto(JobSeekingPreviewResponse.class);
    }
}
