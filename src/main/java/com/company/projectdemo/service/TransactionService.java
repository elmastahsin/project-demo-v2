package com.company.projectdemo.service;

import com.company.projectdemo.dto.TransactionDTO;
import com.company.projectdemo.entity.Transaction;
import com.company.projectdemo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionService extends CrudService<TransactionDTO, Long> {

}
