package com.company.projectdemo.service.impl;

import com.company.projectdemo.entity.LogHistory;
import com.company.projectdemo.entity.User;
import com.company.projectdemo.mapper.MapperUtil;
import com.company.projectdemo.repository.LogHistoryRepository;
import com.company.projectdemo.repository.UserRepository;
import com.company.projectdemo.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogHistoryRepository logHistoryRepository;


    @Override
    public void save(LogHistory log) {
        logHistoryRepository.save(log);
    }

//    @Override
//    public void update(User savedUser, User userToUpdate){
//
//        Map<String,Object> changedFields = EntityComparator.findChangedFields(savedUser, userToUpdate);
//        if (changedFields.isEmpty()) return ;
//
//        LogHistory log = new LogHistory();
//        if (changedFields.equals("address")) log.setTableName("address");
//        else log.setTableName("users");
//        log.setOperation("update");
//        log.setChangedColumn(changedFields);
//        log.setChangedBy(savedUser.getUsername());
//        log.setChangedAt(LocalDateTime.now());
//        save(log);
//    }

}
