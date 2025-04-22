package com.bookjob.job.service;

import com.bookjob.common.exception.NotFoundException;
import com.bookjob.common.utils.BestPostingUtil;
import com.bookjob.job.domain.JobPosting;
import com.bookjob.job.domain.JobPostingOrder;
import com.bookjob.job.dto.JobPostingScore;
import com.bookjob.job.dto.response.CursorJobPostingResponse;
import com.bookjob.job.dto.response.JobPostingBestResponse;
import com.bookjob.job.dto.response.JobPostingDetailsResponse;
import com.bookjob.job.dto.response.JobPostingPreviewResponse;
import com.bookjob.job.repository.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobPostingReadService {

    private final JobPostingRepository jobPostingRepository;

    public CursorJobPostingResponse getJobPostings(JobPostingOrder order, Long cursor, int pageSize) {
        List<JobPostingPreviewResponse> response = jobPostingRepository.getJobPostingsOrderedBy(order, cursor, pageSize);
        if (response.isEmpty()) {
            return new CursorJobPostingResponse(response, null);
        }
        Long newCursor = response.getLast().id();

        return new CursorJobPostingResponse(response, newCursor);
    }

    public JobPostingDetailsResponse getJobPostingDetails(Long id) {
        JobPosting jobPosting = jobPostingRepository.findById(id).orElseThrow(
                NotFoundException::jobPostingNotFound
        );

        jobPosting.incrementViewCount();

        return new JobPostingDetailsResponse(
                jobPosting.getId(),
                jobPosting.getNickname(),
                jobPosting.getTitle(),
                jobPosting.getText(),
                jobPosting.getViewCount(),
                jobPosting.getWebsiteUrl(),
                jobPosting.getExperienceMin(),
                jobPosting.getExperienceMax(),
                jobPosting.getClosingDate(),
                jobPosting.getEmploymentType().toString(),
                jobPosting.getJobCategory().toString(),
                jobPosting.getLocation(),
                jobPosting.getCreatedAt(),
                jobPosting.getModifiedAt()
        );
    }

    public List<JobPostingBestResponse> getJobPostingBest() {
        LocalDateTime threeWeeksAgo = LocalDateTime.now().minusDays(21);
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<JobPosting> jobPostings = jobPostingRepository.findRecentPosts(threeWeeksAgo, pageable);

        return jobPostings.stream()
                .map(job -> new JobPostingScore(job, calculateScore(job)))
                .sorted(Comparator.comparing(JobPostingScore::score).reversed())
                .limit(10)
                .map(jobScore -> new JobPostingBestResponse(jobScore.jobPosting()))
                .toList();
    }

    /**
     * 점수 계산 로직
     * - 조회수 * 5.0
     * - 시간에 따른 감점
     * - 랜덤 요소 (0.0 ~ 1.0)
     */
    private double calculateScore(JobPosting jobPosting) {
        double timeScore = BestPostingUtil.timeDecayScore(jobPosting.getCreatedAt());
        double randomFactor = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
        int viewCount = Optional.ofNullable(jobPosting.getViewCount()).orElse(0);

        return viewCount * 5.0
                + timeScore
                + randomFactor;
    }

}
