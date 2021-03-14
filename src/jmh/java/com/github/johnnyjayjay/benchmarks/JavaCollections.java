package com.github.johnnyjayjay.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JavaCollections {

    List<String> list = new ArrayList<>();

    // JavaCollections.benchmarkArrayList  avgt   25  24558.171 ± 10560.335  ns/op
    @Benchmark
    public void benchmarkArrayList() {
        list.add(RandomString.create());
    }

}
