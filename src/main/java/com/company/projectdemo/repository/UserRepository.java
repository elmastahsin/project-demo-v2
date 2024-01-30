package com.company.projectdemo.repository;

import com.company.projectdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("SELECT t FROM User t WHERE " +
            "LOWER(t.username) LIKE LOWER(:search) OR " +
            "LOWER(t.email) LIKE LOWER(:search) OR " +
            "LOWER(t.password) LIKE LOWER(:search)")
    List<User> findByFilter(@Param("search") String search);

    User findByUsername(String username);
}