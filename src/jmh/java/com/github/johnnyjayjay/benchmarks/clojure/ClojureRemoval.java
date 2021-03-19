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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.RandomString.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(time = 5)
public class ClojureRemoval {

    @State(Scope.Thread)
    public static class ListState {
        IPersistentList list;

        @Setup(Level.Iteration)
        public void setUp() {
            list = PersistentList.create(Arrays.asList(shuffledElements()));
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
            hashSet = PersistentHashSet.create((Object[]) shuffledElements());
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
            for (String element : shuffledElements()) {
                transientMap.assoc(element, "");
            }
            hashMap = transientMap.persistent();
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
            treeSet = PersistentTreeSet.create(PersistentVector.create((Object[]) shuffledElements()).seq());
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
            for (String element : shuffledElements()) {
                transientMap.put(element, "");
            }
            treeMap = PersistentTreeMap.create(transientMap);
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
            for (String element : shuffledElements()) {
                queue = (IPersistentList) RT.conj(queue, element);
            }
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
        state.hashSet = state.hashSet.disjoin(ELEMENTS[state.index == SIZE ? 0 : state.index++]);
    }

    @Benchmark
    public void benchmarkHashMap(HashMapState state) {
        state.hashMap = (IPersistentMap) RT.dissoc(state.hashMap, ELEMENTS[state.index == SIZE ? 0 : state.index++]);
    }

    @Benchmark
    public void benchmarkTreeSet(TreeSetState state) {
        state.treeSet = state.treeSet.disjoin(ELEMENTS[state.index == SIZE ? 0 : state.index++]);
    }

    @Benchmark
    public void benchmarkTreeMap(TreeMapState state) {
        state.treeMap = (IPersistentMap) RT.dissoc(state.treeMap, ELEMENTS[state.index == SIZE ? 0 : state.index++]);
    }

    @Benchmark
    public void benchmarkQueue(QueueState state) {
        state.queue = (IPersistentList) RT.pop(state.queue);
    }
}
