package com.company.projectdemo.service;

import com.company.projectdemo.dto.LogDTO;
import com.company.projectdemo.entity.LogHistory;

public interface LogService {

   void save(LogHistory log);

}
