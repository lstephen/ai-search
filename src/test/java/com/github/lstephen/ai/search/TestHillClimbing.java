package com.github.lstephen.ai.search;

import java.util.stream.Stream;

import com.google.common.collect.Ordering;

import org.assertj.core.api.Assertions;

import org.junit.Test;

public class TestHillClimbing {

  @Test
  public void whenNoActions() {
    String initial = "initial";

    String result = HillClimbing.<String>builder()
      .initial(initial)
      .heuristic(Ordering.allEqual())
      .actionGenerator((s) -> Stream.of())
      .build()
      .search();

    Assertions.assertThat(result).isEqualTo(initial);
  }

  @Test
  public void optimizingBooleanToTrue() {
    Boolean result = HillClimbing.<Boolean>builder()
      .initial(false)
      .heuristic(Ordering.explicit(false, true))
      .actionGenerator((s) -> Stream.of((b) -> !b))
      .build()
      .search();

    Assertions.assertThat(result).isTrue();
  }

  @Test
  public void optimizeBooleanWhenTrueInvalid() {
    Boolean result = HillClimbing.<Boolean>builder()
      .initial(false)
      .heuristic(Ordering.explicit(false, true))
      .validator((s) -> !s)
      .actionGenerator((s) -> Stream.of((b) -> !b))
      .build()
      .search();

    Assertions.assertThat(result).isFalse();
  }

}

