package com.github.lstephen.ai.search;

import com.google.common.collect.Ordering;

/**
 *
 * @author lstephen
 */
public final class Heuristics {

    private Heuristics() {
    }

    public static <S> Heuristic<S> from(Ordering<S> ordering) {
      return ordering::compare;
    }

}
