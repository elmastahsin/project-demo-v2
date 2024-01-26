package com.company.projectdemo.service.impl;

import com.company.projectdemo.entity.LogHistory;
import com.company.projectdemo.mapper.MapperUtil;
import com.company.projectdemo.repository.LogHistoryRepository;
import com.company.projectdemo.repository.UserRepository;
import com.company.projectdemo.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogHistoryRepository logHistoryRepository;


    @Override
    public void save(LogHistory log) {
        logHistoryRepository.save(log);
    }


}
