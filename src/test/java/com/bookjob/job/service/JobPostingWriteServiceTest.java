package com.bookjob.job.service;

import com.bookjob.job.domain.EmploymentType;
import com.bookjob.job.domain.JobCategory;
import com.bookjob.job.domain.JobPosting;
import com.bookjob.job.dto.request.JobPostingCreateRequest;
import com.bookjob.job.repository.JobPostingRepository;
import com.bookjob.member.domain.Member;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobPostingWriteServiceTest {

    @InjectMocks
    private JobPostingWriteService jobPostingWriteService;

    @Mock
    private JobPostingRepository jobPostingRepository;

    @Nested
    class CreateJobPosting {

        @Test
        void 구인_글_작성() {
            // given
            Long memberId = 1L;
            String nickname = "테스터";
            String title = "편집자 구인합니다";
            String text = "편집자 신입 구인합니다";
            String jobCategory = JobCategory.EDITOR.name();
            String employmentType = "FULL_TIME";
            String websiteUrl = "https://example.com";
            String location = "서울 강남구";
            int experienceMin = 0;
            int experienceMax = 7;
            LocalDateTime closingDate = LocalDateTime.now().plusDays(14);

            Member member = mock(Member.class);
            when(member.getId()).thenReturn(memberId);
            when(member.getNickname()).thenReturn(nickname);

            JobPostingCreateRequest request = mock(JobPostingCreateRequest.class);
            when(request.title()).thenReturn(title);
            when(request.text()).thenReturn(text);
            when(request.jobCategory()).thenReturn(jobCategory);
            when(request.employmentType()).thenReturn(employmentType);
            when(request.websiteUrl()).thenReturn(websiteUrl);
            when(request.location()).thenReturn(location);
            when(request.experienceMin()).thenReturn(experienceMin);
            when(request.experienceMax()).thenReturn(experienceMax);
            when(request.closingDate()).thenReturn(closingDate);

            ArgumentCaptor<JobPosting> jobPostingCaptor = ArgumentCaptor.forClass(JobPosting.class);

            // when
            jobPostingWriteService.createJobPosting(request, member);

            // then
            verify(jobPostingRepository).save(jobPostingCaptor.capture());

            JobPosting savedJobPosting = jobPostingCaptor.getValue();

            assertThat(savedJobPosting.getMemberId()).isEqualTo(memberId);
            assertThat(savedJobPosting.getNickname()).isEqualTo(nickname);
            assertThat(savedJobPosting.getTitle()).isEqualTo(title);
            assertThat(savedJobPosting.getText()).isEqualTo(text);
            assertThat(savedJobPosting.getJobCategory()).isEqualTo(JobCategory.valueOf(jobCategory));
            assertThat(savedJobPosting.getEmploymentType()).isEqualTo(EmploymentType.valueOf(employmentType));
            assertThat(savedJobPosting.getWebsiteUrl()).isEqualTo(websiteUrl);
            assertThat(savedJobPosting.getLocation()).isEqualTo(location);
            assertThat(savedJobPosting.getExperienceMin()).isEqualTo(experienceMin);
            assertThat(savedJobPosting.getExperienceMax()).isEqualTo(experienceMax);
            assertThat(savedJobPosting.getClosingDate()).isEqualTo(closingDate);
            assertThat(savedJobPosting.getIsCrawling()).isFalse();
        }
    }
}