package com.ljs.ai.search.hillclimbing;

import java.io.Serializable;

import com.google.common.collect.Ordering;

/**
 *
 * @author lstephen
 */
public final class Heuristics {

    private Heuristics() {
    }

    public static <S> Heuristic<S> from(Ordering<S> ordering) {
      return (S lhs, S rhs) -> ordering.compare(lhs, rhs);
    }

}
