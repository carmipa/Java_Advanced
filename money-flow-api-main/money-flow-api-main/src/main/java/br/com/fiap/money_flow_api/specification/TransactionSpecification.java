package br.com.fiap.money_flow_api.specification;

import br.com.fiap.money_flow_api.model.TransactionFilterDto;
import br.com.fiap.money_flow_api.model.Transaction;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {

    public static Specification<Transaction> withFilters(TransactionFilterDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.description() != null && !filter.description().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get("description")), "%" + filter.description().toLowerCase() + "%")
                );
            }

            if (filter.startDate() != null && filter.endDate() != null) {
                predicates.add(
                        cb.between(root.get("date"), filter.startDate(), filter.endDate())
                );
            } else if (filter.startDate() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("date"), filter.startDate())
                );
            } else if (filter.endDate() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("date"), filter.endDate())
                );
            }

            var arrayPredicate = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicate);
        };
    }

}
