package io.store.steam.utils.response;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@Getter
@Immutable
public class PageableResponse<T> {

    private final int page;
    private final int size;
    private final long total;
    private final List<T> items;
    private final Sort sort;

    @Builder
    public PageableResponse(int page, int size, long total, List<T> items, Sort sort) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.items = Collections.unmodifiableList(items);
        this.sort = sort;
    }

    public static class PageResponseBuilder<T> {
        private int page;
        private int size;
        private long total;
        private List<T> items;
        private Sort sort;

        public PageableResponse<T> build() {
            return new PageableResponse<>(page, size, total, items, sort);
        }
    }

}
