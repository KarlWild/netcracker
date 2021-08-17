package com.netcracker.auto.util;

import com.netcracker.auto.entity.Ad;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Anton Popkov
 */
public class AdSpecification implements Specification<Ad> {

    private SearchCriteria criteria;

    public AdSpecification(final SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Object> arguments = criteria.getArguments();
        Object arg = arguments.get(0);
        switch (criteria.getSearchOperation()) {
            case EQUALITY:
                return criteriaBuilder.equal(root.get(criteria.getKey()), arg);
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(criteria.getKey()), (Comparable) arg);
            case IN:
                return root.get(criteria.getKey()).in(arguments);
        }
        return null;
    }

}
