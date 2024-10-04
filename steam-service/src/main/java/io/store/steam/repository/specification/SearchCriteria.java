package io.store.steam.repository.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static io.store.steam.repository.specification.SearchOperation.OR_PREDICATE_FLAG;
@Getter
@Setter
@NoArgsConstructor
public class SearchCriteria {

    private String key;
    private Object value;
    private SearchOperation operation;
    private boolean orPredicate;

    public SearchCriteria(final String key, final SearchOperation operation, final Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(final String orPredicate, final String key, final SearchOperation operation, final Object value) {
        super();
        this.orPredicate = orPredicate != null && orPredicate.equals(OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
