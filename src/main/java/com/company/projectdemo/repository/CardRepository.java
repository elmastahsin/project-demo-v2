package com.company.projectdemo.repository;

import com.company.projectdemo.entity.Card;
import com.company.projectdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    @Query("SELECT t FROM Card t WHERE " +
            "LOWER(t.email) LIKE LOWER(:search) OR " +
            "LOWER(t.cardno) LIKE LOWER(:search) OR " +
            "LOWER(t.cardcvv) LIKE LOWER(:search)")
    List<Card> findByFilter(@Param("search") String search);
    Card findByCardno(Long cardno);
    boolean existsByCardno(Long cardno);
}

