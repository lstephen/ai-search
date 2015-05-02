package com.github.lstephen.ai.search;

import java.util.Collections;

import com.google.common.collect.Ordering;

import org.assertj.core.api.Assertions;

import org.junit.Test;

public class TestRepeatedHillClimbing {

  @Test
  public void whenNoActionsReturnInitialState() {
    String initial = "initial";

    HillClimbing.Builder<String> builder = HillClimbing.<String>builder()
      .heuristic(Ordering.allEqual())
      .actionGenerator((s) -> Collections.emptyList());

    RepeatedHillClimbing<String> repeated = new RepeatedHillClimbing<>(() -> initial, builder);

    String result = repeated.search();

    Assertions.assertThat(result).isEqualTo(initial);
  }

}

