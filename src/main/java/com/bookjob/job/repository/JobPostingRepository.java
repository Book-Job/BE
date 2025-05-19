package com.bookjob.job.repository;

import com.bookjob.job.domain.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long>, JobPostingQueryRepository {
    @Query("""
            SELECT j
            FROM JobPosting j
            WHERE j.createdAt >= :since
              AND j.deletedAt is null
            """)
    Page<JobPosting> findRecentPosts(@Param("since") LocalDateTime since, Pageable pageable);

    Optional<JobPosting> findByIdAndDeletedAtIsNull(Long id);

    void deleteAllByMemberId(Long memberId);
}
