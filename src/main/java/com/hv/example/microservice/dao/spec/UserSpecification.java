package com.hv.example.microservice.dao.spec;

import com.hv.example.microservice.dao.entity.UserEntity;
import com.hv.example.microservice.domain.user.dto.UserFilterDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


@Component
@Slf4j
public class UserSpecification {
    public Specification<UserEntity> filter(UserFilterDto filter) {
        return (root, query, criteria) -> {
            var predicates = new ArrayList<Predicate>();

            if (filter == null) {
                log.info("NULL FILTER");
                return criteria.and(predicates.toArray(new Predicate[0]));
            }

            //if filter is passed
            if(filter.getFilter() != null && filter.getFilter().trim().length() > 0) {
                var space = " ";
                //saving criteriaOr in filterPredicates list
                var filterPredicates = new ArrayList<Predicate>();
                //sanitize filter removing duplicated spaces
                var filterCleaned = filter.getFilter().trim().replaceAll("\\s+", space);
                //splice filterStr by spaces convert to trim and upper case list of params
                var filterParams = Arrays.stream(filterCleaned.trim().toUpperCase().split(space)).collect(Collectors.toList());
                //iterate ofer filterParams adding criteria or to predicates list
                filterParams.forEach(f -> {
                    var criteriaOr = criteria.or(
                            criteria.like(criteria.upper(root.get("id")),"%"+f+"%"),
                            criteria.like(criteria.upper(root.get("name")),"%"+f+"%"),
                            criteria.like(criteria.upper(root.get("email")),"%"+f+"%")
                    );
                    filterPredicates.add(criteriaOr);
                });
                //adding filterPredicates to predicates list
                predicates.addAll(filterPredicates);
            }

            //if fromDate is passed
            if(filter.getFromDate() != null) {
                predicates.add(criteria.greaterThanOrEqualTo(root.get("createdAt"), filter.getFromDate()));
            }

            //if toDate is passed
            if(filter.getToDate() != null) {
                predicates.add(criteria.lessThanOrEqualTo(root.get("createdAt"), filter.getToDate()));
            }

            //if enabled is passed
            if(filter.getEnabled() != null) {
                predicates.add(criteria.equal(root.get("enabled"), filter.getEnabled()));
            }

            return criteria.and(predicates.toArray(new Predicate[0]));
        };
    }
}