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
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.Global.*;

@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(batchSize = ADD_OPS, iterations = ADD_WARMUP_ITER)
@Measurement(batchSize = ADD_OPS, iterations = ADD_MEAS_ITER)
public class KotlinAddition {

    private PersistentList<String> vector;
    private PersistentSet<String> hashSet;
    private PersistentMap<String, String> hashMap;
    private PersistentSet<String> orderedHashSet;
    private PersistentMap<String, String> orderedHashMap;

    @Setup(Level.Iteration)
    public void setUp() {
        vector = ExtensionsKt.persistentListOf();
        hashSet = ExtensionsKt.persistentHashSetOf();
        hashMap = ExtensionsKt.persistentHashMapOf();
        orderedHashSet = ExtensionsKt.persistentSetOf();
        orderedHashMap = ExtensionsKt.persistentMapOf();
    }

    @Benchmark
    public void benchmarkVector() {
        vector = vector.add(RandomString.create());
    }

    @Benchmark
    public void benchmarkHashSet() {
        hashSet = hashSet.add(RandomString.create());
    }

    @Benchmark
    public void benchmarkHashMap() {
        hashMap = hashMap.put(RandomString.create(), "");
    }

    @Benchmark
    public void benchmarkOrderedHashSet() {
        orderedHashSet = orderedHashSet.add(RandomString.create());
    }

    @Benchmark
    public void benchmarkOrderedHashMap() {
        orderedHashMap = orderedHashMap.put(RandomString.create(), "");
    }
}
