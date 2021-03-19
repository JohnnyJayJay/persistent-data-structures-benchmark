package com.github.johnnyjayjay.benchmarks.kotlin;

import com.github.johnnyjayjay.benchmarks.RandomString;
import kotlin.Pair;
import kotlinx.collections.immutable.ExtensionsKt;
import kotlinx.collections.immutable.PersistentList;
import kotlinx.collections.immutable.PersistentMap;
import kotlinx.collections.immutable.PersistentSet;
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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.RandomString.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class KotlinLookup {

    @State(Scope.Benchmark)
    public static class VectorState {
        PersistentList<String> vector;

        @Setup
        public void setUp() {
            vector = ExtensionsKt.persistentListOf(ELEMENTS);
        }

        @TearDown
        public void tearDown() {
            vector = null;
        }
    }

    @State(Scope.Benchmark)
    public static class HashSetState {
        PersistentSet<String> hashSet;

        @Setup
        public void setUp() {
            hashSet = ExtensionsKt.persistentHashSetOf(ELEMENTS);
        }

        @TearDown
        public void tearDown() {
            hashSet = null;
        }
    }

    @State(Scope.Benchmark)
    public static class HashMapState {
        PersistentMap<String, String> hashMap;

        @Setup
        public void setUp() {
            PersistentMap.Builder<String, String> builder = ExtensionsKt.<String, String>persistentHashMapOf().builder();
            for (String element : RandomString.ELEMENTS) {
                builder.put(element, "");
            }
            hashMap = builder.build();
        }

        @TearDown
        public void tearDown() {
            hashMap = null;
        }
    }

    @State(Scope.Benchmark)
    public static class OrderedHashSetState {
        PersistentSet<String> orderedHashSet;

        @Setup
        public void setUp() {
            orderedHashSet = ExtensionsKt.persistentSetOf(ELEMENTS);
        }

        @TearDown
        public void tearDown() {
            orderedHashSet = null;
        }
    }

    @State(Scope.Benchmark)
    public static class OrderedHashMapState {
        PersistentMap<String, String> orderedHashMap;

        @Setup
        public void setUp() {
            PersistentMap.Builder<String, String> builder = ExtensionsKt.<String, String>persistentHashMapOf().builder();
            for (String element : RandomString.ELEMENTS) {
                builder.put(element, "");
            }
            orderedHashMap = builder.build();
        }

        @TearDown
        public void tearDown() {
            orderedHashMap = null;
        }
    }


    @Benchmark
    public void benchmarkArrayList(VectorState state) {
        Object x = state.vector.get(randomIndex());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashSet(HashSetState state) {
        boolean included = state.hashSet.contains(randomElement());
        boolean notIncluded = state.hashSet.contains(RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashMap(HashMapState state) {
        Object included = state.hashMap.get(randomElement());
        Object notIncluded = state.hashMap.get(RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkOrderedHashSet(OrderedHashSetState state) {
        boolean included = state.orderedHashSet.contains(randomElement());
        boolean notIncluded = state.orderedHashSet.contains(RandomString.create());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkOrderedHashMap(OrderedHashMapState state) {
        Object included = state.orderedHashMap.get(randomElement());
        Object notIncluded = state.orderedHashMap.get(RandomString.create());
    }
}
