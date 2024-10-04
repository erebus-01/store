package io.store.steam.repository.specification;

import io.store.steam.model.Game;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class GameSpecificationBuilder {
    private final List<SearchCriteria> params;

    public GameSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public GameSpecificationBuilder with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public GameSpecificationBuilder with(String orPredicate, String key, String operation, Object value, String prefix, String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if(op != null) {
            if(op == SearchOperation.EQUALS) {
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if(startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if(startWithAsterisk) {
                    op = SearchOperation.END_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.START_WITH;
                }
            }
            params.add(new SearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<Game> build() {
        if(params.isEmpty()) return null;

        Specification<Game> result = new GameSpecification(params.get(0));

        for(int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new GameSpecification(params.get(i)))
                    : Specification.where(result).and(new GameSpecification(params.get(i)));
        }

        return result;

    }

}
