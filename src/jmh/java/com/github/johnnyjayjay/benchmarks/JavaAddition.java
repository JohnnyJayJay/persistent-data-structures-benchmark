package com.github.johnnyjayjay.benchmarks;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JavaAddition {

    private List<String> list;
    private Set<String> set;
    private Map<String, String> map;

    @Setup(Level.Iteration)
    public void setUp() {
        this.list = new ArrayList<>();
        this.set = new HashSet<>();
        this.map = new HashMap<>();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        System.out.printf("List size: %d%nSet size: %d%nMap size: %d%n",
                list.size(), set.size(), map.size());
        this.list = null;
        this.set = null;
        this.map = null;
    }

    @Benchmark
    public void benchmarkArrayList() {
        list.add(RandomString.create());
    }

    @Benchmark
    public void benchmarkHashSet() {
        set.add(RandomString.create());
    }

    @Benchmark
    public void benchmarkHashMap() {
        map.put(RandomString.create(), "");
    }

}
