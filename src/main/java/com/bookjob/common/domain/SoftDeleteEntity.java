package com.bookjob.common.domain;

import com.bookjob.common.exception.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class SoftDeleteEntity extends BaseEntity {
    @Column
    private LocalDateTime deletedAt;

    public void delete() {
        if (this.getDeletedAt() != null) {
            throw BadRequestException.alreadyDeleted();
        }

        deletedAt = LocalDateTime.now();
    }
}
