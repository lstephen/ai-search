package com.ljs.ai.search.hillclimbing;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 *
 * @author lstephen
 */
public final class Validators {

    private Validators() {
    }

    public static <S> Validator<S> alwaysTrue() {
        return from(Predicates.<S>alwaysTrue());
    }

    private static <S> Validator<S> from(Predicate<S> predicate) {
      return (S state) -> predicate.apply(state);
    }

}
