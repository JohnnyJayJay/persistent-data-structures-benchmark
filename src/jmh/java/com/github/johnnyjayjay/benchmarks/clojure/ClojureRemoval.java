package com.github.johnnyjayjay.benchmarks.clojure;

import clojure.lang.IPersistentList;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.ITransientMap;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentHashSet;
import clojure.lang.PersistentList;
import clojure.lang.PersistentQueue;
import clojure.lang.PersistentTreeMap;
import clojure.lang.PersistentTreeSet;
import clojure.lang.PersistentVector;
import clojure.lang.RT;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.Global.*;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(batchSize = REMOVE_OPS, iterations = REMOVE_WARMUP_ITER)
@Measurement(batchSize = REMOVE_OPS, iterations = REMOVE_MEAS_ITER)
public class ClojureRemoval {

    @State(Scope.Thread)
    public static class ListState {
        IPersistentList list;

        @Setup(Level.Iteration)
        public void setUp() {
            list = PersistentList.create(Arrays.asList(elements()));
            shuffleElements();
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            list = null;
        }
    }

    @State(Scope.Thread)
    public static class HashSetState {
        int index;
        IPersistentSet hashSet;

        @Setup(Level.Iteration)
        public void setUp() {
            index = 0;
            hashSet = PersistentHashSet.create((Object[]) elements());
            shuffleElements();
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            index = 0;
            hashSet = null;
        }
    }

    @State(Scope.Thread)
    public static class HashMapState {
        int index;
        IPersistentMap hashMap;

        @Setup(Level.Iteration)
        public void setUp() {
            ITransientMap transientMap = PersistentHashMap.EMPTY.asTransient();
            for (String element : elements()) {
                transientMap.assoc(element, "");
            }
            hashMap = transientMap.persistent();
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            hashMap = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class TreeSetState {
        int index;
        IPersistentSet treeSet;

        @Setup(Level.Iteration)
        public void setUp() {
            treeSet = PersistentTreeSet.create(PersistentVector.create((Object[]) elements()).seq());
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            treeSet = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class TreeMapState {
        int index;
        IPersistentMap treeMap;

        @Setup(Level.Iteration)
        public void setUp() {
            Map<String, String> transientMap = new HashMap<>();
            for (String element : elements()) {
                transientMap.put(element, "");
            }
            treeMap = PersistentTreeMap.create(transientMap);
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            treeMap = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class QueueState {
        IPersistentList queue;

        @Setup(Level.Iteration)
        public void setUp() {
            queue = PersistentQueue.EMPTY;
            for (String element : elements()) {
                queue = (IPersistentList) RT.conj(queue, element);
            }
            shuffleElements();
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            queue = null;
        }
    }

    @Benchmark
    public void benchmarkList(ListState state) {
        state.list = (IPersistentList) RT.pop(state.list);
    }

    @Benchmark
    public void benchmarkHashSet(HashSetState state) {
        state.hashSet = state.hashSet.disjoin(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkHashMap(HashMapState state) {
        state.hashMap = (IPersistentMap) RT.dissoc(state.hashMap, elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkTreeSet(TreeSetState state) {
        state.treeSet = state.treeSet.disjoin(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkTreeMap(TreeMapState state) {
        state.treeMap = (IPersistentMap) RT.dissoc(state.treeMap, elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkQueue(QueueState state) {
        state.queue = (IPersistentList) RT.pop(state.queue);
    }
}
