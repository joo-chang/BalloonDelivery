package com.sparta.balloondelivery.util;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedBy
    private String updatedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    private Boolean deletedYn = false;

    public void setDeletedYnTrue(String userName) {
        this.deletedYn = true;
        this.deletedAt = LocalDateTime.now();
        // 삭제 요청한 사용자 이름은 받아오기
        this.deletedBy = userName;
    }
    public void setDeletedYnFalse() {
        // TODO : 삭제됐던 데이터를 다시 복구하면 삭제됐던 시간이랑 이름은 초기화 해야 되는지?
        this.deletedYn = false;
        this.deletedAt = null;
        this.deletedBy = null;
    }
    // 삭제된 데이터인지 확인
    public Boolean isDeleted() {
        return this.deletedYn;
    }

    public void setUpdatedBy(String userName) {
        this.updatedBy = userName;
        this.updatedAt = LocalDateTime.now();
    }

    public void setCreatedBy(String username) {
        this.createdBy = username;
        this.createdAt = LocalDateTime.now();
    }
}