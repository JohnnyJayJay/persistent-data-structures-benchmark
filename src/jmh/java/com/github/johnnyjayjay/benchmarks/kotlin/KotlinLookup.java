package com.github.johnnyjayjay.benchmarks.kotlin;

import com.github.johnnyjayjay.benchmarks.RandomString;
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
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.Global.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class KotlinLookup {

    @State(Scope.Thread)
    public static class VectorState {
        PersistentList<String> vector;

        @Setup
        public void setUp() {
            vector = ExtensionsKt.persistentListOf(elements());
        }

        @TearDown
        public void tearDown() {
            vector = null;
        }
    }

    @State(Scope.Thread)
    public static class HashSetState {
        PersistentSet<String> hashSet;

        @Setup
        public void setUp() {
            hashSet = ExtensionsKt.persistentHashSetOf(elements());
        }

        @TearDown
        public void tearDown() {
            hashSet = null;
        }
    }

    @State(Scope.Thread)
    public static class HashMapState {
        PersistentMap<String, String> hashMap;

        @Setup
        public void setUp() {
            PersistentMap.Builder<String, String> builder = ExtensionsKt.<String, String>persistentHashMapOf().builder();
            for (String element : elements()) {
                builder.put(element, "");
            }
            hashMap = builder.build();
        }

        @TearDown
        public void tearDown() {
            hashMap = null;
        }
    }

    @State(Scope.Thread)
    public static class OrderedHashSetState {
        PersistentSet<String> orderedHashSet;

        @Setup
        public void setUp() {
            orderedHashSet = ExtensionsKt.persistentSetOf(elements());
        }

        @TearDown
        public void tearDown() {
            orderedHashSet = null;
        }
    }

    @State(Scope.Thread)
    public static class OrderedHashMapState {
        PersistentMap<String, String> orderedHashMap;

        @Setup
        public void setUp() {
            PersistentMap.Builder<String, String> builder = ExtensionsKt.<String, String>persistentHashMapOf().builder();
            for (String element : elements()) {
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
    public String benchmarkVector(VectorState state) {
        return state.vector.get(randomIndex());
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashSet(Blackhole blackhole, HashSetState state) {
        blackhole.consume(state.hashSet.contains(randomElement()));
        blackhole.consume(state.hashSet.contains(RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkHashMap(Blackhole blackhole, HashMapState state) {
        blackhole.consume(state.hashMap.get(randomElement()));
        blackhole.consume(state.hashMap.get(RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkOrderedHashSet(Blackhole blackhole, OrderedHashSetState state) {
        blackhole.consume(state.orderedHashSet.contains(randomElement()));
        blackhole.consume(state.orderedHashSet.contains(RandomString.create()));
    }

    @Benchmark
    @OperationsPerInvocation(2)
    public void benchmarkOrderedHashMap(Blackhole blackhole, OrderedHashMapState state) {
        blackhole.consume(state.orderedHashMap.get(randomElement()));
        blackhole.consume(state.orderedHashMap.get(RandomString.create()));
    }
}
