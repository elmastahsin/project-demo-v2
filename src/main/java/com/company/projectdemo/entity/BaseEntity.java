package com.company.projectdemo.entity;

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
    private LocalDateTime createdatetime;
    //    @Column(nullable = false, updatable = false)
    private Long createuserId;
    //    @Column(nullable = false)
    private LocalDateTime lastupdatetime;
//    @Column(nullable = false)
//  private Long lastupdateuserid;

    private String name;

    //private Boolean isDeleted = false;
    private short flag;
    private boolean status;

    @PrePersist
    public void onPrePersist() {
        this.createdatetime = LocalDateTime.now();
        this.lastupdatetime = LocalDateTime.now();
        this.createuserId = 1L;
        this.flag = 1;
        this.status = true;
//      this.lastupdateuserid = 1L;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.lastupdatetime = LocalDateTime.now();
//      this.lastupdateuserid = 1L;
    }

}
