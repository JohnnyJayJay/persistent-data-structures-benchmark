package com.github.johnnyjayjay.benchmarks.pcoll;

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
import org.pcollections.ConsPStack;
import org.pcollections.HashPMap;
import org.pcollections.HashTreePBag;
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.MapPBag;
import org.pcollections.MapPSet;
import org.pcollections.TreePVector;

import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.Global.ADD_OPS;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(batchSize = ADD_OPS)
public class PCollectionsAddition {

    private TreePVector<String> vector;
    private ConsPStack<String> stack;
    private MapPSet<String> set;
    private HashPMap<String, String> map;
    private MapPBag<String> bag;


    @Setup(Level.Iteration)
    public void setUp() {
        vector = TreePVector.empty();
        stack = ConsPStack.empty();
        set = HashTreePSet.empty();
        map = HashTreePMap.empty();
        bag = HashTreePBag.empty();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        vector = null;
        stack = null;
        set = null;
        map = null;
        bag = null;
    }

    @Benchmark
    public void benchmarkVector() {
        vector = vector.plus(RandomString.create());
    }

    @Benchmark
    public void benchmarkStack() {
        stack = stack.plus(RandomString.create());
    }

    @Benchmark
    public void benchmarkSet() {
        set = set.plus(RandomString.create());
    }

    @Benchmark
    public void benchmarkMap() {
        map = map.plus(RandomString.create(), "");
    }

    @Benchmark
    public void benchmarkBag() {
        bag = bag.plus(RandomString.create());
    }
}
