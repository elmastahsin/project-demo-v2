package com.company.projectdemo.repository.filter;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GenericSpecification<T> implements Specification<T> {

    private List<FilterCriteria> criteriaList;

    public GenericSpecification(List<FilterCriteria> criteriaList) {
        this.criteriaList = criteriaList;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (FilterCriteria criteria : criteriaList) {
            switch (criteria.getOperation().toLowerCase()) {
                case "equals":
                    predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case "like":
                    predicates.add(builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                    break;
                case "greaterthan":
                    predicates.add(builder.greaterThan(root.get(criteria.getKey()), (Comparable) criteria.getValue()));
                    break;
                case "lessthan":
                    predicates.add(builder.lessThan(root.get(criteria.getKey()), (Comparable) criteria.getValue()));
                    break;

            }
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
