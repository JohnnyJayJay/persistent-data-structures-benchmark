package com.github.johnnyjayjay.benchmarks.clojure;

import clojure.lang.Associative;
import clojure.lang.IPersistentCollection;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentHashSet;
import clojure.lang.PersistentList;
import clojure.lang.PersistentQueue;
import clojure.lang.PersistentTreeMap;
import clojure.lang.PersistentTreeSet;
import clojure.lang.PersistentVector;
import clojure.lang.RT;
import com.github.johnnyjayjay.benchmarks.RandomString;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.Global.*;

@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(batchSize = ADD_OPS, iterations = ADD_WARMUP_ITER)
@Measurement(batchSize = ADD_OPS, iterations = ADD_MEAS_ITER)
public class ClojureAddition {

    static {
        ClojureFix.run();
    }

    private IPersistentCollection vector;
    private IPersistentCollection list;
    private IPersistentCollection hashSet;
    private Associative hashMap;
    private IPersistentCollection treeSet;
    private Associative treeMap;
    private IPersistentCollection queue;


    @Setup(Level.Iteration)
    public void setUp() {
        vector = PersistentVector.EMPTY;
        list = PersistentList.EMPTY;
        hashSet = PersistentHashSet.EMPTY;
        hashMap = PersistentHashMap.EMPTY;
        treeSet = PersistentTreeSet.EMPTY;
        treeMap = PersistentTreeMap.EMPTY;
        queue = PersistentQueue.EMPTY;
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        vector = null;
        list = null;
        hashSet = null;
        hashMap = null;
        treeSet = null;
        treeMap = null;
        queue = null;
    }

    @Benchmark
    public void benchmarkVector() {
        vector = RT.conj(vector, RandomString.create());
    }

    @Benchmark
    public void benchmarkList() {
        list = RT.conj(list, RandomString.create());
    }

    @Benchmark
    public void benchmarkHashSet() {
        hashSet = RT.conj(hashSet, RandomString.create());
    }

    @Benchmark
    public void benchmarkHashMap() {
        hashMap = RT.assoc(hashMap, RandomString.create(), "");
    }

    @Benchmark
    public void benchmarkTreeSet() {
        treeSet = RT.conj(treeSet, RandomString.create());
    }

    @Benchmark
    public void benchmarkTreeMap() {
        treeMap = RT.assoc(treeMap, RandomString.create(), "");
    }

    @Benchmark
    public void benchmarkQueue() {
        queue = RT.conj(queue, RandomString.create());
    }
}
