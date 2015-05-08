package com.github.lstephen.ai.search.action;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

/**
 *
 * @author lstephen
 */
public final class SequencedAction<S> implements Action<S> {

    private final ImmutableList<Action<S>> actions;

    private SequencedAction(Iterable<Action<S>> actions) {
        this.actions = ImmutableList.copyOf(actions);
    }

    @Override
    public S apply(S initial) {
      AtomicReference<S> state = new AtomicReference<>(initial);

      actions.stream().forEach((a) -> state.getAndUpdate(a::apply));

      return state.get();
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <S> SequencedAction<S> create(Action<S>... as) {
        return new SequencedAction<>(Arrays.asList(as));
    }

    private static <S> SequencedAction<S> create(Iterable<Action<S>> actions) {
        return create(Iterables.get(actions, 0), Iterables.get(actions, 1));
    }

    public static <S> ImmutableSet<SequencedAction<S>> allPairs(Iterable<? extends Action<S>> actions) {
        ICombinatoricsVector<Action<S>> initial = Factory.createVector(ImmutableSet.copyOf(actions));

        Generator<Action<S>> generator = Factory.createSimpleCombinationGenerator(initial, 2);

        Set<SequencedAction<S>> result = Sets.newHashSet();

        for (ICombinatoricsVector<Action<S>> combination : generator) {
            result.add(create(combination.getVector()));
        }

        return ImmutableSet.copyOf(result);
    }

    public static <S> ImmutableSet<SequencedAction<S>> merged(
        Iterable<? extends Action<S>> firsts,
        Iterable<? extends Action<S>> seconds) {

        Set<SequencedAction<S>> actions = Sets.newHashSet();

        for (Action<S> first : firsts) {
            for (Action<S> second : seconds) {
                actions.add(SequencedAction.create(first, second));
            }
        }

        return ImmutableSet.copyOf(actions);
    }


}
