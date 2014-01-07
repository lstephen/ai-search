package com.ljs.ai.search.hillclimbing;

import com.google.common.collect.Ordering;

/**
 *
 * @author lstephen
 */
public final class Heuristics {

    private Heuristics() { }

    public static <S> Heuristic<S> from(Ordering<S> ordering) {
        return new OrderingHeuristic<S>(ordering);
    }

    private static final class OrderingHeuristic<S> implements Heuristic<S> {

        private final Ordering<S> ordering;

        public OrderingHeuristic(Ordering<S> ordering) {
            this.ordering = ordering;
        }

        public int compare(S lhs, S rhs) {
            return ordering.compare(lhs, rhs);
        }
    }

}
