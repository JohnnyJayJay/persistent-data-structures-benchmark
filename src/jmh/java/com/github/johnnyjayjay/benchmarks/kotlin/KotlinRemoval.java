package com.github.johnnyjayjay.benchmarks.kotlin;

import kotlinx.collections.immutable.ExtensionsKt;
import kotlinx.collections.immutable.PersistentList;
import kotlinx.collections.immutable.PersistentMap;
import kotlinx.collections.immutable.PersistentSet;
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

import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.Global.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(batchSize = REMOVE_OPS)
public class KotlinRemoval {

    @State(Scope.Thread)
    public static class VectorState {
        PersistentList<String> vector;

        @Setup(Level.Iteration)
        public void setUp() {
            vector = ExtensionsKt.persistentListOf(elements());
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            vector = null;
        }
    }

    @State(Scope.Thread)
    public static class HashSetState {
        int index;
        PersistentSet<String> hashSet;

        @Setup(Level.Iteration)
        public void setUp() {
            hashSet = ExtensionsKt.persistentHashSetOf(elements());
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            hashSet = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class HashMapState {
        int index;
        PersistentMap<String, String> hashMap;

        @Setup(Level.Iteration)
        public void setUp() {
            PersistentMap.Builder<String, String> builder = ExtensionsKt.<String, String>persistentHashMapOf().builder();
            for (String element : elements()) {
                builder.put(element, "");
            }
            hashMap = builder.build();
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
    public static class OrderedHashSetState {
        int index;
        PersistentSet<String> orderedHashSet;

        @Setup(Level.Iteration)
        public void setUp() {
            orderedHashSet = ExtensionsKt.persistentSetOf(elements());
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            orderedHashSet = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class OrderedHashMapState {
        int index;
        PersistentMap<String, String> orderedHashMap;

        @Setup(Level.Iteration)
        public void setUp() {
            PersistentMap.Builder<String, String> builder = ExtensionsKt.<String, String>persistentMapOf().builder();
            for (String element : elements()) {
                builder.put(element, "");
            }
            orderedHashMap = builder.build();
            shuffleElements();
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            orderedHashMap = null;
            index = 0;
        }
    }

    @Benchmark
    public void benchmarkVector(VectorState state) {
        state.vector = state.vector.removeAt(randomIndex());
    }

    @Benchmark
    public void benchmarkHashSet(HashSetState state) {
        state.hashSet = state.hashSet.remove(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkHashMap(HashMapState state) {
        state.hashMap = state.hashMap.remove(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkOrderedHashSet(OrderedHashSetState state) {
        state.orderedHashSet = state.orderedHashSet.remove(elements()[state.index++]);
    }

    @Benchmark
    public void benchmarkOrderedHashMap(OrderedHashMapState state) {
        state.orderedHashMap = state.orderedHashMap.remove(elements()[state.index++]);
    }

}
