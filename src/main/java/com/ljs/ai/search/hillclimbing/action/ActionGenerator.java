package com.ljs.ai.search.hillclimbing.action;

import com.google.common.base.Function;

/**
 *
 * @author lstephen
 */
public interface ActionGenerator<S> extends Function<S, Iterable<Action<S>>> {

}
