package com.github.johnnyjayjay.benchmarks;

import clojure.core.Vec;
import clojure.java.api.Clojure;
import clojure.lang.IPersistentCollection;
import clojure.lang.IPersistentVector;
import clojure.lang.RT;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ClojureCollections {

    IPersistentCollection vec = RT.vector();


    // ClojureCollections.benchmarkVec  avgt   25  26555.233 ± 10861.300  ns/op
    @Benchmark
    public void benchmarkVec() {
        vec = RT.conj(vec, RandomString.create());
    }
}
