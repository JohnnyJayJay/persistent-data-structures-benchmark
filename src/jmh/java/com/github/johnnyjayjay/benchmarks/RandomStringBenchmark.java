package com.github.johnnyjayjay.benchmarks;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

@BenchmarkMode(Mode.AverageTime)
public class RandomStringBenchmark {

    @Benchmark
    public void benchmarkRandomStringCreation() {
        RandomString.create();
    }

}
