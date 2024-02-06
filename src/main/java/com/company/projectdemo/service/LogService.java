package com.company.projectdemo.service;

import com.company.projectdemo.dto.LogDTO;
import com.company.projectdemo.entity.Log;

import java.util.List;

public interface LogService {

    void save(Log log);
//   void update(LogHistory log);
    //listAll
    List<LogDTO> listAll();


}
