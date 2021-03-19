package com.github.johnnyjayjay.benchmarks.pcoll;

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
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;
import org.pcollections.MapPSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.github.johnnyjayjay.benchmarks.RandomString.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(time = 5)
public class PCollectionsRemoval {

    @State(Scope.Thread)
    public static class StackState {
        private ConsPStack<String> stack;

        @Setup(Level.Iteration)
        public void setUp() {
            stack = ConsPStack.from(Arrays.asList(shuffledElements()));
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            stack = null;
        }
    }

    @State(Scope.Thread)
    public static class SetState {
        int index;
        private MapPSet<String> set;

        @Setup(Level.Iteration)
        public void setUp() {
            set = HashTreePSet.from(Arrays.asList(shuffledElements()));
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            set = null;
            index = 0;
        }
    }

    @State(Scope.Thread)
    public static class MapState {
        int index;
        private HashPMap<String, String> map;

        @Setup(Level.Iteration)
        public void setUp() {
            Map<String, String> transientMap = new HashMap<>(SIZE);
            for (String element : shuffledElements()) {
                transientMap.put(element, "");
            }
            map = HashTreePMap.from(transientMap);
            index = 0;
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            map = null;
            index = 0;
        }
    }

    @Benchmark
    public void benchmarkStack(StackState state) {
        state.stack = state.stack.minus(0);
    }

    @Benchmark
    public void benchmarkSet(SetState state) {
        state.set = state.set.minus(ELEMENTS[state.index == ELEMENTS.length ? 0 : state.index++]);
    }

    @Benchmark
    public void benchmarkMap(MapState state) {
        state.map = state.map.minus(ELEMENTS[state.index == ELEMENTS.length ? 0 : state.index++]);
    }

}
