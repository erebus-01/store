package io.store.steam.repository.specification;

import io.store.steam.model.Game;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class GameSpecification implements Specification<Game> {

    private SearchCriteria criteria;


    @Override
    public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(criteria == null || criteria.getKey() == null || criteria.getValue() == null) {
            return null;
        }

        Class<?> fieldType = root.get(criteria.getKey()).getJavaType();

        return switch(criteria.getOperation()) {
            case LIKE -> criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
            case EQUALS -> {
                if(fieldType.isEnum()){
                    @SuppressWarnings("unchecked")
                    Enum<?> enumValue = Enum.valueOf((Class<Enum>) fieldType, criteria.getValue().toString());
                    yield criteriaBuilder.equal(root.get(criteria.getKey()), enumValue);
                }
                yield criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
            case NEGATION -> {
                if(fieldType.isEnum()){
                    @SuppressWarnings("unchecked")
                    Enum<?> enumValue = Enum.valueOf((Class<Enum>) fieldType, criteria.getValue().toString());
                    yield criteriaBuilder.notEqual(root.get(criteria.getKey()), enumValue);
                }
                yield criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
            }
            case LESS -> criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case GREATER -> criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
            case START_WITH -> criteriaBuilder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
            case END_WITH -> criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS -> criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        };
    }
}
