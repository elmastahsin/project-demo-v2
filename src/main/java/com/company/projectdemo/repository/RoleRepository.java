package com.company.projectdemo.repository;

import com.company.projectdemo.entity.Role;
import com.company.projectdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    @Query("SELECT r FROM Role r WHERE " +
            "LOWER(r.rank) LIKE LOWER(:search) OR " +
            "LOWER(r.name) LIKE LOWER(:search)")
    List<Role> findByFilter(@Param("search") String search);

}
