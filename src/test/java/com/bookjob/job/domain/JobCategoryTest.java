package com.bookjob.job.domain;

import com.bookjob.common.exception.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JobCategoryTest {

    @Nested
    class FromString {

        @Test
        void 유효한_직무_카테고리_문자열로_JobCategory_생성() {
            // given
            String jobCategoryStr = "EDITOR";

            // when
            JobCategory result = JobCategory.fromString(jobCategoryStr);

            // then
            assertThat(result).isEqualTo(JobCategory.EDITOR);
        }

        @Test
        void 유효하지_않은_직무_카테고리_문자열로_예외_발생() {
            // given
            String invalidJobCategoryStr = "INVALID_CATEGORY";

            // when & then
            assertThatThrownBy(() -> JobCategory.fromString(invalidJobCategoryStr))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(BadRequestException.invalidEnumValue(invalidJobCategoryStr, JobCategory.class).getMessage());
        }
    }
}