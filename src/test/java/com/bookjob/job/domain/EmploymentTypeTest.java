package com.bookjob.job.domain;

import com.bookjob.common.exception.BadRequestException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EmploymentTypeTest {

    @Nested
    class FromString {

        @Test
        void 유효한_직무_카테고리_문자열로_EmploymentType_생성() {
            // given
            String employmentString = "full_time";

            // when
            EmploymentType result = EmploymentType.fromString(employmentString);

            // then
            assertThat(result).isEqualTo(EmploymentType.FULL_TIME);
        }

        @Test
        void 유효하지_않은_직무_카테고리_문자열로_예외_발생() {
            // given
            String invalidType = "invalid_type";

            // when & then
            assertThatThrownBy(() -> EmploymentType.fromString(invalidType))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(BadRequestException.invalidEmployType(invalidType).getMessage());
        }
    }
}