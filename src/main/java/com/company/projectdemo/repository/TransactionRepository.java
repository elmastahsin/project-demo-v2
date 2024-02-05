package com.company.projectdemo.repository;

import com.company.projectdemo.entity.Card;
import com.company.projectdemo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    @Query("SELECT t FROM Transaction t WHERE " +
            "LOWER(t.cardno) LIKE LOWER(:search) OR " +
            "LOWER(t.refundid) LIKE LOWER(:search) OR " +
            "LOWER(t.amountmoney) LIKE LOWER(:search) OR " +
            "LOWER(t.createuserId) LIKE LOWER(:search) OR " +
            "LOWER(t.transactiontype) LIKE LOWER(:search)")
    List<Transaction> findByFilter(@Param("search") String search);

    List<Transaction> findByCardno(Long cardno);

}
