package com.ljs.ai.search.hillclimbing;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

/**
 *
 * @author lstephen
 */
public final class Validators {

    public static <S> Validator<S> alwaysTrue() {
        return from(Predicates.<S>alwaysTrue());
    }

    private static <S> Validator<S> from(Predicate<S> predicate) {
        return new PredicateValidator<S>(predicate);
    }

    private static final class PredicateValidator<S> implements Validator<S> {

        private final Predicate<S> predicate;

        public PredicateValidator(Predicate<S> predicate) {
            this.predicate = predicate;
        }

        public Boolean apply(S state) {
            return predicate.apply(state);
        }
    }

}
