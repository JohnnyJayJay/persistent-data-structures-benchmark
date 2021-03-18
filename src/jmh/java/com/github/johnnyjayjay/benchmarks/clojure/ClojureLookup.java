package com.github.johnnyjayjay.benchmarks.clojure;

import clojure.lang.IPersistentList;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.IPersistentVector;
import clojure.lang.ITransientMap;
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
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.github.johnnyjayjay.benchmarks.RandomString.*;

public class ClojureLookup {

    @State(Scope.Benchmark)
    public static class VectorState {
        IPersistentVector vector;

        @Setup
        public void setUp() {
            vector = PersistentVector.create((Object[]) ELEMENTS);
        }

        @TearDown
        public void tearDown() {
            vector = null;
        }
    }

    @State(Scope.Benchmark)
    public static class ListState {
        IPersistentList list;

        @Setup
        public void setUp() {
            list = PersistentList.create(Arrays.asList(ELEMENTS));
        }

        @TearDown
        public void tearDown() {
            list = null;
        }
    }

    @State(Scope.Benchmark)
    public static class HashSetState {
        IPersistentSet hashSet;

        @Setup
        public void setUp() {
            hashSet = PersistentHashSet.create((Object[]) ELEMENTS);
        }

        @TearDown
        public void tearDown() {
            hashSet = null;
        }
    }

    @State(Scope.Benchmark)
    public static class HashMapState {
        IPersistentMap hashMap;

        @Setup
        public void setUp() {
            ITransientMap transientMap = PersistentHashMap.EMPTY.asTransient();
            for (String element : ELEMENTS) {
                transientMap.assoc(element, "");
            }
            hashMap = transientMap.persistent();
        }

        @TearDown
        public void tearDown() {
            hashMap = null;
        }
    }

    @State(Scope.Benchmark)
    public static class TreeSetState {
        IPersistentSet treeSet;

        @Setup
        public void setUp() {
            treeSet = PersistentTreeSet.create(PersistentVector.create((Object[]) ELEMENTS).seq());
        }

        @TearDown
        public void tearDown() {
            treeSet = null;
        }
    }

    @State(Scope.Benchmark)
    public static class TreeMapState {
        IPersistentMap treeMap;

        @Setup
        public void setUp() {
            Map<String, String> transientMap = new HashMap<>();
            for (String element : ELEMENTS) {
                transientMap.put(element, "");
            }
            treeMap = PersistentTreeMap.create(transientMap);
        }

        @TearDown
        public void tearDown() {
            treeMap = null;
        }
    }

    @State(Scope.Benchmark)
    public static class QueueState {
        IPersistentList queue;

        @Setup
        public void setUp() {
            queue = PersistentQueue.EMPTY;
        }
    }

    @Benchmark
    public void benchmarkVector(VectorState state) {
        Object x = RT.get(state.vector, randomIndex());
    }

    @Benchmark
    public void benchmarkList(ListState state) {
        Object x = RT.first(state.list);
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashSet(HashSetState state) {
        Object included = RT.contains(state.hashSet, randomElement());
        Object notIncluded = RT.contains(state.hashSet, RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashMap(HashMapState state) {
        Object included = RT.get(state.hashMap, randomElement());
        Object notIncluded = RT.get(state.hashMap, RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkTreeMap(TreeMapState state) {
        Object included = RT.get(state.treeMap, randomElement());
        Object notIncluded = RT.get(state.treeMap, RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkTreeSet(TreeSetState state) {
        Object included = RT.contains(state.treeSet, randomElement());
        Object notIncluded = RT.contains(state.treeSet, RandomString.create());
    }

    @Benchmark
    public void benchmarkQueue(QueueState state) {
        RT.conj(state.queue, RandomString.create());
    }
}
