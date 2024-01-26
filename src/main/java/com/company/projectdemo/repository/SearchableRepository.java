package com.company.projectdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface SearchableRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    @Query("SELECT e FROM #{#entityName} e WHERE " +
            "LOWER(e.rank) LIKE LOWER(:search) OR " +
            "LOWER(e.createuserId) LIKE LOWER(:search) OR " +
            "LOWER(e.name) LIKE LOWER(:search) OR " +
            "LOWER(e.status) LIKE LOWER(:search)")
    List<T> findByFilter(@Param("search") String search);
}
