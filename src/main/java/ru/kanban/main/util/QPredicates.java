package ru.kanban.main.util;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * The type Q predicates.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QPredicates {
    /**
     * The Predicates.
     */
    final List<Predicate> predicates = new ArrayList<>();

    /**
     * Builder q predicates.
     *
     * @return the q predicates
     */
    public static QPredicates builder() {
        return new QPredicates();
    }

    /**
     * Add q predicates.
     *
     * @param <T>      the type parameter
     * @param object   the object
     * @param function the function
     * @return the q predicates
     */
    public <T> QPredicates add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    /**
     * Add q predicates.
     *
     * @param predicate the predicate
     * @return the q predicates
     */
    public QPredicates add(Predicate predicate) {
        if (predicate != null) {
            predicates.add(predicate);
        }
        return this;
    }

    /**
     * Add q predicates.
     *
     * @param expression the expression
     * @return the q predicates
     */
    public QPredicates add(BooleanExpression expression) {
        if (expression != null) {
            predicates.add(expression);
        }
        return this;
    }

    /**
     * Build and predicate.
     *
     * @return the predicate
     */
    public Predicate buildAnd() {
        return ExpressionUtils.allOf(predicates);
    }

    /**
     * Build or predicate.
     *
     * @return the predicate
     */
    public Predicate buildOr() {
        return ExpressionUtils.anyOf(predicates);
    }
}
