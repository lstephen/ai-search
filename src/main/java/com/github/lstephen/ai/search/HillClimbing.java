package com.github.lstephen.ai.search;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.github.lstephen.ai.search.action.Action;
import com.github.lstephen.ai.search.action.ActionGenerator;

/**
 *
 * @author lstephen
 */
public final class HillClimbing<S> {

    private final S initial;

    private final Validator<S> validator;

    private final Heuristic<S> heuristic;

    private final ActionGenerator<S> actionGenerator;

    private HillClimbing(Builder<S> builder) {
        Preconditions.checkNotNull(builder.initial);
        Preconditions.checkNotNull(builder.validator);
        Preconditions.checkNotNull(builder.heuristic);
        Preconditions.checkNotNull(builder.actionGenerator);

        this.initial = builder.initial;
        this.validator = builder.validator;
        this.heuristic = builder.heuristic;
        this.actionGenerator = builder.actionGenerator;
    }

    public S search() {
        S current = initial;

        Optional<S> next = next(current);

        while (next.isPresent()) {
            current = next.get();

            next = next(current);
        }

        return current;
    }

    private Optional<S> next(S current) {

        for (Action<S> action : actionGenerator.apply(current)) {

            S next = action.apply(current);

            if (validator.apply(next) && heuristic.compare(current, next) < 0) {
                return Optional.of(next);
            }
        }

        return Optional.absent();
    }

    public static <S> Builder<S> builder() {
        return Builder.create();
    }

    private static <S> HillClimbing<S> build(Builder<S> builder) {
        return new HillClimbing<S>(builder);
    }

    public static final class Builder<S> {

        private S initial;

        private Validator<S> validator = Validators.alwaysTrue();

        private Heuristic<S> heuristic;

        private ActionGenerator<S> actionGenerator;

        private Builder() {
        }

        public Builder<S> initial(S initial) {
            this.initial = initial;
            return this;
        }

        public Builder<S> validator(Validator<S> validator) {
            this.validator = validator;
            return this;
        }

        public Heuristic<S> getHeuristic() {
            return heuristic;
        }

        public Builder<S> heuristic(Heuristic<S> heurisitc) {
            this.heuristic = heurisitc;
            return this;
        }

        public Builder<S> heuristic(final Ordering<? super S> ordering) {
            return heuristic(Heuristics.from(ordering));
        }

        public Builder<S> actionGenerator(ActionGenerator<S> actionGenerator) {
            this.actionGenerator = actionGenerator;
            return this;
        }

        public HillClimbing<S> build() {
            return HillClimbing.build(this);
        }

        public static <S> Builder<S> create() {
            return new Builder<S>();
        }
    }

}
