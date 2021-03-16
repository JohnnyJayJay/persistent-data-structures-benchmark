package com.github.johnnyjayjay.benchmarks;

import clojure.lang.Associative;
import clojure.lang.IPersistentCollection;
import clojure.lang.RT;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ClojureAddition {

    private IPersistentCollection vector;
    private IPersistentCollection set;
    private Associative map;

    @Setup(Level.Iteration)
    public void setUp() {
        this.vector = RT.vector();
        this.set = RT.set();
        this.map = RT.map();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        this.vector = null;
        this.set = null;
        this.map = null;
    }

    @Benchmark
    public void benchmarkVector() {
        vector = RT.conj(vector, RandomString.create());
    }

    @Benchmark
    public void benchmarkSet() {
        set = RT.conj(set, RandomString.create());
    }

    @Benchmark
    public void benchmarkMap() {
        map = RT.assoc(map, RandomString.create(), "");
    }
}
