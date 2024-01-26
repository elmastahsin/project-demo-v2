package com.company.projectdemo.repository.filter;

import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;


public class GenericSpecification<T> implements Specification<T> {

    private final FilterCriteria criteria;

    public GenericSpecification(FilterCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if ("EQUALS".equalsIgnoreCase(criteria.getOperation())) {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
        if ("LIKE".equalsIgnoreCase(criteria.getOperation())) {
            return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        }
        return null;
    }
}
