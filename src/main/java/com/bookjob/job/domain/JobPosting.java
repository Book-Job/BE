package com.bookjob.job.domain;

import com.bookjob.common.domain.SoftDeleteEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "idx_job_posting_created_at", columnList = "createdAt"),
        @Index(name = "idx_job_posting_closing_date", columnList = "closingDate")
})
public class JobPosting extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(nullable = false)
    private Integer viewCount = 0;

    private String websiteUrl;

    private Integer experienceMin;

    private Integer experienceMax;

    private LocalDateTime closingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;

    @Column(nullable = false)
    private Boolean isCrawling;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobCategory jobCategory;

    private String location;

    @Builder
    private JobPosting(LocalDateTime closingDate, EmploymentType employmentType, Boolean isCrawling, JobCategory jobCategory, String location, Integer experienceMax, Long memberId, Integer experienceMin, String nickname, String text, String title, String websiteUrl) {
        this.closingDate = closingDate;
        this.employmentType = employmentType;
        this.isCrawling = isCrawling;
        this.jobCategory = jobCategory;
        this.location = location;
        this.experienceMax = experienceMax;
        this.memberId = memberId;
        this.experienceMin = experienceMin;
        this.nickname = nickname;
        this.text = text;
        this.title = title;
        this.websiteUrl = websiteUrl;
    }

    public void update(String title, String text, String websiteUrl,
                       Integer experienceMin, Integer experienceMax,
                       LocalDateTime closingDate, EmploymentType employmentType,
                       JobCategory jobCategory, String location) {
        this.title = title;
        this.text = text;
        this.websiteUrl = websiteUrl;
        this.experienceMin = experienceMin;
        this.experienceMax = experienceMax;
        this.closingDate = closingDate;
        this.employmentType = employmentType;
        this.jobCategory = jobCategory;
        this.location = location;
    }
}