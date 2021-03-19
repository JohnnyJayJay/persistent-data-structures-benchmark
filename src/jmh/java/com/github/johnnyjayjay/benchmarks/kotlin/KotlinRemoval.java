package com.github.johnnyjayjay.benchmarks.kotlin;

import com.github.johnnyjayjay.benchmarks.RandomString;
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

import static com.github.johnnyjayjay.benchmarks.RandomString.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(time = 5)
public class KotlinRemoval {

    @State(Scope.Thread)
    public static class VectorState {
        PersistentList<String> vector;

        @Setup(Level.Iteration)
        public void setUp() {
            vector = ExtensionsKt.persistentListOf(ELEMENTS);
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
            hashSet = ExtensionsKt.persistentHashSetOf(shuffledElements());
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
            for (String element : shuffledElements()) {
                builder.put(element, "");
            }
            hashMap = builder.build();
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
            orderedHashSet = ExtensionsKt.persistentSetOf(shuffledElements());
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
            PersistentMap.Builder<String, String> builder = ExtensionsKt.<String, String>persistentHashMapOf().builder();
            for (String element : shuffledElements()) {
                builder.put(element, "");
            }
            orderedHashMap = builder.build();
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
        PersistentList<String> vector = state.vector;
        if (!vector.isEmpty()) {
            state.vector = vector.removeAt(RANDOM.nextInt(vector.size()));
        }
    }

    @Benchmark
    public void benchmarkHashSet(HashSetState state) {
        state.hashSet = state.hashSet.remove(ELEMENTS[state.index == ELEMENTS.length ? 0 : state.index++]);
    }

    @Benchmark
    public void benchmarkHashMap(HashMapState state) {
        state.hashMap = state.hashMap.remove(ELEMENTS[state.index == ELEMENTS.length ? 0 : state.index++]);
    }

    @Benchmark
    public void benchmarkOrderedHashSet(OrderedHashSetState state) {
        state.orderedHashSet = state.orderedHashSet.remove(ELEMENTS[state.index == ELEMENTS.length ? 0 : state.index++]);
    }

    @Benchmark
    public void benchmarkOrderedHashMap(OrderedHashMapState state) {
        state.orderedHashMap = state.orderedHashMap.remove(ELEMENTS[state.index == ELEMENTS.length ? 0 : state.index++]);
    }

}
