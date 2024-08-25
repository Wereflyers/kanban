package ru.kanban.main.configuration;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * The type Page request override.
 */
public class PageRequestOverride extends PageRequest {

    private final int from;

    /**
     * Instantiates a new Page request override.
     *
     * @param from the from
     * @param size the size
     * @param sort the sort
     */
    protected PageRequestOverride(int from, int size, Sort sort) {
        super(from / size, size, sort);
        this.from = from;
    }

    /**
     * Of page request override.
     *
     * @param from the from
     * @param size the size
     * @return the page request override
     */
    public static PageRequestOverride of(int from, int size) {
        return new PageRequestOverride(from, size, Sort.unsorted());
    }

    @Override
    public long getOffset() {
        return from;
    }
}