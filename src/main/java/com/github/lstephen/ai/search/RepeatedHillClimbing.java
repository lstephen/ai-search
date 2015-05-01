package com.github.lstephen.ai.search;

import com.google.common.base.Throwables;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 *
 * @author lstephen
 */
public class RepeatedHillClimbing<S> {

    private static final Integer REPEATS = 5;

    private Callable<S> initialFactory;

    private HillClimbing.Builder<S> hillClimbing;

    public RepeatedHillClimbing(Callable<S> initialFactory, HillClimbing.Builder<S> hillClimbing) {
        this.initialFactory = initialFactory;
        this.hillClimbing = hillClimbing;
    }

    public S search() {
        try {
            System.out.print("Searching (" + initialFactory.call().getClass().getSimpleName() + ") ...");
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }

        ListeningExecutorService executor = MoreExecutors.listeningDecorator(
            Executors.newFixedThreadPool(REPEATS));

        Set<ListenableFuture<S>> candidatesF = Sets.newHashSet();

        for (int i = 0; i < REPEATS; i++) {
            candidatesF.add(executor.submit(doSearch()));
        }

        try {
            S result = Ordering.from(hillClimbing.getHeuristic()).max(Futures.allAsList(candidatesF).get());
            System.out.println(" Done. ");
            return result;
        } catch (InterruptedException e) {
            throw Throwables.propagate(e);
        } catch (ExecutionException e) {
            throw Throwables.propagate(e);
        } finally {
            executor.shutdown();
        }
    }

    private Callable<S> doSearch() {
        return new Callable<S>() {
            public S call() {
                System.out.print(">-");

                try {
                    S result = hillClimbing.initial(initialFactory.call()).build().search();

                    System.out.print("-|");

                    return result;
                } catch (Exception e) {
                    throw Throwables.propagate(e);
                }
            }
        };
    }

}
