package com.company.projectdemo.entity;

import com.company.projectdemo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public class BaseEntity {
    //    @Column(nullable = false, updatable = false)
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdatetime;
    //    @Column(nullable = false, updatable = false)
    private Long createuserId;
    //    @Column(nullable = false)
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastupdatetime;
    private String note;
    private String name;
    private short flag;
    @Enumerated(EnumType.STRING)
    private Status status;


    //private Boolean isDeleted = false;
    //    @Column(nullable = false)
    //  private Long lastupdateuserid;
    @PrePersist
    public void onPrePersist() {
        this.createdatetime = LocalDateTime.now();
        this.lastupdatetime = LocalDateTime.now();
        this.createuserId = 1L;
        this.flag = 1;
        this.status = Status.ACTIVE;
//      this.lastupdateuserid = 1L;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.lastupdatetime = LocalDateTime.now();
//      this.lastupdateuserid = 1L;
    }

}
