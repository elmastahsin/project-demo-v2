package com.company.projectdemo.repository;

import com.company.projectdemo.entity.LogHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogHistoryRepository extends JpaRepository<LogHistory, Long> {

}
