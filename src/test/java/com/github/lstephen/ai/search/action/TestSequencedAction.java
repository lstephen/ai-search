package com.github.lstephen.ai.search.action;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;

import org.assertj.core.api.Assertions;

import org.junit.Test;

public class TestSequencedAction {

  private final Action<Integer> addOne = (s) -> s + 1;

  private final Action<Integer> addTen = (s) -> s + 10;

  @Test
  public void testAddOneTwice() {
    Assertions
      .assertThat(SequencedAction.create(addOne, addOne).apply(1))
      .isEqualTo(3);
  }

  @Test
  public void mergedAddOne() {
    ImmutableSet<SequencedAction<Integer>> actions =
      SequencedAction.merged(ImmutableSet.of(addOne), ImmutableSet.of(addOne));

    Assertions.assertThat(actions).hasSize(1);
    Assertions.assertThat(applyAllTo(actions, 1)).hasSize(1).contains(3);
  }

  @Test
  public void testAllPairs() {
    ImmutableSet<SequencedAction<Integer>> allPairs =
      SequencedAction.allPairs(ImmutableSet.of(addOne, addTen));

    Assertions.assertThat(allPairs).hasSize(4);
    Assertions.assertThat(applyAllTo(allPairs, 1)).contains(3, 12, 21);
  }

  private <S> Set<S> applyAllTo(Collection<SequencedAction<S>> as, S state) {
    return as.stream().map((a) -> a.apply(state)).collect(Collectors.toSet());
  }

}

