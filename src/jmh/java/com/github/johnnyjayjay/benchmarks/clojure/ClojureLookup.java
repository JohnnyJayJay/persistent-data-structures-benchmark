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
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.Global.*;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ClojureLookup {

    static {
        ClojureFix.run();
    }

    @State(Scope.Thread)
    public static class VectorState {
        IPersistentVector vector;

        @Setup
        public void setUp() {
            vector = PersistentVector.create((Object[]) elements());
        }

        @TearDown
        public void tearDown() {
            vector = null;
        }
    }

    @State(Scope.Thread)
    public static class ListState {
        IPersistentList list;

        @Setup
        public void setUp() {
            list = PersistentList.create(Arrays.asList(elements()));
        }

        @TearDown
        public void tearDown() {
            list = null;
        }
    }

    @State(Scope.Thread)
    public static class HashSetState {
        IPersistentSet hashSet;

        @Setup
        public void setUp() {
            hashSet = PersistentHashSet.create((Object[]) elements());
        }

        @TearDown
        public void tearDown() {
            hashSet = null;
        }
    }

    @State(Scope.Thread)
    public static class HashMapState {
        IPersistentMap hashMap;

        @Setup
        public void setUp() {
            ITransientMap transientMap = PersistentHashMap.EMPTY.asTransient();
            for (String element : elements()) {
                transientMap.assoc(element, "");
            }
            hashMap = transientMap.persistent();
        }

        @TearDown
        public void tearDown() {
            hashMap = null;
        }
    }

    @State(Scope.Thread)
    public static class TreeSetState {
        IPersistentSet treeSet;

        @Setup
        public void setUp() {
            treeSet = PersistentTreeSet.create(PersistentVector.create((Object[]) elements()).seq());
        }

        @TearDown
        public void tearDown() {
            treeSet = null;
        }
    }

    @State(Scope.Thread)
    public static class TreeMapState {
        IPersistentMap treeMap;

        @Setup
        public void setUp() {
            Map<String, String> transientMap = new HashMap<>();
            for (String element : elements()) {
                transientMap.put(element, "");
            }
            treeMap = PersistentTreeMap.create(transientMap);
        }

        @TearDown
        public void tearDown() {
            treeMap = null;
        }
    }

    @State(Scope.Thread)
    public static class QueueState {
        IPersistentList queue;

        @Setup
        public void setUp() {
            queue = PersistentQueue.EMPTY;
            for (String element : elements()) {
                queue = (IPersistentList) RT.conj(queue, element);
            }
        }

        @TearDown
        public void tearDown() {
            queue = null;
        }
    }

    @Benchmark
    public Object benchmarkVector(VectorState state) {
        return RT.get(state.vector, randomIndex());
    }

    @Benchmark
    public Object benchmarkList(ListState state) {
        return RT.first(state.list);
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashSet(Blackhole blackhole, HashSetState state) {
        blackhole.consume(RT.contains(state.hashSet, randomElement()));
        blackhole.consume(RT.contains(state.hashSet, RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashMap(Blackhole blackhole, HashMapState state) {
        blackhole.consume(RT.get(state.hashMap, randomElement()));
        blackhole.consume(RT.get(state.hashMap, RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkTreeMap(Blackhole blackhole, TreeMapState state) {
        blackhole.consume(RT.get(state.treeMap, randomElement()));
        blackhole.consume(RT.get(state.treeMap, RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkTreeSet(Blackhole blackhole, TreeSetState state) {
        blackhole.consume(RT.contains(state.treeSet, randomElement()));
        blackhole.consume(RT.contains(state.treeSet, RandomString.create()));
    }

    @Benchmark
    public Object benchmarkQueue(Blackhole blackhole, QueueState state) {
        return RT.peek(state.queue);
    }
}
