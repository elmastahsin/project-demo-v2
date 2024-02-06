package com.company.projectdemo.service.impl;

import com.company.projectdemo.dto.LogDTO;
import com.company.projectdemo.entity.Log;
import com.company.projectdemo.mapper.MapperUtil;
import com.company.projectdemo.repository.LogRepository;
import com.company.projectdemo.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final MapperUtil mapper;

    @Override
    public void save(Log log) {
        logRepository.save(log);
    }

    @Override
    public List<LogDTO> listAll() {
        List<Log> logs = logRepository.findAll();
        return logs.stream().map(log -> mapper.convert(log, new LogDTO())).collect(Collectors.toList());
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
