package com.bookjob.job.domain;

import com.bookjob.common.domain.SoftDeleteEntity;
import com.bookjob.common.exception.BadRequestException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobSeeking extends SoftDeleteEntity {

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

    @Column(nullable = false)
    private String contactEmail;

    private String experience;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobCategory jobCategory;

    @Builder
    public JobSeeking(Long memberId, String nickname, String title, String text, String contactEmail, String experience, EmploymentType employmentType, JobCategory jobCategory) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.title = title;
        this.text = text;
        this.contactEmail = contactEmail;
        this.experience = experience;
        this.employmentType = employmentType;
        this.jobCategory = jobCategory;
    }

    public void incrementViewCount() {
        if (this.viewCount == null) {
            this.viewCount = 1;
        } else {
            this.viewCount++;
        }
    }

    public void update(
            String title,
            String text,
            EmploymentType employmentType,
            JobCategory jobCategory,
            String contactEmail,
            String experience
    ) {
        this.title = title;
        this.text = text;
        this.employmentType = employmentType;
        this.jobCategory = jobCategory;
        this.contactEmail = contactEmail;
        this.experience = experience;
    }

    public void softDelete() {
        if (this.getDeletedAt() != null) {
            throw BadRequestException.JobSeekingAlreadyDeleted();
        }
        this.delete();
    }
}
